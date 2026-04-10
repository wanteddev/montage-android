package com.wanted.android.montage.sample.input.framedstyle

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.input.framedstyle.DSWantedFramedStyleDemoScreenContract.DSWantedFramedStyleDemoEvent
import com.wanted.android.montage.sample.input.framedstyle.DSWantedFramedStyleDemoScreenContract.DSWantedFramedStyleDemoSideEffect
import com.wanted.android.montage.sample.input.framedstyle.DSWantedFramedStyleDemoScreenContract.DSWantedFramedStyleDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedFramedStyleDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedFramedStyleDemoEvent, DSWantedFramedStyleDemoViewState, DSWantedFramedStyleDemoSideEffect>() {
    override fun setInitialState() = DSWantedFramedStyleDemoViewState()

    override fun handleEvents(event: DSWantedFramedStyleDemoEvent) {
        when (event) {
            is DSWantedFramedStyleDemoEvent.InitState -> setState { event.viewState }
            is DSWantedFramedStyleDemoEvent.CopyCode -> {
                setEffect { DSWantedFramedStyleDemoSideEffect.CopyCode(getCode()) }
            }

            is DSWantedFramedStyleDemoEvent.SetEnabled -> {
                setState { copy(enabled = event.enabled) }
            }

            is DSWantedFramedStyleDemoEvent.SetStatus -> {
                setState { copy(status = event.status) }
            }

            is DSWantedFramedStyleDemoEvent.ShowCode -> {
                showCode(event.show)
            }
        }
    }

    private fun showCode(isShowCode: Boolean) {
        setState {
            copy(
                code = getCode(),
                isShowCode = isShowCode
            )
        }
    }

    private fun getCode(): String {
        return """
            Box(
                Modifier
                .size(50.dp)
                .framedStyle(
                    status = WantedFramedStyleStatus.${viewState.value.status},
                    enabled = ${viewState.value.enabled}
                )
            )
            """
    }
}