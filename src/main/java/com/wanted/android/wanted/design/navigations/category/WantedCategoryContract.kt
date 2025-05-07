package com.wanted.android.wanted.design.navigations.category

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object WantedCategoryContract {

    /**
     * 카테고리 항목의 크기 및 여백 설정을 위한 enum 클래스입니다.
     *
     * 각 사이즈에 따라 세로 패딩과 수평 간격이 조절됩니다.
     *
     * @property verticalPadding Dp: 항목 세로 패딩입니다.
     * @property horizontalSpacing Dp: 항목 수평 간격입니다.
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