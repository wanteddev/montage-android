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
        data class ShowAll(val isShowAll: Boolean) : DSWantedAvatarDemoEvent
        data class SetModel(val model: DSWantedAvatarDemoModel) : DSWantedAvatarDemoEvent
        data class SetColoredBackground(val isColoredBackground: Boolean) : DSWantedAvatarDemoEvent

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

        val modelList: List<DSWantedAvatarDemoModel> = DSWantedAvatarDemoModel.entries.toList(),
        val selectedModel: DSWantedAvatarDemoModel = DSWantedAvatarDemoModel.None,
        val isColoredBackground: Boolean = false,
    ) : BaseViewState

    // 미리보기 아바타에 넘길 이미지 모델 종류.
    // 기본 placeholder 는 model 이 없을 때와 이미지 로딩에 실패했을 때 두 경로로 그려지므로 둘 다 확인할 수 있게 둔다.
    enum class DSWantedAvatarDemoModel {
        /** model = null — placeholder 를 바로 그린다. */
        None,

        /** 존재하지 않는 주소 — Glide 로딩 중·실패 placeholder 를 그린다. */
        LoadFail,

        /** Drawable 리소스 — 실제 이미지를 그린다. */
        Image
    }

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
        data class OnSelectModel(val model: DSWantedAvatarDemoModel) : DSWantedAvatarDemoViewEvent
        data class OnChangeColoredBackground(val isColoredBackground: Boolean) :
            DSWantedAvatarDemoViewEvent

        data class OnChangeCustomSize(val size: Dp) : DSWantedAvatarDemoViewEvent
        data class OnChangeCustomCornerRadius(val cornerRadius: Dp) : DSWantedAvatarDemoViewEvent
        data class OnChangeCustomBadgeSize(val badgeSize: PushBadgeSize) :
            DSWantedAvatarDemoViewEvent
        data class OnChangeCustomBadgeSizeDefault(val isDefault: Boolean) :
            DSWantedAvatarDemoViewEvent
    }
}
