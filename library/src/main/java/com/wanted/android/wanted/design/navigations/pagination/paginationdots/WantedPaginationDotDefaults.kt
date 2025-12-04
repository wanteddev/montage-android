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
     * - Small: 작은 크기의 dot입니다.
     * - Medium: 중간 크기의 dot입니다.
     */
    enum class WantedDotIndicatorSize {
        Small,
        Medium
    }

    /**
     * enum class WantedDotIndicatorType
     *
     * Dot Indicator의 색상을 정의하는 enum 클래스입니다.
     * - Normal: 일반 스타일의 dot입니다.
     * - White: 흰색 스타일의 dot입니다.
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