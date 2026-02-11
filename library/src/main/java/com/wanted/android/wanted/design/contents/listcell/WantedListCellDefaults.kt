package com.wanted.android.wanted.design.contents.listcell

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * object WantedListCellDefaults
 *
 * WantedListCell 컴포넌트에 사용되는 수직 패딩 및 인터랙션 패딩 관련 설정을 정의하는 객체입니다.
 */
object WantedListCellDefaults {

    /**
     * enum class VerticalPadding
     *
     * WantedListCell 세로 방향 패딩 크기를 정의하는 enum 클래스입니다.
     *
     * 각 값은 셀의 상하 여백을 조정하며, 셀의 전체 높이에 영향을 줍니다.
     * 제공되는 옵션은 다음과 같습니다:
     * - None: 패딩 없음 (0dp)입니다.
     * - Small: 8dp 패딩입니다.
     * - Medium: 12dp 패딩입니다.
     * - Large: 16dp 패딩입니다.
     *
     * @property value Dp: 적용되는 세로 패딩 값입니다.
     */
    enum class VerticalPadding(val value: Dp) {
        None(0.dp),
        Small(8.dp),
        Medium(12.dp),
        Large(16.dp)
    }

    /**
     * sealed class InteractionPadding
     *
     * Cell 내부의 상호작용 요소(e.g. 클릭 영역)에 적용되는 패딩 값을 정의합는 sealed 클래스입니다.
     *
     * - Default: fillWidth 옵션에 따라 12dp 또는 20dp를 적용합니다.
     * - Custom: 개발자가 직접 패딩 값을 지정할 수 있습니다.
     *
     * @property padding Dp: 상호작용 영역에 적용되는 패딩 값입니다.
     */
    sealed class InteractionPadding(open val padding: Dp) {

        /**
         * data class Default
         *
         * fillWidth 값에 따라 기본 패딩을 지정합니다.
         *
         * @param fillWidth Boolean: true일 경우 20dp, false일 경우 12dp 패딩이 적용됩니다.
         */
        data class Default(
            val fillWidth: Boolean = false
        ) : InteractionPadding(if (fillWidth) 20.dp else 12.dp)

        /**
         * data class Custom
         *
         * 개발자가 원하는 패딩 값을 직접 설정할 수 있습니다.
         *
         * @param padding Dp: 사용자 지정 패딩 값입니다. 기본값은 0dp입니다.
         */
        data class Custom(override val padding: Dp = 0.dp) : InteractionPadding(padding)
    }
}