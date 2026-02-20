package com.britetodo.notesforandroid.data.repository

import android.content.Context
import android.graphics.Bitmap
import com.britetodo.notesforandroid.data.db.*
import com.britetodo.notesforandroid.data.models.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotebookRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notebookDao: NotebookDao,
    private val pageDao: PageDao,
) {
    private val json = Json { ignoreUnknownKeys = true; prettyPrint = false }

    // ─── Notebooks ───────────────────────────────────────────────────────────

    fun observeNotebooks(): Flow<List<Notebook>> =
        notebookDao.observeAll().map { list -> list.map { it.toModel() } }

    fun observeFavorites(): Flow<List<Notebook>> =
        notebookDao.observeFavorites().map { list -> list.map { it.toModel() } }

    fun observeArchived(): Flow<List<Notebook>> =
        notebookDao.observeArchived().map { list -> list.map { it.toModel() } }

    fun observeNotebook(id: String): Flow<Notebook?> =
        notebookDao.observeById(id).map { it?.toModel() }

    suspend fun getNotebook(id: String): Notebook? =
        notebookDao.getById(id)?.toModel()

    suspend fun createNotebook(
        name: String,
        colorHex: String = "#4A90E2",
        spineColorHex: String = "#357ABD",
        iconName: String = "book",
        templateId: String = "blank",
    ): Notebook {
        val notebook = Notebook(
            id = UUID.randomUUID().toString(),
            name = name,
            colorHex = colorHex,
            spineColorHex = spineColorHex,
            iconName = iconName,
            templateId = templateId,
        )
        notebookDao.insert(notebook.toEntity())
        return notebook
    }

    suspend fun updateNotebook(notebook: Notebook) {
        notebookDao.update(notebook.copy(updatedAt = System.currentTimeMillis()).toEntity())
    }

    suspend fun deleteNotebook(id: String) {
        notebookDao.deleteById(id)
        // Files for pages cleaned up via CASCADE + file cleanup
        val drawingsDir = File(context.filesDir, "drawings")
        val thumbnailsDir = File(context.filesDir, "thumbnails")
        // Page files are deleted when pages are deleted via CASCADE
    }

    suspend fun toggleFavorite(id: String, isFavorite: Boolean) {
        notebookDao.setFavorite(id, isFavorite)
    }

    suspend fun setArchived(id: String, isArchived: Boolean) {
        notebookDao.setArchived(id, isArchived)
    }

    suspend fun countActiveNotebooks(): Int = notebookDao.countActive()

    // ─── Pages ───────────────────────────────────────────────────────────────

    fun observePages(notebookId: String): Flow<List<Page>> =
        pageDao.observeByNotebook(notebookId).map { list -> list.map { it.toModel() } }

    fun observePage(id: String): Flow<Page?> =
        pageDao.observeById(id).map { it?.toModel() }

    suspend fun getPage(id: String): Page? = pageDao.getById(id)?.toModel()

    suspend fun getPages(notebookId: String): List<Page> =
        pageDao.getByNotebook(notebookId).map { it.toModel() }

    suspend fun createPage(notebookId: String, templateId: String = "blank", date: Long? = null): Page {
        val pageCount = pageDao.countByNotebook(notebookId)
        val page = Page(
            id = UUID.randomUUID().toString(),
            notebookId = notebookId,
            templateId = templateId,
            order = pageCount,
            date = date,
        )
        pageDao.insert(page.toEntity())
        notebookDao.updatePageCount(notebookId, pageCount + 1)
        notebookDao.updateLastPage(notebookId, page.id)
        return page
    }

    suspend fun deletePage(id: String, notebookId: String) {
        val page = pageDao.getById(id) ?: return
        // Clean up drawing file
        page.drawingFilePath?.let { File(it).delete() }
        page.thumbnailPath?.let { File(it).delete() }
        pageDao.deleteById(id)
        // Re-order remaining pages
        val remaining = pageDao.getByNotebook(notebookId)
        remaining.forEachIndexed { index, p -> pageDao.updateOrder(p.id, index) }
        notebookDao.updatePageCount(notebookId, remaining.size)
    }

    // ─── Drawing Storage ─────────────────────────────────────────────────────

    suspend fun saveDrawingStrokes(pageId: String, strokes: List<Stroke>): String =
        withContext(Dispatchers.IO) {
            val dir = File(context.filesDir, "drawings").apply { mkdirs() }
            val file = File(dir, "$pageId.json")
            file.writeText(json.encodeToString(strokes))
            pageDao.updateDrawingPath(pageId, file.absolutePath)
            file.absolutePath
        }

    suspend fun loadDrawingStrokes(pageId: String): List<Stroke> =
        withContext(Dispatchers.IO) {
            val dir = File(context.filesDir, "drawings")
            val file = File(dir, "$pageId.json")
            if (!file.exists()) return@withContext emptyList()
            try {
                json.decodeFromString<List<Stroke>>(file.readText())
            } catch (e: Exception) {
                emptyList()
            }
        }

    // ─── Text Notes Storage ───────────────────────────────────────────────────

    suspend fun saveTextNotes(pageId: String, notes: List<TextNote>) {
        pageDao.updateTextNotes(pageId, json.encodeToString(notes))
    }

    suspend fun loadTextNotes(pageId: String): List<TextNote> {
        val page = pageDao.getById(pageId) ?: return emptyList()
        val notesJson = page.textNotesJson ?: return emptyList()
        return try {
            json.decodeFromString(notesJson)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // ─── Sticker Storage ─────────────────────────────────────────────────────

    suspend fun saveStickers(pageId: String, stickers: List<StickerItem>) {
        pageDao.updateStickers(pageId, json.encodeToString(stickers))
    }

    suspend fun loadStickers(pageId: String): List<StickerItem> {
        val page = pageDao.getById(pageId) ?: return emptyList()
        val stickersJson = page.stickersJson ?: return emptyList()
        return try {
            json.decodeFromString(stickersJson)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // ─── Thumbnail Storage ───────────────────────────────────────────────────

    suspend fun saveThumbnail(pageId: String, bitmap: Bitmap): String =
        withContext(Dispatchers.IO) {
            val dir = File(context.filesDir, "thumbnails").apply { mkdirs() }
            val file = File(dir, "$pageId.png")
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 85, out)
            }
            pageDao.updateThumbnail(pageId, file.absolutePath)
            file.absolutePath
        }
}
