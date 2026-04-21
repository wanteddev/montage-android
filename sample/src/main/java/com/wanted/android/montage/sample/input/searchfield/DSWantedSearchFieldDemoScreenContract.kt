package com.wanted.android.montage.sample.input.searchfield

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.input.search.WantedSearchFieldDefaults.Size

object DSWantedSearchFieldDemoScreenContract {
    sealed interface DSWantedSearchFieldDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedSearchFieldDemoViewState) :
            DSWantedSearchFieldDemoEvent

        data class ShowCode(val isShowCode: Boolean) : DSWantedSearchFieldDemoEvent
        data object CopyCode : DSWantedSearchFieldDemoEvent
        data class SetText(val text: String) : DSWantedSearchFieldDemoEvent
        data class SetSize(val size: Size) : DSWantedSearchFieldDemoEvent
        data class SetEnabled(val enabled: Boolean) : DSWantedSearchFieldDemoEvent
    }

    data class DSWantedSearchFieldDemoViewState(
        val isLoading: Boolean = true,
        val text: String = "",
        val isShowCode: Boolean = false,
        val code: String = "",
        val size: Size = Size.Medium(),
        val enabled: Boolean = true
    ) : BaseViewState

    sealed interface DSWantedSearchFieldDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedSearchFieldDemoSideEffect
    }

    sealed interface DSWantedSearchFieldDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedSearchFieldDemoViewEvent
        data object OnClickShowCode : DSWantedSearchFieldDemoViewEvent
        data object OnClickCopyCode : DSWantedSearchFieldDemoViewEvent
        data class OnTextChanged(val text: String) : DSWantedSearchFieldDemoViewEvent
        data class OnSizeChanged(val size: Size) : DSWantedSearchFieldDemoViewEvent
        data class OnEnabledChanged(val enabled: Boolean) : DSWantedSearchFieldDemoViewEvent
    }
}