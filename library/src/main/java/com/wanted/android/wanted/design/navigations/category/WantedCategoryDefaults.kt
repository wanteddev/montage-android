package com.wanted.android.wanted.design.navigations.category

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * object WantedCategoryDefaults
 *
 * 카테고리 컴포넌트에서 사용하는 설정 값을 정의하는 객체입니다.
 */
object WantedCategoryDefaults {

    /**
     * enum class Size
     *
     * 카테고리 항목의 크기 및 여백을 정의하는 enum 클래스입니다.
     * Small, Medium, Large, XLarge 네 가지 크기가 존재합니다.
     *
     * @property verticalPadding Dp: 항목의 세로 패딩입니다.
     * @property horizontalSpacing Dp: 항목 간 수평 간격입니다.
     */
    enum class Size(
        val verticalPadding: Dp,
        val horizontalSpacing: Dp,
    ) {
        Small(8.dp, 4.dp),
        Medium(8.dp, 6.dp),
        Large(10.dp, 8.dp),
        XLarge(10.dp, 10.dp)
    }
}