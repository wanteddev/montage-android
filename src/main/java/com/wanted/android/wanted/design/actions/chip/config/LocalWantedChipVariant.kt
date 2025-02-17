package com.wanted.android.wanted.design.actions.chip.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.wanted.android.wanted.design.actions.chip.WantedActionContract


val LocalWantedChipVariant = WantedChipVariantCompositionLocal()

@JvmInline
value class WantedChipVariantCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedActionContract.ChipActionVariant> = staticCompositionLocalOf { WantedActionContract.ChipActionVariant.FILLED }
) {
    val current: WantedActionContract.ChipActionVariant
        @Composable get() = delegate.current

    infix fun provides(value: WantedActionContract.ChipActionVariant) = delegate provides value
}
