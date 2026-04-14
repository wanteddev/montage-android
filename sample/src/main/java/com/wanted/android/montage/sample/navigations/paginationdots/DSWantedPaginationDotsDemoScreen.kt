package com.wanted.android.montage.sample.navigations.paginationdots

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
import com.wanted.android.montage.sample.navigations.paginationdots.DSWantedPaginationDotsDemoScreenContract.DSWantedPaginationDotsDemoEvent
import com.wanted.android.montage.sample.navigations.paginationdots.DSWantedPaginationDotsDemoScreenContract.DSWantedPaginationDotsDemoSideEffect
import com.wanted.android.montage.sample.navigations.paginationdots.DSWantedPaginationDotsDemoScreenContract.DSWantedPaginationDotsDemoViewEvent
import com.wanted.android.montage.sample.navigations.paginationdots.DSWantedPaginationDotsDemoScreenContract.DSWantedPaginationDotsDemoViewState
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.navigations.pagination.paginationdots.WantedDotIndicator
import com.wanted.android.wanted.design.navigations.pagination.paginationdots.WantedPaginationDotDefaults.WantedDotIndicatorSize
import com.wanted.android.wanted.design.navigations.pagination.paginationdots.WantedPaginationDotDefaults.WantedDotIndicatorType
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedPaginationDotsDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedPaginationDotsDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val clipboardManager = LocalClipboardManager.current

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedPaginationDotsDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedPaginationDotsDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedPaginationDotsDemoViewEvent.OnClickBack -> onClickBack()
            DSWantedPaginationDotsDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedPaginationDotsDemoEvent.ShowCode(true))
            }

            DSWantedPaginationDotsDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedPaginationDotsDemoEvent.CopyCode)
            }

            is DSWantedPaginationDotsDemoViewEvent.OnSizeChanged -> {
                viewModel.setEvent(DSWantedPaginationDotsDemoEvent.SetSize(viewEvent.size))
            }

            is DSWantedPaginationDotsDemoViewEvent.OnTypeChanged -> {
                viewModel.setEvent(DSWantedPaginationDotsDemoEvent.SetType(viewEvent.type))
            }

            is DSWantedPaginationDotsDemoViewEvent.OnTotalCountChanged -> {
                viewModel.setEvent(DSWantedPaginationDotsDemoEvent.SetTotalCount(viewEvent.count))
            }

            is DSWantedPaginationDotsDemoViewEvent.OnVisibleCountChanged -> {
                viewModel.setEvent(DSWantedPaginationDotsDemoEvent.SetVisibleCount(viewEvent.count))
            }

            is DSWantedPaginationDotsDemoViewEvent.OnCurrentIndexChanged -> {
                viewModel.setEvent(DSWantedPaginationDotsDemoEvent.SetCurrentIndex(viewEvent.index))
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedPaginationDotsDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedPaginationDotsDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedPaginationDotsDemoEvent.ShowCode(false))
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
private fun DSWantedPaginationDotsDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedPaginationDotsDemoViewState,
    onViewEvent: (DSWantedPaginationDotsDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedDotIndicator") {
                onViewEvent(DSWantedPaginationDotsDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedPaginationDotsDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                neutral = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedPaginationDotsDemoViewEvent.OnClickCopyCode)
                        }
                    )
                },
            )
        }
    ) { innerPadding ->
        DSWantedPaginationDotsDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                WantedDotIndicator(
                    totalPageCount = viewState.totalCount,
                    visibleDotCount = viewState.visibleCount,
                    currentIndex = viewState.currentIndex,
                    size = viewState.size,
                    type = viewState.type
                )
            },
            size = {
                WantedSelect(
                    value = "size : ${viewState.size.name}",
                    selectedValue = viewState.size.name,
                    selectValueList = listOf("Small", "Medium"),
                    onSelect = { sizeName ->
                        val size = if (sizeName == "Small") {
                            WantedDotIndicatorSize.Small
                        } else {
                            WantedDotIndicatorSize.Medium
                        }
                        onViewEvent(DSWantedPaginationDotsDemoViewEvent.OnSizeChanged(size))
                    }
                )
            },
            type = {
                WantedSelect(
                    value = "type : ${viewState.type.name}",
                    selectedValue = viewState.type.name,
                    selectValueList = listOf("Normal", "White"),
                    onSelect = { typeName ->
                        val type = if (typeName == "White") {
                            WantedDotIndicatorType.White
                        } else {
                            WantedDotIndicatorType.Normal
                        }
                        onViewEvent(DSWantedPaginationDotsDemoViewEvent.OnTypeChanged(type))
                    }
                )
            },
            indexControl = {
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
                                DSWantedPaginationDotsDemoViewEvent.OnCurrentIndexChanged(
                                    (viewState.currentIndex - 1).coerceAtLeast(0)
                                )
                            )
                        }
                    )
                    WantedButton(
                        modifier = Modifier.weight(1f),
                        text = "+",
                        onClick = {
                            onViewEvent(
                                DSWantedPaginationDotsDemoViewEvent.OnCurrentIndexChanged(
                                    (viewState.currentIndex + 1).coerceAtMost(viewState.totalCount - 1)
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
                            onViewEvent(DSWantedPaginationDotsDemoViewEvent.OnTotalCountChanged(next))
                        }
                    )
                    WantedButton(
                        modifier = Modifier.weight(1f),
                        text = "Total +",
                        onClick = {
                            onViewEvent(
                                DSWantedPaginationDotsDemoViewEvent.OnTotalCountChanged(viewState.totalCount + 1)
                            )
                        }
                    )
                }
            },
            visibleControl = {
                DSWantedOptionSwitchCell(
                    text = "visibleCount : ${viewState.visibleCount}",
                    checkState = viewState.visibleCount > 3,
                    onCheckChanged = { checked ->
                        val count = if (checked) 7 else 3
                        onViewEvent(DSWantedPaginationDotsDemoViewEvent.OnVisibleCountChanged(count))
                    }
                )
            }
        )
    }
}

@Composable
private fun DSWantedPaginationDotsDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    size: @Composable () -> Unit,
    type: @Composable () -> Unit,
    indexControl: @Composable () -> Unit,
    totalControl: @Composable () -> Unit,
    visibleControl: @Composable () -> Unit,
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
        type()
        indexControl()
        totalControl()
        visibleControl()
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@DevicePreviews
@Composable
private fun DSWantedPaginationDotsDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedPaginationDotsDemoScreen(
            onClickBack = {}
        )
    }
}
