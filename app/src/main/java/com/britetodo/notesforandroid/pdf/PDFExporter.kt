package com.britetodo.notesforandroid.pdf

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import androidx.core.content.FileProvider
import com.britetodo.notesforandroid.data.models.Page
import com.britetodo.notesforandroid.data.models.StickerItem
import com.britetodo.notesforandroid.data.models.Stroke
import com.britetodo.notesforandroid.data.models.TextNote
import com.britetodo.notesforandroid.data.repository.NotebookRepository
import com.britetodo.notesforandroid.drawing.DrawingEngine
import com.britetodo.notesforandroid.drawing.ToolSettings
import com.britetodo.notesforandroid.templates.TemplateColorTheme
import com.britetodo.notesforandroid.templates.TemplateRegistry
import com.britetodo.notesforandroid.templates.TemplateRenderer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Exports pages to PDF using Android's PdfDocument API.
 * Mirrors iOS PDFKit rendering in DrawingViewController.
 *
 * PDF page size: A4 portrait equivalent in 72dpi = 595×842
 * We render at 2048×2732 (like iOS) then scale.
 */
@Singleton
class PDFExporter @Inject constructor(
    @ApplicationContext private val context: Context,
    private val templateRenderer: TemplateRenderer,
    private val repository: NotebookRepository,
) {
    companion object {
        private const val PDF_WIDTH = 2048
        private const val PDF_HEIGHT = 2732
        // A4 at 72dpi
        private const val PAGE_WIDTH_PT = 595
        private const val PAGE_HEIGHT_PT = 842
    }

    suspend fun exportPage(
        page: Page,
        colorTheme: TemplateColorTheme = TemplateColorTheme.classic,
    ): Uri = withContext(Dispatchers.IO) {
        val template = TemplateRegistry.byId(page.templateId)
        val strokes = repository.loadDrawingStrokes(page.id)
        val textNotes = repository.loadTextNotes(page.id)
        val stickers = repository.loadStickers(page.id)

        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH_PT, PAGE_HEIGHT_PT, 1).create()
        val pdfPage = pdfDocument.startPage(pageInfo)
        val canvas = pdfPage.canvas

        // Scale to PDF points
        val scaleX = PAGE_WIDTH_PT.toFloat() / PDF_WIDTH
        val scaleY = PAGE_HEIGHT_PT.toFloat() / PDF_HEIGHT
        canvas.scale(scaleX, scaleY)

        // 1. Template background
        val templateBitmap = templateRenderer.renderToBitmap(template, PDF_WIDTH, PDF_HEIGHT, colorTheme)
        canvas.drawBitmap(templateBitmap, 0f, 0f, null)

        // 2. Draw strokes
        drawStrokesToCanvas(canvas, strokes)

        // 3. Draw text notes
        drawTextNotesToCanvas(canvas, textNotes)

        pdfDocument.finishPage(pdfPage)

        // Save PDF file
        val pdfDir = File(context.cacheDir, "pdfs").apply { mkdirs() }
        val pdfFile = File(pdfDir, "page_${page.id}.pdf")
        FileOutputStream(pdfFile).use { pdfDocument.writeTo(it) }
        pdfDocument.close()

        // Return content URI for sharing
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            pdfFile,
        )
    }

    suspend fun exportNotebook(
        notebookId: String,
        colorTheme: TemplateColorTheme = TemplateColorTheme.classic,
    ): Uri = withContext(Dispatchers.IO) {
        val pages = repository.getPages(notebookId)
        val pdfDocument = PdfDocument()

        pages.forEachIndexed { index, page ->
            val template = TemplateRegistry.byId(page.templateId)
            val strokes = repository.loadDrawingStrokes(page.id)
            val textNotes = repository.loadTextNotes(page.id)

            val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH_PT, PAGE_HEIGHT_PT, index + 1).create()
            val pdfPage = pdfDocument.startPage(pageInfo)
            val canvas = pdfPage.canvas

            val scaleX = PAGE_WIDTH_PT.toFloat() / PDF_WIDTH
            val scaleY = PAGE_HEIGHT_PT.toFloat() / PDF_HEIGHT
            canvas.scale(scaleX, scaleY)

            val templateBitmap = templateRenderer.renderToBitmap(template, PDF_WIDTH, PDF_HEIGHT, colorTheme)
            canvas.drawBitmap(templateBitmap, 0f, 0f, null)
            drawStrokesToCanvas(canvas, strokes)
            drawTextNotesToCanvas(canvas, textNotes)

            pdfDocument.finishPage(pdfPage)
        }

        val pdfDir = File(context.cacheDir, "pdfs").apply { mkdirs() }
        val pdfFile = File(pdfDir, "notebook_${notebookId}.pdf")
        FileOutputStream(pdfFile).use { pdfDocument.writeTo(it) }
        pdfDocument.close()

        FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            pdfFile,
        )
    }

    private fun drawStrokesToCanvas(canvas: Canvas, strokes: List<Stroke>) {
        strokes.forEach { stroke ->
            if (stroke.points.size < 2) return@forEach
            val paint = Paint().apply {
                color = stroke.color.toInt()
                strokeWidth = stroke.width
                strokeCap = Paint.Cap.ROUND
                strokeJoin = Paint.Join.ROUND
                isAntiAlias = true
                style = Paint.Style.STROKE
                alpha = (stroke.alpha * 255).toInt()
            }

            val path = android.graphics.Path()
            path.moveTo(stroke.points[0].x, stroke.points[0].y)
            for (i in 1 until stroke.points.size) {
                path.lineTo(stroke.points[i].x, stroke.points[i].y)
            }
            canvas.drawPath(path, paint)
        }
    }

    private fun drawTextNotesToCanvas(canvas: Canvas, notes: List<TextNote>) {
        notes.forEach { note ->
            val paint = Paint().apply {
                color = note.colorArgb.toInt()
                textSize = note.fontSize
                isAntiAlias = true
                isFakeBoldText = note.isBold
                isUnderlineText = note.isUnderlined
            }
            val x = note.xNorm * PDF_WIDTH
            val y = note.yNorm * PDF_HEIGHT
            note.text.split("\n").forEachIndexed { i, line ->
                canvas.drawText(line, x, y + i * note.fontSize * 1.4f, paint)
            }
        }
    }
}
