package com.wanted.android.wanted.design.navigations.tab


/**
 * object WantedTabDefaults
 *
 * Tab 컴포넌트에서 사용하는 설정값을 정의하는 객체입니다.
 */
object WantedTabDefaults {

    /**
     * enum class TabSize
     *
     * Tab 텍스트의 크기를 정의하는 enum 클래스입니다.
     * - Small: body2Bold 타이포를 씁니다. rightIcon 슬롯 크기는 20dp 입니다.
     * - Medium: headline2Bold 타이포를 씁니다. rightIcon 슬롯 크기는 22dp 입니다.
     * - Large: heading2Bold 타이포를 씁니다. rightIcon 슬롯 크기는 24dp 입니다.
     */
    enum class TabSize {
        Small,
        Medium,
        Large
    }
}