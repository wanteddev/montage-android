package com.wanted.android.wanted.design.contents.contentbadge

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_16
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.OPACITY_8


/**
 * data class WantedContentBadgeDefault
 *
 * Content badge의 시각적 속성을 정의하는 데이터 클래스입니다.
 *
 * 텍스트 또는 아이콘 색상(contentColor), 배경색(backgroundColor), 테두리 색상(outLineColor)을 설정합니다.
 *
 * @param contentColor Color: 콘텐츠 색상입니다.
 * @param backgroundColor Color: 배경 색상입니다. 기본값은 contentColor에 OPACITY_8을 적용한 색입니다.
 * @param outLineColor Color: 테두리 색상입니다. 기본값은 contentColor에 OPACITY_43을 적용한 색입니다(Line/Accent 토큰과 같은 규칙).
 */
data class WantedContentBadgeDefault(
    val contentColor: Color,
    val backgroundColor: Color = contentColor.copy(OPACITY_8),
    val outLineColor: Color = contentColor.copy(OPACITY_43)
)



/**
 * object WantedContentBadgeDefaults
 *
 * 기본 Content badge 구성을 제공하는 객체입니다.
 *
 * Accent, Neutral 스타일의 구성을 제공합니다.
 */
object WantedContentBadgeDefaults {

    /**
     * fun getAccentDefault(...)
     *
     * Accent 스타일의 기본 Content badge를 반환합니다.
     *
     * 사용 예시:
     * ```
     * val badgeDefault = WantedContentBadgeDefaults.getAccentDefault()
     * WantedContentBadge(
     *     text = "New",
     *     color = ContentBadgeColor.Accent,
     *     accentDefault = badgeDefault
     * )
     * ```
     *
     * @param contentColor Color: 콘텐츠 색상입니다. 기본값은 foregroundAccentCyan 색상입니다.
     * @param backgroundColor Color: 배경 색상입니다. contentColor 가 기본값이면 surfaceAccentCyan, 다른 색이면 contentColor에 OPACITY_8을 적용한 색입니다.
     * @param outLineColor Color: 테두리 색상입니다. contentColor 가 기본값이면 lineAccentCyan, 다른 색이면 contentColor에 OPACITY_43을 적용한 색입니다.
     * @return WantedContentBadgeDefault: 강조 배지 구성을 반환합니다.
     */
    @Composable
    fun getAccentDefault(
        contentColor: Color = DesignSystemTheme.colors.foregroundAccentCyan,
        backgroundColor: Color = resolveAccentBackgroundColor(
            contentColor = contentColor,
            defaultContentColor = DesignSystemTheme.colors.foregroundAccentCyan,
            defaultBackgroundColor = DesignSystemTheme.colors.surfaceAccentCyan
        ),
        outLineColor: Color = resolveAccentOutLineColor(
            contentColor = contentColor,
            defaultContentColor = DesignSystemTheme.colors.foregroundAccentCyan,
            defaultOutLineColor = DesignSystemTheme.colors.lineAccentCyan
        )
    ) = WantedContentBadgeDefault(
        contentColor = contentColor,
        backgroundColor = backgroundColor,
        outLineColor = outLineColor
    )


    /**
     *
     * fun getNeutralDefault(...)
     *
     * Neutral 스타일의 기본 Content badge를 반환합니다.
     *
     * 배경은 surfaceNeutralSecondary, 테두리는 foregroundNeutralTertiary 16% 로 고정되며 contentColor 를 바꿔도 따라 바뀌지 않습니다.
     *
     * 사용 예시:
     * ```
     * val badgeDefault = WantedContentBadgeDefaults.getNeutralDefault()
     * WantedContentBadge(
     *     text = "Label",
     *     accentDefault = badgeDefault
     * )
     * ```
     *
     * @param contentColor Color: 콘텐츠 색상입니다. 기본값은 foregroundNeutralTertiary 색상입니다.
     * @return WantedContentBadgeDefault: 중립 배지 구성을 반환합니다.
     */
    @Composable
    fun getNeutralDefault(
        contentColor: Color = DesignSystemTheme.colors.foregroundNeutralTertiary,
    ) = WantedContentBadgeDefault(
        contentColor = contentColor,
        backgroundColor = DesignSystemTheme.colors.surfaceNeutralSecondary,
        outLineColor = DesignSystemTheme.colors.foregroundNeutralTertiary.copy(OPACITY_16)
    )
}


/**
 * Accent 배지 배경 기본값을 정합니다.
 *
 * 기본 글자색(cyan)이면 Figma 배경 토큰을 그대로 쓰고, 글자색을 바꾼 호출부는 기존처럼 글자색에 OPACITY_8을 적용합니다.
 * 배경 토큰은 글자색과 다른 단계의 색이라 글자색에서 계산할 수 없습니다.
 * 기본 여부는 색 값으로 판정하므로, 호출부가 기본 글자색과 같은 색을 직접 넘겨도 토큰이 적용됩니다.
 *
 * @param contentColor Color: 배지의 글자색입니다.
 * @param defaultContentColor Color: 기본 글자색(foregroundAccentCyan)입니다.
 * @param defaultBackgroundColor Color: 기본 배경 토큰(surfaceAccentCyan)입니다.
 * @return Color: 배지의 배경색을 반환합니다.
 */
internal fun resolveAccentBackgroundColor(
    contentColor: Color,
    defaultContentColor: Color,
    defaultBackgroundColor: Color
): Color = if (contentColor == defaultContentColor) {
    defaultBackgroundColor
} else {
    contentColor.copy(OPACITY_8)
}

/**
 * Accent 배지 테두리 기본값을 정합니다.
 *
 * 기본 글자색(cyan)이면 Figma 테두리 토큰을 쓰고, 다른 글자색이면 같은 규칙(글자색 × OPACITY_43)으로 계산합니다.
 *
 * @param contentColor Color: 배지의 글자색입니다.
 * @param defaultContentColor Color: 기본 글자색(foregroundAccentCyan)입니다.
 * @param defaultOutLineColor Color: 기본 테두리 토큰(lineAccentCyan)입니다.
 * @return Color: 배지의 테두리색을 반환합니다.
 */
internal fun resolveAccentOutLineColor(
    contentColor: Color,
    defaultContentColor: Color,
    defaultOutLineColor: Color
): Color = if (contentColor == defaultContentColor) {
    defaultOutLineColor
} else {
    contentColor.copy(OPACITY_43)
}
