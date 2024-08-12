package com.wanted.android.wanted.design.textfield.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
internal fun WantedTextAreaCount(
    modifier: Modifier = Modifier,
    current: Int,
    maxWordCount: Int
) {
    Row(
        modifier = modifier.padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$current",
            style = WantedTextStyle(
                colorRes = R.color.label_alternative,
                style = DesignSystemTheme.typography.label2Medium
            )
        )

        Text(
            text = "/$maxWordCount",
            style = WantedTextStyle(
                colorRes = R.color.label_assistive,
                style = DesignSystemTheme.typography.label2Medium
            )
        )
    }
}