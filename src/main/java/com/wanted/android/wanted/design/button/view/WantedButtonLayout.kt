package com.wanted.android.wanted.design.button.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.getButtonRadius
import com.wanted.android.wanted.design.util.getButtonSpaceBetweenTextAndIcon

@Composable
fun WantedButtonLayout(
    modifier: Modifier,
    buttonShape: ButtonShape = ButtonShape.SOLID,
    buttonSize: ButtonSize = ButtonSize.LARGE,
    text: @Composable (() -> Unit)? = null,
    leftDrawable: @Composable (() -> Unit)? = null,
    rightDrawable: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(size = getButtonRadius(buttonShape, buttonSize)))
            .then(modifier)
            .buttonHeight(buttonShape, buttonSize)
            .buttonWidth(buttonSize, text == null)
            .buttonVerticalPadding(buttonShape != ButtonShape.TEXT && text != null)
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
fun WantedTextButtonLayout(
    modifier: Modifier,
    buttonShape: ButtonShape = ButtonShape.SOLID,
    buttonSize: ButtonSize = ButtonSize.LARGE,
    text: @Composable (() -> Unit)? = null,
    leftDrawable: @Composable (() -> Unit)? = null,
    rightDrawable: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(size = getButtonRadius(buttonShape, buttonSize))),
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
    shape == ButtonShape.TEXT -> this

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
    isUseVerticalPadding: Boolean
): Modifier = when {
    isUseVerticalPadding -> this.padding(vertical = 4.dp)
    else -> this
}

