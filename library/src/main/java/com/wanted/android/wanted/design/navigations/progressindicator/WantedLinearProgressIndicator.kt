package com.wanted.android.wanted.design.navigations.progressindicator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews


/**
 * WantedLinearProgressIndicator
 *
 * 진행 상태를 나타내는 선형 Progress indicator 컴포넌트입니다.
 *
 * 0.0~1.0 사이의 값으로 진행률을 표시하며, 높이는 2dp로 고정됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * var progress by remember { mutableFloatStateOf(0.3f) }
 *
 * WantedLinearProgressIndicator(
 *     currentProgress = progress
 * )
 * ```
 *
 * @param currentProgress Float: 진행률입니다. 0.0은 0%, 1.0은 100%를 의미합니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 */
@Composable
fun WantedLinearProgressIndicator(
    currentProgress: Float,
    modifier: Modifier = Modifier
) {

    LinearProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .height(2.dp),
        color = DesignSystemTheme.colors.primaryNormal,
        trackColor = DesignSystemTheme.colors.fillNormal,
        progress = { currentProgress }
    )
}

@DevicePreviews
@Composable
private fun WantedLinearProgressIndicatorPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedLinearProgressIndicator(
                    currentProgress = 0.1f
                )
            }
        }
    }
}