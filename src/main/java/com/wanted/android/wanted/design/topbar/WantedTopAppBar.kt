package com.wanted.android.wanted.design.topbar

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.topbar.WantedTopAppBarContract.TopAppBarType
import com.wanted.android.wanted.design.topbar.view.LocalWantedTopBarIconType
import com.wanted.android.wanted.design.topbar.view.WantedExtendedTopAppBarLayout
import com.wanted.android.wanted.design.topbar.view.WantedTopAppBarIconButton
import com.wanted.android.wanted.design.topbar.view.WantedTopAppBarLayout
import com.wanted.android.wanted.design.util.getStatusBarHeight
import com.wanted.android.wanted.design.util.pxToDp

/**
 * figma : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=14852-43366&m=dev
 * 설명 figma : https://www.figma.com/design/MK6KmtXBxX7ZkoQXfD9MFH/%EA%B0%9C%EC%84%A0%3A-Components?node-id=1330-37845&t=KJWIkEkkcHKMDAcN-4
 */
@Composable
fun WantedTopAppBar(
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = false,
    type: TopAppBarType = TopAppBarType.Normal,
    titleAlignCenter: Boolean = false,
    scrollableState: ScrollableState? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    title: String = "",
    actions: @Composable (RowScope.() -> Unit)? = null
) {
    if (titleAlignCenter) {
        WantedCenterTopAppBar(
            modifier = modifier,
            isFullScreen = isFullScreen,
            type = type,
            scrollableState = scrollableState,
            navigationIcon = navigationIcon,
            title = {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            actions = actions
        )
    } else {
        WantedTopAppBar(
            modifier = modifier,
            isFullScreen = isFullScreen, type = type,
            scrollableState = scrollableState,
            navigationIcon = navigationIcon,
            title = {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            actions = actions
        )
    }
}

@Composable
fun WantedTopAppBarCustomTitle(
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = false,
    type: TopAppBarType = TopAppBarType.Normal,
    scrollableState: ScrollableState? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null
) {
    WantedTopAppBar(
        modifier = modifier,
        isFullScreen = isFullScreen, type = type,
        scrollableState = scrollableState,
        navigationIcon = navigationIcon,
        title = title,
        actions = actions
    )
}


@Composable
fun WantedBackTopAppBar(
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = false,
    type: TopAppBarType = TopAppBarType.Normal,
    scrollableState: ScrollableState? = null,
    title: String = "",
    actions: @Composable (RowScope.() -> Unit)? = null,
    onClickBack: () -> Unit
) {
    WantedTopAppBar(
        modifier = modifier,
        isFullScreen = isFullScreen,
        type = type,
        scrollableState = scrollableState,
        navigationIcon = {
            WantedTopAppBarIconButton(
                type = type,
                painter = painterResource(id = R.drawable.ic_normal_arrow_left_svg),
                onClick = { onClickBack() }
            )
        },
        title = title,
        actions = actions
    )
}

@Composable
fun WantedTopAppBar(
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = false,
    type: TopAppBarType = TopAppBarType.Normal,
    scrollableState: ScrollableState? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null
) {
    val elevation = remember { mutableIntStateOf(0) }
    LaunchedEffect(key1 = scrollableState?.canScrollBackward) {
        if (scrollableState?.canScrollBackward == true) {
            elevation.intValue = 4
        } else {
            elevation.intValue = 0
        }
    }

    val layoutModifier = if (isFullScreen) {
        Modifier.padding(top = getStatusBarHeight().pxToDp())
    } else {
        Modifier
    }

    Surface(
        modifier = modifier,
        shadowElevation = elevation.intValue.dp,
        color = if (type == TopAppBarType.Floating) {
            Color.Transparent
        } else {
            colorResource(id = R.color.background_normal_normal)
        }
    ) {
        CompositionLocalProvider(LocalWantedTopBarIconType.provides(type)) {
            when (type) {
                TopAppBarType.Normal -> {
                    WantedTopAppBarLayout(
                        modifier = layoutModifier,
                        navigationIcon = navigationIcon,
                        title = title,
                        actions = actions
                    )
                }

                TopAppBarType.Extended -> {
                    WantedExtendedTopAppBarLayout(
                        modifier = layoutModifier,
                        navigationIcon = navigationIcon,
                        title = title,
                        actions = actions
                    )
                }

                else -> {
                    WantedTopAppBarLayout(
                        modifier = Modifier,
                        navigationIcon = navigationIcon,
                        title = title,
                        actions = actions
                    )
                }
            }
        }
    }
}


@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Preview(
    "foldableLight",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "ko",
    device = "spec:width=673dp,height=841dp"
)
@Composable
private fun CustomTopAppBarPreview() {
    DesignSystemTheme {

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            WantedTopAppBar(
                title = "title",
                actions = {
                    WantedTopAppBarIconButton(
                        painter = painterResource(id = R.drawable.ic_normal_share_svg),
                        onClick = { }
                    )
                }
            )

            Box(Modifier.background(Color.DarkGray)) {
                WantedTopAppBar(
                    type = TopAppBarType.Floating,
                    actions = {}
                )
            }

            WantedTopAppBar(
                type = TopAppBarType.Extended,
                title = "title",
                actions = {}
            )

            WantedBackTopAppBar(
                title = "title",
                onClickBack = {}
            )

            Box(Modifier.background(Color.DarkGray)) {
                WantedBackTopAppBar(
                    type = TopAppBarType.Floating,
                    actions = {
                        WantedTopAppBarIconButton(
                            painter = painterResource(id = R.drawable.ic_normal_share_svg),
                            onClick = { }
                        )
                        WantedTopAppBarIconButton(
                            painter = painterResource(id = R.drawable.ic_normal_share_svg),
                            onClick = { }
                        )
                    },
                    onClickBack = { }
                )
            }

            WantedBackTopAppBar(
                type = TopAppBarType.Extended,
                title = "title",
                actions = {
                    WantedTopAppBarIconButton(
                        painter = painterResource(id = R.drawable.ic_normal_share_svg),
                        onClick = { }
                    )
                },
                onClickBack = {}
            )
        }
    }
}