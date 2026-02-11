package com.wanted.android.wanted.design.actions.button.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.ButtonVariant


val LocalWantedButtonContent = WantedButtonContentCompositionLocal()

interface WantedButtonContentLoader {
    @Composable
    fun getContentColor(
        shape: ButtonVariant,
        type: ButtonType,
        enabled: Boolean
    ): Color

    @Composable
    fun getBackgroundColor(
        variant: ButtonVariant,
        type: ButtonType,
        enabled: Boolean
    ): Color
}

internal class WantedButtonContentLoaderImpl : WantedButtonContentLoader {
    @Composable
    override fun getContentColor(
        shape: ButtonVariant,
        type: ButtonType,
        enabled: Boolean
    ): Color = when (shape) {
        ButtonVariant.SOLID -> getSolidContentColor(type, enabled)
        ButtonVariant.OUTLINED -> getOutlineContentColor(type, enabled)
        ButtonVariant.TEXT -> getTextContentColor(type, enabled)
    }

    @Composable
    fun getSolidContentColor(
        type: ButtonType,
        enabled: Boolean
    ): Color = colorResource(
        id = when {
            !enabled -> R.color.label_assistive
            type == ButtonType.ASSISTIVE -> R.color.label_neutral
            else -> R.color.static_white
        }
    )

    @Composable
    fun getOutlineContentColor(
        type: ButtonType,
        enabled: Boolean
    ) = colorResource(
        id = when {
            !enabled -> R.color.label_disable
            type == ButtonType.ASSISTIVE -> R.color.label_normal
            else -> R.color.primary_normal
        }
    )

    @Composable
    fun getTextContentColor(
        type: ButtonType,
        enabled: Boolean
    ) = colorResource(
        id = when {
            !enabled -> R.color.label_disable
            type == ButtonType.ASSISTIVE -> R.color.label_alternative
            else -> R.color.primary_normal
        }
    )

    @Composable
    override fun getBackgroundColor(
        variant: ButtonVariant,
        type: ButtonType,
        enabled: Boolean
    ): Color = when (variant) {
        ButtonVariant.SOLID -> getSolidBackgroundColor(type, enabled)
        ButtonVariant.OUTLINED -> DesignSystemTheme.colors.transparent
        ButtonVariant.TEXT -> DesignSystemTheme.colors.transparent
    }

    @Composable
    fun getSolidBackgroundColor(
        type: ButtonType,
        enabled: Boolean
    ): Color = colorResource(
        id = when {
            !enabled -> R.color.interaction_disable
            type == ButtonType.ASSISTIVE -> R.color.fill_normal
            else -> R.color.primary_normal
        }
    )
}

@JvmInline
value class WantedButtonContentCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedButtonContentLoader> = staticCompositionLocalOf { WantedButtonContentLoaderImpl() }
) {
    val current: WantedButtonContentLoader
        @Composable get() = delegate.current

    infix fun provides(value: WantedButtonContentLoader) = delegate provides value
}
