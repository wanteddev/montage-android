package com.wanted.android.wanted.design.contents.contentbadge

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_16
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
 * @param outLineColor Color: 테두리 색상입니다. 기본값은 contentColor입니다.
 */
data class WantedContentBadgeDefault(
    val contentColor: Color,
    val backgroundColor: Color = contentColor.copy(OPACITY_8),
    val outLineColor: Color = contentColor
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
     *     badgeDefault = badgeDefault
     * )
     * ```
     *
     * @param contentColor Color: 콘텐츠 색상입니다. 기본값은 cyan 계열 색상입니다.
     * @param backgroundColor Color: 배경 색상입니다. 기본값은 contentColor에 OPACITY_8을 적용한 색입니다.
     * @param outLineColor Color: 테두리 색상입니다. 기본값은 contentColor입니다.
     * @return WantedContentBadgeDefault: 강조 배지 구성을 반환합니다.
     */
    @Composable
    fun getAccentDefault(
        contentColor: Color = DesignSystemTheme.colors.accentBackgroundCyan,
        backgroundColor: Color = contentColor.copy(OPACITY_8),
        outLineColor: Color = contentColor
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
     * 사용 예시:
     * ```
     * val badgeDefault = WantedContentBadgeDefaults.getNeutralDefault()
     * WantedContentBadge(
     *     text = "Label",
     *     badgeDefault = badgeDefault
     * )
     * ```
     *
     * @param contentColor Color: 콘텐츠 색상입니다. 기본값은 label_alternative 색상입니다.
     * @return WantedContentBadgeDefault: 중립 배지 구성을 반환합니다.
     */
    @Composable
    fun getNeutralDefault(
        contentColor: Color = DesignSystemTheme.colors.labelAlternative,
    ) = WantedContentBadgeDefault(
        contentColor = contentColor,
        backgroundColor = DesignSystemTheme.colors.fillNormal,
        outLineColor = DesignSystemTheme.colors.labelAlternative.copy(OPACITY_16)
    )
}