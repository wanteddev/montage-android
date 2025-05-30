package com.wanted.android.wanted.design.input.segmentedcontrol

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.wanted.android.wanted.design.input.segmentedcontrol.WantedSegmentedContract.SegmentedSize

/**
 * object WantedSegmentedContract
 *
 * SegmentedControl UI 구성 요소에 사용되는 설정 값을 정의하는 객체입니다.
 *
 * 세그먼트 크기를 정의하는 enum 클래스 `SegmentedSize`를 포함하고 있습니다.
 */
object WantedSegmentedContract {

    /**
     * enum class SegmentedSize
     *
     * SegmentedControl의 크기를 정의하는 enum 클래스입니다.
     *
     * 사용되는 UI 환경에 따라 다음과 같은 크기를 제공합니다:
     * - Small: 작은 크기
     * - Medium: 중간 크기
     * - Large: 큰 크기
     */
    enum class SegmentedSize {
        Small,
        Medium,
        Large
    }
}

/**
 * LocalWantedSegmentedSize
 *
 * SegmentedControl 내에서 사용할 수 있는 전역 CompositionLocal 변수입니다.
 *
 * 기본적으로 `SegmentedSize.Medium`을 제공합니다.
 */
val LocalWantedSegmentedSize = WantedSegmentedSizeCompositionLocal()


@JvmInline
value class WantedSegmentedSizeCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<SegmentedSize> = staticCompositionLocalOf { SegmentedSize.Medium }
) {
    val current: SegmentedSize
        @Composable get() = delegate.current

    infix fun provides(value: SegmentedSize) = delegate provides value
}
