package com.wanted.android.wanted.design.beta.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.clickOnce
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun BottomSheetMultiSelectItem(
    modifier: Modifier = Modifier,
    text: String,
    isSelect: Boolean,
    onClick: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickOnce { onClick() }
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
                .padding(vertical = 12.dp),
            text = text,
            style = WantedTextStyle(
                colorRes = if (isSelect) {
                    R.color.label_strong
                } else {
                    R.color.label_neutral
                },
                style = if (isSelect) {
                    DesignSystemTheme.typography.body1Bold
                } else {
                    DesignSystemTheme.typography.body1Medium
                }
            )
        )


        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .background(
                    color = if (isSelect) {
                        colorResource(id = R.color.primary_normal)
                    } else {
                        colorResource(id = R.color.transparent)
                    },
                    shape = CircleShape
                )
                .border(
                    shape = CircleShape,
                    width = 1.5.dp,
                    color = if (isSelect) {
                        colorResource(id = R.color.transparent)
                    } else {
                        colorResource(id = R.color.line_normal_neutral)
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isSelect) {
                Icon(
                    modifier = Modifier
                        .size(16.dp),
                    painter = painterResource(id = R.drawable.icon_normal_check),
                    contentDescription = "",
                    tint = colorResource(id = R.color.static_white)
                )
            }

        }
    }
}

@DevicePreviews
@Composable
private fun OnboardingBottomSheetMultiSelectItemPreview() {
    DesignSystemTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                BottomSheetMultiSelectItem(
                    text = "OnboardingBottomSheetItem",
                    isSelect = true,
                    onClick = {}
                )

                BottomSheetMultiSelectItem(
                    text = "OnboardingBottomSheetItem",
                    isSelect = false,
                    onClick = {}
                )
            }
        }
    }
}