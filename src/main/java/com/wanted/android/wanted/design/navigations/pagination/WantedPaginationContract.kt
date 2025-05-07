package com.wanted.android.wanted.design.navigations.pagination

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object WantedPaginationContract {
    /**
     * Dot indicator의 크기를 정의하는 enum 클래스입니다.
     *
     * - Small: 2~6dp 범위 크기로 dot을 구성합니다.
     * - Medium: 6~10dp 범위 크기로 dot을 구성합니다.
     */
    enum class WantedDotIndicatorSize {
        Small,
        Medium
    }

    /**
     * Dot indicator의 스타일을 정의하는 enum 클래스입니다.
     *
     * - Normal: 배경이 채워진 원형 스타일입니다.
     * - White: 흰색 테두리와 반투명 배경의 스타일입니다.
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
     * 페이지 카운터의 크기를 설정하는 enum 클래스입니다.
     *
     * 각 크기는 내부 패딩 및 항목 간 간격을 조절합니다.
     *
     * @property paddingHorizontal Dp: 좌우 패딩 값입니다.
     * @property paddingVertical Dp: 상하 패딩 값입니다.
     * @property space Dp: 항목 간 간격입니다.
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