package com.wanted.android.wanted.design.input.textinput.textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.input.textinput.view.ComponentTitle
import com.wanted.android.wanted.design.input.textinput.view.WantedCustomTextField
import com.wanted.android.wanted.design.input.textinput.view.WantedTextInputLayout
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.WantedTextStyle

/**
 * 텍스트 입력을 위한 커스텀 TextField 컴포저블입니다. (String 기반)
 *
 * `TextFieldValue`가 아닌 문자열 기반으로 값을 주고받는 방식입니다.
 * 에러, 완료 상태, 우측 버튼, 아이콘, 설명, 포커스 등을 커스터마이징할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedTextField(
 *     text = "입력값",
 *     placeholder = "텍스트를 입력하세요",
 *     onValueChange = { newValue -> ... }
 * )
 * ```
 *
 * @param text String: 현재 입력된 텍스트입니다.
 * @param modifier Modifier: 레이아웃 및 스타일을 설정합니다.
 * @param placeholder String: 텍스트 필드에 힌트로 표시될 문자열입니다.
 * @param title String: 상단 제목 텍스트입니다.
 * @param description String?: 하단에 표시할 설명 또는 상태 메시지입니다.
 * @param rightButton String?: 우측 버튼에 표시될 텍스트입니다.
 * @param rightButtonVariant RightVariant: 우측 버튼 스타일을 지정합니다.
 * @param status Status: 텍스트 필드의 상태 (Normal, Positive, Negative)입니다.
 * @param enabled Boolean: 입력 가능 여부입니다.
 * @param rightButtonEnabled Boolean: 우측 버튼 활성화 여부입니다.
 * @param maxLines Int: 텍스트 필드의 최대 줄 수입니다.
 * @param minLines Int: 텍스트 필드의 최소 줄 수입니다.
 * @param maxWordCount Int: 허용되는 최대 글자 수입니다.
 * @param enabledOverflowText Boolean: 글자 수 초과 허용 여부입니다.
 * @param requiredBadge Boolean: 제목 옆에 필수 뱃지 표시 여부입니다.
 * @param interactionSource MutableInteractionSource: 포커스 및 인터랙션 상태를 추적합니다.
 * @param focused State<Boolean>: 포커스 여부를 외부에서 제어합니다.
 * @param keyboardOptions KeyboardOptions: 키보드 동작 옵션입니다.
 * @param keyboardActions KeyboardActions: 키보드 액션에 대한 핸들링입니다.
 * @param background Color: 텍스트 필드 배경 색상입니다.
 * @param leadingIcon (() -> Unit)?: 좌측 아이콘 슬롯입니다.
 * @param trailingIcon (() -> Unit)?: 우측 아이콘 슬롯입니다.
 * @param rightContent ((Dp) -> Unit)?: 우측 버튼 외 영역에 들어갈 커스텀 콘텐츠입니다.
 * @param onClickRightButton () -> Unit: 우측 버튼 클릭 시 콜백입니다.
 * @param onValueChange (String) -> Unit: 텍스트 변경 시 콜백입니다.
 */
@Composable
fun WantedTextField(
    text: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    title: String = "",
    description: String? = null,
    rightButton: String? = null,
    rightButtonVariant: WantedTextFieldContract.RightVariant = WantedTextFieldContract.RightVariant.Normal,
    status: WantedTextFieldContract.Status = WantedTextFieldContract.Status.Normal,
    enabled: Boolean = true,
    rightButtonEnabled: Boolean = true,
    maxLines: Int = 1,
    minLines: Int = 1,
    maxWordCount: Int = 2000,
    enabledOverflowText: Boolean = false,
    requiredBadge: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focused: State<Boolean> = interactionSource.collectIsFocusedAsState(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = colorResource(id = R.color.background_normal_normal),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    rightContent: @Composable ((Dp) -> Unit)? = null,
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
            WantedCustomTextField(
                modifier = Modifier,
                value = textFieldValue,
                error = status == WantedTextFieldContract.Status.Negative,
                enabled = enabled,
                rightButtonEnabled = rightButtonEnabled,
                focused = focused.value,
                complete = status == WantedTextFieldContract.Status.Positive,
                maxLines = maxLines,
                minLines = minLines,
                maxWordCount = maxWordCount,
                enabledOverflowText = enabledOverflowText,
                interactionSource = interactionSource,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                background = background,
                rightButton = rightButton,
                rightButtonVariant = rightButtonVariant,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                rightContent = rightContent,
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
        message = if (!description.isNullOrEmpty()) {
            {
                Text(
                    text = description,
                    style = WantedTextStyle(
                        colorRes = when {
                            enabled && status == WantedTextFieldContract.Status.Negative -> R.color.status_negative
                            else -> R.color.label_alternative
                        },
                        style = DesignSystemTheme.typography.caption1Regular
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        } else null
    )
}

/**
 * 텍스트 입력을 위한 커스텀 TextField 컴포저블입니다. (TextFieldValue 기반)
 *
 * 커서 위치 및 선택 영역 처리를 위해 `TextFieldValue` 객체를 사용합니다.
 *
 * 사용 예시:
 * ```kotlin
 * val state = remember { mutableStateOf(TextFieldValue("")) }
 * WantedTextField(value = state.value, onValueChange = { state.value = it })
 * ```
 *
 * @param value TextFieldValue: 입력된 텍스트 및 커서 상태를 포함한 값입니다.
 * @param onValueChange (TextFieldValue) -> Unit: 값 변경 시 호출되는 콜백입니다.
 * @param modifier Modifier: 외형 및 레이아웃 설정입니다.
 * @param enabled Boolean: 입력 가능 여부입니다.
 * @param title String: 상단 제목 텍스트입니다.
 * @param requiredBadge Boolean: 필수 뱃지 표시 여부입니다.
 * @param placeholder String: 플레이스홀더 문자열입니다.
 * @param description String?: 하단 상태 또는 설명 메시지입니다.
 * @param leadingIcon (() -> Unit)?: 좌측 아이콘 슬롯입니다.
 * @param trailingIcon (() -> Unit)?: 우측 아이콘 슬롯입니다.
 * @param rightContent ((Dp) -> Unit)?: 우측 영역 콘텐츠 슬롯입니다.
 * @param rightButton String?: 우측 버튼 텍스트입니다.
 * @param onClickRightButton () -> Unit: 우측 버튼 클릭 콜백입니다.
 * @param rightButtonEnabled Boolean: 우측 버튼 활성화 여부입니다.
 * @param rightButtonVariant RightVariant: 우측 버튼 스타일입니다.
 * @param status Status: 입력 상태입니다.
 * @param maxWordCount Int: 최대 글자 수입니다.
 * @param enabledOverflowText Boolean: 글자 수 초과 허용 여부입니다.
 * @param minLines Int: 최소 줄 수입니다.
 * @param maxLines Int: 최대 줄 수입니다.
 * @param interactionSource MutableInteractionSource: 포커스 추적용입니다.
 * @param focused State<Boolean>: 포커스 상태 제어용입니다.
 * @param keyboardOptions KeyboardOptions: 키보드 동작 설정입니다.
 * @param keyboardActions KeyboardActions: 키보드 액션 처리입니다.
 * @param background Color: 배경 색상입니다.
 */
@Composable
fun WantedTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit = {},
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    title: String = "",
    requiredBadge: Boolean = false,
    placeholder: String = "",
    description: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    rightContent: @Composable ((Dp) -> Unit)? = null,
    rightButton: String? = null,
    onClickRightButton: () -> Unit = {},
    rightButtonEnabled: Boolean = true,
    rightButtonVariant: WantedTextFieldContract.RightVariant = WantedTextFieldContract.RightVariant.Normal,
    status: WantedTextFieldContract.Status = WantedTextFieldContract.Status.Normal,
    maxWordCount: Int = 2000,
    enabledOverflowText: Boolean = false,
    minLines: Int = 1,
    maxLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focused: State<Boolean> = interactionSource.collectIsFocusedAsState(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = colorResource(id = R.color.background_normal_normal)
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
            WantedCustomTextField(
                modifier = Modifier,
                value = value,
                error = status == WantedTextFieldContract.Status.Negative,
                enabled = enabled,
                rightButtonEnabled = rightButtonEnabled,
                focused = focused.value,
                complete = status == WantedTextFieldContract.Status.Positive,
                maxLines = maxLines,
                minLines = minLines,
                maxWordCount = maxWordCount,
                enabledOverflowText = enabledOverflowText,
                interactionSource = interactionSource,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                background = background,
                rightButton = rightButton,
                rightButtonVariant = rightButtonVariant,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                rightContent = rightContent,
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
                            enabled && status == WantedTextFieldContract.Status.Negative -> R.color.status_negative
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


@DevicePreviews
@Composable
private fun WantedTextFieldPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    focused = remember { mutableStateOf(true) }
                )

                WantedTextField(
                    title = "주제",
                    requiredBadge = true,
                    text = "입력한 텍스트.",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트"
                )

                WantedTextField(
                    title = "",
                    text = "",
                    placeholder = "텍스트를 입력해 주세요.",
                    enabled = false,
                    status = WantedTextFieldContract.Status.Negative
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    enabled = false,
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    status = WantedTextFieldContract.Status.Negative
                )

                WantedTextField(
                    requiredBadge = true,
                    text = "입력한 텍스트.",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    enabled = false
                )

                WantedTextField(
                    text = "텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요.",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    rightButtonVariant = WantedTextFieldContract.RightVariant.Assistive
                )

                WantedTextField(
                    text = "텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요.",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    rightButtonVariant = WantedTextFieldContract.RightVariant.Assistive,
                    rightButtonEnabled = false
                )

                WantedTextField(
                    text = "텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요.",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    rightButtonEnabled = false
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    status = WantedTextFieldContract.Status.Positive,
                    focused = remember { mutableStateOf(false) }
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    status = WantedTextFieldContract.Status.Positive,
                    focused = remember { mutableStateOf(true) }
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    status = WantedTextFieldContract.Status.Positive,
                    focused = remember { mutableStateOf(false) }
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    status = WantedTextFieldContract.Status.Positive,
                    focused = remember { mutableStateOf(true) }
                )
            }

        }
    }
}


@DevicePreviews
@Composable
private fun WantedTextInputPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedTextField(
                    text = "텍스트를 입력해 주세요.",
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
                    text = "텍스트를 입력해 주세요.",
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


                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    description = "메시지에 마침표를 찍어요.",
                    focused = remember { mutableStateOf(true) }
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    status = WantedTextFieldContract.Status.Negative,
                    description = "메시지에 마침표를 찍어요.",
                    focused = remember { mutableStateOf(true) }
                )


                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    enabled = false,
                    description = "메시지에 마침표를 찍어요.",
                    focused = remember { mutableStateOf(true) }
                )

                WantedTextField(
                    text = "",
                    placeholder = "텍스트를 입력해 주세요.",
                    enabled = false,
                    description = "메시지에 마침표를 찍어요.",
                    focused = remember { mutableStateOf(true) }
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    enabled = false,
                    description = "메시지에 마침표를 찍어요.",
                    focused = remember { mutableStateOf(true) }
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    enabled = false,
                    status = WantedTextFieldContract.Status.Positive,
                    description = "메시지에 마침표를 찍어요.",
                    focused = remember { mutableStateOf(false) }
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    enabled = false,
                    status = WantedTextFieldContract.Status.Negative,
                    description = "메시지에 마침표를 찍어요.",
                    focused = remember { mutableStateOf(true) }
                )
            }
        }
    }
}

