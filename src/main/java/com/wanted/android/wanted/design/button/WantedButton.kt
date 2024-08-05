package com.wanted.android.wanted.design.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wanted.android.wanted.design.button.config.WantedButtonDefault
import com.wanted.android.wanted.design.button.config.WantedButtonDefaults
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType

/**
 * figma : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=14852-40142&m=dev
 */
@Composable
fun WantedButton(
    modifier: Modifier = Modifier,
    text: String,
    buttonShape: ButtonShape = ButtonShape.SOLID,
    type: ButtonType = ButtonType.PRIMARY,
    size: ButtonSize = ButtonSize.LARGE,
    enabled: Boolean = true,
    buttonDefault: WantedButtonDefault = WantedButtonDefaults.getDefault(
        shape = buttonShape,
        type = type,
        size = size,
        enabled = enabled
    ),
    leftDrawable: Int? = null,
    rightDrawable: Int? = null,
    onClick: () -> Unit = {}
) {

    when (buttonShape) {
        ButtonShape.SOLID -> {
            WantedSolidButton(
                modifier = modifier,
                text = text,
                type = type,
                size = size,
                enabled = enabled,
                buttonDefault = buttonDefault,
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
                enabled = enabled,
                buttonDefault = buttonDefault,
                leftDrawable = leftDrawable,
                rightDrawable = rightDrawable,
                clickListener = onClick,
            )
        }

        ButtonShape.TEXT -> {
            WantedTextButton(
                modifier = modifier,
                text = text,
                size = size,
                type = type,
                enabled = enabled,
                buttonDefault = buttonDefault,
                leftDrawable = leftDrawable,
                rightDrawable = rightDrawable,
                onClick = onClick,
            )
        }
    }
}