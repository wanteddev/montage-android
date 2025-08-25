package com.wanted.android.wanted.design.input.framedstyle

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_43


fun Modifier.framedStyle(
    status: WantedFramedStyleStatus = WantedFramedStyleStatus.Normal,
    enabled: Boolean = true
) = composed {
    this
        .border(
            shape = RoundedCornerShape(12.dp),
            color = when {
                status == WantedFramedStyleStatus.Negative
                        || status == WantedFramedStyleStatus.Selected -> {
                    colorResource(id = R.color.background_normal_normal)
                        .copy(alpha = OPACITY_43)
                }

                else -> colorResource(R.color.transparent)
            },
            width = if (status == WantedFramedStyleStatus.Selected) 2.dp else 1.dp
        )
        .border(
            shape = RoundedCornerShape(12.dp),
            color = when {
                !enabled -> colorResource(R.color.line_normal_alternative)
                status == WantedFramedStyleStatus.Negative -> {
                    colorResource(R.color.status_negative).copy(OPACITY_43)
                }

                status == WantedFramedStyleStatus.Selected -> {
                    colorResource(R.color.primary_normal).copy(OPACITY_43)
                }

                else -> colorResource(R.color.line_normal_neutral)
            },
            width = if (status == WantedFramedStyleStatus.Selected) 2.dp else 1.dp
        )
        .clip(RoundedCornerShape(12.dp))
}

enum class WantedFramedStyleStatus {
    Normal,
    Negative,
    Selected
}

@DevicePreviews
@Composable
private fun WantedFramedStylePreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .framedStyle(
                            status = WantedFramedStyleStatus.Negative,
                            enabled = true
                        )
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .framedStyle(
                            status = WantedFramedStyleStatus.Selected,
                            enabled = true
                        )
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .framedStyle(
                            status = WantedFramedStyleStatus.Normal,
                            enabled = true
                        )
                )


                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .framedStyle(
                            status = WantedFramedStyleStatus.Negative,
                            enabled = false
                        )
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .framedStyle(
                            status = WantedFramedStyleStatus.Selected,
                            enabled = false
                        )
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .framedStyle(
                            status = WantedFramedStyleStatus.Normal,
                            enabled = false
                        )
                )
            }
        }
    }
}