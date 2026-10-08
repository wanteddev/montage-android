package com.wanted.android.wanted.design.input.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme

/**
 * object WantedSearchFieldDefaults
 *
 * WantedSearchField 컴포넌트에서 사용하는 설정 값을 정의하는 객체입니다.
 *
 * 치수·타이포는 WantedTextField 와 동일한 값을 사용하며,
 * Size 별 토큰은 아래 internal 확장 프로퍼티로 분리되어 있습니다.
 */
object WantedSearchFieldDefaults {
    /**
     * sealed class Size
     *
     * 검색 입력 필드의 크기를 정의하는 sealed class입니다.
     * 각 크기에 따라 padding, radius, 최소 높이, 입력 typography, 아이콘 크기가 달라집니다.
     * - Large: 큰 크기입니다. (최소 높이 48dp)
     * - Medium: 중간 크기입니다. (최소 높이 40dp)
     */
    sealed class Size {
        data object Large : Size()
        data object Medium : Size()
    }

    /**
     * enum class Variant
     *
     * 검색 입력 필드의 스타일을 정의하는 enum 클래스입니다.
     * - Solid: 채워진 배경을 사용하고 보더가 없습니다.
     * - Outlined: 투명한 배경 위에 1dp 보더를 사용합니다.
     */
    enum class Variant {
        Solid,
        Outlined
    }
}

internal val WantedSearchFieldDefaults.Size.containerPadding: Dp
    get() = when (this) {
        WantedSearchFieldDefaults.Size.Large -> 8.dp
        WantedSearchFieldDefaults.Size.Medium -> 6.dp
    }

internal val WantedSearchFieldDefaults.Size.borderRadius: Dp
    get() = when (this) {
        WantedSearchFieldDefaults.Size.Large -> 14.dp
        WantedSearchFieldDefaults.Size.Medium -> 12.dp
    }

internal val WantedSearchFieldDefaults.Size.minHeight: Dp
    get() = when (this) {
        WantedSearchFieldDefaults.Size.Large -> 48.dp
        WantedSearchFieldDefaults.Size.Medium -> 40.dp
    }

// Size별 아이콘 크기 토큰입니다. leading 검색 아이콘과 clear 아이콘에 함께 적용합니다.
internal val WantedSearchFieldDefaults.Size.iconSize: Dp
    get() = when (this) {
        WantedSearchFieldDefaults.Size.Large -> 20.dp
        WantedSearchFieldDefaults.Size.Medium -> 18.dp
    }

// Figma 는 `Icon` 프레임에 패딩을 두고 그 안에 아이콘을 넣습니다. (프레임 = 아이콘 + 패딩 × 2)
internal val WantedSearchFieldDefaults.Size.iconPadding: Dp
    get() = when (this) {
        WantedSearchFieldDefaults.Size.Large -> 2.dp
        WantedSearchFieldDefaults.Size.Medium -> 1.dp
    }

internal val WantedSearchFieldDefaults.Size.inputTextStyle: TextStyle
    @Composable get() = when (this) {
        WantedSearchFieldDefaults.Size.Large -> DesignSystemTheme.typography.body2Regular
        WantedSearchFieldDefaults.Size.Medium -> DesignSystemTheme.typography.label1Regular
    }
