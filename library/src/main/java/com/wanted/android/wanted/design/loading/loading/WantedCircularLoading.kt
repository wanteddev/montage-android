package com.wanted.android.wanted.design.loading.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * WantedCircularLoading
 *
 * 화면 중앙에 원형으로 로딩 상태를 안내하는 컴포넌트입니다.
 *
 * dim 배경 포함 여부, 크기, 색상을 설정할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedCircularLoading(
 *     size = 32.dp,
 *     circleColor = Color.Gray,
 *     dimColor = Color.Black.copy(alpha = 0.3f)
 * )
 * ```
 *
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param circleColor Color: 로딩 인디케이터 색상입니다.
 * @param dimColor Color: 배경 dim 색상입니다.
 * @param size Dp: 로딩 인디케이터 크기입니다.
 */
@Composable
fun WantedCircularLoading(
    modifier: Modifier = Modifier,
    circleColor: Color = DesignSystemTheme.colors.lineSolidNormal,
    dimColor: Color = Color.Transparent,
    size: Dp = 24.dp
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = dimColor
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            WantedCircularProgressIndicator(modifier = Modifier.size(size), color = circleColor)
        }
    }
}


@DevicePreviews
@Composable
private fun WantedCircularLoadingPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            WantedCircularLoading()
        }
    }
}

