package com.wanted.android.wanted.design.input.select

object WantedSelectContract {
    /**
     * 멀티 셀렉트의 선택된 항목을 화면에 렌더링하는 방식을 정의하는 열거형입니다.
     *
     * - `Text` : 선택된 항목을 텍스트 형태로 나열합니다.
     * - `Chip` : 선택된 항목을 Chip 형태로 표시합니다.
     *
     * 사용 예시:
     * ```kotlin
     * render = WantedSelectContract.MultiSelectRender.Chip
     * ```
     */
    enum class MultiSelectRender {
        Chip,
        Text
    }

    /**
     * 셀렉트 다이얼로그에서 항목을 선택할 때 사용할 UI 타입을 정의하는 열거형입니다.
     *
     * - `CheckMark` : 단일 선택 시 체크마크 방식
     * - `CheckBox` : 멀티 선택 시 체크박스 방식
     * - `Radio` : 단일 선택 시 라디오 버튼 방식
     *
     * 사용 예시:
     * ```kotlin
     * selectType = WantedSelectContract.SelectType.Radio
     * ```
     */
    enum class SelectType {
        CheckMark,
        CheckBox,
        Radio
    }
}