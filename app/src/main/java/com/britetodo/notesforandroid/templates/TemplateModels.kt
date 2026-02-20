package com.britetodo.notesforandroid.templates

import androidx.compose.ui.graphics.Color

// iOS-equivalent TemplatePageSize.standard = 768x1024 (3:4 ratio)
object TemplatePageSize {
    const val WIDTH = 768f
    const val HEIGHT = 1024f
    const val MARGIN_TOP = 40f
    const val MARGIN_LEFT = 20f
    const val MARGIN_BOTTOM = 40f
    const val MARGIN_RIGHT = 20f
}

enum class TemplateType {
    BLANK,
    LINED,
    WIDE_LINED,
    NARROW_LINED,
    GRID,
    DOTTED,
    DOTTED_GRID,
    ISOMETRIC,
    HEXAGONAL,
    DAILY_PLANNER,
    DAILY_SCHEDULE,
    DAILY_REFLECTION,
    WEEKLY_PLANNER,
    WEEKLY_SCHEDULE,
    WEEKLY_REVIEW,
    MONTHLY_PLANNER,
    MONTHLY_CALENDAR,
    YEARLY_OVERVIEW,
    HABIT_TRACKER,
    HABIT_TRACKER_MINI,
    MOOD_TRACKER,
    SLEEP_TRACKER,
    WATER_TRACKER,
    MEAL_PLANNER,
    MEAL_LOG,
    GROCERY_LIST,
    FITNESS_LOG,
    WORKOUT_PLAN,
    RUNNING_LOG,
    BULLET_JOURNAL,
    BRAIN_DUMP,
    MEETING_NOTES,
    LECTURE_NOTES,
    CORNELL_NOTES,
    PROJECT_PLANNER,
    GOAL_TRACKER,
    BOOK_LOG,
    READING_LIST,
    TRAVEL_JOURNAL,
    BUDGET_PLANNER,
    EXPENSE_TRACKER,
    GRATITUDE_JOURNAL,
    JOURNAL,
    DIARY,
    RECIPE_CARD,
    STUDY_SCHEDULE,
    EXAM_PREP,
    VOCABULARY_LIST,
    MIND_MAP,
    KANBAN,
    CHECKLIST,
    SHOPPING_LIST,
    PACKING_LIST,
    BUCKET_LIST,
    VISION_BOARD,
    STORYBOARD,
    MUSIC_SHEET,
    SKETCH,
    WATERCOLOR,
    MANGA,
    COMIC_PANEL,
    CALLIGRAPHY,
    HAND_LETTERING,
}

data class Template(
    val id: String,
    val type: TemplateType,
    val name: String,
    val category: String,
    val isPremium: Boolean = false,
    val colorThemeId: String = "classic",
    val iconResName: String = "ic_template_blank",
    val description: String = "",
)

data class TemplateColorTheme(
    val id: String,
    val name: String,
    val backgroundColor: Color,
    val headerBgColor: Color,
    val lineColor: Color,
    val accentColor: Color,
    val textColor: Color,
) {
    companion object {
        val classic = TemplateColorTheme(
            id = "classic",
            name = "Classic",
            backgroundColor = Color.White,
            headerBgColor = Color(0xFFF5F5F5),
            lineColor = Color(0xFFCCCCCC),
            accentColor = Color(0xFF4A90E2),
            textColor = Color(0xFF333333),
        )
        val ocean = TemplateColorTheme(
            id = "ocean",
            name = "Ocean",
            backgroundColor = Color(0xFFF0F7FF),
            headerBgColor = Color(0xFF2196F3),
            lineColor = Color(0xFFB3D4F7),
            accentColor = Color(0xFF1565C0),
            textColor = Color(0xFF0D47A1),
        )
        val forest = TemplateColorTheme(
            id = "forest",
            name = "Forest",
            backgroundColor = Color(0xFFF1F8E9),
            headerBgColor = Color(0xFF4CAF50),
            lineColor = Color(0xFFA5D6A7),
            accentColor = Color(0xFF2E7D32),
            textColor = Color(0xFF1B5E20),
        )
        val sunset = TemplateColorTheme(
            id = "sunset",
            name = "Sunset",
            backgroundColor = Color(0xFFFFF3E0),
            headerBgColor = Color(0xFFFF9800),
            lineColor = Color(0xFFFFCC80),
            accentColor = Color(0xFFE65100),
            textColor = Color(0xFFBF360C),
        )
        val lavender = TemplateColorTheme(
            id = "lavender",
            name = "Lavender",
            backgroundColor = Color(0xFFF3E5F5),
            headerBgColor = Color(0xFF9C27B0),
            lineColor = Color(0xFFCE93D8),
            accentColor = Color(0xFF6A1B9A),
            textColor = Color(0xFF4A148C),
        )
        val rose = TemplateColorTheme(
            id = "rose",
            name = "Rose",
            backgroundColor = Color(0xFFFCE4EC),
            headerBgColor = Color(0xFFE91E63),
            lineColor = Color(0xFFF48FB1),
            accentColor = Color(0xFFAD1457),
            textColor = Color(0xFF880E4F),
        )
        val dark = TemplateColorTheme(
            id = "dark",
            name = "Dark",
            backgroundColor = Color(0xFF1A1A2E),
            headerBgColor = Color(0xFF16213E),
            lineColor = Color(0xFF2D2D44),
            accentColor = Color(0xFF4A90E2),
            textColor = Color(0xFFE0E0E0),
        )

        val all = listOf(classic, ocean, forest, sunset, lavender, rose, dark)
        fun byId(id: String) = all.firstOrNull { it.id == id } ?: classic
    }
}

data class TemplateCategory(
    val id: String,
    val name: String,
    val templates: List<Template>,
)
