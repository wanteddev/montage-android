package com.wanted.android.wanted.design.snackbar

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle


/**
 * https://www.figma.com/design/MK6KmtXBxX7ZkoQXfD9MFH/%EA%B0%9C%EC%84%A0%3A-Components?node-id=1596-18812&m=dev
 */
@Composable
fun WantedSnackBar(
    modifier: Modifier = Modifier,
    text: String = "",
    description: String = "",
    buttonText: String,
    icon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit
) {
    WantedSnackBarLayout(
        modifier = modifier,
        icon = icon,
        headingSlot = {
            if (text.isNotEmpty()) {
                Text(
                    text = text,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        },
        descriptionSlot = {
            if (description.isNotEmpty()) {
                Text(
                    text = description,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
        },
        buttonTextSlot = {
            if (buttonText.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .defaultMinSize(minWidth = 54.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickOnceForDesignSystem { onClick() }
                        .padding(vertical = 4.dp, horizontal = 7.dp),

                    text = buttonText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }
    )
}


@Composable
private fun WantedSnackBarLayout(
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    headingSlot: @Composable (() -> Unit) = {},
    descriptionSlot: @Composable (() -> Unit) = {},
    buttonTextSlot: @Composable (() -> Unit) = {}
) {
    Row(
        modifier = modifier
            .padding(20.dp)
            .wrapContentHeight()
            .widthIn(max = 360.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(colorResource(id = R.color.background_normal_normal))
            .background(colorResource(id = R.color.inverse_background).copy(0.52f))
            .background(colorResource(id = R.color.primary_normal).copy(0.05f))
            .padding(start = 16.dp, end = 11.dp)
            .padding(vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Box(
                modifier = Modifier.size(32.dp),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 2.dp, vertical = 5.dp),
        ) {
            ProvideTextStyle(
                value = WantedTextStyle(
                    colorRes = R.color.static_white,
                    style = DesignSystemTheme.typography.body2Bold
                )
            ) {
                headingSlot()
            }

            ProvideTextStyle(
                value = WantedTextStyle(
                    colorRes = R.color.static_white,
                    style = DesignSystemTheme.typography.label2Regular
                )
            ) {
                descriptionSlot()
            }
        }

        Spacer(modifier = Modifier.size(7.dp))

        ProvideTextStyle(
            value = WantedTextStyle(
                colorRes = R.color.static_white,
                style = DesignSystemTheme.typography.body2Bold
            )
        ) {
            buttonTextSlot()
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
fun WantedSnackBarDescriptionExtraContentPreview() {
    DesignSystemTheme {
        Surface(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                WantedSnackBar(
                    text = "메시지에 마침표를 찍어요.",
                    description = "설명은 필요할 때만 써요.",
                    buttonText = "텍스트",
                    icon = {
                        Icon(
                            contentDescription = "icon",
                            painter = painterResource(id = R.drawable.ic_normal_eye_fill_svg),
                            modifier = Modifier
                                .size(32.dp),
                            tint = colorResource(id = R.color.design_default_color_error)
                        )
                    },
                    onClick = {}
                )

                WantedSnackBar(
                    description = "설명은 필요할 때만 써요, 설명은 필요할 때만 써요, 설명은 필요할 때만 써요",
                    buttonText = "텍스트",
                    icon = {
                        Icon(
                            contentDescription = "icon",
                            painter = painterResource(id = R.drawable.ic_normal_eye_fill_svg),
                            modifier = Modifier.fillMaxSize(),
                            tint = colorResource(id = R.color.design_default_color_error)
                        )
                    },
                    onClick = {}
                )

                WantedSnackBar(
                    description = "설명은 필요할 때만 써요, 설명은 필요할 때만 써요, 설명은 필요할 때만 써요",
                    buttonText = "텍스트",
                    onClick = {}
                )

                WantedSnackBar(
                    text = "메시지에 마침표를 찍어요.",
                    buttonText = "텍스트",
                    onClick = {}
                )
            }
        }
    }
}