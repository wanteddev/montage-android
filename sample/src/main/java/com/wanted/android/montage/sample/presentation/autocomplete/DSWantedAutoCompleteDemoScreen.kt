package com.wanted.android.montage.sample.presentation.autocomplete

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MenuAnchorType
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
import com.wanted.android.montage.sample.presentation.autocomplete.DSWantedAutoCompleteDemoScreenContract.DSWantedAutoCompleteDemoEvent
import com.wanted.android.montage.sample.presentation.autocomplete.DSWantedAutoCompleteDemoScreenContract.DSWantedAutoCompleteDemoSideEffect
import com.wanted.android.montage.sample.presentation.autocomplete.DSWantedAutoCompleteDemoScreenContract.DSWantedAutoCompleteDemoViewEvent
import com.wanted.android.montage.sample.presentation.autocomplete.DSWantedAutoCompleteDemoScreenContract.DSWantedAutoCompleteDemoViewState
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.contents.listcell.WantedListCell
import com.wanted.android.wanted.design.input.textinput.textfield.WantedTextField
import com.wanted.android.wanted.design.presentation.autocomplete.WantedAutoComplete
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedAutoCompleteDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedAutoCompleteDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val clipboardManager = LocalClipboardManager.current

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedAutoCompleteDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedAutoCompleteDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedAutoCompleteDemoViewEvent.OnClickBack -> onClickBack()
            DSWantedAutoCompleteDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedAutoCompleteDemoEvent.ShowCode(true))
            }

            DSWantedAutoCompleteDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedAutoCompleteDemoEvent.CopyCode)
            }

            is DSWantedAutoCompleteDemoViewEvent.OnTextChanged -> {
                viewModel.setEvent(DSWantedAutoCompleteDemoEvent.SetText(viewEvent.text))
            }

            is DSWantedAutoCompleteDemoViewEvent.OnExpandedChanged -> {
                viewModel.setEvent(DSWantedAutoCompleteDemoEvent.SetExpanded(viewEvent.expanded))
            }

            is DSWantedAutoCompleteDemoViewEvent.OnEnabledChanged -> {
                viewModel.setEvent(DSWantedAutoCompleteDemoEvent.SetEnabled(viewEvent.enabled))
            }

            is DSWantedAutoCompleteDemoViewEvent.OnShowSectionTitleChanged -> {
                viewModel.setEvent(DSWantedAutoCompleteDemoEvent.SetShowSectionTitle(viewEvent.show))
            }

            is DSWantedAutoCompleteDemoViewEvent.OnShowTopDirectInputChanged -> {
                viewModel.setEvent(DSWantedAutoCompleteDemoEvent.SetShowTopDirectInput(viewEvent.show))
            }

            is DSWantedAutoCompleteDemoViewEvent.OnShowBottomDirectInputChanged -> {
                viewModel.setEvent(DSWantedAutoCompleteDemoEvent.SetShowBottomDirectInput(viewEvent.show))
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedAutoCompleteDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedAutoCompleteDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedAutoCompleteDemoEvent.ShowCode(false))
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
private fun DSWantedAutoCompleteDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedAutoCompleteDemoViewState,
    onViewEvent: (DSWantedAutoCompleteDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedAutoComplete") {
                onViewEvent(DSWantedAutoCompleteDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedAutoCompleteDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                neutral = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedAutoCompleteDemoViewEvent.OnClickCopyCode)
                        }
                    )
                },
            )
        }
    ) { innerPadding ->
        DSWantedAutoCompleteDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                ExposedDropdownMenuBox(
                    expanded = viewState.expanded,
                    onExpandedChange = {
                        onViewEvent(DSWantedAutoCompleteDemoViewEvent.OnExpandedChanged(it))
                    }
                ) {
                    WantedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(type = MenuAnchorType.PrimaryEditable, enabled = true),
                        text = viewState.text,
                        placeholder = "검색어를 입력해주세요",
                        enabled = viewState.enabled,
                        onValueChange = { text ->
                            onViewEvent(DSWantedAutoCompleteDemoViewEvent.OnTextChanged(text))
                            onViewEvent(
                                DSWantedAutoCompleteDemoViewEvent.OnExpandedChanged(text.isNotEmpty())
                            )
                        }
                    )

                    WantedAutoComplete(
                        expanded = viewState.expanded,
                        onDismissRequest = {
                            onViewEvent(DSWantedAutoCompleteDemoViewEvent.OnExpandedChanged(it))
                        },
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
                                onViewEvent(DSWantedAutoCompleteDemoViewEvent.OnExpandedChanged(false))
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
                                        DSWantedAutoCompleteDemoViewEvent.OnExpandedChanged(false)
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
                                        DSWantedAutoCompleteDemoViewEvent.OnExpandedChanged(false)
                                    )
                                }
                            }
                        } else {
                            null
                        }
                    )
                }
            },
            enabled = {
                DSWantedOptionSwitchCell(
                    text = "enabled : ${viewState.enabled}",
                    checkState = viewState.enabled,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedAutoCompleteDemoViewEvent.OnEnabledChanged(checked))
                    }
                )
            },
            showSectionTitle = {
                DSWantedOptionSwitchCell(
                    text = "sectionTitle : ${viewState.showSectionTitle}",
                    checkState = viewState.showSectionTitle,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedAutoCompleteDemoViewEvent.OnShowSectionTitleChanged(checked))
                    }
                )
            },
            showTopDirectInput = {
                DSWantedOptionSwitchCell(
                    text = "topDirectInput : ${viewState.showTopDirectInput}",
                    checkState = viewState.showTopDirectInput,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedAutoCompleteDemoViewEvent.OnShowTopDirectInputChanged(checked))
                    }
                )
            },
            showBottomDirectInput = {
                DSWantedOptionSwitchCell(
                    text = "bottomDirectInput : ${viewState.showBottomDirectInput}",
                    checkState = viewState.showBottomDirectInput,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedAutoCompleteDemoViewEvent.OnShowBottomDirectInputChanged(checked))
                    }
                )
            }
        )
    }
}

@Composable
private fun DSWantedAutoCompleteDemoScreenLayout(
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
        preview()

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
private fun DSWantedAutoCompleteDemoScreenPreview() {
    DSWantedAutoCompleteDemoScreen(
        onClickBack = {}
    )
}