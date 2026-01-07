package com.wanted.android.wanted.design.input.input

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.input.input.WantedInputDefaults.WantedInputSize
import com.wanted.android.wanted.design.input.input.WantedInputDefaults.WantedInputVariant
import com.wanted.android.wanted.design.input.input.control.CheckBoxSize
import com.wanted.android.wanted.design.input.input.control.CheckBoxState
import com.wanted.android.wanted.design.input.input.control.CheckBoxStyle
import com.wanted.android.wanted.design.input.input.control.WantedCheckBox
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.clickOnce


/**
 * Checkbox, Radio, Checkmark 스타일을 포함하는 커스텀 입력 행 컴포넌트입니다.
 *
 * label, size, variant, checkBoxState 등을 조합하여 텍스트 라벨과 Checkbox를 포함한 입력 항목을 구성합니다.
 * 내부적으로 WantedCheckBox 및 WantedInputLayout을 활용하여 입력 항목 UI를 생성합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedInput(
 *     label = "이용약관에 동의합니다.",
 *     variant = WantedInputVariant.CheckBox,
 *     size = WantedInputSize.Medium,
 *     checkBoxState = CheckBoxState.Checked,
 *     onCheckedChange = { /* 상태 변경 처리 */ }
 * )
 * ```
 *
 * @param modifier Modifier: 외형 및 배치를 제어하는 Modifier입니다.
 * @param label String: 항목에 표시될 텍스트입니다.
 * @param variant WantedInputVariant: CheckBox, Radio, CheckMark, Switch 중 하나의 타입을 지정합니다.
 * @param size WantedInputSize: Medium 또는 Small 사이즈를 지정합니다.
 * @param checkBoxState CheckBoxState: 체크 상태 (Unchecked, Checked, Indeterminate)를 지정합니다.
 * @param bold Boolean: true일 경우 텍스트를 굵게 표시합니다.
 * @param enabled Boolean: 항목의 활성화 여부를 설정합니다.
 * @param tight Boolean: true일 경우 컴팩트한 스타일로 표시됩니다.
 * @param textStyle TextStyle: 텍스트 스타일을 수동 지정할 수 있으며, 생략 시 자동으로 size와 bold를 기반으로 설정됩니다.
 * @param interactionSource MutableInteractionSource: 클릭 상호작용 효과를 전달합니다.
 * @param onCheckedChange (Boolean) -> Unit: 체크 상태가 변경될 때 호출되는 콜백입니다.
 */
@Composable
fun WantedInput(
    modifier: Modifier = Modifier,
    label: String = "",
    variant: WantedInputVariant = WantedInputVariant.CheckBox,
    size: WantedInputSize = WantedInputSize.Medium,
    checkBoxState: CheckBoxState = CheckBoxState.Unchecked,
    bold: Boolean = false,
    enabled: Boolean = true,
    tight: Boolean = false,
    textStyle: TextStyle = if (size == WantedInputSize.Medium) {
        if (bold) {
            DesignSystemTheme.typography.body2Bold
        } else {
            DesignSystemTheme.typography.body2Regular
        }
    } else {
        if (bold) {
            DesignSystemTheme.typography.label1Bold
        } else {
            DesignSystemTheme.typography.label1Regular
        }
    }.copy(
        color = if (enabled) {
            DesignSystemTheme.colors.labelNormal
        } else {
            DesignSystemTheme.colors.labelDisable
        }
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onCheckedChange: ((Boolean) -> Unit) = {}
) {
    val density = LocalDensity.current
    val checkBoxInteractionSource: MutableInteractionSource =
        remember { MutableInteractionSource() }

    ProvideTextStyle(value = textStyle) {
        WantedInputLayout(
            modifier = modifier
                .clickOnce(
                    enabled = enabled,
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    if (checkBoxState == CheckBoxState.Unchecked) {
                        onCheckedChange(true)
                    } else {
                        onCheckedChange(false)
                    }

                    val offset = with(density) {
                        (if (size == WantedInputSize.Medium) 16.dp else 12.dp).toPx()
                    }

                    val press = PressInteraction.Press(Offset(offset, offset))
                    checkBoxInteractionSource.tryEmit(press)
                    checkBoxInteractionSource.tryEmit(PressInteraction.Release(press))
                },
            size = size,
            tight = tight,
            textStyle = textStyle,
            leadingIcon = {
                WantedCheckBox(
                    modifier = Modifier,
                    size = if (size == WantedInputSize.Medium) {
                        CheckBoxSize.Normal
                    } else {
                        CheckBoxSize.Small
                    },
                    style = when (variant) {
                        WantedInputVariant.CheckBox -> CheckBoxStyle.CheckBox
                        WantedInputVariant.Radio -> CheckBoxStyle.Radio
                        WantedInputVariant.CheckMark -> CheckBoxStyle.Check
                        WantedInputVariant.Switch -> CheckBoxStyle.Switch
                    },
                    checkState = checkBoxState,
                    tight = tight,
                    enabled = enabled,
                    interactionSource = checkBoxInteractionSource,
                    onCheckedChange = onCheckedChange

                )
            },
            label = {
                Text(text = label)
            }
        )
    }
}

@DevicePreviews
@Composable
private fun WantedInputPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {

                Text(text = "CheckBox Small")
                WantedInput(WantedInputSize.Small, WantedInputVariant.CheckBox)

                Text(text = "CheckBox Normal")
                WantedInput(WantedInputSize.Medium, WantedInputVariant.CheckBox)

                Text(text = "Radio Small")
                WantedInput(WantedInputSize.Small, WantedInputVariant.Radio)

                Text(text = "Radio Normal")
                WantedInput(WantedInputSize.Medium, WantedInputVariant.Radio)

                Text(text = "NestedCheckBox Small")
                WantedInput(WantedInputSize.Small, WantedInputVariant.CheckMark)

                Text(text = "NestedCheckBox Normal")
                WantedInput(WantedInputSize.Medium, WantedInputVariant.CheckMark)
            }
        }
    }
}

@Composable
private fun WantedInput(
    size: WantedInputSize,
    variant: WantedInputVariant
) {
    var checked by remember { mutableStateOf(CheckBoxState.Unchecked) }

    WantedInput(
        modifier = Modifier,
        label = "${size.name}\n${size.name}",
        size = size,
        checkBoxState = checked,
        bold = false,
        enabled = true,
        variant = variant,
        onCheckedChange = {
            checked = if (it) {
                CheckBoxState.Checked
            } else {
                CheckBoxState.Unchecked
            }
        }
    )

    WantedInput(
        modifier = Modifier,
        label = size.name,
        size = size,
        checkBoxState = checked,
        bold = false,
        enabled = false,
        variant = variant,
        onCheckedChange = {
            checked = if (it) {
                CheckBoxState.Checked
            } else {
                CheckBoxState.Unchecked
            }
        }
    )

    WantedInput(
        modifier = Modifier,
        label = size.name,
        size = size,
        checkBoxState = checked,
        bold = true,
        enabled = true,
        variant = variant,
        onCheckedChange = {
            checked = if (it) {
                CheckBoxState.Checked
            } else {
                CheckBoxState.Unchecked
            }
        }
    )

    WantedInput(
        modifier = Modifier,
        label = size.name,
        size = size,
        checkBoxState = checked,
        bold = true,
        enabled = false,
        variant = variant,
        onCheckedChange = {
            checked = if (it) {
                CheckBoxState.Checked
            } else {
                CheckBoxState.Unchecked
            }
        }
    )
}