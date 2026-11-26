package com.wanted.android.montage.sample.input.framedstyle

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
import com.wanted.android.montage.sample.DSWantedCheckerBackgroundOptionCell
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.R
import com.wanted.android.montage.sample.input.framedstyle.DSWantedFramedStyleDemoScreenContract.DSWantedFramedStyleDemoEvent
import com.wanted.android.montage.sample.input.framedstyle.DSWantedFramedStyleDemoScreenContract.DSWantedFramedStyleDemoSideEffect
import com.wanted.android.montage.sample.input.framedstyle.DSWantedFramedStyleDemoScreenContract.DSWantedFramedStyleDemoViewEvent
import com.wanted.android.montage.sample.input.framedstyle.DSWantedFramedStyleDemoScreenContract.DSWantedFramedStyleDemoViewState
import com.wanted.android.montage.sample.util.demoCheckerBackground
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.base.WantedDropShadowDefaults.WantedShadowStyle
import com.wanted.android.wanted.design.input.framedstyle.WantedFramedStyleStatus
import com.wanted.android.wanted.design.input.framedstyle.framedStyle
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.navigations.topbar.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopup
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
fun DSWantedFramedStyleDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedFramedStyleDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedFramedStyleDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedFramedStyleDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            is DSWantedFramedStyleDemoViewEvent.OnClickBack -> onClickBack()
            is DSWantedFramedStyleDemoViewEvent.OnChangeEnable -> {
                viewModel.setEvent(DSWantedFramedStyleDemoEvent.SetEnabled(viewEvent.enabled))
            }

            is DSWantedFramedStyleDemoViewEvent.OnChangeSelected -> {
                viewModel.setEvent(DSWantedFramedStyleDemoEvent.SetSelected(viewEvent.selected))
            }

            is DSWantedFramedStyleDemoViewEvent.OnChangeShadow -> {
                viewModel.setEvent(DSWantedFramedStyleDemoEvent.SetShadow(viewEvent.shadow))
            }

            is DSWantedFramedStyleDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedFramedStyleDemoEvent.CopyCode)
            }

            is DSWantedFramedStyleDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedFramedStyleDemoEvent.ShowCode(true))
            }

            is DSWantedFramedStyleDemoViewEvent.OnSelectedStatus -> {
                viewModel.setEvent(DSWantedFramedStyleDemoEvent.SetStatus(viewEvent.status))
            }
        }
    }

    if (viewState.isShowCode) {
        WantedPopup(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedFramedStyleDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedFramedStyleDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedFramedStyleDemoEvent.ShowCode(false))
            },
            content = {
                Text(text = viewState.code)
            }
        )
    }
}

@Composable
private fun DSWantedFramedStyleDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedFramedStyleDemoViewState,
    onViewEvent: (DSWantedFramedStyleDemoViewEvent) -> Unit
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedFramedStyle") {
                onViewEvent(DSWantedFramedStyleDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedFramedStyleDemoViewEvent.OnClickShowCode)
                        }
                    )
                }
            )

        }
    ) { innerPadding ->

        DSWantedFramedStyleDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        Modifier
                            .size(50.dp)
                            .framedStyle(
                                selected = viewState.selected,
                                status = viewState.status,
                                enabled = viewState.enabled,
                                shadow = if (viewState.shadow) WantedShadowStyle.XSmall() else null
                            )
                    )

                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedFramedStyleDemoViewEvent.OnClickCopyCode)
                        }
                    )
                }
            },
            status = {
                WantedSelect(
                    value = "status : ${viewState.status.name}",
                    selectedValue = viewState.status.name,
                    selectValueList = WantedFramedStyleStatus.entries.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedFramedStyleDemoViewEvent.OnSelectedStatus(WantedFramedStyleStatus.valueOf(it))
                        )
                    },
                )
            },
            enabled = {
                DSWantedOptionSwitchCell(
                    text = "enabled : ${viewState.enabled}",
                    checkState = viewState.enabled,
                    onCheckChanged = {
                        onViewEvent(DSWantedFramedStyleDemoViewEvent.OnChangeEnable(it))
                    }
                )
            },
            selected = {
                DSWantedOptionSwitchCell(
                    text = "selected : ${viewState.selected}",
                    checkState = viewState.selected,
                    onCheckChanged = {
                        onViewEvent(DSWantedFramedStyleDemoViewEvent.OnChangeSelected(it))
                    }
                )
            },
            shadow = {
                DSWantedOptionSwitchCell(
                    text = "shadow : ${viewState.shadow}",
                    checkState = viewState.shadow,
                    onCheckChanged = {
                        onViewEvent(DSWantedFramedStyleDemoViewEvent.OnChangeShadow(it))
                    }
                )
            },
        )
    }
}


@Composable
private fun DSWantedFramedStyleDemoScreenLayout(
    modifier: Modifier,
    preview: @Composable () -> Unit,
    status: @Composable () -> Unit,
    selected: @Composable () -> Unit,
    enabled: @Composable () -> Unit,
    shadow: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = "Preview",
            style = WantedTextStyle(
                colorRes = com.wanted.android.montage.sample.R.color.foreground_neutral_strong,
                style = DesignSystemTheme.typography.heading2Bold
            )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .demoCheckerBackground()
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
                    colorRes = com.wanted.android.montage.sample.R.color.foreground_neutral_strong,
                    style = DesignSystemTheme.typography.heading2Bold
                )
            )

            DSWantedCheckerBackgroundOptionCell()

            selected()

            status()

            enabled()

            shadow()
        }
    }
}

@DevicePreviews
@Composable
private fun DSWantedFramedStyleDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedFramedStyleDemoScreenContent(
            viewState = DSWantedFramedStyleDemoViewState(),
            onViewEvent = { }
        )
    }
}