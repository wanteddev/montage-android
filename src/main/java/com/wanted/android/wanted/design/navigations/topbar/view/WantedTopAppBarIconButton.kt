package com.wanted.android.wanted.design.navigations.topbar.view

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.clickOnce
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarContract.TopAppBarType

/**
 * TopAppBar에 사용되는 아이콘 버튼 컴포저블입니다.
 *
 * Floating 타입에 따라 다양한 배경 스타일이 적용됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedTopAppBarIconButton(
 *     painter = painterResource(id = R.drawable.ic_normal_arrow_left_svg),
 *     onClick = { /* 클릭 동작 */ }
 * )
 * ```
 *
 * @param painter Painter: 아이콘으로 표시할 이미지입니다.
 * @param modifier Modifier: 버튼의 크기, 외형, 배치를 조정하는 Modifier입니다.
 * @param type TopAppBarType: 앱바 타입으로 스타일에 영향을 미칩니다.
 * @param enabled Boolean: 버튼 활성화 여부입니다.
 * @param floatingStyleAlternative Boolean: Floating 타입의 대체 스타일 여부입니다.
 * @param floatingStyleBackground Boolean: Floating 타입의 기본 배경 스타일 사용 여부입니다.
 * @param interactionSource MutableInteractionSource: 사용자 인터랙션을 처리하는 객체입니다.
 * @param tint Color: 아이콘 색상입니다.
 * @param onClick () -> Unit: 버튼 클릭 시 실행되는 콜백입니다.
 */
@Composable
fun WantedTopAppBarIconButton(
    painter: Painter,
    modifier: Modifier = Modifier,
    type: TopAppBarType = LocalWantedTopBarIconType.current,
    enabled: Boolean = true,
    floatingStyleAlternative: Boolean = false,
    floatingStyleBackground: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    tint: Color = colorResource(id = R.color.label_normal),
    onClick: () -> Unit = {}
) {
    /**
     * 시스템에 정의되어 있는 IconButton의 default size 56.dp
     * size를 40으로 줄이면 ripple 효과만 56.dp 로 보인다.
     */
    when {
        type == TopAppBarType.Floating && floatingStyleAlternative -> {
            IconButton(
                modifier = modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .padding(4.dp)
                    .background(colorResource(id = R.color.cool_neutral_30).copy(alpha = 0.18f)),
                enabled = enabled,
                interactionSource = interactionSource,
                onClick = { onClick.clickOnce() }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painter,
                    contentDescription = null,
                    tint = tint
                )
            }
        }

        type == TopAppBarType.Floating && floatingStyleBackground -> {
            IconButton(
                modifier = modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .padding(4.dp)
                    .background(colorResource(id = R.color.static_black).copy(alpha = 0.05f))
                    .background(colorResource(id = R.color.static_white).copy(alpha = 0.35f)),
                enabled = enabled,
                interactionSource = interactionSource,
                onClick = { onClick.clickOnce() }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painter,
                    contentDescription = null,
                    tint = tint
                )
            }
        }

        else -> {
            IconButton(
                modifier = modifier
                    .size(40.dp),
                enabled = enabled,
                interactionSource = interactionSource,
                onClick = { onClick.clickOnce() }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painter,
                    contentDescription = null,
                    tint = tint
                )
            }
        }
    }
}


val LocalWantedTopBarIconType = WantedTopBarIconTypeCompositionLocal()


@JvmInline
value class WantedTopBarIconTypeCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<TopAppBarType> = staticCompositionLocalOf { TopAppBarType.Normal }
) {
    val current: TopAppBarType
        @Composable get() = delegate.current

    infix fun provides(value: TopAppBarType) = delegate provides value
}

