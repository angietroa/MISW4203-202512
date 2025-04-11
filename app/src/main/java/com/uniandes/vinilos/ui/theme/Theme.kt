package com.uniandes.vinilos.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val ColorScheme = lightColorScheme(
    background = BlackBackground,
    primary = PrimaryColor,
    secondary = SecondaryColor,
    tertiary = TertiaryColor
)

@Composable
fun VinilosTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = content
    )
}
