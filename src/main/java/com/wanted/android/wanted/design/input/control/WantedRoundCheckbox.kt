package com.wanted.android.wanted.design.input.control

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import com.google.android.material.checkbox.MaterialCheckBox
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.theme.DesignSystemTheme

@Deprecated("Deprecated")
class WantedRoundCheckBox : MaterialCheckBox {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )
}

@Deprecated("Deprecated")
@Composable
fun WantedRoundCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    isIndeterminate: Boolean = false,
    onCheckedChange: ((Boolean) -> Unit),
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: CheckboxColors = CheckboxDefaults.colors(
        uncheckedColor = colorResource(id = R.color.line_normal_neutral),
        checkedColor = colorResource(id = R.color.primary_normal)
    ),
    size: CheckBoxSize = CheckBoxSize.Normal
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

    RoundCheckboxImpl(
        enabled = enabled,
        value = toggleState,
        modifier = modifier.then(toggleableModifier),
        colors = colors,
        size = size
    )
}

@Composable
private fun RoundCheckboxImpl(
    modifier: Modifier,
    enabled: Boolean,
    value: ToggleableState,
    colors: CheckboxColors,
    size: CheckBoxSize
) {
    Box(
        modifier = modifier
            .size(size = if (size == CheckBoxSize.Normal) 24.dp else 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(
                id =
                when (value) {
                    ToggleableState.On -> R.drawable.ic_normal_round_checkbox_checked_svg
                    ToggleableState.Indeterminate -> R.drawable.ic_normal_round_checkbox_partial_svg
                    else -> R.drawable.ic_normal_round_checkbox_unchecked_svg
                }
            ), contentDescription = "radio button"
        )
    }
}
//    val borderColor = colors.borderColor(enabled = enabled, state = value).value
//    val boxColor = colors.boxColor(enabled = enabled, state = value).value
//    val checkmarkColor = colors.checkmarkColor(state = value).value
//
//    val height = remember { mutableStateOf(0.dp) }
//    val width = remember { mutableStateOf(0.dp) }
//    val localDensity = LocalDensity.current
//
//
//    Box(
//        modifier = Modifier
//            .size(24.dp)
//            .defaultMinSize(18.dp)
//            .width(intrinsicSize = IntrinsicSize.Min)
//            .height(intrinsicSize = IntrinsicSize.Min)
//            .clip(CircleShape)
//            .border(
//                width = 2.dp,
//                color = borderColor,
//                CircleShape
//            )
//            .background(boxColor, CircleShape)
//            .then(modifier)
//            .onGloballyPositioned { coordinates ->
//                // Set column height using the LayoutCoordinates
//                height.value = with(localDensity) { coordinates.size.height.toDp() }
//                width.value = with(localDensity) { coordinates.size.width.toDp() }
//            },
//        contentAlignment = Alignment.Center
//    ) {
//        val paddingValue = if (value == ToggleableState.Indeterminate) {
//            5.dp
//        } else {
//            6.dp
//        }
//
//        Image(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValue),
//            painter = painterResource(
//                if (value == ToggleableState.Indeterminate) {
//                    R.drawable.icon_checkbox_indeterminate
//                } else {
//                    R.drawable.icon_checkbox_checked
//                }
//            ),
//            contentDescription = "checkBox_check",
//            colorFilter = ColorFilter.tint(
//                color = checkmarkColor
//            )
//        )
//    }
//}

@DevicePreviews
@Composable
private fun CertificationAuthInputScreenPreview() {
    DesignSystemTheme {
        Surface {
            Column {
                WantedRoundCheckBox(
                    modifier = Modifier,
                    checked = true,
                    onCheckedChange = {}
                )

                Spacer(modifier = Modifier.size(10.dp))

                WantedRoundCheckBox(
                    modifier = Modifier,
                    isIndeterminate = true,
                    checked = true,
                    onCheckedChange = {}
                )
                Spacer(modifier = Modifier.size(10.dp))

                WantedRoundCheckBox(
                    modifier = Modifier,
                    checked = false,
                    onCheckedChange = {}
                )
            }
        }
    }
}
