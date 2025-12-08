package com.wanted.android.wanted.design.input.filterbutton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf


val LocalWantedFilterButtonActive = WantedFilterButtonActiveCompositionLocal()

@JvmInline
value class WantedFilterButtonActiveCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<Boolean> = staticCompositionLocalOf { false }
) {
    val current: Boolean
        @Composable get() = delegate.current

    infix fun provides(value: Boolean) = delegate provides value
}
