package com.wanted.android.wanted.design.actions.chip.config

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

open class WantedChipDefault(
    open val isActive: Boolean = false,
    open val isEnable: Boolean = true,
    open val iconColor: Color,
    open val backgroundColor: Color,
    open val borderColor: Color,
    open val textStyle: TextStyle
)