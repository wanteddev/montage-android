package com.wanted.android.montage.sample.navigations.progressindicator

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent

object DSWantedProgressIndicatorDemoScreenContract {
    sealed interface DSWantedProgressIndicatorDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedProgressIndicatorDemoViewState) :
            DSWantedProgressIndicatorDemoEvent

        data class ShowCode(val isShowCode: Boolean) : DSWantedProgressIndicatorDemoEvent
        data object CopyCode : DSWantedProgressIndicatorDemoEvent
        data class SetProgress(val progress: Float) : DSWantedProgressIndicatorDemoEvent
    }

    data class DSWantedProgressIndicatorDemoViewState(
        val isLoading: Boolean = false,
        val isShowCode: Boolean = false,
        val code: String = "",
        val progress: Float = 0.3f,
    ) : BaseViewState

    sealed interface DSWantedProgressIndicatorDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedProgressIndicatorDemoSideEffect
    }

    sealed interface DSWantedProgressIndicatorDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedProgressIndicatorDemoViewEvent
        data object OnClickShowCode : DSWantedProgressIndicatorDemoViewEvent
        data object OnClickCopyCode : DSWantedProgressIndicatorDemoViewEvent
        data class OnProgressChanged(val progress: Float) : DSWantedProgressIndicatorDemoViewEvent
    }
}