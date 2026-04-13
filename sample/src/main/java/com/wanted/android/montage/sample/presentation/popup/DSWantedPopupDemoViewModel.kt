package com.wanted.android.montage.sample.presentation.popup

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.presentation.popup.DSWantedPopupDemoScreenContract.DSWantedPopupDemoEvent
import com.wanted.android.montage.sample.presentation.popup.DSWantedPopupDemoScreenContract.DSWantedPopupDemoSideEffect
import com.wanted.android.montage.sample.presentation.popup.DSWantedPopupDemoScreenContract.DSWantedPopupDemoViewState
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalType
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
            is DSWantedPopupDemoEvent.SetModalType -> setState { copy(modalType = event.type) }
            is DSWantedPopupDemoEvent.SetUseTopBar -> setState { copy(useTopBar = event.use) }
        }
    }

    private fun copyCode() {
        setEffect { DSWantedPopupDemoSideEffect.CopyCode(getCode()) }
    }

    private fun getCode(): String {
        val state = viewState.value
        val typeString = when (val type = state.modalType) {
            ModalType.Flexible -> "ModalType.Flexible"
            is ModalType.FixedWrapContent -> {
                "ModalType.FixedWrapContent(isCloseable = ${type.isCloseable}, isSystemBottomSheet = ${type.isSystemBottomSheet})"
            }

            is ModalType.Fixed -> {
                "ModalType.Fixed(height = ${type.height.value}.dp, isCloseable = ${type.isCloseable}, isSystemBottomSheet = ${type.isSystemBottomSheet})"
            }

            is ModalType.FixedFullScreen -> {
                "ModalType.FixedFullScreen(isCloseable = ${type.isCloseable}, isSystemBottomSheet = ${type.isSystemBottomSheet})"
            }

            is ModalType.FixedRatio -> {
                "ModalType.FixedRatio(ratio = ${type.ratio}f, isCloseable = ${type.isCloseable}, isSystemBottomSheet = ${type.isSystemBottomSheet})"
            }
        }
        val topBarLine = if (state.useTopBar) {
            "topBar = { WantedDialogTopAppBar(title = \"제목\") },"
        } else {
            "topBar = null,"
        }

        return """
            if (showPopup) {
                WantedModal(
                    onDismissRequest = { showPopup = false },
                    type = $typeString,
                    $topBarLine
                    positive = \"확인\",
                    onClickPositive = { showPopup = false },
                    content = { Text(\"Popup Content\") }
                )
            }
        """.trimIndent()
    }
}