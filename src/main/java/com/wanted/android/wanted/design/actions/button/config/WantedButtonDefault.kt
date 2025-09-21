package com.wanted.android.wanted.design.actions.button.config

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.ButtonVariant


data class WantedButtonDefault(
    val variant: ButtonVariant,
    val type: ButtonType,
    val enabled: Boolean,
    val size: ButtonSize,
    val contentColor: Color,
    val leftIconTintColor: Color,
    val rightIconTintColor: Color,
    val backgroundColor: Color,
    val borderColor: Color,
    val borderShape: RoundedCornerShape,
    val textStyle: TextStyle,
    val loadingSize: Dp,
    val loadingColor: Color
)


object WantedButtonDefaults {
    @Composable
    fun getDefault(
        variant: ButtonVariant = ButtonVariant.SOLID,
        type: ButtonType = ButtonType.PRIMARY,
        enabled: Boolean = true,
        size: ButtonSize = ButtonSize.LARGE,
        contentColor: Color = getContentColor(variant, type, enabled),
        leftIconTintColor: Color = getContentColor(variant, type, enabled),
        rightIconTintColor: Color = getContentColor(variant, type, enabled),
        backgroundColor: Color = getBackgroundColor(variant, type, enabled),
        borderColor: Color = getBorderColor(variant),
        borderShape: RoundedCornerShape = getBorderShape(variant, size),
        textStyle: TextStyle = getTextStyle(variant, type, size),
        loadingSize: Dp = getLoadingSize(size),
        loadingColor: Color = getLoadingColor(variant, type, enabled),
    ) = WantedButtonDefault(
        variant = variant,
        type = type,
        enabled = enabled,
        size = size,
        contentColor = contentColor,
        leftIconTintColor = leftIconTintColor,
        rightIconTintColor = rightIconTintColor,
        backgroundColor = backgroundColor,
        borderColor = borderColor,
        borderShape = borderShape,
        textStyle = textStyle,
        loadingSize = loadingSize,
        loadingColor = loadingColor
    )

    @Composable
    private fun getContentColor(
        variant: ButtonVariant,
        type: ButtonType = ButtonType.PRIMARY,
        enabled: Boolean
    ): Color = LocalWantedButtonContent.current.getContentColor(variant, type, enabled)

    @Composable
    private fun getBackgroundColor(
        variant: ButtonVariant,
        type: ButtonType = ButtonType.PRIMARY,
        enabled: Boolean
    ): Color = LocalWantedButtonContent.current.getBackgroundColor(
        variant = variant,
        type = type,
        enabled = enabled
    )

    @Composable
    private fun getBorderColor(
        variant: ButtonVariant
    ): Color = LocalWantedButtonBorder.current.getBorderColor(variant = variant)

    @Composable
    private fun getBorderShape(
        variant: ButtonVariant,
        size: ButtonSize = ButtonSize.LARGE
    ): RoundedCornerShape = LocalWantedButtonBorder.current.getBorderShape(variant, size)

    @Composable
    private fun getTextStyle(
        variant: ButtonVariant,
        type: ButtonType = ButtonType.PRIMARY,
        size: ButtonSize = ButtonSize.LARGE
    ): TextStyle = LocalWantedButtonTextStyle.current.getTextStyle(
        variant = variant,
        type = type,
        size = size
    )

    @Composable
    private fun getLoadingSize(
        size: ButtonSize = ButtonSize.LARGE
    ): Dp = LocalWantedButtonLoading.current.getLoadingSize(size)

    @Composable
    private fun getLoadingColor(
        variant: ButtonVariant,
        type: ButtonType = ButtonType.PRIMARY,
        enabled: Boolean = true
    ): Color = LocalWantedButtonLoading.current.getLoadingColor(variant, type, enabled)
}
