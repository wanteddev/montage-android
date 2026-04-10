package com.wanted.android.montage.sample.input.numberpicker

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.R
import com.wanted.android.montage.sample.input.numberpicker.DSWantedNumberPickerDemoScreenContract.DSWantedNumberPickerDemoEvent
import com.wanted.android.montage.sample.input.numberpicker.DSWantedNumberPickerDemoScreenContract.DSWantedNumberPickerDemoSideEffect
import com.wanted.android.montage.sample.input.numberpicker.DSWantedNumberPickerDemoScreenContract.DSWantedNumberPickerDemoViewEvent
import com.wanted.android.montage.sample.input.numberpicker.DSWantedNumberPickerDemoScreenContract.DSWantedNumberPickerDemoViewState
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.picker.numberpicker.WantedNumberPicker
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedNumberPickerDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedNumberPickerDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedNumberPickerDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedNumberPickerDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            is DSWantedNumberPickerDemoViewEvent.OnClickBack -> onClickBack()
            is DSWantedNumberPickerDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedNumberPickerDemoEvent.ShowCode(true))
            }

            is DSWantedNumberPickerDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedNumberPickerDemoEvent.CopyCode)
            }

            is DSWantedNumberPickerDemoViewEvent.OnChangeUserScrollEnabled -> {
                viewModel.setEvent(DSWantedNumberPickerDemoEvent.SetUserScrollEnabled(viewEvent.enabled))
            }

            is DSWantedNumberPickerDemoViewEvent.OnChangeStart -> {
                viewModel.setEvent(DSWantedNumberPickerDemoEvent.SetStart(viewEvent.start))
            }

            is DSWantedNumberPickerDemoViewEvent.OnChangeEnd -> {
                viewModel.setEvent(DSWantedNumberPickerDemoEvent.SetEnd(viewEvent.end))
            }

            is DSWantedNumberPickerDemoViewEvent.OnChangeStep -> {
                viewModel.setEvent(DSWantedNumberPickerDemoEvent.SetStep(viewEvent.step))
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedNumberPickerDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedNumberPickerDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedNumberPickerDemoEvent.ShowCode(false))
            },
            content = {
                Text(text = viewState.code)
            }
        )
    }
}

@Composable
private fun DSWantedNumberPickerDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedNumberPickerDemoViewState,
    onViewEvent: (DSWantedNumberPickerDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedNumberPicker") {
                onViewEvent(DSWantedNumberPickerDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedNumberPickerDemoViewEvent.OnClickShowCode)
                        }
                    )
                }
            )
        }
    ) { innerPadding ->
        DSWantedNumberPickerDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                WantedNumberPicker(
                    modifier = Modifier.fillMaxWidth(),
                    start = viewState.start,
                    end = viewState.end,
                    step = viewState.step,
                    selectedValue = viewState.selectedValue,
                    userScrollEnabled = viewState.userScrollEnabled,
                    onSelect = { _, value, _ ->
                        onViewEvent(DSWantedNumberPickerDemoViewEvent.OnClickCopyCode)
                    }
                )
            },
            options = {
                DSWantedOptionSwitchCell(
                    text = "userScrollEnabled : ${viewState.userScrollEnabled}",
                    checkState = viewState.userScrollEnabled,
                    onCheckChanged = {
                        onViewEvent(DSWantedNumberPickerDemoViewEvent.OnChangeUserScrollEnabled(it))
                    }
                )
            }
        )
    }
}

@Composable
private fun DSWantedNumberPickerDemoScreenLayout(
    modifier: Modifier,
    preview: @Composable () -> Unit,
    options: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = "Preview",
            style = WantedTextStyle(
                colorRes = com.wanted.android.designsystem.R.color.label_strong,
                style = DesignSystemTheme.typography.heading2Bold
            )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.line_normal_normal),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            preview()
        }

        Spacer(Modifier.size(10.dp))

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = "Option",
                style = WantedTextStyle(
                    colorRes = com.wanted.android.designsystem.R.color.label_strong,
                    style = DesignSystemTheme.typography.heading2Bold
                )
            )

            options()
        }
    }
}

@DevicePreviews
@Composable
private fun DSWantedNumberPickerDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedNumberPickerDemoScreenContent(
            viewState = DSWantedNumberPickerDemoViewState(),
            onViewEvent = { }
        )
    }
}
