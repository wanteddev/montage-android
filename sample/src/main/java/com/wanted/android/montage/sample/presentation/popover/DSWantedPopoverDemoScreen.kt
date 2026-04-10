package com.wanted.android.montage.sample.presentation.popover

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.designsystem.R
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.presentation.popover.DSWantedPopoverDemoScreenContract.DSWantedPopoverDemoEvent
import com.wanted.android.montage.sample.presentation.popover.DSWantedPopoverDemoScreenContract.DSWantedPopoverDemoSideEffect
import com.wanted.android.montage.sample.presentation.popover.DSWantedPopoverDemoScreenContract.DSWantedPopoverDemoViewEvent
import com.wanted.android.montage.sample.presentation.popover.DSWantedPopoverDemoScreenContract.DSWantedPopoverDemoViewState
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.input.textinput.textfield.WantedTextField
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.presentation.popover.WantedPopover
import com.wanted.android.wanted.design.presentation.popover.WantedPopoverAlign
import com.wanted.android.wanted.design.presentation.popover.WantedSimplePopoverState
import com.wanted.android.wanted.design.presentation.popover.rememberPopoverState
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedPopoverDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedPopoverDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val popoverState = rememberPopoverState()

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedPopoverDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedPopoverDemoScreenContent(
        modifier = modifier,
        viewState = viewState,
        popoverState = popoverState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedPopoverDemoViewEvent.OnClickBack -> onClickBack()
            is DSWantedPopoverDemoViewEvent.OnChangeAlways -> {
                viewModel.setEvent(DSWantedPopoverDemoEvent.OnChangeAlways(viewEvent.always))
            }

            is DSWantedPopoverDemoViewEvent.OnChangeAlign -> {
                viewModel.setEvent(DSWantedPopoverDemoEvent.OnChangeAlign(viewEvent.align))
            }

            is DSWantedPopoverDemoViewEvent.OnChangeHeading -> {
                viewModel.setEvent(DSWantedPopoverDemoEvent.OnChangeHeading(viewEvent.heading))
            }

            is DSWantedPopoverDemoViewEvent.OnChangeHeadingText -> {
                viewModel.setEvent(DSWantedPopoverDemoEvent.OnChangeHeadingText(viewEvent.headingText))
            }

            is DSWantedPopoverDemoViewEvent.OnChangeCloseButton -> {
                viewModel.setEvent(DSWantedPopoverDemoEvent.OnChangeCloseButton(viewEvent.closeButton))
            }

            is DSWantedPopoverDemoViewEvent.OnChangePositionTop -> {
                viewModel.setEvent(DSWantedPopoverDemoEvent.OnChangePositionTop(viewEvent.positionTop))
            }

            is DSWantedPopoverDemoViewEvent.OnChangeAction -> {
                viewModel.setEvent(DSWantedPopoverDemoEvent.OnChangeAction(viewEvent.action))
            }

            DSWantedPopoverDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedPopoverDemoEvent.CopyCode)
            }

            DSWantedPopoverDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedPopoverDemoEvent.ShowCode(true))
            }

            DSWantedPopoverDemoViewEvent.OnClickBottomTooltip -> {
                if (popoverState.isVisible) {
                    popoverState.dismiss()
                } else {
                    popoverState.show()
                }
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedPopoverDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedPopoverDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedPopoverDemoEvent.ShowCode(false))
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
private fun DSWantedPopoverDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedPopoverDemoViewState,
    popoverState: WantedSimplePopoverState,
    onViewEvent: (DSWantedPopoverDemoViewEvent) -> Unit
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedPopover") {
                onViewEvent(DSWantedPopoverDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedPopoverDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                neutral = {
                    WantedPopover(
                        modifier = Modifier.wrapContentSize(),
                        state = popoverState,
                        windowInsets = WindowInsets.systemBars,
                        align = viewState.align,
                        always = viewState.always,
                        heading = if (viewState.heading) viewState.headingText else "",
                        closeButton = viewState.closeButton,
                        positionTop = viewState.positionTop,
                        text = "Popover",
                        content = {
                            WantedButton(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Bottom Tooltip",
                                size = ButtonSize.SMALL,
                                onClick = {
                                    onViewEvent(DSWantedPopoverDemoViewEvent.OnClickBottomTooltip)
                                }
                            )
                        },
                        action = if (viewState.action) {
                            {
                                WantedButton(
                                    variant = ButtonVariant.TEXT,
                                    type = ButtonType.ASSISTIVE,
                                    size = ButtonSize.SMALL,
                                    text = "취소",
                                )

                                WantedButton(
                                    variant = ButtonVariant.TEXT,
                                    size = ButtonSize.SMALL,
                                    text = "확인",
                                )
                            }
                        } else {
                            null
                        }
                    )
                },
                negative = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        size = ButtonSize.SMALL,
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedPopoverDemoViewEvent.OnClickCopyCode)
                        }
                    )
                }
            )
        }
    ) { innerPadding ->
        DSWantedPopoverDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                PreviewContents(
                    modifier = Modifier,
                    state = rememberPopoverState(),
                    state1 = rememberPopoverState(),
                    state2 = rememberPopoverState(),
                    state3 = rememberPopoverState(),
                    state4 = rememberPopoverState(),
                    state5 = rememberPopoverState(),
                    state6 = rememberPopoverState(),
                    state7 = rememberPopoverState(),
                    state8 = rememberPopoverState(),
                    align = viewState.align,
                    always = viewState.always,
                    action = viewState.action,
                    heading = if (viewState.heading) viewState.headingText else "",
                    closeButton = viewState.closeButton,
                    positionTop = viewState.positionTop
                )
            },
            always = {
                DSWantedOptionSwitchCell(
                    text = "always : ${viewState.always}",
                    checkState = viewState.always,
                    onCheckChanged = {
                        onViewEvent(DSWantedPopoverDemoViewEvent.OnChangeAlways(it))
                    }
                )
            },
            align = {
                WantedSelect(
                    value = "align : ${viewState.align.name}",
                    selectedValue = viewState.align.name,
                    selectValueList = WantedPopoverAlign.values().map { it.name },
                    onSelect = { align ->
                        onViewEvent(
                            DSWantedPopoverDemoViewEvent.OnChangeAlign(
                                WantedPopoverAlign.valueOf(align)
                            )
                        )
                    }
                )
            },
            heading = {
                Column {
                    DSWantedOptionSwitchCell(
                        text = "heading : ${viewState.heading}",
                        checkState = viewState.heading,
                        onCheckChanged = {
                            onViewEvent(DSWantedPopoverDemoViewEvent.OnChangeHeading(it))
                        }
                    )
                    if (viewState.heading) {
                        WantedTextField(
                            modifier = Modifier.padding(top = 8.dp),
                            text = viewState.headingText,
                            onValueChange = { headingText ->
                                onViewEvent(
                                    DSWantedPopoverDemoViewEvent.OnChangeHeadingText(
                                        headingText
                                    )
                                )
                            },
                            placeholder = "Popover heading"
                        )
                    }
                }
            },
            closeButton = {
                DSWantedOptionSwitchCell(
                    text = "closeButton : ${viewState.closeButton}",
                    checkState = viewState.closeButton,
                    onCheckChanged = {
                        onViewEvent(DSWantedPopoverDemoViewEvent.OnChangeCloseButton(it))
                    }
                )
            },
            positionTop = {
                DSWantedOptionSwitchCell(
                    text = "positionTop : ${viewState.positionTop}",
                    checkState = viewState.positionTop,
                    onCheckChanged = {
                        onViewEvent(DSWantedPopoverDemoViewEvent.OnChangePositionTop(it))
                    }
                )
            },
            action = {
                DSWantedOptionSwitchCell(
                    text = "action : ${viewState.action}",
                    checkState = viewState.action,
                    onCheckChanged = {
                        onViewEvent(DSWantedPopoverDemoViewEvent.OnChangeAction(it))
                    }
                )
            }
        )
    }
}

@Composable
private fun DSWantedPopoverDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    always: @Composable () -> Unit,
    align: @Composable () -> Unit,
    heading: @Composable () -> Unit,
    closeButton: @Composable () -> Unit,
    positionTop: @Composable () -> Unit,
    action: @Composable () -> Unit
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
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
                .weight(1.5f)
                .verticalScroll(rememberScrollState())
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

            always()
            align()
            heading()
            closeButton()
            positionTop()
            action()
        }
    }
}

@Composable
private fun PreviewContents(
    modifier: Modifier = Modifier,
    state: WantedSimplePopoverState = rememberPopoverState(),
    state1: WantedSimplePopoverState = rememberPopoverState(),
    state2: WantedSimplePopoverState = rememberPopoverState(),
    state3: WantedSimplePopoverState = rememberPopoverState(),
    state4: WantedSimplePopoverState = rememberPopoverState(),
    state5: WantedSimplePopoverState = rememberPopoverState(),
    state6: WantedSimplePopoverState = rememberPopoverState(),
    state7: WantedSimplePopoverState = rememberPopoverState(),
    state8: WantedSimplePopoverState = rememberPopoverState(),
    align: WantedPopoverAlign,
    always: Boolean,
    heading: String = "",
    action: Boolean = false,
    closeButton: Boolean = false,
    positionTop: Boolean = false
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            PreviewContentsRow(
                modifier = Modifier.fillMaxWidth(),
                state = state,
                state1 = state1,
                state2 = state2,
                index = 0,
                always = always,
                action = action,
                align = align,
                heading = heading,
                closeButton = closeButton,
                positionTop = positionTop
            )
        }

        item {
            Spacer(Modifier.size(300.dp))
        }

        item {
            PreviewContentsRow(
                modifier = Modifier.fillMaxWidth(),
                state = state3,
                state1 = state4,
                state2 = state5,
                index = 2,
                always = always,
                action = action,
                align = align,
                heading = heading,
                closeButton = closeButton,
                positionTop = positionTop
            )
        }

        item {
            Spacer(Modifier.size(300.dp))
        }

        item {
            PreviewContentsRow(
                modifier = Modifier.fillMaxWidth(),
                state = state6,
                state1 = state7,
                state2 = state8,
                index = 4,
                always = always,
                action = action,
                align = align,
                heading = heading,
                closeButton = closeButton,
                positionTop = positionTop
            )
        }
    }
}

@Composable
private fun PreviewContentsRow(
    modifier: Modifier = Modifier,
    index: Int,
    align: WantedPopoverAlign,
    always: Boolean,
    action: Boolean,
    state: WantedSimplePopoverState,
    state1: WantedSimplePopoverState,
    state2: WantedSimplePopoverState,
    heading: String = "",
    closeButton: Boolean = false,
    positionTop: Boolean = false
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        WantedPopover(
            modifier = Modifier.wrapContentSize(),
            state = state,
            align = align,
            always = always,
            heading = heading,
            closeButton = closeButton,
            positionTop = positionTop,
            text = "Popover",
            action = if (action) {
                {
                    WantedButton(
                        variant = ButtonVariant.TEXT,
                        type = ButtonType.ASSISTIVE,
                        size = ButtonSize.SMALL,
                        text = "보조행동",
                    )

                    WantedButton(
                        variant = ButtonVariant.TEXT,
                        size = ButtonSize.SMALL,
                        text = "확인",
                    )
                }
            } else {
                null
            },
            content = {
                WantedButton(
                    modifier = Modifier,
                    text = "text $index",
                    size = ButtonSize.SMALL,
                    onClick = {
                        if (state.isVisible) {
                            state.dismiss()
                        } else {
                            state.show()
                        }
                    }
                )
            }
        )

        WantedPopover(
            modifier = Modifier.wrapContentSize(),
            state = state1,
            align = align,
            always = always,
            heading = heading,
            closeButton = closeButton,
            positionTop = positionTop,
            text = "Popover",
            action = if (action) {
                {
                    WantedButton(
                        variant = ButtonVariant.TEXT,
                        type = ButtonType.ASSISTIVE,
                        size = ButtonSize.SMALL,
                        text = "취소",
                    )

                    WantedButton(
                        variant = ButtonVariant.TEXT,
                        size = ButtonSize.SMALL,
                        text = "확인",
                    )
                }
            } else {
                null
            },
            content = {
                WantedButton(
                    modifier = Modifier,
                    text = "text ${index + 1}",
                    size = ButtonSize.SMALL,
                    onClick = {
                        if (state1.isVisible) {
                            state1.dismiss()
                        } else {
                            state1.show()
                        }
                    }
                )
            }
        )

        WantedPopover(
            modifier = Modifier.wrapContentSize(),
            state = state2,
            align = align,
            always = always,
            heading = heading,
            closeButton = closeButton,
            positionTop = positionTop,
            text = "아주긴 텍스트로 툴팁을 띄웠을 때 이렇게 됩니다.",
            action = if (action) {
                {
                    WantedButton(
                        variant = ButtonVariant.TEXT,
                        type = ButtonType.ASSISTIVE,
                        size = ButtonSize.SMALL,
                        text = "취소",
                    )

                    WantedButton(
                        variant = ButtonVariant.TEXT,
                        size = ButtonSize.SMALL,
                        text = "확인",
                    )
                }
            } else {
                null
            },
            content = {
                WantedButton(
                    modifier = Modifier,
                    size = ButtonSize.SMALL,
                    text = "text ${index + 2}",
                    onClick = {
                        if (state2.isVisible) {
                            state2.dismiss()
                        } else {
                            state2.show()
                        }
                    }
                )
            }
        )
    }
}
