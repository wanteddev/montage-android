package com.wanted.android.wanted.design.contents.playtime.playbadge

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object WantedPlayBadgeContract {
    enum class Size(val container: Dp, val icon: Dp) {
        Small(36.dp, 24.dp),
        Medium(60.dp, 40.dp),
        Large(80.dp, 56.dp)
    }
}