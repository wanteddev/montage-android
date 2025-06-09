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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.theme.DesignSystemTheme


/**
 * 진행 상태를 나타내는 LinearProgressIndicator 컴포넌트입니다.
 *
 * 지정된 `currentProgress` 값을 기준으로 전체 너비의 2dp 높이로 진행 바가 렌더링되며,
 * 색상은 디자인 시스템의 `primary_normal`과 `fill_normal`을 사용합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedLinearProgressIndicator(
 *     currentProgress = 0.3f
 * )
 * ```
 *
 * @param currentProgress Float: 진행률 (0.0 ~ 1.0)입니다. 0은 0%, 1은 100%를 의미합니다.
 * @param modifier Modifier: 레이아웃 및 외형을 조정하기 위한 Modifier입니다. 기본값은 Modifier입니다.
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
        color = colorResource(R.color.primary_normal),
        trackColor = colorResource(R.color.fill_normal),
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