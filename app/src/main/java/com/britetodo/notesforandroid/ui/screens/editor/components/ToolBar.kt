package com.britetodo.notesforandroid.ui.screens.editor.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.britetodo.notesforandroid.drawing.DrawingTool
import com.britetodo.notesforandroid.drawing.ToolSettings

/**
 * Floating vertical toolbar on the right side of the editor.
 * Contains: Pen, Marker, Eraser, Lasso, Text, Sticker, Color picker, Width slider.
 */
@Composable
fun ToolBar(
    toolSettings: ToolSettings,
    onSetTool: (DrawingTool) -> Unit,
    onSetColor: (Long) -> Unit,
    onSetWidth: (Float) -> Unit,
    onSetOpacity: (Float) -> Unit,
    onStickerTap: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showWidthPicker by remember { mutableStateOf(false) }
    var showColorPicker by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        shadowElevation = 8.dp,
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            // Pen
            ToolButton(
                icon = Icons.Default.Edit,
                label = "Pen",
                selected = toolSettings.tool == DrawingTool.PEN,
                onClick = { onSetTool(DrawingTool.PEN) },
            )

            // Marker (semi-transparent)
            ToolButton(
                icon = Icons.Default.Brush,
                label = "Marker",
                selected = toolSettings.tool == DrawingTool.MARKER,
                onClick = { onSetTool(DrawingTool.MARKER) },
            )

            // Eraser
            ToolButton(
                icon = Icons.Default.AutoFixOff,
                label = "Eraser",
                selected = toolSettings.tool == DrawingTool.ERASER,
                onClick = { onSetTool(DrawingTool.ERASER) },
            )

            // Lasso
            ToolButton(
                icon = Icons.Default.SelectAll,
                label = "Lasso",
                selected = toolSettings.tool == DrawingTool.LASSO,
                onClick = { onSetTool(DrawingTool.LASSO) },
            )

            HorizontalDivider(modifier = Modifier.width(40.dp))

            // Text
            ToolButton(
                icon = Icons.Default.TextFields,
                label = "Text",
                selected = toolSettings.tool == DrawingTool.TEXT,
                onClick = { onSetTool(DrawingTool.TEXT) },
            )

            // Sticker
            ToolButton(
                icon = Icons.Default.EmojiEmotions,
                label = "Sticker",
                selected = false,
                onClick = onStickerTap,
            )

            HorizontalDivider(modifier = Modifier.width(40.dp))

            // Color picker button
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(toolSettings.colorArgb.toInt()))
                    .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
                    .clickable { showColorPicker = !showColorPicker },
            )

            // Stroke width indicator
            IconButton(
                onClick = { showWidthPicker = !showWidthPicker },
                modifier = Modifier.size(36.dp),
            ) {
                Icon(
                    Icons.Default.LineWeight,
                    "Stroke width",
                    modifier = Modifier.size(20.dp),
                )
            }
        }
    }

    // Width picker popout
    if (showWidthPicker) {
        StrokeWidthPicker(
            current = toolSettings.strokeWidth,
            onSelect = { onSetWidth(it); showWidthPicker = false },
            onDismiss = { showWidthPicker = false },
        )
    }

    // Color picker popout
    if (showColorPicker) {
        ColorPickerDialog(
            currentColor = Color(toolSettings.colorArgb.toInt()),
            onSelect = { color ->
                onSetColor(color.value.toLong())
                showColorPicker = false
            },
            onDismiss = { showColorPicker = false },
        )
    }
}

@Composable
private fun ToolButton(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val bgColor = if (selected) MaterialTheme.colorScheme.primaryContainer
    else Color.Transparent

    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (selected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(22.dp),
        )
    }
}

@Composable
private fun StrokeWidthPicker(
    current: Float,
    onSelect: (Float) -> Unit,
    onDismiss: () -> Unit,
) {
    val widths = listOf(2f, 4f, 6f, 10f, 16f, 24f)
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Stroke Width") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                widths.forEach { w ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelect(w) }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .width(80.dp)
                                .height(w.dp.coerceAtLeast(2.dp))
                                .background(MaterialTheme.colorScheme.onSurface),
                        )
                        Text("${w.toInt()}px")
                        if (w == current) {
                            Icon(Icons.Default.Check, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } },
    )
}

@Composable
private fun ColorPickerDialog(
    currentColor: Color,
    onSelect: (Color) -> Unit,
    onDismiss: () -> Unit,
) {
    val colors = listOf(
        Color.Black, Color.White, Color.Gray,
        Color(0xFFE74C3C), Color(0xFFE67E22), Color(0xFFF1C40F),
        Color(0xFF2ECC71), Color(0xFF3498DB), Color(0xFF9B59B6),
        Color(0xFF1ABC9C), Color(0xFFFF69B4), Color(0xFF795548),
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Pen Color") },
        text = {
            Column {
                colors.chunked(6).forEach { row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        row.forEach { color ->
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .border(
                                        if (color == currentColor) 3.dp else 1.dp,
                                        if (color == currentColor) MaterialTheme.colorScheme.primary else Color.LightGray,
                                        CircleShape,
                                    )
                                    .clickable { onSelect(color) },
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }
        },
        confirmButton = {},
        dismissButton = { TextButton(onClick = onDismiss) { Text("Close") } },
    )
}
