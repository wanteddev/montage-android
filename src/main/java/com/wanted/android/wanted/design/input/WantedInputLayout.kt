package com.wanted.android.wanted.design.input

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.element.WantedCheckBox
import com.wanted.android.wanted.design.input.WantedInputContract.WantedInputSize
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
internal fun WantedInputLayout(
    modifier: Modifier,
    size: WantedInputSize,
    bold: Boolean,
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
                modifier = Modifier.size(if (size == WantedInputSize.Normal) 24.dp else 20.dp),
                contentAlignment = Alignment.Center
            ) {
                leadingIcon()
            }
        }

        ProvideTextStyle(
            value = WantedTextStyle(
                colorRes = R.color.label_normal,
                style = if (size == WantedInputSize.Normal) {
                    if (bold) {
                        DesignSystemTheme.typography.body2Bold
                    } else {
                        DesignSystemTheme.typography.body2Regular
                    }

                } else {
                    if (bold) {
                        DesignSystemTheme.typography.label1Bold
                    } else {
                        DesignSystemTheme.typography.label1Regular
                    }
                }
            )
        ) {
            text()
        }
    }
}

@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Preview(
    "foldableLight",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "ko",
    device = Devices.FOLDABLE
)
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
                    bold = false,
                    leadingIcon = {
                        WantedCheckBox(checked = true, onCheckedChange = {})
                    },
                    text = {
                        Text(text = "텍스트\n텍스트")
                    }
                )
                WantedInputLayout(
                    modifier = Modifier,
                    size = WantedInputSize.Normal,
                    bold = true,
                    leadingIcon = {
                        WantedCheckBox(checked = true, onCheckedChange = {})
                    },
                    text = {
                        Text(text = "텍스트\n텍스트")
                    }
                )

                WantedInputLayout(
                    modifier = Modifier,
                    size = WantedInputSize.Small,
                    bold = false,
                    leadingIcon = {
                        WantedCheckBox(checked = true, onCheckedChange = {})
                    },
                    text = {
                        Text(text = "텍스트\n텍스트")
                    }
                )

                WantedInputLayout(
                    modifier = Modifier,
                    size = WantedInputSize.Small,
                    bold = true,
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
