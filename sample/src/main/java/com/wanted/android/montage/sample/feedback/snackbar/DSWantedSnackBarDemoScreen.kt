package com.wanted.android.montage.sample.feedback.snackbar

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.wanted.android.montage.sample.feedback.snackbar.DSWantedSnackBarDemoScreenContract.DSWantedSnackBarDemoEvent
import com.wanted.android.montage.sample.feedback.snackbar.DSWantedSnackBarDemoScreenContract.DSWantedSnackBarDemoSideEffect
import com.wanted.android.montage.sample.feedback.snackbar.DSWantedSnackBarDemoScreenContract.DSWantedSnackBarDemoViewEvent
import com.wanted.android.montage.sample.feedback.snackbar.DSWantedSnackBarDemoScreenContract.DSWantedSnackBarDemoViewState
import com.wanted.android.montage.sample.feedback.snackbar.DSWantedSnackBarDemoScreenContract.SnackBarOption
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.feedback.snackbar.WantedSnackBar
import com.wanted.android.wanted.design.feedback.snackbar.WantedSnackbarVisuals
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.WantedTextStyle
import kotlinx.coroutines.launch

@Composable
fun DSWantedSnackBarDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedSnackBarDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedSnackBarDemoSideEffect.ShowSnackBar -> {
                coroutineScope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(
                        visuals = when (sideEffect.option) {
                            SnackBarOption.MessageOnly -> WantedSnackbarVisuals(
                                message = "메시지에 마침표를 찍어요."
                            )

                            SnackBarOption.MessageWithButton -> WantedSnackbarVisuals(
                                message = "메시지에 마침표를 찍어요.",
                                actionLabel = "버튼"
                            )

                            SnackBarOption.MessageWithDescription -> WantedSnackbarVisuals(
                                message = "메시지에 마침표를 찍어요.",
                                description = "설명은 필요할 때만 써요."
                            )

                            SnackBarOption.MessageWithDescriptionAndButtonAndIcon -> WantedSnackbarVisuals(
                                message = "메시지에 마침표를 찍어요.",
                                description = "설명은 필요할 때만 써요.",
                                actionLabel = "버튼",
                                icon = {
                                    Icon(
                                        contentDescription = "icon",
                                        painter = painterResource(id = R.drawable.icon_normal_eye_fill),
                                        modifier = Modifier.size(32.dp),
                                        tint = DesignSystemTheme.colors.statusNegative
                                    )
                                }
                            )
                        }
                    )
                }
            }

            is DSWantedSnackBarDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedSnackBarDemoScreenContent(
        modifier = modifier,
        viewState = viewState,
        snackbarHostState = snackbarHostState,
        onViewEvent = { viewEvent ->
            when (viewEvent) {
                DSWantedSnackBarDemoViewEvent.OnClickBack -> onClickBack()
                DSWantedSnackBarDemoViewEvent.OnClickShowSnackBar -> {
                    viewModel.setEvent(DSWantedSnackBarDemoEvent.ShowSnackBar)
                }

                is DSWantedSnackBarDemoViewEvent.OnClickOption -> {
                    viewModel.setEvent(DSWantedSnackBarDemoEvent.SetOption(viewEvent.option))
                }

                DSWantedSnackBarDemoViewEvent.OnClickCopyCode -> {
                    viewModel.setEvent(DSWantedSnackBarDemoEvent.CopyCode)
                }

                DSWantedSnackBarDemoViewEvent.OnClickShowCode -> {
                    viewModel.setEvent(DSWantedSnackBarDemoEvent.ShowCode(true))
                }
            }
        }
    )

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedSnackBarDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedSnackBarDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedSnackBarDemoEvent.ShowCode(false))
            },
            content = {
                Text(text = viewState.code)
            }
        )
    }
}

@Composable
private fun DSWantedSnackBarDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedSnackBarDemoViewState,
    snackbarHostState: SnackbarHostState,
    onViewEvent: (DSWantedSnackBarDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedSnackBar") {
                onViewEvent(DSWantedSnackBarDemoViewEvent.OnClickBack)
            }
        },
        snackbarHost = {
            WantedSnackBar(snackbarHostState = snackbarHostState)
        },
        bottomBar = {
            WantedActionArea(
                modifier = Modifier.navigationBarsPadding(),
                background = true,
                negative = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 보기",
                        onClick = {
                            onViewEvent(DSWantedSnackBarDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                positive = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        variant = ButtonVariant.OUTLINED,
                        onClick = {
                            onViewEvent(DSWantedSnackBarDemoViewEvent.OnClickCopyCode)
                        }
                    )
                }
            )
        }
    ) { innerPadding ->
        DSWantedSnackBarDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "스낵바 보기",
                    variant = ButtonVariant.SOLID,
                    onClick = {
                        onViewEvent(DSWantedSnackBarDemoViewEvent.OnClickShowSnackBar)
                    }
                )
            },
            messageOnly = {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "메시지",
                    variant = if (viewState.selectedOption == SnackBarOption.MessageOnly) {
                        ButtonVariant.SOLID
                    } else {
                        ButtonVariant.OUTLINED
                    },
                    onClick = {
                        onViewEvent(DSWantedSnackBarDemoViewEvent.OnClickOption(SnackBarOption.MessageOnly))
                    }
                )
            },
            messageWithButton = {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "메시지 + 버튼",
                    variant = if (viewState.selectedOption == SnackBarOption.MessageWithButton) {
                        ButtonVariant.SOLID
                    } else {
                        ButtonVariant.OUTLINED
                    },
                    onClick = {
                        onViewEvent(DSWantedSnackBarDemoViewEvent.OnClickOption(SnackBarOption.MessageWithButton))
                    }
                )
            },
            messageWithDescription = {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "메시지 + 설명",
                    variant = if (viewState.selectedOption == SnackBarOption.MessageWithDescription) {
                        ButtonVariant.SOLID
                    } else {
                        ButtonVariant.OUTLINED
                    },
                    onClick = {
                        onViewEvent(DSWantedSnackBarDemoViewEvent.OnClickOption(SnackBarOption.MessageWithDescription))
                    }
                )
            },
            messageWithDescriptionAndButtonAndIcon = {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "메시지 + 설명 + 버튼 + 아이콘",
                    variant = if (viewState.selectedOption == SnackBarOption.MessageWithDescriptionAndButtonAndIcon) {
                        ButtonVariant.SOLID
                    } else {
                        ButtonVariant.OUTLINED
                    },
                    onClick = {
                        onViewEvent(DSWantedSnackBarDemoViewEvent.OnClickOption(SnackBarOption.MessageWithDescriptionAndButtonAndIcon))
                    }
                )
            }
        )
    }
}

@Composable
private fun DSWantedSnackBarDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    messageOnly: @Composable () -> Unit,
    messageWithButton: @Composable () -> Unit,
    messageWithDescription: @Composable () -> Unit,
    messageWithDescriptionAndButtonAndIcon: @Composable () -> Unit,
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

            messageOnly()
            messageWithButton()
            messageWithDescription()
            messageWithDescriptionAndButtonAndIcon()
        }
    }
}

@DevicePreviews
@Composable
private fun DSWantedSnackBarDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedSnackBarDemoScreenContent(
            viewState = DSWantedSnackBarDemoViewState(),
            snackbarHostState = SnackbarHostState(),
            onViewEvent = { }
        )
    }
}
