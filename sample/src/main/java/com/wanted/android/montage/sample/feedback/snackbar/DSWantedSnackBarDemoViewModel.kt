package com.wanted.android.montage.sample.feedback.snackbar

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.feedback.snackbar.DSWantedSnackBarDemoScreenContract.DSWantedSnackBarDemoEvent
import com.wanted.android.montage.sample.feedback.snackbar.DSWantedSnackBarDemoScreenContract.DSWantedSnackBarDemoSideEffect
import com.wanted.android.montage.sample.feedback.snackbar.DSWantedSnackBarDemoScreenContract.DSWantedSnackBarDemoViewState
import com.wanted.android.montage.sample.feedback.snackbar.DSWantedSnackBarDemoScreenContract.SnackBarOption
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedSnackBarDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedSnackBarDemoEvent, DSWantedSnackBarDemoViewState, DSWantedSnackBarDemoSideEffect>() {

    override fun setInitialState() = DSWantedSnackBarDemoViewState()

    override fun handleEvents(event: DSWantedSnackBarDemoEvent) {
        when (event) {
            is DSWantedSnackBarDemoEvent.SetOption -> {
                setState { copy(selectedOption = event.option) }
            }

            is DSWantedSnackBarDemoEvent.ShowSnackBar -> {
                setEffect { DSWantedSnackBarDemoSideEffect.ShowSnackBar(viewState.value.selectedOption) }
            }

            is DSWantedSnackBarDemoEvent.CopyCode -> {
                setEffect { DSWantedSnackBarDemoSideEffect.CopyCode(getCode()) }
            }

            is DSWantedSnackBarDemoEvent.ShowCode -> {
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
        return when (viewState.value.selectedOption) {
            SnackBarOption.MessageOnly -> """
                WantedSnackbarVisuals(
                    message = "메시지에 마침표를 찍어요."
                )
            """.trimIndent()

            SnackBarOption.MessageWithButton -> """
                WantedSnackbarVisuals(
                    message = "메시지에 마침표를 찍어요.",
                    actionLabel = "버튼"
                )
            """.trimIndent()

            SnackBarOption.MessageWithDescription -> """
                WantedSnackbarVisuals(
                    message = "메시지에 마침표를 찍어요.",
                    description = "설명은 필요할 때만 써요."
                )
            """.trimIndent()

            SnackBarOption.MessageWithDescriptionAndButtonAndIcon -> """
                WantedSnackbarVisuals(
                    message = "메시지에 마침표를 찍어요.",
                    description = "설명은 필요할 때만 써요.",
                    actionLabel = "버튼",
                    icon = { /* Icon composable */ }
                )
            """.trimIndent()
        }
    }
}
