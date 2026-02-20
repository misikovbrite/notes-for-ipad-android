package com.britetodo.notesforandroid.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.britetodo.notesforandroid.data.models.Page

@Entity(
    tableName = "pages",
    foreignKeys = [
        ForeignKey(
            entity = NotebookEntity::class,
            parentColumns = ["id"],
            childColumns = ["notebookId"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [Index("notebookId")],
)
data class PageEntity(
    @PrimaryKey val id: String,
    val notebookId: String,
    val templateId: String,
    val order: Int,
    val title: String?,
    val date: Long?,
    val drawingFilePath: String?,
    val textNotesJson: String?,
    val stickersJson: String?,
    val thumbnailPath: String?,
    val hasContent: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
)

fun PageEntity.toModel() = Page(
    id = id,
    notebookId = notebookId,
    templateId = templateId,
    order = order,
    title = title,
    date = date,
    drawingFilePath = drawingFilePath,
    textNotesJson = textNotesJson,
    stickersJson = stickersJson,
    thumbnailPath = thumbnailPath,
    hasContent = hasContent,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun Page.toEntity() = PageEntity(
    id = id,
    notebookId = notebookId,
    templateId = templateId,
    order = order,
    title = title,
    date = date,
    drawingFilePath = drawingFilePath,
    textNotesJson = textNotesJson,
    stickersJson = stickersJson,
    thumbnailPath = thumbnailPath,
    hasContent = hasContent,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
