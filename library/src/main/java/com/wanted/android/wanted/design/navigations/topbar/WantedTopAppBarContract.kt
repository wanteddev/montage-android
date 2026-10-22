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
     * - Normal: 일반 형태입니다.
     * - Display: 제목을 title3Bold 로 크게, 왼쪽 정렬로 표시합니다(가운데 정렬 설정·WantedCenterTopAppBar 에서도 왼쪽 정렬). navigationIcon 이 함께 있으면 제목은 아이콘 줄 아래 두 번째 줄에 놓입니다.
     * - Floating: 레이아웃은 Normal 과 같고, background = true 이거나 스크롤된 동안 배경을 backgroundColor 88% → 투명 세로 그라디언트로 칠하고 하단 16dp 여백을 둡니다.
     * - Search: 레이아웃은 Normal 과 같습니다. 검색 필드는 만들지 않으므로 WantedSearchTopAppBar 를 쓰세요.
     */
    enum class Variant {
        Normal,
        Display,
        Floating,
        Search
    }
}