package com.wanted.android.montage.sample.navigations.pagecounter

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.navigations.pagecounter.DSWantedPageCounterDemoScreenContract.DSWantedPageCounterDemoEvent
import com.wanted.android.montage.sample.navigations.pagecounter.DSWantedPageCounterDemoScreenContract.DSWantedPageCounterDemoSideEffect
import com.wanted.android.montage.sample.navigations.pagecounter.DSWantedPageCounterDemoScreenContract.DSWantedPageCounterDemoViewState
import com.wanted.android.wanted.design.navigations.pagination.pagecounter.WantedPaginationCounterDefaults.WantedPageCounterSize
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedPageCounterDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedPageCounterDemoEvent, DSWantedPageCounterDemoViewState, DSWantedPageCounterDemoSideEffect>() {
    override fun setInitialState() = DSWantedPageCounterDemoViewState()

    override fun handleEvents(event: DSWantedPageCounterDemoEvent) {
        when (event) {
            is DSWantedPageCounterDemoEvent.InitState -> setState { event.viewState }
            is DSWantedPageCounterDemoEvent.ShowCode -> {
                setState { copy(isShowCode = event.isShowCode, code = getCode()) }
            }

            DSWantedPageCounterDemoEvent.CopyCode -> copyCode()
            is DSWantedPageCounterDemoEvent.SetSize -> setState { copy(size = event.size) }
            is DSWantedPageCounterDemoEvent.SetAlternative -> {
                setState { copy(isAlternative = event.enabled) }
            }

            is DSWantedPageCounterDemoEvent.SetCurrentIndex -> {
                setState { copy(currentIndex = event.index.coerceAtLeast(1)) }
            }

            is DSWantedPageCounterDemoEvent.SetTotalCount -> {
                setState { copy(totalCount = event.count.coerceAtLeast(1)) }
            }
        }
    }

    private fun copyCode() {
        setEffect { DSWantedPageCounterDemoSideEffect.CopyCode(getCode()) }
    }

    private fun getCode(): String {
        val state = viewState.value
        val sizeString = when (state.size) {
            WantedPageCounterSize.Small -> "WantedPageCounterSize.Small"
            WantedPageCounterSize.Normal -> "WantedPageCounterSize.Normal"
        }

        return """
            WantedPageCounter(
                totalPageCount = ${state.totalCount},
                currentIndex = ${state.currentIndex},
                size = $sizeString,
                isAlternative = ${state.isAlternative}
            )
        """.trimIndent()
    }
}