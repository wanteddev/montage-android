package com.wanted.android.montage.sample.input.input

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.input.input.control.CheckBoxState
import com.wanted.android.wanted.design.input.input.WantedInputDefaults.WantedInputSize
import com.wanted.android.wanted.design.input.input.WantedInputDefaults.WantedInputVariant

object DSWantedInputDemoScreenContract {
    sealed interface DSWantedInputDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedInputDemoViewState) : DSWantedInputDemoEvent
        data object OnClickCopyCode : DSWantedInputDemoEvent
        data class OnChangeText(val text: Boolean) : DSWantedInputDemoEvent
        data class OnSelectType(val type: WantedInputVariant) : DSWantedInputDemoEvent
        data class OnSelectSize(val size: WantedInputSize) : DSWantedInputDemoEvent
        data class OnChangeBold(val bold: Boolean) : DSWantedInputDemoEvent
        data class OnChangeEnabled(val enabled: Boolean) : DSWantedInputDemoEvent
        data class OnChangeTight(val tight: Boolean) : DSWantedInputDemoEvent
        data class OnSelectCheckBoxState(val checkBoxState: CheckBoxState) : DSWantedInputDemoEvent
        data class ShowCode(val isShowCode: Boolean) : DSWantedInputDemoEvent
    }

    data class DSWantedInputDemoViewState(
        val isLoading: Boolean = true,
        val text: Boolean = true,
        val type: WantedInputVariant = WantedInputVariant.CheckBox,
        val size: WantedInputSize = WantedInputSize.Small,
        val checkBoxState: CheckBoxState = CheckBoxState.Unchecked,
        val bold: Boolean = true,
        val enabled: Boolean = true,
        val tight: Boolean = true,
        val code: String = "",
        val isShowCode: Boolean = false
    ) : BaseViewState

    sealed interface DSWantedInputDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedInputDemoSideEffect
    }

    sealed interface DSWantedInputDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedInputDemoViewEvent
        data object OnClickCopyCode : DSWantedInputDemoViewEvent
        data class OnChangeText(val text: Boolean) : DSWantedInputDemoViewEvent
        data class OnSelectType(val type: WantedInputVariant) : DSWantedInputDemoViewEvent
        data class OnSelectSize(val size: WantedInputSize) : DSWantedInputDemoViewEvent
        data class OnSelectCheckBoxState(val checkBoxState: CheckBoxState) : DSWantedInputDemoViewEvent
        data class OnChangeBold(val bold: Boolean) : DSWantedInputDemoViewEvent
        data class OnChangeEnabled(val enabled: Boolean) : DSWantedInputDemoViewEvent
        data class OnChangeTight(val tight: Boolean) : DSWantedInputDemoViewEvent
        data object OnClickShowCode : DSWantedInputDemoViewEvent
    }
}