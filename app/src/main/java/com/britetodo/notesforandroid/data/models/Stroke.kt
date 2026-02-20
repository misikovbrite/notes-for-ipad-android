package com.britetodo.notesforandroid.data.models

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class DrawingPoint(
    val x: Float,
    val y: Float,
    val pressure: Float = 1f,
    val timestamp: Long = System.currentTimeMillis(),
)

@Serializable
data class Stroke(
    val id: String = UUID.randomUUID().toString(),
    val points: List<DrawingPoint>,
    val color: Long = 0xFF000000,   // ARGB packed long
    val width: Float = 4f,
    val tool: String = "pen",       // "pen", "marker", "eraser"
    val alpha: Float = 1f,
)
