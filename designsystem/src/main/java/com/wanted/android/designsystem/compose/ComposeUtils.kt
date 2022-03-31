package com.wanted.android.designsystem.compose


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.wanted.android.designsystem.R

private val LightColorPalette = lightColors(
    primary = Color.White,
    background = Color.White,
    secondary = Color(0xFF3366ff),
)

private val DarkColorPalette = darkColors(
    primary = Color(0xFF2B2B2B),
    background = Color(0xFF2B2B2B),
    surface = Color(0xFF2B2B2B),
    secondary = Color(0xFF4d7dff),
)

val pretendardFontFamily = FontFamily(
    Font(R.font.pretendard_regular),
)


@Composable
fun WantedTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        content = content,
        typography = Typography(
            defaultFontFamily = pretendardFontFamily,
        )
    )
}
