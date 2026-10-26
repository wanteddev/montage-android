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
     * - Text: 임의의 문자열(숫자·"N" 등)을 그대로 표시합니다.
     * - MaxCount: 개수를 표시하되 maxCount 초과 시 "maxCount+"(예: 99+)로 표기합니다.
     */
    enum class PushBadgeVariant {
        Dot,
        Text,
        MaxCount
    }

    /**
     * enum class PushBadgeSize
     *
     * Push 배지의 크기를 정의합니다.
     *
     * - XSmall: 가장 작은 배지입니다(점 4dp, 텍스트 caption2Bold).
     * - Small: 중간 크기 배지입니다(점 6dp, 텍스트는 XSmall 과 같은 caption2Bold 이고 여백만 큽니다).
     * - Medium: 가장 큰 배지입니다(점 8dp, 텍스트 label1Bold).
     */
    enum class PushBadgeSize {
        XSmall,
        Small,
        Medium
    }

    /**
     * enum class PushBadgePosition
     *
     * Push 배지가 놓인 자리에서 어느 방향으로 걸쳐 나갈지 정의하는 열거형 클래스입니다.
     *
     * 대상의 해당 모서리로 옮기지는 않습니다. 대상 모서리에 붙이려면 배지 modifier 에 같은 방향의 정렬(예: Modifier.align(Alignment.TopEnd))을 함께 지정합니다.
     *
     * - TopStart: 배지를 자기 크기의 절반만큼 위·시작 방향으로 걸쳐 나가게 합니다.
     * - TopCenter: 배지를 자기 크기의 절반만큼 위 방향으로 걸쳐 나가게 합니다.
     * - TopEnd: 배지를 자기 크기의 절반만큼 위·끝 방향으로 걸쳐 나가게 합니다.
     * - MiddleStart: 배지를 자기 크기의 절반만큼 시작 방향으로 걸쳐 나가게 합니다.
     * - MiddleCenter: 배지를 놓인 자리 그대로 둡니다(걸쳐 나가지 않음).
     * - MiddleEnd: 배지를 자기 크기의 절반만큼 끝 방향으로 걸쳐 나가게 합니다.
     * - BottomStart: 배지를 자기 크기의 절반만큼 아래·시작 방향으로 걸쳐 나가게 합니다.
     * - BottomCenter: 배지를 자기 크기의 절반만큼 아래 방향으로 걸쳐 나가게 합니다.
     * - BottomEnd: 배지를 자기 크기의 절반만큼 아래·끝 방향으로 걸쳐 나가게 합니다.
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