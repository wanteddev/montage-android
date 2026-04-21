package com.wanted.android.montage.sample.navigations.topbar

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarContract.Variant

object DSWantedTopAppBarDemoScreenContract {
    sealed interface DSWantedTopAppBarDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedTopAppBarDemoViewState) :
            DSWantedTopAppBarDemoEvent

        data class SetType(val type: Variant) : DSWantedTopAppBarDemoEvent
        data class SetBackground(val background: Boolean) : DSWantedTopAppBarDemoEvent
        data class SetTitleAlignCenter(val titleAlignCenter: Boolean) : DSWantedTopAppBarDemoEvent
        data class SetNavigationIcon(val navigationIcon: Boolean) : DSWantedTopAppBarDemoEvent
        data class SetActions(val actions: Boolean) : DSWantedTopAppBarDemoEvent
        data class SetScrollable(val scrollable: Boolean) : DSWantedTopAppBarDemoEvent
        data class ShowCode(val show: Boolean) : DSWantedTopAppBarDemoEvent
        data object CopyCode : DSWantedTopAppBarDemoEvent
    }

    data class DSWantedTopAppBarDemoViewState(
        val isLoading: Boolean = false,
        val variant: Variant = Variant.Normal,
        val background: Boolean = true,
        val titleAlignCenter: Boolean = false,
        val navigationIcon: Boolean = true,
        val actions: Boolean = false,
        val scrollable: Boolean = false,
        val isShowCode: Boolean = false,
        val code: String = ""
    ) : BaseViewState

    sealed interface DSWantedTopAppBarDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedTopAppBarDemoSideEffect
    }

    sealed interface DSWantedTopAppBarDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedTopAppBarDemoViewEvent
        data object OnClickShowCode : DSWantedTopAppBarDemoViewEvent
        data object OnClickCopyCode : DSWantedTopAppBarDemoViewEvent
        data class OnSelectedType(val type: Variant) : DSWantedTopAppBarDemoViewEvent
        data class OnChangeBackground(val background: Boolean) : DSWantedTopAppBarDemoViewEvent
        data class OnChangeTitleAlignCenter(val titleAlignCenter: Boolean) :
            DSWantedTopAppBarDemoViewEvent

        data class OnChangeNavigationIcon(val navigationIcon: Boolean) :
            DSWantedTopAppBarDemoViewEvent

        data class OnChangeActions(val actions: Boolean) : DSWantedTopAppBarDemoViewEvent
        data class OnChangeScrollable(val scrollable: Boolean) : DSWantedTopAppBarDemoViewEvent
    }
}