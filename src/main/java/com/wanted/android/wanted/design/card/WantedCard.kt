package com.wanted.android.wanted.design.card

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.GlideImage
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.theme.DesignSystemTheme

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=23188-76308&m=dev
 * 설명 : https://www.figma.com/design/MK6KmtXBxX7ZkoQXfD9MFH/%EA%B0%9C%EC%84%A0%3A-Components?node-id=3295-855&m=dev
 */

@Composable
fun WantedCard(
    modifier: Modifier = Modifier,
    isList: Boolean = false,
    thumbnail: Any? = null,
    overlayCaption: String = "",
    title: String = "",
    caption: String = "",
    extraCaption: String = "",
    @DrawableRes overlayToggleIcon: Int? = null,
    topContent: @Composable (() -> Unit)? = null,
    bottomContent: @Composable (() -> Unit)? = null
) {
    WantedCardLayout(
        modifier = modifier,
        isList = isList,
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
}

@Composable
fun WantedCardHorizontal(
    modifier: Modifier = Modifier,
    thumbnailTitle: String = "",
    leftContent: @Composable (() -> Unit)? = null,
    rightContent: @Composable (() -> Unit)? = null
) {

}

@Composable
private fun WantedCardLayout(
    modifier: Modifier = Modifier,
    isList: Boolean,
    thumbnail: @Composable (width: Dp, height: Dp) -> Unit,
    thumbnailOverlay: @Composable (() -> Unit)? = null,
    description: @Composable (() -> Unit)? = null,
    leftContent: @Composable (() -> Unit)? = null,
    rightContent: @Composable (() -> Unit)? = null
) {
    if (isList) {

    } else {
        WantedCardVerticalLayout(
            modifier = modifier,
            thumbnail = thumbnail,
            thumbnailOverlay = thumbnailOverlay,
            description = description
        )
    }
}


@Composable
private fun WantedCardVerticalLayout(
    modifier: Modifier = Modifier,
    thumbnail: @Composable (width: Dp, height: Dp) -> Unit,
    thumbnailOverlay: @Composable (() -> Unit)? = null,
    description: @Composable (() -> Unit)? = null
) {
    Column(modifier = modifier) {
        BoxWithConstraints(
            modifier = Modifier.aspectRatio(4 / 3f),
            contentAlignment = Alignment.TopStart
        ) {
            thumbnail(maxWidth, maxHeight)
            thumbnailOverlay?.invoke()
        }

        description?.invoke()
    }
}

@Composable
private fun WantedCardHorizontalLayout(
    modifier: Modifier = Modifier,
    thumbnail: @Composable () -> Unit,
    thumbnailOverlay: @Composable (() -> Unit)? = null,
    description: @Composable (() -> Unit)? = null,
) {

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

            }
        }
    }
}