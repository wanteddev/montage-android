package com.wanted.android.wanted.design.navigations.topbar


object WantedTopAppBarContract {

    /**
     * TopAppBar의 형태를 정의하는 enum 클래스입니다.
     *
     * - Normal: 기본 상단 앱바 형태입니다.
     * - Extended: 높이가 확장된 형태의 앱바입니다.
     * - Floating: 배경이 투명하거나 공중에 떠 있는 듯한 스타일의 앱바입니다.
     */
    enum class TopAppBarType {
        Normal,
        Extended,
        Floating
    }
}