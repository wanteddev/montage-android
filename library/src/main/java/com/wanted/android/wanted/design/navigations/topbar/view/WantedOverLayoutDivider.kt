package com.wanted.android.wanted.design.navigations.topbar.view

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.colorResource
import com.wanted.android.designsystem.R


@Composable
internal fun WantedOverLayoutDivider(
    modifier: Modifier = Modifier,
    color: Color = colorResource(R.color.line_normal_neutral)
) {
    Layout(
        modifier = modifier,
        content = {
            HorizontalDivider(
                color = color
            )
        }
    ) { measurables, constraints ->
        val textPlaceable = measurables[0].measure(constraints)

        // Calculate the expanded dimensions
        val expandedHeight = textPlaceable.height

        layout(textPlaceable.width, expandedHeight) {
            textPlaceable.placeRelative(
                x = 0,
                y = textPlaceable.height
            )
        }
    }
}