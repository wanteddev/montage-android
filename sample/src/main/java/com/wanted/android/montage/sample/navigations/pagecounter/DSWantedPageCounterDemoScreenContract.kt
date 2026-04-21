package com.wanted.android.montage.sample.navigations.pagecounter

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.navigations.pagination.pagecounter.WantedPaginationCounterDefaults.WantedPageCounterSize

object DSWantedPageCounterDemoScreenContract {
    sealed interface DSWantedPageCounterDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedPageCounterDemoViewState) :
            DSWantedPageCounterDemoEvent

        data class ShowCode(val isShowCode: Boolean) : DSWantedPageCounterDemoEvent
        data object CopyCode : DSWantedPageCounterDemoEvent
        data class SetSize(val size: WantedPageCounterSize) : DSWantedPageCounterDemoEvent
        data class SetAlternative(val enabled: Boolean) : DSWantedPageCounterDemoEvent
        data class SetCurrentIndex(val index: Int) : DSWantedPageCounterDemoEvent
        data class SetTotalCount(val count: Int) : DSWantedPageCounterDemoEvent
    }

    data class DSWantedPageCounterDemoViewState(
        val isLoading: Boolean = false,
        val isShowCode: Boolean = false,
        val code: String = "",
        val size: WantedPageCounterSize = WantedPageCounterSize.Normal,
        val isAlternative: Boolean = false,
        val currentIndex: Int = 1,
        val totalCount: Int = 5,
    ) : BaseViewState

    sealed interface DSWantedPageCounterDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedPageCounterDemoSideEffect
    }

    sealed interface DSWantedPageCounterDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedPageCounterDemoViewEvent
        data object OnClickShowCode : DSWantedPageCounterDemoViewEvent
        data object OnClickCopyCode : DSWantedPageCounterDemoViewEvent
        data class OnSizeChanged(val size: WantedPageCounterSize) : DSWantedPageCounterDemoViewEvent
        data class OnAlternativeChanged(val enabled: Boolean) : DSWantedPageCounterDemoViewEvent
        data class OnCurrentIndexChanged(val index: Int) : DSWantedPageCounterDemoViewEvent
        data class OnTotalCountChanged(val count: Int) : DSWantedPageCounterDemoViewEvent
    }
}