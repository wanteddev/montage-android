package com.wanted.android.wanted.design.loading.progress

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle
import com.wanted.android.wanted.design.util.dpToSp


@Composable
fun WantedProgressTrackerItem(
    modifier: Modifier = Modifier,
    isHorizontalItem: Boolean = true,
    step: String,
    enabled: Boolean,
    completed: Boolean = false,
    label: String = "",
) {
    if (isHorizontalItem) {
        WantedProgressTrackerHorizontalItem(
            modifier = modifier,
            step = step,
            enabled = enabled,
            completed = completed,
            label = label
        )
    } else {
        WantedProgressTrackerVerticalItem(
            modifier = modifier,
            step = step,
            enabled = enabled,
            completed = completed,
            label = label
        )
    }
}

@Composable
private fun WantedProgressTrackerHorizontalItem(
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
private fun WantedProgressTrackerVerticalItem(
    modifier: Modifier = Modifier,
    step: String,
    label: String,
    enabled: Boolean,
    completed: Boolean
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
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
private fun WantedProgressTrackerStep(
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


@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Preview(
    "foldableLight",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "ko",
    device = Devices.FOLDABLE
)
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
                WantedProgressTrackerItem(
                    modifier = Modifier,
                    isHorizontalItem = true,
                    step = "1",
                    enabled = true,
                    completed = false,
                )

                WantedProgressTrackerItem(
                    modifier = Modifier,
                    isHorizontalItem = true,
                    step = "1",
                    enabled = true,
                    completed = false,
                    label = "단계 "
                )

                WantedProgressTrackerItem(
                    modifier = Modifier,
                    isHorizontalItem = true,
                    step = "1",
                    enabled = true,
                    completed = true,
                )

                WantedProgressTrackerItem(
                    modifier = Modifier,
                    isHorizontalItem = true,
                    step = "1",
                    enabled = true,
                    completed = true,
                    label = "단계 "
                )

                WantedProgressTrackerItem(
                    modifier = Modifier,
                    isHorizontalItem = true,
                    step = "1",
                    enabled = false,
                    completed = false,
                )

                WantedProgressTrackerItem(
                    modifier = Modifier,
                    isHorizontalItem = true,
                    step = "1",
                    enabled = false,
                    completed = false,
                    label = "단계 "
                )

                // vertical Item
                WantedProgressTrackerItem(
                    modifier = Modifier,
                    isHorizontalItem = false,
                    step = "1",
                    enabled = true,
                    completed = false,
                )

                WantedProgressTrackerItem(
                    modifier = Modifier,
                    isHorizontalItem = false,
                    step = "1",
                    enabled = true,
                    completed = false,
                    label = "단계 "
                )

                WantedProgressTrackerItem(
                    modifier = Modifier,
                    isHorizontalItem = false,
                    step = "1",
                    enabled = true,
                    completed = true,
                )

                WantedProgressTrackerItem(
                    modifier = Modifier,
                    isHorizontalItem = false,
                    step = "1",
                    enabled = true,
                    completed = true,
                    label = "단계 "
                )

                WantedProgressTrackerItem(
                    modifier = Modifier,
                    isHorizontalItem = false,
                    step = "1",
                    enabled = false,
                    completed = false,
                )

                WantedProgressTrackerItem(
                    modifier = Modifier,
                    isHorizontalItem = false,
                    step = "1",
                    enabled = false,
                    completed = false,
                    label = "단계 "
                )
            }
        }
    }
}