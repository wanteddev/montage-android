package com.wanted.android.wanted.design.segmentcontrol

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
fun WantedSegmentControlItem(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean
) {
    Text(
        modifier = modifier
            .padding(vertical = 9.dp, horizontal = 8.dp),
        text = title,
        textAlign = TextAlign.Center,
        style = WantedTextStyle(
            colorRes = if (isSelected) {
                R.color.label_normal
            } else {
                R.color.label_alternative
            },
            style = DesignSystemTheme.typography.headline2Medium
        )
    )
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
private fun WantedSegmentControlItemPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedSegmentControlItem(
                    modifier = Modifier,
                    title = "타이틀",
                    isSelected = false
                )

                WantedSegmentControlItem(
                    modifier = Modifier,
                    title = "타이틀",
                    isSelected = true
                )
            }
        }
    }
}