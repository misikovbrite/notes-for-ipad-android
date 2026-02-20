package com.britetodo.notesforandroid.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PageDao {
    @Query("SELECT * FROM pages WHERE notebookId = :notebookId ORDER BY `order` ASC")
    fun observeByNotebook(notebookId: String): Flow<List<PageEntity>>

    @Query("SELECT * FROM pages WHERE notebookId = :notebookId ORDER BY `order` ASC")
    suspend fun getByNotebook(notebookId: String): List<PageEntity>

    @Query("SELECT * FROM pages WHERE id = :id")
    suspend fun getById(id: String): PageEntity?

    @Query("SELECT * FROM pages WHERE id = :id")
    fun observeById(id: String): Flow<PageEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(page: PageEntity)

    @Update
    suspend fun update(page: PageEntity)

    @Delete
    suspend fun delete(page: PageEntity)

    @Query("DELETE FROM pages WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM pages WHERE notebookId = :notebookId")
    suspend fun deleteByNotebook(notebookId: String)

    @Query("SELECT COUNT(*) FROM pages WHERE notebookId = :notebookId")
    suspend fun countByNotebook(notebookId: String): Int

    @Query("UPDATE pages SET thumbnailPath = :path, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateThumbnail(id: String, path: String, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE pages SET drawingFilePath = :path, hasContent = 1, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateDrawingPath(id: String, path: String, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE pages SET textNotesJson = :json, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateTextNotes(id: String, json: String, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE pages SET stickersJson = :json, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateStickers(id: String, json: String, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE pages SET `order` = :order WHERE id = :id")
    suspend fun updateOrder(id: String, order: Int)
}
