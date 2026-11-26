package com.wanted.android.montage.sample.input.focusring

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.input.focusring.DSWantedFocusRingDemoScreenContract.DSWantedFocusRingDemoEvent
import com.wanted.android.montage.sample.input.focusring.DSWantedFocusRingDemoScreenContract.DSWantedFocusRingDemoSideEffect
import com.wanted.android.montage.sample.input.focusring.DSWantedFocusRingDemoScreenContract.DSWantedFocusRingDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedFocusRingDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedFocusRingDemoEvent, DSWantedFocusRingDemoViewState, DSWantedFocusRingDemoSideEffect>() {
    override fun setInitialState() = DSWantedFocusRingDemoViewState()

    override fun handleEvents(event: DSWantedFocusRingDemoEvent) {
        when (event) {
            is DSWantedFocusRingDemoEvent.CopyCode -> {
                setEffect { DSWantedFocusRingDemoSideEffect.CopyCode(getCode()) }
            }

            is DSWantedFocusRingDemoEvent.SetVisible -> {
                setState { copy(visible = event.visible) }
            }

            is DSWantedFocusRingDemoEvent.SetNegative -> {
                setState { copy(negative = event.negative) }
            }

            is DSWantedFocusRingDemoEvent.ShowCode -> {
                setState { copy(code = getCode(), isShowCode = event.show) }
            }
        }
    }

    private fun getCode(): String {
        val state = viewState.value
        val color = if (state.negative) "lineNegativeFocus" else "lineBrandFocus"
        return """
            Box(
                Modifier
                .size(50.dp)
                .focusRing(
                    visible = ${state.visible},
                    shape = RoundedCornerShape(14.dp),
                    color = DesignSystemTheme.colors.$color,
                )
            )
            """
    }
}
