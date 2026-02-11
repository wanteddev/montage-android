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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.input.search.WantedSearchFieldDefaults.Size
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * 검색 입력 필드 컴포넌트입니다.
 *
 * String 타입의 텍스트를 받아 검색 기능을 제공하는 입력 필드를 표시합니다.
 * 검색 아이콘과 삭제 버튼이 자동으로 표시되며, 포커스 상태에 따라 UI가 변경됩니다.
 *
 * 사용 예시 :
 * ```kotlin
 * var searchText by remember { mutableStateOf("") }
 *
 * WantedSearchField(
 *     text = searchText,
 *     placeholder = "검색어를 입력해주세요",
 *     onValueChange = { searchText = it }
 * )
 * ```
 *
 * @param text String: 입력 필드에 표시할 텍스트입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param placeholder String: 입력 필드가 비어있을 때 표시할 힌트 텍스트입니다.
 * @param enabled Boolean: 입력 필드의 활성화 여부입니다. false인 경우 사용자 입력이 불가능합니다.
 * @param size Size: 입력 필드의 크기입니다. Size.Medium() 또는 Size.Small()을 사용할 수 있습니다.
 * @param maxWordCount Int: 입력 가능한 최대 글자 수입니다.
 * @param enabledOverflowText Boolean: 최대 글자 수를 초과하는 입력을 허용할지 여부입니다.
 * @param interactionSource MutableInteractionSource: 사용자 상호작용 상태를 추적하는 소스입니다.
 * @param keyboardOptions KeyboardOptions: 키보드 옵션 설정입니다.
 * @param keyboardActions KeyboardActions: 키보드 액션 설정입니다.
 * @param focused State<Boolean>: 입력 필드의 포커스 상태입니다.
 * @param textStyle TextStyle: 입력 텍스트의 스타일입니다.
 * @param cursorBrush Brush: 커서의 색상을 지정하는 브러시입니다.
 * @param focusRequester FocusRequester: 포커스 요청을 처리하는 객체입니다.
 * @param onValueChange (String) -> Unit: 텍스트 값이 변경될 때 호출되는 콜백 함수입니다.
 */
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
    textStyle: TextStyle = DesignSystemTheme.typography.body1Regular.copy(
        color = if (enabled) {
            DesignSystemTheme.colors.labelNormal
        } else {
            DesignSystemTheme.colors.labelAlternative
        }
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

/**
 * 검색 입력 필드 컴포넌트입니다.
 *
 * TextFieldValue 타입의 값을 받아 검색 기능을 제공하는 입력 필드를 표시합니다.
 * 커서 위치와 선택 영역을 세밀하게 제어해야 하는 경우 이 오버로드를 사용합니다.
 *
 * 사용 예시 :
 * ```kotlin
 * var textFieldValue by remember { mutableStateOf(TextFieldValue()) }
 *
 * WantedSearchField(
 *     value = textFieldValue,
 *     placeholder = "검색어를 입력해주세요",
 *     onValueChange = { textFieldValue = it }
 * )
 * ```
 *
 * @param value TextFieldValue: 입력 필드의 값, 커서 위치, 선택 영역을 포함하는 객체입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param placeholder String: 입력 필드가 비어있을 때 표시할 힌트 텍스트입니다.
 * @param enabled Boolean: 입력 필드의 활성화 여부입니다. false인 경우 사용자 입력이 불가능합니다.
 * @param size Size: 입력 필드의 크기입니다. Size.Medium() 또는 Size.Small()을 사용할 수 있습니다.
 * @param maxWordCount Int: 입력 가능한 최대 글자 수입니다.
 * @param enabledOverflowText Boolean: 최대 글자 수를 초과하는 입력을 허용할지 여부입니다.
 * @param interactionSource MutableInteractionSource: 사용자 상호작용 상태를 추적하는 소스입니다.
 * @param keyboardOptions KeyboardOptions: 키보드 옵션 설정입니다.
 * @param keyboardActions KeyboardActions: 키보드 액션 설정입니다.
 * @param focused State<Boolean>: 입력 필드의 포커스 상태입니다.
 * @param textStyle TextStyle: 입력 텍스트의 스타일입니다.
 * @param cursorBrush Brush: 커서의 색상을 지정하는 브러시입니다.
 * @param focusRequester FocusRequester: 포커스 요청을 처리하는 객체입니다.
 * @param onValueChange (TextFieldValue) -> Unit: 텍스트 값이 변경될 때 호출되는 콜백 함수입니다.
 */
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
    textStyle: TextStyle = DesignSystemTheme.typography.body1Regular.copy(
        color = if (enabled) {
            DesignSystemTheme.colors.labelNormal
        } else {
            DesignSystemTheme.colors.labelAlternative
        }
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
            .background(DesignSystemTheme.colors.fillNormal)
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
                            style = DesignSystemTheme.typography.body1Regular,
                            color = if (enabled) {
                                DesignSystemTheme.colors.labelAssistive
                            } else {
                                DesignSystemTheme.colors.labelDisable
                            }
                        )
                    }
                } else {
                    null
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(R.drawable.icon_normal_search),
                        tint = if (enabled) {
                            DesignSystemTheme.colors.labelAlternative
                        } else {
                            DesignSystemTheme.colors.labelAssistive
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
                                        tint = DesignSystemTheme.colors.labelAssistive,
                                        contentDescription = null
                                    )
                                },
                                onClick = {
                                    onValueChange(
                                        value.copy(
                                            text = "",
                                            selection = TextRange.Zero,
                                            composition = null
                                        )
                                    )
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