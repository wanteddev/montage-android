package com.wanted.android.wanted.design.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DesignSystemTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    setStatusBarColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val colors = getDesignSystemColor()
    val colorPalette = remember { colors }
    colorPalette.update(colors)

    if (setStatusBarColor) {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setStatusBarColor(
                color = colors.backgroundNormalNormal,
                darkIcons = !isDarkTheme
            )
        }
    }

    CompositionLocalProvider(
        LocalWantedTypography.provides(WantedTypography()),
        LocalWantedColor.provides(colorPalette),
        LocalOverscrollConfiguration provides null

    ) {
        MaterialTheme(
            colors = colorPalette.getColor(isDarkTheme),
            shapes = OneIdShapes,
            content = content,
        )
    }
}

object DesignSystemTheme {
    val typography: WantedTypography
        @Composable
        get() = LocalWantedTypography.current
}