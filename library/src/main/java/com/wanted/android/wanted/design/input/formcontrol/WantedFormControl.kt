package com.wanted.android.wanted.design.input.formcontrol

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth
import androidx.compose.ui.unit.dp
import kotlin.math.ceil
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * WantedFormControl
 *
 * 입력 컨트롤(TextField, TextArea, Select 등)에 라벨·필수 표시·메시지·글자수를 붙이는 슬롯 기반 래퍼 컴포넌트입니다.
 * 라벨/입력/메시지의 배치도 함께 처리합니다.
 *
 * 단독으로 값을 입력받지 않습니다. input 슬롯에 실제 입력 컴포넌트를 조합해서 사용합니다.
 * Web @wanteddev/wds 의 FormControl 과 1:1 대응되는 Android 구현입니다.
 * status·enabled 는 입력 아래 메시지(description)에만 적용되고 input 슬롯에는 전달되지 않으므로, 입력 컴포넌트의 상태(TextField 는 error, TextArea·Select 는 status)와 enabled 는 따로 넘깁니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedFormControl(
 *     label = "이메일",
 *     required = true,
 *     description = "올바른 이메일 형식을 입력해 주세요.",
 *     status = WantedFormControlDefaults.Status.Negative,
 * ) {
 *     WantedTextField(
 *         value = email,
 *         placeholder = "이메일을 입력해 주세요.",
 *         error = true,
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
 *         onValueChange = { email = it },
 *     )
 * }
 * ```
 *
 * @param modifier Modifier: Modifier를 설정합니다.
 * @param label String: 라벨 텍스트입니다. 빈 문자열이면 라벨과 필수 뱃지를 모두 렌더링하지 않습니다. 1줄로 표시되고 넘치면 말줄임(…) 처리되며 필수 뱃지(*)는 말줄임한 텍스트 바로 뒤에 붙습니다. Leading 배치를 단독으로 쓰면 라벨 폭이 전체 폭의 50%로 제한되고, WantedFormControlGroup 안에서는 그룹의 라벨 열 폭을 따릅니다.
 * @param required Boolean: true이면 라벨 옆에 필수 뱃지(*)를 표시합니다. 뱃지 타이포는 size를 따릅니다.
 * @param description String?: 입력 컨트롤 하단에 표시되는 도움말 또는 에러 메시지입니다. null이면 표시하지 않으며, 최대 2줄까지 표시되고 넘치면 말줄임(…) 처리됩니다. 색상은 status·enabled를 따릅니다.
 * @param accessory (@Composable () -> Unit)?: 글자수 표시 슬롯입니다. WantedTextAreaCharacterCount를 주입할 수 있습니다.
 * @param size WantedFormControlDefaults.Size: 라벨·필수 뱃지 텍스트 크기를 지정합니다. (Large, Medium)
 * @param status WantedFormControlDefaults.Status: description 색상을 정합니다. Negative이면 에러 색, Normal·Positive는 기본 색으로 표시됩니다(Positive 전용 색은 없습니다). enabled가 false이면 status와 무관하게 비활성화 색이 우선합니다. (Normal, Positive, Negative)
 * @param labelPlacement WantedFormControlDefaults.LabelPlacement: 라벨 배치 위치를 지정합니다. (Top, Leading)
 * @param labelWidth Dp?: Leading 배치의 라벨 열 폭입니다. 값을 주면 그 폭으로 고정하고 넘치는 라벨은 말줄임합니다. WantedFormControlGroup의 labelWidth보다 우선합니다. null이면 그룹 안에서는 그룹 폭을, 단독으로는 부모 폭 50% 규칙을 따릅니다. Top 배치에서는 쓰지 않습니다.
 * @param enabled Boolean: false이면 description을 비활성화 색상으로 표시합니다(status보다 우선). 라벨·accessory·input 슬롯은 바뀌지 않으므로 입력 컴포넌트의 enabled는 따로 넘깁니다.
 * @param input @Composable () -> Unit: 필수 입력 컨트롤 슬롯입니다. (WantedTextField, WantedTextArea, WantedSelect 등)
 */
@Composable
fun WantedFormControl(
    modifier: Modifier = Modifier,
    label: String = "",
    required: Boolean = false,
    description: String? = null,
    accessory: @Composable (() -> Unit)? = null,
    size: WantedFormControlDefaults.Size = WantedFormControlDefaults.Size.Large,
    status: WantedFormControlDefaults.Status = WantedFormControlDefaults.Status.Normal,
    labelPlacement: WantedFormControlDefaults.LabelPlacement = WantedFormControlDefaults.LabelPlacement.Top,
    labelWidth: Dp? = null,
    enabled: Boolean = true,
    input: @Composable () -> Unit,
) {
    val group = LocalWantedFormControlGroupState.current

    // 개별 labelWidth·그룹 labelWidth가 모두 없을 때만 그룹의 공유 폭(가장 긴 라벨) 계산에 참여한다.
    if (group != null && sharesGroupLabelWidth(group, label, labelPlacement, labelWidth)) {
        RegisterGroupLabelWidth(
            registry = group.registry,
            label = label,
            required = required,
            size = size,
        )
    }

    WantedFormControlLayout(
        modifier = modifier,
        labelPlacement = labelPlacement,
        leadingLabelWidth = {
            val fixedWidth = labelWidth ?: group?.labelWidth
            when {
                fixedWidth != null -> LeadingLabelWidth.Fixed(fixedWidth.roundToPx())
                group != null -> LeadingLabelWidth.Shared(group.registry.maxWidthPx())
                else -> LeadingLabelWidth.Standalone
            }
        },
        label = if (label.isNotEmpty()) {
            {
                WantedFormControlTitle(
                    label = label,
                    required = required,
                    size = size,
                )
            }
        } else null,
        footer = if (description != null || accessory != null) {
            {
                WantedFormControlFooter(
                    description = description,
                    accessory = accessory,
                    enabled = enabled,
                    status = status,
                )
            }
        } else null,
        input = input,
    )
}

// 그룹의 공유 폭(가장 긴 라벨) 계산에 이 FormControl 의 라벨이 참여하는지 판정합니다.
//
// Leading 배치·라벨 있음·개별/그룹 labelWidth 모두 없음일 때만 참여합니다.
private fun sharesGroupLabelWidth(
    group: WantedFormControlGroupState,
    label: String,
    labelPlacement: WantedFormControlDefaults.LabelPlacement,
    labelWidth: Dp?,
): Boolean = labelPlacement == WantedFormControlDefaults.LabelPlacement.Leading &&
    label.isNotEmpty() &&
    labelWidth == null &&
    group.labelWidth == null

/** 라벨 Row 의 좌우 여백입니다. 그룹 공유 폭 계산과 같은 값을 써야 합니다. */
private val TitleHorizontalPadding = 2.dp

/** 라벨 텍스트와 필수 뱃지(*) 사이 간격입니다. 그룹 공유 폭 계산과 같은 값을 써야 합니다. */
private val TitleRequiredGap = 2.dp

private const val REQUIRED_MARK = "*"

@Composable
private fun titleTextStyle(size: WantedFormControlDefaults.Size): TextStyle = when (size) {
    WantedFormControlDefaults.Size.Large -> DesignSystemTheme.typography.label1Bold
    WantedFormControlDefaults.Size.Medium -> DesignSystemTheme.typography.label2Bold
}

// 그룹 안 Leading 라벨의 자연 폭(말줄임 없이 그릴 때 [WantedFormControlTitle] 의 폭)을 그룹에 등록합니다.
//
// 폭은 [WantedFormControlTitle] 과 같은 스타일·여백·간격으로 텍스트를 측정해 구합니다.
// SideEffect 로 등록하므로 같은 프레임의 측정 단계에서 그룹 최댓값에 반영됩니다.
@Composable
private fun RegisterGroupLabelWidth(
    registry: WantedFormControlGroupLabelWidthRegistry,
    label: String,
    required: Boolean,
    size: WantedFormControlDefaults.Size,
) {
    val key = remember { Any() }
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current
    val textStyle = titleTextStyle(size)

    val widthPx = remember(label, required, textStyle, density, textMeasurer) {
        with(density) {
            val labelWidthPx = textMeasurer.measure(text = label, style = textStyle, maxLines = 1).size.width
            val requiredWidthPx = if (required) {
                TitleRequiredGap.roundToPx() +
                    textMeasurer.measure(text = REQUIRED_MARK, style = textStyle, maxLines = 1).size.width
            } else {
                0
            }
            TitleHorizontalPadding.roundToPx() * 2 + labelWidthPx + requiredWidthPx
        }
    }

    SideEffect { registry.register(key, widthPx) }
    DisposableEffect(registry, key) {
        onDispose { registry.unregister(key) }
    }
}

// 라벨 텍스트와 필수 뱃지(*)를 한 줄로 배치합니다.
//
// 라벨이 넘쳐 말줄임되면 Text 는 받은 폭을 모두 차지해 "…" 뒤에 빈 공간이 남습니다.
// 그 폭 뒤에 * 를 두면 "담당자 연…  *" 처럼 떨어지므로, 실제로 그려진 줄 폭(onTextLayout)
// 바로 뒤에 * 를 둡니다. onTextLayout 은 Text 측정 중에 호출되어 같은 측정 단계에서 읽을 수 있습니다.
@Composable
private fun WantedFormControlTitle(
    label: String,
    required: Boolean,
    size: WantedFormControlDefaults.Size,
) {
    val textStyle = titleTextStyle(size)
    val labelLayout = remember { TitleTextLayoutHolder() }

    Layout(
        modifier = Modifier.padding(horizontal = TitleHorizontalPadding),
        content = {
            Text(
                text = label,
                style = textStyle,
                color = DesignSystemTheme.colors.foregroundNeutralSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { labelLayout.result = it },
            )
            if (required) {
                Text(
                    text = REQUIRED_MARK,
                    style = textStyle,
                    color = DesignSystemTheme.colors.foregroundNegativePrimary,
                    maxLines = 1,
                )
            }
        },
    ) { measurables, constraints ->
        val requiredPlaceable = if (required) {
            measurables[1].measure(constraints.copy(minWidth = 0, minHeight = 0))
        } else {
            null
        }
        val requiredWidth = requiredPlaceable?.let { it.width + TitleRequiredGap.roundToPx() } ?: 0

        val labelMaxWidth = if (constraints.hasBoundedWidth) {
            (constraints.maxWidth - requiredWidth).coerceAtLeast(0)
        } else {
            Constraints.Infinity
        }
        val labelPlaceable = measurables[0].measure(
            constraints.copy(minWidth = 0, maxWidth = labelMaxWidth, minHeight = 0)
        )
        // 말줄임된 경우 Text 폭이 아니라 실제로 그려진 줄 폭을 쓴다.
        val labelVisibleWidth = labelLayout.result
            ?.let { ceil(it.getLineRight(0) - it.getLineLeft(0)).toInt() }
            ?.coerceAtMost(labelPlaceable.width)
            ?: labelPlaceable.width

        val width = constraints.constrainWidth(labelVisibleWidth + requiredWidth)
        val height = constraints.constrainHeight(
            maxOf(labelPlaceable.height, requiredPlaceable?.height ?: 0)
        )

        layout(width, height) {
            // 기존 Row(verticalAlignment = Bottom) 와 같은 하단 정렬
            labelPlaceable.placeRelative(x = 0, y = height - labelPlaceable.height)
            requiredPlaceable?.placeRelative(
                x = labelVisibleWidth + TitleRequiredGap.roundToPx(),
                y = height - requiredPlaceable.height,
            )
        }
    }
}

// 라벨 Text 의 마지막 TextLayoutResult 를 측정 단계에서 읽기 위한 보관함입니다. (state 가 아니므로 재구성을 일으키지 않음)
private class TitleTextLayoutHolder {
    var result: TextLayoutResult? = null
}

@Composable
private fun WantedFormControlFooter(
    description: String?,
    accessory: @Composable (() -> Unit)?,
    enabled: Boolean,
    status: WantedFormControlDefaults.Status,
) {
    val descriptionColor = when {
        !enabled -> DesignSystemTheme.colors.foregroundDisablePrimary
        status == WantedFormControlDefaults.Status.Negative -> {
            DesignSystemTheme.colors.foregroundNegativePrimary
        }

        else -> DesignSystemTheme.colors.foregroundNeutralTertiary
    }

    when {
        description != null && accessory != null -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = description,
                    style = DesignSystemTheme.typography.caption1Regular,
                    color = descriptionColor,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                accessory()
            }
        }

        description != null -> {
            Text(
                modifier = Modifier.padding(horizontal = 2.dp),
                text = description,
                style = DesignSystemTheme.typography.caption1Regular,
                color = descriptionColor,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }

        accessory != null -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                accessory()
            }
        }
    }
}


@DevicePreviews
@Composable
private fun WantedFormControlPreview() {
    DesignSystemTheme {
        Surface {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                WantedFormControl(
                    label = "라벨",
                    description = "도움말 메시지입니다.",
                    status = WantedFormControlDefaults.Status.Normal,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .background(DesignSystemTheme.colors.surfaceNeutralSecondary)
                    )
                }

                WantedFormControl(
                    label = "필수 항목",
                    required = true,
                    description = "이미 사용 중인 값입니다.",
                    status = WantedFormControlDefaults.Status.Negative,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .background(DesignSystemTheme.colors.surfaceNeutralSecondary)
                    )
                }

                WantedFormControl(
                    label = "비활성화",
                    description = "비활성화 상태 메시지입니다.",
                    enabled = false,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .background(DesignSystemTheme.colors.surfaceNeutralTertiary)
                    )
                }

                WantedFormControl(
                    label = "Medium 크기",
                    required = true,
                    size = WantedFormControlDefaults.Size.Medium,
                    description = "Medium 크기 라벨입니다.",
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(DesignSystemTheme.colors.surfaceNeutralSecondary)
                    )
                }

                WantedFormControl(
                    label = "Leading 배치",
                    required = true,
                    labelPlacement = WantedFormControlDefaults.LabelPlacement.Leading,
                    description = "라벨이 좌측에 배치됩니다.",
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .background(DesignSystemTheme.colors.surfaceNeutralSecondary)
                    )
                }

                WantedFormControl(
                    label = "Leading TextArea",
                    required = true,
                    labelPlacement = WantedFormControlDefaults.LabelPlacement.Leading,
                    description = "여러 줄 입력 시 라벨은 상단 영역 기준으로 정렬됩니다.",
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(116.dp)
                            .background(DesignSystemTheme.colors.surfaceNeutralSecondary)
                    )
                }
            }
        }
    }
}
