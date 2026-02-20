package com.britetodo.notesforandroid.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Page(
    val id: String,
    val notebookId: String,
    val templateId: String = "blank",
    val order: Int = 0,
    val title: String? = null,
    val date: Long? = null,
    val drawingFilePath: String? = null,
    val textNotesJson: String? = null,
    val stickersJson: String? = null,
    val thumbnailPath: String? = null,
    val hasContent: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
)
