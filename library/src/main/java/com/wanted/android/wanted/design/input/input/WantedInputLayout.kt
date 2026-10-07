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
    label: @Composable (() -> Unit)?
) {
    // 라벨이 없으면 leading 만 영역을 잡는다. 빈 라벨을 배치하면 그 앞 간격(8·10dp)과
    // 라벨 줄 높이 기준 최소 높이가 남아, ListCell 슬롯 등에 넣었을 때 보이지 않는 여백이 생긴다.
    if (label == null) {
        Box(modifier = modifier) {
            leadingIcon?.invoke()
        }
        return
    }

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
                        DesignSystemTheme.colors.foregroundNeutralTertiary
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
                        DesignSystemTheme.colors.foregroundNeutralTertiary
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
                        DesignSystemTheme.colors.foregroundNeutralPrimary
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
                        DesignSystemTheme.colors.foregroundNeutralPrimary
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
