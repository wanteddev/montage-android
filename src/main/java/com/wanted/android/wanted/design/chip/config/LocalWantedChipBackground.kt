package com.wanted.android.wanted.design.chip.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.chip.WantedActionContract
import com.wanted.android.wanted.design.util.OPACITY_5


val LocalWantedChipBackground = WantedChipBackgroundCompositionLocal()

interface WantedChipBackgroundLoader {
    @Composable
    fun getBackgroundColor(
        variant: WantedActionContract.ChipActionVariant,
        isActive: Boolean,
        isEnable: Boolean
    ): Color
}

internal class WantedChipBackgroundLoaderImpl : WantedChipBackgroundLoader {
    @Composable
    override fun getBackgroundColor(
        variant: WantedActionContract.ChipActionVariant,
        isActive: Boolean,
        isEnable: Boolean
    ): Color = when (variant) {
        WantedActionContract.ChipActionVariant.FILLED -> {
            when {
                !isEnable -> colorResource(id = R.color.interaction_disable)
                isActive -> colorResource(id = R.color.inverse_background)
                else -> colorResource(id = R.color.fill_alternative)
            }
        }

        WantedActionContract.ChipActionVariant.OUTLINED -> {
            when {
                !isEnable -> colorResource(id = R.color.transparent)
                isActive -> colorResource(id = R.color.primary_normal).copy(alpha = OPACITY_5)
                else -> colorResource(id = R.color.transparent)
            }
        }
    }
}


@JvmInline
value class WantedChipBackgroundCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedChipBackgroundLoader> = staticCompositionLocalOf { WantedChipBackgroundLoaderImpl() }
) {
    val current: WantedChipBackgroundLoader
        @Composable get() = delegate.current

    infix fun provides(value: WantedChipBackgroundLoader) = delegate provides value
}
