package com.wanted.android.wanted.design.input.select.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
internal fun WantedSelectPlaceHolder(
    modifier: Modifier = Modifier,
    placeHolder: String = "",
    enabled: Boolean,
) {
    Text(
        modifier = modifier.padding(horizontal = 4.dp),
        text = placeHolder,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = WantedTextStyle(
            colorRes = if (enabled) {
                R.color.label_alternative
            } else {
                R.color.label_disable
            },
            style = DesignSystemTheme.typography.body1Regular
        )
    )
}
