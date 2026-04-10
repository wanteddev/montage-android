package com.wanted.android.montage.sample.navigations.topbar.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.designsystem.R
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.navigations.topbar.search.DSWantedSearchTopAppBarDemoScreenContract.DSWantedSearchTopAppBarDemoEvent
import com.wanted.android.montage.sample.navigations.topbar.search.DSWantedSearchTopAppBarDemoScreenContract.DSWantedSearchTopAppBarDemoSideEffect
import com.wanted.android.montage.sample.navigations.topbar.search.DSWantedSearchTopAppBarDemoScreenContract.DSWantedSearchTopAppBarDemoViewEvent
import com.wanted.android.montage.sample.navigations.topbar.search.DSWantedSearchTopAppBarDemoScreenContract.DSWantedSearchTopAppBarDemoViewState
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.search.WantedSearchFieldDefaults.Size
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.input.textinput.textfield.WantedTextField
import com.wanted.android.wanted.design.navigations.topbar.WantedSearchTopAppBar
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarContract.Variant
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarIconButton
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedSearchTopAppBarDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedSearchTopAppBarDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedSearchTopAppBarDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedSearchTopAppBarDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedSearchTopAppBarDemoViewEvent.OnClickBack -> onClickBack()
            DSWantedSearchTopAppBarDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedSearchTopAppBarDemoEvent.ShowCode(true))
            }

            DSWantedSearchTopAppBarDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedSearchTopAppBarDemoEvent.CopyCode)
            }

            is DSWantedSearchTopAppBarDemoViewEvent.OnSearchTextChange -> {
                viewModel.setEvent(DSWantedSearchTopAppBarDemoEvent.SetSearchText(viewEvent.text))
            }

            is DSWantedSearchTopAppBarDemoViewEvent.OnChangePlaceholder -> {
                viewModel.setEvent(DSWantedSearchTopAppBarDemoEvent.SetPlaceholder(viewEvent.placeholder))
            }

            is DSWantedSearchTopAppBarDemoViewEvent.OnChangeSize -> {
                viewModel.setEvent(DSWantedSearchTopAppBarDemoEvent.SetSize(viewEvent.size))
            }

            is DSWantedSearchTopAppBarDemoViewEvent.OnChangeBackground -> {
                viewModel.setEvent(DSWantedSearchTopAppBarDemoEvent.SetBackground(viewEvent.background))
            }

            is DSWantedSearchTopAppBarDemoViewEvent.OnChangeEnabled -> {
                viewModel.setEvent(DSWantedSearchTopAppBarDemoEvent.SetEnabled(viewEvent.enabled))
            }

            is DSWantedSearchTopAppBarDemoViewEvent.OnChangeActions -> {
                viewModel.setEvent(DSWantedSearchTopAppBarDemoEvent.SetActions(viewEvent.actions))
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedSearchTopAppBarDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedSearchTopAppBarDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedSearchTopAppBarDemoEvent.ShowCode(false))
            },
            content = {
                Text(
                    modifier = Modifier.padding(20.dp),
                    text = viewState.code
                )
            }
        )
    }
}

@Composable
private fun DSWantedSearchTopAppBarDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedSearchTopAppBarDemoViewState,
    onViewEvent: (DSWantedSearchTopAppBarDemoViewEvent) -> Unit
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            WantedSearchTopAppBar(
                text = viewState.searchText,
                backgroundColor = if (viewState.background) {
                    colorResource(R.color.background_normal_normal)
                } else {
                    colorResource(R.color.transparent)
                },
                placeholder = viewState.placeholder,
                size = viewState.size,
                enabled = viewState.enabled,
                actions = if (viewState.actions) {
                    {
                        WantedTopAppBarIconButton(
                            variant = Variant.Search,
                            painter = painterResource(R.drawable.icon_normal_share),
                            onClick = { }
                        )
                    }
                } else null,
                onClickBack = {
                    onViewEvent(DSWantedSearchTopAppBarDemoViewEvent.OnClickBack)
                },
                onValueChange = { text ->
                    onViewEvent(DSWantedSearchTopAppBarDemoViewEvent.OnSearchTextChange(text))
                }
            )
        },
        bottomBar = {
            WantedActionArea(
                modifier = Modifier.navigationBarsPadding(),
                background = true,
                positive = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        size = ButtonSize.SMALL,
                        text = "코드 보기",
                        onClick = {
                            onViewEvent(DSWantedSearchTopAppBarDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                negative = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        size = ButtonSize.SMALL,
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedSearchTopAppBarDemoViewEvent.OnClickCopyCode)
                        }
                    )
                }
            )
        }
    ) { innerPadding ->

        DSWantedSearchTopAppBarDemoScreenLayout(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 20.dp),
            placeholder = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "placeholder",
                        style = WantedTextStyle(
                            colorRes = R.color.label_normal,
                            style = DesignSystemTheme.typography.body2Regular
                        )
                    )
                    WantedTextField(
                        text = viewState.placeholder,
                        onValueChange = { newPlaceholder ->
                            onViewEvent(
                                DSWantedSearchTopAppBarDemoViewEvent.OnChangePlaceholder(
                                    newPlaceholder
                                )
                            )
                        },
                        placeholder = "placeholder를 입력하세요"
                    )
                }
            },
            size = {
                WantedSelect(
                    value = "size : ${getSizeName(viewState.size)}",
                    selectedValue = getSizeName(viewState.size),
                    selectValueList = listOf("Small", "Medium", "Custom"),
                    onSelect = { sizeName ->
                        val newSize = when (sizeName) {
                            "Small" -> Size.Small()
                            "Medium" -> Size.Medium()
                            "Custom" -> Size.Custom()
                            else -> Size.Medium()
                        }
                        onViewEvent(DSWantedSearchTopAppBarDemoViewEvent.OnChangeSize(newSize))
                    }
                )
            },
            background = {
                DSWantedOptionSwitchCell(
                    text = "background : ${viewState.background}",
                    checkState = viewState.background,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedSearchTopAppBarDemoViewEvent.OnChangeBackground(checked))
                    }
                )
            },
            enabled = {
                DSWantedOptionSwitchCell(
                    text = "enabled : ${viewState.enabled}",
                    checkState = viewState.enabled,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedSearchTopAppBarDemoViewEvent.OnChangeEnabled(checked))
                    }
                )
            },
            actions = {
                DSWantedOptionSwitchCell(
                    text = "actions : ${viewState.actions}",
                    checkState = viewState.actions,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedSearchTopAppBarDemoViewEvent.OnChangeActions(checked))
                    }
                )
            }
        )
    }
}

@Composable
private fun DSWantedSearchTopAppBarDemoScreenLayout(
    modifier: Modifier = Modifier,
    placeholder: @Composable () -> Unit,
    size: @Composable () -> Unit,
    background: @Composable () -> Unit,
    enabled: @Composable () -> Unit,
    actions: @Composable () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
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

            placeholder()
            size()
            background()
            enabled()
            actions()
        }
    }
}

private fun getSizeName(size: Size): String {
    return when (size) {
        is Size.Small -> "Small"
        is Size.Medium -> "Medium"
        is Size.Custom -> "Custom"
    }
}

@DevicePreviews
@Composable
private fun DSWantedSearchTopAppBarDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedSearchTopAppBarDemoScreenContent(
            viewState = DSWantedSearchTopAppBarDemoViewState(),
            onViewEvent = { }
        )
    }
}
