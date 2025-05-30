package com.wanted.android.wanted.design.navigations.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal val LocalTabGradationColor = WantedTabGradationColorCompositionLocal()


/**
 * 탭 컴포넌트 내 그라디언트 색상을 설정하기 위한 CompositionLocal입니다.
 *
 * `CompositionLocalProvider`로 주입한 색상은 좌우 gradient 영역에 사용됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * CompositionLocalProvider(LocalTabGradationColor provides Color.Red) {
 *     ...
 * }
 * ```
 *
 * @property current Color: 현재 설정된 색상 값입니다.
 */
@JvmInline
value class WantedTabGradationColorCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<Color> = staticCompositionLocalOf { Color.Transparent }
) {
    val current: Color
        @Composable get() = delegate.current

    infix fun provides(value: Color) = delegate provides value
}
