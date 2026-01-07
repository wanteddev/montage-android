package com.wanted.android.wanted.design.actions.chip

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.OPACITY_5

/**
 * data class WantedChipDefault
 *
 * WantedChip의 기본 스타일을 정의하는 데이터 클래스입니다.
 * 크기, 스타일 변형, 활성화 여부, 사용 가능 여부, 아이콘 색상, 배경 색상, 테두리 색상, 텍스트 스타일을 포함합니다.
 *
 * 사용 예시:
 * ```kotlin
 * val chipDefault = WantedChipDefault(
 *     size = ChipSize.Medium,
 *     variant = ChipVariant.Solid,
 *     isActive = true,
 *     isEnable = true,
 *     iconColor = Color.Black,
 *     backgroundColor = Color.White,
 *     borderColor = Color.Gray,
 *     textStyle = TextStyle.Default
 * )
 * ```
 *
 * @param size ChipSize: Chip의 크기를 설정합니다.
 * @param variant ChipVariant: Chip의 스타일 변형입니다 (Solid, Outlined).
 * @param isActive Boolean: Chip의 활성화 여부입니다.
 * @param isEnable Boolean: Chip의 사용 가능 여부입니다.
 * @param iconColor Color: 아이콘의 색상입니다.
 * @param backgroundColor Color: 배경 색상입니다.
 * @param borderColor Color: 테두리 색상입니다.
 * @param textStyle TextStyle: 텍스트 스타일입니다.
 */
data class WantedChipDefault(
    val size: WantedChipContract.ChipSize = WantedChipContract.ChipSize.Medium,
    val variant: WantedChipContract.ChipVariant = WantedChipContract.ChipVariant.Solid,
    val isActive: Boolean = false,
    val isEnable: Boolean = true,
    val iconColor: Color,
    val backgroundColor: Color,
    val borderColor: Color,
    val textStyle: TextStyle
)


/**
 * object WantedChipDefaults
 *
 * WantedChip의 기본 스타일을 제공하는 객체입니다.
 */
object WantedChipDefaults {
    /**
     * fun getDefault(...)
     *
     * 기본 스타일에 맞춰 WantedChipDefault 객체를 생성합니다.
     * Compose 환경에 따라 Chip의 스타일을 동적으로 결정합니다.
     *
     * 사용 예시:
     * ```kotlin
     * val chipDefault = WantedChipDefaults.getDefault(
     *     size = ChipSize.Medium,
     *     variant = ChipVariant.Solid
     * )
     * ```
     *
     * @param size ChipSize: Chip의 크기를 설정합니다.
     * @param variant ChipVariant: Chip의 스타일 변형입니다 (Solid, Outlined).
     * @param isActive Boolean: 활성화 여부입니다.
     * @param isEnable Boolean: 사용 가능 여부입니다.
     * @param iconColor Color: 아이콘의 색상입니다.
     * @param backgroundColor Color: 배경 색상입니다.
     * @param borderColor Color: 테두리 색상입니다.
     * @param textStyle TextStyle: 텍스트 스타일입니다.
     * @return WantedChipDefault: 설정된 WantedChipDefault 객체를 반환합니다.
     */
    @Composable
    fun getDefault(
        size: WantedChipContract.ChipSize = LocalWantedChipSize.current,
        variant: WantedChipContract.ChipVariant = LocalWantedChipVariant.current,
        isActive: Boolean = LocalWantedChipActive.current,
        isEnable: Boolean = LocalWantedChipEnable.current,
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
    ) = WantedChipDefault(
        size = size,
        variant = variant,
        isActive = isActive,
        isEnable = isEnable,
        iconColor = iconColor,
        backgroundColor = backgroundColor,
        borderColor = borderColor,
        textStyle = textStyle
    )


    @Composable
    private fun getIconColor(
        variant: WantedChipContract.ChipVariant = LocalWantedChipVariant.current,
        isActive: Boolean = LocalWantedChipActive.current,
        isEnable: Boolean = LocalWantedChipEnable.current
    ): Int {
        return when (variant) {
            WantedChipContract.ChipVariant.Solid -> {
                when {
                    !isEnable -> R.color.label_disable
                    isActive -> R.color.inverse_label
                    else -> R.color.label_normal
                }
            }

            WantedChipContract.ChipVariant.Outlined -> {
                when {
                    !isEnable -> R.color.label_disable
                    isActive -> R.color.primary_normal
                    else -> R.color.label_normal
                }
            }
        }
    }

    @Composable
    private fun getBackgroundColor(
        variant: WantedChipContract.ChipVariant = LocalWantedChipVariant.current,
        isActive: Boolean = LocalWantedChipActive.current,
        isEnable: Boolean = LocalWantedChipEnable.current
    ): Color = when (variant) {
        WantedChipContract.ChipVariant.Solid -> {
            when {
                !isEnable -> DesignSystemTheme.colors.interactionDisable
                isActive -> DesignSystemTheme.colors.inverseBackground
                else -> DesignSystemTheme.colors.fillAlternative
            }
        }

        WantedChipContract.ChipVariant.Outlined -> {
            when {
                !isEnable -> DesignSystemTheme.colors.transparent
                isActive -> DesignSystemTheme.colors.primaryNormal.copy(alpha = OPACITY_5)
                else -> DesignSystemTheme.colors.transparent
            }
        }
    }

    @Composable
    private fun getBorderColor(
        variant: WantedChipContract.ChipVariant = LocalWantedChipVariant.current,
        isActive: Boolean = LocalWantedChipActive.current,
        isEnable: Boolean = LocalWantedChipEnable.current
    ): Color = when (variant) {
        WantedChipContract.ChipVariant.Solid -> {
            when {
                !isEnable -> DesignSystemTheme.colors.transparent
                isActive -> DesignSystemTheme.colors.transparent
                else -> DesignSystemTheme.colors.transparent
            }
        }

        WantedChipContract.ChipVariant.Outlined -> {
            when {
                !isEnable -> DesignSystemTheme.colors.lineNormalNeutral
                isActive -> DesignSystemTheme.colors.primaryNormal.copy(alpha = OPACITY_43)
                else -> DesignSystemTheme.colors.lineNormalNeutral
            }
        }
    }

    @Composable
    private fun getTextStyle(
        size: WantedChipContract.ChipSize = LocalWantedChipSize.current,
        variant: WantedChipContract.ChipVariant = LocalWantedChipVariant.current,
        isActive: Boolean = LocalWantedChipActive.current,
        isEnable: Boolean = LocalWantedChipEnable.current
    ): TextStyle = getChipActionTextStyle(size).copy(
        color = getChipActionTextColor(variant, isActive, isEnable)
    )


    @Composable
    private fun getChipActionTextColor(
        variant: WantedChipContract.ChipVariant = LocalWantedChipVariant.current,
        isActive: Boolean = LocalWantedChipActive.current,
        isEnable: Boolean = LocalWantedChipEnable.current
    ): Color {
        return when (variant) {
            WantedChipContract.ChipVariant.Solid -> {
                when {
                    !isEnable -> DesignSystemTheme.colors.labelDisable
                    isActive -> DesignSystemTheme.colors.inverseLabel
                    else -> DesignSystemTheme.colors.labelNormal
                }
            }

            WantedChipContract.ChipVariant.Outlined -> {
                when {
                    !isEnable -> DesignSystemTheme.colors.labelDisable
                    isActive -> DesignSystemTheme.colors.primaryNormal
                    else -> DesignSystemTheme.colors.labelNormal
                }
            }
        }
    }

    @Composable
    private fun getChipActionTextStyle(
        size: WantedChipContract.ChipSize = LocalWantedChipSize.current
    ): TextStyle = when (size) {
        WantedChipContract.ChipSize.XSmall -> DesignSystemTheme.typography.caption1Medium
        WantedChipContract.ChipSize.Small -> DesignSystemTheme.typography.label1Medium
        WantedChipContract.ChipSize.Medium -> DesignSystemTheme.typography.body2Medium
        WantedChipContract.ChipSize.Large -> DesignSystemTheme.typography.body2Medium
    }
}