package com.wanted.android.wanted.design.actions.button.iconbutton

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.clickOnce

/**
 * WantedIconButtonOutlined
 *
 * WantedIconButtonSize를 기반으로 하는 Outlined 스타일의 아이콘 버튼입니다.
 *
 * 아이콘의 크기와 패딩을 enum으로 간편하게 설정할 수 있으며, 외곽선과 배경, 텍스트 색상 등을 상태별로 지정할 수 있습니다.
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
 * @param size WantedIconButtonSize: 버튼의 크기와 내부 여백을 지정하는 enum입니다.
 * @param modifier Modifier: 외형 및 배치를 제어하는 Modifier입니다.
 * @param enabled Boolean: 버튼의 활성화 여부입니다.
 * @param outlineColor Color: 활성화 상태의 외곽선 색상입니다.
 * @param disableOutlineColor Color: 비활성 상태의 외곽선 색상입니다.
 * @param tint Color: 활성 상태의 아이콘 색상입니다.
 * @param disableTint Color: 비활성 상태의 아이콘 색상입니다.
 * @param background Color: 활성 상태의 배경 색상입니다.
 * @param disableBackground Color: 비활성 상태의 배경 색상입니다.
 * @param onClick () -> Unit: 클릭 시 호출되는 콜백입니다.
 */
@Composable
fun WantedIconButtonOutlined(
    @DrawableRes icon: Int,
    size: WantedIconButtonSize,
    modifier: Modifier,
    enabled: Boolean = true,
    outlineColor: Color = DesignSystemTheme.colors.lineNormalNeutral,
    disableOutlineColor: Color = DesignSystemTheme.colors.lineNormalNeutral,
    tint: Color = DesignSystemTheme.colors.labelNormal,
    disableTint: Color = DesignSystemTheme.colors.labelDisable,
    background: Color = DesignSystemTheme.colors.transparent,
    disableBackground: Color = DesignSystemTheme.colors.transparent,
    onClick: () -> Unit = {},
) {
    WantedIconButtonOutlined(
        modifier = modifier.size(size.size),
        icon = icon,
        enabled = enabled,
        padding = size.padding,
        outlineColor = outlineColor,
        disableOutlineColor = disableOutlineColor,
        tint = tint,
        disableTint = disableTint,
        background = background,
        disableBackground = disableBackground,
        onClick = onClick
    )
}


/**
 * WantedIconButtonOutlined
 *
 * 패딩을 수동으로 지정할 수 있는 Outlined 아이콘 버튼입니다.
 *
 * 이 함수는 Size 대신 Modifier 크기 및 패딩을 직접 지정하여 더 유연한 스타일링이 가능합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedIconButtonOutlined(
 *     icon = R.drawable.ic_icon,
 *     modifier = Modifier.size(40.dp),
 *     padding = 8.dp,
 *     onClick = { /* 클릭 처리 */ }
 * )
 * ```
 *
 * @param icon Int: 버튼에 표시할 drawable 리소스 ID입니다.
 * @param modifier Modifier: 외형 및 배치를 제어하는 Modifier입니다.
 * @param padding Dp: 아이콘 내부 패딩입니다.
 * @param enabled Boolean: 버튼의 활성화 여부입니다.
 * @param outlineColor Color: 활성화 상태의 외곽선 색상입니다.
 * @param disableOutlineColor Color: 비활성 상태의 외곽선 색상입니다.
 * @param tint Color: 활성 상태의 아이콘 색상입니다.
 * @param disableTint Color: 비활성 상태의 아이콘 색상입니다.
 * @param background Color: 활성 상태의 배경 색상입니다.
 * @param disableBackground Color: 비활성 상태의 배경 색상입니다.
 * @param onClick () -> Unit: 클릭 시 호출되는 콜백입니다.
 */
@Composable
fun WantedIconButtonOutlined(
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    padding: Dp = 10.dp,
    enabled: Boolean = true,
    outlineColor: Color = DesignSystemTheme.colors.lineNormalNeutral,
    disableOutlineColor: Color = DesignSystemTheme.colors.lineNormalNeutral,
    tint: Color = DesignSystemTheme.colors.labelNormal,
    disableTint: Color = DesignSystemTheme.colors.labelDisable,
    background: Color = DesignSystemTheme.colors.transparent,
    disableBackground: Color = DesignSystemTheme.colors.transparent,
    onClick: () -> Unit = {},
) {
    Icon(
        modifier = modifier
            .clip(CircleShape)
            .background(if (enabled) background else disableBackground)
            .border(
                width = 1.dp,
                color = if (enabled) outlineColor else disableOutlineColor,
                shape = CircleShape
            )
            .clickOnce(enabled) { onClick() }
            .padding(padding),
        painter = painterResource(id = icon),
        contentDescription = "",
        tint = if (enabled) tint else disableTint
    )
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
                    modifier = Modifier,
                    size = WantedIconButtonSize.Medium,
                    icon = R.drawable.icon_normal_company,
                    onClick = {}
                )

                WantedIconButtonOutlined(
                    modifier = Modifier,
                    size = WantedIconButtonSize.Small,
                    icon = R.drawable.icon_normal_company,
                    onClick = {}
                )

                WantedIconButtonOutlined(
                    modifier = Modifier.size(40.dp),
                    icon = R.drawable.icon_normal_company,
                    onClick = {}
                )
            }
        }
    }
}