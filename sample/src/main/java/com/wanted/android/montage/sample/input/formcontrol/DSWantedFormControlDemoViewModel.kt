package com.wanted.android.montage.sample.input.formcontrol

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.input.formcontrol.DSWantedFormControlDemoScreenContract.DSWantedFormControlDemoEvent
import com.wanted.android.montage.sample.input.formcontrol.DSWantedFormControlDemoScreenContract.DSWantedFormControlDemoSideEffect
import com.wanted.android.montage.sample.input.formcontrol.DSWantedFormControlDemoScreenContract.DSWantedFormControlDemoViewState
import com.wanted.android.wanted.design.input.formcontrol.WantedFormControlDefaults
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedFormControlDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedFormControlDemoEvent, DSWantedFormControlDemoViewState, DSWantedFormControlDemoSideEffect>() {
    override fun setInitialState() = DSWantedFormControlDemoViewState()

    override fun handleEvents(event: DSWantedFormControlDemoEvent) {
        when (event) {
            is DSWantedFormControlDemoEvent.InitState -> setState { event.viewState }
            is DSWantedFormControlDemoEvent.CopyCode -> copyCode()

            is DSWantedFormControlDemoEvent.ShowCode -> {
                setState {
                    copy(
                        code = getCode(),
                        isShowCode = event.isShowCode
                    )
                }
            }

            is DSWantedFormControlDemoEvent.ShowAll -> setState { copy(isShowAll = event.isShowAll) }

            is DSWantedFormControlDemoEvent.SetTextFieldValue -> {
                setState { copy(text = event.text) }
            }

            is DSWantedFormControlDemoEvent.SetLabel -> setState { copy(labelText = event.label) }
            is DSWantedFormControlDemoEvent.SetRequired -> setState { copy(required = event.required) }
            is DSWantedFormControlDemoEvent.SetDescription -> {
                setState { copy(description = event.description) }
            }

            is DSWantedFormControlDemoEvent.SetAccessory -> {
                setState { copy(accessory = event.accessory) }
            }

            is DSWantedFormControlDemoEvent.SetEnabled -> setState { copy(enabled = event.enabled) }

            is DSWantedFormControlDemoEvent.SetSize -> setState { copy(size = event.size) }
            is DSWantedFormControlDemoEvent.SetStatus -> setState { copy(status = event.status) }
            is DSWantedFormControlDemoEvent.SetLabelPlacement -> {
                setState { copy(labelPlacement = event.labelPlacement) }
            }
        }
    }

    private fun copyCode() {
        setEffect { DSWantedFormControlDemoSideEffect.CopyCode(getCode()) }
    }

    private fun getCode(): String {
        val state = viewState.value
        return """
        WantedFormControl(
            label = "${state.labelText}", ${getDefaultString(state.labelText == DSWantedFormControlDemoScreenContract.DEFAULT_LABEL_TEXT)}
            required = ${state.required}, ${getDefaultString(!state.required)}
            description = ${if (state.description) "\"도움말 메시지입니다.\"" else "null"}, ${getDefaultString(state.description)}
            accessory = ${if (state.accessory) "{ WantedTextAreaCharacterCount(...) }" else "null"}, ${getDefaultString(!state.accessory)}
            size = WantedFormControlDefaults.Size.${state.size.name}, ${getDefaultString(state.size == WantedFormControlDefaults.Size.Large)}
            status = WantedFormControlDefaults.Status.${state.status.name}, ${getDefaultString(state.status == WantedFormControlDefaults.Status.Normal)}
            labelPlacement = WantedFormControlDefaults.LabelPlacement.${state.labelPlacement.name}, ${getDefaultString(state.labelPlacement == WantedFormControlDefaults.LabelPlacement.Top)}
            enabled = ${state.enabled}, ${getDefaultString(state.enabled)}
        ) {
            WantedTextField(
                text = ${state.text.ifEmpty { "\"\"" }},
                placeholder = "텍스트를 입력해 주세요.",
                onValueChange = { value -> }
            )
        }
        """.trimIndent()
    }

    private fun getDefaultString(isDefault: Boolean): String {
        return if (isDefault) "// (default)" else ""
    }
}
