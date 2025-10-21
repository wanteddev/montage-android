package com.wanted.android.wanted.design.input.search

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object WantedSearchFieldConstant {
    sealed class Size(
        open val padding: Dp,
        open val minHeight: Dp
    ) {
        data class Small(
            override val padding: Dp = 8.dp,
            override val minHeight: Dp = 40.dp
        ) : Size(padding, minHeight)

        data class Medium(
            override val padding: Dp = 12.dp,
            override val minHeight: Dp = 48.dp
        ) : Size(padding, minHeight)

        data class Custom(
            override val padding: Dp = 12.dp,
            override val minHeight: Dp = 48.dp
        ) : Size(padding, minHeight)
    }
}