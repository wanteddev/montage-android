package com.wanted.android.montage.sample.actions.iconbutton

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.actions.button.iconbutton.IconButtonBadgePosition
import com.wanted.android.wanted.design.actions.button.iconbutton.IconButtonInteractionEffect
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonNormalSize
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonSize

object DSWantedIconButtonDemoScreenContract {
    sealed interface DSWantedIconButtonDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedIconButtonDemoViewState) : DSWantedIconButtonDemoEvent
        data class ShowCode(val isShowCode: Boolean) : DSWantedIconButtonDemoEvent
        data object CopyCode : DSWantedIconButtonDemoEvent
        data class SetContainerSize(val size: WantedIconButtonSize) : DSWantedIconButtonDemoEvent
        data class SetNormalSize(val size: WantedIconButtonNormalSize) : DSWantedIconButtonDemoEvent
        data class SetUseCustomSize(val useCustomSize: Boolean) : DSWantedIconButtonDemoEvent
        data class SetCustomBoxSize(val boxSizeDp: Int) : DSWantedIconButtonDemoEvent
        data class SetEnabled(val enabled: Boolean) : DSWantedIconButtonDemoEvent
        data class SetVariant(val variant: IconButtonVariant) : DSWantedIconButtonDemoEvent
        data class SetInteractionEffect(
            val interactionEffect: IconButtonInteractionEffect
        ) : DSWantedIconButtonDemoEvent
        data class SetInteractionOverflow(val interactionOverflow: Boolean) : DSWantedIconButtonDemoEvent
        data class SetUseNormalInteraction(val useNormalInteraction: Boolean) : DSWantedIconButtonDemoEvent
        data class SetShowBadge(val showBadge: Boolean) : DSWantedIconButtonDemoEvent
        data class SetBadgePosition(val badgePosition: IconButtonBadgePosition) : DSWantedIconButtonDemoEvent
    }

    data class DSWantedIconButtonDemoViewState(
        val isShowCode: Boolean = false,
        val code: String = "",
        val containerSize: WantedIconButtonSize = WantedIconButtonSize.Medium,
        val normalSize: WantedIconButtonNormalSize = WantedIconButtonNormalSize.Xlarge,
        val useCustomSize: Boolean = false,
        val customBoxSize: Int = DEFAULT_CUSTOM_BOX_SIZE_DP,
        val enabled: Boolean = true,
        val variant: IconButtonVariant = IconButtonVariant.Outlined,
        val interactionEffect: IconButtonInteractionEffect = IconButtonInteractionEffect.Highlight,
        val interactionOverflow: Boolean = false,
        val useNormalInteraction: Boolean = false,
        val showBadge: Boolean = false,
        val badgePosition: IconButtonBadgePosition = IconButtonBadgePosition.TopRight,
    ) : BaseViewState

    sealed interface DSWantedIconButtonDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedIconButtonDemoSideEffect
    }

    sealed interface DSWantedIconButtonDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedIconButtonDemoViewEvent
        data object OnClickShowCode : DSWantedIconButtonDemoViewEvent
        data object OnClickCopyCode : DSWantedIconButtonDemoViewEvent
        data class OnContainerSizeChanged(val size: WantedIconButtonSize) : DSWantedIconButtonDemoViewEvent
        data class OnNormalSizeChanged(val size: WantedIconButtonNormalSize) : DSWantedIconButtonDemoViewEvent
        data class OnUseCustomSizeChanged(val useCustomSize: Boolean) : DSWantedIconButtonDemoViewEvent
        data class OnCustomBoxSizeChanged(val boxSizeDp: Int) : DSWantedIconButtonDemoViewEvent
        data class OnEnabledChanged(val enabled: Boolean) : DSWantedIconButtonDemoViewEvent
        data class OnVariantChanged(val variant: IconButtonVariant) : DSWantedIconButtonDemoViewEvent
        data class OnInteractionEffectChanged(
            val interactionEffect: IconButtonInteractionEffect
        ) : DSWantedIconButtonDemoViewEvent
        data class OnInteractionOverflowChanged(val interactionOverflow: Boolean) : DSWantedIconButtonDemoViewEvent
        data class OnUseNormalInteractionChanged(val useNormalInteraction: Boolean) : DSWantedIconButtonDemoViewEvent
        data class OnShowBadgeChanged(val showBadge: Boolean) : DSWantedIconButtonDemoViewEvent
        data class OnBadgePositionChanged(val badgePosition: IconButtonBadgePosition) : DSWantedIconButtonDemoViewEvent
    }

    enum class IconButtonVariant {
        Normal,
        Outlined,
        Solid,
        Background,
    }

    const val DEFAULT_CUSTOM_BOX_SIZE_DP = 48

    /** Custom 박스 크기 선택 옵션(dp). 80은 상한(64dp) clamp 동작 확인용. */
    val CUSTOM_BOX_SIZE_OPTIONS = listOf(24, 32, 40, 48, 56, 64, 80)
}
