package com.wanted.android.wanted.design.contents.contentbadge

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.OPACITY_16
import com.wanted.android.wanted.design.util.OPACITY_8


data class WantedContentBadgeDefault(
    val contentColor: Color,
    val backgroundColor: Color = contentColor.copy(OPACITY_8),
    val outLineColor: Color = contentColor
)

object WantedContentBadgeDefaults {
    @Composable
    fun getAccentDefault(
        contentColor: Color = colorResource(id = R.color.accent_background_cyan),
        backgroundColor: Color = contentColor.copy(OPACITY_8),
        outLineColor: Color = contentColor
    ) = WantedContentBadgeDefault(
        contentColor = contentColor,
        backgroundColor = backgroundColor,
        outLineColor = outLineColor
    )

    @Composable
    fun getNeutralDefault(
        contentColor: Color = colorResource(id = R.color.label_alternative),
    ) = WantedContentBadgeDefault(
        contentColor = contentColor,
        backgroundColor = colorResource(id = R.color.fill_normal),
        outLineColor = colorResource(id = R.color.label_alternative).copy(OPACITY_16)
    )
}