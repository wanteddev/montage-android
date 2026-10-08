package com.wanted.android.wanted.design.actions.button.iconbutton

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.button.iconbutton.internal.IconButtonGeometry
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.clickOnce

/**
 * WantedIconButtonOutlined
 *
 * WantedIconButtonSize를 기반으로 하는 Outlined 스타일의 아이콘 버튼입니다.
 * (Confluence IconButton 스펙 §3 — 2026-06-24)
 *
 * 아이콘 크기 산출: `icon = round(box × 0.47)` → dimension 토큰 스냅(동률이면 작은 값).
 * 중앙정렬 inset = `(box - icon) / 2`. padding 방식 제거.
 * Radius = `radius.full` (CircleShape). 박스는 `clamp(24dp, N, 64dp)`.
 *
 * 사용 예시:
 * ```kotlin
 * WantedIconButtonOutlined(
 *     icon = R.drawable.ic_icon,
 *     size = WantedIconButtonSize.Medium,
 *     onClick = { /* 클릭 처리 */ }
 * )
 * ```
 *
 * @param icon Int: 버튼에 표시할 drawable 리소스 ID입니다.
 * @param size WantedIconButtonSize: 박스 크기를 지정하는 sealed class입니다. preset(Medium/Small) 외 박스는 [WantedIconButtonSize.Custom] 으로 지정합니다.
 * @param modifier Modifier: 외형 및 배치를 제어하는 Modifier입니다.
 * @param enabled Boolean: 버튼의 활성화 여부입니다.
 * @param outlineColor Color: 활성화 상태의 외곽선 색상입니다.
 * @param disableOutlineColor Color: 비활성 상태의 외곽선 색상입니다.
 * @param tint Color: 활성 상태의 아이콘 색상입니다.
 * @param disableTint Color: 비활성 상태의 아이콘 색상입니다.
 * @param background Color: 활성 상태의 배경 색상입니다.
 * @param disableBackground Color: 비활성 상태의 배경 색상입니다. 기본값 `backgroundNeutralPrimary`
 * @param pushBadge @Composable (() -> Unit)?: 지정 위치에 표시할 PushBadge 등 컴포넌트. 박스 모서리 기준으로 정렬됩니다.
 * @param badgePosition IconButtonBadgePosition: pushBadge 정렬 기준점. 기본값 [IconButtonBadgePosition.TopRight]
 * @param disableInteraction Boolean: true이면 press/hover ripple 효과 없음. 기본값 false
 * @param interactionColor Color: ripple 색상. 기본값 `foregroundNeutralPrimary`
 * @param onClick () -> Unit: 클릭 시 호출되는 콜백입니다.
 */
@Composable
fun WantedIconButtonOutlined(
    @DrawableRes icon: Int,
    size: WantedIconButtonSize,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    outlineColor: Color = DesignSystemTheme.colors.lineNeutralSecondary,
    disableOutlineColor: Color = DesignSystemTheme.colors.lineNeutralSecondary,
    tint: Color = DesignSystemTheme.colors.foregroundNeutralPrimary,
    disableTint: Color = DesignSystemTheme.colors.foregroundDisablePrimary,
    background: Color = DesignSystemTheme.colors.transparent,
    disableBackground: Color = DesignSystemTheme.colors.backgroundNeutralPrimary,
    pushBadge: (@Composable () -> Unit)? = null,
    badgePosition: IconButtonBadgePosition = IconButtonBadgePosition.TopRight,
    disableInteraction: Boolean = false,
    interactionColor: Color = DesignSystemTheme.colors.foregroundNeutralPrimary,
    onClick: () -> Unit = {},
) {
    val geometry = remember(size) { IconButtonGeometry.calcOutlinedSolidIconSize(size.boxSize) }
    WantedIconButtonOutlined(
        icon = icon,
        boxSize = geometry.box,
        iconSize = geometry.icon,
        modifier = modifier,
        enabled = enabled,
        outlineColor = outlineColor,
        disableOutlineColor = disableOutlineColor,
        tint = tint,
        disableTint = disableTint,
        background = background,
        disableBackground = disableBackground,
        pushBadge = pushBadge,
        badgePosition = badgePosition,
        disableInteraction = disableInteraction,
        interactionColor = interactionColor,
        onClick = onClick
    )
}

@Composable
private fun WantedIconButtonOutlined(
    @DrawableRes icon: Int,
    boxSize: Dp,
    iconSize: Dp,
    modifier: Modifier,
    enabled: Boolean,
    outlineColor: Color,
    disableOutlineColor: Color,
    tint: Color,
    disableTint: Color,
    background: Color,
    disableBackground: Color,
    pushBadge: (@Composable () -> Unit)?,
    badgePosition: IconButtonBadgePosition,
    disableInteraction: Boolean,
    interactionColor: Color,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    // pushBadge가 clip(CircleShape) 영역 밖으로 잘리지 않도록, 클립되지 않는 외곽 Box로 래핑해 오버레이한다.
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .size(boxSize)
                .clip(CircleShape)
                .background(if (enabled) background else disableBackground)
                .border(
                    width = DesignSystemTheme.primitive.primitive1,
                    color = if (enabled) outlineColor else disableOutlineColor,
                    shape = CircleShape
                )
                .clickOnce(
                    interactionSource = interactionSource,
                    indication = iconButtonIndication(
                        strength = IconButtonInteractionStrength.Light,
                        color = interactionColor,
                        disabled = disableInteraction || !enabled,
                    ),
                    enabled = enabled,
                    onClick = onClick
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                painter = painterResource(id = icon),
                contentDescription = "",
                tint = if (enabled) tint else disableTint
            )
        }

        pushBadge?.let {
            // Outlined variant: 박스 모서리 기준 정렬, inset 보정 없음
            Box(modifier = Modifier.align(badgePosition.toAlignment())) {
                pushBadge()
            }
        }
    }
}


@DevicePreviews
@Composable
private fun WantedIconButtonOutlinedPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedIconButtonOutlined(
                    size = WantedIconButtonSize.Medium,
                    icon = R.drawable.icon_normal_company,
                    onClick = {}
                )

                WantedIconButtonOutlined(
                    modifier = Modifier,
                    size = WantedIconButtonSize.Medium,
                    icon = R.drawable.icon_normal_company,
                    enabled = false,
                    onClick = {}
                )

                WantedIconButtonOutlined(
                    modifier = Modifier,
                    size = WantedIconButtonSize.Small,
                    icon = R.drawable.icon_normal_company,
                    onClick = {}
                )

                WantedIconButtonOutlined(
                    size = WantedIconButtonSize.Small,
                    icon = R.drawable.icon_normal_company,
                    enabled = false,
                    onClick = {}
                )

                WantedIconButtonOutlined(
                    size = WantedIconButtonSize.Custom(48.dp),
                    icon = R.drawable.icon_normal_company,
                    onClick = {}
                )
            }
        }
    }
}
