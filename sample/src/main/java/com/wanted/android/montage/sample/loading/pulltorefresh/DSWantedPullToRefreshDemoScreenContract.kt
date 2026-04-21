package com.wanted.android.montage.sample.loading.pulltorefresh

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent

object DSWantedPullToRefreshDemoScreenContract {
    sealed interface DSWantedPullToRefreshDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedPullToRefreshDemoViewState) :
            DSWantedPullToRefreshDemoEvent

        data class ShowCode(val isShowCode: Boolean) : DSWantedPullToRefreshDemoEvent
        data object CopyCode : DSWantedPullToRefreshDemoEvent
        data class SetRefreshing(val refreshing: Boolean) : DSWantedPullToRefreshDemoEvent
    }

    data class DSWantedPullToRefreshDemoViewState(
        val isLoading: Boolean = false,
        val isShowCode: Boolean = false,
        val code: String = "",
        val isRefreshing: Boolean = false,
    ) : BaseViewState

    sealed interface DSWantedPullToRefreshDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedPullToRefreshDemoSideEffect
    }

    sealed interface DSWantedPullToRefreshDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedPullToRefreshDemoViewEvent
        data object OnClickShowCode : DSWantedPullToRefreshDemoViewEvent
        data object OnClickCopyCode : DSWantedPullToRefreshDemoViewEvent
        data class OnRefreshingChanged(val refreshing: Boolean) : DSWantedPullToRefreshDemoViewEvent
    }
}