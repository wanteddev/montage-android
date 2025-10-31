package com.wanted.android.wanted.design.input.textinput.textfield

/**
 * object WantedTextFieldDefaults
 *
 * TextField 컴포넌트에서 사용되는 설정 값을 정의하는 객체입니다.
 *
 * 선택 렌더링 방식과 선택 UI 스타일을 제어할 수 있는 enum 클래스를 포함합니다.
 */
object WantedTextFieldDefaults {

    /**
     * enum class Status
     *
     * TextField의 상태를 정의하는 enum 클래스입니다.
     * Normal, Positive, Negative 세 가지 상태가 존재합니다.
     */
    enum class Status {
        Normal,
        Positive,
        Negative
    }

    /**
     * enum class RightVariant
     *
     * TextField 우측 버튼의 스타일을 정의하는 enum 클래스입니다.
     * Normal, Assistive 두 가지 스타일이 존재합니다.
     */
    enum class RightVariant {
        Normal,
        Assistive
    }
}
