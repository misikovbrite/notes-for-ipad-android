package com.britetodo.notesforandroid.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.britetodo.notesforandroid.data.models.Notebook

@Entity(tableName = "notebooks")
data class NotebookEntity(
    @PrimaryKey val id: String,
    val name: String,
    val colorHex: String,
    val spineColorHex: String,
    val iconName: String,
    val coverId: String?,
    val templateId: String,
    val templateColorThemeId: String,
    val isFavorite: Boolean,
    val isArchived: Boolean,
    val pageCount: Int,
    val lastPageId: String?,
    val createdAt: Long,
    val updatedAt: Long,
)

fun NotebookEntity.toModel() = Notebook(
    id = id,
    name = name,
    colorHex = colorHex,
    spineColorHex = spineColorHex,
    iconName = iconName,
    coverId = coverId,
    templateId = templateId,
    templateColorThemeId = templateColorThemeId,
    isFavorite = isFavorite,
    isArchived = isArchived,
    pageCount = pageCount,
    lastPageId = lastPageId,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun Notebook.toEntity() = NotebookEntity(
    id = id,
    name = name,
    colorHex = colorHex,
    spineColorHex = spineColorHex,
    iconName = iconName,
    coverId = coverId,
    templateId = templateId,
    templateColorThemeId = templateColorThemeId,
    isFavorite = isFavorite,
    isArchived = isArchived,
    pageCount = pageCount,
    lastPageId = lastPageId,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
