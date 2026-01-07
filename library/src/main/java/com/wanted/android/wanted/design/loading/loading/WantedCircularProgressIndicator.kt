package com.wanted.android.wanted.design.loading.loading

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme

/**
 * WantedCircularProgressIndicator
 *
 * 커스터마이징 가능한 원형 Loading Indicator입니다.
 *
 * 기본 크기는 24dp이며, 선 굵기는 전체 크기의 10%로 자동 적용됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedCircularProgressIndicator(
 *     modifier = Modifier.size(40.dp),
 *     color = Color.Red
 * )
 * ```
 *
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param color Color: 인디케이터 색상입니다.
 */
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun WantedCircularProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = DesignSystemTheme.colors.lineSolidNormal,
) {

    // 기본 size 28  custom 가능/ 선 굵기 퍼센트로
    BoxWithConstraints(modifier = modifier.defaultMinSize(24.dp)) {
        CircularProgressIndicator(
            modifier = Modifier.size(minWidth),
            color = color,
            strokeWidth = minWidth * 0.1f,
            strokeCap = StrokeCap.Round
        )
    }
}