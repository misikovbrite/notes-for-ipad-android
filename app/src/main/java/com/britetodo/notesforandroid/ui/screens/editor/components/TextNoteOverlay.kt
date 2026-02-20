package com.britetodo.notesforandroid.ui.screens.editor.components

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.britetodo.notesforandroid.data.models.TextNote

/**
 * Renders floating text boxes over the canvas.
 * Each box can be dragged (finger) and edited (tap).
 */
@Composable
fun TextNoteOverlay(
    notes: List<TextNote>,
    selectedNoteId: String?,
    canvasSize: IntSize,
    onUpdate: (String, String) -> Unit,
    onMove: (String, Float, Float) -> Unit,
    onDelete: (String) -> Unit,
    onSelect: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        notes.forEach { note ->
            TextNoteBox(
                note = note,
                isSelected = note.id == selectedNoteId,
                canvasSize = canvasSize,
                onUpdate = { onUpdate(note.id, it) },
                onMove = { x, y -> onMove(note.id, x, y) },
                onDelete = { onDelete(note.id) },
                onSelect = { onSelect(note.id) },
            )
        }
    }
}

@Composable
private fun TextNoteBox(
    note: TextNote,
    isSelected: Boolean,
    canvasSize: IntSize,
    onUpdate: (String) -> Unit,
    onMove: (Float, Float) -> Unit,
    onDelete: () -> Unit,
    onSelect: () -> Unit,
) {
    if (canvasSize.width == 0 || canvasSize.height == 0) return

    val x = note.xNorm * canvasSize.width
    val y = note.yNorm * canvasSize.height
    val w = note.widthNorm * canvasSize.width
    val density = LocalDensity.current

    var dragOffsetX by remember { mutableFloatStateOf(0f) }
    var dragOffsetY by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = Modifier
            .offset(
                x = with(density) { (x + dragOffsetX).toDp() },
                y = with(density) { (y + dragOffsetY).toDp() },
            )
            .width(with(density) { w.toDp() })
            .then(
                if (isSelected) Modifier.border(
                    1.dp,
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(4.dp),
                ) else Modifier
            )
            .pointerInput(note.id) {
                detectDragGestures(
                    onDragStart = { onSelect() },
                    onDrag = { _, delta ->
                        dragOffsetX += delta.x
                        dragOffsetY += delta.y
                    },
                    onDragEnd = {
                        val newXNorm = ((x + dragOffsetX) / canvasSize.width).coerceIn(0f, 1f)
                        val newYNorm = ((y + dragOffsetY) / canvasSize.height).coerceIn(0f, 1f)
                        onMove(newXNorm, newYNorm)
                        dragOffsetX = 0f
                        dragOffsetY = 0f
                    },
                )
            }
    ) {
        Surface(
            color = Color.White.copy(alpha = 0.9f),
            shape = RoundedCornerShape(4.dp),
            shadowElevation = if (isSelected) 4.dp else 0.dp,
        ) {
            Column {
                // Delete button when selected
                if (isSelected) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        IconButton(
                            onClick = onDelete,
                            modifier = Modifier.size(24.dp),
                        ) {
                            Icon(
                                Icons.Default.Close,
                                "Delete",
                                modifier = Modifier.size(16.dp),
                                tint = Color.Red,
                            )
                        }
                    }
                }

                BasicTextField(
                    value = note.text,
                    onValueChange = onUpdate,
                    textStyle = TextStyle(
                        fontSize = note.fontSize.sp,
                        color = Color(note.colorArgb.toInt()),
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .defaultMinSize(minWidth = 100.dp, minHeight = 40.dp),
                    decorationBox = { inner ->
                        if (note.text.isEmpty()) {
                            Text("Type here...", color = Color.LightGray, fontSize = note.fontSize.sp)
                        }
                        inner()
                    },
                )
            }
        }
    }
}
