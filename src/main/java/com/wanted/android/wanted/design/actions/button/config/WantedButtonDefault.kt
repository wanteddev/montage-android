package com.wanted.android.wanted.design.actions.button.config

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType


data class WantedButtonDefault(
    val shape: ButtonShape,
    val type: ButtonType,
    val enabled: Boolean,
    val size: ButtonSize,
    val contentColor: Color,
    val backgroundColor: Color,
    val borderColor: Color,
    val textStyle: TextStyle
)


object WantedButtonDefaults {
    @Composable
    fun getDefault(
        shape: ButtonShape = ButtonShape.SOLID,
        type: ButtonType = ButtonType.PRIMARY ,
        enabled: Boolean = true,
        size: ButtonSize = ButtonSize.LARGE,
        contentColor: Color = getContentColor(shape, type, enabled),
        backgroundColor: Color = getBackgroundColor(shape, type, enabled),
        borderColor: Color = getBorderColor(shape, type, enabled),
        textStyle: TextStyle = getTextStyle(shape, type, size)
    ) = WantedButtonDefault(
        shape = shape,
        type = type,
        enabled = enabled,
        size = size,
        contentColor = contentColor,
        backgroundColor = backgroundColor,
        borderColor = borderColor,
        textStyle = textStyle
    )

    @Composable
    private fun getContentColor(
        shape: ButtonShape,
        type: ButtonType = ButtonType.PRIMARY,
        enabled: Boolean
    ): Color {
        return LocalWantedButtonContent.current.getContentColor(shape, type, enabled)
    }

    @Composable
    private fun getBackgroundColor(
        shape: ButtonShape,
        type: ButtonType = ButtonType.PRIMARY,
        enabled: Boolean
    ): Color {
        return LocalWantedButtonBackground.current.getBackgroundColor(
            shape = shape,
            type = type,
            enabled = enabled
        )
    }

    @Composable
    private fun getBorderColor(
        shape: ButtonShape,
        type: ButtonType = ButtonType.PRIMARY,
        enabled: Boolean
    ): Color {
        return LocalWantedButtonBorder.current.getBorderColor(
            shape = shape,
            type = type,
            enabled = enabled
        )
    }

    @Composable
    private fun getTextStyle(
        shape: ButtonShape,
        type: ButtonType = ButtonType.PRIMARY,
        size: ButtonSize = ButtonSize.LARGE
    ): TextStyle {
        return LocalWantedButtonTextStyle.current.getTextStyle(
            shape = shape,
            type = type,
            size = size
        )
    }
}




