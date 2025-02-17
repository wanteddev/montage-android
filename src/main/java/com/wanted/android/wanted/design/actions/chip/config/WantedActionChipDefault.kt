package com.wanted.android.wanted.design.actions.chip.config

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.chip.WantedActionContract.ChipActionSize
import com.wanted.android.wanted.design.actions.chip.WantedActionContract.ChipActionVariant
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_5
import com.wanted.android.wanted.design.util.WantedTextStyle

data class WantedChipDefault(
    val size: ChipActionSize = ChipActionSize.NORMAL,
    val variant: ChipActionVariant = ChipActionVariant.FILLED,
    val isActive: Boolean = false,
    val isEnable: Boolean = true,
    val iconColor: Color,
    val backgroundColor: Color,
    val borderColor: Color,
    val textStyle: TextStyle
)


object WantedChipDefaults {
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
            ChipActionVariant.FILLED -> {
                when {
                    !isEnable -> R.color.label_disable
                    isActive -> R.color.inverse_label
                    else -> R.color.label_normal
                }
            }

            ChipActionVariant.OUTLINED -> {
                when {
                    !isEnable -> R.color.label_disable
                    isActive -> R.color.primary_normal
                    else -> R.color.label_normal
                }
            }
        }
    }

    @Composable
    fun getFilterIconColor(
        variant: ChipActionVariant = LocalWantedChipVariant.current,
        isActive: Boolean = LocalWantedChipActive.current,
        isEnable: Boolean = LocalWantedChipEnable.current
    ): Int {
        return when (variant) {
            ChipActionVariant.FILLED -> {
                when {
                    !isEnable -> R.color.label_disable
                    isActive -> R.color.inverse_label
                    else -> R.color.label_normal
                }
            }

            ChipActionVariant.OUTLINED -> {
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
        ChipActionVariant.FILLED -> {
            when {
                !isEnable -> colorResource(id = R.color.interaction_disable)
                isActive -> colorResource(id = R.color.inverse_background)
                else -> colorResource(id = R.color.fill_alternative)
            }
        }

        ChipActionVariant.OUTLINED -> {
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
        ChipActionVariant.FILLED -> {
            when {
                !isEnable -> colorResource(id = R.color.transparent)
                isActive -> colorResource(id = R.color.transparent)
                else -> colorResource(id = R.color.transparent)
            }
        }

        ChipActionVariant.OUTLINED -> {
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
            ChipActionVariant.FILLED -> {
                when {
                    !isEnable -> R.color.label_disable
                    isActive -> R.color.inverse_label
                    else -> R.color.label_normal
                }
            }

            ChipActionVariant.OUTLINED -> {
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
        ChipActionSize.XSMALL -> DesignSystemTheme.typography.caption1Medium
        ChipActionSize.SMALL -> DesignSystemTheme.typography.label1Medium
        ChipActionSize.NORMAL -> DesignSystemTheme.typography.body2Medium
        ChipActionSize.LARGE -> DesignSystemTheme.typography.body2Medium
    }
}