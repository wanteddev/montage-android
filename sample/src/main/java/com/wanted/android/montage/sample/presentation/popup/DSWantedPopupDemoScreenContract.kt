package com.wanted.android.montage.sample.presentation.popup

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalType

object DSWantedPopupDemoScreenContract {
    sealed interface DSWantedPopupDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedPopupDemoViewState) : DSWantedPopupDemoEvent
        data class ShowCode(val isShowCode: Boolean) : DSWantedPopupDemoEvent
        data object CopyCode : DSWantedPopupDemoEvent
        data class SetShowPopup(val show: Boolean) : DSWantedPopupDemoEvent
        data class SetModalType(val type: ModalType) : DSWantedPopupDemoEvent
        data class SetUseTopBar(val use: Boolean) : DSWantedPopupDemoEvent
    }

    data class DSWantedPopupDemoViewState(
        val isLoading: Boolean = false,
        val isShowCode: Boolean = false,
        val code: String = "",
        val showPopup: Boolean = false,
        val modalType: ModalType = ModalType.Flexible,
        val useTopBar: Boolean = true,
    ) : BaseViewState

    sealed interface DSWantedPopupDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedPopupDemoSideEffect
    }

    sealed interface DSWantedPopupDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedPopupDemoViewEvent
        data object OnClickShowCode : DSWantedPopupDemoViewEvent
        data object OnClickCopyCode : DSWantedPopupDemoViewEvent
        data class OnShowPopupChanged(val show: Boolean) : DSWantedPopupDemoViewEvent
        data class OnModalTypeChanged(val type: ModalType) : DSWantedPopupDemoViewEvent
        data class OnUseTopBarChanged(val use: Boolean) : DSWantedPopupDemoViewEvent
    }
}