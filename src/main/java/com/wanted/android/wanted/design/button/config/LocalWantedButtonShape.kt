package com.wanted.android.wanted.design.button.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.wanted.android.wanted.design.util.ButtonShape


val LocalWantedButtonShape = WantedChipButtonShapeCompositionLocal()

@JvmInline
value class WantedChipButtonShapeCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<ButtonShape> = staticCompositionLocalOf { ButtonShape.SOLID }
) {
    val current: ButtonShape
        @Composable get() = delegate.current

    infix fun provides(value: ButtonShape) = delegate provides value
}
