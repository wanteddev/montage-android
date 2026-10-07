package com.wanted.android.wanted.design.actions.button.iconbutton

import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.feedback.pushbadge.WantedPushBadge
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.clickOnce

private const val DEPRECATED_DISABLE_INTERACTION_MESSAGE =
    "disableInteraction 은 interactionEffect 로 대체되었습니다. " +
        "차단이 필요하면 IconButtonInteractionEffect.None 을 사용해주세요."

private const val DEPRECATED_DISABLE_INTERACTION_REPLACEMENT =
    "WantedIconButtonNormal(icon = icon, modifier = modifier, size = size, enabled = enabled, tint = tint, " +
        "pushBadge = pushBadge, badgePosition = badgePosition, " +
        "interactionEffect = if (disableInteraction) IconButtonInteractionEffect.None " +
        "else IconButtonInteractionEffect.Highlight, " +
        "interactionColor = interactionColor, onClick = onClick)"

/**
 * WantedIconButtonNormal
 *
 * 배경 없이 아이콘만 표시하는 기본 아이콘 버튼입니다.
 *
 * [size] 로 지정한 박스 크기에서 아이콘과 모서리 반경이 자동 산출되며, 인터랙션 피드백은 [interactionEffect] 로 지정합니다.
 *
 * 기본값에서는 레이아웃이 차지하는 크기가 박스(= 인터랙션 영역) 크기와 같습니다.
 * 아이콘 크기만 차지하고 인터랙션 영역만 바깥으로 넘치게 하려면 [interactionOverflow] 를 켭니다.
 *
 * 사용 예시 :
 * ```kotlin
 * WantedIconButtonNormal(
 *     icon = R.drawable.ic_icon,
 *     size = WantedIconButtonNormalSize.Xlarge,
 *     onClick = { /* 클릭 처리 */ }
 * )
 * ```
 *
 * @param icon Int: 아이콘 drawable 리소스 ID
 * @param modifier Modifier: 외형 및 배치를 제어하는 Modifier (size 는 자동 산출되므로 외부 정렬/패딩 용도로 사용)
 * @param size WantedIconButtonNormalSize: 박스 크기. 기본값 Xlarge, preset 외 크기는 [WantedIconButtonNormalSize.Custom] 으로 지정. [interactionOverflow] 가 true 이면 아이콘 크기를 뜻합니다(preset 은 24 · 20 · 18 · 16, Custom 은 숫자 그대로)
 * @param enabled Boolean: 활성화 여부
 * @param tint Color: 활성 시 아이콘 색상. 기본값 `foregroundNeutralPrimary`. 비활성 시 `foregroundDisablePrimary`
 * @param pushBadge @Composable (() -> Unit)?: 지정 위치에 표시할 PushBadge 등 컴포넌트
 * @param badgePosition IconButtonBadgePosition: pushBadge 정렬 기준점. 기본값 TopRight, 아이콘 모서리 기준으로 inset 보정 적용
 * @param interactionEffect IconButtonInteractionEffect: 인터랙션 피드백 방식. 기본값 [IconButtonInteractionEffect.Highlight]
 * @param interactionColor Color: 인터랙션 피드백 색상. 미지정 시 ripple 은 `foregroundNeutralPrimary`, Dim 은 [tint] 를 기준으로 함
 * @param interactionOverflow Boolean: true 이면 레이아웃 크기가 아이콘 크기가 되고, 인터랙션 영역 `max(24, ceil(아이콘 × 1.5 ÷ 4) × 4)` 이 상하좌우로 `(영역 - 아이콘) / 2` 만큼 넘칩니다. 기본값 false
 * @param onClick () -> Unit: 클릭 콜백
 */
@Composable
fun WantedIconButtonNormal(
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    size: WantedIconButtonNormalSize = WantedIconButtonNormalSize.Xlarge,
    enabled: Boolean = true,
    tint: Color = DesignSystemTheme.colors.foregroundNeutralPrimary,
    pushBadge: @Composable (() -> Unit)? = null,
    badgePosition: IconButtonBadgePosition = IconButtonBadgePosition.TopRight,
    interactionEffect: IconButtonInteractionEffect = IconButtonInteractionEffect.Highlight,
    interactionColor: Color = Color.Unspecified,
    interactionOverflow: Boolean = false,
    onClick: () -> Unit = {}
) {
    val geometry = remember(size, interactionOverflow) {
        if (interactionOverflow) {
            IconButtonGeometry.calcOverflowGeometry(size.overflowIconSize)
        } else {
            IconButtonGeometry.calcNormalGeometry(size.boxSize)
        }
    }
    // 아이콘 모서리와 박스 모서리 사이 여백. 배지 inset 보정값이자 interactionOverflow 의 넘침 크기다.
    val inset = geometry.overflowInset
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    val resolvedInteractionColor = interactionEffect.resolveInteractionColor(
        interactionColor = interactionColor,
        tint = tint,
        rippleFallback = DesignSystemTheme.colors.foregroundNeutralPrimary,
    )

    val iconColor = when {
        !enabled -> DesignSystemTheme.colors.foregroundDisablePrimary
        interactionEffect.dimsIcon && pressed -> resolvedInteractionColor.dimmedForPress()
        else -> tint
    }

    if (interactionOverflow) {
        WantedTouchArea(
            modifier = modifier,
            verticalPadding = inset,
            horizontalPadding = inset,
            shape = RoundedCornerShape(geometry.radius),
            enabled = enabled,
            rippleColor = iconButtonRippleColor(IconButtonInteractionStrength.Light, resolvedInteractionColor),
            isUseRipple = interactionEffect.showsIndication,
            interactionSource = interactionSource,
            content = {
                // WantedTouchArea 는 콘텐츠를 wrapContent 로 재므로 컨테이너를 아이콘 크기로 고정한다.
                // 고정하지 않으면 아이콘보다 큰 pushBadge(Text·MaxCount 등)가 레이아웃과 터치 영역을 함께 키운다.
                // 컨테이너가 곧 아이콘 크기라 배지는 inset 보정 없이 아이콘 모서리에 정렬된다.
                IconButtonNormalContent(
                    modifier = Modifier.size(geometry.icon),
                    icon = icon,
                    iconSize = geometry.icon,
                    iconColor = iconColor,
                    pushBadge = pushBadge,
                    badgePosition = badgePosition,
                    badgeInset = 0.dp,
                )
            },
            onClick = onClick
        )
    } else {
        // 박스가 아이콘보다 크므로 배지를 박스 안쪽으로 inset 만큼 당긴다.
        IconButtonNormalContent(
            modifier = modifier
                .size(geometry.box)
                .clip(RoundedCornerShape(geometry.radius))
                .clickOnce(
                    interactionSource = interactionSource,
                    indication = iconButtonIndication(
                        strength = IconButtonInteractionStrength.Light,
                        color = resolvedInteractionColor,
                        disabled = !interactionEffect.showsIndication || !enabled,
                    ),
                    enabled = enabled,
                    onClick = onClick
                ),
            icon = icon,
            iconSize = geometry.icon,
            iconColor = iconColor,
            pushBadge = pushBadge,
            badgePosition = badgePosition,
            badgeInset = inset,
        )
    }
}

/**
 * IconButtonNormalContent
 *
 * 아이콘과 PushBadge 를 그립니다. 배지는 [badgePosition] 기준으로 정렬한 뒤 [badgeInset] 만큼 안쪽으로 당깁니다.
 *
 * 컨테이너 크기는 [modifier] 로 **항상 명시**합니다. 크기를 주지 않으면 wrapContent 가 되어
 * 아이콘보다 큰 [pushBadge] 가 컨테이너를 키웁니다.
 *
 * @param icon Int: 아이콘 drawable 리소스 ID
 * @param iconSize Dp: 아이콘 크기
 * @param iconColor Color: 아이콘 색상 (enabled·인터랙션 상태가 반영된 최종 색)
 * @param pushBadge @Composable (() -> Unit)?: 표시할 PushBadge 등 컴포넌트
 * @param badgePosition IconButtonBadgePosition: 배지 정렬 기준점
 * @param badgeInset Dp: 배지 inset 보정값. 컨테이너가 아이콘 크기와 같으면 0
 * @param modifier Modifier: 컨테이너 크기·클립·클릭을 지정하는 Modifier
 */
@Composable
private fun IconButtonNormalContent(
    @DrawableRes icon: Int,
    iconSize: Dp,
    iconColor: Color,
    pushBadge: @Composable (() -> Unit)?,
    badgePosition: IconButtonBadgePosition,
    badgeInset: Dp,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Icon(
            modifier = Modifier.size(iconSize),
            painter = painterResource(id = icon),
            contentDescription = "",
            tint = iconColor,
        )

        pushBadge?.let {
            Box(
                modifier = Modifier
                    .align(badgePosition.toAlignment())
                    .offset(
                        x = badgeInset * badgePosition.horizontalInsetSign.toFloat(),
                        y = badgeInset * badgePosition.verticalInsetSign.toFloat(),
                    )
            ) {
                pushBadge()
            }
        }
    }
}

/**
 * WantedIconButtonNormal
 *
 * `disableInteraction` 을 사용하는 이전 시그니처입니다.
 * [IconButtonInteractionEffect] 로 대체되었으니 신규 코드에서는 사용하지 않습니다.
 *
 * @param icon Int: 아이콘 drawable 리소스 ID
 * @param disableInteraction Boolean: true이면 press/hover ripple 효과 없음
 * @param modifier Modifier: 외형 및 배치를 제어하는 Modifier
 * @param size WantedIconButtonNormalSize: 박스 크기. 기본값 [WantedIconButtonNormalSize.Xlarge]
 * @param enabled Boolean: 활성화 여부
 * @param tint Color: 활성 시 아이콘 색상. 기본값 `foregroundNeutralPrimary`. 비활성 시 `foregroundDisablePrimary`
 * @param pushBadge @Composable (() -> Unit)?: 지정 위치에 표시할 PushBadge 등 컴포넌트
 * @param badgePosition IconButtonBadgePosition: pushBadge 정렬 기준점. 기본값 [IconButtonBadgePosition.TopRight]
 * @param interactionColor Color: 인터랙션 피드백 색상. 미지정 시 ripple 은 `foregroundNeutralPrimary`
 * @param onClick () -> Unit: 클릭 콜백
 */
@Deprecated(
    message = DEPRECATED_DISABLE_INTERACTION_MESSAGE,
    replaceWith = ReplaceWith(DEPRECATED_DISABLE_INTERACTION_REPLACEMENT),
    level = DeprecationLevel.WARNING
)
@Composable
fun WantedIconButtonNormal(
    @DrawableRes icon: Int,
    disableInteraction: Boolean,
    modifier: Modifier = Modifier,
    size: WantedIconButtonNormalSize = WantedIconButtonNormalSize.Xlarge,
    enabled: Boolean = true,
    tint: Color = DesignSystemTheme.colors.foregroundNeutralPrimary,
    pushBadge: @Composable (() -> Unit)? = null,
    badgePosition: IconButtonBadgePosition = IconButtonBadgePosition.TopRight,
    interactionColor: Color = Color.Unspecified,
    onClick: () -> Unit = {}
) {
    WantedIconButtonNormal(
        icon = icon,
        modifier = modifier,
        size = size,
        enabled = enabled,
        tint = tint,
        pushBadge = pushBadge,
        badgePosition = badgePosition,
        interactionEffect = IconButtonInteractionEffect.fromDisableInteraction(disableInteraction),
        interactionColor = interactionColor,
        onClick = onClick
    )
}

@DevicePreviews
@Composable
private fun WantedIconButtonNormalPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedIconButtonNormal(
                    icon = R.drawable.icon_normal_company,
                    size = WantedIconButtonNormalSize.Xlarge,
                    onClick = {}
                )

                WantedIconButtonNormal(
                    icon = R.drawable.icon_normal_company,
                    size = WantedIconButtonNormalSize.Large,
                    onClick = {}
                )

                WantedIconButtonNormal(
                    icon = R.drawable.icon_normal_company,
                    size = WantedIconButtonNormalSize.Medium,
                    onClick = {}
                )

                WantedIconButtonNormal(
                    icon = R.drawable.icon_normal_company,
                    size = WantedIconButtonNormalSize.Small,
                    onClick = {}
                )

                WantedIconButtonNormal(
                    icon = R.drawable.icon_normal_company,
                    size = WantedIconButtonNormalSize.Xlarge,
                    pushBadge = { WantedPushBadge() },
                    onClick = {}
                )

                WantedIconButtonNormal(
                    icon = R.drawable.icon_normal_company,
                    size = WantedIconButtonNormalSize.Custom(40.dp),
                    onClick = {}
                )

                WantedIconButtonNormal(
                    icon = R.drawable.icon_normal_company,
                    size = WantedIconButtonNormalSize.Xlarge,
                    interactionEffect = IconButtonInteractionEffect.Dim,
                    onClick = {}
                )

                WantedIconButtonNormal(
                    icon = R.drawable.icon_normal_company,
                    size = WantedIconButtonNormalSize.Xlarge,
                    interactionEffect = IconButtonInteractionEffect.None,
                    onClick = {}
                )

                // interactionOverflow: 레이아웃은 아이콘(24dp), 인터랙션 영역은 박스(36dp)
                WantedIconButtonNormal(
                    icon = R.drawable.icon_normal_company,
                    size = WantedIconButtonNormalSize.Xlarge,
                    interactionOverflow = true,
                    onClick = {}
                )
            }
        }
    }
}
