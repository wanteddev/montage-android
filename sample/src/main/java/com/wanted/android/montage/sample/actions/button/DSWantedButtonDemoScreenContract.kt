package com.wanted.android.montage.sample.actions.button

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType

object DSWantedButtonDemoScreenContract {
    sealed interface DSWantedButtonDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedButtonDemoViewState) : DSWantedButtonDemoEvent
        data class ShowCode(val isShowCode: Boolean) : DSWantedButtonDemoEvent
        data object CopyCode : DSWantedButtonDemoEvent

        data class ShowAll(val isShowAll: Boolean) : DSWantedButtonDemoEvent
        data class SetButtonShape(val buttonVariant: ButtonVariant) : DSWantedButtonDemoEvent
        data class SetType(val type: ButtonType) : DSWantedButtonDemoEvent
        data class SetSize(val buttonSize: ButtonSize) : DSWantedButtonDemoEvent
        data class SetEnable(val enabled: Boolean) : DSWantedButtonDemoEvent
        data class SetLoading(val isLoading: Boolean) : DSWantedButtonDemoEvent
        data class SetEnableLeadingDrawable(val enableLeftDrawable: Boolean) :
            DSWantedButtonDemoEvent

        data class SetEnableTrailingDrawable(
            val enableRightDrawable: Boolean
        ) : DSWantedButtonDemoEvent
    }

    data class DSWantedButtonDemoViewState(
        val isShowCode: Boolean = false,
        val code: String = "",

        val isShowAll: Boolean = false,

        val buttonVariantLists: List<ButtonVariant> = ButtonVariant.entries.toList(),
        val selectedButtonVariant: ButtonVariant = ButtonVariant.SOLID,

        val typeList: List<ButtonType> = ButtonType.entries.toList(),
        val selectedType: ButtonType = ButtonType.PRIMARY,

        val sizeList: List<ButtonSize> = ButtonSize.entries.toList(),
        val selectedSize: ButtonSize = ButtonSize.LARGE,

        val isLoading: Boolean = false,
        val enabled: Boolean = true,
        val enabledLeadingDrawable: Boolean = false,
        val enabledTrailingDrawable: Boolean = false,

        ) : BaseViewState

    sealed interface DSWantedButtonDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedButtonDemoSideEffect
    }


    sealed interface DSWantedButtonDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedButtonDemoViewEvent
        data object OnClickShowCode : DSWantedButtonDemoViewEvent
        data object OnClickCopyCode : DSWantedButtonDemoViewEvent
        data object OnClickShowAll : DSWantedButtonDemoViewEvent

        data class OnSelectButtonShape(val buttonVariant: ButtonVariant) : DSWantedButtonDemoViewEvent
        data class OnSelectType(val type: ButtonType) : DSWantedButtonDemoViewEvent
        data class OnSelectSize(val buttonSize: ButtonSize) : DSWantedButtonDemoViewEvent
        data class OnChangeEnable(val enabled: Boolean) : DSWantedButtonDemoViewEvent
        data class OnChangeLoading(val isLoading: Boolean) : DSWantedButtonDemoViewEvent
        data class OnChangeEnabledLeadingDrawable(
            val enabledLeadingDrawable: Boolean
        ) : DSWantedButtonDemoViewEvent

        data class OnChangeEnabledTrailingDrawable(
            val enabledTrailingDrawable: Boolean
        ) : DSWantedButtonDemoViewEvent
    }
}