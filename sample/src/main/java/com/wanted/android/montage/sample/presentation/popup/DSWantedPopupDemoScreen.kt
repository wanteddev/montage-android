package com.wanted.android.montage.sample.presentation.popup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.designsystem.R
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.presentation.popup.DSWantedPopupDemoScreenContract.DSWantedPopupDemoEvent
import com.wanted.android.montage.sample.presentation.popup.DSWantedPopupDemoScreenContract.DSWantedPopupDemoSideEffect
import com.wanted.android.montage.sample.presentation.popup.DSWantedPopupDemoScreenContract.DSWantedPopupDemoViewEvent
import com.wanted.android.montage.sample.presentation.popup.DSWantedPopupDemoScreenContract.DSWantedPopupDemoViewState
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.navigations.topbar.dialogtopbar.WantedDialogTopAppBar
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalType
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedPopupDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedPopupDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val clipboardManager = LocalClipboardManager.current

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedPopupDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedPopupDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedPopupDemoViewEvent.OnClickBack -> onClickBack()
            DSWantedPopupDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedPopupDemoEvent.ShowCode(true))
            }

            DSWantedPopupDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedPopupDemoEvent.CopyCode)
            }

            is DSWantedPopupDemoViewEvent.OnShowPopupChanged -> {
                viewModel.setEvent(DSWantedPopupDemoEvent.SetShowPopup(viewEvent.show))
            }

            is DSWantedPopupDemoViewEvent.OnModalTypeChanged -> {
                viewModel.setEvent(DSWantedPopupDemoEvent.SetModalType(viewEvent.type))
            }

            is DSWantedPopupDemoViewEvent.OnUseTopBarChanged -> {
                viewModel.setEvent(DSWantedPopupDemoEvent.SetUseTopBar(viewEvent.use))
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedPopupDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedPopupDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedPopupDemoEvent.ShowCode(false))
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
private fun DSWantedPopupDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedPopupDemoViewState,
    onViewEvent: (DSWantedPopupDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedModal") {
                onViewEvent(DSWantedPopupDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedPopupDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                neutral = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedPopupDemoViewEvent.OnClickCopyCode)
                        }
                    )
                },
            )
        }
    ) { innerPadding ->
        DSWantedPopupDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "팝업 열기",
                    onClick = {
                        onViewEvent(DSWantedPopupDemoViewEvent.OnShowPopupChanged(true))
                    }
                )
            },
            type = {
                WantedSelect(
                    value = "type : ${viewState.modalType::class.simpleName}",
                    selectedValue = viewState.modalType::class.simpleName ?: "Flexible",
                    selectValueList = listOf("Flexible", "FixedWrapContent", "Fixed", "FixedFullScreen", "FixedRatio"),
                    onSelect = { typeName ->
                        val type = when (typeName) {
                            "FixedWrapContent" -> ModalType.FixedWrapContent()
                            "Fixed" -> ModalType.Fixed(height = 400.dp)
                            "FixedFullScreen" -> ModalType.FixedFullScreen()
                            "FixedRatio" -> ModalType.FixedRatio(ratio = 0.6f)
                            else -> ModalType.Flexible
                        }
                        onViewEvent(DSWantedPopupDemoViewEvent.OnModalTypeChanged(type))
                    }
                )
            },
            topBar = {
                DSWantedOptionSwitchCell(
                    text = "topBar : ${viewState.useTopBar}",
                    checkState = viewState.useTopBar,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedPopupDemoViewEvent.OnUseTopBarChanged(checked))
                    }
                )
            }
        )
    }

    if (viewState.showPopup) {
        WantedModal(
            onDismissRequest = {
                onViewEvent(DSWantedPopupDemoViewEvent.OnShowPopupChanged(false))
            },
            type = viewState.modalType,
            topBar = if (viewState.useTopBar) {
                { WantedDialogTopAppBar(title = "제목") }
            } else {
                null
            },
            positive = "확인",
            onClickPositive = {
                onViewEvent(DSWantedPopupDemoViewEvent.OnShowPopupChanged(false))
            },
            content = {
                Text(text = "Popup Content")
            }
        )
    }
}

@Composable
private fun DSWantedPopupDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    type: @Composable () -> Unit,
    topBar: @Composable () -> Unit,
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
        topBar()
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@DevicePreviews
@Composable
private fun DSWantedPopupDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedPopupDemoScreen(
            onClickBack = {}
        )
    }
}