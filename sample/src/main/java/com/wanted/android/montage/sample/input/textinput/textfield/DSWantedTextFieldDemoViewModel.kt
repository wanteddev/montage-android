package com.wanted.android.montage.sample.input.textinput.textfield

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.input.textinput.textfield.DSWantedTextFieldDemoScreenContract.DSWantedTextFieldDemoEvent
import com.wanted.android.montage.sample.input.textinput.textfield.DSWantedTextFieldDemoScreenContract.DSWantedTextFieldDemoSideEffect
import com.wanted.android.montage.sample.input.textinput.textfield.DSWantedTextFieldDemoScreenContract.DSWantedTextFieldDemoViewState
import com.wanted.android.wanted.design.input.textinput.textfield.WantedTextFieldDefaults.RightVariant
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

            is DSWantedTextFieldDemoEvent.SetEnabledRequiredBadge -> {
                setState { copy(enabledRequiredBadge = event.enabledRequiredBadge) }
            }

            is DSWantedTextFieldDemoEvent.SetEnabledTrailingIcon -> {
                setState { copy(enabledTrailingIcon = event.enabledTrailingIcon) }
            }

            is DSWantedTextFieldDemoEvent.SetRightButton -> {
                setState { copy(rightButton = event.enabledRightButton) }
            }

            is DSWantedTextFieldDemoEvent.SetRightButtonVariant -> {
                setState { copy(selectedRightButtonVariant = event.rightButtonVariant) }
            }

            is DSWantedTextFieldDemoEvent.SetRightContent -> {
                setState { copy(rightContent = event.enabledRightContent) }
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

            is DSWantedTextFieldDemoEvent.SetTitle -> {
                setState { copy(title = event.title) }
            }

            DSWantedTextFieldDemoEvent.Focus -> {
                setEffect { DSWantedTextFieldDemoSideEffect.Focus }
            }
        }
    }

    private fun copyCode() {
        setEffect { DSWantedTextFieldDemoSideEffect.CopyCode(getCode()) }
    }

    private fun getCode(): String {
        val state = viewState.value
        return """
        WantedTextField(
            text = ${state.text.ifEmpty { "\"\"" }},
            placeholder = "텍스트를 입력해 주세요.",
            maxLines = ${state.maxLines}, ${getDefaultString(state.maxLines == 1)}
            minLines = ${state.minLines}, ${getDefaultString(state.minLines == 1)}
            maxWordCount = ${state.maxWordCount}, ${getDefaultString(state.maxWordCount == 2000)}
            leadingIcon = if (enabledLeadingIcon) {
                {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.button_check_circle_fill_12dp_svg),
                        contentDescription = ""
                    )
                }
            } else null, // (default null) 
            trailingIcon = if (enabledTrailingIcon) {
                {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.button_check_circle_fill_12dp_svg),
                        contentDescription = ""
                    )
                }
            } else null, // (default null) 
            rightButton = ${if (state.rightButton) "확인" else "\"\""}, ${getDefaultString(!state.rightButton)}
            rightButtonVariant = RightVariant.${state.selectedRightButtonVariant}, ${
            getDefaultString(
                state.selectedRightButtonVariant == RightVariant.Normal
            )
        }
            rightContent = if (enableRightContent) {
                { size ->
                    Icon(
                        modifier = Modifier.size(size),
                        painter = painterResource(id = R.drawable.button_check_circle_fill_12dp_svg),
                        contentDescription = ""
                    )
                }
            } else null, // (default null) 
            status = Status.${state.selectedStatus}, ${getDefaultString(state.selectedStatus == Status.Normal)}
            enabled = ${state.enabled}, ${getDefaultString(state.enabled)}
            title = ${if (state.title) "Title" else "\"\""}, ${getDefaultString(!state.title)}
            requiredBadge = ${state.enabledRequiredBadge}, ${getDefaultString(!state.enabledRequiredBadge)}
            enabledOverflowText = ${state.enabledOverflowText}, ${getDefaultString(!state.enabledOverflowText)}
            focusRequester = FocusRequester(), // (FocusRequester 인스턴스 필요)
            onValueChange = { value -> }
        )   
        """.trimIndent()
    }

    private fun getDefaultString(isDefault: Boolean): String {
        return if (isDefault) {
            "// (default)"
        } else {
            ""
        }
    }
}