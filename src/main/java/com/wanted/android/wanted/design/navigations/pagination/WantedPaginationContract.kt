package com.wanted.android.wanted.design.navigations.pagination

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * object WantedPaginationContract
 *
 * 페이지네이션 컴포넌트에서 사용되는 설정 값을 정의하는 객체입니다.
 *
 * Dot indicator, Page counter와 관련된 시각적 스타일과 크기를 제어할 수 있는 enum 클래스를 포함합니다.
 */
object WantedPaginationContract {
    /**
     * enum class WantedDotIndicatorSize
     *
     * 페이지네이션의 Dot Indicator 크기를 정의하는 enum 클래스입니다.
     *
     * 포함된 크기 옵션:
     * - Small: 2~6dp 범위
     * - Medium: 6~10dp 범위
     */
    enum class WantedDotIndicatorSize {
        Small,
        Medium
    }

    /**
     * enum class WantedDotIndicatorType
     *
     * Dot Indicator의 시각적 스타일을 정의하는 enum 클래스입니다.
     *
     * 포함된 스타일 옵션:
     * - Normal: 배경이 채워진 원형 스타일
     * - White: 흰색 테두리와 반투명 배경 스타일
     */
    enum class WantedDotIndicatorType {
        Normal,
        White
    }

    internal enum class IndicatorDotSize {
        Max,
        Mid,
        Min,
        Zero
    }

    /**
     * enum class WantedPageCounterSize
     *
     * 페이지 카운터 UI의 크기를 정의하는 enum 클래스입니다.
     *
     * 각 크기는 내부 패딩 및 항목 간 간격을 조정합니다.
     *
     * 포함된 크기 옵션:
     * - Small: 좌우 10dp, 상하 4dp, 간격 3dp
     * - Normal: 좌우 12dp, 상하 6dp, 간격 4dp
     *
     * @property paddingHorizontal `Dp`: 좌우 패딩 값입니다.
     * @property paddingVertical `Dp`: 상하 패딩 값입니다.
     * @property space `Dp`: 항목 간 간격입니다.
     */
    enum class WantedPageCounterSize(
        val paddingHorizontal: Dp,
        val paddingVertical: Dp,
        val space: Dp,
    ) {
        Small(paddingHorizontal = 10.dp, paddingVertical = 4.dp, space = 3.dp),
        Normal(paddingHorizontal = 12.dp, paddingVertical = 6.dp, space = 4.dp)
    }
}