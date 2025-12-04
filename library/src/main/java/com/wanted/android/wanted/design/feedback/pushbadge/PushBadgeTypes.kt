package com.wanted.android.wanted.design.feedback.pushbadge

/**
 * object PushBadgeTypes
 * Push badge Type 클래스입니다.
 *
 * 이 클래스는 Push badge의 표시 유형, 크기, 위치를 정의하는 열거형 클래스들을 포함합니다.
 */
object PushBadgeTypes {
    /**
     * enum class PushBadgeVariant
     *
     * Push 배지의 표시 유형을 정의합니다.
     *
     * - Dot: 작은 점 형태의 배지를 표시합니다.
     * - Number: 숫자 형태로 개수를 표시합니다.
     * - New: "N" 텍스트를 통해 새로운 항목을 표시합니다.
     */
    enum class PushBadgeVariant {
        Dot,
        Number,
        New
    }

    /**
     * enum class PushBadgeSize
     *
     * Push 배지의 크기를 정의합니다.
     *
     * - XSmall: 가장 작은 크기로 텍스트가 작게 표시됩니다.
     * - Small: 중간 크기의 배지입니다.
     * - Medium: 가장 큰 배지로 강조 표시 시 적합합니다.
     */
    enum class PushBadgeSize {
        XSmall,
        Small,
        Medium
    }

    /**
     * enum class PushBadgePosition
     *
     * Push 배지를 배치할 위치를 정의하는 열거형 클래스입니다.
     *
     * - TopStart: 상단의 좌측 위치입니다.
     * - TopCenter: 상단의 중앙 위치입니다.
     * - TopEnd: 상단의 우측 위치입니다.
     * - MiddleStart: 중앙의 좌측 위치입니다.
     * - MiddleCenter: 중앙의 중앙 위치입니다.
     * - MiddleEnd: 중앙의 우측 위치입니다.
     * - BottomStart: 하단의 좌측 위치입니다.
     * - BottomCenter: 하단의 중앙 위치입니다.
     * - BottomEnd: 하단의 우측 위치입니다.
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