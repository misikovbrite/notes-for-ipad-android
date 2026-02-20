package com.britetodo.notesforandroid.ui.screens.editor

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.britetodo.notesforandroid.drawing.DrawingTool
import com.britetodo.notesforandroid.templates.TemplateRegistry
import com.britetodo.notesforandroid.ui.screens.editor.components.TemplateLayer
import com.britetodo.notesforandroid.ui.screens.editor.components.TextNoteOverlay
import com.britetodo.notesforandroid.ui.screens.editor.components.StickerLayer
import com.britetodo.notesforandroid.ui.screens.editor.components.ToolBar

/**
 * Main editor screen. Layers (bottom to top):
 * 1. Template background (rendered bitmap)
 * 2. Drawing canvas (strokes)
 * 3. Sticker layer (below ink if behindInk=true)
 * 4. Text notes layer
 * 5. Tool bar (floating side panel)
 *
 * All layers wrapped in TransformableBox for pinch-to-zoom + pan.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorScreen(
    notebookId: String,
    pageId: String,
    onBack: () -> Unit,
    viewModel: EditorViewModel = hiltViewModel(),
) {
    LaunchedEffect(notebookId, pageId) { viewModel.init(notebookId, pageId) }

    val page = viewModel.page
    val template = TemplateRegistry.byId(page?.templateId ?: "blank")

    // Canvas transform state
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val transformState = rememberTransformableState { zoomChange, panChange, _ ->
        scale = (scale * zoomChange).coerceIn(0.5f, 5f)
        offset += panChange
    }

    var canvasSize by remember { mutableStateOf(IntSize.Zero) }

    Box(modifier = Modifier.fillMaxSize()) {
        // ─── Zoomable canvas stack ─────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxSize()
                .transformable(transformState)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offset.x
                    translationY = offset.y
                }
                .onGloballyPositioned { canvasSize = it.size }
        ) {
            // 1. Template background
            TemplateLayer(template = template, modifier = Modifier.fillMaxSize())

            // 2. Stickers BEHIND ink
            StickerLayer(
                stickers = viewModel.stickers.filter { it.behindInk },
                selectedId = viewModel.selectedStickerId,
                canvasSize = canvasSize,
                scale = scale,
                onMove = viewModel::moveSticker,
                onDelete = viewModel::deleteSticker,
                onSelect = { viewModel.selectedStickerId = it },
                modifier = Modifier.fillMaxSize(),
            )

            // 3. Drawing canvas
            DrawingCanvas(
                strokes = viewModel.drawingEngine.strokes,
                currentStroke = viewModel.drawingEngine.currentStroke,
                toolSettings = viewModel.toolSettings,
                canvasScale = scale,
                onStrokeStart = viewModel::onStrokeStarted,
                onStrokeProgress = viewModel::onStrokeProgress,
                onStrokeEnd = viewModel::onStrokeEnded,
                onTap = { tapOffset ->
                    when (viewModel.toolSettings.tool) {
                        DrawingTool.TEXT -> {
                            if (canvasSize.width > 0 && canvasSize.height > 0) {
                                viewModel.addTextNote(
                                    xNorm = tapOffset.x / canvasSize.width,
                                    yNorm = tapOffset.y / canvasSize.height,
                                )
                            }
                        }
                        DrawingTool.STICKER -> viewModel.showStickerGallery = true
                        else -> {}
                    }
                },
                modifier = Modifier.fillMaxSize(),
            )

            // 4. Stickers ABOVE ink
            StickerLayer(
                stickers = viewModel.stickers.filter { !it.behindInk },
                selectedId = viewModel.selectedStickerId,
                canvasSize = canvasSize,
                scale = scale,
                onMove = viewModel::moveSticker,
                onDelete = viewModel::deleteSticker,
                onSelect = { viewModel.selectedStickerId = it },
                modifier = Modifier.fillMaxSize(),
            )

            // 5. Text notes
            TextNoteOverlay(
                notes = viewModel.textNotes,
                selectedNoteId = viewModel.selectedTextNoteId,
                canvasSize = canvasSize,
                onUpdate = viewModel::updateTextNote,
                onMove = viewModel::moveTextNote,
                onDelete = viewModel::deleteTextNote,
                onSelect = { viewModel.selectedTextNoteId = it },
                modifier = Modifier.fillMaxSize(),
            )
        }

        // ─── Top bar ──────────────────────────────────────────────────────
        EditorTopBar(
            onBack = {
                viewModel.saveNow()
                onBack()
            },
            onUndo = viewModel::undo,
            onRedo = viewModel::redo,
            canUndo = viewModel.drawingEngine.canUndo,
            canRedo = viewModel.drawingEngine.canRedo,
            onTemplate = { viewModel.showTemplateGallery = true },
            onExport = { /* TODO: PDF export */ },
            modifier = Modifier.align(Alignment.TopCenter),
        )

        // ─── Side tool bar ────────────────────────────────────────────────
        ToolBar(
            toolSettings = viewModel.toolSettings,
            onSetTool = viewModel::setTool,
            onSetColor = viewModel::setColor,
            onSetWidth = viewModel::setStrokeWidth,
            onSetOpacity = viewModel::setOpacity,
            onStickerTap = { viewModel.showStickerGallery = true },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp),
        )
    }

    // Template gallery sheet
    if (viewModel.showTemplateGallery) {
        TemplateGallerySheet(
            currentTemplateId = page?.templateId ?: "blank",
            onSelect = viewModel::changeTemplate,
            onDismiss = { viewModel.showTemplateGallery = false },
        )
    }

    // Sticker gallery sheet
    if (viewModel.showStickerGallery) {
        StickerGallerySheet(
            onSelect = { assetId ->
                viewModel.addSticker(assetId)
                viewModel.showStickerGallery = false
            },
            onDismiss = { viewModel.showStickerGallery = false },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditorTopBar(
    onBack: () -> Unit,
    onUndo: () -> Unit,
    onRedo: () -> Unit,
    canUndo: Boolean,
    canRedo: Boolean,
    onTemplate: () -> Unit,
    onExport: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        shadowElevation = 2.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, "Back")
                }
                IconButton(onClick = onUndo, enabled = canUndo) {
                    Icon(Icons.Default.Undo, "Undo")
                }
                IconButton(onClick = onRedo, enabled = canRedo) {
                    Icon(Icons.Default.Redo, "Redo")
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(onClick = onTemplate) {
                    Icon(Icons.Default.GridView, null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Template")
                }
                IconButton(onClick = onExport) {
                    Icon(Icons.Default.Share, "Export PDF")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TemplateGallerySheet(
    currentTemplateId: String,
    onSelect: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val registry = remember { com.britetodo.notesforandroid.templates.TemplateRegistry }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(Modifier.padding(16.dp)) {
            Text("Choose Template", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))

            registry.categories.forEach { category ->
                Text(
                    category.name,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp),
                )
                category.templates.chunked(4).forEach { row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        row.forEach { template ->
                            val isSelected = template.id == currentTemplateId
                            Surface(
                                onClick = { onSelect(template.id) },
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                                else MaterialTheme.colorScheme.surface,
                                border = if (isSelected) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null,
                                modifier = Modifier.width(80.dp),
                            ) {
                                Column(
                                    Modifier.padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Text("📄", fontSize = 20.sp)
                                    Text(
                                        template.name,
                                        style = MaterialTheme.typography.labelSmall,
                                        maxLines = 2,
                                        fontSize = 10.sp,
                                    )
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }
            Spacer(Modifier.height(32.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StickerGallerySheet(
    onSelect: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    // Sample sticker categories (assets from assets/stickers/ folder)
    val categories = listOf(
        "food" to listOf("pizza", "sushi", "coffee", "cake", "burger"),
        "nature" to listOf("flower", "leaf", "sun", "moon", "star"),
        "emoji" to listOf("heart", "fire", "lightning", "rainbow", "sparkle"),
        "school" to listOf("pencil", "book", "apple", "ruler", "globe"),
        "travel" to listOf("airplane", "map", "camera", "compass", "bag"),
    )

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(Modifier.padding(16.dp)) {
            Text("Stickers", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))

            categories.forEach { (category, items) ->
                Text(
                    category.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items.forEach { item ->
                        val assetId = "stickers/$category/$item.png"
                        Surface(
                            onClick = { onSelect(assetId) },
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.size(60.dp),
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("🎨", fontSize = 28.sp) // Placeholder
                            }
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }
            Spacer(Modifier.height(32.dp))
        }
    }
}
