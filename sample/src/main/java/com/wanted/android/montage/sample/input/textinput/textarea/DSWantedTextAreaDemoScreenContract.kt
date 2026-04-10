package com.wanted.android.montage.sample.input.textinput.textarea

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent

object DSWantedTextAreaDemoScreenContract {
    sealed interface DSWantedTextAreaDemoEvent : BaseEvent {
        data class InitState(
            val viewState: DSWantedTextAreaDemoViewState
        ) : DSWantedTextAreaDemoEvent

        data class ShowCode(val isShowCode: Boolean) : DSWantedTextAreaDemoEvent
        data class ShowAll(val isShowAll: Boolean) : DSWantedTextAreaDemoEvent
        data object CopyCode : DSWantedTextAreaDemoEvent

        data class SetTextFieldValue(val text: String) : DSWantedTextAreaDemoEvent

        data class SetRightButton(val enabledRightButton: Boolean) : DSWantedTextAreaDemoEvent

        data class SetEnabled(val enabled: Boolean) : DSWantedTextAreaDemoEvent

        data class SetTitle(val title: Boolean) : DSWantedTextAreaDemoEvent
        data class SetEnabledRequiredBadge(
            val enabledRequiredBadge: Boolean
        ) : DSWantedTextAreaDemoEvent

        data class SetEnabledOverflowText(
            val enabledOverflowText: Boolean
        ) : DSWantedTextAreaDemoEvent

        data class ShowMaxLinePicker(val isShowMaxLinePicker: Boolean) : DSWantedTextAreaDemoEvent
        data class SetMaxLines(val maxLines: Int) : DSWantedTextAreaDemoEvent

        data class ShowMinLinesPicker(
            val isShowMinLinesPicker: Boolean
        ) : DSWantedTextAreaDemoEvent

        data class SetMinLines(val minLines: Int) : DSWantedTextAreaDemoEvent

        data class ShowMaxWordCountPicker(
            val isShowMaxWordCountPicker: Boolean
        ) : DSWantedTextAreaDemoEvent

        data class SetMaxWordCount(val maxWordCount: Int) : DSWantedTextAreaDemoEvent

        data class ShowSample(val isShowSample: Boolean) : DSWantedTextAreaDemoEvent

        data class SetDescription(val description: Boolean) : DSWantedTextAreaDemoEvent
        data class SetLeadingContent(val leadingContent: Boolean) : DSWantedTextAreaDemoEvent
        data class SetTrailingContent(val trailingContent: Boolean) : DSWantedTextAreaDemoEvent
        data class SetNegative(val negative: Boolean) : DSWantedTextAreaDemoEvent
        data class SetGraphemeClusterCount(val isGraphemeClusterCount: Boolean) :
            DSWantedTextAreaDemoEvent
        data object Focus: DSWantedTextAreaDemoEvent
    }

    data class DSWantedTextAreaDemoViewState(
        val isLoading: Boolean = true,

        val text: String = "",
        val isShowCode: Boolean = false,
        val code: String = "",

        val isShowAll: Boolean = false,
        val isShowSample: Boolean = false,

        val description: Boolean = false,
        val rightButton: Boolean = false,

        val enabled: Boolean = true,
        val negative: Boolean = false,
        val leadingContent: Boolean = false,
        val trailingContent: Boolean = false,

        val title: Boolean = false,
        val requiredBadge: Boolean = false,
        val isGraphemeClusterCount: Boolean = false,
        val enabledOverflowText: Boolean = false,

        val isShowMaxLinePicker: Boolean = false,
        val maxLines: Int = 1,

        val isShowMinLinesPicker: Boolean = false,
        val minLines: Int = 1,

        val isShowMaxWordCountPicker: Boolean = false,
        val maxWordCount: Int = 2000
    ) : BaseViewState

    sealed interface DSWantedTextAreaDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedTextAreaDemoSideEffect
        data object Focus: DSWantedTextAreaDemoSideEffect
    }

    sealed interface DSWantedTextAreaDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedTextAreaDemoViewEvent

        data object OnClickShowCode : DSWantedTextAreaDemoViewEvent
        data object OnClickShowAll : DSWantedTextAreaDemoViewEvent
        data object OnClickCopyCode : DSWantedTextAreaDemoViewEvent

        data class OnChangeRightButton(
            val enabledRightButton: Boolean
        ) : DSWantedTextAreaDemoViewEvent

        data class OnChangeDescription(
            val description: Boolean
        ) : DSWantedTextAreaDemoViewEvent


        data class OnChangeEnabled(val enabled: Boolean) : DSWantedTextAreaDemoViewEvent
        data class OnChangeNegative(val negative: Boolean) : DSWantedTextAreaDemoViewEvent

        data class OnChangeTitle(val title: Boolean) : DSWantedTextAreaDemoViewEvent
        data class OnChangeEnabledRequiredBadge(
            val enabledRequiredBadge: Boolean
        ) : DSWantedTextAreaDemoViewEvent

        data class OnChangeEnabledOverflowText(
            val enabledOverflowText: Boolean
        ) : DSWantedTextAreaDemoViewEvent

        data class OnTextFieldValueChanged(val text: String) : DSWantedTextAreaDemoViewEvent

        data object OnShowMaxLinePicker : DSWantedTextAreaDemoViewEvent

        data object OnShowMinLinesPicker : DSWantedTextAreaDemoViewEvent

        data object OnShowMaxWordCountPicker : DSWantedTextAreaDemoViewEvent

        data object OnClickShowSample : DSWantedTextAreaDemoViewEvent

        data class OnChangeLeadingContent(val leadingContent: Boolean) :
            DSWantedTextAreaDemoViewEvent

        data class OnChangeTrailingContent(val trailingContent: Boolean) :
            DSWantedTextAreaDemoViewEvent

        data class OnChangeGraphemeClusterCount(val isGraphemeClusterCount: Boolean) :
            DSWantedTextAreaDemoViewEvent

        data object OnClickFocus: DSWantedTextAreaDemoViewEvent

    }
}