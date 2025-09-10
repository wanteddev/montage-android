package com.wanted.android.wanted.design.actions.button.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.ButtonVariant


val LocalWantedButtonBorder = WantedButtonBorderCompositionLocal()

interface WantedButtonBorderLoader {
    @Composable
    fun getBorderColor(
        variant: ButtonVariant,
    ): Color
}

internal class WantedButtonBorderLoaderImpl : WantedButtonBorderLoader {
    @Composable
    override fun getBorderColor(
        variant: ButtonVariant,
    ): Color = when (variant) {
        ButtonVariant.OUTLINED -> getOutlineContentColor()
        else -> colorResource(id = R.color.transparent)
    }

    @Composable
    fun getOutlineContentColor() = colorResource(id = R.color.line_normal_neutral)
}

@JvmInline
value class WantedButtonBorderCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedButtonBorderLoader> = staticCompositionLocalOf { WantedButtonBorderLoaderImpl() }
) {
    val current: WantedButtonBorderLoader
        @Composable get() = delegate.current

    infix fun provides(value: WantedButtonBorderLoader) = delegate provides value
}
