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

    sealed class InteractionPadding(open val padding: Dp) {
        data class Default(
            val fillWidth: Boolean = false
        ) : InteractionPadding(if (fillWidth) 20.dp else 12.dp)
        data class Custom(override val padding: Dp = 0.dp) : InteractionPadding(padding)
    }
}