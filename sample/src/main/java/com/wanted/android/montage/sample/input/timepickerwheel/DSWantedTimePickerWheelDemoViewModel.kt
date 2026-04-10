package com.wanted.android.montage.sample.input.timepickerwheel

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.input.timepickerwheel.DSWantedTimePickerWheelDemoScreenContract.DSWantedTimePickerWheelDemoEvent
import com.wanted.android.montage.sample.input.timepickerwheel.DSWantedTimePickerWheelDemoScreenContract.DSWantedTimePickerWheelDemoSideEffect
import com.wanted.android.montage.sample.input.timepickerwheel.DSWantedTimePickerWheelDemoScreenContract.DSWantedTimePickerWheelDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedTimePickerWheelDemoViewModel @Inject constructor() :
	WantedStateViewModel<DSWantedTimePickerWheelDemoEvent, DSWantedTimePickerWheelDemoViewState, DSWantedTimePickerWheelDemoSideEffect>() {

	override fun setInitialState() = DSWantedTimePickerWheelDemoViewState()

	override fun handleEvents(event: DSWantedTimePickerWheelDemoEvent) {
		when (event) {
			is DSWantedTimePickerWheelDemoEvent.ShowPicker -> setState { copy(isShowPicker = event.isShow) }
			is DSWantedTimePickerWheelDemoEvent.SetIsAm -> setState { copy(isAm = event.isAm) }
			is DSWantedTimePickerWheelDemoEvent.SetEnableAm -> setState { copy(enableAm = event.enableAm) }
			is DSWantedTimePickerWheelDemoEvent.SetEnablePm -> setState { copy(enablePm = event.enablePm) }
			is DSWantedTimePickerWheelDemoEvent.SetIsHideDisableTime -> setState { copy(isHideDisableTime = event.isHide) }
			is DSWantedTimePickerWheelDemoEvent.SetSelectedTime -> setState {
				copy(
					isAm = event.isAm,
					hour = event.hour,
					minute = event.minute,
					selectedTimeText = "${if (event.isAm) "AM" else "PM"} ${event.hour}:${event.minute.toString().padStart(2, '0')}",
					isShowPicker = false,
				)
			}
		}
	}
}
