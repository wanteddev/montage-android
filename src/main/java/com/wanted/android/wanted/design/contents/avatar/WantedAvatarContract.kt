package com.wanted.android.wanted.design.contents.avatar

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


object WantedAvatarContract {
    /**
     * 아바타 크기와 모서리 반경을 정의하는 Sealed Class입니다.
     *
     * @property size Dp: 아바타의 가로/세로 크기입니다.
     * @property cornerRadius Dp: 아바타 모서리의 반경입니다.
     *
     * @see WantedAvatar
     */
    sealed class WantedAvatarSize(
        open val size: Dp,
        open val cornerRadius: Dp
    ) {

        data object XSmall : WantedAvatarSize(24.dp, 6.dp)
        data object Small : WantedAvatarSize(32.dp, 6.dp)
        data object Medium : WantedAvatarSize(40.dp, 8.dp)
        data object Large : WantedAvatarSize(48.dp, 10.dp)
        data object XLarge : WantedAvatarSize(56.dp, 12.dp)

        companion object {
            val entries: List<WantedAvatarSize> = listOf(XSmall, Small, Medium, Large, XLarge)
        }
    }

    /**
     * 아바타의 유형을 나타내는 Enum입니다.
     *
     * @see WantedAvatar
     */
    enum class WantedAvatarType {
        /**
         * 사람(개인) 아바타 유형입니다.
         */
        Person,

        /**
         * 회사 아바타 유형입니다.
         */
        Company,

        /**
         * 학력/학교 아바타 유형입니다.
         */
        Academic
    }
}
