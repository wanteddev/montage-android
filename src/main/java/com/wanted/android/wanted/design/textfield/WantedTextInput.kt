package com.wanted.android.wanted.design.textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedComponentTitle
import com.wanted.android.wanted.design.textfield.view.WantedCustomTextField
import com.wanted.android.wanted.design.textfield.view.WantedTextInputLayout
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=14852-42414&m=dev
 * 설명 : https://www.figma.com/design/MK6KmtXBxX7ZkoQXfD9MFH/%EA%B0%9C%EC%84%A0%3A-Components?node-id=1915-22967&t=33KjAy2RlyzyhLH6-4
 */
@Composable
fun WantedTextInput(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String = "",
    title: String = "",
    description: String? = null,
    rightButton: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    rightContent: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    error: Boolean = false,
    complete: Boolean = false,
    maxLines: Int = 1,
    maxWordCount: Int = 2000,
    requiredBadge: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focused: State<Boolean> = interactionSource.collectIsFocusedAsState(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = colorResource(id = R.color.background_normal_normal),
    onClickRightButton: () -> Unit = {},
    onValueChange: (String) -> Unit = {}
) {
    WantedTextInputLayout(
        modifier = modifier,
        title = if (title.isNotEmpty()) {
            {
                WantedComponentTitle(
                    title = title,
                    isRequiredBadge = requiredBadge
                )
            }
        } else {
            null
        },
        textField = {
            WantedCustomTextField(
                modifier = Modifier,
                value = value,
                error = error,
                enabled = enabled,
                focused = focused.value,
                complete = complete,
                maxLines = maxLines,
                maxWordCount = maxWordCount,
                interactionSource = interactionSource,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                background = background,
                rightButton = rightButton,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                rightContent = rightContent,
                onClickRightButton = onClickRightButton,
                onValueChange = onValueChange
            )
        },
        message = description?.let {
            {
                Text(
                    text = description,
                    style = WantedTextStyle(
                        colorRes = when {
                            enabled && error -> R.color.status_negative
                            else -> R.color.label_alternative
                        },
                        style = DesignSystemTheme.typography.caption1Regular
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    )
}

