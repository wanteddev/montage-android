package com.wanted.android.wanted.design.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DesignSystemTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = getDesignSystemColor()
    val colorPalette = remember { colors }
    colorPalette.update(colors)

    CompositionLocalProvider(
        LocalWantedTypography.provides(WantedTypography()),
        LocalWantedColor.provides(colorPalette),
        LocalOverscrollConfiguration provides null

    ) {
        MaterialTheme(
            colorScheme = colorPalette.getColor(isDarkTheme),
            shapes = OneIdShapes,
            content = content,
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DesignSystemBottomSheetTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = getDesignSystemColor()
    val colorPalette = remember { colors }
    colorPalette.update(colors)

    CompositionLocalProvider(
        LocalWantedTypography.provides(WantedTypography()),
        LocalWantedColor.provides(colorPalette),
        LocalOverscrollConfiguration provides null

    ) {
        MaterialTheme(
            colorScheme = colorPalette.getColor(isDarkTheme),
            shapes = OneIdShapes,
            content = content,
        )
    }
}

object DesignSystemTheme {
    val typography: WantedTypography
        @Composable
        get() = LocalWantedTypography.current

    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.shapes

}