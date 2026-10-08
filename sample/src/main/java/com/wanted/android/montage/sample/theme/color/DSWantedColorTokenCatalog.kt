package com.wanted.android.montage.sample.theme.color

import androidx.annotation.ColorRes
import com.wanted.android.montage.sample.R
import com.wanted.android.wanted.design.theme.WantedColorOpacityScheme
import com.wanted.android.wanted.design.theme.WantedColorScheme

/**
 * 색상 토큰이 속한 스킴입니다. 코드 복사 시 `DesignSystemTheme.<accessor>` 로 쓰입니다.
 */
internal enum class ColorTokenScheme(val accessor: String) {
    Semantic("colors"),
    Opacity("colorsOpacity")
}

/**
 * 색상 토큰 데모의 그룹(용도, Property) 구분입니다. [All] 은 필터에서 전체를 뜻합니다.
 */
internal enum class ColorTokenGroup(val label: String) {
    All("전체"),
    Foreground("Foreground"),
    Background("Background"),
    Surface("Surface"),
    Line("Line"),
    Static("Static"),
    Effect("Effect"),
    Opacity("Opacity");

    companion object {
        // 투명도 변형은 별도 그룹, 시맨틱 토큰은 이름의 첫 단어(용도)로 판정한다.
        fun from(name: String, scheme: ColorTokenScheme): ColorTokenGroup = when (scheme) {
            ColorTokenScheme.Opacity -> Opacity
            ColorTokenScheme.Semantic -> entries.firstOrNull { group ->
                group != All && group != Opacity && name.startsWith(group.name.lowercase())
            } ?: All
        }
    }
}

/**
 * 색상 토큰 하나입니다.
 *
 * @property name String: [WantedColorScheme]·[WantedColorOpacityScheme] 의 프로퍼티 이름입니다. 코드 복사에 그대로 씁니다.
 * @property colorRes Int: 토큰의 색상 리소스입니다. 라이트·다크 값을 각각 읽기 위해 리소스로 둡니다.
 * @property scheme ColorTokenScheme: 토큰이 속한 스킴입니다.
 */
internal data class DSWantedColorTokenItem(
    val name: String,
    @param:ColorRes val colorRes: Int,
    val scheme: ColorTokenScheme,
) {
    val group: ColorTokenGroup = ColorTokenGroup.from(name, scheme)

    /** 가이드 표기(용도/역할/변형)입니다. 예: foregroundNeutralPrimary → foreground/neutral/primary, 투명도 변형은 `· N%` 를 붙입니다. */
    val tokenPath: String = toTokenPath(name)

    val code: String = "DesignSystemTheme.${scheme.accessor}.$name"

    fun matches(query: String): Boolean {
        val normalized = query.normalizeForSearch()
        return normalized.isEmpty() ||
            name.normalizeForSearch().contains(normalized) ||
            tokenPath.normalizeForSearch().contains(normalized)
    }
}

private val OPACITY_SUFFIX = Regex("Opacity(\\d+)$")
private val WORD_BOUNDARY = Regex("(?=[A-Z])")
private const val PATH_PROPERTY_INTENT_COUNT = 2

internal fun toTokenPath(name: String): String {
    val opacity = OPACITY_SUFFIX.find(name)?.groupValues?.get(1)
    val words = OPACITY_SUFFIX.replace(name, "").split(WORD_BOUNDARY).map { it.replaceFirstChar(Char::lowercaseChar) }
    val head = words.take(PATH_PROPERTY_INTENT_COUNT)
    val variant = words.drop(PATH_PROPERTY_INTENT_COUNT).mapIndexed { index, word ->
        if (index == 0) word else word.replaceFirstChar(Char::uppercaseChar)
    }.joinToString("")
    val path = (head + variant).filter { it.isNotEmpty() }.joinToString("/")
    return if (opacity == null) path else "$path · $opacity%"
}

private fun String.normalizeForSearch(): String = lowercase().filter { it.isLetterOrDigit() }

private fun semantic(name: String, @ColorRes colorRes: Int) = DSWantedColorTokenItem(name, colorRes, ColorTokenScheme.Semantic)

private fun opacity(name: String, @ColorRes colorRes: Int) = DSWantedColorTokenItem(name, colorRes, ColorTokenScheme.Opacity)

internal object DSWantedColorTokenCatalog {
    val semanticItems: List<DSWantedColorTokenItem> = listOf(
        semantic("staticWhite", R.color.static_white),
        semantic("staticBlack", R.color.static_black),
        semantic("foregroundNeutralPrimary", R.color.foreground_neutral_primary),
        semantic("foregroundNeutralStrong", R.color.foreground_neutral_strong),
        semantic("foregroundNeutralSecondary", R.color.foreground_neutral_secondary),
        semantic("foregroundNeutralTertiary", R.color.foreground_neutral_tertiary),
        semantic("foregroundNeutralQuaternary", R.color.foreground_neutral_quaternary),
        semantic("foregroundNeutralInverse", R.color.foreground_neutral_inverse),
        semantic("foregroundDisablePrimary", R.color.foreground_disable_primary),
        semantic("foregroundInactivePrimary", R.color.foreground_inactive_primary),
        semantic("foregroundBrandPrimary", R.color.foreground_brand_primary),
        semantic("foregroundBrandInverse", R.color.foreground_brand_inverse),
        semantic("foregroundPositivePrimary", R.color.foreground_positive_primary),
        semantic("foregroundCautionaryPrimary", R.color.foreground_cautionary_primary),
        semantic("foregroundNegativePrimary", R.color.foreground_negative_primary),
        semantic("foregroundNegativeStrong", R.color.foreground_negative_strong),
        semantic("foregroundAccentLime", R.color.foreground_accent_lime),
        semantic("foregroundAccentCyan", R.color.foreground_accent_cyan),
        semantic("foregroundAccentLightBlue", R.color.foreground_accent_lightblue),
        semantic("foregroundAccentViolet", R.color.foreground_accent_violet),
        semantic("foregroundAccentPurple", R.color.foreground_accent_purple),
        semantic("foregroundAccentPink", R.color.foreground_accent_pink),
        semantic("backgroundNeutralPrimary", R.color.background_neutral_primary),
        semantic("backgroundNeutralSecondary", R.color.background_neutral_secondary),
        semantic("surfaceNeutralPrimary", R.color.surface_neutral_primary),
        semantic("surfaceNeutralSecondary", R.color.surface_neutral_secondary),
        semantic("surfaceNeutralTertiary", R.color.surface_neutral_tertiary),
        semantic("surfaceNeutralStrong", R.color.surface_neutral_strong),
        semantic("surfaceNeutralInverse", R.color.surface_neutral_inverse),
        semantic("surfaceElevatedPrimary", R.color.surface_elevated_primary),
        semantic("surfaceElevatedSecondary", R.color.surface_elevated_secondary),
        semantic("surfaceBrandPrimary", R.color.surface_brand_primary),
        semantic("surfaceBrandStrong", R.color.surface_brand_strong),
        semantic("surfaceBrandHeavy", R.color.surface_brand_heavy),
        semantic("surfaceBrandSubtle", R.color.surface_brand_subtle),
        semantic("surfacePositivePrimary", R.color.surface_positive_primary),
        semantic("surfaceCautionaryPrimary", R.color.surface_cautionary_primary),
        semantic("surfaceNegativePrimary", R.color.surface_negative_primary),
        semantic("surfaceNegativeStrong", R.color.surface_negative_strong),
        semantic("surfaceDisablePrimary", R.color.surface_disable_primary),
        semantic("surfaceAccentLime", R.color.surface_accent_lime),
        semantic("surfaceAccentCyan", R.color.surface_accent_cyan),
        semantic("surfaceAccentLightBlue", R.color.surface_accent_lightblue),
        semantic("surfaceAccentViolet", R.color.surface_accent_violet),
        semantic("surfaceAccentPurple", R.color.surface_accent_purple),
        semantic("surfaceAccentPink", R.color.surface_accent_pink),
        semantic("surfaceAccentLimeOpaque", R.color.surface_accent_lime_opaque),
        semantic("surfaceAccentCyanOpaque", R.color.surface_accent_cyan_opaque),
        semantic("surfaceAccentLightBlueOpaque", R.color.surface_accent_lightblue_opaque),
        semantic("surfaceAccentVioletOpaque", R.color.surface_accent_violet_opaque),
        semantic("surfaceAccentPurpleOpaque", R.color.surface_accent_purple_opaque),
        semantic("surfaceAccentPinkOpaque", R.color.surface_accent_pink_opaque),
        semantic("lineNeutralPrimary", R.color.line_neutral_primary),
        semantic("lineNeutralSecondary", R.color.line_neutral_secondary),
        semantic("lineNeutralTertiary", R.color.line_neutral_tertiary),
        semantic("lineNeutralPrimaryOpaque", R.color.line_neutral_primary_opaque),
        semantic("lineNeutralSecondaryOpaque", R.color.line_neutral_secondary_opaque),
        semantic("lineNeutralTertiaryOpaque", R.color.line_neutral_tertiary_opaque),
        semantic("lineBrandPrimary", R.color.line_brand_primary),
        semantic("lineBrandStrong", R.color.line_brand_strong),
        semantic("lineBrandFocus", R.color.line_brand_focus),
        semantic("linePositivePrimary", R.color.line_positive_primary),
        semantic("lineCautionaryPrimary", R.color.line_cautionary_primary),
        semantic("lineNegativePrimary", R.color.line_negative_primary),
        semantic("lineNegativeStrong", R.color.line_negative_strong),
        semantic("lineNegativeFocus", R.color.line_negative_focus),
        semantic("lineAccentLime", R.color.line_accent_lime),
        semantic("lineAccentCyan", R.color.line_accent_cyan),
        semantic("lineAccentLightBlue", R.color.line_accent_lightblue),
        semantic("lineAccentViolet", R.color.line_accent_violet),
        semantic("lineAccentPurple", R.color.line_accent_purple),
        semantic("lineAccentPink", R.color.line_accent_pink),
        semantic("effectTransparentPrimary", R.color.effect_transparent_primary),
        semantic("effectTransparentSecondary", R.color.effect_transparent_secondary),
        semantic("effectDimmerPrimary", R.color.effect_dimmer_primary)
    )

    val opacityItems: List<DSWantedColorTokenItem> = listOf(
        opacity("staticWhiteOpacity5", R.color.static_white_opacity5),
        opacity("staticWhiteOpacity8", R.color.static_white_opacity8),
        opacity("staticWhiteOpacity12", R.color.static_white_opacity12),
        opacity("staticWhiteOpacity22", R.color.static_white_opacity22),
        opacity("staticWhiteOpacity28", R.color.static_white_opacity28),
        opacity("staticWhiteOpacity35", R.color.static_white_opacity35),
        opacity("staticWhiteOpacity52", R.color.static_white_opacity52),
        opacity("staticWhiteOpacity61", R.color.static_white_opacity61),
        opacity("staticWhiteOpacity74", R.color.static_white_opacity74),
        opacity("staticWhiteOpacity88", R.color.static_white_opacity88),
        opacity("staticBlackOpacity0", R.color.static_black_opacity0),
        opacity("staticBlackOpacity8", R.color.static_black_opacity8),
        opacity("staticBlackOpacity12", R.color.static_black_opacity12),
        opacity("staticBlackOpacity22", R.color.static_black_opacity22),
        opacity("staticBlackOpacity28", R.color.static_black_opacity28),
        opacity("staticBlackOpacity35", R.color.static_black_opacity35),
        opacity("staticBlackOpacity43", R.color.static_black_opacity43),
        opacity("staticBlackOpacity52", R.color.static_black_opacity52),
        opacity("staticBlackOpacity74", R.color.static_black_opacity74),
        opacity("staticBlackOpacity88", R.color.static_black_opacity88),
        opacity("surfaceBrandPrimaryOpacity5", R.color.surface_brand_primary_opacity5),
        opacity("surfaceBrandPrimaryOpacity8", R.color.surface_brand_primary_opacity8),
        opacity("surfaceBrandPrimaryOpacity12", R.color.surface_brand_primary_opacity12),
        opacity("surfaceBrandPrimaryOpacity22", R.color.surface_brand_primary_opacity22),
        opacity("surfaceBrandPrimaryOpacity28", R.color.surface_brand_primary_opacity28),
        opacity("surfaceBrandPrimaryOpacity35", R.color.surface_brand_primary_opacity35),
        opacity("surfaceBrandPrimaryOpacity52", R.color.surface_brand_primary_opacity52),
        opacity("surfaceBrandPrimaryOpacity61", R.color.surface_brand_primary_opacity61),
        opacity("surfaceBrandPrimaryOpacity74", R.color.surface_brand_primary_opacity74),
        opacity("surfaceBrandPrimaryOpacity88", R.color.surface_brand_primary_opacity88),
        opacity("foregroundNeutralPrimaryOpacity5", R.color.foreground_neutral_primary_opacity5),
        opacity("foregroundNeutralPrimaryOpacity8", R.color.foreground_neutral_primary_opacity8),
        opacity("foregroundNeutralPrimaryOpacity12", R.color.foreground_neutral_primary_opacity12),
        opacity("foregroundNeutralStrongOpacity5", R.color.foreground_neutral_strong_opacity5),
        opacity("foregroundNeutralStrongOpacity8", R.color.foreground_neutral_strong_opacity8),
        opacity("foregroundNeutralStrongOpacity12", R.color.foreground_neutral_strong_opacity12),
        opacity("foregroundNeutralStrongOpacity35", R.color.foreground_neutral_strong_opacity35),
        opacity("foregroundNeutralStrongOpacity52", R.color.foreground_neutral_strong_opacity52),
        opacity("foregroundNeutralStrongOpacity74", R.color.foreground_neutral_strong_opacity74),
        opacity("foregroundNeutralStrongOpacity88", R.color.foreground_neutral_strong_opacity88),
        opacity("foregroundNeutralTertiaryOpacity5", R.color.foreground_neutral_tertiary_opacity5),
        opacity("foregroundNeutralTertiaryOpacity8", R.color.foreground_neutral_tertiary_opacity8),
        opacity("foregroundNeutralTertiaryOpacity12", R.color.foreground_neutral_tertiary_opacity12),
        opacity("foregroundNeutralTertiaryOpacity35", R.color.foreground_neutral_tertiary_opacity35),
        opacity("foregroundNeutralTertiaryOpacity52", R.color.foreground_neutral_tertiary_opacity52),
        opacity("foregroundNeutralTertiaryOpacity74", R.color.foreground_neutral_tertiary_opacity74),
        opacity("foregroundNeutralTertiaryOpacity88", R.color.foreground_neutral_tertiary_opacity88),
        opacity("backgroundNeutralPrimaryOpacity0", R.color.background_neutral_primary_opacity0),
        opacity("backgroundNeutralPrimaryOpacity61", R.color.background_neutral_primary_opacity61),
        opacity("surfaceElevatedPrimaryOpacity0", R.color.surface_elevated_primary_opacity0),
        opacity("surfaceElevatedPrimaryOpacity12", R.color.surface_elevated_primary_opacity12),
        opacity("surfaceElevatedPrimaryOpacity88", R.color.surface_elevated_primary_opacity88),
        opacity("surfaceElevatedPrimaryOpacity97", R.color.surface_elevated_primary_opacity97),
        opacity("lineNeutralPrimaryOpaqueOpacity28", R.color.line_neutral_primary_opaque_opacity28),
        opacity("lineNeutralPrimaryOpaqueOpacity61", R.color.line_neutral_primary_opaque_opacity61),
        opacity("lineNeutralTertiaryOpaqueOpacity52", R.color.line_neutral_tertiary_opaque_opacity52),
        opacity("foregroundPositivePrimaryOpacity5", R.color.foreground_positive_primary_opacity5),
        opacity("foregroundPositivePrimaryOpacity8", R.color.foreground_positive_primary_opacity8),
        opacity("foregroundPositivePrimaryOpacity12", R.color.foreground_positive_primary_opacity12),
        opacity("foregroundPositivePrimaryOpacity16", R.color.foreground_positive_primary_opacity16),
        opacity("foregroundPositivePrimaryOpacity43", R.color.foreground_positive_primary_opacity43),
        opacity("foregroundNegativePrimaryOpacity8", R.color.foreground_negative_primary_opacity8),
        opacity("surfaceAccentCyanOpaqueOpacity8", R.color.surface_accent_cyan_opaque_opacity8),
        opacity("surfaceAccentCyanOpaqueOpacity35", R.color.surface_accent_cyan_opaque_opacity35),
        opacity("surfaceAccentLightBlueOpaqueOpacity5", R.color.surface_accent_lightblue_opaque_opacity5),
        opacity("surfaceAccentLightBlueOpaqueOpacity8", R.color.surface_accent_lightblue_opaque_opacity8),
        opacity("surfaceAccentLightBlueOpaqueOpacity12", R.color.surface_accent_lightblue_opaque_opacity12),
        opacity("surfaceAccentVioletOpaqueOpacity5", R.color.surface_accent_violet_opaque_opacity5),
        opacity("surfaceAccentVioletOpaqueOpacity8", R.color.surface_accent_violet_opaque_opacity8),
        opacity("surfaceAccentVioletOpaqueOpacity12", R.color.surface_accent_violet_opaque_opacity12),
        opacity("surfaceAccentPinkOpaqueOpacity8", R.color.surface_accent_pink_opaque_opacity8),
        opacity("surfaceAccentLimeOpaqueOpacity8", R.color.surface_accent_lime_opaque_opacity8)
    )

    val items: List<DSWantedColorTokenItem> = semanticItems + opacityItems
}
