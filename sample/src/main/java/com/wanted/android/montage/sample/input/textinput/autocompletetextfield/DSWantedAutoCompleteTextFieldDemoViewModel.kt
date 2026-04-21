package com.wanted.android.montage.sample.input.textinput.autocompletetextfield

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.input.textinput.autocompletetextfield.DSWantedAutoCompleteTextFieldDemoScreenContract.DSWantedAutoCompleteTextFieldDemoEvent
import com.wanted.android.montage.sample.input.textinput.autocompletetextfield.DSWantedAutoCompleteTextFieldDemoScreenContract.DSWantedAutoCompleteTextFieldDemoSideEffect
import com.wanted.android.montage.sample.input.textinput.autocompletetextfield.DSWantedAutoCompleteTextFieldDemoScreenContract.DSWantedAutoCompleteTextFieldDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedAutoCompleteTextFieldDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedAutoCompleteTextFieldDemoEvent, DSWantedAutoCompleteTextFieldDemoViewState, DSWantedAutoCompleteTextFieldDemoSideEffect>() {
    override fun setInitialState() = DSWantedAutoCompleteTextFieldDemoViewState()

    override fun handleEvents(event: DSWantedAutoCompleteTextFieldDemoEvent) {
        when (event) {
            is DSWantedAutoCompleteTextFieldDemoEvent.InitState -> setState { event.viewState }
            is DSWantedAutoCompleteTextFieldDemoEvent.ShowCode -> {
                setState { copy(isShowCode = event.isShowCode, code = getCode()) }
            }

            DSWantedAutoCompleteTextFieldDemoEvent.CopyCode -> copyCode()
            is DSWantedAutoCompleteTextFieldDemoEvent.SetText -> setState { copy(text = event.text) }
            is DSWantedAutoCompleteTextFieldDemoEvent.SetExpanded -> {
                setState { copy(expanded = event.expanded) }
            }

            is DSWantedAutoCompleteTextFieldDemoEvent.SetEnabled -> setState { copy(enabled = event.enabled) }
            is DSWantedAutoCompleteTextFieldDemoEvent.SetShowSectionTitle -> {
                setState { copy(showSectionTitle = event.show) }
            }

            is DSWantedAutoCompleteTextFieldDemoEvent.SetShowTopDirectInput -> {
                setState { copy(showTopDirectInput = event.show) }
            }

            is DSWantedAutoCompleteTextFieldDemoEvent.SetShowBottomDirectInput -> {
                setState { copy(showBottomDirectInput = event.show) }
            }
        }
    }

    private fun copyCode() {
        setEffect { DSWantedAutoCompleteTextFieldDemoSideEffect.CopyCode(getCode()) }
    }

    private fun getCode(): String {
        val state = viewState.value
        val sectionTitleLine = if (state.showSectionTitle) {
            "sectionTitle = { section -> \"Section ${'$'}section\" },"
        } else {
            "sectionTitle = null,"
        }
        val topDirectInputLine = if (state.showTopDirectInput) {
            "topDirectInput = { /* 상단 직접 입력 */ },"
        } else {
            "topDirectInput = null,"
        }
        val bottomDirectInputLine = if (state.showBottomDirectInput) {
            "bottomDirectInput = { /* 하단 직접 입력 */ },"
        } else {
            "bottomDirectInput = null,"
        }

        return """
            WantedAutoCompleteTextField(
                text = "${state.text}",
                placeholder = "검색어를 입력해주세요",
                enabled = ${state.enabled},
                expanded = ${state.expanded},
                sectionCount = 2,
                sectionItemCount = { 5 },
                sectionItem = { section, index ->
                    Text("Section ${'$'}section - Item ${'$'}index")
                },
                $sectionTitleLine
                $topDirectInputLine
                $bottomDirectInputLine
                onExpandedChange = { /* on expanded change */ },
                onValueChange = { /* on text change */ }
            )
        """.trimIndent()
    }
}