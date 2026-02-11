package com.wanted.android.wanted.design.input.textinput.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme


@Composable
internal fun ComponentTitle(
    title: String,
    isRequiredBadge: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f, fill = false),
            text = title,
            style = DesignSystemTheme.typography.label1Bold,
            color = DesignSystemTheme.colors.labelNeutral,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        if (isRequiredBadge) {
            Text(
                modifier = Modifier.wrapContentSize(),
                text = "*",
                style = DesignSystemTheme.typography.label1Medium,
                color = DesignSystemTheme.colors.statusNegative,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
