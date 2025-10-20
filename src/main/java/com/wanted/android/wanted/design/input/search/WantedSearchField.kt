package com.wanted.android.wanted.design.input.search

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.input.search.WantedSearchFieldConstant.Size
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.WantedTextStyle
import com.wanted.android.wanted.design.util.clickOnce


@Composable
fun WantedSearchField(
    text: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    enabled: Boolean = true,
    size: Size = Size.Medium(),
    maxWordCount: Int = Int.MAX_VALUE,
    enabledOverflowText: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    focused: State<Boolean> = interactionSource.collectIsFocusedAsState(),
    textStyle: TextStyle = WantedTextStyle(
        colorRes = if (enabled) R.color.label_normal else R.color.label_alternative,
        style = DesignSystemTheme.typography.body1Regular
    ),
    cursorBrush: Brush = SolidColor(textStyle.color),
    focusRequester: FocusRequester = FocusRequester(),
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

    SearchTextField(
        value = textFieldValue,
        placeholder = placeholder,
        size = size,
        enabled = enabled,
        maxWordCount = maxWordCount,
        enabledOverflowText = enabledOverflowText,
        interactionSource = interactionSource,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = modifier,
        focused = focused,
        cursorBrush = cursorBrush,
        textStyle = textStyle,
        focusRequester = focusRequester,
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
}

@Composable
fun WantedSearchField(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    enabled: Boolean = true,
    size: Size = Size.Medium(),
    maxWordCount: Int = Int.MAX_VALUE,
    enabledOverflowText: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    focused: State<Boolean> = interactionSource.collectIsFocusedAsState(),
    textStyle: TextStyle = WantedTextStyle(
        colorRes = if (enabled) R.color.label_normal else R.color.label_alternative,
        style = DesignSystemTheme.typography.body1Regular
    ),
    cursorBrush: Brush = SolidColor(textStyle.color),
    focusRequester: FocusRequester = FocusRequester(),
    onValueChange: (TextFieldValue) -> Unit = {}
) {
    SearchTextField(
        value = value,
        placeholder = placeholder,
        size = size,
        enabled = enabled,
        maxWordCount = maxWordCount,
        enabledOverflowText = enabledOverflowText,
        interactionSource = interactionSource,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = modifier,
        focused = focused,
        cursorBrush = cursorBrush,
        textStyle = textStyle,
        focusRequester = focusRequester,
        onValueChange = onValueChange
    )
}


@Composable
private fun SearchTextField(
    value: TextFieldValue,
    placeholder: String,
    size: Size,
    enabled: Boolean,
    maxWordCount: Int,
    enabledOverflowText: Boolean,
    interactionSource: MutableInteractionSource,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier,
    focused: State<Boolean>,
    cursorBrush: Brush,
    textStyle: TextStyle,
    onValueChange: (TextFieldValue) -> Unit = {},
    focusRequester: FocusRequester = FocusRequester()
) {
    BasicTextField(
        modifier = modifier
            .focusRequester(focusRequester)
            .clip(RoundedCornerShape(12.dp))
            .background(colorResource(R.color.fill_normal))
            .defaultMinSize(minHeight = size.minHeight)
            .fillMaxWidth()
            .padding(size.padding),
        value = value,
        maxLines = 1,
        minLines = 1,
        enabled = enabled,
        singleLine = true,
        cursorBrush = cursorBrush,
        interactionSource = interactionSource,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        textStyle = textStyle,
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
                            style = WantedTextStyle(
                                colorRes = if (enabled) R.color.label_assistive else R.color.label_disable,
                                style = DesignSystemTheme.typography.body1Regular
                            )
                        )
                    }
                } else {
                    null
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(R.drawable.ic_normal_search_svg),
                        tint = if (enabled) {
                            colorResource(id = R.color.label_alternative)
                        } else {
                            colorResource(id = R.color.label_assistive)
                        },
                        contentDescription = null
                    )
                },
                trailingIcon = when {
                    value.text.isNotEmpty() && enabled && focused.value -> {
                        {
                            WantedTouchArea(
                                modifier = Modifier,
                                shape = CircleShape,
                                verticalPadding = 8.dp,
                                horizontalPadding = 8.dp,
                                content = {
                                    Icon(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(CircleShape),
                                        painter = painterResource(R.drawable.icon_normal_circle_close_fill),
                                        tint = colorResource(id = R.color.label_assistive),
                                        contentDescription = null
                                    )
                                },
                                onClick = {
                                    onValueChange(value.copy(""))
                                }
                            )

                        }
                    }

                    else -> null
                }
            )
        }
    )
}


@Composable
private fun DecorationBox(
    innerTextField: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
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
                    .padding(2.dp),
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
    }
}


@DevicePreviews
@Composable
private fun WantedSearchFieldPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedSearchField(
                    text = "",
                    placeholder = "검색어를 입력해주세요"
                )

                WantedSearchField(
                    text = "",
                    placeholder = "검색어를 입력해주세요",
                    enabled = false
                )

                WantedSearchField(
                    text = "입력함",
                    placeholder = "검색어를 입력해주세요",
                    size = Size.Small()
                )

                WantedSearchField(
                    text = "입력함",
                    placeholder = "검색어를 입력해주세요",
                    enabled = false,
                    size = Size.Small()
                )
            }
        }
    }
}