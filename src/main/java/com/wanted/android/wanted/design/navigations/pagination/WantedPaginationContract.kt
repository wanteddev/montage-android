package com.wanted.android.wanted.design.navigations.pagination

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object WantedPaginationContract {
    enum class WantedDotIndicatorSize {
        Small,
        Normal
    }

    enum class WantedDotIndicatorType {
        Normal,
        White
    }

    internal enum class IndicatorDotSize {
        Max,
        Mid,
        Min,
        Zero
    }

    enum class WantedPageCounterSize(
        val paddingHorizontal: Dp,
        val paddingVertical: Dp,
        val space: Dp,
    ) {
        Small(paddingHorizontal = 10.dp, paddingVertical = 4.dp, space = 3.dp),
        Normal(paddingHorizontal = 12.dp, paddingVertical = 6.dp, space = 4.dp)
    }
}