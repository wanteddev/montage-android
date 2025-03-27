package com.wanted.android.wanted.design.contents.cell

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
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.chip.WantedActionChip
import com.wanted.android.wanted.design.input.control.WantedRadioButton
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.WantedTextStyle
import com.wanted.android.wanted.design.util.toAnnotatedString

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=14854-45066&m=dev
 */

@Composable
fun WantedCellImpl(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    textMaxLine: Int = 1,
    caption: AnnotatedString = AnnotatedString(""),
    isEnable: Boolean = true,
    isActive: Boolean = false,
    ellipsis: Boolean = true,
    verticalAlignCenter: Boolean = ellipsis,
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
        verticalAlignment = if (verticalAlignCenter) Alignment.CenterVertically else Alignment.Top,
        text = {
            Text(
                text = text,
                maxLines = textMaxLine,
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
                    maxLines = if (ellipsis) 1 else Int.MAX_VALUE,
                    overflow = if (ellipsis) TextOverflow.Ellipsis else TextOverflow.Clip,
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
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    text: @Composable () -> Unit,
    caption: @Composable (() -> Unit)?,
    leftContent: (@Composable () -> Unit)?,
    rightContent: (@Composable () -> Unit)?,
    chevrons: (@Composable () -> Unit)?
) {
    Row(
        modifier = Modifier.then(modifier),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = verticalAlignment
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

        rightContent?.let {
            Box(
                modifier = Modifier
                    .size(contentHeight.height)
                    .wrapContentWidth()
                    .align(Alignment.CenterVertically),
                contentAlignment = Alignment.CenterEnd
            ) {
                rightContent()
            }
        }


        chevrons?.let {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterVertically),
                contentAlignment = Alignment.Center
            ) {
                chevrons()
            }
        }
    }
}

@DevicePreviews
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