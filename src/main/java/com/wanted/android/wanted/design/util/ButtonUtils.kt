package com.wanted.android.wanted.design.util

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.contents.contentbadge.ContentBadgeSize

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
    size: ButtonSize
): Dp =
    when (size) {
        ButtonSize.LARGE -> if (isLargeSizeButtonExist(shape)) 48.dp else 40.dp
        ButtonSize.MEDIUM -> 40.dp
        ButtonSize.SMALL -> 32.dp
    }

internal fun getButtonRadius(
    shape: ButtonShape,
    size: ButtonSize
): Dp =
    when (size) {
        ButtonSize.LARGE -> if (isLargeSizeButtonExist(shape)) 12.dp else 10.dp
        ButtonSize.MEDIUM -> 10.dp
        ButtonSize.SMALL -> 8.dp
    }

internal fun getButtonSpaceBetweenTextAndIcon(
    shape: ButtonShape,
    size: ButtonSize
): Dp =
    when (size) {
        ButtonSize.LARGE -> if (isLargeSizeButtonExist(shape)) 6.dp else 5.dp
        ButtonSize.MEDIUM -> 5.dp
        ButtonSize.SMALL -> 4.dp
    }

@Composable
internal fun getButtonTypography(
    shape: ButtonShape,
    type: ButtonType,
    size: ButtonSize
): TextStyle =
    getTextStyle(
        textStyle =
        when (shape) {
            ButtonShape.TEXT -> when (size) {
                ButtonSize.SMALL -> WantedTextStyle.LABEL1_BOLD
                else -> WantedTextStyle.BODY1_BOLD
            }

            else -> if (type == ButtonType.ASSISTIVE) {
                when (size) {
                    ButtonSize.LARGE -> WantedTextStyle.BODY1_BOLD
                    ButtonSize.MEDIUM -> WantedTextStyle.BODY2_BOLD
                    ButtonSize.SMALL -> WantedTextStyle.LABEL2_BOLD
                }
            } else {
                when (size) {
                    ButtonSize.LARGE -> WantedTextStyle.BODY1_MEDIUM
                    ButtonSize.MEDIUM -> WantedTextStyle.BODY2_MEDIUM
                    ButtonSize.SMALL -> WantedTextStyle.LABEL2_MEDIUM
                }
            }
        }
    )

@Composable
internal fun getButtonHorizontalPadding(
    shape: ButtonShape,
    size: ButtonSize
): Dp =
    when (size) {
        ButtonSize.LARGE -> if (isLargeSizeButtonExist(shape)) 28.dp else 20.dp
        ButtonSize.MEDIUM -> 20.dp
        ButtonSize.SMALL -> 14.dp
    }

@Composable
internal fun getButtonDrawableSize(
    shape: ButtonShape,
    size: ButtonSize
): Modifier =
    when (size) {
        ButtonSize.LARGE -> if (isLargeSizeButtonExist(shape)) Modifier
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

private fun isLargeSizeButtonExist(shape: ButtonShape): Boolean =
    when (shape) {
        ButtonShape.SOLID -> true
        ButtonShape.OUTLINED -> true
        ButtonShape.TEXT -> false
    }

@Composable
internal fun getContentBadgeDrawableSize(
    size: ContentBadgeSize
): Modifier =
    when (size) {
        ContentBadgeSize.Large -> Modifier
            .height(16.dp)
            .wrapContentWidth()

        ContentBadgeSize.Small -> Modifier
            .height(14.dp)
            .wrapContentWidth()

        ContentBadgeSize.XSmall -> Modifier
            .size(12.dp)
            .wrapContentWidth()
    }