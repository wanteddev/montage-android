package com.wanted.android.montage.sample.actions.actionarea

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.actions.actionarea.DSWantedActionAreaDemoScreenContract.DSWantedActionAreaDemoEvent
import com.wanted.android.montage.sample.actions.actionarea.DSWantedActionAreaDemoScreenContract.DSWantedActionAreaDemoSideEffect
import com.wanted.android.montage.sample.actions.actionarea.DSWantedActionAreaDemoScreenContract.DSWantedActionAreaDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedActionAreaDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedActionAreaDemoEvent, DSWantedActionAreaDemoViewState, DSWantedActionAreaDemoSideEffect>() {
    override fun setInitialState() = DSWantedActionAreaDemoViewState()

    override fun handleEvents(event: DSWantedActionAreaDemoEvent) {
        when (event) {
            is DSWantedActionAreaDemoEvent.InitState -> setState { event.viewState }
            is DSWantedActionAreaDemoEvent.ShowCode -> setState {
                copy(isShowCode = event.isShowCode, code = buildCode(this))
            }

            is DSWantedActionAreaDemoEvent.Sample -> {
                // 데모 화면에서는 ShowAll을 코드 보기와 동일하게 처리
                setState { copy(isShowSample = event.isShowSample) }
            }

            is DSWantedActionAreaDemoEvent.CopyCode -> {
                setEffect { DSWantedActionAreaDemoSideEffect.CopyCode(viewState.value.code) }
            }

            is DSWantedActionAreaDemoEvent.SetType -> setState { copy(type = event.type) }
            is DSWantedActionAreaDemoEvent.SetSafeArea -> setState { copy(safeArea = event.safeArea) }
            is DSWantedActionAreaDemoEvent.SetCaption -> setState { copy(caption = event.caption) }
            is DSWantedActionAreaDemoEvent.SetDivider -> setState { copy(divider = event.divider) }
            is DSWantedActionAreaDemoEvent.SetNegative -> setState { copy(negative = event.negative) }
            is DSWantedActionAreaDemoEvent.SetNeutral -> setState { copy(neutral = event.neutral) }
            is DSWantedActionAreaDemoEvent.SetExtra -> setState { copy(extra = event.extra) }
            is DSWantedActionAreaDemoEvent.SetBackground -> setState { copy(background = event.background) }
            is DSWantedActionAreaDemoEvent.SetGradationColor -> setState { copy(gradationColorIndex = event.colorIndex) }
        }
    }

    private fun buildCode(state: DSWantedActionAreaDemoViewState): String {
        fun boolStr(b: Boolean) = b.toString()
        val type = state.type.name
        val captionLine = if (state.caption) "\n    caption = \"캡션 입니다.\"," else ""
        val negativeSlot =
            if (state.negative) ",\n    negative = { /* WantedButton(...) */ }" else ""
        val neutralSlot = if (state.neutral) ",\n    neutral = { /* WantedButton(...) */ }" else ""
        val extraSlot = if (state.extra) ",\n    extra = { /* Your extra composable */ }" else ""
        val colorHex = "0x${
            state.gradationColorList[state.gradationColorIndex].color.toString(16).uppercase()
                .padStart(8, '0')
        }"
        val gradationColorLine =
            if (state.background) "\n    gradationColor = Color($colorHex)," else ""
        return """
            WantedActionArea(
                type = ActionAreaType.$type,
                safeArea = ${boolStr(state.safeArea)},
                background = ${boolStr(state.background)},
                divider = ${boolStr(state.divider)},$gradationColorLine$captionLine
                positive = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = \"positive\"
                    )
                }$negativeSlot$neutralSlot$extraSlot
            )
        """.trimMargin()
    }
}