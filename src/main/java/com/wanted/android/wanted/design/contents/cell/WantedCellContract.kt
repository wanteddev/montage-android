package com.wanted.android.wanted.design.contents.cell

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object WantedCellContract {
    enum class VerticalPadding(val value: Dp) {
        None(0.dp),
        Small(8.dp),
        Medium(12.dp),
        Large(16.dp)
    }

    sealed class InteractionPadding(open val padding: Dp = 20.dp) {
        data object Default : InteractionPadding()
        data class Custom(override val padding: Dp = 0.dp) : InteractionPadding()
    }
}