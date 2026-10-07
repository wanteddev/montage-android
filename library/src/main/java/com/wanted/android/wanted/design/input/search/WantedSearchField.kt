package com.wanted.android.wanted.design.input.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.input.search.WantedSearchFieldDefaults.Size
import com.wanted.android.wanted.design.input.search.WantedSearchFieldDefaults.Variant
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * WantedSearchField
 *
 * 검색 입력 필드 컴포넌트입니다.
 *
 * String 타입의 텍스트를 받아 검색 기능을 제공하는 입력 필드를 표시합니다.
 * 검색 아이콘이 항상 표시되고, 입력값이 있으면 삭제 버튼이 표시됩니다.
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
 * @param variant Variant: 입력 필드의 스타일입니다. Solid 또는 Outlined를 사용할 수 있습니다.
 * @param size Size: 입력 필드의 크기입니다. Size.Large 또는 Size.Medium을 사용할 수 있습니다.
 * @param maxWordCount Int: 입력 가능한 최대 글자 수입니다.
 * @param enabledOverflowText Boolean: 최대 글자 수를 초과하는 입력을 허용할지 여부입니다.
 * @param interactionSource MutableInteractionSource: 사용자 상호작용 상태를 추적하는 소스입니다.
 * @param keyboardOptions KeyboardOptions: 키보드 옵션 설정입니다.
 * @param keyboardActions KeyboardActions: 키보드 액션 설정입니다.
 * @param textStyle TextStyle?: 입력 텍스트의 스타일입니다. null이면 size별 기본 typography를 사용합니다.
 * @param cursorBrush Brush?: 커서의 색상을 지정하는 브러시입니다. null이면 primary 색상을 사용합니다.
 * @param focusRequester FocusRequester: 포커스 요청을 처리하는 객체입니다.
 * @param onValueChange (String) -> Unit: 텍스트 값이 변경될 때 호출되는 콜백 함수입니다.
 */
@Composable
fun WantedSearchField(
    text: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    enabled: Boolean = true,
    variant: Variant = Variant.Solid,
    size: Size = Size.Large,
    maxWordCount: Int = Int.MAX_VALUE,
    enabledOverflowText: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textStyle: TextStyle? = null,
    cursorBrush: Brush? = null,
    focusRequester: FocusRequester = remember { FocusRequester() },
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
        variant = variant,
        size = size,
        enabled = enabled,
        maxWordCount = maxWordCount,
        enabledOverflowText = enabledOverflowText,
        interactionSource = interactionSource,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = modifier,
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
 * WantedSearchField
 *
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
 * @param variant Variant: 입력 필드의 스타일입니다. Solid 또는 Outlined를 사용할 수 있습니다.
 * @param size Size: 입력 필드의 크기입니다. Size.Large 또는 Size.Medium을 사용할 수 있습니다.
 * @param maxWordCount Int: 입력 가능한 최대 글자 수입니다.
 * @param enabledOverflowText Boolean: 최대 글자 수를 초과하는 입력을 허용할지 여부입니다.
 * @param interactionSource MutableInteractionSource: 사용자 상호작용 상태를 추적하는 소스입니다.
 * @param keyboardOptions KeyboardOptions: 키보드 옵션 설정입니다.
 * @param keyboardActions KeyboardActions: 키보드 액션 설정입니다.
 * @param textStyle TextStyle?: 입력 텍스트의 스타일입니다. null이면 size별 기본 typography를 사용합니다.
 * @param cursorBrush Brush?: 커서의 색상을 지정하는 브러시입니다. null이면 primary 색상을 사용합니다.
 * @param focusRequester FocusRequester: 포커스 요청을 처리하는 객체입니다.
 * @param onValueChange (TextFieldValue) -> Unit: 텍스트 값이 변경될 때 호출되는 콜백 함수입니다.
 */
@Composable
fun WantedSearchField(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    enabled: Boolean = true,
    variant: Variant = Variant.Solid,
    size: Size = Size.Large,
    maxWordCount: Int = Int.MAX_VALUE,
    enabledOverflowText: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textStyle: TextStyle? = null,
    cursorBrush: Brush? = null,
    focusRequester: FocusRequester = remember { FocusRequester() },
    onValueChange: (TextFieldValue) -> Unit = {}
) {
    SearchTextField(
        value = value,
        placeholder = placeholder,
        variant = variant,
        size = size,
        enabled = enabled,
        maxWordCount = maxWordCount,
        enabledOverflowText = enabledOverflowText,
        interactionSource = interactionSource,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = modifier,
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
    variant: Variant,
    size: Size,
    enabled: Boolean,
    maxWordCount: Int,
    enabledOverflowText: Boolean,
    interactionSource: MutableInteractionSource,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    textStyle: TextStyle?,
    cursorBrush: Brush?,
    modifier: Modifier = Modifier,
    onValueChange: (TextFieldValue) -> Unit = {},
    focusRequester: FocusRequester = remember { FocusRequester() }
) {
    val shape = RoundedCornerShape(size.borderRadius)
    val inputTextStyle = size.inputTextStyle
    // 호출부 textStyle 은 존중하되, disabled 색은 placeholder 와 동일하게 컴포넌트가 보장한다.
    val baseTextStyle = textStyle
        ?: inputTextStyle.copy(color = DesignSystemTheme.colors.foregroundNeutralPrimary)
    val resolvedTextStyle = if (enabled) {
        baseTextStyle
    } else {
        baseTextStyle.copy(color = DesignSystemTheme.colors.foregroundDisablePrimary)
    }
    val resolvedCursorBrush = cursorBrush ?: SolidColor(DesignSystemTheme.colors.foregroundBrandPrimary)

    BasicTextField(
        modifier = modifier
            .defaultMinSize(minHeight = size.minHeight)
            .fillMaxWidth()
            .clip(shape)
            .background(containerBackgroundColor(variant = variant, enabled = enabled))
            .border(
                width = CONTAINER_BORDER_WIDTH,
                color = containerBorderColor(variant = variant, enabled = enabled),
                shape = shape
            )
            .focusRequester(focusRequester)
            .padding(size.containerPadding),
        value = value,
        maxLines = 1,
        minLines = 1,
        enabled = enabled,
        singleLine = true,
        cursorBrush = resolvedCursorBrush,
        interactionSource = interactionSource,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        textStyle = resolvedTextStyle,
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
                iconSize = size.iconSize,
                iconPadding = size.iconPadding,
                innerTextField = innerTextField,
                placeholder = placeholderSlot(
                    value = value,
                    placeholder = placeholder,
                    enabled = enabled,
                    textStyle = inputTextStyle
                ),
                leadingIcon = leadingIconSlot(enabled = enabled),
                trailingIcon = clearIconSlot(
                    value = value,
                    enabled = enabled,
                    onValueChange = onValueChange
                )
            )
        }
    )
}

/** 최대 글자 수 정책을 적용한 값을 반환합니다. */
private fun coerceWordCount(
    newValue: TextFieldValue,
    currentValue: TextFieldValue,
    maxWordCount: Int,
    enabledOverflowText: Boolean
): TextFieldValue = when {
    enabledOverflowText -> newValue
    newValue.text.length <= maxWordCount -> newValue
    newValue.text.length < currentValue.text.length -> newValue
    else -> currentValue
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
            color = if (enabled) {
                DesignSystemTheme.colors.foregroundNeutralTertiary
            } else {
                DesignSystemTheme.colors.foregroundDisablePrimary
            }
        )
    }
}

/** 좌측 검색 아이콘 슬롯을 반환합니다. */
private fun leadingIconSlot(enabled: Boolean): @Composable () -> Unit = {
    Icon(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(R.drawable.icon_normal_search),
        tint = if (enabled) {
            DesignSystemTheme.colors.foregroundNeutralTertiary
        } else {
            DesignSystemTheme.colors.foregroundDisablePrimary
        },
        contentDescription = null
    )
}

// 우측 clear 아이콘 슬롯을 반환합니다.
//
// 입력값이 있고 활성 상태이면 포커스와 무관하게 노출합니다. (Figma active 속성 기준)
private fun clearIconSlot(
    value: TextFieldValue,
    enabled: Boolean,
    onValueChange: (TextFieldValue) -> Unit
): (@Composable () -> Unit)? {
    if (value.text.isEmpty() || !enabled) return null

    return {
        WantedTouchArea(
            modifier = Modifier,
            shape = CircleShape,
            verticalPadding = CLEAR_TOUCH_PADDING,
            horizontalPadding = CLEAR_TOUCH_PADDING,
            content = {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    painter = painterResource(R.drawable.icon_normal_circle_close_fill),
                    tint = DesignSystemTheme.colors.foregroundNeutralQuaternary,
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

// Variant·활성 상태별 Container 배경 색상입니다.
//
// Figma 4.0.0 disabled 정의 기준입니다.
// - Solid: disabled 에서도 배경은 surfaceNeutralSecondary 을 유지하고 icon·text 만 foregroundDisablePrimary 로 내립니다.
// - Outlined: enabled 는 투명 배경, disabled 는 surfaceNeutralTertiary 를 사용합니다.
@Composable
private fun containerBackgroundColor(
    variant: Variant,
    enabled: Boolean
): Color = when (variant) {
    Variant.Solid -> DesignSystemTheme.colors.surfaceNeutralSecondary
    Variant.Outlined -> if (enabled) {
        DesignSystemTheme.colors.effectTransparentPrimary
    } else {
        DesignSystemTheme.colors.surfaceNeutralTertiary
    }
}

/** Variant·활성 상태별 Container 보더 색상입니다. Solid는 보더를 사용하지 않습니다. */
@Composable
private fun containerBorderColor(
    variant: Variant,
    enabled: Boolean
): Color = when (variant) {
    Variant.Solid -> DesignSystemTheme.colors.transparent
    Variant.Outlined -> if (enabled) {
        DesignSystemTheme.colors.lineNeutralSecondary
    } else {
        DesignSystemTheme.colors.lineNeutralTertiary
    }
}


// Figma 의 Container > Content 구조를 그대로 옮긴 decoration box 입니다.
//
// clear 버튼도 Content 안에 두어 Content 좌우 패딩을 함께 받습니다. Content 밖에 두면
// 컨테이너 패딩만 받아 우측으로 밀려 보입니다.
@Composable
private fun DecorationBox(
    innerTextField: @Composable () -> Unit,
    iconSize: Dp,
    iconPadding: Dp,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier.padding(horizontal = CONTENT_HORIZONTAL_PADDING),
        // Content 내부(icon ↔ text ↔ clear) gap 2dp
        horizontalArrangement = Arrangement.spacedBy(CONTENT_GAP, alignment = Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingIcon?.let {
            IconFrame(iconSize = iconSize, iconPadding = iconPadding) {
                leadingIcon()
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = TEXT_HORIZONTAL_PADDING)
                .wrapContentHeight(),
            contentAlignment = Alignment.CenterStart
        ) {
            placeholder?.let {
                placeholder()
            }

            innerTextField()
        }

        trailingIcon?.let {
            IconFrame(iconSize = iconSize, iconPadding = iconPadding) {
                trailingIcon()
            }
        }
    }
}

// Figma 의 `Icon` 프레임입니다.
//
// 아이콘 크기 + 좌우 패딩만큼의 정사각 영역을 차지하고, 그 안쪽에 아이콘을 담습니다.
// 이 패딩이 없으면 아이콘과 뒤따르는 텍스트가 좌측으로 밀려 보입니다.
@Composable
private fun IconFrame(
    iconSize: Dp,
    iconPadding: Dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .size(iconSize + iconPadding * 2)
            .padding(iconPadding),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

private val CONTAINER_BORDER_WIDTH = 1.dp

private val CONTENT_GAP = 2.dp

private val CONTENT_HORIZONTAL_PADDING = 4.dp

private val TEXT_HORIZONTAL_PADDING = 4.dp

private val CLEAR_TOUCH_PADDING = 8.dp


/** Size × 상태 조합을 한 번에 보여 주는 preview 본문입니다. */
@Composable
private fun WantedSearchFieldPreviewCases(variant: Variant) {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                listOf(Size.Large, Size.Medium).forEach { size ->
                    WantedSearchField(
                        text = "",
                        placeholder = "검색어를 입력해 주세요.",
                        variant = variant,
                        size = size
                    )

                    WantedSearchField(
                        text = "검색어",
                        placeholder = "검색어를 입력해 주세요.",
                        variant = variant,
                        size = size
                    )

                    WantedSearchField(
                        text = "",
                        placeholder = "검색어를 입력해 주세요.",
                        variant = variant,
                        size = size,
                        enabled = false
                    )

                    // disabled + 입력값: clear 아이콘 미노출과 foregroundDisablePrimary 입력 색을 확인한다.
                    WantedSearchField(
                        text = "검색어",
                        placeholder = "검색어를 입력해 주세요.",
                        variant = variant,
                        size = size,
                        enabled = false
                    )
                }
            }
        }
    }
}


@DevicePreviews
@Composable
private fun WantedSearchFieldSolidPreview() {
    WantedSearchFieldPreviewCases(variant = Variant.Solid)
}


@DevicePreviews
@Composable
private fun WantedSearchFieldOutlinedPreview() {
    WantedSearchFieldPreviewCases(variant = Variant.Outlined)
}
