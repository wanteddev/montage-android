package com.wanted.android.wanted.design.input.textinput.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedDropShadowDefaults
import com.wanted.android.wanted.design.base.wantedDropShadow
import com.wanted.android.wanted.design.input.textinput.textfield.WantedTextFieldDefaults.RightVariant
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.clickOnce


@Composable
internal fun WantedCustomTextField(
    value: TextFieldValue,
    placeholder: String,
    error: Boolean,
    enabled: Boolean,
    rightButtonEnabled: Boolean,
    complete: Boolean,
    maxLines: Int,
    minLines: Int,
    maxWordCount: Int,
    enabledOverflowText: Boolean,
    interactionSource: MutableInteractionSource,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    rightButtonVariant: RightVariant,
    modifier: Modifier = Modifier,
    focused: State<Boolean> = interactionSource.collectIsFocusedAsState(),
    cursorBrush: Brush = SolidColor(DesignSystemTheme.colors.primaryNormal),
    background: Color = DesignSystemTheme.colors.backgroundNormalNormal,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    rightButton: String? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onClickRightButton: () -> Unit = {},
    onValueChange: (TextFieldValue) -> Unit = {},
    focusRequester: FocusRequester = FocusRequester()
) {
    ConstraintLayout {
        WantedCustomTextFieldLayout(
            modifier = modifier
                .wantedDropShadow(WantedDropShadowDefaults.WantedShadowStyle.XSmall())
                .clip(RoundedCornerShape(12.dp))
                .background(
                    if (enabled) {
                        background
                    } else {
                        DesignSystemTheme.colors.fillAlternative
                    }
                )
                .height(IntrinsicSize.Min),
            textField = {
                BasicTextField(
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .border(
                            shape = RoundedCornerShape(
                                topStart = 12.dp,
                                bottomStart = 12.dp,
                                topEnd = rightButton?.let { 0.dp } ?: 12.dp,
                                bottomEnd = rightButton?.let { 0.dp } ?: 12.dp
                            ),
                            color = when {
                                !enabled -> DesignSystemTheme.colors.transparent
                                error || focused.value -> {
                                    DesignSystemTheme.colors.backgroundNormalNormal
                                        .copy(alpha = OPACITY_43)
                                }

                                else -> DesignSystemTheme.colors.transparent
                            },
                            width = if (focused.value) 2.dp else 1.dp
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
                                !enabled -> DesignSystemTheme.colors.lineNormalAlternative
                                error -> DesignSystemTheme.colors.statusNegative.copy(OPACITY_43)
                                focused.value -> DesignSystemTheme.colors.primaryNormal.copy(
                                    OPACITY_43
                                )

                                else -> DesignSystemTheme.colors.lineNormalNeutral
                            },
                            width = if (focused.value) 2.dp else 1.dp
                        )
                        .padding(horizontal = 12.dp)
                        .padding(vertical = 12.dp)
                        .height(
                            with(LocalDensity.current) {
                                DesignSystemTheme.typography.body1Regular.lineHeight.toDp()
                            }
                        )
                        .fillMaxSize(),
                    value = value,
                    maxLines = maxLines,
                    minLines = minLines,
                    enabled = enabled,
                    singleLine = true,
                    cursorBrush = cursorBrush,
                    interactionSource = interactionSource,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    visualTransformation = visualTransformation,
                    textStyle = DesignSystemTheme.typography.body1Regular.copy(
                        color = if (enabled) {
                            DesignSystemTheme.colors.labelNormal
                        } else {
                            DesignSystemTheme.colors.labelAlternative
                        }
                    ),
                    onValueChange = {
                        when {
                            enabledOverflowText -> onValueChange(it)
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
                                        style = DesignSystemTheme.typography.body1Regular,
                                        color = if (enabled) {
                                            DesignSystemTheme.colors.labelAssistive
                                        } else {
                                            DesignSystemTheme.colors.labelDisable
                                        },
                                    )
                                }
                            } else {
                                null
                            },
                            leadingIcon = leadingIcon,
                            trailingIcon = when {
                                !focused.value && enabled && error -> {
                                    {
                                        Icon(
                                            modifier = Modifier.fillMaxSize(),
                                            painter = painterResource(id = R.drawable.icon_normal_circle_exclamation_fill),
                                            tint = DesignSystemTheme.colors.statusNegative,
                                            contentDescription = ""
                                        )
                                    }
                                }

                                !focused.value && complete -> {
                                    {
                                        Icon(
                                            modifier = Modifier.fillMaxSize(),
                                            painter = painterResource(R.drawable.icon_normal_circle_check_fill),
                                            tint = DesignSystemTheme.colors.primaryNormal,
                                            contentDescription = ""
                                        )
                                    }
                                }

                                trailingIcon == null && value.text.isNotEmpty() && enabled && focused.value -> {
                                    {
                                        Icon(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .clip(CircleShape)
                                                .clickOnce {
                                                    onValueChange(value.copy(""))
                                                },
                                            painter = painterResource(R.drawable.icon_normal_circle_close_fill),
                                            tint = DesignSystemTheme.colors.labelAssistive,
                                            contentDescription = ""
                                        )
                                    }
                                }

                                else -> trailingIcon
                            },
                            trailingContent = trailingContent
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
                            .clickOnce(
                                isEnableRightButton(
                                    rightButtonEnabled,
                                    enabled
                                )
                            ) { onClickRightButton() },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        WantedTextFieldButton(
                            modifier = Modifier
                                .background(
                                    if (rightButtonEnabled) {
                                        DesignSystemTheme.colors.transparent
                                    } else {
                                        DesignSystemTheme.colors.fillAlternative
                                    }
                                )
                                .borderExceptStart(
                                    topEndRadius = with(LocalDensity.current) { 12.dp.toPx() },
                                    bottomEndRadius = with(LocalDensity.current) { 12.dp.toPx() },
                                    color = when {
                                        !enabled -> DesignSystemTheme.colors.transparent
                                        error || focused.value -> {
                                            DesignSystemTheme.colors.backgroundNormalNormal
                                                .copy(alpha = OPACITY_43)
                                        }

                                        else -> DesignSystemTheme.colors.transparent
                                    },
                                    width = with(LocalDensity.current) {
                                        if (focused.value) 2.dp.toPx() else 1.dp.toPx()
                                    }
                                )
                                .borderExceptStart(
                                    topEndRadius = with(LocalDensity.current) { 12.dp.toPx() },
                                    bottomEndRadius = with(LocalDensity.current) { 12.dp.toPx() },
                                    color = when {
                                        !enabled -> DesignSystemTheme.colors.lineNormalAlternative
                                        else -> DesignSystemTheme.colors.lineNormalNeutral
                                    },
                                    width = with(LocalDensity.current) {
                                        if (focused.value) 2.dp.toPx() else 1.dp.toPx()
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
private fun isValidButtonBorder(
    rightButtonEnabled: Boolean,
    enabled: Boolean,
    error: Boolean,
    focused: Boolean
): Boolean {
    return when {
        !rightButtonEnabled -> true
        !enabled -> false
        !error -> false
        !focused -> true
        else -> false
    }
}

@Composable
private fun DecorationBox(
    innerTextField: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null
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

        trailingContent?.let {
            Box(
                modifier = Modifier.defaultMinSize(24.dp),
                contentAlignment = Alignment.Center,
            ) {
                trailingContent()
            }
        }
    }
}

@Composable
private fun WantedTextFieldButton(
    rightButtonVariant: RightVariant,
    title: String,
    enable: Boolean,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(vertical = 12.dp),
        text = title,
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = DesignSystemTheme.typography.body1Bold,
        color = when {
            !enable -> DesignSystemTheme.colors.labelAssistive
            rightButtonVariant == RightVariant.Assistive -> DesignSystemTheme.colors.labelNormal
            else -> DesignSystemTheme.colors.primaryNormal
        }
    )
}


@Composable
private fun WantedCustomTextFieldLayout(
    textField: @Composable () -> Unit,
    modifier: Modifier = Modifier,
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

/**
 * 왼쪽을 제외한 상단, 우측, 하단에만 border를 그리는 Modifier
 *
 * @param topEndRadius 우측 상단 corner radius
 * @param bottomEndRadius 우측 하단 corner radius
 * @param color border 색상
 * @param width border 두께
 */
private fun Modifier.borderExceptStart(
    topEndRadius: Float,
    bottomEndRadius: Float,
    color: Color,
    width: Float
): Modifier = this.drawBehind {
    val halfStroke = width / 2

    // Path를 사용하여 왼쪽을 제외한 3면 + 라운드 코너를 그립니다
    val path = Path().apply {
        // 왼쪽 상단에서 시작 (border 두께의 절반만큼 안쪽)
        moveTo(0f, halfStroke)

        // 상단 라인 (왼쪽 -> 오른쪽, 라운드 시작 지점까지)
        lineTo(size.width - topEndRadius, halfStroke)

        // 우측 상단 라운드 코너
        arcTo(
            rect = Rect(
                left = size.width - topEndRadius * 2,
                top = halfStroke,
                right = size.width - halfStroke,
                bottom = topEndRadius * 2 + halfStroke
            ),
            startAngleDegrees = 270f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        // 우측 라인 (위 -> 아래, 라운드 끝 지점부터 아래 라운드 시작까지)
        lineTo(size.width - halfStroke, size.height - bottomEndRadius)

        // 우측 하단 라운드 코너
        arcTo(
            rect = Rect(
                left = size.width - bottomEndRadius * 2,
                top = size.height - bottomEndRadius * 2 - halfStroke,
                right = size.width - halfStroke,
                bottom = size.height - halfStroke
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        // 하단 라인 (오른쪽 -> 왼쪽)
        lineTo(0f, size.height - halfStroke)
    }

    drawPath(
        path = path,
        color = color,
        style = Stroke(width = width)
    )
}
