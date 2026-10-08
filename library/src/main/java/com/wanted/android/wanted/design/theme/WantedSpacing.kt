package com.wanted.android.wanted.design.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * data class WantedSpacing
 *
 * Figma `Spacing` 컬렉션을 매핑한 시맨틱 토큰. GAP(간격, 패딩, 마진)에 사용한다.
 * 각 값은 [WantedPrimitive]를 alias 한다.
 */
@Immutable
data class WantedSpacing(
    val spacing0: Dp = 0.dp,
    val spacing2: Dp = 2.dp,
    val spacing4: Dp = 4.dp,
    val spacing6: Dp = 6.dp,
    val spacing8: Dp = 8.dp,
    val spacing10: Dp = 10.dp,
    val spacing12: Dp = 12.dp,
    val spacing14: Dp = 14.dp,
    val spacing16: Dp = 16.dp,
    val spacing20: Dp = 20.dp,
    val spacing24: Dp = 24.dp,
    val spacing32: Dp = 32.dp,
    val spacing40: Dp = 40.dp,
    val spacing48: Dp = 48.dp,
    val spacing56: Dp = 56.dp,
    val spacing64: Dp = 64.dp,
    val spacing72: Dp = 72.dp,
    val spacing80: Dp = 80.dp,
)

val LocalWantedSpacing = staticCompositionLocalOf { WantedSpacing() }
