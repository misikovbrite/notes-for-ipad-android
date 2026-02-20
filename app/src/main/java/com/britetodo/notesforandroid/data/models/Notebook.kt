package com.britetodo.notesforandroid.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Notebook(
    val id: String,
    val name: String,
    val colorHex: String = "#4A90E2",
    val spineColorHex: String = "#357ABD",
    val iconName: String = "book",
    val coverId: String? = null,
    val templateId: String = "blank",
    val templateColorThemeId: String = "classic",
    val isFavorite: Boolean = false,
    val isArchived: Boolean = false,
    val pageCount: Int = 0,
    val lastPageId: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
)

// Available notebook cover colors (matching iOS palette)
val notebookColors = listOf(
    "#4A90E2", // Blue
    "#50C878", // Emerald
    "#FF6B6B", // Coral
    "#FF9F43", // Orange
    "#A29BFE", // Lavender
    "#00B894", // Teal
    "#FD79A8", // Pink
    "#636E72", // Gray
    "#FDCB6E", // Yellow
    "#6C5CE7", // Purple
    "#E17055", // Salmon
    "#00CEC9", // Cyan
    "#2D3436", // Dark
    "#B2BEC3", // Light gray
    "#D63031", // Red
    "#0984E3", // Dark blue
)

// Available notebook icons (Material equivalents of SF Symbols)
val notebookIcons = listOf(
    "book", "calendar", "star", "heart", "lightbulb",
    "camera", "music", "fitness_center", "local_dining", "home",
    "work", "school", "flight", "directions_car", "attach_money",
    "favorite", "lock", "flag", "emoji_nature", "pets",
    "sport", "brush", "code", "eco",
)
