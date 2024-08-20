package com.wanted.android.wanted.design.textfield.view

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedCommonIcon
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.textfield.WantedTextField
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
internal fun WantedCustomTextField(
    modifier: Modifier,
    value: String,
    placeholder: String,
    error: Boolean,
    enabled: Boolean,
    focused: Boolean,
    complete: Boolean,
    maxLines: Int,
    maxWordCount: Int,
    interactionSource: MutableInteractionSource,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    rightContent: @Composable (() -> Unit)? = null,
    rightButton: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onClickRightButton: () -> Unit = {},
    onValueChange: (String) -> Unit = {}
) {
    WantedCustomTextFieldLayout(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(
                shape = RoundedCornerShape(12.dp),
                color = colorResource(
                    if (enabled) R.color.line_normal_neutral else R.color.line_normal_alternative
                ),
                width = 1.dp
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
                    .border(
                        shape = RoundedCornerShape(
                            topStart = 12.dp,
                            bottomStart = 12.dp,
                            topEnd = 0.dp,
                            bottomEnd = 0.dp
                        ),
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
                        shape = rightButton?.let {
                            RoundedCornerShape(
                                topStart = 12.dp,
                                bottomStart = 12.dp,
                                topEnd = 0.dp,
                                bottomEnd = 0.dp
                            )
                        } ?: run { RoundedCornerShape(12.dp) },
                        color = colorResource(
                            id = when {
                                !enabled -> R.color.transparent
                                error -> R.color.status_negative
                                focused -> R.color.primary_normal
                                else -> R.color.transparent
                            }
                        ),
                        width = if (focused) 2.dp else 1.dp
                    )
                    .padding(horizontal = 12.dp)
                    .padding(vertical = 12.dp)
                    .fillMaxSize(),
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
                                        colorRes = if (enabled) R.color.label_assistive else R.color.label_disable,
                                        style = DesignSystemTheme.typography.body1Regular
                                    )
                                )
                            }
                        } else {
                            null
                        },
                        leadingIcon = leadingIcon,
                        trailingIcon = when {
                            !focused && enabled && error -> {
                                {
                                    WantedCommonIcon(
                                        modifier = Modifier.fillMaxSize(),
                                        resourceId = R.drawable.ic_normal_circle_exclamation_fill_svg,
                                        tint = colorResource(id = R.color.status_negative)
                                    )
                                }
                            }

                            !focused && complete -> {
                                {
                                    WantedCommonIcon(
                                        modifier = Modifier.fillMaxSize(),
                                        resourceId = R.drawable.ic_normal_circle_check_fill_svg,
                                        tint = colorResource(id = R.color.primary_normal)
                                    )
                                }
                            }

                            trailingIcon == null && value.isNotEmpty() && enabled -> {
                                {
                                    WantedCommonIcon(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(CircleShape)
                                            .clickOnceForDesignSystem { onValueChange("") },
                                        resourceId = R.drawable.ic_normal_circle_close_svg,
                                        tint = colorResource(id = R.color.label_alternative)
                                    )
                                }
                            }

                            else -> trailingIcon
                        },
                        rightContent = rightContent
                    )
                }
            )
        },
        rightButton = if (rightButton.isNullOrEmpty()) {
            null
        } else {
            {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickOnceForDesignSystem(enabled) { onClickRightButton() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!enabled || !error && !focused) {
                        VerticalDivider(
                            color = colorResource(id = R.color.line_normal_neutral),
                            thickness = 1.dp
                        )
                    }

                    WantedTextFieldButton(
                        title = rightButton,
                        enable = enabled
                    )
                }
            }
        }
    )
}

@Composable
private fun DecorationBox(
    modifier: Modifier = Modifier,
    innerTextField: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    rightContent: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingIcon?.let {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .padding(1.dp),
                contentAlignment = Alignment.Center,
            ) {
                leadingIcon()
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp)
                .wrapContentHeight(),
            contentAlignment = Alignment.CenterStart
        ) {
            placeholder?.let {
                placeholder()
            }

            innerTextField()
        }

        trailingIcon?.let {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .padding(1.dp),
                contentAlignment = Alignment.Center,
            ) {
                trailingIcon()
            }
        }

        rightContent?.let {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .padding(1.dp),
                contentAlignment = Alignment.Center,
            ) {
                rightContent()
            }
        }
    }
}

@Composable
private fun WantedTextFieldButton(
    modifier: Modifier = Modifier,
    title: String,
    enable: Boolean
) {
    Text(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(vertical = 12.dp),
        text = title,
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = WantedTextStyle(
            colorRes = if (enable) R.color.primary_normal else R.color.label_assistive,
            style = DesignSystemTheme.typography.body1Bold
        )
    )
}


@Composable
private fun WantedCustomTextFieldLayout(
    modifier: Modifier = Modifier,
    textField: @Composable () -> Unit,
    rightButton: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .weight(1f),
        ) {
            textField()
        }

        rightButton?.let {
            rightButton()
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