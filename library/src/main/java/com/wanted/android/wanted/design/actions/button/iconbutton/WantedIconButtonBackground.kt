package com.wanted.android.wanted.design.actions.button.iconbutton

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.button.iconbutton.internal.IconButtonGeometry
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_22
import com.wanted.android.wanted.design.util.OPACITY_35
import com.wanted.android.wanted.design.util.OPACITY_5
import com.wanted.android.wanted.design.util.OPACITY_61
import com.wanted.android.wanted.design.util.OPACITY_88
import com.wanted.android.wanted.design.util.clickOnce

/**
 * fun WantedIconButtonBackground(...)
 *
 * 콘텐츠 위에 떠 있는 floating 아이콘 버튼. 이미지·미디어 등 오버레이 액션에 사용합니다.
 * (Confluence IconButton 스펙 §3·§4)
 *
 * [size] 는 박스(컨테이너) 크기이며, 아이콘은 박스에서 자동 산출됩니다.
 * - 아이콘 = `nearestDimensionToken(box × 2/3, tie → down)`
 * - Border radius = `CircleShape` (radius.full)
 * - 박스 = `clamp(24dp, N, 64dp)`, 글래스 레이어는 박스를 정확히 채움 (`inset: 0`)
 * - Hit area = 박스 100%
 *
 * background 는 단일 [WantedIconButtonBackgroundSize.Default] (box 32dp, icon 20dp) preset 으로 수렴하며,
 * 박스 커스텀은 [WantedIconButtonBackgroundSize.Custom] 으로 지정합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedIconButtonBackground(
 *     icon = R.drawable.ic_icon,
 *     // size 미지정 → Default(box 32dp) 기본값
 *     alternative = false,
 *     onClick = { /* 클릭 처리 */ }
 * )
 * ```
 *
 * @param icon Int: 아이콘 drawable 리소스 ID
 * @param modifier Modifier: 외형 및 배치를 제어하는 Modifier
 * @param alternative Boolean: true 일 경우 다크 오버레이 위 사용 변형
 * @param enabled Boolean: 활성화 여부
 * @param size WantedIconButtonBackgroundSize: 박스 크기. 기본값 [WantedIconButtonBackgroundSize.Default]
 * @param tint Color: 아이콘 색상 (기본값은 alternative 여부에 따라 자동 결정)
 * @param pushBadge @Composable (() -> Unit)?: 지정 위치에 표시할 PushBadge 등 컴포넌트. 박스 모서리 기준으로 정렬됩니다.
 * @param badgePosition IconButtonBadgePosition: pushBadge 정렬 기준점. 기본값 [IconButtonBadgePosition.TopRight]
 * @param disableInteraction Boolean: true이면 press/hover ripple 효과 없음. 기본값 false
 * @param interactionColor Color: ripple 색상. 기본값 `foregroundNeutralPrimary`
 * @param useNormalInteraction Boolean: true이면 light 대신 normal 강도 ripple 적용. 기본값 false
 * @param onClick () -> Unit: 클릭 콜백
 */
@Composable
fun WantedIconButtonBackground(
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    alternative: Boolean = false,
    enabled: Boolean = true,
    size: WantedIconButtonBackgroundSize = WantedIconButtonBackgroundSize.Default,
    tint: Color = defaultBackgroundTint(alternative),
    pushBadge: (@Composable () -> Unit)? = null,
    badgePosition: IconButtonBadgePosition = IconButtonBadgePosition.TopRight,
    disableInteraction: Boolean = false,
    interactionColor: Color = DesignSystemTheme.colors.foregroundNeutralPrimary,
    useNormalInteraction: Boolean = false,
    onClick: () -> Unit = {}
) {
    val geometry = remember(size) { IconButtonGeometry.calcBackgroundGeometry(size.boxSize) }
    val interactionSource = remember { MutableInteractionSource() }
    val interactionStrength = remember(useNormalInteraction) {
        if (useNormalInteraction) {
            IconButtonInteractionStrength.Normal
        } else {
            IconButtonInteractionStrength.Light
        }
    }

    // pushBadge가 clip(CircleShape) 영역 밖으로 잘리지 않도록, 클립되지 않는 외곽 Box로 래핑해 오버레이한다.
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .size(geometry.box)
                .clip(CircleShape)
                .backgroundLayer(enabled = enabled, alternative = alternative)
                .clickOnce(
                    interactionSource = interactionSource,
                    indication = iconButtonIndication(
                        strength = interactionStrength,
                        color = interactionColor,
                        disabled = disableInteraction || !enabled,
                    ),
                    enabled = enabled,
                    onClick = onClick,
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(geometry.icon),
                painter = painterResource(id = icon),
                contentDescription = "",
                tint = if (enabled) {
                    tint
                } else {
                    colorResource(id = R.color.cool_neutral_50).copy(alpha = OPACITY_22)
                }
            )
        }

        // Background variant: 박스 모서리 기준 정렬, inset 보정 없음
        pushBadge?.let {
            Box(modifier = Modifier.align(badgePosition.toAlignment())) {
                pushBadge()
            }
        }
    }
}

@Composable
private fun defaultBackgroundTint(alternative: Boolean): Color =
    if (alternative) {
        DesignSystemTheme.colors.staticWhite.copy(alpha = OPACITY_88)
    } else {
        colorResource(id = R.color.cool_neutral_50).copy(alpha = OPACITY_61)
    }

@Composable
private fun Modifier.backgroundLayer(
    enabled: Boolean,
    alternative: Boolean
): Modifier {
    return when {
        !enabled -> this.background(DesignSystemTheme.colors.surfaceNeutralTertiary)
        alternative -> this.background(
            colorResource(id = R.color.cool_neutral_30).copy(alpha = OPACITY_61)
        )

        else -> this
            .background(DesignSystemTheme.colors.staticWhite.copy(alpha = OPACITY_35))
            .background(DesignSystemTheme.colors.staticBlack.copy(alpha = OPACITY_5))
    }
}

@DevicePreviews
@Composable
private fun WantedIconButtonBackgroundPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedIconButtonBackground(
                    icon = R.drawable.icon_normal_company,
                    onClick = {}
                )

                WantedIconButtonBackground(
                    icon = R.drawable.icon_normal_company,
                    alternative = true,
                    onClick = {}
                )

                WantedIconButtonBackground(
                    icon = R.drawable.icon_normal_company,
                    enabled = false,
                    onClick = {}
                )

                WantedIconButtonBackground(
                    icon = R.drawable.icon_normal_company,
                    alternative = true,
                    enabled = false,
                    onClick = {}
                )

                WantedIconButtonBackground(
                    icon = R.drawable.icon_normal_company,
                    size = WantedIconButtonBackgroundSize.Custom(48.dp),
                    onClick = {}
                )
            }
        }
    }
}
