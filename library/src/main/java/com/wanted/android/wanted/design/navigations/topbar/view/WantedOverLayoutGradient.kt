package com.wanted.android.wanted.design.navigations.topbar.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme


@Composable
internal fun WantedOverLayoutGradient(
    modifier: Modifier = Modifier,
    color: Color = DesignSystemTheme.colors.lineNeutralSecondary
) {
    Layout(
        modifier = modifier,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                color,
                                Color.Transparent
                            )
                        )
                    )
            )
        }
    ) { measurables, constraints ->
        val placeable = measurables[0].measure(constraints)

        val expandedHeight = placeable.height

        layout(placeable.width, expandedHeight) {
            placeable.placeRelative(
                x = 0,
                y = placeable.height
            )
        }
    }
}