package com.wanted.android.montage.sample.input.textinput.textfield

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.montage.sample.DSWantedOptionPicker
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.R
import com.wanted.android.montage.sample.input.textinput.textfield.DSWantedTextFieldDemoScreenContract.DSWantedTextFieldDemoEvent
import com.wanted.android.montage.sample.input.textinput.textfield.DSWantedTextFieldDemoScreenContract.DSWantedTextFieldDemoSideEffect
import com.wanted.android.montage.sample.input.textinput.textfield.DSWantedTextFieldDemoScreenContract.DSWantedTextFieldDemoViewEvent
import com.wanted.android.montage.sample.input.textinput.textfield.DSWantedTextFieldDemoScreenContract.DSWantedTextFieldDemoViewState
import com.wanted.android.montage.sample.input.textinput.textfield.DSWantedTextFieldDemoScreenContract.TrailingContentType
import com.wanted.android.montage.sample.input.textinput.textfield.before.DSWantedTextFieldSampleModal
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonNormal
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonNormalSize
import com.wanted.android.wanted.design.input.formcontrol.WantedFormControl
import com.wanted.android.wanted.design.input.formcontrol.WantedFormControlDefaults
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.input.textinput.textfield.WantedTextField
import com.wanted.android.wanted.design.input.textinput.textfield.WantedTextFieldDefaults.Size
import com.wanted.android.wanted.design.input.textinput.textfield.WantedTextFieldDefaults.Status
import com.wanted.android.wanted.design.input.textinput.view.WantedTextAreaCharacterCount
import com.wanted.android.wanted.design.navigations.topbar.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopup
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedTextFieldDemoScreen(
    modifier: Modifier,
    viewModel: DSWantedTextFieldDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val clipboardManager = LocalClipboardManager.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedTextFieldDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }

            DSWantedTextFieldDemoSideEffect.Focus -> {
                focusRequester.requestFocus()
            }

            DSWantedTextFieldDemoSideEffect.ClearFocus -> {
                focusManager.clearFocus()
            }
        }
    }

    DSWantedTextFieldDemoScreenImpl(
        modifier = modifier,
        viewState = viewState,
        focusRequester = focusRequester
    ) { viewEvent ->
        when (viewEvent) {
            is DSWantedTextFieldDemoViewEvent.OnClickBack -> onClickBack()
            is DSWantedTextFieldDemoViewEvent.OnChangeEnabled -> {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.SetEnabled(viewEvent.enabled))
            }

            is DSWantedTextFieldDemoViewEvent.OnChangeEnabledLeadingIcon -> {
                viewModel.setEvent(
                    DSWantedTextFieldDemoEvent.SetEnabledLeadingIcon(viewEvent.enabledLeadingIcon)
                )
            }

            is DSWantedTextFieldDemoViewEvent.OnChangeEnabledOverflowText -> {
                viewModel.setEvent(
                    DSWantedTextFieldDemoEvent.SetEnabledOverflowText(viewEvent.enabledOverflowText)
                )
            }

            is DSWantedTextFieldDemoViewEvent.OnChangeUseFormControl -> {
                viewModel.setEvent(
                    DSWantedTextFieldDemoEvent.SetUseFormControl(viewEvent.useFormControl)
                )
            }

            is DSWantedTextFieldDemoViewEvent.OnChangeFormControlLabel -> {
                viewModel.setEvent(
                    DSWantedTextFieldDemoEvent.SetFormControlLabel(viewEvent.label)
                )
            }

            is DSWantedTextFieldDemoViewEvent.OnChangeFormControlRequired -> {
                viewModel.setEvent(
                    DSWantedTextFieldDemoEvent.SetFormControlRequired(viewEvent.required)
                )
            }

            is DSWantedTextFieldDemoViewEvent.OnChangeFormControlDescription -> {
                viewModel.setEvent(
                    DSWantedTextFieldDemoEvent.SetFormControlDescription(viewEvent.description)
                )
            }

            is DSWantedTextFieldDemoViewEvent.OnChangeFormControlAccessory -> {
                viewModel.setEvent(
                    DSWantedTextFieldDemoEvent.SetFormControlAccessory(viewEvent.accessory)
                )
            }

            is DSWantedTextFieldDemoViewEvent.OnChangeFormControlSize -> {
                viewModel.setEvent(
                    DSWantedTextFieldDemoEvent.SetFormControlSize(viewEvent.size)
                )
            }

            is DSWantedTextFieldDemoViewEvent.OnChangeFormControlLabelPlacement -> {
                viewModel.setEvent(
                    DSWantedTextFieldDemoEvent.SetFormControlLabelPlacement(
                        viewEvent.labelPlacement
                    )
                )
            }

            is DSWantedTextFieldDemoViewEvent.OnChangeEnabledTrailingIcon -> {
                viewModel.setEvent(
                    DSWantedTextFieldDemoEvent.SetEnabledTrailingIcon(viewEvent.enabledTrailingIcon)
                )
            }

            is DSWantedTextFieldDemoViewEvent.OnChangeTrailingButton -> {
                viewModel.setEvent(
                    DSWantedTextFieldDemoEvent.SetTrailingButton(viewEvent.enabledTrailingButton)
                )

            }

            is DSWantedTextFieldDemoViewEvent.OnChangeTrailingContent -> {
                viewModel.setEvent(
                    DSWantedTextFieldDemoEvent.SetTrailingContent(viewEvent.trailingContent)
                )
            }

            is DSWantedTextFieldDemoViewEvent.OnChangeSize -> {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.SetSize(viewEvent.size))
            }

            is DSWantedTextFieldDemoViewEvent.OnChangeStatus -> {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.SetStatus(viewEvent.status))
            }

            is DSWantedTextFieldDemoViewEvent.OnClickShowAll -> {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowAll(true))
            }

            is DSWantedTextFieldDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowCode(true))
            }

            is DSWantedTextFieldDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.CopyCode)
            }

            is DSWantedTextFieldDemoViewEvent.OnTextFieldValueChanged -> {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.SetTextFieldValue(viewEvent.text))
            }

            is DSWantedTextFieldDemoViewEvent.OnShowMaxLinePicker -> {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowMaxLinePicker(true))
            }

            is DSWantedTextFieldDemoViewEvent.OnShowMaxWordCountPicker -> {
                viewModel.setEvent(
                    DSWantedTextFieldDemoEvent.ShowMaxWordCountPicker(true)
                )
            }

            is DSWantedTextFieldDemoViewEvent.OnShowMinLinesPicker -> {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowMinLinesPicker(true))
            }

            is DSWantedTextFieldDemoViewEvent.OnClickShowSample -> {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowSample(true))
            }

            DSWantedTextFieldDemoViewEvent.OnClickFocus -> {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.Focus)
            }

            DSWantedTextFieldDemoViewEvent.OnClickClearFocus -> {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ClearFocus)
            }
        }
    }

    if (viewState.isShowCode) {
        WantedPopup(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowCode(false))
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
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowAll(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowAll(false))
            },
            content = {
                DSWantedAllTextField(viewState, focusRequester)
            }
        )
    }

    if (viewState.isShowSample) {
        DSWantedTextFieldSampleModal {
            viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowSample(false))
        }
    }

    if (viewState.isShowMaxLinePicker) {
        DSWantedOptionPicker(
            title = "maxLines",
            selectedValue = viewState.maxLines,
            start = viewState.minLines,
            end = 2000,
            onSelect = {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.SetMaxLines(it))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowMaxLinePicker(false))
            }
        )
    }

    if (viewState.isShowMinLinesPicker) {
        DSWantedOptionPicker(
            title = "minLines",
            selectedValue = viewState.minLines,
            start = 1,
            end = viewState.maxLines,
            onSelect = {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.SetMinLines(it))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowMinLinesPicker(false))
            }
        )
    }

    if (viewState.isShowMaxWordCountPicker) {
        DSWantedOptionPicker(
            title = "maxWordCount",
            selectedValue = viewState.maxWordCount,
            start = 1,
            end = 2000,
            onSelect = {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.SetMaxWordCount(it))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowMaxWordCountPicker(false))
            }
        )
    }
}

@Composable
private fun DSWantedTextFieldDemoScreenImpl(
    modifier: Modifier = Modifier,
    viewState: DSWantedTextFieldDemoViewState,
    focusRequester: FocusRequester,
    onViewEvent: (DSWantedTextFieldDemoViewEvent) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedTextField") {
                onViewEvent(DSWantedTextFieldDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedTextFieldDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                sub = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        WantedButton(
                            modifier = Modifier.weight(1f),
                            text = "샘플 보기",
                            variant = ButtonVariant.OUTLINED,
                            onClick = {
                                onViewEvent(DSWantedTextFieldDemoViewEvent.OnClickShowSample)
                            }
                        )

                        WantedButton(
                            modifier = Modifier.weight(1f),
                            text = "모든 옵션 보기",
                            variant = ButtonVariant.OUTLINED,
                            onClick = {
                                onViewEvent(DSWantedTextFieldDemoViewEvent.OnClickShowAll)
                            }
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        DSWantedTextFieldDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    DSWantedTextFieldSample(
                        viewState = viewState,
                        focusRequester = focusRequester,
                        onValueChange = { value ->
                            onViewEvent(DSWantedTextFieldDemoViewEvent.OnTextFieldValueChanged(value))
                        }
                    )

                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedTextFieldDemoViewEvent.OnClickCopyCode)
                        }
                    )
                }

            },
            leadingIcon = {
                DSWantedOptionSwitchCell(
                    text = "leadingIcon : ${if (viewState.enabledLeadingIcon) "icon" else null}",
                    checkState = viewState.enabledLeadingIcon,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeEnabledLeadingIcon(it)
                        )
                    }
                )
            },
            trailingIcon = {
                DSWantedOptionSwitchCell(
                    text = "trailingIcon : ${if (viewState.enabledTrailingIcon) "icon" else null}",
                    checkState = viewState.enabledTrailingIcon,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeEnabledTrailingIcon(it)
                        )
                    }
                )
            },
            trailingButton = {
                DSWantedOptionSwitchCell(
                    text = "trailingButton : ${if (viewState.trailingButton) "String" else null}",
                    checkState = viewState.trailingButton,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeTrailingButton(it)
                        )
                    }
                )
            },
            trailingContent = {
                WantedSelect(
                    value = "trailingContent : ${viewState.selectedTrailingContentType.name}",
                    selectedValue = viewState.selectedTrailingContentType.name,
                    selectValueList = viewState.trailingContentType.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeTrailingContent(
                                TrailingContentType.valueOf(it)
                            )
                        )
                    }
                )
            },
            size = {
                WantedSelect(
                    value = "size : ${viewState.selectedSize.name}",
                    selectedValue = viewState.selectedSize.name,
                    selectValueList = viewState.size.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeSize(Size.valueOf(it))
                        )
                    }
                )
            },
            status = {
                WantedSelect(
                    value = "status : ${viewState.selectedStatus.name}",
                    selectedValue = viewState.selectedStatus.name,
                    selectValueList = viewState.status.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeStatus(Status.valueOf(it))
                        )
                    }
                )
            },
            enabled = {
                DSWantedOptionSwitchCell(
                    text = "enabled : ${viewState.enabled}",
                    checkState = viewState.enabled,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeEnabled(it)
                        )
                    }
                )
            },
            useFormControl = {
                DSWantedOptionSwitchCell(
                    text = "WantedFormControl 사용 : ${viewState.useFormControl}",
                    checkState = viewState.useFormControl,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeUseFormControl(it)
                        )
                    }
                )
            },
            isUseFormControl = viewState.useFormControl,
            formControlLabel = {
                DSWantedOptionSwitchCell(
                    text = "formControl.label : ${if (viewState.formControlLabel) "Label" else null}",
                    checkState = viewState.formControlLabel,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeFormControlLabel(it)
                        )
                    }
                )
            },
            formControlRequired = {
                DSWantedOptionSwitchCell(
                    text = "formControl.required : ${viewState.formControlRequired}",
                    checkState = viewState.formControlRequired,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeFormControlRequired(it)
                        )
                    }
                )
            },
            formControlDescription = {
                DSWantedOptionSwitchCell(
                    text = "formControl.description : ${if (viewState.formControlDescription) "String" else null}",
                    checkState = viewState.formControlDescription,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeFormControlDescription(it)
                        )
                    }
                )
            },
            formControlAccessory = {
                DSWantedOptionSwitchCell(
                    text = "formControl.accessory : ${if (viewState.formControlAccessory) "CharacterCount" else null}",
                    checkState = viewState.formControlAccessory,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeFormControlAccessory(it)
                        )
                    }
                )
            },
            formControlSize = {
                WantedSelect(
                    value = "formControl.size : ${viewState.selectedFormControlSize.name}",
                    selectedValue = viewState.selectedFormControlSize.name,
                    selectValueList = viewState.formControlSize.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeFormControlSize(
                                WantedFormControlDefaults.Size.valueOf(it)
                            )
                        )
                    }
                )
            },
            formControlLabelPlacement = {
                WantedSelect(
                    value = "formControl.labelPlacement : ${viewState.selectedFormControlLabelPlacement.name}",
                    selectedValue = viewState.selectedFormControlLabelPlacement.name,
                    selectValueList = viewState.formControlLabelPlacement.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeFormControlLabelPlacement(
                                WantedFormControlDefaults.LabelPlacement.valueOf(it)
                            )
                        )
                    }
                )
            },
            enabledOverflowText = {
                DSWantedOptionSwitchCell(
                    text = "enabledOverflowText : ${viewState.enabledOverflowText}",
                    checkState = viewState.enabledOverflowText,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeEnabledOverflowText(it)
                        )
                    }
                )
            },
            maxLines = {
                WantedSelect(
                    value = "maxLines : ${viewState.maxLines}",
                    focused = viewState.isShowMaxLinePicker,
                    onClick = {
                        onViewEvent(DSWantedTextFieldDemoViewEvent.OnShowMaxLinePicker)
                    }
                )
            },
            minLines = {
                WantedSelect(
                    value = "minLines : ${viewState.minLines}",
                    focused = viewState.isShowMinLinesPicker,
                    onClick = {
                        onViewEvent(DSWantedTextFieldDemoViewEvent.OnShowMinLinesPicker)
                    }
                )
            },
            maxWordCount = {
                WantedSelect(
                    value = "maxWordCount : ${viewState.maxWordCount}",
                    focused = viewState.isShowMaxWordCountPicker,
                    onClick = {
                        onViewEvent(DSWantedTextFieldDemoViewEvent.OnShowMaxWordCountPicker)
                    }
                )
            },
            focus = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    WantedButton(
                        modifier = Modifier.weight(1f),
                        text = "Focus",
                        onClick = {
                            onViewEvent(DSWantedTextFieldDemoViewEvent.OnClickFocus)
                        }
                    )

                    WantedButton(
                        modifier = Modifier.weight(1f),
                        text = "Focus Out",
                        variant = ButtonVariant.OUTLINED,
                        onClick = {
                            onViewEvent(DSWantedTextFieldDemoViewEvent.OnClickClearFocus)
                        }
                    )
                }
            }
        )
    }
}

@Composable
private fun DSWantedTextFieldDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    leadingIcon: @Composable () -> Unit,
    trailingIcon: @Composable () -> Unit,
    trailingButton: @Composable () -> Unit,
    trailingContent: @Composable () -> Unit,
    size: @Composable () -> Unit,
    status: @Composable () -> Unit,
    enabled: @Composable () -> Unit,
    useFormControl: @Composable () -> Unit,
    isUseFormControl: Boolean,
    formControlLabel: @Composable () -> Unit,
    formControlRequired: @Composable () -> Unit,
    formControlDescription: @Composable () -> Unit,
    formControlAccessory: @Composable () -> Unit,
    formControlSize: @Composable () -> Unit,
    formControlLabelPlacement: @Composable () -> Unit,
    enabledOverflowText: @Composable () -> Unit,
    maxLines: @Composable () -> Unit,
    minLines: @Composable () -> Unit,
    maxWordCount: @Composable () -> Unit,
    focus: @Composable () -> Unit,
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

            leadingIcon()

            trailingIcon()

            trailingButton()

            trailingContent()

            size()

            status()

            enabled()

            useFormControl()

            // FormControl 옵션은 래핑을 사용할 때만 조정 가능하다.
            if (isUseFormControl) {
                formControlLabel()

                formControlRequired()

                formControlDescription()

                formControlAccessory()

                formControlSize()

                formControlLabelPlacement()
            }

            enabledOverflowText()

            maxLines()

            minLines()

            maxWordCount()

            focus()
        }
    }
}


/**
 * 옵션 상태를 반영한 미리보기 한 벌.
 *
 * useFormControl 이 켜져 있으면 라벨·필수 표시·메시지·글자수를 담당하는 WantedFormControl 의
 * input 슬롯에 WantedTextField 를 조합한다. 꺼져 있으면 입력 필드 본체만 렌더링한다.
 */
@Composable
private fun DSWantedTextFieldSample(
    viewState: DSWantedTextFieldDemoViewState,
    focusRequester: FocusRequester,
    onValueChange: (String) -> Unit
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(text = viewState.text)) }
    val interactionSource = remember { MutableInteractionSource() }

    val textField: @Composable () -> Unit = {
        DSWantedTextFieldPreviewField(
            viewState = viewState,
            value = textFieldValue,
            status = viewState.selectedStatus,
            interactionSource = interactionSource,
            focusRequester = focusRequester,
            onValueChange = { newValue ->
                textFieldValue = newValue
                onValueChange(newValue.text)
            }
        )
    }

    if (viewState.useFormControl) {
        WantedFormControl(
            label = if (viewState.formControlLabel) "Label" else "",
            required = viewState.formControlRequired,
            description = if (viewState.formControlDescription) {
                "도움말 메시지입니다."
            } else {
                null
            },
            accessory = if (viewState.formControlAccessory) {
                {
                    WantedTextAreaCharacterCount(
                        current = textFieldValue.text.length,
                        maxWordCount = viewState.maxWordCount,
                        error = textFieldValue.text.length > viewState.maxWordCount,
                        enable = viewState.enabled
                    )
                }
            } else null,
            size = viewState.selectedFormControlSize,
            status = viewState.selectedStatus.toFormControlStatus(),
            labelPlacement = viewState.selectedFormControlLabelPlacement,
            enabled = viewState.enabled,
            input = textField
        )
    } else {
        textField()
    }
}

@Composable
private fun DSWantedTextFieldPreviewField(
    viewState: DSWantedTextFieldDemoViewState,
    value: TextFieldValue,
    status: Status,
    interactionSource: MutableInteractionSource,
    focusRequester: FocusRequester,
    onValueChange: (TextFieldValue) -> Unit
) {
    WantedTextField(
        value = value,
        placeholder = "텍스트를 입력해 주세요.",
        error = status == Status.Negative,
        enabled = viewState.enabled,
        trailingButtonEnabled = true,
        complete = status == Status.Positive,
        maxLines = viewState.maxLines,
        minLines = viewState.minLines,
        maxWordCount = viewState.maxWordCount,
        enabledOverflowText = viewState.enabledOverflowText,
        interactionSource = interactionSource,
        keyboardOptions = KeyboardOptions.Default,
        keyboardActions = KeyboardActions.Default,
        size = viewState.selectedSize,
        trailingButton = if (viewState.trailingButton) "확인" else null,
        leadingIcon = if (viewState.enabledLeadingIcon) {
            {
                Icon(
                    modifier = Modifier.size(viewState.selectedSize.demoIconSize),
                    painter = painterResource(id = R.drawable.icon_normal_circle_check_fill),
                    tint = colorResource(R.color.foreground_brand_primary),
                    contentDescription = ""
                )
            }
        } else null,
        trailingIcon = if (viewState.enabledTrailingIcon) {
            {
                Icon(
                    modifier = Modifier.size(viewState.selectedSize.demoIconSize),
                    painter = painterResource(id = R.drawable.icon_normal_circle_check_fill),
                    tint = colorResource(R.color.foreground_brand_primary),
                    contentDescription = ""
                )
            }
        } else null,
        trailingContent = trailingContentSlot(
            type = viewState.selectedTrailingContentType,
            size = viewState.selectedSize
        ),
        focusRequester = focusRequester,
        onValueChange = onValueChange
    )
}

/**
 * trailingContent 슬롯 예시입니다.
 *
 * 슬롯은 임의 Composable 을 받으므로 아이콘 외에 텍스트·아이콘 버튼도 넣을 수 있다는 것을 보여 준다.
 */
private fun trailingContentSlot(
    type: TrailingContentType,
    size: Size
): (@Composable () -> Unit)? = when (type) {
    TrailingContentType.None -> null

    TrailingContentType.Icon -> {
        {
            Icon(
                modifier = Modifier.size(size.demoIconSize),
                painter = painterResource(id = R.drawable.icon_normal_circle_check_fill),
                tint = colorResource(R.color.foreground_brand_primary),
                contentDescription = ""
            )
        }
    }

    TrailingContentType.Text -> {
        {
            Text(
                text = "0:00",
                style = DesignSystemTheme.typography.body2Regular,
                color = DesignSystemTheme.colors.foregroundNeutralPrimary
            )
        }
    }

    TrailingContentType.IconButton -> {
        {
            WantedIconButtonNormal(
                icon = R.drawable.icon_normal_close,
                size = size.demoIconButtonSize,
                onClick = { }
            )
        }
    }
}

/**
 * 데모에서 아이콘 슬롯에 넣는 아이콘 크기입니다. (영역 크기가 아니라 아이콘 자체 크기)
 *
 * 미리보기와 "코드 보기/복사" 결과가 어긋나지 않도록 ViewModel 의 코드 생성도 이 값을 사용한다.
 */
internal val Size.demoIconSize: Dp
    get() = when (this) {
        Size.Large -> 20.dp
        Size.Medium -> 18.dp
    }

/** 데모 trailingContent 의 IconButton 크기입니다. 코드 생성도 이 값을 사용한다. */
internal val Size.demoIconButtonSize: WantedIconButtonNormalSize
    get() = when (this) {
        Size.Large -> WantedIconButtonNormalSize.Large
        Size.Medium -> WantedIconButtonNormalSize.Medium
    }

internal fun Status.toFormControlStatus(): WantedFormControlDefaults.Status = when (this) {
    Status.Normal -> WantedFormControlDefaults.Status.Normal
    Status.Positive -> WantedFormControlDefaults.Status.Positive
    Status.Negative -> WantedFormControlDefaults.Status.Negative
}

@Composable
private fun DSWantedAllTextField(
    viewState: DSWantedTextFieldDemoViewState,
    focusRequester: FocusRequester
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        viewState.trailingContentType.forEach { contentType ->
            viewState.status.forEach { status ->
                item {
                    val label = "$contentType + $status"
                    val itemValue = TextFieldValue(text = label)
                    val itemInteractionSource = remember { MutableInteractionSource() }

                    val textField: @Composable () -> Unit = {
                        DSWantedTextFieldPreviewField(
                            viewState = viewState.copy(selectedTrailingContentType = contentType),
                            value = itemValue,
                            status = status,
                            interactionSource = itemInteractionSource,
                            focusRequester = focusRequester,
                            onValueChange = { }
                        )
                    }

                    if (viewState.useFormControl) {
                        WantedFormControl(
                            label = if (viewState.formControlLabel) label else "",
                            required = viewState.formControlRequired,
                            description = if (viewState.formControlDescription) {
                                "도움말 메시지입니다."
                            } else {
                                null
                            },
                            accessory = if (viewState.formControlAccessory) {
                                {
                                    WantedTextAreaCharacterCount(
                                        current = itemValue.text.length,
                                        maxWordCount = viewState.maxWordCount,
                                        error = itemValue.text.length > viewState.maxWordCount,
                                        enable = viewState.enabled
                                    )
                                }
                            } else null,
                            size = viewState.selectedFormControlSize,
                            status = status.toFormControlStatus(),
                            labelPlacement = viewState.selectedFormControlLabelPlacement,
                            enabled = viewState.enabled,
                            input = textField
                        )
                    } else {
                        textField()
                    }
                }

            }
        }
    }
}

@DevicePreviews
@Composable
private fun DSWantedTextFieldDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedTextFieldDemoScreenImpl(
            viewState = DSWantedTextFieldDemoViewState(),
            focusRequester = remember { FocusRequester() },
            onViewEvent = { }
        )
    }
}