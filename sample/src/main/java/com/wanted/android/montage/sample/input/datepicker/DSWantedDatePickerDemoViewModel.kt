package com.wanted.android.montage.sample.input.datepicker

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.input.datepicker.DSWantedDatePickerDemoScreenContract.DSWantedDatePickerDemoEvent
import com.wanted.android.montage.sample.input.datepicker.DSWantedDatePickerDemoScreenContract.DSWantedDatePickerDemoSideEffect
import com.wanted.android.montage.sample.input.datepicker.DSWantedDatePickerDemoScreenContract.DSWantedDatePickerDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedDatePickerDemoViewModel @Inject constructor() :
	WantedStateViewModel<DSWantedDatePickerDemoEvent, DSWantedDatePickerDemoViewState, DSWantedDatePickerDemoSideEffect>() {

	override fun setInitialState() = DSWantedDatePickerDemoViewState()

	override fun handleEvents(event: DSWantedDatePickerDemoEvent) {
		when (event) {
			DSWantedDatePickerDemoEvent.ShowDatePicker -> setState { copy(isShowDatePicker = true) }
			DSWantedDatePickerDemoEvent.DismissDatePicker -> setState { copy(isShowDatePicker = false) }
		}
	}

	fun onDateSelected(dateMillis: Long?) {
		setState { copy(selectedDateMillis = dateMillis, isShowDatePicker = false) }
	}
}
