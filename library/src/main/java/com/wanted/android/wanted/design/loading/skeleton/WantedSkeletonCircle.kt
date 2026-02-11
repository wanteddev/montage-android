package com.wanted.android.wanted.design.loading.skeleton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews


/**
 * WantedSkeletonCircle
 *
 * 원형 Skeleton 컴포넌트입니다.
 *
 * 내부적으로 WantedSkeletonRectangle을 CircleShape로 설정하여 사용합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedSkeletonCircle(
 *     modifier = Modifier.size(100.dp)
 * )
 * ```
 *
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param color Color: 배경 색상입니다.
 */
@Composable
fun WantedSkeletonCircle(
    modifier: Modifier = Modifier,
    color: Color = DesignSystemTheme.colors.fillNormal
) {
    WantedSkeletonRectangle(
        modifier = modifier,
        shape = CircleShape,
        color = color
    )
}

@DevicePreviews
@Composable
private fun WantedSkeletonCirclePreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedSkeletonCircle(
                    modifier = Modifier.size(200.dp)
                )
            }
        }
    }
}