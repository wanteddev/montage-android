package com.wanted.android.wanted.design.textfield.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedDropShadow
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.textfield.WantedTextInputRightVariant
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
internal fun WantedCustomTextField(
    modifier: Modifier,
    value: TextFieldValue,
    placeholder: String,
    error: Boolean,
    enabled: Boolean,
    rightButtonEnabled: Boolean,
    focused: Boolean,
    complete: Boolean,
    maxLines: Int,
    minLines: Int,
    maxWordCount: Int,
    interactionSource: MutableInteractionSource,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    background: Color = colorResource(id = R.color.background_normal_normal),
    rightContent: @Composable (() -> Unit)? = null,
    rightButton: String? = null,
    rightButtonVariant: WantedTextInputRightVariant,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onClickRightButton: () -> Unit = {},
    onValueChange: (TextFieldValue) -> Unit = {}
) {
    ConstraintLayout {
        val (shadow, textField) = createRefs()
        WantedDropShadow(
            Modifier
                .constrainAs(shadow) {
                    top.linkTo(textField.top)
                    bottom.linkTo(textField.bottom)
                    start.linkTo(textField.start)
                    end.linkTo(textField.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            background = background,
            shape = RoundedCornerShape(12.dp)
        )
        WantedCustomTextFieldLayout(
            modifier = modifier
                .constrainAs(textField) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .clip(RoundedCornerShape(12.dp))
                .border(
                    shape = RoundedCornerShape(12.dp),
                    color = colorResource(
                        if (enabled) R.color.line_normal_neutral else R.color.line_normal_alternative
                    ),
                    width = 1.dp
                )
                .background(
                    if (enabled) {
                        background
                    } else {
                        colorResource(R.color.interaction_disable)
                    }
                )
                .height(IntrinsicSize.Min),
            textField = {
                BasicTextField(
                    modifier = Modifier
                        .border(
                            shape = RoundedCornerShape(
                                topStart = 12.dp,
                                bottomStart = 12.dp,
                                topEnd = rightButton?.let { 0.dp } ?: 12.dp,
                                bottomEnd = rightButton?.let { 0.dp } ?: 12.dp
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
                            color = when {
                                !enabled -> colorResource(R.color.transparent)
                                error -> colorResource(R.color.status_negative).copy(OPACITY_43)
                                focused -> colorResource(R.color.primary_normal).copy(OPACITY_43)
                                else -> colorResource(R.color.transparent)
                            },
                            width = if (focused) 2.dp else 1.dp
                        )
                        .padding(horizontal = 12.dp)
                        .padding(vertical = 12.dp)
                        .fillMaxSize(),
                    value = value,
                    maxLines = maxLines,
                    minLines = minLines,
                    enabled = enabled,
                    singleLine = true,
                    interactionSource = interactionSource,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    textStyle = WantedTextStyle(
                        colorRes = if (enabled) R.color.label_normal else R.color.label_alternative,
                        style = DesignSystemTheme.typography.body1Regular
                    ),
                    onValueChange = {
                        when {
                            it.text.length <= maxWordCount -> onValueChange(it)
                            it.text.length < value.text.length -> onValueChange(it)
                            else -> onValueChange(value)
                        }
                    },
                    decorationBox = { innerTextField ->
                        DecorationBox(
                            modifier = Modifier,
                            innerTextField = innerTextField,
                            placeholder = if (value.text.isEmpty() && placeholder.isNotEmpty()) {
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
                                        Icon(
                                            modifier = Modifier.fillMaxSize(),
                                            painter = painterResource(id = R.drawable.ic_normal_circle_exclamation_fill_svg),
                                            tint = colorResource(id = R.color.status_negative),
                                            contentDescription = ""
                                        )
                                    }
                                }

                                !focused && complete -> {
                                    {
                                        Icon(
                                            modifier = Modifier.fillMaxSize(),
                                            painter = painterResource(R.drawable.ic_normal_circle_check_fill_svg),
                                            tint = colorResource(id = R.color.primary_normal),
                                            contentDescription = ""
                                        )
                                    }
                                }

                                trailingIcon == null && value.text.isNotEmpty() && enabled && focused -> {
                                    {
                                        Icon(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .clip(CircleShape)
                                                .clickOnceForDesignSystem {
                                                    onValueChange(value.copy(""))
                                                },
                                            painter = painterResource(R.drawable.ic_normal_circle_close_svg),
                                            tint = colorResource(id = R.color.label_assistive),
                                            contentDescription = ""
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
                            .clickOnceForDesignSystem(
                                isEnableRightButton(
                                    rightButtonEnabled,
                                    enabled
                                )
                            ) { onClickRightButton() },
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        if (isVisibleVerticalDivider(rightButtonEnabled, enabled, error, focused)) {
                            VerticalDivider(
                                color = colorResource(id = R.color.line_normal_neutral),
                                thickness = 1.dp
                            )
                        }

                        WantedTextFieldButton(
                            modifier = Modifier.background(
                                if (rightButtonEnabled) {
                                    colorResource(id = R.color.transparent)
                                } else {
                                    colorResource(id = R.color.interaction_disable)
                                }
                            ),
                            title = rightButton,
                            rightButtonVariant = rightButtonVariant,
                            enable = isEnableRightButton(rightButtonEnabled, enabled)
                        )
                    }
                }
            }
        )
    }
}

@Composable
private fun isEnableRightButton(
    rightButtonEnabled: Boolean,
    enabled: Boolean
) = when {
    !rightButtonEnabled -> false
    else -> enabled
}

@Composable
private fun isVisibleVerticalDivider(
    rightButtonEnabled: Boolean,
    enabled: Boolean,
    error: Boolean,
    focused: Boolean
): Boolean {
    return when {
        !rightButtonEnabled -> true
        !enabled -> true
        !error -> true
        !focused -> true
        else -> false
    }
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
                    .defaultMinSize(24.dp)
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
    rightButtonVariant: WantedTextInputRightVariant,
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
            colorRes = when {
                !enable -> R.color.label_assistive
                rightButtonVariant == WantedTextInputRightVariant.Assistive -> R.color.label_normal
                else -> R.color.primary_normal
            },
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
