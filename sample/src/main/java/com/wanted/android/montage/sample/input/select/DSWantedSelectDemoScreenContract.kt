package com.wanted.android.montage.sample.input.select

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.input.formcontrol.WantedFormControlDefaults
import com.wanted.android.wanted.design.input.select.WantedSelectDefaults.MultiSelectRender
import com.wanted.android.wanted.design.input.select.WantedSelectDefaults.SelectType
import com.wanted.android.wanted.design.input.select.WantedSelectDefaults.Size

object DSWantedSelectDemoScreenContract {

    /**
     * 데모에서 단일 선택(WantedSelect)과 다중 선택(WantedSelectWithString) 중
     * 어떤 오버로드를 미리보기에 사용할지 정하는 값입니다.
     */
    enum class SelectMode {
        Single,
        Multi
    }

    sealed interface DSWantedSelectDemoEvent : BaseEvent {
        data class ShowCode(val isShowCode: Boolean) : DSWantedSelectDemoEvent
        data class ShowAll(val isShowAll: Boolean) : DSWantedSelectDemoEvent
        data object CopyCode : DSWantedSelectDemoEvent

        data class SetMode(val mode: SelectMode) : DSWantedSelectDemoEvent
        data class SetSize(val size: Size) : DSWantedSelectDemoEvent
        data class SetRender(val render: MultiSelectRender) : DSWantedSelectDemoEvent
        data class SetSelectType(val selectType: SelectType) : DSWantedSelectDemoEvent

        data class SetLeadingIcon(val leadingIcon: Boolean) : DSWantedSelectDemoEvent
        data class SetConfirmText(val confirmText: Boolean) : DSWantedSelectDemoEvent

        data class SetUseFormControl(val useFormControl: Boolean) : DSWantedSelectDemoEvent
        data class SetFormControlLabel(val label: Boolean) : DSWantedSelectDemoEvent
        data class SetFormControlRequired(val required: Boolean) : DSWantedSelectDemoEvent
        data class SetFormControlDescription(val description: Boolean) : DSWantedSelectDemoEvent
        data class SetFormControlSize(
            val size: WantedFormControlDefaults.Size
        ) : DSWantedSelectDemoEvent

        data class SetFormControlLabelPlacement(
            val labelPlacement: WantedFormControlDefaults.LabelPlacement
        ) : DSWantedSelectDemoEvent

        data class SetNegative(val negative: Boolean) : DSWantedSelectDemoEvent
        data class SetFocused(val focused: Boolean) : DSWantedSelectDemoEvent
        data class SetEnabled(val enabled: Boolean) : DSWantedSelectDemoEvent
        data class SetOverflow(val overflow: Boolean) : DSWantedSelectDemoEvent

        data class SetSelectedValue(val value: String) : DSWantedSelectDemoEvent
        data class SetSelectedValueList(val valueList: List<String>) : DSWantedSelectDemoEvent
        data class DeleteSelectedValue(val value: String) : DSWantedSelectDemoEvent
    }

    data class DSWantedSelectDemoViewState(
        val isShowCode: Boolean = false,
        val code: String = "",
        val isShowAll: Boolean = false,

        val mode: List<SelectMode> = SelectMode.entries.toList(),
        val selectedMode: SelectMode = SelectMode.Single,

        val size: List<Size> = Size.entries.toList(),
        val selectedSize: Size = Size.Large,

        val render: List<MultiSelectRender> = MultiSelectRender.entries.toList(),
        val selectedRender: MultiSelectRender = MultiSelectRender.Text,

        val selectType: List<SelectType> = SelectType.entries.toList(),
        val selectedSelectType: SelectType = SelectType.CheckMark,

        val leadingIcon: Boolean = false,
        val confirmText: Boolean = false,

        val useFormControl: Boolean = false,
        val formControlLabel: Boolean = false,
        val formControlRequired: Boolean = false,
        val formControlDescription: Boolean = false,

        val formControlSize: List<WantedFormControlDefaults.Size> =
            WantedFormControlDefaults.Size.entries.toList(),
        val selectedFormControlSize: WantedFormControlDefaults.Size =
            WantedFormControlDefaults.Size.Large,

        val formControlLabelPlacement: List<WantedFormControlDefaults.LabelPlacement> =
            WantedFormControlDefaults.LabelPlacement.entries.toList(),
        val selectedFormControlLabelPlacement: WantedFormControlDefaults.LabelPlacement =
            WantedFormControlDefaults.LabelPlacement.Top,

        val negative: Boolean = false,
        val focused: Boolean = false,
        val enabled: Boolean = true,
        val overflow: Boolean = false,

        val selectedValue: String = "",
        val selectedValueList: List<String> = emptyList()
    ) : BaseViewState

    sealed interface DSWantedSelectDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedSelectDemoSideEffect
    }

    sealed interface DSWantedSelectDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedSelectDemoViewEvent

        data object OnClickShowCode : DSWantedSelectDemoViewEvent
        data object OnClickShowAll : DSWantedSelectDemoViewEvent
        data object OnClickCopyCode : DSWantedSelectDemoViewEvent

        data class OnChangeMode(val mode: SelectMode) : DSWantedSelectDemoViewEvent
        data class OnChangeSize(val size: Size) : DSWantedSelectDemoViewEvent
        data class OnChangeRender(val render: MultiSelectRender) : DSWantedSelectDemoViewEvent
        data class OnChangeSelectType(val selectType: SelectType) : DSWantedSelectDemoViewEvent

        data class OnChangeLeadingIcon(val leadingIcon: Boolean) : DSWantedSelectDemoViewEvent
        data class OnChangeConfirmText(val confirmText: Boolean) : DSWantedSelectDemoViewEvent

        data class OnChangeUseFormControl(
            val useFormControl: Boolean
        ) : DSWantedSelectDemoViewEvent

        data class OnChangeFormControlLabel(val label: Boolean) : DSWantedSelectDemoViewEvent
        data class OnChangeFormControlRequired(val required: Boolean) : DSWantedSelectDemoViewEvent
        data class OnChangeFormControlDescription(
            val description: Boolean
        ) : DSWantedSelectDemoViewEvent

        data class OnChangeFormControlSize(
            val size: WantedFormControlDefaults.Size
        ) : DSWantedSelectDemoViewEvent

        data class OnChangeFormControlLabelPlacement(
            val labelPlacement: WantedFormControlDefaults.LabelPlacement
        ) : DSWantedSelectDemoViewEvent

        data class OnChangeNegative(val negative: Boolean) : DSWantedSelectDemoViewEvent
        data class OnChangeFocused(val focused: Boolean) : DSWantedSelectDemoViewEvent
        data class OnChangeEnabled(val enabled: Boolean) : DSWantedSelectDemoViewEvent
        data class OnChangeOverflow(val overflow: Boolean) : DSWantedSelectDemoViewEvent

        data class OnSelectValue(val value: String) : DSWantedSelectDemoViewEvent
        data class OnSelectValueList(val valueList: List<String>) : DSWantedSelectDemoViewEvent
        data class OnDeleteValue(val value: String) : DSWantedSelectDemoViewEvent
    }
}
