package com.example.ejemplo.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF1DB954),      // Verde Spotifake
    onPrimary = Color.White,
    primaryContainer = Color(0xFF282828), // Gris oscuro para contenedores
    onPrimaryContainer = Color.White,
    background = Color(0xFF121212),   // Casi negro
    onBackground = Color.White,
    surface = Color(0xFF181818),      // Gris superficie
    onSurface = Color.White,
    surfaceVariant = Color(0xFF2A2A28),
    onSurfaceVariant = Color(0xFFB3B3B3) // Gris claro para texto secundario
)

@Composable
fun SpotifakeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}
