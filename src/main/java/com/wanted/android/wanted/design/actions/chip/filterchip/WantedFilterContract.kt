package com.wanted.android.wanted.design.actions.chip.filterchip

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.actions.chip.actionchip.WantedActionChipContract.ActionChipSize

/**
 * object WantedActionContract
 *
 * ChipAction에 대한 전반적인 계약(Contract)을 정의하는 객체입니다.
 *
 * 이 객체는 ChipAction의 시각적 변형 및 크기에 대한 enum 클래스를 포함합니다.
 */
object WantedFilterChipContract {
    /**
     * enum class FilterChipVariant
     *
     * WantedFilterChip의 시각적 스타일을 정의하는 enum 클래스입니다.
     *
     * 다음과 같은 두 가지 스타일 옵션을 제공합니다:
     * - Solid: 배경이 채워진 형태
     * - Outlined: 테두리만 있는 형태
     *
     * @see WantedFilterChip
     */
    enum class FilterChipVariant {
        Solid, Outlined
    }

    /**
     * enum class FilterChipSize
     *
     * WantedFilterChip의 크기를 정의하는 enum 클래스입니다.
     *
     * 각 사이즈는 패딩, 아이콘 크기, 텍스트 간격 등에 영향을 미칩니다.
     * 제공되는 크기 옵션은 다음과 같습니다:
     * - Large: 큰 사이즈
     * - Medium: 중간 사이즈
     * - Small: 작은 사이즈
     * - XSmall: 가장 작은 사이즈
     *
     * @see WantedFilterChip
     */
    enum class FilterChipSize {
        Large, Medium, Small, XSmall
    }


    internal fun Modifier.actionChipPadding(
        size: ActionChipSize
    ): Modifier = when (size) {
        ActionChipSize.Large -> {
            this.then(Modifier.padding(vertical = 9.dp, horizontal = 12.dp))
        }

        ActionChipSize.Medium -> {
            this.then(Modifier.padding(vertical = 7.dp, horizontal = 11.dp))
        }

        ActionChipSize.Small -> {
            this.then(Modifier.padding(vertical = 6.dp, horizontal = 8.dp))
        }

        ActionChipSize.XSmall -> {
            this.then(Modifier.padding(vertical = 4.dp, horizontal = 7.dp))
        }
    }

    internal fun Modifier.filterChipPadding(
        size: FilterChipSize
    ): Modifier = when (size) {
        FilterChipSize.Large -> {
            this.then(
                Modifier
                    .padding(vertical = 9.dp)
                    .padding(start = 12.dp, end = 10.dp)
            )
        }

        FilterChipSize.Medium -> {
            this.then(
                Modifier
                    .padding(vertical = 7.dp)
                    .padding(start = 11.dp, end = 9.dp)
            )
        }

        FilterChipSize.Small -> {
            this.then(
                Modifier
                    .padding(vertical = 6.dp)
                    .padding(start = 8.dp, end = 6.dp)
            )
        }

        FilterChipSize.XSmall -> {
            this.then(
                Modifier
                    .padding(vertical = 4.dp)
                    .padding(start = 7.dp, end = 5.dp)
            )
        }
    }


    internal fun Modifier.filterChipIconSize(
        size: FilterChipSize
    ): Modifier {
        val modifier = when (size) {
            FilterChipSize.Large -> Modifier.size(16.dp)
            FilterChipSize.Medium -> Modifier.size(16.dp)
            FilterChipSize.Small -> Modifier.size(14.dp)
            FilterChipSize.XSmall -> Modifier.size(12.dp)
        }

        return this.then(modifier)
    }

    internal fun Modifier.filterChipTextPadding(
        size: FilterChipSize
    ): Modifier {
        val modifier = when (size) {
            FilterChipSize.Large -> Modifier.padding(horizontal = 2.dp)
            FilterChipSize.Medium -> Modifier.padding(horizontal = 2.dp)
            FilterChipSize.Small -> Modifier.padding(horizontal = 2.dp)
            FilterChipSize.XSmall -> Modifier.padding(horizontal = 1.dp)
        }

        return this.then(modifier)
    }


    @Composable
    internal fun getFilterChipRadius(size: FilterChipSize) = when (size) {
        FilterChipSize.XSmall -> 6.dp
        FilterChipSize.Small -> 8.dp
        FilterChipSize.Medium -> 10.dp
        FilterChipSize.Large -> 10.dp
    }

    @Composable
    internal fun getFilterChipHorizontalArrangement(size: FilterChipSize) = when (size) {
        FilterChipSize.XSmall -> 1.dp
        FilterChipSize.Small -> 1.dp
        FilterChipSize.Medium -> 2.dp
        FilterChipSize.Large -> 2.dp
    }

}


