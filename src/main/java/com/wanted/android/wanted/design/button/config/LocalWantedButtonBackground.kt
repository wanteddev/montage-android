package com.wanted.android.wanted.design.button.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.ButtonType


val LocalWantedButtonBackground = WantedButtonBackgroundCompositionLocal()

interface WantedButtonBackgroundLoader {
    @Composable
    fun getBackgroundColor(
        shape: ButtonShape,
        type: ButtonType,
        enabled: Boolean
    ): Color
}

internal class WantedButtonBackgroundLoaderImpl : WantedButtonBackgroundLoader {
    @Composable
    override fun getBackgroundColor(
        shape: ButtonShape,
        type: ButtonType,
        enabled: Boolean
    ): Color = when (shape) {
        ButtonShape.SOLID -> getSolidBackgroundColor(type, enabled)
        ButtonShape.OUTLINED -> colorResource(id = R.color.transparent)
        ButtonShape.TEXT -> colorResource(id = R.color.transparent)
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
value class WantedButtonBackgroundCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedButtonBackgroundLoader> = staticCompositionLocalOf { WantedButtonBackgroundLoaderImpl() }
) {
    val current: WantedButtonBackgroundLoader
        @Composable get() = delegate.current

    infix fun provides(value: WantedButtonBackgroundLoader) = delegate provides value
}
