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
     * 카테고리 항목의 크기 및 여백을 정의하는 Enum 클래스입니다.
     *
     * 카테고리 항목의 시각적 크기와 간격을 결정할 때 사용됩니다. UI 요구사항에 따라 다음의 네 가지 옵션을 제공합니다:
     * - Small: 작은 크기의 카테고리 항목입니다.
     * - Medium: 중간 크기의 카테고리 항목입니다.
     * - Large: 큰 크기의 카테고리 항목입니다.
     * - XLarge: 매우 큰 크기의 카테고리 항목입니다.
     *
     * @see WantedCategoryDefault
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