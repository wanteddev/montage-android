package com.wanted.android.wanted.design.presentation.menu

object WantedMenuContract {

    /**
     * 메뉴 항목의 타입을 정의하는 열거형입니다.
     *
     * @property Normal 기본 텍스트 형태의 리스트입니다.
     * @property Radio 우측에 라디오 버튼이 포함된 리스트입니다.
     * @property Check 좌측에 체크박스가 포함된 리스트입니다.
     */
    enum class ListType {
        Normal,
        Radio,
        Check
    }
}