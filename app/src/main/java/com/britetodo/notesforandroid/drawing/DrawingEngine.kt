package com.britetodo.notesforandroid.drawing

import androidx.compose.runtime.mutableStateListOf
import com.britetodo.notesforandroid.data.models.DrawingPoint
import com.britetodo.notesforandroid.data.models.Stroke
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID

/**
 * Core drawing engine: manages committed strokes, in-progress stroke,
 * undo/redo stacks, and serialization.
 *
 * Mirrors the PencilKit-backed DrawingViewController on iOS.
 */
class DrawingEngine {

    val strokes = mutableStateListOf<Stroke>()
    private val undoStack = mutableListOf<Stroke>()

    var currentStroke: Stroke? = null
        private set

    // ─── Stroke lifecycle ─────────────────────────────────────────────────────

    fun beginStroke(point: DrawingPoint, settings: ToolSettings) {
        undoStack.clear()
        currentStroke = Stroke(
            id = UUID.randomUUID().toString(),
            points = listOf(point),
            color = settings.colorArgb,
            width = settings.strokeWidth,
            tool = settings.tool.name.lowercase(),
            alpha = settings.opacity,
        )
    }

    fun continueStroke(point: DrawingPoint) {
        val s = currentStroke ?: return
        currentStroke = s.copy(points = s.points + point)
    }

    fun endStroke() {
        val s = currentStroke ?: return
        if (s.points.isNotEmpty()) {
            if (s.tool == "eraser") {
                applyEraser(s)
            } else {
                strokes.add(s)
            }
        }
        currentStroke = null
    }

    fun cancelStroke() {
        currentStroke = null
    }

    // ─── Eraser logic ─────────────────────────────────────────────────────────

    private fun applyEraser(eraserStroke: Stroke) {
        val eraserPath = eraserStroke.points
        val toRemove = mutableListOf<Stroke>()
        strokes.forEach { stroke ->
            if (strokeIntersectsEraser(stroke, eraserPath, eraserStroke.width)) {
                toRemove.add(stroke)
            }
        }
        toRemove.forEach { strokes.remove(it) }
    }

    private fun strokeIntersectsEraser(
        stroke: Stroke,
        eraserPoints: List<DrawingPoint>,
        eraserWidth: Float,
    ): Boolean {
        val radius = eraserWidth * 2f
        for (ep in eraserPoints) {
            for (sp in stroke.points) {
                val dx = ep.x - sp.x
                val dy = ep.y - sp.y
                if (dx * dx + dy * dy < radius * radius) return true
            }
        }
        return false
    }

    // ─── Undo / Redo ─────────────────────────────────────────────────────────

    fun undo() {
        if (strokes.isNotEmpty()) {
            val last = strokes.removeAt(strokes.lastIndex)
            undoStack.add(last)
        }
    }

    fun redo() {
        if (undoStack.isNotEmpty()) {
            val next = undoStack.removeAt(undoStack.lastIndex)
            strokes.add(next)
        }
    }

    val canUndo get() = strokes.isNotEmpty()
    val canRedo get() = undoStack.isNotEmpty()

    // ─── Load / Clear ─────────────────────────────────────────────────────────

    fun loadStrokes(loaded: List<Stroke>) {
        strokes.clear()
        strokes.addAll(loaded)
        undoStack.clear()
    }

    fun clear() {
        val all = strokes.toList()
        strokes.clear()
        undoStack.clear()
        undoStack.addAll(all) // allow undo of clear
    }

    // ─── Serialization ───────────────────────────────────────────────────────

    fun serialize(): String = Json.encodeToString(strokes.toList())
}
