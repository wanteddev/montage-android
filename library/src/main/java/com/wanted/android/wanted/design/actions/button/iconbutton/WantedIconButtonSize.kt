package com.wanted.android.wanted.design.actions.button.iconbutton

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * enum class WantedIconButtonSize
 *
 * 아이콘 버튼의 크기 및 내부 패딩을 정의하는 enum 클래스입니다.
 *
 * - Medium: 표준 사이즈 (40dp)로, 대부분의 UI에 적합합니다.
 * - Small: 소형 사이즈 (32dp)로, 공간이 제한된 영역에 적합합니다.
 */
enum class WantedIconButtonSize(val size: Dp, val padding: Dp) {
    Medium(40.dp, 10.dp),
    Small(32.dp, 7.dp)
}