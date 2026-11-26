package com.wanted.android.wanted.design.navigations.topbar

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarContract.Variant

/**
 * object WantedTopAppBarToolbarDefaults
 *
 * TopAppBar toolbar 영역의 Slot 프리셋 기본 값입니다.
 *
 * @property SlotVerticalPadding Dp: Slot 콘텐츠의 상하 여백입니다.
 * @property SlotHorizontalPadding Dp: Slot 콘텐츠의 좌우 여백입니다. 가로 스크롤 시 콘텐츠 처음과 끝에만 붙습니다.
 * @property SlotItemSpacing Dp: Slot 아이템 사이 간격입니다.
 */
object WantedTopAppBarToolbarDefaults {
    val SlotVerticalPadding: Dp = 8.dp
    val SlotHorizontalPadding: Dp = 20.dp
    val SlotItemSpacing: Dp = 8.dp
}

/**
 * Variant 가 toolbar 영역을 그리는지 여부입니다. Floating 은 toolbar 를 쓰지 않습니다.
 */
internal val Variant.supportsToolbar: Boolean
    get() = this != Variant.Floating
