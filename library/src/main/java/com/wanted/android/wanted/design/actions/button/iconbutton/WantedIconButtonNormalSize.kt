package com.wanted.android.wanted.design.actions.button.iconbutton

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.actions.button.iconbutton.internal.IconButtonGeometry

/**
 * sealed class WantedIconButtonNormalSize
 *
 * `WantedIconButtonNormal` 의 박스(컨테이너) 크기입니다. (Confluence IconButton 스펙 §3·§4)
 *
 * `size` 는 박스 크기를 뜻하며, 아이콘·radius 는 박스에서 자동 산출됩니다
 * (`icon = box × 2/3` → dimension 토큰 스냅, `radius = box × 0.3` → radius 토큰 스냅).
 * 박스는 `clamp(24dp, N, 64dp)` 범위로 클램프됩니다.
 *
 * - [Xlarge] : box 36dp (기본값) → icon 24dp, radius 10dp
 * - [Large]  : box 32dp → icon 20dp, radius 10dp
 * - [Medium] : box 28dp → icon 18dp, radius 8dp
 * - [Small]  : box 24dp → icon 16dp, radius 8dp
 * - [Custom] : 임의 박스 크기. `copy` 로 커스텀할 수 있습니다.
 *
 * `interactionOverflow = true` 이면 size 는 **아이콘 크기**를 뜻합니다(Web 방식, WRP-3163).
 * 인터랙션 영역은 아이콘에서 `max(24, ceil(icon × 1.5 ÷ 4) × 4)` 로 계산합니다.
 * - preset 은 위 표의 아이콘 크기(24 · 20 · 18 · 16)를 쓰므로 인터랙션 영역은 36 · 32 · 28 · 24 로 같습니다.
 * - [Custom] 은 숫자가 그대로 아이콘 크기가 됩니다. 예) Custom(20.dp) → icon 20dp, 인터랙션 영역 32dp
 */
sealed class WantedIconButtonNormalSize {
    abstract val boxSize: Dp

    data object Xlarge : WantedIconButtonNormalSize() {
        override val boxSize = 36.dp
    }

    data object Large : WantedIconButtonNormalSize() {
        override val boxSize = 32.dp
    }

    data object Medium : WantedIconButtonNormalSize() {
        override val boxSize = 28.dp
    }

    data object Small : WantedIconButtonNormalSize() {
        override val boxSize = 24.dp
    }

    data class Custom(override val boxSize: Dp) : WantedIconButtonNormalSize()

    companion object {
        // 즉시 초기화하면 안 된다 — companion 초기화는 WantedIconButtonNormalSize 의 클래스 초기화 안에서 돌고,
        // 그 초기화는 `Xlarge` 같은 하위 data object 를 먼저 만드는 경로로도 진입한다(예: `size = Xlarge` 기본값).
        // 그 경우 아직 `INSTANCE` 가 배정되지 않은 object 가 리스트에 `null` 로 담긴다. lazy 로 첫 접근 시점까지 미룬다.
        /** preset(데이터 오브젝트) 목록 — 큰 → 작은 순. */
        val presets: List<WantedIconButtonNormalSize> by lazy { listOf(Xlarge, Large, Medium, Small) }
    }
}

// interactionOverflow = true 일 때 size 가 뜻하는 아이콘 크기다.
// preset 은 이름이 가리키는 기존 아이콘 크기를, Custom 은 숫자를 그대로 아이콘 크기로 쓴다.
internal val WantedIconButtonNormalSize.overflowIconSize: Dp
    get() = when (this) {
        is WantedIconButtonNormalSize.Custom -> boxSize
        WantedIconButtonNormalSize.Xlarge,
        WantedIconButtonNormalSize.Large,
        WantedIconButtonNormalSize.Medium,
        WantedIconButtonNormalSize.Small -> IconButtonGeometry.calcNormalGeometry(boxSize).icon
    }
