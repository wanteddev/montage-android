package com.wanted.android.montage.sample.input.numberpicker

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.input.numberpicker.DSWantedNumberPickerDemoScreenContract.DSWantedNumberPickerDemoEvent
import com.wanted.android.montage.sample.input.numberpicker.DSWantedNumberPickerDemoScreenContract.DSWantedNumberPickerDemoSideEffect
import com.wanted.android.montage.sample.input.numberpicker.DSWantedNumberPickerDemoScreenContract.DSWantedNumberPickerDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedNumberPickerDemoViewModel @Inject constructor() :
    WantedStateViewModel<DSWantedNumberPickerDemoEvent, DSWantedNumberPickerDemoViewState, DSWantedNumberPickerDemoSideEffect>() {

    override fun setInitialState() = DSWantedNumberPickerDemoViewState()

    override fun handleEvents(event: DSWantedNumberPickerDemoEvent) {
        when (event) {
            is DSWantedNumberPickerDemoEvent.SetStart -> {
                setState { copy(start = event.start, selectedValue = event.start) }
            }

            is DSWantedNumberPickerDemoEvent.SetEnd -> {
                setState { copy(end = event.end) }
            }

            is DSWantedNumberPickerDemoEvent.SetStep -> {
                val safeStep = if (event.step < 1) 1 else event.step
                setState { copy(step = safeStep, selectedValue = start) }
            }

            is DSWantedNumberPickerDemoEvent.SetSelectedValue -> {
                setState { copy(selectedValue = event.value) }
            }

            is DSWantedNumberPickerDemoEvent.SetUserScrollEnabled -> {
                setState { copy(userScrollEnabled = event.enabled) }
            }

            is DSWantedNumberPickerDemoEvent.ShowCode -> {
                showCode(event.show)
            }

            is DSWantedNumberPickerDemoEvent.CopyCode -> {
                setEffect { DSWantedNumberPickerDemoSideEffect.CopyCode(getCode()) }
            }
        }
    }

    private fun showCode(isShowCode: Boolean) {
        setState {
            copy(
                code = getCode(),
                isShowCode = isShowCode
            )
        }
    }

    private fun getCode(): String {
        return with(viewState.value) {
            """
            WantedNumberPicker(
                start = $start,
                end = $end,
                step = $step,
                selectedValue = $selectedValue,
                userScrollEnabled = $userScrollEnabled,
                onSelect = { index, value, enabled ->
                    // handle selection
                }
            )
            """.trimIndent()
        }
    }
}
