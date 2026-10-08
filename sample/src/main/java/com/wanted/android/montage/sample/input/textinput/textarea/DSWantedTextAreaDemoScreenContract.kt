package com.wanted.android.montage.sample.input.textinput.textarea

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.input.textinput.textarea.WantedTextAreaDefaults
import com.wanted.android.wanted.design.input.textinput.textarea.WantedTextAreaDefaults.DEFAULT_MAX_LINE
import com.wanted.android.wanted.design.input.textinput.textarea.WantedTextAreaDefaults.DEFAULT_MIN_LINE

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
        data class SetNegative(val negative: Boolean) : DSWantedTextAreaDemoEvent
        data class SetGraphemeClusterCount(val isGraphemeClusterCount: Boolean) :
            DSWantedTextAreaDemoEvent
        data object Focus : DSWantedTextAreaDemoEvent

        data class SetSize(val size: WantedTextAreaDefaults.Size) : DSWantedTextAreaDemoEvent
        data class SetResize(val resize: WantedTextAreaDefaults.Resize) : DSWantedTextAreaDemoEvent
        data class SetLeadingContent(val enabled: Boolean) : DSWantedTextAreaDemoEvent
        data class SetTrailingContent(val enabled: Boolean) : DSWantedTextAreaDemoEvent
        data class SetFormControl(val enabled: Boolean) : DSWantedTextAreaDemoEvent
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

        // WantedFormControl 로 감싸서 label/description/requiredBadge 를 노출할지 여부.
        // false 면 WantedTextArea 단독으로 렌더링한다.
        val formControl: Boolean = true,

        val enabled: Boolean = true,
        val negative: Boolean = false,
        val title: Boolean = false,
        val requiredBadge: Boolean = false,
        val isGraphemeClusterCount: Boolean = false,
        val enabledOverflowText: Boolean = false,

        val isShowMaxLinePicker: Boolean = false,
        val maxLines: Int = DEFAULT_MAX_LINE,

        val isShowMinLinesPicker: Boolean = false,
        val minLines: Int = DEFAULT_MIN_LINE,

        val isShowMaxWordCountPicker: Boolean = false,
        val maxWordCount: Int = 2000,

        val size: WantedTextAreaDefaults.Size = WantedTextAreaDefaults.Size.Large,
        val resize: WantedTextAreaDefaults.Resize = WantedTextAreaDefaults.Resize.Normal,
        val leadingContent: Boolean = false,
        val trailingContent: Boolean = false,
    ) : BaseViewState

    sealed interface DSWantedTextAreaDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedTextAreaDemoSideEffect
        data object Focus : DSWantedTextAreaDemoSideEffect
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

        data class OnChangeGraphemeClusterCount(val isGraphemeClusterCount: Boolean) :
            DSWantedTextAreaDemoViewEvent

        data object OnClickFocus : DSWantedTextAreaDemoViewEvent

        data class OnChangeSize(val size: WantedTextAreaDefaults.Size) : DSWantedTextAreaDemoViewEvent
        data class OnChangeResize(val resize: WantedTextAreaDefaults.Resize) : DSWantedTextAreaDemoViewEvent
        data class OnChangeLeadingContent(val enabled: Boolean) : DSWantedTextAreaDemoViewEvent
        data class OnChangeTrailingContent(val enabled: Boolean) : DSWantedTextAreaDemoViewEvent
        data class OnChangeFormControl(val enabled: Boolean) : DSWantedTextAreaDemoViewEvent
    }
}
