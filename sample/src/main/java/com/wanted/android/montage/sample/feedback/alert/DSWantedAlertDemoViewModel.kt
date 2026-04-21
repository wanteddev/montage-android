package com.wanted.android.montage.sample.feedback.alert

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.feedback.alert.DSWantedAlertDemoScreenContract.DSWantedAlertDemoEvent
import com.wanted.android.montage.sample.feedback.alert.DSWantedAlertDemoScreenContract.DSWantedAlertDemoSideEffect
import com.wanted.android.montage.sample.feedback.alert.DSWantedAlertDemoScreenContract.DSWantedAlertDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedAlertDemoViewModel @Inject constructor() :
	WantedStateViewModel<DSWantedAlertDemoEvent, DSWantedAlertDemoViewState, DSWantedAlertDemoSideEffect>() {

	override fun setInitialState() = DSWantedAlertDemoViewState()

	override fun handleEvents(event: DSWantedAlertDemoEvent) {
		when (event) {
			DSWantedAlertDemoEvent.ShowAlert -> setState { copy(isShowAlert = true) }
			DSWantedAlertDemoEvent.DismissAlert -> setState { copy(isShowAlert = false) }
			DSWantedAlertDemoEvent.ToggleTitle -> setState { copy(hasTitle = !hasTitle) }
			DSWantedAlertDemoEvent.ToggleNegative -> setState { copy(isNegative = !isNegative) }
			DSWantedAlertDemoEvent.ToggleCancel -> setState { copy(hasCancel = !hasCancel) }
		}
	}
}
