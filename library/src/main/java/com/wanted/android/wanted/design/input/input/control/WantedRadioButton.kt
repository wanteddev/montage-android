package com.wanted.android.wanted.design.input.input.control

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.google.android.material.radiobutton.MaterialRadioButton
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_43

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
internal fun WantedRadioButton(
    checked: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    tight: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    size: CheckBoxSize = CheckBoxSize.Normal,
    onCheckedChange: (Boolean) -> Unit = {},
) {

    WantedTouchArea(
        modifier = modifier,
        enabled = enabled,
        shape = CircleShape,
        horizontalPadding = if (tight) 6.dp else 4.dp,
        verticalPadding = 4.dp,
        interactionSource = interactionSource,
        content = {
            Box(
                modifier = Modifier
                    .height(height = if (size == CheckBoxSize.Small) 20.dp else 24.dp)
                    .width(
                        width = when {
                            tight -> if (size == CheckBoxSize.Small) 16.dp else 20.dp
                            else -> if (size == CheckBoxSize.Small) 20.dp else 24.dp
                        }
                    )
                    .padding(horizontal = if (tight) 0.dp else 2.dp)
                    .padding(vertical = 2.dp)
                    .clip(CircleShape)
                    .border(
                        width = when {
                            checked && size == CheckBoxSize.Small -> 4.5.dp
                            checked && size == CheckBoxSize.Normal -> 6.dp
                            else -> 1.5.dp
                        },
                        color = if (checked) {
                            if (enabled) {
                                DesignSystemTheme.colors.primaryNormal
                            } else {
                                DesignSystemTheme.colors.primaryNormal.copy(OPACITY_43)
                            }
                        } else {
                            if (enabled) {
                                DesignSystemTheme.colors.lineNormalNormal
                            } else {
                                DesignSystemTheme.colors.lineNormalNormal.copy(0.1f)
                            }
                        },
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
            }
        }
    ) {
        onCheckedChange(!checked)
    }
}


@DevicePreviews
@Composable
private fun WantedRadioButtonPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedRadioButton(
                    modifier = Modifier,
                    size = CheckBoxSize.Normal,
                    checked = false,
                    onCheckedChange = {}
                )

                WantedRadioButton(
                    modifier = Modifier,
                    size = CheckBoxSize.Normal,
                    checked = false,
                    enabled = false,
                    onCheckedChange = {}
                )

                WantedRadioButton(
                    modifier = Modifier,
                    size = CheckBoxSize.Normal,
                    checked = true,
                    onCheckedChange = {}
                )

                WantedRadioButton(
                    modifier = Modifier,
                    size = CheckBoxSize.Normal,
                    checked = true,
                    enabled = false,
                    onCheckedChange = {}
                )

                WantedRadioButton(
                    modifier = Modifier,
                    size = CheckBoxSize.Small,
                    checked = false,
                    onCheckedChange = {}
                )

                WantedRadioButton(
                    modifier = Modifier,
                    size = CheckBoxSize.Small,
                    checked = false,
                    enabled = false,
                    onCheckedChange = {}
                )

                WantedRadioButton(
                    modifier = Modifier,
                    size = CheckBoxSize.Small,
                    checked = true,
                    onCheckedChange = {}
                )

                WantedRadioButton(
                    modifier = Modifier,
                    size = CheckBoxSize.Small,
                    checked = true,
                    enabled = false,
                    onCheckedChange = {}
                )
            }
        }
    }
}