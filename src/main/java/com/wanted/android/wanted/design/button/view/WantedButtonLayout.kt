package com.wanted.android.wanted.design.button.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
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
            .buttonHeight(buttonShape, buttonSize)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = if (isEnable) wantedRippleEffect() else null,
                onClick = {
                    if (isEnable) {
                        clickListener.clickOnceForDesignSystem()
                    }
                }
            )
            .buttonHorizontalPadding(buttonShape, buttonSize),
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

        Box(
            Modifier.wrapContentHeight(),
            contentAlignment = Alignment.Center
        ) {
            text?.invoke()
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
    this.wrapContentHeight()
} else {
    this.height(
        height = when (size) {
            ButtonSize.LARGE -> 48.dp
            ButtonSize.MEDIUM -> 40.dp
            else -> 32.dp
        }
    )
}

@Composable
private fun Modifier.buttonHorizontalPadding(
    shape: ButtonShape,
    size: ButtonSize
): Modifier = if (shape == ButtonShape.TEXT) {
    this.padding(horizontal = 6.dp)
} else {
    this.padding(
        horizontal = when (size) {
            ButtonSize.LARGE -> 28.dp
            ButtonSize.MEDIUM -> 20.dp
            else -> 14.dp
        }
    )
}
