package com.wanted.android.wanted.design.navigations.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal val LocalCategoryGradationColor = WantedCategoryGradationColorCompositionLocal()


@JvmInline
value class WantedCategoryGradationColorCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<Color> = staticCompositionLocalOf { Color.Transparent }
) {
    val current: Color
        @Composable get() = delegate.current

    infix fun provides(value: Color) = delegate provides value
}
