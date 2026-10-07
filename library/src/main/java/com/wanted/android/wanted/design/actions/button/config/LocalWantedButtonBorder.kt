package com.wanted.android.wanted.design.actions.button.config

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonVariant


val LocalWantedButtonBorder = WantedButtonBorderCompositionLocal()

interface WantedButtonBorderLoader {
    @Composable
    fun getBorderColor(
        variant: ButtonVariant,
    ): Color

    @Composable
    fun getBorderShape(
        variant: ButtonVariant,
        size: ButtonSize
    ): RoundedCornerShape
}

internal class WantedButtonBorderLoaderImpl : WantedButtonBorderLoader {
    @Composable
    override fun getBorderColor(
        variant: ButtonVariant,
    ): Color = when (variant) {
        ButtonVariant.OUTLINED -> getOutlineContentColor()
        else -> DesignSystemTheme.colors.transparent
    }

    @Composable
    override fun getBorderShape(
        variant: ButtonVariant,
        size: ButtonSize
    ): RoundedCornerShape {
        val radius = DesignSystemTheme.radius
        return RoundedCornerShape(
            when (size) {
                ButtonSize.LARGE -> if (variant == ButtonVariant.TEXT) radius.radius10 else radius.radius14
                ButtonSize.MEDIUM -> if (variant == ButtonVariant.TEXT) radius.radius8 else radius.radius12
                ButtonSize.SMALL -> if (variant == ButtonVariant.TEXT) radius.radius8 else radius.radius10
                ButtonSize.XSMALL -> radius.radius8
            }
        )
    }

    @Composable
    fun getOutlineContentColor() = DesignSystemTheme.colors.lineNeutralSecondary
}

@JvmInline
value class WantedButtonBorderCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedButtonBorderLoader> = staticCompositionLocalOf { WantedButtonBorderLoaderImpl() }
) {
    val current: WantedButtonBorderLoader
        @Composable get() = delegate.current

    infix fun provides(value: WantedButtonBorderLoader) = delegate provides value
}
