package com.wanted.android.montage.sample.input.numberpicker

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent

object DSWantedNumberPickerDemoScreenContract {
    sealed interface DSWantedNumberPickerDemoEvent : BaseEvent {
        data class SetStart(val start: Int) : DSWantedNumberPickerDemoEvent
        data class SetEnd(val end: Int) : DSWantedNumberPickerDemoEvent
        data class SetStep(val step: Int) : DSWantedNumberPickerDemoEvent
        data class SetSelectedValue(val value: Int) : DSWantedNumberPickerDemoEvent
        data class SetUserScrollEnabled(val enabled: Boolean) : DSWantedNumberPickerDemoEvent
        data class ShowCode(val show: Boolean) : DSWantedNumberPickerDemoEvent
        data object CopyCode : DSWantedNumberPickerDemoEvent
    }

    data class DSWantedNumberPickerDemoViewState(
        val start: Int = 1,
        val end: Int = 12,
        val step: Int = 1,
        val selectedValue: Int = 1,
        val userScrollEnabled: Boolean = true,
        val isShowCode: Boolean = false,
        val code: String = ""
    ) : BaseViewState

    sealed interface DSWantedNumberPickerDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedNumberPickerDemoSideEffect
    }

    sealed interface DSWantedNumberPickerDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedNumberPickerDemoViewEvent
        data object OnClickShowCode : DSWantedNumberPickerDemoViewEvent
        data object OnClickCopyCode : DSWantedNumberPickerDemoViewEvent
        data class OnChangeUserScrollEnabled(val enabled: Boolean) : DSWantedNumberPickerDemoViewEvent
        data class OnChangeStart(val start: Int) : DSWantedNumberPickerDemoViewEvent
        data class OnChangeEnd(val end: Int) : DSWantedNumberPickerDemoViewEvent
        data class OnChangeStep(val step: Int) : DSWantedNumberPickerDemoViewEvent
    }
}
