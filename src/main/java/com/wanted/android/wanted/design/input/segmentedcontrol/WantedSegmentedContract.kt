package com.wanted.android.wanted.design.input.segmentedcontrol

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.wanted.android.wanted.design.input.segmentedcontrol.WantedSegmentedContract.SegmentedSize

object WantedSegmentedContract {
    enum class SegmentedSize {
        Small,
        Medium,
        Large
    }
}

val LocalWantedSegmentedSize = WantedSegmentedSizeCompositionLocal()


@JvmInline
value class WantedSegmentedSizeCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<SegmentedSize> = staticCompositionLocalOf { SegmentedSize.Medium }
) {
    val current: SegmentedSize
        @Composable get() = delegate.current

    infix fun provides(value: SegmentedSize) = delegate provides value
}
