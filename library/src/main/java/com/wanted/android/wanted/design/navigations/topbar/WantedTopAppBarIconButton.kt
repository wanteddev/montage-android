package com.wanted.android.wanted.design.navigations.topbar

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.actions.button.iconbutton.IconButtonInteractionEffect
import com.wanted.android.wanted.design.actions.button.iconbutton.dimmedForPress
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.theme.DesignSystemTheme

/**
 * WantedTopAppBarIconButton
 *
 * TopAppBar에 사용되는 아이콘 버튼 컴포넌트입니다.
 *
 * 아이콘 24dp · 터치 영역 40dp 원형 규격이며, 인터랙션 피드백은 [interactionEffect] 로 지정합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedTopAppBarIconButton(
 *     painter = painterResource(id = R.drawable.icon_normal_arrow_left),
 *     onClick = { /* 클릭 처리 */ }
 * )
 * ```
 *
 * @param painter Painter: 아이콘으로 표시할 이미지입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param enabled Boolean: 버튼 활성화 여부입니다.
 * @param interactionSource MutableInteractionSource: 사용자 인터랙션을 처리하는 객체입니다.
 * @param tint Color: 아이콘 색상입니다.
 * @param badgeAlignment Alignment: 배지 정렬 위치입니다.
 * @param badge (@Composable () -> Unit)?: 배지 콘텐츠입니다.
 * @param interactionEffect IconButtonInteractionEffect: 인터랙션 피드백 방식입니다. 기본값 [IconButtonInteractionEffect.Dim]
 * @param onClick () -> Unit: 버튼 클릭 시 호출되는 콜백입니다.
 */
@Composable
fun WantedTopAppBarIconButton(
    painter: Painter,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    tint: Color = LocalWantedTopBarIconTint.current,
    badgeAlignment: Alignment = Alignment.TopEnd,
    badge: @Composable (() -> Unit)? = null,
    interactionEffect: IconButtonInteractionEffect = IconButtonInteractionEffect.Dim,
    onClick: () -> Unit = {}
) {
    val pressed by interactionSource.collectIsPressedAsState()
    val iconColor = if (interactionEffect.dimsIcon && pressed) tint.dimmedForPress() else tint

    WantedTouchArea(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        verticalPadding = 8.dp,
        horizontalPadding = 8.dp,
        interactionSource = interactionSource,
        shape = CircleShape,
        isUseRipple = interactionEffect.showsIndication,
        content = {
            Box(modifier = Modifier) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painter,
                    contentDescription = null,
                    tint = iconColor
                )

                badge?.let {
                    Box(
                        modifier = Modifier.size(24.dp),
                        contentAlignment = badgeAlignment
                    ) {
                        badge()
                    }
                }
            }
        }
    )
}

val LocalWantedTopBarIconTint = WantedTopBarIconTintCompositionLocal()


interface WantedTopBarIconTintLoader {
    @Composable
    fun getColor(): Color
}

private open class WantedTopBarIconTintImpl() : WantedTopBarIconTintLoader {
    @Composable
    override fun getColor(): Color {
        return DesignSystemTheme.colors.foregroundNeutralPrimary
    }
}

@JvmInline
value class WantedTopBarIconTintCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedTopBarIconTintLoader> = staticCompositionLocalOf { WantedTopBarIconTintImpl() }
) {
    val current: Color
        @Composable get() = delegate.current.getColor()

    infix fun provides(value: Color) = delegate provides object : WantedTopBarIconTintImpl() {
        @Composable
        override fun getColor(): Color {
            return value
        }
    }
}

