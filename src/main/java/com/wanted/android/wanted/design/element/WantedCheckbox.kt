package com.wanted.android.wanted.design.element

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import com.google.android.material.checkbox.MaterialCheckBox
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_43

enum class CheckBoxSize {
    Normal, Small
}

enum class CheckBoxStyle {
    CheckBox,
    RoundCheckBox,
    Check,
    Radio,
    Switch
}

enum class CheckBoxState {
    Unchecked,
    Checked,
    Indeterminate
}


class WantedCheckBox : MaterialCheckBox {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )
}

@Composable
fun WantedCheckBox(
    modifier: Modifier,
    size: CheckBoxSize,
    style: CheckBoxStyle = CheckBoxStyle.CheckBox,
    checkState: CheckBoxState = CheckBoxState.Unchecked,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onCheckedChange: ((Boolean) -> Unit)
) {
    when (style) {
        CheckBoxStyle.CheckBox -> {
            WantedCheckBoxImpl(
                modifier = modifier,
                size = size,
                shape = RoundedCornerShape(5.dp),
                checkState = checkState,
                enabled = enabled,
                interactionSource = interactionSource,
                onCheckedChange = onCheckedChange
            )
        }

        CheckBoxStyle.RoundCheckBox -> {
            WantedCheckBoxImpl(
                modifier = modifier,
                size = size,
                shape = CircleShape,
                checkState = checkState,
                enabled = enabled,
                interactionSource = interactionSource,
                onCheckedChange = onCheckedChange
            )
        }

        CheckBoxStyle.Check -> {
            WantedCheckMark(
                modifier = modifier,
                size = size,
                checked = checkState != CheckBoxState.Unchecked,
                enabled = enabled,
                interactionSource = interactionSource,
                onCheckedChange = onCheckedChange
            )
        }

        CheckBoxStyle.Radio -> {
            WantedRadioButton(
                modifier = modifier,
                size = size,
                checked = checkState != CheckBoxState.Unchecked,
                enabled = enabled,
                interactionSource = interactionSource,
                onCheckedChange = onCheckedChange
            )
        }

        CheckBoxStyle.Switch -> {
            WantedSwitch(
                modifier = modifier,
                size = size,
                checked = checkState != CheckBoxState.Unchecked,
                enabled = enabled,
                interactionSource = interactionSource,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Composable
private fun WantedCheckBoxImpl(
    modifier: Modifier,
    size: CheckBoxSize,
    shape: Shape,
    checkState: CheckBoxState,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onCheckedChange: ((Boolean) -> Unit)
) {
    WantedTouchArea(
        modifier = modifier,
        enabled = enabled,
        shape = CircleShape,
        horizontalPadding = 4.dp,
        verticalPadding = 4.dp,
        interactionSource = interactionSource,
        content = {
            Box(
                modifier = Modifier
                    .size(if (size == CheckBoxSize.Small) 20.dp else 24.dp)
                    .padding(if (size == CheckBoxSize.Small) 2.dp else 3.dp)
                    .clip(shape)
                    .border(
                        width = 1.5.dp,
                        color = when {
                            enabled && checkState == CheckBoxState.Unchecked -> {
                                colorResource(id = R.color.line_normal_normal)
                            }

                            !enabled && checkState == CheckBoxState.Unchecked -> {
                                colorResource(id = R.color.line_normal_normal).copy(0.1f)
                            }

                            else -> colorResource(id = R.color.transparent)
                        },
                        shape = shape
                    )
                    .background(
                        color = when {
                            checkState == CheckBoxState.Unchecked -> colorResource(id = R.color.transparent)
                            enabled -> colorResource(id = R.color.primary_normal)
                            else -> colorResource(id = R.color.primary_normal).copy(OPACITY_43)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (checkState != CheckBoxState.Unchecked) {
                    Image(
                        modifier = Modifier
                            .size(if (size == CheckBoxSize.Small) 16.dp else 18.dp)
                            .padding(2.dp),
                        painter = painterResource(
                            if (checkState == CheckBoxState.Indeterminate) {
                                R.drawable.icon_normal_line_horizontal
                            } else {
                                R.drawable.icon_normal_check_thick
                            }
                        ),
                        contentDescription = "checkBox_check",
                        colorFilter = ColorFilter.tint(
                            color = colorResource(id = R.color.static_white)
                        )
                    )
                }
            }
        }
    ) {
        onCheckedChange(checkState == CheckBoxState.Unchecked)
    }
}

@Deprecated(
    "Use \n" +
        "WantedCheckBox(modifier: Modifier,\n" +
        "    size: CheckBoxSize,\n" +
        "    style: CheckBoxStyle = CheckBoxStyle.CheckBox,\n" +
        "    checkState: CheckBoxState = CheckBoxState.Unchecked,\n" +
        "    enabled: Boolean = true,\n" +
        "    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },\n" +
        "    onCheckedChange: ((Boolean) -> Unit))"
)
@Composable
fun WantedCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    isIndeterminate: Boolean = false,
    onCheckedChange: ((Boolean) -> Unit),
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: CheckboxColors = CheckboxDefaults.colors(
        uncheckedColor = colorResource(id = R.color.line_normal_neutral),
        checkedColor = colorResource(id = R.color.primary_normal),
        checkmarkColor = colorResource(id = R.color.static_white)
    )
) {
    val toggleState = when {
        !checked -> ToggleableState.Off
        isIndeterminate -> ToggleableState.Indeterminate
        else -> ToggleableState.On
    }

    val toggleableModifier = Modifier.triStateToggleable(
        state = toggleState,
        onClick = { onCheckedChange(!checked) },
        enabled = enabled,
        role = Role.Checkbox,
        interactionSource = interactionSource,
        indication = ripple(bounded = false)
    )

    CheckboxImpl(
        enabled = enabled,
        value = toggleState,
        modifier = modifier.then(toggleableModifier),
        colors = colors
    )
}


@Composable
private fun CheckboxImpl(
    modifier: Modifier,
    enabled: Boolean,
    value: ToggleableState,
    colors: CheckboxColors
) {
    val borderColor = colors.borderColor(enabled = enabled, state = value).value
    val boxColor = colors.boxColor(enabled = enabled, state = value).value
    val checkmarkColor = colors.checkmarkColor(state = value).value

    Box(
        modifier = Modifier
            .padding(3.dp)
            .defaultMinSize(18.dp)
            .width(intrinsicSize = IntrinsicSize.Min)
            .height(intrinsicSize = IntrinsicSize.Min)
            .clip(RoundedCornerShape(3.dp))
            .then(modifier)
            .border(
                width = 2.dp,
                color = borderColor,
                RoundedCornerShape(3.dp)
            )
            .background(boxColor),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(3.dp),
            painter = painterResource(
                if (value == ToggleableState.Indeterminate) {
                    R.drawable.icon_checkbox_indeterminate
                } else {
                    R.drawable.icon_checkbox_checked
                }
            ),
            contentDescription = "checkBox_check",
            colorFilter = ColorFilter.tint(
                color = checkmarkColor
            )
        )
    }
}

@DevicePreviews
@Composable
private fun CertificationAuthInputScreenPreview() {
    DesignSystemTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                CheckBox(CheckBoxStyle.CheckBox)
                CheckBox(CheckBoxStyle.RoundCheckBox)
                CheckBox(CheckBoxStyle.Check)
                CheckBox(CheckBoxStyle.Radio)
                CheckBox(CheckBoxStyle.Switch)
            }
        }
    }
}

@Composable
private fun CheckBox(
    style: CheckBoxStyle = CheckBoxStyle.CheckBox,
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            WantedCheckBox(
                modifier = Modifier,
                size = CheckBoxSize.Small,
                style = style,
                checkState = CheckBoxState.Unchecked,
                onCheckedChange = {

                }
            )

            WantedCheckBox(
                modifier = Modifier,
                size = CheckBoxSize.Small,
                style = style,
                checkState = CheckBoxState.Checked,
                onCheckedChange = {

                }
            )
            WantedCheckBox(
                modifier = Modifier,
                size = CheckBoxSize.Small,
                style = style,
                checkState = CheckBoxState.Indeterminate,
                onCheckedChange = {

                }
            )

            WantedCheckBox(
                modifier = Modifier,
                size = CheckBoxSize.Normal,
                style = style,
                checkState = CheckBoxState.Unchecked,
                onCheckedChange = {

                }
            )

            WantedCheckBox(
                modifier = Modifier,
                size = CheckBoxSize.Normal,
                style = style,
                checkState = CheckBoxState.Checked,
                onCheckedChange = {

                }
            )
            WantedCheckBox(
                modifier = Modifier,
                size = CheckBoxSize.Normal,
                style = style,
                checkState = CheckBoxState.Indeterminate,
                onCheckedChange = {

                }
            )

        }

        // ------- enable
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            WantedCheckBox(
                modifier = Modifier,
                size = CheckBoxSize.Small,
                style = style,
                enabled = false,
                checkState = CheckBoxState.Unchecked,
                onCheckedChange = {

                }
            )

            WantedCheckBox(
                modifier = Modifier,
                size = CheckBoxSize.Small,
                style = style,
                enabled = false,
                checkState = CheckBoxState.Checked,
                onCheckedChange = {

                }
            )
            WantedCheckBox(
                modifier = Modifier,
                size = CheckBoxSize.Small,
                style = style,
                enabled = false,
                checkState = CheckBoxState.Indeterminate,
                onCheckedChange = {

                }
            )

            WantedCheckBox(
                modifier = Modifier,
                size = CheckBoxSize.Normal,
                style = style,
                enabled = false,
                checkState = CheckBoxState.Unchecked,
                onCheckedChange = {

                }
            )

            WantedCheckBox(
                modifier = Modifier,
                size = CheckBoxSize.Normal,
                style = style,
                enabled = false,
                checkState = CheckBoxState.Checked,
                onCheckedChange = {

                }
            )
            WantedCheckBox(
                modifier = Modifier,
                size = CheckBoxSize.Normal,
                style = style,
                enabled = false,
                checkState = CheckBoxState.Indeterminate,
                onCheckedChange = {

                }
            )

        }
    }
}
