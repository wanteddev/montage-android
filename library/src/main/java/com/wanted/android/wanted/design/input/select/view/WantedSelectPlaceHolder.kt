package com.wanted.android.wanted.design.input.select.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme

@Composable
internal fun WantedSelectPlaceHolder(
    enabled: Boolean,
    modifier: Modifier = Modifier,
    placeHolder: String = ""
) {
    Text(
        modifier = modifier.padding(horizontal = 4.dp),
        text = placeHolder,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = DesignSystemTheme.typography.body1Regular,
        color = if (enabled) {
            DesignSystemTheme.colors.labelAssistive
        } else {
            DesignSystemTheme.colors.labelDisable
        }
    )
}
