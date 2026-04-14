package com.wanted.android.montage.sample.feedback.fallback

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
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
import com.wanted.android.montage.sample.util.isNullOrBlock
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.designsystem.R
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.feedback.fallback.DSWantedFallbackViewDemoScreenContract.DSWantedFallbackViewDemoEvent
import com.wanted.android.montage.sample.feedback.fallback.DSWantedFallbackViewDemoScreenContract.DSWantedFallbackViewDemoSideEffect
import com.wanted.android.montage.sample.feedback.fallback.DSWantedFallbackViewDemoScreenContract.DSWantedFallbackViewDemoViewEvent
import com.wanted.android.montage.sample.feedback.fallback.DSWantedFallbackViewDemoScreenContract.DSWantedFallbackViewDemoViewState
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.feedback.fallback.WantedFallbackButtonVariant
import com.wanted.android.wanted.design.feedback.fallback.WantedFallbackView
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
fun DSWantedFallbackViewDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedFallbackViewDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
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
            is DSWantedFallbackViewDemoViewEvent.OnChangeButtonVariant -> {
                viewModel.setEvent(
                    DSWantedFallbackViewDemoEvent.OnChangeButtonVariant(viewEvent.buttonVariant)
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

            is DSWantedFallbackViewDemoViewEvent.OnChangeImage -> {
                viewModel.setEvent(
                    DSWantedFallbackViewDemoEvent.OnChangeImage(viewEvent.image)
                )
            }

            is DSWantedFallbackViewDemoViewEvent.OnChangeNegative -> {
                viewModel.setEvent(
                    DSWantedFallbackViewDemoEvent.OnChangeNegative(viewEvent.negative)
                )
            }

            is DSWantedFallbackViewDemoViewEvent.OnChangeNegativeColor -> {
                viewModel.setEvent(
                    DSWantedFallbackViewDemoEvent.OnChangeNegativeColor(viewEvent.negativeColor)
                )
            }

            is DSWantedFallbackViewDemoViewEvent.OnChangePositive -> {
                viewModel.setEvent(
                    DSWantedFallbackViewDemoEvent.OnChangePositive(viewEvent.positive)
                )
            }

            is DSWantedFallbackViewDemoViewEvent.OnChangePositiveColor -> {
                viewModel.setEvent(
                    DSWantedFallbackViewDemoEvent.OnChangePositiveColor(viewEvent.positiveColor)
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
        WantedModal(
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
                positive = {
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
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    WantedFallbackView(
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
                        image = viewState.image.isNullOrBlock {
                            Box(modifier = Modifier
                                .fillMaxSize()
                                .background(colorResource(R.color.label_disable)))
                        },
                        buttonVariant = viewState.buttonVariant,
                        positive = if (viewState.positive) {
                            "행동"
                        } else {
                            null
                        },
                        positiveColor = viewState.positiveColor,
                        negative = if (viewState.negative) {
                            "보조행동"
                        } else {
                            null
                        },
                        negativeColor = viewState.negativeColor
                    )

                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedFallbackViewDemoViewEvent.OnClickCopyCode)
                        }
                    )
                }
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
            image = {
                DSWantedOptionSwitchCell(
                    text = "image : ${viewState.image}",
                    checkState = viewState.image,
                    onCheckChanged = {
                        onViewEvent(DSWantedFallbackViewDemoViewEvent.OnChangeImage(it))
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
            positive = {
                DSWantedOptionSwitchCell(
                    text = "positive : ${viewState.positive}",
                    checkState = viewState.positive,
                    onCheckChanged = {
                        onViewEvent(DSWantedFallbackViewDemoViewEvent.OnChangePositive(it))
                    }
                )
            },
            positiveColor = {
                WantedSelect(
                    value = "positiveColor : ${viewState.positiveColor.name}",
                    selectedValue = viewState.positiveColor.name,
                    selectValueList = ButtonType.entries.map { it.name },
                    onSelect = { color ->
                        onViewEvent(
                            DSWantedFallbackViewDemoViewEvent.OnChangePositiveColor(
                                ButtonType.entries.find { it.name == color } ?: ButtonType.ASSISTIVE
                            )
                        )
                    }
                )
            },
            negative = {
                DSWantedOptionSwitchCell(
                    text = "negative : ${viewState.negative}",
                    checkState = viewState.negative,
                    onCheckChanged = {
                        onViewEvent(DSWantedFallbackViewDemoViewEvent.OnChangeNegative(it))
                    }
                )

            },
            negativeColor = {
                WantedSelect(
                    value = "negativeColor : ${viewState.negativeColor.name}",
                    selectedValue = viewState.negativeColor.name,
                    selectValueList = ButtonType.entries.map { it.name },
                    onSelect = { color ->
                        onViewEvent(
                            DSWantedFallbackViewDemoViewEvent.OnChangeNegativeColor(
                                ButtonType.entries.find { it.name == color } ?: ButtonType.ASSISTIVE
                            )
                        )
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
    heading: @Composable () -> Unit,
    image: @Composable () -> Unit,
    description: @Composable () -> Unit,
    buttonVariant: @Composable () -> Unit,
    positive: @Composable () -> Unit,
    positiveColor: @Composable () -> Unit,
    negative: @Composable () -> Unit,
    negativeColor: @Composable () -> Unit
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

            heading()
            description()
            image()
            buttonVariant()
            positive()
            positiveColor()
            negative()
            negativeColor()
        }
    }
}


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
