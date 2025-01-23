package com.wanted.android.wanted.design.category

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object WantedCategoryContract {
    enum class Size(
        val verticalPadding: Dp,
        val horizontalSpacing: Dp,
    ) {
        Small(8.dp, 4.dp),
        Medium(8.dp, 6.dp),
        Large(10.dp, 8.dp),
        XLarge(10.dp, 10.dp)
    }
}