package com.wanted.android.wanted.design.badge

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.OPACITY_8


data class WantedContentBadgeDefault(
    val contentColor: Color,
    val backgroundColor: Color = contentColor.copy(OPACITY_8),
    val outLineColor: Color = contentColor
)

object WantedContentBadgeDefaults {
    @Composable
    fun getAccentDefault(
        contentColor: Color = colorResource(id = R.color.accent_cyan),
        backgroundColor: Color = contentColor.copy(OPACITY_8),
        outLineColor: Color = contentColor
    ) = WantedContentBadgeDefault(
        contentColor = contentColor,
        backgroundColor = backgroundColor,
        outLineColor = outLineColor
    )
}