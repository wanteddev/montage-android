package com.wanted.android.wanted.design.card

import androidx.compose.runtime.Composable


data class WantedCardDefault(
    val topContentSkeleton: Boolean = false,
    val captionSkeleton: Boolean = true,
    val extraCaptionSkeleton: Boolean = true,
    val bottomContentSkeleton: Boolean = false
)

object WantedCardDefaults {
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