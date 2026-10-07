package com.wanted.android.wanted.design.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * data class WantedDimension
 *
 * Figma `Dimension` 컬렉션을 매핑한 시맨틱 토큰. height/width 용도.
 * 각 값은 [WantedPrimitive]를 alias 한다.
 *
 * Layout 시맨틱 토큰(WantedLayout, 추후 추가 예정)을 우선 사용하세요.
 */
@Immutable
data class WantedDimension(
    val dimension12: Dp = 12.dp,
    val dimension14: Dp = 14.dp,
    val dimension16: Dp = 16.dp,
    val dimension18: Dp = 18.dp,
    val dimension20: Dp = 20.dp,
    val dimension24: Dp = 24.dp,
    val dimension28: Dp = 28.dp,
    val dimension32: Dp = 32.dp,
    val dimension36: Dp = 36.dp,
    val dimension40: Dp = 40.dp,
    val dimension48: Dp = 48.dp,
    val dimension56: Dp = 56.dp,
    val dimension64: Dp = 64.dp,
)

val LocalWantedDimension = staticCompositionLocalOf { WantedDimension() }
