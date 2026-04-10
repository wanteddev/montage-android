package com.wanted.android.montage.sample.input.input

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
import com.wanted.android.montage.sample.input.input.DSWantedInputDemoScreenContract.DSWantedInputDemoEvent
import com.wanted.android.montage.sample.input.input.DSWantedInputDemoScreenContract.DSWantedInputDemoSideEffect
import com.wanted.android.montage.sample.input.input.DSWantedInputDemoScreenContract.DSWantedInputDemoViewEvent
import com.wanted.android.montage.sample.input.input.DSWantedInputDemoScreenContract.DSWantedInputDemoViewState
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.input.control.CheckBoxState
import com.wanted.android.wanted.design.input.input.WantedInput
import com.wanted.android.wanted.design.input.input.WantedInputDefaults.WantedInputSize
import com.wanted.android.wanted.design.input.input.WantedInputDefaults.WantedInputVariant
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
fun DSWantedInputDemoScreen(
    modifier: Modifier,
    viewModel: DSWantedInputDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedInputDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedInputDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            is DSWantedInputDemoViewEvent.OnClickBack -> onClickBack()
            is DSWantedInputDemoViewEvent.OnChangeBold -> {
                viewModel.setEvent(DSWantedInputDemoEvent.OnChangeBold(viewEvent.bold))
            }

            is DSWantedInputDemoViewEvent.OnChangeEnabled -> {
                viewModel.setEvent(DSWantedInputDemoEvent.OnChangeEnabled(viewEvent.enabled))
            }

            is DSWantedInputDemoViewEvent.OnChangeText -> {
                viewModel.setEvent(DSWantedInputDemoEvent.OnChangeText(viewEvent.text))
            }

            is DSWantedInputDemoViewEvent.OnChangeTight -> {
                viewModel.setEvent(DSWantedInputDemoEvent.OnChangeTight(viewEvent.tight))
            }

            is DSWantedInputDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedInputDemoEvent.OnClickCopyCode)
            }

            is DSWantedInputDemoViewEvent.OnSelectSize -> {
                viewModel.setEvent(DSWantedInputDemoEvent.OnSelectSize(viewEvent.size))
            }

            is DSWantedInputDemoViewEvent.OnSelectType -> {
                viewModel.setEvent(DSWantedInputDemoEvent.OnSelectType(viewEvent.type))
            }

            is DSWantedInputDemoViewEvent.OnSelectCheckBoxState -> {
                viewModel.setEvent(
                    DSWantedInputDemoEvent.OnSelectCheckBoxState(viewEvent.checkBoxState)
                )
            }

            is DSWantedInputDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedInputDemoEvent.ShowCode(true))
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedInputDemoEvent.OnClickCopyCode)
                viewModel.setEvent(DSWantedInputDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedInputDemoEvent.ShowCode(false))
            },
            content = {
                Text(text = viewState.code)
            }
        )
    }
}

@Composable
private fun DSWantedInputDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedInputDemoViewState,
    onViewEvent: (DSWantedInputDemoViewEvent) -> Unit
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedInput") {
                onViewEvent(DSWantedInputDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedInputDemoViewEvent.OnClickShowCode)
                        }
                    )
                }
            )

        }
    ) { innerPadding ->

        DSWantedInputDemoScreenContentLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    WantedInput(
                        modifier = Modifier,
                        label = if (viewState.text) "text 입니다." else "",
                        variant = viewState.type,
                        size = viewState.size,
                        checkBoxState = viewState.checkBoxState,
                        bold = viewState.bold,
                        enabled = viewState.enabled,
                        tight = viewState.tight,
                        onCheckedChange = { }
                    )

                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedInputDemoViewEvent.OnClickCopyCode)
                        }
                    )
                }

            },
            text = {
                DSWantedOptionSwitchCell(
                    text = "text : ${if (viewState.text) "title" else null}",
                    checkState = viewState.text,
                    onCheckChanged = {
                        onViewEvent(DSWantedInputDemoViewEvent.OnChangeText(it))
                    }
                )
            },
            type = {
                WantedSelect(
                    value = "type : ${viewState.type}",
                    selectedValue = viewState.type.name,
                    selectValueList = WantedInputVariant.entries.map { it.name },
                    onSelect = {
                        onViewEvent(DSWantedInputDemoViewEvent.OnSelectType(WantedInputVariant.valueOf(it)))
                    }
                )
            },
            size = {
                WantedSelect(
                    value = "size : ${viewState.size.name}",
                    selectedValue = viewState.type.name,
                    selectValueList = WantedInputSize.entries.map { it.name },
                    onSelect = {
                        onViewEvent(DSWantedInputDemoViewEvent.OnSelectSize(WantedInputSize.valueOf(it)))
                    }
                )
            },
            checkBoxState = {
                WantedSelect(
                    value = "checkBoxState : ${viewState.checkBoxState.name}",
                    selectedValue = viewState.checkBoxState.name,
                    selectValueList = CheckBoxState.entries.map { it.name },
                    onSelect = {
                        onViewEvent(DSWantedInputDemoViewEvent.OnSelectCheckBoxState(CheckBoxState.valueOf(it)))
                    }
                )
            },
            bold = {
                DSWantedOptionSwitchCell(
                    text = "bold : ${viewState.bold}",
                    checkState = viewState.bold,
                    onCheckChanged = {
                        onViewEvent(DSWantedInputDemoViewEvent.OnChangeBold(it))
                    }
                )
            },
            enabled = {
                DSWantedOptionSwitchCell(
                    text = "enabled : ${viewState.enabled}",
                    checkState = viewState.enabled,
                    onCheckChanged = {
                        onViewEvent(DSWantedInputDemoViewEvent.OnChangeEnabled(it))
                    }
                )
            },
            tight = {
                DSWantedOptionSwitchCell(
                    text = "tight : ${viewState.tight}",
                    checkState = viewState.tight,
                    onCheckChanged = {
                        onViewEvent(DSWantedInputDemoViewEvent.OnChangeTight(it))
                    }
                )
            }
        )
    }
}


@Composable
private fun DSWantedInputDemoScreenContentLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    text: @Composable () -> Unit,
    type: @Composable () -> Unit,
    size: @Composable () -> Unit,
    checkBoxState: @Composable () -> Unit,
    bold: @Composable () -> Unit,
    enabled: @Composable () -> Unit,
    tight: @Composable () -> Unit
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

            text()
            type()
            size()
            checkBoxState()
            bold()
            enabled()
            tight()
        }
    }
}


@DevicePreviews
@Composable
private fun DSWantedInputDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedInputDemoScreenContent(
            viewState = DSWantedInputDemoViewState(),
            onViewEvent = { }
        )
    }
}