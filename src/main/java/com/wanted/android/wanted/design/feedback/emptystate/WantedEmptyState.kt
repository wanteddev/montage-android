package com.wanted.android.wanted.design.feedback.emptystate

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.WantedTextStyle

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=22838-255809&m=dev
 * 설명 : https://www.figma.com/design/MK6KmtXBxX7ZkoQXfD9MFH/%EA%B0%9C%EC%84%A0%3A-Components?node-id=3263-262&m=dev
 */
@Composable
fun WantedEmptyState(
    modifier: Modifier,
    image: @Composable (() -> Unit)? = null,
    heading: String? = null,
    description: String? = null,
    button: String? = null,
    onClick: () -> Unit = {}
) {
    WantedEmptyLayout(
        modifier = modifier.fillMaxWidth(),
        image = image,
        heading = heading?.let {
            {
                Text(
                    text = it,
                    textAlign = TextAlign.Center
                )
            }
        },
        description = description?.let {
            {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        button = button?.let {
            {
                WantedButton(
                    text = it,
                    buttonShape = ButtonShape.OUTLINED,
                    type = ButtonType.ASSISTIVE,
                    size = ButtonSize.LARGE,
                    onClick = onClick
                )
            }
        }
    )
}

@Composable
private fun WantedEmptyLayout(
    modifier: Modifier,
    image: @Composable (() -> Unit)? = null,
    heading: @Composable (() -> Unit)? = null,
    description: @Composable (() -> Unit)? = null,
    button: @Composable (() -> Unit)? = null,
) {
    Column(
        modifier = modifier.padding(bottom = image?.let { 20.dp } ?: 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        image?.let {
            Box(
                modifier = Modifier.size(160.dp),
                contentAlignment = Alignment.Center
            ) {
                image()
            }
        }

        WantedEmptyLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = image?.let { 0.dp } ?: 4.dp)
                .wrapContentHeight(),
            heading = heading,
            description = description,
            button = button
        )
    }
}

@Composable
private fun WantedEmptyLayout(
    modifier: Modifier,
    heading: @Composable (() -> Unit)? = null,
    description: @Composable (() -> Unit)? = null,
    button: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        heading?.let {
            ProvideTextStyle(
                value = WantedTextStyle(
                    colorRes = R.color.label_normal,
                    style = DesignSystemTheme.typography.heading2Bold
                )
            ) {
                heading()
            }
        }

        description?.let {
            ProvideTextStyle(
                value = WantedTextStyle(
                    colorRes = R.color.label_alternative,
                    style = DesignSystemTheme.typography.body1ReadingRegular
                )
            ) {
                description()
            }
        }

        button?.let {
            Box(modifier = Modifier.padding(top = 12.dp)) {
                button()
            }
        }
    }
}

@DevicePreviews
@Composable
private fun WantedEmptyPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedEmptyState(
                    modifier = Modifier,
                    heading = "타이틀이 들어가요.",
                    description = "상황에 대한 설명이 들어가요.\n" +
                        "설명은 최대 두 줄로 작성해요.",
                    button = "텍스트",
                    onClick = {}

                )

                WantedEmptyState(
                    modifier = Modifier,
                    image = {
                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(colorResource(id = R.color.label_disable))
                                .padding(10.dp),
                            painter = painterResource(id = R.drawable.ic_normal_camera_fill_svg),
                            contentScale = ContentScale.Crop,
                            contentDescription = ""
                        )
                    },
                    heading = "타이틀이 들어가요.",
                    description = "상황에 대한 설명이 들어가요.\n" +
                        "설명은 최대 두 줄로 작성해요.",
                    button = "텍스트",
                    onClick = {}

                )
            }
        }
    }
}