package com.wanted.android.wanted.design.input.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.input.control.WantedCheckBox
import com.wanted.android.wanted.design.input.input.WantedInputContract.WantedInputSize
import com.wanted.android.wanted.design.theme.DesignSystemTheme


@Composable
internal fun WantedInputLayout(
    modifier: Modifier,
    size: WantedInputSize,
    leadingIcon: @Composable (() -> Unit)? = null,
    text: @Composable () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        leadingIcon?.let {
            Box(
                modifier = Modifier.wrapContentSize(),
                contentAlignment = Alignment.TopStart
            ) {
                Text(
                    modifier = Modifier
                        .width(0.dp)
                        .wrapContentHeight(),
                    text = ""
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(if (size == WantedInputSize.Normal) 24.dp else 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    leadingIcon()
                }
            }
        }

        text()
    }
}

@DevicePreviews
@Composable
private fun WantedInputLayoutPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedInputLayout(
                    modifier = Modifier,
                    size = WantedInputSize.Normal,
                    leadingIcon = {
                        WantedCheckBox(checked = true, onCheckedChange = {})
                    },
                    text = {
                        Text(
                            text = "텍스트\n텍스트"
                        )
                    }
                )

                WantedInputLayout(
                    modifier = Modifier,
                    size = WantedInputSize.Small,
                    leadingIcon = {
                        WantedCheckBox(checked = true, onCheckedChange = {})
                    },
                    text = {
                        Text(text = "텍스트\n텍스트")
                    }
                )

            }
        }
    }
}
