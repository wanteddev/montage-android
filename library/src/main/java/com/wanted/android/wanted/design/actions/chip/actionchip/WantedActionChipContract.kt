package com.wanted.android.wanted.design.actions.chip.actionchip

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * object WantedActionChipContract
 *
 * ActionChip에 대한 전반적인 계약(Contract)을 정의하는 객체입니다.
 *
 * 이 객체는 ActionChip의 시각적 변형 및 크기에 대한 enum 클래스를 포함합니다.
 */
object WantedActionChipContract {
    /**
     * enum class ActionChipVariant
     *
     * WantedActionChip의 시각적 스타일을 정의하는 enum 클래스입니다.
     *
     * 다음과 같은 두 가지 스타일 옵션을 제공합니다:
     * - Solid: 배경이 채워진 형태입니다.
     * - Outlined: 테두리만 있는 형태입니다.
     *
     * @see WantedActionChip
     */
    enum class ActionChipVariant {
        Solid, Outlined
    }

    /**
     * enum class ActionChipSize
     *
     * WantedActionChip의 크기를 정의하는 enum 클래스입니다.
     *
     * 각 사이즈는 패딩, 아이콘 크기, 텍스트 간격 등에 영향을 미칩니다.
     * 제공되는 크기 옵션은 다음과 같습니다:
     * - Large: 큰 사이즈입니다.
     * - Medium: 중간 사이즈입니다.
     * - Small: 작은 사이즈입니다.
     * - XSmall: 가장 작은 사이즈입니다.
     *
     * @see WantedActionChip
     */
    enum class ActionChipSize {
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


    internal fun Modifier.actionChipIconSize(
        size: ActionChipSize
    ): Modifier {
        val modifier = when (size) {
            ActionChipSize.Large -> Modifier.size(16.dp)
            ActionChipSize.Medium -> Modifier.size(14.dp)
            ActionChipSize.Small -> Modifier.size(14.dp)
            ActionChipSize.XSmall -> Modifier.size(12.dp)
        }

        return this.then(modifier)
    }


    internal fun Modifier.actionChipTextPadding(
        size: ActionChipSize
    ): Modifier {
        val modifier = when (size) {
            ActionChipSize.Large -> Modifier.padding(horizontal = 2.dp)
            ActionChipSize.Medium -> Modifier.padding(horizontal = 2.dp)
            ActionChipSize.Small -> Modifier.padding(horizontal = 2.dp)
            ActionChipSize.XSmall -> Modifier.padding(horizontal = 1.dp)
        }

        return this.then(modifier)
    }


    @Composable
    internal fun getActionChipRadius(size: ActionChipSize) = when (size) {
        ActionChipSize.XSmall -> 6.dp
        ActionChipSize.Small -> 8.dp
        ActionChipSize.Medium -> 10.dp
        ActionChipSize.Large -> 10.dp
    }

    @Composable
    internal fun getActionChipHorizontalArrangement(size: ActionChipSize) = when (size) {
        ActionChipSize.XSmall -> 2.dp
        ActionChipSize.Small -> 2.dp
        ActionChipSize.Medium -> 3.dp
        ActionChipSize.Large -> 3.dp
    }
}



