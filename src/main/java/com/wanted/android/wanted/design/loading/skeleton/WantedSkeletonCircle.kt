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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.theme.DesignSystemTheme


/**
 * 원형 스켈레톤 UI를 구성하는 컴포저블입니다.
 *
 * 내부적으로 `WantedSkeletonRectangle`을 `CircleShape`로 설정하여 사용합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedSkeletonCircle(modifier = Modifier.size(100.dp))
 * ```
 *
 * @param modifier Modifier: 외형 및 배치를 조정합니다.
 * @param color Color: 배경 색상입니다.
 */
@Composable
fun WantedSkeletonCircle(
    modifier: Modifier = Modifier,
    color: Color = colorResource(id = R.color.fill_normal)
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