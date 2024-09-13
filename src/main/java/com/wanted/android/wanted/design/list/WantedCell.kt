package com.wanted.android.wanted.design.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.toAnnotatedString

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=14854-45065&m=dev
 */

@Composable
fun WantedCell(
    modifier: Modifier = Modifier,
    annotatedString: AnnotatedString,
    annotatedCaption: AnnotatedString = AnnotatedString(""),
    padding: WantedCellContract.Padding = WantedCellContract.Padding.Normal,
    paddingInset: Boolean = false,
    divider: Boolean = false,
    isEnable: Boolean = true,
    leftContent: (@Composable () -> Unit)? = null,
    rightContent: (@Composable () -> Unit)? = null,
    onClick: () -> Unit
) {
    WantedTouchArea(
        horizontalPadding = if (paddingInset) 0.dp else 12.dp,
        shape = RoundedCornerShape(12.dp),
        content = {
            WantedCellLayout(
                modifier = modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickOnceForDesignSystem(enabled = isEnable) {
                        onClick()
                    }
                    .padding(horizontal = if (paddingInset) 20.dp else 0.dp)
                    .padding(
                        vertical = when (padding) {
                            WantedCellContract.Padding.Small -> 8.dp
                            WantedCellContract.Padding.Normal -> 12.dp
                            WantedCellContract.Padding.Medium -> 16.dp
                        }
                    ),
                isDivider = divider,
                contents = {
                    WantedList(
                        text = annotatedString,
                        caption = annotatedCaption,
                        isEnable = isEnable,
                        leftContent = leftContent,
                        rightContent = rightContent
                    )
                }
            )
        }
    ) {
        onClick()
    }
}

@Composable
fun WantedCell(
    modifier: Modifier = Modifier,
    text: String,
    caption: String = "",
    padding: WantedCellContract.Padding = WantedCellContract.Padding.Normal,
    paddingInset: Boolean = false,
    divider: Boolean = false,
    isEnable: Boolean = true,
    leftContent: (@Composable () -> Unit)? = null,
    rightContent: (@Composable () -> Unit)? = null,
    onClick: () -> Unit
) {
    WantedCell(
        modifier,
        annotatedString = text.toAnnotatedString(),
        annotatedCaption = caption.toAnnotatedString(),
        padding = padding,
        paddingInset = paddingInset,
        divider = divider,
        isEnable = isEnable,
        leftContent = leftContent,
        rightContent = rightContent,
        onClick = onClick
    )
}


@Composable
fun WantedCell(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    caption: String = "",
    padding: WantedCellContract.Padding = WantedCellContract.Padding.Normal,
    paddingInset: Boolean = false,
    divider: Boolean = false,
    isEnable: Boolean = true,
    leftContent: (@Composable () -> Unit)? = null,
    rightContent: (@Composable () -> Unit)? = null,
    onClick: () -> Unit
) {
    WantedCell(
        modifier = modifier,
        annotatedString = text,
        annotatedCaption = caption.toAnnotatedString(),
        padding = padding,
        paddingInset = paddingInset,
        divider = divider,
        isEnable = isEnable,
        leftContent = leftContent,
        rightContent = rightContent,
        onClick = onClick
    )
}

@Composable
private fun WantedCellLayout(
    modifier: Modifier = Modifier,
    isDivider: Boolean,
    contents: @Composable () -> Unit,
) {
    Column(modifier = modifier) {
        contents()

        if (isDivider) {
            HorizontalDivider(
                color = colorResource(id = R.color.line_normal_alternative)
            )
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
private fun WantedCellPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedCell(
                    text = "텍스트",
                    onClick = {}
                )

                WantedCell(
                    text = "텍스트",
                    caption = "캡션",
                    onClick = {}
                )

                WantedCell(
                    text = "텍스트",
                    caption = "캡션",
                    isEnable = false,
                    onClick = {}
                )

                WantedCell(
                    text = "텍스트 padding Small",
                    caption = "캡션",
                    padding = WantedCellContract.Padding.Small,
                    onClick = {}
                )

                WantedCell(
                    text = "텍스트 padding Normal",
                    caption = "캡션",
                    padding = WantedCellContract.Padding.Normal,
                    onClick = {}
                )

                WantedCell(
                    text = "텍스트 padding Medium",
                    caption = "캡션",
                    padding = WantedCellContract.Padding.Medium,
                    onClick = {}
                )

                WantedCell(
                    text = "텍스트 padding Medium paddingInset asdf asdf",
                    caption = "캡션",
                    paddingInset = true,
                    padding = WantedCellContract.Padding.Medium,
                    onClick = {}
                )
            }
        }
    }
}