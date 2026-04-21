package com.wanted.android.montage.sample.input.searchfield

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.input.searchfield.DSWantedSearchFieldDemoScreenContract.DSWantedSearchFieldDemoEvent
import com.wanted.android.montage.sample.input.searchfield.DSWantedSearchFieldDemoScreenContract.DSWantedSearchFieldDemoSideEffect
import com.wanted.android.montage.sample.input.searchfield.DSWantedSearchFieldDemoScreenContract.DSWantedSearchFieldDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedSearchFieldDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedSearchFieldDemoEvent, DSWantedSearchFieldDemoViewState, DSWantedSearchFieldDemoSideEffect>() {
    override fun setInitialState() = DSWantedSearchFieldDemoViewState()

    override fun handleEvents(event: DSWantedSearchFieldDemoEvent) {
        when (event) {
            is DSWantedSearchFieldDemoEvent.InitState -> setState { event.viewState }
            is DSWantedSearchFieldDemoEvent.ShowCode -> {
                setState { copy(isShowCode = event.isShowCode, code = getCode()) }
            }

            DSWantedSearchFieldDemoEvent.CopyCode -> copyCode()
            is DSWantedSearchFieldDemoEvent.SetText -> setState { copy(text = event.text) }
            is DSWantedSearchFieldDemoEvent.SetSize -> setState { copy(size = event.size) }
            is DSWantedSearchFieldDemoEvent.SetEnabled -> setState { copy(enabled = event.enabled) }
        }
    }

    private fun copyCode() {
        setEffect { DSWantedSearchFieldDemoSideEffect.CopyCode(getCode()) }
    }

    private fun getCode(): String {
        val state = viewState.value
        val sizeString = when (state.size) {
            is com.wanted.android.wanted.design.input.search.WantedSearchFieldDefaults.Size.Small -> "Size.Small()"
            is com.wanted.android.wanted.design.input.search.WantedSearchFieldDefaults.Size.Medium -> "Size.Medium()"
            is com.wanted.android.wanted.design.input.search.WantedSearchFieldDefaults.Size.Custom -> "Size.Custom()"
        }

        return """
            WantedSearchField(
                text = "${state.text}",
                placeholder = "검색어를 입력해주세요",
                enabled = ${state.enabled},
                size = $sizeString,
                onValueChange = { /* on text change */ }
            )
        """.trimIndent()
    }
}