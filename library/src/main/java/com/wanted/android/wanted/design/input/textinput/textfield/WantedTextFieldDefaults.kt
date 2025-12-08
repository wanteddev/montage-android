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
     * - Normal: 일반 상태입니다.
     * - Positive: 긍정 상태입니다.
     * - Negative: 부정 상태입니다.
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
     * - Normal: 일반 스타일입니다.
     * - Assistive: 보조 스타일입니다.
     */
    enum class RightVariant {
        Normal,
        Assistive
    }
}
