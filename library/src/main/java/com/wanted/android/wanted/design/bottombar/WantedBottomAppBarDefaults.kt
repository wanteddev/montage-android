package com.wanted.android.wanted.design.bottombar

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable

object WantedBottomAppBarDefaults  {
    val windowInsets: WindowInsets
        @Composable
        get() {
            return WindowInsets.systemBars
                .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
        }
}