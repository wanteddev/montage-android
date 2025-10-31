package com.wanted.android.wanted.design.navigations.pagination

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * object WantedPaginationContract
 *
 * 페이지네이션 컴포넌트에서 사용하는 설정 값을 정의하는 객체입니다.
 */
object WantedPaginationContract {
    /**
     * enum class WantedDotIndicatorSize
     *
     * Dot Indicator의 크기를 정의하는 enum 클래스입니다.
     * Small, Medium 두 가지 크기가 존재합니다.
     */
    enum class WantedDotIndicatorSize {
        Small,
        Medium
    }

    /**
     * enum class WantedDotIndicatorType
     *
     * Dot Indicator의 스타일을 정의하는 enum 클래스입니다.
     * Normal, White 두 가지 스타일이 존재합니다.
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
     * 페이지 카운터의 크기를 정의하는 enum 클래스입니다.
     * Small, Normal 두 가지 크기가 존재합니다.
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