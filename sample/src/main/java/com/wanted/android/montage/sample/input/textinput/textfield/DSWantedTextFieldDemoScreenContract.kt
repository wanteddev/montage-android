package com.wanted.android.montage.sample.input.textinput.textfield

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.input.textinput.textfield.WantedTextFieldDefaults.RightVariant
import com.wanted.android.wanted.design.input.textinput.textfield.WantedTextFieldDefaults.Status

object DSWantedTextFieldDemoScreenContract {
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

        data class SetRightButton(val enabledRightButton: Boolean) : DSWantedTextFieldDemoEvent
        data class SetRightButtonVariant(
            val rightButtonVariant: RightVariant
        ) : DSWantedTextFieldDemoEvent

        data class SetRightContent(val enabledRightContent: Boolean) : DSWantedTextFieldDemoEvent
        data class SetStatus(val status: Status) : DSWantedTextFieldDemoEvent
        data class SetEnabled(val enabled: Boolean) : DSWantedTextFieldDemoEvent

        data class SetTitle(val title: Boolean) : DSWantedTextFieldDemoEvent
        data class SetEnabledRequiredBadge(
            val enabledRequiredBadge: Boolean
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

        val rightButton: Boolean = false,
        val rightButtonVariant: List<RightVariant> = RightVariant.entries.toList(),
        val selectedRightButtonVariant: RightVariant = RightVariant.entries.first(),

        val rightContent: Boolean = false,

        val status: List<Status> = Status.entries.toList(),
        val selectedStatus: Status = Status.Normal,

        val enabled: Boolean = true,

        val title: Boolean = false,
        val enabledRequiredBadge: Boolean = false,
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

        data class OnChangeRightButton(
            val enabledRightButton: Boolean
        ) : DSWantedTextFieldDemoViewEvent

        data class OnChangeRightButtonVariant(
            val rightButtonVariant: RightVariant
        ) : DSWantedTextFieldDemoViewEvent

        data class OnChangeRightContent(
            val enabledRightContent: Boolean
        ) : DSWantedTextFieldDemoViewEvent

        data class OnChangeStatus(val status: Status) : DSWantedTextFieldDemoViewEvent
        data class OnChangeEnabled(val enabled: Boolean) : DSWantedTextFieldDemoViewEvent

        data class OnChangeTitle(val title: Boolean) : DSWantedTextFieldDemoViewEvent
        data class OnChangeEnabledRequiredBadge(
            val enabledRequiredBadge: Boolean
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
    }
}