package com.britetodo.notesforandroid.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4A90E2),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD4E8FF),
    secondary = Color(0xFF6C757D),
    surface = Color.White,
    background = Color(0xFFF8F9FA),
    onBackground = Color(0xFF212121),
    onSurface = Color(0xFF212121),
    outline = Color(0xFFCCCCCC),
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF6AABFF),
    onPrimary = Color(0xFF003063),
    primaryContainer = Color(0xFF004794),
    secondary = Color(0xFFADB5BD),
    surface = Color(0xFF1A1A2E),
    background = Color(0xFF121212),
    onBackground = Color(0xFFE0E0E0),
    onSurface = Color(0xFFE0E0E0),
    outline = Color(0xFF2D2D44),
)

@Composable
fun NotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
