package com.wanted.android.wanted.design.input.textinput

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.actions.chip.WantedActionChip
import com.wanted.android.wanted.design.base.ComponentTitle
import com.wanted.android.wanted.design.base.WantedDropShadow
import com.wanted.android.wanted.design.input.textinput.view.WantedTextAreaCharacterCount
import com.wanted.android.wanted.design.input.textinput.view.WantedTextAreaLayout
import com.wanted.android.wanted.design.input.textinput.view.WantedTextInputLayout
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.WantedTextStyle
import java.text.BreakIterator


@Composable
fun WantedTextArea(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String = "",
    title: String = "",
    description: String? = null,
    rightButton: String? = null,
    enabled: Boolean = true,
    negative: Boolean = false,
    maxLines: Int = MAX_LINE,
    minLines: Int = MIN_LINE,
    maxWordCount: Int = 2000,
    requiredBadge: Boolean = false,
    isGraphemeClusterCount: Boolean = false, // 커서 숫자로 판단 - 이모지 때문
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focused: State<Boolean> = interactionSource.collectIsFocusedAsState(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = colorResource(id = R.color.background_normal_normal),
    onClickRightButton: () -> Unit = {},
    onValueChange: (String) -> Unit = {}
) {
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
    val textFieldValue = textFieldValueState.copy(text = value)

    SideEffect {
        if (textFieldValue.selection != textFieldValueState.selection ||
            textFieldValue.composition != textFieldValueState.composition
        ) {
            textFieldValueState = textFieldValue
        }
    }
    var lastTextValue by remember(value) { mutableStateOf(value) }

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
            WantedTextArea(
                modifier = Modifier,
                value = textFieldValue,
                negative = negative,
                enabled = enabled,
                focused = focused.value,
                maxLines = maxLines,
                minLines = minLines,
                maxWordCount = maxWordCount,
                isGraphemeClusterCount = isGraphemeClusterCount,
                interactionSource = interactionSource,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                rightButton = rightButton,
                placeholder = placeholder,
                background = background,
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
        message = description?.let {
            {
                Text(
                    text = description,
                    style = WantedTextStyle(
                        colorRes = when {
                            enabled && negative -> R.color.status_negative
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
fun WantedTextArea(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    placeholder: String = "",
    title: String = "",
    description: String? = null,
    rightButton: String? = null,
    enabled: Boolean = true,
    negative: Boolean = false,
    maxLines: Int = MAX_LINE,
    minLines: Int = MIN_LINE,
    maxWordCount: Int = 2000,
    requiredBadge: Boolean = false,
    isGraphemeClusterCount: Boolean = false, // 커서 숫자로 판단 - 이모지 때문
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
            WantedTextArea(
                modifier = Modifier,
                value = value,
                negative = negative,
                enabled = enabled,
                focused = focused.value,
                maxLines = maxLines,
                minLines = minLines,
                maxWordCount = maxWordCount,
                isGraphemeClusterCount = isGraphemeClusterCount,
                interactionSource = interactionSource,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                rightButton = rightButton,
                placeholder = placeholder,
                background = background,
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
                            enabled && negative -> R.color.status_negative
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
fun WantedTextArea(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String = "",
    title: String = "",
    description: String? = null,
    leftContent: @Composable (() -> Unit)? = null,
    rightContent: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    negative: Boolean = false,
    maxLines: Int = MAX_LINE,
    minLines: Int = MIN_LINE,
    maxWordCount: Int = 2000,
    requiredBadge: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focused: State<Boolean> = interactionSource.collectIsFocusedAsState(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = colorResource(id = R.color.background_normal_normal),
    onValueChange: (String) -> Unit = {}
) {
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
    val textFieldValue = textFieldValueState.copy(text = value)

    SideEffect {
        if (textFieldValue.selection != textFieldValueState.selection ||
            textFieldValue.composition != textFieldValueState.composition
        ) {
            textFieldValueState = textFieldValue
        }
    }
    var lastTextValue by remember(value) { mutableStateOf(value) }

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
            WantedTextArea(
                modifier = Modifier,
                value = textFieldValue,
                negative = negative,
                enabled = enabled,
                focused = focused.value,
                maxLines = maxLines,
                minLines = minLines,
                maxWordCount = maxWordCount,
                interactionSource = interactionSource,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                rightContent = rightContent,
                leftContent = leftContent,
                placeholder = placeholder,
                background = background,
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
        message = description?.let {
            {
                Text(
                    text = description,
                    style = WantedTextStyle(
                        colorRes = when {
                            enabled && negative -> R.color.status_negative
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
fun WantedTextArea(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    placeholder: String = "",
    title: String = "",
    description: String? = null,
    leftContent: @Composable (() -> Unit)? = null,
    rightContent: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    negative: Boolean = false,
    maxLines: Int = MAX_LINE,
    minLines: Int = MIN_LINE,
    maxWordCount: Int = 2000,
    requiredBadge: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focused: State<Boolean> = interactionSource.collectIsFocusedAsState(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = colorResource(id = R.color.background_normal_normal),
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
            WantedTextArea(
                modifier = Modifier,
                value = value,
                negative = negative,
                enabled = enabled,
                focused = focused.value,
                maxLines = maxLines,
                minLines = minLines,
                maxWordCount = maxWordCount,
                interactionSource = interactionSource,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                rightContent = rightContent,
                leftContent = leftContent,
                placeholder = placeholder,
                background = background,
                onValueChange = onValueChange
            )
        },
        message = description?.let {
            {
                Text(
                    text = description,
                    style = WantedTextStyle(
                        colorRes = when {
                            enabled && negative -> R.color.status_negative
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
private fun WantedTextArea(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    placeholder: String = "",
    negative: Boolean = false,
    enabled: Boolean = true,
    focused: Boolean = false,
    isGraphemeClusterCount: Boolean = false,
    maxLines: Int = MAX_LINE,
    minLines: Int = MIN_LINE,
    maxWordCount: Int = 2000,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = colorResource(id = R.color.background_normal_normal),
    rightButton: String?,
    onClickRightButton: () -> Unit = {},
    onValueChange: (TextFieldValue) -> Unit = {}
) {
    WantedTextArea(
        modifier = modifier,
        value = value,
        placeholder = placeholder,
        negative = negative,
        enabled = enabled,
        focused = focused,
        maxLines = maxLines,
        minLines = minLines,
        maxWordCount = maxWordCount,
        isGraphemeClusterCount = isGraphemeClusterCount,
        interactionSource = interactionSource,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        background = background,
        leftContent = {
            WantedTextAreaCharacterCount(
                current = if (isGraphemeClusterCount) {
                    graphemeClusterCount(value.text)
                } else {
                    value.text.length
                },
                maxWordCount = maxWordCount
            )
        },
        rightContent = {
            if (negative) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(1.dp),
                    painter = painterResource(id = R.drawable.ic_normal_circle_exclamation_fill_svg),
                    tint = colorResource(id = R.color.status_negative),
                    contentDescription = ""
                )
            } else {
                rightButton?.let {
                    WantedButton(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        text = rightButton,
                        buttonShape = ButtonShape.TEXT,
                        enabled = enabled,
                        onClick = { onClickRightButton() }
                    )
                }
            }
        },
        onValueChange = onValueChange
    )
}


@Composable
private fun WantedTextArea(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    placeholder: String = "",
    negative: Boolean = false,
    enabled: Boolean = true,
    focused: Boolean = false,
    maxLines: Int = MAX_LINE,
    minLines: Int = MIN_LINE,
    maxWordCount: Int = 2000,
    isGraphemeClusterCount: Boolean = false, // 커서 숫자로 판단 - 이모지 때문
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = colorResource(id = R.color.background_normal_normal),
    leftContent: @Composable (() -> Unit)? = null,
    rightContent: @Composable (() -> Unit)? = null,
    onValueChange: (TextFieldValue) -> Unit
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

        WantedTextAreaLayout(
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
                    color = when {
                        negative || focused -> {
                            colorResource(id = R.color.background_normal_normal)
                                .copy(alpha = OPACITY_43)
                        }

                        else -> colorResource(R.color.transparent)
                    },
                    width = if (focused) 2.dp else 1.dp
                )
                .border(
                    shape = RoundedCornerShape(12.dp),
                    color = when {
                        !enabled -> colorResource(R.color.line_normal_neutral)
                        negative -> colorResource(R.color.status_negative).copy(OPACITY_43)
                        focused -> colorResource(R.color.primary_normal).copy(OPACITY_43)
                        else -> colorResource(R.color.line_normal_neutral)
                    },
                    width = if (focused) 2.dp else 1.dp
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
                        .padding(horizontal = 12.dp)
                        .padding(vertical = 12.dp)
                        .fillMaxWidth(),
                    value = value,
                    maxLines = maxLines,
                    minLines = minLines,
                    enabled = enabled,
                    interactionSource = interactionSource,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    textStyle = WantedTextStyle(
                        colorRes = if (enabled) R.color.label_normal else R.color.label_alternative,
                        style = DesignSystemTheme.typography.body1Regular
                    ),
                    onValueChange = {

                        when {
                            isGraphemeClusterCount && graphemeClusterCount(text = it.text) <= maxWordCount -> {
                                onValueChange(it)
                            }

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
                            }
                        )
                    }
                )
            },
            leftContent = leftContent,
            rightContent = rightContent
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
        contentAlignment = Alignment.TopStart
    ) {
        placeholder?.let {
            placeholder()
        }

        innerTextField()
    }
}

private fun graphemeClusterCount(text: String): Int {
    val iterator = BreakIterator.getCharacterInstance()
    iterator.setText(text)
    var count = 0
    while (iterator.next() != BreakIterator.DONE) {
        count++
    }
    return count
}

private const val MAX_LINE = 3
private const val MIN_LINE = 1


@DevicePreviews
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
                WantedTextArea(
                    modifier = Modifier,
                    value = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                )

                WantedTextArea(
                    modifier = Modifier,
                    title = "주제",
                    negative = true,
                    requiredBadge = true,
                    value = "입력한 텍스트.",
                    isGraphemeClusterCount = true,
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트"
                )

                WantedTextArea(
                    modifier = Modifier,
                    title = "주제",
                    negative = true,
                    requiredBadge = true,
                    value = "입력한 텍스트.",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    description = "에러가 나면 이렇게 메시지가 나와요"
                )

                WantedTextArea(
                    modifier = Modifier,
                    value = "텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요.",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트"
                )

                WantedTextArea(
                    value = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트"
                )

                WantedTextArea(
                    value = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트"
                )

                WantedTextArea(
                    value = "",
                    placeholder = "텍스트를 입력해 주세요.",
                    minLines = 4,
                    rightContent = {
                        WantedActionChip(text = "WantedActionChip")
                    }
                )

                WantedTextArea(
                    value = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    leftContent = {
                        WantedActionChip(text = "WantedActionChip")
                    }
                )
            }
        }
    }
}