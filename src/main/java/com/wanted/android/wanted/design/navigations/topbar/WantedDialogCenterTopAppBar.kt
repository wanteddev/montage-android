package com.wanted.android.wanted.design.navigations.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.navigations.topbar.view.WantedTopAppBarIconButton
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
fun WantedDialogCenterCloseTopAppBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WantedTopAppBarDefaults.windowInsets,
    type: WantedTopAppBarContract.TopAppBarType = WantedTopAppBarContract.TopAppBarType.Normal,
    scrollableState: ScrollableState? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    title: String = "",
    onClickBack: () -> Unit
) {
    WantedCenterTopAppBar(
        modifier = modifier,
        windowInsets = windowInsets,
        type = type,
        scrollableState = scrollableState,
        navigationIcon = navigationIcon,
        title = {
            if (type == WantedTopAppBarContract.TopAppBarType.Normal) {
                Text(
                    text = title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            } else {
                Text(
                    text = title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = WantedTextStyle(
                        colorRes = R.color.label_strong,
                        style = DesignSystemTheme.typography.headline2Bold
                    )
                )
            }
        },
        actions = {
            WantedTopAppBarIconButton(
                type = type,
                painter = painterResource(id = R.drawable.ic_normal_close_svg),
                onClick = { onClickBack() }
            )
        }
    )
}

@Composable
fun WantedDialogCenterTopAppBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WantedTopAppBarDefaults.windowInsets,
    type: WantedTopAppBarContract.TopAppBarType = WantedTopAppBarContract.TopAppBarType.Normal,
    scrollableState: ScrollableState? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    title: String = "",
    actions: @Composable (RowScope.() -> Unit)? = null
) {
    WantedCenterTopAppBar(
        modifier = modifier,
        windowInsets = windowInsets,
        type = type,
        scrollableState = scrollableState,
        navigationIcon = navigationIcon,
        title = {
            if (type == WantedTopAppBarContract.TopAppBarType.Normal) {
                Text(
                    text = title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            } else {
                Text(
                    text = title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = WantedTextStyle(
                        colorRes = R.color.label_strong,
                        style = DesignSystemTheme.typography.headline2Bold
                    )
                )
            }
        },
        actions = actions
    )
}


@DevicePreviews
@Composable
private fun WantedDialogCenterTopAppBarPreview() {
    DesignSystemTheme {

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            WantedDialogCenterTopAppBar(
                title = "title",
            )

            WantedDialogCenterTopAppBar(
                title = "title",
                navigationIcon = {
                    WantedTopAppBarIconButton(
                        painter = painterResource(id = R.drawable.ic_normal_share_svg),
                        onClick = { }
                    )
                },
            )

            WantedDialogCenterTopAppBar(
                title = "title",
                actions = {
                    WantedTopAppBarIconButton(
                        painter = painterResource(id = R.drawable.ic_normal_share_svg),
                        onClick = { }
                    )
                },
            )

            Box(Modifier.background(Color.DarkGray)) {
                WantedDialogCenterTopAppBar(
                    type = WantedTopAppBarContract.TopAppBarType.Floating,
                )
            }

            WantedDialogCenterTopAppBar(
                type = WantedTopAppBarContract.TopAppBarType.Extended,
                title = "title",
            )

            WantedDialogCenterCloseTopAppBar(
                title = "title",
                onClickBack = { }
            )

            Box(Modifier.background(Color.DarkGray)) {
                WantedDialogCenterCloseTopAppBar(
                    type = WantedTopAppBarContract.TopAppBarType.Floating,
                    navigationIcon = {
                        WantedTopAppBarIconButton(
                            painter = painterResource(id = R.drawable.ic_normal_share_svg),
                            onClick = { }
                        )
                    },
                    onClickBack = { }
                )
            }

            WantedDialogCenterCloseTopAppBar(
                type = WantedTopAppBarContract.TopAppBarType.Extended,
                title = "title",
                navigationIcon = {
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


