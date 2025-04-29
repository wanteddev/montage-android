package com.wanted.android.wanted.design.contents.accordion

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 아코디언 헤더 영역의 수직 패딩 크기를 정의하는 Enum 클래스입니다.
 *
 * 아코디언 헤더의 콘텐츠(Title, Icon 등) 간격을 조정할 때 사용됩니다.
 * 다양한 UI 요구사항에 맞춰 3가지 패딩 옵션을 제공합니다.
 *
 * 사용 예시:
 * ```kotlin
 * verticalPadding = VerticalPadding.Padding12
 * ```
 *
 * @property value Dp: 실제 적용될 DP 단위의 패딩 값입니다.
 *
 * @see WantedAccordion
 */
object WantedAccordionContract {
    /**
     * header vertical padding
     */
    enum class VerticalPadding(val value: Dp) {
        Padding16(16.dp),
        Padding12(12.dp),
        Padding8(8.dp)
    }
}