package com.wanted.android.wanted.design.actions.button.iconbutton

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
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
 * WantedIconButtonSolid
 *
 * WantedIconButtonSize를 사용하여 간편하게 크기와 패딩을 지정할 수 있는 Solid 스타일 아이콘 버튼입니다.
 *
 * 원형 배경과 흰색 아이콘을 기본으로 하며, 배경 및 아이콘 색상은 커스터마이징이 가능합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedIconButtonSolid(
 *     icon = R.drawable.ic_icon,
 *     size = WantedIconButtonSize.Medium,
 *     onClick = { /* 클릭 처리 */ }
 * )
 * ```
 *
 * @param icon Int: 아이콘으로 사용할 drawable 리소스 ID입니다.
 * @param size WantedIconButtonSize: 버튼 크기와 내부 패딩을 정의하는 enum입니다.
 * @param modifier Modifier: 외형 및 배치를 제어하는 Modifier입니다.
 * @param enabled Boolean: 버튼의 활성화 여부입니다.
 * @param tint Color: 아이콘의 색상입니다. 기본값은 흰색입니다.
 * @param background Color: 배경 색상입니다. 기본값은 primary_normal입니다.
 * @param onClick () -> Unit: 클릭 시 호출되는 콜백입니다.
 */
@Composable
fun WantedIconButtonSolid(
    @DrawableRes icon: Int,
    size: WantedIconButtonSize,
    modifier: Modifier,
    enabled: Boolean = true,
    tint: Color = DesignSystemTheme.colors.staticWhite,
    background: Color = DesignSystemTheme.colors.primaryNormal,
    onClick: () -> Unit = {}
) {
    WantedIconButtonSolid(
        modifier = modifier.size(size.size),
        icon = icon,
        padding = size.padding,
        enabled = enabled,
        tint = tint,
        background = background,
        onClick = onClick
    )
}


/**
 * WantedIconButtonSolid
 *
 * 크기와 패딩을 직접 지정할 수 있는 Solid 스타일 아이콘 버튼입니다.
 *
 * 배경, 아이콘 색상 및 클릭 이벤트를 자유롭게 설정할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedIconButtonSolid(
 *     icon = R.drawable.ic_icon,
 *     modifier = Modifier.size(40.dp),
 *     padding = 8.dp,
 *     onClick = { /* 클릭 처리 */ }
 * )
 * ```
 *
 * @param icon Int: 아이콘으로 사용할 drawable 리소스 ID입니다.
 * @param modifier Modifier: 외형 및 배치를 제어하는 Modifier입니다.
 * @param enabled Boolean: 버튼의 활성화 여부입니다.
 * @param padding Dp: 아이콘 내부 패딩입니다.
 * @param tint Color: 아이콘의 색상입니다. 기본값은 흰색입니다.
 * @param background Color: 배경 색상입니다. 기본값은 primary_normal입니다.
 * @param onClick () -> Unit: 클릭 시 호출되는 콜백입니다.
 */
@Composable
fun WantedIconButtonSolid(
    @DrawableRes icon: Int,
    modifier: Modifier,
    enabled: Boolean = true,
    padding: Dp = 10.dp,
    tint: Color = DesignSystemTheme.colors.staticWhite,
    background: Color = DesignSystemTheme.colors.primaryNormal,
    onClick: () -> Unit = {}
) {
    Icon(
        modifier = modifier
            .clip(CircleShape)
            .background(if (enabled) background else DesignSystemTheme.colors.fillNormal)
            .clickOnce(enabled) { onClick() }
            .padding(padding),
        painter = painterResource(id = icon),
        contentDescription = "",
        tint = if (enabled) tint else DesignSystemTheme.colors.labelDisable
    )
}

@DevicePreviews
@Composable
private fun WantedIconButtonSolidPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {

                WantedIconButtonSolid(
                    modifier = Modifier,
                    size = WantedIconButtonSize.Medium,
                    icon = R.drawable.icon_normal_company,
                    onClick = {}
                )

                WantedIconButtonSolid(
                    modifier = Modifier,
                    size = WantedIconButtonSize.Small,
                    icon = R.drawable.icon_normal_company,
                    onClick = {}
                )

                WantedIconButtonSolid(
                    modifier = Modifier.size(40.dp),
                    icon = R.drawable.icon_normal_company,
                    onClick = {}
                )
            }
        }
    }
}