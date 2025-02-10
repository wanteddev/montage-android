package com.wanted.android.wanted.design.dialog

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object WantedModalContract {

    sealed class BottomSheetDialogType {
        data object Flexible : BottomSheetDialogType()

        data object FixedWrapContent : BottomSheetDialogType()
        data class Fixed(
            val height: Dp,
            val isFullScreen: Boolean = false
        ) : BottomSheetDialogType()

        data class FixedRatio(val ratio: Float) : BottomSheetDialogType()
    }

    enum class ModalSize(
        val contentPadding: Dp,
        val bottomBarPadding: Dp,
        val titleVerticalPadding: Dp,
        val titleHorizontalPadding: Dp,
    ) {
        Small(
            contentPadding = 20.dp,
            bottomBarPadding = 16.dp,
            titleVerticalPadding = 0.dp,
            titleHorizontalPadding = 0.dp
        ),
        Normal(
            contentPadding = 20.dp,
            bottomBarPadding = 20.dp,
            titleVerticalPadding = 4.dp,
            titleHorizontalPadding = 0.dp
        ),
        Medium(
            contentPadding = 24.dp,
            bottomBarPadding = 24.dp,
            titleVerticalPadding = 4.dp,
            titleHorizontalPadding = 0.dp
        ),
        Large(
            contentPadding = 32.dp,
            bottomBarPadding = 32.dp,
            titleVerticalPadding = 8.dp,
            titleHorizontalPadding = 4.dp
        ),
        Custom(
            contentPadding = 0.dp,
            bottomBarPadding = 0.dp,
            titleVerticalPadding = 0.dp,
            titleHorizontalPadding = 0.dp
        )
    }
}