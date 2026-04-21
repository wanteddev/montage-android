package com.wanted.android.montage.sample.input.datepickerwheel

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent

object DSWantedDatePickerWheelDemoScreenContract {
    sealed interface DSWantedDatePickerWheelDemoEvent : BaseEvent {
        data object ShowDatePickerWheel : DSWantedDatePickerWheelDemoEvent
        data object DismissDatePickerWheel : DSWantedDatePickerWheelDemoEvent
        data class SelectDate(val year: Int, val month: Int) : DSWantedDatePickerWheelDemoEvent
        data object ToggleHideDisableDate : DSWantedDatePickerWheelDemoEvent
    }

    data class DSWantedDatePickerWheelDemoViewState(
        val isShowDatePickerWheel: Boolean = false,
        val selectedYear: Int = 2025,
        val selectedMonth: Int = 1,
        val isHideDisableDate: Boolean = false,
    ) : BaseViewState

    sealed interface DSWantedDatePickerWheelDemoSideEffect : BaseSideEffect

    sealed interface DSWantedDatePickerWheelDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedDatePickerWheelDemoViewEvent
        data object OnClickShowDatePickerWheel : DSWantedDatePickerWheelDemoViewEvent
        data object OnDismissDatePickerWheel : DSWantedDatePickerWheelDemoViewEvent
        data class OnSelectDate(val year: Int, val month: Int) : DSWantedDatePickerWheelDemoViewEvent
        data object OnClickToggleHideDisableDate : DSWantedDatePickerWheelDemoViewEvent
    }
}
