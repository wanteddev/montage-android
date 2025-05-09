package com.wanted.android.wanted.design.presentation.modal

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object WantedModalContract {

    /**
     * 모달의 유형(Flexible, Fixed 등)을 정의하는 sealed 클래스입니다.
     *
     * 각 하위 타입은 모달의 높이 또는 비율, 시스템 사용 여부 등을 설정합니다.
     */
    sealed class ModalType(
        open val isCloseable: Boolean = true,
        open val isSystemBottomSheet: Boolean = true
    ) {
        /**
         * 기본형 Flexible 모달입니다. 크기가 콘텐츠에 따라 유동적으로 조정됩니다.
         */
        data object Flexible : ModalType()

        /**
         * 콘텐츠 높이에 맞게 wrap되는 고정형 모달입니다.
         *
         * @param isCloseable 닫기 가능 여부입니다.
         * @param isSystemBottomSheet 시스템 BottomSheet 사용 여부입니다.
         */
        data class FixedWrapContent(
            override val isCloseable: Boolean = true,
            override val isSystemBottomSheet: Boolean = false
        ) : ModalType(isCloseable, isSystemBottomSheet)


        /**
         * 특정 높이를 갖는 고정형 모달입니다.
         *
         * @param height 지정할 모달 높이입니다.
         * @param isCloseable 닫기 가능 여부입니다.
         * @param isSystemBottomSheet 시스템 BottomSheet 사용 여부입니다.
         */
        data class Fixed(
            val height: Dp,
            override val isCloseable: Boolean = true,
            override val isSystemBottomSheet: Boolean = false
        ) : ModalType(isCloseable, isSystemBottomSheet)

        /**
         * 전체 화면을 채우는 고정형 모달입니다.
         *
         * @param isCloseable 닫기 가능 여부입니다.
         * @param isSystemBottomSheet 시스템 BottomSheet 사용 여부입니다.
         */
        data class FixedFullScreen(
            override val isCloseable: Boolean = true,
            override val isSystemBottomSheet: Boolean = false
        ) : ModalType(isCloseable, isSystemBottomSheet)

        /**
         * 화면 비율 기반으로 높이를 설정하는 고정형 모달입니다.
         *
         * @param ratio 높이 비율입니다. (0.0 ~ 1.0)
         * @param isCloseable 닫기 가능 여부입니다.
         * @param isSystemBottomSheet 시스템 BottomSheet 사용 여부입니다.
         */
        data class FixedRatio(
            val ratio: Float,
            override val isCloseable: Boolean = true,
            override val isSystemBottomSheet: Boolean = false
        ) : ModalType(isCloseable, isSystemBottomSheet)
    }

    /**
     * 모달의 크기(Padding, 여백 등)를 정의하는 enum 클래스입니다.
     *
     * 각 크기별로 콘텐츠 패딩, 버튼 패딩, 타이틀 여백 등을 조절할 수 있습니다.
     *
     * @property contentPadding 콘텐츠에 적용될 기본 패딩입니다.
     * @property bottomBarPadding 하단 바에 적용될 패딩입니다.
     * @property titleVerticalPadding 타이틀 영역의 세로 패딩입니다.
     * @property titleHorizontalPadding 타이틀 영역의 가로 패딩입니다.
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
        @Deprecated("")
        Custom(
            contentPadding = 0.dp,
            bottomBarPadding = 0.dp,
            titleVerticalPadding = 0.dp,
            titleHorizontalPadding = 0.dp
        )
    }

    internal const val MAX_MODAL_SIZE = 760
}