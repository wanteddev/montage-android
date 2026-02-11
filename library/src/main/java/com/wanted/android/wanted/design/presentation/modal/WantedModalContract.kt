package com.wanted.android.wanted.design.presentation.modal

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * object WantedModalContract
 *
 * Modal 컴포넌트에서 사용하는 설정값을 정의하는 객체입니다.
 *
 */
object WantedModalContract {

    /**
     * sealed class ModalType
     *
     * Modal의 형태를 정의하는 sealed 클래스입니다.
     * Flexible, FixedWrapContent, Fixed, FixedFullScreen, FixedRatio 형태를 제공합니다.
     */
    sealed class ModalType(
        open val isCloseable: Boolean = true,
        open val isSystemBottomSheet: Boolean = true
    ) {
        /**
         * data object Flexible
         *
         * 콘텐츠 크기에 따라 자동으로 조정되는 유동형 Modal 입니다.
         */
        data object Flexible : ModalType()

        /**
         * data class FixedWrapContent
         *
         * 콘텐츠 높이에 맞게 조정되는 고정형 Modal 입니다.
         *
         * @property isCloseable Boolean: 닫기 가능 여부입니다.
         * @property isSystemBottomSheet Boolean: 시스템 BottomSheet 사용 여부입니다.
         */
        data class FixedWrapContent(
            override val isCloseable: Boolean = true,
            override val isSystemBottomSheet: Boolean = false
        ) : ModalType(isCloseable, isSystemBottomSheet)

        /**
         * data class Fixed
         *
         * 특정 높이를 갖는 고정형 Modal 입니다.
         *
         * @property height Dp: Modal 의 높이입니다.
         * @property isCloseable Boolean: 닫기 가능 여부입니다.
         * @property isSystemBottomSheet Boolean: 시스템 BottomSheet 사용 여부입니다.
         */
        data class Fixed(
            val height: Dp,
            override val isCloseable: Boolean = true,
            override val isSystemBottomSheet: Boolean = false
        ) : ModalType(isCloseable, isSystemBottomSheet)

        /**
         * data class FixedFullScreen
         *
         * 화면 전체를 덮는 고정형 Modal 입니다.
         *
         * @property isCloseable Boolean: 닫기 가능 여부입니다.
         * @property isSystemBottomSheet Boolean: 시스템 BottomSheet 사용 여부입니다.
         */
        data class FixedFullScreen(
            override val isCloseable: Boolean = true,
            override val isSystemBottomSheet: Boolean = false
        ) : ModalType(isCloseable, isSystemBottomSheet)

        /**
         * data class FixedRatio
         *
         * 화면 비율을 기준으로 높이가 설정되는 고정형 Modal입니다.
         *
         * @property ratio Float: 0.0 ~ 1.0 사이의 높이 비율입니다.
         * @property isCloseable Boolean: 닫기 가능 여부입니다.
         * @property isSystemBottomSheet Boolean: 시스템 BottomSheet 사용 여부입니다.
         */
        data class FixedRatio(
            val ratio: Float,
            override val isCloseable: Boolean = true,
            override val isSystemBottomSheet: Boolean = false
        ) : ModalType(isCloseable, isSystemBottomSheet)
    }

    /**
     * enum class ModalSize
     *
     * Modal의 여백 및 패딩을 정의하는 enum 클래스입니다.
     *
     * - Small: 작은 크기의 Modal 입니다.
     * - Medium: 중간 크기의 Modal 입니다.
     * - Large: 큰 크기의 Modal 입니다.
     * - XLarge: 매우 큰 크기의 Modal 입니다.
     * - Custom: 커스텀 크기의 Modal 입니다. 모든 패딩이 0dp로 설정되어 사용자가 직접 정의 할 수 있습니다.
     */
    enum class ModalSize(
        val contentPadding: Dp,
        val bottomBarPadding: Dp,
        val titleVerticalPadding: Dp,
        val titleHorizontalPadding: Dp,
    ) {
        @Deprecated("")
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

    internal const val MAX_MODAL_SIZE = 760
}