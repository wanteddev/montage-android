package com.wanted.android.wanted.design.loading.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle
import com.wanted.android.wanted.design.util.dpToSp


@Composable
internal fun WantedProgressTrackerHorizontalItem(
    modifier: Modifier = Modifier,
    step: String,
    label: String,
    enabled: Boolean,
    completed: Boolean
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        WantedProgressTrackerStep(
            step = step,
            enabled = enabled,
            completed = completed
        )

        if (label.isNotEmpty()) {
            Text(
                text = label,
                style = WantedTextStyle(
                    colorRes = if (!enabled || completed) {
                        R.color.label_alternative
                    } else {
                        R.color.label_normal
                    },
                    style = DesignSystemTheme.typography.label2Bold
                )
            )
        }
    }
}

@Composable
internal fun WantedProgressTrackerStep(
    modifier: Modifier = Modifier,
    step: String,
    enabled: Boolean,
    completed: Boolean
) {

    Box(
        modifier = modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(colorResource(R.color.static_white))
            .background(
                when {
                    completed -> colorResource(R.color.primary_normal)
                    enabled -> colorResource(R.color.primary_normal)
                    else -> colorResource(R.color.label_assistive)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        if (completed) {
            Icon(
                modifier = Modifier.size(14.dp),
                painter = painterResource(R.drawable.icon_normal_check_thick),
                tint = colorResource(R.color.static_white),
                contentDescription = ""
            )
        } else {
            Text(
                text = step,
                style = WantedTextStyle(
                    colorRes = R.color.static_white,
                    style = DesignSystemTheme.typography.caption1Bold
                ),
                fontSize = 12.dp.dpToSp()
            )
        }
    }
}


@DevicePreviews
@Composable
private fun WantedProgressTrackerItemPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                // Horizontal Item
                WantedProgressTrackerHorizontalItem(
                    modifier = Modifier,
                    step = "1",
                    enabled = true,
                    completed = false,
                    label = ""
                )

                WantedProgressTrackerHorizontalItem(
                    modifier = Modifier,
                    step = "1",
                    enabled = true,
                    completed = false,
                    label = "단계 "
                )

                WantedProgressTrackerHorizontalItem(
                    modifier = Modifier,
                    step = "1",
                    enabled = true,
                    completed = true,
                    label = ""
                )

                WantedProgressTrackerHorizontalItem(
                    modifier = Modifier,
                    step = "1",
                    enabled = true,
                    completed = true,
                    label = "단계 "
                )

                WantedProgressTrackerHorizontalItem(
                    modifier = Modifier,
                    step = "1",
                    enabled = false,
                    completed = false,
                    label = ""
                )
            }
        }
    }
}