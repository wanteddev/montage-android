package com.wanted.android.wanted.design.accordion

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object WantedAccordionContract {
    enum class HeaderVerticalPadding(val value: Dp) {
        Padding16(16.dp),
        Padding12(12.dp),
        Padding8(8.dp)
    }
}