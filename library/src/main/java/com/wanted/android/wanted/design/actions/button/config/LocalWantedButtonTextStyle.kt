package com.wanted.android.wanted.design.actions.button.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.WantedTextStyle


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
    ): TextStyle = com.wanted.android.wanted.design.util.getTextStyle(
        textStyle =
                when (variant) {
                    ButtonVariant.TEXT -> when (size) {
                        ButtonSize.SMALL -> WantedTextStyle.LABEL1_BOLD
                        else -> WantedTextStyle.BODY1_BOLD
                    }

                    else -> if (type == ButtonType.PRIMARY) {
                        when (size) {
                            ButtonSize.LARGE -> WantedTextStyle.BODY1_BOLD
                            ButtonSize.MEDIUM -> WantedTextStyle.BODY2_BOLD
                            ButtonSize.SMALL -> WantedTextStyle.LABEL2_BOLD
                        }
                    } else {
                        when (size) {
                            ButtonSize.LARGE -> WantedTextStyle.BODY1_MEDIUM
                            ButtonSize.MEDIUM -> WantedTextStyle.BODY2_MEDIUM
                            ButtonSize.SMALL -> WantedTextStyle.LABEL2_MEDIUM
                        }
                    }
                }
    )
}

@JvmInline
value class WantedButtonTextStyleCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedButtonTextStyleLoader> = staticCompositionLocalOf { WantedButtonTextStyleLoaderImpl() }
) {
    val current: WantedButtonTextStyleLoader
        @Composable get() = delegate.current

    infix fun provides(value: WantedButtonTextStyleLoader) = delegate provides value
}
