package com.wanted.android.montage.sample.navigations.progressindicator

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
import com.wanted.android.montage.sample.navigations.progressindicator.DSWantedProgressIndicatorDemoScreenContract.DSWantedProgressIndicatorDemoEvent
import com.wanted.android.montage.sample.navigations.progressindicator.DSWantedProgressIndicatorDemoScreenContract.DSWantedProgressIndicatorDemoSideEffect
import com.wanted.android.montage.sample.navigations.progressindicator.DSWantedProgressIndicatorDemoScreenContract.DSWantedProgressIndicatorDemoViewEvent
import com.wanted.android.montage.sample.navigations.progressindicator.DSWantedProgressIndicatorDemoScreenContract.DSWantedProgressIndicatorDemoViewState
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.navigations.progressindicator.WantedLinearProgressIndicator
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedProgressIndicatorDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedProgressIndicatorDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val clipboardManager = LocalClipboardManager.current

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedProgressIndicatorDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedProgressIndicatorDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedProgressIndicatorDemoViewEvent.OnClickBack -> onClickBack()
            DSWantedProgressIndicatorDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedProgressIndicatorDemoEvent.ShowCode(true))
            }

            DSWantedProgressIndicatorDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedProgressIndicatorDemoEvent.CopyCode)
            }

            is DSWantedProgressIndicatorDemoViewEvent.OnProgressChanged -> {
                viewModel.setEvent(DSWantedProgressIndicatorDemoEvent.SetProgress(viewEvent.progress))
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedProgressIndicatorDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedProgressIndicatorDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedProgressIndicatorDemoEvent.ShowCode(false))
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
private fun DSWantedProgressIndicatorDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedProgressIndicatorDemoViewState,
    onViewEvent: (DSWantedProgressIndicatorDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedLinearProgressIndicator") {
                onViewEvent(DSWantedProgressIndicatorDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedProgressIndicatorDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                neutral = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedProgressIndicatorDemoViewEvent.OnClickCopyCode)
                        }
                    )
                },
            )
        }
    ) { innerPadding ->
        DSWantedProgressIndicatorDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                WantedLinearProgressIndicator(
                    currentProgress = viewState.progress
                )
            },
            controls = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedButton(
                        modifier = Modifier.weight(1f),
                        text = "-",
                        onClick = {
                            onViewEvent(
                                DSWantedProgressIndicatorDemoViewEvent.OnProgressChanged(
                                    (viewState.progress - 0.1f).coerceAtLeast(0f)
                                )
                            )
                        }
                    )
                    WantedButton(
                        modifier = Modifier.weight(1f),
                        text = "+",
                        onClick = {
                            onViewEvent(
                                DSWantedProgressIndicatorDemoViewEvent.OnProgressChanged(
                                    (viewState.progress + 0.1f).coerceAtMost(1f)
                                )
                            )
                        }
                    )
                }
            }
        )
    }
}

@Composable
private fun DSWantedProgressIndicatorDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    controls: @Composable () -> Unit,
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
        DSWantedPreviewContainer {
            preview()
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Option",
            style = WantedTextStyle(
                colorRes = R.color.label_strong,
                style = DesignSystemTheme.typography.heading2Bold
            )
        )
        controls()
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@DevicePreviews
@Composable
private fun DSWantedProgressIndicatorDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedProgressIndicatorDemoScreen(
            onClickBack = {}
        )
    }
}
