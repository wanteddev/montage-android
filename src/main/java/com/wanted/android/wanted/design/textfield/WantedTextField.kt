package com.wanted.android.wanted.design.textfield

import android.content.res.Configuration
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
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
    placeholder: String? = null,
    title: String? = null,
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
        title = {
            WantedTextFieldTitle(
                title = "askljd hflkjashdhdflkasjhd hflkjashdhdflkasjhdf flkjashdhdflkasjhd",
                isRequiredBadge = true
            )
        },
        textField = {
            CustomTextField(
                modifier = Modifier,
                value = value,
                enabled = enabled,
                error = error,
                rightButton = rightButton
            )
        },
        message = {

        }
    )
//
//    OutlinedTextField(
//        modifier = modifier,
//        shape = RoundedCornerShape(12.dp),
//        value = value,
//        enabled = enabled,
//        placeholder = {
//            placeholder?.let {
//                Text(text = it)
//            }
//        },
//        singleLine = true,
//        leadingIcon = leadingIcon,
//        trailingIcon = {
//            if (value.isNotEmpty() && enabled) {
//            }
//        },
//        textStyle = DesignSystemTheme.typography.body1Regular,
//        keyboardOptions = keyboardOptions,
//        keyboardActions = keyboardActions,
//        onValueChange = { onValueChange(it) }
//    )
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
    error: Boolean,
    enabled: Boolean,
    rightButton: String? = null
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
                onValueChange = {}
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
fun WantedTextFieldButton(
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
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    rightContent: @Composable (() -> Unit)? = null,
    rightButton: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(vertical = 12.dp)
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            leadingIcon?.let {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(1.dp)
                ) {

                }
            }

            Box(
                Modifier
                    .background(Color.Yellow)
                    .fillMaxSize()
                    .padding(horizontal = 4.dp),
            ) {
                textField()
            }

            trailingIcon?.let {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(1.dp)
                ) {
                    trailingIcon()
                }
            }

            rightContent?.let {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(1.dp)
                ) {
                    rightContent()
                }
            }
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
        verticalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterVertically)
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
                    value = "",
                    placeholder = "텍스트를 입력해 주세요."
                )

                WantedTextField(
                    value = "텍스트를 입력해 주세요.",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트"
                )

                WantedTextField(
                    value = "텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요.",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트"
                )
            }
        }

    }
}