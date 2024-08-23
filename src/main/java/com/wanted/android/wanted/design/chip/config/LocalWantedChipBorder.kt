package com.wanted.android.wanted.design.chip.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.chip.WantedActionContract
import com.wanted.android.wanted.design.util.OPACITY_5


val LocalWantedChipBorder = WantedChipBorderCompositionLocal()

interface WantedChipBorderLoader {
    @Composable
    fun getBorderColor(
        variant: WantedActionContract.ChipActionVariant,
        isActive: Boolean,
        isEnable: Boolean
    ): Color
}

internal class WantedChipBorderLoaderImpl : WantedChipBorderLoader {
    @Composable
    override fun getBorderColor(
        variant: WantedActionContract.ChipActionVariant,
        isActive: Boolean,
        isEnable: Boolean
    ): Color = when (variant) {
        WantedActionContract.ChipActionVariant.FILLED -> {
            when {
                !isEnable -> colorResource(id = R.color.transparent)
                isActive -> colorResource(id = R.color.transparent)
                else -> colorResource(id = R.color.transparent)
            }
        }

        WantedActionContract.ChipActionVariant.OUTLINED -> {
            when {
                !isEnable -> colorResource(id = R.color.line_normal_neutral)
                isActive -> colorResource(id = R.color.primary_normal).copy(alpha = OPACITY_5)
                else -> colorResource(id = R.color.line_normal_neutral)
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
