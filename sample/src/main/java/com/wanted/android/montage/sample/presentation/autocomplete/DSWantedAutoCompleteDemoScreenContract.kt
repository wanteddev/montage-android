package com.wanted.android.montage.sample.presentation.autocomplete

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent

object DSWantedAutoCompleteDemoScreenContract {
    sealed interface DSWantedAutoCompleteDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedAutoCompleteDemoViewState) :
            DSWantedAutoCompleteDemoEvent

        data class ShowCode(val isShowCode: Boolean) : DSWantedAutoCompleteDemoEvent
        data object CopyCode : DSWantedAutoCompleteDemoEvent
        data class SetText(val text: String) : DSWantedAutoCompleteDemoEvent
        data class SetExpanded(val expanded: Boolean) : DSWantedAutoCompleteDemoEvent
        data class SetEnabled(val enabled: Boolean) : DSWantedAutoCompleteDemoEvent
        data class SetShowSectionTitle(val show: Boolean) : DSWantedAutoCompleteDemoEvent
        data class SetShowTopDirectInput(val show: Boolean) : DSWantedAutoCompleteDemoEvent
        data class SetShowBottomDirectInput(val show: Boolean) : DSWantedAutoCompleteDemoEvent
    }

    data class DSWantedAutoCompleteDemoViewState(
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

    sealed interface DSWantedAutoCompleteDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedAutoCompleteDemoSideEffect
    }

    sealed interface DSWantedAutoCompleteDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedAutoCompleteDemoViewEvent
        data object OnClickShowCode : DSWantedAutoCompleteDemoViewEvent
        data object OnClickCopyCode : DSWantedAutoCompleteDemoViewEvent
        data class OnTextChanged(val text: String) : DSWantedAutoCompleteDemoViewEvent
        data class OnExpandedChanged(val expanded: Boolean) : DSWantedAutoCompleteDemoViewEvent
        data class OnEnabledChanged(val enabled: Boolean) : DSWantedAutoCompleteDemoViewEvent
        data class OnShowSectionTitleChanged(val show: Boolean) : DSWantedAutoCompleteDemoViewEvent
        data class OnShowTopDirectInputChanged(val show: Boolean) : DSWantedAutoCompleteDemoViewEvent
        data class OnShowBottomDirectInputChanged(val show: Boolean) : DSWantedAutoCompleteDemoViewEvent
    }
}