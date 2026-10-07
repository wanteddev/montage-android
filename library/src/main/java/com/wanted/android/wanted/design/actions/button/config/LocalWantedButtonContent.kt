package com.wanted.android.wanted.design.actions.button.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.button.textbutton.WantedTextButtonDefaults
import com.wanted.android.wanted.design.actions.button.textbutton.toWantedTextButtonColor
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
        ButtonVariant.TEXT -> WantedTextButtonDefaults.getContentColor(
            color = type.toWantedTextButtonColor(),
            enabled = enabled
        )
    }

    @Composable
    fun getSolidContentColor(
        type: ButtonType,
        enabled: Boolean
    ): Color = colorResource(
        id = when {
            !enabled -> R.color.foreground_disable_primary
            type == ButtonType.ASSISTIVE -> R.color.foreground_neutral_secondary
            type == ButtonType.NEGATIVE -> R.color.foreground_negative_strong
            else -> R.color.static_white
        }
    )

    // NEGATIVE는 outlined에서 미지원. WantedOutlinedButton 진입부에서 PRIMARY로 fallback된다.
    @Composable
    fun getOutlineContentColor(
        type: ButtonType,
        enabled: Boolean
    ) = colorResource(
        id = when {
            !enabled -> R.color.foreground_disable_primary
            type == ButtonType.ASSISTIVE -> R.color.foreground_neutral_primary
            else -> R.color.foreground_brand_primary
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
            !enabled -> R.color.surface_disable_primary
            type == ButtonType.ASSISTIVE -> R.color.surface_neutral_secondary
            type == ButtonType.NEGATIVE -> R.color.foreground_negative_primary_opacity12
            else -> R.color.surface_brand_primary
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
