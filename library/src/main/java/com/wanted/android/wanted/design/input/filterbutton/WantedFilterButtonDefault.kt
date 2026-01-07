package com.wanted.android.wanted.design.input.filterbutton

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.input.filterbutton.WantedFilterButtonContract.FilterButtonSize
import com.wanted.android.wanted.design.input.filterbutton.WantedFilterButtonContract.FilterButtonVariant
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.OPACITY_5

/**
 * data class WantedFilterButtonDefault
 *
 * WantedFilterButton의 기본 스타일을 정의하는 데이터 클래스입니다.
 * 크기, 스타일 변형, 활성화 여부, 사용 가능 여부, 아이콘 색상, 배경 색상, 테두리 색상, 텍스트 스타일을 포함합니다.
 *
 * 사용 예시:
 * ```kotlin
 * val chipDefault = WantedFilterButtonDefault(
 *     size = FilterButtonSize.Medium,
 *     variant = FilterButtonVariant.Solid,
 *     isActive = true,
 *     isEnable = true,
 *     iconColor = Color.Black,
 *     backgroundColor = Color.White,
 *     borderColor = Color.Gray,
 *     textStyle = TextStyle.Default
 * )
 * ```
 *
 * @param size FilterButtonSize: Chip의 크기를 설정합니다.
 * @param variant FilterButtonVariant: Chip의 스타일 변형입니다 (Solid, Outlined).
 * @param isActive Boolean: Chip의 활성화 여부입니다.
 * @param isEnabled Boolean: Chip의 사용 가능 여부입니다.
 * @param iconColor Color: 아이콘의 색상입니다.
 * @param backgroundColor Color: 배경 색상입니다.
 * @param borderColor Color: 테두리 색상입니다.
 * @param textStyle TextStyle: 텍스트 스타일입니다.
 * @return WantedFilterButtonDefault: 설정된 WantedFilterButtonDefault 객체를 반환합니다.
 */
data class WantedFilterButtonDefault(
    val size: FilterButtonSize = FilterButtonSize.Medium,
    val variant: FilterButtonVariant = FilterButtonVariant.Solid,
    val isActive: Boolean = false,
    val isEnabled: Boolean = true,
    val iconColor: Color,
    val backgroundColor: Color,
    val borderColor: Color,
    val textStyle: TextStyle
)

/**
 * object WantedFilterButtonDefaults
 * WantedFilterButton의 기본 스타일을 제공하는 객체입니다.
 *
 */
object WantedFilterButtonDefaults {
    /**
     * fun getDefault(...)
     * 기본 스타일에 맞춰 WantedFilterButtonDefault 객체를 생성합니다.
     * Compose 환경에 따라 Chip의 스타일을 동적으로 결정합니다.
     *
     * 사용 예시:
     * ```kotlin
     * val chipDefault = WantedFilterButtonDefaults.getDefault(
     *     size = FilterButtonSize.Medium,
     *     variant = FilterButtonVariant.Solid
     * )
     * ```
     *
     * @param size FilterButtonSize: Chip의 크기를 설정합니다.
     * @param variant FilterButtonVariant: Chip의 스타일 변형입니다 (Solid, Outlined).
     * @param isActive Boolean: 활성화 여부입니다.
     * @param isEnable Boolean: 사용 가능 여부입니다.
     * @param iconColor Color: 아이콘의 색상입니다.
     * @param backgroundColor Color: 배경 색상입니다.
     * @param borderColor Color: 테두리 색상입니다.
     * @param textStyle TextStyle: 텍스트 스타일입니다.
     * @return WantedFilterButtonDefault: 설정된 WantedFilterButtonDefault 객체를 반환합니다.
     */
    @Composable
    fun getDefault(
        size: FilterButtonSize = LocalWantedFilterButtonSize.current,
        variant: FilterButtonVariant = LocalWantedFilterButtonVariant.current,
        isActive: Boolean = LocalWantedFilterButtonActive.current,
        isEnable: Boolean = LocalWantedFilterButtonEnable.current,
        iconColor: Color = colorResource(
            id = getIconColor(
                variant = variant,
                isActive = isActive,
                isEnable = isEnable
            )
        ),
        backgroundColor: Color = getBackgroundColor(
            variant = variant,
            isActive = isActive,
            isEnable = isEnable
        ),
        borderColor: Color = getBorderColor(
            variant = variant,
            isActive = isActive,
            isEnable = isEnable
        ),
        textStyle: TextStyle = getTextStyle(
            variant = variant,
            size = size,
            isActive = isActive,
            isEnable = isEnable
        )
    ) = WantedFilterButtonDefault(
        size = size,
        variant = variant,
        isActive = isActive,
        isEnabled = isEnable,
        iconColor = iconColor,
        backgroundColor = backgroundColor,
        borderColor = borderColor,
        textStyle = textStyle
    )


    @Composable
    private fun getIconColor(
        variant: FilterButtonVariant = LocalWantedFilterButtonVariant.current,
        isActive: Boolean = LocalWantedFilterButtonActive.current,
        isEnable: Boolean = LocalWantedFilterButtonEnable.current
    ): Int {
        return when (variant) {
            FilterButtonVariant.Solid -> {
                when {
                    !isEnable -> R.color.label_disable
                    isActive -> R.color.inverse_label
                    else -> R.color.label_normal
                }
            }

            FilterButtonVariant.Outlined -> {
                when {
                    !isEnable -> R.color.label_disable
                    isActive -> R.color.primary_normal
                    else -> R.color.label_normal
                }
            }
        }
    }

    /**
     * fun getFilterIconColor(...)
     *
     * Filter button 전용으로 아이콘 색상을 반환합니다.
     * Variant, 활성화 여부, 사용 가능 여부에 따라 색상이 결정됩니다.
     *
     * 사용 예시:
     * ```kotlin
     * val iconColor = WantedFilterButtonDefaults.getFilterIconColor(
     *     variant = FilterButtonVariant.Solid,
     *     isActive = true
     * )
     * ```
     *
     * @param variant FilterButtonVariant: Chip의 스타일 변형입니다 (Solid, Outlined).
     * @param isActive Boolean: 활성화 여부입니다.
     * @param isEnable Boolean: 사용 가능 여부입니다.
     * @return Int: 아이콘 색상에 해당하는 리소스 ID를 반환합니다.
     */
    @Composable
    fun getFilterIconColor(
        variant: FilterButtonVariant = LocalWantedFilterButtonVariant.current,
        isActive: Boolean = LocalWantedFilterButtonActive.current,
        isEnable: Boolean = LocalWantedFilterButtonEnable.current
    ): Int {
        return when (variant) {
            FilterButtonVariant.Solid -> {
                when {
                    !isEnable -> R.color.label_disable
                    isActive -> R.color.inverse_label
                    else -> R.color.label_normal
                }
            }

            FilterButtonVariant.Outlined -> {
                when {
                    !isEnable -> R.color.label_disable
                    isActive -> R.color.label_normal
                    else -> R.color.label_normal
                }
            }
        }
    }

    @Composable
    private fun getBackgroundColor(
        variant: FilterButtonVariant = LocalWantedFilterButtonVariant.current,
        isActive: Boolean = LocalWantedFilterButtonActive.current,
        isEnable: Boolean = LocalWantedFilterButtonEnable.current
    ): Color = when (variant) {
        FilterButtonVariant.Solid -> {
            when {
                !isEnable -> DesignSystemTheme.colors.interactionDisable
                isActive -> DesignSystemTheme.colors.inverseBackground
                else -> DesignSystemTheme.colors.fillAlternative
            }
        }

        FilterButtonVariant.Outlined -> {
            when {
                !isEnable -> DesignSystemTheme.colors.transparent
                isActive -> DesignSystemTheme.colors.primaryNormal.copy(alpha = OPACITY_5)
                else -> DesignSystemTheme.colors.transparent
            }
        }
    }

    @Composable
    private fun getBorderColor(
        variant: FilterButtonVariant = LocalWantedFilterButtonVariant.current,
        isActive: Boolean = LocalWantedFilterButtonActive.current,
        isEnable: Boolean = LocalWantedFilterButtonEnable.current
    ): Color = when (variant) {
        FilterButtonVariant.Solid -> {
            when {
                !isEnable -> DesignSystemTheme.colors.transparent
                isActive -> DesignSystemTheme.colors.transparent
                else -> DesignSystemTheme.colors.transparent
            }
        }

        FilterButtonVariant.Outlined -> {
            when {
                !isEnable -> DesignSystemTheme.colors.lineNormalNeutral
                isActive -> DesignSystemTheme.colors.primaryNormal.copy(alpha = OPACITY_43)
                else -> DesignSystemTheme.colors.lineNormalNeutral
            }
        }
    }

    @Composable
    private fun getTextStyle(
        size: FilterButtonSize = LocalWantedFilterButtonSize.current,
        variant: FilterButtonVariant = LocalWantedFilterButtonVariant.current,
        isActive: Boolean = LocalWantedFilterButtonActive.current,
        isEnable: Boolean = LocalWantedFilterButtonEnable.current
    ): TextStyle = getChipFilterTextStyle(size).copy(
        color = getChipFilterTextColor(variant, isActive, isEnable)
    )

    @Composable
    private fun getChipFilterTextColor(
        variant: FilterButtonVariant = LocalWantedFilterButtonVariant.current,
        isActive: Boolean = LocalWantedFilterButtonActive.current,
        isEnable: Boolean = LocalWantedFilterButtonEnable.current
    ): Color {
        return when (variant) {
            FilterButtonVariant.Solid -> {
                when {
                    !isEnable -> DesignSystemTheme.colors.labelDisable
                    isActive -> DesignSystemTheme.colors.inverseLabel
                    else -> DesignSystemTheme.colors.labelNormal
                }
            }

            FilterButtonVariant.Outlined -> {
                when {
                    !isEnable -> DesignSystemTheme.colors.labelDisable
                    isActive -> DesignSystemTheme.colors.primaryNormal
                    else -> DesignSystemTheme.colors.labelNormal
                }
            }
        }
    }

    @Composable
    private fun getChipFilterTextStyle(
        size: FilterButtonSize = LocalWantedFilterButtonSize.current
    ): TextStyle = when (size) {
        FilterButtonSize.XSmall -> DesignSystemTheme.typography.caption1Medium
        FilterButtonSize.Small -> DesignSystemTheme.typography.label1Medium
        FilterButtonSize.Medium -> DesignSystemTheme.typography.body2Medium
        FilterButtonSize.Large -> DesignSystemTheme.typography.body2Medium
    }
}