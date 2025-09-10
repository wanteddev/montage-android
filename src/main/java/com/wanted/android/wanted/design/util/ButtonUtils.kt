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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.contents.contentbadge.ContentBadgeSize

enum class ButtonVariant {
    SOLID, OUTLINED, TEXT
}

enum class ButtonStatus {
    ENABLE, DISABLE
}

enum class ButtonType {
    PRIMARY, ASSISTIVE
}

enum class ButtonSize {
    LARGE, MEDIUM, SMALL
}

@Composable
internal fun Modifier.getButtonWidth(buttonWidth: Int): Modifier = this.then(
    when (buttonWidth) {
        -1 -> Modifier.fillMaxWidth()
        -2 -> Modifier.wrapContentWidth(align = Alignment.CenterHorizontally)
        else -> Modifier.width(buttonWidth.dp)
    }
)


@Composable
internal fun Modifier.getTextButtonSize(buttonWidth: Int, buttonHeight: Int): Modifier {
    val widthModifier = getButtonWidth(buttonWidth = buttonWidth)
    val heightModifier = when (buttonHeight) {
        -1 -> Modifier.fillMaxHeight()
        -2 -> Modifier.wrapContentHeight(align = Alignment.CenterVertically)
        else -> Modifier.height(buttonHeight.dp)
    }

    return Modifier
        .then(this)
        .then(widthModifier)
        .then(heightModifier)

}

internal fun getButtonRadius(
    variant: ButtonVariant,
    size: ButtonSize
): Dp =
        when (size) {
            ButtonSize.LARGE -> if (isLargeSizeButtonExist(variant)) 12.dp else 10.dp
            ButtonSize.MEDIUM -> 10.dp
            ButtonSize.SMALL -> 8.dp
        }

internal fun getButtonSpaceBetweenTextAndIcon(
    variant: ButtonVariant,
    size: ButtonSize
): Dp =
        when (size) {
            ButtonSize.LARGE -> if (isLargeSizeButtonExist(variant)) 6.dp else 5.dp
            ButtonSize.MEDIUM -> 5.dp
            ButtonSize.SMALL -> 4.dp
        }


@Composable
internal fun Modifier.getButtonDrawableSize(
    variant: ButtonVariant,
    size: ButtonSize
): Modifier = this.then(
    when (size) {
        ButtonSize.LARGE -> if (isLargeSizeButtonExist(variant)) Modifier
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
)

private fun isLargeSizeButtonExist(variant: ButtonVariant): Boolean =
        when (variant) {
            ButtonVariant.SOLID -> true
            ButtonVariant.OUTLINED -> true
            ButtonVariant.TEXT -> false
        }

@Composable
internal fun Modifier.getContentBadgeDrawableSize(
    size: ContentBadgeSize
): Modifier = this.then(
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
)