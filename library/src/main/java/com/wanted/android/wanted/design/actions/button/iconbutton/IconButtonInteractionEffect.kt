package com.wanted.android.wanted.design.actions.button.iconbutton

import androidx.compose.ui.graphics.Color
import com.wanted.android.wanted.design.util.OPACITY_22

/**
 * enum class IconButtonInteractionEffect
 *
 * IconButton 의 인터랙션 피드백 방식을 정의합니다. (Figma 4.0.0 IconButton 스펙)
 *
 * 인터랙션 차단 여부(불리언)와 피드백 방식을 따로 다루던 두 속성을 하나로 합친 값입니다.
 * 값 이름은 Figma 4.0.0 컴포넌트 variant(Highlight / Dim / None) 및 iOS 와 동일하게 맞춥니다.
 *
 * - Highlight: 아이콘 뒤에 인터랙션 레이어(ripple)를 표시합니다. 기본값입니다.
 * - Dim: 레이어 대신 아이콘 투명도를 낮춥니다. Top Navigation 처럼 레이어 형태가 어색한 자리에 씁니다. Normal variant 에만 제공합니다.
 * - None: 인터랙션 피드백이 없습니다.
 *
 * 세 값 모두 클릭·터치 영역은 같습니다. 시각 피드백만 달라지고 Hit area = 박스 100% 규정은 그대로 유지됩니다.
 */
enum class IconButtonInteractionEffect {
    Highlight,
    Dim,
    None;

    /** 아이콘 뒤 인터랙션 레이어(ripple) 표시 여부입니다. */
    internal val showsIndication: Boolean get() = this == Highlight

    /** 눌림 상태에서 아이콘 투명도를 낮추는지 여부입니다. */
    internal val dimsIcon: Boolean get() = this == Dim

    internal companion object {
        /** deprecated 된 `disableInteraction` 파라미터를 InteractionEffect 로 변환합니다. */
        fun fromDisableInteraction(disableInteraction: Boolean): IconButtonInteractionEffect =
            if (disableInteraction) None else Highlight
    }
}

/** 인터랙션 피드백 색을 결정합니다. [interactionColor] 미지정 시 [Dim] 은 [tint] 를, 나머지는 [rippleFallback] 을 기준으로 합니다. */
internal fun IconButtonInteractionEffect.resolveInteractionColor(
    interactionColor: Color,
    tint: Color,
    rippleFallback: Color,
): Color = when {
    interactionColor != Color.Unspecified -> interactionColor
    dimsIcon -> tint
    else -> rippleFallback
}

/** Dim 눌림 상태의 아이콘 색입니다. 알파에 22%(Figma Pressed 레이어 불투명도)를 곱하며, Hover 가 없는 Android 는 Hovered(52%)를 구현하지 않습니다. */
internal fun Color.dimmedForPress(): Color = copy(alpha = alpha * OPACITY_22)
