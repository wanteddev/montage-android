package com.wanted.android.wanted.design.actions.button.iconbutton

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.feedback.pushbadge.WantedPushBadge
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * WantedIconButtonNormal
 *
 * 배경을 제외하고 아이콘만을 표시하는 기본 아이콘 버튼입니다.
 *
 * 필요 시 우측 상단에 PushBadge 컴포넌트을 함께 표시할 수 있으며,
 * 터치 영역은 `WantedTouchArea`를 통해 보장되어 있습니다.
 *
 * 사용 예시 :
 * ```kotlin
 * WantedIconButtonNormal(
 *     icon = R.drawable.ic_icon,
 *     modifier = Modifier.size(24.dp),
 *     pushBadge = {
 *         WantedPushBadge()
 *     },
 *     onClick = { /* 클릭 처리 */ }
 * )
 * ```
 *
 * @param icon Int: 아이콘으로 사용할 drawable 리소스 ID입니다.
 * @param modifier Modifier: 버튼 외형 및 배치를 제어하는 Modifier입니다.
 * @param enabled Boolean: 버튼의 활성화 여부입니다. false일 경우 비활성 색상으로 표시됩니다.
 * @param tint Color: 아이콘의 색상입니다. 기본값은 label_normal입니다.
 * @param pushBadge (@Composable () -> Unit)?: 우측 상단에 표시될 PushBadge 등 컴포넌트입니다.
 * @param onClick () -> Unit: 버튼 클릭 시 호출되는 콜백입니다.
 */
@Composable
fun WantedIconButtonNormal(
    @DrawableRes icon: Int,
    modifier: Modifier,
    enabled: Boolean = true,
    tint: Color = DesignSystemTheme.colors.labelNormal,
    pushBadge: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    WantedTouchArea(
        content = {
            Icon(
                modifier = modifier,
                painter = painterResource(id = icon),
                contentDescription = "",
                tint = if (enabled) tint else DesignSystemTheme.colors.labelDisable,
            )

            pushBadge?.let {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(10.dp, (-10).dp)
                ) {
                    pushBadge()
                }
            }
        },
        enabled = enabled,
        shape = CircleShape,
        horizontalPadding = 8.dp,
        verticalPadding = 8.dp,
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
                    modifier = Modifier.size(24.dp),
                    icon = R.drawable.icon_normal_company,
                    onClick = {}
                )

                WantedIconButtonNormal(
                    modifier = Modifier.size(24.dp),
                    icon = R.drawable.icon_normal_company,
                    pushBadge = {
                        WantedPushBadge()
                    },
                    onClick = {}
                )
            }
        }
    }
}