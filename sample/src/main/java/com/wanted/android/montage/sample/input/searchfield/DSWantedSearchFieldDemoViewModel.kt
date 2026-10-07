package com.wanted.android.montage.sample.input.searchfield

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.input.searchfield.DSWantedSearchFieldDemoScreenContract.DSWantedSearchFieldDemoEvent
import com.wanted.android.montage.sample.input.searchfield.DSWantedSearchFieldDemoScreenContract.DSWantedSearchFieldDemoSideEffect
import com.wanted.android.montage.sample.input.searchfield.DSWantedSearchFieldDemoScreenContract.DSWantedSearchFieldDemoViewState
import com.wanted.android.wanted.design.input.search.WantedSearchFieldDefaults.Size
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
            is DSWantedSearchFieldDemoEvent.SetVariant -> setState { copy(variant = event.variant) }
            is DSWantedSearchFieldDemoEvent.SetSize -> setState { copy(size = event.size) }
            is DSWantedSearchFieldDemoEvent.SetEnabled -> setState { copy(enabled = event.enabled) }
        }
    }

    private fun copyCode() {
        setEffect { DSWantedSearchFieldDemoSideEffect.CopyCode(getCode()) }
    }

    private fun getCode(): String {
        val state = viewState.value
        // 생성 코드를 그대로 복사해도 컴파일되도록 문자열 리터럴 특수문자를 이스케이프한다.
        val escapedText = state.text
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("$", "\\\$")
        val sizeString = when (state.size) {
            Size.Large -> "Size.Large"
            Size.Medium -> "Size.Medium"
        }

        return """
            WantedSearchField(
                text = "$escapedText",
                placeholder = "검색어를 입력해주세요",
                enabled = ${state.enabled},
                variant = Variant.${state.variant.name},
                size = $sizeString,
                onValueChange = { /* on text change */ }
            )
        """.trimIndent()
    }
}
