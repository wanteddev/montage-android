package com.wanted.android.wanted.design.beta.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.actions.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.input.control.WantedRadioButton
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun BottomSheetSelectItem(
    modifier: Modifier = Modifier,
    text: String,
    isSelect: Boolean,
    onClick: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickOnceForDesignSystem { onClick() }
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
                colorRes = R.color.label_normal,
                style = if (isSelect) {
                    DesignSystemTheme.typography.body1Bold
                } else {
                    DesignSystemTheme.typography.body1Medium
                }
            )
        )

        WantedRadioButton(
            checked = isSelect,
            onCheckedChange = {
                onClick()
            }
        )
    }
}

@DevicePreviews
@Composable
private fun OnboardingBottomSheetItemPreview() {
    DesignSystemTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                BottomSheetSelectItem(
                    text = "OnboardingBottomSheetItem",
                    isSelect = true,
                    onClick = {}
                )

                BottomSheetSelectItem(
                    text = "OnboardingBottomSheetItem",
                    isSelect = false,
                    onClick = {}
                )
            }
        }
    }
}