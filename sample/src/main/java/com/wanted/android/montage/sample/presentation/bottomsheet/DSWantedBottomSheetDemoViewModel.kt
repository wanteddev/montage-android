package com.wanted.android.montage.sample.presentation.bottomsheet

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.presentation.bottomsheet.DSWantedBottomSheetDemoScreenContract.DSWantedBottomSheetDemoEvent
import com.wanted.android.montage.sample.presentation.bottomsheet.DSWantedBottomSheetDemoScreenContract.DSWantedBottomSheetDemoSideEffect
import com.wanted.android.montage.sample.presentation.bottomsheet.DSWantedBottomSheetDemoScreenContract.DSWantedBottomSheetDemoViewState
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalSize
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedBottomSheetDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedBottomSheetDemoEvent, DSWantedBottomSheetDemoViewState, DSWantedBottomSheetDemoSideEffect>() {
    override fun setInitialState() = DSWantedBottomSheetDemoViewState()

    override fun handleEvents(event: DSWantedBottomSheetDemoEvent) {
        when (event) {
            is DSWantedBottomSheetDemoEvent.InitState -> setState { event.viewState }
            is DSWantedBottomSheetDemoEvent.ShowCode -> {
                setState { copy(isShowCode = event.isShowCode, code = getCode()) }
            }

            DSWantedBottomSheetDemoEvent.CopyCode -> copyCode()
            is DSWantedBottomSheetDemoEvent.SetModalType -> setState { copy(modalType = event.type) }
            is DSWantedBottomSheetDemoEvent.SetModalSize -> setState { copy(modalSize = event.size) }
            is DSWantedBottomSheetDemoEvent.SetDismissOnClickOutside -> {
                setState { copy(dismissOnClickOutside = event.dismiss) }
            }

            is DSWantedBottomSheetDemoEvent.SetShowSheet -> setState { copy(isShowSheet = event.show) }
        }
    }

    private fun copyCode() {
        setEffect { DSWantedBottomSheetDemoSideEffect.CopyCode(getCode()) }
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
        val sizeString = when (state.modalSize) {
            ModalSize.Small -> "ModalSize.Small"
            ModalSize.Medium -> "ModalSize.Medium"
            ModalSize.Large -> "ModalSize.Large"
            ModalSize.XLarge -> "ModalSize.XLarge"
            ModalSize.Custom -> "ModalSize.Custom"
        }

        return """
            WantedModalBottomSheet(
                isShow = ${state.isShowSheet},
                onDismissRequest = { /* on dismiss */ },
                type = $typeString,
                modalSize = $sizeString,
                dismissOnClickOutside = ${state.dismissOnClickOutside},
                content = { Text(\"Bottom Sheet\") }
            )
        """.trimIndent()
    }
}