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
 * 셀 표면(배경색·모양·테두리·안쪽 여백) 스펙을 한 덩어리로 묶어 전달하는 데이터 클래스입니다.
 *
 * `modifier` 는 셀 최외곽(클릭·리플 영역 바깥) 노드에 적용되므로, `modifier` 에 배경·테두리·안쪽 여백을 주면
 * 인터랙션 영역과 어긋납니다. 표면 표현은 모두 이 객체로 지정합니다.
 *
 * 좌우 패딩·인터랙션 영역·radius 는 표면이 아니라 [WantedListCellDefaults.Variant] 가 정합니다.
 *
 * @param backgroundColor Color: 셀 표면 배경색입니다. 기본값 Transparent 는 배경을 그리지 않는 것과 같습니다.
 * @param shape Shape: 배경 모양입니다.
 * @param border BorderStroke?: 셀 표면 테두리입니다. null 이면 테두리를 그리지 않습니다. 배경 위에 그려지므로 modifier 에 직접 border 를 주면 배경에 가려집니다.
 * @param contentPadding PaddingValues: 셀 콘텐츠 안쪽 여백입니다. modifier 의 padding 은 클릭 영역 바깥 마진이 되므로 안쪽 여백은 이 값으로 줍니다.
 */
data class WantedListCellDefault(
    val backgroundColor: Color = Color.Transparent,
    val shape: Shape = RectangleShape,
    val border: BorderStroke? = null,
    val contentPadding: PaddingValues = PaddingValues(0.dp)
)

/**
 * object WantedListCellDefaults
 *
 * WantedListCell 컴포넌트에 사용되는 variant·수직 패딩·표면 스펙 설정을 정의하는 객체입니다.
 */
object WantedListCellDefaults {

    /**
     * enum class Variant
     *
     * WantedListCell 의 좌우 패딩·인터랙션 영역·radius 를 하나로 묶어 정의하는 enum 클래스입니다.
     *
     * 기준은 셀이 아니라 셀을 담는 **리스트(컨테이너)의 가장자리**입니다.
     * 두 variant 모두 콘텐츠가 놓이는 위치는 같고, 셀이 폭을 어디까지 차지하는지와
     * 인터랙션 영역이 어디까지 번지는지만 다릅니다.
     *
     * - [Inset]: 셀이 콘텐츠 폭을 갖고 **리스트가 좌우 여백을 준다.** 인터랙션은 셀보다 좌우로 12dp 넓고 radius 16dp 입니다.
     * - [Full]: 셀이 **리스트 폭을 채우고** 내부 좌우 패딩 20dp 를 갖는다. 인터랙션은 셀과 동일하며 radius 0dp 입니다.
     *
     * 커스텀 값은 제공하지 않습니다. 다른 수치가 필요하면 이 enum 에 variant 를 추가합니다.
     *
     * @property horizontalPadding Dp: 셀 내부 좌우 패딩입니다. divider 좌우 여백에도 같은 값을 적용합니다.
     * @property interactionOutset Dp: 인터랙션 영역이 셀보다 좌우로 넓어지는 값입니다.
     * @property interactionRadius Dp: 인터랙션 영역의 corner radius 입니다.
     */
    enum class Variant(
        val horizontalPadding: Dp,
        val interactionOutset: Dp,
        val interactionRadius: Dp,
    ) {
        Inset(
            horizontalPadding = 0.dp,
            interactionOutset = 12.dp,
            interactionRadius = 16.dp
        ),
        Full(
            horizontalPadding = 20.dp,
            interactionOutset = 0.dp,
            interactionRadius = 0.dp
        )
    }

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
     * fun getDefault(...)
     *
     * 셀 표면 스펙 설정값을 반환하는 Compose 함수입니다.
     *
     * 사용 예시:
     * ```kotlin
     * val surface = WantedListCellDefaults.getDefault(
     *     backgroundColor = DesignSystemTheme.colors.backgroundNeutralSecondary,
     *     shape = RoundedCornerShape(20.dp),
     *     contentPadding = PaddingValues(horizontal = 20.dp)
     * )
     * ```
     *
     * @param backgroundColor Color: 셀 표면 배경색입니다. 기본값 Transparent 는 배경을 그리지 않는 것과 같습니다.
     * @param shape Shape: 배경 모양입니다.
     * @param border BorderStroke?: 셀 표면 테두리입니다. null 이면 테두리를 그리지 않습니다.
     * @param contentPadding PaddingValues: 셀 콘텐츠 안쪽 여백입니다.
     * @return WantedListCellDefault: 셀 표면 스펙이 담긴 데이터 클래스입니다.
     */
    @Composable
    fun getDefault(
        backgroundColor: Color = Color.Transparent,
        shape: Shape = RectangleShape,
        border: BorderStroke? = null,
        contentPadding: PaddingValues = PaddingValues(0.dp)
    ) = WantedListCellDefault(
        backgroundColor = backgroundColor,
        shape = shape,
        border = border,
        contentPadding = contentPadding
    )
}
