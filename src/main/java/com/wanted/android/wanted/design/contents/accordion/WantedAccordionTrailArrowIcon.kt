package com.wanted.android.wanted.design.contents.accordion

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R

@Composable
internal fun WantedAccordionTrailArrowIcon(
    isExpanded: Boolean,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Icon(
        modifier = modifier
            .clip(CircleShape)
            .size(20.dp),
        painter = if (isExpanded) {
            painterResource(R.drawable.icon_normal_chevron_up)
        } else {
            painterResource(R.drawable.icon_normal_chevron_down)
        },
        tint = tint,
        contentDescription = ""
    )
}