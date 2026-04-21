package com.wanted.android.montage.sample.navigations.topbar.dialogtopbar

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.navigations.topbar.dialogtopbar.WantedDialogTopAppBarContract.Variant

object DSWantedDialogTopAppBarDemoScreenContract {
    sealed interface DSWantedDialogTopAppBarDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedDialogTopAppBarDemoViewState) :
            DSWantedDialogTopAppBarDemoEvent

        data class SetVariant(val variant: Variant) : DSWantedDialogTopAppBarDemoEvent
        data class SetBackground(val background: Boolean) : DSWantedDialogTopAppBarDemoEvent
        data class SetBackgroundColorEnabled(val backgroundColorEnabled: Boolean) : DSWantedDialogTopAppBarDemoEvent
        data class SetNavigationIcon(val navigationIcon: Boolean) : DSWantedDialogTopAppBarDemoEvent
        data class SetActions(val actions: Boolean) : DSWantedDialogTopAppBarDemoEvent
        data class SetShowCloseButton(val showCloseButton: Boolean) :
            DSWantedDialogTopAppBarDemoEvent

        data class SetScrollable(val scrollable: Boolean) : DSWantedDialogTopAppBarDemoEvent
        data class SetListRevert(val listRevert: Boolean) : DSWantedDialogTopAppBarDemoEvent
        data class SetUseBottomSheet(val useBottomSheet: Boolean) : DSWantedDialogTopAppBarDemoEvent
        data class ShowCode(val show: Boolean) : DSWantedDialogTopAppBarDemoEvent
        data class ShowModal(val show: Boolean) : DSWantedDialogTopAppBarDemoEvent
        data object CopyCode : DSWantedDialogTopAppBarDemoEvent
    }

    data class DSWantedDialogTopAppBarDemoViewState(
        val isLoading: Boolean = false,
        val variant: Variant = Variant.Normal,
        val background: Boolean = true,
        val backgroundColorEnabled: Boolean = true,
        val navigationIcon: Boolean = false,
        val actions: Boolean = false,
        val showCloseButton: Boolean = false,
        val scrollable: Boolean = false,
        val listRevert: Boolean = true,
        val useBottomSheet: Boolean = false,
        val isShowCode: Boolean = false,
        val isShowModal: Boolean = false,
        val code: String = ""
    ) : BaseViewState

    sealed interface DSWantedDialogTopAppBarDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedDialogTopAppBarDemoSideEffect
    }

    sealed interface DSWantedDialogTopAppBarDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedDialogTopAppBarDemoViewEvent
        data object OnClickShowCode : DSWantedDialogTopAppBarDemoViewEvent
        data object OnClickShowModal : DSWantedDialogTopAppBarDemoViewEvent
        data object OnClickCopyCode : DSWantedDialogTopAppBarDemoViewEvent
        data class OnSelectedVariant(val variant: Variant) : DSWantedDialogTopAppBarDemoViewEvent
        data class OnChangeBackground(val background: Boolean) :
            DSWantedDialogTopAppBarDemoViewEvent

        data class OnChangeBackgroundColorEnabled(val backgroundColorEnabled: Boolean) :
            DSWantedDialogTopAppBarDemoViewEvent

        data class OnChangeNavigationIcon(val navigationIcon: Boolean) :
            DSWantedDialogTopAppBarDemoViewEvent

        data class OnChangeActions(val actions: Boolean) : DSWantedDialogTopAppBarDemoViewEvent
        data class OnChangeShowCloseButton(val showCloseButton: Boolean) :
            DSWantedDialogTopAppBarDemoViewEvent

        data class OnChangeScrollable(val scrollable: Boolean) :
            DSWantedDialogTopAppBarDemoViewEvent

        data class OnChangeListRevert(val listRevert: Boolean) :
            DSWantedDialogTopAppBarDemoViewEvent

        data class OnChangeUseBottomSheet(val useBottomSheet: Boolean) :
            DSWantedDialogTopAppBarDemoViewEvent
    }
}
