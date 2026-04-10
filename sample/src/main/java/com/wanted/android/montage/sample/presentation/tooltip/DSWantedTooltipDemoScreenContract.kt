package com.wanted.android.montage.sample.presentation.tooltip

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.presentation.tooltip.WantedTooltipAlign
import com.wanted.android.wanted.design.presentation.tooltip.WantedTooltipSize

object DSWantedTooltipDemoScreenContract {
    sealed interface DSWantedTooltipDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedTooltipDemoViewState) : DSWantedTooltipDemoEvent
        data class OnChangeSize(val size: WantedTooltipSize) : DSWantedTooltipDemoEvent
        data class OnChangeAlways(val always: Boolean) : DSWantedTooltipDemoEvent
        data class OnChangeAlign(val align: WantedTooltipAlign) : DSWantedTooltipDemoEvent
        data class OnChangePositionTop(val positionTop: Boolean) : DSWantedTooltipDemoEvent
        data class ShowCode(val show: Boolean) : DSWantedTooltipDemoEvent
        data object CopyCode : DSWantedTooltipDemoEvent

    }

    data class DSWantedTooltipDemoViewState(
        val isLoading: Boolean = true,
        val size: WantedTooltipSize = WantedTooltipSize.Medium,
        val always: Boolean = false,
        val align: WantedTooltipAlign = WantedTooltipAlign.Left,
        val positionTop: Boolean = false,
        val isShowCode: Boolean = false,
        val code: String = ""
    ) : BaseViewState

    sealed interface DSWantedTooltipDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedTooltipDemoSideEffect
    }


    sealed interface DSWantedTooltipDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedTooltipDemoViewEvent
        data object OnClickShowCode : DSWantedTooltipDemoViewEvent
        data object OnClickCopyCode : DSWantedTooltipDemoViewEvent
        data class OnChangeSize(val size: WantedTooltipSize) : DSWantedTooltipDemoViewEvent
        data class OnChangeAlways(val always: Boolean) : DSWantedTooltipDemoViewEvent
        data class OnChangeAlign(val align: WantedTooltipAlign) : DSWantedTooltipDemoViewEvent
        data class OnChangePositionTop(val positionTop: Boolean) : DSWantedTooltipDemoViewEvent

        data object OnClickBottomTooltip : DSWantedTooltipDemoViewEvent
        data object OnClickTopBarTooltip : DSWantedTooltipDemoViewEvent

    }
}