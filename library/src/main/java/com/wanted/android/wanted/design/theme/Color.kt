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

    val foregroundNeutralPrimary: Color = Color.Transparent,
    val foregroundNeutralStrong: Color = Color.Transparent,
    val foregroundNeutralSecondary: Color = Color.Transparent,
    val foregroundNeutralTertiary: Color = Color.Transparent,
    val foregroundNeutralQuaternary: Color = Color.Transparent,
    val foregroundNeutralInverse: Color = Color.Transparent,
    val foregroundDisablePrimary: Color = Color.Transparent,
    val foregroundInactivePrimary: Color = Color.Transparent,
    val foregroundBrandPrimary: Color = Color.Transparent,
    val foregroundBrandInverse: Color = Color.Transparent,
    val foregroundPositivePrimary: Color = Color.Transparent,
    val foregroundCautionaryPrimary: Color = Color.Transparent,
    val foregroundNegativePrimary: Color = Color.Transparent,
    val foregroundNegativeStrong: Color = Color.Transparent,
    val foregroundAccentLime: Color = Color.Transparent,
    val foregroundAccentCyan: Color = Color.Transparent,
    val foregroundAccentLightBlue: Color = Color.Transparent,
    val foregroundAccentViolet: Color = Color.Transparent,
    val foregroundAccentPurple: Color = Color.Transparent,
    val foregroundAccentPink: Color = Color.Transparent,

    val backgroundNeutralPrimary: Color = Color.Transparent,
    val backgroundNeutralSecondary: Color = Color.Transparent,

    val surfaceNeutralPrimary: Color = Color.Transparent,
    val surfaceNeutralSecondary: Color = Color.Transparent,
    val surfaceNeutralTertiary: Color = Color.Transparent,
    val surfaceNeutralStrong: Color = Color.Transparent,
    val surfaceNeutralInverse: Color = Color.Transparent,
    val surfaceElevatedPrimary: Color = Color.Transparent,
    val surfaceElevatedSecondary: Color = Color.Transparent,
    val surfaceBrandPrimary: Color = Color.Transparent,
    val surfaceBrandStrong: Color = Color.Transparent,
    val surfaceBrandHeavy: Color = Color.Transparent,
    val surfaceBrandSubtle: Color = Color.Transparent,
    val surfacePositivePrimary: Color = Color.Transparent,
    val surfaceCautionaryPrimary: Color = Color.Transparent,
    val surfaceNegativePrimary: Color = Color.Transparent,
    val surfaceNegativeStrong: Color = Color.Transparent,
    val surfaceDisablePrimary: Color = Color.Transparent,
    val surfaceAccentLime: Color = Color.Transparent,
    val surfaceAccentCyan: Color = Color.Transparent,
    val surfaceAccentLightBlue: Color = Color.Transparent,
    val surfaceAccentViolet: Color = Color.Transparent,
    val surfaceAccentPurple: Color = Color.Transparent,
    val surfaceAccentPink: Color = Color.Transparent,
    val surfaceAccentLimeOpaque: Color = Color.Transparent,
    val surfaceAccentCyanOpaque: Color = Color.Transparent,
    val surfaceAccentLightBlueOpaque: Color = Color.Transparent,
    val surfaceAccentVioletOpaque: Color = Color.Transparent,
    val surfaceAccentPurpleOpaque: Color = Color.Transparent,
    val surfaceAccentPinkOpaque: Color = Color.Transparent,

    val lineNeutralPrimary: Color = Color.Transparent,
    val lineNeutralSecondary: Color = Color.Transparent,
    val lineNeutralTertiary: Color = Color.Transparent,
    val lineNeutralPrimaryOpaque: Color = Color.Transparent,
    val lineNeutralSecondaryOpaque: Color = Color.Transparent,
    val lineNeutralTertiaryOpaque: Color = Color.Transparent,
    val lineBrandPrimary: Color = Color.Transparent,
    val lineBrandStrong: Color = Color.Transparent,
    val lineBrandFocus: Color = Color.Transparent,
    val linePositivePrimary: Color = Color.Transparent,
    val lineCautionaryPrimary: Color = Color.Transparent,
    val lineNegativePrimary: Color = Color.Transparent,
    val lineNegativeStrong: Color = Color.Transparent,
    val lineNegativeFocus: Color = Color.Transparent,
    val lineAccentLime: Color = Color.Transparent,
    val lineAccentCyan: Color = Color.Transparent,
    val lineAccentLightBlue: Color = Color.Transparent,
    val lineAccentViolet: Color = Color.Transparent,
    val lineAccentPurple: Color = Color.Transparent,
    val lineAccentPink: Color = Color.Transparent,

    val effectTransparentPrimary: Color = Color.Transparent,
    val effectTransparentSecondary: Color = Color.Transparent,
    val effectDimmerPrimary: Color = Color.Transparent,

    val transparent: Color = Color.Transparent
)

val AppWantedColorScheme: WantedColorScheme
    @Composable
    get() = WantedColorScheme(
        staticWhite = colorResource(id = R.color.static_white),
        staticBlack = colorResource(id = R.color.static_black),

        foregroundNeutralPrimary = colorResource(id = R.color.foreground_neutral_primary),
        foregroundNeutralStrong = colorResource(id = R.color.foreground_neutral_strong),
        foregroundNeutralSecondary = colorResource(id = R.color.foreground_neutral_secondary),
        foregroundNeutralTertiary = colorResource(id = R.color.foreground_neutral_tertiary),
        foregroundNeutralQuaternary = colorResource(id = R.color.foreground_neutral_quaternary),
        foregroundNeutralInverse = colorResource(id = R.color.foreground_neutral_inverse),
        foregroundDisablePrimary = colorResource(id = R.color.foreground_disable_primary),
        foregroundInactivePrimary = colorResource(id = R.color.foreground_inactive_primary),
        foregroundBrandPrimary = colorResource(id = R.color.foreground_brand_primary),
        foregroundBrandInverse = colorResource(id = R.color.foreground_brand_inverse),
        foregroundPositivePrimary = colorResource(id = R.color.foreground_positive_primary),
        foregroundCautionaryPrimary = colorResource(id = R.color.foreground_cautionary_primary),
        foregroundNegativePrimary = colorResource(id = R.color.foreground_negative_primary),
        foregroundNegativeStrong = colorResource(id = R.color.foreground_negative_strong),
        foregroundAccentLime = colorResource(id = R.color.foreground_accent_lime),
        foregroundAccentCyan = colorResource(id = R.color.foreground_accent_cyan),
        foregroundAccentLightBlue = colorResource(id = R.color.foreground_accent_lightblue),
        foregroundAccentViolet = colorResource(id = R.color.foreground_accent_violet),
        foregroundAccentPurple = colorResource(id = R.color.foreground_accent_purple),
        foregroundAccentPink = colorResource(id = R.color.foreground_accent_pink),

        backgroundNeutralPrimary = colorResource(id = R.color.background_neutral_primary),
        backgroundNeutralSecondary = colorResource(id = R.color.background_neutral_secondary),

        surfaceNeutralPrimary = colorResource(id = R.color.surface_neutral_primary),
        surfaceNeutralSecondary = colorResource(id = R.color.surface_neutral_secondary),
        surfaceNeutralTertiary = colorResource(id = R.color.surface_neutral_tertiary),
        surfaceNeutralStrong = colorResource(id = R.color.surface_neutral_strong),
        surfaceNeutralInverse = colorResource(id = R.color.surface_neutral_inverse),
        surfaceElevatedPrimary = colorResource(id = R.color.surface_elevated_primary),
        surfaceElevatedSecondary = colorResource(id = R.color.surface_elevated_secondary),
        surfaceBrandPrimary = colorResource(id = R.color.surface_brand_primary),
        surfaceBrandStrong = colorResource(id = R.color.surface_brand_strong),
        surfaceBrandHeavy = colorResource(id = R.color.surface_brand_heavy),
        surfaceBrandSubtle = colorResource(id = R.color.surface_brand_subtle),
        surfacePositivePrimary = colorResource(id = R.color.surface_positive_primary),
        surfaceCautionaryPrimary = colorResource(id = R.color.surface_cautionary_primary),
        surfaceNegativePrimary = colorResource(id = R.color.surface_negative_primary),
        surfaceNegativeStrong = colorResource(id = R.color.surface_negative_strong),
        surfaceDisablePrimary = colorResource(id = R.color.surface_disable_primary),
        surfaceAccentLime = colorResource(id = R.color.surface_accent_lime),
        surfaceAccentCyan = colorResource(id = R.color.surface_accent_cyan),
        surfaceAccentLightBlue = colorResource(id = R.color.surface_accent_lightblue),
        surfaceAccentViolet = colorResource(id = R.color.surface_accent_violet),
        surfaceAccentPurple = colorResource(id = R.color.surface_accent_purple),
        surfaceAccentPink = colorResource(id = R.color.surface_accent_pink),
        surfaceAccentLimeOpaque = colorResource(id = R.color.surface_accent_lime_opaque),
        surfaceAccentCyanOpaque = colorResource(id = R.color.surface_accent_cyan_opaque),
        surfaceAccentLightBlueOpaque = colorResource(id = R.color.surface_accent_lightblue_opaque),
        surfaceAccentVioletOpaque = colorResource(id = R.color.surface_accent_violet_opaque),
        surfaceAccentPurpleOpaque = colorResource(id = R.color.surface_accent_purple_opaque),
        surfaceAccentPinkOpaque = colorResource(id = R.color.surface_accent_pink_opaque),

        lineNeutralPrimary = colorResource(id = R.color.line_neutral_primary),
        lineNeutralSecondary = colorResource(id = R.color.line_neutral_secondary),
        lineNeutralTertiary = colorResource(id = R.color.line_neutral_tertiary),
        lineNeutralPrimaryOpaque = colorResource(id = R.color.line_neutral_primary_opaque),
        lineNeutralSecondaryOpaque = colorResource(id = R.color.line_neutral_secondary_opaque),
        lineNeutralTertiaryOpaque = colorResource(id = R.color.line_neutral_tertiary_opaque),
        lineBrandPrimary = colorResource(id = R.color.line_brand_primary),
        lineBrandStrong = colorResource(id = R.color.line_brand_strong),
        lineBrandFocus = colorResource(id = R.color.line_brand_focus),
        linePositivePrimary = colorResource(id = R.color.line_positive_primary),
        lineCautionaryPrimary = colorResource(id = R.color.line_cautionary_primary),
        lineNegativePrimary = colorResource(id = R.color.line_negative_primary),
        lineNegativeStrong = colorResource(id = R.color.line_negative_strong),
        lineNegativeFocus = colorResource(id = R.color.line_negative_focus),
        lineAccentLime = colorResource(id = R.color.line_accent_lime),
        lineAccentCyan = colorResource(id = R.color.line_accent_cyan),
        lineAccentLightBlue = colorResource(id = R.color.line_accent_lightblue),
        lineAccentViolet = colorResource(id = R.color.line_accent_violet),
        lineAccentPurple = colorResource(id = R.color.line_accent_purple),
        lineAccentPink = colorResource(id = R.color.line_accent_pink),

        effectTransparentPrimary = colorResource(id = R.color.effect_transparent_primary),
        effectTransparentSecondary = colorResource(id = R.color.effect_transparent_secondary),
        effectDimmerPrimary = colorResource(id = R.color.effect_dimmer_primary),
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
            primary = current.surfaceBrandPrimary,
            secondary = current.surfaceBrandPrimary,
            background = current.backgroundNeutralPrimary,
            surface = current.backgroundNeutralPrimary,
            error = current.backgroundNeutralPrimary,
            onPrimary = current.foregroundNeutralPrimary,
            onSecondary = current.foregroundNeutralPrimary,
            onBackground = current.foregroundNeutralPrimary,
            onSurface = current.foregroundNeutralPrimary,
            onError = current.foregroundNegativePrimary,
        )
    } else {
        lightColorScheme(
            primary = current.surfaceBrandPrimary,
            secondary = current.surfaceBrandPrimary,
            background = current.backgroundNeutralPrimary,
            surface = current.backgroundNeutralPrimary,
            error = current.backgroundNeutralPrimary,
            onPrimary = current.foregroundNeutralPrimary,
            onSecondary = current.foregroundNeutralPrimary,
            onBackground = current.foregroundNeutralPrimary,
            onSurface = current.foregroundNeutralPrimary,
            onError = current.foregroundNegativePrimary
        )
    }
}