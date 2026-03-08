package com.wanted.android.wanted.design.input.textinput.textarea

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.actions.chip.WantedChip
import com.wanted.android.wanted.design.base.WantedDropShadowDefaults
import com.wanted.android.wanted.design.base.wantedDropShadow
import com.wanted.android.wanted.design.input.textinput.view.ComponentTitle
import com.wanted.android.wanted.design.input.textinput.view.WantedTextAreaCharacterCount
import com.wanted.android.wanted.design.input.textinput.view.WantedTextAreaLayout
import com.wanted.android.wanted.design.input.textinput.view.WantedTextInputLayout
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_43
import java.text.BreakIterator


/**
 * WantedTextArea
 *
 * 여러 줄의 텍스트 입력이 필요한 경우 사용하는 입력 컴포넌트입니다.
 *
 * 버튼, 아이콘, 타이틀, 설명 등을 유연하게 조합할 수 있습니다.
 * 내부적으로 TextFieldValue를 상태로 관리하며 onValueChange를 통해 외부에 값을 전달합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedTextArea(
 *     text = "내용",
 *     title = "설명",
 *     placeholder = "입력해주세요",
 *     rightButton = "완료",
 *     onValueChange = { newText -> ... }
 * )
 * ```
 *
 * @param text String: 입력된 텍스트 값입니다.
 * @param modifier Modifier: 외형 및 레이아웃 조정용입니다.
 * @param placeholder String: 힌트로 보여질 텍스트입니다.
 * @param title String: 상단 제목 텍스트입니다.
 * @param description String?: 하단 메시지 또는 설명입니다.
 * @param rightButton String?: 우측 버튼 텍스트입니다.
 * @param leadingContent (() -> Unit)?: 왼쪽 슬롯 콘텐츠입니다.
 * @param trailingContent (() -> Unit)?: 오른쪽 슬롯 콘텐츠입니다.
 * @param enabled Boolean: 입력 활성화 여부입니다.
 * @param negative Boolean: 에러 상태 여부입니다.
 * @param maxLines Int: 최대 줄 수입니다.
 * @param minLines Int: 최소 줄 수입니다.
 * @param maxWordCount Int: 입력 가능한 최대 글자 수입니다.
 * @param enabledOverflowText Boolean: 글자 수 초과 입력 허용 여부입니다.
 * @param isGraphemeClusterCount Boolean: 문자 수 대신 grapheme cluster 기준으로 글자 수를 셉니다.
 * @param requiredBadge Boolean: 필수 입력 뱃지 표시 여부입니다.
 * @param interactionSource MutableInteractionSource: 포커스 등 인터랙션 추적용입니다.
 * @param keyboardOptions KeyboardOptions: 키보드 설정입니다.
 * @param keyboardActions KeyboardActions: 키보드 액션 설정입니다.
 * @param background Color: 배경 색상입니다.
 * @param visualTransformation VisualTransformation: 텍스트 표시 방식을 변환합니다 (예: 비밀번호 마스킹).
 * @param onClickRightButton () -> Unit: 우측 버튼 클릭 콜백입니다.
 * @param onValueChange (String) -> Unit: 값 변경 콜백입니다.
 */
@Composable
fun WantedTextArea(
    text: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    title: String = "",
    description: String? = null,
    rightButton: String? = null,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    negative: Boolean = false,
    maxLines: Int = MAX_LINE,
    minLines: Int = MIN_LINE,
    maxWordCount: Int = 2000,
    enabledOverflowText: Boolean = false,
    isGraphemeClusterCount: Boolean = false,
    requiredBadge: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focusRequester: FocusRequester = remember { FocusRequester() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = colorResource(id = R.color.background_transparent_alternative),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onClickRightButton: () -> Unit = {},
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
            if (leadingContent == null && trailingContent == null) {
                WantedTextArea(
                    modifier = Modifier,
                    value = textFieldValue,
                    negative = negative,
                    enabled = enabled,
                    maxLines = maxLines,
                    minLines = minLines,
                    maxWordCount = maxWordCount,
                    enabledOverflowText = enabledOverflowText,
                    isGraphemeClusterCount = isGraphemeClusterCount,
                    interactionSource = interactionSource,
                    focusRequester = focusRequester,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    rightButton = rightButton,
                    placeholder = placeholder,
                    background = background,
                    visualTransformation = visualTransformation,
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
            } else {
                WantedTextAreaContent(
                    modifier = Modifier,
                    value = textFieldValue,
                    negative = negative,
                    enabled = enabled,
                    maxLines = maxLines,
                    minLines = minLines,
                    maxWordCount = maxWordCount,
                    enabledOverflowText = enabledOverflowText,
                    isGraphemeClusterCount = isGraphemeClusterCount,
                    interactionSource = interactionSource,
                    focusRequester = focusRequester,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    rightContent = trailingContent,
                    leftContent = leadingContent,
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
            }

        },
        message = if (description.isNullOrEmpty()) null else {
            {
                Text(
                    text = description,
                    style = DesignSystemTheme.typography.caption1Regular,
                    color = when {
                        enabled && negative -> DesignSystemTheme.colors.statusNegative
                        else -> DesignSystemTheme.colors.labelAlternative
                    },
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    )
}

/**
 * WantedTextArea
 *
 * 텍스트 입력 컴포넌트입니다.
 *
 * 커서, 선택 영역 등 복잡한 상태를 다룰 수 있는 TextFieldValue를 사용합니다.
 *
 * 사용 예시:
 * ```kotlin
 * val state = remember { mutableStateOf(TextFieldValue("입력값")) }
 * WantedTextArea(value = state.value, onValueChange = { state.value = it })
 * ```
 *
 * @param value TextFieldValue: 입력 값 및 커서, 선택 정보 등을 포함합니다.
 * @param onValueChange (TextFieldValue) -> Unit: 값 변경 콜백입니다.
 * @param modifier Modifier: 외형 및 레이아웃 조정용입니다.
 * @param placeholder String: 힌트 텍스트입니다.
 * @param title String: 상단 제목입니다.
 * @param description String?: 하단 설명 또는 상태 메시지입니다.
 * @param rightButton String?: 우측 버튼 텍스트입니다.
 * @param enabled Boolean: 입력 활성화 여부입니다.
 * @param negative Boolean: 에러 상태 여부입니다.
 * @param maxLines Int: 최대 줄 수입니다.
 * @param minLines Int: 최소 줄 수입니다.
 * @param maxWordCount Int: 입력 가능한 최대 글자 수입니다.
 * @param enabledOverflowText Boolean: 글자 수 초과 허용 여부입니다.
 * @param requiredBadge Boolean: 필수 입력 여부입니다.
 * @param isGraphemeClusterCount Boolean: grapheme cluster 기준 글자 수 사용 여부입니다.
 * @param interactionSource MutableInteractionSource: 포커스 등 인터랙션 추적용입니다.
 * @param keyboardOptions KeyboardOptions: 키보드 동작 설정입니다.
 * @param keyboardActions KeyboardActions: 키보드 액션 설정입니다.
 * @param background Color: 배경 색상입니다.
 * @param visualTransformation VisualTransformation: 텍스트 표시 방식을 변환합니다 (예: 비밀번호 마스킹).
 */
@Composable
fun WantedTextArea(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    title: String = "",
    description: String? = null,
    rightButton: String? = null,
    enabled: Boolean = true,
    negative: Boolean = false,
    maxLines: Int = MAX_LINE,
    minLines: Int = MIN_LINE,
    maxWordCount: Int = 2000,
    enabledOverflowText: Boolean = false,
    requiredBadge: Boolean = false,
    isGraphemeClusterCount: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focusRequester: FocusRequester = remember { FocusRequester() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = colorResource(id = R.color.background_transparent_alternative),
    visualTransformation: VisualTransformation = VisualTransformation.None,
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
                maxLines = maxLines,
                minLines = minLines,
                maxWordCount = maxWordCount,
                enabledOverflowText = enabledOverflowText,
                isGraphemeClusterCount = isGraphemeClusterCount,
                interactionSource = interactionSource,
                focusRequester = focusRequester,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                rightButton = rightButton,
                placeholder = placeholder,
                background = background,
                visualTransformation = visualTransformation,
                onClickRightButton = onClickRightButton,
                onValueChange = onValueChange
            )
        },
        message = description?.let {
            {
                Text(
                    text = description,
                    style = DesignSystemTheme.typography.caption1Regular,
                    color = when {
                        enabled && negative -> DesignSystemTheme.colors.statusNegative
                        else -> DesignSystemTheme.colors.labelAlternative
                    },
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    )
}


@Composable
fun WantedTextArea(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    title: String = "",
    description: String? = null,
    enabled: Boolean = true,
    negative: Boolean = false,
    maxLines: Int = MAX_LINE,
    minLines: Int = MIN_LINE,
    maxWordCount: Int = 2000,
    enabledOverflowText: Boolean = false,
    requiredBadge: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focusRequester: FocusRequester = remember { FocusRequester() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = colorResource(id = R.color.background_transparent_alternative),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leftContent: (@Composable (() -> Unit))? = null,
    rightContent: (@Composable (() -> Unit))? = null,
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
            WantedTextAreaContent(
                modifier = Modifier,
                value = value,
                negative = negative,
                enabled = enabled,
                maxLines = maxLines,
                minLines = minLines,
                maxWordCount = maxWordCount,
                enabledOverflowText = enabledOverflowText,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                focusRequester = focusRequester,
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
                    style = DesignSystemTheme.typography.caption1Regular.copy(
                        color = when {
                            enabled && negative -> DesignSystemTheme.colors.statusNegative
                            else -> DesignSystemTheme.colors.labelAlternative
                        }
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
    value: TextFieldValue,
    rightButton: String?,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    negative: Boolean = false,
    enabled: Boolean = true,
    focusRequester: FocusRequester,
    isGraphemeClusterCount: Boolean = false,
    maxLines: Int = MAX_LINE,
    minLines: Int = MIN_LINE,
    maxWordCount: Int = 2000,
    enabledOverflowText: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = colorResource(id = R.color.background_transparent_alternative),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onClickRightButton: () -> Unit = {},
    onValueChange: (TextFieldValue) -> Unit = {}
) {
    WantedTextAreaContent(
        modifier = modifier,
        value = value,
        placeholder = placeholder,
        negative = negative,
        enabled = enabled,
        focusRequester = focusRequester,
        maxLines = maxLines,
        minLines = minLines,
        maxWordCount = maxWordCount,
        enabledOverflowText = enabledOverflowText,
        isGraphemeClusterCount = isGraphemeClusterCount,
        interactionSource = interactionSource,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        background = background,
        visualTransformation = visualTransformation,
        leftContent = {
            WantedTextAreaCharacterCount(
                current = if (isGraphemeClusterCount) {
                    graphemeClusterCount(value.text)
                } else {
                    value.text.length
                },
                error = value.text.length > maxWordCount,
                enable = enabled,
                maxWordCount = maxWordCount
            )
        },
        rightContent = {
            rightButton?.let {
                WantedButton(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    text = rightButton,
                    variant = ButtonVariant.TEXT,
                    enabled = enabled && !negative,
                    onClick = { onClickRightButton() }
                )
            }
        },
        onValueChange = onValueChange
    )
}


@Composable
private fun WantedTextAreaContent(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    negative: Boolean = false,
    enabled: Boolean = true,
    focusRequester: FocusRequester,
    maxLines: Int = MAX_LINE,
    minLines: Int = MIN_LINE,
    maxWordCount: Int = 2000,
    enabledOverflowText: Boolean = false,
    isGraphemeClusterCount: Boolean = false, // 커서 숫자로 판단 - 이모지 때문
    cursorBrush: Brush = SolidColor(DesignSystemTheme.colors.primaryNormal),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focused: State<Boolean> = interactionSource.collectIsFocusedAsState(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = colorResource(id = R.color.background_transparent_alternative),
    leftContent: @Composable (() -> Unit)? = null,
    rightContent: @Composable (() -> Unit)? = null,
    onValueChange: (TextFieldValue) -> Unit
) {
    ConstraintLayout(modifier = modifier) {

        WantedTextAreaLayout(
            modifier = Modifier
                .wantedDropShadow(WantedDropShadowDefaults.WantedShadowStyle.XSmall())
                .clip(RoundedCornerShape(12.dp))
                .border(
                    shape = RoundedCornerShape(12.dp),
                    color = when {
                        !enabled -> DesignSystemTheme.colors.lineNormalNeutral
                        negative -> DesignSystemTheme.colors.statusNegative.copy(OPACITY_43)
                        focused.value -> DesignSystemTheme.colors.primaryNormal.copy(OPACITY_43)
                        else -> DesignSystemTheme.colors.lineNormalNeutral
                    },
                    width = if (focused.value) 2.dp else 1.dp
                )
                .border(
                    shape = RoundedCornerShape(12.dp),
                    color = when {
                        negative || focused.value -> {
                            DesignSystemTheme.colors.backgroundNormalNormal
                        }

                        else -> DesignSystemTheme.colors.transparent
                    },
                    width = if (focused.value) 2.dp else 1.dp
                )
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
                    value = value,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(vertical = 12.dp)
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    maxLines = maxLines,
                    minLines = minLines,
                    enabled = enabled,
                    visualTransformation = visualTransformation,
                    interactionSource = interactionSource,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    cursorBrush = cursorBrush,
                    textStyle = DesignSystemTheme.typography.body1Regular.copy(
                        color = if (enabled) {
                            DesignSystemTheme.colors.labelNormal
                        } else {
                            DesignSystemTheme.colors.labelAlternative
                        }
                    ),
                    onValueChange = {

                        when {
                            isGraphemeClusterCount && graphemeClusterCount(text = it.text) <= maxWordCount -> {
                                onValueChange(it)
                            }

                            enabledOverflowText -> onValueChange(it)
                            it.text.length <= maxWordCount -> onValueChange(it)
                            it.text.length < value.text.length -> onValueChange(it)
                            it.text == value.text -> onValueChange(it)
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
    innerTextField: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit)? = null
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
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                )

                WantedTextArea(
                    modifier = Modifier,
                    title = "주제",
                    negative = true,
                    requiredBadge = true,
                    text = "입력한 텍스트.",
                    isGraphemeClusterCount = true,
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트"
                )

                WantedTextArea(
                    modifier = Modifier,
                    title = "주제",
                    negative = true,
                    requiredBadge = true,
                    text = "입력한 텍스트.",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    description = "에러가 나면 이렇게 메시지가 나와요"
                )

                WantedTextArea(
                    modifier = Modifier,
                    text = "텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요.",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트"
                )

                WantedTextArea(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트"
                )

                WantedTextArea(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트"
                )

                WantedTextArea(
                    text = "",
                    placeholder = "텍스트를 입력해 주세요.",
                    minLines = 4,
                    trailingContent = {
                        WantedChip(text = "WantedActionChip")
                    }
                )

                WantedTextArea(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    leadingContent = {
                        WantedChip(text = "WantedActionChip")
                    }
                )
            }
        }
    }
}