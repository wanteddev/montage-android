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
         * 콘텐츠 크기에 따라 자동으로 조정되는 유동형 Modal 입니다. 항상 시스템 BottomSheet 로 그려지고 닫을 수 있습니다. 부분 펼침(중간 높이)을 허용하지만, 콘텐츠가 충분히 높을 때만 그 상태가 생기며 짧은 콘텐츠는 바로 펼쳐진(Expanded) 상태로 표시됩니다.
         */
        data object Flexible : ModalType()

        /**
         * data class FixedWrapContent
         *
         * 콘텐츠 높이에 맞게 조정되는 고정형 Modal 입니다. 기본은 커스텀 시트(isSystemBottomSheet = false)입니다. isCloseable(기본 true)이 false 면 드래그 핸들을 숨기고, 시스템 시트에서는 스와이프·바깥 탭으로 닫히지 않습니다. 뒤로가기는 시스템·커스텀 시트 모두 onDismissRequest 를 호출하고(커스텀 시트는 바깥 탭(dismissOnClickOutside)도 호출), 실제로 닫을지는 호출부가 표시 상태로 정합니다.
         *
         * @property isCloseable Boolean: false 면 드래그 핸들을 숨기고, 시스템 시트에서는 스와이프·바깥 탭으로 닫히지 않습니다. 뒤로가기는 시스템·커스텀 시트 모두 onDismissRequest 를 호출하므로(커스텀 시트는 바깥 탭도), 닫히지 않게 하려면 호출부가 그 콜백에서 표시 상태를 바꾸지 않습니다.
         * @property isSystemBottomSheet Boolean: true 면 Material ModalBottomSheet, false(기본)면 핸들로만 드래그되는 커스텀 시트로 그립니다.
         */
        data class FixedWrapContent(
            override val isCloseable: Boolean = true,
            override val isSystemBottomSheet: Boolean = false
        ) : ModalType(isCloseable, isSystemBottomSheet)

        /**
         * data class Fixed
         *
         * height 로 지정한 높이를 갖는 고정형 Modal 입니다. 기본은 커스텀 시트(isSystemBottomSheet = false)입니다. isCloseable(기본 true)이 false 면 드래그 핸들을 숨기고, 시스템 시트에서는 스와이프·바깥 탭으로 닫히지 않습니다. 뒤로가기는 시스템·커스텀 시트 모두 onDismissRequest 를 호출하고(커스텀 시트는 바깥 탭(dismissOnClickOutside)도 호출), 실제로 닫을지는 호출부가 표시 상태로 정합니다.
         *
         * @property height Dp: Modal 의 높이입니다.
         * @property isCloseable Boolean: false 면 드래그 핸들을 숨기고, 시스템 시트에서는 스와이프·바깥 탭으로 닫히지 않습니다. 뒤로가기는 시스템·커스텀 시트 모두 onDismissRequest 를 호출하므로(커스텀 시트는 바깥 탭도), 닫히지 않게 하려면 호출부가 그 콜백에서 표시 상태를 바꾸지 않습니다.
         * @property isSystemBottomSheet Boolean: true 면 Material ModalBottomSheet, false(기본)면 핸들로만 드래그되는 커스텀 시트로 그립니다.
         */
        data class Fixed(
            val height: Dp,
            override val isCloseable: Boolean = true,
            override val isSystemBottomSheet: Boolean = false
        ) : ModalType(isCloseable, isSystemBottomSheet)

        /**
         * data class FixedFullScreen
         *
         * 화면 전체를 덮는 고정형 Modal 입니다. 기본은 커스텀 시트(isSystemBottomSheet = false)입니다. isCloseable(기본 true)이 false 면 드래그 핸들을 숨기고, 시스템 시트에서는 스와이프·바깥 탭으로 닫히지 않습니다. 뒤로가기는 시스템·커스텀 시트 모두 onDismissRequest 를 호출하고(커스텀 시트는 바깥 탭(dismissOnClickOutside)도 호출), 실제로 닫을지는 호출부가 표시 상태로 정합니다.
         *
         * @property isCloseable Boolean: false 면 드래그 핸들을 숨기고, 시스템 시트에서는 스와이프·바깥 탭으로 닫히지 않습니다. 뒤로가기는 시스템·커스텀 시트 모두 onDismissRequest 를 호출하므로(커스텀 시트는 바깥 탭도), 닫히지 않게 하려면 호출부가 그 콜백에서 표시 상태를 바꾸지 않습니다.
         * @property isSystemBottomSheet Boolean: true 면 Material ModalBottomSheet, false(기본)면 핸들로만 드래그되는 커스텀 시트로 그립니다.
         */
        data class FixedFullScreen(
            override val isCloseable: Boolean = true,
            override val isSystemBottomSheet: Boolean = false
        ) : ModalType(isCloseable, isSystemBottomSheet)

        /**
         * data class FixedRatio
         *
         * 화면 높이 × ratio(0.0 ~ 1.0)로 높이가 정해지는 고정형 Modal입니다. 다만 화면 높이에서 상단 inset·10dp·드래그 핸들(19dp)을 뺀 값을 넘지 않으므로 ratio 가 1f 여도 화면 전체 높이가 되지 않습니다. 기본은 커스텀 시트(isSystemBottomSheet = false)입니다. isCloseable(기본 true)이 false 면 드래그 핸들을 숨기고, 시스템 시트에서는 스와이프·바깥 탭으로 닫히지 않습니다. 뒤로가기는 시스템·커스텀 시트 모두 onDismissRequest 를 호출하고(커스텀 시트는 바깥 탭(dismissOnClickOutside)도 호출), 실제로 닫을지는 호출부가 표시 상태로 정합니다.
         *
         * @property ratio Float: 0.0 ~ 1.0 사이의 높이 비율입니다.
         * @property isCloseable Boolean: false 면 드래그 핸들을 숨기고, 시스템 시트에서는 스와이프·바깥 탭으로 닫히지 않습니다. 뒤로가기는 시스템·커스텀 시트 모두 onDismissRequest 를 호출하므로(커스텀 시트는 바깥 탭도), 닫히지 않게 하려면 호출부가 그 콜백에서 표시 상태를 바꾸지 않습니다.
         * @property isSystemBottomSheet Boolean: true 면 Material ModalBottomSheet, false(기본)면 핸들로만 드래그되는 커스텀 시트로 그립니다.
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
     * - Medium: 콘텐츠 여백 20dp, 하단 영역 여백 20dp, 제목 상하 여백 4dp 입니다.
     * - Large: 콘텐츠 여백 24dp, 하단 영역 여백 24dp, 제목 상하 여백 4dp 입니다.
     * - XLarge: 콘텐츠 여백 32dp, 하단 영역 여백 32dp, 제목 상하 8dp·좌우 4dp 여백입니다.
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