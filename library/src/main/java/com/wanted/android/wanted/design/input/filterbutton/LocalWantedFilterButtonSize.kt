package com.wanted.android.wanted.design.input.filterbutton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.wanted.android.wanted.design.input.filterbutton.WantedFilterButtonContract.FilterButtonSize


val LocalWantedFilterButtonSize = WantedFilterButtonSizeCompositionLocal()

@JvmInline
value class WantedFilterButtonSizeCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<FilterButtonSize> = staticCompositionLocalOf { FilterButtonSize.Medium }
) {
    val current: FilterButtonSize
        @Composable get() = delegate.current

    infix fun provides(value: FilterButtonSize) = delegate provides value
}
