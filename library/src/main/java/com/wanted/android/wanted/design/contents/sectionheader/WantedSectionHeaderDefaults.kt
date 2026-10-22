package com.wanted.android.wanted.design.contents.sectionheader


/**
 * object WantedSectionHeaderDefaults
 *
 * Section header 컴포넌트에 사용하는 설정값을 정의하는 객체입니다.
 *
 * 제목 타이포·색을 고르는 크기 정보를 제공합니다.
 */
object WantedSectionHeaderDefaults {


    /**
     * enum class Size
     *
     * Section header의 사이즈를 정의하는 enum 클래스입니다.
     *
     * textStyle 이 null 일 때의 타이포·색만 정하며 여백은 바꾸지 않습니다.
     * - XSmall: label1Bold, foregroundNeutralTertiary 색입니다.
     * - Small: headline2Bold, foregroundNeutralStrong 색입니다.
     * - Medium: heading2Bold, foregroundNeutralStrong 색입니다.
     * - Large: title3Bold, foregroundNeutralStrong 색입니다.
     */
    enum class Size {
        XSmall,
        Small,
        Medium,
        Large
    }
}