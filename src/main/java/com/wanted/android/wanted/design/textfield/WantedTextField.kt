package com.wanted.android.wanted.design.textfield

import android.content.res.Configuration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=14852-42414&m=dev
 */
@Composable
fun WantedTextField(
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
    maxLines: Int = Int.MAX_VALUE,
    requiredBadge: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focused: State<Boolean> = interactionSource.collectIsFocusedAsState(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (String) -> Unit = {}
) {
    WantedTextFieldLayout(
        modifier = modifier,
        title = if (title.isNotEmpty()) {
            {
                WantedTextFieldTitle(
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
                interactionSource = interactionSource,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                rightButton = rightButton,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                rightContent = rightContent,
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

@Composable
private fun WantedTextFieldTitle(
    modifier: Modifier = Modifier,
    title: String,
    isRequiredBadge: Boolean
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f, fill = false),
            text = title,
            style = WantedTextStyle(
                colorRes = R.color.label_neutral,
                style = DesignSystemTheme.typography.label1Bold
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        if (isRequiredBadge) {
            Text(
                modifier = Modifier.wrapContentSize(),
                text = "*",
                style = WantedTextStyle(
                    colorRes = R.color.status_negative,
                    style = DesignSystemTheme.typography.label1Medium
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Composable
private fun WantedTextFieldLayout(
    modifier: Modifier,
    title: @Composable (() -> Unit)? = null,
    textField: @Composable () -> Unit,
    message: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        title?.invoke()

        textField()

        message?.invoke()
    }
}

@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Preview(
    "foldableLight",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "ko",
    device = Devices.FOLDABLE
)
@Composable
private fun WantedTextFieldPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedTextField(
                    value = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요."
                )

                WantedTextField(
                    title = "주제",
                    value = "",
                    placeholder = "텍스트를 입력해 주세요."
                )

                WantedTextField(
                    title = "주제",
                    requiredBadge = true,
                    value = "입력한 텍스트.",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트"
                )

                WantedTextField(
                    value = "텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요.",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트"
                )

                WantedTextField(
                    value = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    complete = true,
                    focused = remember { mutableStateOf(false) }
                )

                WantedTextField(
                    value = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    complete = true,
                    focused = remember { mutableStateOf(true) }
                )

                WantedTextField(
                    value = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    error = true,
                    focused = remember { mutableStateOf(false) }
                )

                WantedTextField(
                    value = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    error = true,
                    focused = remember { mutableStateOf(true) }
                )
            }

        }
    }
}


@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Preview(
    "foldableLight",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "ko",
    device = Devices.FOLDABLE
)
@Composable
private fun WantedTextField1Preview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedTextField(
                    value = "텍스트를 입력해 주세요.",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = R.drawable.button_check_circle_fill_12dp_svg),
                            contentDescription = ""
                        )
                    }
                )

                WantedTextField(
                    value = "텍스트를 입력해 주세요.",
                    placeholder = "텍스트를 입력해 주세요. ",
                    rightButton = "텍스트",
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = R.drawable.button_check_circle_fill_12dp_svg),
                            contentDescription = ""
                        )
                    },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = R.drawable.button_check_circle_fill_12dp_svg),
                            contentDescription = ""
                        )
                    },
                    rightContent = {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = R.drawable.button_check_circle_fill_12dp_svg),
                            contentDescription = ""
                        )
                    }
                )


                WantedTextField(
                    value = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    description = "메시지에 마침표를 찍어요.",
                    focused = remember { mutableStateOf(true) }
                )

                WantedTextField(
                    value = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    error = true,
                    description = "메시지에 마침표를 찍어요.",
                    focused = remember { mutableStateOf(true) }
                )


                WantedTextField(
                    value = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    enabled = false,
                    description = "메시지에 마침표를 찍어요.",
                    focused = remember { mutableStateOf(true) }
                )

                WantedTextField(
                    value = "",
                    placeholder = "텍스트를 입력해 주세요.",
                    enabled = false,
                    description = "메시지에 마침표를 찍어요.",
                    focused = remember { mutableStateOf(true) }
                )

                WantedTextField(
                    value = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    enabled = false,
                    description = "메시지에 마침표를 찍어요.",
                    focused = remember { mutableStateOf(true) }
                )

                WantedTextField(
                    value = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    enabled = false,
                    complete = true,
                    description = "메시지에 마침표를 찍어요.",
                    focused = remember { mutableStateOf(false) }
                )

                WantedTextField(
                    value = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    enabled = false,
                    error = true,
                    description = "메시지에 마침표를 찍어요.",
                    focused = remember { mutableStateOf(true) }
                )
            }
        }
    }
}