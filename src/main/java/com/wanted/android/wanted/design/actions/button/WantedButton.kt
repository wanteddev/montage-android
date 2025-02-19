package com.wanted.android.wanted.design.actions.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.actions.button.config.WantedButtonDefault
import com.wanted.android.wanted.design.actions.button.config.WantedButtonDefaults
import com.wanted.android.wanted.design.theme.DesignSystemTheme
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
    isLoading: Boolean = false,
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
                isLoading = isLoading,
                buttonDefault = WantedButtonDefaults.getDefault(
                    shape = buttonShape,
                    type = type,
                    enabled = enabled,
                    size = size
                ),
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
                isLoading = isLoading,
                buttonDefault = WantedButtonDefaults.getDefault(
                    shape = buttonShape,
                    type = type,
                    enabled = enabled,
                    size = size
                ),
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
                isLoading = isLoading,
                buttonDefault = WantedButtonDefaults.getDefault(
                    shape = buttonShape,
                    type = type,
                    enabled = enabled,
                    size = size
                ),
                leftDrawable = leftDrawable,
                rightDrawable = rightDrawable,
                onClick = onClick,
            )
        }
    }
}

@Composable
fun WantedButton(
    modifier: Modifier = Modifier,
    text: String,
    isLoading: Boolean = false,
    buttonDefault: WantedButtonDefault = WantedButtonDefaults.getDefault(),
    leftDrawable: Int? = null,
    rightDrawable: Int? = null,
    onClick: () -> Unit = {}
) {

    when (buttonDefault.shape) {
        ButtonShape.SOLID -> {
            WantedSolidButton(
                modifier = modifier,
                text = text,
                isLoading = isLoading,
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
                isLoading = isLoading,
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
                isLoading = isLoading,
                buttonDefault = buttonDefault,
                leftDrawable = leftDrawable,
                rightDrawable = rightDrawable,
                onClick = onClick,
            )
        }
    }
}


@DevicePreviews
@Composable
private fun WantedAvatarPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "텍스트",
                    buttonShape = ButtonShape.OUTLINED,
                    type = ButtonType.SECONDARY,
                    size = ButtonSize.MEDIUM,
                    onClick = { }
                )

                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "텍스트",
                    buttonDefault = WantedButtonDefaults.getDefault(
                        shape = ButtonShape.OUTLINED,
                        type = ButtonType.SECONDARY,
                        size = ButtonSize.MEDIUM
                    ),
                    onClick = { }
                )
            }
        }
    }
}