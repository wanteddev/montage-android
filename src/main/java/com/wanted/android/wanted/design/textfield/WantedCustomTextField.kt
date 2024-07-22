package com.wanted.android.wanted.design.textfield

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.theme.DesignSystemTheme
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
                                enabled && error -> R.color.status_negative
                                !enabled -> R.color.transparent
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
                onValueChange = onValueChange,
                decorationBox = { innerTextField ->
                    DecorationBox(
                        modifier = modifier,
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
                        },
                        leadingIcon = leadingIcon,
                        trailingIcon = when {
                            !focused && enabled && error -> {
                                {
                                    WantedTextFieldIcon(
                                        modifier = Modifier.fillMaxSize(),
                                        resourceId = R.drawable.ic_normal_circle_exclamation_fill_svg,
                                        tint = colorResource(id = R.color.status_negative)
                                    )
                                }
                            }

                            !focused && complete -> {
                                {
                                    WantedTextFieldIcon(
                                        modifier = Modifier.fillMaxSize(),
                                        resourceId = R.drawable.ic_normal_circle_check_fill_svg,
                                        tint = colorResource(id = R.color.primary_normal)
                                    )
                                }
                            }

                            trailingIcon == null && value.isNotEmpty() && enabled -> {
                                {
                                    WantedTextFieldIcon(
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
private fun WantedTextFieldIcon(
    modifier: Modifier = Modifier,
    @DrawableRes resourceId: Int,
    tint: Color
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(11.dp)
                .background(colorResource(id = R.color.static_white))
        )

        Icon(
            contentDescription = "icon",
            painter = painterResource(id = resourceId),
            modifier = Modifier.size(22.dp),
            tint = tint
        )
    }
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