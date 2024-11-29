package com.wanted.android.wanted.design.list

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object WantedCellContract {
    enum class Padding(val value: Dp) {
        Padding0(0.dp),
        Padding8(8.dp),
        Padding12(12.dp),
        Padding16(16.dp),
        PaddingCustom(0.dp);
    }

    sealed class InteractionPadding(open val padding: Dp = 20.dp) {
        data object Default : InteractionPadding()
        data class Custom(override val padding: Dp = 0.dp) : InteractionPadding()
    }

    enum class ContentHeight(val height: Dp) {
        ContentHeight24(24.dp),
        ContentHeight40(40.dp),
        ContentHeight56(56.dp)
    }
}