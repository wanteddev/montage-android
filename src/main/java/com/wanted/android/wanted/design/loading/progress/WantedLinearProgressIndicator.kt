package com.wanted.android.wanted.design.loading.progress

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
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.theme.DesignSystemTheme


/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=14854-45476&m=dev
 */
@Composable
fun WantedLinearProgressIndicator(
    modifier: Modifier = Modifier,
    currentProgress: Float
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