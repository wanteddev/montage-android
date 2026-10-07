package com.wanted.android.wanted.design.actions.chip

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * object WantedChipContract
 *
 * Chip에 대한 전반적인 계약(Contract)을 정의하는 객체입니다.
 *
 * 이 객체는 Chip의 시각적 변형 및 크기에 대한 enum 클래스를 포함합니다.
 */
object WantedChipContract {
    /**
     * enum class ChipVariant
     *
     * WantedChip의 시각적 스타일을 정의하는 enum 클래스입니다.
     *
     * 다음과 같은 두 가지 스타일 옵션을 제공합니다:
     * - Solid: 배경이 채워진 형태입니다.
     * - Outlined: 테두리만 있는 형태입니다.
     *
     * @see WantedChip
     */
    enum class ChipVariant {
        Solid, Outlined
    }

    /**
     * enum class ChipSize
     *
     * WantedChip의 크기를 정의하는 enum 클래스입니다.
     *
     * 각 사이즈는 패딩, 아이콘 크기, 텍스트 간격 등에 영향을 미칩니다.
     * 제공되는 크기 옵션은 다음과 같습니다:
     * - Large: 큰 사이즈입니다.
     * - Medium: 중간 사이즈입니다.
     * - Small: 작은 사이즈입니다.
     * - XSmall: 가장 작은 사이즈입니다.
     *
     * @see WantedChip
     */
    enum class ChipSize {
        Large, Medium, Small, XSmall
    }


    internal fun Modifier.chipPadding(
        size: ChipSize
    ): Modifier = when (size) {
        ChipSize.Large -> {
            this.then(Modifier.padding(vertical = 10.dp, horizontal = 12.dp))
        }

        ChipSize.Medium -> {
            this.then(Modifier.padding(vertical = 9.dp, horizontal = 10.dp))
        }

        ChipSize.Small -> {
            this.then(Modifier.padding(vertical = 8.dp, horizontal = 8.dp))
        }

        ChipSize.XSmall -> {
            this.then(Modifier.padding(vertical = 5.dp, horizontal = 6.dp))
        }
    }

    // Size별 Chip 의 기준 높이입니다.
    //
    // Figma 는 높이를 고정값으로 정의하므로 이 값을 최소 높이(defaultMinSize)로 사용합니다.
    // 시스템 폰트 확대 시에는 텍스트가 잘리지 않도록 이 값보다 커질 수 있습니다.
    internal fun getChipHeight(size: ChipSize) = when (size) {
        ChipSize.Large -> 40.dp
        ChipSize.Medium -> 36.dp
        ChipSize.Small -> 32.dp
        ChipSize.XSmall -> 24.dp
    }


    internal fun Modifier.chipIconSize(
        size: ChipSize
    ): Modifier {
        val modifier = when (size) {
            ChipSize.Large -> Modifier.size(16.dp)
            ChipSize.Medium -> Modifier.size(14.dp)
            ChipSize.Small -> Modifier.size(14.dp)
            ChipSize.XSmall -> Modifier.size(12.dp)
        }

        return this.then(modifier)
    }


    /** 텍스트 좌우 패딩입니다. 전 사이즈 공통 2dp 입니다. */
    internal fun Modifier.chipTextPadding(): Modifier =
        this.then(Modifier.padding(horizontal = 2.dp))


    @Composable
    internal fun getChipRadius(size: ChipSize) = when (size) {
        ChipSize.XSmall -> 8.dp
        ChipSize.Small -> 10.dp
        ChipSize.Medium -> 10.dp
        ChipSize.Large -> 12.dp
    }

    /** 아이콘 ↔ 텍스트 간격입니다. XSmall 만 0dp 입니다. */
    @Composable
    internal fun getChipHorizontalArrangement(size: ChipSize) = when (size) {
        ChipSize.XSmall -> 0.dp
        ChipSize.Small -> 2.dp
        ChipSize.Medium -> 2.dp
        ChipSize.Large -> 2.dp
    }
}



