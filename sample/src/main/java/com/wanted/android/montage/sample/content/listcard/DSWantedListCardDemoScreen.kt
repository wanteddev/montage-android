package com.wanted.android.montage.sample.content.listcard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.designsystem.R
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.content.listcard.DSWantedListCardDemoScreenContract.DSWantedListCardDemoEvent
import com.wanted.android.montage.sample.content.listcard.DSWantedListCardDemoScreenContract.DSWantedListCardDemoSideEffect
import com.wanted.android.montage.sample.content.listcard.DSWantedListCardDemoScreenContract.DSWantedListCardDemoViewEvent
import com.wanted.android.montage.sample.content.listcard.DSWantedListCardDemoScreenContract.DSWantedListCardDemoViewState
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.contents.card.listcard.WantedListCard
import com.wanted.android.wanted.design.contents.contentbadge.WantedContentBadge
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedListCardDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedListCardDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val clipboardManager = LocalClipboardManager.current

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedListCardDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedListCardDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedListCardDemoViewEvent.OnClickBack -> onClickBack()
            DSWantedListCardDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedListCardDemoEvent.ShowCode(true))
            }

            DSWantedListCardDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedListCardDemoEvent.CopyCode)
            }

            is DSWantedListCardDemoViewEvent.OnLoadingChanged -> {
                viewModel.setEvent(DSWantedListCardDemoEvent.SetLoading(viewEvent.loading))
            }

            is DSWantedListCardDemoViewEvent.OnTopContentChanged -> {
                viewModel.setEvent(DSWantedListCardDemoEvent.SetTopContent(viewEvent.enabled))
            }

            is DSWantedListCardDemoViewEvent.OnBottomContentChanged -> {
                viewModel.setEvent(DSWantedListCardDemoEvent.SetBottomContent(viewEvent.enabled))
            }

            is DSWantedListCardDemoViewEvent.OnLeadingContentChanged -> {
                viewModel.setEvent(DSWantedListCardDemoEvent.SetLeadingContent(viewEvent.enabled))
            }

            is DSWantedListCardDemoViewEvent.OnTrailingContentChanged -> {
                viewModel.setEvent(DSWantedListCardDemoEvent.SetTrailingContent(viewEvent.enabled))
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedListCardDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedListCardDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedListCardDemoEvent.ShowCode(false))
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
private fun DSWantedListCardDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedListCardDemoViewState,
    onViewEvent: (DSWantedListCardDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedListCard") {
                onViewEvent(DSWantedListCardDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedListCardDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                neutral = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedListCardDemoViewEvent.OnClickCopyCode)
                        }
                    )
                },
            )
        }
    ) { innerPadding ->
        DSWantedListCardDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                WantedListCard(
                    modifier = Modifier.fillMaxWidth(),
                    title = "ListCard Title",
                    caption = "Caption",
                    extraCaption = "Extra Caption",
                    isLoading = viewState.isLoading,
                    topContent = if (viewState.topContentEnabled) {
                        { WantedContentBadge(text = "상단") }
                    } else {
                        null
                    },
                    bottomContent = if (viewState.bottomContentEnabled) {
                        { WantedContentBadge(text = "하단") }
                    } else {
                        null
                    },
                    leadingContent = if (viewState.leadingContentEnabled) {
                        {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_normal_company),
                                contentDescription = null
                            )
                        }
                    } else {
                        null
                    },
                    trailingContent = if (viewState.trailingContentEnabled) {
                        {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_normal_company),
                                contentDescription = null
                            )
                        }
                    } else {
                        null
                    },
                    onClick = {}
                )
            },
            loading = {
                DSWantedOptionSwitchCell(
                    text = "isLoading : ${viewState.isLoading}",
                    checkState = viewState.isLoading,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedListCardDemoViewEvent.OnLoadingChanged(checked))
                    }
                )
            },
            topContent = {
                DSWantedOptionSwitchCell(
                    text = "topContent : ${viewState.topContentEnabled}",
                    checkState = viewState.topContentEnabled,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedListCardDemoViewEvent.OnTopContentChanged(checked))
                    }
                )
            },
            bottomContent = {
                DSWantedOptionSwitchCell(
                    text = "bottomContent : ${viewState.bottomContentEnabled}",
                    checkState = viewState.bottomContentEnabled,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedListCardDemoViewEvent.OnBottomContentChanged(checked))
                    }
                )
            },
            leadingContent = {
                DSWantedOptionSwitchCell(
                    text = "leadingContent : ${viewState.leadingContentEnabled}",
                    checkState = viewState.leadingContentEnabled,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedListCardDemoViewEvent.OnLeadingContentChanged(checked))
                    }
                )
            },
            trailingContent = {
                DSWantedOptionSwitchCell(
                    text = "trailingContent : ${viewState.trailingContentEnabled}",
                    checkState = viewState.trailingContentEnabled,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedListCardDemoViewEvent.OnTrailingContentChanged(checked))
                    }
                )
            }
        )
    }
}

@Composable
private fun DSWantedListCardDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    loading: @Composable () -> Unit,
    topContent: @Composable () -> Unit,
    bottomContent: @Composable () -> Unit,
    leadingContent: @Composable () -> Unit,
    trailingContent: @Composable () -> Unit,
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
        loading()
        topContent()
        bottomContent()
        leadingContent()
        trailingContent()
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@DevicePreviews
@Composable
private fun DSWantedListCardDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedListCardDemoScreen(
            onClickBack = {}
        )
    }
}