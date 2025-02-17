package com.wanted.android.wanted.design.contents.accordion

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.contents.accordion.WantedAccordionContract.VerticalPadding
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_8
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
fun WantedAccordion(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    titleStyle: TextStyle = WantedTextStyle(
        colorRes = R.color.label_normal,
        style = DesignSystemTheme.typography.body2Bold
    ),
    descriptionStyle: TextStyle = WantedTextStyle(
        colorRes = R.color.label_neutral,
        style = DesignSystemTheme.typography.label1Regular
    ),
    isExpanded: Boolean = false,
    fillWidth: Boolean = false,
    divider: Boolean = true,
    verticalPadding: VerticalPadding = VerticalPadding.Padding12,
    content: @Composable (() -> Unit)? = null,
    onChangeExpanded: (Boolean) -> Unit,
    trail: @Composable () -> Unit = {
        WantedAccordionTrailArrowIcon(
            isExpanded = isExpanded,
            tint = titleStyle.color
        )
    }
) {
    AccordionLayout(
        modifier = modifier.animateContentSize(),
        isExpanded = isExpanded,
        divider = divider,
        verticalPadding = verticalPadding,
        header = {
            WantedAccordionHeader(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalPadding = verticalPadding,
                title = title,
                style = titleStyle,
                fillWidth = fillWidth,
                trail = trail,
                onClick = {
                    onChangeExpanded(!isExpanded)
                }
            )
        },
        description = description?.let {
            {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = if (fillWidth) 20.dp else 0.dp),
                    text = description,
                    style = descriptionStyle
                )
            }
        },
        content = content?.let {
            {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = if (fillWidth) 20.dp else 0.dp)
                ) {
                    content()
                }
            }
        }
    )
}

@Composable
fun AccordionLayout(
    modifier: Modifier = Modifier,
    divider: Boolean,
    isExpanded: Boolean,
    verticalPadding: VerticalPadding,
    header: @Composable () -> Unit,
    description: @Composable (() -> Unit)?,
    content: @Composable (() -> Unit)?,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            header()

            if (isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = when (verticalPadding) {
                                VerticalPadding.Padding16 -> 0.dp
                                VerticalPadding.Padding12 -> 4.dp
                                VerticalPadding.Padding8 -> 8.dp
                            }
                        )
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    description?.invoke()

                    content?.let {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            content()
                        }
                    }
                }
            }
        }

        if (divider) {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                color = colorResource(R.color.line_normal_alternative)
            )
        }
    }
}

@DevicePreviews
@Composable
private fun WantedAccordionPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedAccordion(
                    modifier = Modifier,
                    isExpanded = true,
                    title = "제목",
                    description = "제목에 대한 상세 내용을 입력해주세요.\n긴 컨텐츠라면 접은 상태를 기본 값으로 사용하세요.",
                    content = {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(colorResource(R.color.accent_violet).copy(OPACITY_8))
                        )
                    },
                    onChangeExpanded = {}
                )

                WantedAccordion(
                    modifier = Modifier,
                    isExpanded = true,
                    fillWidth = true,
                    title = "제목",
                    description = "제목에 대한 상세 내용을 입력해주세요.\n긴 컨텐츠라면 접은 상태를 기본 값으로 사용하세요.",
                    content = {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(colorResource(R.color.accent_violet).copy(OPACITY_8))
                        )
                    },
                    onChangeExpanded = {}
                )

                WantedAccordion(
                    modifier = Modifier,
                    title = "제목",
                    description = "제목에 대한 상세 내용을 입력해주세요.\n긴 컨텐츠라면 접은 상태를 기본 값으로 사용하세요.",
                    fillWidth = true,
                    content = {},
                    onChangeExpanded = {}
                )
            }
        }
    }
}