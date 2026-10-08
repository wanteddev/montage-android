package com.wanted.android.montage.sample.feedback.pushbadge

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.montage.sample.R
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.feedback.pushbadge.DSWantedPushBadgeDemoScreenContract.DSWantedPushBadgeDemoEvent
import com.wanted.android.montage.sample.feedback.pushbadge.DSWantedPushBadgeDemoScreenContract.DSWantedPushBadgeDemoSideEffect
import com.wanted.android.montage.sample.feedback.pushbadge.DSWantedPushBadgeDemoScreenContract.DSWantedPushBadgeDemoViewEvent
import com.wanted.android.montage.sample.feedback.pushbadge.DSWantedPushBadgeDemoScreenContract.DSWantedPushBadgeDemoViewState
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.feedback.pushbadge.PushBadgeTypes.PushBadgePosition
import com.wanted.android.wanted.design.feedback.pushbadge.PushBadgeTypes.PushBadgeSize
import com.wanted.android.wanted.design.feedback.pushbadge.PushBadgeTypes.PushBadgeVariant
import com.wanted.android.wanted.design.feedback.pushbadge.WantedPushBadge
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.navigations.topbar.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopup
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedPushBadgeDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedPushBadgeDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedPushBadgeDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedPushBadgeDemoScreenImpl(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            is DSWantedPushBadgeDemoViewEvent.OnClickBack -> onClickBack()
            is DSWantedPushBadgeDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedPushBadgeDemoEvent.ShowCode(true))
            }

            is DSWantedPushBadgeDemoViewEvent.OnSelectVariant -> {
                viewModel.setEvent(DSWantedPushBadgeDemoEvent.SetVariant(viewEvent.variant))
            }

            is DSWantedPushBadgeDemoViewEvent.OnSelectSize -> {
                viewModel.setEvent(DSWantedPushBadgeDemoEvent.SetSize(viewEvent.size))
            }

            is DSWantedPushBadgeDemoViewEvent.OnSelectPosition -> {
                viewModel.setEvent(DSWantedPushBadgeDemoEvent.SetPosition(viewEvent.position))
            }

            is DSWantedPushBadgeDemoViewEvent.OnChangeOutlineBorder -> {
                viewModel.setEvent(DSWantedPushBadgeDemoEvent.SetOutlineBorder(viewEvent.outlineBorder))
            }

            is DSWantedPushBadgeDemoViewEvent.OnSelectOutlineBorderColor -> {
                viewModel.setEvent(DSWantedPushBadgeDemoEvent.SetOutlineBorderColor(viewEvent.outlineBorderColor))
            }

            is DSWantedPushBadgeDemoViewEvent.OnChangeInset -> {
                viewModel.setEvent(DSWantedPushBadgeDemoEvent.SetInset(viewEvent.insetEnabled))
            }

            is DSWantedPushBadgeDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedPushBadgeDemoEvent.CopyCode)
            }
        }
    }

    if (viewState.isShowCode) {
        WantedPopup(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedPushBadgeDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedPushBadgeDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedPushBadgeDemoEvent.ShowCode(false))
            },
            content = {
                Text(text = viewState.code)
            }
        )
    }
}

@Composable
private fun DSWantedPushBadgeDemoScreenImpl(
    modifier: Modifier = Modifier,
    viewState: DSWantedPushBadgeDemoViewState,
    onViewEvent: (DSWantedPushBadgeDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedPushBadge") {
                onViewEvent(DSWantedPushBadgeDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedPushBadgeDemoViewEvent.OnClickShowCode)
                        }
                    )
                }
            )
        }
    ) { innerPadding ->
        DSWantedPushBadgeDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = DesignSystemTheme.colors.surfaceNeutralSecondary,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    // 실제 사용처(WantedAvatar/IconButton)처럼 선택한 position 모서리에 배지를 정렬한 뒤,
                    // 배지의 position + offset 이 바깥으로 걸치도록 한다.
                    WantedPushBadge(
                        modifier = Modifier.align(viewState.selectedPosition.toAlignment()),
                        variant = viewState.selectedVariant,
                        size = viewState.selectedSize,
                        position = viewState.selectedPosition,
                        text = if (viewState.selectedVariant == PushBadgeVariant.MaxCount) {
                            viewState.sampleCount.toString()
                        } else {
                            viewState.sampleText
                        },
                        maxCount = viewState.sampleMaxCount,
                        outlineBorder = viewState.outlineBorder,
                        outlineBorderColor = viewState.selectedOutlineBorderColor.toColor(),
                        inset = if (viewState.insetEnabled) {
                            DpOffset(viewState.sampleInset.dp, viewState.sampleInset.dp)
                        } else {
                            DpOffset(0.dp, 0.dp)
                        }
                    )
                }
            },
            variant = {
                WantedSelect(
                    value = "Variant : ${viewState.selectedVariant.name}",
                    selectedValue = viewState.selectedVariant.name,
                    selectValueList = viewState.variantList.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedPushBadgeDemoViewEvent.OnSelectVariant(PushBadgeVariant.valueOf(it))
                        )
                    },
                )
            },
            size = {
                WantedSelect(
                    value = "Size : ${viewState.selectedSize.name}",
                    selectedValue = viewState.selectedSize.name,
                    selectValueList = viewState.sizeList.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedPushBadgeDemoViewEvent.OnSelectSize(PushBadgeSize.valueOf(it))
                        )
                    },
                )
            },
            position = {
                WantedSelect(
                    value = "Position : ${viewState.selectedPosition.name}",
                    selectedValue = viewState.selectedPosition.name,
                    selectValueList = viewState.positionList.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedPushBadgeDemoViewEvent.OnSelectPosition(PushBadgePosition.valueOf(it))
                        )
                    },
                )
            },
            outlineBorder = {
                DSWantedOptionSwitchCell(
                    text = "outlineBorder : ${viewState.outlineBorder}",
                    checkState = viewState.outlineBorder,
                    onCheckChanged = {
                        onViewEvent(DSWantedPushBadgeDemoViewEvent.OnChangeOutlineBorder(it))
                    }
                )
            },
            outlineBorderColor = {
                // outlineBorder 가 켜진 경우에만 외곽 보더 색상 선택 메뉴를 노출한다.
                if (viewState.outlineBorder) {
                    WantedSelect(
                        value = "outlineBorderColor : ${viewState.selectedOutlineBorderColor.label}",
                        selectedValue = viewState.selectedOutlineBorderColor.name,
                        selectValueList = viewState.outlineBorderColorList.map { it.name },
                        onSelect = {
                            onViewEvent(
                                DSWantedPushBadgeDemoViewEvent.OnSelectOutlineBorderColor(OutlineBorderColorOption.valueOf(it))
                            )
                        },
                    )
                }
            },
            inset = {
                DSWantedOptionSwitchCell(
                    text = "inset : ${if (viewState.insetEnabled) "(${viewState.sampleInset}, ${viewState.sampleInset})" else "(0, 0)"}",
                    checkState = viewState.insetEnabled,
                    onCheckChanged = {
                        onViewEvent(DSWantedPushBadgeDemoViewEvent.OnChangeInset(it))
                    }
                )
            }
        )
    }
}

@Composable
private fun DSWantedPushBadgeDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    variant: @Composable () -> Unit,
    size: @Composable () -> Unit,
    position: @Composable () -> Unit,
    outlineBorder: @Composable () -> Unit,
    outlineBorderColor: @Composable () -> Unit,
    inset: @Composable () -> Unit,
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

            variant()

            size()

            position()

            outlineBorder()

            outlineBorderColor()

            inset()
        }
    }
}

@Composable
private fun OutlineBorderColorOption.toColor(): Color = when (this) {
    OutlineBorderColorOption.Default -> DesignSystemTheme.colors.backgroundNeutralPrimary
    OutlineBorderColorOption.White -> DesignSystemTheme.colors.staticWhite
    OutlineBorderColorOption.Black -> DesignSystemTheme.colors.staticBlack
    OutlineBorderColorOption.Primary -> DesignSystemTheme.colors.surfaceBrandPrimary
}

private fun PushBadgePosition.toAlignment(): Alignment = when (this) {
    PushBadgePosition.TopStart -> Alignment.TopStart
    PushBadgePosition.TopCenter -> Alignment.TopCenter
    PushBadgePosition.TopEnd -> Alignment.TopEnd
    PushBadgePosition.MiddleStart -> Alignment.CenterStart
    PushBadgePosition.MiddleCenter -> Alignment.Center
    PushBadgePosition.MiddleEnd -> Alignment.CenterEnd
    PushBadgePosition.BottomStart -> Alignment.BottomStart
    PushBadgePosition.BottomCenter -> Alignment.BottomCenter
    PushBadgePosition.BottomEnd -> Alignment.BottomEnd
}

@DevicePreviews
@Composable
private fun DSWantedPushBadgeDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedPushBadgeDemoScreenImpl(
            viewState = DSWantedPushBadgeDemoViewState(),
            onViewEvent = { }
        )
    }
}
