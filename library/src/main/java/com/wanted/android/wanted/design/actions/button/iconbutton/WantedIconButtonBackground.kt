package com.wanted.android.wanted.design.actions.button.iconbutton

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_22
import com.wanted.android.wanted.design.util.OPACITY_35
import com.wanted.android.wanted.design.util.OPACITY_5
import com.wanted.android.wanted.design.util.OPACITY_61
import com.wanted.android.wanted.design.util.OPACITY_88

/**
 * WantedIconButton
 *
 * 아이콘을 버튼 요소로 사용하기 위해 확대된 터치 영역을 제공합니다.
 *
 * 활성/비활성 상태, 배경 스타일, 아이콘 색상 등 다양한 시각적 스타일을 지정할 수 있으며,
 * 넓은 터치 영역을 통해 사용자 편의성을 높입니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedIconButtonBackground(
 *     icon = R.drawable.ic_icon,
 *     modifier = Modifier.size(24.dp),
 *     alternative = true,
 *     enabled = true,
 *     onClick = { /* 클릭 처리 */ }
 * )
 * ```
 *
 * @param icon Int: 아이콘으로 사용할 drawable 리소스 ID입니다.
 * @param modifier Modifier: 버튼 외형 및 배치를 제어하는 Modifier입니다.
 * @param alternative Boolean: true일 경우 대체 배경 색상을 적용합니다.
 * @param enabled Boolean: 버튼의 활성화 여부를 지정합니다. false일 경우 흐리게 표시됩니다.
 * @param tint Color: 아이콘의 색상입니다. 기본값은 회색 계열이며 alternative에 따라 달라집니다.
 * @param onClick () -> Unit: 버튼 클릭 시 호출되는 콜백입니다.
 */
@Composable
fun WantedIconButtonBackground(
    @DrawableRes icon: Int,
    modifier: Modifier,
    alternative: Boolean = false,
    enabled: Boolean = true,
    tint: Color = colorResource(id = R.color.cool_neutral_50)
        .copy(alpha = if (alternative) OPACITY_88 else OPACITY_61),
    onClick: () -> Unit = {}
) {
    val contentSize = remember { mutableStateOf(0.dp) }
    val localDensity = LocalDensity.current

    WantedTouchArea(
        content = {
            Icon(
                modifier = Modifier
                    .background(
                        enabled = enabled,
                        alternative = alternative,
                        padding = 4.dp,
                        size = contentSize.value
                    )
                    .clip(CircleShape)
                    .then(modifier)
                    .onGloballyPositioned { coordinates ->
                        // Set column height using the LayoutCoordinates
                        contentSize.value = with(localDensity) { coordinates.size.width.toDp() }
                    }
                    .padding(2.dp),
                painter = painterResource(id = icon),
                contentDescription = "",
                tint = if (enabled) tint else colorResource(id = R.color.cool_neutral_50).copy(alpha = OPACITY_22)
            )
        },
        enabled = enabled,
        shape = CircleShape,
        horizontalPadding = 4.dp,
        verticalPadding = 4.dp,
        onClick = onClick
    )
}

@Composable
private fun Modifier.background(
    alternative: Boolean = false,
    enabled: Boolean = true,
    padding: Dp,
    size: Dp
): Modifier {
    val localDensity = LocalDensity.current
    val modifier = when {
        !enabled -> {
            val color = DesignSystemTheme.colors.fillAlternative
            Modifier.drawBehind {
                drawCircle(
                    color = color,
                    radius = with(localDensity) { (size + padding * 2).toPx() } / 2,
                    center = center,
                    style = Fill
                )
            }
        }

        alternative -> {
            val color = colorResource(id = R.color.cool_neutral_30)

            Modifier.drawBehind {
                drawCircle(
                    color = color,
                    radius = with(localDensity) { (size + padding * 2).toPx() } / 2,
                    center = center,
                    style = Fill
                )
            }
        }

        else -> {
            val color = DesignSystemTheme.colors.staticWhite.copy(OPACITY_35)
            val color1 = DesignSystemTheme.colors.staticBlack.copy(OPACITY_5)
            Modifier.drawBehind {
                drawCircle(
                    color = color,
                    radius = with(localDensity) { (size + padding * 2).toPx() } / 2,
                    center = center,
                    style = Fill
                )

                drawCircle(
                    color = color1,
                    radius = with(localDensity) { (size + padding * 2).toPx() } / 2,
                    center = center,
                    style = Fill
                )
            }
        }
    }

    return this.then(modifier)
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
                    modifier = Modifier.size(24.dp),
                    icon = R.drawable.icon_normal_company,
                    onClick = {}
                )

                WantedIconButtonBackground(
                    modifier = Modifier.size(24.dp),
                    icon = R.drawable.icon_normal_company,
                    alternative = true,
                    onClick = {}
                )

                WantedIconButtonBackground(
                    modifier = Modifier.size(24.dp),
                    icon = R.drawable.icon_normal_company,
                    enabled = false,
                    onClick = {}
                )

                WantedIconButtonBackground(
                    modifier = Modifier.size(24.dp),
                    icon = R.drawable.icon_normal_company,
                    alternative = true,
                    enabled = false,
                    onClick = {}
                )
            }
        }
    }
}