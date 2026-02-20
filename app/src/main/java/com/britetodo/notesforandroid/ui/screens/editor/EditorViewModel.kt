package com.britetodo.notesforandroid.ui.screens.editor

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.britetodo.notesforandroid.data.models.Page
import com.britetodo.notesforandroid.data.models.StickerItem
import com.britetodo.notesforandroid.data.models.TextNote
import com.britetodo.notesforandroid.data.repository.NotebookRepository
import com.britetodo.notesforandroid.drawing.DrawingEngine
import com.britetodo.notesforandroid.drawing.DrawingTool
import com.britetodo.notesforandroid.drawing.ToolSettings
import com.britetodo.notesforandroid.templates.TemplateRegistry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val repository: NotebookRepository,
) : ViewModel() {

    val drawingEngine = DrawingEngine()

    var page by mutableStateOf<Page?>(null)
        private set

    var toolSettings by mutableStateOf(ToolSettings())
        private set

    var textNotes by mutableStateOf<List<TextNote>>(emptyList())
        private set

    var stickers by mutableStateOf<List<StickerItem>>(emptyList())
        private set

    var selectedTextNoteId by mutableStateOf<String?>(null)
    var selectedStickerId by mutableStateOf<String?>(null)

    var showTemplateGallery by mutableStateOf(false)
    var showStickerGallery by mutableStateOf(false)
    var showColorPicker by mutableStateOf(false)

    private var autoSaveJob: Job? = null
    private var currentPageId: String? = null
    private var currentNotebookId: String? = null

    // ─── Init ─────────────────────────────────────────────────────────────────

    fun init(notebookId: String, pageId: String) {
        currentNotebookId = notebookId
        currentPageId = pageId
        viewModelScope.launch {
            val loadedPage = repository.getPage(pageId) ?: return@launch
            page = loadedPage
            // Load drawing strokes
            val strokes = repository.loadDrawingStrokes(pageId)
            drawingEngine.loadStrokes(strokes)
            // Load text notes and stickers
            textNotes = repository.loadTextNotes(pageId)
            stickers = repository.loadStickers(pageId)
        }
    }

    // ─── Tool settings ────────────────────────────────────────────────────────

    fun setTool(tool: DrawingTool) {
        toolSettings = toolSettings.copy(tool = tool)
    }

    fun setColor(argb: Long) {
        toolSettings = toolSettings.copy(colorArgb = argb)
    }

    fun setStrokeWidth(width: Float) {
        toolSettings = toolSettings.copy(strokeWidth = width)
    }

    fun setOpacity(opacity: Float) {
        toolSettings = toolSettings.copy(opacity = opacity)
    }

    // ─── Undo/Redo ────────────────────────────────────────────────────────────

    fun undo() {
        drawingEngine.undo()
        scheduleAutoSave()
    }

    fun redo() {
        drawingEngine.redo()
        scheduleAutoSave()
    }

    // ─── Stroke input ─────────────────────────────────────────────────────────

    fun onStrokeStarted(point: com.britetodo.notesforandroid.data.models.DrawingPoint) {
        drawingEngine.beginStroke(point, toolSettings)
    }

    fun onStrokeProgress(point: com.britetodo.notesforandroid.data.models.DrawingPoint) {
        drawingEngine.continueStroke(point)
    }

    fun onStrokeEnded() {
        drawingEngine.endStroke()
        scheduleAutoSave()
    }

    // ─── Text notes ───────────────────────────────────────────────────────────

    fun addTextNote(xNorm: Float, yNorm: Float) {
        val note = TextNote(
            id = UUID.randomUUID().toString(),
            xNorm = xNorm,
            yNorm = yNorm,
        )
        textNotes = textNotes + note
        selectedTextNoteId = note.id
        scheduleAutoSave()
    }

    fun updateTextNote(noteId: String, text: String) {
        textNotes = textNotes.map { if (it.id == noteId) it.copy(text = text, updatedAt = System.currentTimeMillis()) else it }
        scheduleAutoSave()
    }

    fun moveTextNote(noteId: String, xNorm: Float, yNorm: Float) {
        textNotes = textNotes.map { if (it.id == noteId) it.copy(xNorm = xNorm, yNorm = yNorm) else it }
        scheduleAutoSave()
    }

    fun deleteTextNote(noteId: String) {
        textNotes = textNotes.filter { it.id != noteId }
        if (selectedTextNoteId == noteId) selectedTextNoteId = null
        scheduleAutoSave()
    }

    // ─── Stickers ─────────────────────────────────────────────────────────────

    fun addSticker(assetId: String, xNorm: Float = 0.4f, yNorm: Float = 0.4f) {
        val sticker = StickerItem(
            id = UUID.randomUUID().toString(),
            assetId = assetId,
            xNorm = xNorm,
            yNorm = yNorm,
            zIndex = stickers.size,
        )
        stickers = stickers + sticker
        selectedStickerId = sticker.id
        scheduleAutoSave()
    }

    fun moveSticker(stickerId: String, xNorm: Float, yNorm: Float) {
        stickers = stickers.map { if (it.id == stickerId) it.copy(xNorm = xNorm, yNorm = yNorm) else it }
        scheduleAutoSave()
    }

    fun resizeSticker(stickerId: String, widthNorm: Float, heightNorm: Float) {
        stickers = stickers.map { if (it.id == stickerId) it.copy(widthNorm = widthNorm, heightNorm = heightNorm) else it }
        scheduleAutoSave()
    }

    fun rotateSticker(stickerId: String, rotation: Float) {
        stickers = stickers.map { if (it.id == stickerId) it.copy(rotation = rotation) else it }
        scheduleAutoSave()
    }

    fun deleteSticker(stickerId: String) {
        stickers = stickers.filter { it.id != stickerId }
        if (selectedStickerId == stickerId) selectedStickerId = null
        scheduleAutoSave()
    }

    // ─── Template ─────────────────────────────────────────────────────────────

    fun changeTemplate(templateId: String) {
        val p = page ?: return
        page = p.copy(templateId = templateId, updatedAt = System.currentTimeMillis())
        showTemplateGallery = false
        scheduleAutoSave()
    }

    // ─── Auto save ────────────────────────────────────────────────────────────

    private fun scheduleAutoSave() {
        autoSaveJob?.cancel()
        autoSaveJob = viewModelScope.launch {
            delay(1500) // debounce 1.5s
            saveNow()
        }
    }

    fun saveNow() {
        val pageId = currentPageId ?: return
        viewModelScope.launch {
            repository.saveDrawingStrokes(pageId, drawingEngine.strokes.toList())
            repository.saveTextNotes(pageId, textNotes)
            repository.saveStickers(pageId, stickers)
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Final save on close
        saveNow()
    }
}
