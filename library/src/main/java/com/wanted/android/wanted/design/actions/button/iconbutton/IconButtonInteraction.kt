package com.wanted.android.wanted.design.actions.button.iconbutton

import androidx.compose.foundation.Indication
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.wanted.android.wanted.design.util.OPACITY_12
import com.wanted.android.wanted.design.util.OPACITY_20
import com.wanted.android.wanted.design.util.OPACITY_8

/** IconButton variant별 ripple 강도입니다. Light 0.08(normal·background·outlined) / Normal 0.12(background alternative) / Strong 0.20(solid). */
internal enum class IconButtonInteractionStrength {
    Light,
    Normal,
    Strong,
}

/** IconButton용 Indication을 반환합니다. [disabled] 이면 null 을 반환해 press/hover 효과를 끕니다. */
@Composable
internal fun iconButtonIndication(
    strength: IconButtonInteractionStrength,
    color: Color,
    disabled: Boolean,
): Indication? {
    if (disabled) return null
    return ripple(bounded = true, color = iconButtonRippleColor(strength, color))
}

/** IconButton ripple 색을 반환합니다. [strength] 에 해당하는 불투명도를 [color] 에 적용합니다. ripple 을 직접 만들지 않고 색만 넘겨야 하는 경로(`WantedTouchArea` 의 `rippleColor` 등)에서 [iconButtonIndication] 과 같은 값을 쓰기 위한 진입점입니다. */
internal fun iconButtonRippleColor(
    strength: IconButtonInteractionStrength,
    color: Color,
): Color {
    val alpha = when (strength) {
        IconButtonInteractionStrength.Light -> OPACITY_8
        IconButtonInteractionStrength.Normal -> OPACITY_12
        IconButtonInteractionStrength.Strong -> OPACITY_20
    }
    return color.copy(alpha = alpha)
}
