package com.wanted.android.montage.sample.input.datepicker

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent

object DSWantedDatePickerDemoScreenContract {
	sealed interface DSWantedDatePickerDemoEvent : BaseEvent {
		data object ShowDatePicker : DSWantedDatePickerDemoEvent
		data object DismissDatePicker : DSWantedDatePickerDemoEvent
	}

	data class DSWantedDatePickerDemoViewState(
		val isShowDatePicker: Boolean = false,
		val selectedDateMillis: Long? = null,
	) : BaseViewState

	sealed interface DSWantedDatePickerDemoSideEffect : BaseSideEffect

	sealed interface DSWantedDatePickerDemoViewEvent : ViewEvent {
		data object OnClickBack : DSWantedDatePickerDemoViewEvent
		data object OnClickShowDatePicker : DSWantedDatePickerDemoViewEvent
		data object OnDismissDatePicker : DSWantedDatePickerDemoViewEvent
		data class OnDateSelected(val dateMillis: Long?) : DSWantedDatePickerDemoViewEvent
	}
}
