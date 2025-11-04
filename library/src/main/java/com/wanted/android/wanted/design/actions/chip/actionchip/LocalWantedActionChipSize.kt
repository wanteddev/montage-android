package com.wanted.android.wanted.design.actions.chip.actionchip

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf


val LocalWantedActionChipSize = WantedActionChipSizeCompositionLocal()

@JvmInline
value class WantedActionChipSizeCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedActionChipContract.ActionChipSize> = staticCompositionLocalOf { WantedActionChipContract.ActionChipSize.Medium }
) {
    val current: WantedActionChipContract.ActionChipSize
        @Composable get() = delegate.current

    infix fun provides(value: WantedActionChipContract.ActionChipSize) = delegate provides value
}
