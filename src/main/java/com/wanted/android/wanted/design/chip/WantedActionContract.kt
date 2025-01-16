package com.wanted.android.wanted.design.chip

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.chip.WantedActionContract.ChipActionSize

object WantedActionContract {
    enum class ChipActionVariant {
        FILLED, OUTLINED
    }

    enum class ChipActionSize {
        LARGE, NORMAL, SMALL, XSMALL
    }
}


internal fun Modifier.actionChipPadding(
    size: ChipActionSize
): Modifier = composed {
    when (size) {
        ChipActionSize.LARGE -> {
            this.then(padding(vertical = 9.dp, horizontal = 12.dp))
        }

        ChipActionSize.NORMAL -> {
            this.then(padding(vertical = 7.dp, horizontal = 11.dp))
        }

        ChipActionSize.SMALL -> {
            this.then(padding(vertical = 6.dp, horizontal = 8.dp))
        }

        ChipActionSize.XSMALL -> {
            this.then(padding(vertical = 4.dp, horizontal = 7.dp))
        }
    }
}

internal fun Modifier.filterChipPadding(
    size: ChipActionSize
): Modifier = composed {
    when (size) {
        ChipActionSize.LARGE -> {
            this.then(padding(vertical = 9.dp).padding(start = 12.dp, end = 10.dp))
        }

        ChipActionSize.NORMAL -> {
            this.then(padding(vertical = 7.dp).padding(start = 11.dp, end = 9.dp))
        }

        ChipActionSize.SMALL -> {
            this.then(padding(vertical = 6.dp).padding(start = 8.dp, end = 6.dp))
        }

        ChipActionSize.XSMALL -> {
            this.then(padding(vertical = 4.dp).padding(start = 7.dp, end = 5.dp))
        }
    }
}

internal fun Modifier.actionChipIconSize(
    size: ChipActionSize
): Modifier = composed {
    val modifier = when (size) {
        ChipActionSize.LARGE -> Modifier.size(16.dp)
        ChipActionSize.NORMAL -> Modifier.size(14.dp)
        ChipActionSize.SMALL -> Modifier.size(14.dp)
        ChipActionSize.XSMALL -> Modifier.size(12.dp)
    }
    this.then(modifier)
}

internal fun Modifier.filterChipIconSize(
    size: ChipActionSize
): Modifier = composed {
    val modifier = when (size) {
        ChipActionSize.LARGE -> Modifier.size(16.dp)
        ChipActionSize.NORMAL -> Modifier.size(16.dp)
        ChipActionSize.SMALL -> Modifier.size(14.dp)
        ChipActionSize.XSMALL -> Modifier.size(12.dp)
    }
    this.then(modifier)
}

internal fun Modifier.actionChipTextPadding(
    size: ChipActionSize
): Modifier = composed {
    val modifier = when (size) {
        ChipActionSize.LARGE -> Modifier.padding(horizontal = 2.dp)
        ChipActionSize.NORMAL -> Modifier.padding(horizontal = 2.dp)
        ChipActionSize.SMALL -> Modifier.padding(horizontal = 2.dp)
        ChipActionSize.XSMALL -> Modifier.padding(horizontal = 1.dp)
    }
    this.then(modifier)
}


@Composable
internal fun getChipRadius(size: ChipActionSize) = when (size) {
    ChipActionSize.XSMALL -> 6.dp
    ChipActionSize.SMALL -> 8.dp
    ChipActionSize.NORMAL -> 10.dp
    ChipActionSize.LARGE -> 10.dp
}

@Composable
internal fun getActionChipHorizontalArrangement(size: ChipActionSize) = when (size) {
    ChipActionSize.XSMALL -> 2.dp
    ChipActionSize.SMALL -> 2.dp
    ChipActionSize.NORMAL -> 3.dp
    ChipActionSize.LARGE -> 3.dp
}

@Composable
internal fun getFilterChipHorizontalArrangement(size: ChipActionSize) = when (size) {
    ChipActionSize.XSMALL -> 1.dp
    ChipActionSize.SMALL -> 1.dp
    ChipActionSize.NORMAL -> 2.dp
    ChipActionSize.LARGE -> 2.dp
}


