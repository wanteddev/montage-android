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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.chip.WantedActionChip
import com.wanted.android.wanted.design.element.WantedRadioButton
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle
import com.wanted.android.wanted.design.util.toAnnotatedString

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=14854-45066&m=dev
 */

@Composable
fun WantedCellImpl(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    caption: AnnotatedString = AnnotatedString(""),
    isEnable: Boolean = true,
    isActive: Boolean = false,
    ellipsis: Boolean = true,
    chevrons: Boolean = false,
    contentHeight: WantedCellContract.ContentHeight = WantedCellContract.ContentHeight.ContentHeight24,
    titleStyle: TextStyle? = null,
    captionStyle: TextStyle? = null,
    leftContent: (@Composable () -> Unit)? = null,
    rightContent: (@Composable () -> Unit)? = null
) {
    WantedCellLayout(
        modifier = modifier.fillMaxWidth(),
        contentHeight = contentHeight,
        text = {
            Text(
                text = text,
                maxLines = 1,
                overflow = if (ellipsis) TextOverflow.Ellipsis else TextOverflow.Clip,
                style = WantedTextStyle(
                    colorRes = when {
                        !isEnable -> R.color.label_disable
                        isActive -> R.color.primary_normal
                        else -> R.color.label_normal
                    },
                    style = when {
                        titleStyle != null -> titleStyle
                        else -> DesignSystemTheme.typography.body1Regular
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
                        style = when {
                            captionStyle != null -> captionStyle
                            else -> DesignSystemTheme.typography.label2Regular
                        }
                    )
                )
            }
        } else null,
        leftContent = leftContent,
        rightContent = rightContent,
        chevrons = if (chevrons) {
            {
                Icon(
                    painter = painterResource(id = R.drawable.ic_normal_chevron_right_tight_small_svg),
                    tint = colorResource(id = R.color.label_assistive),
                    contentDescription = ""
                )
            }
        } else {
            null
        }
    )
}


@Composable
private fun WantedCellLayout(
    modifier: Modifier = Modifier,
    contentHeight: WantedCellContract.ContentHeight = WantedCellContract.ContentHeight.ContentHeight24,
    text: @Composable () -> Unit,
    caption: @Composable (() -> Unit)?,
    leftContent: (@Composable () -> Unit)?,
    rightContent: (@Composable () -> Unit)?,
    chevrons: (@Composable () -> Unit)?
) {
    Row(
        modifier = Modifier.then(modifier),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Top
    ) {

        leftContent?.let {
            Box(
                modifier = Modifier
                    .size(contentHeight.height)
                    .wrapContentWidth()
                    .align(Alignment.CenterVertically)
            ) {
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

        Box(
            modifier = Modifier
                .size(contentHeight.height)
                .wrapContentWidth()
                .align(Alignment.CenterVertically),
            contentAlignment = Alignment.Center
        ) {
            rightContent?.invoke()
        }

        Box(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterVertically),
            contentAlignment = Alignment.Center
        ) {
            chevrons?.invoke()
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
private fun WantedListPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedCellImpl(
                    text = "텍스트".toAnnotatedString()
                )

                WantedCellImpl(
                    text = "텍스트".toAnnotatedString(),
                    caption = "캡션".toAnnotatedString()
                )

                WantedCellImpl(
                    text = "텍스트".toAnnotatedString(),
                    caption = "캡션".toAnnotatedString(),
                    isEnable = false,
                )

                WantedCellImpl(
                    text = "텍스트".toAnnotatedString(),
                    leftContent = {
                        WantedRadioButton(checked = true, onCheckedChange = {})
                    }
                )

                WantedCellImpl(
                    text = "텍스트".toAnnotatedString(),
                    caption = "캡션".toAnnotatedString(),
                    leftContent = {
                        WantedRadioButton(checked = true, onCheckedChange = {})
                    }
                )

                WantedCellImpl(
                    text = "텍스트".toAnnotatedString(),
                    rightContent = {
                        WantedActionChip(text = "Chip")
                    }
                )

                WantedCellImpl(
                    text = "텍스트".toAnnotatedString(),
                    caption = "캡션".toAnnotatedString(),
                    rightContent = {
                        WantedActionChip(text = "Chip")
                    }
                )

                WantedCellImpl(
                    text = "텍스트".toAnnotatedString(),
                    caption = "캡션".toAnnotatedString(),
                    rightContent = {
                        WantedActionChip(text = "Chip")
                    },
                    chevrons = true
                )
            }
        }
    }
}