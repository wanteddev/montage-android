package com.wanted.android.montage.sample.input.datepickerwheel

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.input.datepickerwheel.DSWantedDatePickerWheelDemoScreenContract.DSWantedDatePickerWheelDemoEvent
import com.wanted.android.montage.sample.input.datepickerwheel.DSWantedDatePickerWheelDemoScreenContract.DSWantedDatePickerWheelDemoSideEffect
import com.wanted.android.montage.sample.input.datepickerwheel.DSWantedDatePickerWheelDemoScreenContract.DSWantedDatePickerWheelDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedDatePickerWheelDemoViewModel @Inject constructor() :
    WantedStateViewModel<DSWantedDatePickerWheelDemoEvent, DSWantedDatePickerWheelDemoViewState, DSWantedDatePickerWheelDemoSideEffect>() {

    override fun setInitialState() = DSWantedDatePickerWheelDemoViewState()

    override fun handleEvents(event: DSWantedDatePickerWheelDemoEvent) {
        when (event) {
            DSWantedDatePickerWheelDemoEvent.ShowDatePickerWheel -> setState { copy(isShowDatePickerWheel = true) }
            DSWantedDatePickerWheelDemoEvent.DismissDatePickerWheel -> setState { copy(isShowDatePickerWheel = false) }
            is DSWantedDatePickerWheelDemoEvent.SelectDate -> setState {
                copy(
                    selectedYear = event.year,
                    selectedMonth = event.month,
                    isShowDatePickerWheel = false
                )
            }
            DSWantedDatePickerWheelDemoEvent.ToggleHideDisableDate -> setState { copy(isHideDisableDate = !isHideDisableDate) }
        }
    }
}
