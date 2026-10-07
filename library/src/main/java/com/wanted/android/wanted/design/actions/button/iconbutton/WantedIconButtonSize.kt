package com.wanted.android.wanted.design.actions.button.iconbutton

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * sealed class WantedIconButtonSize
 *
 * `WantedIconButtonOutlined` / `WantedIconButtonSolid` 의 박스(컨테이너) 크기입니다.
 * (Confluence IconButton 스펙 §3·§4)
 *
 * `size` 는 박스 크기를 뜻하며, 아이콘은 `box × 0.47` → dimension 토큰 스냅(동률 → 작은 값)으로 자동 산출됩니다.
 * radius 는 full(CircleShape), 박스는 `clamp(24dp, N, 64dp)` 범위로 클램프됩니다.
 *
 * - [Medium] : box 40dp (기본값) → icon 18dp
 * - [Small]  : box 32dp → icon 16dp
 * - [Custom] : 임의 박스 크기. `copy` 로 커스텀할 수 있습니다.
 */
sealed class WantedIconButtonSize {
    abstract val boxSize: Dp

    data object Medium : WantedIconButtonSize() {
        override val boxSize = 40.dp
    }

    data object Small : WantedIconButtonSize() {
        override val boxSize = 32.dp
    }

    data class Custom(override val boxSize: Dp) : WantedIconButtonSize()

    companion object {
        /** preset(데이터 오브젝트) 목록 — 큰 → 작은 순. */
        val presets: List<WantedIconButtonSize> = listOf(Medium, Small)
    }
}
