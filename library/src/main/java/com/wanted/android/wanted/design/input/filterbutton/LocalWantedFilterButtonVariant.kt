package com.wanted.android.wanted.design.input.filterbutton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf


val LocalWantedFilterButtonVariant = WantedFilterButtonVariantCompositionLocal()

@JvmInline
value class WantedFilterButtonVariantCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedFilterButtonContract.FilterButtonVariant> = staticCompositionLocalOf { WantedFilterButtonContract.FilterButtonVariant.Solid }
) {
    val current: WantedFilterButtonContract.FilterButtonVariant
        @Composable get() = delegate.current

    infix fun provides(value: WantedFilterButtonContract.FilterButtonVariant) = delegate provides value
}
