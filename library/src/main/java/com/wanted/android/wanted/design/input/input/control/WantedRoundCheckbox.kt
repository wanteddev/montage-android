package com.wanted.android.wanted.design.input.input.control

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.material3.Surface
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    checked: Boolean,
    modifier: Modifier = Modifier,
    isIndeterminate: Boolean = false,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    size: CheckBoxSize = CheckBoxSize.Normal,
    onCheckedChange: (Boolean) -> Unit = {}
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
        value = toggleState,
        modifier = modifier.then(toggleableModifier),
        size = size
    )
}

@Composable
private fun RoundCheckboxImpl(
    modifier: Modifier,
    value: ToggleableState,
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
