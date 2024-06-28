package com.wanted.android.wanted.design.button.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.getButtonRadius
import com.wanted.android.wanted.design.util.getButtonSpaceBetweenTextAndIcon
import com.wanted.android.wanted.design.util.wantedRippleEffect

@Composable
fun WantedButtonLayout(
    modifier: Modifier,
    buttonShape: ButtonShape = ButtonShape.SOLID,
    buttonSize: ButtonSize = ButtonSize.LARGE,
    isEnable: Boolean = true,
    text: @Composable (() -> Unit)? = null,
    leftDrawable: @Composable (() -> Unit)? = null,
    rightDrawable: @Composable (() -> Unit)? = null,
    clickListener: () -> Unit,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(size = getButtonRadius(buttonShape, buttonSize)))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = getWantedRippleEffect(buttonShape, isEnable),
                onClick = {
                    if (isEnable) {
                        clickListener.clickOnceForDesignSystem()
                    }
                }
            )
            .buttonHeight(buttonShape, buttonSize)
            .buttonWidth(buttonSize, text == null)
            .buttonVerticalPadding(text == null)
            .buttonHorizontalPadding(buttonShape, buttonSize, text == null),
        horizontalArrangement = Arrangement.spacedBy(
            space = getButtonSpaceBetweenTextAndIcon(
                buttonShape,
                buttonSize
            ),
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leftDrawable?.let {
            leftDrawable()
        }

        text?.let {
            Box(
                Modifier.wrapContentHeight(),
                contentAlignment = Alignment.Center
            ) {
                text()
            }
        }

        rightDrawable?.let {
            rightDrawable()
        }
    }
}

@Composable
private fun Modifier.buttonHeight(
    shape: ButtonShape,
    size: ButtonSize
): Modifier = if (shape == ButtonShape.TEXT) {
    this
        .height(
            height = when (size) {
                ButtonSize.LARGE -> 32.dp
                else -> 28.dp
            }
        )
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
private fun Modifier.buttonWidth(
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
private fun Modifier.buttonHorizontalPadding(
    shape: ButtonShape,
    size: ButtonSize,
    isIconOnly: Boolean
): Modifier = when {
    isIconOnly -> this
    shape == ButtonShape.TEXT -> {
        this.padding(horizontal = 6.dp)
    }

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
private fun Modifier.buttonVerticalPadding(
    isIconOnly: Boolean
): Modifier = when {
    isIconOnly -> this
    else -> this.padding(vertical = 4.dp)
}


@Composable
private fun getWantedRippleEffect(
    buttonShape: ButtonShape,
    isEnable: Boolean
) = if (isEnable) {
    wantedRippleEffect(
        if (buttonShape == ButtonShape.TEXT) {
            colorResource(id = R.color.primary_normal_opacity12)
        } else {
            colorResource(id = R.color.label_normal_opacity12)
        }
    )
} else {
    null
}
