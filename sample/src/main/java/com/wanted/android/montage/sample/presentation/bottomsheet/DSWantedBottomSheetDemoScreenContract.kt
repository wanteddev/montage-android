package com.wanted.android.montage.sample.presentation.bottomsheet

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalSize
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalType

object DSWantedBottomSheetDemoScreenContract {
    sealed interface DSWantedBottomSheetDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedBottomSheetDemoViewState) :
            DSWantedBottomSheetDemoEvent

        data class ShowCode(val isShowCode: Boolean) : DSWantedBottomSheetDemoEvent
        data object CopyCode : DSWantedBottomSheetDemoEvent
        data class SetModalType(val type: ModalType) : DSWantedBottomSheetDemoEvent
        data class SetModalSize(val size: ModalSize) : DSWantedBottomSheetDemoEvent
        data class SetDismissOnClickOutside(val dismiss: Boolean) : DSWantedBottomSheetDemoEvent
        data class SetShowSheet(val show: Boolean) : DSWantedBottomSheetDemoEvent
    }

    data class DSWantedBottomSheetDemoViewState(
        val isLoading: Boolean = true,
        val isShowCode: Boolean = false,
        val code: String = "",
        val modalType: ModalType = ModalType.Flexible,
        val modalSize: ModalSize = ModalSize.Medium,
        val dismissOnClickOutside: Boolean = true,
        val isShowSheet: Boolean = false,
    ) : BaseViewState

    sealed interface DSWantedBottomSheetDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedBottomSheetDemoSideEffect
    }

    sealed interface DSWantedBottomSheetDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedBottomSheetDemoViewEvent
        data object OnClickShowCode : DSWantedBottomSheetDemoViewEvent
        data object OnClickCopyCode : DSWantedBottomSheetDemoViewEvent
        data class OnModalTypeChanged(val type: ModalType) : DSWantedBottomSheetDemoViewEvent
        data class OnModalSizeChanged(val size: ModalSize) : DSWantedBottomSheetDemoViewEvent
        data class OnDismissOnClickOutsideChanged(val dismiss: Boolean) : DSWantedBottomSheetDemoViewEvent
        data class OnShowSheetChanged(val show: Boolean) : DSWantedBottomSheetDemoViewEvent
    }
}