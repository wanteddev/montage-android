package com.wanted.android.wanted.design.actions.button.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.ButtonVariant


val LocalWantedButtonTextStyle = WantedButtonTextStyleCompositionLocal()

interface WantedButtonTextStyleLoader {
    @Composable
    fun getTextStyle(
        variant: ButtonVariant,
        type: ButtonType,
        size: ButtonSize
    ): TextStyle
}

internal class WantedButtonTextStyleLoaderImpl : WantedButtonTextStyleLoader {

    @Composable
    override fun getTextStyle(
        variant: ButtonVariant,
        type: ButtonType,
        size: ButtonSize
    ): TextStyle = when (variant) {
        ButtonVariant.TEXT -> when (size) {
            ButtonSize.SMALL -> DesignSystemTheme.typography.label1Bold
            else -> DesignSystemTheme.typography.body1Bold
        }

        else -> if (type == ButtonType.PRIMARY) {
            when (size) {
                ButtonSize.LARGE -> DesignSystemTheme.typography.body1Bold
                ButtonSize.MEDIUM -> DesignSystemTheme.typography.body2Bold
                ButtonSize.SMALL -> DesignSystemTheme.typography.label2Bold
            }
        } else {
            when (size) {
                ButtonSize.LARGE -> DesignSystemTheme.typography.body1Medium
                ButtonSize.MEDIUM -> DesignSystemTheme.typography.body2Medium
                ButtonSize.SMALL -> DesignSystemTheme.typography.label2Medium
            }
        }
    }
}

@JvmInline
value class WantedButtonTextStyleCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedButtonTextStyleLoader> = staticCompositionLocalOf { WantedButtonTextStyleLoaderImpl() }
) {
    val current: WantedButtonTextStyleLoader
        @Composable get() = delegate.current

    infix fun provides(value: WantedButtonTextStyleLoader) = delegate provides value
}
