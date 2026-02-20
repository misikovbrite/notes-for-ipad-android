package com.britetodo.notesforandroid.ui.screens.editor

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter
import com.britetodo.notesforandroid.data.models.DrawingPoint
import com.britetodo.notesforandroid.data.models.Stroke as StrokeModel
import com.britetodo.notesforandroid.drawing.DrawingMode
import com.britetodo.notesforandroid.drawing.DrawingTool
import com.britetodo.notesforandroid.drawing.ToolSettings

/**
 * Core drawing surface. Handles:
 * - MotionEvent → DrawingPoint pipeline
 * - Historical points for smooth curves
 * - Stylus pressure support
 * - CubicBezier smoothing for rendered strokes
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DrawingCanvas(
    strokes: List<StrokeModel>,
    currentStroke: StrokeModel?,
    toolSettings: ToolSettings,
    canvasScale: Float = 1f,
    canvasOffset: Offset = Offset.Zero,
    onStrokeStart: (DrawingPoint) -> Unit,
    onStrokeProgress: (DrawingPoint) -> Unit,
    onStrokeEnd: () -> Unit,
    onTap: (Offset) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Canvas(
        modifier = modifier
            .pointerInteropFilter { event ->
                val toolType = event.getToolType(0)
                val acceptStylus = toolType == MotionEvent.TOOL_TYPE_STYLUS
                val acceptFinger = toolType == MotionEvent.TOOL_TYPE_FINGER

                val shouldDraw = when (toolSettings.mode) {
                    DrawingMode.STYLUS_AND_FINGER -> acceptStylus || acceptFinger
                    DrawingMode.STYLUS_ONLY -> acceptStylus
                    DrawingMode.FINGER_ONLY -> acceptFinger
                }

                // Text/Sticker tools: handle taps regardless of draw mode
                val isTapTool = toolSettings.tool == DrawingTool.TEXT ||
                        toolSettings.tool == DrawingTool.STICKER

                if (isTapTool) {
                    if (event.actionMasked == MotionEvent.ACTION_UP) {
                        onTap(Offset(event.x, event.y))
                    }
                    return@pointerInteropFilter true
                }

                if (!shouldDraw) return@pointerInteropFilter false

                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        onStrokeStart(event.toDrawingPoint())
                        true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        // Consume historical points for smoother curves
                        for (i in 0 until event.historySize) {
                            onStrokeProgress(event.toHistoricalDrawingPoint(i))
                        }
                        onStrokeProgress(event.toDrawingPoint())
                        true
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        onStrokeEnd()
                        true
                    }
                    else -> false
                }
            }
    ) {
        // Render committed strokes
        strokes.forEach { stroke -> drawStroke(stroke, canvasScale) }
        // Render the in-progress stroke
        currentStroke?.let { drawStroke(it, canvasScale) }
    }
}

// ─── Drawing helpers ──────────────────────────────────────────────────────────

private fun DrawScope.drawStroke(stroke: StrokeModel, scale: Float) {
    if (stroke.points.isEmpty()) return

    val strokeColor = Color(stroke.color).copy(alpha = stroke.alpha)
    val strokeWidth = stroke.width * scale

    val isEraser = stroke.tool == "eraser"
    val isMarker = stroke.tool == "marker"

    val blendMode = if (isMarker)
        androidx.compose.ui.graphics.BlendMode.Multiply
    else
        androidx.compose.ui.graphics.BlendMode.SrcOver

    val color = if (isEraser) Color.Transparent else strokeColor
    val drawWidth = if (isEraser) strokeWidth * 3f else strokeWidth

    val path = buildSmoothPath(stroke.points, scale)

    drawPath(
        path = path,
        color = if (isEraser) Color.White else color,
        style = Stroke(
            width = drawWidth,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round,
        ),
        blendMode = if (isEraser) androidx.compose.ui.graphics.BlendMode.Clear else blendMode,
    )
}

/**
 * Build a cubic bezier path through the given points for smooth curves.
 * Mirrors PencilKit's automatic smoothing on iOS.
 */
private fun buildSmoothPath(points: List<DrawingPoint>, scale: Float): Path {
    val path = Path()
    if (points.isEmpty()) return path
    if (points.size == 1) {
        // Single point — draw a tiny circle
        path.moveTo(points[0].x * scale, points[0].y * scale)
        path.lineTo(points[0].x * scale + 0.1f, points[0].y * scale)
        return path
    }

    path.moveTo(points[0].x * scale, points[0].y * scale)

    if (points.size == 2) {
        path.lineTo(points[1].x * scale, points[1].y * scale)
        return path
    }

    // Catmull-Rom to cubic bezier conversion
    for (i in 1 until points.size - 1) {
        val p0 = points[i - 1]
        val p1 = points[i]
        val p2 = points[i + 1]

        val cp1x = (p0.x + p1.x) / 2f * scale
        val cp1y = (p0.y + p1.y) / 2f * scale
        val cp2x = (p1.x + p2.x) / 2f * scale
        val cp2y = (p1.y + p2.y) / 2f * scale

        path.cubicTo(
            cp1x, cp1y,
            cp2x, cp2y,
            (cp1x + cp2x) / 2f, (cp1y + cp2y) / 2f
        )
    }
    // Last point
    val last = points.last()
    path.lineTo(last.x * scale, last.y * scale)
    return path
}

// ─── MotionEvent extensions ───────────────────────────────────────────────────

private fun MotionEvent.toDrawingPoint() = DrawingPoint(
    x = x,
    y = y,
    pressure = pressure.coerceIn(0f, 1f),
    timestamp = eventTime,
)

private fun MotionEvent.toHistoricalDrawingPoint(index: Int) = DrawingPoint(
    x = getHistoricalX(index),
    y = getHistoricalY(index),
    pressure = getHistoricalPressure(index).coerceIn(0f, 1f),
    timestamp = getHistoricalEventTime(index),
)
