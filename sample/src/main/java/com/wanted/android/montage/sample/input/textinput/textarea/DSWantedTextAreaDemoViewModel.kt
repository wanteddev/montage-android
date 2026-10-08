package com.wanted.android.montage.sample.input.textinput.textarea

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoEvent
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoSideEffect
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.wanted.android.wanted.design.input.textinput.textarea.WantedTextAreaDefaults.DEFAULT_MAX_LINE
import com.wanted.android.wanted.design.input.textinput.textarea.WantedTextAreaDefaults.DEFAULT_MIN_LINE


@HiltViewModel
class DSWantedTextAreaDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedTextAreaDemoEvent, DSWantedTextAreaDemoViewState, DSWantedTextAreaDemoSideEffect>() {
    override fun setInitialState() = DSWantedTextAreaDemoViewState()

    override fun handleEvents(event: DSWantedTextAreaDemoEvent) {
        when (event) {
            is DSWantedTextAreaDemoEvent.InitState -> setState { event.viewState }
            is DSWantedTextAreaDemoEvent.CopyCode -> copyCode()
            is DSWantedTextAreaDemoEvent.SetEnabled -> setState { copy(enabled = event.enabled) }
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

            is DSWantedTextAreaDemoEvent.ShowAll -> setState { copy(isShowAll = event.isShowAll) }

            is DSWantedTextAreaDemoEvent.SetMaxLines -> setState { copy(maxLines = event.maxLines) }

            is DSWantedTextAreaDemoEvent.ShowMaxLinePicker -> {
                setState { copy(isShowMaxLinePicker = event.isShowMaxLinePicker) }
            }

            is DSWantedTextAreaDemoEvent.SetMaxWordCount -> {
                setState { copy(maxWordCount = event.maxWordCount) }
            }

            is DSWantedTextAreaDemoEvent.SetMinLines -> setState { copy(minLines = event.minLines) }

            is DSWantedTextAreaDemoEvent.ShowMaxWordCountPicker -> {
                setState { copy(isShowMaxWordCountPicker = event.isShowMaxWordCountPicker) }
            }

            is DSWantedTextAreaDemoEvent.ShowMinLinesPicker -> {
                setState { copy(isShowMinLinesPicker = event.isShowMinLinesPicker) }
            }

            is DSWantedTextAreaDemoEvent.ShowSample -> setState { copy(isShowSample = event.isShowSample) }

            is DSWantedTextAreaDemoEvent.SetTitle -> setState { copy(title = event.title) }

            is DSWantedTextAreaDemoEvent.SetDescription -> {
                setState { copy(description = event.description) }
            }

            is DSWantedTextAreaDemoEvent.SetGraphemeClusterCount -> {
                setState { copy(isGraphemeClusterCount = event.isGraphemeClusterCount) }
            }

            is DSWantedTextAreaDemoEvent.SetLeadingContent -> {
                setState { copy(leadingContent = event.enabled) }
            }

            is DSWantedTextAreaDemoEvent.SetNegative -> setState { copy(negative = event.negative) }

            is DSWantedTextAreaDemoEvent.SetTrailingContent -> {
                setState { copy(trailingContent = event.enabled) }
            }

            is DSWantedTextAreaDemoEvent.SetFormControl -> {
                setState { copy(formControl = event.enabled) }
            }

            DSWantedTextAreaDemoEvent.Focus -> setEffect { DSWantedTextAreaDemoSideEffect.Focus }

            is DSWantedTextAreaDemoEvent.SetSize -> setState { copy(size = event.size) }

            is DSWantedTextAreaDemoEvent.SetResize -> setState { copy(resize = event.resize) }

        }
    }

    private fun copyCode() {
        setEffect { DSWantedTextAreaDemoSideEffect.CopyCode(getCode()) }
    }

    private fun getCode(): String {
        val state = viewState.value
        val textArea = getTextAreaCode(state)

        return if (state.formControl) {
            """
            |WantedFormControl(
            |    label = ${if (state.title) "\"Title\"" else "\"\""}, ${getDefaultString(!state.title)}
            |    required = ${state.requiredBadge}, ${getDefaultString(!state.requiredBadge)}
            |    description = ${if (state.description) "\"Description\"" else "null"}, ${getDefaultString(!state.description)}
            |    size = WantedFormControlDefaults.Size.${state.size.name}, ${getDefaultString(state.size.name == "Large")}
            |    status = WantedFormControlDefaults.Status.${if (state.negative) "Negative" else "Normal"}, ${getDefaultString(!state.negative)}
            |    enabled = ${state.enabled}, ${getDefaultString(state.enabled)}
            |    input = {
            |${textArea.prependIndent("        ")}
            |    }
            |)
            """.trimMargin()
        } else {
            textArea
        }
    }

    /**
     * button 이 지정되면 trailingContent 슬롯을 대체하므로 두 슬롯은 상호 배타적으로 사용한다.
     */
    private fun getTextAreaCode(state: DSWantedTextAreaDemoViewState): String {
        val slot = if (state.rightButton) {
            """
            |button = "확인",
            |onClickButton = {},
            """.trimMargin()
        } else {
            """
            |trailingContent = ${if (state.trailingContent) "{ /* Content */ }" else "null"}, ${getDefaultString(!state.trailingContent)}
            """.trimMargin()
        }

        return """
        |WantedTextArea(
        |    value = TextFieldValue(${state.text.toCodeLiteral()}),
        |    placeholder = "텍스트를 입력해 주세요.",
        |    size = WantedTextAreaDefaults.Size.${state.size.name}, ${getDefaultString(state.size.name == "Large")}
        |    resize = WantedTextAreaDefaults.Resize.${state.resize.name}, ${getDefaultString(state.resize.name == "Normal")}
        |    leadingContent = ${if (state.leadingContent) "{ /* Content */ }" else "null"}, ${getDefaultString(!state.leadingContent)}
        |${slot.prependIndent("    ")}
        |    enabled = ${state.enabled}, ${getDefaultString(state.enabled)}
        |    status = WantedTextAreaDefaults.Status.${state.negative.toTextAreaStatusName()}, ${getDefaultString(!state.negative)}
        |    maxLines = ${state.maxLines}, ${getDefaultString(state.maxLines == DEFAULT_MAX_LINE)}
        |    minLines = ${state.minLines}, ${getDefaultString(state.minLines == DEFAULT_MIN_LINE)}
        |    maxWordCount = ${state.maxWordCount}, ${getDefaultString(state.maxWordCount == 2000)}
        |    enabledOverflowText = ${state.enabledOverflowText}, ${getDefaultString(!state.enabledOverflowText)}
        |    isGraphemeClusterCount = ${state.isGraphemeClusterCount}, ${getDefaultString(!state.isGraphemeClusterCount)}
        |    onValueChange = { value -> }
        |)
        """.trimMargin()
    }

    /**
     * 생성 코드에 그대로 붙여 넣을 수 있도록 문자열 리터럴로 감싼다.
     */
    private fun String.toCodeLiteral(): String {
        val escaped = replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\$", "\\\$")
            .replace("\r", "\\r")
            .replace("\n", "\\n")
        return "\"$escaped\""
    }

    private fun getDefaultString(isDefault: Boolean): String {
        return if (isDefault) "// (default)" else ""
    }
}

/** 데모의 negative 스위치를 생성 코드용 Status 이름으로 변환합니다. */
private fun Boolean.toTextAreaStatusName(): String = if (this) "Negative" else "Normal"
