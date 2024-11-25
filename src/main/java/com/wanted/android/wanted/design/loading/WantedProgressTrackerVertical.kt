package com.wanted.android.wanted.design.loading

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme


@Composable
fun WantedProgressTrackerVertical(
    modifier: Modifier = Modifier,
    stepCount: Int,
    currentStep: Int,
    label: ((index: Int) -> String)? = null,
    content: @Composable (index: Int) -> Unit
) {
    WantedProgressTrackerVerticalLayout(
        modifier = modifier,
        stepCount = stepCount,
        step = { index ->
            WantedProgressTrackerItem(
                isHorizontalItem = false,
                step = "${index + 1}",
                completed = index < (currentStep - 1),
                enabled = index == (currentStep - 1),
                label = label?.invoke(index) ?: ""
            )
        },
        progress = { index ->
            if (stepCount != index + 1) {
                Box(
                    modifier = Modifier.width(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    VerticalDivider()

                    if (index < (currentStep - 1)) {
                        VerticalDivider(color = colorResource(R.color.primary_normal))
                    }
                }
            }
        },
        content = content
    )
}

@Composable
private fun WantedProgressTrackerVerticalLayout(
    modifier: Modifier = Modifier,
    stepCount: Int,
    step: @Composable (index: Int) -> Unit,
    progress: @Composable (index: Int) -> Unit,
    content: @Composable (index: Int) -> Unit
) {
    Column(
        modifier = modifier.wrapContentHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(stepCount) { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Column(
                    modifier = Modifier,
                ) {
                    step(index)

                    progress(index)
                }

                Column(modifier = Modifier.weight(1f)) {
                    Box(modifier = Modifier.defaultMinSize(minHeight = 20.dp)) {
                        content(index)
                    }

                    if (stepCount != index + 1) {
                        Spacer(modifier = Modifier.size(20.dp))
                    }
                }
            }
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
private fun WantedProgressTrackerVerticalPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedProgressTrackerVertical(
                    modifier = Modifier,
                    stepCount = 4,
                    label = { index ->
                        "${index + 1}단계"
                    },
                    currentStep = 2,
                    content = {
//                        Box(
//                            modifier = Modifier
//                                .background(Color.DarkGray)
//                                .fillMaxWidth()
//                                .height(30.dp)
//                        ) { }
                    }
                )
            }
        }
    }
}