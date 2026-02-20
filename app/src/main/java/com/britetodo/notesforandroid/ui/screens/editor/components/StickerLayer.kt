package com.britetodo.notesforandroid.ui.screens.editor.components

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.britetodo.notesforandroid.data.models.StickerItem

@Composable
fun StickerLayer(
    stickers: List<StickerItem>,
    selectedId: String?,
    canvasSize: IntSize,
    scale: Float,
    onMove: (String, Float, Float) -> Unit,
    onDelete: (String) -> Unit,
    onSelect: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        stickers.sortedBy { it.zIndex }.forEach { sticker ->
            StickerView(
                sticker = sticker,
                isSelected = sticker.id == selectedId,
                canvasSize = canvasSize,
                onMove = { x, y -> onMove(sticker.id, x, y) },
                onDelete = { onDelete(sticker.id) },
                onSelect = { onSelect(sticker.id) },
            )
        }
    }
}

@Composable
private fun StickerView(
    sticker: StickerItem,
    isSelected: Boolean,
    canvasSize: IntSize,
    onMove: (Float, Float) -> Unit,
    onDelete: () -> Unit,
    onSelect: () -> Unit,
) {
    if (canvasSize.width == 0 || canvasSize.height == 0) return

    val x = sticker.xNorm * canvasSize.width
    val y = sticker.yNorm * canvasSize.height
    val w = sticker.widthNorm * canvasSize.width
    val h = sticker.heightNorm * canvasSize.height
    val density = LocalDensity.current
    val context = LocalContext.current

    var dragOffsetX by remember { mutableFloatStateOf(0f) }
    var dragOffsetY by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = Modifier
            .offset(
                x = with(density) { (x + dragOffsetX).toDp() },
                y = with(density) { (y + dragOffsetY).toDp() },
            )
            .size(
                width = with(density) { w.toDp() },
                height = with(density) { h.toDp() },
            )
            .graphicsLayer {
                rotationZ = sticker.rotation
                alpha = sticker.opacity
            }
            .then(
                if (isSelected) Modifier.border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(4.dp),
                ) else Modifier
            )
            .pointerInput(sticker.id) {
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
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data("file:///android_asset/${sticker.assetId}")
                .crossfade(true)
                .build(),
            contentDescription = "Sticker",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize(),
        )

        if (isSelected && !sticker.isLocked) {
            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(24.dp),
            ) {
                Icon(
                    Icons.Default.Close,
                    "Remove sticker",
                    modifier = Modifier.size(16.dp),
                    tint = Color.Red,
                )
            }
        }
    }
}
