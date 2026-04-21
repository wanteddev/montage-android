package com.wanted.android.montage.sample.presentation.tooltip

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.presentation.tooltip.DSWantedTooltipDemoScreenContract.DSWantedTooltipDemoEvent
import com.wanted.android.montage.sample.presentation.tooltip.DSWantedTooltipDemoScreenContract.DSWantedTooltipDemoSideEffect
import com.wanted.android.montage.sample.presentation.tooltip.DSWantedTooltipDemoScreenContract.DSWantedTooltipDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedTooltipDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedTooltipDemoEvent, DSWantedTooltipDemoViewState, DSWantedTooltipDemoSideEffect>() {
    override fun setInitialState() = DSWantedTooltipDemoViewState()

    override fun handleEvents(event: DSWantedTooltipDemoEvent) {
        when (event) {
            is DSWantedTooltipDemoEvent.InitState -> setState { event.viewState }
            is DSWantedTooltipDemoEvent.OnChangeAlways -> {
                setState { copy(always = event.always) }
            }

            is DSWantedTooltipDemoEvent.OnChangeSize -> {
                setState { copy(size = event.size) }
            }

            is DSWantedTooltipDemoEvent.OnChangeAlign -> {
                setState { copy(align = event.align) }
            }

            is DSWantedTooltipDemoEvent.OnChangePositionTop -> {
                setState { copy(positionTop = event.positionTop) }
            }

            is DSWantedTooltipDemoEvent.CopyCode -> {
                setEffect { DSWantedTooltipDemoSideEffect.CopyCode(getCode()) }
            }

            is DSWantedTooltipDemoEvent.ShowCode -> {
                showCode(event.show)
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
        val state = viewState.value
        return """
WantedTooltip(
    modifier = Modifier.wrapContentSize(),
    tooltipState = tooltipState,
    size = WantedTooltipSize.${state.size},
    align = WantedTooltipAlign.${state.align},
    positionTop = ${state.positionTop},
    always = ${state.always},
    text = "아주긴 텍스트로 툴팁을 띄웠을 때 이렇게 됩니다.",
    content = {
        WantedButton(
            modifier = Modifier,
            text = "text",
            onClick = {
                if (tooltipState.isVisible) {
                    tooltipState.dismiss()
                } else {
                    tooltipState.show()
                }
            }
        )
    }
)
        """.trimIndent()
    }

}