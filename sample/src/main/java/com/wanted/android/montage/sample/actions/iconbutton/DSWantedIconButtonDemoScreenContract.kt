package com.wanted.android.montage.sample.actions.iconbutton

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonSize

object DSWantedIconButtonDemoScreenContract {
    sealed interface DSWantedIconButtonDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedIconButtonDemoViewState) :
            DSWantedIconButtonDemoEvent

        data class ShowCode(val isShowCode: Boolean) : DSWantedIconButtonDemoEvent
        data object CopyCode : DSWantedIconButtonDemoEvent
        data class SetSize(val size: WantedIconButtonSize) : DSWantedIconButtonDemoEvent
        data class SetEnabled(val enabled: Boolean) : DSWantedIconButtonDemoEvent
        data class SetVariant(val variant: IconButtonVariant) : DSWantedIconButtonDemoEvent
    }

    data class DSWantedIconButtonDemoViewState(
        val isLoading: Boolean = true,
        val isShowCode: Boolean = false,
        val code: String = "",
        val size: WantedIconButtonSize = WantedIconButtonSize.Medium,
        val enabled: Boolean = true,
        val variant: IconButtonVariant = IconButtonVariant.Outlined,
    ) : BaseViewState

    sealed interface DSWantedIconButtonDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedIconButtonDemoSideEffect
    }

    sealed interface DSWantedIconButtonDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedIconButtonDemoViewEvent
        data object OnClickShowCode : DSWantedIconButtonDemoViewEvent
        data object OnClickCopyCode : DSWantedIconButtonDemoViewEvent
        data class OnSizeChanged(val size: WantedIconButtonSize) : DSWantedIconButtonDemoViewEvent
        data class OnEnabledChanged(val enabled: Boolean) : DSWantedIconButtonDemoViewEvent
        data class OnVariantChanged(val variant: IconButtonVariant) : DSWantedIconButtonDemoViewEvent
    }

    enum class IconButtonVariant {
        Normal,
        Outlined,
        Solid,
        Background
    }
}