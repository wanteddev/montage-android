package com.wanted.android.wanted.design.beta.topbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem

@Composable
fun MainTopAppBarIconButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    tint: Color = colorResource(id = R.color.label_normal),
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .size(44.dp)
            .clip(CircleShape)
            .clickOnceForDesignSystem { onClick() }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = null,
            tint = tint
        )
    }
}
