package com.wanted.android.montage.sample.feedback.toast

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.designsystem.R
import com.wanted.android.montage.sample.feedback.toast.DSWantedToastDemoScreenContract.DSWantedToastDemoEvent
import com.wanted.android.montage.sample.feedback.toast.DSWantedToastDemoScreenContract.DSWantedToastDemoSideEffect
import com.wanted.android.montage.sample.feedback.toast.DSWantedToastDemoScreenContract.DSWantedToastDemoViewEvent
import com.wanted.android.montage.sample.feedback.toast.DSWantedToastDemoScreenContract.DSWantedToastDemoViewState
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.feedback.toast.showToast
import com.wanted.android.wanted.design.feedback.toast.WantedToast
import com.wanted.android.wanted.design.feedback.toast.WantedToastVariant
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedToastDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedToastDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedToastDemoSideEffect.ShowToast -> {
                snackbarHostState.showToast(
                    scope = coroutineScope,
                    message = "토스트가 보여졌습니다.",
                    variant = sideEffect.variant
                )
            }
        }
    }

    DSWantedToastDemoScreenContent(
        modifier = modifier,
        viewState = viewState,
        snackbarHostState = snackbarHostState,
        onViewEvent = { viewEvent ->
            when (viewEvent) {
                DSWantedToastDemoViewEvent.OnClickBack -> onClickBack()
                DSWantedToastDemoViewEvent.OnClickCautionary -> {
                    viewModel.setEvent(
                        DSWantedToastDemoEvent.SetVariant(WantedToastVariant.Cautionary)
                    )
                }


                DSWantedToastDemoViewEvent.OnClickMessage -> {
                    viewModel.setEvent(
                        DSWantedToastDemoEvent.SetVariant(WantedToastVariant.Message)
                    )
                }

                DSWantedToastDemoViewEvent.OnClickNegative -> {
                    viewModel.setEvent(
                        DSWantedToastDemoEvent.SetVariant(WantedToastVariant.Negative)
                    )
                }

                DSWantedToastDemoViewEvent.OnClickPositive -> {
                    viewModel.setEvent(
                        DSWantedToastDemoEvent.SetVariant(WantedToastVariant.Positive)
                    )
                }

                DSWantedToastDemoViewEvent.OnClickShowToast -> {
                    viewModel.setEvent(DSWantedToastDemoEvent.ShowToast)
                }
            }
        }
    )
}

@Composable
private fun DSWantedToastDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedToastDemoViewState,
    snackbarHostState: SnackbarHostState,
    onViewEvent: (DSWantedToastDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedTextArea") {
                onViewEvent(DSWantedToastDemoViewEvent.OnClickBack)
            }
        }, snackbarHost = {
            WantedToast(snackbarHostState = snackbarHostState)
        }
    ) { innerPadding ->
        DSWantedTextAreaDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "toast 보기",
                    variant = ButtonVariant.SOLID,
                    onClick = {
                        onViewEvent(DSWantedToastDemoViewEvent.OnClickShowToast)
                    }
                )
            },
            message = {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "메시지",
                    variant = if (viewState.toastVariant == WantedToastVariant.Message) {
                        ButtonVariant.SOLID
                    } else {
                        ButtonVariant.OUTLINED
                    },
                    onClick = {
                        onViewEvent(DSWantedToastDemoViewEvent.OnClickMessage)
                    }
                )
            },
            positive = {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "포지티브",
                    variant = if (viewState.toastVariant == WantedToastVariant.Positive) {
                        ButtonVariant.SOLID
                    } else {
                        ButtonVariant.OUTLINED
                    },
                    onClick = {
                        onViewEvent(DSWantedToastDemoViewEvent.OnClickPositive)
                    }
                )
            },
            cautionary = {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "주의",
                    variant = if (viewState.toastVariant == WantedToastVariant.Cautionary) {
                        ButtonVariant.SOLID
                    } else {
                        ButtonVariant.OUTLINED
                    },
                    onClick = {
                        onViewEvent(DSWantedToastDemoViewEvent.OnClickCautionary)
                    }
                )
            },
            negative = {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "네거티브",
                    variant = if (viewState.toastVariant == WantedToastVariant.Negative) {
                        ButtonVariant.SOLID
                    } else {
                        ButtonVariant.OUTLINED
                    },
                    onClick = {
                        onViewEvent(DSWantedToastDemoViewEvent.OnClickNegative)
                    }
                )
            }
        )
    }
}


@Composable
private fun DSWantedTextAreaDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    message: @Composable () -> Unit,
    positive: @Composable () -> Unit,
    cautionary: @Composable () -> Unit,
    negative: @Composable () -> Unit
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

            message()
            positive()
            cautionary()
            negative()
        }
    }
}


@DevicePreviews
@Composable
private fun DSWantedToastDemoScreenPreview() {
    DesignSystemTheme {
    }
}