package com.wanted.android.wanted.design.actions.chip

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf


val LocalWantedChipEnable = WantedChipBackgroundCompositionLocal()

@JvmInline
value class WantedChipBackgroundCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<Boolean> = staticCompositionLocalOf { true }
) {
    val current: Boolean
        @Composable get() = delegate.current

    infix fun provides(value: Boolean) = delegate provides value
}
