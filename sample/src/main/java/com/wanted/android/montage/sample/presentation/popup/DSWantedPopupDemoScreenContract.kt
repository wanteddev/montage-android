package com.wanted.android.montage.sample.presentation.popup

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.actions.actionarea.ActionAreaType
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopupContract.Resize

object DSWantedPopupDemoScreenContract {
    sealed interface DSWantedPopupDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedPopupDemoViewState) : DSWantedPopupDemoEvent
        data class ShowCode(val isShowCode: Boolean) : DSWantedPopupDemoEvent
        data object CopyCode : DSWantedPopupDemoEvent
        data class SetShowPopup(val show: Boolean) : DSWantedPopupDemoEvent
        data class SetResize(val resize: Resize) : DSWantedPopupDemoEvent
        data class SetUseTopBar(val use: Boolean) : DSWantedPopupDemoEvent
        data class SetUseActionArea(val use: Boolean) : DSWantedPopupDemoEvent
        data class SetActionAreaType(val actionAreaType: ActionAreaType) : DSWantedPopupDemoEvent
    }

    data class DSWantedPopupDemoViewState(
        val isLoading: Boolean = false,
        val isShowCode: Boolean = false,
        val code: String = "",
        val showPopup: Boolean = false,
        val resize: Resize = Resize.Hug,
        val useTopBar: Boolean = true,
        val useActionArea: Boolean = true,
        val actionAreaType: ActionAreaType = ActionAreaType.Strong,
    ) : BaseViewState

    sealed interface DSWantedPopupDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedPopupDemoSideEffect
    }

    sealed interface DSWantedPopupDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedPopupDemoViewEvent
        data object OnClickShowCode : DSWantedPopupDemoViewEvent
        data object OnClickCopyCode : DSWantedPopupDemoViewEvent
        data class OnShowPopupChanged(val show: Boolean) : DSWantedPopupDemoViewEvent
        data class OnResizeChanged(val resize: Resize) : DSWantedPopupDemoViewEvent
        data class OnUseTopBarChanged(val use: Boolean) : DSWantedPopupDemoViewEvent
        data class OnUseActionAreaChanged(val use: Boolean) : DSWantedPopupDemoViewEvent
        data class OnActionAreaTypeChanged(
            val actionAreaType: ActionAreaType
        ) : DSWantedPopupDemoViewEvent
    }
}