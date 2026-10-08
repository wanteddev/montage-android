package com.wanted.android.montage.sample.feedback.fallback

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
import com.wanted.android.montage.sample.R
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.feedback.fallback.DSWantedFallbackViewDemoScreenContract.DSWantedFallbackViewDemoEvent
import com.wanted.android.montage.sample.feedback.fallback.DSWantedFallbackViewDemoScreenContract.DSWantedFallbackViewDemoSideEffect
import com.wanted.android.montage.sample.feedback.fallback.DSWantedFallbackViewDemoScreenContract.DSWantedFallbackViewDemoViewEvent
import com.wanted.android.montage.sample.feedback.fallback.DSWantedFallbackViewDemoScreenContract.DSWantedFallbackViewDemoViewState
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.feedback.fallback.WantedFallbackButtonVariant
import com.wanted.android.wanted.design.feedback.fallback.WantedFallbackPadding
import com.wanted.android.wanted.design.feedback.fallback.WantedFallbackView
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.navigations.topbar.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopup
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
fun DSWantedFallbackViewDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedFallbackViewDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit,
    onClickFallbackMain: (DSWantedFallbackViewDemoViewState) -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedFallbackViewDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedFallbackViewDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedFallbackViewDemoViewEvent.OnClickBack -> onClickBack()
            DSWantedFallbackViewDemoViewEvent.OnClickFallbackMain -> {
                onClickFallbackMain(viewState)
            }

            is DSWantedFallbackViewDemoViewEvent.OnChangeButtonVariant -> {
                viewModel.setEvent(
                    DSWantedFallbackViewDemoEvent.OnChangeButtonVariant(viewEvent.buttonVariant)
                )
            }

            is DSWantedFallbackViewDemoViewEvent.OnChangePadding -> {
                viewModel.setEvent(
                    DSWantedFallbackViewDemoEvent.OnChangePadding(viewEvent.padding)
                )
            }

            is DSWantedFallbackViewDemoViewEvent.OnChangeDescription -> {
                viewModel.setEvent(
                    DSWantedFallbackViewDemoEvent.OnChangeDescription(viewEvent.description)
                )
            }

            is DSWantedFallbackViewDemoViewEvent.OnChangeHeading -> {
                viewModel.setEvent(
                    DSWantedFallbackViewDemoEvent.OnChangeHeading(viewEvent.heading)
                )
            }

            is DSWantedFallbackViewDemoViewEvent.OnChangeAlternative -> {
                viewModel.setEvent(
                    DSWantedFallbackViewDemoEvent.OnChangeAlternative(viewEvent.alternative)
                )
            }

            is DSWantedFallbackViewDemoViewEvent.OnChangeMain -> {
                viewModel.setEvent(
                    DSWantedFallbackViewDemoEvent.OnChangeMain(viewEvent.main)
                )
            }

            is DSWantedFallbackViewDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedFallbackViewDemoEvent.CopyCode)
            }

            is DSWantedFallbackViewDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedFallbackViewDemoEvent.ShowCode(true))
            }
        }
    }

    if (viewState.isShowCode) {
        WantedPopup(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedFallbackViewDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedFallbackViewDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedFallbackViewDemoEvent.ShowCode(false))
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
private fun DSWantedFallbackViewDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedFallbackViewDemoViewState,
    onViewEvent: (DSWantedFallbackViewDemoViewEvent) -> Unit
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedFallbackView") {
                onViewEvent(DSWantedFallbackViewDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedFallbackViewDemoViewEvent.OnClickShowCode)
                        }
                    )
                }
            )
        }
    ) { innerPadding ->

        DSWantedFallbackViewDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                WantedFallbackView(
                    padding = viewState.padding,
                    heading = if (viewState.heading) {
                        "헤더입니다."
                    } else {
                        null
                    },
                    description = if (viewState.description) {
                        "마침표를 찍어주세요."
                    } else {
                        null
                    },
                    buttonVariant = viewState.buttonVariant,
                    // 데모에서는 이 버튼이 전체 화면 미리보기로 이동하므로 동작에 맞춰 문구를 바꾼다.
                    main = if (viewState.main) {
                        "미리보기"
                    } else {
                        null
                    },
                    alternative = if (viewState.alternative) {
                        "보조행동"
                    } else {
                        null
                    },
                    onClickMain = {
                        onViewEvent(DSWantedFallbackViewDemoViewEvent.OnClickFallbackMain)
                    }
                )
            },
            copyCode = {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "코드 복사",
                    onClick = {
                        onViewEvent(DSWantedFallbackViewDemoViewEvent.OnClickCopyCode)
                    }
                )
            },
            heading = {
                DSWantedOptionSwitchCell(
                    text = "heading : ${viewState.heading}",
                    checkState = viewState.heading,
                    onCheckChanged = {
                        onViewEvent(DSWantedFallbackViewDemoViewEvent.OnChangeHeading(it))
                    }
                )
            },
            description = {
                DSWantedOptionSwitchCell(
                    text = "description : ${viewState.description}",
                    checkState = viewState.description,
                    onCheckChanged = {
                        onViewEvent(DSWantedFallbackViewDemoViewEvent.OnChangeDescription(it))
                    }
                )
            },
            buttonVariant = {
                WantedSelect(
                    value = "buttonVariant : ${viewState.buttonVariant.name}",
                    selectedValue = viewState.buttonVariant.name,
                    selectValueList = WantedFallbackButtonVariant.entries.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedFallbackViewDemoViewEvent.OnChangeButtonVariant(
                                WantedFallbackButtonVariant.valueOf(it)
                            )
                        )
                    }
                )
            },
            padding = {
                WantedSelect(
                    value = "padding : ${viewState.padding.name}",
                    selectedValue = viewState.padding.name,
                    selectValueList = WantedFallbackPadding.entries.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedFallbackViewDemoViewEvent.OnChangePadding(
                                WantedFallbackPadding.valueOf(it)
                            )
                        )
                    }
                )
            },
            main = {
                DSWantedOptionSwitchCell(
                    text = "main : ${viewState.main}",
                    checkState = viewState.main,
                    onCheckChanged = {
                        onViewEvent(DSWantedFallbackViewDemoViewEvent.OnChangeMain(it))
                    }
                )
            },
            alternative = {
                DSWantedOptionSwitchCell(
                    text = "alternative : ${viewState.alternative}",
                    checkState = viewState.alternative,
                    onCheckChanged = {
                        onViewEvent(DSWantedFallbackViewDemoViewEvent.OnChangeAlternative(it))
                    }
                )

            }
        )
    }
}


@Composable
private fun DSWantedFallbackViewDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    copyCode: @Composable () -> Unit,
    heading: @Composable () -> Unit,
    description: @Composable () -> Unit,
    buttonVariant: @Composable () -> Unit,
    padding: @Composable () -> Unit,
    main: @Composable () -> Unit,
    alternative: @Composable () -> Unit
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Preview",
            style = WantedTextStyle(
                colorRes = R.color.foreground_neutral_strong,
                style = DesignSystemTheme.typography.heading2Bold
            )
        )

        // padding 옵션(Normal 160dp)까지 그대로 그리면 화면 대부분을 차지해 옵션이 가려진다.
        // 박스 높이를 제한하고 안에서 스크롤해 확인한다 — 실제 여백 비율은 전체 화면 미리보기에서 본다.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = PREVIEW_MAX_HEIGHT)
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.line_neutral_primary),
                    shape = RoundedCornerShape(8.dp)
                )
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            preview()
        }

        copyCode()

        Spacer(Modifier.size(10.dp))

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
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

            heading()
            description()
            buttonVariant()
            padding()
            main()
            alternative()
        }
    }
}


private val PREVIEW_MAX_HEIGHT = 320.dp

@DevicePreviews
@Composable
private fun DSWantedFallbackViewDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedFallbackViewDemoScreenContent(
            viewState = DSWantedFallbackViewDemoViewState(),
            onViewEvent = { }
        )
    }
}
