package com.wanted.android.wanted.design.util

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.badge.ContentBadgeSize

enum class ButtonShape {
    SOLID, OUTLINED, TEXT
}

enum class ButtonStatus {
    ENABLE, DISABLE
}

enum class ButtonType {
    PRIMARY, SECONDARY, ASSISTIVE
}

enum class ButtonSize {
    LARGE, MEDIUM, SMALL
}

@Composable
internal fun getButtonWidth(buttonWidth: Int): Modifier =
    when (buttonWidth) {
        -1 -> Modifier.fillMaxWidth()
        -2 -> Modifier.wrapContentWidth(align = Alignment.CenterHorizontally)
        else -> Modifier.width(buttonWidth.dp)
    }

@Composable
internal fun getTextButtonSize(buttonWidth: Int, buttonHeight: Int): Modifier {
    val widthModifier = getButtonWidth(buttonWidth = buttonWidth)
    val heightModifier = when (buttonHeight) {
        -1 -> Modifier.fillMaxHeight()
        -2 -> Modifier.wrapContentHeight(align = Alignment.CenterVertically)
        else -> Modifier.height(buttonHeight.dp)
    }

    return Modifier
        .then(widthModifier)
        .then(heightModifier)
}

internal fun getButtonHeight(
    shape: ButtonShape,
    type: ButtonType = ButtonType.PRIMARY,
    size: ButtonSize
): Dp =
    when (size) {
        ButtonSize.LARGE -> if (isLargeSizeButtonExist(shape, type)) 48.dp else 40.dp
        ButtonSize.MEDIUM -> 40.dp
        ButtonSize.SMALL -> 32.dp
    }

internal fun getButtonRadius(
    shape: ButtonShape,
    type: ButtonType = ButtonType.PRIMARY,
    size: ButtonSize
): Dp =
    when (size) {
        ButtonSize.LARGE -> if (isLargeSizeButtonExist(shape, type)) 12.dp else 10.dp
        ButtonSize.MEDIUM -> 10.dp
        ButtonSize.SMALL -> 8.dp
    }

internal fun getButtonSpaceBetweenTextAndIcon(
    shape: ButtonShape,
    type: ButtonType = ButtonType.PRIMARY,
    size: ButtonSize
): Dp =
    when (size) {
        ButtonSize.LARGE -> if (isLargeSizeButtonExist(shape, type)) 6.dp else 5.dp
        ButtonSize.MEDIUM -> 5.dp
        ButtonSize.SMALL -> 4.dp
    }

@Composable
internal fun getButtonTypography(
    shape: ButtonShape,
    type: ButtonType = ButtonType.PRIMARY,
    size: ButtonSize
): TextStyle =
    getTextStyle(
        textStyle =
        when (shape) {
            ButtonShape.SOLID -> when (size) {
                ButtonSize.LARGE -> WantedTextStyle.BODY1_BOLD
                ButtonSize.MEDIUM -> WantedTextStyle.BODY2_BOLD
                ButtonSize.SMALL -> WantedTextStyle.LABEL2_BOLD
            }
            ButtonShape.OUTLINED -> when (type) {
                ButtonType.ASSISTIVE -> when (size) {
                    ButtonSize.SMALL -> WantedTextStyle.LABEL2_MEDIUM
                    else -> WantedTextStyle.BODY2_MEDIUM
                }
                else -> when (size) {
                    ButtonSize.LARGE -> WantedTextStyle.BODY1_BOLD
                    ButtonSize.MEDIUM -> WantedTextStyle.BODY2_BOLD
                    ButtonSize.SMALL -> WantedTextStyle.LABEL2_BOLD
                }
            }
            ButtonShape.TEXT -> when (size) {
                ButtonSize.SMALL -> WantedTextStyle.LABEL1_BOLD
                else -> WantedTextStyle.BODY1_BOLD
            }
        }
    )

@Composable
internal fun  getButtonHorizontalPadding(
    shape: ButtonShape,
    type: ButtonType = ButtonType.PRIMARY,
    size: ButtonSize
): Dp =
    when (size) {
        ButtonSize.LARGE -> if (isLargeSizeButtonExist(shape, type)) 28.dp else 20.dp
        ButtonSize.MEDIUM -> 20.dp
        ButtonSize.SMALL -> 14.dp
    }

@Composable
internal fun getButtonDrawableSize(
    shape: ButtonShape,
    type: ButtonType = ButtonType.PRIMARY,
    size: ButtonSize
): Modifier =
    when (size) {
        ButtonSize.LARGE -> if (isLargeSizeButtonExist(shape, type)) Modifier
            .height(20.dp)
            .wrapContentWidth()
        else Modifier
            .height(18.dp)
            .wrapContentWidth()
        ButtonSize.MEDIUM -> Modifier
            .height(18.dp)
            .wrapContentWidth()
        ButtonSize.SMALL -> Modifier
            .height(16.dp)
            .wrapContentWidth()
    }

private fun isLargeSizeButtonExist(shape: ButtonShape, type: ButtonType): Boolean =
    when (shape) {
        ButtonShape.SOLID -> true
        ButtonShape.OUTLINED -> when (type) {
            ButtonType.ASSISTIVE -> false
            else -> true
        }
        ButtonShape.TEXT -> false
    }

@Composable
internal fun getContentBadgeTypography(
    size: ContentBadgeSize
): TextStyle =
    getTextStyle(
        textStyle =
        when (size) {
            ContentBadgeSize.MEDIUM -> WantedTextStyle.LABEL1_BOLD
            ContentBadgeSize.SMALL -> WantedTextStyle.CAPTION1_BOLD
            ContentBadgeSize.XSMALL -> WantedTextStyle.CAPTION2_BOLD
        }
    )

internal fun getContentBadgeSpaceBetweenTextAndIcon(
    size: ContentBadgeSize
): Dp =
    when (size) {
        ContentBadgeSize.MEDIUM -> 4.dp
        ContentBadgeSize.SMALL -> 3.dp
        ContentBadgeSize.XSMALL -> 2.dp
    }

@Composable
internal fun getContentBadgeDrawableSize(
    size: ContentBadgeSize
): Modifier =
    when (size) {
        ContentBadgeSize.MEDIUM -> Modifier
            .height(16.dp)
            .wrapContentWidth()
        ContentBadgeSize.SMALL -> Modifier
            .height(14.dp)
            .wrapContentWidth()
        ContentBadgeSize.XSMALL -> Modifier
            .size(12.dp)
            .wrapContentWidth()
    }