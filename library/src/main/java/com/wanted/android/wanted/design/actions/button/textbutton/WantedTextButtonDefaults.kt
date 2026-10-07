package com.wanted.android.wanted.design.actions.button.textbutton

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_12

/**
 * data class WantedTextButtonDefault
 *
 * [WantedTextButton]의 스타일을 정의한 데이터 클래스입니다.
 *
 * SOLID/OUTLINED와 공유하던 `WantedButtonDefault`와 달리 배경·테두리처럼 Text Button에 없는 속성을 갖지 않으며,
 * 폐기된 `ButtonVariant`에도 의존하지 않습니다.
 *
 * @param color WantedTextButtonColor: 버튼의 색 축입니다.
 * @param size WantedTextButtonSize: 버튼의 크기 축입니다.
 * @param enabled Boolean: 버튼의 활성화 여부입니다.
 * @param contentColor Color: 텍스트 색상입니다.
 * @param leadingIconTintColor Color: 왼쪽 아이콘의 색상입니다.
 * @param trailingIconTintColor Color: 오른쪽 아이콘의 색상입니다.
 * @param textStyle TextStyle: 텍스트의 타이포그래피입니다.
 * @param rippleColor Color: 터치 영역 리플의 색상입니다.
 * @param loadingColor Color: 로딩 인디케이터의 색상입니다.
 */
data class WantedTextButtonDefault(
    val color: WantedTextButtonColor,
    val size: WantedTextButtonSize,
    val enabled: Boolean,
    val contentColor: Color,
    val leadingIconTintColor: Color,
    val trailingIconTintColor: Color,
    val textStyle: TextStyle,
    val rippleColor: Color,
    val loadingSize: Dp,
    val loadingColor: Color
)

/**
 * object WantedTextButtonDefaults
 *
 * [WantedTextButtonDefault]의 기본값을 제공하는 객체입니다.
 *
 * Figma Text Button 컴포넌트가 정의한 `color` x `size` 축만으로 기본 스타일을 결정하고,
 * 그 조합으로 표현되지 않는 색·타이포는 `contentColor` / `textStyle` 인자로 덮어 사용합니다.
 * (Figma의 customize 속성 = contentColor, typography)
 */
object WantedTextButtonDefaults {
    /**
     * fun getDefault(...)
     *
     * [WantedTextButtonDefault]의 기본 설정을 생성합니다.
     *
     * 사용 예시:
     * ```kotlin
     * val config = WantedTextButtonDefaults.getDefault(
     *     color = WantedTextButtonColor.ASSISTIVE,
     *     size = WantedTextButtonSize.SMALL
     * )
     * ```
     *
     * `enabled = false`일 때 텍스트·아이콘 색은 Figma 스펙상 고정이므로 호출부가 덮은 색보다 우선합니다.
     * 덕분에 색을 덮는 호출부가 disabled 분기를 따로 들고 있지 않아도 됩니다.
     *
     * @param color WantedTextButtonColor: 버튼의 색 축입니다. 기본값은 PRIMARY입니다.
     * @param size WantedTextButtonSize: 버튼의 크기 축입니다. 기본값은 MEDIUM입니다.
     * @param enabled Boolean: 버튼의 활성화 여부입니다. 기본값은 true입니다.
     * @param contentColor Color: 텍스트 색상입니다. color, enabled에 따라 자동 설정됩니다.
     * @param leadingIconTintColor Color: 왼쪽 아이콘의 색상입니다. 기본값은 contentColor입니다.
     * @param trailingIconTintColor Color: 오른쪽 아이콘의 색상입니다. 기본값은 contentColor입니다.
     * @param textStyle TextStyle: 텍스트의 타이포그래피입니다. size에 따라 자동 설정됩니다.
     * @param rippleColor Color: 터치 영역 리플의 색상입니다. color, contentColor에 따라 자동 설정됩니다.
     * @param loadingSize Dp: 로딩 인디케이터의 크기입니다. size에 따라 자동 설정됩니다.
     * @param loadingColor Color: 로딩 인디케이터의 색상입니다. color, contentColor에 따라 자동 설정됩니다.
     * @return WantedTextButtonDefault: 설정된 WantedTextButtonDefault 인스턴스를 반환합니다.
     */
    @Composable
    fun getDefault(
        color: WantedTextButtonColor = WantedTextButtonColor.PRIMARY,
        size: WantedTextButtonSize = WantedTextButtonSize.MEDIUM,
        enabled: Boolean = true,
        contentColor: Color = getContentColor(color, enabled),
        leadingIconTintColor: Color = contentColor,
        trailingIconTintColor: Color = contentColor,
        textStyle: TextStyle = getTextStyle(size),
        rippleColor: Color = getRippleColor(color, contentColor),
        loadingSize: Dp = getLoadingSize(size),
        loadingColor: Color = getLoadingColor(color, contentColor)
    ): WantedTextButtonDefault {
        // disable 상태의 텍스트·아이콘 색은 고정이라 호출부가 덮은 색보다 우선한다.
        val disableColor = DesignSystemTheme.colors.foregroundDisablePrimary
        return WantedTextButtonDefault(
            color = color,
            size = size,
            enabled = enabled,
            contentColor = if (enabled) contentColor else disableColor,
            leadingIconTintColor = if (enabled) leadingIconTintColor else disableColor,
            trailingIconTintColor = if (enabled) trailingIconTintColor else disableColor,
            textStyle = textStyle,
            rippleColor = rippleColor,
            loadingSize = loadingSize,
            loadingColor = loadingColor
        )
    }

    @Composable
    internal fun getContentColor(
        color: WantedTextButtonColor,
        enabled: Boolean
    ): Color {
        val colors = DesignSystemTheme.colors
        return when {
            !enabled -> colors.foregroundDisablePrimary
            color == WantedTextButtonColor.ASSISTIVE -> colors.foregroundNeutralTertiary
            else -> colors.foregroundBrandPrimary
        }
    }

    @Composable
    internal fun getTextStyle(
        size: WantedTextButtonSize
    ): TextStyle {
        val typography = DesignSystemTheme.typography
        return when (size) {
            WantedTextButtonSize.LARGE -> typography.body1Bold
            WantedTextButtonSize.MEDIUM -> typography.body1Bold
            WantedTextButtonSize.SMALL -> typography.label1Bold
            WantedTextButtonSize.XSMALL -> typography.label1Bold
        }
    }

    @Composable
    internal fun getRippleColor(
        color: WantedTextButtonColor,
        contentColor: Color
    ): Color = when (color) {
        WantedTextButtonColor.PRIMARY -> contentColor.copy(alpha = OPACITY_12)
        WantedTextButtonColor.ASSISTIVE -> DesignSystemTheme.colorsOpacity.foregroundNeutralPrimaryOpacity12
    }

    @Composable
    internal fun getLoadingSize(
        size: WantedTextButtonSize
    ): Dp {
        val dimension = DesignSystemTheme.dimension
        return when (size) {
            WantedTextButtonSize.LARGE -> dimension.dimension16
            WantedTextButtonSize.MEDIUM -> dimension.dimension16
            WantedTextButtonSize.SMALL -> dimension.dimension12
            WantedTextButtonSize.XSMALL -> dimension.dimension12
        }
    }

    @Composable
    internal fun getLoadingColor(
        color: WantedTextButtonColor,
        contentColor: Color
    ): Color = when (color) {
        WantedTextButtonColor.ASSISTIVE -> DesignSystemTheme.colors.foregroundNeutralQuaternary
        WantedTextButtonColor.PRIMARY -> contentColor
    }
}
