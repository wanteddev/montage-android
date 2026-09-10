package com.wanted.android.wanted.design.contents.listcell

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * data class WantedListCellDefault
 *
 * 셀의 스펙을 한 덩어리로 묶어 전달하는 데이터 클래스입니다.
 *
 * 배경·모양·테두리·안쪽 여백에 더해 디자인 시스템 패딩 스펙(verticalPadding·interactionPadding)을 함께 담습니다.
 *
 * 셀에 적용되는 모든 여백의 단일 소스이므로, 컴포넌트 파라미터로 패딩을 따로 넘겨 값이 겹치는 일이 없습니다.
 *
 * @param backgroundColor Color: 셀 표면 배경색입니다. 기본값 Transparent 는 배경을 그리지 않는 것과 같습니다.
 * @param shape Shape: 배경 모양입니다.
 * @param border BorderStroke?: 셀 표면 테두리입니다. null 이면 테두리를 그리지 않습니다. 배경 위에 그려지므로 modifier 에 직접 border 를 주면 배경에 가려집니다.
 * @param interactionShape Shape?: 클릭·리플 모양입니다. null 이면 fillWidth 에 따라 자동으로 정해집니다.
 * @param contentPadding PaddingValues: 셀 콘텐츠 안쪽 여백입니다. modifier 의 padding 은 클릭 영역 바깥 마진이 되므로 안쪽 여백은 이 값으로 줍니다.
 * @param verticalPadding WantedListCellDefaults.VerticalPadding: 셀 상하 패딩 스펙입니다.
 * @param interactionPadding WantedListCellDefaults.InteractionPadding: 터치 영역 좌우 여백 스펙입니다. Default 는 컴포넌트의 fillWidth 값으로 해석됩니다.
 */
data class WantedListCellDefault(
    val backgroundColor: Color = Color.Transparent,
    val shape: Shape = RectangleShape,
    val border: BorderStroke? = null,
    val interactionShape: Shape? = null,
    val contentPadding: PaddingValues = PaddingValues(0.dp),
    val verticalPadding: WantedListCellDefaults.VerticalPadding = WantedListCellDefaults.VerticalPadding.Medium,
    val interactionPadding: WantedListCellDefaults.InteractionPadding = WantedListCellDefaults.InteractionPadding.Default()
)

/**
 * object WantedListCellDefaults
 *
 * WantedListCell 컴포넌트에 사용되는 수직 패딩 및 인터랙션 패딩 관련 설정을 정의하는 객체입니다.
 */
object WantedListCellDefaults {

    /**
     * fun getDefault(...)
     *
     * 셀 스펙 설정값을 반환하는 Compose 함수입니다.
     *
     * 표면(배경·모양·테두리·안쪽 여백)과 디자인 시스템 패딩 스펙을 한 번에 지정합니다.
     *
     * 사용 예시:
     * ```kotlin
     * val config = WantedListCellDefaults.getDefault(
     *     backgroundColor = DesignSystemTheme.colors.backgroundNormalAlternative,
     *     shape = RoundedCornerShape(20.dp),
     *     contentPadding = PaddingValues(horizontal = 20.dp),
     *     verticalPadding = WantedListCellDefaults.VerticalPadding.Large
     * )
     * ```
     *
     * @param backgroundColor Color: 셀 표면 배경색입니다. 기본값 Transparent 는 배경을 그리지 않는 것과 같습니다.
     * @param shape Shape: 배경 모양입니다.
     * @param border BorderStroke?: 셀 표면 테두리입니다. null 이면 테두리를 그리지 않습니다.
     * @param interactionShape Shape?: 클릭·리플 모양입니다. null 이면 fillWidth 에 따라 자동으로 정해집니다.
     * @param contentPadding PaddingValues: 셀 콘텐츠 안쪽 여백입니다.
     * @param verticalPadding VerticalPadding: 셀 상하 패딩 스펙입니다.
     * @param interactionPadding InteractionPadding: 터치 영역 좌우 여백 스펙입니다. Default 는 컴포넌트의 fillWidth 값으로 해석됩니다.
     * @return WantedListCellDefault: 셀 스펙이 담긴 데이터 클래스입니다.
     */
    @Composable
    fun getDefault(
        backgroundColor: Color = Color.Transparent,
        shape: Shape = RectangleShape,
        border: BorderStroke? = null,
        interactionShape: Shape? = null,
        contentPadding: PaddingValues = PaddingValues(0.dp),
        verticalPadding: VerticalPadding = VerticalPadding.Medium,
        interactionPadding: InteractionPadding = InteractionPadding.Default()
    ) = WantedListCellDefault(
        backgroundColor = backgroundColor,
        shape = shape,
        border = border,
        interactionShape = interactionShape,
        contentPadding = contentPadding,
        verticalPadding = verticalPadding,
        interactionPadding = interactionPadding
    )

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
         * WantedListCellDefault 에 담겨 전달될 때는 fillWidth 를 알 수 없으므로, 컴포넌트가 실제 fillWidth 값으로 다시 해석합니다.
         * 값을 직접 고정하려면 Custom 을 사용합니다.
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
