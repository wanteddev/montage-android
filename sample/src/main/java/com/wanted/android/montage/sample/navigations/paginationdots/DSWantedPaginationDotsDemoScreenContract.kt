package com.wanted.android.montage.sample.navigations.paginationdots

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.navigations.pagination.paginationdots.WantedPaginationDotDefaults.WantedDotIndicatorSize
import com.wanted.android.wanted.design.navigations.pagination.paginationdots.WantedPaginationDotDefaults.WantedDotIndicatorType

object DSWantedPaginationDotsDemoScreenContract {
    sealed interface DSWantedPaginationDotsDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedPaginationDotsDemoViewState) :
            DSWantedPaginationDotsDemoEvent

        data class ShowCode(val isShowCode: Boolean) : DSWantedPaginationDotsDemoEvent
        data object CopyCode : DSWantedPaginationDotsDemoEvent
        data class SetSize(val size: WantedDotIndicatorSize) : DSWantedPaginationDotsDemoEvent
        data class SetType(val type: WantedDotIndicatorType) : DSWantedPaginationDotsDemoEvent
        data class SetTotalCount(val count: Int) : DSWantedPaginationDotsDemoEvent
        data class SetVisibleCount(val count: Int) : DSWantedPaginationDotsDemoEvent
        data class SetCurrentIndex(val index: Int) : DSWantedPaginationDotsDemoEvent
    }

    data class DSWantedPaginationDotsDemoViewState(
        val isLoading: Boolean = false,
        val isShowCode: Boolean = false,
        val code: String = "",
        val size: WantedDotIndicatorSize = WantedDotIndicatorSize.Medium,
        val type: WantedDotIndicatorType = WantedDotIndicatorType.Normal,
        val totalCount: Int = 8,
        val visibleCount: Int = 5,
        val currentIndex: Int = 0,
    ) : BaseViewState

    sealed interface DSWantedPaginationDotsDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedPaginationDotsDemoSideEffect
    }

    sealed interface DSWantedPaginationDotsDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedPaginationDotsDemoViewEvent
        data object OnClickShowCode : DSWantedPaginationDotsDemoViewEvent
        data object OnClickCopyCode : DSWantedPaginationDotsDemoViewEvent
        data class OnSizeChanged(val size: WantedDotIndicatorSize) : DSWantedPaginationDotsDemoViewEvent
        data class OnTypeChanged(val type: WantedDotIndicatorType) : DSWantedPaginationDotsDemoViewEvent
        data class OnTotalCountChanged(val count: Int) : DSWantedPaginationDotsDemoViewEvent
        data class OnVisibleCountChanged(val count: Int) : DSWantedPaginationDotsDemoViewEvent
        data class OnCurrentIndexChanged(val index: Int) : DSWantedPaginationDotsDemoViewEvent
    }
}