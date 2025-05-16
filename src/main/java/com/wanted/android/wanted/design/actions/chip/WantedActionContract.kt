package com.wanted.android.wanted.design.actions.chip

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.actions.chip.WantedActionContract.ChipActionSize

/**
 * object WantedActionContract
 *
 * ChipAction에 대한 전반적인 계약(Contract)을 정의하는 객체입니다.
 *
 * 이 객체는 ChipAction의 시각적 변형 및 크기에 대한 enum 클래스를 포함합니다.
 */
object WantedActionContract {
    /**
     * enum class ChipActionVariant
     *
     * ChipAction의 시각적 스타일을 정의하는 enum 클래스입니다.
     *
     * 다음과 같은 두 가지 스타일 옵션을 제공합니다:
     * - Solid: 배경이 채워진 형태
     * - Outlined: 테두리만 있는 형태
     *
     * @see WantedActionChip
     */
    enum class ChipActionVariant {
        Solid, Outlined
    }

    /**
     * enum class ChipActionSize
     *
     * ChipAction의 크기를 정의하는 enum 클래스입니다.
     *
     * 각 사이즈는 패딩, 아이콘 크기, 텍스트 간격 등에 영향을 미칩니다.
     * 제공되는 크기 옵션은 다음과 같습니다:
     * - Large: 큰 사이즈
     * - Medium: 중간 사이즈
     * - Small: 작은 사이즈
     * - XSmall: 가장 작은 사이즈
     *
     * @see WantedActionChip
     */
    enum class ChipActionSize {
        Large, Medium, Small, XSmall
    }
}


internal fun Modifier.actionChipPadding(
    size: ChipActionSize
): Modifier = when (size) {
    ChipActionSize.Large -> {
        this.then(Modifier.padding(vertical = 9.dp, horizontal = 12.dp))
    }

    ChipActionSize.Medium -> {
        this.then(Modifier.padding(vertical = 7.dp, horizontal = 11.dp))
    }

    ChipActionSize.Small -> {
        this.then(Modifier.padding(vertical = 6.dp, horizontal = 8.dp))
    }

    ChipActionSize.XSmall -> {
        this.then(Modifier.padding(vertical = 4.dp, horizontal = 7.dp))
    }
}

internal fun Modifier.filterChipPadding(
    size: ChipActionSize
): Modifier = when (size) {
    ChipActionSize.Large -> {
        this.then(
            Modifier
                .padding(vertical = 9.dp)
                .padding(start = 12.dp, end = 10.dp)
        )
    }

    ChipActionSize.Medium -> {
        this.then(
            Modifier
                .padding(vertical = 7.dp)
                .padding(start = 11.dp, end = 9.dp)
        )
    }

    ChipActionSize.Small -> {
        this.then(
            Modifier
                .padding(vertical = 6.dp)
                .padding(start = 8.dp, end = 6.dp)
        )
    }

    ChipActionSize.XSmall -> {
        this.then(
            Modifier
                .padding(vertical = 4.dp)
                .padding(start = 7.dp, end = 5.dp)
        )
    }
}

internal fun Modifier.actionChipIconSize(
    size: ChipActionSize
): Modifier {
    val modifier = when (size) {
        ChipActionSize.Large -> Modifier.size(16.dp)
        ChipActionSize.Medium -> Modifier.size(14.dp)
        ChipActionSize.Small -> Modifier.size(14.dp)
        ChipActionSize.XSmall -> Modifier.size(12.dp)
    }

    return this.then(modifier)
}

internal fun Modifier.filterChipIconSize(
    size: ChipActionSize
): Modifier {
    val modifier = when (size) {
        ChipActionSize.Large -> Modifier.size(16.dp)
        ChipActionSize.Medium -> Modifier.size(16.dp)
        ChipActionSize.Small -> Modifier.size(14.dp)
        ChipActionSize.XSmall -> Modifier.size(12.dp)
    }

    return this.then(modifier)
}

internal fun Modifier.actionChipTextPadding(
    size: ChipActionSize
): Modifier {
    val modifier = when (size) {
        ChipActionSize.Large -> Modifier.padding(horizontal = 2.dp)
        ChipActionSize.Medium -> Modifier.padding(horizontal = 2.dp)
        ChipActionSize.Small -> Modifier.padding(horizontal = 2.dp)
        ChipActionSize.XSmall -> Modifier.padding(horizontal = 1.dp)
    }

    return this.then(modifier)
}


@Composable
internal fun getChipRadius(size: ChipActionSize) = when (size) {
    ChipActionSize.XSmall -> 6.dp
    ChipActionSize.Small -> 8.dp
    ChipActionSize.Medium -> 10.dp
    ChipActionSize.Large -> 10.dp
}

@Composable
internal fun getActionChipHorizontalArrangement(size: ChipActionSize) = when (size) {
    ChipActionSize.XSmall -> 2.dp
    ChipActionSize.Small -> 2.dp
    ChipActionSize.Medium -> 3.dp
    ChipActionSize.Large -> 3.dp
}

@Composable
internal fun getFilterChipHorizontalArrangement(size: ChipActionSize) = when (size) {
    ChipActionSize.XSmall -> 1.dp
    ChipActionSize.Small -> 1.dp
    ChipActionSize.Medium -> 2.dp
    ChipActionSize.Large -> 2.dp
}


