package com.wanted.android.wanted.design.actions.chip.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.wanted.android.wanted.design.actions.chip.WantedActionContract


val LocalWantedChipSize = WantedChipSizeCompositionLocal()

@JvmInline
value class WantedChipSizeCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedActionContract.ChipActionSize> = staticCompositionLocalOf { WantedActionContract.ChipActionSize.Medium }
) {
    val current: WantedActionContract.ChipActionSize
        @Composable get() = delegate.current

    infix fun provides(value: WantedActionContract.ChipActionSize) = delegate provides value
}
