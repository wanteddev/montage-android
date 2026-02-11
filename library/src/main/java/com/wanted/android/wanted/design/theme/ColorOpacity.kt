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

    val primaryNormalOpacity5: Color = Color.Transparent,
    val primaryNormalOpacity8: Color = Color.Transparent,
    val primaryNormalOpacity12: Color = Color.Transparent,
    val primaryNormalOpacity22: Color = Color.Transparent,
    val primaryNormalOpacity28: Color = Color.Transparent,
    val primaryNormalOpacity35: Color = Color.Transparent,
    val primaryNormalOpacity52: Color = Color.Transparent,
    val primaryNormalOpacity61: Color = Color.Transparent,
    val primaryNormalOpacity74: Color = Color.Transparent,
    val primaryNormalOpacity88: Color = Color.Transparent,

    val labelNormalOpacity5: Color = Color.Transparent,
    val labelNormalOpacity8: Color = Color.Transparent,
    val labelNormalOpacity12: Color = Color.Transparent,

    val labelStrongOpacity5: Color = Color.Transparent,
    val labelStrongOpacity8: Color = Color.Transparent,
    val labelStrongOpacity12: Color = Color.Transparent,
    val labelStrongOpacity35: Color = Color.Transparent,
    val labelStrongOpacity52: Color = Color.Transparent,
    val labelStrongOpacity74: Color = Color.Transparent,
    val labelStrongOpacity88: Color = Color.Transparent,

    val labelAlternativeOpacity5: Color = Color.Transparent,
    val labelAlternativeOpacity8: Color = Color.Transparent,
    val labelAlternativeOpacity12: Color = Color.Transparent,
    val labelAlternativeOpacity35: Color = Color.Transparent,
    val labelAlternativeOpacity52: Color = Color.Transparent,
    val labelAlternativeOpacity74: Color = Color.Transparent,
    val labelAlternativeOpacity88: Color = Color.Transparent,


    val backgroundNormalNormalOpacity0: Color = Color.Transparent,
    val backgroundNormalNormalOpacity61: Color = Color.Transparent,

    val backgroundElevatedNormalOpacity0: Color = Color.Transparent,
    val backgroundElevatedNormalOpacity12: Color = Color.Transparent,
    val backgroundElevatedNormalOpacity88: Color = Color.Transparent,
    val backgroundElevatedNormalOpacity97: Color = Color.Transparent,

    val lineNormalOpacity28: Color = Color.Transparent,
    val lineNormalOpacity61: Color = Color.Transparent,

    val lineAlternativeOpacity52: Color = Color.Transparent,

    val statusPositiveOpacity5: Color = Color.Transparent,
    val statusPositiveOpacity8: Color = Color.Transparent,
    val statusPositiveOpacity12: Color = Color.Transparent,
    val statusPositiveOpacity16: Color = Color.Transparent,
    val statusPositiveOpacity43: Color = Color.Transparent,

    val statusNegativeOpacity8: Color = Color.Transparent,

    val accentCyanOpacity8: Color = Color.Transparent,
    val accentCyanOpacity35: Color = Color.Transparent,

    val accentLightBlueOpacity5: Color = Color.Transparent,
    val accentLightBlueOpacity8: Color = Color.Transparent,
    val accentLightBlueOpacity12: Color = Color.Transparent,

    val accentVioletOpacity5: Color = Color.Transparent,
    val accentVioletOpacity8: Color = Color.Transparent,
    val accentVioletOpacity12: Color = Color.Transparent,

    val accentPinkOpacity8: Color = Color.Transparent,
    val accentLimeOpacity8: Color = Color.Transparent
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

        primaryNormalOpacity5 = colorResource(id = R.color.primary_normal_opacity5),
        primaryNormalOpacity8 = colorResource(id = R.color.primary_normal_opacity8),
        primaryNormalOpacity12 = colorResource(id = R.color.primary_normal_opacity12),
        primaryNormalOpacity22 = colorResource(id = R.color.primary_normal_opacity22),
        primaryNormalOpacity28 = colorResource(id = R.color.primary_normal_opacity28),
        primaryNormalOpacity35 = colorResource(id = R.color.primary_normal_opacity35),
        primaryNormalOpacity52 = colorResource(id = R.color.primary_normal_opacity52),
        primaryNormalOpacity61 = colorResource(id = R.color.primary_normal_opacity61),
        primaryNormalOpacity74 = colorResource(id = R.color.primary_normal_opacity74),
        primaryNormalOpacity88 = colorResource(id = R.color.primary_normal_opacity88),

        labelNormalOpacity5 = colorResource(id = R.color.label_normal_opacity5),
        labelNormalOpacity8 = colorResource(id = R.color.label_normal_opacity8),
        labelNormalOpacity12 = colorResource(id = R.color.label_normal_opacity12),

        labelStrongOpacity5 = colorResource(id = R.color.label_strong_opacity5),
        labelStrongOpacity8 = colorResource(id = R.color.label_strong_opacity8),
        labelStrongOpacity12 = colorResource(id = R.color.label_strong_opacity12),
        labelStrongOpacity35 = colorResource(id = R.color.label_strong_opacity35),
        labelStrongOpacity52 = colorResource(id = R.color.label_strong_opacity52),
        labelStrongOpacity74 = colorResource(id = R.color.label_strong_opacity74),
        labelStrongOpacity88 = colorResource(id = R.color.label_strong_opacity88),

        labelAlternativeOpacity5 = colorResource(id = R.color.label_alternative_opacity5),
        labelAlternativeOpacity8 = colorResource(id = R.color.label_alternative_opacity8),
        labelAlternativeOpacity12 = colorResource(id = R.color.label_alternative_opacity12),
        labelAlternativeOpacity35 = colorResource(id = R.color.label_alternative_opacity35),
        labelAlternativeOpacity52 = colorResource(id = R.color.label_alternative_opacity52),
        labelAlternativeOpacity74 = colorResource(id = R.color.label_alternative_opacity74),
        labelAlternativeOpacity88 = colorResource(id = R.color.label_alternative_opacity88),


        backgroundNormalNormalOpacity0 = colorResource(id = R.color.background_normal_normal_opacity0),
        backgroundNormalNormalOpacity61 = colorResource(id = R.color.background_normal_normal_opacity61),

        backgroundElevatedNormalOpacity0 = colorResource(id = R.color.background_elevated_normal_opacity0),
        backgroundElevatedNormalOpacity12 = colorResource(id = R.color.background_elevated_normal_opacity12),
        backgroundElevatedNormalOpacity88 = colorResource(id = R.color.background_elevated_normal_opacity88),
        backgroundElevatedNormalOpacity97 = colorResource(id = R.color.background_elevated_normal_opacity97),

        lineNormalOpacity28 = colorResource(id = R.color.line_normal_opacity28),
        lineNormalOpacity61 = colorResource(id = R.color.line_normal_opacity61),

        lineAlternativeOpacity52 = colorResource(id = R.color.line_alternative_opacity52),

        statusPositiveOpacity5 = colorResource(id = R.color.status_positive_opacity5),
        statusPositiveOpacity8 = colorResource(id = R.color.status_positive_opacity8),
        statusPositiveOpacity12 = colorResource(id = R.color.status_positive_opacity12),
        statusPositiveOpacity16 = colorResource(id = R.color.status_positive_opacity16),
        statusPositiveOpacity43 = colorResource(id = R.color.status_positive_opacity43),

        statusNegativeOpacity8 = colorResource(id = R.color.status_negative_opacity8),

        accentCyanOpacity8 = colorResource(id = R.color.accent_cyan_opacity8),
        accentCyanOpacity35 = colorResource(id = R.color.accent_cyan_opacity35),

        accentLightBlueOpacity5 = colorResource(id = R.color.accent_lightblue_opacity5),
        accentLightBlueOpacity8 = colorResource(id = R.color.accent_lightblue_opacity8),
        accentLightBlueOpacity12 = colorResource(id = R.color.accent_lightblue_opacity12),

        accentVioletOpacity5 = colorResource(id = R.color.accent_violet_opacity5),
        accentVioletOpacity8 = colorResource(id = R.color.accent_violet_opacity8),
        accentVioletOpacity12 = colorResource(id = R.color.accent_violet_opacity12),

        accentPinkOpacity8 = colorResource(id = R.color.accent_pink_opacity8),
        accentLimeOpacity8 = colorResource(id = R.color.accent_lime_opacity8)
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