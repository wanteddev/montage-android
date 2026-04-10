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
import androidx.compose.foundation.layout.wrapContentSize
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
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonSize
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

            is DSWantedActionAreaDemoViewEvent.OnChangeDivider -> {
                viewModel.setEvent(
                    DSWantedActionAreaDemoEvent.SetDivider(viewEvent.divider)
                )
            }

            is DSWantedActionAreaDemoViewEvent.OnChangeNegative -> {
                viewModel.setEvent(
                    DSWantedActionAreaDemoEvent.SetNegative(viewEvent.negative)
                )
            }

            is DSWantedActionAreaDemoViewEvent.OnChangeNeutral -> {
                viewModel.setEvent(
                    DSWantedActionAreaDemoEvent.SetNeutral(viewEvent.neutral)
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

            is DSWantedActionAreaDemoViewEvent.OnChangeGradationColor -> {
                viewModel.setEvent(
                    DSWantedActionAreaDemoEvent.SetGradationColor(viewEvent.colorIndex)
                )
            }

            is DSWantedActionAreaDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedActionAreaDemoEvent.CopyCode)
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
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
        WantedModal(
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
                positive = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 보기",
                        onClick = {
                            onViewEvent(DSWantedActionAreaDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                negative = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Sample 보기",
                        variant = ButtonVariant.OUTLINED,
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
                        scrollableState = scrollState,
                        divider = viewState.divider,
                        background = viewState.background,
                        gradationColor = Color(viewState.gradationColorList[viewState.gradationColorIndex].color),
                        positive = {
                            WantedButton(
                                modifier = Modifier.fillMaxWidth(),
                                text = "positive",
                                onClick = {
                                    onViewEvent(DSWantedActionAreaDemoViewEvent.OnClickCopyCode)
                                }
                            )
                        },
                        negative = if (viewState.negative) {
                            {
                                WantedButton(
                                    modifier = Modifier.fillMaxWidth(),
                                    variant = ButtonVariant.OUTLINED,
                                    type = ButtonType.PRIMARY,
                                    text = "negative",
                                    onClick = {
                                        onViewEvent(DSWantedActionAreaDemoViewEvent.OnClickCopyCode)
                                    }
                                )
                            }
                        } else null,
                        neutral = if (viewState.neutral) {
                            {
                                WantedButton(
                                    modifier = Modifier.wrapContentSize(),
                                    variant = if (viewState.type == ActionAreaType.Strong) {
                                        ButtonVariant.TEXT
                                    } else {
                                        ButtonVariant.OUTLINED
                                    },
                                    size = if (viewState.type == ActionAreaType.Strong) {
                                        ButtonSize.SMALL
                                    } else {
                                        ButtonSize.LARGE
                                    },
                                    type = ButtonType.ASSISTIVE,
                                    text = "neutral",
                                    onClick = {
                                        onViewEvent(DSWantedActionAreaDemoViewEvent.OnClickCopyCode)
                                    }
                                )
                            }
                        } else null,
                        extra = if (viewState.extra) {
                            {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(colorResource(R.color.accent_background_purple)),
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
            gradationColor = {
                WantedSelect(
                    value = "Gradation Color : ${viewState.gradationColorList[viewState.gradationColorIndex].name}",
                    selectedValue = viewState.gradationColorList[viewState.gradationColorIndex].name,
                    selectValueList = viewState.gradationColorList.map { it.name },
                    onSelect = { name ->
                        val index = viewState.gradationColorList.indexOfFirst { it.name == name }
                        onViewEvent(DSWantedActionAreaDemoViewEvent.OnChangeGradationColor(index))
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
            divider = {
                DSWantedOptionSwitchCell(
                    text = "divider : ${viewState.divider}",
                    checkState = viewState.divider,
                    onCheckChanged = {
                        onViewEvent(DSWantedActionAreaDemoViewEvent.OnChangeDivider(it))
                    }
                )
            },
            negative = {
                DSWantedOptionSwitchCell(
                    text = "negative : ${viewState.negative}",
                    checkState = viewState.negative,
                    onCheckChanged = {
                        onViewEvent(DSWantedActionAreaDemoViewEvent.OnChangeNegative(it))
                    }
                )
            },
            neutral = {
                DSWantedOptionSwitchCell(
                    text = "neutral : ${viewState.neutral}",
                    checkState = viewState.neutral,
                    onCheckChanged = {
                        onViewEvent(DSWantedActionAreaDemoViewEvent.OnChangeNeutral(it))
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
    gradationColor: @Composable () -> Unit,
    divider: @Composable () -> Unit,
    caption: @Composable () -> Unit,
    negative: @Composable () -> Unit,
    neutral: @Composable () -> Unit,
    extra: @Composable () -> Unit,
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

            type()
            safeArea()
            background()
            gradationColor()
            caption()
            divider()
            negative()
            neutral()
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
            negative = {
                WantedButton(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    text = "취소",
                    variant = ButtonVariant.OUTLINED,
                    type = ButtonType.PRIMARY
                )
            },
            positive = {
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
                .background(colorResource(R.color.material_dimmer))
        )

        WantedActionArea(
            modifier = Modifier.fillMaxWidth(),
            type = ActionAreaType.Strong,
            negative = {
                WantedButton(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    text = "취소",
                    variant = ButtonVariant.OUTLINED,
                    type = ButtonType.ASSISTIVE,
                )
            },
            positive = {
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
                .background(colorResource(R.color.material_dimmer))
        )

        WantedActionArea(
            type = ActionAreaType.Strong,
            caption = "캡션",
            positive = "메인 액션",
            negative = "대체 액션",
            neutral = "보조 액션",
            onClickPositive = {},
            onClickNegative = {},
            onClickNeutral = {}
        )

        Box(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
                .background(colorResource(R.color.material_dimmer))
        )

        WantedActionArea(
            type = ActionAreaType.Neutral,
            caption = "캡션",
            positive = "메인 액션",
            negative = "대체 액션",
            neutral = "보조 액션",
            onClickPositive = {},
            onClickNegative = {},
            onClickNeutral = {}
        )

        Box(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
                .background(colorResource(R.color.material_dimmer))
        )

        WantedActionArea(
            type = ActionAreaType.Neutral,
            caption = "캡션",
            positive = "메인 액션",
            negative = "대체 액션",
            neutral = "보조 액션",
            onClickPositive = {},
            onClickNegative = {},
//                onClickNeutral = {}
        )

        Box(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
                .background(colorResource(R.color.material_dimmer))
        )

        WantedActionArea(
            type = ActionAreaType.Neutral,
            caption = "캡션",
            positive = "메인 액션",
            negative = "대체 액션",
            neutral = "보조 액션",
            onClickPositive = {},
//                onClickNegative = {},
            onClickNeutral = {}
        )

        Box(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
                .background(colorResource(R.color.material_dimmer))
        )

        WantedActionArea(
            type = ActionAreaType.Cancel,
            caption = "캡션",
            positive = "메인 액션",
            negative = "대체 액션",
            neutral = "보조 액션",
            onClickPositive = {},
            onClickNegative = {},
            onClickNeutral = {}
        )

        Box(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
                .background(colorResource(R.color.material_dimmer))
        )

        Column(modifier = Modifier) {
            Text(text = "sticky true - 컨텐츠 영역 컨텐츠 영역")
            Text(text = "sticky true - 컨텐츠 영역 컨텐츠 영역")
            WantedActionArea(
                type = ActionAreaType.Cancel,
                background = true,
                positive = "메인 액션",
                negative = "대체 액션",
                neutral = "보조 액션",
                onClickPositive = {},
                onClickNegative = {},
                onClickNeutral = {}
            )
        }

        Box(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
                .background(colorResource(R.color.material_dimmer))
        )

        Column(modifier = Modifier) {
            Text(text = "sticky true safeArea false - 컨텐츠 영역 컨텐츠 영역")
            Text(text = "sticky true safeArea false - 컨텐츠 영역 컨텐츠 영역")
            WantedActionArea(
                type = ActionAreaType.Cancel,
                background = true,
                safeArea = false,
                positive = "메인 액션",
                negative = "대체 액션",
                neutral = "보조 액션",
                onClickPositive = {},
                onClickNegative = {},
                onClickNeutral = {}
            )
        }

        Box(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
                .background(colorResource(R.color.material_dimmer))
        )

        WantedActionArea(
            type = ActionAreaType.Cancel,
            caption = "캡션",
            positive = "메인 액션",
            negative = "대체 액션",
            neutral = "보조 액션",
            onClickPositive = {},
            onClickNegative = {},
            onClickNeutral = {},
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
