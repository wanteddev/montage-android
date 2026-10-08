package com.wanted.android.wanted.design.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.wanted.android.designsystem.R


data class WantedColorOpacityScheme(
    val staticWhiteOpacity5: Color = Color.Transparent,
    val staticWhiteOpacity8: Color = Color.Transparent,
    val staticWhiteOpacity12: Color = Color.Transparent,
    val staticWhiteOpacity22: Color = Color.Transparent,
    val staticWhiteOpacity28: Color = Color.Transparent,
    val staticWhiteOpacity35: Color = Color.Transparent,
    val staticWhiteOpacity52: Color = Color.Transparent,
    val staticWhiteOpacity61: Color = Color.Transparent,
    val staticWhiteOpacity74: Color = Color.Transparent,
    val staticWhiteOpacity88: Color = Color.Transparent,

    val staticBlackOpacity0: Color = Color.Transparent,
    val staticBlackOpacity8: Color = Color.Transparent,
    val staticBlackOpacity12: Color = Color.Transparent,
    val staticBlackOpacity22: Color = Color.Transparent,
    val staticBlackOpacity28: Color = Color.Transparent,
    val staticBlackOpacity35: Color = Color.Transparent,
    val staticBlackOpacity43: Color = Color.Transparent,
    val staticBlackOpacity52: Color = Color.Transparent,
    val staticBlackOpacity74: Color = Color.Transparent,
    val staticBlackOpacity88: Color = Color.Transparent,

    val surfaceBrandPrimaryOpacity5: Color = Color.Transparent,
    val surfaceBrandPrimaryOpacity8: Color = Color.Transparent,
    val surfaceBrandPrimaryOpacity12: Color = Color.Transparent,
    val surfaceBrandPrimaryOpacity22: Color = Color.Transparent,
    val surfaceBrandPrimaryOpacity28: Color = Color.Transparent,
    val surfaceBrandPrimaryOpacity35: Color = Color.Transparent,
    val surfaceBrandPrimaryOpacity52: Color = Color.Transparent,
    val surfaceBrandPrimaryOpacity61: Color = Color.Transparent,
    val surfaceBrandPrimaryOpacity74: Color = Color.Transparent,
    val surfaceBrandPrimaryOpacity88: Color = Color.Transparent,

    val foregroundNeutralPrimaryOpacity5: Color = Color.Transparent,
    val foregroundNeutralPrimaryOpacity8: Color = Color.Transparent,
    val foregroundNeutralPrimaryOpacity12: Color = Color.Transparent,

    val foregroundNeutralStrongOpacity5: Color = Color.Transparent,
    val foregroundNeutralStrongOpacity8: Color = Color.Transparent,
    val foregroundNeutralStrongOpacity12: Color = Color.Transparent,
    val foregroundNeutralStrongOpacity35: Color = Color.Transparent,
    val foregroundNeutralStrongOpacity52: Color = Color.Transparent,
    val foregroundNeutralStrongOpacity74: Color = Color.Transparent,
    val foregroundNeutralStrongOpacity88: Color = Color.Transparent,

    val foregroundNeutralTertiaryOpacity5: Color = Color.Transparent,
    val foregroundNeutralTertiaryOpacity8: Color = Color.Transparent,
    val foregroundNeutralTertiaryOpacity12: Color = Color.Transparent,
    val foregroundNeutralTertiaryOpacity35: Color = Color.Transparent,
    val foregroundNeutralTertiaryOpacity52: Color = Color.Transparent,
    val foregroundNeutralTertiaryOpacity74: Color = Color.Transparent,
    val foregroundNeutralTertiaryOpacity88: Color = Color.Transparent,


    val backgroundNeutralPrimaryOpacity0: Color = Color.Transparent,
    val backgroundNeutralPrimaryOpacity61: Color = Color.Transparent,

    val surfaceElevatedPrimaryOpacity0: Color = Color.Transparent,
    val surfaceElevatedPrimaryOpacity12: Color = Color.Transparent,
    val surfaceElevatedPrimaryOpacity88: Color = Color.Transparent,
    val surfaceElevatedPrimaryOpacity97: Color = Color.Transparent,

    val lineNeutralPrimaryOpaqueOpacity28: Color = Color.Transparent,
    val lineNeutralPrimaryOpaqueOpacity61: Color = Color.Transparent,

    val lineNeutralTertiaryOpaqueOpacity52: Color = Color.Transparent,

    val foregroundPositivePrimaryOpacity5: Color = Color.Transparent,
    val foregroundPositivePrimaryOpacity8: Color = Color.Transparent,
    val foregroundPositivePrimaryOpacity12: Color = Color.Transparent,
    val foregroundPositivePrimaryOpacity16: Color = Color.Transparent,
    val foregroundPositivePrimaryOpacity43: Color = Color.Transparent,

    val foregroundNegativePrimaryOpacity8: Color = Color.Transparent,

    val surfaceAccentCyanOpaqueOpacity8: Color = Color.Transparent,
    val surfaceAccentCyanOpaqueOpacity35: Color = Color.Transparent,

    val surfaceAccentLightBlueOpaqueOpacity5: Color = Color.Transparent,
    val surfaceAccentLightBlueOpaqueOpacity8: Color = Color.Transparent,
    val surfaceAccentLightBlueOpaqueOpacity12: Color = Color.Transparent,

    val surfaceAccentVioletOpaqueOpacity5: Color = Color.Transparent,
    val surfaceAccentVioletOpaqueOpacity8: Color = Color.Transparent,
    val surfaceAccentVioletOpaqueOpacity12: Color = Color.Transparent,

    val surfaceAccentPinkOpaqueOpacity8: Color = Color.Transparent,
    val surfaceAccentLimeOpaqueOpacity8: Color = Color.Transparent
)

val AppWantedColorOpacityScheme: WantedColorOpacityScheme
    @Composable
    get() = WantedColorOpacityScheme(
        staticWhiteOpacity5 = colorResource(id = R.color.static_white_opacity5),
        staticWhiteOpacity8 = colorResource(id = R.color.static_white_opacity8),
        staticWhiteOpacity12 = colorResource(id = R.color.static_white_opacity12),
        staticWhiteOpacity22 = colorResource(id = R.color.static_white_opacity22),
        staticWhiteOpacity28 = colorResource(id = R.color.static_white_opacity28),
        staticWhiteOpacity35 = colorResource(id = R.color.static_white_opacity35),
        staticWhiteOpacity52 = colorResource(id = R.color.static_white_opacity52),
        staticWhiteOpacity61 = colorResource(id = R.color.static_white_opacity61),
        staticWhiteOpacity74 = colorResource(id = R.color.static_white_opacity74),
        staticWhiteOpacity88 = colorResource(id = R.color.static_white_opacity88),

        staticBlackOpacity0 = colorResource(id = R.color.static_black_opacity0),
        staticBlackOpacity8 = colorResource(id = R.color.static_black_opacity8),
        staticBlackOpacity12 = colorResource(id = R.color.static_black_opacity12),
        staticBlackOpacity22 = colorResource(id = R.color.static_black_opacity22),
        staticBlackOpacity28 = colorResource(id = R.color.static_black_opacity28),
        staticBlackOpacity35 = colorResource(id = R.color.static_black_opacity35),
        staticBlackOpacity43 = colorResource(id = R.color.static_black_opacity43),
        staticBlackOpacity52 = colorResource(id = R.color.static_black_opacity52),
        staticBlackOpacity74 = colorResource(id = R.color.static_black_opacity74),
        staticBlackOpacity88 = colorResource(id = R.color.static_black_opacity88),

        surfaceBrandPrimaryOpacity5 = colorResource(id = R.color.surface_brand_primary_opacity5),
        surfaceBrandPrimaryOpacity8 = colorResource(id = R.color.surface_brand_primary_opacity8),
        surfaceBrandPrimaryOpacity12 = colorResource(id = R.color.surface_brand_primary_opacity12),
        surfaceBrandPrimaryOpacity22 = colorResource(id = R.color.surface_brand_primary_opacity22),
        surfaceBrandPrimaryOpacity28 = colorResource(id = R.color.surface_brand_primary_opacity28),
        surfaceBrandPrimaryOpacity35 = colorResource(id = R.color.surface_brand_primary_opacity35),
        surfaceBrandPrimaryOpacity52 = colorResource(id = R.color.surface_brand_primary_opacity52),
        surfaceBrandPrimaryOpacity61 = colorResource(id = R.color.surface_brand_primary_opacity61),
        surfaceBrandPrimaryOpacity74 = colorResource(id = R.color.surface_brand_primary_opacity74),
        surfaceBrandPrimaryOpacity88 = colorResource(id = R.color.surface_brand_primary_opacity88),

        foregroundNeutralPrimaryOpacity5 = colorResource(id = R.color.foreground_neutral_primary_opacity5),
        foregroundNeutralPrimaryOpacity8 = colorResource(id = R.color.foreground_neutral_primary_opacity8),
        foregroundNeutralPrimaryOpacity12 = colorResource(id = R.color.foreground_neutral_primary_opacity12),

        foregroundNeutralStrongOpacity5 = colorResource(id = R.color.foreground_neutral_strong_opacity5),
        foregroundNeutralStrongOpacity8 = colorResource(id = R.color.foreground_neutral_strong_opacity8),
        foregroundNeutralStrongOpacity12 = colorResource(id = R.color.foreground_neutral_strong_opacity12),
        foregroundNeutralStrongOpacity35 = colorResource(id = R.color.foreground_neutral_strong_opacity35),
        foregroundNeutralStrongOpacity52 = colorResource(id = R.color.foreground_neutral_strong_opacity52),
        foregroundNeutralStrongOpacity74 = colorResource(id = R.color.foreground_neutral_strong_opacity74),
        foregroundNeutralStrongOpacity88 = colorResource(id = R.color.foreground_neutral_strong_opacity88),

        foregroundNeutralTertiaryOpacity5 = colorResource(id = R.color.foreground_neutral_tertiary_opacity5),
        foregroundNeutralTertiaryOpacity8 = colorResource(id = R.color.foreground_neutral_tertiary_opacity8),
        foregroundNeutralTertiaryOpacity12 = colorResource(id = R.color.foreground_neutral_tertiary_opacity12),
        foregroundNeutralTertiaryOpacity35 = colorResource(id = R.color.foreground_neutral_tertiary_opacity35),
        foregroundNeutralTertiaryOpacity52 = colorResource(id = R.color.foreground_neutral_tertiary_opacity52),
        foregroundNeutralTertiaryOpacity74 = colorResource(id = R.color.foreground_neutral_tertiary_opacity74),
        foregroundNeutralTertiaryOpacity88 = colorResource(id = R.color.foreground_neutral_tertiary_opacity88),


        backgroundNeutralPrimaryOpacity0 = colorResource(id = R.color.background_neutral_primary_opacity0),
        backgroundNeutralPrimaryOpacity61 = colorResource(id = R.color.background_neutral_primary_opacity61),

        surfaceElevatedPrimaryOpacity0 = colorResource(id = R.color.surface_elevated_primary_opacity0),
        surfaceElevatedPrimaryOpacity12 = colorResource(id = R.color.surface_elevated_primary_opacity12),
        surfaceElevatedPrimaryOpacity88 = colorResource(id = R.color.surface_elevated_primary_opacity88),
        surfaceElevatedPrimaryOpacity97 = colorResource(id = R.color.surface_elevated_primary_opacity97),

        lineNeutralPrimaryOpaqueOpacity28 = colorResource(id = R.color.line_neutral_primary_opaque_opacity28),
        lineNeutralPrimaryOpaqueOpacity61 = colorResource(id = R.color.line_neutral_primary_opaque_opacity61),

        lineNeutralTertiaryOpaqueOpacity52 = colorResource(id = R.color.line_neutral_tertiary_opaque_opacity52),

        foregroundPositivePrimaryOpacity5 = colorResource(id = R.color.foreground_positive_primary_opacity5),
        foregroundPositivePrimaryOpacity8 = colorResource(id = R.color.foreground_positive_primary_opacity8),
        foregroundPositivePrimaryOpacity12 = colorResource(id = R.color.foreground_positive_primary_opacity12),
        foregroundPositivePrimaryOpacity16 = colorResource(id = R.color.foreground_positive_primary_opacity16),
        foregroundPositivePrimaryOpacity43 = colorResource(id = R.color.foreground_positive_primary_opacity43),

        foregroundNegativePrimaryOpacity8 = colorResource(id = R.color.foreground_negative_primary_opacity8),

        surfaceAccentCyanOpaqueOpacity8 = colorResource(id = R.color.surface_accent_cyan_opaque_opacity8),
        surfaceAccentCyanOpaqueOpacity35 = colorResource(id = R.color.surface_accent_cyan_opaque_opacity35),

        surfaceAccentLightBlueOpaqueOpacity5 = colorResource(id = R.color.surface_accent_lightblue_opaque_opacity5),
        surfaceAccentLightBlueOpaqueOpacity8 = colorResource(id = R.color.surface_accent_lightblue_opaque_opacity8),
        surfaceAccentLightBlueOpaqueOpacity12 = colorResource(id = R.color.surface_accent_lightblue_opaque_opacity12),

        surfaceAccentVioletOpaqueOpacity5 = colorResource(id = R.color.surface_accent_violet_opaque_opacity5),
        surfaceAccentVioletOpaqueOpacity8 = colorResource(id = R.color.surface_accent_violet_opaque_opacity8),
        surfaceAccentVioletOpaqueOpacity12 = colorResource(id = R.color.surface_accent_violet_opaque_opacity12),

        surfaceAccentPinkOpaqueOpacity8 = colorResource(id = R.color.surface_accent_pink_opaque_opacity8),
        surfaceAccentLimeOpaqueOpacity8 = colorResource(id = R.color.surface_accent_lime_opaque_opacity8)
    )


internal val LocalWantedColorOpacityScheme = WantedColorOpacitySchemeLocal()

@JvmInline
value class WantedColorOpacitySchemeLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedColorOpacityScheme> = staticCompositionLocalOf { WantedColorOpacityScheme() }
) {
    val current: WantedColorOpacityScheme
        @Composable get() = delegate.current

    infix fun provides(value: WantedColorOpacityScheme) = delegate provides value
}