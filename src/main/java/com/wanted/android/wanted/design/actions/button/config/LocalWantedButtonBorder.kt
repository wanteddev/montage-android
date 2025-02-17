package com.wanted.android.wanted.design.actions.button.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.ButtonType


val LocalWantedButtonBorder = WantedButtonBorderCompositionLocal()

interface WantedButtonBorderLoader {
    @Composable
    fun getBorderColor(
        shape: ButtonShape,
        type: ButtonType,
        enabled: Boolean
    ): Color
}

internal class WantedButtonBorderLoaderImpl : WantedButtonBorderLoader {
    @Composable
    override fun getBorderColor(
        shape: ButtonShape,
        type: ButtonType,
        enabled: Boolean
    ): Color = when (shape) {
        ButtonShape.OUTLINED -> getOutlineContentColor(type, enabled)
        else -> colorResource(id = R.color.transparent)
    }

    @Composable
    fun getOutlineContentColor(
        type: ButtonType,
        enabled: Boolean
    ) = colorResource(
        id = if (enabled && type == ButtonType.PRIMARY) {
            R.color.primary_normal
        } else {
            R.color.line_normal_neutral
        }
    )
}

@JvmInline
value class WantedButtonBorderCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedButtonBorderLoader> = staticCompositionLocalOf { WantedButtonBorderLoaderImpl() }
) {
    val current: WantedButtonBorderLoader
        @Composable get() = delegate.current

    infix fun provides(value: WantedButtonBorderLoader) = delegate provides value
}
