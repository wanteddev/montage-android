package com.wanted.android.montage.sample.navigations.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.designsystem.R
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.navigations.topbar.DSWantedTopAppBarDemoScreenContract.DSWantedTopAppBarDemoEvent
import com.wanted.android.montage.sample.navigations.topbar.DSWantedTopAppBarDemoScreenContract.DSWantedTopAppBarDemoSideEffect
import com.wanted.android.montage.sample.navigations.topbar.DSWantedTopAppBarDemoScreenContract.DSWantedTopAppBarDemoViewEvent
import com.wanted.android.montage.sample.navigations.topbar.DSWantedTopAppBarDemoScreenContract.DSWantedTopAppBarDemoViewState
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBar
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarContract.Variant
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarIconButton
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
fun DSWantedTopAppBarDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedTopAppBarDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedTopAppBarDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedTopAppBarDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedTopAppBarDemoViewEvent.OnClickBack -> onClickBack()
            DSWantedTopAppBarDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedTopAppBarDemoEvent.ShowCode(true))
            }

            DSWantedTopAppBarDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedTopAppBarDemoEvent.CopyCode)
            }

            is DSWantedTopAppBarDemoViewEvent.OnSelectedType -> {
                viewModel.setEvent(DSWantedTopAppBarDemoEvent.SetType(viewEvent.type))
            }

            is DSWantedTopAppBarDemoViewEvent.OnChangeBackground -> {
                viewModel.setEvent(DSWantedTopAppBarDemoEvent.SetBackground(viewEvent.background))
            }

            is DSWantedTopAppBarDemoViewEvent.OnChangeTitleAlignCenter -> {
                viewModel.setEvent(DSWantedTopAppBarDemoEvent.SetTitleAlignCenter(viewEvent.titleAlignCenter))
            }

            is DSWantedTopAppBarDemoViewEvent.OnChangeNavigationIcon -> {
                viewModel.setEvent(DSWantedTopAppBarDemoEvent.SetNavigationIcon(viewEvent.navigationIcon))
            }

            is DSWantedTopAppBarDemoViewEvent.OnChangeActions -> {
                viewModel.setEvent(DSWantedTopAppBarDemoEvent.SetActions(viewEvent.actions))
            }

            is DSWantedTopAppBarDemoViewEvent.OnChangeScrollable -> {
                viewModel.setEvent(DSWantedTopAppBarDemoEvent.SetScrollable(viewEvent.scrollable))
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedTopAppBarDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedTopAppBarDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedTopAppBarDemoEvent.ShowCode(false))
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
private fun DSWantedTopAppBarDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedTopAppBarDemoViewState,
    onViewEvent: (DSWantedTopAppBarDemoViewEvent) -> Unit
) {

    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier,
        topBar = {
            WantedTopAppBar(
                variant = viewState.variant,
                background = viewState.background,
                titleAlignCenter = viewState.titleAlignCenter,
                scrollableState = if (viewState.scrollable) scrollState else null,
                title = "Sample Title",
                navigationIcon = if (viewState.navigationIcon) {
                    {
                        WantedTopAppBarIconButton(
                            variant = viewState.variant,
                            painter = painterResource(R.drawable.icon_normal_arrow_left),
                            onClick = { }
                        )
                    }
                } else null,
                actions = if (viewState.actions) {
                    {
                        WantedTopAppBarIconButton(
                            variant = viewState.variant,
                            painter = painterResource(R.drawable.icon_normal_share),
                            onClick = { }
                        )

                        WantedTopAppBarIconButton(
                            variant = viewState.variant,
                            painter = painterResource(R.drawable.icon_normal_share),
                            onClick = { }
                        )
                    }
                } else null
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
                            onViewEvent(DSWantedTopAppBarDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                negative = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        size = ButtonSize.SMALL,
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedTopAppBarDemoViewEvent.OnClickCopyCode)
                        }
                    )
                }
            )
        }
    ) { innerPadding ->

        DSWantedTooltipDemoScreenLayout(
            modifier = if (viewState.variant == Variant.Floating) {
                Modifier
                    .padding(bottom = innerPadding.calculateBottomPadding())
                    .consumeWindowInsets(innerPadding)
                    .verticalScroll(scrollState)
                    .padding(vertical = 20.dp)
                    .padding(
                        top = with(LocalDensity.current) {
                            WindowInsets.systemBars.getTop(this).toDp()
                        }
                    )
                    .padding(
                        top = 30.dp
                    )
            } else {
                Modifier
                    .padding(innerPadding)
                    .verticalScroll(scrollState)
                    .padding(vertical = 20.dp)
            },
            type = {
                WantedSelect(
                    value = "type : ${viewState.variant.name}",
                    selectedValue = viewState.variant.name,
                    selectValueList = Variant.entries
                        .filter { it != Variant.Search }
                        .map { it.name },
                    onSelect = { typeName ->
                        onViewEvent(
                            DSWantedTopAppBarDemoViewEvent.OnSelectedType(
                                Variant.valueOf(
                                    typeName
                                )
                            )
                        )
                    }
                )
            },
            background = {
                DSWantedOptionSwitchCell(
                    text = "background : ${viewState.background}",
                    checkState = viewState.background,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedTopAppBarDemoViewEvent.OnChangeBackground(checked))
                    }
                )
            },
            titleAlignCenter = {
                DSWantedOptionSwitchCell(
                    text = "titleAlignCenter : ${viewState.titleAlignCenter}",
                    checkState = viewState.titleAlignCenter,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedTopAppBarDemoViewEvent.OnChangeTitleAlignCenter(checked))
                    }
                )
            },
            scrollable = {
                DSWantedOptionSwitchCell(
                    text = "scrollable : ${viewState.scrollable}",
                    checkState = viewState.scrollable,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedTopAppBarDemoViewEvent.OnChangeScrollable(checked))
                    }
                )
            },
            navigationIcon = {
                DSWantedOptionSwitchCell(
                    text = "navigationIcon : ${viewState.navigationIcon}",
                    checkState = viewState.navigationIcon,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedTopAppBarDemoViewEvent.OnChangeNavigationIcon(checked))
                    }
                )
            },
            actions = {
                DSWantedOptionSwitchCell(
                    text = "actions : ${viewState.actions}",
                    checkState = viewState.actions,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedTopAppBarDemoViewEvent.OnChangeActions(checked))
                    }
                )
            },
            preview = {
                DSWantedTopAppBarPreview(viewState = viewState)
            },
            otherContent = {
                for (index in 0..50) {
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
        )
    }
}


@Composable
private fun DSWantedTooltipDemoScreenLayout(
    modifier: Modifier = Modifier,
    type: @Composable () -> Unit,
    background: @Composable () -> Unit,
    titleAlignCenter: @Composable () -> Unit,
    scrollable: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable () -> Unit,
    preview: @Composable () -> Unit,
    otherContent: @Composable ColumnScope.() -> Unit
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
                    colorRes = R.color.label_strong,
                    style = DesignSystemTheme.typography.heading2Bold
                )
            )
            DSWantedPreviewContainer {
                preview()
            }
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
                    colorRes = R.color.label_strong,
                    style = DesignSystemTheme.typography.heading2Bold
                )
            )

            type()
            background()
            titleAlignCenter()
            scrollable()
            navigationIcon()
            actions()
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            otherContent()
        }
    }
}


@Composable
private fun DSWantedTopAppBarPreview(
    viewState: DSWantedTopAppBarDemoViewState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DesignSystemTheme.colors.backgroundNormalAlternative)
            .padding(20.dp)
    ) {
        WantedTopAppBar(
            variant = viewState.variant,
            backgroundColor = if (viewState.background) {
                colorResource(R.color.background_normal_normal)
            } else {
                colorResource(R.color.transparent)
            },
            titleAlignCenter = viewState.titleAlignCenter,
            title = "Sample Title",
            navigationIcon = if (viewState.navigationIcon) {
                {
                    WantedTopAppBarIconButton(
                        variant = viewState.variant,
                        painter = painterResource(R.drawable.icon_normal_arrow_left),
                        onClick = { }
                    )
                }
            } else null,
            actions = if (viewState.actions) {
                {
                    WantedTopAppBarIconButton(
                        variant = viewState.variant,
                        painter = painterResource(R.drawable.icon_normal_share),
                        onClick = { }
                    )

                    WantedTopAppBarIconButton(
                        variant = viewState.variant,
                        painter = painterResource(R.drawable.icon_normal_share),
                        onClick = { }
                    )
                }
            } else null
        )
    }
}

@DevicePreviews
@Composable
private fun DSWantedTopAppBarDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedTopAppBarDemoScreenContent(
            viewState = DSWantedTopAppBarDemoViewState(),
            onViewEvent = { }
        )
    }
}