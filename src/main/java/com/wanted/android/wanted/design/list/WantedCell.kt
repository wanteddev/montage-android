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
import androidx.compose.ui.text.TextStyle
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
 * 설명 : https://www.figma.com/design/MK6KmtXBxX7ZkoQXfD9MFH/%EA%B0%9C%EC%84%A0%3A-Components?node-id=4013-9151&m=dev
 *
 */


@Composable
fun WantedCell(
    modifier: Modifier = Modifier,
    text: String,
    caption: String = "",
    padding: WantedCellContract.Padding = WantedCellContract.Padding.Padding12,
    interactionPadding: WantedCellContract.InteractionPadding = WantedCellContract.InteractionPadding.Default,
    fillWidth: Boolean = false,
    divider: Boolean = false,
    isEnable: Boolean = true,
    isActive: Boolean = false,
    ellipsis: Boolean = true,
    chevrons: Boolean = false,
    contentHeight: WantedCellContract.ContentHeight = WantedCellContract.ContentHeight.ContentHeight24,
    leftContent: (@Composable () -> Unit)? = null,
    rightContent: (@Composable () -> Unit)? = null,
    onClick: () -> Unit
) {
    WantedCell(
        modifier = modifier,
        annotatedString = text.toAnnotatedString(),
        annotatedCaption = caption.toAnnotatedString(),
        padding = padding,
        interactionPadding = interactionPadding,
        fillWidth = fillWidth,
        divider = divider,
        isEnable = isEnable,
        isActive = isActive,
        ellipsis = ellipsis,
        chevrons = chevrons,
        contentHeight = contentHeight,
        leftContent = leftContent,
        rightContent = rightContent,
        onClick = onClick
    )
}


@Composable
fun WantedCell(
    modifier: Modifier = Modifier,
    annotatedString: AnnotatedString,
    annotatedCaption: AnnotatedString = AnnotatedString(""),
    padding: WantedCellContract.Padding = WantedCellContract.Padding.Padding12,
    interactionPadding: WantedCellContract.InteractionPadding = WantedCellContract.InteractionPadding.Default,
    fillWidth: Boolean = false,
    divider: Boolean = false,
    isEnable: Boolean = true,
    isActive: Boolean = false,
    ellipsis: Boolean = true,
    chevrons: Boolean = false,
    contentHeight: WantedCellContract.ContentHeight = WantedCellContract.ContentHeight.ContentHeight24,
    titleStyle: TextStyle? = null,
    captionStyle: TextStyle? = null,
    leftContent: (@Composable () -> Unit)? = null,
    rightContent: (@Composable () -> Unit)? = null,
    onClick: () -> Unit
) {
    WantedTouchArea(
        horizontalPadding = if (fillWidth) 0.dp else 12.dp,
        shape = RoundedCornerShape(12.dp),
        content = {
            Column {
                WantedCellImpl(
                    modifier = modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickOnceForDesignSystem(enabled = isEnable) {
                            onClick()
                        }
                        .padding(horizontal = if (fillWidth) interactionPadding.padding else 0.dp)
                        .padding(vertical = padding.value),
                    text = annotatedString,
                    caption = annotatedCaption,
                    isEnable = isEnable,
                    isActive = isActive,
                    ellipsis = ellipsis,
                    contentHeight = contentHeight,
                    chevrons = chevrons,
                    titleStyle = titleStyle,
                    captionStyle = captionStyle,
                    leftContent = leftContent,
                    rightContent = rightContent
                )

                if (divider) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = if (fillWidth) 20.dp else 0.dp),
                        color = colorResource(id = R.color.line_normal_alternative)
                    )
                }
            }

        }
    ) {
        onClick()
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
                    fillWidth = true,
                    onClick = {}
                )

                WantedCell(
                    text = "텍스트",
                    fillWidth = true,
                    interactionPadding = WantedCellContract.InteractionPadding.Custom(30.dp),
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
                    padding = WantedCellContract.Padding.Padding8,
                    onClick = {}
                )

                WantedCell(
                    text = "텍스트 padding Normal",
                    caption = "캡션",
                    padding = WantedCellContract.Padding.Padding12,
                    onClick = {}
                )

                WantedCell(
                    text = "텍스트 padding Medium",
                    caption = "캡션",
                    padding = WantedCellContract.Padding.Padding16,
                    onClick = {}
                )

                WantedCell(
                    text = "텍스트 padding Medium paddingInset asdf asdf",
                    caption = "캡션",
                    fillWidth = true,
                    divider = true,
                    padding = WantedCellContract.Padding.Padding16,
                    onClick = {}
                )

            }
        }
    }
}