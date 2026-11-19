package com.wanted.android.montage.sample.input.formcontrol

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.input.formcontrol.WantedFormControlDefaults

object DSWantedFormControlDemoScreenContract {
    const val DEFAULT_LABEL_TEXT = "라벨"

    /** 그룹 예시에서 함께 묶는 두 번째 FormControl 의 라벨입니다. */
    const val GROUP_SECOND_LABEL_TEXT = "담당자 연락처"

    /** 그룹 예시에서 labelWidth 를 고정할 때 쓰는 폭(dp)입니다. */
    const val GROUP_FIXED_LABEL_WIDTH_DP = 80

    sealed interface DSWantedFormControlDemoEvent : BaseEvent {
        data class InitState(
            val viewState: DSWantedFormControlDemoViewState
        ) : DSWantedFormControlDemoEvent

        data class ShowCode(val isShowCode: Boolean) : DSWantedFormControlDemoEvent
        data class ShowAll(val isShowAll: Boolean) : DSWantedFormControlDemoEvent
        data object CopyCode : DSWantedFormControlDemoEvent

        data class SetTextFieldValue(val text: String) : DSWantedFormControlDemoEvent

        data class SetLabel(val label: String) : DSWantedFormControlDemoEvent
        data class SetRequired(val required: Boolean) : DSWantedFormControlDemoEvent
        data class SetDescription(val description: Boolean) : DSWantedFormControlDemoEvent
        data class SetAccessory(val accessory: Boolean) : DSWantedFormControlDemoEvent
        data class SetEnabled(val enabled: Boolean) : DSWantedFormControlDemoEvent
        data class SetGroup(val group: Boolean) : DSWantedFormControlDemoEvent
        data class SetGroupLabelWidth(val fixed: Boolean) : DSWantedFormControlDemoEvent

        data class SetSize(val size: WantedFormControlDefaults.Size) : DSWantedFormControlDemoEvent
        data class SetStatus(val status: WantedFormControlDefaults.Status) : DSWantedFormControlDemoEvent
        data class SetLabelPlacement(
            val labelPlacement: WantedFormControlDefaults.LabelPlacement
        ) : DSWantedFormControlDemoEvent
    }

    data class DSWantedFormControlDemoViewState(
        val isLoading: Boolean = true,

        val text: String = "",
        val isShowCode: Boolean = false,
        val code: String = "",

        val isShowAll: Boolean = false,

        val labelText: String = DEFAULT_LABEL_TEXT,
        val required: Boolean = false,
        val description: Boolean = true,
        val accessory: Boolean = false,
        val enabled: Boolean = true,
        val group: Boolean = false,
        val groupLabelWidthFixed: Boolean = false,

        val size: WantedFormControlDefaults.Size = WantedFormControlDefaults.Size.Large,
        val status: WantedFormControlDefaults.Status = WantedFormControlDefaults.Status.Normal,
        val labelPlacement: WantedFormControlDefaults.LabelPlacement =
            WantedFormControlDefaults.LabelPlacement.Top,
    ) : BaseViewState

    sealed interface DSWantedFormControlDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedFormControlDemoSideEffect
    }

    sealed interface DSWantedFormControlDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedFormControlDemoViewEvent

        data object OnClickShowCode : DSWantedFormControlDemoViewEvent
        data object OnClickShowAll : DSWantedFormControlDemoViewEvent
        data object OnClickCopyCode : DSWantedFormControlDemoViewEvent

        data class OnTextFieldValueChanged(val text: String) : DSWantedFormControlDemoViewEvent

        data class OnChangeLabel(val label: String) : DSWantedFormControlDemoViewEvent
        data class OnChangeRequired(val required: Boolean) : DSWantedFormControlDemoViewEvent
        data class OnChangeDescription(val description: Boolean) : DSWantedFormControlDemoViewEvent
        data class OnChangeAccessory(val accessory: Boolean) : DSWantedFormControlDemoViewEvent
        data class OnChangeEnabled(val enabled: Boolean) : DSWantedFormControlDemoViewEvent
        data class OnChangeGroup(val group: Boolean) : DSWantedFormControlDemoViewEvent
        data class OnChangeGroupLabelWidth(val fixed: Boolean) : DSWantedFormControlDemoViewEvent

        data class OnChangeSize(
            val size: WantedFormControlDefaults.Size
        ) : DSWantedFormControlDemoViewEvent

        data class OnChangeStatus(
            val status: WantedFormControlDefaults.Status
        ) : DSWantedFormControlDemoViewEvent

        data class OnChangeLabelPlacement(
            val labelPlacement: WantedFormControlDefaults.LabelPlacement
        ) : DSWantedFormControlDemoViewEvent
    }
}
