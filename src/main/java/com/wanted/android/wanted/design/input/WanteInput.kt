package com.wanted.android.wanted.design.input

import android.content.res.Configuration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.element.CheckBoxSize
import com.wanted.android.wanted.design.element.CheckBoxState
import com.wanted.android.wanted.design.element.CheckBoxStyle
import com.wanted.android.wanted.design.element.WantedCheckBox
import com.wanted.android.wanted.design.input.WantedInputContract.WantedInputSize
import com.wanted.android.wanted.design.input.WantedInputContract.WantedInputType
import com.wanted.android.wanted.design.theme.DesignSystemTheme


@Composable
fun WantedInput(
    text: String,
    modifier: Modifier = Modifier,
    type: WantedInputType = WantedInputType.CheckBox,
    size: WantedInputSize = WantedInputSize.Normal,
    checkBoxState: CheckBoxState = CheckBoxState.Unchecked,
    bold: Boolean = false,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onCheckedChange: ((Boolean) -> Unit)
) {
    val density = LocalDensity.current
    val checkBoxInteractionSource: MutableInteractionSource =
        remember { MutableInteractionSource() }
    WantedInputLayout(
        modifier = modifier
            .clickOnceForDesignSystem(
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
            }
            .semantics { contentDescription = "WantedInput" },
        size = size,
        bold = bold,
        leadingIcon = {
            WantedCheckBox(
                modifier = Modifier.semantics {},
                size = if (size == WantedInputSize.Normal) {
                    CheckBoxSize.Normal
                } else {
                    CheckBoxSize.Small
                },
                style = when (type) {
                    WantedInputType.CheckBox -> CheckBoxStyle.CheckBox
                    WantedInputType.Radio -> CheckBoxStyle.Radio
                    WantedInputType.NestedCheckBox -> CheckBoxStyle.Check
                },
                checkState = checkBoxState,
                enabled = enabled,
                interactionSource = checkBoxInteractionSource,
                onCheckedChange = onCheckedChange

            )
        },
        text = { Text(text = text) }
    )
}

@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Preview(
    "foldableLight",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "ko",
    device = Devices.FOLDABLE
)
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
                WantedInput(WantedInputSize.Small, WantedInputType.NestedCheckBox)

                Text(text = "NestedCheckBox Normal")
                WantedInput(WantedInputSize.Normal, WantedInputType.NestedCheckBox)
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
        text = size.name,
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