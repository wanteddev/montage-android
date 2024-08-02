package com.wanted.android.wanted.design.select

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class WantedSelectData(
    val id: String = "",
    val text: String = "",
    val iconUrl: String = "",
    @DrawableRes val iconRes: Int = 0,
    @ColorRes val tint: Int = 0
)