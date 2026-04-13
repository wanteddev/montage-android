package com.wanted.android.montage.sample.navigations.progressindicator

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.navigations.progressindicator.DSWantedProgressIndicatorDemoScreenContract.DSWantedProgressIndicatorDemoEvent
import com.wanted.android.montage.sample.navigations.progressindicator.DSWantedProgressIndicatorDemoScreenContract.DSWantedProgressIndicatorDemoSideEffect
import com.wanted.android.montage.sample.navigations.progressindicator.DSWantedProgressIndicatorDemoScreenContract.DSWantedProgressIndicatorDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedProgressIndicatorDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedProgressIndicatorDemoEvent, DSWantedProgressIndicatorDemoViewState, DSWantedProgressIndicatorDemoSideEffect>() {
    override fun setInitialState() = DSWantedProgressIndicatorDemoViewState()

    override fun handleEvents(event: DSWantedProgressIndicatorDemoEvent) {
        when (event) {
            is DSWantedProgressIndicatorDemoEvent.InitState -> setState { event.viewState }
            is DSWantedProgressIndicatorDemoEvent.ShowCode -> {
                setState { copy(isShowCode = event.isShowCode, code = getCode()) }
            }

            DSWantedProgressIndicatorDemoEvent.CopyCode -> copyCode()
            is DSWantedProgressIndicatorDemoEvent.SetProgress -> {
                setState { copy(progress = event.progress.coerceIn(0f, 1f)) }
            }
        }
    }

    private fun copyCode() {
        setEffect { DSWantedProgressIndicatorDemoSideEffect.CopyCode(getCode()) }
    }

    private fun getCode(): String {
        val state = viewState.value
        return """
            WantedLinearProgressIndicator(
                currentProgress = ${"%.2f".format(state.progress)}f
            )
        """.trimIndent()
    }
}