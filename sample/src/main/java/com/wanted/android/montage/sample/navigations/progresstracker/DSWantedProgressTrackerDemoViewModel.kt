package com.wanted.android.montage.sample.navigations.progresstracker

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.navigations.progresstracker.DSWantedProgressTrackerDemoScreenContract.DSWantedProgressTrackerDemoEvent
import com.wanted.android.montage.sample.navigations.progresstracker.DSWantedProgressTrackerDemoScreenContract.DSWantedProgressTrackerDemoSideEffect
import com.wanted.android.montage.sample.navigations.progresstracker.DSWantedProgressTrackerDemoScreenContract.DSWantedProgressTrackerDemoViewState
import com.wanted.android.montage.sample.navigations.progresstracker.DSWantedProgressTrackerDemoScreenContract.TrackerOrientation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedProgressTrackerDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedProgressTrackerDemoEvent, DSWantedProgressTrackerDemoViewState, DSWantedProgressTrackerDemoSideEffect>() {
    override fun setInitialState() = DSWantedProgressTrackerDemoViewState()

    override fun handleEvents(event: DSWantedProgressTrackerDemoEvent) {
        when (event) {
            is DSWantedProgressTrackerDemoEvent.InitState -> setState { event.viewState }
            is DSWantedProgressTrackerDemoEvent.ShowCode -> {
                setState { copy(isShowCode = event.isShowCode, code = getCode()) }
            }

            DSWantedProgressTrackerDemoEvent.CopyCode -> copyCode()
            is DSWantedProgressTrackerDemoEvent.SetStepCount -> {
                setState { copy(stepCount = event.count.coerceAtLeast(1)) }
            }

            is DSWantedProgressTrackerDemoEvent.SetCurrentStep -> {
                setState { copy(currentStep = event.step.coerceAtLeast(1)) }
            }

            is DSWantedProgressTrackerDemoEvent.SetOrientation -> {
                setState { copy(orientation = event.orientation) }
            }
        }
    }

    private fun copyCode() {
        setEffect { DSWantedProgressTrackerDemoSideEffect.CopyCode(getCode()) }
    }

    private fun getCode(): String {
        val state = viewState.value
        val content = when (state.orientation) {
            TrackerOrientation.Horizontal -> "WantedProgressTrackerHorizontal"
            TrackerOrientation.Vertical -> "WantedProgressTrackerVertical"
        }

        return """
            $content(
                stepCount = ${state.stepCount},
                currentStep = ${state.currentStep},
                label = { index -> \"${'$'}{index + 1}단계\" }
            )
        """.trimIndent()
    }
}