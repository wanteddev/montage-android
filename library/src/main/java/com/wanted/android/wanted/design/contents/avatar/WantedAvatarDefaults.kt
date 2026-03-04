package com.wanted.android.wanted.design.contents.avatar

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.feedback.pushbadge.PushBadgeTypes


/**
 * object WantedAvatarDefaults
 *
 * Avatar에 사용되는 크기와 유형 관련 설정을 정의하는 객체입니다.
 *
 * 다양한 사이즈와 타입을 설정하여 아바타 UI 요소를 유연하게 구성할 수 있도록 지원합니다.
 */
object WantedAvatarDefaults {
    /**
     * sealed class WantedAvatarSize
     *
     * Avatar의 크기 및 모서리 반경 정보를 포함하는 sealed 클래스입니다.
     *
     * 각 사이즈는 data object로 구체화되어 있으며, 아바타의 가로/세로 크기와 모서리 반경이 함께 정의됩니다.
     *
     * @see WantedAvatar
     */
    sealed class WantedAvatarSize(
        open val size: Dp,
        open val cornerRadius: Dp,
        open val badgeSize: PushBadgeTypes.PushBadgeSize
    ) {
        /**
         * data object XSmall
         *
         * 24dp 크기, 6dp 모서리 반경의 가장 작은 아바타 크기입니다.
         */
        data object XSmall : WantedAvatarSize(24.dp, 6.dp, PushBadgeTypes.PushBadgeSize.XSmall)

        /**
         * data object Small
         *
         * 32dp 크기, 8dp 모서리 반경의 작은 아바타 크기입니다.
         */
        data object Small : WantedAvatarSize(32.dp, 8.dp, PushBadgeTypes.PushBadgeSize.XSmall)

        /**
         * data object Medium
         *
         * 40dp 크기, 10dp 모서리 반경의 중간 아바타 크기입니다.
         */
        data object Medium : WantedAvatarSize(40.dp, 10.dp, PushBadgeTypes.PushBadgeSize.Small)

        /**
         * data object Large
         *
         * 48dp 크기, 12dp 모서리 반경의 큰 아바타 크기입니다.
         */
        data object Large : WantedAvatarSize(48.dp, 12.dp, PushBadgeTypes.PushBadgeSize.Small)

        /**
         * data object XLarge
         *
         * 56dp 크기, 14dp 모서리 반경의 가장 큰 아바타 크기입니다.
         */
        data object XLarge : WantedAvatarSize(56.dp, 14.dp, PushBadgeTypes.PushBadgeSize.Medium)


        /**
         * data class Custom
         *
         * 크기, 모서리 반경, 뱃지 크기를 커스텀 할 수 있는 아바타 크기입니다.
         */
        data class Custom(
            override val size: Dp,
            override val cornerRadius: Dp,
            override val badgeSize: PushBadgeTypes.PushBadgeSize = when {
                size <= 36.dp -> PushBadgeTypes.PushBadgeSize.XSmall
                size <= 52.dp -> PushBadgeTypes.PushBadgeSize.Small
                else -> PushBadgeTypes.PushBadgeSize.Medium
            }
        ) : WantedAvatarSize(size, cornerRadius, badgeSize)

        companion object {
            val entries: List<WantedAvatarSize> = listOf(XSmall, Small, Medium, Large, XLarge)
        }
    }

    /**
     * enum class WantedAvatarType
     *
     * 아바타의 유형을 정의하는 enum 클래스입니다.
     *
     * 아바타가 표현하는 주체의 성격(사람, 회사, 학력 등)에 따라 다음의 유형을 가집니다:
     * - Person: 사람(개인)입니다.
     * - Company: 회사입니다.
     * - Academic: 학력/학교입니다.
     *
     * @see WantedAvatar
     */
    enum class WantedAvatarType {
        Person,
        Company,
        Academic
    }
}
