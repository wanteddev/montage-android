package com.wanted.android.wanted.design.textfield.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
internal fun WantedTextFieldLayout(
    modifier: Modifier,
    title: @Composable (() -> Unit)? = null,
    textField: @Composable () -> Unit,
    message: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        title?.invoke()

        textField()

        message?.invoke()
    }
}

