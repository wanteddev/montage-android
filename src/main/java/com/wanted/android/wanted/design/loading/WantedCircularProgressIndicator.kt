package com.wanted.android.wanted.design.loading

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R


@Composable
fun WantedCircularProgressIndicator(
    modifier: Modifier = Modifier
) {

    // 기본 size 28  custom 가능/ 선 굵기 퍼센트로
    BoxWithConstraints(modifier = modifier.defaultMinSize(24.dp)) {
        CircularProgressIndicator(
            modifier = Modifier.size(minWidth),
            color = colorResource(id = R.color.line_solid_normal),
            strokeWidth = minWidth * 0.1f,
            strokeCap = StrokeCap.Round
        )
    }

}