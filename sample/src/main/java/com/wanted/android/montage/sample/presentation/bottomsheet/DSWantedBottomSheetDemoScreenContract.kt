package com.wanted.android.montage.sample.presentation.bottomsheet

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.navigations.topbar.dialogtopbar.WantedDialogTopAppBarContract.Variant
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalType

object DSWantedBottomSheetDemoScreenContract {
    sealed interface DSWantedBottomSheetDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedBottomSheetDemoViewState) :
            DSWantedBottomSheetDemoEvent

        data class ShowCode(val isShowCode: Boolean) : DSWantedBottomSheetDemoEvent
        data object CopyCode : DSWantedBottomSheetDemoEvent
        data class SetModalType(val type: ModalType) : DSWantedBottomSheetDemoEvent
        data class SetDismissOnClickOutside(val dismiss: Boolean) : DSWantedBottomSheetDemoEvent
        data class SetShowSheet(val show: Boolean) : DSWantedBottomSheetDemoEvent
        data class SetNavigationVariant(val variant: Variant) : DSWantedBottomSheetDemoEvent
        data class SetCloseButtonBackground(val use: Boolean) : DSWantedBottomSheetDemoEvent
        data class SetContentPadding(val use: Boolean) : DSWantedBottomSheetDemoEvent
        data class SetUseActionArea(val use: Boolean) : DSWantedBottomSheetDemoEvent
    }

    data class DSWantedBottomSheetDemoViewState(
        val isLoading: Boolean = true,
        val isShowCode: Boolean = false,
        val code: String = "",
        val modalType: ModalType = ModalType.Flexible,
        val dismissOnClickOutside: Boolean = true,
        val isShowSheet: Boolean = false,
        val navigationVariant: Variant = Variant.Emphasized,
        val closeButtonBackground: Boolean = false,
        val useContentPadding: Boolean = true,
        val useActionArea: Boolean = true,
    ) : BaseViewState

    sealed interface DSWantedBottomSheetDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedBottomSheetDemoSideEffect
    }

    sealed interface DSWantedBottomSheetDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedBottomSheetDemoViewEvent
        data object OnClickShowCode : DSWantedBottomSheetDemoViewEvent
        data object OnClickCopyCode : DSWantedBottomSheetDemoViewEvent
        data class OnModalTypeChanged(val type: ModalType) : DSWantedBottomSheetDemoViewEvent
        data class OnDismissOnClickOutsideChanged(val dismiss: Boolean) : DSWantedBottomSheetDemoViewEvent
        data class OnShowSheetChanged(val show: Boolean) : DSWantedBottomSheetDemoViewEvent
        data class OnNavigationVariantChanged(val variant: Variant) : DSWantedBottomSheetDemoViewEvent
        data class OnCloseButtonBackgroundChanged(val use: Boolean) : DSWantedBottomSheetDemoViewEvent
        data class OnContentPaddingChanged(val use: Boolean) : DSWantedBottomSheetDemoViewEvent
        data class OnUseActionAreaChanged(val use: Boolean) : DSWantedBottomSheetDemoViewEvent
    }
}