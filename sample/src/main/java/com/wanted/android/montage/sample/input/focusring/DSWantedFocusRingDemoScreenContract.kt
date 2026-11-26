package com.wanted.android.montage.sample.input.focusring

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent

object DSWantedFocusRingDemoScreenContract {
    sealed interface DSWantedFocusRingDemoEvent : BaseEvent {
        data class SetVisible(val visible: Boolean) : DSWantedFocusRingDemoEvent
        data class SetNegative(val negative: Boolean) : DSWantedFocusRingDemoEvent
        data class ShowCode(val show: Boolean) : DSWantedFocusRingDemoEvent
        data object CopyCode : DSWantedFocusRingDemoEvent
    }

    data class DSWantedFocusRingDemoViewState(
        val visible: Boolean = true,
        val negative: Boolean = false,
        val isShowCode: Boolean = false,
        val code: String = ""
    ) : BaseViewState

    sealed interface DSWantedFocusRingDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedFocusRingDemoSideEffect
    }

    sealed interface DSWantedFocusRingDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedFocusRingDemoViewEvent
        data object OnClickShowCode : DSWantedFocusRingDemoViewEvent
        data object OnClickCopyCode : DSWantedFocusRingDemoViewEvent
        data class OnChangeVisible(val visible: Boolean) : DSWantedFocusRingDemoViewEvent
        data class OnChangeNegative(val negative: Boolean) : DSWantedFocusRingDemoViewEvent
    }
}
