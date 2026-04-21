package com.wanted.android.montage.sample.navigations.pagecounter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.designsystem.R
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.navigations.pagecounter.DSWantedPageCounterDemoScreenContract.DSWantedPageCounterDemoEvent
import com.wanted.android.montage.sample.navigations.pagecounter.DSWantedPageCounterDemoScreenContract.DSWantedPageCounterDemoSideEffect
import com.wanted.android.montage.sample.navigations.pagecounter.DSWantedPageCounterDemoScreenContract.DSWantedPageCounterDemoViewEvent
import com.wanted.android.montage.sample.navigations.pagecounter.DSWantedPageCounterDemoScreenContract.DSWantedPageCounterDemoViewState
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.navigations.pagination.pagecounter.WantedPageCounter
import com.wanted.android.wanted.design.navigations.pagination.pagecounter.WantedPaginationCounterDefaults.WantedPageCounterSize
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedPageCounterDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedPageCounterDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val clipboardManager = LocalClipboardManager.current

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedPageCounterDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedPageCounterDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedPageCounterDemoViewEvent.OnClickBack -> onClickBack()
            DSWantedPageCounterDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedPageCounterDemoEvent.ShowCode(true))
            }

            DSWantedPageCounterDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedPageCounterDemoEvent.CopyCode)
            }

            is DSWantedPageCounterDemoViewEvent.OnSizeChanged -> {
                viewModel.setEvent(DSWantedPageCounterDemoEvent.SetSize(viewEvent.size))
            }

            is DSWantedPageCounterDemoViewEvent.OnAlternativeChanged -> {
                viewModel.setEvent(DSWantedPageCounterDemoEvent.SetAlternative(viewEvent.enabled))
            }

            is DSWantedPageCounterDemoViewEvent.OnCurrentIndexChanged -> {
                viewModel.setEvent(DSWantedPageCounterDemoEvent.SetCurrentIndex(viewEvent.index))
            }

            is DSWantedPageCounterDemoViewEvent.OnTotalCountChanged -> {
                viewModel.setEvent(DSWantedPageCounterDemoEvent.SetTotalCount(viewEvent.count))
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedPageCounterDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedPageCounterDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedPageCounterDemoEvent.ShowCode(false))
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
private fun DSWantedPageCounterDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedPageCounterDemoViewState,
    onViewEvent: (DSWantedPageCounterDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedPageCounter") {
                onViewEvent(DSWantedPageCounterDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedPageCounterDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                neutral = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedPageCounterDemoViewEvent.OnClickCopyCode)
                        }
                    )
                },
            )
        }
    ) { innerPadding ->
        DSWantedPageCounterDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                WantedPageCounter(
                    totalPageCount = viewState.totalCount,
                    currentIndex = viewState.currentIndex,
                    size = viewState.size,
                    isAlternative = viewState.isAlternative
                )
            },
            size = {
                WantedSelect(
                    value = "size : ${viewState.size.name}",
                    selectedValue = viewState.size.name,
                    selectValueList = listOf("Small", "Normal"),
                    onSelect = { sizeName ->
                        val size = if (sizeName == "Small") {
                            WantedPageCounterSize.Small
                        } else {
                            WantedPageCounterSize.Normal
                        }
                        onViewEvent(DSWantedPageCounterDemoViewEvent.OnSizeChanged(size))
                    }
                )
            },
            alternative = {
                DSWantedOptionSwitchCell(
                    text = "isAlternative : ${viewState.isAlternative}",
                    checkState = viewState.isAlternative,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedPageCounterDemoViewEvent.OnAlternativeChanged(checked))
                    }
                )
            },
            pageControl = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedButton(
                        modifier = Modifier.weight(1f),
                        text = "-",
                        onClick = {
                            onViewEvent(
                                DSWantedPageCounterDemoViewEvent.OnCurrentIndexChanged(
                                    (viewState.currentIndex - 1).coerceAtLeast(1)
                                )
                            )
                        }
                    )
                    WantedButton(
                        modifier = Modifier.weight(1f),
                        text = "+",
                        onClick = {
                            onViewEvent(
                                DSWantedPageCounterDemoViewEvent.OnCurrentIndexChanged(
                                    (viewState.currentIndex + 1).coerceAtMost(viewState.totalCount)
                                )
                            )
                        }
                    )
                }
            },
            totalControl = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedButton(
                        modifier = Modifier.weight(1f),
                        text = "Total -",
                        onClick = {
                            val next = (viewState.totalCount - 1).coerceAtLeast(1)
                            onViewEvent(DSWantedPageCounterDemoViewEvent.OnTotalCountChanged(next))
                        }
                    )
                    WantedButton(
                        modifier = Modifier.weight(1f),
                        text = "Total +",
                        onClick = {
                            onViewEvent(
                                DSWantedPageCounterDemoViewEvent.OnTotalCountChanged(viewState.totalCount + 1)
                            )
                        }
                    )
                }
            }
        )
    }
}

@Composable
private fun DSWantedPageCounterDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    size: @Composable () -> Unit,
    alternative: @Composable () -> Unit,
    pageControl: @Composable () -> Unit,
    totalControl: @Composable () -> Unit,
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
        size()
        alternative()
        pageControl()
        totalControl()
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@DevicePreviews
@Composable
private fun DSWantedPageCounterDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedPageCounterDemoScreen(
            onClickBack = {}
        )
    }
}
