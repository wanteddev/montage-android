package com.wanted.android.wanted.design.navigations.topbar.dialogtopbar

import androidx.compose.ui.unit.dp

/**
 * object WantedDialogTopAppBarDefaults
 *
 * Modal Navigation(Popup · Bottom Sheet · Full) 의 여백 기본값을 제공하는 객체입니다. (Figma 4.0.0)
 *
 * Navigation 여백은 Modal 이 아니라 Navigation 컴포넌트가 소유합니다 — Modal 쪽 `navigationPadding` 은 0 으로 두고 이 값을 씁니다.
 */
object WantedDialogTopAppBarDefaults {

    // Popup · Bottom Sheet 의 Navigation 상하좌우 여백 (Figma Margin/Navigation/Normal · Horizontal)
    val NAVIGATION_PADDING = 24.dp

    // Full 의 Navigation 상하좌우 여백 (Figma Margin/Navigation, None)
    val FULL_NAVIGATION_PADDING = 20.dp
}
