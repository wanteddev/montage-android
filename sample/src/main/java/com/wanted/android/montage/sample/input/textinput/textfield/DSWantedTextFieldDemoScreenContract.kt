package com.wanted.android.montage.sample.input.textinput.textfield

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.input.formcontrol.WantedFormControlDefaults
import com.wanted.android.wanted.design.input.textinput.textfield.WantedTextFieldDefaults.Size
import com.wanted.android.wanted.design.input.textinput.textfield.WantedTextFieldDefaults.Status

object DSWantedTextFieldDemoScreenContract {

    /**
     * trailingContent 슬롯에 넣어 볼 수 있는 요소 종류입니다.
     *
     * 슬롯은 임의 Composable 을 받으므로, 아이콘 외의 요소도 확인할 수 있도록 데모에서 열거한다.
     */
    enum class TrailingContentType {
        None,
        Icon,
        Text,
        IconButton
    }

    sealed interface DSWantedTextFieldDemoEvent : BaseEvent {
        data class InitState(
            val viewState: DSWantedTextFieldDemoViewState
        ) : DSWantedTextFieldDemoEvent

        data class ShowCode(val isShowCode: Boolean) : DSWantedTextFieldDemoEvent
        data class ShowAll(val isShowAll: Boolean) : DSWantedTextFieldDemoEvent
        data object CopyCode : DSWantedTextFieldDemoEvent

        data class SetTextFieldValue(val text: String) : DSWantedTextFieldDemoEvent

        data class SetEnabledLeadingIcon(
            val enabledLeadingIcon: Boolean
        ) : DSWantedTextFieldDemoEvent

        data class SetEnabledTrailingIcon(
            val enabledTrailingIcon: Boolean
        ) : DSWantedTextFieldDemoEvent

        data class SetTrailingButton(val enabledTrailingButton: Boolean) : DSWantedTextFieldDemoEvent

        data class SetTrailingContent(
            val trailingContent: TrailingContentType
        ) : DSWantedTextFieldDemoEvent

        data class SetSize(val size: Size) : DSWantedTextFieldDemoEvent
        data class SetStatus(val status: Status) : DSWantedTextFieldDemoEvent
        data class SetEnabled(val enabled: Boolean) : DSWantedTextFieldDemoEvent

        data class SetUseFormControl(val useFormControl: Boolean) : DSWantedTextFieldDemoEvent
        data class SetFormControlLabel(val label: Boolean) : DSWantedTextFieldDemoEvent
        data class SetFormControlRequired(val required: Boolean) : DSWantedTextFieldDemoEvent
        data class SetFormControlDescription(
            val description: Boolean
        ) : DSWantedTextFieldDemoEvent

        data class SetFormControlAccessory(val accessory: Boolean) : DSWantedTextFieldDemoEvent
        data class SetFormControlSize(
            val size: WantedFormControlDefaults.Size
        ) : DSWantedTextFieldDemoEvent

        data class SetFormControlLabelPlacement(
            val labelPlacement: WantedFormControlDefaults.LabelPlacement
        ) : DSWantedTextFieldDemoEvent

        data class SetEnabledOverflowText(
            val enabledOverflowText: Boolean
        ) : DSWantedTextFieldDemoEvent

        data class ShowMaxLinePicker(val isShowMaxLinePicker: Boolean) : DSWantedTextFieldDemoEvent
        data class SetMaxLines(val maxLines: Int) : DSWantedTextFieldDemoEvent

        data class ShowMinLinesPicker(
            val isShowMinLinesPicker: Boolean
        ) : DSWantedTextFieldDemoEvent

        data class SetMinLines(val minLines: Int) : DSWantedTextFieldDemoEvent

        data class ShowMaxWordCountPicker(
            val isShowMaxWordCountPicker: Boolean
        ) : DSWantedTextFieldDemoEvent

        data class SetMaxWordCount(val maxWordCount: Int) : DSWantedTextFieldDemoEvent

        data class ShowSample(val isShowSample: Boolean) : DSWantedTextFieldDemoEvent
        data object Focus : DSWantedTextFieldDemoEvent
        data object ClearFocus : DSWantedTextFieldDemoEvent
    }

    data class DSWantedTextFieldDemoViewState(
        val isLoading: Boolean = true,

        val text: String = "",
        val isShowCode: Boolean = false,
        val code: String = "",

        val isShowAll: Boolean = false,
        val isShowSample: Boolean = false,


        val enabledLeadingIcon: Boolean = false,
        val enabledTrailingIcon: Boolean = false,

        val trailingButton: Boolean = false,

        val trailingContentType: List<TrailingContentType> = TrailingContentType.entries.toList(),
        val selectedTrailingContentType: TrailingContentType = TrailingContentType.None,

        val size: List<Size> = Size.entries.toList(),
        val selectedSize: Size = Size.Large,

        val status: List<Status> = Status.entries.toList(),
        val selectedStatus: Status = Status.Normal,

        val enabled: Boolean = true,

        val useFormControl: Boolean = false,
        val formControlLabel: Boolean = false,
        val formControlRequired: Boolean = false,
        val formControlDescription: Boolean = false,
        val formControlAccessory: Boolean = false,

        val formControlSize: List<WantedFormControlDefaults.Size> =
            WantedFormControlDefaults.Size.entries.toList(),
        val selectedFormControlSize: WantedFormControlDefaults.Size =
            WantedFormControlDefaults.Size.Large,

        val formControlLabelPlacement: List<WantedFormControlDefaults.LabelPlacement> =
            WantedFormControlDefaults.LabelPlacement.entries.toList(),
        val selectedFormControlLabelPlacement: WantedFormControlDefaults.LabelPlacement =
            WantedFormControlDefaults.LabelPlacement.Top,

        val enabledOverflowText: Boolean = false,

        val isShowMaxLinePicker: Boolean = false,
        val maxLines: Int = 1,

        val isShowMinLinesPicker: Boolean = false,
        val minLines: Int = 1,

        val isShowMaxWordCountPicker: Boolean = false,
        val maxWordCount: Int = 2000

    ) : BaseViewState

    sealed interface DSWantedTextFieldDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedTextFieldDemoSideEffect
        data object Focus : DSWantedTextFieldDemoSideEffect
        data object ClearFocus : DSWantedTextFieldDemoSideEffect
    }


    sealed interface DSWantedTextFieldDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedTextFieldDemoViewEvent

        data object OnClickShowCode : DSWantedTextFieldDemoViewEvent
        data object OnClickShowAll : DSWantedTextFieldDemoViewEvent
        data object OnClickCopyCode : DSWantedTextFieldDemoViewEvent

        data class OnChangeEnabledLeadingIcon(
            val enabledLeadingIcon: Boolean
        ) : DSWantedTextFieldDemoViewEvent

        data class OnChangeEnabledTrailingIcon(
            val enabledTrailingIcon: Boolean
        ) : DSWantedTextFieldDemoViewEvent

        data class OnChangeTrailingButton(
            val enabledTrailingButton: Boolean
        ) : DSWantedTextFieldDemoViewEvent

        data class OnChangeTrailingContent(
            val trailingContent: TrailingContentType
        ) : DSWantedTextFieldDemoViewEvent

        data class OnChangeSize(val size: Size) : DSWantedTextFieldDemoViewEvent

        data class OnChangeStatus(val status: Status) : DSWantedTextFieldDemoViewEvent
        data class OnChangeEnabled(val enabled: Boolean) : DSWantedTextFieldDemoViewEvent

        data class OnChangeUseFormControl(
            val useFormControl: Boolean
        ) : DSWantedTextFieldDemoViewEvent

        data class OnChangeFormControlLabel(
            val label: Boolean
        ) : DSWantedTextFieldDemoViewEvent

        data class OnChangeFormControlRequired(
            val required: Boolean
        ) : DSWantedTextFieldDemoViewEvent

        data class OnChangeFormControlDescription(
            val description: Boolean
        ) : DSWantedTextFieldDemoViewEvent

        data class OnChangeFormControlAccessory(
            val accessory: Boolean
        ) : DSWantedTextFieldDemoViewEvent

        data class OnChangeFormControlSize(
            val size: WantedFormControlDefaults.Size
        ) : DSWantedTextFieldDemoViewEvent

        data class OnChangeFormControlLabelPlacement(
            val labelPlacement: WantedFormControlDefaults.LabelPlacement
        ) : DSWantedTextFieldDemoViewEvent

        data class OnChangeEnabledOverflowText(
            val enabledOverflowText: Boolean
        ) : DSWantedTextFieldDemoViewEvent

        data class OnTextFieldValueChanged(val text: String) : DSWantedTextFieldDemoViewEvent

        data object OnShowMaxLinePicker : DSWantedTextFieldDemoViewEvent

        data object OnShowMinLinesPicker : DSWantedTextFieldDemoViewEvent

        data object OnShowMaxWordCountPicker : DSWantedTextFieldDemoViewEvent

        data object OnClickShowSample : DSWantedTextFieldDemoViewEvent

        data object OnClickFocus : DSWantedTextFieldDemoViewEvent

        data object OnClickClearFocus : DSWantedTextFieldDemoViewEvent
    }
}