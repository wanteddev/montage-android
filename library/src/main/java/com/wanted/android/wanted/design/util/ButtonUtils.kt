package com.wanted.android.wanted.design.util

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class ButtonVariant {
    SOLID, OUTLINED, TEXT
}

enum class ButtonStatus {
    ENABLE, DISABLE
}

enum class ButtonType {
    PRIMARY, ASSISTIVE
}

enum class ButtonSize {
    LARGE, MEDIUM, SMALL
}

@Composable
internal fun Modifier.getButtonWidth(buttonWidth: Int): Modifier = this.then(
    when (buttonWidth) {
        -1 -> Modifier.fillMaxWidth()
        -2 -> Modifier.wrapContentWidth(align = Alignment.CenterHorizontally)
        else -> Modifier.width(buttonWidth.dp)
    }
)


@Composable
internal fun Modifier.getTextButtonSize(buttonWidth: Int, buttonHeight: Int): Modifier {
    val widthModifier = getButtonWidth(buttonWidth = buttonWidth)
    val heightModifier = when (buttonHeight) {
        -1 -> Modifier.fillMaxHeight()
        -2 -> Modifier.wrapContentHeight(align = Alignment.CenterVertically)
        else -> Modifier.height(buttonHeight.dp)
    }

    return Modifier
        .then(this)
        .then(widthModifier)
        .then(heightModifier)

}