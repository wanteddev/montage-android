package com.wanted.android.montage.sample.base.dropshadow

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.montage.sample.util.clickOnce
import com.wanted.android.montage.sample.R
import com.wanted.android.montage.sample.base.dropshadow.DSWantedDropShadowDemoScreenContract.DSWantedDropShadowDemoEvent.CopyCode
import com.wanted.android.montage.sample.base.dropshadow.DSWantedDropShadowDemoScreenContract.DSWantedDropShadowDemoEvent.SetContentBackground
import com.wanted.android.montage.sample.base.dropshadow.DSWantedDropShadowDemoScreenContract.DSWantedDropShadowDemoEvent.SetPreviewBackground
import com.wanted.android.montage.sample.base.dropshadow.DSWantedDropShadowDemoScreenContract.DSWantedDropShadowDemoEvent.SetStyle
import com.wanted.android.montage.sample.base.dropshadow.DSWantedDropShadowDemoScreenContract.DSWantedDropShadowDemoEvent.ShowCode
import com.wanted.android.montage.sample.base.dropshadow.DSWantedDropShadowDemoScreenContract.DSWantedDropShadowDemoSideEffect
import com.wanted.android.montage.sample.base.dropshadow.DSWantedDropShadowDemoScreenContract.DSWantedDropShadowDemoViewEvent
import com.wanted.android.montage.sample.base.dropshadow.DSWantedDropShadowDemoScreenContract.DSWantedDropShadowDemoViewState
import com.wanted.android.montage.sample.base.dropshadow.DSWantedDropShadowDemoScreenContract.ShadowPreviewBackground
import com.wanted.android.montage.sample.base.dropshadow.DSWantedDropShadowDemoScreenContract.ShadowSampleContentBackground
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.base.WantedDropShadowDefaults.WantedShadowStyle
import com.wanted.android.wanted.design.base.wantedDropShadow
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
fun DSWantedDropShadowDemoScreen(
    modifier: Modifier,
    viewModel: DSWantedDropShadowDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedDropShadowDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedDropShadowDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedDropShadowDemoViewEvent.OnClickBack -> onClickBack()
            DSWantedDropShadowDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(ShowCode(true))
            }

            is DSWantedDropShadowDemoViewEvent.OnClickContentBackground -> {
                viewModel.setEvent(
                    SetContentBackground(
                        viewEvent.background
                    )
                )
            }

            is DSWantedDropShadowDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(CopyCode)
            }

            is DSWantedDropShadowDemoViewEvent.OnClickStyle -> {
                viewModel.setEvent(SetStyle(viewEvent.style))
            }

            is DSWantedDropShadowDemoViewEvent.OnClickPreviewBackground -> {
                viewModel.setEvent(SetPreviewBackground(viewEvent.background))
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
                Text(text = viewState.code)
            }
        )
    }
}

@Composable
private fun DSWantedDropShadowDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedDropShadowDemoViewState,
    onViewEvent: (DSWantedDropShadowDemoViewEvent) -> Unit
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedDropShadow") {
                onViewEvent(DSWantedDropShadowDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedDropShadowDemoViewEvent.OnClickShowCode)
                        }
                    )
                }
            )

        }
    ) { innerPadding ->

        DSWantedDropShadowDemoScreenLayout(
            modifier = modifier.padding(innerPadding),
            preview = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = if (viewState.previewBackground == ShadowPreviewBackground.ContentBackground) {
                                viewState.contentBackground.color
                            } else {
                                viewState.previewBackground.color
                            }
                        )
                        .padding(vertical = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        Modifier
                            .size(100.dp)
                            .wantedDropShadow(
                                style = viewState.style.withBackgroundColor(viewState.contentBackground.color),
                            )
                            .clickOnce {
                                onViewEvent(DSWantedDropShadowDemoViewEvent.OnClickCopyCode)
                            }
                    )
                }
            },
            style = {
                WantedSelect(
                    value = "Style : ${viewState.style.javaClass.simpleName}",
                    selectedValue = viewState.style.javaClass.simpleName,
                    selectValueList = viewState.styleSizeList.map { it.javaClass.simpleName },
                    onSelect = {
                        viewState.styleSizeList.firstOrNull { style ->
                            style.javaClass.simpleName == it
                        }?.let {
                            onViewEvent(DSWantedDropShadowDemoViewEvent.OnClickStyle(it))
                        }
                    },
                )
            },
            background = {
                WantedSelect(
                    value = "Content Background : ${viewState.contentBackground.name}",
                    selectedValue = viewState.contentBackground.name,
                    selectValueList = ShadowSampleContentBackground.entries.map { it.name },
                    onSelect = { name ->
                        val background =
                            ShadowSampleContentBackground.entries.find { it.name == name }
                        background?.let {
                            onViewEvent(DSWantedDropShadowDemoViewEvent.OnClickContentBackground(it))
                        }
                    },
                )
            },
            previewBackground = {
                WantedSelect(
                    value = "Preview Background : ${viewState.previewBackground.name}",
                    selectedValue = viewState.previewBackground.name,
                    selectValueList = ShadowPreviewBackground.entries.map { it.name },
                    onSelect = { name ->
                        val background = ShadowPreviewBackground.entries.find { it.name == name }
                        background?.let {
                            onViewEvent(DSWantedDropShadowDemoViewEvent.OnClickPreviewBackground(it))
                        }
                    },
                )
            }
        )
    }
}


@Composable
private fun DSWantedDropShadowDemoScreenLayout(
    modifier: Modifier,
    preview: @Composable () -> Unit,
    style: @Composable () -> Unit,
    background: @Composable () -> Unit,
    previewBackground: @Composable () -> Unit
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

        DSWantedPreviewContainer {
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

            style()
            background()
            previewBackground()
        }
    }
}

private fun WantedShadowStyle.withBackgroundColor(backgroundColor: Color): WantedShadowStyle {
    return when (this) {
        is WantedShadowStyle.Large -> WantedShadowStyle.Large(backgroundColor = backgroundColor)
        is WantedShadowStyle.Medium -> WantedShadowStyle.Medium(backgroundColor = backgroundColor)
        is WantedShadowStyle.Small -> WantedShadowStyle.Small(backgroundColor = backgroundColor)
        is WantedShadowStyle.XLarge -> WantedShadowStyle.XLarge(backgroundColor = backgroundColor)
        is WantedShadowStyle.XSmall -> WantedShadowStyle.XSmall(backgroundColor = backgroundColor)
    }
}


@DevicePreviews
@Composable
private fun DSWantedDropShadowDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedDropShadowDemoScreenContent(
            viewState = DSWantedDropShadowDemoViewState(),
            onViewEvent = { }
        )
    }
}