package com.wanted.android.wanted.design.actions.chip.filterchip

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.wanted.android.wanted.design.actions.chip.actionchip.WantedActionChipContract


val LocalWantedFilterChipSize = WantedFilterChipSizeCompositionLocal()

@JvmInline
value class WantedFilterChipSizeCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedFilterChipContract.FilterChipSize> = staticCompositionLocalOf { WantedFilterChipContract.FilterChipSize.Medium }
) {
    val current: WantedFilterChipContract.FilterChipSize
        @Composable get() = delegate.current

    infix fun provides(value: WantedFilterChipContract.FilterChipSize) = delegate provides value
}
