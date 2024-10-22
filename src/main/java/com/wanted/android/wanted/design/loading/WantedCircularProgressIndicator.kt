package com.wanted.android.wanted.design.loading

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R


@Composable
fun WantedCircularProgressIndicator(
    modifier: Modifier = Modifier,
    size: WantedCircularProgressSize = WantedCircularProgressSize.Normal
) {
    CircularProgressIndicator(
        modifier = modifier.size(size.size),
        color = colorResource(id = R.color.line_solid_normal),
        strokeWidth = size.width,
        strokeCap = StrokeCap.Round
    )
}

enum class WantedCircularProgressSize(val size: Dp, val width: Dp) {
    Normal(48.dp, 4.dp),
    Small(36.dp, 3.dp)
}