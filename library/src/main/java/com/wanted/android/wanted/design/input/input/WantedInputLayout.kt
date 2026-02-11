package com.wanted.android.wanted.design.input.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.wanted.android.wanted.design.input.input.WantedInputDefaults.WantedInputSize
import com.wanted.android.wanted.design.input.input.control.WantedCheckBox
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews


@Composable
internal fun WantedInputLayout(
    modifier: Modifier,
    size: WantedInputSize,
    tight: Boolean,
    textStyle: TextStyle,
    leadingIcon: @Composable (() -> Unit)? = null,
    label: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val lineHeight = remember(size, textStyle) {
        max(
            with(density) { textStyle.lineHeight.value.dp },
            if (size == WantedInputSize.Medium) 24.dp else 20.dp
        )
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(if (tight) 10.dp else 8.dp)
    ) {
        leadingIcon?.let {
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .defaultMinSize(minHeight = lineHeight),
                contentAlignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .wrapContentSize(),
                    contentAlignment = Alignment.Center
                ) {
                    leadingIcon()
                }
            }
        }

        Box(
            modifier = Modifier.defaultMinSize(minHeight = lineHeight),
            contentAlignment = Alignment.CenterStart
        ) {
            label()
        }
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
                    size = WantedInputSize.Medium,
                    textStyle = DesignSystemTheme.typography.caption1Medium.copy(
                        DesignSystemTheme.colors.labelAlternative
                    ),
                    leadingIcon = {
                        WantedCheckBox(checked = true, onCheckedChange = {})
                    },
                    tight = false,
                    label = {
                        Text(text = "텍스트 한줄")
                    }
                )

                WantedInputLayout(
                    modifier = Modifier,
                    size = WantedInputSize.Medium,
                    textStyle = DesignSystemTheme.typography.caption1Medium.copy(
                        DesignSystemTheme.colors.labelAlternative
                    ),
                    leadingIcon = {
                        WantedCheckBox(checked = true, onCheckedChange = {})
                    },
                    tight = false,
                    label = {
                        Text(text = "텍스트 \n두줄")
                    }
                )

                WantedInputLayout(
                    modifier = Modifier,
                    size = WantedInputSize.Medium,
                    textStyle = DesignSystemTheme.typography.label1Regular.copy(
                        DesignSystemTheme.colors.labelNormal
                    ),
                    leadingIcon = {
                        WantedCheckBox(checked = true, onCheckedChange = {})
                    },
                    tight = false,
                    label = {
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
                    textStyle = DesignSystemTheme.typography.label1Regular.copy(
                        DesignSystemTheme.colors.labelNormal
                    ),
                    tight = false,
                    label = {
                        Text(text = "텍스트\n텍스트")
                    }
                )

            }
        }
    }
}
