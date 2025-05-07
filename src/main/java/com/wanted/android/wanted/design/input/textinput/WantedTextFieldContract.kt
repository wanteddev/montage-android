package com.wanted.android.wanted.design.input.textinput

object WantedTextFieldContract {

    /**
     * TextField의 시각적 상태를 정의합니다.
     *
     * 각 상태는 UI 내 색상, 아이콘, 설명 등의 구성에 영향을 미칩니다.
     *
     * - `Normal`: 일반 상태로 아무런 강조 없이 기본 스타일로 표시됩니다.
     * - `Positive`: 완료 상태로, 성공 또는 완료된 입력을 나타냅니다.
     * - `Negative`: 에러 상태로, 입력 값이 유효하지 않거나 경고가 필요할 때 사용됩니다.
     */
    enum class Status {
        Normal,
        Positive,
        Negative
    }

    /**
     * TextField 우측 버튼의 시각적 스타일을 정의합니다.
     *
     * - `Normal`: 강조된 기본 스타일로, 일반적인 주요 액션에 사용됩니다.
     * - `Assistive`: 보조적 역할을 하는 버튼 스타일로, 눈에 띄지 않게 표현됩니다.
     */
    enum class RightVariant {
        Normal,
        Assistive
    }
}
