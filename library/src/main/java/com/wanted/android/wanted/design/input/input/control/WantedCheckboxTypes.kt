package com.wanted.android.wanted.design.input.input.control

/**
 * enum class CheckBoxSize
 *
 * 체크박스의 크기를 정의하는 enum 클래스입니다.
 *
 * 컴포넌트의 시각적 일관성을 유지하기 위해 두 가지 사이즈를 제공합니다:
 * - Normal: 일반 사이즈 (24dp)입니다.
 * - Small: 소형 사이즈 (20dp)입니다.
 */
enum class CheckBoxSize {
    Normal, Small
}

/**
 * enum class CheckBoxStyle
 *
 * 다양한 형태의 체크박스 스타일을 정의하는 enum 클래스입니다.
 *
 * 사용 목적이나 UI 환경에 따라 다음과 같은 스타일을 사용할 수 있습니다:
 * - CheckBox: 기본 사각형 체크박스입니다.
 * - RoundCheckBox: 원형 배경을 가진 체크박스입니다.
 * - Check: 체크 마크만 표시되는 스타일입니다.
 * - Radio: 라디오 버튼 형식입니다.
 * - Switch: 토글 스위치 형식입니다.
 */
enum class CheckBoxStyle {
    CheckBox,
    RoundCheckBox,
    Check,
    Radio,
    Switch
}

/**
 * enum class CheckBoxState
 *
 * 체크박스의 현재 상태를 나타내는 enum 클래스입니다.
 *
 * 세 가지 상태를 통해 선택 여부 또는 그룹 선택의 중간 상태를 표현할 수 있습니다:
 * - Unchecked: 선택되지 않았습니다.
 * - Checked: 선택되었습니다.
 * - Indeterminate: 일부만 선택된 상태 등 중간 상태입니다.
 */
enum class CheckBoxState {
    Unchecked,
    Checked,
    Indeterminate
}