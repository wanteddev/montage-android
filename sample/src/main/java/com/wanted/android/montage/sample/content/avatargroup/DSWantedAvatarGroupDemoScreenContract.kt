package com.wanted.android.montage.sample.content.avatargroup

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults.WantedAvatarSize
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults.WantedAvatarType

object DSWantedAvatarGroupDemoScreenContract {
    sealed interface DSWantedAvatarGroupDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedAvatarGroupDemoViewState) :
            DSWantedAvatarGroupDemoEvent

        data class ShowCode(val isShowCode: Boolean) : DSWantedAvatarGroupDemoEvent
        data object CopyCode : DSWantedAvatarGroupDemoEvent
        data class SetSize(val size: WantedAvatarSize) : DSWantedAvatarGroupDemoEvent
        data class SetType(val type: WantedAvatarType) : DSWantedAvatarGroupDemoEvent
        data class SetShowTrailing(val show: Boolean) : DSWantedAvatarGroupDemoEvent
    }

    data class DSWantedAvatarGroupDemoViewState(
        val isLoading: Boolean = true,
        val isShowCode: Boolean = false,
        val code: String = "",
        val size: WantedAvatarSize = WantedAvatarSize.Medium,
        val type: WantedAvatarType = WantedAvatarType.Person,
        val showTrailing: Boolean = true,
    ) : BaseViewState

    sealed interface DSWantedAvatarGroupDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedAvatarGroupDemoSideEffect
    }

    sealed interface DSWantedAvatarGroupDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedAvatarGroupDemoViewEvent
        data object OnClickShowCode : DSWantedAvatarGroupDemoViewEvent
        data object OnClickCopyCode : DSWantedAvatarGroupDemoViewEvent
        data class OnSizeChanged(val size: WantedAvatarSize) : DSWantedAvatarGroupDemoViewEvent
        data class OnTypeChanged(val type: WantedAvatarType) : DSWantedAvatarGroupDemoViewEvent
        data class OnShowTrailingChanged(val show: Boolean) : DSWantedAvatarGroupDemoViewEvent
    }
}