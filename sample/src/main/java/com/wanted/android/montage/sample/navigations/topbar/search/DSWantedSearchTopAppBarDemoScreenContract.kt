package com.wanted.android.montage.sample.navigations.topbar.search

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.input.search.WantedSearchFieldDefaults.Size

object DSWantedSearchTopAppBarDemoScreenContract {
    sealed interface DSWantedSearchTopAppBarDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedSearchTopAppBarDemoViewState) :
            DSWantedSearchTopAppBarDemoEvent

        data class SetSearchText(val text: String) : DSWantedSearchTopAppBarDemoEvent
        data class SetPlaceholder(val placeholder: String) : DSWantedSearchTopAppBarDemoEvent
        data class SetSize(val size: Size) : DSWantedSearchTopAppBarDemoEvent
        data class SetBackground(val background: Boolean) : DSWantedSearchTopAppBarDemoEvent
        data class SetEnabled(val enabled: Boolean) : DSWantedSearchTopAppBarDemoEvent
        data class SetActions(val actions: Boolean) : DSWantedSearchTopAppBarDemoEvent
        data class ShowCode(val show: Boolean) : DSWantedSearchTopAppBarDemoEvent
        data object CopyCode : DSWantedSearchTopAppBarDemoEvent
    }

    data class DSWantedSearchTopAppBarDemoViewState(
        val isLoading: Boolean = false,
        val searchText: String = "",
        val placeholder: String = "검색어를 입력하세요",
        val size: Size = Size.Small(),
        val background: Boolean = true,
        val enabled: Boolean = true,
        val actions: Boolean = false,
        val isShowCode: Boolean = false,
        val code: String = ""
    ) : BaseViewState

    sealed interface DSWantedSearchTopAppBarDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedSearchTopAppBarDemoSideEffect
    }

    sealed interface DSWantedSearchTopAppBarDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedSearchTopAppBarDemoViewEvent
        data object OnClickShowCode : DSWantedSearchTopAppBarDemoViewEvent
        data object OnClickCopyCode : DSWantedSearchTopAppBarDemoViewEvent
        data class OnSearchTextChange(val text: String) : DSWantedSearchTopAppBarDemoViewEvent
        data class OnChangePlaceholder(val placeholder: String) :
            DSWantedSearchTopAppBarDemoViewEvent

        data class OnChangeSize(val size: Size) : DSWantedSearchTopAppBarDemoViewEvent
        data class OnChangeBackground(val background: Boolean) :
            DSWantedSearchTopAppBarDemoViewEvent

        data class OnChangeEnabled(val enabled: Boolean) : DSWantedSearchTopAppBarDemoViewEvent
        data class OnChangeActions(val actions: Boolean) : DSWantedSearchTopAppBarDemoViewEvent
    }
}