package com.wanted.android.montage.sample.actions.button

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import com.wanted.android.montage.sample.actions.button.DSWantedButtonDemoScreenContract.DSWantedButtonDemoEvent
import com.wanted.android.montage.sample.actions.button.DSWantedButtonDemoScreenContract.DSWantedButtonDemoSideEffect
import com.wanted.android.montage.sample.actions.button.DSWantedButtonDemoScreenContract.DSWantedButtonDemoViewEvent
import com.wanted.android.montage.sample.actions.button.DSWantedButtonDemoScreenContract.DSWantedButtonDemoViewState
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedButtonDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedButtonDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedButtonDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedButtonDemoScreenImpl(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            is DSWantedButtonDemoViewEvent.OnClickBack -> onClickBack()
            is DSWantedButtonDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedButtonDemoEvent.ShowCode(true))
            }

            is DSWantedButtonDemoViewEvent.OnSelectButtonShape -> {
                viewModel.setEvent(DSWantedButtonDemoEvent.SetButtonShape(viewEvent.buttonVariant))
            }

            is DSWantedButtonDemoViewEvent.OnSelectType -> {
                viewModel.setEvent(DSWantedButtonDemoEvent.SetType(viewEvent.type))
            }

            is DSWantedButtonDemoViewEvent.OnSelectSize -> {
                viewModel.setEvent(DSWantedButtonDemoEvent.SetSize(viewEvent.buttonSize))
            }

            is DSWantedButtonDemoViewEvent.OnChangeEnable -> {
                viewModel.setEvent(DSWantedButtonDemoEvent.SetEnable(viewEvent.enabled))
            }

            is DSWantedButtonDemoViewEvent.OnChangeLoading -> {
                viewModel.setEvent(DSWantedButtonDemoEvent.SetLoading(viewEvent.isLoading))
            }

            is DSWantedButtonDemoViewEvent.OnChangeEnabledLeadingDrawable -> {
                viewModel.setEvent(DSWantedButtonDemoEvent.SetEnableLeadingDrawable(viewEvent.enabledLeadingDrawable))
            }

            is DSWantedButtonDemoViewEvent.OnChangeEnabledTrailingDrawable -> {
                viewModel.setEvent(DSWantedButtonDemoEvent.SetEnableTrailingDrawable(viewEvent.enabledTrailingDrawable))
            }

            is DSWantedButtonDemoViewEvent.OnClickShowAll -> {
                viewModel.setEvent(DSWantedButtonDemoEvent.ShowAll(true))
            }

            is DSWantedButtonDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedButtonDemoEvent.CopyCode)
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedButtonDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedButtonDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedButtonDemoEvent.ShowCode(false))
            },
            content = {
                Text(text = viewState.code)
            }
        )
    }

    if (viewState.isShowAll) {
        WantedModal(
            positive = "확인",
            onClickPositive = {
                viewModel.setEvent(DSWantedButtonDemoEvent.ShowAll(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedButtonDemoEvent.ShowAll(false))
            },
            content = {
                DSWantedAllButton(viewState) { buttonShape, type, size ->
                    viewModel.setEvent(DSWantedButtonDemoEvent.SetButtonShape(buttonShape))
                    viewModel.setEvent(DSWantedButtonDemoEvent.SetType(type))
                    viewModel.setEvent(DSWantedButtonDemoEvent.SetSize(size))
                    viewModel.setEvent(DSWantedButtonDemoEvent.ShowAll(false))
                    viewModel.setEvent(DSWantedButtonDemoEvent.CopyCode)
                }
            }
        )
    }
}

@Composable
private fun DSWantedButtonDemoScreenImpl(
    modifier: Modifier = Modifier,
    viewState: DSWantedButtonDemoViewState,
    onViewEvent: (DSWantedButtonDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedButton") {
                onViewEvent(DSWantedButtonDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedButtonDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                neutral = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "모든 옵션 보기",
                        variant = ButtonVariant.OUTLINED,
                        onClick = {
                            onViewEvent(DSWantedButtonDemoViewEvent.OnClickShowAll)
                        }
                    )
                }
            )

        }
    ) { innerPadding ->
        DSWantedButtonDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "preview",
                    variant = viewState.selectedButtonVariant,
                    type = viewState.selectedType,
                    size = viewState.selectedSize,
                    enabled = viewState.enabled,
                    isLoading = viewState.isLoading,
                    leadingDrawable = if (viewState.enabledLeadingDrawable) {
                        R.drawable.icon_wanted
                    } else null,
                    trailingDrawable = if (viewState.enabledTrailingDrawable) {
                        R.drawable.icon_wanted
                    } else null,
                    onClick = {
                        onViewEvent(DSWantedButtonDemoViewEvent.OnClickCopyCode)
                    }
                )
            },
            buttonShape = {
                WantedSelect(
                    value = "Button Shape : ${viewState.selectedButtonVariant.name}",
                    selectedValue = viewState.selectedButtonVariant.name,
                    selectValueList = viewState.buttonVariantLists.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedButtonDemoViewEvent.OnSelectButtonShape(ButtonVariant.valueOf(it))
                        )
                    },
                )

            },
            type = {
                WantedSelect(
                    value = "Type : ${viewState.selectedType.name}",
                    selectedValue = viewState.selectedType.name,
                    selectValueList = viewState.typeList.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedButtonDemoViewEvent.OnSelectType(ButtonType.valueOf(it))
                        )
                    },
                )
            },
            size = {
                WantedSelect(
                    value = "Size : ${viewState.selectedSize.name}",
                    selectedValue = viewState.selectedSize.name,
                    selectValueList = viewState.sizeList.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedButtonDemoViewEvent.OnSelectSize(ButtonSize.valueOf(it))
                        )
                    },
                )
            },
            enabled = {
                DSWantedOptionSwitchCell(
                    text = "enabled : ${viewState.enabled}",
                    checkState = viewState.enabled,
                    onCheckChanged = {
                        onViewEvent(DSWantedButtonDemoViewEvent.OnChangeEnable(it))
                    }
                )
            },
            isLoading = {
                DSWantedOptionSwitchCell(
                    text = "isLoading : ${viewState.isLoading}",
                    checkState = viewState.isLoading,
                    onCheckChanged = {
                        onViewEvent(DSWantedButtonDemoViewEvent.OnChangeLoading(it))
                    }
                )
            },
            leadingDrawable = {
                DSWantedOptionSwitchCell(
                    text = "leadingDrawable : ${if (viewState.enabledLeadingDrawable) "icon" else null}",
                    checkState = viewState.enabledLeadingDrawable,
                    onCheckChanged = {
                        onViewEvent(DSWantedButtonDemoViewEvent.OnChangeEnabledLeadingDrawable(it))
                    }
                )
            },
            trailingDrawable = {
                DSWantedOptionSwitchCell(
                    text = "trailingDrawable : ${if (viewState.enabledTrailingDrawable) "icon" else null}",
                    checkState = viewState.enabledTrailingDrawable,
                    onCheckChanged = {
                        onViewEvent(DSWantedButtonDemoViewEvent.OnChangeEnabledTrailingDrawable(it))
                    }
                )
            }
        )
    }
}

@Composable
private fun DSWantedAllButton(
    viewState: DSWantedButtonDemoViewState,
    onClick: (ButtonVariant, ButtonType, ButtonSize) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        viewState.buttonVariantLists.forEach { buttonShape ->
            viewState.typeList.forEach { buttonType ->
                viewState.sizeList.forEach { size ->
                    item {
                        WantedButton(
                            modifier = Modifier.fillMaxWidth(),
                            text = "${buttonShape.name} - ${buttonType.name} - ${size.name}",
                            variant = buttonShape,
                            type = buttonType,
                            size = size,
                            enabled = viewState.enabled,
                            isLoading = viewState.isLoading,
                            leadingDrawable = if (viewState.enabledLeadingDrawable) {
                                R.drawable.icon_wanted
                            } else null,
                            trailingDrawable = if (viewState.enabledTrailingDrawable) {
                                R.drawable.icon_wanted
                            } else null,
                            onClick = {
                                onClick(buttonShape, buttonType, size)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DSWantedButtonDemoScreenLayout(
    modifier: Modifier,
    buttonShape: @Composable () -> Unit,
    preview: @Composable () -> Unit,
    type: @Composable () -> Unit,
    size: @Composable () -> Unit,
    enabled: @Composable () -> Unit,
    isLoading: @Composable () -> Unit,
    leadingDrawable: @Composable () -> Unit,
    trailingDrawable: @Composable () -> Unit
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

        DSWantedPreviewContainer {
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

            buttonShape()

            type()

            size()

            enabled()

            isLoading()

            leadingDrawable()

            trailingDrawable()
        }
    }
}

@DevicePreviews
@Composable
private fun DSWantedButtonDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedButtonDemoScreenImpl(
            viewState = DSWantedButtonDemoViewState(),
            onViewEvent = { }
        )
    }
}