package com.wanted.android.wanted.design.chip.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.chip.WantedActionContract


val LocalWantedChipIconColor = WantedChipIconColorCompositionLocal()

interface WantedChipIconColorLoader {
    @Composable
    fun getIconColor(
        variant: WantedActionContract.ChipActionVariant,
        isActive: Boolean,
        isEnable: Boolean
    ): Int
}

internal class WantedChipIconColorLoaderImpl : WantedChipIconColorLoader {

    @Composable
    override fun getIconColor(
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

}

internal class LocalWantedFilterChipIconColorLoaderImpl : WantedChipIconColorLoader {

    @Composable
    override fun getIconColor(
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
                    isActive -> R.color.label_normal
                    else -> R.color.label_normal
                }
            }
        }
    }

}


@JvmInline
value class WantedChipIconColorCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedChipIconColorLoader> = staticCompositionLocalOf { WantedChipIconColorLoaderImpl() }
) {
    val current: WantedChipIconColorLoader
        @Composable get() = delegate.current

    infix fun provides(value: WantedChipIconColorLoader) = delegate provides value
}
