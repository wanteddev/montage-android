package com.wanted.android.montage.sample.input.timepickerwheel

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent

object DSWantedTimePickerWheelDemoScreenContract {
	sealed interface DSWantedTimePickerWheelDemoEvent : BaseEvent {
		data class ShowPicker(val isShow: Boolean) : DSWantedTimePickerWheelDemoEvent
		data class SetIsAm(val isAm: Boolean) : DSWantedTimePickerWheelDemoEvent
		data class SetEnableAm(val enableAm: Boolean) : DSWantedTimePickerWheelDemoEvent
		data class SetEnablePm(val enablePm: Boolean) : DSWantedTimePickerWheelDemoEvent
		data class SetIsHideDisableTime(val isHide: Boolean) : DSWantedTimePickerWheelDemoEvent
		data class SetSelectedTime(
			val isAm: Boolean,
			val hour: Int,
			val minute: Int
		) : DSWantedTimePickerWheelDemoEvent
	}

	data class DSWantedTimePickerWheelDemoViewState(
		val isShowPicker: Boolean = false,
		val isAm: Boolean = true,
		val hour: Int = 9,
		val minute: Int = 0,
		val enableAm: Boolean = true,
		val enablePm: Boolean = true,
		val isHideDisableTime: Boolean = false,
		val selectedTimeText: String = "선택된 시간 없음",
	) : BaseViewState

	sealed interface DSWantedTimePickerWheelDemoSideEffect : BaseSideEffect

	sealed interface DSWantedTimePickerWheelDemoViewEvent : ViewEvent {
		data object OnClickBack : DSWantedTimePickerWheelDemoViewEvent
		data object OnClickOpenPicker : DSWantedTimePickerWheelDemoViewEvent
		data object OnDismissPicker : DSWantedTimePickerWheelDemoViewEvent
		data class OnChangeIsAm(val isAm: Boolean) : DSWantedTimePickerWheelDemoViewEvent
		data class OnChangeEnableAm(val enableAm: Boolean) : DSWantedTimePickerWheelDemoViewEvent
		data class OnChangeEnablePm(val enablePm: Boolean) : DSWantedTimePickerWheelDemoViewEvent
		data class OnChangeIsHideDisableTime(val isHide: Boolean) : DSWantedTimePickerWheelDemoViewEvent
		data class OnTimeSelected(
			val isAm: Boolean,
			val hour: Int,
			val minute: Int
		) : DSWantedTimePickerWheelDemoViewEvent
	}
}
