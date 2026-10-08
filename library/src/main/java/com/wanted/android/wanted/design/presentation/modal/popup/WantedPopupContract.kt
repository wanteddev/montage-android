package com.wanted.android.wanted.design.presentation.modal.popup

import androidx.compose.ui.unit.Dp

/**
 * object WantedPopupContract
 *
 * Popup 컴포넌트에서 사용하는 설정값을 정의하는 객체입니다.
 */
object WantedPopupContract {

    /**
     * sealed class Resize
     *
     * Popup 의 높이 결정 방식을 정의하는 sealed 클래스입니다.
     * Hug, Fixed 두 가지를 제공합니다.
     */
    sealed class Resize {

        /**
         * data object Hug
         *
         * 콘텐츠 높이에 맞춰 Popup 높이가 결정됩니다. 기본값입니다.
         */
        data object Hug : Resize()

        /**
         * data class Fixed
         *
         * 지정한 높이로 Popup 높이가 고정됩니다.
         *
         * @property height Dp: Popup 의 높이입니다.
         */
        data class Fixed(val height: Dp) : Resize()
    }
}
