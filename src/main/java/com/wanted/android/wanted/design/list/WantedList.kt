package com.wanted.android.wanted.design.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.chip.WantedActionChip
import com.wanted.android.wanted.design.element.WantedRadioButton
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=14854-45066&m=dev
 */
@Composable
fun WantedList(
    modifier: Modifier = Modifier,
    text: String,
    caption: String = "",
    isEnable: Boolean = true,
    bold: Boolean = false,
    leftContent: (@Composable () -> Unit)? = null,
    rightContent: (@Composable () -> Unit)? = null
) {
    WantedListLayout(
        modifier = modifier.fillMaxWidth(),
        text = {
            Text(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = WantedTextStyle(
                    colorRes = if (isEnable) {
                        R.color.label_normal
                    } else {
                        R.color.label_disable
                    },
                    style = if (bold) {
                        DesignSystemTheme.typography.body1Medium
                    } else {
                        DesignSystemTheme.typography.body1Regular
                    }
                )
            )
        },
        caption = if (caption.isNotEmpty()) {
            {
                Text(
                    text = caption,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = WantedTextStyle(
                        colorRes = if (isEnable) {
                            R.color.label_alternative
                        } else {
                            R.color.label_disable
                        },
                        style = DesignSystemTheme.typography.label2Regular
                    )
                )
            }
        } else null,
        leftContent = leftContent,
        rightContent = rightContent
    )

}

@Composable
private fun WantedListLayout(
    modifier: Modifier = Modifier,
    text: @Composable () -> Unit,
    caption: @Composable (() -> Unit)?,
    leftContent: (@Composable () -> Unit)?,
    rightContent: (@Composable () -> Unit)?,
) {
    Row(
        modifier = Modifier.then(modifier),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Top
    ) {

        leftContent?.let {
            Box(modifier = Modifier.size(24.dp)) {
                leftContent()
            }
        }

        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            text()

            caption?.invoke()
        }

        rightContent?.invoke()
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
private fun WantedListPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedList(
                    text = "텍스트"
                )

                WantedList(
                    text = "텍스트",
                    caption = "캡션"
                )

                WantedList(
                    text = "텍스트",
                    caption = "캡션",
                    isEnable = false,
                )

                WantedList(
                    text = "텍스트",
                    leftContent = {
                        WantedRadioButton(checked = true, onCheckedChange = {})
                    }
                )

                WantedList(
                    text = "텍스트",
                    caption = "캡션",
                    leftContent = {
                        WantedRadioButton(checked = true, onCheckedChange = {})
                    }
                )

                WantedList(
                    text = "텍스트",
                    rightContent = {
                        WantedActionChip(text = "Chip")
                    }
                )

                WantedList(
                    text = "텍스트",
                    caption = "캡션",
                    rightContent = {
                        WantedActionChip(text = "Chip")
                    }
                )
            }
        }
    }
}