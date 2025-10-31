package com.wanted.android.wanted.design.actions.chip.actionchip

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.chip.actionchip.WantedActionChipContract.ActionChipSize
import com.wanted.android.wanted.design.actions.chip.actionchip.WantedActionChipContract.ActionChipVariant
import com.wanted.android.wanted.design.actions.chip.config.LocalWantedChipActive
import com.wanted.android.wanted.design.actions.chip.config.LocalWantedChipEnable
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.OPACITY_5
import com.wanted.android.wanted.design.util.WantedTextStyle

/**
 * data class WantedActionChipDefault
 *
 * WantedActionChip의 기본 스타일을 정의하는 데이터 클래스입니다.
 * 크기, 스타일 변형, 활성화 여부, 사용 가능 여부, 아이콘 색상, 배경 색상, 테두리 색상, 텍스트 스타일을 포함합니다.
 *
 * 사용 예시:
 * ```kotlin
 * val chipDefault = WantedActionChipDefault(
 *     size = ActionChipSize.Medium,
 *     variant = ActionChipVariant.Solid,
 *     isActive = true,
 *     isEnable = true,
 *     iconColor = Color.Black,
 *     backgroundColor = Color.White,
 *     borderColor = Color.Gray,
 *     textStyle = TextStyle.Default
 * )
 * ```
 *
 * @param size ActionChipSize: Chip의 크기를 설정합니다.
 * @param variant ActionChipVariant: Chip의 스타일 변형입니다 (Solid, Outlined).
 * @param isActive Boolean: Chip의 활성화 여부입니다.
 * @param isEnable Boolean: Chip의 사용 가능 여부입니다.
 * @param iconColor Color: 아이콘의 색상입니다.
 * @param backgroundColor Color: 배경 색상입니다.
 * @param borderColor Color: 테두리 색상입니다.
 * @param textStyle TextStyle: 텍스트 스타일입니다.
 */
data class WantedActionChipDefault(
    val size: ActionChipSize = ActionChipSize.Medium,
    val variant: ActionChipVariant = ActionChipVariant.Solid,
    val isActive: Boolean = false,
    val isEnable: Boolean = true,
    val iconColor: Color,
    val backgroundColor: Color,
    val borderColor: Color,
    val textStyle: TextStyle
)


object WantedActionChipDefaults {
    /**
     * fun getDefault(...)
     *
     * 기본 스타일에 맞춰 WantedActionChipDefault 객체를 생성합니다.
     * Compose 환경에 따라 Chip의 스타일을 동적으로 결정합니다.
     *
     * 사용 예시:
     * ```kotlin
     * val chipDefault = WantedActionChipDefaults.getDefault(
     *     size = ActionChipSize.Medium,
     *     variant = ActionChipVariant.Solid
     * )
     * ```
     *
     * @param size ActionChipSize: Chip의 크기를 설정합니다.
     * @param variant ActionChipVariant: Chip의 스타일 변형입니다 (Solid, Outlined).
     * @param isActive Boolean: 활성화 여부입니다.
     * @param isEnable Boolean: 사용 가능 여부입니다.
     * @param iconColor Color: 아이콘의 색상입니다.
     * @param backgroundColor Color: 배경 색상입니다.
     * @param borderColor Color: 테두리 색상입니다.
     * @param textStyle TextStyle: 텍스트 스타일입니다.
     * @return WantedActionChipDefault: 설정된 WantedActionChipDefault 객체를 반환합니다.
     */
    @Composable
    fun getDefault(
        size: ActionChipSize = LocalWantedActionChipSize.current,
        variant: ActionChipVariant = LocalWantedActionChipVariant.current,
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
    ) = WantedActionChipDefault(
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
        variant: ActionChipVariant = LocalWantedActionChipVariant.current,
        isActive: Boolean = LocalWantedChipActive.current,
        isEnable: Boolean = LocalWantedChipEnable.current
    ): Int {
        return when (variant) {
            ActionChipVariant.Solid -> {
                when {
                    !isEnable -> R.color.label_disable
                    isActive -> R.color.inverse_label
                    else -> R.color.label_normal
                }
            }

            ActionChipVariant.Outlined -> {
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
     * val iconColor = WantedActionChipDefaults.getFilterIconColor(
     *     variant = ActionChipVariant.Solid,
     *     isActive = true
     * )
     * ```
     *
     * @param variant ActionChipVariant: Chip의 스타일 변형입니다 (Solid, Outlined).
     * @param isActive Boolean: 활성화 여부입니다.
     * @param isEnable Boolean: 사용 가능 여부입니다.
     * @return Int: 아이콘 색상에 해당하는 리소스 ID를 반환합니다.
     */
    @Composable
    fun getFilterIconColor(
        variant: ActionChipVariant = LocalWantedActionChipVariant.current,
        isActive: Boolean = LocalWantedChipActive.current,
        isEnable: Boolean = LocalWantedChipEnable.current
    ): Int {
        return when (variant) {
            ActionChipVariant.Solid -> {
                when {
                    !isEnable -> R.color.label_disable
                    isActive -> R.color.inverse_label
                    else -> R.color.label_normal
                }
            }

            ActionChipVariant.Outlined -> {
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
        variant: ActionChipVariant = LocalWantedActionChipVariant.current,
        isActive: Boolean = LocalWantedChipActive.current,
        isEnable: Boolean = LocalWantedChipEnable.current
    ): Color = when (variant) {
        ActionChipVariant.Solid -> {
            when {
                !isEnable -> colorResource(id = R.color.interaction_disable)
                isActive -> colorResource(id = R.color.inverse_background)
                else -> colorResource(id = R.color.fill_alternative)
            }
        }

        ActionChipVariant.Outlined -> {
            when {
                !isEnable -> colorResource(id = R.color.transparent)
                isActive -> colorResource(id = R.color.primary_normal).copy(alpha = OPACITY_5)
                else -> colorResource(id = R.color.transparent)
            }
        }
    }

    @Composable
    private fun getBorderColor(
        variant: ActionChipVariant = LocalWantedActionChipVariant.current,
        isActive: Boolean = LocalWantedChipActive.current,
        isEnable: Boolean = LocalWantedChipEnable.current
    ): Color = when (variant) {
        ActionChipVariant.Solid -> {
            when {
                !isEnable -> colorResource(id = R.color.transparent)
                isActive -> colorResource(id = R.color.transparent)
                else -> colorResource(id = R.color.transparent)
            }
        }

        ActionChipVariant.Outlined -> {
            when {
                !isEnable -> colorResource(id = R.color.line_normal_neutral)
                isActive -> colorResource(id = R.color.primary_normal).copy(alpha = OPACITY_43)
                else -> colorResource(id = R.color.line_normal_neutral)
            }
        }
    }

    @Composable
    private fun getTextStyle(
        size: ActionChipSize = LocalWantedActionChipSize.current,
        variant: ActionChipVariant = LocalWantedActionChipVariant.current,
        isActive: Boolean = LocalWantedChipActive.current,
        isEnable: Boolean = LocalWantedChipEnable.current
    ): TextStyle = WantedTextStyle(
        colorRes = getChipActionTextColor(variant, isActive, isEnable),
        style = getChipActionTextStyle(size)
    )


    @Composable
    private fun getChipActionTextColor(
        variant: ActionChipVariant = LocalWantedActionChipVariant.current,
        isActive: Boolean = LocalWantedChipActive.current,
        isEnable: Boolean = LocalWantedChipEnable.current
    ): Int {
        return when (variant) {
            ActionChipVariant.Solid -> {
                when {
                    !isEnable -> R.color.label_disable
                    isActive -> R.color.inverse_label
                    else -> R.color.label_normal
                }
            }

            ActionChipVariant.Outlined -> {
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
        size: ActionChipSize = LocalWantedActionChipSize.current
    ): TextStyle = when (size) {
        ActionChipSize.XSmall -> DesignSystemTheme.typography.caption1Medium
        ActionChipSize.Small -> DesignSystemTheme.typography.label1Medium
        ActionChipSize.Medium -> DesignSystemTheme.typography.body2Medium
        ActionChipSize.Large -> DesignSystemTheme.typography.body2Medium
    }
}