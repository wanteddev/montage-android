package com.wanted.android.wanted.design.navigations.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal val LocalCategoryGradationColor = WantedCategoryGradationColorCompositionLocal()


/**
 * 카테고리 좌우 그라디언트 배경 색상 지정을 위한 CompositionLocal 래퍼입니다.
 *
 * `CompositionLocalProvider`를 통해 원하는 색상을 주입하여 내부 그라디언트 표현에 사용됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * CompositionLocalProvider(LocalCategoryGradationColor provides Color.Red) {
 *     ...
 * }
 * ```
 *
 * @property current Color: 현재 CompositionLocal에 저장된 색상입니다.
 */
@JvmInline
value class WantedCategoryGradationColorCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<Color> = staticCompositionLocalOf { Color.Transparent }
) {
    val current: Color
        @Composable get() = delegate.current

    infix fun provides(value: Color) = delegate provides value
}
