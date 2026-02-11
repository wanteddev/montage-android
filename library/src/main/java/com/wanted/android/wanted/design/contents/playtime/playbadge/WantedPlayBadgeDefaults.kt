package com.wanted.android.wanted.design.contents.playtime.playbadge

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * object WantedPlayBadgeDefaults
 *
 * Play badge 컴포넌트에서 사용되는 설정값을 정의하는 객체입니다.
 *
 * 주로 badge의 크기와 관련된 정보를 제공합니다.
 */
object WantedPlayBadgeDefaults {

    /**
     * enum class Size
     *
     * PlayBadge의 크기를 정의하는 enum 클래스입니다.
     *
     * 각 값은 배지 컨테이너 크기와 아이콘 크기를 함께 정의합니다.
     * 제공되는 옵션은 다음과 같습니다:
     * - Small: 컨테이너 36dp, 아이콘 24dp입니다.
     * - Medium: 컨테이너 60dp, 아이콘 40dp입니다.
     * - Large: 컨테이너 80dp, 아이콘 56dp입니다.
     *
     * @property container Dp: PlayBadge 외곽 컨테이너 크기입니다.
     * @property icon Dp: Play 아이콘의 크기입니다.
     */
    enum class Size(val container: Dp, val icon: Dp) {
        Small(36.dp, 24.dp),
        Medium(60.dp, 40.dp),
        Large(80.dp, 56.dp)
    }
}