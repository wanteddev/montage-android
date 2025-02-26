package com.wanted.android.wanted.design.input.textinput

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.input.ComponentTitle
import com.wanted.android.wanted.design.input.textinput.view.WantedCustomTextField
import com.wanted.android.wanted.design.input.textinput.view.WantedTextInputLayout
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.WantedTextStyle

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=14852-42414&m=dev
 * 설명 : https://www.figma.com/design/MK6KmtXBxX7ZkoQXfD9MFH/%EA%B0%9C%EC%84%A0%3A-Components?node-id=1915-22967&t=33KjAy2RlyzyhLH6-4
 */
@Composable
fun WantedTextField(
    modifier: Modifier = Modifier,
    text: String,
    placeholder: String = "",
    title: String = "",
    description: String? = null,
    rightButton: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    rightContent: @Composable (() -> Unit)? = null,
    rightButtonVariant: WantedTextInputRightVariant = WantedTextInputRightVariant.Normal,
    status: WantedTextInputContract.Status = WantedTextInputContract.Status.Normal,
    enabled: Boolean = true,
    rightButtonEnabled: Boolean = true,
    maxLines: Int = 1,
    minLines: Int = 1,
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
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = text)) }
    val textFieldValue = textFieldValueState.copy(text = text)

    SideEffect {
        if (textFieldValue.selection != textFieldValueState.selection ||
            textFieldValue.composition != textFieldValueState.composition
        ) {
            textFieldValueState = textFieldValue
        }
    }
    var lastTextValue by remember(text) { mutableStateOf(text) }

    WantedTextInputLayout(
        modifier = modifier,
        title = if (title.isNotEmpty()) {
            {
                ComponentTitle(
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
                value = textFieldValue,
                error = status == WantedTextInputContract.Status.Negative,
                enabled = enabled,
                rightButtonEnabled = rightButtonEnabled,
                focused = focused.value,
                complete = status == WantedTextInputContract.Status.Positive,
                maxLines = maxLines,
                minLines = minLines,
                maxWordCount = maxWordCount,
                interactionSource = interactionSource,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                background = background,
                rightButton = rightButton,
                rightButtonVariant = rightButtonVariant,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                rightContent = rightContent,
                onClickRightButton = onClickRightButton,
                onValueChange = { newTextFieldValueState ->
                    textFieldValueState = newTextFieldValueState

                    val stringChangedSinceLastInvocation =
                        lastTextValue != newTextFieldValueState.text
                    lastTextValue = newTextFieldValueState.text

                    if (stringChangedSinceLastInvocation) {
                        onValueChange(newTextFieldValueState.text)
                    }
                }
            )
        },
        message = if (!description.isNullOrEmpty()) {
            {
                Text(
                    text = description,
                    style = WantedTextStyle(
                        colorRes = when {
                            enabled && status == WantedTextInputContract.Status.Negative -> R.color.status_negative
                            else -> R.color.label_alternative
                        },
                        style = DesignSystemTheme.typography.caption1Regular
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        } else null
    )
}


@Composable
fun WantedTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    placeholder: String = "",
    title: String = "",
    description: String? = null,
    rightButton: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    rightContent: @Composable (() -> Unit)? = null,
    rightButtonVariant: WantedTextInputRightVariant = WantedTextInputRightVariant.Normal,
    status: WantedTextInputContract.Status = WantedTextInputContract.Status.Normal,
    enabled: Boolean = true,
    rightButtonEnabled: Boolean = true,
    maxLines: Int = 1,
    minLines: Int = 1,
    maxWordCount: Int = 2000,
    requiredBadge: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focused: State<Boolean> = interactionSource.collectIsFocusedAsState(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = colorResource(id = R.color.background_normal_normal),
    onClickRightButton: () -> Unit = {},
    onValueChange: (TextFieldValue) -> Unit = {}
) {
    WantedTextInputLayout(
        modifier = modifier,
        title = if (title.isNotEmpty()) {
            {
                ComponentTitle(
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
                error = status == WantedTextInputContract.Status.Negative,
                enabled = enabled,
                rightButtonEnabled = rightButtonEnabled,
                focused = focused.value,
                complete = status == WantedTextInputContract.Status.Positive,
                maxLines = maxLines,
                minLines = minLines,
                maxWordCount = maxWordCount,
                interactionSource = interactionSource,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                background = background,
                rightButton = rightButton,
                rightButtonVariant = rightButtonVariant,
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
                            enabled && status == WantedTextInputContract.Status.Negative -> R.color.status_negative
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


enum class WantedTextInputRightVariant {
    Normal, Assistive
}


@DevicePreviews
@Composable
private fun WantedTextFieldPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    focused = remember { mutableStateOf(true) }
                )

                WantedTextField(
                    title = "주제",
                    requiredBadge = true,
                    text = "입력한 텍스트.",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트"
                )

                WantedTextField(
                    title = "",
                    text = "",
                    placeholder = "텍스트를 입력해 주세요.",
                    enabled = false,
                    status = WantedTextInputContract.Status.Negative
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    enabled = false,
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    status = WantedTextInputContract.Status.Negative
                )

                WantedTextField(
                    requiredBadge = true,
                    text = "입력한 텍스트.",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    enabled = false
                )

                WantedTextField(
                    text = "텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요.",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    rightButtonVariant = WantedTextInputRightVariant.Assistive
                )

                WantedTextField(
                    text = "텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요.",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    rightButtonVariant = WantedTextInputRightVariant.Assistive,
                    rightButtonEnabled = false
                )

                WantedTextField(
                    text = "텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요.",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    rightButtonEnabled = false
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    status = WantedTextInputContract.Status.Positive,
                    focused = remember { mutableStateOf(false) }
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    status = WantedTextInputContract.Status.Positive,
                    focused = remember { mutableStateOf(true) }
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    status = WantedTextInputContract.Status.Positive,
                    focused = remember { mutableStateOf(false) }
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    status = WantedTextInputContract.Status.Positive,
                    focused = remember { mutableStateOf(true) }
                )
            }

        }
    }
}


@DevicePreviews
@Composable
private fun WantedTextInputPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedTextField(
                    text = "텍스트를 입력해 주세요.",
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
                    text = "텍스트를 입력해 주세요.",
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
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    description = "메시지에 마침표를 찍어요.",
                    focused = remember { mutableStateOf(true) }
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    status = WantedTextInputContract.Status.Negative,
                    description = "메시지에 마침표를 찍어요.",
                    focused = remember { mutableStateOf(true) }
                )


                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    enabled = false,
                    description = "메시지에 마침표를 찍어요.",
                    focused = remember { mutableStateOf(true) }
                )

                WantedTextField(
                    text = "",
                    placeholder = "텍스트를 입력해 주세요.",
                    enabled = false,
                    description = "메시지에 마침표를 찍어요.",
                    focused = remember { mutableStateOf(true) }
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    enabled = false,
                    description = "메시지에 마침표를 찍어요.",
                    focused = remember { mutableStateOf(true) }
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    enabled = false,
                    status = WantedTextInputContract.Status.Positive,
                    description = "메시지에 마침표를 찍어요.",
                    focused = remember { mutableStateOf(false) }
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    enabled = false,
                    status = WantedTextInputContract.Status.Negative,
                    description = "메시지에 마침표를 찍어요.",
                    focused = remember { mutableStateOf(true) }
                )
            }
        }
    }
}

