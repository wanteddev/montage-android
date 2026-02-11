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
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_5
import com.wanted.android.wanted.design.util.clickOnce

@Composable
internal fun WantedSelectChip(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    error: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null
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
            .clickOnce { onClick() },
        leadingIcon = leadingIcon,
        text = {
            Text(
                text = text,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = DesignSystemTheme.typography.caption1Medium,
                color =  when {
                    !enable -> DesignSystemTheme.colors.labelDisable
                    error -> DesignSystemTheme.colors.statusNegative
                    else -> DesignSystemTheme.colors.labelAlternative
                }
            )
        },
        trailingIcon = {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.icon_normal_close_thick),
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
                            painter = painterResource(id = R.drawable.icon_normal_circle_exclamation_fill),
                            tint = DesignSystemTheme.colors.statusCautionary,
                            contentDescription = ""
                        )
                    },
                    onClick = { }
                )
            }
        }
    }
}
