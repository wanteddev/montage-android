package com.wanted.android.wanted.design.navigations.category

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * object WantedCategoryContract
 *
 * 카테고리 내 항목 UI 설정을 정의하는 객체입니다.
 *
 * 항목의 크기와 여백 설정을 위한 enum 클래스 `Size`를 포함하고 있습니다.
 */
object WantedCategoryContract {

    /**
     * enum class Size
     *
     * 카테고리 항목의 크기 및 여백 설정을 위한 enum 클래스입니다.
     *
     * 각 사이즈에 따라 항목의 세로 패딩 및 수평 간격이 다르게 적용됩니다.
     *
     * 포함된 사이즈 값:
     * - Small: 세로 패딩 8dp, 수평 간격 4dp
     * - Medium: 세로 패딩 8dp, 수평 간격 6dp
     * - Large: 세로 패딩 10dp, 수평 간격 8dp
     * - XLarge: 세로 패딩 10dp, 수평 간격 10dp
     *
     * @property verticalPadding `Dp`: 항목 세로 패딩입니다.
     * @property horizontalSpacing `Dp`: 항목 수평 간격입니다.
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