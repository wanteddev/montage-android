package com.wanted.android.wanted.design.chip.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.chip.WantedActionContract
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.OPACITY_5


val LocalWantedChipBorder = WantedChipBorderCompositionLocal()

interface WantedChipBorderLoader {
    @Composable
    fun getBorderColor(
        variant: WantedActionContract.ChipActionVariant,
        size: WantedActionContract.ChipActionSize,
        isActive: Boolean
    ): Color
}

internal class WantedChipBorderLoaderImpl : WantedChipBorderLoader {
    @Composable
    override fun getBorderColor(
        variant: WantedActionContract.ChipActionVariant,
        size: WantedActionContract.ChipActionSize,
        isActive: Boolean
    ): Color = when (variant) {
        WantedActionContract.ChipActionVariant.FILLED -> {
            if (isActive) {
                colorResource(id = R.color.inverse_background)
            } else {
                colorResource(id = R.color.fill_alternative)
            }
        }

        WantedActionContract.ChipActionVariant.OUTLINED -> {
            if (isActive) {
                colorResource(id = R.color.primary_normal).copy(alpha = OPACITY_43)
            } else {
                colorResource(id = R.color.line_normal_neutral)
            }
        }
    }
}

@JvmInline
value class WantedChipBorderCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedChipBorderLoader> = staticCompositionLocalOf { WantedChipBorderLoaderImpl() }
) {
    val current: WantedChipBorderLoader
        @Composable get() = delegate.current

    infix fun provides(value: WantedChipBorderLoader) = delegate provides value
}
