package com.wanted.android.wanted.design.contents.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.GlideImage
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.contents.contentbadge.WantedContentBadge
import com.wanted.android.wanted.design.loading.skeleton.WantedSkeletonRectangle
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.clickOnce

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=23188-76308&m=dev
 * 설명 : https://www.figma.com/design/MK6KmtXBxX7ZkoQXfD9MFH/%EA%B0%9C%EC%84%A0%3A-Components?node-id=3295-855&m=dev
 */

@Composable
fun WantedCardVertical(
    modifier: Modifier = Modifier,
    thumbnail: Any? = null,
    overlayCaption: String = "",
    title: String = "",
    caption: String = "",
    extraCaption: String = "",
    isLoading: Boolean = false,
    cardDefault: WantedCardDefault = WantedCardDefaults.getDefault(),
    overlayToggleIcon: @Composable (() -> Unit)? = null,
    topContent: @Composable (() -> Unit)? = null,
    bottomContent: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    if (isLoading) {
        WantedCardVerticalSkeleton(
            modifier = modifier,
            topContent = cardDefault.topContentSkeleton,
            caption = cardDefault.captionSkeleton,
            extraCaption = cardDefault.extraCaptionSkeleton,
            bottomContent = cardDefault.bottomContentSkeleton,
        )
    } else {
        WantedTouchArea(
            content = {
                WantedCardVerticalLayout(
                    modifier = modifier,
                    thumbnail = { width: Dp, height: Dp ->
                        GlideImage(
                            modifier = Modifier.size(width, height),
                            model = thumbnail,
                            contentDescription = ""
                        )
                    },
                    thumbnailOverlay = if (overlayCaption.isNotEmpty() || overlayToggleIcon != null) {
                        {
                            WantedThumbnailOverly(
                                modifier = Modifier,
                                title = overlayCaption,
                                toggleIcon = overlayToggleIcon?.let {
                                    {
                                        WantedTouchArea(
                                            content = {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.button_bookmark_line_svg),
                                                    contentDescription = ""
                                                )
                                            },
                                            onClick = {}
                                        )
                                    }
                                }
                            )
                        }
                    } else null,
                    description = {
                        WantedCardDescription(
                            modifier = Modifier,
                            title = title,
                            caption = caption,
                            extraCaption = extraCaption,
                            bottomContent = bottomContent,
                            topContent = topContent
                        )
                    }
                )
            },
            verticalPadding = 8.dp,
            horizontalPadding = 8.dp,
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
                bottomStart = 12.dp,
                bottomEnd = 12.dp
            ),
            onClick = onClick
        )
    }
}

@Composable
private fun WantedCardVerticalSkeleton(
    modifier: Modifier = Modifier,
    topContent: Boolean = false,
    caption: Boolean = true,
    extraCaption: Boolean = true,
    bottomContent: Boolean = false
) {
    WantedCardVerticalLayout(
        modifier = modifier,
        thumbnail = { width: Dp, height: Dp ->
            WantedSkeletonRectangle(Modifier.size(width, height))
        },
        thumbnailOverlay = null,
        description = {
            WantedCardDescriptionSkeleton(
                modifier = Modifier,
                caption = caption,
                extraCaption = extraCaption,
                bottomContent = bottomContent,
                topContent = topContent
            )
        }
    )
}

@Composable
private fun WantedCardVerticalLayout(
    modifier: Modifier = Modifier,
    thumbnail: @Composable (width: Dp, height: Dp) -> Unit,
    thumbnailOverlay: @Composable (() -> Unit)? = null,
    description: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        BoxWithConstraints(
            modifier = Modifier
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
            thumbnailOverlay?.invoke()
        }

        description?.invoke()
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
                WantedCardVertical(
                    modifier = Modifier.width(152.dp),
                    title = "제목",
                    caption = "캡션",
                    overlayCaption = "overlayCaption"
                )

                WantedCardVertical(
                    modifier = Modifier.width(152.dp),
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션",
                    overlayCaption = "overlayCaption",
                    overlayToggleIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.button_bookmark_fill_svg),
                            tint = colorResource(R.color.primary_normal),
                            contentDescription = ""
                        )
                    }
                )

                WantedCardVertical(
                    modifier = Modifier.width(152.dp),
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션",
                    topContent = {
                        WantedContentBadge(text = "텍스트")
                    }
                )

                WantedCardVertical(
                    modifier = Modifier.width(152.dp),
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션",
                    bottomContent = {
                        WantedContentBadge(text = "텍스트")
                    }
                )

                WantedCardVertical(
                    modifier = Modifier.width(152.dp),
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션",
                    bottomContent = {
                        WantedContentBadge(text = "텍스트")
                    },
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
                WantedCardVertical(
                    modifier = Modifier.width(152.dp),
                    isLoading = true,
                    title = "제목",
                    caption = "캡션",
                    overlayCaption = "overlayCaption"
                )

                WantedCardVertical(
                    modifier = Modifier.width(152.dp),
                    isLoading = true,
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션",
                    overlayCaption = "overlayCaption",
                    cardDefault = WantedCardDefault(
                        topContentSkeleton = true
                    ),
                    overlayToggleIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.button_bookmark_fill_svg),
                            tint = colorResource(R.color.primary_normal),
                            contentDescription = ""
                        )
                    }
                )

                WantedCardVertical(
                    modifier = Modifier.width(152.dp),
                    isLoading = true,
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션",
                    cardDefault = WantedCardDefault(
                        topContentSkeleton = true,
                        bottomContentSkeleton = true
                    ),
                    topContent = {
                        WantedContentBadge(text = "텍스트")
                    }
                )
            }
        }
    }
}