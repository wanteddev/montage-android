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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R

/**
 * 커스터마이징 가능한 원형 로딩 인디케이터입니다.
 *
 * 기본 크기는 28dp이며, 선 두께는 전체 크기의 10%입니다. StrokeCap은 Round입니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedCircularProgressIndicator(modifier = Modifier.size(40.dp), color = Color.Red)
 * ```
 *
 * @param modifier Modifier: 크기 및 레이아웃 조정용입니다.
 * @param color Color: 인디케이터 색상입니다.
 */
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun WantedCircularProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = colorResource(id = R.color.line_solid_normal),
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