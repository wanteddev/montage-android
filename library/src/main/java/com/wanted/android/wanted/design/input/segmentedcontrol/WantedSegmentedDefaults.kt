package com.wanted.android.wanted.design.input.segmentedcontrol

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.wanted.android.wanted.design.input.segmentedcontrol.WantedSegmentedDefaults.SegmentedSize

/**
 * object WantedSegmentedDefaults
 *
 * SegmentedControl 컴포넌트에서 사용하는 설정값을 정의하는 객체입니다.
 */
object WantedSegmentedDefaults {

    /**
     * enum class SegmentedSize
     *
     * SegmentedControl 의 크기를 정의하는 enum 클래스입니다.
     * - Small: 작은 사이즈입니다.
     * - Medium: 중간 사이즈입니다.
     * - Large: 큰 사이즈입니다.
     */
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
