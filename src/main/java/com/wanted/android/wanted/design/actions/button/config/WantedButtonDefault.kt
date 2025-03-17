package com.wanted.android.wanted.design.actions.button.config

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType


data class WantedButtonDefault(
    val shape: ButtonShape,
    val type: ButtonType,
    val enabled: Boolean,
    val size: ButtonSize,
    val contentColor: Color,
    val leftIconTintColor: Color,
    val rightIconTintColor: Color,
    val backgroundColor: Color,
    val borderColor: Color,
    val textStyle: TextStyle,
    val loadingSize: Dp,
    val loadingColor: Color
)


object WantedButtonDefaults {
    @Composable
    fun getDefault(
        shape: ButtonShape = ButtonShape.SOLID,
        type: ButtonType = ButtonType.PRIMARY,
        enabled: Boolean = true,
        size: ButtonSize = ButtonSize.LARGE,
        contentColor: Color = getContentColor(shape, type, enabled),
        leftIconTintColor: Color = getContentColor(shape, type, enabled),
        rightIconTintColor: Color = getContentColor(shape, type, enabled),
        backgroundColor: Color = getBackgroundColor(shape, type, enabled),
        borderColor: Color = getBorderColor(shape, type, enabled),
        textStyle: TextStyle = getTextStyle(shape, type, size),
        loadingSize: Dp = getLoadingSize(size),
        loadingColor: Color = getLoadingColor(shape, type, enabled),
    ) = WantedButtonDefault(
        shape = shape,
        type = type,
        enabled = enabled,
        size = size,
        contentColor = contentColor,
        leftIconTintColor = leftIconTintColor,
        rightIconTintColor = rightIconTintColor,
        backgroundColor = backgroundColor,
        borderColor = borderColor,
        textStyle = textStyle,
        loadingSize = loadingSize,
        loadingColor = loadingColor
    )

    @Composable
    private fun getContentColor(
        shape: ButtonShape,
        type: ButtonType = ButtonType.PRIMARY,
        enabled: Boolean
    ): Color = LocalWantedButtonContent.current.getContentColor(shape, type, enabled)

    @Composable
    private fun getBackgroundColor(
        shape: ButtonShape,
        type: ButtonType = ButtonType.PRIMARY,
        enabled: Boolean
    ): Color = LocalWantedButtonBackground.current.getBackgroundColor(
        shape = shape,
        type = type,
        enabled = enabled
    )

    @Composable
    private fun getBorderColor(
        shape: ButtonShape,
        type: ButtonType = ButtonType.PRIMARY,
        enabled: Boolean
    ): Color = LocalWantedButtonBorder.current.getBorderColor(
        shape = shape,
        type = type,
        enabled = enabled
    )

    @Composable
    private fun getTextStyle(
        shape: ButtonShape,
        type: ButtonType = ButtonType.PRIMARY,
        size: ButtonSize = ButtonSize.LARGE
    ): TextStyle = LocalWantedButtonTextStyle.current.getTextStyle(
        shape = shape,
        type = type,
        size = size
    )

    @Composable
    private fun getLoadingSize(
        size: ButtonSize = ButtonSize.LARGE
    ): Dp = LocalWantedButtonLoading.current.getLoadingSize(size)

    @Composable
    private fun getLoadingColor(
        shape: ButtonShape,
        type: ButtonType = ButtonType.PRIMARY,
        enabled: Boolean = true
    ): Color = LocalWantedButtonLoading.current.getLoadingColor(shape, type, enabled)
}
