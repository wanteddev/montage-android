package com.wanted.android.montage.sample.feedback.snackbar

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent

object DSWantedSnackBarDemoScreenContract {

    enum class SnackBarOption {
        MessageOnly,
        MessageWithButton,
        MessageWithDescription,
        MessageWithDescriptionAndButtonAndIcon,
    }

    sealed interface DSWantedSnackBarDemoEvent : BaseEvent {
        data class SetOption(val option: SnackBarOption) : DSWantedSnackBarDemoEvent
        data object ShowSnackBar : DSWantedSnackBarDemoEvent
        data object CopyCode : DSWantedSnackBarDemoEvent
        data class ShowCode(val isShowCode: Boolean) : DSWantedSnackBarDemoEvent
    }

    data class DSWantedSnackBarDemoViewState(
        val selectedOption: SnackBarOption = SnackBarOption.MessageOnly,
        val code: String = "",
        val isShowCode: Boolean = false,
    ) : BaseViewState

    sealed interface DSWantedSnackBarDemoSideEffect : BaseSideEffect {
        data class ShowSnackBar(val option: SnackBarOption) : DSWantedSnackBarDemoSideEffect
        data class CopyCode(val code: String) : DSWantedSnackBarDemoSideEffect
    }

    sealed interface DSWantedSnackBarDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedSnackBarDemoViewEvent
        data object OnClickShowSnackBar : DSWantedSnackBarDemoViewEvent
        data class OnClickOption(val option: SnackBarOption) : DSWantedSnackBarDemoViewEvent
        data object OnClickCopyCode : DSWantedSnackBarDemoViewEvent
        data object OnClickShowCode : DSWantedSnackBarDemoViewEvent
    }
}
