package com.wanted.android.montage.sample.content.listcard

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent

object DSWantedListCardDemoScreenContract {
    sealed interface DSWantedListCardDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedListCardDemoViewState) :
            DSWantedListCardDemoEvent

        data class ShowCode(val isShowCode: Boolean) : DSWantedListCardDemoEvent
        data object CopyCode : DSWantedListCardDemoEvent
        data class SetLoading(val loading: Boolean) : DSWantedListCardDemoEvent
        data class SetTopContent(val enabled: Boolean) : DSWantedListCardDemoEvent
        data class SetBottomContent(val enabled: Boolean) : DSWantedListCardDemoEvent
        data class SetLeadingContent(val enabled: Boolean) : DSWantedListCardDemoEvent
        data class SetTrailingContent(val enabled: Boolean) : DSWantedListCardDemoEvent
    }

    data class DSWantedListCardDemoViewState(
        val isLoading: Boolean = false,
        val isShowCode: Boolean = false,
        val code: String = "",
        val topContentEnabled: Boolean = true,
        val bottomContentEnabled: Boolean = true,
        val leadingContentEnabled: Boolean = true,
        val trailingContentEnabled: Boolean = true,
    ) : BaseViewState

    sealed interface DSWantedListCardDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedListCardDemoSideEffect
    }

    sealed interface DSWantedListCardDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedListCardDemoViewEvent
        data object OnClickShowCode : DSWantedListCardDemoViewEvent
        data object OnClickCopyCode : DSWantedListCardDemoViewEvent
        data class OnLoadingChanged(val loading: Boolean) : DSWantedListCardDemoViewEvent
        data class OnTopContentChanged(val enabled: Boolean) : DSWantedListCardDemoViewEvent
        data class OnBottomContentChanged(val enabled: Boolean) : DSWantedListCardDemoViewEvent
        data class OnLeadingContentChanged(val enabled: Boolean) : DSWantedListCardDemoViewEvent
        data class OnTrailingContentChanged(val enabled: Boolean) : DSWantedListCardDemoViewEvent
    }
}