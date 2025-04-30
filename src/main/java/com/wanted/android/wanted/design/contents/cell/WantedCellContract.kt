package com.wanted.android.wanted.design.contents.cell

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * WantedCell의 세로 방향 패딩 크기를 정의하는 enum 클래스입니다.
 *
 * 각 값은 셀의 상하 여백을 조정하며, 셀의 전체 높이에 영향을 줍니다.
 *
 * 사용 예시 :
 * ```kotlin
 * WantedCell(
 *     text = "텍스트",
 *     verticalPadding = WantedCellContract.VerticalPadding.Small
 * )
 * ```
 */
object WantedCellContract {
    enum class VerticalPadding(val value: Dp) {
        None(0.dp),
        Small(8.dp),
        Medium(12.dp),
        Large(16.dp)
    }

    sealed class InteractionPadding(open val padding: Dp) {
        data class Default(
            val fillWidth: Boolean = false
        ) : InteractionPadding(if (fillWidth) 20.dp else 12.dp)
        data class Custom(override val padding: Dp = 0.dp) : InteractionPadding(padding)
    }
}