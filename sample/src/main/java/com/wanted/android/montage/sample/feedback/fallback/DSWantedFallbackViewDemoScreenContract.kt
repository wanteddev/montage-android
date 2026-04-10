package com.wanted.android.montage.sample.feedback.fallback

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.feedback.fallback.WantedFallbackButtonVariant
import com.wanted.android.wanted.design.util.ButtonType

object DSWantedFallbackViewDemoScreenContract {
    sealed interface DSWantedFallbackViewDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedFallbackViewDemoViewState) : DSWantedFallbackViewDemoEvent
        data class OnChangeHeading(val heading: Boolean) : DSWantedFallbackViewDemoEvent
        data class OnChangeImage(val image: Boolean) : DSWantedFallbackViewDemoEvent
        data class OnChangeDescription(val description: Boolean) : DSWantedFallbackViewDemoEvent
        data class OnChangeButtonVariant(val buttonVariant: WantedFallbackButtonVariant) : DSWantedFallbackViewDemoEvent
        data class OnChangePositive(val positive: Boolean) : DSWantedFallbackViewDemoEvent
        data class OnChangeNegative(val negative: Boolean) : DSWantedFallbackViewDemoEvent
        data class OnChangePositiveColor(val positiveColor: ButtonType) : DSWantedFallbackViewDemoEvent
        data class OnChangeNegativeColor(val negativeColor: ButtonType) : DSWantedFallbackViewDemoEvent
        data class ShowCode(val show: Boolean) : DSWantedFallbackViewDemoEvent
        data object CopyCode : DSWantedFallbackViewDemoEvent
    }

    data class DSWantedFallbackViewDemoViewState(
        val isLoading: Boolean = true,
        val heading: Boolean = true,
        val image: Boolean = true,
        val description: Boolean = true,
        val buttonVariant: WantedFallbackButtonVariant = WantedFallbackButtonVariant.Single,
        val positive: Boolean = true,
        val negative: Boolean = true,
        val positiveColor: ButtonType = ButtonType.ASSISTIVE,
        val negativeColor: ButtonType = ButtonType.ASSISTIVE,
        val isShowCode: Boolean = false,
        val code: String = ""
    ) : BaseViewState

    sealed interface DSWantedFallbackViewDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedFallbackViewDemoSideEffect
    }

    sealed interface DSWantedFallbackViewDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedFallbackViewDemoViewEvent
        data object OnClickShowCode : DSWantedFallbackViewDemoViewEvent
        data object OnClickCopyCode : DSWantedFallbackViewDemoViewEvent
        data class OnChangeHeading(val heading: Boolean) : DSWantedFallbackViewDemoViewEvent
        data class OnChangeDescription(val description: Boolean) : DSWantedFallbackViewDemoViewEvent
        data class OnChangeButtonVariant(val buttonVariant: WantedFallbackButtonVariant) : DSWantedFallbackViewDemoViewEvent
        data class OnChangePositive(val positive: Boolean) : DSWantedFallbackViewDemoViewEvent
        data class OnChangeNegative(val negative: Boolean) : DSWantedFallbackViewDemoViewEvent
        data class OnChangePositiveColor(val positiveColor: ButtonType) : DSWantedFallbackViewDemoViewEvent
        data class OnChangeNegativeColor(val negativeColor: ButtonType) : DSWantedFallbackViewDemoViewEvent
        data class OnChangeImage(val image: Boolean) : DSWantedFallbackViewDemoViewEvent
    }
}