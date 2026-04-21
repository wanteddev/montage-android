package com.wanted.android.montage.sample.input.textinput.autocompletetextfield

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent

object DSWantedAutoCompleteTextFieldDemoScreenContract {
    sealed interface DSWantedAutoCompleteTextFieldDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedAutoCompleteTextFieldDemoViewState) :
            DSWantedAutoCompleteTextFieldDemoEvent

        data class ShowCode(val isShowCode: Boolean) : DSWantedAutoCompleteTextFieldDemoEvent
        data object CopyCode : DSWantedAutoCompleteTextFieldDemoEvent
        data class SetText(val text: String) : DSWantedAutoCompleteTextFieldDemoEvent
        data class SetExpanded(val expanded: Boolean) : DSWantedAutoCompleteTextFieldDemoEvent
        data class SetEnabled(val enabled: Boolean) : DSWantedAutoCompleteTextFieldDemoEvent
        data class SetShowSectionTitle(val show: Boolean) : DSWantedAutoCompleteTextFieldDemoEvent
        data class SetShowTopDirectInput(val show: Boolean) : DSWantedAutoCompleteTextFieldDemoEvent
        data class SetShowBottomDirectInput(val show: Boolean) : DSWantedAutoCompleteTextFieldDemoEvent
    }

    data class DSWantedAutoCompleteTextFieldDemoViewState(
        val isLoading: Boolean = true,
        val text: String = "",
        val isShowCode: Boolean = false,
        val code: String = "",
        val expanded: Boolean = false,
        val enabled: Boolean = true,
        val showSectionTitle: Boolean = true,
        val showTopDirectInput: Boolean = true,
        val showBottomDirectInput: Boolean = false,
    ) : BaseViewState

    sealed interface DSWantedAutoCompleteTextFieldDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedAutoCompleteTextFieldDemoSideEffect
    }

    sealed interface DSWantedAutoCompleteTextFieldDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedAutoCompleteTextFieldDemoViewEvent
        data object OnClickShowCode : DSWantedAutoCompleteTextFieldDemoViewEvent
        data object OnClickCopyCode : DSWantedAutoCompleteTextFieldDemoViewEvent
        data class OnTextChanged(val text: String) : DSWantedAutoCompleteTextFieldDemoViewEvent
        data class OnExpandedChanged(val expanded: Boolean) : DSWantedAutoCompleteTextFieldDemoViewEvent
        data class OnEnabledChanged(val enabled: Boolean) : DSWantedAutoCompleteTextFieldDemoViewEvent
        data class OnShowSectionTitleChanged(val show: Boolean) : DSWantedAutoCompleteTextFieldDemoViewEvent
        data class OnShowTopDirectInputChanged(val show: Boolean) : DSWantedAutoCompleteTextFieldDemoViewEvent
        data class OnShowBottomDirectInputChanged(val show: Boolean) : DSWantedAutoCompleteTextFieldDemoViewEvent
    }
}