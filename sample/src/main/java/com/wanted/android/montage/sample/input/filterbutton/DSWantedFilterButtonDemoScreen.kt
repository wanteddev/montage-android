package com.wanted.android.montage.sample.input.filterbutton

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
import com.wanted.android.designsystem.R
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.input.filterbutton.DSWantedFilterButtonDemoScreenContract.DSWantedFilterButtonDemoEvent
import com.wanted.android.montage.sample.input.filterbutton.DSWantedFilterButtonDemoScreenContract.DSWantedFilterButtonDemoSideEffect
import com.wanted.android.montage.sample.input.filterbutton.DSWantedFilterButtonDemoScreenContract.DSWantedFilterButtonDemoViewEvent
import com.wanted.android.montage.sample.input.filterbutton.DSWantedFilterButtonDemoScreenContract.DSWantedFilterButtonDemoViewState
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.filterbutton.WantedFilterButton
import com.wanted.android.wanted.design.input.filterbutton.WantedFilterButtonContract.FilterButtonSize
import com.wanted.android.wanted.design.input.filterbutton.WantedFilterButtonContract.FilterButtonVariant
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedFilterButtonDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedFilterButtonDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedFilterButtonDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedFilterButtonDemoScreenImpl(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            is DSWantedFilterButtonDemoViewEvent.OnClickBack -> onClickBack()
            is DSWantedFilterButtonDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedFilterButtonDemoEvent.ShowCode(true))
            }

            is DSWantedFilterButtonDemoViewEvent.OnSelectVariant -> {
                viewModel.setEvent(DSWantedFilterButtonDemoEvent.SetVariant(viewEvent.variant))
            }

            is DSWantedFilterButtonDemoViewEvent.OnSelectSize -> {
                viewModel.setEvent(DSWantedFilterButtonDemoEvent.SetSize(viewEvent.size))
            }

            is DSWantedFilterButtonDemoViewEvent.OnChangeActive -> {
                viewModel.setEvent(DSWantedFilterButtonDemoEvent.SetActive(viewEvent.isActive))
            }

            is DSWantedFilterButtonDemoViewEvent.OnChangeEnable -> {
                viewModel.setEvent(DSWantedFilterButtonDemoEvent.SetEnable(viewEvent.isEnable))
            }

            is DSWantedFilterButtonDemoViewEvent.OnChangeExpend -> {
                viewModel.setEvent(DSWantedFilterButtonDemoEvent.SetExpend(viewEvent.isExpend))
            }

            is DSWantedFilterButtonDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedFilterButtonDemoEvent.CopyCode)
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedFilterButtonDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedFilterButtonDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedFilterButtonDemoEvent.ShowCode(false))
            },
            content = {
                Text(text = viewState.code)
            }
        )
    }
}

@Composable
private fun DSWantedFilterButtonDemoScreenImpl(
    modifier: Modifier = Modifier,
    viewState: DSWantedFilterButtonDemoViewState,
    onViewEvent: (DSWantedFilterButtonDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedFilterButton") {
                onViewEvent(DSWantedFilterButtonDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedFilterButtonDemoViewEvent.OnClickShowCode)
                        }
                    )
                }
            )
        }
    ) { innerPadding ->
        DSWantedFilterButtonDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                WantedFilterButton(
                    text = "텍스트",
                    activeLabel = if (viewState.isActive) "1" else "",
                    variant = viewState.selectedVariant,
                    size = viewState.selectedSize,
                    isActive = viewState.isActive,
                    isEnable = viewState.isEnable,
                    isExpend = viewState.isExpend,
                )
            },
            variant = {
                WantedSelect(
                    value = "Variant : ${viewState.selectedVariant.name}",
                    selectedValue = viewState.selectedVariant.name,
                    selectValueList = viewState.variantList.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedFilterButtonDemoViewEvent.OnSelectVariant(
                                FilterButtonVariant.valueOf(it)
                            )
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
                            DSWantedFilterButtonDemoViewEvent.OnSelectSize(FilterButtonSize.valueOf(it))
                        )
                    },
                )
            },
            active = {
                DSWantedOptionSwitchCell(
                    text = "isActive : ${viewState.isActive}",
                    checkState = viewState.isActive,
                    onCheckChanged = {
                        onViewEvent(DSWantedFilterButtonDemoViewEvent.OnChangeActive(it))
                    }
                )
            },
            enable = {
                DSWantedOptionSwitchCell(
                    text = "isEnable : ${viewState.isEnable}",
                    checkState = viewState.isEnable,
                    onCheckChanged = {
                        onViewEvent(DSWantedFilterButtonDemoViewEvent.OnChangeEnable(it))
                    }
                )
            },
            expend = {
                DSWantedOptionSwitchCell(
                    text = "isExpend : ${viewState.isExpend}",
                    checkState = viewState.isExpend,
                    onCheckChanged = {
                        onViewEvent(DSWantedFilterButtonDemoViewEvent.OnChangeExpend(it))
                    }
                )
            }
        )
    }
}

@Composable
private fun DSWantedFilterButtonDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    variant: @Composable () -> Unit,
    size: @Composable () -> Unit,
    active: @Composable () -> Unit,
    enable: @Composable () -> Unit,
    expend: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
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
                    colorRes = R.color.label_strong,
                    style = DesignSystemTheme.typography.heading2Bold
                )
            )

            variant()

            size()

            active()

            enable()

            expend()
        }
    }
}

@DevicePreviews
@Composable
private fun DSWantedFilterButtonDemoScreenPreview() {
    DesignSystemTheme {
        val viewState = DSWantedFilterButtonDemoViewState()
        DSWantedFilterButtonDemoScreenImpl(
            viewState = viewState,
            onViewEvent = { }
        )
    }
}
