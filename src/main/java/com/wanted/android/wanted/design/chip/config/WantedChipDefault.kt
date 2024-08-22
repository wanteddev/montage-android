package com.wanted.android.wanted.design.chip.config

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import com.wanted.android.wanted.design.chip.WantedActionContract.ChipActionSize
import com.wanted.android.wanted.design.chip.WantedActionContract.ChipActionVariant

data class WantedChipDefault(
    val size: ChipActionSize = ChipActionSize.SMALL,
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
        size: ChipActionSize = ChipActionSize.SMALL,
        variant: ChipActionVariant = ChipActionVariant.FILLED,
        isActive: Boolean = false,
        isEnable: Boolean = true,
        iconColor: Color = getIconColor(
            variant = variant,
            isActive = isActive,
            isEnable = isEnable
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
        variant: ChipActionVariant,
        isActive: Boolean,
        isEnable: Boolean
    ): Color {
        return colorResource(
            id = LocalWantedChipIconColor.current.getIconColor(
                variant,
                isActive,
                isEnable
            )
        )
    }

    @Composable
    private fun getBackgroundColor(
        variant: ChipActionVariant,
        isActive: Boolean,
        isEnable: Boolean
    ): Color {
        return LocalWantedChipBackground.current.getBackgroundColor(
            variant = variant,
            isActive = isActive,
            isEnable = isEnable
        )
    }

    @Composable
    private fun getBorderColor(
        variant: ChipActionVariant,
        isActive: Boolean,
        isEnable: Boolean
    ): Color {
        return LocalWantedChipBorder.current.getBorderColor(
            variant = variant,
            isActive = isActive,
            isEnable = isEnable
        )
    }

    @Composable
    private fun getTextStyle(
        variant: ChipActionVariant,
        size: ChipActionSize,
        isActive: Boolean,
        isEnable: Boolean
    ): TextStyle {
        return LocalWantedChipTextStyle.current.getTextStyle(
            variant = variant,
            size = size,
            isActive = isActive,
            isEnable = isEnable
        )
    }
}