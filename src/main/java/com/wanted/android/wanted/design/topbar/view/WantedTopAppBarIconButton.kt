package com.wanted.android.wanted.design.topbar.view

import androidx.compose.foundation.interaction.MutableInteractionSource
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
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.topbar.WantedTopAppBarContract.TopAppBarType

@Composable
fun WantedTopAppBarIconButton(
    modifier: Modifier = Modifier,
    type: TopAppBarType = TopAppBarType.Normal,
    painter: Painter,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    tint: Color = colorResource(id = R.color.label_normal),
    onClick: () -> Unit
) {

    /**
     * 시스템에 정의되어 있는 IconButton의 default size 56.dp
     * size를 40으로 줄이면 ripple 효과만 56.dp 로 보인다.
     */
    IconButton(
        modifier = modifier
            .size(40.dp),
        enabled = enabled,
        interactionSource = interactionSource,
        onClick = { onClick.clickOnceForDesignSystem() }
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painter,
            contentDescription = null,
            tint = tint
        )
    }
}
