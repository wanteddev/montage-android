package com.wanted.android.wanted.design.feedback.pushbadge

class PushBadgeContract {
    /**
     * enum class PushBadgeVariant
     *
     * Push 뱃지의 표시 유형을 정의합니다.
     *
     * Dot - 작은 점 형태의 뱃지를 표시합니다.
     * Number - 숫자 형태로 개수를 표시합니다.
     * New - "N" 텍스트를 통해 새로운 항목을 표시합니다.
     */
    enum class PushBadgeVariant {
        Dot,
        Number,
        New
    }

    /**
     * enum class PushBadgeSize
     *
     * Push 뱃지의 크기를 정의합니다.
     *
     * XSmall - 가장 작은 크기로 텍스트가 작게 표시됩니다.
     * Small - 중간 크기의 뱃지입니다.
     * Medium - 가장 큰 뱃지로 강조 표시 시 적합합니다.
     */
    enum class PushBadgeSize {
        XSmall,
        Small,
        Medium
    }

    /**
     * enum class PushBadgePosition
     *
     * Push 뱃지를 배치할 위치를 정의합니다.
     *
     * TopStart, TopCenter, TopEnd - 상단의 좌측/중앙/우측 위치
     * MiddleStart, MiddleCenter, MiddleEnd - 중앙의 좌측/중앙/우측 위치
     * BottomStart, BottomCenter, BottomEnd - 하단의 좌측/중앙/우측 위치
     */
    enum class PushBadgePosition {
        TopStart,
        TopCenter,
        TopEnd,
        MiddleStart,
        MiddleCenter,
        MiddleEnd,
        BottomStart,
        BottomCenter,
        BottomEnd
    }
}