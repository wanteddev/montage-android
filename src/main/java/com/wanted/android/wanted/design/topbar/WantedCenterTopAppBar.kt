package com.wanted.android.wanted.design.topbar

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.topbar.WantedTopAppBarContract.TopAppBarType
import com.wanted.android.wanted.design.topbar.view.WantedCenterTopAppBarLayout
import com.wanted.android.wanted.design.topbar.view.WantedTopAppBarIconButton

/**
 * figma : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=14852-43366&m=dev
 * 설명 figma : https://www.figma.com/design/MK6KmtXBxX7ZkoQXfD9MFH/%EA%B0%9C%EC%84%A0%3A-Components?node-id=1330-37845&t=KJWIkEkkcHKMDAcN-4
 */
@Composable
private fun WantedCenterTopAppBar(
    modifier: Modifier = Modifier,
    type: TopAppBarType = TopAppBarType.Normal,
    scrollableState: ScrollableState? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    title: String = "",
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

    Surface(
        elevation = elevation.intValue.dp
    ) {
        when (type) {
            TopAppBarType.Normal -> {
                WantedCenterTopAppBar(
                    modifier = modifier.background(colorResource(id = R.color.background_normal_normal)),
                    navigationIcon = navigationIcon,
                    title = title,
                    actions = actions
                )
            }

            TopAppBarType.Floating -> {
                WantedCenterTopAppBar(
                    modifier = modifier.background(Color.Transparent),
                    navigationIcon = navigationIcon,
                    title = title,
                    actions = actions
                )
            }

            TopAppBarType.Extended -> {
                WantedExtendedCenterTopAppBarLayout(
                    modifier = modifier.background(Color.Transparent),
                    navigationIcon = navigationIcon,
                    title = title,
                    actions = actions
                )
            }
        }
    }
}

@Composable
fun WantedCenterTopAppBar(
    modifier: Modifier = Modifier,
    type: TopAppBarType = TopAppBarType.Normal,
    scrollableState: ScrollableState? = null,
    title: String = "",
    actions: @Composable (RowScope.() -> Unit)? = null,
    onClickBack: () -> Unit
) {
    WantedCenterTopAppBar(
        modifier = modifier,
        type = type,
        scrollableState = scrollableState,
        navigationIcon = {
            WantedTopAppBarIconButton(
                painter = painterResource(id = R.drawable.ic_normal_arrow_left_svg),
                onClick = { onClickBack() }
            )
        },
        title = title,
        actions = actions
    )
}

@Composable
private fun WantedCenterTopAppBar(
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    title: String = "",
    actions: @Composable (RowScope.() -> Unit)? = null
) {
    val height = remember { mutableFloatStateOf(56f) }
    val localDensity = LocalDensity.current
    WantedCenterTopAppBarLayout(
        modifier = modifier.fillMaxWidth(),
        heightPx = height.floatValue,
        navigationIcon = {
            Row {
                navigationIcon?.let {
                    Box(
                        modifier = Modifier
                            .size(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        navigationIcon()
                    }

                    Spacer(modifier = Modifier.size(12.dp))
                } ?: kotlin.run {
                    Spacer(modifier = Modifier.size(12.dp))
                }
            }
        },
        title = {
            Text(
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    height.floatValue = with(localDensity) {
                        coordinates.size.height.toFloat() + 28.dp.toPx()
                    }
                },
                text = title
            )
        },
        actions = {
            actions?.let {
                Row(
                    modifier = Modifier.wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    actions()
                }
            }
        }
    )
}

@Composable
private fun WantedExtendedCenterTopAppBarLayout(
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    title: String = "",
    actions: @Composable (RowScope.() -> Unit)? = null
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
private fun WantedCenterTopAppBarPreview() {
    DesignSystemTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.background_normal_normal)),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            WantedCenterTopAppBar(
                title = "타이틀",
                actions = {
                    WantedTopAppBarIconButton(
                        painter = painterResource(id = R.drawable.ic_normal_share_svg),
                        onClick = { }
                    )
                    WantedTopAppBarIconButton(
                        painter = painterResource(id = R.drawable.ic_normal_share_svg),
                        onClick = { }
                    )
                }
            )

            WantedCenterTopAppBar(
                navigationIcon = {
                    WantedTopAppBarIconButton(
                        painter = painterResource(id = R.drawable.ic_normal_arrow_left_svg),
                        onClick = { }
                    )
                },
                title = "타이틀",
                actions = {
                    WantedTopAppBarIconButton(
                        painter = painterResource(id = R.drawable.ic_normal_share_svg),
                        onClick = { }
                    )
                    WantedTopAppBarIconButton(
                        painter = painterResource(id = R.drawable.ic_normal_share_svg),
                        onClick = { }
                    )
                }
            )

        }
    }
}