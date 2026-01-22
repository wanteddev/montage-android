package com.wanted.android.wanted.design.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.wanted.android.designsystem.R


data class WantedColorScheme(
    val staticWhite: Color = Color.Transparent,
    val staticBlack: Color = Color.Transparent,

    val primaryNormal: Color = Color.Transparent,
    val primaryStrong: Color = Color.Transparent,
    val primaryHeavy: Color = Color.Transparent,

    val labelNormal: Color = Color.Transparent,
    val labelStrong: Color = Color.Transparent,
    val labelNeutral: Color = Color.Transparent,
    val labelAlternative: Color = Color.Transparent,
    val labelAssistive: Color = Color.Transparent,
    val labelDisable: Color = Color.Transparent,

    val backgroundNormalNormal: Color = Color.Transparent,
    val backgroundNormalAlternative: Color = Color.Transparent,
    val backgroundElevatedNormal: Color = Color.Transparent,
    val backgroundElevatedAlternative: Color = Color.Transparent,
    val backgroundTransparentNormal: Color = Color.Transparent,
    val backgroundTransparentAlternative: Color = Color.Transparent,

    val interactionInactive: Color = Color.Transparent,
    val interactionDisable: Color = Color.Transparent,

    val lineNormalNormal: Color = Color.Transparent,
    val lineNormalNeutral: Color = Color.Transparent,
    val lineNormalAlternative: Color = Color.Transparent,
    val lineSolidNormal: Color = Color.Transparent,
    val lineSolidNeutral: Color = Color.Transparent,
    val lineSolidAlternative: Color = Color.Transparent,

    val statusPositive: Color = Color.Transparent,
    val statusNegative: Color = Color.Transparent,
    val statusCautionary: Color = Color.Transparent,

    val accentBackgroundLime: Color = Color.Transparent,
    val accentBackgroundCyan: Color = Color.Transparent,
    val accentBackgroundLightBlue: Color = Color.Transparent,
    val accentBackgroundViolet: Color = Color.Transparent,
    val accentBackgroundPurple: Color = Color.Transparent,
    val accentBackgroundPink: Color = Color.Transparent,
    val accentBackgroundRedOrange: Color = Color.Transparent,

    val accentForegroundRed: Color = Color.Transparent,
    val accentForegroundRedOrange: Color = Color.Transparent,
    val accentForegroundOrange: Color = Color.Transparent,
    val accentForegroundLime: Color = Color.Transparent,
    val accentForegroundGreen: Color = Color.Transparent,
    val accentForegroundCyan: Color = Color.Transparent,
    val accentForegroundLightBlue: Color = Color.Transparent,
    val accentForegroundBlue: Color = Color.Transparent,
    val accentForegroundViolet: Color = Color.Transparent,
    val accentForegroundPurple: Color = Color.Transparent,
    val accentForegroundPink: Color = Color.Transparent,

    val inversePrimary: Color = Color.Transparent,
    val inverseBackground: Color = Color.Transparent,
    val inverseLabel: Color = Color.Transparent,

    val fillNormal: Color = Color.Transparent,
    val fillStrong: Color = Color.Transparent,
    val fillAlternative: Color = Color.Transparent,

    val materialDimmer: Color = Color.Transparent,

    val transparent: Color = Color.Transparent
)

val AppWantedColorScheme: WantedColorScheme
    @Composable
    get() = WantedColorScheme(
        staticWhite = colorResource(id = R.color.static_white),
        staticBlack = colorResource(id = R.color.static_black),

        primaryNormal = colorResource(id = R.color.primary_normal),
        primaryStrong = colorResource(id = R.color.primary_strong),
        primaryHeavy = colorResource(id = R.color.primary_heavy),

        labelNormal = colorResource(id = R.color.label_normal),
        labelStrong = colorResource(id = R.color.label_strong),
        labelNeutral = colorResource(id = R.color.label_neutral),
        labelAlternative = colorResource(id = R.color.label_alternative),
        labelAssistive = colorResource(id = R.color.label_assistive),
        labelDisable = colorResource(id = R.color.label_disable),

        backgroundNormalNormal = colorResource(id = R.color.background_normal_normal),
        backgroundNormalAlternative = colorResource(id = R.color.background_normal_alternative),
        backgroundElevatedNormal = colorResource(id = R.color.background_elevated_normal),
        backgroundElevatedAlternative = colorResource(id = R.color.background_elevated_alternative),
        backgroundTransparentNormal = colorResource(id = R.color.background_transparent_normal),
        backgroundTransparentAlternative = colorResource(id = R.color.background_transparent_alternative),

        interactionInactive = colorResource(id = R.color.interaction_inactive),
        interactionDisable = colorResource(id = R.color.interaction_disable),

        lineNormalNormal = colorResource(id = R.color.line_normal_normal),
        lineNormalNeutral = colorResource(id = R.color.line_normal_neutral),
        lineNormalAlternative = colorResource(id = R.color.line_normal_alternative),
        lineSolidNormal = colorResource(id = R.color.line_solid_normal),
        lineSolidNeutral = colorResource(id = R.color.line_solid_neutral),
        lineSolidAlternative = colorResource(id = R.color.line_solid_alternative),

        statusPositive = colorResource(id = R.color.status_positive),
        statusNegative = colorResource(id = R.color.status_negative),
        statusCautionary = colorResource(id = R.color.status_cautionary),

        accentBackgroundLime = colorResource(R.color.accent_background_lime),
        accentBackgroundCyan = colorResource(R.color.accent_background_cyan),
        accentBackgroundLightBlue = colorResource(R.color.accent_background_lightblue),
        accentBackgroundViolet = colorResource(R.color.accent_background_violet),
        accentBackgroundPurple = colorResource(R.color.accent_background_purple),
        accentBackgroundPink = colorResource(R.color.accent_background_pink),
        accentBackgroundRedOrange = colorResource(R.color.accent_background_redorange),

        accentForegroundRed = colorResource(R.color.accent_foreground_red),
        accentForegroundRedOrange = colorResource(R.color.accent_foreground_redorange),
        accentForegroundOrange = colorResource(R.color.accent_foreground_orange),
        accentForegroundLime = colorResource(R.color.accent_foreground_lime),
        accentForegroundGreen = colorResource(R.color.accent_foreground_green),
        accentForegroundCyan = colorResource(R.color.accent_foreground_cyan),
        accentForegroundLightBlue = colorResource(R.color.accent_foreground_lightblue),
        accentForegroundBlue = colorResource(R.color.accent_foreground_blue),
        accentForegroundViolet = colorResource(R.color.accent_foreground_violet),
        accentForegroundPurple = colorResource(R.color.accent_foreground_purple),
        accentForegroundPink = colorResource(R.color.accent_foreground_pink),

        inversePrimary = colorResource(id = R.color.inverse_primary),
        inverseLabel = colorResource(id = R.color.inverse_label),
        inverseBackground = colorResource(id = R.color.inverse_background),

        fillNormal = colorResource(id = R.color.fill_normal),
        fillStrong = colorResource(id = R.color.fill_strong),
        fillAlternative = colorResource(id = R.color.fill_alternative),

        materialDimmer = colorResource(id = R.color.material_dimmer),
    )

internal val LocalWantedColorScheme = WantedColorSchemeLocal()

@JvmInline
value class WantedColorSchemeLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedColorScheme> = staticCompositionLocalOf { WantedColorScheme() }
) {
    val current: WantedColorScheme
        @Composable get() = delegate.current

    infix fun provides(value: WantedColorScheme) = delegate provides value


    @Composable
    fun getSystemColor(isDarkTheme: Boolean) = if (isDarkTheme) {
        darkColorScheme(
            primary = current.primaryNormal,
            secondary = current.primaryNormal,
            background = current.backgroundNormalNormal,
            surface = current.backgroundNormalNormal,
            error = current.backgroundNormalNormal,
            onPrimary = current.labelNormal,
            onSecondary = current.labelNormal,
            onBackground = current.labelNormal,
            onSurface = current.labelNormal,
            onError = current.statusNegative,
        )
    } else {
        lightColorScheme(
            primary = current.primaryNormal,
            secondary = current.primaryNormal,
            background = current.backgroundNormalNormal,
            surface = current.backgroundNormalNormal,
            error = current.backgroundNormalNormal,
            onPrimary = current.labelNormal,
            onSecondary = current.labelNormal,
            onBackground = current.labelNormal,
            onSurface = current.labelNormal,
            onError = current.statusNegative
        )
    }
}