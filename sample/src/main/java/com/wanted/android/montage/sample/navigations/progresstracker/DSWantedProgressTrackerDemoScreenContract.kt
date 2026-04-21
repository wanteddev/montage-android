package com.wanted.android.montage.sample.navigations.progresstracker

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent

object DSWantedProgressTrackerDemoScreenContract {
    sealed interface DSWantedProgressTrackerDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedProgressTrackerDemoViewState) :
            DSWantedProgressTrackerDemoEvent

        data class ShowCode(val isShowCode: Boolean) : DSWantedProgressTrackerDemoEvent
        data object CopyCode : DSWantedProgressTrackerDemoEvent
        data class SetStepCount(val count: Int) : DSWantedProgressTrackerDemoEvent
        data class SetCurrentStep(val step: Int) : DSWantedProgressTrackerDemoEvent
        data class SetOrientation(val orientation: TrackerOrientation) : DSWantedProgressTrackerDemoEvent
    }

    data class DSWantedProgressTrackerDemoViewState(
        val isLoading: Boolean = false,
        val isShowCode: Boolean = false,
        val code: String = "",
        val stepCount: Int = 4,
        val currentStep: Int = 2,
        val orientation: TrackerOrientation = TrackerOrientation.Horizontal,
    ) : BaseViewState

    sealed interface DSWantedProgressTrackerDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedProgressTrackerDemoSideEffect
    }

    sealed interface DSWantedProgressTrackerDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedProgressTrackerDemoViewEvent
        data object OnClickShowCode : DSWantedProgressTrackerDemoViewEvent
        data object OnClickCopyCode : DSWantedProgressTrackerDemoViewEvent
        data class OnStepCountChanged(val count: Int) : DSWantedProgressTrackerDemoViewEvent
        data class OnCurrentStepChanged(val step: Int) : DSWantedProgressTrackerDemoViewEvent
        data class OnOrientationChanged(val orientation: TrackerOrientation) :
            DSWantedProgressTrackerDemoViewEvent
    }

    enum class TrackerOrientation {
        Horizontal,
        Vertical
    }
}