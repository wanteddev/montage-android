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
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.WantedTextStyle


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


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DesignSystemPickerTheme(
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
            typography = MaterialTheme.typography.copy(
                // 오전 / 오후 text
                titleMedium = WantedTextStyle(
                    colorRes = R.color.label_normal,
                    style = DesignSystemTheme.typography.label2Bold
                ),
                // 시간 선택기의 숫자
                displayLarge = WantedTextStyle(
                    colorRes = R.color.label_alternative,
                    style = DesignSystemTheme.typography.label2Bold
                ),
                // 시계 숫자
                bodyLarge = WantedTextStyle(
                    colorRes = R.color.label_alternative,
                    style = DesignSystemTheme.typography.label2Bold
                ),
            ),
            content = content,
        )
    }
}