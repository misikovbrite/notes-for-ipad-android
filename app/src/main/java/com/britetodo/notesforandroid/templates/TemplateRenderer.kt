package com.britetodo.notesforandroid.templates

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Renders template backgrounds to Bitmap using android.graphics.Canvas.
 * Mirrors iOS CoreGraphics template rendering from TemplateComponents.swift.
 *
 * Template canvas coordinates: 768x1024 (3:4 ratio, matching iOS).
 * Callers scale the bitmap to screen size.
 */
@Singleton
class TemplateRenderer @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    // Simple in-memory cache
    private val cache = mutableMapOf<String, Bitmap>()

    fun renderToBitmap(
        template: Template,
        widthPx: Int = 1536,  // 2x the logical 768
        heightPx: Int = 2048, // 2x the logical 1024
        theme: TemplateColorTheme = TemplateColorTheme.classic,
    ): Bitmap {
        val cacheKey = "${template.id}_${theme.id}_${widthPx}x${heightPx}"
        cache[cacheKey]?.let { return it }

        val bitmap = Bitmap.createBitmap(widthPx, heightPx, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // Fill background
        val bgPaint = Paint().apply { color = theme.backgroundColor.toArgb() }
        canvas.drawRect(0f, 0f, widthPx.toFloat(), heightPx.toFloat(), bgPaint)

        // Scale factor from logical 768x1024 to actual pixel size
        val scaleX = widthPx / TemplatePageSize.WIDTH
        val scaleY = heightPx / TemplatePageSize.HEIGHT

        when (template.type) {
            TemplateType.BLANK -> { /* nothing */ }
            TemplateType.LINED -> renderLined(canvas, widthPx, heightPx, scaleX, scaleY, theme, lineSpacingDp = 40)
            TemplateType.WIDE_LINED -> renderLined(canvas, widthPx, heightPx, scaleX, scaleY, theme, lineSpacingDp = 60)
            TemplateType.NARROW_LINED -> renderLined(canvas, widthPx, heightPx, scaleX, scaleY, theme, lineSpacingDp = 28)
            TemplateType.GRID -> renderGrid(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.DOTTED -> renderDotted(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.DOTTED_GRID -> renderDottedGrid(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.ISOMETRIC -> renderIsometric(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.HEXAGONAL -> renderHexagonal(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.DAILY_PLANNER -> renderDailyPlanner(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.DAILY_SCHEDULE -> renderDailySchedule(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.DAILY_REFLECTION -> renderDailyReflection(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.WEEKLY_PLANNER -> renderWeeklyPlanner(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.WEEKLY_SCHEDULE -> renderWeeklySchedule(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.WEEKLY_REVIEW -> renderWeeklyReview(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.MONTHLY_PLANNER -> renderMonthlyPlanner(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.MONTHLY_CALENDAR -> renderMonthlyCalendar(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.YEARLY_OVERVIEW -> renderYearlyOverview(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.HABIT_TRACKER -> renderHabitTracker(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.HABIT_TRACKER_MINI -> renderHabitTrackerMini(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.MOOD_TRACKER -> renderMoodTracker(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.SLEEP_TRACKER -> renderSleepTracker(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.WATER_TRACKER -> renderWaterTracker(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.MEAL_PLANNER -> renderMealPlanner(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.MEAL_LOG -> renderMealLog(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.GROCERY_LIST -> renderChecklist(canvas, widthPx, heightPx, scaleX, scaleY, theme, "Grocery List")
            TemplateType.FITNESS_LOG -> renderFitnessLog(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.WORKOUT_PLAN -> renderWorkoutPlan(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.RUNNING_LOG -> renderRunningLog(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.BULLET_JOURNAL -> renderBulletJournal(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.BRAIN_DUMP -> renderBrainDump(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.MEETING_NOTES -> renderMeetingNotes(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.LECTURE_NOTES -> renderLectureNotes(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.CORNELL_NOTES -> renderCornellNotes(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.PROJECT_PLANNER -> renderProjectPlanner(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.GOAL_TRACKER -> renderGoalTracker(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.BOOK_LOG -> renderBookLog(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.READING_LIST -> renderChecklist(canvas, widthPx, heightPx, scaleX, scaleY, theme, "Reading List")
            TemplateType.TRAVEL_JOURNAL -> renderTravelJournal(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.BUDGET_PLANNER -> renderBudgetPlanner(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.EXPENSE_TRACKER -> renderExpenseTracker(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.GRATITUDE_JOURNAL -> renderGratitudeJournal(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.JOURNAL -> renderJournal(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.DIARY -> renderDiary(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.RECIPE_CARD -> renderRecipeCard(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.STUDY_SCHEDULE -> renderStudySchedule(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.EXAM_PREP -> renderExamPrep(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.VOCABULARY_LIST -> renderVocabularyList(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.MIND_MAP -> renderMindMap(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.KANBAN -> renderKanban(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.CHECKLIST -> renderChecklist(canvas, widthPx, heightPx, scaleX, scaleY, theme, "Checklist")
            TemplateType.SHOPPING_LIST -> renderChecklist(canvas, widthPx, heightPx, scaleX, scaleY, theme, "Shopping List")
            TemplateType.PACKING_LIST -> renderChecklist(canvas, widthPx, heightPx, scaleX, scaleY, theme, "Packing List")
            TemplateType.BUCKET_LIST -> renderBucketList(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.VISION_BOARD -> renderVisionBoard(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.STORYBOARD -> renderStoryboard(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.MUSIC_SHEET -> renderMusicSheet(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.SKETCH -> renderSketch(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.WATERCOLOR -> { /* blank with texture hint */ }
            TemplateType.MANGA -> renderManga(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.COMIC_PANEL -> renderComicPanel(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.CALLIGRAPHY -> renderCalligraphy(canvas, widthPx, heightPx, scaleX, scaleY, theme)
            TemplateType.HAND_LETTERING -> renderHandLettering(canvas, widthPx, heightPx, scaleX, scaleY, theme)
        }

        cache[cacheKey] = bitmap
        return bitmap
    }

    fun clearCache() = cache.clear()

    // ─── Paint helpers ───────────────────────────────────────────────────────

    private fun linePaint(color: Int, strokeWidth: Float) = Paint().apply {
        this.color = color
        this.strokeWidth = strokeWidth
        isAntiAlias = true
        style = Paint.Style.STROKE
    }

    private fun fillPaint(color: Int) = Paint().apply {
        this.color = color
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    private fun textPaint(color: Int, size: Float, bold: Boolean = false) = Paint().apply {
        this.color = color
        textSize = size
        isAntiAlias = true
        typeface = if (bold) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
    }

    private fun androidx.compose.ui.graphics.Color.toArgb(): Int {
        val alpha = (this.alpha * 255).toInt()
        val red = (this.red * 255).toInt()
        val green = (this.green * 255).toInt()
        val blue = (this.blue * 255).toInt()
        return (alpha shl 24) or (red shl 16) or (green shl 8) or blue
    }

    // ─── Basic Templates ─────────────────────────────────────────────────────

    private fun renderLined(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float,
        theme: TemplateColorTheme, lineSpacingDp: Int = 40,
    ) {
        val spacing = lineSpacingDp * sy
        val paint = linePaint(theme.lineColor.toArgb(), 1.5f * sy)
        val marginLeft = TemplatePageSize.MARGIN_LEFT * sx
        val marginRight = w - TemplatePageSize.MARGIN_RIGHT * sx
        var y = TemplatePageSize.MARGIN_TOP * sy + spacing
        while (y < h - TemplatePageSize.MARGIN_BOTTOM * sy) {
            canvas.drawLine(marginLeft, y, marginRight, y, paint)
            y += spacing
        }
        // Red margin line (classic ruled paper)
        val marginPaint = linePaint(0x40FF4444, 1.5f * sx)
        val marginX = 60f * sx
        canvas.drawLine(marginX, 0f, marginX, h.toFloat(), marginPaint)
    }

    private fun renderGrid(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float,
        theme: TemplateColorTheme, spacingDp: Int = 30,
    ) {
        val spacingX = spacingDp * sx
        val spacingY = spacingDp * sy
        val paint = linePaint(theme.lineColor.toArgb(), 1f * sy)

        var x = 0f
        while (x <= w) { canvas.drawLine(x, 0f, x, h.toFloat(), paint); x += spacingX }
        var y = 0f
        while (y <= h) { canvas.drawLine(0f, y, w.toFloat(), y, paint); y += spacingY }
    }

    private fun renderDotted(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float,
        theme: TemplateColorTheme, spacingDp: Int = 30,
    ) {
        val spacingX = spacingDp * sx
        val spacingY = spacingDp * sy
        val paint = fillPaint(theme.lineColor.toArgb())
        val dotRadius = 2f * sx
        val marginTop = TemplatePageSize.MARGIN_TOP * sy
        val marginLeft = TemplatePageSize.MARGIN_LEFT * sx

        var y = marginTop
        while (y <= h - TemplatePageSize.MARGIN_BOTTOM * sy) {
            var x = marginLeft
            while (x <= w - TemplatePageSize.MARGIN_RIGHT * sx) {
                canvas.drawCircle(x, y, dotRadius, paint)
                x += spacingX
            }
            y += spacingY
        }
    }

    private fun renderDottedGrid(c: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme) =
        renderDotted(c, w, h, sx, sy, theme, 25)

    private fun renderIsometric(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        val paint = linePaint(theme.lineColor.toArgb(), 1f * sy)
        val spacing = 30f * sy
        val angle = Math.PI / 3.0 // 60 degrees

        // Horizontal lines
        var y = 0f
        while (y <= h) { canvas.drawLine(0f, y, w.toFloat(), y, paint); y += spacing }

        // Diagonal lines (left to right)
        val dx = (spacing / Math.tan(angle)).toFloat()
        var startX = -h.toFloat()
        while (startX <= w.toFloat()) {
            canvas.drawLine(startX, 0f, startX + h / Math.tan(angle).toFloat(), h.toFloat(), paint)
            startX += dx
        }

        // Diagonal lines (right to left)
        startX = 0f
        while (startX <= w * 2f) {
            canvas.drawLine(startX, 0f, startX - h / Math.tan(angle).toFloat(), h.toFloat(), paint)
            startX += dx
        }
    }

    private fun renderHexagonal(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        val paint = linePaint(theme.lineColor.toArgb(), 1f * sy)
        val size = 25f * sx
        val hexW = size * 2
        val hexH = Math.sqrt(3.0).toFloat() * size
        val colW = hexW * 0.75f

        var col = 0
        var x = 0f
        while (x < w + hexW) {
            val yOffset = if (col % 2 == 0) 0f else hexH / 2f
            var y = yOffset - hexH
            while (y < h + hexH) {
                drawHexagon(canvas, x, y, size, paint)
                y += hexH
            }
            x += colW
            col++
        }
    }

    private fun drawHexagon(canvas: Canvas, cx: Float, cy: Float, size: Float, paint: Paint) {
        val path = android.graphics.Path()
        for (i in 0..5) {
            val angle = Math.PI / 180 * (60 * i - 30)
            val px = (cx + size * Math.cos(angle)).toFloat()
            val py = (cy + size * Math.sin(angle)).toFloat()
            if (i == 0) path.moveTo(px, py) else path.lineTo(px, py)
        }
        path.close()
        canvas.drawPath(path, paint)
    }

    // ─── Planner Templates ───────────────────────────────────────────────────

    private fun renderHeader(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float,
        theme: TemplateColorTheme, title: String, headerHeight: Float = 80f,
    ) {
        val headerPx = headerHeight * sy
        // Header background
        val headerPaint = fillPaint(theme.headerBgColor.toArgb())
        canvas.drawRect(0f, 0f, w.toFloat(), headerPx, headerPaint)
        // Title text
        val titlePaint = textPaint(theme.textColor.copy(alpha = 1f).toArgb(), 28f * sy, bold = true)
        val titleX = TemplatePageSize.MARGIN_LEFT * sx
        val titleY = headerPx * 0.65f
        canvas.drawText(title, titleX, titleY, titlePaint)
    }

    private fun renderDailyPlanner(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Daily Planner")
        val headerHeight = 80f * sy
        val linePaint = linePaint(theme.lineColor.toArgb(), 1.5f * sy)
        val labelPaint = textPaint(theme.accentColor.toArgb(), 18f * sy, bold = true)
        val marginLeft = TemplatePageSize.MARGIN_LEFT * sx
        val marginRight = w - TemplatePageSize.MARGIN_RIGHT * sx
        val contentWidth = marginRight - marginLeft

        // Time slots: 6am to 10pm
        val timeSlots = (6..22).map { h ->
            "${if (h <= 12) h else h - 12}:00 ${if (h < 12) "AM" else "PM"}"
        }
        val slotHeight = (h - headerHeight - 80f * sy) / timeSlots.size
        timeSlots.forEachIndexed { i, time ->
            val y = headerHeight + i * slotHeight
            canvas.drawLine(marginLeft, y, marginRight, y, linePaint)
            canvas.drawText(time, marginLeft, y - 6f * sy, labelPaint)
        }
    }

    private fun renderDailySchedule(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Daily Schedule")
        val headerH = 80f * sy
        val paint = linePaint(theme.lineColor.toArgb(), 1.5f * sy)
        val labelPaint = textPaint(theme.accentColor.toArgb(), 16f * sy)
        val ml = TemplatePageSize.MARGIN_LEFT * sx
        val mr = w - TemplatePageSize.MARGIN_RIGHT * sx

        // Sections: Morning, Afternoon, Evening, Tonight
        val sections = listOf("Morning", "Afternoon", "Evening", "Tonight")
        val sectionH = (h - headerH) / sections.size
        sections.forEachIndexed { i, name ->
            val y = headerH + i * sectionH
            // Section divider
            val divPaint = fillPaint(theme.accentColor.copy(alpha = 0.15f).toArgb())
            canvas.drawRect(ml, y, mr, y + 35f * sy, divPaint)
            canvas.drawText(name, ml + 8f * sx, y + 25f * sy, textPaint(theme.accentColor.toArgb(), 18f * sy, true))
            // Lined area
            var lineY = y + 50f * sy
            while (lineY < y + sectionH - 10f * sy) {
                canvas.drawLine(ml, lineY, mr, lineY, paint)
                lineY += 36f * sy
            }
        }
    }

    private fun renderDailyReflection(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Daily Reflection")
        val sections = listOf(
            "What went well today?",
            "What could be improved?",
            "What am I grateful for?",
            "Tomorrow's top priority:",
        )
        renderSectionedLined(canvas, w, h, sx, sy, theme, sections, startY = 90f)
    }

    private fun renderWeeklyPlanner(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Weekly Planner")
        val headerH = 80f * sy
        val days = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
        val colWidth = (w - TemplatePageSize.MARGIN_LEFT * sx - TemplatePageSize.MARGIN_RIGHT * sx) / days.size
        val ml = TemplatePageSize.MARGIN_LEFT * sx
        val paint = linePaint(theme.lineColor.toArgb(), 1f * sy)
        val headerPaint = fillPaint(theme.accentColor.copy(alpha = 0.12f).toArgb())
        val labelPaint = textPaint(theme.accentColor.toArgb(), 16f * sy, bold = true)
        val dayLabelH = 40f * sy

        days.forEachIndexed { i, day ->
            val x = ml + i * colWidth
            // Day header
            canvas.drawRect(x, headerH, x + colWidth, headerH + dayLabelH, headerPaint)
            val textX = x + (colWidth - labelPaint.measureText(day)) / 2f
            canvas.drawText(day, textX, headerH + 27f * sy, labelPaint)
            // Column separator
            canvas.drawLine(x, headerH, x, h.toFloat(), paint)
            // Horizontal lines in each column
            var lineY = headerH + dayLabelH + 35f * sy
            while (lineY < h.toFloat()) {
                canvas.drawLine(x, lineY, x + colWidth, lineY, paint)
                lineY += 35f * sy
            }
        }
    }

    private fun renderWeeklySchedule(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Weekly Schedule")
        val headerH = 80f * sy
        val ml = TemplatePageSize.MARGIN_LEFT * sx
        val mr = w - TemplatePageSize.MARGIN_RIGHT * sx
        val timeColW = 70f * sx
        val days = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
        val dayColW = (mr - ml - timeColW) / days.size
        val paint = linePaint(theme.lineColor.toArgb(), 1f * sy)
        val labelPaint = textPaint(theme.accentColor.toArgb(), 14f * sy, bold = true)
        val dayHeaderH = 35f * sy

        // Draw day headers
        days.forEachIndexed { i, day ->
            val x = ml + timeColW + i * dayColW
            val hPaint = fillPaint(theme.accentColor.copy(alpha = 0.12f).toArgb())
            canvas.drawRect(x, headerH, x + dayColW, headerH + dayHeaderH, hPaint)
            canvas.drawText(day, x + 6f * sx, headerH + 24f * sy, labelPaint)
            canvas.drawLine(x, headerH, x, h.toFloat(), paint)
        }

        // Time rows
        val times = (7..22).map { "$it:00" }
        val rowH = (h - headerH - dayHeaderH) / times.size
        times.forEachIndexed { i, time ->
            val y = headerH + dayHeaderH + i * rowH
            canvas.drawLine(ml, y, mr, y, paint)
            canvas.drawText(time, ml + 4f * sx, y + 14f * sy, textPaint(theme.accentColor.toArgb(), 13f * sy))
        }
    }

    private fun renderWeeklyReview(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Weekly Review")
        val sections = listOf(
            "Highlights of the week",
            "What didn't go as planned?",
            "What did I learn?",
            "Next week goals",
            "Personal wins",
        )
        renderSectionedLined(canvas, w, h, sx, sy, theme, sections, startY = 90f)
    }

    private fun renderMonthlyPlanner(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Monthly Planner")
        val headerH = 80f * sy
        val ml = TemplatePageSize.MARGIN_LEFT * sx
        val mr = w - TemplatePageSize.MARGIN_RIGHT * sx
        val contentH = h - headerH
        val paint = linePaint(theme.lineColor.toArgb(), 1f * sy)
        val labelPaint = textPaint(theme.accentColor.toArgb(), 14f * sy)

        // 31 rows for days
        val rowH = contentH / 31f
        (1..31).forEach { day ->
            val y = headerH + (day - 1) * rowH
            canvas.drawText("$day", ml + 4f * sx, y + rowH * 0.7f, labelPaint)
            canvas.drawLine(ml + 25f * sx, y, mr, y, paint)
        }
        // Day column divider
        canvas.drawLine(ml + 24f * sx, headerH, ml + 24f * sx, h.toFloat(), paint)
    }

    private fun renderMonthlyCalendar(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Monthly Calendar")
        val headerH = 80f * sy
        val ml = TemplatePageSize.MARGIN_LEFT * sx
        val mr = w - TemplatePageSize.MARGIN_RIGHT * sx
        val calW = mr - ml
        val days = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")
        val colW = calW / 7f
        val dayHeaderH = 35f * sy
        val rowCount = 6
        val rowH = (h - headerH - dayHeaderH) / rowCount
        val paint = linePaint(theme.lineColor.toArgb(), 1f * sy)
        val labelPaint = textPaint(theme.accentColor.toArgb(), 14f * sy, bold = true)

        // Day headers
        days.forEachIndexed { i, day ->
            val x = ml + i * colW
            canvas.drawText(day, x + 4f * sx, headerH + 24f * sy, labelPaint)
            canvas.drawLine(x, headerH, x, h.toFloat(), paint)
        }
        canvas.drawLine(ml, headerH + dayHeaderH, mr, headerH + dayHeaderH, paint)

        // Grid rows
        (0..rowCount).forEach { row ->
            val y = headerH + dayHeaderH + row * rowH
            canvas.drawLine(ml, y, mr, y, paint)
        }
    }

    private fun renderYearlyOverview(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Yearly Overview")
        val headerH = 80f * sy
        val ml = TemplatePageSize.MARGIN_LEFT * sx
        val mr = w - TemplatePageSize.MARGIN_RIGHT * sx
        val paint = linePaint(theme.lineColor.toArgb(), 1f * sy)
        val labelPaint = textPaint(theme.accentColor.toArgb(), 14f * sy, bold = true)
        val months = listOf("JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC")
        val colCount = 3
        val rowCount = 4
        val colW = (mr - ml) / colCount
        val rowH = (h - headerH) / rowCount

        months.forEachIndexed { i, month ->
            val col = i % colCount
            val row = i / colCount
            val x = ml + col * colW
            val y = headerH + row * rowH
            canvas.drawRect(x, y, x + colW, y + rowH, paint.apply { style = Paint.Style.STROKE })
            canvas.drawText(month, x + 8f * sx, y + 24f * sy, labelPaint)
        }
    }

    // ─── Health Templates ────────────────────────────────────────────────────

    private fun renderHabitTracker(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Habit Tracker")
        val headerH = 80f * sy
        val ml = TemplatePageSize.MARGIN_LEFT * sx
        val mr = w - TemplatePageSize.MARGIN_RIGHT * sx
        val habitColW = 180f * sx
        val dayCount = 31
        val dayColW = (mr - ml - habitColW) / dayCount
        val rowH = 36f * sy
        val maxHabits = ((h - headerH - 40f * sy) / rowH).toInt()
        val paint = linePaint(theme.lineColor.toArgb(), 1f * sy)
        val labelPaint = textPaint(theme.accentColor.toArgb(), 11f * sy)

        // Day headers (1-31)
        (1..dayCount).forEach { day ->
            val x = ml + habitColW + (day - 1) * dayColW
            canvas.drawText("$day", x + 2f, headerH + 25f * sy, labelPaint)
            canvas.drawLine(x, headerH, x, h.toFloat(), paint)
        }
        // Habit divider
        canvas.drawLine(ml + habitColW, headerH, ml + habitColW, h.toFloat(), paint)

        // Rows
        (0..maxHabits).forEach { i ->
            val y = headerH + 35f * sy + i * rowH
            canvas.drawLine(ml, y, mr, y, paint)
        }
    }

    private fun renderHabitTrackerMini(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) = renderHabitTracker(canvas, w, h, sx, sy, theme)

    private fun renderMoodTracker(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Mood Tracker")
        val headerH = 80f * sy
        val ml = TemplatePageSize.MARGIN_LEFT * sx
        val mr = w - TemplatePageSize.MARGIN_RIGHT * sx
        val dayCount = 31
        val circleRadius = 22f * sx
        val spacing = (mr - ml) / 7f
        val labelPaint = textPaint(theme.lineColor.toArgb(), 13f * sy)

        // 5 rows x 7 cols of circles
        val moods = listOf("😊","🙂","😐","😔","😢")
        val moodPaint = fillPaint(theme.lineColor.copy(alpha = 0.3f).toArgb())
        var day = 1
        for (row in 0..4) {
            for (col in 0..6) {
                if (day > dayCount) break
                val cx = ml + col * spacing + spacing / 2f
                val cy = headerH + 60f * sy + row * (circleRadius * 2.8f)
                canvas.drawCircle(cx, cy, circleRadius, moodPaint)
                canvas.drawText("$day", cx - 8f * sx, cy + 5f * sy, labelPaint)
                day++
            }
        }
    }

    private fun renderSleepTracker(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Sleep Tracker")
        val sections = listOf("Date", "Bedtime", "Wake up", "Hours slept", "Quality (1-5)", "Notes")
        renderTableHeader(canvas, w, h, sx, sy, theme, sections, startY = 85f)
    }

    private fun renderWaterTracker(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Water Tracker")
        val headerH = 80f * sy
        val ml = TemplatePageSize.MARGIN_LEFT * sx
        val mr = w - TemplatePageSize.MARGIN_RIGHT * sx
        val circleR = 28f * sx
        val spacing = (mr - ml) / 8f
        val fillPaint = fillPaint(theme.accentColor.copy(alpha = 0.2f).toArgb())
        val borderPaint = linePaint(theme.accentColor.toArgb(), 2f * sx)
        val labelPaint = textPaint(theme.textColor.toArgb(), 13f * sy)

        // 8 glasses per day × 4 weeks
        for (week in 0..3) {
            for (glass in 0..7) {
                val cx = ml + glass * spacing + spacing / 2f
                val cy = headerH + 60f * sy + week * (circleR * 2.8f)
                canvas.drawCircle(cx, cy, circleR, fillPaint)
                canvas.drawCircle(cx, cy, circleR, borderPaint)
            }
        }
    }

    private fun renderMealPlanner(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Meal Planner")
        val headerH = 80f * sy
        val ml = TemplatePageSize.MARGIN_LEFT * sx
        val mr = w - TemplatePageSize.MARGIN_RIGHT * sx
        val days = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
        val meals = listOf("Breakfast", "Lunch", "Dinner", "Snacks")
        val colW = (mr - ml - 80f * sx) / days.size
        val rowH = (h - headerH - 40f * sy) / meals.size
        val paint = linePaint(theme.lineColor.toArgb(), 1f * sy)
        val labelPaint = textPaint(theme.accentColor.toArgb(), 14f * sy, bold = true)

        // Day headers
        days.forEachIndexed { i, day ->
            val x = ml + 80f * sx + i * colW
            canvas.drawText(day, x + 4f * sx, headerH + 25f * sy, labelPaint)
            canvas.drawLine(x, headerH, x, h.toFloat(), paint)
        }
        // Meal rows
        meals.forEachIndexed { i, meal ->
            val y = headerH + 35f * sy + i * rowH
            canvas.drawText(meal, ml + 4f * sx, y + rowH / 2f + 6f * sy, labelPaint)
            canvas.drawLine(ml, y, mr, y, paint)
        }
        canvas.drawLine(ml + 79f * sx, headerH, ml + 79f * sx, h.toFloat(), paint)
    }

    private fun renderMealLog(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Meal Log")
        val columns = listOf("Time", "Food/Drink", "Calories", "Notes")
        renderTableHeader(canvas, w, h, sx, sy, theme, columns, startY = 85f, rows = 20)
    }

    private fun renderFitnessLog(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Fitness Log")
        val columns = listOf("Exercise", "Sets", "Reps", "Weight", "Notes")
        renderTableHeader(canvas, w, h, sx, sy, theme, columns, startY = 85f, rows = 18)
    }

    private fun renderWorkoutPlan(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Workout Plan")
        val headerH = 80f * sy
        val days = listOf("Day 1", "Day 2", "Day 3", "Day 4", "Day 5")
        val ml = TemplatePageSize.MARGIN_LEFT * sx
        val mr = w - TemplatePageSize.MARGIN_RIGHT * sx
        val colW = (mr - ml) / days.size
        val paint = linePaint(theme.lineColor.toArgb(), 1f * sy)
        val labelPaint = textPaint(theme.accentColor.toArgb(), 16f * sy, bold = true)
        val dayHeaderH = 35f * sy

        days.forEachIndexed { i, day ->
            val x = ml + i * colW
            canvas.drawRect(x, headerH, x + colW, headerH + dayHeaderH,
                fillPaint(theme.accentColor.copy(alpha = 0.12f).toArgb()))
            canvas.drawText(day, x + 6f * sx, headerH + 24f * sy, labelPaint)
            canvas.drawLine(x, headerH, x, h.toFloat(), paint)
            var lineY = headerH + dayHeaderH + 35f * sy
            while (lineY < h.toFloat()) { canvas.drawLine(x, lineY, x + colW, lineY, paint); lineY += 35f * sy }
        }
    }

    private fun renderRunningLog(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Running Log")
        val columns = listOf("Date", "Distance", "Time", "Pace", "Heart Rate", "Notes")
        renderTableHeader(canvas, w, h, sx, sy, theme, columns, startY = 85f, rows = 15)
    }

    private fun renderGratitudeJournal(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Gratitude Journal")
        val sections = listOf(
            "I am grateful for...",
            "Today's highlight:",
            "Someone I appreciate:",
            "A positive affirmation:",
        )
        renderSectionedLined(canvas, w, h, sx, sy, theme, sections, startY = 90f)
    }

    // ─── Work / Study Templates ───────────────────────────────────────────────

    private fun renderMeetingNotes(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Meeting Notes")
        val headerH = 80f * sy
        val ml = TemplatePageSize.MARGIN_LEFT * sx
        val mr = w - TemplatePageSize.MARGIN_RIGHT * sx
        val paint = linePaint(theme.lineColor.toArgb(), 1f * sy)
        val labelPaint = textPaint(theme.accentColor.toArgb(), 15f * sy, bold = true)

        // Meta section: Date, Attendees, Topic
        val metaFields = listOf("Date:", "Attendees:", "Topic:")
        metaFields.forEachIndexed { i, field ->
            val y = headerH + 30f * sy + i * 30f * sy
            canvas.drawText(field, ml, y, labelPaint)
            canvas.drawLine(ml + 80f * sx, y + 5f * sy, mr, y + 5f * sy, paint)
        }

        // Main notes area with lines
        val notesStartY = headerH + 130f * sy
        canvas.drawText("Notes:", ml, notesStartY - 8f * sy, labelPaint)
        var lineY = notesStartY + 5f * sy
        while (lineY < h - 100f * sy) { canvas.drawLine(ml, lineY, mr, lineY, paint); lineY += 34f * sy }

        // Action items section
        val actionY = h - 90f * sy
        canvas.drawText("Action Items:", ml, actionY - 6f * sy, labelPaint)
        canvas.drawLine(ml, actionY, mr, actionY, paint)
        canvas.drawLine(ml, actionY + 34f * sy, mr, actionY + 34f * sy, paint)
        canvas.drawLine(ml, actionY + 68f * sy, mr, actionY + 68f * sy, paint)
    }

    private fun renderLectureNotes(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Lecture Notes")
        renderLined(canvas, w, h, sx, sy, theme)
    }

    private fun renderCornellNotes(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Cornell Notes")
        val headerH = 80f * sy
        val ml = TemplatePageSize.MARGIN_LEFT * sx
        val mr = w - TemplatePageSize.MARGIN_RIGHT * sx
        val paint = linePaint(theme.lineColor.toArgb(), 2f * sy)
        val labelPaint = textPaint(theme.accentColor.toArgb(), 15f * sy, bold = true)
        val cueColW = 180f * sx
        val summaryH = 120f * sy

        // Cue column divider
        canvas.drawLine(ml + cueColW, headerH, ml + cueColW, h - summaryH, paint)
        // Summary divider
        canvas.drawLine(ml, h - summaryH, mr, h - summaryH, paint)
        // Labels
        canvas.drawText("Cues", ml + 4f * sx, headerH + 20f * sy, labelPaint)
        canvas.drawText("Notes", ml + cueColW + 8f * sx, headerH + 20f * sy, labelPaint)
        canvas.drawText("Summary", ml + 4f * sx, h - summaryH + 22f * sy, labelPaint)

        // Lines in cue column
        val cueLinePaint = linePaint(theme.lineColor.toArgb(), 1f * sy)
        var lineY = headerH + 40f * sy
        while (lineY < h - summaryH) {
            canvas.drawLine(ml, lineY, ml + cueColW, lineY, cueLinePaint)
            canvas.drawLine(ml + cueColW, lineY, mr, lineY, cueLinePaint)
            lineY += 34f * sy
        }
    }

    private fun renderProjectPlanner(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Project Planner")
        val sections = listOf(
            "Project Name:",
            "Goal:",
            "Deadline:",
            "Team / Stakeholders:",
            "Milestones:",
            "Tasks:",
            "Notes:",
        )
        renderSectionedLined(canvas, w, h, sx, sy, theme, sections, startY = 90f)
    }

    private fun renderGoalTracker(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Goal Tracker")
        val sections = listOf(
            "My Goal:",
            "Why it matters:",
            "Steps to achieve it:",
            "Deadline:",
            "Progress check:",
            "Celebrate when:",
        )
        renderSectionedLined(canvas, w, h, sx, sy, theme, sections, startY = 90f)
    }

    private fun renderBookLog(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Book Log")
        val columns = listOf("Title", "Author", "Genre", "Rating", "Finished")
        renderTableHeader(canvas, w, h, sx, sy, theme, columns, startY = 85f, rows = 18)
    }

    private fun renderBulletJournal(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderDotted(canvas, w, h, sx, sy, theme, 20)
    }

    private fun renderBrainDump(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Brain Dump")
        renderLined(canvas, w, h, sx, sy, theme)
    }

    private fun renderTravelJournal(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Travel Journal")
        val sections = listOf(
            "Destination:", "Dates:", "Highlights:", "Places visited:",
            "Food I loved:", "People I met:", "Memories:",
        )
        renderSectionedLined(canvas, w, h, sx, sy, theme, sections, startY = 90f)
    }

    private fun renderBudgetPlanner(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Budget Planner")
        val columns = listOf("Category", "Budgeted", "Actual", "Difference")
        renderTableHeader(canvas, w, h, sx, sy, theme, columns, startY = 85f, rows = 20)
    }

    private fun renderExpenseTracker(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Expense Tracker")
        val columns = listOf("Date", "Description", "Category", "Amount")
        renderTableHeader(canvas, w, h, sx, sy, theme, columns, startY = 85f, rows = 22)
    }

    private fun renderJournal(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Journal")
        val headerH = 80f * sy
        val ml = TemplatePageSize.MARGIN_LEFT * sx
        val mr = w - TemplatePageSize.MARGIN_RIGHT * sx
        // Date field at top
        val datePaint = textPaint(theme.accentColor.toArgb(), 16f * sy)
        canvas.drawText("Date: ____________________", ml, headerH + 30f * sy, datePaint)
        // Lines
        val paint = linePaint(theme.lineColor.toArgb(), 1.5f * sy)
        var lineY = headerH + 55f * sy
        while (lineY < h.toFloat()) { canvas.drawLine(ml, lineY, mr, lineY, paint); lineY += 38f * sy }
    }

    private fun renderDiary(c: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme) =
        renderJournal(c, w, h, sx, sy, theme)

    private fun renderStudySchedule(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Study Schedule")
        renderWeeklySchedule(canvas, w, h, sx, sy, theme)
    }

    private fun renderExamPrep(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Exam Prep")
        val sections = listOf(
            "Subject:", "Exam Date:", "Topics to study:",
            "Key formulas / facts:", "Practice questions:", "Review notes:",
        )
        renderSectionedLined(canvas, w, h, sx, sy, theme, sections, startY = 90f)
    }

    private fun renderVocabularyList(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Vocabulary List")
        val columns = listOf("Word", "Definition", "Example sentence")
        renderTableHeader(canvas, w, h, sx, sy, theme, columns, startY = 85f, rows = 20)
    }

    private fun renderMindMap(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        // Central circle
        val cx = w / 2f; val cy = h / 2f
        val centerR = 60f * sx
        val paint = linePaint(theme.accentColor.toArgb(), 2f * sx)
        val fillPaint = fillPaint(theme.accentColor.copy(alpha = 0.15f).toArgb())
        canvas.drawCircle(cx, cy, centerR, fillPaint)
        canvas.drawCircle(cx, cy, centerR, paint)

        // Branch nodes
        val branches = 6
        val branchR = 35f * sx
        val branchDist = 200f * sx
        for (i in 0 until branches) {
            val angle = (360.0 / branches * i) * Math.PI / 180.0
            val bx = (cx + branchDist * Math.cos(angle)).toFloat()
            val by = (cy + branchDist * Math.sin(angle)).toFloat()
            canvas.drawLine(cx, cy, bx, by, paint)
            canvas.drawCircle(bx, by, branchR, fillPaint)
            canvas.drawCircle(bx, by, branchR, paint)
        }
    }

    private fun renderKanban(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Kanban Board")
        val headerH = 80f * sy
        val cols = listOf("To Do", "In Progress", "Done")
        val ml = TemplatePageSize.MARGIN_LEFT * sx
        val mr = w - TemplatePageSize.MARGIN_RIGHT * sx
        val colW = (mr - ml) / cols.size
        val paint = linePaint(theme.lineColor.toArgb(), 1.5f * sy)
        val labelPaint = textPaint(theme.accentColor.toArgb(), 18f * sy, bold = true)
        val colHeaderH = 45f * sy

        cols.forEachIndexed { i, col ->
            val x = ml + i * colW
            canvas.drawRect(x, headerH, x + colW, headerH + colHeaderH,
                fillPaint(theme.accentColor.copy(alpha = 0.12f + i * 0.08f).toArgb()))
            canvas.drawText(col, x + 8f * sx, headerH + 30f * sy, labelPaint)
            canvas.drawLine(x, headerH, x, h.toFloat(), paint)
        }
    }

    private fun renderBucketList(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) = renderChecklist(canvas, w, h, sx, sy, theme, "Bucket List")

    private fun renderVisionBoard(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Vision Board")
        val headerH = 80f * sy
        val ml = TemplatePageSize.MARGIN_LEFT * sx; val mr = w - TemplatePageSize.MARGIN_RIGHT * sx
        val boxPaint = linePaint(theme.lineColor.toArgb(), 1.5f * sy)
        // 2×3 grid of vision boxes
        val cols = 2; val rows = 3
        val boxW = (mr - ml) / cols; val boxH = (h - headerH) / rows
        for (r in 0 until rows) for (c in 0 until cols) {
            canvas.drawRect(ml + c * boxW, headerH + r * boxH, ml + (c+1) * boxW, headerH + (r+1) * boxH, boxPaint)
        }
    }

    private fun renderRecipeCard(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Recipe Card")
        val sections = listOf(
            "Recipe Name:", "Prep Time:  Cook Time:  Servings:",
            "Ingredients:", "", "", "", "Instructions:", "", "", "",
        )
        renderSectionedLined(canvas, w, h, sx, sy, theme, sections, startY = 90f)
    }

    // ─── Creative Templates ───────────────────────────────────────────────────

    private fun renderStoryboard(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, "Storyboard")
        val headerH = 80f * sy
        val ml = TemplatePageSize.MARGIN_LEFT * sx; val mr = w - TemplatePageSize.MARGIN_RIGHT * sx
        val paint = linePaint(theme.lineColor.toArgb(), 1.5f * sy)
        // 2 cols × 3 rows of panels with desc below
        val panelCols = 2; val panelRows = 3
        val panelW = (mr - ml) / panelCols; val totalH = (h - headerH)
        val panelH = totalH / panelRows * 0.72f; val descH = totalH / panelRows * 0.28f
        for (r in 0 until panelRows) for (c in 0 until panelCols) {
            val x = ml + c * panelW; val y = headerH + r * (panelH + descH)
            canvas.drawRect(x + 6f*sx, y + 6f*sy, x + panelW - 6f*sx, y + panelH, paint)
            val linePaint2 = linePaint(theme.lineColor.copy(alpha=0.5f).toArgb(), 1f * sy)
            canvas.drawLine(x + 6f*sx, y + panelH + 20f*sy, x + panelW - 6f*sx, y + panelH + 20f*sy, linePaint2)
        }
    }

    private fun renderMusicSheet(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        val ml = TemplatePageSize.MARGIN_LEFT * sx; val mr = w - TemplatePageSize.MARGIN_RIGHT * sx
        val paint = linePaint(theme.lineColor.toArgb(), 1f * sy)
        val staffSpacing = 12f * sy; val staffGroupSpacing = 40f * sy
        var y = TemplatePageSize.MARGIN_TOP * sy
        while (y < h - TemplatePageSize.MARGIN_BOTTOM * sy) {
            // 5 staff lines
            for (line in 0..4) {
                canvas.drawLine(ml, y + line * staffSpacing, mr, y + line * staffSpacing, paint)
            }
            y += 5 * staffSpacing + staffGroupSpacing
        }
    }

    private fun renderSketch(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        // Light border only
        val paint = linePaint(theme.lineColor.copy(alpha=0.3f).toArgb(), 1f * sy)
        val ml = TemplatePageSize.MARGIN_LEFT * sx; val mr = w - TemplatePageSize.MARGIN_RIGHT * sx
        val mt = TemplatePageSize.MARGIN_TOP * sy; val mb = h - TemplatePageSize.MARGIN_BOTTOM * sy
        canvas.drawRect(ml, mt, mr, mb, paint)
    }

    private fun renderManga(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        val paint = linePaint(theme.lineColor.toArgb(), 2f * sy)
        val ml = TemplatePageSize.MARGIN_LEFT * sx; val mr = w - TemplatePageSize.MARGIN_RIGHT * sx
        val mt = TemplatePageSize.MARGIN_TOP * sy; val mb = h - TemplatePageSize.MARGIN_BOTTOM * sy
        // Manga panel layout: asymmetric
        val midX = ml + (mr - ml) * 0.4f; val midY = mt + (mb - mt) * 0.55f
        canvas.drawRect(ml, mt, mr, mb, paint)
        canvas.drawLine(midX, mt, midX, midY, paint)
        canvas.drawLine(ml, midY, mr, midY, paint)
        canvas.drawLine(midX + (mr - midX) / 2f, midY, midX + (mr - midX) / 2f, mb, paint)
    }

    private fun renderComicPanel(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        val paint = linePaint(theme.lineColor.toArgb(), 3f * sy)
        val ml = TemplatePageSize.MARGIN_LEFT * sx; val mr = w - TemplatePageSize.MARGIN_RIGHT * sx
        val mt = TemplatePageSize.MARGIN_TOP * sy; val mb = h - TemplatePageSize.MARGIN_BOTTOM * sy
        val gap = 8f * sy
        // 3×2 panels
        val cols = 3; val rows = 2
        val pw = (mr - ml - gap * (cols - 1)) / cols; val ph = (mb - mt - gap * (rows - 1)) / rows
        for (r in 0 until rows) for (c in 0 until cols) {
            val x = ml + c * (pw + gap); val y = mt + r * (ph + gap)
            canvas.drawRect(x, y, x + pw, y + ph, paint)
        }
    }

    private fun renderCalligraphy(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme,
    ) {
        val ml = TemplatePageSize.MARGIN_LEFT * sx; val mr = w - TemplatePageSize.MARGIN_RIGHT * sx
        val basePaint = linePaint(theme.lineColor.toArgb(), 1f * sy)
        val slantPaint = linePaint(theme.lineColor.copy(alpha=0.4f).toArgb(), 0.5f * sy)
        val groupH = 70f * sy; val lineCount = 4
        val spacings = listOf(0f, 20f, 45f, 65f) // ascender, cap, base, descender
        var groupY = TemplatePageSize.MARGIN_TOP * sy
        while (groupY < h - TemplatePageSize.MARGIN_BOTTOM * sy) {
            for (i in spacings.indices) {
                val y = groupY + spacings[i] * sy
                if (y > h) break
                canvas.drawLine(ml, y, mr, y, if (i == 2) basePaint else slantPaint)
            }
            // Slant guides
            var sx2 = ml
            while (sx2 < mr) {
                canvas.drawLine(sx2, groupY, sx2 + 20f * sy, groupY + groupH, slantPaint)
                sx2 += 30f * sx
            }
            groupY += groupH + 20f * sy
        }
    }

    private fun renderHandLettering(c: Canvas, w: Int, h: Int, sx: Float, sy: Float, theme: TemplateColorTheme) =
        renderCalligraphy(c, w, h, sx, sy, theme)

    // ─── Shared helpers ───────────────────────────────────────────────────────

    private fun renderSectionedLined(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float,
        theme: TemplateColorTheme, sections: List<String>, startY: Float,
    ) {
        val ml = TemplatePageSize.MARGIN_LEFT * sx; val mr = w - TemplatePageSize.MARGIN_RIGHT * sx
        val paint = linePaint(theme.lineColor.toArgb(), 1.5f * sy)
        val labelPaint = textPaint(theme.accentColor.toArgb(), 16f * sy, bold = true)
        val linesPerSection = ((h - startY * sy) / sections.size / (34f * sy)).toInt().coerceAtLeast(2)
        var y = startY * sy

        sections.forEach { section ->
            // Section label
            if (section.isNotEmpty()) {
                canvas.drawText(section, ml, y, labelPaint)
                y += 20f * sy
            }
            // Lines
            for (line in 0 until linesPerSection) {
                if (y >= h - 20f * sy) return
                canvas.drawLine(ml, y, mr, y, paint)
                y += 34f * sy
            }
            y += 12f * sy
        }
    }

    private fun renderTableHeader(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float,
        theme: TemplateColorTheme, columns: List<String>,
        startY: Float, rows: Int = 15,
    ) {
        val ml = TemplatePageSize.MARGIN_LEFT * sx; val mr = w - TemplatePageSize.MARGIN_RIGHT * sx
        val paint = linePaint(theme.lineColor.toArgb(), 1f * sy)
        val labelPaint = textPaint(theme.accentColor.toArgb(), 14f * sy, bold = true)
        val colW = (mr - ml) / columns.size
        val headerRowH = 35f * sy
        val rowH = (h - startY * sy - headerRowH) / rows

        // Header row background
        canvas.drawRect(ml, startY * sy, mr, startY * sy + headerRowH,
            fillPaint(theme.accentColor.copy(alpha = 0.12f).toArgb()))

        columns.forEachIndexed { i, col ->
            val x = ml + i * colW
            canvas.drawText(col, x + 4f * sx, startY * sy + 24f * sy, labelPaint)
            canvas.drawLine(x, startY * sy, x, h.toFloat(), paint)
        }
        canvas.drawLine(ml, startY * sy + headerRowH, mr, startY * sy + headerRowH, paint)

        for (row in 1..rows) {
            val y = startY * sy + headerRowH + row * rowH
            canvas.drawLine(ml, y, mr, y, paint)
        }
    }

    private fun renderChecklist(
        canvas: Canvas, w: Int, h: Int, sx: Float, sy: Float,
        theme: TemplateColorTheme, title: String,
    ) {
        renderHeader(canvas, w, h, sx, sy, theme, title)
        val headerH = 80f * sy
        val ml = TemplatePageSize.MARGIN_LEFT * sx; val mr = w - TemplatePageSize.MARGIN_RIGHT * sx
        val paint = linePaint(theme.lineColor.toArgb(), 1.5f * sy)
        val boxPaint = linePaint(theme.accentColor.toArgb(), 1.5f * sx)
        val itemH = 38f * sy
        var y = headerH + 20f * sy
        while (y < h - 30f * sy) {
            canvas.drawLine(ml, y, mr, y, paint)
            // Checkbox
            canvas.drawRect(ml, y - 22f * sy, ml + 20f * sx, y - 2f * sy, boxPaint)
            y += itemH
        }
    }
}
