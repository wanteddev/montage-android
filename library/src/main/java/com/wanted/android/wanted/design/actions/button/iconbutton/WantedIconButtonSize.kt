package com.wanted.android.wanted.design.actions.button.iconbutton

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 아이콘 버튼의 크기 및 내부 패딩을 정의하는 enum 클래스입니다.
 *
 * Medium - 표준 사이즈 (40dp)로, 대부분의 UI에 적합합니다.
 * Small - 소형 사이즈 (32dp)로, 공간이 제한된 영역에 적합합니다.
 *
 * 각 값은 버튼의 전체 크기(size)와 아이콘과 배경 간의 간격을 나타내는 padding을 제공합니다.
 *
 * @param size Dp: 버튼의 전체 크기입니다.
 * @param padding Dp: 아이콘 내부 패딩입니다.
 */
enum class WantedIconButtonSize(val size: Dp, val padding: Dp) {
    Medium(40.dp, 10.dp),
    Small(32.dp, 7.dp)
}