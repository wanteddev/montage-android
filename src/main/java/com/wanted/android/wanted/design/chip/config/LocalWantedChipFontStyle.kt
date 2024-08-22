package com.wanted.android.wanted.design.chip.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.chip.WantedActionContract
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle


val LocalWantedChipTextStyle = WantedChipTextStyleCompositionLocal()

interface WantedChipTextStyleLoader {
    @Composable
    fun getTextStyle(
        variant: WantedActionContract.ChipActionVariant,
        size: WantedActionContract.ChipActionSize,
        isActive: Boolean,
        isEnable: Boolean
    ): TextStyle
}

internal class WantedChipTextStyleLoaderImpl : WantedChipTextStyleLoader {

    @Composable
    override fun getTextStyle(
        variant: WantedActionContract.ChipActionVariant,
        size: WantedActionContract.ChipActionSize,
        isActive: Boolean,
        isEnable: Boolean
    ): TextStyle = WantedTextStyle(
        colorRes = getChipActionTextColor(variant, isActive, isEnable),
        style = getChipActionTextStyle(size)
    )


    @Composable
    private fun getChipActionTextColor(
        variant: WantedActionContract.ChipActionVariant,
        isActive: Boolean,
        isEnable: Boolean
    ): Int {
        return when (variant) {
            WantedActionContract.ChipActionVariant.FILLED -> {
                when {
                    !isEnable -> R.color.label_disable
                    isActive -> R.color.inverse_label
                    else -> R.color.label_normal
                }
            }

            WantedActionContract.ChipActionVariant.OUTLINED -> {
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
        size: WantedActionContract.ChipActionSize
    ): TextStyle = when (size) {
        WantedActionContract.ChipActionSize.XSMALL -> DesignSystemTheme.typography.caption1Medium
        WantedActionContract.ChipActionSize.SMALL -> DesignSystemTheme.typography.label1Medium
        WantedActionContract.ChipActionSize.NORMAL -> DesignSystemTheme.typography.body2Medium
        WantedActionContract.ChipActionSize.LARGE -> DesignSystemTheme.typography.body2Medium
    }
}

@JvmInline
value class WantedChipTextStyleCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedChipTextStyleLoader> = staticCompositionLocalOf { WantedChipTextStyleLoaderImpl() }
) {
    val current: WantedChipTextStyleLoader
        @Composable get() = delegate.current

    infix fun provides(value: WantedChipTextStyleLoader) = delegate provides value
}
