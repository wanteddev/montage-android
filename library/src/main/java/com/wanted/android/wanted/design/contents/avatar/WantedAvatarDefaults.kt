package com.wanted.android.wanted.design.contents.avatar

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.feedback.pushbadge.PushBadgeTypes
import kotlin.math.ceil
import kotlin.math.roundToInt


/**
 * object WantedAvatarDefaults
 *
 * Avatar에 사용되는 크기와 유형 관련 설정을 정의하는 객체입니다.
 *
 * 다양한 사이즈와 타입을 설정하여 아바타 UI 요소를 유연하게 구성할 수 있도록 지원합니다.
 *
 * @property MAX_GROUP_VISIBLE_COUNT Int: WantedAvatarGroup이 한 번에 표시하는 최대 아바타 수입니다. 초과 인원은 렌더링하지 않으므로 호출부가 `trailingContent`로 처리합니다(예: "외 N명").
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
         * 24dp 크기, 8dp 모서리 반경의 가장 작은 아바타 크기입니다.
         */
        data object XSmall : WantedAvatarSize(24.dp, 8.dp, PushBadgeTypes.PushBadgeSize.XSmall)

        /**
         * data object Small
         *
         * 32dp 크기, 10dp 모서리 반경의 작은 아바타 크기입니다.
         */
        data object Small : WantedAvatarSize(32.dp, 10.dp, PushBadgeTypes.PushBadgeSize.XSmall)

        /**
         * data object Medium
         *
         * 40dp 크기, 12dp 모서리 반경의 중간 아바타 크기입니다.
         */
        data object Medium : WantedAvatarSize(40.dp, 12.dp, PushBadgeTypes.PushBadgeSize.Small)

        /**
         * data object Large
         *
         * 48dp 크기, 14dp 모서리 반경의 큰 아바타 크기입니다.
         */
        data object Large : WantedAvatarSize(48.dp, 14.dp, PushBadgeTypes.PushBadgeSize.Small)

        /**
         * data object XLarge
         *
         * 56dp 크기, 16dp 모서리 반경의 가장 큰 아바타 크기입니다.
         */
        data object XLarge : WantedAvatarSize(56.dp, 16.dp, PushBadgeTypes.PushBadgeSize.Medium)


        /**
         * data class Custom
         *
         * 크기, 모서리 반경, 뱃지 크기를 커스텀 할 수 있는 아바타 크기입니다.
         * cornerRadius를 지정하지 않으면 `ceil((size × 0.25) / 2) × 2 + 2` 공식으로 계산합니다.
         */
        data class Custom(
            override val size: Dp,
            override val cornerRadius: Dp = (ceil((size.value * 0.25) / 2).toInt() * 2 + 2).dp,
            override val badgeSize: PushBadgeTypes.PushBadgeSize = when {
                size <= 36.dp -> PushBadgeTypes.PushBadgeSize.XSmall
                size <= 52.dp -> PushBadgeTypes.PushBadgeSize.Small
                else -> PushBadgeTypes.PushBadgeSize.Medium
            }
        ) : WantedAvatarSize(size, cornerRadius, badgeSize)

        companion object {
            // 고정 사이즈 목록입니다. [Custom]은 포함하지 않습니다.
            //
            // 즉시 초기화하면 안 된다 — companion 초기화는 [WantedAvatarSize]의 클래스 초기화 안에서 돌고,
            // 그 초기화는 `XSmall` 같은 하위 data object를 먼저 만드는 경로로도 진입한다.
            // 그 경우 아직 `INSTANCE`가 배정되지 않은 object가 리스트에 `null`로 담겨,
            // 사용처의 `it::class` 같은 접근에서 NPE가 난다. lazy로 첫 접근 시점까지 미룬다.
            val entries: List<WantedAvatarSize> by lazy {
                listOf(XSmall, Small, Medium, Large, XLarge)
            }
        }
    }

    // 그룹 아바타(`isGroup = true`)의 겹침 분리 테두리 두께입니다.
    //
    // 배경색과 매칭시켜 겹쳐진 아바타 사이를 시각적으로 분리하는 용도이며, 단독 아바타에는 적용되지 않습니다.
    internal val groupBorderWidth: Dp = 1.5.dp

    // WantedAvatarGroup이 한 번에 표시하는 최대 아바타 수입니다.
    //
    // 초과 인원은 렌더링하지 않으므로 호출부가 `trailingContent`로 처리합니다(예: "외 N명").
    const val MAX_GROUP_VISIBLE_COUNT: Int = 5

    // Person 타입 아바타의 Push Badge inset(상단·우측 padding)입니다.
    // 고정 사이즈: XSmall 4 / Small 5 / Medium 6 / Large 7 / XLarge 8,
    // Custom: `size / 8 + 1`.
    internal fun personPushBadgeInset(size: WantedAvatarSize): Dp = when (size) {
        WantedAvatarSize.XSmall -> 4.dp
        WantedAvatarSize.Small -> 5.dp
        WantedAvatarSize.Medium -> 6.dp
        WantedAvatarSize.Large -> 7.dp
        WantedAvatarSize.XLarge -> 8.dp
        is WantedAvatarSize.Custom -> (size.size.value / 8 + 1).dp
    }

    // Company·Academy 타입 아바타의 Push Badge inset(상단·우측 padding)입니다.
    // 고정 사이즈: XSmall 2 / Small 3 / Medium 4 / Large 4 / XLarge 5,
    // Custom: `round(size × 0.09)`.
    internal fun contentsPushBadgeInset(size: WantedAvatarSize): Dp = when (size) {
        WantedAvatarSize.XSmall -> 2.dp
        WantedAvatarSize.Small -> 3.dp
        WantedAvatarSize.Medium -> 4.dp
        WantedAvatarSize.Large -> 4.dp
        WantedAvatarSize.XLarge -> 5.dp
        is WantedAvatarSize.Custom -> (size.size.value * 0.09).roundToInt().dp
    }

    /**
     * enum class WantedAvatarType
     *
     * 아바타의 유형을 정의하는 enum 클래스입니다.
     *
     * 아바타가 표현하는 주체의 성격(사람, 회사, 학력 등)에 따라 다음의 유형을 가집니다:
     * - Person: 사람(개인)입니다.
     * - Company: 회사입니다.
     * - Academy: 학력/학교입니다.
     *
     * @see WantedAvatar
     */
    enum class WantedAvatarType {
        Person,
        Company,
        Academy
    }
}
