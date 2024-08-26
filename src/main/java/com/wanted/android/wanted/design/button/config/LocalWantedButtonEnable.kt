package com.wanted.android.wanted.design.button.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf


val LocalWantedButtonEnable = WantedChipButtonEnableCompositionLocal()

@JvmInline
value class WantedChipButtonEnableCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<Boolean> = staticCompositionLocalOf { true }
) {
    val current: Boolean
        @Composable get() = delegate.current

    infix fun provides(value: Boolean) = delegate provides value
}
