package com.wanted.android.wanted.design.input.textinput.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
internal fun WantedTextAreaLayout(
    modifier: Modifier = Modifier,
    leftContent: @Composable (() -> Unit)? = null,
    rightContent: @Composable (() -> Unit)? = null,
    textField: @Composable () -> Unit
) {

    Column(
        modifier = modifier,
    ) {

        textField()

        if (leftContent != null || rightContent != null) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                leftContent?.let {
                    Box(modifier = Modifier.wrapContentSize()) {
                        leftContent()
                    }
                }


                Spacer(Modifier.weight(1f))

                rightContent?.let {
                    Box(modifier = Modifier.wrapContentSize()) {
                        rightContent()
                    }
                }
            }
        }
    }
}



