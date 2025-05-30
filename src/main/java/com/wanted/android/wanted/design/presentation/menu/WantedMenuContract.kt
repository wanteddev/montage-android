package com.wanted.android.wanted.design.presentation.menu

/**
 * object WantedMenuContract
 *
 * 메뉴(Menu) 컴포넌트에서 사용되는 설정 값을 정의하는 객체입니다.
 *
 * 리스트 형태를 지정하는 enum 클래스 `ListType`을 포함합니다.
 */
object WantedMenuContract {

    /**
     * enum class ListType
     *
     * 메뉴 항목의 리스트 타입을 정의하는 enum 클래스입니다.
     *
     * 항목의 시각적 구성 방식에 따라 다음과 같은 타입을 제공합니다:
     * - Normal: 기본 텍스트만 있는 리스트
     * - Radio: 우측에 라디오 버튼이 포함된 리스트
     * - Check: 좌측에 체크박스가 포함된 리스트
     */
    enum class ListType {
        Normal,
        Radio,
        Check
    }
}