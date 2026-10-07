package com.wanted.android.wanted.design.input.formcontrol

/**
 * object WantedFormControlDefaults
 *
 * WantedFormControl 컴포넌트에서 사용되는 설정 값을 정의하는 객체입니다.
 *
 * 상태, 크기, 라벨 배치 방향을 제어할 수 있는 enum 클래스를 포함합니다.
 */
object WantedFormControlDefaults {

    /**
     * enum class Status
     *
     * FormControl의 상태를 정의하는 enum 클래스입니다.
     * - Normal: 일반 상태입니다.
     * - Positive: 긍정 상태입니다.
     * - Negative: 부정 상태입니다. (에러)
     */
    enum class Status {
        Normal,
        Positive,
        Negative
    }

    /**
     * enum class Size
     *
     * FormControl 라벨과 필수 뱃지의 크기를 정의하는 enum 클래스입니다.
     * - Large: 큰 크기입니다. (label1Bold)
     * - Medium: 중간 크기입니다. (label2Bold)
     */
    enum class Size {
        Large,
        Medium
    }

    /**
     * enum class LabelPlacement
     *
     * FormControl에서 라벨의 배치 위치를 정의하는 enum 클래스입니다.
     * - Top: 입력 컨트롤 위에 라벨 배치 (Column 구성)
     * - Leading: 입력 컨트롤 좌측에 라벨 배치 (Row 구성)
     */
    enum class LabelPlacement {
        Top,
        Leading
    }
}
