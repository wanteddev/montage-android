package com.wanted.android.wanted.design.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.badge.WantedContentBadge
import com.wanted.android.wanted.design.loading.WantedSkeletonLength
import com.wanted.android.wanted.design.loading.WantedSkeletonRectangle
import com.wanted.android.wanted.design.loading.WantedSkeletonText
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
fun WantedCardDescription(
    modifier: Modifier = Modifier,
    title: String,
    maxLines: Int = 2,
    caption: String = "",
    extraCaption: String = "",
    topContent: @Composable (() -> Unit)? = null,
    bottomContent: @Composable (() -> Unit)? = null
) {
    WantedCardDescriptionLayout(
        modifier = modifier,
        topContent = topContent,
        title = {
            Text(
                text = title,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis
            )
        },
        cation = caption.ifEmpty { null }?.let {
            {
                Text(
                    text = caption,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        extraCaption = extraCaption.ifEmpty { null }?.let {
            {
                Text(
                    text = extraCaption,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        bottomContent = bottomContent
    )
}


@Composable
internal fun WantedCardDescriptionSkeleton(
    modifier: Modifier = Modifier,
    topContent: Boolean = false,
    caption: Boolean = true,
    extraCaption: Boolean = true,
    bottomContent: Boolean = false
) {
    WantedCardDescriptionLayout(
        modifier = modifier,
        topContent = if (topContent) {
            { WantedSkeletonRectangle(modifier = Modifier.size(48.dp, 20.dp)) }
        } else null,
        title = {
            WantedSkeletonText(
                modifier = Modifier.height(22.dp),
                length = WantedSkeletonLength.Ratio100
            )
        },
        cation = if (caption) {
            {
                WantedSkeletonText(
                    modifier = Modifier.height(18.dp),
                    length = WantedSkeletonLength.Ratio50
                )
            }
        } else null,
        extraCaption = if (extraCaption) {
            {
                WantedSkeletonText(
                    modifier = Modifier.height(18.dp),
                    length = WantedSkeletonLength.Ratio25
                )
            }
        } else null,
        bottomContent = if (bottomContent) {
            { WantedSkeletonRectangle(modifier = Modifier.size(48.dp, 20.dp)) }
        } else null
    )
}


@Composable
internal fun WantedCardDescriptionLayout(
    modifier: Modifier = Modifier,
    topContent: @Composable (() -> Unit)? = null,
    title: @Composable () -> Unit,
    cation: @Composable (() -> Unit)? = null,
    extraCaption: @Composable (() -> Unit)? = null,
    bottomContent: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        topContent?.let {
            Box(modifier = Modifier.padding(bottom = 4.dp)) {
                topContent()
            }
        }

        ProvideTextStyle(
            value = WantedTextStyle(
                colorRes = R.color.label_normal,
                style = DesignSystemTheme.typography.body1Bold
            )
        ) {
            title()
        }

        ProvideTextStyle(
            value = WantedTextStyle(
                colorRes = R.color.label_alternative,
                style = DesignSystemTheme.typography.label2Medium
            )
        ) {
            cation?.invoke()

            extraCaption?.invoke()
        }

        bottomContent?.let {
            Box(modifier = Modifier.padding(top = 4.dp)) {
                bottomContent()
            }
        }
    }
}


@DevicePreviews
@Composable
private fun WantedCardDescriptionPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedCardDescription(
                    modifier = Modifier,
                    title = "제목"
                )

                WantedCardDescription(
                    modifier = Modifier,
                    title = "제목",
                    caption = "캡션"
                )

                WantedCardDescription(
                    modifier = Modifier,
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션"
                )

                WantedCardDescription(
                    modifier = Modifier,
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션",
                    topContent = {
                        WantedContentBadge(text = "텍스트")
                    }
                )

                WantedCardDescription(
                    modifier = Modifier,
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션",
                    bottomContent = {
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            WantedContentBadge(text = "텍스트")
                            WantedContentBadge(text = "텍스트")
                        }
                    }
                )

                WantedCardDescriptionSkeleton()

                WantedCardDescriptionSkeleton(topContent = true, bottomContent = true)
            }
        }
    }
}