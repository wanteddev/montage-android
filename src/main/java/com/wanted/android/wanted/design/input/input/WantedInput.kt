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
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.clickOnce
import com.wanted.android.wanted.design.input.control.CheckBoxSize
import com.wanted.android.wanted.design.input.control.CheckBoxState
import com.wanted.android.wanted.design.input.control.CheckBoxStyle
import com.wanted.android.wanted.design.input.control.WantedCheckBox
import com.wanted.android.wanted.design.input.input.WantedInputContract.WantedInputSize
import com.wanted.android.wanted.design.input.input.WantedInputContract.WantedInputType
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
fun WantedInput(
    text: String,
    modifier: Modifier = Modifier,
    type: WantedInputType = WantedInputType.CheckBox,
    size: WantedInputSize = WantedInputSize.Normal,
    checkBoxState: CheckBoxState = CheckBoxState.Unchecked,
    bold: Boolean = false,
    enabled: Boolean = true,
    textStyle: TextStyle = WantedTextStyle(
        colorRes = if (enabled) R.color.label_normal else R.color.label_disable,
        style = if (size == WantedInputSize.Normal) {
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
        }
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onCheckedChange: ((Boolean) -> Unit)
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
                        (if (size == WantedInputSize.Normal) 16.dp else 12.dp).toPx()
                    }

                    val press = PressInteraction.Press(Offset(offset, offset))
                    checkBoxInteractionSource.tryEmit(press)
                    checkBoxInteractionSource.tryEmit(PressInteraction.Release(press))
                },
            size = size,
            leadingIcon = {
                WantedCheckBox(
                    modifier = Modifier,
                    size = if (size == WantedInputSize.Normal) {
                        CheckBoxSize.Normal
                    } else {
                        CheckBoxSize.Small
                    },
                    style = when (type) {
                        WantedInputType.CheckBox -> CheckBoxStyle.CheckBox
                        WantedInputType.Radio -> CheckBoxStyle.Radio
                        WantedInputType.CheckMark -> CheckBoxStyle.Check
                    },
                    checkState = checkBoxState,
                    enabled = enabled,
                    interactionSource = checkBoxInteractionSource,
                    onCheckedChange = onCheckedChange

                )
            },
            text = {
                Text(text = text)
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
                WantedInput(WantedInputSize.Small, WantedInputType.CheckBox)

                Text(text = "CheckBox Normal")
                WantedInput(WantedInputSize.Normal, WantedInputType.CheckBox)

                Text(text = "Radio Small")
                WantedInput(WantedInputSize.Small, WantedInputType.Radio)

                Text(text = "Radio Normal")
                WantedInput(WantedInputSize.Normal, WantedInputType.Radio)

                Text(text = "NestedCheckBox Small")
                WantedInput(WantedInputSize.Small, WantedInputType.CheckMark)

                Text(text = "NestedCheckBox Normal")
                WantedInput(WantedInputSize.Normal, WantedInputType.CheckMark)
            }
        }
    }
}

@Composable
private fun WantedInput(
    size: WantedInputSize,
    type: WantedInputType
) {
    var checked by remember { mutableStateOf(CheckBoxState.Unchecked) }

    WantedInput(
        modifier = Modifier,
        text = "${size.name}\n${size.name}",
        size = size,
        checkBoxState = checked,
        bold = false,
        enabled = true,
        type = type,
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
        text = size.name,
        size = size,
        checkBoxState = checked,
        bold = false,
        enabled = false,
        type = type,
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
        text = size.name,
        size = size,
        checkBoxState = checked,
        bold = true,
        enabled = true,
        type = type,
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
        text = size.name,
        size = size,
        checkBoxState = checked,
        bold = true,
        enabled = false,
        type = type,
        onCheckedChange = {
            checked = if (it) {
                CheckBoxState.Checked
            } else {
                CheckBoxState.Unchecked
            }
        }
    )
}