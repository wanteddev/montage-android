package com.wanted.android.wanted.design.button.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.wanted.android.wanted.design.util.ButtonType


val LocalWantedButtonType = WantedChipButtonTypeCompositionLocal()

@JvmInline
value class WantedChipButtonTypeCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<ButtonType> = staticCompositionLocalOf { ButtonType.PRIMARY }
) {
    val current: ButtonType
        @Composable get() = delegate.current

    infix fun provides(value: ButtonType) = delegate provides value
}
