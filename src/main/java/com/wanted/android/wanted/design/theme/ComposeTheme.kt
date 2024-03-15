package com.wanted.android.wanted.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.wanted.android.designsystem.R

@Composable
fun WantedTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        getDarkColorColor()
    } else {
        getLightColor()
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
}

@Composable
private fun getLightColor() = lightColors(
    primary = colorResource(id = R.color.primary_normal),
    background = colorResource(id = R.color.background_normal_normal),
    surface = colorResource(id = R.color.background_normal_normal),
    secondary = colorResource(id = R.color.primary_normal)
)

@Composable
private fun getDarkColorColor() = darkColors(
    primary = colorResource(id = R.color.primary_normal),
    background = colorResource(id = R.color.background_normal_normal),
    surface = colorResource(id = R.color.background_normal_normal),
    secondary = colorResource(id = R.color.primary_normal)
)

@Composable
fun CareerReportTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) {
        darkColors(
            primary = colorResource(id = R.color.primary_normal),
            background = colorResource(id = R.color.background_normal_normal),
            surface = colorResource(id = R.color.background_normal_normal),
            secondary = colorResource(id = R.color.primary_normal),
        )
    } else {
        lightColors(
            primary = colorResource(id = R.color.primary_normal),
            background = colorResource(id = R.color.background_normal_normal),
            surface = colorResource(id = R.color.background_normal_normal),
            secondary = colorResource(id = R.color.primary_normal),
        )
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
}