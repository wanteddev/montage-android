package com.wanted.android.wanted.design.navigations.pagination.paginationdots

/**
 * object WantedPaginationDotDefaults
 *
 * Pagination 컴포넌트에서 사용하는 설정값을 정의하는 객체입니다.
 */
object WantedPaginationDotDefaults {
    /**
     * enum class WantedDotIndicatorSize
     *
     * Dot Indicator 의 크기를 정의하는 enum 클래스입니다.
     * - Small: dot 크기 6dp(표시 구간 가장자리 쪽은 4·2dp 로 줄어듦)입니다.
     * - Medium: dot 크기 10dp(표시 구간 가장자리 쪽은 8·6dp 로 줄어듦)입니다.
     */
    enum class WantedDotIndicatorSize {
        Small,
        Medium
    }

    /**
     * enum class WantedDotIndicatorType
     *
     * Dot Indicator의 색상을 정의하는 enum 클래스입니다.
     * - Normal: foregroundNeutralPrimary 색 dot 이며, dot 간격은 10dp 입니다.
     * - White: 이미지 위에 쓰는 흰색 dot 이며, 1dp 테두리가 있고 dot 간격은 6dp 입니다.
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
}