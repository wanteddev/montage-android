package com.wanted.android.montage.sample.content.card.vertical

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent

object DSWantedCardDemoScreenContract {
    sealed interface DSWantedCardDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedCardDemoViewState) : DSWantedCardDemoEvent
        data class ShowCode(val isShowCode: Boolean) : DSWantedCardDemoEvent
        data object CopyCode : DSWantedCardDemoEvent
        data class ShowAll(val isShowAll: Boolean) : DSWantedCardDemoEvent

        data class SetOverlayCaption(val isChecked: Boolean) : DSWantedCardDemoEvent
        data class SetTitle(val isChecked: Boolean) : DSWantedCardDemoEvent
        data class SetCaption(val isChecked: Boolean) : DSWantedCardDemoEvent
        data class SetSubCaption(val isChecked: Boolean) : DSWantedCardDemoEvent
        data class SetExtraCaption(val isChecked: Boolean) : DSWantedCardDemoEvent
        data class SetOverlayToggleIcon(val isChecked: Boolean) : DSWantedCardDemoEvent
        data class SetTopContent(val isChecked: Boolean) : DSWantedCardDemoEvent
        data class SetBottomContent(val isChecked: Boolean) : DSWantedCardDemoEvent
        data class SetLoading(val isChecked: Boolean) : DSWantedCardDemoEvent
    }

    data class DSWantedCardDemoViewState(
        val isLoading: Boolean = false,

        val isShowCode: Boolean = false,
        val code: String = "",

        val isShowAll: Boolean = false,

        val overlayCaption: Boolean = true,
        val title: Boolean = true,
        val caption: Boolean = true,
        val subCaption: Boolean = true,
        val extraCaption: Boolean = true,
        val overlayToggleIcon: Boolean = true,
        val topContent: Boolean = true,
        val bottomContent: Boolean = true,
    ) : BaseViewState

    sealed interface DSWantedCardDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedCardDemoSideEffect
    }


    sealed interface DSWantedCardDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedCardDemoViewEvent
        data object OnClickShowCode : DSWantedCardDemoViewEvent
        data object OnClickCopyCode : DSWantedCardDemoViewEvent
        data object OnClickShowAll : DSWantedCardDemoViewEvent

        data class OnClickOverlayCaption(val isChecked: Boolean) : DSWantedCardDemoViewEvent
        data class OnClickTitle(val isChecked: Boolean) : DSWantedCardDemoViewEvent
        data class OnClickCaption(val isChecked: Boolean) : DSWantedCardDemoViewEvent
        data class OnClickSubCaption(val isChecked: Boolean) : DSWantedCardDemoViewEvent
        data class OnClickExtraCaption(val isChecked: Boolean) : DSWantedCardDemoViewEvent
        data class OnClickOverlayToggleIcon(val isChecked: Boolean) : DSWantedCardDemoViewEvent
        data class OnClickTopContent(val isChecked: Boolean) : DSWantedCardDemoViewEvent
        data class OnClickBottomContent(val isChecked: Boolean) : DSWantedCardDemoViewEvent
        data class OnClickLoading(val isChecked: Boolean) : DSWantedCardDemoViewEvent
    }
}