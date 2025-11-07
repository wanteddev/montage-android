package com.wanted.android.wanted.design.actions.chip.filterchip

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.wanted.android.wanted.design.actions.chip.actionchip.WantedActionChipContract


val LocalWantedFilterChipVariant = WantedFilterChipVariantCompositionLocal()

@JvmInline
value class WantedFilterChipVariantCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedFilterChipContract.FilterChipVariant> = staticCompositionLocalOf { WantedFilterChipContract.FilterChipVariant.Solid }
) {
    val current: WantedFilterChipContract.FilterChipVariant
        @Composable get() = delegate.current

    infix fun provides(value: WantedFilterChipContract.FilterChipVariant) = delegate provides value
}
