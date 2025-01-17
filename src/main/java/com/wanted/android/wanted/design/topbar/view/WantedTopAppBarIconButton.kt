package com.wanted.android.wanted.design.topbar.view

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
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.topbar.WantedTopAppBarContract.TopAppBarType

@Composable
fun WantedTopAppBarIconButton(
    modifier: Modifier = Modifier,
    type: TopAppBarType = LocalWantedTopBarIconType.current,
    painter: Painter,
    enabled: Boolean = true,
    floatingStyleAlternative: Boolean = false,
    floatingStyleBackground: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    tint: Color = colorResource(id = R.color.label_normal),
    onClick: () -> Unit
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
                onClick = { onClick.clickOnceForDesignSystem() }
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
                onClick = { onClick.clickOnceForDesignSystem() }
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
                onClick = { onClick.clickOnceForDesignSystem() }
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

