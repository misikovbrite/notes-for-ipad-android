package com.britetodo.notesforandroid.drawing

import com.britetodo.notesforandroid.data.models.Stroke
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object StrokeSerializer {
    private val json = Json { ignoreUnknownKeys = true }

    fun encode(strokes: List<Stroke>): String = json.encodeToString(strokes)

    fun decode(jsonString: String): List<Stroke> = try {
        json.decodeFromString(jsonString)
    } catch (e: Exception) {
        emptyList()
    }
}
