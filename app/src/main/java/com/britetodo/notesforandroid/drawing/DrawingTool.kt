package com.britetodo.notesforandroid.drawing

enum class DrawingTool {
    PEN,
    MARKER,
    ERASER,
    LASSO,
    TEXT,
    STICKER,
}

enum class DrawingMode {
    STYLUS_AND_FINGER,  // Both draw
    STYLUS_ONLY,        // Finger scrolls/zooms
    FINGER_ONLY,        // No stylus needed
}

data class ToolSettings(
    val tool: DrawingTool = DrawingTool.PEN,
    val colorArgb: Long = 0xFF000000,
    val strokeWidth: Float = 4f,
    val opacity: Float = 1f,
    val mode: DrawingMode = DrawingMode.STYLUS_AND_FINGER,
)
