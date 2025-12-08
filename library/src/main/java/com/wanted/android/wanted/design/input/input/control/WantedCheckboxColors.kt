package com.wanted.android.wanted.design.input.input.control

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.CheckboxColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState

@Composable
internal fun CheckboxColors.borderColor(enabled: Boolean, state: ToggleableState): State<Color> {
    val target = if (enabled) {
        when (state) {
            ToggleableState.On, ToggleableState.Indeterminate -> checkedBorderColor
            ToggleableState.Off -> uncheckedBorderColor
        }
    } else {
        when (state) {
            ToggleableState.Indeterminate -> disabledIndeterminateBorderColor
            ToggleableState.On -> disabledBorderColor
            ToggleableState.Off -> disabledUncheckedBorderColor
        }
    }

    // If not enabled 'snap' to the disabled state, as there should be no animations between
    // enabled / disabled.
    return if (enabled) {
        val duration = if (state == ToggleableState.Off) BoxOutDuration else BoxInDuration
        animateColorAsState(target, tween(durationMillis = duration), label = "")
    } else {
        rememberUpdatedState(target)
    }
}

@Composable
internal fun CheckboxColors.boxColor(enabled: Boolean, state: ToggleableState): State<Color> {
    val target = if (enabled) {
        when (state) {
            ToggleableState.On, ToggleableState.Indeterminate -> checkedBoxColor
            ToggleableState.Off -> uncheckedBoxColor
        }
    } else {
        when (state) {
            ToggleableState.On -> disabledCheckedBoxColor
            ToggleableState.Indeterminate -> disabledIndeterminateBoxColor
            ToggleableState.Off -> disabledUncheckedBoxColor
        }
    }

    // If not enabled 'snap' to the disabled state, as there should be no animations between
    // enabled / disabled.
    return if (enabled) {
        val duration = if (state == ToggleableState.Off) BoxOutDuration else BoxInDuration
        animateColorAsState(target, tween(durationMillis = duration), label = "")
    } else {
        rememberUpdatedState(target)
    }
}

@Composable
internal fun CheckboxColors.checkmarkColor(state: ToggleableState): State<Color> {
    val target = if (state == ToggleableState.Off) {
        uncheckedCheckmarkColor
    } else {
        checkedCheckmarkColor
    }

    val duration = if (state == ToggleableState.Off) BoxOutDuration else BoxInDuration
    return animateColorAsState(target, tween(durationMillis = duration), label = "")
}

private const val BoxInDuration = 50
private const val BoxOutDuration = 100