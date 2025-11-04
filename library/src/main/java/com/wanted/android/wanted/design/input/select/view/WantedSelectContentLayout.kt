package com.wanted.android.wanted.design.input.select.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
internal fun WantedSelectContentLayout(
    modifier: Modifier = Modifier,
    contents: @Composable () -> Unit,
    rightButton: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        leadingIcon?.let {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .padding(1.dp),
                contentAlignment = Alignment.Center
            ) {
                leadingIcon()
            }
        }

        Box(
            modifier = Modifier
                .weight(weight = 1f, fill = false),
            contentAlignment = Alignment.CenterStart
        ) {
            contents()
        }

        trailingIcon?.let {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .padding(1.dp),
                contentAlignment = Alignment.Center
            ) {
                trailingIcon()
            }
        }

        Box(
            modifier = Modifier
                .align(alignment = Alignment.Top)
                .size(24.dp)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            rightButton()
        }
    }
}
