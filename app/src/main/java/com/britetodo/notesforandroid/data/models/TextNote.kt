package com.britetodo.notesforandroid.data.models

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class TextNote(
    val id: String = UUID.randomUUID().toString(),
    val text: String = "",
    // Normalized 0-1 position in page space
    val xNorm: Float = 0.1f,
    val yNorm: Float = 0.1f,
    val widthNorm: Float = 0.5f,
    val heightNorm: Float = 0.2f,
    val fontName: String = "default",
    val fontSize: Float = 24f,
    val colorArgb: Long = 0xFF000000,
    val isBold: Boolean = false,
    val isItalic: Boolean = false,
    val isUnderlined: Boolean = false,
    val textAlignment: Int = 0,  // 0=left, 1=center, 2=right
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
)
