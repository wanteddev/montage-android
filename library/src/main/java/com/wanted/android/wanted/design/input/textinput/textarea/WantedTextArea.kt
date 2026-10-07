package com.wanted.android.wanted.design.input.textinput.textarea

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.formcontrol.WantedFormControl
import com.wanted.android.wanted.design.input.formcontrol.WantedFormControlDefaults
import com.wanted.android.wanted.design.input.framedstyle.focusRing
import com.wanted.android.wanted.design.input.textinput.textarea.WantedTextAreaDefaults.DEFAULT_MAX_LINE
import com.wanted.android.wanted.design.input.textinput.textarea.WantedTextAreaDefaults.DEFAULT_MIN_LINE
import com.wanted.android.wanted.design.input.textinput.view.WantedTextAreaCharacterCount
import com.wanted.android.wanted.design.input.textinput.view.WantedTextAreaLayout
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_12
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.OPACITY_52
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
 *     onValueChange = { newText -> ... }
 * )
 * ```
 *
 * @param text String: 입력된 텍스트 값입니다.
 * @param modifier Modifier: 외형 및 레이아웃 조정용입니다.
 * @param placeholder String: 힌트로 보여질 텍스트입니다.
 * @param title String: 상단 제목 텍스트입니다.
 * @param description String?: 하단 메시지 또는 설명입니다.
 * @param leadingContent (() -> Unit)?: 하단 영역 왼쪽 슬롯 콘텐츠입니다.
 * @param trailingContent (() -> Unit)?: 하단 영역 오른쪽 슬롯 콘텐츠입니다.
 * @param size WantedTextAreaDefaults.Size: TextArea 크기입니다. (Large, Medium)
 * @param resize WantedTextAreaDefaults.Resize: 높이 확장 동작입니다. (Normal, Limit, Fixed)
 * @param enabled Boolean: 입력 활성화 여부입니다.
 * @param status WantedTextAreaDefaults.Status: 입력 상태입니다. (Normal, Negative)
 * @param maxLines Int: 최대 줄 수입니다. Resize.Limit 일 때 스크롤 기준이 됩니다.
 * @param minLines Int: 최소 줄 수입니다.
 * @param maxWordCount Int: 입력 가능한 최대 글자 수입니다.
 * @param enabledOverflowText Boolean: 글자 수 초과 입력 허용 여부입니다.
 * @param isGraphemeClusterCount Boolean: grapheme cluster 기준으로 글자 수를 셉니다.
 * @param requiredBadge Boolean: 필수 입력 뱃지 표시 여부입니다.
 * @param interactionSource MutableInteractionSource: 포커스 등 인터랙션 추적용입니다.
 * @param keyboardOptions KeyboardOptions: 키보드 설정입니다.
 * @param keyboardActions KeyboardActions: 키보드 액션 설정입니다.
 * @param background Color: 배경 색상입니다.
 * @param visualTransformation VisualTransformation: 텍스트 표시 방식을 변환합니다 (예: 비밀번호 마스킹).
 * @param onValueChange (String) -> Unit: 값 변경 콜백입니다.
 */
@Deprecated(DEPRECATED_FORM_CONTROL_MESSAGE, level = DeprecationLevel.WARNING)
@Composable
fun WantedTextArea(
    text: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    title: String = "",
    description: String? = null,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    size: WantedTextAreaDefaults.Size = WantedTextAreaDefaults.Size.Large,
    resize: WantedTextAreaDefaults.Resize = WantedTextAreaDefaults.Resize.Limit,
    enabled: Boolean = true,
    status: WantedTextAreaDefaults.Status = WantedTextAreaDefaults.Status.Normal,
    maxLines: Int = LEGACY_MAX_LINE,
    minLines: Int = LEGACY_MIN_LINE,
    maxWordCount: Int = 2000,
    enabledOverflowText: Boolean = false,
    isGraphemeClusterCount: Boolean = false,
    requiredBadge: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focusRequester: FocusRequester = remember { FocusRequester() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = colorResource(id = R.color.effect_transparent_secondary),
    visualTransformation: VisualTransformation = VisualTransformation.None,
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

    WantedFormControl(
        modifier = modifier,
        label = title,
        required = requiredBadge,
        description = description?.takeIf { it.isNotEmpty() },
        size = size.toFormControlSize(),
        status = status.toFormControlStatus(),
        enabled = enabled,
        input = {
            WantedTextAreaContent(
                modifier = Modifier,
                value = textFieldValue,
                status = status,
                enabled = enabled,
                size = size,
                resize = resize,
                maxLines = maxLines,
                minLines = minLines,
                maxWordCount = maxWordCount,
                enabledOverflowText = enabledOverflowText,
                isGraphemeClusterCount = isGraphemeClusterCount,
                interactionSource = interactionSource,
                focusRequester = focusRequester,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                placeholder = placeholder,
                background = background,
                visualTransformation = visualTransformation,
                leadingContent = legacyLeadingContent(
                    leadingContent = leadingContent,
                    trailingContent = trailingContent,
                    text = textFieldValue.text,
                    maxWordCount = maxWordCount,
                    isGraphemeClusterCount = isGraphemeClusterCount,
                    enabled = enabled
                ),
                trailingContent = trailingContent,
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
 * @param size WantedTextAreaDefaults.Size: TextArea 크기입니다.
 * @param resize WantedTextAreaDefaults.Resize: 높이 확장 동작입니다.
 * @param enabled Boolean: 입력 활성화 여부입니다.
 * @param status WantedTextAreaDefaults.Status: 입력 상태입니다. (Normal, Negative)
 * @param maxLines Int: 최대 줄 수입니다.
 * @param minLines Int: 최소 줄 수입니다.
 * @param maxWordCount Int: 입력 가능한 최대 글자 수입니다.
 * @param enabledOverflowText Boolean: 글자 수 초과 허용 여부입니다.
 * @param requiredBadge Boolean: 필수 입력 여부입니다.
 * @param isGraphemeClusterCount Boolean: grapheme cluster 기준 글자 수 사용 여부입니다.
 * @param leadingContent (() -> Unit)?: 하단 영역 왼쪽 슬롯 콘텐츠입니다.
 * @param trailingContent (() -> Unit)?: 하단 영역 오른쪽 슬롯 콘텐츠입니다.
 * @param interactionSource MutableInteractionSource: 포커스 등 인터랙션 추적용입니다.
 * @param keyboardOptions KeyboardOptions: 키보드 동작 설정입니다.
 * @param keyboardActions KeyboardActions: 키보드 액션 처리입니다.
 * @param background Color: 배경 색상입니다.
 * @param visualTransformation VisualTransformation: 텍스트 표시 방식을 변환합니다 (예: 비밀번호 마스킹).
 */
@Deprecated(DEPRECATED_FORM_CONTROL_MESSAGE, level = DeprecationLevel.WARNING)
@Composable
fun WantedTextArea(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    title: String = "",
    description: String? = null,
    size: WantedTextAreaDefaults.Size = WantedTextAreaDefaults.Size.Large,
    resize: WantedTextAreaDefaults.Resize = WantedTextAreaDefaults.Resize.Limit,
    enabled: Boolean = true,
    status: WantedTextAreaDefaults.Status = WantedTextAreaDefaults.Status.Normal,
    maxLines: Int = LEGACY_MAX_LINE,
    minLines: Int = LEGACY_MIN_LINE,
    maxWordCount: Int = 2000,
    enabledOverflowText: Boolean = false,
    requiredBadge: Boolean = false,
    isGraphemeClusterCount: Boolean = false,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focusRequester: FocusRequester = remember { FocusRequester() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = colorResource(id = R.color.effect_transparent_secondary),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (TextFieldValue) -> Unit = {}
) {
    WantedFormControl(
        modifier = modifier,
        label = title,
        required = requiredBadge,
        description = description?.takeIf { it.isNotEmpty() },
        size = size.toFormControlSize(),
        status = status.toFormControlStatus(),
        enabled = enabled,
        input = {
            WantedTextAreaContent(
                modifier = Modifier,
                value = value,
                status = status,
                enabled = enabled,
                size = size,
                resize = resize,
                maxLines = maxLines,
                minLines = minLines,
                maxWordCount = maxWordCount,
                enabledOverflowText = enabledOverflowText,
                isGraphemeClusterCount = isGraphemeClusterCount,
                interactionSource = interactionSource,
                focusRequester = focusRequester,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                placeholder = placeholder,
                background = background,
                visualTransformation = visualTransformation,
                leadingContent = legacyLeadingContent(
                    leadingContent = leadingContent,
                    trailingContent = trailingContent,
                    text = value.text,
                    maxWordCount = maxWordCount,
                    isGraphemeClusterCount = isGraphemeClusterCount,
                    enabled = enabled
                ),
                trailingContent = trailingContent,
                onValueChange = onValueChange
            )
        }
    )
}


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
 *     button = "완료",
 *     onValueChange = { newText -> ... }
 * )
 * ```
 *
 * @param text String: 입력된 텍스트 값입니다.
 * @param modifier Modifier: 외형 및 레이아웃 조정용입니다.
 * @param placeholder String: 힌트로 보여질 텍스트입니다.
 * @param title String: 상단 제목 텍스트입니다.
 * @param description String?: 하단 메시지 또는 설명입니다.
 * @param button String?: 하단 버튼 텍스트입니다.
 * @param leadingContent (() -> Unit)?: 하단 영역 왼쪽 슬롯 콘텐츠입니다.
 * @param size WantedTextAreaDefaults.Size: TextArea 크기입니다. (Large, Medium)
 * @param resize WantedTextAreaDefaults.Resize: 높이 확장 동작입니다. (Normal, Limit, Fixed)
 * @param enabled Boolean: 입력 활성화 여부입니다.
 * @param status WantedTextAreaDefaults.Status: 입력 상태입니다. (Normal, Negative)
 * @param maxLines Int: 최대 줄 수입니다. Resize.Limit 일 때 스크롤 기준이 됩니다.
 * @param minLines Int: 최소 줄 수입니다.
 * @param maxWordCount Int: 입력 가능한 최대 글자 수입니다.
 * @param enabledOverflowText Boolean: 글자 수 초과 입력 허용 여부입니다.
 * @param isGraphemeClusterCount Boolean: grapheme cluster 기준으로 글자 수를 셉니다.
 * @param requiredBadge Boolean: 필수 입력 뱃지 표시 여부입니다.
 * @param interactionSource MutableInteractionSource: 포커스 등 인터랙션 추적용입니다.
 * @param keyboardOptions KeyboardOptions: 키보드 설정입니다.
 * @param keyboardActions KeyboardActions: 키보드 액션 설정입니다.
 * @param background Color: 배경 색상입니다.
 * @param visualTransformation VisualTransformation: 텍스트 표시 방식을 변환합니다 (예: 비밀번호 마스킹).
 * @param onClickButton () -> Unit: 버튼 클릭 콜백입니다.
 * @param onValueChange (String) -> Unit: 값 변경 콜백입니다.
 */
@Deprecated(DEPRECATED_FORM_CONTROL_MESSAGE, level = DeprecationLevel.WARNING)
@Composable
fun WantedTextArea(
    text: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    title: String = "",
    description: String? = null,
    button: String? = null,
    leadingContent: (@Composable () -> Unit)? = null,
    size: WantedTextAreaDefaults.Size = WantedTextAreaDefaults.Size.Large,
    resize: WantedTextAreaDefaults.Resize = WantedTextAreaDefaults.Resize.Limit,
    enabled: Boolean = true,
    status: WantedTextAreaDefaults.Status = WantedTextAreaDefaults.Status.Normal,
    maxLines: Int = LEGACY_MAX_LINE,
    minLines: Int = LEGACY_MIN_LINE,
    maxWordCount: Int = 2000,
    enabledOverflowText: Boolean = false,
    isGraphemeClusterCount: Boolean = false,
    requiredBadge: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focusRequester: FocusRequester = remember { FocusRequester() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = colorResource(id = R.color.effect_transparent_secondary),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onClickButton: () -> Unit = {},
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

    WantedFormControl(
        modifier = modifier,
        label = title,
        required = requiredBadge,
        description = description?.takeIf { it.isNotEmpty() },
        size = size.toFormControlSize(),
        status = status.toFormControlStatus(),
        enabled = enabled,
        input = {
            WantedTextAreaContent(
                modifier = Modifier,
                value = textFieldValue,
                status = status,
                enabled = enabled,
                size = size,
                resize = resize,
                maxLines = maxLines,
                minLines = minLines,
                maxWordCount = maxWordCount,
                enabledOverflowText = enabledOverflowText,
                isGraphemeClusterCount = isGraphemeClusterCount,
                interactionSource = interactionSource,
                focusRequester = focusRequester,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                button = button,
                placeholder = placeholder,
                background = background,
                visualTransformation = visualTransformation,
                leadingContent = legacyLeadingContent(
                    leadingContent = leadingContent,
                    trailingContent = null,
                    text = textFieldValue.text,
                    maxWordCount = maxWordCount,
                    isGraphemeClusterCount = isGraphemeClusterCount,
                    enabled = enabled
                ),
                onClickButton = onClickButton,
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
 * @param button String?: 하단 버튼 텍스트입니다.
 * @param size WantedTextAreaDefaults.Size: TextArea 크기입니다.
 * @param resize WantedTextAreaDefaults.Resize: 높이 확장 동작입니다.
 * @param enabled Boolean: 입력 활성화 여부입니다.
 * @param status WantedTextAreaDefaults.Status: 입력 상태입니다. (Normal, Negative)
 * @param maxLines Int: 최대 줄 수입니다.
 * @param minLines Int: 최소 줄 수입니다.
 * @param maxWordCount Int: 입력 가능한 최대 글자 수입니다.
 * @param enabledOverflowText Boolean: 글자 수 초과 허용 여부입니다.
 * @param requiredBadge Boolean: 필수 입력 여부입니다.
 * @param isGraphemeClusterCount Boolean: grapheme cluster 기준 글자 수 사용 여부입니다.
 * @param leadingContent (() -> Unit)?: 하단 영역 왼쪽 슬롯 콘텐츠입니다.
 * @param interactionSource MutableInteractionSource: 포커스 등 인터랙션 추적용입니다.
 * @param keyboardOptions KeyboardOptions: 키보드 동작 설정입니다.
 * @param keyboardActions KeyboardActions: 키보드 액션 처리입니다.
 * @param background Color: 배경 색상입니다.
 * @param visualTransformation VisualTransformation: 텍스트 표시 방식을 변환합니다 (예: 비밀번호 마스킹).
 */
@Deprecated(DEPRECATED_FORM_CONTROL_MESSAGE, level = DeprecationLevel.WARNING)
@Composable
fun WantedTextArea(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    title: String = "",
    description: String? = null,
    button: String? = null,
    size: WantedTextAreaDefaults.Size = WantedTextAreaDefaults.Size.Large,
    resize: WantedTextAreaDefaults.Resize = WantedTextAreaDefaults.Resize.Limit,
    enabled: Boolean = true,
    status: WantedTextAreaDefaults.Status = WantedTextAreaDefaults.Status.Normal,
    maxLines: Int = LEGACY_MAX_LINE,
    minLines: Int = LEGACY_MIN_LINE,
    maxWordCount: Int = 2000,
    enabledOverflowText: Boolean = false,
    requiredBadge: Boolean = false,
    isGraphemeClusterCount: Boolean = false,
    leadingContent: (@Composable () -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focusRequester: FocusRequester = remember { FocusRequester() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = colorResource(id = R.color.effect_transparent_secondary),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onClickButton: () -> Unit = {},
    onValueChange: (TextFieldValue) -> Unit = {}
) {
    WantedFormControl(
        modifier = modifier,
        label = title,
        required = requiredBadge,
        description = description?.takeIf { it.isNotEmpty() },
        size = size.toFormControlSize(),
        status = status.toFormControlStatus(),
        enabled = enabled,
        input = {
            WantedTextAreaContent(
                modifier = Modifier,
                value = value,
                status = status,
                enabled = enabled,
                size = size,
                resize = resize,
                maxLines = maxLines,
                minLines = minLines,
                maxWordCount = maxWordCount,
                enabledOverflowText = enabledOverflowText,
                isGraphemeClusterCount = isGraphemeClusterCount,
                interactionSource = interactionSource,
                focusRequester = focusRequester,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                button = button,
                placeholder = placeholder,
                background = background,
                visualTransformation = visualTransformation,
                leadingContent = legacyLeadingContent(
                    leadingContent = leadingContent,
                    trailingContent = null,
                    text = value.text,
                    maxWordCount = maxWordCount,
                    isGraphemeClusterCount = isGraphemeClusterCount,
                    enabled = enabled
                ),
                onClickButton = onClickButton,
                onValueChange = onValueChange
            )
        }
    )
}

/**
 * WantedTextArea
 *
 * 여러 줄의 텍스트 입력이 필요한 경우 사용하는 입력 컴포넌트입니다.
 *
 * 라벨/설명/필수 뱃지 등 폼 부가 요소는 포함하지 않습니다.
 * 해당 요소가 필요하면 WantedFormControl 의 input 슬롯에 조합해 사용합니다.
 *
 * 사용 예시:
 * ```kotlin
 * val state = remember { mutableStateOf(TextFieldValue("입력값")) }
 * WantedFormControl(
 *     label = "설명",
 *     input = {
 *         WantedTextArea(
 *             value = state.value,
 *             placeholder = "입력해주세요",
 *             onValueChange = { state.value = it }
 *         )
 *     }
 * )
 * ```
 *
 * @param value TextFieldValue: 입력 값 및 커서, 선택 정보 등을 포함합니다.
 * @param modifier Modifier: 외형 및 레이아웃 조정용입니다.
 * @param placeholder String: 힌트로 보여질 텍스트입니다.
 * @param status WantedTextAreaDefaults.Status: 입력 상태입니다. (Normal, Negative)
 * @param enabled Boolean: 입력 활성화 여부입니다.
 * @param focusRequester FocusRequester: 입력 필드 포커스 요청용입니다.
 * @param size WantedTextAreaDefaults.Size: TextArea 크기입니다. (Large, Medium)
 * @param resize WantedTextAreaDefaults.Resize: 높이 확장 동작입니다. (Normal, Limit, Fixed)
 * @param maxLines Int: 최대 줄 수입니다. Resize.Limit 일 때 스크롤 기준이 됩니다.
 * @param minLines Int: 최소 줄 수입니다.
 * @param maxWordCount Int: 입력 가능한 최대 글자 수입니다.
 * @param enabledOverflowText Boolean: 글자 수 초과 입력 허용 여부입니다.
 * @param isGraphemeClusterCount Boolean: grapheme cluster 기준으로 글자 수를 셉니다.
 * @param visualTransformation VisualTransformation: 텍스트 표시 방식을 변환합니다 (예: 비밀번호 마스킹).
 * @param interactionSource MutableInteractionSource: 포커스 등 인터랙션 추적용입니다.
 * @param keyboardOptions KeyboardOptions: 키보드 설정입니다.
 * @param keyboardActions KeyboardActions: 키보드 액션 설정입니다.
 * @param background Color: 배경 색상입니다.
 * @param button String?: 하단 버튼 텍스트입니다. 지정하면 trailingContent 대신 버튼이 노출됩니다.
 * @param leadingContent (() -> Unit)?: 하단 영역 왼쪽 슬롯 콘텐츠입니다.
 * @param trailingContent (() -> Unit)?: 하단 영역 오른쪽 슬롯 콘텐츠입니다.
 * @param onClickButton () -> Unit: 버튼 클릭 콜백입니다.
 * @param onValueChange (TextFieldValue) -> Unit: 값 변경 콜백입니다.
 */
@Composable
fun WantedTextArea(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    status: WantedTextAreaDefaults.Status = WantedTextAreaDefaults.Status.Normal,
    enabled: Boolean = true,
    focusRequester: FocusRequester = remember { FocusRequester() },
    size: WantedTextAreaDefaults.Size = WantedTextAreaDefaults.Size.Large,
    resize: WantedTextAreaDefaults.Resize = WantedTextAreaDefaults.Resize.Normal,
    maxLines: Int = DEFAULT_MAX_LINE,
    minLines: Int = DEFAULT_MIN_LINE,
    maxWordCount: Int = 2000,
    enabledOverflowText: Boolean = false,
    isGraphemeClusterCount: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = colorResource(id = R.color.effect_transparent_secondary),
    button: String? = null,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    onClickButton: () -> Unit = {},
    onValueChange: (TextFieldValue) -> Unit
) {
    WantedTextAreaContent(
        value = value,
        modifier = modifier,
        placeholder = placeholder,
        status = status,
        enabled = enabled,
        focusRequester = focusRequester,
        size = size,
        resize = resize,
        maxLines = maxLines,
        minLines = minLines,
        maxWordCount = maxWordCount,
        enabledOverflowText = enabledOverflowText,
        isGraphemeClusterCount = isGraphemeClusterCount,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        background = background,
        button = button,
        leadingContent = leadingContent,
        trailingContent = trailingContent,
        onClickButton = onClickButton,
        onValueChange = onValueChange
    )
}

// WantedTextArea 의 실제 렌더링 본체.
//
// public 오버로드와 @Deprecated 오버로드가 모두 이 함수로 위임한다.
// 동일 이름 오버로드끼리 서로 호출하지 않게 하여, 파라미터 추가 시
// 오버로드 해소(기본값 사용 개수 기준)가 바뀌는 문제를 차단한다.
@Composable
private fun WantedTextAreaContent(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    status: WantedTextAreaDefaults.Status = WantedTextAreaDefaults.Status.Normal,
    enabled: Boolean = true,
    focusRequester: FocusRequester = remember { FocusRequester() },
    size: WantedTextAreaDefaults.Size = WantedTextAreaDefaults.Size.Large,
    resize: WantedTextAreaDefaults.Resize = WantedTextAreaDefaults.Resize.Normal,
    maxLines: Int = DEFAULT_MAX_LINE,
    minLines: Int = DEFAULT_MIN_LINE,
    maxWordCount: Int = 2000,
    enabledOverflowText: Boolean = false,
    isGraphemeClusterCount: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = colorResource(id = R.color.effect_transparent_secondary),
    button: String? = null,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    onClickButton: () -> Unit = {},
    onValueChange: (TextFieldValue) -> Unit
) {
    val negative = status == WantedTextAreaDefaults.Status.Negative
    val cursorBrush: Brush = SolidColor(DesignSystemTheme.colors.foregroundBrandPrimary)
    val focused: State<Boolean> = interactionSource.collectIsFocusedAsState()

    val shape = RoundedCornerShape(size.borderRadius)

    val resolvedMaxLines = when (resize) {
        WantedTextAreaDefaults.Resize.Normal -> Int.MAX_VALUE
        WantedTextAreaDefaults.Resize.Limit -> maxLines
        WantedTextAreaDefaults.Resize.Fixed -> minLines
    }
    val resolvedMinLines = when (resize) {
        WantedTextAreaDefaults.Resize.Fixed -> minLines
        else -> minLines
    }

    val resolvedTrailingContent: (@Composable () -> Unit)? = if (button != null) {
        {
            WantedButton(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = button,
                type = ButtonType.ASSISTIVE,
                variant = ButtonVariant.OUTLINED,
                size = size.buttonSize,
                enabled = enabled && !negative,
                onClick = onClickButton
            )
        }
    } else {
        trailingContent
    }

    WantedTextAreaLayout(
        modifier = modifier.textAreaContainer(
            shape = shape,
            enabled = enabled,
            negative = negative,
            focused = focused.value,
            background = background
        ),
        leadingContent = leadingContent,
        trailingContent = resolvedTrailingContent,
        textField = {
            BasicTextField(
                value = value,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = size.contentMinHeight)
                    .focusRequester(focusRequester),
                maxLines = resolvedMaxLines,
                minLines = resolvedMinLines,
                enabled = enabled,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                cursorBrush = cursorBrush,
                textStyle = textAreaTextStyle(size = size, enabled = enabled),
                onValueChange = { changed ->
                    onValueChange(
                        resolveValueChange(
                            current = value,
                            changed = changed,
                            maxWordCount = maxWordCount,
                            enabledOverflowText = enabledOverflowText,
                            isGraphemeClusterCount = isGraphemeClusterCount
                        )
                    )
                },
                decorationBox = { innerTextField ->
                    DecorationBox(
                        modifier = Modifier,
                        innerTextField = innerTextField,
                        contentPaddingX = size.contentPaddingX,
                        placeholder = if (value.text.isEmpty() && placeholder.isNotEmpty()) {
                            {
                                TextAreaPlaceholder(
                                    text = placeholder,
                                    size = size,
                                    enabled = enabled
                                )
                            }
                        } else {
                            null
                        },
                    )
                }
            )
        }
    )
}

// TextArea 컨테이너의 포커스 링·테두리·배경을 구성한다.
//
// border 는 WantedTextField 와 동일하게 [상태 색 border] 와 [그 아래 깔리는 underlay] 를 같은 굵기로 겹쳐 그린다.
// Modifier.border 는 content 를 그린 뒤 자기 stroke 를 덧그리므로 체인에서 먼저 선언한 border 가 위에 얹힌다.
// 상태 색 border 를 먼저 선언해야 지정 opacity(43%/52%)가 그대로 보이고, 순서가 뒤집히면 underlay 43% 가
// 상태 색을 57% 로 깎는다. 포커스 시 바깥 4dp 링은 [focusRing] 이 컴포넌트 경계 바깥에만 그린다.
@Composable
private fun Modifier.textAreaContainer(
    shape: RoundedCornerShape,
    enabled: Boolean,
    negative: Boolean,
    focused: Boolean,
    background: Color
): Modifier = this
    // Focus Ring 은 focus 상태에서만 그린다. Negative 는 red(foregroundNegativePrimary 12%) ring.
    .focusRing(
        visible = enabled && focused,
        shape = shape,
        color = if (negative) {
            DesignSystemTheme.colors.foregroundNegativePrimary.copy(alpha = OPACITY_12)
        } else {
            DesignSystemTheme.colors.lineBrandFocus
        }
    )
    // 배경은 border 보다 먼저 그린다. 나중에 그리면 반투명 배경(white 28%)이 border 위에 덮여
    // border 색의 opacity 가 스펙대로 보이지 않는다.
    .background(
        color = if (enabled) background else DesignSystemTheme.colors.surfaceNeutralTertiary,
        shape = shape
    )
    .border(
        shape = shape,
        color = containerBorderColor(enabled = enabled, negative = negative, focused = focused),
        width = 1.dp
    )
    .border(
        shape = shape,
        color = containerUnderlayBorderColor(enabled = enabled, negative = negative, focused = focused),
        width = 1.dp
    )
    .clip(shape)

/** 상태 색 border 아래에 깔리는 underlay border 색상입니다. 상태 색 위를 덮지 않습니다. */
@Composable
private fun containerUnderlayBorderColor(
    enabled: Boolean,
    negative: Boolean,
    focused: Boolean
): Color = when {
    !enabled -> DesignSystemTheme.colors.transparent
    negative || focused -> DesignSystemTheme.colors.backgroundNeutralPrimary.copy(alpha = OPACITY_43)
    else -> DesignSystemTheme.colors.transparent
}

/** Container border 색상입니다. Negative + Focused 는 opacity 를 52% 로 올려 강조합니다. */
@Composable
private fun containerBorderColor(
    enabled: Boolean,
    negative: Boolean,
    focused: Boolean
): Color = when {
    !enabled -> DesignSystemTheme.colors.lineNeutralTertiary
    negative && focused -> DesignSystemTheme.colors.foregroundNegativePrimary.copy(OPACITY_52)
    negative -> DesignSystemTheme.colors.foregroundNegativePrimary.copy(OPACITY_43)
    focused -> DesignSystemTheme.colors.surfaceBrandPrimary.copy(OPACITY_43)
    else -> DesignSystemTheme.colors.lineNeutralSecondary
}

@Composable
private fun textAreaTextStyle(
    size: WantedTextAreaDefaults.Size,
    enabled: Boolean
): TextStyle = when (size) {
    WantedTextAreaDefaults.Size.Large -> DesignSystemTheme.typography.body2ReadingRegular
    WantedTextAreaDefaults.Size.Medium -> DesignSystemTheme.typography.label1ReadingRegular
}.copy(
    color = if (enabled) {
        DesignSystemTheme.colors.foregroundNeutralPrimary
    } else {
        DesignSystemTheme.colors.foregroundNeutralTertiary
    }
)

@Composable
private fun TextAreaPlaceholder(
    text: String,
    size: WantedTextAreaDefaults.Size,
    enabled: Boolean
) {
    Text(
        text = text,
        style = when (size) {
            WantedTextAreaDefaults.Size.Large -> DesignSystemTheme.typography.body2ReadingRegular
            WantedTextAreaDefaults.Size.Medium -> DesignSystemTheme.typography.label1ReadingRegular
        },
        color = if (enabled) {
            DesignSystemTheme.colors.foregroundNeutralQuaternary
        } else {
            DesignSystemTheme.colors.foregroundDisablePrimary
        }
    )
}

// 글자 수 제한 정책에 따라 반영할 값을 결정한다.
// 제한을 넘긴 입력은 기존 값(current)을 그대로 유지한다.
private fun resolveValueChange(
    current: TextFieldValue,
    changed: TextFieldValue,
    maxWordCount: Int,
    enabledOverflowText: Boolean,
    isGraphemeClusterCount: Boolean
): TextFieldValue = when {
    isGraphemeClusterCount && graphemeClusterCount(text = changed.text) <= maxWordCount -> changed
    enabledOverflowText -> changed
    changed.text.length <= maxWordCount -> changed
    changed.text.length < current.text.length -> changed
    changed.text == current.text -> changed
    else -> current
}


@Composable
private fun DecorationBox(
    innerTextField: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    contentPaddingX: androidx.compose.ui.unit.Dp = 4.dp,
    placeholder: @Composable (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth(1f)
            .padding(horizontal = contentPaddingX),
    ) {
        placeholder?.let { placeholder() }
        innerTextField()
    }
}

private fun WantedTextAreaDefaults.Status.toFormControlStatus(): WantedFormControlDefaults.Status =
    when (this) {
        WantedTextAreaDefaults.Status.Normal -> WantedFormControlDefaults.Status.Normal
        WantedTextAreaDefaults.Status.Negative -> WantedFormControlDefaults.Status.Negative
    }

private fun WantedTextAreaDefaults.Size.toFormControlSize(): WantedFormControlDefaults.Size =
    when (this) {
        WantedTextAreaDefaults.Size.Large -> WantedFormControlDefaults.Size.Large
        WantedTextAreaDefaults.Size.Medium -> WantedFormControlDefaults.Size.Medium
    }

// 3.0 호환: deprecated 오버로드는 하단 슬롯(leading·trailing)이 모두 비어 있으면 3.0 처럼 왼쪽 아래에 글자 수 카운터를 붙인다.
//
// 4.0 본체 오버로드는 카운터를 그리지 않는다(글자 수는 WantedFormControl 의 accessory 가 맡는다).
// deprecated 오버로드를 삭제할 때 함께 지운다.
private fun legacyLeadingContent(
    leadingContent: (@Composable () -> Unit)?,
    trailingContent: (@Composable () -> Unit)?,
    text: String,
    maxWordCount: Int,
    isGraphemeClusterCount: Boolean,
    enabled: Boolean
): (@Composable () -> Unit)? = when {
    leadingContent != null || trailingContent != null -> leadingContent
    else -> {
        {
            WantedTextAreaCharacterCount(
                current = if (isGraphemeClusterCount) graphemeClusterCount(text) else text.length,
                maxWordCount = maxWordCount,
                error = text.length > maxWordCount,
                enable = enabled
            )
        }
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


// 3.0 호환: deprecated 오버로드의 기본 줄 수와 Resize(Limit)는 3.0 동작(maxLines 가 항상 상한)을 유지한다.
// 4.0 본체 오버로드의 기본값은 DEFAULT_MIN_LINE / DEFAULT_MAX_LINE, Resize.Normal 이다. deprecated 오버로드를 삭제할 때 함께 지운다.
private const val LEGACY_MIN_LINE = 1
private const val LEGACY_MAX_LINE = 3

private const val DEPRECATED_FORM_CONTROL_MESSAGE =
    "WantedFormControl(label/description/requiredBadge)이 내장된 WantedTextArea 는 더 이상 사용하지 않습니다. " +
            "WantedFormControl(input = { WantedTextArea(value = ..., onValueChange = ...) }) 형태로 조합해 사용하세요."


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
                    value = TextFieldValue("Large (default)"),
                    placeholder = "텍스트를 입력해 주세요.",
                    size = WantedTextAreaDefaults.Size.Large,
                    onValueChange = {}
                )

                WantedTextArea(
                    value = TextFieldValue("Medium"),
                    placeholder = "텍스트를 입력해 주세요.",
                    size = WantedTextAreaDefaults.Size.Medium,
                    onValueChange = {}
                )

                WantedTextArea(
                    value = TextFieldValue("Large + button"),
                    placeholder = "텍스트를 입력해 주세요.",
                    size = WantedTextAreaDefaults.Size.Large,
                    button = "확인",
                    onValueChange = {}
                )

                WantedFormControl(
                    label = "주제",
                    required = true,
                    status = WantedFormControlDefaults.Status.Negative,
                    input = {
                        WantedTextArea(
                            value = TextFieldValue("입력한 텍스트."),
                            placeholder = "텍스트를 입력해 주세요.",
                            status = WantedTextAreaDefaults.Status.Negative,
                            button = "텍스트",
                            onValueChange = {}
                        )
                    }
                )

                WantedFormControl(
                    label = "주제",
                    required = true,
                    description = "에러가 나면 이렇게 메시지가 나와요",
                    status = WantedFormControlDefaults.Status.Negative,
                    input = {
                        WantedTextArea(
                            value = TextFieldValue("입력한 텍스트."),
                            placeholder = "텍스트를 입력해 주세요.",
                            status = WantedTextAreaDefaults.Status.Negative,
                            button = "텍스트",
                            onValueChange = {}
                        )
                    }
                )

                WantedTextArea(
                    value = TextFieldValue("Resize.Limit — maxLines=3"),
                    placeholder = "텍스트를 입력해 주세요.",
                    resize = WantedTextAreaDefaults.Resize.Limit,
                    maxLines = 3,
                    onValueChange = {}
                )

                WantedTextArea(
                    value = TextFieldValue("Resize.Fixed"),
                    placeholder = "텍스트를 입력해 주세요.",
                    resize = WantedTextAreaDefaults.Resize.Fixed,
                    minLines = 3,
                    onValueChange = {}
                )
            }
        }
    }
}
