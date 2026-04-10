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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.designsystem.R
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
import com.wanted.android.wanted.design.feedback.pushbadge.WantedPushBadgeBorder
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
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

            is DSWantedPushBadgeDemoViewEvent.OnChangeBordered -> {
                viewModel.setEvent(DSWantedPushBadgeDemoEvent.SetBordered(viewEvent.bordered))
            }

            is DSWantedPushBadgeDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedPushBadgeDemoEvent.CopyCode)
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
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
                positive = {
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
                            color = DesignSystemTheme.colors.fillNormal,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    if (viewState.bordered) {
                        WantedPushBadgeBorder(
                            variant = viewState.selectedVariant,
                            size = viewState.selectedSize,
                            position = viewState.selectedPosition,
                            count = "1",
                            bordered = true,
                        )
                    } else {
                        WantedPushBadge(
                            variant = viewState.selectedVariant,
                            size = viewState.selectedSize,
                            position = viewState.selectedPosition,
                            count = "1",
                        )
                    }
                }
            },
            variant = {
                WantedSelect(
                    value = "Variant : ${viewState.selectedVariant.name}",
                    selectedValue = viewState.selectedVariant.name,
                    selectValueList = viewState.variantList.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedPushBadgeDemoViewEvent.OnSelectVariant(
                                PushBadgeVariant.valueOf(
                                    it
                                )
                            )
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
                            DSWantedPushBadgeDemoViewEvent.OnSelectPosition(
                                PushBadgePosition.valueOf(
                                    it
                                )
                            )
                        )
                    },
                )
            },
            bordered = {
                DSWantedOptionSwitchCell(
                    text = "bordered : ${viewState.bordered}",
                    checkState = viewState.bordered,
                    onCheckChanged = {
                        onViewEvent(DSWantedPushBadgeDemoViewEvent.OnChangeBordered(it))
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
    bordered: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
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
                    color = colorResource(com.wanted.android.designsystem.R.color.line_normal_normal),
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
                    colorRes = R.color.label_strong,
                    style = DesignSystemTheme.typography.heading2Bold
                )
            )

            variant()

            size()

            position()

            bordered()
        }
    }
}

@DevicePreviews
@Composable
private fun DSWantedPushBadgeDemoScreenPreview() {
    DesignSystemTheme {
        val viewState = DSWantedPushBadgeDemoViewState()
        DSWantedPushBadgeDemoScreenImpl(
            viewState = viewState,
            onViewEvent = { }
        )
    }
}
