package com.wanted.android.montage.sample.feedback.toast

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.feedback.toast.DSWantedToastDemoScreenContract.DSWantedToastDemoEvent
import com.wanted.android.montage.sample.feedback.toast.DSWantedToastDemoScreenContract.DSWantedToastDemoSideEffect
import com.wanted.android.montage.sample.feedback.toast.DSWantedToastDemoScreenContract.DSWantedToastDemoSideEffect.ShowToast
import com.wanted.android.montage.sample.feedback.toast.DSWantedToastDemoScreenContract.DSWantedToastDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedToastDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedToastDemoEvent, DSWantedToastDemoViewState, DSWantedToastDemoSideEffect>() {
    override fun setInitialState() = DSWantedToastDemoViewState()

    override fun handleEvents(event: DSWantedToastDemoEvent) {
        when (event) {
            is DSWantedToastDemoEvent.InitState -> setState { event.viewState }

            is DSWantedToastDemoEvent.ShowToast -> {
                setEffect { ShowToast(viewState.value.toastVariant) }
            }

            is DSWantedToastDemoEvent.SetVariant -> {
                setState { copy(toastVariant = event.variant) }
            }
        }
    }
}