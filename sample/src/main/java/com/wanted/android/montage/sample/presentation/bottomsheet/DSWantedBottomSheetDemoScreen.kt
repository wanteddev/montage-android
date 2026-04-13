package com.wanted.android.montage.sample.presentation.bottomsheet

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
import com.wanted.android.montage.sample.presentation.bottomsheet.DSWantedBottomSheetDemoScreenContract.DSWantedBottomSheetDemoEvent
import com.wanted.android.montage.sample.presentation.bottomsheet.DSWantedBottomSheetDemoScreenContract.DSWantedBottomSheetDemoSideEffect
import com.wanted.android.montage.sample.presentation.bottomsheet.DSWantedBottomSheetDemoScreenContract.DSWantedBottomSheetDemoViewEvent
import com.wanted.android.montage.sample.presentation.bottomsheet.DSWantedBottomSheetDemoScreenContract.DSWantedBottomSheetDemoViewState
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalSize
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalType
import com.wanted.android.wanted.design.presentation.modal.bottomsheet.WantedModalBottomSheet
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedBottomSheetDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedBottomSheetDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val clipboardManager = LocalClipboardManager.current

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedBottomSheetDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedBottomSheetDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedBottomSheetDemoViewEvent.OnClickBack -> onClickBack()
            DSWantedBottomSheetDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedBottomSheetDemoEvent.ShowCode(true))
            }

            DSWantedBottomSheetDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedBottomSheetDemoEvent.CopyCode)
            }

            is DSWantedBottomSheetDemoViewEvent.OnModalTypeChanged -> {
                viewModel.setEvent(DSWantedBottomSheetDemoEvent.SetModalType(viewEvent.type))
            }

            is DSWantedBottomSheetDemoViewEvent.OnModalSizeChanged -> {
                viewModel.setEvent(DSWantedBottomSheetDemoEvent.SetModalSize(viewEvent.size))
            }

            is DSWantedBottomSheetDemoViewEvent.OnDismissOnClickOutsideChanged -> {
                viewModel.setEvent(
                    DSWantedBottomSheetDemoEvent.SetDismissOnClickOutside(viewEvent.dismiss)
                )
            }

            is DSWantedBottomSheetDemoViewEvent.OnShowSheetChanged -> {
                viewModel.setEvent(DSWantedBottomSheetDemoEvent.SetShowSheet(viewEvent.show))
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedBottomSheetDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedBottomSheetDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedBottomSheetDemoEvent.ShowCode(false))
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
private fun DSWantedBottomSheetDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedBottomSheetDemoViewState,
    onViewEvent: (DSWantedBottomSheetDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedModalBottomSheet") {
                onViewEvent(DSWantedBottomSheetDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedBottomSheetDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                neutral = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedBottomSheetDemoViewEvent.OnClickCopyCode)
                        }
                    )
                },
            )
        }
    ) { innerPadding ->
        DSWantedBottomSheetDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "바텀시트 열기",
                    onClick = {
                        onViewEvent(DSWantedBottomSheetDemoViewEvent.OnShowSheetChanged(true))
                    }
                )
            },
            type = {
                WantedSelect(
                    value = "type : ${getTypeName(viewState.modalType)}",
                    selectedValue = getTypeName(viewState.modalType),
                    selectValueList = listOf(
                        "Flexible",
                        "FixedWrapContent",
                        "Fixed",
                        "FixedFullScreen",
                        "FixedRatio"
                    ),
                    onSelect = { typeName ->
                        onViewEvent(
                            DSWantedBottomSheetDemoViewEvent.OnModalTypeChanged(
                                getTypeFromName(typeName)
                            )
                        )
                    }
                )
            },
            size = {
                WantedSelect(
                    value = "size : ${viewState.modalSize.name}",
                    selectedValue = viewState.modalSize.name,
                    selectValueList = listOf("Medium", "Large", "XLarge", "Custom"),
                    onSelect = { sizeName ->
                        onViewEvent(
                            DSWantedBottomSheetDemoViewEvent.OnModalSizeChanged(
                                getSizeFromName(sizeName)
                            )
                        )
                    }
                )
            },
            dismissOnClickOutside = {
                DSWantedOptionSwitchCell(
                    text = "dismissOnClickOutside : ${viewState.dismissOnClickOutside}",
                    checkState = viewState.dismissOnClickOutside,
                    onCheckChanged = { checked ->
                        onViewEvent(
                            DSWantedBottomSheetDemoViewEvent.OnDismissOnClickOutsideChanged(checked)
                        )
                    }
                )
            }
        )
    }

    WantedModalBottomSheet(
        isShow = viewState.isShowSheet,
        onDismissRequest = {
            onViewEvent(DSWantedBottomSheetDemoViewEvent.OnShowSheetChanged(false))
        },
        type = viewState.modalType,
        modalSize = viewState.modalSize,
        dismissOnClickOutside = viewState.dismissOnClickOutside,
        topBar = {
            Text(
                text = "Bottom Sheet",
                style = DesignSystemTheme.typography.title2Bold
            )
        },
        content = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(text = "바텀시트 콘텐츠")
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "닫기",
                    onClick = {
                        onViewEvent(DSWantedBottomSheetDemoViewEvent.OnShowSheetChanged(false))
                    }
                )
            }
        }
    )
}

@Composable
private fun DSWantedBottomSheetDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    type: @Composable () -> Unit,
    size: @Composable () -> Unit,
    dismissOnClickOutside: @Composable () -> Unit,
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
        type()
        size()
        dismissOnClickOutside()
        Spacer(modifier = Modifier.height(20.dp))
    }
}

private fun getTypeName(type: ModalType): String {
    return when (type) {
        ModalType.Flexible -> "Flexible"
        is ModalType.FixedWrapContent -> "FixedWrapContent"
        is ModalType.Fixed -> "Fixed"
        is ModalType.FixedFullScreen -> "FixedFullScreen"
        is ModalType.FixedRatio -> "FixedRatio"
    }
}

private fun getTypeFromName(name: String): ModalType {
    return when (name) {
        "Flexible" -> ModalType.Flexible
        "FixedWrapContent" -> ModalType.FixedWrapContent()
        "Fixed" -> ModalType.Fixed(height = 400.dp)
        "FixedFullScreen" -> ModalType.FixedFullScreen()
        "FixedRatio" -> ModalType.FixedRatio(ratio = 0.6f)
        else -> ModalType.Flexible
    }
}

private fun getSizeFromName(name: String): ModalSize {
    return when (name) {
        "Medium" -> ModalSize.Medium
        "Large" -> ModalSize.Large
        "XLarge" -> ModalSize.XLarge
        "Custom" -> ModalSize.Custom
        else -> ModalSize.Medium
    }
}

@DevicePreviews
@Composable
private fun DSWantedBottomSheetDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedBottomSheetDemoScreen(
            onClickBack = {}
        )
    }
}