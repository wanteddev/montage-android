package com.wanted.android.wanted.design.actions.chip

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.chip.WantedActionContract.ChipActionSize
import com.wanted.android.wanted.design.actions.chip.WantedActionContract.ChipActionVariant
import com.wanted.android.wanted.design.actions.chip.config.LocalWantedChipActive
import com.wanted.android.wanted.design.actions.chip.config.LocalWantedChipEnable
import com.wanted.android.wanted.design.actions.chip.config.LocalWantedChipSize
import com.wanted.android.wanted.design.actions.chip.config.LocalWantedChipVariant
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_5
import com.wanted.android.wanted.design.util.WantedTextStyle

/**
 * WantedActionChip 기본 스타일을 정의하는 데이터 클래스입니다.
 * 크기, 스타일 변형, 활성화 여부, 사용 가능 여부, 아이콘 색상, 배경 색상, 테두리 색상, 텍스트 스타일을 포함합니다.
 *
 * 사용 예시:
 * ```kotlin
 * val chipDefault = WantedChipDefault(
 *     size = ChipActionSize.Medium,
 *     variant = ChipActionVariant.Solid,
 *     isActive = true,
 *     isEnable = true,
 *     iconColor = Color.Black,
 *     backgroundColor = Color.White,
 *     borderColor = Color.Gray,
 *     textStyle = TextStyle.Default
 * )
 * ```
 *
 * @param size Chip 크기 설정
 * @param variant Chip 스타일 변형 (Solid, Outlined)
 * @param isActive Chip 활성화 여부
 * @param isEnable Chip 사용 가능 여부
 * @param iconColor 아이콘 색상
 * @param backgroundColor 배경 색상
 * @param borderColor 테두리 색상
 * @param textStyle 텍스트 스타일
 */
data class WantedChipDefault(
    val size: ChipActionSize = ChipActionSize.Medium,
    val variant: ChipActionVariant = ChipActionVariant.Solid,
    val isActive: Boolean = false,
    val isEnable: Boolean = true,
    val iconColor: Color,
    val backgroundColor: Color,
    val borderColor: Color,
    val textStyle: TextStyle
)


object WantedChipDefaults {
    /**
     * 기본 스타일에 맞춰 WantedChipDefault 객체를 생성합니다.
     * 컴포즈 환경에 따라 Chip의 스타일을 동적으로 결정합니다.
     *
     * 사용 예시:
     * ```kotlin
     * val chipDefault = WantedChipDefaults.getDefault()
     * ```
     *
     * @param size Chip 크기 설정
     * @param variant Chip 스타일 변형 (Solid, Outlined)
     * @param isActive 활성화 여부
     * @param isEnable 사용 가능 여부
     * @param iconColor 아이콘 색상
     * @param backgroundColor 배경 색상
     * @param borderColor 테두리 색상
     * @param textStyle 텍스트 스타일
     * @return 설정된 WantedChipDefault 객체 반환
     */
    @Composable
    fun getDefault(
        size: ChipActionSize = LocalWantedChipSize.current,
        variant: ChipActionVariant = LocalWantedChipVariant.current,
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
        variant: ChipActionVariant = LocalWantedChipVariant.current,
        isActive: Boolean = LocalWantedChipActive.current,
        isEnable: Boolean = LocalWantedChipEnable.current
    ): Int {
        return when (variant) {
            ChipActionVariant.Solid -> {
                when {
                    !isEnable -> R.color.label_disable
                    isActive -> R.color.inverse_label
                    else -> R.color.label_normal
                }
            }

            ChipActionVariant.Outlined -> {
                when {
                    !isEnable -> R.color.label_disable
                    isActive -> R.color.primary_normal
                    else -> R.color.label_normal
                }
            }
        }
    }

    /**
     * 필터 Chip 전용으로 아이콘 색상을 반환합니다.
     * Chip의 variant, 활성화 여부, 사용 가능 여부에 따라 색상이 결정됩니다.
     *
     * 사용 예시:
     * ```kotlin
     * val iconColor = WantedChipDefaults.getFilterIconColor()
     * ```
     *
     * @param variant Chip 스타일 변형 (Solid, Outlined)
     * @param isActive 활성화 여부
     * @param isEnable 사용 가능 여부
     * @return 아이콘 색상에 해당하는 리소스 ID 반환
     */
    @Composable
    fun getFilterIconColor(
        variant: ChipActionVariant = LocalWantedChipVariant.current,
        isActive: Boolean = LocalWantedChipActive.current,
        isEnable: Boolean = LocalWantedChipEnable.current
    ): Int {
        return when (variant) {
            ChipActionVariant.Solid -> {
                when {
                    !isEnable -> R.color.label_disable
                    isActive -> R.color.inverse_label
                    else -> R.color.label_normal
                }
            }

            ChipActionVariant.Outlined -> {
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
        variant: ChipActionVariant = LocalWantedChipVariant.current,
        isActive: Boolean = LocalWantedChipActive.current,
        isEnable: Boolean = LocalWantedChipEnable.current
    ): Color = when (variant) {
        ChipActionVariant.Solid -> {
            when {
                !isEnable -> colorResource(id = R.color.interaction_disable)
                isActive -> colorResource(id = R.color.inverse_background)
                else -> colorResource(id = R.color.fill_alternative)
            }
        }

        ChipActionVariant.Outlined -> {
            when {
                !isEnable -> colorResource(id = R.color.transparent)
                isActive -> colorResource(id = R.color.primary_normal).copy(alpha = OPACITY_5)
                else -> colorResource(id = R.color.transparent)
            }
        }
    }

    @Composable
    private fun getBorderColor(
        variant: ChipActionVariant = LocalWantedChipVariant.current,
        isActive: Boolean = LocalWantedChipActive.current,
        isEnable: Boolean = LocalWantedChipEnable.current
    ): Color = when (variant) {
        ChipActionVariant.Solid -> {
            when {
                !isEnable -> colorResource(id = R.color.transparent)
                isActive -> colorResource(id = R.color.transparent)
                else -> colorResource(id = R.color.transparent)
            }
        }

        ChipActionVariant.Outlined -> {
            when {
                !isEnable -> colorResource(id = R.color.line_normal_neutral)
                isActive -> colorResource(id = R.color.primary_normal).copy(alpha = OPACITY_5)
                else -> colorResource(id = R.color.line_normal_neutral)
            }
        }
    }

    @Composable
    private fun getTextStyle(
        size: ChipActionSize = LocalWantedChipSize.current,
        variant: ChipActionVariant = LocalWantedChipVariant.current,
        isActive: Boolean = LocalWantedChipActive.current,
        isEnable: Boolean = LocalWantedChipEnable.current
    ): TextStyle = WantedTextStyle(
        colorRes = getChipActionTextColor(variant, isActive, isEnable),
        style = getChipActionTextStyle(size)
    )


    @Composable
    private fun getChipActionTextColor(
        variant: ChipActionVariant = LocalWantedChipVariant.current,
        isActive: Boolean = LocalWantedChipActive.current,
        isEnable: Boolean = LocalWantedChipEnable.current
    ): Int {
        return when (variant) {
            ChipActionVariant.Solid -> {
                when {
                    !isEnable -> R.color.label_disable
                    isActive -> R.color.inverse_label
                    else -> R.color.label_normal
                }
            }

            ChipActionVariant.Outlined -> {
                when {
                    !isEnable -> R.color.label_disable
                    isActive -> R.color.primary_normal
                    else -> R.color.label_normal
                }
            }
        }
    }

    @Composable
    private fun getChipActionTextStyle(
        size: ChipActionSize = LocalWantedChipSize.current
    ): TextStyle = when (size) {
        ChipActionSize.XSmall -> DesignSystemTheme.typography.caption1Medium
        ChipActionSize.Small -> DesignSystemTheme.typography.label1Medium
        ChipActionSize.Medium -> DesignSystemTheme.typography.body2Medium
        ChipActionSize.Large -> DesignSystemTheme.typography.body2Medium
    }
}