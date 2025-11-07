package com.wanted.android.wanted.design.actions.button.config

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonVariant


@Composable
internal fun Modifier.buttonDrawableSize(
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

@Composable
internal fun Modifier.buttonHeight(
    shape: ButtonVariant,
    size: ButtonSize
): Modifier = if (shape == ButtonVariant.TEXT) {
    this
} else {
    this
        .height(
            height = when (size) {
                ButtonSize.LARGE -> 48.dp
                ButtonSize.MEDIUM -> 40.dp
                else -> 32.dp
            }
        )
}

@Composable
internal fun Modifier.buttonWidth(
    size: ButtonSize,
    isIconOnly: Boolean
): Modifier = when {
    isIconOnly -> this.width(
        width = when (size) {
            ButtonSize.LARGE -> 48.dp
            ButtonSize.MEDIUM -> 40.dp
            else -> 32.dp
        }
    )

    else -> this
}

@Composable
internal fun Modifier.buttonHorizontalPadding(
    shape: ButtonVariant,
    size: ButtonSize,
    isIconOnly: Boolean
): Modifier = when {
    isIconOnly -> this
    shape == ButtonVariant.TEXT -> this

    else -> {
        this.padding(
            horizontal = when (size) {
                ButtonSize.LARGE -> 28.dp
                ButtonSize.MEDIUM -> 20.dp
                else -> 14.dp
            }
        )
    }
}

@Composable
internal fun Modifier.buttonVerticalPadding(
    isUseVerticalPadding: Boolean
): Modifier = when {
    isUseVerticalPadding -> this.padding(vertical = 4.dp)
    else -> this
}


private fun isLargeSizeButtonExist(variant: ButtonVariant): Boolean =
        when (variant) {
            ButtonVariant.SOLID -> true
            ButtonVariant.OUTLINED -> true
            ButtonVariant.TEXT -> false
        }
