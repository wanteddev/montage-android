package com.wanted.android.montage.sample.loading.pulltorefresh

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.loading.pulltorefresh.DSWantedPullToRefreshDemoScreenContract.DSWantedPullToRefreshDemoEvent
import com.wanted.android.montage.sample.loading.pulltorefresh.DSWantedPullToRefreshDemoScreenContract.DSWantedPullToRefreshDemoSideEffect
import com.wanted.android.montage.sample.loading.pulltorefresh.DSWantedPullToRefreshDemoScreenContract.DSWantedPullToRefreshDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedPullToRefreshDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedPullToRefreshDemoEvent, DSWantedPullToRefreshDemoViewState, DSWantedPullToRefreshDemoSideEffect>() {
    override fun setInitialState() = DSWantedPullToRefreshDemoViewState()

    override fun handleEvents(event: DSWantedPullToRefreshDemoEvent) {
        when (event) {
            is DSWantedPullToRefreshDemoEvent.InitState -> setState { event.viewState }
            is DSWantedPullToRefreshDemoEvent.ShowCode -> {
                setState { copy(isShowCode = event.isShowCode, code = getCode()) }
            }

            DSWantedPullToRefreshDemoEvent.CopyCode -> copyCode()
            is DSWantedPullToRefreshDemoEvent.SetRefreshing -> {
                setState { copy(isRefreshing = event.refreshing) }
            }
        }
    }

    private fun copyCode() {
        setEffect { DSWantedPullToRefreshDemoSideEffect.CopyCode(getCode()) }
    }

    private fun getCode(): String {
        val state = viewState.value
        return """
            var isRefreshing by remember { mutableStateOf(${state.isRefreshing}) }

            WantedPullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = {
                    isRefreshing = true
                    isRefreshing = false
                }
            ) {
                Column { /* content */ }
            }
        """.trimIndent()
    }
}