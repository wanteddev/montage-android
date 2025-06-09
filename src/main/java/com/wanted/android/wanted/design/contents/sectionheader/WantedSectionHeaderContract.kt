package com.wanted.android.wanted.design.contents.sectionheader


/**
 * object WantedSectionHeaderContract
 *
 * 섹션 헤더 컴포넌트에 사용되는 설정 값을 정의하는 객체입니다.
 *
 * 주로 텍스트 크기나 패딩 등의 기준이 되는 사이즈 정보를 제공합니다.
 */
object WantedSectionHeaderContract {


    /**
     * enum class Size
     *
     * 섹션 헤더의 사이즈를 정의하는 enum 클래스입니다.
     *
     * 사이즈에 따라 텍스트 크기나 마진 등이 달라질 수 있습니다.
     * 제공되는 사이즈 옵션은 다음과 같습니다:
     * - XSmall: 매우 작은 사이즈
     * - Small: 작은 사이즈
     * - Medium: 중간 사이즈
     * - Large: 큰 사이즈
     */
    enum class Size {
        XSmall,
        Small,
        Medium,
        Large
    }
}