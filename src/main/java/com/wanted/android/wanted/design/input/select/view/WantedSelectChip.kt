package com.wanted.android.wanted.design.input.select.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.actions.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_5
import com.wanted.android.wanted.design.util.WantedTextStyle

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=16370-41464&m=dev
 * 설명 : https://www.figma.com/design/MK6KmtXBxX7ZkoQXfD9MFH/%EA%B0%9C%EC%84%A0%3A-Components?node-id=1934-43909&t=33KjAy2RlyzyhLH6-4
 */


@Composable
internal fun WantedSelectChip(
    modifier: Modifier = Modifier,
    text: String,
    enable: Boolean = true,
    error: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit
) {
    WantedSelectChipLayout(
        modifier = modifier
            .background(
                colorResource(
                    id = when {
                        !enable -> R.color.fill_alternative
                        error -> R.color.status_negative
                        else -> R.color.fill_alternative
                    }
                ).copy(alpha = OPACITY_5)
            )
            .clickOnceForDesignSystem { onClick() },
        leadingIcon = leadingIcon,
        text = {
            Text(
                text = text,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = WantedTextStyle(
                    colorRes = when {
                        !enable -> R.color.label_disable
                        error -> R.color.status_negative
                        else -> R.color.label_normal
                    },
                    style = DesignSystemTheme.typography.caption1Medium
                )
            )
        },
        trailingIcon = {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.ic_normal_close_thick_svg),
                tint = colorResource(
                    id = when {
                        !enable -> R.color.label_disable
                        error -> R.color.status_negative
                        else -> R.color.label_alternative
                    }
                ),
                contentDescription = ""
            )
        }
    )
}

@Composable
private fun WantedSelectChipLayout(
    modifier: Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    text: @Composable () -> Unit,
    trailingIcon: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .then(modifier)
            .padding(vertical = 4.dp, horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        leadingIcon?.let {
            Box(
                modifier = Modifier.size(12.dp),
                contentAlignment = Alignment.Center
            ) {
                leadingIcon()
            }
        }

        text()

        Box(
            modifier = Modifier.size(12.dp),
            contentAlignment = Alignment.Center
        ) {
            trailingIcon()
        }
    }
}

@DevicePreviews
@Composable
private fun WantedSelectChipPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {

                WantedSelectChip(
                    text = "선택1",
                    onClick = { }
                )

                WantedSelectChip(
                    text = "선택1",
                    enable = false,
                    onClick = { }
                )


                WantedSelectChip(
                    text = "선택1",
                    error = true,
                    onClick = { }
                )


                WantedSelectChip(
                    text = "선택1",
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = R.drawable.ic_normal_circle_exclamation_fill_svg),
                            tint = colorResource(id = R.color.status_cautionary),
                            contentDescription = ""
                        )
                    },
                    onClick = { }
                )
            }
        }
    }
}
