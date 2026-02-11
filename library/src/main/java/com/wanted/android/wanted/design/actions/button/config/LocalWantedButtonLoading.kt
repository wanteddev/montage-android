package com.wanted.android.wanted.design.actions.button.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.ButtonVariant


val LocalWantedButtonLoading = WantedButtonLoadingCompositionLocal()

interface WantedButtonLoadingLoader {
    @Composable
    fun getLoadingSize(size: ButtonSize): Dp

    @Composable
    fun getLoadingColor(
        shape: ButtonVariant,
        type: ButtonType,
        enabled: Boolean
    ): Color
}

internal class WantedButtonLoadingLoaderImpl : WantedButtonLoadingLoader {
    @Composable
    override fun getLoadingSize(
        size: ButtonSize
    ): Dp = when (size) {
        ButtonSize.LARGE -> 18.dp
        ButtonSize.MEDIUM -> 16.dp
        ButtonSize.SMALL -> 14.dp
    }

    @Composable
    override fun getLoadingColor(
        shape: ButtonVariant,
        type: ButtonType,
        enabled: Boolean
    ): Color = when (type) {
        ButtonType.ASSISTIVE -> DesignSystemTheme.colors.labelAssistive
        else -> LocalWantedButtonContent.current.getContentColor(shape, type, enabled)
    }
}


@JvmInline
value class WantedButtonLoadingCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedButtonLoadingLoader> = staticCompositionLocalOf { WantedButtonLoadingLoaderImpl() }
) {
    val current: WantedButtonLoadingLoader
        @Composable get() = delegate.current

    infix fun provides(value: WantedButtonLoadingLoader) = delegate provides value
}
