package com.wanted.android.montage.sample.actions.textbutton

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
import androidx.compose.foundation.layout.wrapContentSize
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
import com.wanted.android.montage.sample.actions.textbutton.DSWantedTextButtonDemoScreenContract.DSWantedTextButtonDemoEvent
import com.wanted.android.montage.sample.actions.textbutton.DSWantedTextButtonDemoScreenContract.DSWantedTextButtonDemoSideEffect
import com.wanted.android.montage.sample.actions.textbutton.DSWantedTextButtonDemoScreenContract.DSWantedTextButtonDemoViewEvent
import com.wanted.android.montage.sample.actions.textbutton.DSWantedTextButtonDemoScreenContract.DSWantedTextButtonDemoViewState
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.actions.button.textbutton.WantedTextButton
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.navigations.topbar.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopup
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.actions.button.textbutton.WantedTextButtonColor
import com.wanted.android.wanted.design.actions.button.textbutton.WantedTextButtonSize
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.WantedTextStyle

// WantedTextButton 공식 미지원(레거시) 옵션은 데모 목록에 (deprecated)로 표기한다.
// 공식 지원: color = PRIMARY/ASSISTIVE, size = MEDIUM/SMALL
private fun WantedTextButtonColor.toDisplayLabel(): String = name

private fun WantedTextButtonSize.toDisplayLabel(): String =
    if (this == WantedTextButtonSize.LARGE || this == WantedTextButtonSize.XSMALL) {
        "$name (deprecated)"
    } else {
        name
    }

@Composable
fun DSWantedTextButtonDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedTextButtonDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedTextButtonDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedTextButtonDemoScreenImpl(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            is DSWantedTextButtonDemoViewEvent.OnClickBack -> onClickBack()
            is DSWantedTextButtonDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedTextButtonDemoEvent.ShowCode(true))
            }

            is DSWantedTextButtonDemoViewEvent.OnSelectType -> {
                viewModel.setEvent(DSWantedTextButtonDemoEvent.SetType(viewEvent.type))
            }

            is DSWantedTextButtonDemoViewEvent.OnSelectSize -> {
                viewModel.setEvent(DSWantedTextButtonDemoEvent.SetSize(viewEvent.buttonSize))
            }

            is DSWantedTextButtonDemoViewEvent.OnChangeEnable -> {
                viewModel.setEvent(DSWantedTextButtonDemoEvent.SetEnable(viewEvent.enabled))
            }

            is DSWantedTextButtonDemoViewEvent.OnChangeLoading -> {
                viewModel.setEvent(DSWantedTextButtonDemoEvent.SetLoading(viewEvent.isLoading))
            }

            is DSWantedTextButtonDemoViewEvent.OnChangeEnabledLeadingDrawable -> {
                viewModel.setEvent(DSWantedTextButtonDemoEvent.SetEnableLeadingDrawable(viewEvent.enabledLeadingDrawable))
            }

            is DSWantedTextButtonDemoViewEvent.OnChangeEnabledTrailingDrawable -> {
                viewModel.setEvent(DSWantedTextButtonDemoEvent.SetEnableTrailingDrawable(viewEvent.enabledTrailingDrawable))
            }

            is DSWantedTextButtonDemoViewEvent.OnClickShowAll -> {
                viewModel.setEvent(DSWantedTextButtonDemoEvent.ShowAll(true))
            }

            is DSWantedTextButtonDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedTextButtonDemoEvent.CopyCode)
            }
        }
    }

    if (viewState.isShowCode) {
        WantedPopup(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedTextButtonDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedTextButtonDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedTextButtonDemoEvent.ShowCode(false))
            },
            content = {
                Text(text = viewState.code)
            }
        )
    }

    if (viewState.isShowAll) {
        WantedPopup(
            positive = "확인",
            onClickPositive = {
                viewModel.setEvent(DSWantedTextButtonDemoEvent.ShowAll(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedTextButtonDemoEvent.ShowAll(false))
            },
            content = {
                DSWantedAllTextButton(viewState) { type, size ->
                    viewModel.setEvent(DSWantedTextButtonDemoEvent.SetType(type))
                    viewModel.setEvent(DSWantedTextButtonDemoEvent.SetSize(size))
                    viewModel.setEvent(DSWantedTextButtonDemoEvent.ShowAll(false))
                    viewModel.setEvent(DSWantedTextButtonDemoEvent.CopyCode)
                }
            }
        )
    }
}

@Composable
private fun DSWantedTextButtonDemoScreenImpl(
    modifier: Modifier = Modifier,
    viewState: DSWantedTextButtonDemoViewState,
    onViewEvent: (DSWantedTextButtonDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedTextButton") {
                onViewEvent(DSWantedTextButtonDemoViewEvent.OnClickBack)
            }
        },
        bottomBar = {
            WantedActionArea(
                modifier = Modifier.navigationBarsPadding(),
                background = true,
                main = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 보기",
                        onClick = {
                            onViewEvent(DSWantedTextButtonDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                sub = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "모든 옵션 보기",
                        variant = ButtonVariant.OUTLINED,
                        onClick = {
                            onViewEvent(DSWantedTextButtonDemoViewEvent.OnClickShowAll)
                        }
                    )
                }
            )

        }
    ) { innerPadding ->
        DSWantedTextButtonDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                WantedTextButton(
                    modifier = Modifier.wrapContentSize(),
                    text = "preview",
                    color = viewState.selectedType,
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
                        onViewEvent(DSWantedTextButtonDemoViewEvent.OnClickCopyCode)
                    }
                )
            },
            type = {
                WantedSelect(
                    value = "Type : ${viewState.selectedType.toDisplayLabel()}",
                    selectedValue = viewState.selectedType.toDisplayLabel(),
                    selectValueList = viewState.typeList.map { it.toDisplayLabel() },
                    onSelect = { label ->
                        viewState.typeList
                            .firstOrNull { it.toDisplayLabel() == label }
                            ?.let { onViewEvent(DSWantedTextButtonDemoViewEvent.OnSelectType(it)) }
                    },
                )
            },
            size = {
                WantedSelect(
                    value = "Size : ${viewState.selectedSize.toDisplayLabel()}",
                    selectedValue = viewState.selectedSize.toDisplayLabel(),
                    selectValueList = viewState.sizeList.map { it.toDisplayLabel() },
                    onSelect = { label ->
                        viewState.sizeList
                            .firstOrNull { it.toDisplayLabel() == label }
                            ?.let { onViewEvent(DSWantedTextButtonDemoViewEvent.OnSelectSize(it)) }
                    },
                )
            },
            enabled = {
                DSWantedOptionSwitchCell(
                    text = "enabled : ${viewState.enabled}",
                    checkState = viewState.enabled,
                    onCheckChanged = {
                        onViewEvent(DSWantedTextButtonDemoViewEvent.OnChangeEnable(it))
                    }
                )
            },
            isLoading = {
                DSWantedOptionSwitchCell(
                    text = "isLoading : ${viewState.isLoading}",
                    checkState = viewState.isLoading,
                    onCheckChanged = {
                        onViewEvent(DSWantedTextButtonDemoViewEvent.OnChangeLoading(it))
                    }
                )
            },
            leadingDrawable = {
                DSWantedOptionSwitchCell(
                    text = "leadingDrawable : ${if (viewState.enabledLeadingDrawable) "icon" else null}",
                    checkState = viewState.enabledLeadingDrawable,
                    onCheckChanged = {
                        onViewEvent(DSWantedTextButtonDemoViewEvent.OnChangeEnabledLeadingDrawable(it))
                    }
                )
            },
            trailingDrawable = {
                DSWantedOptionSwitchCell(
                    text = "trailingDrawable : ${if (viewState.enabledTrailingDrawable) "icon" else null}",
                    checkState = viewState.enabledTrailingDrawable,
                    onCheckChanged = {
                        onViewEvent(DSWantedTextButtonDemoViewEvent.OnChangeEnabledTrailingDrawable(it))
                    }
                )
            }
        )
    }
}

@Composable
private fun DSWantedAllTextButton(
    viewState: DSWantedTextButtonDemoViewState,
    onClick: (WantedTextButtonColor, WantedTextButtonSize) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        viewState.typeList.forEach { buttonType ->
            viewState.sizeList.forEach { size ->
                item {
                    WantedTextButton(
                        modifier = Modifier.wrapContentSize(),
                        text = "${buttonType.toDisplayLabel()} - ${size.toDisplayLabel()}",
                        color = buttonType,
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
                            onClick(buttonType, size)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun DSWantedTextButtonDemoScreenLayout(
    modifier: Modifier,
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
                colorRes = R.color.foreground_neutral_strong,
                style = DesignSystemTheme.typography.heading2Bold
            )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.line_neutral_primary),
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
                    colorRes = R.color.foreground_neutral_strong,
                    style = DesignSystemTheme.typography.heading2Bold
                )
            )

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
private fun DSWantedTextButtonDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedTextButtonDemoScreenImpl(
            viewState = DSWantedTextButtonDemoViewState(),
            onViewEvent = { }
        )
    }
}
