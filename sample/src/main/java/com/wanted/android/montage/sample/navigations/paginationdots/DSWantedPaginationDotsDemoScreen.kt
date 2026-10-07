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
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.R
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
import com.wanted.android.wanted.design.input.slider.WantedSlider
import com.wanted.android.wanted.design.navigations.pagination.paginationdots.WantedDotIndicator
import com.wanted.android.wanted.design.navigations.pagination.paginationdots.WantedPaginationDotDefaults.WantedDotIndicatorSize
import com.wanted.android.wanted.design.navigations.pagination.paginationdots.WantedPaginationDotDefaults.WantedDotIndicatorType
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopup
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle
import kotlin.math.roundToInt

// 데모에서 totalCount 슬라이더가 조절할 수 있는 최대 페이지 수
private const val MAX_TOTAL_COUNT = 30

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
        WantedPopup(
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
                main = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 보기",
                        onClick = {
                            onViewEvent(DSWantedPaginationDotsDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                sub = {
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
                WantedSlider(
                    value = viewState.totalCount.toFloat(),
                    valueRange = 1f..MAX_TOTAL_COUNT.toFloat(),
                    header = "totalCount : ${viewState.totalCount}",
                    onValueChange = { value ->
                        onViewEvent(
                            DSWantedPaginationDotsDemoViewEvent.OnTotalCountChanged(value.roundToInt())
                        )
                    }
                )
            },
            visibleControl = {
                // totalCount(=visibleCount의 max)가 바뀌면 슬라이더의 thumb 위치 기준이 달라지므로
                // key로 재생성하여 현재 visibleCount를 새 범위에 맞게 다시 반영한다.
                key(viewState.totalCount) {
                    WantedSlider(
                        value = viewState.visibleCount.toFloat(),
                        valueRange = 1f..viewState.totalCount.toFloat().coerceAtLeast(2f),
                        header = "visibleCount : ${viewState.visibleCount}",
                        onValueChange = { value ->
                            // 슬라이더 범위는 thumb 표시를 위해 최소 2까지 열려 있으나(coerceAtLeast(2f)),
                            // visibleCount는 totalCount를 넘을 수 없으므로 이벤트 값을 totalCount로 clamp한다.
                            onViewEvent(
                                DSWantedPaginationDotsDemoViewEvent.OnVisibleCountChanged(
                                    value.roundToInt().coerceAtMost(viewState.totalCount)
                                )
                            )
                        }
                    )
                }
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
                colorRes = R.color.foreground_neutral_strong,
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
                colorRes = R.color.foreground_neutral_strong,
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
