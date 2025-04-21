package com.wanted.android.wanted.design.contents.cell

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
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
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
    textMaxLine: Int = 1,
    caption: String = "",
    fillWidth: Boolean = false,
    verticalPadding: WantedCellContract.VerticalPadding = WantedCellContract.VerticalPadding.Medium,
    interactionPadding: WantedCellContract.InteractionPadding = WantedCellContract.InteractionPadding.Default(fillWidth),
    divider: Boolean = false,
    isEnable: Boolean = true,
    isActive: Boolean = false,
    ellipsis: Boolean = true,
    verticalAlignCenter: Boolean = ellipsis,
    chevrons: Boolean = false,
    leftContent: (@Composable () -> Unit)? = null,
    rightContent: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    WantedCell(
        modifier = modifier,
        annotatedString = text.toAnnotatedString(),
        annotatedCaption = caption.toAnnotatedString(),
        textMaxLine = textMaxLine,
        verticalPadding = verticalPadding,
        interactionPadding = interactionPadding,
        fillWidth = fillWidth,
        divider = divider,
        isEnable = isEnable,
        isActive = isActive,
        ellipsis = ellipsis,
        verticalAlignCenter = verticalAlignCenter,
        chevrons = chevrons,
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
    fillWidth: Boolean = false,
    verticalPadding: WantedCellContract.VerticalPadding = WantedCellContract.VerticalPadding.Medium,
    interactionPadding: WantedCellContract.InteractionPadding = WantedCellContract.InteractionPadding.Default(fillWidth),
    divider: Boolean = false,
    isEnable: Boolean = true,
    isActive: Boolean = false,
    ellipsis: Boolean = true,
    verticalAlignCenter: Boolean = ellipsis,
    chevrons: Boolean = false,
    textMaxLine: Int = 1,
    titleStyle: TextStyle? = null,
    captionStyle: TextStyle? = null,
    leftContent: (@Composable () -> Unit)? = null,
    rightContent: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    WantedTouchArea(
        shape = RoundedCornerShape(12.dp),
        isUseRipple = isEnable && onClick != null,
        horizontalPadding = if (fillWidth) 0.dp else interactionPadding.padding,
        content = {
            Column {
                WantedCellImpl(
                    modifier = modifier
                        .clip(RoundedCornerShape(12.dp))
                        .padding(horizontal = if (fillWidth) interactionPadding.padding else 0.dp)
                        .padding(vertical = verticalPadding.value),
                    text = annotatedString,
                    textMaxLine = textMaxLine,
                    caption = annotatedCaption,
                    isEnable = isEnable,
                    isActive = isActive,
                    ellipsis = ellipsis,
                    verticalAlignCenter = verticalAlignCenter,
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
        onClick?.invoke()
    }
}

@DevicePreviews
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
                    fillWidth = false,
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
                    verticalPadding = WantedCellContract.VerticalPadding.Small,
                    onClick = {}
                )

                WantedCell(
                    text = "텍스트 padding Normal",
                    caption = "캡션",
                    verticalPadding = WantedCellContract.VerticalPadding.Medium,
                    onClick = {}
                )

                WantedCell(
                    text = "텍스트 padding Medium",
                    caption = "캡션",
                    verticalPadding = WantedCellContract.VerticalPadding.Large,
                    onClick = {}
                )

                WantedCell(
                    text = "텍스트 padding Medium paddingInset asdf asdf",
                    caption = "캡션",
                    fillWidth = true,
                    divider = true,
                    verticalPadding = WantedCellContract.VerticalPadding.Large,
                    onClick = {}
                )

            }
        }
    }
}