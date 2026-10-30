package com.christa.vp_week6.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CampusColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    primaryContainer = PrimaryLight,
    onPrimaryContainer = DeepBlue,
    secondaryContainer = PrimaryLight,
    onSecondaryContainer = DeepBlue,
    background = BackgroundGray,
    onBackground = TextDark,
    surface = Color.White,
    onSurface = TextDark,
    surfaceVariant = ChipGray,
    onSurfaceVariant = TextGray,
    outline = CardBorder,
    outlineVariant = CardBorder,
    error = DangerRed
)

@Composable
fun VP_Week6Theme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CampusColorScheme,
        typography = Typography,
        content = content
    )
}