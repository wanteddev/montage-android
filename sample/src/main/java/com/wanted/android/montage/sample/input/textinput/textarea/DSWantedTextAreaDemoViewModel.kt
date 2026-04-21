package com.wanted.android.montage.sample.input.textinput.textarea

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoEvent
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoSideEffect
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedTextAreaDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedTextAreaDemoEvent, DSWantedTextAreaDemoViewState, DSWantedTextAreaDemoSideEffect>() {
    override fun setInitialState() = DSWantedTextAreaDemoViewState()

    override fun handleEvents(event: DSWantedTextAreaDemoEvent) {
        when (event) {
            is DSWantedTextAreaDemoEvent.InitState -> setState { event.viewState }
            is DSWantedTextAreaDemoEvent.CopyCode -> copyCode()
            is DSWantedTextAreaDemoEvent.SetEnabled -> {
                setState { copy(enabled = event.enabled) }
            }


            is DSWantedTextAreaDemoEvent.SetEnabledOverflowText -> {
                setState { copy(enabledOverflowText = event.enabledOverflowText) }
            }

            is DSWantedTextAreaDemoEvent.SetEnabledRequiredBadge -> {
                setState { copy(requiredBadge = event.enabledRequiredBadge) }
            }


            is DSWantedTextAreaDemoEvent.SetRightButton -> {
                setState { copy(rightButton = event.enabledRightButton) }
            }

            is DSWantedTextAreaDemoEvent.ShowCode -> {
                setState {
                    copy(
                        code = getCode(),
                        isShowCode = event.isShowCode
                    )
                }
            }

            is DSWantedTextAreaDemoEvent.SetTextFieldValue -> {
                setState { copy(text = event.text) }
            }

            is DSWantedTextAreaDemoEvent.ShowAll -> {
                setState { copy(isShowAll = event.isShowAll) }
            }

            is DSWantedTextAreaDemoEvent.SetMaxLines -> {
                setState { copy(maxLines = event.maxLines) }
            }

            is DSWantedTextAreaDemoEvent.ShowMaxLinePicker -> {
                setState { copy(isShowMaxLinePicker = event.isShowMaxLinePicker) }
            }

            is DSWantedTextAreaDemoEvent.SetMaxWordCount -> {
                setState { copy(maxWordCount = event.maxWordCount) }
            }

            is DSWantedTextAreaDemoEvent.SetMinLines -> {
                setState { copy(minLines = event.minLines) }
            }

            is DSWantedTextAreaDemoEvent.ShowMaxWordCountPicker -> {
                setState { copy(isShowMaxWordCountPicker = event.isShowMaxWordCountPicker) }
            }

            is DSWantedTextAreaDemoEvent.ShowMinLinesPicker -> {
                setState { copy(isShowMinLinesPicker = event.isShowMinLinesPicker) }
            }

            is DSWantedTextAreaDemoEvent.ShowSample -> {
                setState { copy(isShowSample = event.isShowSample) }
            }

            is DSWantedTextAreaDemoEvent.SetTitle -> {
                setState { copy(title = event.title) }
            }

            is DSWantedTextAreaDemoEvent.SetDescription -> {
                setState { copy(description = event.description) }
            }

            is DSWantedTextAreaDemoEvent.SetGraphemeClusterCount -> {
                setState { copy(isGraphemeClusterCount = event.isGraphemeClusterCount) }
            }

            is DSWantedTextAreaDemoEvent.SetLeadingContent -> {
                setState { copy(leadingContent = event.leadingContent) }
            }

            is DSWantedTextAreaDemoEvent.SetNegative -> {
                setState { copy(negative = event.negative) }
            }

            is DSWantedTextAreaDemoEvent.SetTrailingContent -> {
                setState { copy(trailingContent = event.trailingContent) }
            }

            DSWantedTextAreaDemoEvent.Focus -> {
                setEffect { DSWantedTextAreaDemoSideEffect.Focus }
            }
        }
    }

    private fun copyCode() {
        setEffect { DSWantedTextAreaDemoSideEffect.CopyCode(getCode()) }
    }

    private fun getCode(): String {
        val state = viewState.value
        return """
        WantedTextArea(
            text = ${state.text.ifEmpty { "\"\"" }},
            placeholder = "텍스트를 입력해 주세요.",
            title = ${if (state.title) "Title" else "\"\""}, ${getDefaultString(!viewState.value.title)}
            description = ${if (state.title) "Description" else "\"\""}, ${getDefaultString(!viewState.value.description)}
            rightButton = ${if (state.rightButton) "확인" else "\"\""}, ${getDefaultString(!state.rightButton)}
            leadingContent = if (leadingContent) {
                {
                    Icon(
                        modifier = Modifier.wrapContentSize(),
                        painter = painterResource(id = R.drawable.button_check_circle_fill_12dp_svg),
                        contentDescription = ""
                    )
                }
            } else null, // (default null) 
            trailingContent = if (trailingContent) {
                {
                    Icon(
                        modifier = Modifier.wrapContentSize(),
                        painter = painterResource(id = R.drawable.button_check_circle_fill_12dp_svg),
                        contentDescription = ""
                    )
                }
            } else null, // (default null)
            enabled = ${state.enabled}, ${getDefaultString(state.enabled)},
            negative = ${state.negative}, ${getDefaultString(!state.negative)}
            maxLines = ${state.maxLines}, ${getDefaultString(state.maxLines == 1)}
            minLines = ${state.minLines}, ${getDefaultString(state.minLines == 1)}
            maxWordCount = ${state.maxWordCount}, ${getDefaultString(state.maxWordCount == 2000)}
            enabledOverflowText = ${state.enabledOverflowText}, ${getDefaultString(!state.enabledOverflowText)}
            isGraphemeClusterCount = ${state.isGraphemeClusterCount}, ${getDefaultString(!state.isGraphemeClusterCount)}
            requiredBadge = ${state.requiredBadge}, ${getDefaultString(!state.requiredBadge)}
            onClickRightButton = {},
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