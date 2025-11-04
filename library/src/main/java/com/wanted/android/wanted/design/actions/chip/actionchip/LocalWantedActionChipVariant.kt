package com.wanted.android.wanted.design.actions.chip.actionchip

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf


val LocalWantedActionChipVariant = WantedActionChipVariantCompositionLocal()

@JvmInline
value class WantedActionChipVariantCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedActionChipContract.ActionChipVariant> = staticCompositionLocalOf { WantedActionChipContract.ActionChipVariant.Solid }
) {
    val current: WantedActionChipContract.ActionChipVariant
        @Composable get() = delegate.current

    infix fun provides(value: WantedActionChipContract.ActionChipVariant) = delegate provides value
}
