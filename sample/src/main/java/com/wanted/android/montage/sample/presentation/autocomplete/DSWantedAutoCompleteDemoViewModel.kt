package com.wanted.android.montage.sample.presentation.autocomplete

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.presentation.autocomplete.DSWantedAutoCompleteDemoScreenContract.DSWantedAutoCompleteDemoEvent
import com.wanted.android.montage.sample.presentation.autocomplete.DSWantedAutoCompleteDemoScreenContract.DSWantedAutoCompleteDemoSideEffect
import com.wanted.android.montage.sample.presentation.autocomplete.DSWantedAutoCompleteDemoScreenContract.DSWantedAutoCompleteDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedAutoCompleteDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedAutoCompleteDemoEvent, DSWantedAutoCompleteDemoViewState, DSWantedAutoCompleteDemoSideEffect>() {
    override fun setInitialState() = DSWantedAutoCompleteDemoViewState()

    override fun handleEvents(event: DSWantedAutoCompleteDemoEvent) {
        when (event) {
            is DSWantedAutoCompleteDemoEvent.InitState -> setState { event.viewState }
            is DSWantedAutoCompleteDemoEvent.ShowCode -> {
                setState { copy(isShowCode = event.isShowCode, code = getCode()) }
            }

            DSWantedAutoCompleteDemoEvent.CopyCode -> copyCode()
            is DSWantedAutoCompleteDemoEvent.SetText -> setState { copy(text = event.text) }
            is DSWantedAutoCompleteDemoEvent.SetExpanded -> setState { copy(expanded = event.expanded) }
            is DSWantedAutoCompleteDemoEvent.SetEnabled -> setState { copy(enabled = event.enabled) }
            is DSWantedAutoCompleteDemoEvent.SetShowSectionTitle -> {
                setState { copy(showSectionTitle = event.show) }
            }

            is DSWantedAutoCompleteDemoEvent.SetShowTopDirectInput -> {
                setState { copy(showTopDirectInput = event.show) }
            }

            is DSWantedAutoCompleteDemoEvent.SetShowBottomDirectInput -> {
                setState { copy(showBottomDirectInput = event.show) }
            }
        }
    }

    private fun copyCode() {
        setEffect { DSWantedAutoCompleteDemoSideEffect.CopyCode(getCode()) }
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
            ExposedDropdownMenuBox(
                expanded = ${state.expanded},
                onExpandedChange = { /* on expanded change */ }
            ) {
                WantedTextField(
                    text = "${state.text}",
                    placeholder = "검색어를 입력해주세요",
                    enabled = ${state.enabled},
                    onValueChange = { /* on text change */ }
                )

                WantedAutoComplete(
                    expanded = ${state.expanded},
                    onDismissRequest = { /* on dismiss */ },
                    sectionCount = 2,
                    sectionItemCount = { 5 },
                    sectionItem = { section, index ->
                        Text("Section ${'$'}section - Item ${'$'}index")
                    },
                    $sectionTitleLine
                    $topDirectInputLine
                    $bottomDirectInputLine
                )
            }
        """.trimIndent()
    }
}