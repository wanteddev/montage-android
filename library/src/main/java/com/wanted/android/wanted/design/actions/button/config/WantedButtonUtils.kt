package com.wanted.android.wanted.design.actions.button.config

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonSize

// SOLID/OUTLINED 전용 치수 표입니다.
// TEXT의 치수는 WantedTextButtonUtils가 WantedTextButtonSize 기준으로 따로 소유합니다.

@Composable
internal fun Modifier.buttonDrawableSize(
    size: ButtonSize
): Modifier {
    val dimension = DesignSystemTheme.dimension
    // 아이콘은 텍스트와 달리 fontScale을 따라가지 않고 사이즈별 스펙 값으로 고정한다.
    // heightIn(min)으로 두면 상위 Row/Box가 높이를 고정하지 않아 maxHeight가 무한이 되고,
    // Image가 drawable의 intrinsic 크기(예: 24dp)로 그려져 스펙보다 크게 보인다.
    // height(고정) + ContentScale.FillHeight 조합이면 너비는 원본 비율대로 따라온다.
    val iconHeight = when (size) {
        ButtonSize.LARGE -> dimension.dimension20
        ButtonSize.MEDIUM -> dimension.dimension18
        ButtonSize.SMALL -> dimension.dimension16
        ButtonSize.XSMALL -> dimension.dimension14
    }
    return this
        .height(iconHeight)
        .wrapContentWidth()
}

@Composable
internal fun Modifier.buttonHeight(
    size: ButtonSize
): Modifier {
    val dimension = DesignSystemTheme.dimension
    // fontScale 1.3x/1.5x 등 큰 폰트에서도 텍스트가 잘리지 않도록 heightIn(min)으로 노출.
    val minHeight = when (size) {
        ButtonSize.LARGE -> dimension.dimension48
        ButtonSize.MEDIUM -> dimension.dimension40
        ButtonSize.SMALL -> dimension.dimension32
        ButtonSize.XSMALL -> dimension.dimension28
    }
    return this.heightIn(min = minHeight)
}

@Composable
internal fun Modifier.buttonWidth(
    size: ButtonSize,
    isIconOnly: Boolean
): Modifier = when {
    isIconOnly -> {
        val dimension = DesignSystemTheme.dimension
        this.widthIn(
            min = when (size) {
                ButtonSize.LARGE -> dimension.dimension48
                ButtonSize.MEDIUM -> dimension.dimension40
                ButtonSize.SMALL -> dimension.dimension32
                ButtonSize.XSMALL -> dimension.dimension28
            }
        )
    }

    else -> this
}

@Composable
internal fun Modifier.buttonHorizontalPadding(
    size: ButtonSize,
    isIconOnly: Boolean
): Modifier = when {
    isIconOnly -> this

    else -> {
        val spacing = DesignSystemTheme.spacing
        this.padding(
            horizontal = when (size) {
                ButtonSize.LARGE -> spacing.spacing20
                ButtonSize.MEDIUM -> spacing.spacing16
                ButtonSize.SMALL -> spacing.spacing12
                ButtonSize.XSMALL -> spacing.spacing10
            }
        )
    }
}

@Composable
internal fun Modifier.buttonVerticalPadding(
    size: ButtonSize,
    isUseVerticalPadding: Boolean
): Modifier {
    if (!isUseVerticalPadding) return this
    val spacing = DesignSystemTheme.spacing
    val vertical = when (size) {
        // 13은 spacing/primitive 토큰 미존재 — hardcoded 유지
        ButtonSize.LARGE -> 13.dp
        ButtonSize.MEDIUM -> spacing.spacing10
        ButtonSize.SMALL -> spacing.spacing8
        ButtonSize.XSMALL -> spacing.spacing6
    }
    return this.padding(vertical = vertical)
}
