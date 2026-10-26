package com.wanted.android.wanted.design.contents.avatar.avatargroup

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults.WantedAvatarSize

/**
 * object WantedAvatarGroupDefaults
 *
 * Avatar Group 컴포넌트에서 사용하는 설정 값을 정의하는 객체입니다.
 */
object WantedAvatarGroupDefaults {

    /**
     * enum class WantedAvatarGroupSize
     *
     * Avatar Group 이 지원하는 크기입니다.
     *
     * 단독 [WantedAvatarSize] 는 5종 + Custom 이지만, Avatar Group 은 Figma·iOS 와 동일하게
     * XSmall·Small 2종만 제공합니다. 나머지 크기는 그룹 스펙(겹침 폭·trailing 간격·trailing 높이)이
     * 정의되어 있지 않습니다.
     *
     * - XSmall: 아바타 24dp, 겹침 6dp, trailing 간격 8dp, trailing 슬롯 높이 24dp 입니다.
     * - Small: 아바타 32dp, 겹침 8dp, trailing 간격 10dp, trailing 슬롯 높이 32dp 입니다.
     *
     * @property avatarSize WantedAvatarSize: 그룹을 구성하는 개별 아바타의 크기입니다.
     */
    enum class WantedAvatarGroupSize(
        val avatarSize: WantedAvatarSize,
        internal val avatarOverlap: Dp,
        internal val trailingSpacing: Dp,
        internal val trailingHeight: Dp
    ) {
        XSmall(
            avatarSize = WantedAvatarSize.XSmall,
            avatarOverlap = (-6).dp,
            trailingSpacing = 8.dp,
            trailingHeight = 24.dp
        ),
        Small(
            avatarSize = WantedAvatarSize.Small,
            avatarOverlap = (-8).dp,
            trailingSpacing = 10.dp,
            trailingHeight = 32.dp
        )
    }
}
