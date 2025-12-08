package com.wanted.android.wanted.design.actions.chip

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf


val LocalWantedChipVariant = WantedChipVariantCompositionLocal()

@JvmInline
value class WantedChipVariantCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedChipContract.ChipVariant> = staticCompositionLocalOf { WantedChipContract.ChipVariant.Solid }
) {
    val current: WantedChipContract.ChipVariant
        @Composable get() = delegate.current

    infix fun provides(value: WantedChipContract.ChipVariant) = delegate provides value
}
