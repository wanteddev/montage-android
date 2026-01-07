package com.wanted.android.wanted.design.input.textinput.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_74

@Composable
fun WantedTextAreaCharacterCount(
    current: Int,
    maxWordCount: Int,
    modifier: Modifier = Modifier,
    error: Boolean = false,
    enable: Boolean = true
) {
    Row(
        modifier = modifier.padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.alpha(OPACITY_74),
            text = "$current",
            style = DesignSystemTheme.typography.label2Medium,
            color = when {
                !enable -> DesignSystemTheme.colors.labelDisable
                error -> DesignSystemTheme.colors.statusNegative
                else -> DesignSystemTheme.colors.labelAlternative
            }
        )

        Text(
            modifier = Modifier.alpha(OPACITY_74),
            text = "/$maxWordCount",
            style = DesignSystemTheme.typography.label2Medium,
            color = when {
                !enable -> DesignSystemTheme.colors.labelDisable
                else -> DesignSystemTheme.colors.labelAlternative
            }
        )
    }
}