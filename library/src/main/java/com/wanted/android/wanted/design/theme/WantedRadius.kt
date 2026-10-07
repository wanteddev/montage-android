package com.wanted.android.wanted.design.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * data class WantedRadius
 *
 * Figma `Radius` 컬렉션을 매핑한 시맨틱 토큰. corner-radius 용도.
 * 각 값은 [WantedPrimitive]를 alias 한다.
 *
 * [radiusFull]은 Figma의 `radius/full(9999px)`에 해당하며 Compose에선 [CircleShape]로 노출한다.
 *
 * 값을 추가·삭제하면 IconButtonGeometry 의 RadiusToken(radius 스냅 후보군)도 함께 바꿔야 한다.
 * 두 목록이 따로 관리돼 한쪽만 고치면 토큰은 생겼는데 스냅 로직이 새 값을 보지 못한다. (WRP-3160)
 */
@Immutable
data class WantedRadius(
    val radius0: Dp = 0.dp,
    val radius4: Dp = 4.dp,
    val radius8: Dp = 8.dp,
    val radius10: Dp = 10.dp,
    val radius12: Dp = 12.dp,
    val radius14: Dp = 14.dp,
    val radius16: Dp = 16.dp,
    val radius20: Dp = 20.dp,
    val radius24: Dp = 24.dp,
    val radius28: Dp = 28.dp,
    val radius32: Dp = 32.dp,
    val radiusFull: Shape = CircleShape,
)

val LocalWantedRadius = staticCompositionLocalOf { WantedRadius() }
