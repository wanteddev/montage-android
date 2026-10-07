package com.wanted.android.montage.sample.actions.textbutton

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.actions.button.textbutton.WantedTextButtonColor
import com.wanted.android.wanted.design.actions.button.textbutton.WantedTextButtonSize

object DSWantedTextButtonDemoScreenContract {
    sealed interface DSWantedTextButtonDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedTextButtonDemoViewState) : DSWantedTextButtonDemoEvent
        data class ShowCode(val isShowCode: Boolean) : DSWantedTextButtonDemoEvent
        data object CopyCode : DSWantedTextButtonDemoEvent

        data class ShowAll(val isShowAll: Boolean) : DSWantedTextButtonDemoEvent
        data class SetType(val type: WantedTextButtonColor) : DSWantedTextButtonDemoEvent
        data class SetSize(val buttonSize: WantedTextButtonSize) : DSWantedTextButtonDemoEvent
        data class SetEnable(val enabled: Boolean) : DSWantedTextButtonDemoEvent
        data class SetLoading(val isLoading: Boolean) : DSWantedTextButtonDemoEvent
        data class SetEnableLeadingDrawable(val enableLeftDrawable: Boolean) :
            DSWantedTextButtonDemoEvent

        data class SetEnableTrailingDrawable(
            val enableRightDrawable: Boolean
        ) : DSWantedTextButtonDemoEvent
    }

    data class DSWantedTextButtonDemoViewState(
        val isShowCode: Boolean = false,
        val code: String = "",

        val isShowAll: Boolean = false,

        val typeList: List<WantedTextButtonColor> = WantedTextButtonColor.entries.toList(),
        val selectedType: WantedTextButtonColor = WantedTextButtonColor.PRIMARY,

        val sizeList: List<WantedTextButtonSize> = WantedTextButtonSize.entries.toList(),
        val selectedSize: WantedTextButtonSize = WantedTextButtonSize.MEDIUM,

        val isLoading: Boolean = false,
        val enabled: Boolean = true,
        val enabledLeadingDrawable: Boolean = false,
        val enabledTrailingDrawable: Boolean = false,

        ) : BaseViewState

    sealed interface DSWantedTextButtonDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedTextButtonDemoSideEffect
    }


    sealed interface DSWantedTextButtonDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedTextButtonDemoViewEvent
        data object OnClickShowCode : DSWantedTextButtonDemoViewEvent
        data object OnClickCopyCode : DSWantedTextButtonDemoViewEvent
        data object OnClickShowAll : DSWantedTextButtonDemoViewEvent

        data class OnSelectType(val type: WantedTextButtonColor) : DSWantedTextButtonDemoViewEvent
        data class OnSelectSize(val buttonSize: WantedTextButtonSize) : DSWantedTextButtonDemoViewEvent
        data class OnChangeEnable(val enabled: Boolean) : DSWantedTextButtonDemoViewEvent
        data class OnChangeLoading(val isLoading: Boolean) : DSWantedTextButtonDemoViewEvent
        data class OnChangeEnabledLeadingDrawable(
            val enabledLeadingDrawable: Boolean
        ) : DSWantedTextButtonDemoViewEvent

        data class OnChangeEnabledTrailingDrawable(
            val enabledTrailingDrawable: Boolean
        ) : DSWantedTextButtonDemoViewEvent
    }
}
