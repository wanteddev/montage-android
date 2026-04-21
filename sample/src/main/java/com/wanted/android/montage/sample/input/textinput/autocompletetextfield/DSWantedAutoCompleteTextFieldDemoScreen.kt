package com.wanted.android.montage.sample.input.textinput.autocompletetextfield

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
import com.wanted.android.montage.sample.input.textinput.autocompletetextfield.DSWantedAutoCompleteTextFieldDemoScreenContract.DSWantedAutoCompleteTextFieldDemoEvent
import com.wanted.android.montage.sample.input.textinput.autocompletetextfield.DSWantedAutoCompleteTextFieldDemoScreenContract.DSWantedAutoCompleteTextFieldDemoSideEffect
import com.wanted.android.montage.sample.input.textinput.autocompletetextfield.DSWantedAutoCompleteTextFieldDemoScreenContract.DSWantedAutoCompleteTextFieldDemoViewEvent
import com.wanted.android.montage.sample.input.textinput.autocompletetextfield.DSWantedAutoCompleteTextFieldDemoScreenContract.DSWantedAutoCompleteTextFieldDemoViewState
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.contents.listcell.WantedListCell
import com.wanted.android.wanted.design.input.textinput.autocompletetextfield.WantedAutoCompleteTextField
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedAutoCompleteTextFieldDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedAutoCompleteTextFieldDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val clipboardManager = LocalClipboardManager.current

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedAutoCompleteTextFieldDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedAutoCompleteTextFieldDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedAutoCompleteTextFieldDemoViewEvent.OnClickBack -> onClickBack()
            DSWantedAutoCompleteTextFieldDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedAutoCompleteTextFieldDemoEvent.ShowCode(true))
            }

            DSWantedAutoCompleteTextFieldDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedAutoCompleteTextFieldDemoEvent.CopyCode)
            }

            is DSWantedAutoCompleteTextFieldDemoViewEvent.OnTextChanged -> {
                viewModel.setEvent(DSWantedAutoCompleteTextFieldDemoEvent.SetText(viewEvent.text))
            }

            is DSWantedAutoCompleteTextFieldDemoViewEvent.OnExpandedChanged -> {
                viewModel.setEvent(DSWantedAutoCompleteTextFieldDemoEvent.SetExpanded(viewEvent.expanded))
            }

            is DSWantedAutoCompleteTextFieldDemoViewEvent.OnEnabledChanged -> {
                viewModel.setEvent(DSWantedAutoCompleteTextFieldDemoEvent.SetEnabled(viewEvent.enabled))
            }

            is DSWantedAutoCompleteTextFieldDemoViewEvent.OnShowSectionTitleChanged -> {
                viewModel.setEvent(
                    DSWantedAutoCompleteTextFieldDemoEvent.SetShowSectionTitle(viewEvent.show)
                )
            }

            is DSWantedAutoCompleteTextFieldDemoViewEvent.OnShowTopDirectInputChanged -> {
                viewModel.setEvent(
                    DSWantedAutoCompleteTextFieldDemoEvent.SetShowTopDirectInput(viewEvent.show)
                )
            }

            is DSWantedAutoCompleteTextFieldDemoViewEvent.OnShowBottomDirectInputChanged -> {
                viewModel.setEvent(
                    DSWantedAutoCompleteTextFieldDemoEvent.SetShowBottomDirectInput(viewEvent.show)
                )
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedAutoCompleteTextFieldDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedAutoCompleteTextFieldDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedAutoCompleteTextFieldDemoEvent.ShowCode(false))
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
private fun DSWantedAutoCompleteTextFieldDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedAutoCompleteTextFieldDemoViewState,
    onViewEvent: (DSWantedAutoCompleteTextFieldDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedAutoCompleteTextField") {
                onViewEvent(DSWantedAutoCompleteTextFieldDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedAutoCompleteTextFieldDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                neutral = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedAutoCompleteTextFieldDemoViewEvent.OnClickCopyCode)
                        }
                    )
                },
            )
        }
    ) { innerPadding ->
        DSWantedAutoCompleteTextFieldDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                WantedAutoCompleteTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = viewState.text,
                    placeholder = "검색어를 입력해주세요",
                    enabled = viewState.enabled,
                    expanded = viewState.expanded,
                    anchorPadding = 8.dp,
                    sectionCount = 2,
                    sectionTitle = if (viewState.showSectionTitle) {
                        { section -> "Section ${section + 1}" }
                    } else {
                        null
                    },
                    sectionItemCount = { 5 },
                    sectionItem = { section, index ->
                        WantedListCell(
                            modifier = Modifier.fillMaxWidth(),
                            fillWidth = true,
                            text = "Section ${section + 1} / Item ${index + 1}"
                        ) {
                            onViewEvent(
                                DSWantedAutoCompleteTextFieldDemoViewEvent.OnExpandedChanged(false)
                            )
                        }
                    },
                    topDirectInput = if (viewState.showTopDirectInput) {
                        {
                            WantedListCell(
                                modifier = Modifier.fillMaxWidth(),
                                fillWidth = true,
                                text = "직접 입력"
                            ) {
                                onViewEvent(
                                    DSWantedAutoCompleteTextFieldDemoViewEvent.OnExpandedChanged(false)
                                )
                            }
                        }
                    } else {
                        null
                    },
                    bottomDirectInput = if (viewState.showBottomDirectInput) {
                        {
                            WantedListCell(
                                modifier = Modifier.fillMaxWidth(),
                                fillWidth = true,
                                text = "하단 직접 입력"
                            ) {
                                onViewEvent(
                                    DSWantedAutoCompleteTextFieldDemoViewEvent.OnExpandedChanged(false)
                                )
                            }
                        }
                    } else {
                        null
                    },
                    onExpandedChange = {
                        onViewEvent(DSWantedAutoCompleteTextFieldDemoViewEvent.OnExpandedChanged(it))
                    },
                    onValueChange = { text ->
                        onViewEvent(DSWantedAutoCompleteTextFieldDemoViewEvent.OnTextChanged(text))
                        onViewEvent(
                            DSWantedAutoCompleteTextFieldDemoViewEvent.OnExpandedChanged(text.isNotEmpty())
                        )
                    }
                )
            },
            enabled = {
                DSWantedOptionSwitchCell(
                    text = "enabled : ${viewState.enabled}",
                    checkState = viewState.enabled,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedAutoCompleteTextFieldDemoViewEvent.OnEnabledChanged(checked))
                    }
                )
            },
            showSectionTitle = {
                DSWantedOptionSwitchCell(
                    text = "sectionTitle : ${viewState.showSectionTitle}",
                    checkState = viewState.showSectionTitle,
                    onCheckChanged = { checked ->
                        onViewEvent(
                            DSWantedAutoCompleteTextFieldDemoViewEvent.OnShowSectionTitleChanged(checked)
                        )
                    }
                )
            },
            showTopDirectInput = {
                DSWantedOptionSwitchCell(
                    text = "topDirectInput : ${viewState.showTopDirectInput}",
                    checkState = viewState.showTopDirectInput,
                    onCheckChanged = { checked ->
                        onViewEvent(
                            DSWantedAutoCompleteTextFieldDemoViewEvent.OnShowTopDirectInputChanged(checked)
                        )
                    }
                )
            },
            showBottomDirectInput = {
                DSWantedOptionSwitchCell(
                    text = "bottomDirectInput : ${viewState.showBottomDirectInput}",
                    checkState = viewState.showBottomDirectInput,
                    onCheckChanged = { checked ->
                        onViewEvent(
                            DSWantedAutoCompleteTextFieldDemoViewEvent.OnShowBottomDirectInputChanged(checked)
                        )
                    }
                )
            }
        )
    }
}

@Composable
private fun DSWantedAutoCompleteTextFieldDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    enabled: @Composable () -> Unit,
    showSectionTitle: @Composable () -> Unit,
    showTopDirectInput: @Composable () -> Unit,
    showBottomDirectInput: @Composable () -> Unit,
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
        enabled()
        showSectionTitle()
        showTopDirectInput()
        showBottomDirectInput()
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@DevicePreviews
@Composable
private fun DSWantedAutoCompleteTextFieldDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedAutoCompleteTextFieldDemoScreen(
            onClickBack = {}
        )
    }
}