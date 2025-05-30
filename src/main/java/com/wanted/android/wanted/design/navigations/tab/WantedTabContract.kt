package com.wanted.android.wanted.design.navigations.tab


/**
 * object WantedTabContract
 *
 * 탭(Tab) UI 컴포넌트에 사용되는 설정 값을 정의하는 객체입니다.
 *
 * 탭의 텍스트 크기를 조정하기 위한 enum 클래스 `TabSize`를 포함합니다.
 */
object WantedTabContract {

    /**
     * enum class TabSize
     *
     * 탭 텍스트의 크기를 정의하는 enum 클래스입니다.
     *
     * 텍스트 스타일에 따라 세 가지 크기를 제공합니다:
     * - Small: `body2Bold` 스타일
     * - Medium: `headline2Bold` 스타일
     * - Large: `heading2Bold` 스타일
     */
    enum class TabSize {
        Small,
        Medium,
        Large
    }
}