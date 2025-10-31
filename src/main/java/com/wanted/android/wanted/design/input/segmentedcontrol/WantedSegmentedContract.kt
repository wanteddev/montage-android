package com.wanted.android.wanted.design.input.segmentedcontrol

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.wanted.android.wanted.design.input.segmentedcontrol.WantedSegmentedContract.SegmentedSize

/**
 * object WantedSegmentedContract
 *
 * SegmentedControl 컴포넌트에서 사용하는 설정 값을 정의하는 객체입니다.
 */
object WantedSegmentedContract {

    /**
     * enum class SegmentedSize
     *
     * SegmentedControl의 크기를 정의하는 enum 클래스입니다.
     * Small, Medium, Large 세 가지 크기가 존재합니다.
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
