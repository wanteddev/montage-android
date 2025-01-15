package com.wanted.android.wanted.design.category

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object WantedCategoryContract {
    enum class Size(
        val verticalPadding: Dp,
        val horizontalSpacing: Dp,
    ) {
        XSmall(8.dp, 4.dp),
        Small(8.dp, 6.dp),
        Medium(10.dp, 7.dp),
        Large(10.dp, 9.dp)
    }
}