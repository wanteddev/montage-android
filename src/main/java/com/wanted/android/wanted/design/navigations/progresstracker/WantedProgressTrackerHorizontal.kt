package com.wanted.android.wanted.design.navigations.progresstracker

import android.annotation.SuppressLint
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
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.theme.DesignSystemTheme

/**
 * 수평형 단계 진행 표시 컴포저블입니다.
 *
 * 전체 단계 수와 현재 진행 단계를 기준으로 진행 바와 단계 라벨을 렌더링합니다.
 * 각 단계는 번호와 함께 완료/진행 중/예정 상태로 표시되며, 선택된 단계에 강조 스타일이 적용됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedProgressTrackerHorizontal(
 *     stepCount = 4,
 *     currentStep = 2,
 *     label = { index -> "${index + 1}단계" }
 * )
 * ```
 *
 * @param stepCount Int: 전체 단계 수입니다.
 * @param currentStep Int: 현재 선택된 단계 (1부터 시작)입니다.
 * @param modifier Modifier: 외형 및 배치 조정을 위한 Modifier입니다.
 * @param label ((Int) -> String)?: 각 단계별 라벨 텍스트를 반환하는 함수입니다.
 */
@Composable
fun WantedProgressTrackerHorizontal(
    stepCount: Int,
    currentStep: Int,
    modifier: Modifier = Modifier,
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

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun WantedProgressTrackerHorizontalLayout(
    stepCount: Int,
    progress: @Composable () -> Unit,
    modifier: Modifier = Modifier,
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