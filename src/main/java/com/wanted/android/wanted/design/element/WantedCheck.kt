package com.wanted.android.wanted.design.element

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_43


@Composable
internal fun WantedCheck(
    modifier: Modifier,
    size: CheckBoxSize,
    checked: Boolean,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onCheckedChange: ((Boolean) -> Unit)
) {
    WantedTouchArea(
        modifier = modifier,
        enabled = enabled,
        shape = CircleShape,
        horizontalPadding = 4.dp,
        verticalPadding = 4.dp,
        interactionSource = interactionSource,
        content = {
            Image(
                modifier = Modifier
                    .size(if (size == CheckBoxSize.Small) 20.dp else 24.dp)
                    .padding(3.dp),
                painter = painterResource(R.drawable.icon_normal_check_thick),
                contentDescription = "checkBox_check",
                colorFilter = ColorFilter.tint(
                    color = if (checked) {
                        if (enabled) {
                            colorResource(id = R.color.primary_normal)
                        } else {
                            colorResource(id = R.color.primary_normal).copy(OPACITY_43)
                        }
                    } else {
                        if (enabled) {
                            colorResource(id = R.color.label_assistive)
                        } else {
                            colorResource(id = R.color.label_assistive).copy(0.13f)
                        }
                    }
                )
            )
        }
    ) {
        onCheckedChange(!checked)
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
private fun WantedCheckPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedCheck(
                    modifier = Modifier,
                    size = CheckBoxSize.Normal,
                    checked = false,
                    onCheckedChange = {}
                )

                WantedCheck(
                    modifier = Modifier,
                    size = CheckBoxSize.Normal,
                    checked = false,
                    enabled = false,
                    onCheckedChange = {}
                )

                WantedCheck(
                    modifier = Modifier,
                    size = CheckBoxSize.Normal,
                    checked = true,
                    onCheckedChange = {}
                )

                WantedCheck(
                    modifier = Modifier,
                    size = CheckBoxSize.Normal,
                    checked = true,
                    enabled = false,
                    onCheckedChange = {}
                )
            }
        }
    }
}