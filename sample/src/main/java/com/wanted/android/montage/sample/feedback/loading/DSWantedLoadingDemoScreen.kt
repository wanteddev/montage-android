package com.wanted.android.montage.sample.feedback.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.designsystem.R
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.feedback.loading.DSWantedLoadingDemoScreenContract.DSWantedLoadingDemoEvent
import com.wanted.android.montage.sample.feedback.loading.DSWantedLoadingDemoScreenContract.DSWantedLoadingDemoSideEffect
import com.wanted.android.montage.sample.feedback.loading.DSWantedLoadingDemoScreenContract.DSWantedLoadingDemoViewEvent
import com.wanted.android.montage.sample.feedback.loading.DSWantedLoadingDemoScreenContract.DSWantedLoadingDemoViewState
import com.wanted.android.montage.sample.feedback.loading.DSWantedLoadingDemoScreenContract.LoadingType
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.loading.loading.WantedCircularLoading
import com.wanted.android.wanted.design.loading.loading.WantedLogoLoading
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedLoadingDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedLoadingDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val clipboardManager = LocalClipboardManager.current

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedLoadingDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedLoadingDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedLoadingDemoViewEvent.OnClickBack -> onClickBack()
            DSWantedLoadingDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedLoadingDemoEvent.ShowCode(true))
            }

            DSWantedLoadingDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedLoadingDemoEvent.CopyCode)
            }

            is DSWantedLoadingDemoViewEvent.OnLoadingTypeChanged -> {
                viewModel.setEvent(DSWantedLoadingDemoEvent.SetLoadingType(viewEvent.type))
            }

            is DSWantedLoadingDemoViewEvent.OnUseDimChanged -> {
                viewModel.setEvent(DSWantedLoadingDemoEvent.SetUseDim(viewEvent.useDim))
            }

            is DSWantedLoadingDemoViewEvent.OnShowLoadingChanged -> {
                viewModel.setEvent(DSWantedLoadingDemoEvent.SetShowLoading(viewEvent.show))
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedLoadingDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedLoadingDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedLoadingDemoEvent.ShowCode(false))
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
private fun DSWantedLoadingDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedLoadingDemoViewState,
    onViewEvent: (DSWantedLoadingDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedLoading") {
                onViewEvent(DSWantedLoadingDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedLoadingDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                neutral = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedLoadingDemoViewEvent.OnClickCopyCode)
                        }
                    )
                },
            )
        }
    ) { innerPadding ->
        DSWantedLoadingDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (viewState.showLoading) {
                        when (viewState.loadingType) {
                            LoadingType.Circular -> {
                                WantedCircularLoading(
                                    size = 32.dp,
                                    circleColor = DesignSystemTheme.colors.lineSolidNormal,
                                    dimColor = if (viewState.useDim) {
                                        DesignSystemTheme.colors.staticBlack.copy(alpha = 0.3f)
                                    } else {
                                        Color.Transparent
                                    }
                                )
                            }

                            LoadingType.Logo -> {
                                WantedLogoLoading(isUseDim = viewState.useDim)
                            }
                        }
                    } else {
                        Text(text = "Loading Hidden")
                    }
                }
            },
            type = {
                WantedSelect(
                    value = "type : ${viewState.loadingType.name}",
                    selectedValue = viewState.loadingType.name,
                    selectValueList = listOf("Circular", "Logo"),
                    onSelect = { typeName ->
                        val type = if (typeName == "Logo") LoadingType.Logo else LoadingType.Circular
                        onViewEvent(DSWantedLoadingDemoViewEvent.OnLoadingTypeChanged(type))
                    }
                )
            },
            useDim = {
                DSWantedOptionSwitchCell(
                    text = "useDim : ${viewState.useDim}",
                    checkState = viewState.useDim,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedLoadingDemoViewEvent.OnUseDimChanged(checked))
                    }
                )
            },
            showLoading = {
                DSWantedOptionSwitchCell(
                    text = "showLoading : ${viewState.showLoading}",
                    checkState = viewState.showLoading,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedLoadingDemoViewEvent.OnShowLoadingChanged(checked))
                    }
                )
            }
        )
    }
}

@Composable
private fun DSWantedLoadingDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    type: @Composable () -> Unit,
    useDim: @Composable () -> Unit,
    showLoading: @Composable () -> Unit,
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
        type()
        useDim()
        showLoading()
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@DevicePreviews
@Composable
private fun DSWantedLoadingDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedLoadingDemoScreen(
            onClickBack = {}
        )
    }
}