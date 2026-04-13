package com.wanted.android.montage.sample.loading.pulltorefresh

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.designsystem.R
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.loading.pulltorefresh.DSWantedPullToRefreshDemoScreenContract.DSWantedPullToRefreshDemoEvent
import com.wanted.android.montage.sample.loading.pulltorefresh.DSWantedPullToRefreshDemoScreenContract.DSWantedPullToRefreshDemoSideEffect
import com.wanted.android.montage.sample.loading.pulltorefresh.DSWantedPullToRefreshDemoScreenContract.DSWantedPullToRefreshDemoViewEvent
import com.wanted.android.montage.sample.loading.pulltorefresh.DSWantedPullToRefreshDemoScreenContract.DSWantedPullToRefreshDemoViewState
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.loading.pulltorefresh.WantedPullToRefreshBox
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle
import kotlinx.coroutines.delay

@Composable
fun DSWantedPullToRefreshDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedPullToRefreshDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val clipboardManager = LocalClipboardManager.current

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedPullToRefreshDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedPullToRefreshDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedPullToRefreshDemoViewEvent.OnClickBack -> onClickBack()
            DSWantedPullToRefreshDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedPullToRefreshDemoEvent.ShowCode(true))
            }

            DSWantedPullToRefreshDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedPullToRefreshDemoEvent.CopyCode)
            }

            is DSWantedPullToRefreshDemoViewEvent.OnRefreshingChanged -> {
                viewModel.setEvent(DSWantedPullToRefreshDemoEvent.SetRefreshing(viewEvent.refreshing))
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedPullToRefreshDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedPullToRefreshDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedPullToRefreshDemoEvent.ShowCode(false))
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
private fun DSWantedPullToRefreshDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedPullToRefreshDemoViewState,
    onViewEvent: (DSWantedPullToRefreshDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedPullToRefreshBox") {
                onViewEvent(DSWantedPullToRefreshDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedPullToRefreshDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                neutral = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedPullToRefreshDemoViewEvent.OnClickCopyCode)
                        }
                    )
                },
            )
        }
    ) { innerPadding ->
        DSWantedPullToRefreshDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(360.dp)
                ) {
                    WantedPullToRefreshBox(
                        modifier = Modifier.fillMaxSize(),
                        isRefreshing = viewState.isRefreshing,
                        onRefresh = {
                            onViewEvent(
                                DSWantedPullToRefreshDemoViewEvent.OnRefreshingChanged(true)
                            )
                        }
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items((1..20).toList()) { index ->
                                Text(
                                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                                    text = "Pulled item $index"
                                )
                            }
                        }
                    }
                }
            },
            option = {
                DSWantedOptionSwitchCell(
                    text = "isRefreshing : ${viewState.isRefreshing}",
                    checkState = viewState.isRefreshing,
                    onCheckChanged = {
                        onViewEvent(DSWantedPullToRefreshDemoViewEvent.OnRefreshingChanged(it))
                    }
                )
            }
        )
    }

    LaunchedEffect(viewState.isRefreshing) {
        if (viewState.isRefreshing) {
            delay(1000)
            onViewEvent(DSWantedPullToRefreshDemoViewEvent.OnRefreshingChanged(false))
        }
    }
}

@Composable
private fun DSWantedPullToRefreshDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    option: @Composable () -> Unit,
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.line_normal_normal),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(20.dp)
        ) {
            preview()
        }

        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "Option",
            style = WantedTextStyle(
                colorRes = R.color.label_strong,
                style = DesignSystemTheme.typography.heading2Bold
            )
        )
        option()

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "리스트를 아래로 당겨 새로고침을 확인하세요.",
            style = WantedTextStyle(
                colorRes = R.color.label_neutral,
                style = DesignSystemTheme.typography.body2ReadingRegular
            )
        )
    }
}

@DevicePreviews
@Composable
private fun DSWantedPullToRefreshDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedPullToRefreshDemoScreenContent(
            viewState = DSWantedPullToRefreshDemoViewState(),
            onViewEvent = { }
        )
    }
}
