package com.wanted.android.wanted.design.input.search

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * object WantedSearchFieldDefaults
 *
 * WantedSearchField 컴포넌트에서 사용하는 상수들을 정의하는 객체입니다.
 */
object WantedSearchFieldDefaults {
    /**
     * sealed class Size
     *
     * 검색 입력 필드의 크기를 정의하는 sealed class입니다.
     * Small, Medium, Custom 세 가지 타입이 존재합니다.
     *
     * @property padding Dp: 입력 필드 내부의 패딩 값입니다.
     * @property minHeight Dp: 입력 필드의 최소 높이입니다.
     */
    sealed class Size(
        open val padding: Dp,
        open val minHeight: Dp
    ) {
        /**
         * data class Small
         *
         * 작은 크기의 검색 입력 필드입니다.
         *
         * @property padding Dp: 내부 패딩 8dp입니다.
         * @property minHeight Dp: 최소 높이 40dp입니다.
         */
        data class Small(
            override val padding: Dp = 8.dp,
            override val minHeight: Dp = 40.dp
        ) : Size(padding, minHeight)

        /**
         * data class Medium
         *
         * 중간 크기의 검색 입력 필드입니다. (기본값)
         *
         * @property padding Dp: 내부 패딩 12dp입니다.
         * @property minHeight Dp: 최소 높이 48dp입니다.
         */
        data class Medium(
            override val padding: Dp = 12.dp,
            override val minHeight: Dp = 48.dp
        ) : Size(padding, minHeight)

        /**
         * data class Custom
         *
         * 사용자 정의 크기의 검색 입력 필드입니다.
         *
         * @property padding Dp: 내부 패딩을 직접 지정할 수 있습니다. 기본값은 12dp입니다.
         * @property minHeight Dp: 최소 높이를 직접 지정할 수 있습니다. 기본값은 48dp입니다.
         */
        data class Custom(
            override val padding: Dp = 12.dp,
            override val minHeight: Dp = 48.dp
        ) : Size(padding, minHeight)
    }
}