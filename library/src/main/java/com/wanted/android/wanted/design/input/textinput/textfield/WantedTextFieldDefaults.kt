package com.wanted.android.wanted.design.input.textinput.textfield

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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
     * enum class Size
     *
     * TextField의 크기를 정의하는 enum 클래스입니다.
     * 각 크기에 따라 padding, radius, 최소 높이, 입력 typography, 아이콘 크기가 달라집니다.
     * - Large: 큰 크기입니다. (최소 높이 48dp)
     * - Medium: 중간 크기입니다. (최소 높이 40dp)
     */
    enum class Size {
        Large,
        Medium
    }
}

internal val WantedTextFieldDefaults.Size.containerPadding: Dp
    get() = when (this) {
        WantedTextFieldDefaults.Size.Large -> 8.dp
        WantedTextFieldDefaults.Size.Medium -> 6.dp
    }

internal val WantedTextFieldDefaults.Size.borderRadius: Dp
    get() = when (this) {
        WantedTextFieldDefaults.Size.Large -> 14.dp
        WantedTextFieldDefaults.Size.Medium -> 12.dp
    }

internal val WantedTextFieldDefaults.Size.minHeight: Dp
    get() = when (this) {
        WantedTextFieldDefaults.Size.Large -> 48.dp
        WantedTextFieldDefaults.Size.Medium -> 40.dp
    }

internal val WantedTextFieldDefaults.Size.iconSize: Dp
    get() = when (this) {
        WantedTextFieldDefaults.Size.Large -> 20.dp
        WantedTextFieldDefaults.Size.Medium -> 18.dp
    }

// Size별 아이콘 영역(슬롯) 크기 토큰입니다. 아이콘 자체보다 큰 정사각 영역을 차지합니다.
internal val WantedTextFieldDefaults.Size.iconBoxSize: Dp
    get() = when (this) {
        WantedTextFieldDefaults.Size.Large -> 24.dp
        WantedTextFieldDefaults.Size.Medium -> 20.dp
    }
