package com.wanted.android.wanted.design.contents.card

import androidx.compose.runtime.Composable

/**
 * data class WantedCardDefault
 *
 * 스켈레톤 표시 여부를 지정하는 데이터 클래스입니다.
 *
 * 각 항목별로 스켈레톤 표시 여부를 설정합니다.
 *
 * @param topContentSkeleton Boolean: 상단 콘텐츠 영역에 스켈레톤을 표시할지 여부입니다.
 * @param captionSkeleton Boolean: 메인 캡션에 스켈레톤을 표시할지 여부입니다.
 * @param extraCaptionSkeleton Boolean: 추가 캡션에 스켈레톤을 표시할지 여부입니다.
 * @param bottomContentSkeleton Boolean: 하단 콘텐츠 영역에 스켈레톤을 표시할지 여부입니다.
 * @param ratio Float: 썸네일 스켈레톤의 비율입니다.
 */
data class WantedCardDefault(
    val topContentSkeleton: Boolean = false,
    val captionSkeleton: Boolean = true,
    val extraCaptionSkeleton: Boolean = true,
    val bottomContentSkeleton: Boolean = false,
    val ratio: Float = 4 / 3f
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
     * @return WantedCardDefault: 스켈레톤 설정 정보가 담긴 데이터 클래스입니다.
     */
    @Composable
    fun getDefault(
        topContentSkeleton: Boolean = false,
        captionSkeleton: Boolean = true,
        extraCaptionSkeleton: Boolean = true,
        bottomContentSkeleton: Boolean = false
    ) = WantedCardDefault(
        topContentSkeleton = topContentSkeleton,
        captionSkeleton = captionSkeleton,
        extraCaptionSkeleton = extraCaptionSkeleton,
        bottomContentSkeleton = bottomContentSkeleton
    )
}