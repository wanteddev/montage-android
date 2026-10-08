package com.wanted.android.wanted.design.input.segmentedcontrol

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.input.segmentedcontrol.WantedSegmentedDefaults.ContainerPadding
import com.wanted.android.wanted.design.input.segmentedcontrol.WantedSegmentedDefaults.SegmentedSize
import com.wanted.android.wanted.design.theme.DesignSystemTheme

/**
 * object WantedSegmentedDefaults
 *
 * SegmentedControl 컴포넌트에서 사용하는 설정값을 정의하는 객체입니다.
 */
object WantedSegmentedDefaults {

    /** 컨트롤(트랙) 내부 여백입니다. 모든 사이즈 공통값입니다. */
    val ContainerPadding: Dp = 4.dp

    /**
     * enum class SegmentedSize
     *
     * SegmentedControl 의 크기를 정의하는 enum 클래스입니다.
     * 각 사이즈는 Figma 스펙에 맞춘 고정 높이·라운딩·패딩·아이콘 크기 값을 가집니다.
     *
     * @param height 항목의 기준 높이입니다(=트랙 전체 높이 − [ContainerPadding]×2). 항목은 이 값을 최소 높이(defaultMinSize)로 사용하며, 시스템 폰트 확대 시 그 이상으로 늘어납니다.
     * @param containerRadius 컨트롤(트랙) 모서리 반경입니다.
     * @param knobRadius 선택 강조 Knob 의 모서리 반경입니다.
     * @param horizontalPadding text / icon+text 세그먼트의 좌우 여백입니다.
     * @param verticalPadding text / icon+text 세그먼트의 상하 여백입니다.
     * @param iconTextGap 아이콘과 텍스트 사이 간격입니다.
     * @param iconSize icon + text 세그먼트의 아이콘 크기입니다.
     * @param iconOnlyIconSize Icon Only 세그먼트의 아이콘 크기입니다. icon + text 보다 한 단계 큽니다.
     * @param iconOnlyWidth Icon Only 세그먼트의 고정 너비입니다. 높이([height])보다 2dp 넓습니다.
     */
    enum class SegmentedSize(
        val height: Dp,
        val containerRadius: Dp,
        val knobRadius: Dp,
        val horizontalPadding: Dp,
        val verticalPadding: Dp,
        val iconTextGap: Dp,
        val iconSize: Dp,
        val iconOnlyIconSize: Dp,
        val iconOnlyWidth: Dp,
    ) {
        Small(
            height = 24.dp,
            containerRadius = 10.dp,
            knobRadius = 8.dp,
            horizontalPadding = 6.dp,
            verticalPadding = 4.dp,
            iconTextGap = 4.dp,
            iconSize = 14.dp,
            iconOnlyIconSize = 16.dp,
            iconOnlyWidth = 26.dp,
        ),
        Medium(
            height = 32.dp,
            containerRadius = 12.dp,
            knobRadius = 8.dp,
            horizontalPadding = 8.dp,
            verticalPadding = 6.dp,
            iconTextGap = 6.dp,
            iconSize = 16.dp,
            iconOnlyIconSize = 18.dp,
            iconOnlyWidth = 34.dp,
        ),
        Large(
            height = 40.dp,
            containerRadius = 14.dp,
            knobRadius = 10.dp,
            horizontalPadding = 8.dp,
            verticalPadding = 9.dp,
            iconTextGap = 6.dp,
            iconSize = 18.dp,
            iconOnlyIconSize = 20.dp,
            iconOnlyWidth = 42.dp,
        ),
    }
}

// SegmentedSize 별 텍스트 스타일입니다.
// - Small: caption1Medium (12sp)
// - Medium: label1Medium (14sp)
// - Large: body2Medium (15sp)
val SegmentedSize.textStyle: TextStyle
    @Composable get() = when (this) {
        SegmentedSize.Small -> DesignSystemTheme.typography.caption1Medium
        SegmentedSize.Medium -> DesignSystemTheme.typography.label1Medium
        SegmentedSize.Large -> DesignSystemTheme.typography.body2Medium
    }

val LocalWantedSegmentedSize = WantedSegmentedSizeCompositionLocal()

// 아이콘 전용(Icon Only) 모드 여부를 하위 항목에 전달하는 CompositionLocal 입니다.
// 컨트롤([WantedSegmentedControl]) 이 `iconOnly` 값을 provide 하며,
// [WantedSegmentedControlItem] 이 이를 읽어 렌더링 방식(텍스트 vs 아이콘 전용)을 결정합니다.
val LocalWantedSegmentedIconOnly = staticCompositionLocalOf { false }


@JvmInline
value class WantedSegmentedSizeCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<SegmentedSize> = staticCompositionLocalOf { SegmentedSize.Medium }
) {
    val current: SegmentedSize
        @Composable get() = delegate.current

    infix fun provides(value: SegmentedSize) = delegate provides value
}
