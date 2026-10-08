package com.wanted.android.montage.sample.feedback.fallback

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.feedback.fallback.WantedFallbackButtonVariant
import com.wanted.android.wanted.design.feedback.fallback.WantedFallbackPadding

object DSWantedFallbackViewDemoScreenContract {
    sealed interface DSWantedFallbackViewDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedFallbackViewDemoViewState) : DSWantedFallbackViewDemoEvent
        data class OnChangeHeading(val heading: Boolean) : DSWantedFallbackViewDemoEvent
        data class OnChangeDescription(val description: Boolean) : DSWantedFallbackViewDemoEvent
        data class OnChangeButtonVariant(val buttonVariant: WantedFallbackButtonVariant) : DSWantedFallbackViewDemoEvent
        data class OnChangePadding(val padding: WantedFallbackPadding) : DSWantedFallbackViewDemoEvent
        data class OnChangeMain(val main: Boolean) : DSWantedFallbackViewDemoEvent
        data class OnChangeAlternative(val alternative: Boolean) : DSWantedFallbackViewDemoEvent
        data class ShowCode(val show: Boolean) : DSWantedFallbackViewDemoEvent
        data object CopyCode : DSWantedFallbackViewDemoEvent
    }

    data class DSWantedFallbackViewDemoViewState(
        val isLoading: Boolean = true,
        val heading: Boolean = true,
        val description: Boolean = true,
        val buttonVariant: WantedFallbackButtonVariant = WantedFallbackButtonVariant.Single,
        val padding: WantedFallbackPadding = WantedFallbackPadding.Normal,
        val main: Boolean = true,
        val alternative: Boolean = true,
        val isShowCode: Boolean = false,
        val code: String = ""
    ) : BaseViewState

    sealed interface DSWantedFallbackViewDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedFallbackViewDemoSideEffect
    }

    sealed interface DSWantedFallbackViewDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedFallbackViewDemoViewEvent
        data object OnClickFallbackMain : DSWantedFallbackViewDemoViewEvent
        data object OnClickShowCode : DSWantedFallbackViewDemoViewEvent
        data object OnClickCopyCode : DSWantedFallbackViewDemoViewEvent
        data class OnChangeHeading(val heading: Boolean) : DSWantedFallbackViewDemoViewEvent
        data class OnChangeDescription(val description: Boolean) : DSWantedFallbackViewDemoViewEvent
        data class OnChangeButtonVariant(val buttonVariant: WantedFallbackButtonVariant) : DSWantedFallbackViewDemoViewEvent
        data class OnChangePadding(val padding: WantedFallbackPadding) : DSWantedFallbackViewDemoViewEvent
        data class OnChangeMain(val main: Boolean) : DSWantedFallbackViewDemoViewEvent
        data class OnChangeAlternative(val alternative: Boolean) : DSWantedFallbackViewDemoViewEvent
    }
}
