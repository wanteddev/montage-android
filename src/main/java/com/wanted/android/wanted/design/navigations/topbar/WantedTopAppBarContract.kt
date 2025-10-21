package com.wanted.android.wanted.design.navigations.topbar


/**
 * object WantedTopAppBarContract
 *
 * 상단 앱바(TopAppBar) 컴포넌트의 UI 설정 값을 정의하는 객체입니다.
 *
 * 앱바의 형태를 제어하는 enum 클래스 `Variant`을 포함합니다.
 */
object WantedTopAppBarContract {


    /**
     * enum class Variant
     *
     * TopAppBar의 형태를 정의하는 enum 클래스입니다.
     *
     * 제공되는 앱바 타입은 다음과 같습니다:
     * - Normal: 기본 상단 앱바 형태
     * - Extended: 높이가 확장된 형태
     * - Floating: 투명 배경 또는 떠 있는 형태의 앱바
     */
    enum class Variant {
        Normal,
        Display,
        Floating,
        Search
    }
}