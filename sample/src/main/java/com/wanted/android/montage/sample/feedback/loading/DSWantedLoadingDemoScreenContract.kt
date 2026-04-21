package com.wanted.android.montage.sample.feedback.loading

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent

object DSWantedLoadingDemoScreenContract {
    sealed interface DSWantedLoadingDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedLoadingDemoViewState) : DSWantedLoadingDemoEvent
        data class ShowCode(val isShowCode: Boolean) : DSWantedLoadingDemoEvent
        data object CopyCode : DSWantedLoadingDemoEvent
        data class SetLoadingType(val type: LoadingType) : DSWantedLoadingDemoEvent
        data class SetUseDim(val useDim: Boolean) : DSWantedLoadingDemoEvent
        data class SetShowLoading(val show: Boolean) : DSWantedLoadingDemoEvent
    }

    data class DSWantedLoadingDemoViewState(
        val isLoading: Boolean = false,
        val isShowCode: Boolean = false,
        val code: String = "",
        val loadingType: LoadingType = LoadingType.Circular,
        val useDim: Boolean = true,
        val showLoading: Boolean = true,
    ) : BaseViewState

    sealed interface DSWantedLoadingDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedLoadingDemoSideEffect
    }

    sealed interface DSWantedLoadingDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedLoadingDemoViewEvent
        data object OnClickShowCode : DSWantedLoadingDemoViewEvent
        data object OnClickCopyCode : DSWantedLoadingDemoViewEvent
        data class OnLoadingTypeChanged(val type: LoadingType) : DSWantedLoadingDemoViewEvent
        data class OnUseDimChanged(val useDim: Boolean) : DSWantedLoadingDemoViewEvent
        data class OnShowLoadingChanged(val show: Boolean) : DSWantedLoadingDemoViewEvent
    }

    enum class LoadingType {
        Circular,
        Logo
    }
}