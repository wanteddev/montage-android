package com.wanted.android.wanted.design.actions.button.iconbutton

import androidx.compose.ui.Alignment

/**
 * enum class IconButtonBadgePosition
 *
 * IconButton 위에 표시되는 PushBadge 의 정렬 기준점을 정의합니다.
 *
 * - TopLeft: 좌측 상단입니다.
 * - TopCenter: 상단 중앙입니다.
 * - TopRight: 우측 상단입니다.
 * - MiddleLeft: 좌측 중앙입니다.
 * - MiddleCenter: 정중앙입니다.
 * - MiddleRight: 우측 중앙입니다.
 * - BottomLeft: 좌측 하단입니다.
 * - BottomCenter: 하단 중앙입니다.
 * - BottomRight: 우측 하단입니다.
 *
 * 정렬 기준이 되는 모서리는 variant 마다 다릅니다.
 * Normal variant 는 아이콘 모서리 기준으로 정렬되며 `(boxSize - iconSize) / 2` 만큼 inset 보정이 적용되고,
 * Background / Outlined / Solid variant 는 박스 모서리 기준으로 정렬되며 inset 보정이 없습니다.
 */
enum class IconButtonBadgePosition {
    TopLeft,
    TopCenter,
    TopRight,
    MiddleLeft,
    MiddleCenter,
    MiddleRight,
    BottomLeft,
    BottomCenter,
    BottomRight;

    internal fun toAlignment(): Alignment = when (this) {
        TopLeft -> Alignment.TopStart
        TopCenter -> Alignment.TopCenter
        TopRight -> Alignment.TopEnd
        MiddleLeft -> Alignment.CenterStart
        MiddleCenter -> Alignment.Center
        MiddleRight -> Alignment.CenterEnd
        BottomLeft -> Alignment.BottomStart
        BottomCenter -> Alignment.BottomCenter
        BottomRight -> Alignment.BottomEnd
    }

    // inset 보정 방향: +1 = 안쪽(아래/오른쪽), -1 = 안쪽(위/왼쪽), 0 = 보정 없음
    internal val verticalInsetSign: Int get() = when (this) {
        TopLeft, TopCenter, TopRight -> 1
        MiddleLeft, MiddleCenter, MiddleRight -> 0
        BottomLeft, BottomCenter, BottomRight -> -1
    }

    internal val horizontalInsetSign: Int get() = when (this) {
        TopLeft, MiddleLeft, BottomLeft -> 1
        TopCenter, MiddleCenter, BottomCenter -> 0
        TopRight, MiddleRight, BottomRight -> -1
    }
}
