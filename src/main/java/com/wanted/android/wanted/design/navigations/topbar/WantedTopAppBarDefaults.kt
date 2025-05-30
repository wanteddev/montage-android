package com.wanted.android.wanted.design.navigations.topbar

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable

object WantedTopAppBarDefaults {

    /**
     * TopAppBar에 적용되는 기본 WindowInsets입니다.
     *
     * 시스템 바(systemBars)의 수평 및 상단 영역만을 고려합니다.
     *
     * 사용 예시:
     * ```kotlin
     * Modifier.windowInsetsPadding(WantedTopAppBarDefaults.windowInsets)
     * ```
     *
     * @return WindowInsets: 시스템 바 인셋 중 수평 및 상단 영역만 포함한 Insets입니다.
     */
    val windowInsets: WindowInsets
        @Composable
        get() = WindowInsets.systemBars
            .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
}