package com.wanted.android.montage.sample.content.avatar

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults.WantedAvatarSize
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults.WantedAvatarType
import com.wanted.android.wanted.design.feedback.pushbadge.PushBadgeTypes.PushBadgeSize

object DSWantedAvatarDemoScreenContract {
    sealed interface DSWantedAvatarDemoEvent : BaseEvent {
        data class ShowCode(val isShowCode: Boolean) : DSWantedAvatarDemoEvent
        data object CopyCode : DSWantedAvatarDemoEvent
        data class SetType(val type: WantedAvatarType) : DSWantedAvatarDemoEvent
        data class SetSize(val size: WantedAvatarSize) : DSWantedAvatarDemoEvent
        data class SetPushBadge(val pushBadge: Boolean) : DSWantedAvatarDemoEvent
        data class SetIsGroup(val isGroup: Boolean) : DSWantedAvatarDemoEvent
        data class SetIsIcon(val isIcon: Boolean) : DSWantedAvatarDemoEvent
        data class ShowAll(val isShowAll: Boolean) : DSWantedAvatarDemoEvent

        data class SetCustomSize(val size: Dp) : DSWantedAvatarDemoEvent
        data class SetCustomCornerRadius(val cornerRadius: Dp) : DSWantedAvatarDemoEvent
        data class SetCustomBadgeSize(val badgeSize: PushBadgeSize) : DSWantedAvatarDemoEvent
        data class SetCustomBadgeSizeDefault(val isDefault: Boolean) : DSWantedAvatarDemoEvent
    }

    data class DSWantedAvatarDemoViewState(
        val isShowCode: Boolean = false,
        val code: String = "",
        val isShowAll: Boolean = false,

        val typeList: List<WantedAvatarType> = WantedAvatarType.entries.toList(),
        val selectedType: WantedAvatarType = WantedAvatarType.Person,

        val sizeList: List<WantedAvatarSize> = WantedAvatarSize.entries + WantedAvatarSize.Custom(
            40.dp,
            8.dp,
            PushBadgeSize.Small
        ),
        val selectedSize: WantedAvatarSize = WantedAvatarSize.Medium,

        val customSize: Dp = 40.dp,
        val customCornerRadius: Dp = 8.dp,
        val customBadgeSize: PushBadgeSize = PushBadgeSize.Small,
        val isCustomBadgeSizeDefault: Boolean = true,

        val pushBadge: Boolean = false,
        val isGroup: Boolean = false,
        val isIcon: Boolean = false,
    ) : BaseViewState

    sealed interface DSWantedAvatarDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedAvatarDemoSideEffect
    }

    sealed interface DSWantedAvatarDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedAvatarDemoViewEvent
        data object OnClickShowCode : DSWantedAvatarDemoViewEvent
        data object OnClickCopyCode : DSWantedAvatarDemoViewEvent
        data object OnClickShowAll : DSWantedAvatarDemoViewEvent

        data class OnSelectType(val type: WantedAvatarType) : DSWantedAvatarDemoViewEvent
        data class OnSelectSize(val size: WantedAvatarSize) : DSWantedAvatarDemoViewEvent
        data class OnChangePushBadge(val pushBadge: Boolean) : DSWantedAvatarDemoViewEvent
        data class OnChangeIsGroup(val isGroup: Boolean) : DSWantedAvatarDemoViewEvent
        data class OnChangeIsIcon(val isIcon: Boolean) : DSWantedAvatarDemoViewEvent

        data class OnChangeCustomSize(val size: Dp) : DSWantedAvatarDemoViewEvent
        data class OnChangeCustomCornerRadius(val cornerRadius: Dp) : DSWantedAvatarDemoViewEvent
        data class OnChangeCustomBadgeSize(val badgeSize: PushBadgeSize) :
            DSWantedAvatarDemoViewEvent
        data class OnChangeCustomBadgeSizeDefault(val isDefault: Boolean) :
            DSWantedAvatarDemoViewEvent
    }
}
