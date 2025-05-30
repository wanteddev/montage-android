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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.theme.DesignSystemTheme

/**
 * 사각형 형태의 스켈레톤 UI 컴포저블입니다.
 *
 * 로딩 상태를 표현할 때 사용되며, 모서리 곡률과 색상을 지정할 수 있고,
 * shimmer 애니메이션이 기본적으로 적용됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedSkeletonRectangle(
 *     modifier = Modifier.size(200.dp)
 * )
 * ```
 *
 * @param modifier Modifier: 레이아웃과 크기를 조절하는 Modifier입니다.
 * @param shape RoundedCornerShape: 사각형의 모서리 곡률입니다. 기본값은 3.dp입니다.
 * @param color Color: 스켈레톤의 배경 색상입니다. 기본값은 `R.color.fill_normal`입니다.
 */
@Composable
fun WantedSkeletonRectangle(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(3.dp),
    color: Color = colorResource(id = R.color.fill_normal)
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