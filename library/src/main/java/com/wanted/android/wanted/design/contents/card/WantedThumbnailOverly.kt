package com.wanted.android.wanted.design.contents.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_35


@Composable
internal fun WantedThumbnailOverly(
    modifier: Modifier = Modifier,
    title: String? = null,
    toggleIcon: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        DesignSystemTheme.colors.staticBlack.copy(alpha = OPACITY_35),
                        DesignSystemTheme.colors.transparent
                    )
                ),
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            )
            .padding(10.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            title?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = DesignSystemTheme.typography.caption1Bold,
                    color = DesignSystemTheme.colors.staticWhite
                )
            }
        }

        toggleIcon?.let {
            Box(
                modifier = Modifier.size(20.dp),
                contentAlignment = Alignment.Center
            ) {
                toggleIcon()
            }
        }
    }
}

@DevicePreviews
@Composable
private fun WantedThumbnailOverlyPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedThumbnailOverly(
                    modifier = Modifier,
                    title = "제목"
                )


                WantedThumbnailOverly(
                    modifier = Modifier,
                    title = "제목",
                    toggleIcon = {
                        WantedTouchArea(
                            content = {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_normal_bookmark),
                                    contentDescription = "",
                                    tint = DesignSystemTheme.colors.staticWhite
                                )
                            },
                            onClick = {}
                        )
                    }
                )

                WantedThumbnailOverly(
                    modifier = Modifier,
                    toggleIcon = {
                        WantedTouchArea(
                            content = {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_normal_bookmark),
                                    contentDescription = "",
                                    tint = DesignSystemTheme.colors.staticWhite
                                )
                            },
                            onClick = {}
                        )
                    }
                )

                WantedThumbnailOverly(
                    modifier = Modifier,
                    title = "엄청 긴 글 입니다. 엄청 긴 글 입니다. 엄청 긴 글 입니다. 엄청 긴 글 입니다. 엄청 긴 글 입니다.",
                    toggleIcon = {
                        WantedTouchArea(
                            content = {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_normal_bookmark),
                                    contentDescription = "",
                                    tint = DesignSystemTheme.colors.staticWhite
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