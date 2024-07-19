package com.wanted.android.wanted.design.textfield

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
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
    focused: Boolean = false,
    singleLine: Boolean = false,
    requiredBadge: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (String) -> Unit = {}
) {

    WantedTextFieldLayout(
        modifier = Modifier,
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
            CustomTextField(
                modifier = Modifier,
                value = value,
                enabled = enabled,
                error = error,
                rightButton = rightButton,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                rightContent = rightContent
            )
        },
        message = {

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
private fun CustomTextField(
    modifier: Modifier,
    value: String,
    placeholder: String,
    error: Boolean,
    enabled: Boolean,
    rightContent: @Composable (() -> Unit)? = null,
    rightButton: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()

    CustomTextFieldLayout(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(
                shape = RoundedCornerShape(12.dp),
                color = colorResource(
                    id = when {
                        error -> R.color.status_negative
                        !enabled -> R.color.line_normal_alternative
                        focused -> R.color.primary_normal
                        else -> R.color.line_normal_neutral
                    }
                ),
                width = if (focused) 2.dp else 1.dp
            )
            .height(IntrinsicSize.Min),
        textField = {
            BasicTextField(
                modifier = Modifier.fillMaxSize(),
                value = value,
                interactionSource = interactionSource,
                textStyle = WantedTextStyle(
                    colorRes = R.color.label_normal,
                    style = DesignSystemTheme.typography.body1Regular
                ),
                onValueChange = {},
                decorationBox = { innerTextField ->
                    DecorationBox(
                        modifier = modifier,
                        innerTextField = innerTextField,
                        placeholder = if (value.isEmpty() && placeholder.isNotEmpty()) {
                            {
                                Text(text = placeholder)
                            }
                        } else {
                            null
                        },
                        leadingIcon = leadingIcon,
                        trailingIcon = trailingIcon,
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
                    modifier = Modifier.clickOnceForDesignSystem(enabled) { },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    VerticalDivider(
                        color = colorResource(
                            id = when {
                                error -> R.color.status_negative
                                !enabled -> R.color.line_normal_alternative
                                focused -> R.color.primary_normal
                                else -> R.color.line_normal_neutral
                            }
                        ),
                        thickness = if (focused) 2.dp else 1.dp
                    )

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
            } ?: run {
                innerTextField()
            }
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
fun CustomTextFieldLayout(
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
                .padding(horizontal = 12.dp)
                .padding(vertical = 12.dp)
                .weight(1f),
        ) {
            textField()
        }

        rightButton?.let {
            rightButton()
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
private fun CustomTopAppBarPreview() {
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
                    value = "텍스트를 입력해 주세요.",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트"
                )

                WantedTextField(
                    value = "텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요.",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트"
                )

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
            }
        }
    }
}