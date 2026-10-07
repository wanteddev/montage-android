package com.wanted.android.montage.sample.input.textinput.textfield

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.input.textinput.textfield.DSWantedTextFieldDemoScreenContract.DSWantedTextFieldDemoEvent
import com.wanted.android.montage.sample.input.textinput.textfield.DSWantedTextFieldDemoScreenContract.DSWantedTextFieldDemoSideEffect
import com.wanted.android.montage.sample.input.textinput.textfield.DSWantedTextFieldDemoScreenContract.DSWantedTextFieldDemoViewState
import com.wanted.android.montage.sample.input.textinput.textfield.DSWantedTextFieldDemoScreenContract.TrailingContentType
import com.wanted.android.wanted.design.input.formcontrol.WantedFormControlDefaults
import com.wanted.android.wanted.design.input.textinput.textfield.WantedTextFieldDefaults.Size
import com.wanted.android.wanted.design.input.textinput.textfield.WantedTextFieldDefaults.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedTextFieldDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedTextFieldDemoEvent, DSWantedTextFieldDemoViewState, DSWantedTextFieldDemoSideEffect>() {
    override fun setInitialState() = DSWantedTextFieldDemoViewState()

    override fun handleEvents(event: DSWantedTextFieldDemoEvent) {
        when (event) {
            is DSWantedTextFieldDemoEvent.InitState -> setState { event.viewState }
            is DSWantedTextFieldDemoEvent.CopyCode -> copyCode()
            is DSWantedTextFieldDemoEvent.SetEnabled -> {
                setState { copy(enabled = event.enabled) }
            }

            is DSWantedTextFieldDemoEvent.SetEnabledLeadingIcon -> {
                setState { copy(enabledLeadingIcon = event.enabledLeadingIcon) }
            }

            is DSWantedTextFieldDemoEvent.SetEnabledOverflowText -> {
                setState { copy(enabledOverflowText = event.enabledOverflowText) }
            }

            is DSWantedTextFieldDemoEvent.SetUseFormControl -> {
                setState { copy(useFormControl = event.useFormControl) }
            }

            is DSWantedTextFieldDemoEvent.SetFormControlLabel -> {
                setState { copy(formControlLabel = event.label) }
            }

            is DSWantedTextFieldDemoEvent.SetFormControlRequired -> {
                setState { copy(formControlRequired = event.required) }
            }

            is DSWantedTextFieldDemoEvent.SetFormControlDescription -> {
                setState { copy(formControlDescription = event.description) }
            }

            is DSWantedTextFieldDemoEvent.SetFormControlAccessory -> {
                setState { copy(formControlAccessory = event.accessory) }
            }

            is DSWantedTextFieldDemoEvent.SetFormControlSize -> {
                setState { copy(selectedFormControlSize = event.size) }
            }

            is DSWantedTextFieldDemoEvent.SetFormControlLabelPlacement -> {
                setState { copy(selectedFormControlLabelPlacement = event.labelPlacement) }
            }

            is DSWantedTextFieldDemoEvent.SetEnabledTrailingIcon -> {
                setState { copy(enabledTrailingIcon = event.enabledTrailingIcon) }
            }

            is DSWantedTextFieldDemoEvent.SetTrailingButton -> {
                setState { copy(trailingButton = event.enabledTrailingButton) }
            }

            is DSWantedTextFieldDemoEvent.SetTrailingContent -> {
                setState { copy(selectedTrailingContentType = event.trailingContent) }
            }

            is DSWantedTextFieldDemoEvent.SetSize -> {
                setState { copy(selectedSize = event.size) }
            }

            is DSWantedTextFieldDemoEvent.SetStatus -> {
                setState { copy(selectedStatus = event.status) }
            }

            is DSWantedTextFieldDemoEvent.ShowCode -> {
                setState {
                    copy(
                        code = getCode(),
                        isShowCode = event.isShowCode
                    )
                }
            }

            is DSWantedTextFieldDemoEvent.SetTextFieldValue -> {
                setState { copy(text = event.text) }
            }

            is DSWantedTextFieldDemoEvent.ShowAll -> {
                setState { copy(isShowAll = event.isShowAll) }
            }

            is DSWantedTextFieldDemoEvent.SetMaxLines -> {
                setState { copy(maxLines = event.maxLines) }
            }

            is DSWantedTextFieldDemoEvent.ShowMaxLinePicker -> {
                setState { copy(isShowMaxLinePicker = event.isShowMaxLinePicker) }
            }

            is DSWantedTextFieldDemoEvent.SetMaxWordCount -> {
                setState { copy(maxWordCount = event.maxWordCount) }
            }

            is DSWantedTextFieldDemoEvent.SetMinLines -> {
                setState { copy(minLines = event.minLines) }
            }

            is DSWantedTextFieldDemoEvent.ShowMaxWordCountPicker -> {
                setState { copy(isShowMaxWordCountPicker = event.isShowMaxWordCountPicker) }
            }

            is DSWantedTextFieldDemoEvent.ShowMinLinesPicker -> {
                setState { copy(isShowMinLinesPicker = event.isShowMinLinesPicker) }
            }

            is DSWantedTextFieldDemoEvent.ShowSample -> {
                setState { copy(isShowSample = event.isShowSample) }
            }

            DSWantedTextFieldDemoEvent.Focus -> {
                setEffect { DSWantedTextFieldDemoSideEffect.Focus }
            }

            DSWantedTextFieldDemoEvent.ClearFocus -> {
                setEffect { DSWantedTextFieldDemoSideEffect.ClearFocus }
            }
        }
    }

    private fun copyCode() {
        setEffect { DSWantedTextFieldDemoSideEffect.CopyCode(getCode()) }
    }

    private fun getCode(): String {
        val state = viewState.value
        val textFieldCode = getTextFieldCode(state)

        return if (state.useFormControl) {
            // FormControl 로 감쌀 때는 TextField 코드를 input 슬롯 깊이만큼 들여쓴다.
            getFormControlHeaderCode(state) +
                "\n" + textFieldCode.prependIndent(CODE_INDENT) + "\n}"
        } else {
            textFieldCode
        }
    }

    private fun getFormControlHeaderCode(
        state: DSWantedTextFieldDemoViewState
    ): String {
        val accessoryCode = if (state.formControlAccessory) {
            """
            accessory = {
                WantedTextAreaCharacterCount(
                    current = value.text.length,
                    maxWordCount = ${state.maxWordCount},
                    error = value.text.length > ${state.maxWordCount},
                    enable = ${state.enabled}
                )
            },
            """.trimIndent().prependIndent(CODE_INDENT)
        } else {
            "${CODE_INDENT}accessory = null, ${getDefaultString(true)}"
        }

        return """
        WantedFormControl(
            label = ${if (state.formControlLabel) "\"Label\"" else "\"\""}, ${
            getDefaultString(!state.formControlLabel)
        }
            required = ${state.formControlRequired}, ${
            getDefaultString(!state.formControlRequired)
        }
            description = ${
            if (state.formControlDescription) "\"도움말 메시지입니다.\"" else "null"
        }, ${getDefaultString(!state.formControlDescription)}
        """.trimIndent() +
            "\n" + accessoryCode + "\n" +
            """
            size = WantedFormControlDefaults.Size.${state.selectedFormControlSize}, ${
                getDefaultString(
                    state.selectedFormControlSize == WantedFormControlDefaults.Size.Large
                )
            }
            status = WantedFormControlDefaults.Status.${state.selectedStatus.toFormControlStatus()}, ${
                getDefaultString(state.selectedStatus == Status.Normal)
            }
            labelPlacement = WantedFormControlDefaults.LabelPlacement.${state.selectedFormControlLabelPlacement}, ${
                getDefaultString(
                    state.selectedFormControlLabelPlacement ==
                        WantedFormControlDefaults.LabelPlacement.Top
                )
            }
            enabled = ${state.enabled}, ${getDefaultString(state.enabled)}
            """.trimIndent().prependIndent(CODE_INDENT) +
            "\n) {"
    }

    private fun getTextFieldCode(state: DSWantedTextFieldDemoViewState): String {
        return """
        WantedTextField(
            value = value, // TextFieldValue 상태
            placeholder = "텍스트를 입력해 주세요.",
            error = ${state.selectedStatus == Status.Negative}, // (status Negative)
            enabled = ${state.enabled},
            trailingButtonEnabled = true,
            complete = ${state.selectedStatus == Status.Positive}, // (status Positive)
            maxLines = ${state.maxLines},
            minLines = ${state.minLines},
            maxWordCount = ${state.maxWordCount},
            enabledOverflowText = ${state.enabledOverflowText},
            interactionSource = remember { MutableInteractionSource() },
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions.Default,
            size = Size.${state.selectedSize}, ${getDefaultString(state.selectedSize == Size.Large)}
            trailingButton = ${if (state.trailingButton) "\"확인\"" else "null"}, ${
            getDefaultString(!state.trailingButton)
        }
            leadingIcon = if (enabledLeadingIcon) {
                {
                    Icon(
                        modifier = Modifier.size(${state.selectedSize.demoIconSizeCode}),
                        painter = painterResource(id = R.drawable.icon_normal_circle_check_fill),
                        contentDescription = ""
                    )
                }
            } else null, ${getDefaultString(!state.enabledLeadingIcon)}
            trailingIcon = if (enabledTrailingIcon) {
                {
                    Icon(
                        modifier = Modifier.size(${state.selectedSize.demoIconSizeCode}),
                        painter = painterResource(id = R.drawable.icon_normal_circle_check_fill),
                        contentDescription = ""
                    )
                }
            } else null, ${getDefaultString(!state.enabledTrailingIcon)}
            trailingContent = ${
            getTrailingContentCode(
                type = state.selectedTrailingContentType,
                size = state.selectedSize
            )
        }, ${
            getDefaultString(state.selectedTrailingContentType == TrailingContentType.None)
        }
            focusRequester = focusRequester, // (FocusRequester 인스턴스 필요)
            onValueChange = { newValue -> value = newValue }
        )
        """.trimIndent()
    }

    /**
     * trailingContent 슬롯에 들어가는 예시 코드입니다.
     *
     * 바깥 템플릿의 trimIndent 기준에 맞춰 이어지는 줄을 들여쓴다. 첫 줄은 `trailingContent = ` 뒤에
     * 이어 붙으므로 들여쓰기를 지운다. (들여쓰지 않으면 바깥 trimIndent 가 최소 들여쓰기를 0 으로 계산해
     * 생성 코드 전체가 8칸 밀린 채로 복사된다.)
     */
    private fun getTrailingContentCode(type: TrailingContentType, size: Size): String =
        trailingContentBlock(type, size).prependIndent(GENERATED_BLOCK_INDENT).trimStart()

    private fun trailingContentBlock(type: TrailingContentType, size: Size): String = when (type) {
        TrailingContentType.None -> "null"

        TrailingContentType.Icon -> """
        {
            Icon(
                modifier = Modifier.size(${size.demoIconSizeCode}),
                painter = painterResource(id = R.drawable.icon_normal_circle_check_fill),
                contentDescription = ""
            )
        }
        """.trimIndent()

        TrailingContentType.Text -> """
        {
            Text(
                text = "0:00",
                style = DesignSystemTheme.typography.body2Regular,
                color = DesignSystemTheme.colors.foregroundNeutralPrimary
            )
        }
        """.trimIndent()

        TrailingContentType.IconButton -> """
        {
            WantedIconButtonNormal(
                icon = R.drawable.icon_normal_close,
                size = WantedIconButtonNormalSize.${size.demoIconButtonSize},
                onClick = { }
            )
        }
        """.trimIndent()
    }

    /**
     * 생성 코드에 넣을 아이콘 크기 표기입니다. (예: "20.dp")
     *
     * 미리보기가 쓰는 [demoIconSize] 를 그대로 문자열로 바꿔, 복사한 코드와 미리보기가 어긋나지 않게 한다.
     */
    private val Size.demoIconSizeCode: String
        get() = "${demoIconSize.value.toInt()}.dp"

    private fun getDefaultString(isDefault: Boolean): String {
        return if (isDefault) {
            "// (default)"
        } else {
            ""
        }
    }

    private companion object {
        const val CODE_INDENT = "    "

        /** 생성 코드 템플릿에서 프로퍼티 값 블록이 놓이는 들여쓰기 폭입니다. */
        const val GENERATED_BLOCK_INDENT = "            "
    }
}