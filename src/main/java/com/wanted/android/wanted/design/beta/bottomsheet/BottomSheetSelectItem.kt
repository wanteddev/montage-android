package com.wanted.android.wanted.design.beta.bottomsheet

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.element.WantedRadioButton
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
                colorRes = if (isSelect) {
                    R.color.primary_normal
                } else {
                    R.color.label_strong
                },
                style = DesignSystemTheme.typography.label1Bold
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

@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Preview(
    "foldableLight",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "ko",
    device = Devices.FOLDABLE
)
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