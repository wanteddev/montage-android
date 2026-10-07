package com.wanted.android.montage.sample.input.formcontrol

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.R
import com.wanted.android.montage.sample.getStateList
import com.wanted.android.montage.sample.input.formcontrol.DSWantedFormControlDemoScreenContract.DSWantedFormControlDemoEvent
import com.wanted.android.montage.sample.input.formcontrol.DSWantedFormControlDemoScreenContract.DSWantedFormControlDemoEvent.SetAccessory
import com.wanted.android.montage.sample.input.formcontrol.DSWantedFormControlDemoScreenContract.DSWantedFormControlDemoEvent.SetDescription
import com.wanted.android.montage.sample.input.formcontrol.DSWantedFormControlDemoScreenContract.DSWantedFormControlDemoEvent.SetEnabled
import com.wanted.android.montage.sample.input.formcontrol.DSWantedFormControlDemoScreenContract.DSWantedFormControlDemoEvent.SetLabel
import com.wanted.android.montage.sample.input.formcontrol.DSWantedFormControlDemoScreenContract.DSWantedFormControlDemoEvent.SetRequired
import com.wanted.android.montage.sample.input.formcontrol.DSWantedFormControlDemoScreenContract.DSWantedFormControlDemoEvent.SetTextFieldValue
import com.wanted.android.montage.sample.input.formcontrol.DSWantedFormControlDemoScreenContract.DSWantedFormControlDemoEvent.ShowAll
import com.wanted.android.montage.sample.input.formcontrol.DSWantedFormControlDemoScreenContract.DSWantedFormControlDemoEvent.ShowCode
import com.wanted.android.montage.sample.input.formcontrol.DSWantedFormControlDemoScreenContract.DSWantedFormControlDemoSideEffect
import com.wanted.android.montage.sample.input.formcontrol.DSWantedFormControlDemoScreenContract.DSWantedFormControlDemoViewEvent
import com.wanted.android.montage.sample.input.formcontrol.DSWantedFormControlDemoScreenContract.DSWantedFormControlDemoViewState
import com.wanted.android.montage.sample.toMap
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.formcontrol.WantedFormControl
import com.wanted.android.wanted.design.input.formcontrol.WantedFormControlDefaults
import com.wanted.android.wanted.design.input.textinput.textarea.WantedTextArea
import com.wanted.android.wanted.design.input.textinput.textfield.WantedTextField
import com.wanted.android.wanted.design.input.textinput.view.WantedTextAreaCharacterCount
import com.wanted.android.wanted.design.navigations.topbar.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopup
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.WantedTextStyle

private const val MAX_WORD_COUNT = 100

/** TextArea 예시의 최소 줄 수입니다. */
private const val TEXT_AREA_MIN_LINES = 3

@Composable
fun DSWantedFormControlDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedFormControlDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    val clipboardManager = LocalClipboardManager.current

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedFormControlDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedFormControlDemoScreenImpl(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            is DSWantedFormControlDemoViewEvent.OnClickBack -> onClickBack()

            is DSWantedFormControlDemoViewEvent.OnClickShowAll -> {
                viewModel.setEvent(ShowAll(true))
            }

            is DSWantedFormControlDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(ShowCode(true))
            }

            is DSWantedFormControlDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedFormControlDemoEvent.CopyCode)
            }

            is DSWantedFormControlDemoViewEvent.OnTextFieldValueChanged -> {
                viewModel.setEvent(SetTextFieldValue(viewEvent.text))
            }

            is DSWantedFormControlDemoViewEvent.OnChangeLabel -> {
                viewModel.setEvent(SetLabel(viewEvent.label))
            }

            is DSWantedFormControlDemoViewEvent.OnChangeRequired -> {
                viewModel.setEvent(SetRequired(viewEvent.required))
            }

            is DSWantedFormControlDemoViewEvent.OnChangeDescription -> {
                viewModel.setEvent(SetDescription(viewEvent.description))
            }

            is DSWantedFormControlDemoViewEvent.OnChangeAccessory -> {
                viewModel.setEvent(SetAccessory(viewEvent.accessory))
            }

            is DSWantedFormControlDemoViewEvent.OnChangeEnabled -> {
                viewModel.setEvent(SetEnabled(viewEvent.enabled))
            }

            is DSWantedFormControlDemoViewEvent.OnChangeSize -> {
                viewModel.setEvent(DSWantedFormControlDemoEvent.SetSize(viewEvent.size))
            }

            is DSWantedFormControlDemoViewEvent.OnChangeStatus -> {
                viewModel.setEvent(DSWantedFormControlDemoEvent.SetStatus(viewEvent.status))
            }

            is DSWantedFormControlDemoViewEvent.OnChangeLabelPlacement -> {
                viewModel.setEvent(
                    DSWantedFormControlDemoEvent.SetLabelPlacement(viewEvent.labelPlacement)
                )
            }
        }
    }

    if (viewState.isShowCode) {
        WantedPopup(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedFormControlDemoEvent.CopyCode)
                viewModel.setEvent(ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(ShowCode(false))
            },
            content = {
                Text(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    text = viewState.code
                )
            }
        )
    }

    if (viewState.isShowAll) {
        WantedPopup(
            positive = "확인",
            onClickPositive = {
                viewModel.setEvent(ShowAll(false))
            },
            onDismissRequest = {
                viewModel.setEvent(ShowAll(false))
            },
            content = {
                DSWantedAllFormControl(viewState)
            }
        )
    }
}

@Composable
private fun DSWantedFormControlDemoScreenImpl(
    modifier: Modifier = Modifier,
    viewState: DSWantedFormControlDemoViewState,
    onViewEvent: (DSWantedFormControlDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedFormControl") {
                onViewEvent(DSWantedFormControlDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedFormControlDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                sub = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "모든 옵션 보기",
                        variant = ButtonVariant.OUTLINED,
                        onClick = {
                            onViewEvent(DSWantedFormControlDemoViewEvent.OnClickShowAll)
                        }
                    )
                },
            )
        }
    ) { innerPadding ->
        DSWantedFormControlDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    DSWantedFormControlSample(
                        viewState = viewState,
                        onValueChange = { value ->
                            onViewEvent(DSWantedFormControlDemoViewEvent.OnTextFieldValueChanged(value))
                        }
                    )

                    // input 높이가 커질 때(TextArea) 라벨 정렬을 함께 확인할 수 있도록 나란히 노출한다.
                    DSWantedFormControlTextAreaSample(viewState = viewState)

                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedFormControlDemoViewEvent.OnClickCopyCode)
                        }
                    )
                }
            },
            size = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    WantedFormControlDefaults.Size.entries.forEach { sizeOption ->
                        WantedButton(
                            modifier = Modifier.weight(1f),
                            text = "size: ${sizeOption.name}",
                            variant = if (viewState.size == sizeOption) ButtonVariant.SOLID else ButtonVariant.OUTLINED,
                            onClick = {
                                onViewEvent(DSWantedFormControlDemoViewEvent.OnChangeSize(sizeOption))
                            }
                        )
                    }
                }
            },
            status = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    WantedFormControlDefaults.Status.entries.forEach { statusOption ->
                        WantedButton(
                            modifier = Modifier.weight(1f),
                            text = statusOption.name,
                            variant = if (viewState.status == statusOption) ButtonVariant.SOLID else ButtonVariant.OUTLINED,
                            onClick = {
                                onViewEvent(DSWantedFormControlDemoViewEvent.OnChangeStatus(statusOption))
                            }
                        )
                    }
                }
            },
            labelPlacement = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    WantedFormControlDefaults.LabelPlacement.entries.forEach { placementOption ->
                        WantedButton(
                            modifier = Modifier.weight(1f),
                            text = placementOption.name,
                            variant = if (viewState.labelPlacement == placementOption) ButtonVariant.SOLID else ButtonVariant.OUTLINED,
                            onClick = {
                                onViewEvent(
                                    DSWantedFormControlDemoViewEvent.OnChangeLabelPlacement(placementOption)
                                )
                            }
                        )
                    }
                }
            },
            label = {
                WantedTextField(
                    text = viewState.labelText,
                    title = "label",
                    placeholder = "라벨 텍스트를 입력해 주세요.",
                    onValueChange = {
                        onViewEvent(DSWantedFormControlDemoViewEvent.OnChangeLabel(it))
                    }
                )
            },
            required = {
                DSWantedOptionSwitchCell(
                    text = "required : ${viewState.required}",
                    checkState = viewState.required,
                    onCheckChanged = {
                        onViewEvent(DSWantedFormControlDemoViewEvent.OnChangeRequired(it))
                    }
                )
            },
            description = {
                DSWantedOptionSwitchCell(
                    text = "description : ${if (viewState.description) "String" else null}",
                    checkState = viewState.description,
                    onCheckChanged = {
                        onViewEvent(DSWantedFormControlDemoViewEvent.OnChangeDescription(it))
                    }
                )
            },
            accessory = {
                DSWantedOptionSwitchCell(
                    text = "accessory : ${if (viewState.accessory) "CharacterCount" else null}",
                    checkState = viewState.accessory,
                    onCheckChanged = {
                        onViewEvent(DSWantedFormControlDemoViewEvent.OnChangeAccessory(it))
                    }
                )
            },
            enabled = {
                DSWantedOptionSwitchCell(
                    text = "enabled : ${viewState.enabled}",
                    checkState = viewState.enabled,
                    onCheckChanged = {
                        onViewEvent(DSWantedFormControlDemoViewEvent.OnChangeEnabled(it))
                    }
                )
            }
        )
    }
}

@Composable
private fun DSWantedFormControlSample(
    viewState: DSWantedFormControlDemoViewState,
    onValueChange: (String) -> Unit
) {
    WantedFormControl(
        label = viewState.labelText,
        required = viewState.required,
        description = if (viewState.description) "도움말 메시지입니다." else null,
        accessory = if (viewState.accessory) {
            {
                WantedTextAreaCharacterCount(
                    current = viewState.text.length,
                    maxWordCount = MAX_WORD_COUNT,
                    error = viewState.text.length > MAX_WORD_COUNT,
                    enable = viewState.enabled
                )
            }
        } else null,
        size = viewState.size,
        status = viewState.status,
        labelPlacement = viewState.labelPlacement,
        enabled = viewState.enabled,
    ) {
        WantedTextField(
            text = viewState.text,
            placeholder = "텍스트를 입력해 주세요.",
            enabled = viewState.enabled,
            onValueChange = onValueChange
        )
    }
}

/**
 * input 슬롯에 TextArea 를 넣은 예시입니다.
 *
 * TextArea 는 높이가 커지므로, LabelPlacement.Leading 에서 라벨이 상단 영역 기준으로
 * 정렬되는지를 TextField 예시와 나란히 비교할 수 있다.
 */
@Composable
private fun DSWantedFormControlTextAreaSample(
    viewState: DSWantedFormControlDemoViewState
) {
    var value by remember { mutableStateOf(TextFieldValue()) }

    WantedFormControl(
        label = viewState.labelText,
        required = viewState.required,
        description = if (viewState.description) "도움말 메시지입니다." else null,
        accessory = if (viewState.accessory) {
            {
                WantedTextAreaCharacterCount(
                    current = value.text.length,
                    maxWordCount = MAX_WORD_COUNT,
                    error = value.text.length > MAX_WORD_COUNT,
                    enable = viewState.enabled
                )
            }
        } else null,
        size = viewState.size,
        status = viewState.status,
        labelPlacement = viewState.labelPlacement,
        enabled = viewState.enabled,
    ) {
        WantedTextArea(
            value = value,
            placeholder = "텍스트를 입력해 주세요.",
            enabled = viewState.enabled,
            minLines = TEXT_AREA_MIN_LINES,
            onValueChange = { value = it }
        )
    }
}

@Composable
private fun DSWantedFormControlDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    size: @Composable () -> Unit,
    status: @Composable () -> Unit,
    labelPlacement: @Composable () -> Unit,
    label: @Composable () -> Unit,
    required: @Composable () -> Unit,
    description: @Composable () -> Unit,
    accessory: @Composable () -> Unit,
    enabled: @Composable () -> Unit
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

            size()

            status()

            labelPlacement()

            label()

            required()

            description()

            accessory()

            enabled()
        }
    }
}

@Composable
private fun DSWantedAllFormControl(
    viewState: DSWantedFormControlDemoViewState
) {
    val map = viewState.toMap()
    val list = getStateList(
        map = map,
        includeKeyList = listOf(
            "required", "description", "accessory", "enabled"
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(list) { values ->
            val text = values["text"].toString()
            val enabled = values["enabled"] as Boolean

            WantedFormControl(
                label = viewState.labelText,
                required = values["required"] as Boolean,
                description = if (values["description"] as Boolean) "도움말 메시지입니다." else null,
                accessory = if (values["accessory"] as Boolean) {
                    {
                        WantedTextAreaCharacterCount(
                            current = text.length,
                            maxWordCount = MAX_WORD_COUNT,
                            error = text.length > MAX_WORD_COUNT,
                            enable = enabled
                        )
                    }
                } else null,
                size = viewState.size,
                status = viewState.status,
                labelPlacement = viewState.labelPlacement,
                enabled = enabled,
            ) {
                WantedTextField(
                    text = text,
                    placeholder = "텍스트를 입력해 주세요.",
                    enabled = enabled,
                    onValueChange = { }
                )
            }
        }
    }
}


@DevicePreviews
@Composable
private fun DSWantedFormControlDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedFormControlDemoScreenImpl(
            viewState = DSWantedFormControlDemoViewState(),
            onViewEvent = { }
        )
    }
}
