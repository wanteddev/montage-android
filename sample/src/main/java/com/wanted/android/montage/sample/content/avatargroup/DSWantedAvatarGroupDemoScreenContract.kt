package com.wanted.android.montage.sample.content.avatargroup

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.contents.avatar.avatargroup.WantedAvatarGroupDefaults.WantedAvatarGroupSize

object DSWantedAvatarGroupDemoScreenContract {
    /**
     * trailingContent 슬롯에 넣을 프리셋 종류입니다.
     */
    enum class TrailingContentType {
        None,
        Text,
        TextButton
    }

    sealed interface DSWantedAvatarGroupDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedAvatarGroupDemoViewState) :
            DSWantedAvatarGroupDemoEvent

        data class ShowCode(val isShowCode: Boolean) : DSWantedAvatarGroupDemoEvent
        data object CopyCode : DSWantedAvatarGroupDemoEvent
        data class SetSize(val size: WantedAvatarGroupSize) : DSWantedAvatarGroupDemoEvent
        data class SetAvatarCount(val count: Int) : DSWantedAvatarGroupDemoEvent
        data class SetTrailingContentType(val trailingContentType: TrailingContentType) :
            DSWantedAvatarGroupDemoEvent
    }

    data class DSWantedAvatarGroupDemoViewState(
        val isShowCode: Boolean = false,
        val code: String = "",
        val size: WantedAvatarGroupSize = WantedAvatarGroupSize.XSmall,
        val avatarCount: Int = 8,
        val trailingContentType: TrailingContentType = TrailingContentType.Text
    ) : BaseViewState

    sealed interface DSWantedAvatarGroupDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedAvatarGroupDemoSideEffect
    }

    sealed interface DSWantedAvatarGroupDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedAvatarGroupDemoViewEvent
        data object OnClickShowCode : DSWantedAvatarGroupDemoViewEvent
        data object OnClickCopyCode : DSWantedAvatarGroupDemoViewEvent
        data class OnSizeChanged(val size: WantedAvatarGroupSize) : DSWantedAvatarGroupDemoViewEvent
        data class OnAvatarCountChanged(val count: Int) : DSWantedAvatarGroupDemoViewEvent
        data class OnTrailingContentTypeChanged(val trailingContentType: TrailingContentType) :
            DSWantedAvatarGroupDemoViewEvent
    }
}