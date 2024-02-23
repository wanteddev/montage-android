package com.wanted.android.wanted.design.element

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.google.android.material.radiobutton.MaterialRadioButton
import com.wanted.android.designsystem.R

enum class RadioButtonSize {
    NORMAL, SMALL
}

class WantedRadioButton : MaterialRadioButton {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )
}

@Composable
fun WantedRadioButton(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit),
    enabled: Boolean = true,
    size: RadioButtonSize = RadioButtonSize.NORMAL
) {
    val checkableModifier = Modifier.toggleable(
        value = checked,
        enabled = enabled,
        role = Role.RadioButton,
        onValueChange = onCheckedChange,
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
    )

    RadioButtonImpl(
        modifier = checkableModifier,
        checked = checked,
        size = size
    )
}

@Composable
private fun RadioButtonImpl(
    modifier: Modifier,
    checked: Boolean,
    size: RadioButtonSize
) {
    Box(
        modifier = modifier
            .size(size = if (size == RadioButtonSize.NORMAL) 24.dp else 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(
                id =
                if (checked) {
                    R.drawable.ic_normal_radio_checked_svg
                } else {
                    R.drawable.ic_normal_radio_unchecked_svg
                }
            ), contentDescription = "radio button"
        )
    }
}