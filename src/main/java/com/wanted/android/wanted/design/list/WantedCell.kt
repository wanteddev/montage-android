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
    textMaxLine: Int = 1,
    padding: WantedCellContract.Padding = WantedCellContract.Padding.Normal,
    paddingInset: Boolean = false,
    divider: Boolean = false,
    isEnable: Boolean = true,
    isActive: Boolean = false,
    leftContent: (@Composable () -> Unit)? = null,
    rightContent: (@Composable () -> Unit)? = null,
    onClick: () -> Unit
) {
    WantedTouchArea(
        horizontalPadding = if (paddingInset) 0.dp else 12.dp,
        shape = RoundedCornerShape(12.dp),
        content = {
            Column {
                WantedCellImpl(
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
                    text = annotatedString,
                    textMaxLine = textMaxLine,
                    caption = annotatedCaption,
                    isEnable = isEnable,
                    isActive = isActive,
                    leftContent = leftContent,
                    rightContent = rightContent
                )

                if (divider) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = if (paddingInset) 20.dp else 0.dp),
                        color = colorResource(id = R.color.line_normal_alternative)
                    )
                }
            }

        }
    ) {
        onClick()
    }
}

@Composable
fun WantedCell(
    modifier: Modifier = Modifier,
    text: String,
    textMaxLine: Int = 1,
    caption: String = "",
    padding: WantedCellContract.Padding = WantedCellContract.Padding.Normal,
    paddingInset: Boolean = false,
    divider: Boolean = false,
    isEnable: Boolean = true,
    isActive: Boolean = false,
    leftContent: (@Composable () -> Unit)? = null,
    rightContent: (@Composable () -> Unit)? = null,
    onClick: () -> Unit
) {
    WantedCell(
        modifier,
        annotatedString = text.toAnnotatedString(),
        annotatedCaption = caption.toAnnotatedString(),
        textMaxLine = textMaxLine,
        padding = padding,
        paddingInset = paddingInset,
        divider = divider,
        isEnable = isEnable,
        isActive = isActive,
        leftContent = leftContent,
        rightContent = rightContent,
        onClick = onClick
    )
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
                    onClick = {},
                    divider = true
                )

                WantedCell(
                    text = "텍스트",
                    caption = "캡션",
                    onClick = {}
                )

                WantedCell(
                    text = "텍스트",
                    caption = "캡션",
                    isActive = true,
                    onClick = {}
                )

                WantedCell(
                    text = "텍스트",
                    caption = "캡션",
                    isActive = true,
                    isEnable = false,
                    onClick = {}
                )

                WantedCell(
                    text = "텍스트",
                    caption = "캡션",
                    onClick = {},
                    divider = true
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
                    divider = true,
                    padding = WantedCellContract.Padding.Medium,
                    onClick = {}
                )

            }
        }
    }
}