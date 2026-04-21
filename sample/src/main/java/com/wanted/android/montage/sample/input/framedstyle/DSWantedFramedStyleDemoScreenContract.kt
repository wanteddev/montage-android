package com.wanted.android.montage.sample.input.framedstyle

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.input.framedstyle.WantedFramedStyleStatus

object DSWantedFramedStyleDemoScreenContract {
    sealed interface DSWantedFramedStyleDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedFramedStyleDemoViewState) : DSWantedFramedStyleDemoEvent
        data class SetStatus(val status: WantedFramedStyleStatus) : DSWantedFramedStyleDemoEvent
        data class SetEnabled(val enabled: Boolean) : DSWantedFramedStyleDemoEvent
        data class ShowCode(val show: Boolean) : DSWantedFramedStyleDemoEvent
        data object CopyCode : DSWantedFramedStyleDemoEvent
    }

    data class DSWantedFramedStyleDemoViewState(
        val isLoading: Boolean = true,
        val status: WantedFramedStyleStatus = WantedFramedStyleStatus.Normal,
        val enabled: Boolean = true,
        val selectedStatus: WantedFramedStyleStatus = WantedFramedStyleStatus.Normal,
        val isShowCode: Boolean = false,
        val code: String = ""
    ) : BaseViewState

    sealed interface DSWantedFramedStyleDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedFramedStyleDemoSideEffect
    }


    sealed interface DSWantedFramedStyleDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedFramedStyleDemoViewEvent
        data object OnClickShowCode : DSWantedFramedStyleDemoViewEvent
        data object OnClickCopyCode : DSWantedFramedStyleDemoViewEvent
        data class OnChangeEnable(val enabled: Boolean) : DSWantedFramedStyleDemoViewEvent
        data class OnSelectedStatus(val status: WantedFramedStyleStatus) : DSWantedFramedStyleDemoViewEvent

    }
}