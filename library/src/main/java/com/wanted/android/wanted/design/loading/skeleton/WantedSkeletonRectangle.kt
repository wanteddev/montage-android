package com.wanted.android.wanted.design.loading.skeleton

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * WantedSkeletonRectangle
 *
 * 사각형 Skeleton 컴포넌트입니다.
 *
 * Shimmer 애니메이션이 자동으로 적용됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedSkeletonRectangle(
 *     modifier = Modifier.size(200.dp, 50.dp),
 *     shape = RoundedCornerShape(8.dp)
 * )
 * ```
 *
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param shape RoundedCornerShape: 사각형의 모서리 곡률입니다.
 * @param color Color: 스켈레톤의 배경 색상입니다.
 */
@Composable
fun WantedSkeletonRectangle(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(3.dp),
    color: Color = DesignSystemTheme.colors.fillNormal
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(color = color)
            .shimmer()
    )
}

@DevicePreviews
@Composable
private fun WantedSkeletonRectanglePreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedSkeletonRectangle(
                    modifier = Modifier.size(200.dp)
                )
            }
        }
    }
}