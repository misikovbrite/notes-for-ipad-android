package com.britetodo.notesforandroid.ui.screens.editor.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.britetodo.notesforandroid.templates.Template
import com.britetodo.notesforandroid.templates.TemplateColorTheme
import com.britetodo.notesforandroid.templates.TemplateRenderer
import dagger.hilt.android.EntryPointAccessors
import com.britetodo.notesforandroid.di.TemplateRendererEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun TemplateLayer(
    template: Template,
    colorTheme: TemplateColorTheme = TemplateColorTheme.classic,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(template.id, colorTheme.id) {
        bitmap = withContext(Dispatchers.IO) {
            try {
                val renderer = EntryPointAccessors
                    .fromApplication(context.applicationContext, TemplateRendererEntryPoint::class.java)
                    .templateRenderer()
                renderer.renderToBitmap(template, theme = colorTheme)
            } catch (e: Exception) {
                null
            }
        }
    }

    bitmap?.let { bmp ->
        Image(
            bitmap = bmp.asImageBitmap(),
            contentDescription = "Template: ${template.name}",
            modifier = modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
        )
    }
}
