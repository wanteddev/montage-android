package com.wanted.android.montage.sample.navigations.topbar.dialogtopbar

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.R
import com.wanted.android.montage.sample.navigations.topbar.dialogtopbar.DSWantedDialogTopAppBarDemoScreenContract.DSWantedDialogTopAppBarDemoEvent
import com.wanted.android.montage.sample.navigations.topbar.dialogtopbar.DSWantedDialogTopAppBarDemoScreenContract.DSWantedDialogTopAppBarDemoSideEffect
import com.wanted.android.montage.sample.navigations.topbar.dialogtopbar.DSWantedDialogTopAppBarDemoScreenContract.DSWantedDialogTopAppBarDemoViewEvent
import com.wanted.android.montage.sample.navigations.topbar.dialogtopbar.DSWantedDialogTopAppBarDemoScreenContract.DSWantedDialogTopAppBarDemoViewState
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBar
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarIconButton
import com.wanted.android.wanted.design.navigations.topbar.dialogtopbar.WantedDialogCloseTopAppBar
import com.wanted.android.wanted.design.navigations.topbar.dialogtopbar.WantedDialogSearchTopAppBar
import com.wanted.android.wanted.design.navigations.topbar.dialogtopbar.WantedDialogTopAppBar
import com.wanted.android.wanted.design.navigations.topbar.dialogtopbar.WantedDialogTopAppBarContract.Variant
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalType
import com.wanted.android.wanted.design.presentation.modal.bottomsheet.WantedBottomSheetDefaults
import com.wanted.android.wanted.design.presentation.modal.bottomsheet.WantedModalBottomSheet
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopup
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
fun DSWantedDialogTopAppBarDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedDialogTopAppBarDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val scrollState = rememberLazyListState()

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedDialogTopAppBarDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedDialogTopAppBarDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedDialogTopAppBarDemoViewEvent.OnClickBack -> onClickBack()
            DSWantedDialogTopAppBarDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedDialogTopAppBarDemoEvent.ShowCode(true))
            }

            DSWantedDialogTopAppBarDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedDialogTopAppBarDemoEvent.CopyCode)
            }

            DSWantedDialogTopAppBarDemoViewEvent.OnClickShowModal -> {
                viewModel.setEvent(DSWantedDialogTopAppBarDemoEvent.ShowModal(true))
            }

            is DSWantedDialogTopAppBarDemoViewEvent.OnSelectedVariant -> {
                viewModel.setEvent(DSWantedDialogTopAppBarDemoEvent.SetVariant(viewEvent.variant))
            }

            is DSWantedDialogTopAppBarDemoViewEvent.OnChangeBackground -> {
                viewModel.setEvent(DSWantedDialogTopAppBarDemoEvent.SetBackground(viewEvent.background))
            }

            is DSWantedDialogTopAppBarDemoViewEvent.OnChangeBackgroundColorEnabled -> {
                viewModel.setEvent(
                    DSWantedDialogTopAppBarDemoEvent.SetBackgroundColorEnabled(
                        viewEvent.backgroundColorEnabled
                    )
                )
            }

            is DSWantedDialogTopAppBarDemoViewEvent.OnChangeNavigationIcon -> {
                viewModel.setEvent(DSWantedDialogTopAppBarDemoEvent.SetNavigationIcon(viewEvent.navigationIcon))
            }

            is DSWantedDialogTopAppBarDemoViewEvent.OnChangeActions -> {
                viewModel.setEvent(DSWantedDialogTopAppBarDemoEvent.SetActions(viewEvent.actions))
            }

            is DSWantedDialogTopAppBarDemoViewEvent.OnChangeShowCloseButton -> {
                viewModel.setEvent(DSWantedDialogTopAppBarDemoEvent.SetShowCloseButton(viewEvent.showCloseButton))
            }

            is DSWantedDialogTopAppBarDemoViewEvent.OnChangeScrollable -> {
                viewModel.setEvent(DSWantedDialogTopAppBarDemoEvent.SetScrollable(viewEvent.scrollable))
            }

            is DSWantedDialogTopAppBarDemoViewEvent.OnChangeListRevert -> {
                viewModel.setEvent(DSWantedDialogTopAppBarDemoEvent.SetListRevert(viewEvent.listRevert))
            }

            is DSWantedDialogTopAppBarDemoViewEvent.OnChangeUseBottomSheet -> {
                viewModel.setEvent(DSWantedDialogTopAppBarDemoEvent.SetUseBottomSheet(viewEvent.useBottomSheet))
            }
        }
    }

    if (viewState.isShowCode) {
        WantedPopup(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedDialogTopAppBarDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedDialogTopAppBarDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedDialogTopAppBarDemoEvent.ShowCode(false))
            },
            content = {
                Text(
                    modifier = Modifier.padding(20.dp),
                    text = viewState.code
                )
            }
        )
    }

    if (viewState.isShowModal) {
        if (viewState.useBottomSheet) {
            WantedModalBottomSheet(
                isShow = viewState.isShowModal,
                type = ModalType.Fixed(height = 600.dp, isSystemBottomSheet = false),
                sheetDefault = WantedBottomSheetDefaults.getWithoutContentPadding(),
                onDismissRequest = {
                    viewModel.setEvent(DSWantedDialogTopAppBarDemoEvent.ShowModal(false))
                },
                content = {
                    DSWantedDialogTopAppBarModalContent(
                        viewState = viewState,
                        scrollState = scrollState
                    )
                },
                topBar = {
                    if (viewState.variant !is Variant.Floating) {
                        DemoDialogTopAppBar(
                            viewState = viewState,
                            scrollState = scrollState
                        )
                    }
                }
            )
        } else {
            WantedPopup(
                onDismissRequest = {
                    viewModel.setEvent(DSWantedDialogTopAppBarDemoEvent.ShowModal(false))
                },
                content = {
                    DSWantedDialogTopAppBarModalContent(
                        viewState = viewState,
                        scrollState = scrollState
                    )
                },
                topBar = {
                    if (viewState.variant !is Variant.Floating) {
                        DemoDialogTopAppBar(
                            viewState = viewState,
                            scrollState = scrollState
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun DSWantedDialogTopAppBarDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedDialogTopAppBarDemoViewState,
    onViewEvent: (DSWantedDialogTopAppBarDemoViewEvent) -> Unit
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            WantedTopAppBar(
                title = "DialogTopAppBar Demo",
                navigationIcon = {
                    WantedTopAppBarIconButton(
                        painter = painterResource(R.drawable.icon_normal_arrow_left),
                        onClick = { onViewEvent(DSWantedDialogTopAppBarDemoViewEvent.OnClickBack) }
                    )
                }
            )
        },
        bottomBar = {
            WantedActionArea(
                modifier = Modifier.navigationBarsPadding(),
                background = true,
                main = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        size = ButtonSize.SMALL,
                        text = "모달 보기",
                        onClick = {
                            onViewEvent(DSWantedDialogTopAppBarDemoViewEvent.OnClickShowModal)
                        }
                    )
                },
                sub = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        size = ButtonSize.SMALL,
                        text = "코드 보기",
                        onClick = {
                            onViewEvent(DSWantedDialogTopAppBarDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                alternative = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        size = ButtonSize.SMALL,
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedDialogTopAppBarDemoViewEvent.OnClickCopyCode)
                        }
                    )
                }
            )
        }
    ) { innerPadding ->

        DSWantedDialogTopAppBarDemoScreenLayout(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 20.dp),
            variant = {
                WantedSelect(
                    value = "variant : ${variantName(viewState.variant)}",
                    selectedValue = variantName(viewState.variant),
                    selectValueList = Variant.presets.map { variantName(it) },
                    onSelect = { selected ->
                        val variant = Variant.presets
                            .firstOrNull { variantName(it) == selected }
                            ?: Variant.Normal
                        onViewEvent(
                            DSWantedDialogTopAppBarDemoViewEvent.OnSelectedVariant(variant)
                        )
                    }
                )
            },
            background = {
                DSWantedOptionSwitchCell(
                    text = "background : ${viewState.background}",
                    checkState = viewState.background,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedDialogTopAppBarDemoViewEvent.OnChangeBackground(checked))
                    }
                )
            },
            backgroundColorEnabled = {
                DSWantedOptionSwitchCell(
                    text = "backgroundColorEnabled : ${viewState.backgroundColorEnabled}",
                    checkState = viewState.backgroundColorEnabled,
                    onCheckChanged = { checked ->
                        onViewEvent(
                            DSWantedDialogTopAppBarDemoViewEvent.OnChangeBackgroundColorEnabled(
                                checked
                            )
                        )
                    }
                )
            },
            scrollable = {
                DSWantedOptionSwitchCell(
                    text = "scrollable : ${viewState.scrollable}",
                    checkState = viewState.scrollable,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedDialogTopAppBarDemoViewEvent.OnChangeScrollable(checked))
                    }
                )
            },
            navigationIcon = {
                DSWantedOptionSwitchCell(
                    text = "navigationIcon : ${viewState.navigationIcon}",
                    checkState = viewState.navigationIcon,
                    onCheckChanged = { checked ->
                        onViewEvent(
                            DSWantedDialogTopAppBarDemoViewEvent.OnChangeNavigationIcon(
                                checked
                            )
                        )
                    }
                )
            },
            actions = {
                DSWantedOptionSwitchCell(
                    text = "actions : ${viewState.actions}",
                    checkState = viewState.actions,
                    onCheckChanged = { checked ->
                        if (!viewState.showCloseButton) {
                            onViewEvent(DSWantedDialogTopAppBarDemoViewEvent.OnChangeActions(checked))
                        }
                    }
                )
            },
            showCloseButton = {
                DSWantedOptionSwitchCell(
                    text = "showCloseButton : ${viewState.showCloseButton}",
                    checkState = viewState.showCloseButton,
                    onCheckChanged = { checked ->
                        onViewEvent(
                            DSWantedDialogTopAppBarDemoViewEvent.OnChangeShowCloseButton(
                                checked
                            )
                        )
                    }
                )
            },
            listRevert = {
                DSWantedOptionSwitchCell(
                    text = "listRevert : ${viewState.listRevert}",
                    checkState = viewState.listRevert,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedDialogTopAppBarDemoViewEvent.OnChangeListRevert(checked))
                    }
                )
            },
            useBottomSheet = {
                DSWantedOptionSwitchCell(
                    text = "useBottomSheet : ${viewState.useBottomSheet}",
                    checkState = viewState.useBottomSheet,
                    onCheckChanged = { checked ->
                        onViewEvent(
                            DSWantedDialogTopAppBarDemoViewEvent.OnChangeUseBottomSheet(
                                checked
                            )
                        )
                    }
                )
            },
            preview = {
                DSWantedDialogTopAppBarPreview(viewState = viewState)
            }
        )
    }
}


@Composable
private fun DSWantedDialogTopAppBarDemoScreenLayout(
    modifier: Modifier = Modifier,
    variant: @Composable () -> Unit,
    background: @Composable () -> Unit,
    backgroundColorEnabled: @Composable () -> Unit,
    scrollable: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable () -> Unit,
    showCloseButton: @Composable () -> Unit,
    listRevert: @Composable () -> Unit,
    useBottomSheet: @Composable () -> Unit,
    preview: @Composable () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "Preview",
                style = WantedTextStyle(
                    colorRes = R.color.foreground_neutral_strong,
                    style = DesignSystemTheme.typography.heading2Bold
                )
            )
            preview()
        }

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
                    colorRes = R.color.foreground_neutral_strong,
                    style = DesignSystemTheme.typography.heading2Bold
                )
            )

            variant()
            background()
            backgroundColorEnabled()
            scrollable()
            navigationIcon()
            actions()
            showCloseButton()
            listRevert()
            useBottomSheet()
        }


    }
}


@Composable
private fun DSWantedDialogTopAppBarPreview(
    viewState: DSWantedDialogTopAppBarDemoViewState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DesignSystemTheme.colors.backgroundNeutralSecondary)
            .padding(20.dp)
    ) {
        if (viewState.variant is Variant.Search) {
            DemoDialogSearchTopAppBar(viewState = viewState)
        } else if (viewState.showCloseButton) {
            WantedDialogCloseTopAppBar(
                variant = viewState.variant,
                backgroundColor = if (viewState.backgroundColorEnabled) {
                    DesignSystemTheme.colors.surfaceElevatedPrimary
                } else {
                    Color.Transparent
                },
                background = viewState.background,
                scrollableState = if (viewState.scrollable) rememberScrollState() else null,
                title = "Dialog Title",
                navigationIcon = if (viewState.navigationIcon) {
                    {
                        WantedTopAppBarIconButton(
                            painter = painterResource(R.drawable.icon_normal_arrow_left),
                            onClick = { }
                        )
                    }
                } else null,
                onClickClose = { }
            )
        } else {
            WantedDialogTopAppBar(
                variant = viewState.variant,
                backgroundColor = if (viewState.backgroundColorEnabled) {
                    DesignSystemTheme.colors.surfaceElevatedPrimary
                } else {
                    Color.Transparent
                },
                background = viewState.background,
                scrollableState = if (viewState.scrollable) rememberScrollState() else null,
                title = "Dialog Title",
                navigationIcon = if (viewState.navigationIcon) {
                    {
                        WantedTopAppBarIconButton(
                            painter = painterResource(R.drawable.icon_normal_arrow_left),
                            onClick = { }
                        )
                    }
                } else null,
                actions = if (viewState.actions) {
                    {
                        WantedTopAppBarIconButton(
                            painter = painterResource(R.drawable.icon_normal_share),
                            onClick = { }
                        )
                    }
                } else null
            )
        }
    }
}

@Composable
private fun DemoDialogTopAppBar(
    viewState: DSWantedDialogTopAppBarDemoViewState,
    scrollState: ScrollableState? = null
) {
    if (viewState.variant is Variant.Search) {
        DemoDialogSearchTopAppBar(viewState = viewState, scrollState = scrollState)
    } else if (viewState.showCloseButton) {
        WantedDialogCloseTopAppBar(
            variant = viewState.variant,
            backgroundColor = if (viewState.backgroundColorEnabled) {
                DesignSystemTheme.colors.surfaceElevatedPrimary
            } else {
                Color.Transparent
            },
            background = viewState.background,
            scrollableState = if (viewState.scrollable) scrollState else null,
            title = "Dialog Title",
            navigationIcon = if (viewState.navigationIcon) {
                {
                    WantedTopAppBarIconButton(
                        painter = painterResource(R.drawable.icon_normal_arrow_left),
                        onClick = { }
                    )
                }
            } else null,
            onClickClose = { }
        )
    } else {
        WantedDialogTopAppBar(
            variant = viewState.variant,
            backgroundColor = if (viewState.backgroundColorEnabled) {
                DesignSystemTheme.colors.surfaceElevatedPrimary
            } else {
                Color.Transparent
            },
            background = viewState.background,
            scrollableState = if (viewState.scrollable) scrollState else null,
            title = "Dialog Title",
            navigationIcon = if (viewState.navigationIcon) {
                {
                    WantedTopAppBarIconButton(
                        painter = painterResource(R.drawable.icon_normal_arrow_left),
                        onClick = { }
                    )
                }
            } else null,
            actions = if (viewState.actions) {
                {
                    WantedTopAppBarIconButton(
                        painter = painterResource(R.drawable.icon_normal_share),
                        onClick = { }
                    )
                }
            } else null
        )
    }

}

// Variant.Search 는 제목 대신 검색 필드를, 우측에는 actions 대신 취소 텍스트 버튼을 둔다.
@Composable
private fun DemoDialogSearchTopAppBar(
    viewState: DSWantedDialogTopAppBarDemoViewState,
    scrollState: ScrollableState? = null
) {
    var keyword by remember { mutableStateOf("") }

    WantedDialogSearchTopAppBar(
        text = keyword,
        backgroundColor = if (viewState.backgroundColorEnabled) {
            DesignSystemTheme.colors.surfaceElevatedPrimary
        } else {
            Color.Transparent
        },
        background = viewState.background,
        scrollableState = if (viewState.scrollable) scrollState else null,
        placeholder = "검색어를 입력해 주세요.",
        cancelText = "취소",
        navigationIcon = if (viewState.navigationIcon) {
            {
                WantedTopAppBarIconButton(
                    painter = painterResource(R.drawable.icon_normal_arrow_left),
                    onClick = { }
                )
            }
        } else null,
        onValueChange = { keyword = it }
    )
}

@Composable
private fun DSWantedDialogTopAppBarModalContent(
    viewState: DSWantedDialogTopAppBarDemoViewState,
    scrollState: LazyListState
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = scrollState
        ) {
            item {
                val range = if (viewState.listRevert) (50 downTo 0) else (0..50)
                for (index in range) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(
                                Color(
                                    red = (index * 5 % 256) / 255f,
                                    green = (index * 10 % 256) / 255f,
                                    blue = (index * 15 % 256) / 255f,
                                    alpha = 1f
                                )
                            )
                    )
                }
            }
        }

        if (viewState.variant is Variant.Floating) {
            DemoDialogTopAppBar(
                viewState = viewState,
                scrollState = scrollState
            )
        }
    }
}

@DevicePreviews
@Composable
private fun DSWantedDialogTopAppBarDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedDialogTopAppBarDemoScreenContent(
            viewState = DSWantedDialogTopAppBarDemoViewState(),
            onViewEvent = { }
        )
    }
}

// sealed class 라 enum 의 name 이 없다. 선택 UI 표기는 클래스 이름으로 맞춘다.
private fun variantName(variant: Variant): String = variant::class.simpleName.orEmpty()
