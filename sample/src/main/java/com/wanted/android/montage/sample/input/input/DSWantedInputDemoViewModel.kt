package com.wanted.android.montage.sample.input.input

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.input.input.DSWantedInputDemoScreenContract.DSWantedInputDemoEvent
import com.wanted.android.montage.sample.input.input.DSWantedInputDemoScreenContract.DSWantedInputDemoSideEffect
import com.wanted.android.montage.sample.input.input.DSWantedInputDemoScreenContract.DSWantedInputDemoSideEffect.CopyCode
import com.wanted.android.montage.sample.input.input.DSWantedInputDemoScreenContract.DSWantedInputDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedInputDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedInputDemoEvent, DSWantedInputDemoViewState, DSWantedInputDemoSideEffect>() {
    override fun setInitialState() = DSWantedInputDemoViewState()

    override fun handleEvents(event: DSWantedInputDemoEvent) {
        when (event) {
            is DSWantedInputDemoEvent.InitState -> setState { event.viewState }
            is DSWantedInputDemoEvent.OnChangeBold -> {
                setState { copy(bold = event.bold) }
            }

            is DSWantedInputDemoEvent.OnChangeEnabled -> {
                setState { copy(enabled = event.enabled) }
            }

            is DSWantedInputDemoEvent.OnChangeText -> {
                setState { copy(text = event.text) }
            }

            is DSWantedInputDemoEvent.OnChangeTight -> {
                setState { copy(tight = event.tight) }
            }

            is DSWantedInputDemoEvent.OnClickCopyCode -> {
                setEffect { CopyCode(getCode()) }
            }

            is DSWantedInputDemoEvent.OnSelectCheckBoxState -> {
                setState { copy(checkBoxState = event.checkBoxState) }
            }

            is DSWantedInputDemoEvent.OnSelectSize -> {
                setState { copy(size = event.size) }
            }

            is DSWantedInputDemoEvent.OnSelectType -> {
                setState { copy(type = event.type) }

            }

            is DSWantedInputDemoEvent.ShowCode -> {
                setState {
                    copy(
                        isShowCode = event.isShowCode,
                        code = getCode()
                    )
                }
            }
        }
    }

    private fun getCode(): String {
        return """
             WantedInput(
                modifier = Modifier,
                text = ${if (viewState.value.text) "text 입니다." else ""},
                type = WantedInputType.${viewState.value.type},
                size = WantedInputSize.${viewState.value.size},
                checkBoxState = CheckBoxState.${viewState.value.checkBoxState},
                bold = ${viewState.value.bold},
                enabled =  ${viewState.value.enabled},
                tight = ${viewState.value.tight},
                onCheckedChange = { }
            )
        """.trimIndent()
    }

}