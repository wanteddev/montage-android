package com.wanted.android.wanted.design.card

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.GlideImage
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.badge.WantedContentBadge
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.element.CheckBoxSize
import com.wanted.android.wanted.design.element.WantedCheckBox
import com.wanted.android.wanted.design.theme.DesignSystemTheme

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=23188-76308&m=dev
 * 설명 : https://www.figma.com/design/MK6KmtXBxX7ZkoQXfD9MFH/%EA%B0%9C%EC%84%A0%3A-Components?node-id=3295-855&m=dev
 */

@Composable
fun WantedCardHorizontal(
    modifier: Modifier = Modifier,
    thumbnail: Any? = null,
    title: String = "",
    caption: String = "",
    extraCaption: String = "",
    topContent: @Composable (() -> Unit)? = null,
    bottomContent: @Composable (() -> Unit)? = null,
    leftContent: @Composable (() -> Unit)? = null,
    rightContent: @Composable (() -> Unit)? = null,
) {
    WantedCardHorizontalLayout(
        modifier = modifier,
        thumbnail = { width: Dp, height: Dp ->
            GlideImage(
                modifier = Modifier.size(width, height),
                model = thumbnail,
                contentDescription = ""
            )
        },
        description = {
            WantedCardDescription(
                modifier = Modifier,
                title = title,
                caption = caption,
                extraCaption = extraCaption,
                bottomContent = bottomContent,
                topContent = topContent
            )
        },
        leftContent = leftContent,
        rightContent = rightContent
    )
}


@Composable
private fun WantedCardHorizontalLayout(
    modifier: Modifier = Modifier,
    thumbnail: @Composable (width: Dp, height: Dp) -> Unit,
    description: @Composable (() -> Unit)? = null,
    leftContent: @Composable (() -> Unit)? = null,
    rightContent: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        leftContent?.invoke()
        BoxWithConstraints(
            modifier = Modifier
                .height(64.dp)
                .aspectRatio(4 / 3f)
                .clip(RoundedCornerShape(12.dp))
                .background(color = colorResource(id = R.color.fill_alternative))
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.line_solid_alternative),
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.TopStart
        ) {
            thumbnail(maxWidth, maxHeight)
        }

        description?.let {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                description()
            }
        }

        rightContent?.invoke()
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
private fun WantedCardPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedCardHorizontal(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 64.dp)
                        .fillMaxWidth(),
                    title = "제목",
                    caption = "캡션",
                )

                WantedCardHorizontal(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 64.dp)
                        .fillMaxWidth(),
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션"
                )

                WantedCardHorizontal(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 64.dp)
                        .fillMaxWidth(),
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션",
                    topContent = {
                        WantedContentBadge(text = "텍스트")
                    }
                )

                WantedCardHorizontal(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 64.dp)
                        .fillMaxWidth(),
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션",
                    bottomContent = {
                        WantedContentBadge(text = "텍스트")
                    }
                )

                WantedCardHorizontal(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 64.dp)
                        .fillMaxWidth(),
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션",
                    bottomContent = {
                        WantedContentBadge(text = "텍스트")
                    },
                    leftContent = {
                        WantedCheckBox(
                            modifier = Modifier,
                            size = CheckBoxSize.Normal,
                            onCheckedChange = {}
                        )
                    }
                )

                WantedCardHorizontal(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 64.dp)
                        .fillMaxWidth(),
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션",
                    bottomContent = {
                        WantedContentBadge(text = "텍스트")
                    },
                    rightContent = {
                        WantedTouchArea(
                            modifier = Modifier.size(24.dp),
                            content = {
                                Icon(
                                    painter = painterResource(id = R.drawable.button_bookmark_line_svg),
                                    contentDescription = ""
                                )
                            },
                            onClick = {}
                        )
                    }

                )
            }
        }
    }
}