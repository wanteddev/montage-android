package com.wanted.android.wanted.design.input.textinput.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.BasicText
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.framedstyle.focusRing
import com.wanted.android.wanted.design.input.textinput.textfield.WantedTextFieldDefaults.Size
import com.wanted.android.wanted.design.input.textinput.view.ComponentTitle
import com.wanted.android.wanted.design.input.textinput.view.WantedTextInputLayout
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_12
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.OPACITY_52
import com.wanted.android.wanted.design.util.clickOnce


/**
 * WantedTextField
 *
 * 입력 필드 본체만 렌더링하는 Text field 컴포넌트입니다.
 *
 * 라벨·필수 표시(*)·하단 메시지는 포함하지 않습니다. 해당 요소가 필요하면
 * WantedFormControl 의 input 슬롯에 조합해서 사용합니다.
 * 커서 위치와 선택 영역을 다루기 위해 TextFieldValue 로 값을 주고받습니다.
 *
 * 사용 예시:
 * ```kotlin
 * val state = remember { mutableStateOf(TextFieldValue("")) }
 *
 * WantedFormControl(
 *     label = "이메일",
 *     required = true,
 *     description = "올바른 이메일 형식을 입력해 주세요."
 * ) {
 *     WantedTextField(
 *         value = state.value,
 *         placeholder = "이메일을 입력해 주세요.",
 *         error = false,
 *         enabled = true,
 *         trailingButtonEnabled = true,
 *         complete = false,
 *         maxLines = 1,
 *         minLines = 1,
 *         maxWordCount = 2000,
 *         enabledOverflowText = false,
 *         interactionSource = remember { MutableInteractionSource() },
 *         keyboardOptions = KeyboardOptions.Default,
 *         keyboardActions = KeyboardActions.Default,
 *         onValueChange = { state.value = it }
 *     )
 * }
 * ```
 *
 * @param value TextFieldValue: 입력된 텍스트와 커서·선택 영역 상태를 포함한 값입니다. single line 이고 포커스가 없으면 넘치는 값은 말줄임(…) 처리됩니다.
 * @param placeholder String: 값이 비어 있을 때 힌트로 표시될 문자열입니다.
 * @param error Boolean: 에러(Negative) 상태 여부입니다. true 면 red ring 과 border 를 표시합니다.
 * @param enabled Boolean: 입력 가능 여부입니다.
 * @param trailingButtonEnabled Boolean: 우측 버튼 활성화 여부입니다.
 * @param complete Boolean: 완료(Positive) 상태 여부입니다. 포커스가 없을 때 우측에 체크 아이콘을 표시합니다.
 * @param maxLines Int: 텍스트 필드의 최대 줄 수입니다. minLines 와 함께 1 이면 single line 으로 동작합니다. (1 이상, minLines 이상)
 * @param minLines Int: 텍스트 필드의 최소 줄 수입니다. (1 이상, maxLines 이하)
 * @param maxWordCount Int: 허용되는 최대 글자 수입니다.
 * @param enabledOverflowText Boolean: 최대 글자 수 초과 입력 허용 여부입니다.
 * @param interactionSource MutableInteractionSource: 포커스 및 인터랙션 상태를 추적합니다.
 * @param keyboardOptions KeyboardOptions: 키보드 동작 옵션입니다.
 * @param keyboardActions KeyboardActions: 키보드 액션에 대한 핸들링입니다.
 * @param modifier Modifier: 레이아웃 및 스타일을 설정합니다.
 * @param size Size: 텍스트 필드의 크기입니다. (Large, Medium)
 * @param focused State<Boolean>: 포커스 상태입니다. 기본값은 interactionSource 에서 수집합니다.
 * @param cursorBrush Brush: 커서를 그릴 Brush 입니다.
 * @param background Color: 텍스트 필드 배경 색상입니다.
 * @param visualTransformation VisualTransformation: 텍스트 표시 방식을 변환합니다 (예: 비밀번호 마스킹).
 * @param trailingButton String?: 우측 버튼에 표시될 텍스트입니다. null 또는 빈 문자열이면 버튼을 표시하지 않습니다.
 * @param trailingContent (@Composable () -> Unit)?: 우측 아이콘과 버튼 사이에 들어갈 커스텀 콘텐츠 슬롯입니다.
 * @param leadingIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다.
 * @param trailingIcon (@Composable () -> Unit)?: 우측 아이콘 슬롯입니다. 지정하지 않으면 포커스 상태에서 전체 삭제 아이콘이 표시됩니다.
 * @param onClickTrailingButton () -> Unit: 우측 버튼 클릭 시 콜백입니다.
 * @param onValueChange (TextFieldValue) -> Unit: 값 변경 시 콜백입니다.
 * @param focusRequester FocusRequester: 포커스 요청에 사용하는 FocusRequester 입니다.
 */
@Composable
fun WantedTextField(
    value: TextFieldValue,
    placeholder: String,
    error: Boolean,
    enabled: Boolean,
    trailingButtonEnabled: Boolean,
    complete: Boolean,
    maxLines: Int,
    minLines: Int,
    maxWordCount: Int,
    enabledOverflowText: Boolean,
    interactionSource: MutableInteractionSource,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier,
    size: Size = Size.Large,
    focused: State<Boolean> = interactionSource.collectIsFocusedAsState(),
    cursorBrush: Brush = SolidColor(DesignSystemTheme.colors.foregroundBrandPrimary),
    background: Color = DesignSystemTheme.colors.backgroundNeutralPrimary,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingButton: String? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onClickTrailingButton: () -> Unit = {},
    onValueChange: (TextFieldValue) -> Unit = {},
    focusRequester: FocusRequester = remember { FocusRequester() }
) {
    val shape = RoundedCornerShape(size.borderRadius)
    val inputTextStyle = when (size) {
        Size.Large -> DesignSystemTheme.typography.body2Regular
        Size.Medium -> DesignSystemTheme.typography.label1Regular
    }

    // inner border 는 포커스와 무관하게 1dp 로 고정한다. 포커스 강조는 바깥 4dp ring 이 담당한다.
    val borderWidth = 1.dp
    val singleLine = maxLines == 1 && minLines == 1

    Box(
        modifier = modifier
            // 외부 4px Ring 은 focus 상태에서만 그린다. Negative 는 red(foregroundNegativePrimary 12%) ring,
            // 그 외에는 primary(lineBrandFocus) ring.
            .focusRing(
                visible = enabled && focused.value,
                shape = shape,
                color = if (error) {
                    DesignSystemTheme.colors.foregroundNegativePrimary.copy(alpha = OPACITY_12)
                } else {
                    DesignSystemTheme.colors.lineBrandFocus
                }
            )
            .defaultMinSize(minHeight = size.minHeight)
            .background(
                color = if (enabled) background else DesignSystemTheme.colors.surfaceNeutralTertiary,
                shape = shape
            )
            // Modifier.border 는 content 를 그린 뒤 자기 stroke 를 덧그리므로 체인에서 먼저 선언한 border 가
            // 위에 얹힌다. 지정 투명도(43%/52%)를 그대로 보이려면 line border 가 맨 위(= 먼저 선언)여야 하고,
            // underlay(outer)는 그 아래에 둔다. 순서가 뒤집히면 outer 43% 가 line 색을 57% 로 깎는다.
            .border(
                shape = shape,
                color = containerBorderColor(
                    enabled = enabled,
                    error = error,
                    focused = focused.value
                ),
                width = borderWidth
            )
            .border(
                shape = shape,
                color = containerOuterBorderColor(
                    enabled = enabled,
                    error = error,
                    focused = focused.value
                ),
                width = borderWidth
            )
            .clip(shape),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth()
                .padding(size.containerPadding),
            value = value,
            maxLines = maxLines,
            minLines = minLines,
            enabled = enabled,
            // BasicTextField 는 singleLine = true 일 때 maxLines·minLines 를 무시하므로
            // 두 값이 모두 1 인 경우에만 single line 으로 처리한다.
            singleLine = singleLine,
            cursorBrush = cursorBrush,
            interactionSource = interactionSource,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            textStyle = inputTextStyle.copy(
                color = if (enabled) {
                    DesignSystemTheme.colors.foregroundNeutralPrimary
                } else {
                    DesignSystemTheme.colors.foregroundNeutralTertiary
                }
            ),
            onValueChange = { newValue ->
                onValueChange(
                    coerceWordCount(
                        newValue = newValue,
                        currentValue = value,
                        maxWordCount = maxWordCount,
                        enabledOverflowText = enabledOverflowText
                    )
                )
            },
            decorationBox = { innerTextField ->
                DecorationBox(
                    modifier = Modifier,
                    iconBoxSize = size.iconBoxSize,
                    innerTextField = innerTextField,
                    valueOverlay = ellipsizedValueSlot(
                        visible = singleLine && !focused.value && value.text.isNotEmpty(),
                        value = value,
                        visualTransformation = visualTransformation,
                        textStyle = inputTextStyle.copy(
                            color = if (enabled) {
                                DesignSystemTheme.colors.foregroundNeutralPrimary
                            } else {
                                DesignSystemTheme.colors.foregroundNeutralTertiary
                            }
                        )
                    ),
                    placeholder = placeholderSlot(
                        value = value,
                        placeholder = placeholder,
                        enabled = enabled,
                        textStyle = inputTextStyle
                    ),
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIconSlot(
                        trailingIcon = trailingIcon,
                        value = value,
                        iconSize = size.iconSize,
                        enabled = enabled,
                        complete = complete,
                        focused = focused.value,
                        onValueChange = onValueChange
                    ),
                    trailingContent = trailingContent,
                    trailingButton = trailingButtonSlot(
                        trailingButton = trailingButton,
                        size = size,
                        enabled = isEnableTrailingButton(trailingButtonEnabled, enabled),
                        onClick = onClickTrailingButton
                    )
                )
            }
        )
    }
}

private fun isEnableTrailingButton(
    trailingButtonEnabled: Boolean,
    enabled: Boolean
) = trailingButtonEnabled && enabled

// 최대 글자 수 정책을 적용한 값을 반환합니다.
//
// 초과 입력이 허용되지 않아도 커서·선택 영역만 바뀌는 변경과
// 글자 수를 줄이는 변경은 항상 허용합니다.
private fun coerceWordCount(
    newValue: TextFieldValue,
    currentValue: TextFieldValue,
    maxWordCount: Int,
    enabledOverflowText: Boolean
): TextFieldValue = when {
    enabledOverflowText -> newValue
    // 이미 maxWordCount 를 초과한 값이 외부에서 주입되면 커서 이동까지 막히는 것을 방지한다.
    newValue.text == currentValue.text -> newValue
    newValue.text.length <= maxWordCount -> newValue
    newValue.text.length < currentValue.text.length -> newValue
    else -> currentValue
}

/** #4 Container 바깥쪽 1px border(underlay) 색상입니다. line border 아래에 깔려 색을 깎지 않습니다. */
@Composable
private fun containerOuterBorderColor(
    enabled: Boolean,
    error: Boolean,
    focused: Boolean
): Color = when {
    !enabled -> DesignSystemTheme.colors.transparent
    error || focused -> DesignSystemTheme.colors.backgroundNeutralPrimary.copy(alpha = OPACITY_43)
    else -> DesignSystemTheme.colors.transparent
}

/** #4 Container border 색상입니다. Negative + Focused 는 opacity 를 52% 로 올려 강조합니다. */
@Composable
private fun containerBorderColor(
    enabled: Boolean,
    error: Boolean,
    focused: Boolean
): Color = when {
    !enabled -> DesignSystemTheme.colors.lineNeutralTertiary
    error && focused -> DesignSystemTheme.colors.foregroundNegativePrimary.copy(OPACITY_52)
    error -> DesignSystemTheme.colors.foregroundNegativePrimary.copy(OPACITY_43)
    focused -> DesignSystemTheme.colors.surfaceBrandPrimary.copy(OPACITY_43)
    else -> DesignSystemTheme.colors.lineNeutralSecondary
}

/** 값이 비어 있을 때만 placeholder 슬롯을 반환합니다. */
private fun placeholderSlot(
    value: TextFieldValue,
    placeholder: String,
    enabled: Boolean,
    textStyle: TextStyle
): (@Composable () -> Unit)? {
    if (value.text.isNotEmpty() || placeholder.isEmpty()) return null

    return {
        Text(
            text = placeholder,
            style = textStyle,
            // Figma: placeholder 는 한 줄만 보이고 넘치면 말줄임 처리한다.
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = if (enabled) {
                DesignSystemTheme.colors.foregroundNeutralTertiary
            } else {
                DesignSystemTheme.colors.foregroundDisablePrimary
            },
        )
    }
}

// 비포커스 single line 입력 값을 말줄임(…)으로 보여주는 오버레이 슬롯을 반환합니다.
//
// BasicTextField 는 overflow 옵션이 없어 넘치는 값이 잘리기만 하므로, 포커스가 없을 때만 같은 스타일의
// Text 를 위에 덮는다. 실제 입력 필드는 그리기만 생략해 레이아웃·포커스·접근성 노드를 유지하며,
// 오버레이는 시맨틱을 비워 TalkBack 이 값을 두 번 읽지 않게 한다.
private fun ellipsizedValueSlot(
    visible: Boolean,
    value: TextFieldValue,
    visualTransformation: VisualTransformation,
    textStyle: TextStyle
): (@Composable () -> Unit)? {
    if (!visible) return null

    return {
        BasicText(
            modifier = Modifier
                .fillMaxWidth()
                .clearAndSetSemantics { },
            text = visualTransformation.filter(value.annotatedString).text,
            style = textStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// 우측 아이콘 슬롯을 반환합니다.
//
// #9 Negative 상태의 우측 circle exclamation 아이콘은 제거한다.
private fun trailingIconSlot(
    trailingIcon: (@Composable () -> Unit)?,
    value: TextFieldValue,
    iconSize: Dp,
    enabled: Boolean,
    complete: Boolean,
    focused: Boolean,
    onValueChange: (TextFieldValue) -> Unit
): (@Composable () -> Unit)? = when {
    !focused && complete -> {
        {
            Icon(
                modifier = Modifier.size(iconSize),
                painter = painterResource(R.drawable.icon_normal_circle_check_fill),
                tint = DesignSystemTheme.colors.foregroundPositivePrimary,
                contentDescription = ""
            )
        }
    }

    trailingIcon == null && value.text.isNotEmpty() && enabled && focused -> {
        {
            Icon(
                modifier = Modifier
                    .size(iconSize)
                    .clip(CircleShape)
                    .clickOnce {
                        // copy("") 는 composition 이 TextRange.Zero 로 남아 IME 조합 상태가 유지된다.
                        onValueChange(TextFieldValue(""))
                    },
                painter = painterResource(R.drawable.icon_normal_circle_close_fill),
                tint = DesignSystemTheme.colors.foregroundNeutralQuaternary,
                contentDescription = ""
            )
        }
    }

    else -> trailingIcon
}

// 우측 버튼 슬롯을 반환합니다.
//
// #8 Field 내부에 위치하는 Outlined + Assistive 버튼 한 가지 형태만 사용한다.
private fun trailingButtonSlot(
    trailingButton: String?,
    size: Size,
    enabled: Boolean,
    onClick: () -> Unit
): (@Composable () -> Unit)? {
    if (trailingButton.isNullOrEmpty()) return null

    return {
        WantedButton(
            text = trailingButton,
            variant = ButtonVariant.OUTLINED,
            type = ButtonType.ASSISTIVE,
            size = when (size) {
                Size.Large -> ButtonSize.SMALL
                Size.Medium -> ButtonSize.XSMALL
            },
            enabled = enabled,
            onClick = onClick
        )
    }
}

// Figma 기준 Container 구조를 그대로 옮긴 decoration box 입니다.
//
// Container(padding) > Content(horizontal 4dp) > [leadingIcon, Text, Trailing 묶음] + trailingButton.
// Trailing 아이콘·콘텐츠는 Content 안쪽에 위치하므로 우측 외곽선과의 간격이
// Container padding + Content padding 으로 계산된다. Button 만 Content 바깥(Container 직계)에 둔다.
@Composable
private fun DecorationBox(
    innerTextField: @Composable () -> Unit,
    iconBoxSize: Dp,
    modifier: Modifier = Modifier,
    valueOverlay: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    trailingButton: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier,
        // Container gap: Content↔TrailingButton 간 gap 4px (Figma 기준)
        horizontalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp),
            // Content 내부(icon ↔ text ↔ trailing) gap 2px
            horizontalArrangement = Arrangement.spacedBy(2.dp, alignment = Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let {
                Box(
                    modifier = Modifier.size(iconBoxSize),
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

                if (valueOverlay != null) {
                    // 입력 필드는 유지한 채 그리기만 생략하고, 같은 자리에 말줄임 Text 를 그린다.
                    // alpha(0f) 는 투명 노드로 판정돼 TalkBack 이 입력 필드를 건너뛰므로 쓰지 않는다.
                    Box(modifier = Modifier.drawWithContent { }) { innerTextField() }
                    valueOverlay()
                } else {
                    innerTextField()
                }
            }

            if (trailingIcon != null || trailingContent != null) {
                Row(
                    // Trailing 묶음 내부 gap 8px
                    horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    trailingIcon?.let {
                        Box(
                            modifier = Modifier.size(iconBoxSize),
                            contentAlignment = Alignment.Center,
                        ) {
                            trailingIcon()
                        }
                    }

                    trailingContent?.let {
                        Box(
                            modifier = Modifier.defaultMinSize(
                                minWidth = iconBoxSize,
                                minHeight = iconBoxSize
                            ),
                            contentAlignment = Alignment.Center,
                        ) {
                            trailingContent()
                        }
                    }
                }
            }
        }

        trailingButton?.let {
            trailingButton()
        }
    }
}


/**
 * WantedTextField
 *
 * (Deprecated) 라벨·메시지 레이아웃을 함께 렌더링하는 문자열 기반 Text field 컴포넌트입니다.
 *
 * title·requiredBadge·description 은 WantedFormControl 의 label·required·description 으로
 * 대체되었습니다. 입력 필드 본체만 담당하는 WantedTextField(value = ...) 를
 * WantedFormControl 의 input 슬롯에 조합해서 사용합니다.
 *
 * 사용 예시:
 * ```kotlin
 * // 대체 방식
 * WantedFormControl(
 *     label = "주제",
 *     required = true,
 *     description = "메시지에 마침표를 찍어요.",
 *     status = WantedFormControlDefaults.Status.Negative
 * ) {
 *     WantedTextField(value = state.value, ..., onValueChange = { state.value = it })
 * }
 * ```
 *
 * @param text String: 현재 입력된 텍스트입니다.
 * @param modifier Modifier: 레이아웃 및 스타일을 설정합니다.
 * @param placeholder String: 텍스트 필드에 힌트로 표시될 문자열입니다.
 * @param title String: 상단 제목 텍스트입니다. 빈 문자열이면 제목을 렌더링하지 않습니다.
 * @param description String?: 하단에 표시할 설명 또는 상태 메시지입니다.
 * @param trailingButton String?: 우측 버튼에 표시될 텍스트입니다.
 * @param size WantedTextFieldDefaults.Size: 텍스트 필드의 크기입니다. (Large, Medium)
 * @param status WantedTextFieldDefaults.Status: 텍스트 필드의 상태입니다. (Normal, Positive, Negative)
 * @param enabled Boolean: 입력 가능 여부입니다.
 * @param trailingButtonEnabled Boolean: 우측 버튼 활성화 여부입니다.
 * @param maxLines Int: 텍스트 필드의 최대 줄 수입니다.
 * @param minLines Int: 텍스트 필드의 최소 줄 수입니다.
 * @param maxWordCount Int: 허용되는 최대 글자 수입니다.
 * @param enabledOverflowText Boolean: 최대 글자 수 초과 입력 허용 여부입니다.
 * @param requiredBadge Boolean: 제목 옆에 필수 뱃지 표시 여부입니다.
 * @param interactionSource MutableInteractionSource: 포커스 및 인터랙션 상태를 추적합니다.
 * @param focusRequester FocusRequester: 포커스 요청에 사용하는 FocusRequester 입니다.
 * @param keyboardOptions KeyboardOptions: 키보드 동작 옵션입니다.
 * @param keyboardActions KeyboardActions: 키보드 액션에 대한 핸들링입니다.
 * @param background Color: 텍스트 필드 배경 색상입니다.
 * @param visualTransformation VisualTransformation: 텍스트 표시 방식을 변환합니다 (예: 비밀번호 마스킹).
 * @param leadingIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다.
 * @param trailingIcon (@Composable () -> Unit)?: 우측 아이콘 슬롯입니다.
 * @param trailingContent (@Composable () -> Unit)?: 우측 아이콘과 버튼 사이에 들어갈 커스텀 콘텐츠 슬롯입니다.
 * @param onClickTrailingButton () -> Unit: 우측 버튼 클릭 시 콜백입니다.
 * @param onValueChange (String) -> Unit: 텍스트 변경 시 콜백입니다.
 */
@Deprecated(
    message = "title·requiredBadge·description 을 포함한 오버로드는 WantedFormControl 로 분리되었습니다. " +
        "WantedFormControl(label, required, description) 의 input 슬롯에 " +
        "WantedTextField(value = ...) 를 조합해서 사용하세요.",
    level = DeprecationLevel.WARNING
)
@Composable
fun WantedTextField(
    text: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    title: String = "",
    description: String? = null,
    trailingButton: String? = null,
    size: WantedTextFieldDefaults.Size = WantedTextFieldDefaults.Size.Large,
    status: WantedTextFieldDefaults.Status = WantedTextFieldDefaults.Status.Normal,
    enabled: Boolean = true,
    trailingButtonEnabled: Boolean = true,
    maxLines: Int = 1,
    minLines: Int = 1,
    maxWordCount: Int = 2000,
    enabledOverflowText: Boolean = false,
    requiredBadge: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focusRequester: FocusRequester = remember { FocusRequester() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = colorResource(id = R.color.effect_transparent_secondary),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    onClickTrailingButton: () -> Unit = {},
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
            WantedTextField(
                modifier = Modifier,
                value = textFieldValue,
                error = status == WantedTextFieldDefaults.Status.Negative,
                enabled = enabled,
                size = size,
                trailingButtonEnabled = trailingButtonEnabled,
                complete = status == WantedTextFieldDefaults.Status.Positive,
                maxLines = maxLines,
                minLines = minLines,
                maxWordCount = maxWordCount,
                enabledOverflowText = enabledOverflowText,
                interactionSource = interactionSource,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                background = background,
                visualTransformation = visualTransformation,
                trailingButton = trailingButton,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                trailingContent = trailingContent,
                onClickTrailingButton = onClickTrailingButton,
                onValueChange = { newTextFieldValueState ->
                    textFieldValueState = newTextFieldValueState

                    val stringChangedSinceLastInvocation =
                        lastTextValue != newTextFieldValueState.text
                    lastTextValue = newTextFieldValueState.text

                    if (stringChangedSinceLastInvocation) {
                        onValueChange(newTextFieldValueState.text)
                    }
                },
                focusRequester = focusRequester
            )
        },
        message = if (!description.isNullOrEmpty()) {
            {
                Text(
                    text = description,
                    style = DesignSystemTheme.typography.caption1Regular,
                    color = when {
                        enabled && status == WantedTextFieldDefaults.Status.Negative -> {
                            DesignSystemTheme.colors.foregroundNegativePrimary
                        }

                        else -> DesignSystemTheme.colors.foregroundNeutralTertiary
                    },
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        } else null
    )
}

/**
 * WantedTextField
 *
 * (Deprecated) 라벨·메시지 레이아웃을 함께 렌더링하는 TextFieldValue 기반 Text field 컴포넌트입니다.
 *
 * 커서 위치 및 선택 영역 처리를 위해 TextFieldValue 를 사용합니다.
 * title·requiredBadge·description 은 WantedFormControl 의 label·required·description 으로
 * 대체되었습니다. 입력 필드 본체만 담당하는 WantedTextField(value = ...) 를
 * WantedFormControl 의 input 슬롯에 조합해서 사용합니다.
 *
 * 사용 예시:
 * ```kotlin
 * // 대체 방식
 * val state = remember { mutableStateOf(TextFieldValue("")) }
 *
 * WantedFormControl(label = "주제", required = true) {
 *     WantedTextField(value = state.value, ..., onValueChange = { state.value = it })
 * }
 * ```
 *
 * @param value TextFieldValue: 입력된 텍스트 및 커서·선택 영역 상태를 포함한 값입니다.
 * @param onValueChange (TextFieldValue) -> Unit: 값 변경 시 호출되는 콜백입니다.
 * @param modifier Modifier: 외형 및 레이아웃 설정입니다.
 * @param enabled Boolean: 입력 가능 여부입니다.
 * @param title String: 상단 제목 텍스트입니다. 빈 문자열이면 제목을 렌더링하지 않습니다.
 * @param requiredBadge Boolean: 제목 옆에 필수 뱃지 표시 여부입니다.
 * @param placeholder String: 값이 비어 있을 때 힌트로 표시될 문자열입니다.
 * @param description String?: 하단에 표시할 설명 또는 상태 메시지입니다.
 * @param leadingIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다.
 * @param trailingIcon (@Composable () -> Unit)?: 우측 아이콘 슬롯입니다.
 * @param trailingContent (@Composable () -> Unit)?: 우측 아이콘과 버튼 사이에 들어갈 커스텀 콘텐츠 슬롯입니다.
 * @param trailingButton String?: 우측 버튼에 표시될 텍스트입니다.
 * @param onClickTrailingButton () -> Unit: 우측 버튼 클릭 시 콜백입니다.
 * @param trailingButtonEnabled Boolean: 우측 버튼 활성화 여부입니다.
 * @param size WantedTextFieldDefaults.Size: 텍스트 필드의 크기입니다. (Large, Medium)
 * @param status WantedTextFieldDefaults.Status: 텍스트 필드의 상태입니다. (Normal, Positive, Negative)
 * @param maxWordCount Int: 허용되는 최대 글자 수입니다.
 * @param enabledOverflowText Boolean: 최대 글자 수 초과 입력 허용 여부입니다.
 * @param minLines Int: 텍스트 필드의 최소 줄 수입니다.
 * @param maxLines Int: 텍스트 필드의 최대 줄 수입니다.
 * @param interactionSource MutableInteractionSource: 포커스 및 인터랙션 상태를 추적합니다.
 * @param focusRequester FocusRequester: 포커스 요청에 사용하는 FocusRequester 입니다.
 * @param keyboardOptions KeyboardOptions: 키보드 동작 옵션입니다.
 * @param keyboardActions KeyboardActions: 키보드 액션에 대한 핸들링입니다.
 * @param background Color: 텍스트 필드 배경 색상입니다.
 * @param visualTransformation VisualTransformation: 텍스트 표시 방식을 변환합니다 (예: 비밀번호 마스킹).
 */
@Deprecated(
    message = "title·requiredBadge·description 을 포함한 오버로드는 WantedFormControl 로 분리되었습니다. " +
        "WantedFormControl(label, required, description) 의 input 슬롯에 " +
        "WantedTextField(value = ...) 를 조합해서 사용하세요.",
    level = DeprecationLevel.WARNING
)
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
    trailingContent: @Composable (() -> Unit)? = null,
    trailingButton: String? = null,
    onClickTrailingButton: () -> Unit = {},
    trailingButtonEnabled: Boolean = true,
    size: WantedTextFieldDefaults.Size = WantedTextFieldDefaults.Size.Large,
    status: WantedTextFieldDefaults.Status = WantedTextFieldDefaults.Status.Normal,
    maxWordCount: Int = 2000,
    enabledOverflowText: Boolean = false,
    minLines: Int = 1,
    maxLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focusRequester: FocusRequester = remember { FocusRequester() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = colorResource(id = R.color.effect_transparent_secondary),
    visualTransformation: VisualTransformation = VisualTransformation.None
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
            WantedTextField(
                modifier = Modifier,
                value = value,
                error = status == WantedTextFieldDefaults.Status.Negative,
                enabled = enabled,
                size = size,
                trailingButtonEnabled = trailingButtonEnabled,
                complete = status == WantedTextFieldDefaults.Status.Positive,
                maxLines = maxLines,
                minLines = minLines,
                maxWordCount = maxWordCount,
                enabledOverflowText = enabledOverflowText,
                interactionSource = interactionSource,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                background = background,
                visualTransformation = visualTransformation,
                trailingButton = trailingButton,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                trailingContent = trailingContent,
                onClickTrailingButton = onClickTrailingButton,
                onValueChange = onValueChange,
                focusRequester = focusRequester
            )
        },
        message = description?.let {
            {
                Text(
                    text = description,
                    style = DesignSystemTheme.typography.caption1Regular,
                    color = when {
                        enabled && status == WantedTextFieldDefaults.Status.Negative -> {
                            DesignSystemTheme.colors.foregroundNegativePrimary
                        }

                        else -> DesignSystemTheme.colors.foregroundNeutralTertiary
                    },
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
                    text = "Large (default)",
                    placeholder = "텍스트를 입력해 주세요.",
                    size = WantedTextFieldDefaults.Size.Large,
                )

                WantedTextField(
                    text = "Medium",
                    placeholder = "텍스트를 입력해 주세요.",
                    size = WantedTextFieldDefaults.Size.Medium,
                )

                WantedTextField(
                    text = "Medium + button",
                    placeholder = "텍스트를 입력해 주세요.",
                    size = WantedTextFieldDefaults.Size.Medium,
                    trailingButton = "텍스트"
                )

                WantedTextField(
                    title = "주제",
                    requiredBadge = true,
                    text = "입력한 텍스트.",
                    placeholder = "텍스트를 입력해 주세요.",
                    trailingButton = "텍스트"
                )

                WantedTextField(
                    title = "",
                    text = "",
                    placeholder = "텍스트를 입력해 주세요.",
                    enabled = false,
                    status = WantedTextFieldDefaults.Status.Negative
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    enabled = false,
                    placeholder = "텍스트를 입력해 주세요.",
                    trailingButton = "텍스트",
                    status = WantedTextFieldDefaults.Status.Negative
                )

                WantedTextField(
                    requiredBadge = true,
                    text = "입력한 텍스트.",
                    placeholder = "텍스트를 입력해 주세요.",
                    trailingButton = "텍스트",
                    enabled = false
                )

                WantedTextField(
                    text = "텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요.",
                    placeholder = "텍스트를 입력해 주세요.",
                    trailingButton = "텍스트"
                )

                WantedTextField(
                    text = "텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요.",
                    placeholder = "텍스트를 입력해 주세요.",
                    trailingButton = "텍스트",
                    trailingButtonEnabled = false
                )

                WantedTextField(
                    text = "텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요.",
                    placeholder = "텍스트를 입력해 주세요.",
                    trailingButton = "텍스트",
                    trailingButtonEnabled = false
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    trailingButton = "텍스트",
                    status = WantedTextFieldDefaults.Status.Positive,
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    trailingButton = "텍스트",
                    status = WantedTextFieldDefaults.Status.Positive,
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    trailingButton = "텍스트",
                    status = WantedTextFieldDefaults.Status.Positive,
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    trailingButton = "텍스트",
                    status = WantedTextFieldDefaults.Status.Positive,
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
                    trailingButton = "텍스트",
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = R.drawable.icon_normal_circle_check),
                            contentDescription = ""
                        )
                    }
                )

                WantedTextField(
                    text = "텍스트를 입력해 주세요.",
                    placeholder = "텍스트를 입력해 주세요. ",
                    trailingButton = "텍스트",
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = R.drawable.icon_normal_circle_check_fill),
                            tint = DesignSystemTheme.colors.foregroundBrandPrimary,
                            contentDescription = ""
                        )
                    },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = R.drawable.icon_normal_circle_check_fill),
                            tint = DesignSystemTheme.colors.foregroundBrandPrimary,
                            contentDescription = ""
                        )
                    },
                    trailingContent = {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = R.drawable.icon_normal_circle_check_fill),
                            tint = DesignSystemTheme.colors.foregroundBrandPrimary,
                            contentDescription = ""
                        )
                    }
                )


                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    trailingButton = "텍스트",
                    description = "메시지에 마침표를 찍어요.",
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    trailingButton = "텍스트",
                    status = WantedTextFieldDefaults.Status.Negative,
                    description = "메시지에 마침표를 찍어요.",
                )


                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    enabled = false,
                    description = "메시지에 마침표를 찍어요.",
                )

                WantedTextField(
                    text = "",
                    placeholder = "텍스트를 입력해 주세요.",
                    enabled = false,
                    description = "메시지에 마침표를 찍어요.",
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    trailingButton = "텍스트",
                    enabled = false,
                    description = "메시지에 마침표를 찍어요.",
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    trailingButton = "텍스트",
                    enabled = false,
                    status = WantedTextFieldDefaults.Status.Positive,
                    description = "메시지에 마침표를 찍어요.",
                )

                WantedTextField(
                    text = "입력한 텍스트",
                    placeholder = "텍스트를 입력해 주세요.",
                    trailingButton = "텍스트",
                    enabled = false,
                    status = WantedTextFieldDefaults.Status.Negative,
                    description = "메시지에 마침표를 찍어요.",
                )
            }
        }
    }
}
