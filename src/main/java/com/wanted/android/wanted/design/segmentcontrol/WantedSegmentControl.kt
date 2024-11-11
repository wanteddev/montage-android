package com.wanted.android.wanted.design.segmentcontrol

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme


@Composable
fun WantedSegmentControl(
    modifier: Modifier
) {

    Row(
        modifier = modifier,
    ) {

    }
}

@Composable
private fun SegmentControlSolidItem(modifier: Modifier = Modifier) {

}

@Composable
private fun SegmentControlOutlinedItem(
    modifier: Modifier = Modifier,
    shape: Shape
) {

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
private fun WantedSegmentControlPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedSegmentControl(
                    modifier = Modifier
                )
            }
        }
    }
}