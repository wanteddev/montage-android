package com.wanted.android.wanted.design.actions.chip

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf


val LocalWantedChipSize = WantedChipSizeCompositionLocal()

@JvmInline
value class WantedChipSizeCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedChipContract.ChipSize> = staticCompositionLocalOf { WantedChipContract.ChipSize.Medium }
) {
    val current: WantedChipContract.ChipSize
        @Composable get() = delegate.current

    infix fun provides(value: WantedChipContract.ChipSize) = delegate provides value
}
