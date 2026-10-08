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
     * @property avatarSize 그룹을 구성하는 개별 아바타의 크기입니다.
     * @property avatarOverlap 아바타끼리 겹치는 폭입니다. 음수 간격으로 적용됩니다.
     * @property trailingSpacing 아바타 묶음과 trailingContent 사이 간격입니다.
     * @property trailingHeight trailingContent 슬롯의 높이입니다. 슬롯은 이 높이로 고정되며(Figma sizing=FILL), 더 큰 콘텐츠는 이 높이에 맞춰 압축됩니다.
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
