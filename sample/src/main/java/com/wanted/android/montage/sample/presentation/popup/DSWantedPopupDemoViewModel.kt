package com.wanted.android.montage.sample.presentation.popup

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.presentation.popup.DSWantedPopupDemoScreenContract.DSWantedPopupDemoEvent
import com.wanted.android.montage.sample.presentation.popup.DSWantedPopupDemoScreenContract.DSWantedPopupDemoSideEffect
import com.wanted.android.montage.sample.presentation.popup.DSWantedPopupDemoScreenContract.DSWantedPopupDemoViewState
import com.wanted.android.wanted.design.actions.actionarea.ActionAreaType
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopupContract.Resize
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedPopupDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedPopupDemoEvent, DSWantedPopupDemoViewState, DSWantedPopupDemoSideEffect>() {
    override fun setInitialState() = DSWantedPopupDemoViewState()

    override fun handleEvents(event: DSWantedPopupDemoEvent) {
        when (event) {
            is DSWantedPopupDemoEvent.InitState -> setState { event.viewState }
            is DSWantedPopupDemoEvent.ShowCode -> {
                setState { copy(isShowCode = event.isShowCode, code = getCode()) }
            }

            DSWantedPopupDemoEvent.CopyCode -> copyCode()
            is DSWantedPopupDemoEvent.SetShowPopup -> setState { copy(showPopup = event.show) }
            is DSWantedPopupDemoEvent.SetResize -> setState { copy(resize = event.resize) }
            is DSWantedPopupDemoEvent.SetUseTopBar -> setState { copy(useTopBar = event.use) }
            is DSWantedPopupDemoEvent.SetUseActionArea -> {
                setState { copy(useActionArea = event.use) }
            }
            is DSWantedPopupDemoEvent.SetActionAreaType -> {
                setState { copy(actionAreaType = event.actionAreaType) }
            }
        }
    }

    private fun copyCode() {
        setEffect { DSWantedPopupDemoSideEffect.CopyCode(getCode()) }
    }

    private fun getCode(): String {
        val state = viewState.value
        val resizeString = when (val resize = state.resize) {
            Resize.Hug -> "Resize.Hug"
            is Resize.Fixed -> "Resize.Fixed(height = ${resize.height.value}.dp)"
        }
        val titleLine = if (state.useTopBar) {
            "title = \"제목\","
        } else {
            "title = null,"
        }
        // 기본값(Strong)이거나 Action Area 를 쓰지 않을 때는 popupDefault 를 넘길 필요가 없다.
        val popupDefaultLine =
            if (!state.useActionArea || state.actionAreaType == ActionAreaType.Strong) {
                ""
            } else {
                "\n                    popupDefault = WantedPopupDefaults.getDefault(" +
                    "actionAreaType = ActionAreaType.${state.actionAreaType.name}),"
            }
        // onClickPositive 가 null 이면 Popup 은 Action Area 를 그리지 않는다.
        val actionLines = if (state.useActionArea) {
            "\n                    positive = \"확인\"," +
                "\n                    onClickPositive = { showPopup = false },"
        } else {
            ""
        }

        return """
            if (showPopup) {
                WantedPopup(
                    onDismissRequest = { showPopup = false },
                    resize = $resizeString,$popupDefaultLine
                    $titleLine$actionLines
                    content = { Text("Popup Content") }
                )
            }
        """.trimIndent()
    }
}
