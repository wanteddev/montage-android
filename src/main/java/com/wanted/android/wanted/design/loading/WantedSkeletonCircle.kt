package com.wanted.android.wanted.design.loading

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
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.theme.DesignSystemTheme

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