package com.britetodo.notesforandroid.templates

/**
 * Registry of all 60+ templates, mirroring iOS TemplateRegistry.swift.
 * Organized by category: Basic, Planners, Health, Work, School, Creative.
 */
object TemplateRegistry {

    // ─── Basic ────────────────────────────────────────────────────────────────
    private val basicTemplates = listOf(
        Template("blank", TemplateType.BLANK, "Blank", "Basic"),
        Template("lined", TemplateType.LINED, "Lined", "Basic"),
        Template("wide_lined", TemplateType.WIDE_LINED, "Wide Ruled", "Basic"),
        Template("narrow_lined", TemplateType.NARROW_LINED, "Narrow Ruled", "Basic"),
        Template("grid", TemplateType.GRID, "Grid", "Basic"),
        Template("dotted", TemplateType.DOTTED, "Dotted", "Basic"),
        Template("dotted_grid", TemplateType.DOTTED_GRID, "Dot Grid", "Basic"),
        Template("isometric", TemplateType.ISOMETRIC, "Isometric", "Basic"),
        Template("hexagonal", TemplateType.HEXAGONAL, "Hexagonal", "Basic"),
    )

    // ─── Planners ─────────────────────────────────────────────────────────────
    private val plannerTemplates = listOf(
        Template("daily_planner", TemplateType.DAILY_PLANNER, "Daily Planner", "Planners"),
        Template("daily_schedule", TemplateType.DAILY_SCHEDULE, "Daily Schedule", "Planners"),
        Template("daily_reflection", TemplateType.DAILY_REFLECTION, "Daily Reflection", "Planners"),
        Template("weekly_planner", TemplateType.WEEKLY_PLANNER, "Weekly Planner", "Planners"),
        Template("weekly_schedule", TemplateType.WEEKLY_SCHEDULE, "Weekly Schedule", "Planners"),
        Template("weekly_review", TemplateType.WEEKLY_REVIEW, "Weekly Review", "Planners"),
        Template("monthly_planner", TemplateType.MONTHLY_PLANNER, "Monthly Planner", "Planners"),
        Template("monthly_calendar", TemplateType.MONTHLY_CALENDAR, "Monthly Calendar", "Planners"),
        Template("yearly_overview", TemplateType.YEARLY_OVERVIEW, "Yearly Overview", "Planners"),
        Template("bullet_journal", TemplateType.BULLET_JOURNAL, "Bullet Journal", "Planners"),
        Template("brain_dump", TemplateType.BRAIN_DUMP, "Brain Dump", "Planners"),
        Template("project_planner", TemplateType.PROJECT_PLANNER, "Project Planner", "Planners"),
        Template("goal_tracker", TemplateType.GOAL_TRACKER, "Goal Tracker", "Planners"),
        Template("kanban", TemplateType.KANBAN, "Kanban Board", "Planners"),
        Template("checklist", TemplateType.CHECKLIST, "Checklist", "Planners"),
    )

    // ─── Health & Wellness ────────────────────────────────────────────────────
    private val healthTemplates = listOf(
        Template("habit_tracker", TemplateType.HABIT_TRACKER, "Habit Tracker", "Health"),
        Template("habit_tracker_mini", TemplateType.HABIT_TRACKER_MINI, "Mini Habit Tracker", "Health"),
        Template("mood_tracker", TemplateType.MOOD_TRACKER, "Mood Tracker", "Health"),
        Template("sleep_tracker", TemplateType.SLEEP_TRACKER, "Sleep Tracker", "Health"),
        Template("water_tracker", TemplateType.WATER_TRACKER, "Water Tracker", "Health"),
        Template("meal_planner", TemplateType.MEAL_PLANNER, "Meal Planner", "Health"),
        Template("meal_log", TemplateType.MEAL_LOG, "Meal Log", "Health"),
        Template("grocery_list", TemplateType.GROCERY_LIST, "Grocery List", "Health"),
        Template("fitness_log", TemplateType.FITNESS_LOG, "Fitness Log", "Health"),
        Template("workout_plan", TemplateType.WORKOUT_PLAN, "Workout Plan", "Health"),
        Template("running_log", TemplateType.RUNNING_LOG, "Running Log", "Health"),
        Template("gratitude_journal", TemplateType.GRATITUDE_JOURNAL, "Gratitude Journal", "Health"),
    )

    // ─── Work & Productivity ──────────────────────────────────────────────────
    private val workTemplates = listOf(
        Template("meeting_notes", TemplateType.MEETING_NOTES, "Meeting Notes", "Work"),
        Template("budget_planner", TemplateType.BUDGET_PLANNER, "Budget Planner", "Work"),
        Template("expense_tracker", TemplateType.EXPENSE_TRACKER, "Expense Tracker", "Work"),
        Template("shopping_list", TemplateType.SHOPPING_LIST, "Shopping List", "Work"),
        Template("packing_list", TemplateType.PACKING_LIST, "Packing List", "Work"),
        Template("bucket_list", TemplateType.BUCKET_LIST, "Bucket List", "Work"),
        Template("vision_board", TemplateType.VISION_BOARD, "Vision Board", "Work"),
    )

    // ─── School & Study ───────────────────────────────────────────────────────
    private val studyTemplates = listOf(
        Template("lecture_notes", TemplateType.LECTURE_NOTES, "Lecture Notes", "Study"),
        Template("cornell_notes", TemplateType.CORNELL_NOTES, "Cornell Notes", "Study"),
        Template("study_schedule", TemplateType.STUDY_SCHEDULE, "Study Schedule", "Study"),
        Template("exam_prep", TemplateType.EXAM_PREP, "Exam Prep", "Study"),
        Template("vocabulary_list", TemplateType.VOCABULARY_LIST, "Vocabulary List", "Study"),
        Template("mind_map", TemplateType.MIND_MAP, "Mind Map", "Study"),
        Template("book_log", TemplateType.BOOK_LOG, "Book Log", "Study"),
        Template("reading_list", TemplateType.READING_LIST, "Reading List", "Study"),
    )

    // ─── Creative ─────────────────────────────────────────────────────────────
    private val creativeTemplates = listOf(
        Template("journal", TemplateType.JOURNAL, "Journal", "Creative"),
        Template("diary", TemplateType.DIARY, "Diary", "Creative"),
        Template("travel_journal", TemplateType.TRAVEL_JOURNAL, "Travel Journal", "Creative"),
        Template("recipe_card", TemplateType.RECIPE_CARD, "Recipe Card", "Creative"),
        Template("storyboard", TemplateType.STORYBOARD, "Storyboard", "Creative"),
        Template("music_sheet", TemplateType.MUSIC_SHEET, "Music Sheet", "Creative"),
        Template("sketch", TemplateType.SKETCH, "Sketch", "Creative"),
        Template("watercolor", TemplateType.WATERCOLOR, "Watercolor", "Creative"),
        Template("manga", TemplateType.MANGA, "Manga", "Creative"),
        Template("comic_panel", TemplateType.COMIC_PANEL, "Comic Panel", "Creative"),
        Template("calligraphy", TemplateType.CALLIGRAPHY, "Calligraphy", "Creative"),
        Template("hand_lettering", TemplateType.HAND_LETTERING, "Hand Lettering", "Creative"),
    )

    val all: List<Template> = basicTemplates +
            plannerTemplates +
            healthTemplates +
            workTemplates +
            studyTemplates +
            creativeTemplates

    val categories: List<TemplateCategory> = listOf(
        TemplateCategory("basic", "Basic", basicTemplates),
        TemplateCategory("planners", "Planners", plannerTemplates),
        TemplateCategory("health", "Health & Wellness", healthTemplates),
        TemplateCategory("work", "Work & Productivity", workTemplates),
        TemplateCategory("study", "School & Study", studyTemplates),
        TemplateCategory("creative", "Creative", creativeTemplates),
    )

    fun byId(id: String): Template = all.firstOrNull { it.id == id }
        ?: Template("blank", TemplateType.BLANK, "Blank", "Basic")
}
