package com.wanted.android.wanted.design.presentation.modal

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object WantedModalContract {

    sealed class BottomSheetDialogType(
        open val isCloseable: Boolean = true,
        open val isSystemBottomSheet: Boolean = true
    ) {
        data object Flexible : BottomSheetDialogType()

        /**
         * hug
         */
        data class FixedWrapContent(
            override val isCloseable: Boolean = true,
            override val isSystemBottomSheet: Boolean = false
        ) : BottomSheetDialogType(isCloseable, isSystemBottomSheet)

        data class Fixed(
            val height: Dp,
            override val isCloseable: Boolean = true,
            override val isSystemBottomSheet: Boolean = false
        ) : BottomSheetDialogType(isCloseable, isSystemBottomSheet)

        /**
         * fill
         */
        data class FixedFullScreen(
            override val isCloseable: Boolean = true,
            override val isSystemBottomSheet: Boolean = false
        ) : BottomSheetDialogType(isCloseable, isSystemBottomSheet)

        data class FixedRatio(
            val ratio: Float,
            override val isCloseable: Boolean = true,
            override val isSystemBottomSheet: Boolean = false
        ) : BottomSheetDialogType(isCloseable, isSystemBottomSheet)
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
        Medium(
            contentPadding = 20.dp,
            bottomBarPadding = 20.dp,
            titleVerticalPadding = 4.dp,
            titleHorizontalPadding = 0.dp
        ),
        Large(
            contentPadding = 24.dp,
            bottomBarPadding = 24.dp,
            titleVerticalPadding = 4.dp,
            titleHorizontalPadding = 0.dp
        ),
        XLarge(
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