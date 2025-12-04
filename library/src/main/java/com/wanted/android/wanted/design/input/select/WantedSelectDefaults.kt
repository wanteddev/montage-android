package com.wanted.android.wanted.design.input.select

/**
 * object WantedSelectDefaults
 *
 * Select 컴포넌트에서 사용되는 설정 값을 정의하는 객체입니다.
 */
object WantedSelectDefaults {
    /**
     * enum class MultiSelectRender
     *
     * Multi Select 에서 선택된 항목을 화면에 표시하는 방식을 정의하는 enum 클래스입니다.
     *
     * 사용 가능한 렌더링 타입은 다음과 같습니다:
     * - Chip: 선택된 항목을 Chip 형태로 표시
     * - Text: 선택된 항목을 텍스트 형태로 나열
     */
    enum class MultiSelectRender {
        Chip,
        Text
    }

    /**
     * enum class SelectType
     *
     * Select Dialog 에서 항목을 선택할 때 사용할 UI 타입을 정의하는 enum 클래스입니다.
     *
     * 사용 가능한 UI 타입은 다음과 같습니다:
     * - CheckMark: 단일 선택 시 체크마크 방식
     * - CheckBox: 멀티 선택 시 체크박스 방식
     * - Radio: 단일 선택 시 라디오 버튼 방식
     */
    enum class SelectType {
        CheckMark,
        CheckBox,
        Radio
    }
}