package com.wanted.android.wanted.design.input.input

/**
 * object WantedInputDefaults
 *
 * WantedInput 컴포넌트에서 사용되는 입력 필드 관련 설정을 정의하는 객체입니다.
 *
 * 입력 컴포넌트의 사이즈와 타입을 명확히 지정할 수 있도록 enum 클래스를 포함합니다.
 */
object WantedInputDefaults {

    /**
     * enum class WantedInputSize
     *
     * 입력 필드의 높이를 정의하는 enum 클래스입니다.
     *
     * UI에서 사용되는 컴포넌트 높이를 다음과 같이 구분할 수 있습니다:
     * - Medium: 일반적인 높이의 입력 항목입니다.
     * - Small: 좁은 공간에서 사용할 수 있는 컴팩트한 높이의 입력 항목입니다.
     */
    enum class WantedInputSize {
        Medium,
        Small
    }

    /**
     * enum class WantedInputVariant
     *
     * 입력 타입을 정의하는 enum 클래스입니다.
     *
     * 사용자의 선택을 다양한 시각적 스타일로 표현할 수 있도록 다음과 같은 타입을 제공합니다:
     * - CheckBox: 일반적인 사각 체크박스입니다.
     * - Radio: 원형의 라디오 버튼 형식입니다.
     * - CheckMark: 체크 아이콘만 표시되는 스타일입니다.
     * - Switch: 토글 스위치 형식입니다.
     */
    enum class WantedInputVariant {
        CheckBox,
        Radio,
        CheckMark,
        Switch
    }
}