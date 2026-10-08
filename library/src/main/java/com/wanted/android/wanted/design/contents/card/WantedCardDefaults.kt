package com.wanted.android.wanted.design.contents.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * data class WantedCardDefault
 *
 * 카드의 표시 스펙을 지정하는 데이터 클래스입니다.
 *
 * 스켈레톤 표시 여부와 카드 표면(배경·모양·여백) 설정을 함께 담습니다.
 *
 * @param topContentSkeleton Boolean: 상단 콘텐츠 영역에 스켈레톤을 표시할지 여부입니다.
 * @param captionSkeleton Boolean: 메인 캡션에 스켈레톤을 표시할지 여부입니다.
 * @param extraCaptionSkeleton Boolean: 추가 캡션에 스켈레톤을 표시할지 여부입니다.
 * @param bottomContentSkeleton Boolean: 하단 콘텐츠 영역에 스켈레톤을 표시할지 여부입니다.
 * @param ratio Float: 썸네일 스켈레톤의 비율입니다.
 * @param backgroundColor Color: 카드 표면 배경색입니다. 기본값 Transparent 는 배경을 그리지 않는 것과 같습니다.
 * @param shape Shape: 배경 모양입니다.
 * @param border BorderStroke?: 카드 표면 테두리입니다. null 이면 테두리를 그리지 않습니다. 배경 위에 그려지므로 modifier 에 직접 border 를 주면 배경에 가려집니다.
 * @param interactionShape Shape: 클릭·리플 모양입니다.
 * @param contentPadding PaddingValues: 카드 표면 안쪽 여백입니다. modifier 의 padding 은 클릭 영역 바깥 마진이 되므로 안쪽 여백은 이 값으로 줍니다.
 */
data class WantedCardDefault(
    val topContentSkeleton: Boolean = false,
    val captionSkeleton: Boolean = true,
    val extraCaptionSkeleton: Boolean = true,
    val bottomContentSkeleton: Boolean = false,
    val ratio: Float = 4 / 3f,
    val backgroundColor: Color = Color.Transparent,
    val shape: Shape = RectangleShape,
    val border: BorderStroke? = null,
    val interactionShape: Shape = RoundedCornerShape(12.dp),
    val contentPadding: PaddingValues = PaddingValues(0.dp)
)

/**
 * object WantedCardDefaults
 *
 */
object WantedCardDefaults {

    /**
     * fun getDefault(...)
     *
     * 기본 스켈레톤 설정값을 반환하는 Compose 함수입니다.
     *
     * 각 항목에 대해 스켈레톤 UI 표시 여부를 설정할 수 있습니다.
     *
     * 사용 예시:
     * ```kotlin
     * val config = WantedCardDefaults.getDefault(
     *     topContentSkeleton = true,
     *     bottomContentSkeleton = true
     * )
     * ```
     *
     * @param topContentSkeleton Boolean: 상단 콘텐츠 영역에 스켈레톤을 표시할지 여부입니다. 기본값은 false입니다.
     * @param captionSkeleton Boolean: 메인 캡션에 스켈레톤을 표시할지 여부입니다. 기본값은 true입니다.
     * @param extraCaptionSkeleton Boolean: 추가 캡션에 스켈레톤을 표시할지 여부입니다. 기본값은 true입니다.
     * @param bottomContentSkeleton Boolean: 하단 콘텐츠 영역에 스켈레톤을 표시할지 여부입니다. 기본값은 false입니다.
     * @param backgroundColor Color: 카드 표면 배경색입니다. 기본값 Transparent 는 배경을 그리지 않는 것과 같습니다.
     * @param shape Shape: 배경 모양입니다.
     * @param border BorderStroke?: 카드 표면 테두리입니다. null 이면 테두리를 그리지 않습니다. 배경 위에 그려지므로 modifier 에 직접 border 를 주면 배경에 가려집니다.
     * @param interactionShape Shape: 클릭·리플 모양입니다.
     * @param contentPadding PaddingValues: 카드 표면 안쪽 여백입니다. modifier 의 padding 은 클릭 영역 바깥 마진이 되므로 안쪽 여백은 이 값으로 줍니다.
     * @return WantedCardDefault: 스켈레톤 설정 정보가 담긴 데이터 클래스입니다.
     */
    @Composable
    fun getDefault(
        topContentSkeleton: Boolean = false,
        captionSkeleton: Boolean = true,
        extraCaptionSkeleton: Boolean = true,
        bottomContentSkeleton: Boolean = false,
        backgroundColor: Color = Color.Transparent,
        shape: Shape = RectangleShape,
        border: BorderStroke? = null,
        interactionShape: Shape = RoundedCornerShape(12.dp),
        contentPadding: PaddingValues = PaddingValues(0.dp)
    ) = WantedCardDefault(
        topContentSkeleton = topContentSkeleton,
        captionSkeleton = captionSkeleton,
        extraCaptionSkeleton = extraCaptionSkeleton,
        bottomContentSkeleton = bottomContentSkeleton,
        backgroundColor = backgroundColor,
        shape = shape,
        border = border,
        interactionShape = interactionShape,
        contentPadding = contentPadding
    )
}
