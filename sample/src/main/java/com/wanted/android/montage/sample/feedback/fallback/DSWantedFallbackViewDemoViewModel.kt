package com.wanted.android.montage.sample.feedback.fallback

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.feedback.fallback.DSWantedFallbackViewDemoScreenContract.DSWantedFallbackViewDemoEvent
import com.wanted.android.montage.sample.feedback.fallback.DSWantedFallbackViewDemoScreenContract.DSWantedFallbackViewDemoSideEffect
import com.wanted.android.montage.sample.feedback.fallback.DSWantedFallbackViewDemoScreenContract.DSWantedFallbackViewDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedFallbackViewDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedFallbackViewDemoEvent, DSWantedFallbackViewDemoViewState, DSWantedFallbackViewDemoSideEffect>() {
    override fun setInitialState() = DSWantedFallbackViewDemoViewState()

    override fun handleEvents(event: DSWantedFallbackViewDemoEvent) {
        when (event) {
            is DSWantedFallbackViewDemoEvent.InitState -> setState { event.viewState }
            is DSWantedFallbackViewDemoEvent.OnChangeHeading -> {
                setState { copy(heading = event.heading) }
            }

            is DSWantedFallbackViewDemoEvent.OnChangeDescription -> {
                setState { copy(description = event.description) }
            }

            is DSWantedFallbackViewDemoEvent.OnChangeButtonVariant -> {
                setState { copy(buttonVariant = event.buttonVariant) }
            }

            is DSWantedFallbackViewDemoEvent.OnChangePadding -> {
                setState { copy(padding = event.padding) }
            }

            is DSWantedFallbackViewDemoEvent.OnChangeMain -> {
                setState { copy(main = event.main) }
            }

            is DSWantedFallbackViewDemoEvent.OnChangeAlternative -> {
                setState { copy(alternative = event.alternative) }
            }

            is DSWantedFallbackViewDemoEvent.CopyCode -> {
                setEffect { DSWantedFallbackViewDemoSideEffect.CopyCode(getCode()) }
            }

            is DSWantedFallbackViewDemoEvent.ShowCode -> {
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
        val state = viewState.value
        return """
WantedFallbackView(
    heading = ${if (state.heading) "\"헤더입니다.\"" else "null"},
    description = ${if (state.description) "\"마침표를 찍어주세요.\"" else "null"},
    padding = WantedFallbackPadding.${state.padding},
    buttonVariant = WantedFallbackButtonVariant.${state.buttonVariant},
    main = ${if (state.main) "\"행동\"" else "null"},
    alternative = ${if (state.alternative) "\"보조행동\"" else "null"}
)
        """.trimIndent()
    }
}
