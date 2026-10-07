package com.wanted.android.wanted.design.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * data class WantedPrimitive
 *
 * Figma `Primitive` 컬렉션을 매핑한 원시 척도 토큰.
 *
 * 직접 사용하지 말고 가능한 [WantedSpacing], [WantedRadius], [WantedDimension] 시맨틱 토큰을 사용한다.
 */
@Immutable
data class WantedPrimitive(
    val primitive0: Dp = 0.dp,
    val primitive1: Dp = 1.dp,
    val primitive2: Dp = 2.dp,
    val primitive4: Dp = 4.dp,
    val primitive6: Dp = 6.dp,
    val primitive8: Dp = 8.dp,
    val primitive10: Dp = 10.dp,
    val primitive12: Dp = 12.dp,
    val primitive14: Dp = 14.dp,
    val primitive16: Dp = 16.dp,
    val primitive18: Dp = 18.dp,
    val primitive20: Dp = 20.dp,
    val primitive24: Dp = 24.dp,
    val primitive28: Dp = 28.dp,
    val primitive32: Dp = 32.dp,
    val primitive36: Dp = 36.dp,
    val primitive40: Dp = 40.dp,
    val primitive48: Dp = 48.dp,
    val primitive56: Dp = 56.dp,
    val primitive64: Dp = 64.dp,
    val primitive72: Dp = 72.dp,
    val primitive80: Dp = 80.dp,
    val primitive9999: Dp = 9999.dp,
)

val LocalWantedPrimitive = staticCompositionLocalOf { WantedPrimitive() }
