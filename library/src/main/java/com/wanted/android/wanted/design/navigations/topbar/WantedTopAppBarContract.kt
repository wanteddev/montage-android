package com.wanted.android.wanted.design.navigations.topbar


/**
 * object WantedTopAppBarContract
 *
 * TopAppBar 컴포넌트에서 사용하는 설정 값을 정의하는 객체입니다.
 */
object WantedTopAppBarContract {


    /**
     * enum class Variant
     *
     * TopAppBar의 형태를 정의하는 enum 클래스입니다.
     * Normal, Display, Floating, Search 네 가지 형태가 존재합니다.
     */
    enum class Variant {
        Normal,
        Display,
        Floating,
        Search
    }
}