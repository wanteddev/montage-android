package com.wanted.android.montage.sample.feedback.alert

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent

object DSWantedAlertDemoScreenContract {
	sealed interface DSWantedAlertDemoEvent : BaseEvent {
		data object ShowAlert : DSWantedAlertDemoEvent
		data object DismissAlert : DSWantedAlertDemoEvent
		data object ToggleTitle : DSWantedAlertDemoEvent
		data object ToggleNegative : DSWantedAlertDemoEvent
		data object ToggleCancel : DSWantedAlertDemoEvent
	}

	data class DSWantedAlertDemoViewState(
		val isShowAlert: Boolean = false,
		val hasTitle: Boolean = true,
		val isNegative: Boolean = false,
		val hasCancel: Boolean = true,
	) : BaseViewState

	sealed interface DSWantedAlertDemoSideEffect : BaseSideEffect

	sealed interface DSWantedAlertDemoViewEvent : ViewEvent {
		data object OnClickBack : DSWantedAlertDemoViewEvent
		data object OnClickShowAlert : DSWantedAlertDemoViewEvent
		data object OnDismissAlert : DSWantedAlertDemoViewEvent
		data object OnClickConfirm : DSWantedAlertDemoViewEvent
		data object OnClickCancel : DSWantedAlertDemoViewEvent
		data object OnClickToggleTitle : DSWantedAlertDemoViewEvent
		data object OnClickToggleNegative : DSWantedAlertDemoViewEvent
		data object OnClickToggleCancel : DSWantedAlertDemoViewEvent
	}
}
