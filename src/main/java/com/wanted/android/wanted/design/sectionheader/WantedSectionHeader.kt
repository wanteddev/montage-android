package com.wanted.android.wanted.design.sectionheader

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.sectionheader.WantedSectionHeaderContract.Size
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
fun WantedSectionHeader(
    modifier: Modifier,
    title: String,
    size: Size = Size.Medium,
    textStyle: TextStyle? = null,
    leftContent: @Composable (() -> Unit)? = null,
    rightContent: @Composable (RowScope.() -> Unit)? = null
) {
    val density = LocalDensity.current
    val style = textStyle ?: run {
        WantedTextStyle(
            colorRes = if (size == Size.XSmall) {
                R.color.label_alternative
            } else {
                R.color.label_strong
            },
            style = when (size) {
                Size.Large -> DesignSystemTheme.typography.title3Bold
                Size.Medium -> DesignSystemTheme.typography.heading2Bold
                Size.Small -> DesignSystemTheme.typography.headline2Bold
                Size.XSmall -> DesignSystemTheme.typography.label1Bold
            }
        )
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f, false),
                text = title,
                overflow = TextOverflow.Ellipsis,
                maxLines = if (leftContent != null) 1 else Int.MAX_VALUE,
                style = style
            )

            leftContent?.let {
                Box(
                    modifier = Modifier.height(with(density) { style.lineHeight.toDp() }),
                    contentAlignment = Alignment.Center
                ) {
                    leftContent()
                }
            }
        }

        rightContent?.let {
            Row(
                modifier = Modifier.height(with(density) { style.lineHeight.toDp() }),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rightContent.invoke(this)
            }
        }
    }
}

@Composable
fun spToDp(spValue: Float): Dp {
    val density = LocalDensity.current
    return with(density) {
        spValue.sp.toDp()
    }
}

@DevicePreviews
@Composable
private fun WantedSectionHeaderPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedSectionHeader(
                    modifier = Modifier,
                    title = "제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목",
                    leftContent = {
                        Icon(
                            painter = painterResource(R.drawable.ic_normal_circle_exclamation_fill_svg),
                            contentDescription = ""
                        )
                    },
                    rightContent = {
                        Icon(
                            painter = painterResource(R.drawable.ic_normal_circle_exclamation_fill_svg),
                            contentDescription = ""
                        )
                    }
                )

                WantedSectionHeader(
                    modifier = Modifier,
                    title = "제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목",
                )


                WantedSectionHeader(
                    modifier = Modifier,
                    title = "제목",
                    leftContent = {
                        Icon(
                            painter = painterResource(R.drawable.ic_normal_circle_exclamation_fill_svg),
                            contentDescription = ""
                        )
                    },
                    rightContent = {
                        Icon(
                            painter = painterResource(R.drawable.ic_normal_circle_exclamation_fill_svg),
                            contentDescription = ""
                        )
                    }
                )

                WantedSectionHeader(
                    modifier = Modifier,
                    title = "제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목",
                    rightContent = {
                        Icon(
                            painter = painterResource(R.drawable.ic_normal_circle_exclamation_fill_svg),
                            contentDescription = ""
                        )
                    }
                )

                WantedSectionHeader(
                    modifier = Modifier,
                    title = "제목",
                    rightContent = {
                        Icon(
                            modifier = Modifier,
                            painter = painterResource(R.drawable.ic_normal_circle_exclamation_fill_svg),
                            contentDescription = ""
                        )
                    }
                )

                WantedSectionHeader(
                    modifier = Modifier,
                    title = "제목",
                    size = Size.XSmall,
                    leftContent = {
                        Icon(
                            painter = painterResource(R.drawable.ic_normal_circle_exclamation_fill_svg),
                            contentDescription = ""
                        )
                    }
                )

                WantedSectionHeader(
                    modifier = Modifier,
                    title = "제목",
                    size = Size.Small,
                    leftContent = {
                        Icon(
                            painter = painterResource(R.drawable.ic_normal_circle_exclamation_fill_svg),
                            contentDescription = ""
                        )
                    }
                )

                WantedSectionHeader(
                    modifier = Modifier,
                    title = "제목",
                    size = Size.Medium,
                    leftContent = {
                        Icon(
                            painter = painterResource(R.drawable.ic_normal_circle_exclamation_fill_svg),
                            contentDescription = ""
                        )
                    }
                )

                WantedSectionHeader(
                    modifier = Modifier,
                    title = "제목",
                    size = Size.Large,
                    leftContent = {
                        Icon(
                            painter = painterResource(R.drawable.ic_normal_circle_exclamation_fill_svg),
                            contentDescription = ""
                        )
                    }
                )

            }
        }
    }
}