package com.wanted.android.montage.sample.actions.actionarea

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
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
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.R
import com.wanted.android.montage.sample.actions.actionarea.DSWantedActionAreaDemoScreenContract.DSWantedActionAreaDemoEvent
import com.wanted.android.montage.sample.actions.actionarea.DSWantedActionAreaDemoScreenContract.DSWantedActionAreaDemoViewEvent
import com.wanted.android.montage.sample.actions.actionarea.DSWantedActionAreaDemoScreenContract.DSWantedActionAreaDemoViewState
import com.wanted.android.wanted.design.actions.actionarea.ActionAreaType
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.actionarea.WantedActionAreaDefaults
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.navigations.topbar.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopup
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedActionAreaDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedActionAreaDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val clipboardManager = LocalClipboardManager.current
    val previewScrollState = rememberScrollState()

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedActionAreaDemoScreenContract.DSWantedActionAreaDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedActionAreaDemoScreenContent(
        modifier = modifier,
        viewState = viewState,
        scrollState = previewScrollState,
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedActionAreaDemoViewEvent.OnClickBack -> onClickBack()
            DSWantedActionAreaDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(
                    DSWantedActionAreaDemoEvent.ShowCode(true)
                )
            }

            DSWantedActionAreaDemoViewEvent.OnClickSample -> {
                viewModel.setEvent(
                    DSWantedActionAreaDemoEvent.Sample(true)
                )
            }

            is DSWantedActionAreaDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedActionAreaDemoEvent.CopyCode)
            }

            else -> handleOptionViewEvent(viewEvent, viewModel)
        }
    }

    if (viewState.isShowCode) {
        WantedPopup(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedActionAreaDemoEvent.CopyCode)
                viewModel.setEvent(
                    DSWantedActionAreaDemoEvent.ShowCode(false)
                )
            },
            onDismissRequest = {
                viewModel.setEvent(
                    DSWantedActionAreaDemoEvent.ShowCode(false)
                )
            },
            content = {
                Text(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    text = viewState.code
                )
            }
        )
    }

    if (viewState.isShowSample) {
        WantedPopup(
            positive = "확인",
            onClickPositive = {
                viewModel.setEvent(DSWantedActionAreaDemoEvent.Sample(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedActionAreaDemoEvent.Sample(false))
            },
            content = {
                ActionSample()
            }
        )
    }
}

private fun handleOptionViewEvent(
    viewEvent: DSWantedActionAreaDemoViewEvent,
    viewModel: DSWantedActionAreaDemoViewModel
) {
    when (viewEvent) {
        is DSWantedActionAreaDemoViewEvent.OnSelectType -> {
            viewModel.setEvent(
                DSWantedActionAreaDemoEvent.SetType(viewEvent.type)
            )
        }

        is DSWantedActionAreaDemoViewEvent.OnChangeSafeArea -> {
            viewModel.setEvent(
                DSWantedActionAreaDemoEvent.SetSafeArea(viewEvent.safeArea)
            )
        }

        is DSWantedActionAreaDemoViewEvent.OnChangeCaption -> {
            viewModel.setEvent(
                DSWantedActionAreaDemoEvent.SetCaption(viewEvent.caption)
            )
        }

        is DSWantedActionAreaDemoViewEvent.OnChangeCaptionIcon -> {
            viewModel.setEvent(
                DSWantedActionAreaDemoEvent.SetCaptionIcon(viewEvent.captionIcon)
            )
        }

        is DSWantedActionAreaDemoViewEvent.OnChangeDivider -> {
            viewModel.setEvent(
                DSWantedActionAreaDemoEvent.SetDivider(viewEvent.divider)
            )
        }

        is DSWantedActionAreaDemoViewEvent.OnChangeAlternative -> {
            viewModel.setEvent(
                DSWantedActionAreaDemoEvent.SetAlternative(viewEvent.alternative)
            )
        }

        is DSWantedActionAreaDemoViewEvent.OnChangeSub -> {
            viewModel.setEvent(
                DSWantedActionAreaDemoEvent.SetSub(viewEvent.sub)
            )
        }

        is DSWantedActionAreaDemoViewEvent.OnChangeExtra -> {
            viewModel.setEvent(
                DSWantedActionAreaDemoEvent.SetExtra(viewEvent.extra)
            )
        }

        is DSWantedActionAreaDemoViewEvent.OnChangeBackground -> {
            viewModel.setEvent(
                DSWantedActionAreaDemoEvent.SetBackground(viewEvent.background)
            )
        }

        is DSWantedActionAreaDemoViewEvent.OnChangeBackgroundColor -> {
            viewModel.setEvent(
                DSWantedActionAreaDemoEvent.SetBackgroundColor(viewEvent.colorIndex)
            )
        }

        else -> Unit
    }
}

@Composable
private fun DSWantedActionAreaDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedActionAreaDemoViewState,
    scrollState: ScrollState,
    onViewEvent: (DSWantedActionAreaDemoViewEvent) -> Unit
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedActionArea") {
                onViewEvent(DSWantedActionAreaDemoViewEvent.OnClickBack)
            }
        },
        bottomBar = {
            WantedActionArea(
                modifier = Modifier.navigationBarsPadding(),
                background = true,
                type = viewState.type,
                main = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 보기",
                        onClick = {
                            onViewEvent(DSWantedActionAreaDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                alternative = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Sample 보기",
                        variant = ButtonVariant.OUTLINED,
                        type = ButtonType.ASSISTIVE,
                        onClick = {
                            onViewEvent(DSWantedActionAreaDemoViewEvent.OnClickSample)
                        }
                    )
                }
            )
        }
    ) { innerPadding ->
        DSWantedActionAreaDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                Column {
                    Column(
                        modifier = Modifier
                            .height(100.dp)
                            .verticalScroll(scrollState),
                    ) {
                        repeat(25) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .size(10.dp)
                                    .background(
                                        Color(
                                            255 - (it * 10),
                                            255 - (it * 10),
                                            255 - (it * 10)
                                        )
                                    )
                            )
                        }
                    }

                    WantedActionArea(
                        modifier = Modifier.fillMaxWidth(),
                        type = viewState.type,
                        safeArea = viewState.safeArea,
                        caption = if (viewState.caption) "캡션 입니다." else null,
                        captionIcon = if (viewState.captionIcon) {
                            WantedActionAreaDefaults.CAPTION_ICON
                        } else {
                            null
                        },
                        scrollableState = scrollState,
                        divider = viewState.divider,
                        background = viewState.background,
                        backgroundColor = Color(
                            viewState.backgroundColorList[viewState.backgroundColorIndex].color
                        ),
                        main = "main",
                        onClickMain = {
                            onViewEvent(DSWantedActionAreaDemoViewEvent.OnClickCopyCode)
                        },
                        alternative = if (viewState.alternative) "alternative" else null,
                        onClickAlternative = if (viewState.alternative) {
                            { onViewEvent(DSWantedActionAreaDemoViewEvent.OnClickCopyCode) }
                        } else {
                            null
                        },
                        sub = if (viewState.sub) "sub" else null,
                        onClickSub = if (viewState.sub) {
                            { onViewEvent(DSWantedActionAreaDemoViewEvent.OnClickCopyCode) }
                        } else {
                            null
                        },
                        extra = if (viewState.extra) {
                            {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(colorResource(R.color.surface_accent_purple_opaque)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "extra 영역 입니다.")
                                }
                            }
                        } else null
                    )
                }
            },
            type = {
                WantedSelect(
                    value = "Type : ${viewState.type.name}",
                    selectedValue = viewState.type.name,
                    selectValueList = viewState.typeList.map { it.name },
                    onSelect = { name ->
                        onViewEvent(
                            DSWantedActionAreaDemoViewEvent.OnSelectType(ActionAreaType.valueOf(name))
                        )
                    },
                )
            },
            safeArea = {
                DSWantedOptionSwitchCell(
                    text = "safeArea : ${viewState.safeArea}",
                    checkState = viewState.safeArea,
                    onCheckChanged = {
                        onViewEvent(DSWantedActionAreaDemoViewEvent.OnChangeSafeArea(it))
                    }
                )
            },
            background = {
                DSWantedOptionSwitchCell(
                    text = "background : ${viewState.background}",
                    checkState = viewState.background,
                    onCheckChanged = {
                        onViewEvent(DSWantedActionAreaDemoViewEvent.OnChangeBackground(it))
                    }
                )
            },
            backgroundColor = {
                WantedSelect(
                    value = "Background Color : ${viewState.backgroundColorList[viewState.backgroundColorIndex].name}",
                    selectedValue = viewState.backgroundColorList[viewState.backgroundColorIndex].name,
                    selectValueList = viewState.backgroundColorList.map { it.name },
                    onSelect = { name ->
                        val index = viewState.backgroundColorList.indexOfFirst { it.name == name }
                        onViewEvent(DSWantedActionAreaDemoViewEvent.OnChangeBackgroundColor(index))
                    },
                )
            },
            caption = {
                DSWantedOptionSwitchCell(
                    text = "caption : ${viewState.caption}",
                    checkState = viewState.caption,
                    onCheckChanged = {
                        onViewEvent(DSWantedActionAreaDemoViewEvent.OnChangeCaption(it))
                    }
                )
            },
            captionIcon = {
                DSWantedOptionSwitchCell(
                    text = "captionIcon : ${viewState.captionIcon}",
                    checkState = viewState.captionIcon,
                    onCheckChanged = {
                        onViewEvent(DSWantedActionAreaDemoViewEvent.OnChangeCaptionIcon(it))
                    }
                )
            },
            divider = {
                DSWantedOptionSwitchCell(
                    text = "divider : ${viewState.divider}",
                    checkState = viewState.divider,
                    onCheckChanged = {
                        onViewEvent(DSWantedActionAreaDemoViewEvent.OnChangeDivider(it))
                    }
                )
            },
            alternative = {
                DSWantedOptionSwitchCell(
                    text = "alternative : ${viewState.alternative}",
                    checkState = viewState.alternative,
                    onCheckChanged = {
                        onViewEvent(DSWantedActionAreaDemoViewEvent.OnChangeAlternative(it))
                    }
                )
            },
            sub = {
                DSWantedOptionSwitchCell(
                    text = "sub : ${viewState.sub}",
                    checkState = viewState.sub,
                    onCheckChanged = {
                        onViewEvent(DSWantedActionAreaDemoViewEvent.OnChangeSub(it))
                    }
                )
            },
            extra = {
                DSWantedOptionSwitchCell(
                    text = "extra : ${viewState.extra}",
                    checkState = viewState.extra,
                    onCheckChanged = {
                        onViewEvent(DSWantedActionAreaDemoViewEvent.OnChangeExtra(it))
                    }
                )
            }
        )
    }
}

@Composable
private fun DSWantedActionAreaDemoScreenLayout(
    modifier: Modifier,
    preview: @Composable () -> Unit,
    type: @Composable () -> Unit,
    safeArea: @Composable () -> Unit,
    background: @Composable () -> Unit,
    backgroundColor: @Composable () -> Unit,
    divider: @Composable () -> Unit,
    caption: @Composable () -> Unit,
    captionIcon: @Composable () -> Unit,
    alternative: @Composable () -> Unit,
    sub: @Composable () -> Unit,
    extra: @Composable () -> Unit,
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

            type()
            safeArea()
            background()
            backgroundColor()
            caption()
            captionIcon()
            divider()
            alternative()
            sub()
            extra()
        }
    }
}


@Composable
private fun ActionSample(
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 30.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        WantedActionArea(
            modifier = Modifier.fillMaxWidth(),
            type = ActionAreaType.Neutral,
            background = true,
            safeArea = false, // dialog 에서는 false, 일반 screen  에서는 true
            alternative = {
                WantedButton(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    text = "취소",
                    variant = ButtonVariant.OUTLINED,
                    type = ButtonType.ASSISTIVE
                )
            },
            main = {
                WantedButton(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    text = "확인",
                )
            }
        )

        Box(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
                .background(colorResource(R.color.effect_dimmer_primary))
        )

        WantedActionArea(
            modifier = Modifier.fillMaxWidth(),
            type = ActionAreaType.Strong,
            alternative = {
                WantedButton(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    text = "취소",
                    variant = ButtonVariant.OUTLINED,
                    type = ButtonType.ASSISTIVE,
                )
            },
            main = {
                WantedButton(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    text = "확인",
                )
            }
        )

        Box(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
                .background(colorResource(R.color.effect_dimmer_primary))
        )

        WantedActionArea(
            type = ActionAreaType.Strong,
            caption = "캡션",
            main = "메인 액션",
            alternative = "대체 액션",
            sub = "보조 액션",
            onClickMain = {},
            onClickAlternative = {},
            onClickSub = {}
        )

        Box(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
                .background(colorResource(R.color.effect_dimmer_primary))
        )

        WantedActionArea(
            type = ActionAreaType.Neutral,
            caption = "캡션",
            main = "메인 액션",
            alternative = "대체 액션",
            sub = "보조 액션",
            onClickMain = {},
            onClickAlternative = {},
            onClickSub = {}
        )

        Box(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
                .background(colorResource(R.color.effect_dimmer_primary))
        )

        WantedActionArea(
            type = ActionAreaType.Strong,
            caption = "캡션 아이콘",
            captionIcon = WantedActionAreaDefaults.CAPTION_ICON,
            main = "메인 액션",
            alternative = "대체 액션",
            onClickMain = {},
            onClickAlternative = {}
        )

        Box(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
                .background(colorResource(R.color.effect_dimmer_primary))
        )

        WantedActionArea(
            type = ActionAreaType.Neutral,
            caption = "캡션",
            main = "메인 액션",
            alternative = "대체 액션",
            sub = "보조 액션",
            onClickMain = {},
            onClickAlternative = {},
//                onClickSub = {}
        )

        Box(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
                .background(colorResource(R.color.effect_dimmer_primary))
        )

        WantedActionArea(
            type = ActionAreaType.Neutral,
            caption = "캡션",
            main = "메인 액션",
            alternative = "대체 액션",
            sub = "보조 액션",
            onClickMain = {},
//                onClickAlternative = {},
            onClickSub = {}
        )

        Box(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
                .background(colorResource(R.color.effect_dimmer_primary))
        )

        WantedActionArea(
            type = ActionAreaType.Cancel,
            caption = "캡션",
            main = "메인 액션",
            alternative = "대체 액션",
            sub = "보조 액션",
            onClickMain = {},
            onClickAlternative = {},
            onClickSub = {}
        )

        Box(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
                .background(colorResource(R.color.effect_dimmer_primary))
        )

        Column(modifier = Modifier) {
            Text(text = "sticky true - 컨텐츠 영역 컨텐츠 영역")
            Text(text = "sticky true - 컨텐츠 영역 컨텐츠 영역")
            WantedActionArea(
                type = ActionAreaType.Cancel,
                background = true,
                main = "메인 액션",
                alternative = "대체 액션",
                sub = "보조 액션",
                onClickMain = {},
                onClickAlternative = {},
                onClickSub = {}
            )
        }

        Box(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
                .background(colorResource(R.color.effect_dimmer_primary))
        )

        Column(modifier = Modifier) {
            Text(text = "sticky true safeArea false - 컨텐츠 영역 컨텐츠 영역")
            Text(text = "sticky true safeArea false - 컨텐츠 영역 컨텐츠 영역")
            WantedActionArea(
                type = ActionAreaType.Cancel,
                background = true,
                safeArea = false,
                main = "메인 액션",
                alternative = "대체 액션",
                sub = "보조 액션",
                onClickMain = {},
                onClickAlternative = {},
                onClickSub = {}
            )
        }

        Box(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
                .background(colorResource(R.color.effect_dimmer_primary))
        )

        WantedActionArea(
            type = ActionAreaType.Cancel,
            caption = "캡션",
            main = "메인 액션",
            alternative = "대체 액션",
            sub = "보조 액션",
            onClickMain = {},
            onClickAlternative = {},
            onClickSub = {},
            extra = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .background(Color.Gray)
                ) {
                    Text(text = "variant")
                }
            }
        )
    }
}

@DevicePreviews
@Composable
private fun DSWantedActionAreaDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedActionAreaDemoScreenContent(
            viewState = DSWantedActionAreaDemoViewState(),
            scrollState = rememberScrollState(),
            onViewEvent = { }
        )
    }
}
