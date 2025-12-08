package com.wanted.android.wanted.design.actions.button.config

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.ButtonVariant

/**
 * data class WantedButtonDefault
 *
 * 버튼의 스타일 및 속성을 정의한 데이터 클래스입니다.
 * 각 버튼의 모든 시각적 속성과 상태를 개별적으로 설정할 수 있습니다.
 *
 * @param variant ButtonVariant: 버튼의 변형 타입입니다 (SOLID, OUTLINED, TEXT 등).
 * @param type ButtonType: 버튼의 종류입니다 (PRIMARY, ASSISTIVE 등).
 * @param enabled Boolean: 버튼의 활성화 여부입니다.
 * @param size ButtonSize: 버튼의 크기입니다 (LARGE, MEDIUM, SMALL 등).
 * @param contentColor Color: 버튼 콘텐츠의 색상입니다.
 * @param leftIconTintColor Color: 왼쪽 아이콘의 색상입니다.
 * @param rightIconTintColor Color: 오른쪽 아이콘의 색상입니다.
 * @param backgroundColor Color: 버튼의 배경 색상입니다.
 * @param borderColor Color: 버튼의 테두리 색상입니다.
 * @param borderShape RoundedCornerShape: 버튼의 테두리 형태입니다.
 * @param textStyle TextStyle: 버튼 텍스트의 스타일입니다.
 * @param loadingSize Dp: 로딩 인디케이터의 크기입니다.
 * @param loadingColor Color: 로딩 인디케이터의 색상입니다.
 */
data class WantedButtonDefault(
    val variant: ButtonVariant,
    val type: ButtonType,
    val enabled: Boolean,
    val size: ButtonSize,
    val contentColor: Color,
    val leftIconTintColor: Color,
    val rightIconTintColor: Color,
    val backgroundColor: Color,
    val borderColor: Color,
    val borderShape: RoundedCornerShape,
    val textStyle: TextStyle,
    val loadingSize: Dp,
    val loadingColor: Color
)


/**
 * object WantedButtonDefaults
 *
 * WantedButtonDefault의 기본값을 제공하는 객체입니다.
 * Variant, Type, Size에 따라 적절한 스타일을 자동으로 설정합니다.
 */
object WantedButtonDefaults {
    /**
     * fun getDefault(...)
     *
     * WantedButtonDefault의 기본 설정을 생성합니다.
     *
     * 버튼의 Variant, Type, Size에 따라 콘텐츠 색상, 배경 색상, 테두리 등의 스타일을 자동으로 설정합니다.
     * 각 속성을 개별적으로 커스터마이징할 수도 있습니다.
     *
     * 사용 예시:
     * ```kotlin
     * val config = WantedButtonDefaults.getDefault(
     *     variant = ButtonVariant.SOLID,
     *     type = ButtonType.PRIMARY,
     *     size = ButtonSize.LARGE
     * )
     * ```
     *
     * @param variant ButtonVariant: 버튼의 변형 타입입니다. 기본값은 ButtonVariant.SOLID입니다.
     * @param type ButtonType: 버튼의 종류입니다. 기본값은 ButtonType.PRIMARY입니다.
     * @param enabled Boolean: 버튼의 활성화 여부입니다. 기본값은 true입니다.
     * @param size ButtonSize: 버튼의 크기입니다. 기본값은 ButtonSize.LARGE입니다.
     * @param contentColor Color: 버튼 콘텐츠의 색상입니다. variant, type, enabled에 따라 자동 설정됩니다.
     * @param leftIconTintColor Color: 왼쪽 아이콘의 색상입니다. variant, type, enabled에 따라 자동 설정됩니다.
     * @param rightIconTintColor Color: 오른쪽 아이콘의 색상입니다. variant, type, enabled에 따라 자동 설정됩니다.
     * @param backgroundColor Color: 버튼의 배경 색상입니다. variant, type, enabled에 따라 자동 설정됩니다.
     * @param borderColor Color: 버튼의 테두리 색상입니다. variant에 따라 자동 설정됩니다.
     * @param borderShape RoundedCornerShape: 버튼의 테두리 형태입니다. variant, size에 따라 자동 설정됩니다.
     * @param textStyle TextStyle: 버튼 텍스트의 스타일입니다. variant, type, size에 따라 자동 설정됩니다.
     * @param loadingSize Dp: 로딩 인디케이터의 크기입니다. size에 따라 자동 설정됩니다.
     * @param loadingColor Color: 로딩 인디케이터의 색상입니다. variant, type, enabled에 따라 자동 설정됩니다.
     * @return WantedButtonDefault: 설정된 WantedButtonDefault 인스턴스를 반환합니다.
     */
    @Composable
    fun getDefault(
        variant: ButtonVariant = ButtonVariant.SOLID,
        type: ButtonType = ButtonType.PRIMARY,
        enabled: Boolean = true,
        size: ButtonSize = ButtonSize.LARGE,
        contentColor: Color = getContentColor(variant, type, enabled),
        leftIconTintColor: Color = getContentColor(variant, type, enabled),
        rightIconTintColor: Color = getContentColor(variant, type, enabled),
        backgroundColor: Color = getBackgroundColor(variant, type, enabled),
        borderColor: Color = getBorderColor(variant),
        borderShape: RoundedCornerShape = getBorderShape(variant, size),
        textStyle: TextStyle = getTextStyle(variant, type, size),
        loadingSize: Dp = getLoadingSize(size),
        loadingColor: Color = getLoadingColor(variant, type, enabled),
    ) = WantedButtonDefault(
        variant = variant,
        type = type,
        enabled = enabled,
        size = size,
        contentColor = contentColor,
        leftIconTintColor = leftIconTintColor,
        rightIconTintColor = rightIconTintColor,
        backgroundColor = backgroundColor,
        borderColor = borderColor,
        borderShape = borderShape,
        textStyle = textStyle,
        loadingSize = loadingSize,
        loadingColor = loadingColor
    )

    @Composable
    private fun getContentColor(
        variant: ButtonVariant,
        type: ButtonType = ButtonType.PRIMARY,
        enabled: Boolean
    ): Color = LocalWantedButtonContent.current.getContentColor(variant, type, enabled)

    @Composable
    private fun getBackgroundColor(
        variant: ButtonVariant,
        type: ButtonType = ButtonType.PRIMARY,
        enabled: Boolean
    ): Color = LocalWantedButtonContent.current.getBackgroundColor(
        variant = variant,
        type = type,
        enabled = enabled
    )

    @Composable
    private fun getBorderColor(
        variant: ButtonVariant
    ): Color = LocalWantedButtonBorder.current.getBorderColor(variant = variant)

    @Composable
    private fun getBorderShape(
        variant: ButtonVariant,
        size: ButtonSize = ButtonSize.LARGE
    ): RoundedCornerShape = LocalWantedButtonBorder.current.getBorderShape(variant, size)

    @Composable
    private fun getTextStyle(
        variant: ButtonVariant,
        type: ButtonType = ButtonType.PRIMARY,
        size: ButtonSize = ButtonSize.LARGE
    ): TextStyle = LocalWantedButtonTextStyle.current.getTextStyle(
        variant = variant,
        type = type,
        size = size
    )

    @Composable
    private fun getLoadingSize(
        size: ButtonSize = ButtonSize.LARGE
    ): Dp = LocalWantedButtonLoading.current.getLoadingSize(size)

    @Composable
    private fun getLoadingColor(
        variant: ButtonVariant,
        type: ButtonType = ButtonType.PRIMARY,
        enabled: Boolean = true
    ): Color = LocalWantedButtonLoading.current.getLoadingColor(variant, type, enabled)
}
