package com.wanted.android.wanted.design.contents.card

import androidx.compose.runtime.Composable

/**
 * data class WantedCardDefault
 *
 * 카드 UI의 스켈레톤 표시 여부를 지정하는 데이터 클래스입니다.
 *
 * 각 항목별로 스켈레톤을 표시할지 여부를 설정합니다.
 *
 * @property topContentSkeleton 상단 콘텐츠 영역에 스켈레톤을 표시할지 여부
 * @property captionSkeleton 메인 캡션에 스켈레톤을 표시할지 여부
 * @property extraCaptionSkeleton 추가 캡션에 스켈레톤을 표시할지 여부
 * @property bottomContentSkeleton 하단 콘텐츠 영역에 스켈레톤을 표시할지 여부
 * @property ratio 썸네일 스켈레톤의 비율
 */
data class WantedCardDefault(
    val topContentSkeleton: Boolean = false,
    val captionSkeleton: Boolean = true,
    val extraCaptionSkeleton: Boolean = true,
    val bottomContentSkeleton: Boolean = false,
    val ratio: Float = 4 / 3f
)

/**
 *
 * object WantedCardDefaults
 *
 * 카드 컴포넌트의 스켈레톤 UI 기본 설정을 제공하는 객체입니다.
 *
 * 로딩 상태에서 표시할 스켈레톤 UI의 구성 요소별 표시 여부를 설정할 수 있습니다.
 */
object WantedCardDefaults {

    /**
     * 기본 스켈레톤 설정값을 반환합니다.
     *
     * 각 항목에 대해 스켈레톤 UI를 표시할지 여부를 설정할 수 있습니다.
     *
     * 사용 예시:
     * ```kotlin
     * val config = WantedCardDefaults.getDefault(
     *     topContentSkeleton = true,
     *     bottomContentSkeleton = true
     * )
     * ```
     *
     * @param topContentSkeleton 상단 콘텐츠 영역에 스켈레톤을 표시할지 여부 (기본값: false)
     * @param captionSkeleton 메인 캡션에 스켈레톤을 표시할지 여부 (기본값: true)
     * @param extraCaptionSkeleton 추가 캡션에 스켈레톤을 표시할지 여부 (기본값: true)
     * @param bottomContentSkeleton 하단 콘텐츠 영역에 스켈레톤을 표시할지 여부 (기본값: false)
     * @return [WantedCardDefault] 스켈레톤 설정 정보가 담긴 데이터 클래스
     */
    @Composable
    fun getDefault(
        topContentSkeleton: Boolean = false,
        captionSkeleton: Boolean = true,
        extraCaptionSkeleton: Boolean = true,
        bottomContentSkeleton: Boolean = false
    ): WantedCardDefault = WantedCardDefault(
        topContentSkeleton = topContentSkeleton,
        captionSkeleton = captionSkeleton,
        extraCaptionSkeleton = extraCaptionSkeleton,
        bottomContentSkeleton = bottomContentSkeleton
    )
}