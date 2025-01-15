package com.wanted.android.wanted.design.accordion

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedTouchArea

@Composable
internal fun WantedAccordionTrailArrowIcon(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    tint: Color,
    onClick: () -> Unit
) {
    WantedTouchArea(
        modifier = modifier.zIndex(100f),
        horizontalPadding = 12.dp,
        verticalPadding = 12.dp,
        shape = CircleShape,
        content = {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = if (isExpanded) {
                    painterResource(R.drawable.ic_normal_chevron_up_svg)
                } else {
                    painterResource(R.drawable.ic_normal_chevron_down_svg)
                },
                tint = tint,
                contentDescription = ""
            )
        },
        onClick = onClick
    )
}