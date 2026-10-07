package com.wanted.android.wanted.design.actions.button.iconbutton

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * sealed class WantedIconButtonBackgroundSize
 *
 * `WantedIconButtonBackground` 의 박스(컨테이너) 크기입니다. (Confluence IconButton 스펙 §3·§4)
 *
 * background 는 string preset 을 두지 않고 단일 [Default] preset 으로 수렴하며,
 * 박스 커스텀은 [Custom] 으로만 지정합니다. 아이콘은 `box × 2/3` → dimension 토큰 스냅으로 자동 산출되고,
 * 박스는 `clamp(24dp, N, 64dp)` 범위로 클램프됩니다.
 *
 * - [Default] : box 32dp (기본값) → icon 20dp
 * - [Custom]  : 임의 박스 크기. `copy` 로 커스텀할 수 있습니다.
 */
sealed class WantedIconButtonBackgroundSize {
    abstract val boxSize: Dp

    data object Default : WantedIconButtonBackgroundSize() {
        override val boxSize = 32.dp
    }

    data class Custom(override val boxSize: Dp) : WantedIconButtonBackgroundSize()

    companion object {
        /** preset(데이터 오브젝트) 목록. */
        val presets: List<WantedIconButtonBackgroundSize> = listOf(Default)
    }
}
