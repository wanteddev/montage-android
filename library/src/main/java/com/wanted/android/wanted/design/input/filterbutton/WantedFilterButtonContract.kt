package com.wanted.android.wanted.design.input.filterbutton

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * object WantedFilterButtonContract
 *
 * Filter button에 대한 전반적인 계약을 정의하는 객체입니다.
 *
 * 이 객체는 Filter button의 시각적 변형 및 크기에 대한 enum 클래스를 포함합니다.
 */
object WantedFilterButtonContract {
    /**
     * enum class FilterButtonVariant
     *
     * WantedFilterButton의 시각적 스타일을 정의하는 enum 클래스입니다.
     *
     * 다음과 같은 두 가지 스타일 옵션을 제공합니다:
     * - Solid: 배경이 채워진 형태입니다.
     * - Outlined: 테두리만 있는 형태입니다.
     *
     * @see WantedFilterButton
     */
    enum class FilterButtonVariant {
        Solid, Outlined
    }

    /**
     * enum class FilterButtonSize
     *
     * WantedFilterButton의 크기를 정의하는 enum 클래스입니다.
     *
     * 각 사이즈는 패딩, 아이콘 크기, 텍스트 간격 등에 영향을 미칩니다.
     * 제공되는 크기 옵션은 다음과 같습니다:
     * - Large: 큰 사이즈입니다.
     * - Medium: 중간 사이즈입니다.
     * - Small: 작은 사이즈입니다.
     * - XSmall: 가장 작은 사이즈입니다.
     *
     * @see WantedFilterButton
     */
    enum class FilterButtonSize {
        Large, Medium, Small, XSmall
    }

    internal fun Modifier.filterButtonPadding(
        size: FilterButtonSize
    ): Modifier = when (size) {
        FilterButtonSize.Large -> {
            this.then(
                Modifier
                    .padding(vertical = 9.dp)
                    .padding(start = 12.dp, end = 10.dp)
            )
        }

        FilterButtonSize.Medium -> {
            this.then(
                Modifier
                    .padding(vertical = 7.dp)
                    .padding(start = 11.dp, end = 9.dp)
            )
        }

        FilterButtonSize.Small -> {
            this.then(
                Modifier
                    .padding(vertical = 6.dp)
                    .padding(start = 8.dp, end = 6.dp)
            )
        }

        FilterButtonSize.XSmall -> {
            this.then(
                Modifier
                    .padding(vertical = 4.dp)
                    .padding(start = 7.dp, end = 5.dp)
            )
        }
    }


    internal fun Modifier.filterButtonIconSize(
        size: FilterButtonSize
    ): Modifier {
        val modifier = when (size) {
            FilterButtonSize.Large -> Modifier.size(16.dp)
            FilterButtonSize.Medium -> Modifier.size(16.dp)
            FilterButtonSize.Small -> Modifier.size(14.dp)
            FilterButtonSize.XSmall -> Modifier.size(12.dp)
        }

        return this.then(modifier)
    }

    internal fun Modifier.filterButtonTextPadding(
        size: FilterButtonSize
    ): Modifier {
        val modifier = when (size) {
            FilterButtonSize.Large -> Modifier.padding(horizontal = 2.dp)
            FilterButtonSize.Medium -> Modifier.padding(horizontal = 2.dp)
            FilterButtonSize.Small -> Modifier.padding(horizontal = 2.dp)
            FilterButtonSize.XSmall -> Modifier.padding(horizontal = 1.dp)
        }

        return this.then(modifier)
    }


    @Composable
    internal fun getFilterButtonRadius(size: FilterButtonSize) = when (size) {
        FilterButtonSize.XSmall -> 6.dp
        FilterButtonSize.Small -> 8.dp
        FilterButtonSize.Medium -> 10.dp
        FilterButtonSize.Large -> 10.dp
    }

    @Composable
    internal fun getFilterButtonHorizontalArrangement(size: FilterButtonSize) = when (size) {
        FilterButtonSize.XSmall -> 1.dp
        FilterButtonSize.Small -> 1.dp
        FilterButtonSize.Medium -> 2.dp
        FilterButtonSize.Large -> 2.dp
    }

}


