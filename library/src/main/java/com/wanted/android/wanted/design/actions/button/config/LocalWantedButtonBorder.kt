package com.wanted.android.wanted.design.actions.button.config

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
    ) = RoundedCornerShape(
        when (size) {
            ButtonSize.LARGE -> if (variant == ButtonVariant.TEXT) 10.dp else 12.dp
            ButtonSize.MEDIUM -> 10.dp
            ButtonSize.SMALL -> 8.dp
        }
    )

    @Composable
    fun getOutlineContentColor() = DesignSystemTheme.colors.lineNormalNeutral
}

@JvmInline
value class WantedButtonBorderCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedButtonBorderLoader> = staticCompositionLocalOf { WantedButtonBorderLoaderImpl() }
) {
    val current: WantedButtonBorderLoader
        @Composable get() = delegate.current

    infix fun provides(value: WantedButtonBorderLoader) = delegate provides value
}
