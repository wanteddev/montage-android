package com.wanted.android.wanted.design.snackbar

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun WantedSnackBar(
    modifier: Modifier = Modifier,
    heading: String = "",
    description: String = "",
    buttonText: String = "",
    isClickOnce: Boolean = true,
    extraContent: @Composable () -> Unit = {},
    buttonClickable: () -> Unit = {}
) {
    WantedSnackBarLayout(
        modifier = modifier.fillMaxWidth(),
        extraContent = extraContent,
        headingSlot = {
            if (heading.isNotEmpty()) {
                Text(
                    text = heading,
                    style = WantedTextStyle(
                        colorRes = R.color.inverse_label,
                        style = DesignSystemTheme.typography.body2Bold
                    ).copy(
                        lineHeightStyle = LineHeightStyle(
                            LineHeightStyle.Alignment.Center,
                            LineHeightStyle.Trim.None
                        )
                    )
                )
            }
        },
        descriptionSlot = {
            if (description.isNotEmpty()) {
                Text(
                    text = description,
                    style = WantedTextStyle(
                        colorRes = R.color.inverse_label,
                        style = DesignSystemTheme.typography.label2Regular
                    ).copy(
                        lineHeightStyle = LineHeightStyle(
                            LineHeightStyle.Alignment.Center,
                            LineHeightStyle.Trim.None
                        )
                    )
                )
            }
        },
        buttonTextSlot = {
            if (buttonText.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .clickable {
                            if (isClickOnce) {
                                buttonClickable.clickOnceForDesignSystem()
                            } else {
                                buttonClickable.invoke()
                            }
                        }
                        .padding(horizontal = 2.dp, vertical = 4.dp),
                    text = buttonText,
                    style = WantedTextStyle(
                        colorRes = R.color.background_normal_normal,
                        style = DesignSystemTheme.typography.body1Bold
                    ).copy(
                        lineHeightStyle = LineHeightStyle(
                            LineHeightStyle.Alignment.Center,
                            LineHeightStyle.Trim.None
                        )
                    )
                )
            }
        }
    )
}


/*
https://www.figma.com/design/MK6KmtXBxX7ZkoQXfD9MFH/%EA%B0%9C%EC%84%A0%3A-Components?node-id=1596-18812&m=dev
 */
@Composable
private fun WantedSnackBarLayout(
    modifier: Modifier = Modifier,
    extraContent: @Composable () -> Unit = {},
    headingSlot: @Composable (() -> Unit) = {},
    descriptionSlot: @Composable (() -> Unit) = {},
    buttonTextSlot: @Composable (() -> Unit) = {}
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(colorResource(id = R.color.background_normal_normal))
            .background(colorResource(id = R.color.inverse_background).copy(0.61f))
            .background(colorResource(id = R.color.primary_normal).copy(0.08f))
            .padding(horizontal = 16.dp, vertical = 11.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 2.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            extraContent()
            Column(
                modifier = Modifier.weight(1f),
            ) {
                headingSlot()
                descriptionSlot()
            }
            buttonTextSlot()
        }
    }
}

@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Composable
fun WantedSnackBarHeadingDescriptionExtraContentPreview() {
    DesignSystemTheme {
        WantedSnackBar(
            heading = "메시지에 마침표를 찍어요.",
            description = "설명은 필요할 때만 써요.",
            buttonText = "텍스트",
            extraContent = {
                Icon(
                    contentDescription = "icon",
                    painter = painterResource(id = R.drawable.ic_normal_eye_fill_svg),
                    modifier = Modifier
                        .size(32.dp),
                    tint = colorResource(id = R.color.design_default_color_error)
                )
            }
        )
    }
}

@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Composable
fun WantedSnackBarDescriptionExtraContentPreview() {
    DesignSystemTheme {
        WantedSnackBar(
            description = "설명은 필요할 때만 써요, 설명은 필요할 때만 써요, 설명은 필요할 때만 써요",
            buttonText = "텍스트",
            extraContent = {
                Icon(
                    contentDescription = "icon",
                    painter = painterResource(id = R.drawable.ic_normal_eye_fill_svg),
                    modifier = Modifier
                        .size(32.dp),
                    tint = colorResource(id = R.color.design_default_color_error)
                )
            }
        )
    }
}

@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Composable
fun WantedSnackBarDescriptionPreview() {
    DesignSystemTheme {
        WantedSnackBar(
            description = "설명은 필요할 때만 써요, 설명은 필요할 때만 써요, 설명은 필요할 때만 써요",
            buttonText = "텍스트"
        )
    }
}

@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Composable
fun WantedSnackBarHeadingPreview() {
    DesignSystemTheme {
        WantedSnackBar(
            heading = "메시지에 마침표를 찍어요.",
            buttonText = "텍스트"
        )
    }
}