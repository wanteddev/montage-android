package com.wanted.android.montage.sample.presentation.tooltip

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.wanted.android.montage.sample.presentation.tooltip.DSWantedTooltipDemoScreenContract.DSWantedTooltipDemoEvent.*
import com.wanted.android.montage.sample.presentation.tooltip.DSWantedTooltipDemoScreenContract.DSWantedTooltipDemoViewEvent
import com.wanted.android.montage.sample.presentation.tooltip.DSWantedTooltipDemoScreenContract.DSWantedTooltipDemoViewState
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarIconButton
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.presentation.tooltip.WantedTooltip
import com.wanted.android.wanted.design.presentation.tooltip.WantedTooltipAlign
import com.wanted.android.wanted.design.presentation.tooltip.WantedTooltipSize
import com.wanted.android.wanted.design.presentation.tooltip.WantedTooltipState
import com.wanted.android.wanted.design.presentation.tooltip.rememberTooltipState
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedTooltipDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedTooltipDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val tooltipState = rememberTooltipState()
    val topBarTooltipState = rememberTooltipState()

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedTooltipDemoScreenContract.DSWantedTooltipDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedTooltipDemoScreenContent(
        modifier = modifier,
        viewState = viewState,
        tooltipState = tooltipState,
        topBarTooltipState = topBarTooltipState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedTooltipDemoViewEvent.OnClickBack -> onClickBack()
            is DSWantedTooltipDemoViewEvent.OnChangeAlways -> {
                viewModel.setEvent(OnChangeAlways(viewEvent.always))
            }

            is DSWantedTooltipDemoViewEvent.OnChangeSize -> {
                viewModel.setEvent(OnChangeSize(viewEvent.size))
            }

            is DSWantedTooltipDemoViewEvent.OnChangeAlign -> {
                viewModel.setEvent(OnChangeAlign(viewEvent.align))
            }

            DSWantedTooltipDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(CopyCode)
            }

            DSWantedTooltipDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(ShowCode(true))
            }

            is DSWantedTooltipDemoViewEvent.OnChangePositionTop -> {
                viewModel.setEvent(OnChangePositionTop(viewEvent.positionTop))
            }

            DSWantedTooltipDemoViewEvent.OnClickBottomTooltip -> {
                if (tooltipState.isVisible) {
                    tooltipState.dismiss()
                } else {
                    tooltipState.show()
                }
            }

            DSWantedTooltipDemoViewEvent.OnClickTopBarTooltip -> {
                if (topBarTooltipState.isVisible) {
                    topBarTooltipState.dismiss()
                } else {
                    topBarTooltipState.show()
                }
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(CopyCode)
                viewModel.setEvent(ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(ShowCode(false))
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
private fun DSWantedTooltipDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedTooltipDemoViewState,
    tooltipState: WantedTooltipState = rememberTooltipState(),
    topBarTooltipState: WantedTooltipState = rememberTooltipState(),
    onViewEvent: (DSWantedTooltipDemoViewEvent) -> Unit
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(
                title = "WantedTooltip",
                actions = {
                    WantedTooltip(
                        modifier = Modifier.wrapContentSize(),
                        tooltipState = topBarTooltipState,
                        text = "adfasfe"
                    ) {
                        WantedTopAppBarIconButton(
                            painter = painterResource(R.drawable.icon_normal_share),
                            onClick = {
                                onViewEvent(DSWantedTooltipDemoViewEvent.OnClickTopBarTooltip)
                            }
                        )
                    }
                }
            ) {
                onViewEvent(DSWantedTooltipDemoViewEvent.OnClickBack)
            }
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
                            onViewEvent(DSWantedTooltipDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                neutral = {
                    WantedTooltip(
                        modifier = Modifier.wrapContentSize(),
                        tooltipState = tooltipState,
                        size = viewState.size,
                        align = viewState.align,
                        always = viewState.always,
                        positionTop = viewState.positionTop,
                        text = "아주긴 텍스트로 툴팁을 띄웠을 때 이렇게 됩니다.",
                        content = {
                            WantedButton(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Bottom Tooltip",
                                size = ButtonSize.SMALL,
                                onClick = {
                                    onViewEvent(DSWantedTooltipDemoViewEvent.OnClickBottomTooltip)
                                }
                            )
                        }
                    )


                },
                negative = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        size = ButtonSize.SMALL,
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedTooltipDemoViewEvent.OnClickCopyCode)
                        }
                    )
                }
            )
        }
    ) { innerPadding ->
        DSWantedTooltipDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                PreviewContents(
                    size = viewState.size,
                    align = viewState.align,
                    always = viewState.always,
                    positionTop = viewState.positionTop
                )
            },
            size = {
                WantedSelect(
                    value = "size : ${viewState.size.name}",
                    selectedValue = viewState.size.name,
                    selectValueList = WantedTooltipSize.entries.map { it.name },
                    onSelect = { size ->
                        onViewEvent(
                            DSWantedTooltipDemoViewEvent.OnChangeSize(
                                WantedTooltipSize.valueOf(size)
                            )
                        )
                    }
                )
            },
            always = {
                DSWantedOptionSwitchCell(
                    text = "always : ${viewState.always}",
                    checkState = viewState.always,
                    onCheckChanged = {
                        onViewEvent(DSWantedTooltipDemoViewEvent.OnChangeAlways(it))
                    }
                )
            },
            align = {
                WantedSelect(
                    value = "align : ${viewState.align.name}",
                    selectedValue = viewState.align.name,
                    selectValueList = WantedTooltipAlign.entries.map { it.name },
                    onSelect = { align ->
                        onViewEvent(
                            DSWantedTooltipDemoViewEvent.OnChangeAlign(
                                WantedTooltipAlign.valueOf(align)
                            )
                        )
                    }
                )
            },
            positionTop = {
                DSWantedOptionSwitchCell(
                    text = "positionTop : ${viewState.positionTop}",
                    checkState = viewState.positionTop,
                    onCheckChanged = {
                        onViewEvent(DSWantedTooltipDemoViewEvent.OnChangePositionTop(it))
                    }
                )
            }
        )
    }
}

@Composable
private fun PreviewContents(
    modifier: Modifier = Modifier,
    tooltipState: WantedTooltipState = rememberTooltipState(),
    tooltipState1: WantedTooltipState = rememberTooltipState(),
    tooltipState2: WantedTooltipState = rememberTooltipState(),
    tooltipState3: WantedTooltipState = rememberTooltipState(),
    tooltipState4: WantedTooltipState = rememberTooltipState(),
    tooltipState5: WantedTooltipState = rememberTooltipState(),
    size: WantedTooltipSize,
    align: WantedTooltipAlign,
    always: Boolean,
    positionTop: Boolean
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            PreviewContentsRow(
                modifier = Modifier.fillMaxWidth(),
                tooltipState = tooltipState,
                tooltipState1 = tooltipState1,
                index = 0,
                size = size,
                always = always,
                align = align,
                positionTop = positionTop
            )
        }

        item {
            Spacer(Modifier.size(300.dp))
        }

        item {
            PreviewContentsRow(
                modifier = Modifier.fillMaxWidth(),
                tooltipState = tooltipState2,
                tooltipState1 = tooltipState3,
                index = 2,
                size = size,
                always = always,
                align = align,
                positionTop = positionTop
            )
        }

        item {
            Spacer(Modifier.size(300.dp))
        }

        item {
            PreviewContentsRow(
                modifier = Modifier.fillMaxWidth(),
                tooltipState = tooltipState4,
                tooltipState1 = tooltipState5,
                index = 4,
                size = size,
                always = always,
                align = align,
                positionTop = positionTop
            )
        }
    }
}

@Composable
private fun PreviewContentsRow(
    modifier: Modifier = Modifier,
    index: Int,
    size: WantedTooltipSize,
    align: WantedTooltipAlign,
    always: Boolean,
    positionTop: Boolean,
    tooltipState: WantedTooltipState = rememberTooltipState(),
    tooltipState1: WantedTooltipState = rememberTooltipState()
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        WantedTooltip(
            modifier = Modifier.wrapContentSize(),
            tooltipState = tooltipState,
            size = size,
            align = align,
            always = always,
            positionTop = positionTop,
            text = "아주긴 텍스트로 툴팁을 띄웠을 때 이렇게 됩니다.",
            content = {
                WantedButton(
                    modifier = Modifier,
                    text = "text $index",
                    size = ButtonSize.SMALL,
                    onClick = {
                        if (tooltipState.isVisible) {
                            tooltipState.dismiss()
                        } else {
                            tooltipState.show()
                        }
                    }
                )
            }
        )

        WantedTooltip(
            modifier = Modifier.wrapContentSize(),
            tooltipState = tooltipState1,
            size = size,
            align = align,
            always = always,
            positionTop = positionTop,
            text = "아주긴 텍스트로 툴팁을 띄웠을 때 이렇게 됩니다.",
            content = {
                WantedButton(
                    modifier = Modifier,
                    size = ButtonSize.SMALL,
                    text = "text ${index + 1}",
                    onClick = {
                        if (tooltipState1.isVisible) {
                            tooltipState1.dismiss()
                        } else {
                            tooltipState1.show()
                        }
                    }
                )
            }
        )
    }
}


@Composable
private fun DSWantedTooltipDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    size: @Composable () -> Unit,
    always: @Composable () -> Unit,
    align: @Composable () -> Unit,
    positionTop: @Composable () -> Unit
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
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

        Spacer(Modifier.size(10.dp))

        Column(
            modifier = Modifier
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

            size()
            always()
            align()
            positionTop()
        }
    }
}


@DevicePreviews
@Composable
private fun DSWantedTooltipDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedTooltipDemoScreenContent(
            viewState = DSWantedTooltipDemoViewState(),
            onViewEvent = { }
        )
    }
}
