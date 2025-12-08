package com.wanted.android.wanted.design.contents.accordion

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * object WantedAccordionDefaults
 *
 * Accordion 컴포넌트에서 사용되는 상수 및 설정 값을 정의하는 객체입니다.
 *
 * 이 객체는 주로 아코디언 헤더의 시각적 구성 요소 중 패딩 관련 값을 제공합니다.
 */
object WantedAccordionDefaults {

    /**
     * enum class VerticalPadding
     *
     * Accordion 헤더 영역의 수직 패딩 크기를 정의하는 enum 클래스입니다.
     *
     * 아코디언 헤더 내 콘텐츠 간 간격을 조정할 때 사용됩니다. UI 요구사항에 따라 다음의 세 가지 옵션을 제공합니다:
     * - Padding16: 16dp 수직 패딩입니다.
     * - Padding12: 12dp 수직 패딩입니다.
     * - Padding8: 8dp 수직 패딩입니다.
     *
     * @property value Dp: 실제 적용되는 패딩 값입니다.
     *
     * @see WantedAccordion
     */
    enum class VerticalPadding(val value: Dp) {
        Padding16(16.dp),
        Padding12(12.dp),
        Padding8(8.dp)
    }
}