package com.wanted.android.montage.sample.feedback.toast

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.feedback.toast.WantedToastVariant

object DSWantedToastDemoScreenContract {
    sealed interface DSWantedToastDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedToastDemoViewState) : DSWantedToastDemoEvent
        data object ShowToast : DSWantedToastDemoEvent

        data class SetVariant(val variant: WantedToastVariant) : DSWantedToastDemoEvent
    }

    data class DSWantedToastDemoViewState(
        val isLoading: Boolean = false,
        val isShowCode: Boolean = false,
        val code: String = "",
        val toastVariant: WantedToastVariant = WantedToastVariant.Message

    ) : BaseViewState


    sealed interface DSWantedToastDemoSideEffect : BaseSideEffect {
        data class ShowToast(val variant: WantedToastVariant) : DSWantedToastDemoSideEffect
    }

    sealed interface DSWantedToastDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedToastDemoViewEvent
        data object OnClickShowToast: DSWantedToastDemoViewEvent
        data object OnClickMessage : DSWantedToastDemoViewEvent
        data object OnClickPositive : DSWantedToastDemoViewEvent
        data object OnClickNegative : DSWantedToastDemoViewEvent
        data object OnClickCautionary : DSWantedToastDemoViewEvent
    }
}