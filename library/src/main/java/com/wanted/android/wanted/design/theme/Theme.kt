package com.wanted.android.wanted.design.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DesignSystemTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    appTheme: WantedColorScheme = AppWantedColorScheme,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalWantedTypography.provides(WantedTypography()),
        LocalWantedColorScheme provides appTheme,
        LocalWantedColorOpacityScheme provides AppWantedColorOpacityScheme,
        LocalWantedPrimitive provides WantedPrimitive(),
        LocalWantedSpacing provides WantedSpacing(),
        LocalWantedRadius provides WantedRadius(),
        LocalWantedDimension provides WantedDimension(),
        LocalOverscrollConfiguration provides null
    ) {
        MaterialTheme(
            colorScheme = LocalWantedColorScheme.getSystemColor(isDarkTheme),
            typography = pretendardTypography,
            shapes = OneIdShapes,
            content = content
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

    val colors: WantedColorScheme
        @Composable
        get() = LocalWantedColorScheme.current

    val colorsOpacity: WantedColorOpacityScheme
        @Composable
        get() = LocalWantedColorOpacityScheme.current

    val primitive: WantedPrimitive
        @Composable
        @ReadOnlyComposable
        get() = LocalWantedPrimitive.current

    val spacing: WantedSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalWantedSpacing.current

    val radius: WantedRadius
        @Composable
        @ReadOnlyComposable
        get() = LocalWantedRadius.current

    val dimension: WantedDimension
        @Composable
        @ReadOnlyComposable
        get() = LocalWantedDimension.current
}