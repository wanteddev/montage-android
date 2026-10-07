package com.wanted.android.wanted.design.actions.button.textbutton

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme

// Text Button의 치수 표입니다.
// 배경·테두리가 없어 SOLID/OUTLINED와 min-height·padding 값이 다르므로,
// 공용 WantedButtonUtils(ButtonVariant 기반)를 쓰지 않고 WantedTextButtonSize 만으로 결정합니다.
//
// SMALL/MEDIUM은 Figma Text Button 스펙 값이고,
// LARGE/XSMALL은 스펙에 없는 레거시 축이라 폐기 전까지 기존 렌더를 유지합니다.
@Composable
internal fun Modifier.textButtonHeight(
    size: WantedTextButtonSize
): Modifier {
    val dimension = DesignSystemTheme.dimension
    // fontScale 1.3x/1.5x 등 큰 폰트에서도 텍스트가 잘리지 않도록 heightIn(min)으로 노출.
    val minHeight = when (size) {
        WantedTextButtonSize.LARGE -> dimension.dimension40
        WantedTextButtonSize.MEDIUM -> dimension.dimension32
        WantedTextButtonSize.SMALL -> dimension.dimension28
        WantedTextButtonSize.XSMALL -> dimension.dimension28
    }
    return this.heightIn(min = minHeight)
}

@Composable
internal fun Modifier.textButtonWidth(
    size: WantedTextButtonSize,
    isIconOnly: Boolean
): Modifier {
    if (!isIconOnly) return this
    val dimension = DesignSystemTheme.dimension
    return this.widthIn(
        min = when (size) {
            WantedTextButtonSize.LARGE -> dimension.dimension48
            WantedTextButtonSize.MEDIUM -> dimension.dimension40
            WantedTextButtonSize.SMALL -> dimension.dimension32
            WantedTextButtonSize.XSMALL -> dimension.dimension28
        }
    )
}

@Composable
internal fun Modifier.textButtonVerticalPadding(
    size: WantedTextButtonSize
): Modifier {
    val spacing = DesignSystemTheme.spacing
    val vertical = when (size) {
        // 레거시: 5는 spacing/primitive 토큰 미존재 — hardcoded 유지
        WantedTextButtonSize.LARGE -> 5.dp
        WantedTextButtonSize.MEDIUM -> spacing.spacing4
        WantedTextButtonSize.SMALL -> spacing.spacing4
        WantedTextButtonSize.XSMALL -> spacing.spacing4
    }
    return this.padding(vertical = vertical)
}

@Composable
internal fun Modifier.textButtonDrawableSize(
    size: WantedTextButtonSize
): Modifier {
    val dimension = DesignSystemTheme.dimension
    // 아이콘은 텍스트와 달리 fontScale을 따라가지 않고 사이즈별 스펙 값으로 고정한다.
    // heightIn(min)으로 두면 상위 Row/Box가 높이를 고정하지 않아 maxHeight가 무한이 되고,
    // Image가 drawable의 intrinsic 크기(예: 24dp)로 그려져 스펙보다 크게 보인다.
    // height(고정) + ContentScale.FillHeight 조합이면 너비는 원본 비율대로 따라온다.
    val iconHeight = when (size) {
        WantedTextButtonSize.LARGE -> dimension.dimension18
        WantedTextButtonSize.MEDIUM -> dimension.dimension20
        WantedTextButtonSize.SMALL -> dimension.dimension16
        WantedTextButtonSize.XSMALL -> dimension.dimension14
    }
    return this
        .height(iconHeight)
        .wrapContentWidth()
}

@Composable
internal fun getTextButtonTouchAreaHorizontalPadding(
    size: WantedTextButtonSize
): Dp {
    val spacing = DesignSystemTheme.spacing
    return when (size) {
        // 7은 spacing/primitive 토큰 미존재 — hardcoded 유지
        WantedTextButtonSize.MEDIUM -> 7.dp
        WantedTextButtonSize.SMALL -> spacing.spacing6
        WantedTextButtonSize.LARGE -> spacing.spacing8
        WantedTextButtonSize.XSMALL -> spacing.spacing8
    }
}

@Composable
internal fun getTextButtonTouchAreaShape(
    size: WantedTextButtonSize
): RoundedCornerShape {
    val radius = DesignSystemTheme.radius
    // 스펙 값 6dp에 대응하는 radius 시맨틱 토큰이 없어 primitive를 직접 사용한다.
    val primitive6 = DesignSystemTheme.primitive.primitive6
    return RoundedCornerShape(
        when (size) {
            WantedTextButtonSize.MEDIUM -> primitive6
            WantedTextButtonSize.SMALL -> primitive6
            WantedTextButtonSize.LARGE -> radius.radius10
            WantedTextButtonSize.XSMALL -> radius.radius10
        }
    )
}

@Composable
internal fun getTextButtonContentSpacing(
    size: WantedTextButtonSize
): Dp {
    val spacing = DesignSystemTheme.spacing
    return when (size) {
        WantedTextButtonSize.MEDIUM -> spacing.spacing4
        WantedTextButtonSize.SMALL -> spacing.spacing4
        // 레거시: 5는 spacing/primitive 토큰 미존재 — hardcoded 유지
        WantedTextButtonSize.LARGE -> 5.dp
        WantedTextButtonSize.XSMALL -> 5.dp
    }
}
