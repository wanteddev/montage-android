package com.wanted.android.wanted.design.beta.topbar

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.button.clickOnce

@Composable
fun TopAppBarIconButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    tint: Color = colorResource(id = R.color.label_normal),
    onClick: () -> Unit
) {

    IconButton(
        modifier = modifier.defaultMinSize(56.dp),
        enabled = enabled,
        interactionSource = interactionSource,
        onClick = { onClick.clickOnce() }
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painter,
            contentDescription = null,
            tint = tint
        )
    }
}
