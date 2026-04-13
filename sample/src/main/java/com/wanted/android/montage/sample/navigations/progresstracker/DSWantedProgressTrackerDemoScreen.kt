package com.wanted.android.montage.sample.navigations.progresstracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.designsystem.R
import com.wanted.android.montage.sample.navigations.progresstracker.DSWantedProgressTrackerDemoScreenContract.DSWantedProgressTrackerDemoEvent
import com.wanted.android.montage.sample.navigations.progresstracker.DSWantedProgressTrackerDemoScreenContract.DSWantedProgressTrackerDemoSideEffect
import com.wanted.android.montage.sample.navigations.progresstracker.DSWantedProgressTrackerDemoScreenContract.DSWantedProgressTrackerDemoViewEvent
import com.wanted.android.montage.sample.navigations.progresstracker.DSWantedProgressTrackerDemoScreenContract.DSWantedProgressTrackerDemoViewState
import com.wanted.android.montage.sample.navigations.progresstracker.DSWantedProgressTrackerDemoScreenContract.TrackerOrientation
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.navigations.progresstracker.WantedProgressTrackerHorizontal
import com.wanted.android.wanted.design.navigations.progresstracker.WantedProgressTrackerVertical
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedProgressTrackerDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedProgressTrackerDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val clipboardManager = LocalClipboardManager.current

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedProgressTrackerDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedProgressTrackerDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedProgressTrackerDemoViewEvent.OnClickBack -> onClickBack()
            DSWantedProgressTrackerDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedProgressTrackerDemoEvent.ShowCode(true))
            }

            DSWantedProgressTrackerDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedProgressTrackerDemoEvent.CopyCode)
            }

            is DSWantedProgressTrackerDemoViewEvent.OnStepCountChanged -> {
                viewModel.setEvent(DSWantedProgressTrackerDemoEvent.SetStepCount(viewEvent.count))
            }

            is DSWantedProgressTrackerDemoViewEvent.OnCurrentStepChanged -> {
                viewModel.setEvent(DSWantedProgressTrackerDemoEvent.SetCurrentStep(viewEvent.step))
            }

            is DSWantedProgressTrackerDemoViewEvent.OnOrientationChanged -> {
                viewModel.setEvent(DSWantedProgressTrackerDemoEvent.SetOrientation(viewEvent.orientation))
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedProgressTrackerDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedProgressTrackerDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedProgressTrackerDemoEvent.ShowCode(false))
            },
            content = {
                Text(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    text = viewState.code
                )
            }
        )
    }
}

@Composable
private fun DSWantedProgressTrackerDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedProgressTrackerDemoViewState,
    onViewEvent: (DSWantedProgressTrackerDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedProgressTracker") {
                onViewEvent(DSWantedProgressTrackerDemoViewEvent.OnClickBack)
            }
        },
        bottomBar = {
            WantedActionArea(
                modifier = Modifier.navigationBarsPadding(),
                background = true,
                positive = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 보기",
                        onClick = {
                            onViewEvent(DSWantedProgressTrackerDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                neutral = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedProgressTrackerDemoViewEvent.OnClickCopyCode)
                        }
                    )
                },
            )
        }
    ) { innerPadding ->
        DSWantedProgressTrackerDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                when (viewState.orientation) {
                    TrackerOrientation.Horizontal -> {
                        WantedProgressTrackerHorizontal(
                            stepCount = viewState.stepCount,
                            currentStep = viewState.currentStep,
                            label = { index -> "${index + 1}단계" }
                        )
                    }

                    TrackerOrientation.Vertical -> {
                        WantedProgressTrackerVertical(
                            stepCount = viewState.stepCount,
                            currentStep = viewState.currentStep,
                            label = { index -> "${index + 1}단계" },
                            content = { index ->
                                Text(text = "내용 ${index + 1}")
                            }
                        )
                    }
                }
            },
            orientation = {
                WantedSelect(
                    value = "orientation : ${viewState.orientation.name}",
                    selectedValue = viewState.orientation.name,
                    selectValueList = listOf("Horizontal", "Vertical"),
                    onSelect = { name ->
                        val orientation = if (name == "Vertical") {
                            TrackerOrientation.Vertical
                        } else {
                            TrackerOrientation.Horizontal
                        }
                        onViewEvent(DSWantedProgressTrackerDemoViewEvent.OnOrientationChanged(orientation))
                    }
                )
            },
            stepControl = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedButton(
                        modifier = Modifier.weight(1f),
                        text = "Step -",
                        onClick = {
                            onViewEvent(
                                DSWantedProgressTrackerDemoViewEvent.OnCurrentStepChanged(
                                    (viewState.currentStep - 1).coerceAtLeast(1)
                                )
                            )
                        }
                    )
                    WantedButton(
                        modifier = Modifier.weight(1f),
                        text = "Step +",
                        onClick = {
                            onViewEvent(
                                DSWantedProgressTrackerDemoViewEvent.OnCurrentStepChanged(
                                    (viewState.currentStep + 1).coerceAtMost(viewState.stepCount)
                                )
                            )
                        }
                    )
                }
            },
            countControl = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedButton(
                        modifier = Modifier.weight(1f),
                        text = "Count -",
                        onClick = {
                            onViewEvent(
                                DSWantedProgressTrackerDemoViewEvent.OnStepCountChanged(
                                    (viewState.stepCount - 1).coerceAtLeast(1)
                                )
                            )
                        }
                    )
                    WantedButton(
                        modifier = Modifier.weight(1f),
                        text = "Count +",
                        onClick = {
                            onViewEvent(
                                DSWantedProgressTrackerDemoViewEvent.OnStepCountChanged(viewState.stepCount + 1)
                            )
                        }
                    )
                }
            }
        )
    }
}

@Composable
private fun DSWantedProgressTrackerDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    orientation: @Composable () -> Unit,
    stepControl: @Composable () -> Unit,
    countControl: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Preview",
            style = WantedTextStyle(
                colorRes = R.color.label_strong,
                style = DesignSystemTheme.typography.heading2Bold
            )
        )
        preview()

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Option",
            style = WantedTextStyle(
                colorRes = R.color.label_strong,
                style = DesignSystemTheme.typography.heading2Bold
            )
        )
        orientation()
        stepControl()
        countControl()
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@DevicePreviews
@Composable
private fun DSWantedProgressTrackerDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedProgressTrackerDemoScreen(
            onClickBack = {}
        )
    }
}