package com.wanted.android.montage.sample.presentation.popover

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.presentation.popover.WantedPopoverAlign

object DSWantedPopoverDemoScreenContract {
    sealed interface DSWantedPopoverDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedPopoverDemoViewState) : DSWantedPopoverDemoEvent
        data class OnChangeAlways(val always: Boolean) : DSWantedPopoverDemoEvent
        data class OnChangeAlign(val align: WantedPopoverAlign) : DSWantedPopoverDemoEvent
        data class OnChangeHeading(val heading: Boolean) : DSWantedPopoverDemoEvent
        data class OnChangeHeadingText(val headingText: String) : DSWantedPopoverDemoEvent
        data class OnChangeCloseButton(val closeButton: Boolean) : DSWantedPopoverDemoEvent
        data class OnChangePositionTop(val positionTop: Boolean) : DSWantedPopoverDemoEvent
        data class OnChangeAction(val action: Boolean) : DSWantedPopoverDemoEvent
        data class ShowCode(val show: Boolean) : DSWantedPopoverDemoEvent
        data object CopyCode : DSWantedPopoverDemoEvent
    }

    data class DSWantedPopoverDemoViewState(
        val isLoading: Boolean = true,
        val always: Boolean = false,
        val align: WantedPopoverAlign = WantedPopoverAlign.Left,
        val heading: Boolean = false,
        val headingText: String = "Popover 제목",
        val closeButton: Boolean = false,
        val positionTop: Boolean = false,
        val isShowCode: Boolean = false,
        val code: String = "",
        val action: Boolean = false
    ) : BaseViewState

    sealed interface DSWantedPopoverDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedPopoverDemoSideEffect
    }

    sealed interface DSWantedPopoverDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedPopoverDemoViewEvent
        data object OnClickShowCode : DSWantedPopoverDemoViewEvent
        data object OnClickCopyCode : DSWantedPopoverDemoViewEvent
        data class OnChangeAlways(val always: Boolean) : DSWantedPopoverDemoViewEvent
        data class OnChangeAlign(val align: WantedPopoverAlign) : DSWantedPopoverDemoViewEvent
        data class OnChangeHeading(val heading: Boolean) : DSWantedPopoverDemoViewEvent
        data class OnChangeHeadingText(val headingText: String) : DSWantedPopoverDemoViewEvent
        data class OnChangeCloseButton(val closeButton: Boolean) : DSWantedPopoverDemoViewEvent
        data class OnChangePositionTop(val positionTop: Boolean) : DSWantedPopoverDemoViewEvent
        data class OnChangeAction(val action: Boolean) : DSWantedPopoverDemoViewEvent
        data object OnClickBottomTooltip : DSWantedPopoverDemoViewEvent
    }
}