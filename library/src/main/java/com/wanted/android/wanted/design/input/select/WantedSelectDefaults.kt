package com.wanted.android.wanted.design.input.select

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme

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

    /**
     * enum class Status
     *
     * Select의 상태를 정의하는 enum 클래스입니다. (WantedTextArea 의 Status 와 동일한 축)
     * - Normal: 일반 상태입니다.
     * - Negative: 부정(에러) 상태입니다.
     */
    enum class Status {
        Normal,
        Negative
    }

    /**
     * enum class Size
     *
     * Select의 크기를 정의하는 enum 클래스입니다.
     * 각 크기에 따라 padding, radius, 최소 높이, 입력 typography, leading content 크기,
     * Chip 간격이 달라집니다.
     * - Large: 큰 크기입니다. (최소 높이 48dp)
     * - Medium: 중간 크기입니다. (최소 높이 40dp)
     */
    enum class Size {
        Large,
        Medium
    }
}

internal val WantedSelectDefaults.Size.containerPadding: Dp
    get() = when (this) {
        WantedSelectDefaults.Size.Large -> 8.dp
        WantedSelectDefaults.Size.Medium -> 6.dp
    }

// overflow=true 일 때 Container 상하 padding 입니다.
// 한 줄(기본 높이)일 때의 상하 여백 (minHeight - Content 최소 높이 24) / 2 과 같아, 여러 줄로 늘어나도 여백이 유지됩니다.
internal val WantedSelectDefaults.Size.overflowVerticalPadding: Dp
    get() = when (this) {
        WantedSelectDefaults.Size.Large -> 12.dp
        WantedSelectDefaults.Size.Medium -> 8.dp
    }

internal val WantedSelectDefaults.Size.borderRadius: Dp
    get() = when (this) {
        WantedSelectDefaults.Size.Large -> 14.dp
        WantedSelectDefaults.Size.Medium -> 12.dp
    }

internal val WantedSelectDefaults.Size.minHeight: Dp
    get() = when (this) {
        WantedSelectDefaults.Size.Large -> 48.dp
        WantedSelectDefaults.Size.Medium -> 40.dp
    }

internal val WantedSelectDefaults.Size.leadingContentSize: Dp
    get() = when (this) {
        WantedSelectDefaults.Size.Large -> 24.dp
        WantedSelectDefaults.Size.Medium -> 20.dp
    }

internal val WantedSelectDefaults.Size.leadingIconSize: Dp
    get() = when (this) {
        WantedSelectDefaults.Size.Large -> 20.dp
        WantedSelectDefaults.Size.Medium -> 18.dp
    }

internal val WantedSelectDefaults.Size.chipSpacing: Dp
    get() = when (this) {
        WantedSelectDefaults.Size.Large -> 8.dp
        WantedSelectDefaults.Size.Medium -> 6.dp
    }

internal val WantedSelectDefaults.Size.inputTextStyle: TextStyle
    @Composable get() = when (this) {
        WantedSelectDefaults.Size.Large -> DesignSystemTheme.typography.body2Regular
        WantedSelectDefaults.Size.Medium -> DesignSystemTheme.typography.label1Regular
    }

// Deprecated 오버로드의 Boolean negative 를 Status 로 변환합니다.
internal fun Boolean.toSelectStatus(): WantedSelectDefaults.Status = if (this) {
    WantedSelectDefaults.Status.Negative
} else {
    WantedSelectDefaults.Status.Normal
}
