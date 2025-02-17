package com.wanted.android.wanted.design.navigations.progresstracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.theme.DesignSystemTheme

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=25951-95482&m=dev
 *
 */

@Composable
fun WantedProgressTrackerHorizontal(
    modifier: Modifier = Modifier,
    stepCount: Int,
    currentStep: Int,
    label: ((index: Int) -> String)? = null
) {
    WantedProgressTrackerHorizontalLayout(
        modifier = modifier,
        stepCount = stepCount,
        progress = {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp),
                progress = {
                    when {
                        currentStep - 1 <= 0 -> 0f
                        else -> (1f / (stepCount - 1)) * (currentStep - 1)
                    }
                },
                trackColor = colorResource(R.color.line_solid_normal),
                color = colorResource(R.color.primary_normal)
            )
        },
        step = { index ->
            WantedProgressTrackerHorizontalItem(
                step = "${index + 1}",
                completed = index < (currentStep - 1),
                enabled = index == (currentStep - 1),
                label = label?.invoke(index) ?: ""
            )
        }
    )
}

@Composable
private fun WantedProgressTrackerHorizontalLayout(
    modifier: Modifier = Modifier,
    stepCount: Int,
    progress: @Composable () -> Unit,
    step: @Composable (index: Int) -> Unit
) {

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.TopStart
    ) {
        val progressPadding = remember(maxWidth, stepCount) {
            mutableStateOf(
                if (maxWidth != 0.dp && stepCount != 0) {
                    maxWidth / stepCount / 2
                } else {
                    0.dp
                }
            )
        }

        Box(
            modifier = Modifier
                .padding(horizontal = progressPadding.value)
                .fillMaxWidth()
                .height(20.dp),
            contentAlignment = Alignment.Center
        ) {
            progress()
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(stepCount) { index ->
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    step(index)
                }
            }
        }
    }
}


@DevicePreviews
@Composable
private fun WantedProgressTrackerPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {

                WantedProgressTrackerHorizontal(
                    modifier = Modifier,
                    stepCount = 4,
                    label = { index ->
                        when (index) {
                            2 -> {
                                "${index + 1}단계 askjdh fkasjdhfksj hdf"
                            }

                            else -> "${index + 1}단계"
                        }

                    },
                    currentStep = 1
                )

                WantedProgressTrackerHorizontal(
                    modifier = Modifier,
                    stepCount = 4,
                    label = { index ->
                        "${index + 1}단계"
                    },
                    currentStep = 2
                )

                WantedProgressTrackerHorizontal(
                    modifier = Modifier,
                    stepCount = 4,
                    label = { index ->
                        "${index + 1}단계"
                    },
                    currentStep = 3
                )

                WantedProgressTrackerHorizontal(
                    modifier = Modifier,
                    stepCount = 4,
                    label = { index ->
                        "${index + 1}단계"
                    },
                    currentStep = 4
                )

                WantedProgressTrackerHorizontal(
                    modifier = Modifier,
                    stepCount = 4,
                    label = { index ->
                        "${index + 1}단계"
                    },
                    currentStep = 5
                )
            }
        }
    }
}