package com.wanted.android.wanted.design.presentation.modal

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * object WantedModalContract
 *
 * 모달(Modal) UI 컴포넌트에 사용되는 설정 값을 정의하는 객체입니다.
 *
 * 모달의 형태와 크기를 설정할 수 있는 `ModalType`, `ModalSize`를 포함합니다.
 */

object WantedModalContract {

    /**
     * sealed class ModalType
     *
     * 모달의 유형(Flexible, Fixed 등)을 정의하는 sealed 클래스입니다.
     *
     * 각 타입은 모달의 높이, 닫기 가능 여부, 시스템 BottomSheet 사용 여부 등을 설정합니다.
     */
    sealed class ModalType(
        open val isCloseable: Boolean = true,
        open val isSystemBottomSheet: Boolean = true
    ) {
        /**
         * data object Flexible
         *
         * 콘텐츠 크기에 따라 자동으로 조정되는 유동형 모달입니다.
         */
        data object Flexible : ModalType()

        /**
         * data class FixedWrapContent
         *
         * 콘텐츠 높이에 맞게 wrap되는 고정형 모달입니다.
         *
         * @param isCloseable `Boolean`: 닫기 가능 여부입니다.
         * @param isSystemBottomSheet `Boolean`: 시스템 BottomSheet 사용 여부입니다.
         */
        data class FixedWrapContent(
            override val isCloseable: Boolean = true,
            override val isSystemBottomSheet: Boolean = false
        ) : ModalType(isCloseable, isSystemBottomSheet)


        /**
         * data class Fixed
         *
         * 특정 높이를 갖는 고정형 모달입니다.
         *
         * @param height `Dp`: 지정할 모달 높이입니다.
         * @param isCloseable `Boolean`: 닫기 가능 여부입니다.
         * @param isSystemBottomSheet `Boolean`: 시스템 BottomSheet 사용 여부입니다.
         */
        data class Fixed(
            val height: Dp,
            override val isCloseable: Boolean = true,
            override val isSystemBottomSheet: Boolean = false
        ) : ModalType(isCloseable, isSystemBottomSheet)

        /**
         * data class FixedFullScreen
         *
         * 화면 전체를 덮는 고정형 모달입니다.
         *
         * @param isCloseable `Boolean`: 닫기 가능 여부입니다.
         * @param isSystemBottomSheet `Boolean`: 시스템 BottomSheet 사용 여부입니다.
         */
        data class FixedFullScreen(
            override val isCloseable: Boolean = true,
            override val isSystemBottomSheet: Boolean = false
        ) : ModalType(isCloseable, isSystemBottomSheet)

        /**
         * data class FixedRatio
         *
         * 화면의 일정 비율을 기준으로 높이가 설정되는 고정형 모달입니다.
         *
         * @param ratio `Float`: 0.0~1.0 사이의 높이 비율입니다.
         * @param isCloseable `Boolean`: 닫기 가능 여부입니다.
         * @param isSystemBottomSheet `Boolean`: 시스템 BottomSheet 사용 여부입니다.
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
     * 모달의 여백 및 내부 간격을 설정하는 enum 클래스입니다.
     *
     * 각 크기 옵션은 콘텐츠 패딩, 하단 바 간격, 타이틀 여백 등을 정의합니다.
     *
     * 포함된 값:
     * - Medium: 기본형
     * - Large: 넓은 여백
     * - XLarge: 확장된 여백
     *
     * Deprecated:
     * - Small
     * - Custom
     *
     * @property contentPadding `Dp`: 콘텐츠에 적용될 기본 패딩입니다.
     * @property bottomBarPadding `Dp`: 하단 바에 적용될 패딩입니다.
     * @property titleVerticalPadding `Dp`: 타이틀 영역의 세로 패딩입니다.
     * @property titleHorizontalPadding `Dp`: 타이틀 영역의 가로 패딩입니다.
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