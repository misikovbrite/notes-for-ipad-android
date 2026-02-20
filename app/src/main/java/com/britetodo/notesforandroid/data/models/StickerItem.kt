package com.britetodo.notesforandroid.data.models

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class StickerItem(
    val id: String = UUID.randomUUID().toString(),
    val assetId: String,            // e.g. "stickers/food/pizza.png"
    // Normalized 0-1 coordinates in page space
    val xNorm: Float = 0.4f,
    val yNorm: Float = 0.4f,
    val widthNorm: Float = 0.15f,
    val heightNorm: Float = 0.15f,
    val rotation: Float = 0f,
    val opacity: Float = 1f,
    val zIndex: Int = 0,
    val behindInk: Boolean = false,
    val isLocked: Boolean = false,
)

// Sticker categories (matching iOS Assets.xcassets structure)
data class StickerCategory(
    val id: String,
    val name: String,
    val assets: List<String>,
)
