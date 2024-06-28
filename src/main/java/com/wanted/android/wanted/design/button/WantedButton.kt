package com.wanted.android.wanted.design.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonStatus
import com.wanted.android.wanted.design.util.ButtonType

@Composable
fun WantedButton(
    modifier: Modifier = Modifier,
    text: String,
    buttonShape: ButtonShape = ButtonShape.SOLID,
    type: ButtonType = ButtonType.PRIMARY,
    size: ButtonSize = ButtonSize.LARGE,
    status: ButtonStatus = ButtonStatus.ENABLE,
    leftDrawable: Int? = null,
    rightDrawable: Int? = null,
    isClickOnce: Boolean = true,
    onClick: () -> Unit = {}
) {

    when (buttonShape) {
        ButtonShape.SOLID -> {
            NewWantedSolidButton(
                modifier = modifier,
                text = text,
                type = type,
                size = size,
                status = status,
                leftDrawable = leftDrawable,
                rightDrawable = rightDrawable,
                clickListener = onClick,
            )
        }

        ButtonShape.OUTLINED -> {
            WantedOutlinedButton(
                modifier = modifier,
                text = text,
                size = size,
                type = type,
                status = status,
                leftDrawable = leftDrawable,
                rightDrawable = rightDrawable,
                isClickOnce = isClickOnce,
                clickListener = onClick,
            )
        }

        ButtonShape.TEXT -> {
            WantedTextButton(
                modifier = modifier,
                text = text,
                size = size,
                type = type,
                status = status,
                leftDrawable = leftDrawable,
                rightDrawable = rightDrawable,
                isClickOnce = isClickOnce,
                clickListener = onClick,
            )
        }
    }
}