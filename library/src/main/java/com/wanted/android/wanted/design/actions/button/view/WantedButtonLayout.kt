package com.wanted.android.wanted.design.actions.button.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
internal fun WantedButtonLayout(
    modifier: Modifier,
    horizontalArrangement: Arrangement.Horizontal,
    text: @Composable (() -> Unit)? = null,
    leftDrawable: @Composable (() -> Unit)? = null,
    rightDrawable: @Composable (() -> Unit)? = null,
    loading: @Composable (() -> Unit)? = null
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = Alignment.CenterVertically
        ) {
            leftDrawable?.let {
                leftDrawable()
            }

            text?.let {
                Box(
                    Modifier.wrapContentHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    text()
                }
            }

            rightDrawable?.let {
                rightDrawable()
            }
        }

        loading?.invoke()
    }
}

