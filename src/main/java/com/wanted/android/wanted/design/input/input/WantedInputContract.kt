package com.wanted.android.wanted.design.input.input

object WantedInputContract {
    /**
     * WantedInput 컴포저블에서 사용할 입력 컴포넌트의 사이즈를 정의합니다.
     *
     * Medium - 일반적인 높이의 입력 항목입니다.
     * Small - 좁은 공간에서 사용할 수 있는 컴팩트한 높이의 입력 항목입니다.
     */
    enum class WantedInputSize {
        Medium,
        Small
    }

    /**
     * WantedInput에서 사용할 입력 타입을 정의하는 enum 클래스입니다.
     *
     * CheckBox - 일반적인 사각 체크박스입니다.
     * Radio - 원형의 라디오 버튼 형식입니다.
     * CheckMark - 체크 아이콘만 표시되는 스타일입니다.
     */
    enum class WantedInputType {
        CheckBox,
        Radio,
        CheckMark
    }
}