package com.wanted.android.montage.sample.navigations.paginationdots

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.navigations.paginationdots.DSWantedPaginationDotsDemoScreenContract.DSWantedPaginationDotsDemoEvent
import com.wanted.android.montage.sample.navigations.paginationdots.DSWantedPaginationDotsDemoScreenContract.DSWantedPaginationDotsDemoSideEffect
import com.wanted.android.montage.sample.navigations.paginationdots.DSWantedPaginationDotsDemoScreenContract.DSWantedPaginationDotsDemoViewState
import com.wanted.android.wanted.design.navigations.pagination.paginationdots.WantedPaginationDotDefaults.WantedDotIndicatorSize
import com.wanted.android.wanted.design.navigations.pagination.paginationdots.WantedPaginationDotDefaults.WantedDotIndicatorType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedPaginationDotsDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedPaginationDotsDemoEvent, DSWantedPaginationDotsDemoViewState, DSWantedPaginationDotsDemoSideEffect>() {
    override fun setInitialState() = DSWantedPaginationDotsDemoViewState()

    override fun handleEvents(event: DSWantedPaginationDotsDemoEvent) {
        when (event) {
            is DSWantedPaginationDotsDemoEvent.InitState -> setState { event.viewState }
            is DSWantedPaginationDotsDemoEvent.ShowCode -> {
                setState { copy(isShowCode = event.isShowCode, code = getCode()) }
            }

            DSWantedPaginationDotsDemoEvent.CopyCode -> copyCode()
            is DSWantedPaginationDotsDemoEvent.SetSize -> setState { copy(size = event.size) }
            is DSWantedPaginationDotsDemoEvent.SetType -> setState { copy(type = event.type) }
            is DSWantedPaginationDotsDemoEvent.SetTotalCount -> {
                setState { copy(totalCount = event.count.coerceAtLeast(1)) }
            }

            is DSWantedPaginationDotsDemoEvent.SetVisibleCount -> {
                setState { copy(visibleCount = event.count.coerceAtLeast(1)) }
            }

            is DSWantedPaginationDotsDemoEvent.SetCurrentIndex -> {
                setState { copy(currentIndex = event.index.coerceAtLeast(0)) }
            }
        }
    }

    private fun copyCode() {
        setEffect { DSWantedPaginationDotsDemoSideEffect.CopyCode(getCode()) }
    }

    private fun getCode(): String {
        val state = viewState.value
        val sizeString = when (state.size) {
            WantedDotIndicatorSize.Medium -> "WantedDotIndicatorSize.Medium"
            WantedDotIndicatorSize.Small -> "WantedDotIndicatorSize.Small"
        }
        val typeString = when (state.type) {
            WantedDotIndicatorType.Normal -> "WantedDotIndicatorType.Normal"
            WantedDotIndicatorType.White -> "WantedDotIndicatorType.White"
        }

        return """
            WantedDotIndicator(
                totalPageCount = ${state.totalCount},
                visibleDotCount = ${state.visibleCount},
                currentIndex = ${state.currentIndex},
                size = $sizeString,
                type = $typeString
            )
        """.trimIndent()
    }
}