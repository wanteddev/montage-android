package com.wanted.android.wanted.design.navigations.pagination.pagecounter

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * object WantedPaginationCounterDefaults
 *
 * Page counter 컴포넌트에서 사용하는 설정 값을 정의하는 객체입니다.
 */
object WantedPaginationCounterDefaults {
    /**
     * enum class WantedPageCounterSize
     *
     * Page counter 의 크기를 정의하는 enum 클래스입니다.
     * - Small: 작은 크기의 Page counter 입니다.
     * - Normal: 일반 크기의 Page counter 입니다.
     */
    enum class WantedPageCounterSize(
        val paddingHorizontal: Dp,
        val paddingVertical: Dp,
        val space: Dp,
    ) {
        Small(paddingHorizontal = 10.dp, paddingVertical = 4.dp, space = 3.dp),
        Normal(paddingHorizontal = 12.dp, paddingVertical = 6.dp, space = 4.dp)
    }
}