package com.wanted.android.wanted.design.textfield

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.button.WantedButton
import com.wanted.android.wanted.design.icon.WantedCommonIcon
import com.wanted.android.wanted.design.textfield.WantedTextFieldContract.TextFieldType
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.ButtonStatus
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun WantedTextArea(
    modifier: Modifier,
    value: String,
    placeholder: String,
    error: Boolean,
    enabled: Boolean,
    focused: Boolean,
    maxLines: Int,
    maxWordCount: Int,
    interactionSource: MutableInteractionSource,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    rightButton: String? = null,
    onClickRightButton: () -> Unit = {},
    onValueChange: (String) -> Unit = {}
) {
    WantedTextAreaLayout(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(
                shape = RoundedCornerShape(12.dp),
                color = when {
                    error || focused -> {
                        colorResource(id = R.color.background_normal_normal)
                            .copy(alpha = OPACITY_43)
                    }

                    else -> colorResource(R.color.transparent)
                },
                width = if (focused) 2.dp else 1.dp
            )
            .border(
                shape = RoundedCornerShape(12.dp),
                color = colorResource(
                    id = when {
                        enabled && error -> R.color.status_negative
                        !enabled -> R.color.line_normal_neutral
                        focused -> R.color.primary_normal
                        else -> R.color.line_normal_neutral
                    }
                ),
                width = if (focused) 2.dp else 1.dp
            )
            .background(
                colorResource(
                    if (enabled) R.color.background_normal_normal else R.color.interaction_disable
                )
            )
            .height(IntrinsicSize.Min),
        textField = {
            BasicTextField(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(vertical = 12.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                value = value,
                maxLines = maxLines,
                enabled = enabled,
                interactionSource = interactionSource,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                textStyle = WantedTextStyle(
                    colorRes = if (enabled) R.color.label_normal else R.color.label_disable,
                    style = DesignSystemTheme.typography.body1Regular
                ),
                onValueChange = {
                    if (it.length <= maxWordCount) {
                        onValueChange(it)
                    } else {
                        onValueChange(value)
                    }
                },
                decorationBox = { innerTextField ->
                    DecorationBox(
                        modifier = Modifier,
                        innerTextField = innerTextField,
                        placeholder = if (value.isEmpty() && placeholder.isNotEmpty()) {
                            {
                                Text(
                                    text = placeholder,
                                    style = WantedTextStyle(
                                        colorRes = if (enabled) R.color.label_alternative else R.color.label_disable,
                                        style = DesignSystemTheme.typography.body1Regular
                                    )
                                )
                            }
                        } else {
                            null
                        }
                    )
                }
            )
        },
        count = {
            WantedTextAreaCount(
                current = value.length,
                maxWordCount = maxWordCount
            )
        },
        rightButton = {
            if (error) {
                WantedCommonIcon(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 6.dp),
                    resourceId = R.drawable.ic_normal_circle_exclamation_fill_svg,
                    tint = colorResource(id = R.color.status_negative)
                )
            } else {
                rightButton?.let {
                    WantedButton(
                        text = rightButton,
                        buttonShape = ButtonShape.TEXT,
                        status = if (enabled) ButtonStatus.ENABLE else ButtonStatus.DISABLE,
                        onClick = { onClickRightButton() }
                    )
                }
            }
        }
    )
}

@Composable
fun WantedTextAreaCount(
    modifier: Modifier = Modifier,
    current: Int,
    maxWordCount: Int
) {
    Row(
        modifier = modifier.padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$current",
            style = WantedTextStyle(
                colorRes = R.color.label_alternative,
                style = DesignSystemTheme.typography.label2Medium
            )
        )

        Text(
            text = "/$maxWordCount",
            style = WantedTextStyle(
                colorRes = R.color.label_assistive,
                style = DesignSystemTheme.typography.label2Medium
            )
        )
    }
}

@Composable
private fun DecorationBox(
    modifier: Modifier = Modifier,
    innerTextField: @Composable () -> Unit,
    placeholder: @Composable (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth(1f)
            .padding(horizontal = 4.dp)
            .wrapContentHeight(),
        contentAlignment = Alignment.CenterStart
    ) {
        placeholder?.let {
            placeholder()
        }

        innerTextField()
    }
}

@Composable
private fun WantedTextAreaLayout(
    modifier: Modifier = Modifier,
    textField: @Composable () -> Unit,
    count: @Composable () -> Unit,
    rightButton: @Composable () -> Unit
) {

    Column(
        modifier = modifier,
    ) {

        textField()

        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(weight = 1f)) {
                count()
            }

            Box(modifier = Modifier.wrapContentSize()) {
                rightButton()
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
private fun WantedTextAreaPreview() {
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
                    placeholder = "텍스트를 입력해 주세요.",
                    type = TextFieldType.TextArea
                )

                WantedTextField(
                    title = "주제",
                    value = "",
                    placeholder = "텍스트를 입력해 주세요.",
                    type = TextFieldType.TextArea
                )

                WantedTextField(
                    title = "주제",
                    requiredBadge = true,
                    value = "입력한 텍스트.",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    type = TextFieldType.TextArea
                )

                WantedTextField(
                    value = "텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요.",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    type = TextFieldType.TextArea
                )

                WantedTextField(
                    value = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    complete = true,
                    focused = remember { mutableStateOf(false) },
                    type = TextFieldType.TextArea
                )

                WantedTextField(
                    value = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    focused = remember { mutableStateOf(true) },
                    type = TextFieldType.TextArea
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
private fun WantedTextArea1Preview() {
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
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    error = true,
                    focused = remember { mutableStateOf(false) },
                    type = TextFieldType.TextArea
                )

                WantedTextField(
                    value = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    error = true,
                    focused = remember { mutableStateOf(true) },
                    type = TextFieldType.TextArea
                )

                WantedTextField(
                    value = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    enabled = false,
                    focused = remember { mutableStateOf(true) },
                    type = TextFieldType.TextArea
                )
            }
        }
    }
}
