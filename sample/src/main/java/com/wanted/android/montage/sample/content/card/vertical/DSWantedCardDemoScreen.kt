package com.wanted.android.montage.sample.content.card.vertical

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.util.isNullOrBlock
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.R
import com.wanted.android.montage.sample.content.card.vertical.DSWantedCardDemoScreenContract.DSWantedCardDemoEvent
import com.wanted.android.montage.sample.content.card.vertical.DSWantedCardDemoScreenContract.DSWantedCardDemoSideEffect
import com.wanted.android.montage.sample.content.card.vertical.DSWantedCardDemoScreenContract.DSWantedCardDemoViewEvent
import com.wanted.android.montage.sample.content.card.vertical.DSWantedCardDemoScreenContract.DSWantedCardDemoViewState
import com.wanted.android.montage.sample.getStateList
import com.wanted.android.montage.sample.toMap
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.contents.card.WantedCard
import com.wanted.android.wanted.design.contents.contentbadge.ContentBadgeColor
import com.wanted.android.wanted.design.contents.contentbadge.ContentBadgeSize
import com.wanted.android.wanted.design.contents.contentbadge.ContentBadgeType
import com.wanted.android.wanted.design.contents.contentbadge.WantedContentBadge
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.OPACITY_8
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedCardDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedCardDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedCardDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedCardDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            is DSWantedCardDemoViewEvent.OnClickBack -> onClickBack()
            is DSWantedCardDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedCardDemoEvent.CopyCode)
            }

            is DSWantedCardDemoViewEvent.OnClickShowAll -> {
                viewModel.setEvent(DSWantedCardDemoEvent.ShowAll(true))
            }

            is DSWantedCardDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedCardDemoEvent.ShowCode(true))
            }

            is DSWantedCardDemoViewEvent.OnClickBottomContent -> {
                viewModel.setEvent(DSWantedCardDemoEvent.SetBottomContent(viewEvent.isChecked))
            }

            is DSWantedCardDemoViewEvent.OnClickCaption -> {
                viewModel.setEvent(DSWantedCardDemoEvent.SetCaption(viewEvent.isChecked))
            }

            is DSWantedCardDemoViewEvent.OnClickExtraCaption -> {
                viewModel.setEvent(DSWantedCardDemoEvent.SetExtraCaption(viewEvent.isChecked))

            }

            is DSWantedCardDemoViewEvent.OnClickOverlayCaption -> {
                viewModel.setEvent(DSWantedCardDemoEvent.SetOverlayCaption(viewEvent.isChecked))
            }

            is DSWantedCardDemoViewEvent.OnClickOverlayToggleIcon -> {
                viewModel.setEvent(DSWantedCardDemoEvent.SetOverlayToggleIcon(viewEvent.isChecked))
            }

            is DSWantedCardDemoViewEvent.OnClickSubCaption -> {
                viewModel.setEvent(DSWantedCardDemoEvent.SetSubCaption(viewEvent.isChecked))
            }

            is DSWantedCardDemoViewEvent.OnClickTitle -> {
                viewModel.setEvent(DSWantedCardDemoEvent.SetTitle(viewEvent.isChecked))
            }

            is DSWantedCardDemoViewEvent.OnClickTopContent -> {
                viewModel.setEvent(DSWantedCardDemoEvent.SetTopContent(viewEvent.isChecked))
            }

            is DSWantedCardDemoViewEvent.OnClickLoading -> {
                viewModel.setEvent(DSWantedCardDemoEvent.SetLoading(viewEvent.isChecked))
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedCardDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedCardDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedCardDemoEvent.ShowCode(false))
            },
            content = {
                Text(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .verticalScroll(rememberScrollState()),
                    text = viewState.code
                )
            }
        )
    }

    if (viewState.isShowAll) {
        WantedModal(
            positive = "확인",
            onClickPositive = {
                viewModel.setEvent(DSWantedCardDemoEvent.ShowAll(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedCardDemoEvent.ShowAll(false))
            },
            content = {
                DSWantedSampleAll(
                    viewState,
                    onClickCodeCopy = {
                        viewModel.setEvent(DSWantedCardDemoEvent.CopyCode)
                    }
                )
            }
        )
    }
}

@Composable
private fun DSWantedCardDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedCardDemoViewState,
    onViewEvent: (DSWantedCardDemoViewEvent) -> Unit
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedCard") {
                onViewEvent(DSWantedCardDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedCardDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                neutral = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "모든 옵션 보기",
                        variant = ButtonVariant.OUTLINED,
                        onClick = {
                            onViewEvent(DSWantedCardDemoViewEvent.OnClickShowAll)
                        }
                    )
                }
            )

        }
    ) { innerPadding ->
        DSWantedCardDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                WantedCard(
                    modifier = Modifier.width(152.dp),
                    title = if (viewState.title) "title" else "",
                    overlayCaption = if (viewState.overlayCaption) "overlayCaption" else "",
                    caption = if (viewState.caption) "caption" else "",
                    subCaption = if (viewState.subCaption) "subCaption" else "",
                    extraCaption = if (viewState.extraCaption) "extraCaption" else "",
                    thumbnail = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(4 / 3f)
                                .background(colorResource(R.color.fill_normal).copy(OPACITY_8))
                        )
                    },
                    overlayToggleIcon = viewState.overlayToggleIcon.isNullOrBlock {
                        WantedTouchArea(
                            modifier = modifier,
                            verticalPadding = 6.dp,
                            horizontalPadding = 6.dp,
                            shape = CircleShape,
                            content = {
                                Icon(
                                    modifier = Modifier.clip(CircleShape),
                                    painter = painterResource(R.drawable.icon_normal_bookmark_fill),
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = ""
                                )
                            },
                            onClick = {}
                        )
                    },
                    bottomContent = viewState.bottomContent.isNullOrBlock {
                        WantedContentBadge(
                            modifier = Modifier,
                            text = "태그",
                            size = ContentBadgeSize.XSmall,
                            type = ContentBadgeType.Solid,
                            color = ContentBadgeColor.Accent
                        )
                    },
                    isLoading = viewState.isLoading,
                    onClick = {
                        onViewEvent(DSWantedCardDemoViewEvent.OnClickCopyCode)
                    }
                )
            },
            overlayCaption = {
                DSWantedOptionSwitchCell(
                    text = "overlayCaption : ${viewState.overlayCaption}",
                    checkState = viewState.overlayCaption,
                    onCheckChanged = {
                        onViewEvent(DSWantedCardDemoViewEvent.OnClickOverlayCaption(it))
                    }
                )
            },
            title = {
                DSWantedOptionSwitchCell(
                    text = "title : ${viewState.title}",
                    checkState = viewState.title,
                    onCheckChanged = {
                        onViewEvent(DSWantedCardDemoViewEvent.OnClickTitle(it))

                    }
                )
            },
            caption = {
                DSWantedOptionSwitchCell(
                    text = "caption : ${viewState.caption}",
                    checkState = viewState.caption,
                    onCheckChanged = {
                        onViewEvent(DSWantedCardDemoViewEvent.OnClickCaption(it))
                    }
                )
            },
            subCaption = {
                DSWantedOptionSwitchCell(
                    text = "subCaption : ${viewState.subCaption}",
                    checkState = viewState.subCaption,
                    onCheckChanged = {
                        onViewEvent(DSWantedCardDemoViewEvent.OnClickSubCaption(it))
                    }
                )
            },
            extraCaption = {
                DSWantedOptionSwitchCell(
                    text = "extraCaption : ${viewState.extraCaption}",
                    checkState = viewState.extraCaption,
                    onCheckChanged = {
                        onViewEvent(DSWantedCardDemoViewEvent.OnClickExtraCaption(it))
                    }
                )
            },
            isLoading = {
                DSWantedOptionSwitchCell(
                    text = "isLoading : ${viewState.isLoading}",
                    checkState = viewState.isLoading,
                    onCheckChanged = {
                        onViewEvent(DSWantedCardDemoViewEvent.OnClickLoading(it))
                    }
                )
            },
            overlayToggleIcon = {
                DSWantedOptionSwitchCell(
                    text = "overlayToggleIcon : ${viewState.overlayToggleIcon}",
                    checkState = viewState.overlayToggleIcon,
                    onCheckChanged = {
                        onViewEvent(DSWantedCardDemoViewEvent.OnClickOverlayToggleIcon(it))
                    }
                )
            },
            topContent = {
                DSWantedOptionSwitchCell(
                    text = "topContent : ${viewState.topContent}",
                    checkState = viewState.topContent,
                    onCheckChanged = {
                        onViewEvent(DSWantedCardDemoViewEvent.OnClickTopContent(it))
                    }
                )
            },
            bottomContent = {
                DSWantedOptionSwitchCell(
                    text = "bottomContent : ${viewState.bottomContent}",
                    checkState = viewState.bottomContent,
                    onCheckChanged = {
                        onViewEvent(DSWantedCardDemoViewEvent.OnClickBottomContent(it))
                    }
                )
            }
        )
    }
}

@Composable
private fun DSWantedCardDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    overlayCaption: @Composable () -> Unit,
    title: @Composable () -> Unit,
    caption: @Composable () -> Unit,
    subCaption: @Composable () -> Unit,
    extraCaption: @Composable () -> Unit,
    isLoading: @Composable () -> Unit,
    overlayToggleIcon: @Composable () -> Unit,
    topContent: @Composable () -> Unit,
    bottomContent: @Composable () -> Unit
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = "Preview",
            style = WantedTextStyle(
                colorRes = com.wanted.android.designsystem.R.color.label_strong,
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
                    colorRes = com.wanted.android.designsystem.R.color.label_strong,
                    style = DesignSystemTheme.typography.heading2Bold
                )
            )

            overlayCaption()
            title()
            caption()
            subCaption()
            extraCaption()
            isLoading()
            overlayToggleIcon()
            topContent()
            bottomContent()
        }
    }
}

@Composable
private fun DSWantedSampleAll(
    viewState: DSWantedCardDemoViewState,
    onClickCodeCopy: () -> Unit = {}
) {
    val map = viewState.toMap()
    val list = getStateList(
        map = map,
        includeKeyList = listOf(
            "title", "overlayCaption", "caption",
            "subCaption", "extraCaption", "overlayToggleIcon",
            "bottomContent", "isLoading"
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        items(list) { values ->
            WantedCard(
                modifier = Modifier,
                title = if (values["title"] as Boolean) "title" else "",
                overlayCaption = if (values["overlayCaption"] as Boolean) "overlayCaption" else "",
                caption = if (values["caption"] as Boolean) "caption" else "",
                subCaption = if (values["subCaption"] as Boolean) "subCaption" else "",
                extraCaption = if (values["extraCaption"] as Boolean) "extraCaption" else "",
                thumbnail = {},
                overlayToggleIcon = (values["overlayToggleIcon"] as Boolean).isNullOrBlock {
                    WantedTouchArea(
                        modifier = Modifier,
                        verticalPadding = 6.dp,
                        horizontalPadding = 6.dp,
                        shape = CircleShape,
                        content = {
                            Icon(
                                modifier = Modifier.clip(CircleShape),
                                painter = painterResource(R.drawable.icon_normal_bookmark_fill),
                                tint = MaterialTheme.colorScheme.primary,
                                contentDescription = ""
                            )
                        },
                        onClick = {}
                    )
                },
                bottomContent = (values["bottomContent"] as Boolean).isNullOrBlock {
                    WantedContentBadge(
                        modifier = Modifier,
                        text = "태그",
                        size = ContentBadgeSize.XSmall,
                        type = ContentBadgeType.Solid,
                        color = ContentBadgeColor.Accent
                    )
                },
                isLoading = values["isLoading"] as Boolean,
                onClick = {
                    onClickCodeCopy()
                }
            )
        }
    }
}


@DevicePreviews
@Composable
private fun DSWantedCardDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedCardDemoScreenContent(
            viewState = DSWantedCardDemoViewState(),
            onViewEvent = { }
        )
    }
}