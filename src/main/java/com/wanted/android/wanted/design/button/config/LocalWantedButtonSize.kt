package com.wanted.android.wanted.design.button.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.wanted.android.wanted.design.util.ButtonSize


val LocalWantedButtonSize = WantedChipButtonSizeCompositionLocal()

@JvmInline
value class WantedChipButtonSizeCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<ButtonSize> = staticCompositionLocalOf { ButtonSize.LARGE }
) {
    val current: ButtonSize
        @Composable get() = delegate.current

    infix fun provides(value: ButtonSize) = delegate provides value
}
