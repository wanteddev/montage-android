package com.wanted.android.montage.sample.input.segmentedcontrol

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
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
import com.wanted.android.montage.sample.R
import com.wanted.android.montage.sample.input.segmentedcontrol.DSWantedSegmentedControlDemoScreenContract.DSWantedSegmentedControlDemoEvent
import com.wanted.android.montage.sample.input.segmentedcontrol.DSWantedSegmentedControlDemoScreenContract.DSWantedSegmentedControlDemoSideEffect
import com.wanted.android.montage.sample.input.segmentedcontrol.DSWantedSegmentedControlDemoScreenContract.DSWantedSegmentedControlDemoViewEvent
import com.wanted.android.montage.sample.input.segmentedcontrol.DSWantedSegmentedControlDemoScreenContract.DSWantedSegmentedControlDemoViewState
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.segmentedcontrol.WantedSegmentedControl
import com.wanted.android.wanted.design.input.segmentedcontrol.WantedSegmentedControlItem
import com.wanted.android.wanted.design.input.segmentedcontrol.WantedSegmentedDefaults.SegmentedSize
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.navigations.topbar.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopup
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedSegmentedControlDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedSegmentedControlDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedSegmentedControlDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedSegmentedControlDemoScreenImpl(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            is DSWantedSegmentedControlDemoViewEvent.OnClickBack -> onClickBack()
            is DSWantedSegmentedControlDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedSegmentedControlDemoEvent.ShowCode(true))
            }

            is DSWantedSegmentedControlDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedSegmentedControlDemoEvent.CopyCode)
            }

            is DSWantedSegmentedControlDemoViewEvent.OnSelectSize -> {
                viewModel.setEvent(DSWantedSegmentedControlDemoEvent.SetSize(viewEvent.size))
            }

            is DSWantedSegmentedControlDemoViewEvent.OnSelectMode -> {
                viewModel.setEvent(DSWantedSegmentedControlDemoEvent.SetMode(viewEvent.mode))
            }

            is DSWantedSegmentedControlDemoViewEvent.OnClickSegment -> {
                viewModel.setEvent(DSWantedSegmentedControlDemoEvent.SetSelectedIndex(viewEvent.index))
            }

            is DSWantedSegmentedControlDemoViewEvent.OnSelectIcon -> {
                viewModel.setEvent(
                    DSWantedSegmentedControlDemoEvent.SetIcon(viewEvent.index, viewEvent.icon)
                )
            }
        }
    }

    if (viewState.isShowCode) {
        WantedPopup(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedSegmentedControlDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedSegmentedControlDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedSegmentedControlDemoEvent.ShowCode(false))
            },
            content = {
                Text(text = viewState.code)
            }
        )
    }
}

@Composable
private fun DSWantedSegmentedControlDemoScreenImpl(
    modifier: Modifier = Modifier,
    viewState: DSWantedSegmentedControlDemoViewState,
    onViewEvent: (DSWantedSegmentedControlDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedSegmentedControl") {
                onViewEvent(DSWantedSegmentedControlDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedSegmentedControlDemoViewEvent.OnClickShowCode)
                        }
                    )
                }
            )
        }
    ) { innerPadding ->
        DSWantedSegmentedControlDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                DSWantedSegmentedControlPreview(
                    viewState = viewState,
                    onClickSegment = {
                        onViewEvent(DSWantedSegmentedControlDemoViewEvent.OnClickSegment(it))
                    }
                )
            },
            size = {
                WantedSelect(
                    value = "Size : ${viewState.selectedSize.name}",
                    selectedValue = viewState.selectedSize.name,
                    selectValueList = viewState.sizeList.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedSegmentedControlDemoViewEvent.OnSelectSize(SegmentedSize.valueOf(it))
                        )
                    },
                )
            },
            mode = {
                WantedSelect(
                    value = "Content : ${viewState.selectedMode.label}",
                    selectedValue = viewState.selectedMode.name,
                    selectValueList = viewState.modeList.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedSegmentedControlDemoViewEvent.OnSelectMode(SegmentedContentMode.valueOf(it))
                        )
                    },
                )
            },
            icon = {
                // 아이콘을 쓰는 모드에서만 세그먼트별 아이콘 선택 메뉴를 노출한다.
                if (viewState.selectedMode != SegmentedContentMode.Text) {
                    viewState.selectedIcons.forEachIndexed { index, selected ->
                        WantedSelect(
                            value = "Icon ${index + 1} : ${selected.label}",
                            selectedValue = selected.name,
                            selectValueList = viewState.iconOptions.map { it.name },
                            onSelect = { name ->
                                onViewEvent(
                                    DSWantedSegmentedControlDemoViewEvent.OnSelectIcon(
                                        index = index,
                                        icon = SegmentedDemoIcon.valueOf(name)
                                    )
                                )
                            },
                        )
                    }
                }
            }
        )
    }
}

@Composable
private fun DSWantedSegmentedControlPreview(
    viewState: DSWantedSegmentedControlDemoViewState,
    onClickSegment: (Int) -> Unit
) {
    when (viewState.selectedMode) {
        SegmentedContentMode.Text -> {
            WantedSegmentedControl(
                items = viewState.items,
                selectedIndex = viewState.selectedIndex,
                size = viewState.selectedSize,
                onClick = onClickSegment
            )
        }

        SegmentedContentMode.IconText -> {
            WantedSegmentedControl(
                itemCount = viewState.items.size,
                selectedIndex = viewState.selectedIndex,
                size = viewState.selectedSize,
                onClick = onClickSegment,
                item = { index ->
                    WantedSegmentedControlItem(
                        modifier = Modifier.fillMaxWidth(),
                        title = viewState.items[index],
                        isSelected = index == viewState.selectedIndex,
                        icon = {
                            Icon(
                                modifier = Modifier.fillMaxSize(),
                                painter = painterResource(id = viewState.selectedIcons[index].resId),
                                contentDescription = null
                            )
                        }
                    )
                }
            )
        }

        SegmentedContentMode.IconOnly -> {
            WantedSegmentedControl(
                itemCount = viewState.items.size,
                selectedIndex = viewState.selectedIndex,
                size = viewState.selectedSize,
                iconOnly = true,
                onClick = onClickSegment,
                item = { index ->
                    WantedSegmentedControlItem(
                        isSelected = index == viewState.selectedIndex,
                        icon = {
                            Icon(
                                modifier = Modifier.fillMaxSize(),
                                painter = painterResource(id = viewState.selectedIcons[index].resId),
                                contentDescription = null
                            )
                        }
                    )
                }
            )
        }
    }
}

@Composable
private fun DSWantedSegmentedControlDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    size: @Composable () -> Unit,
    mode: @Composable () -> Unit,
    icon: @Composable () -> Unit,
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

            size()

            mode()

            icon()
        }
    }
}

@DevicePreviews
@Composable
private fun DSWantedSegmentedControlDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedSegmentedControlDemoScreenImpl(
            viewState = DSWantedSegmentedControlDemoViewState(),
            onViewEvent = { }
        )
    }
}
