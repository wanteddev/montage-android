package com.wanted.android.wanted.design.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.GlideImage
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.badge.WantedContentBadge
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.element.CheckBoxSize
import com.wanted.android.wanted.design.element.WantedCheckBox
import com.wanted.android.wanted.design.loading.skeleton.WantedSkeletonRectangle
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
    isLoading: Boolean = false,
    cardDefault: WantedCardDefault = WantedCardDefaults.getDefault(),
    topContent: @Composable (() -> Unit)? = null,
    bottomContent: @Composable (() -> Unit)? = null,
    leftContent: @Composable (() -> Unit)? = null,
    rightContent: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    if (isLoading) {
        WantedCardHorizontalSkeleton(
            modifier = modifier,
            topContent = cardDefault.topContentSkeleton,
            caption = cardDefault.captionSkeleton,
            extraCaption = cardDefault.extraCaptionSkeleton,
            bottomContent = cardDefault.bottomContentSkeleton,
            leftContent = leftContent,
            rightContent = rightContent
        )
    } else {
        WantedTouchArea(
            content = {
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
            },
            verticalPadding = 8.dp,
            horizontalPadding = 8.dp,
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 12.dp,
                bottomStart = 20.dp,
                bottomEnd = 12.dp
            ),
            onClick = onClick
        )
    }
}


@Composable
private fun WantedCardHorizontalSkeleton(
    modifier: Modifier = Modifier,
    topContent: Boolean = false,
    caption: Boolean = true,
    extraCaption: Boolean = true,
    bottomContent: Boolean = false,
    leftContent: @Composable (() -> Unit)? = null,
    rightContent: @Composable (() -> Unit)? = null,
) {
    WantedCardHorizontalLayout(
        modifier = modifier,
        thumbnail = { width: Dp, height: Dp ->
            WantedSkeletonRectangle(Modifier.size(width, height))
        },
        description = {
            WantedCardDescriptionSkeleton(
                modifier = Modifier,
                caption = caption,
                extraCaption = extraCaption,
                bottomContent = bottomContent,
                topContent = topContent
            )
        },
        leftContent = if (leftContent != null) {
            {
                Spacer(modifier = Modifier.size(24.dp))
            }
        } else null,
        rightContent = if (rightContent != null) {
            {
                Spacer(modifier = Modifier.size(24.dp))
            }
        } else null,
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


@DevicePreviews
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


@DevicePreviews
@Composable
private fun WantedCardSkeletonPreview() {
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
                    isLoading = true,
                    title = "제목",
                    caption = "캡션",
                )

                WantedCardHorizontal(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 64.dp)
                        .fillMaxWidth(),
                    title = "제목",
                    caption = "캡션",
                    isLoading = true,
                    cardDefault = WantedCardDefault(
                        bottomContentSkeleton = true
                    ),
                    extraCaption = "추가 캡션"
                )

                WantedCardHorizontal(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 64.dp)
                        .fillMaxWidth(),
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션",
                    isLoading = true,
                    cardDefault = WantedCardDefault(
                        topContentSkeleton = true,
                        bottomContentSkeleton = true
                    ),
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
                    isLoading = true,
                    cardDefault = WantedCardDefault(
                        topContentSkeleton = true,
                        bottomContentSkeleton = true
                    ),
                    topContent = {
                        WantedContentBadge(text = "텍스트")
                    },
                    leftContent = {}
                )

                WantedCardHorizontal(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 64.dp)
                        .fillMaxWidth(),
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션",
                    isLoading = true,
                    cardDefault = WantedCardDefault(
                        topContentSkeleton = true,
                        bottomContentSkeleton = true
                    ),
                    topContent = {
                        WantedContentBadge(text = "텍스트")
                    },
                    rightContent = {}
                )

                WantedCardHorizontal(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 64.dp)
                        .fillMaxWidth(),
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션",
                    isLoading = true,
                    cardDefault = WantedCardDefault(
                        topContentSkeleton = true,
                        bottomContentSkeleton = true
                    ),
                    topContent = {
                        WantedContentBadge(text = "텍스트")
                    },
                    leftContent = {},
                    rightContent = {}
                )

            }
        }
    }
}

