package ru.alexsuvorov.paistewiki.core.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val PaisteRed = Color(0xFFE74C3C)
private val PaisteDarkRed = Color(0xFFC0392B)

private val DarkColorScheme = darkColorScheme(
    primary = PaisteRed,
    secondary = PaisteDarkRed,
    tertiary = Color.Gray
)

private val LightColorScheme = lightColorScheme(
    primary = PaisteRed,
    secondary = PaisteDarkRed,
    tertiary = Color.Gray,
    background = Color(0xFFECF0F1),
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun PaisteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}