package com.wanted.android.wanted.design.topbar

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.topbar.view.LocalWantedTopBarIconType
import com.wanted.android.wanted.design.topbar.view.WantedTopAppBarLayout
import com.wanted.android.wanted.design.util.getStatusBarHeight
import com.wanted.android.wanted.design.util.pxToDp
import kotlin.math.roundToInt


@Composable
fun WantedCollapsedScrollTopAppBar(
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = false,
    nestedConnection: WantedCollapsedScrollNestedConnection,
    type: WantedTopAppBarContract.TopAppBarType = WantedTopAppBarContract.TopAppBarType.Normal,
    scrollableState: LazyListState,
    title: @Composable (() -> Unit)? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    header: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val elevation = remember { mutableIntStateOf(0) }
    LaunchedEffect(key1 = scrollableState.canScrollBackward) {
        if (scrollableState.canScrollBackward) {
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
    val contentHeight = remember { mutableFloatStateOf(0f) }
    val localDensity = LocalDensity.current

    val topAppBarOffsetHeightPx = remember { mutableFloatStateOf(0f) }

    nestedConnection.onPreScroll = { available: Offset ->
        val delta = available.y
        val newOffset = topAppBarOffsetHeightPx.floatValue + delta
        topAppBarOffsetHeightPx.floatValue =
            newOffset.coerceIn(-contentHeight.floatValue, 0f)

//        Offset.Zero
        if (newOffset > -400) {
            Offset(x = 0f, y = available.y)
        } else {
            Offset.Zero
        }

    }
    Column(
        modifier = Modifier,
    ) {
        Surface(
            modifier = modifier
                .then(layoutModifier)
                .wrapContentHeight()
                .offset {
                    IntOffset(x = 0, y = topAppBarOffsetHeightPx.floatValue.roundToInt())
                },
            color = Color.Yellow
        ) {

            LazyColumn(
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        contentHeight.floatValue = coordinates.size.height.toFloat()
                    }
                    .nestedScroll(nestedConnection)
                    .background(Color.Blue),
                state = scrollableState
            ) {
                item {
                    header()
                }
            }

            CompositionLocalProvider(LocalWantedTopBarIconType.provides(type)) {
                WantedTopAppBarLayout(
                    modifier = Modifier,
                    navigationIcon = navigationIcon,
                    title = title,
                    actions = actions
                )
            }
        }


        Box(
            modifier = Modifier
                .offset {
                    IntOffset(x = 0, y = topAppBarOffsetHeightPx.floatValue.roundToInt())
                }
                .weight(1f)

        ) {
            content()
        }
    }
}

class WantedCollapsedScrollNestedConnection : NestedScrollConnection {
    var onPreScroll: (available: Offset) -> Offset = {
        Offset.Zero
    }

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        return onPreScroll(available)
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
private fun WantedCollapsedScrollTopAppBarPreview() {
    DesignSystemTheme {
        val lazyListState = rememberLazyListState()
        val nestedConnection = WantedCollapsedScrollNestedConnection()
        Scaffold(
            Modifier.nestedScroll(nestedConnection),
            topBar = {

            }
        ) {
            WantedCollapsedScrollTopAppBar(
                scrollableState = lazyListState,
                nestedConnection = nestedConnection,
                header = {
                    Box(
                        modifier = Modifier
                            .background(Color.Red)
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {

                    }
                },
                content = {
                    LazyColumn(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize(),
                        state = lazyListState,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        items(100) {
                            Box(
                                modifier = Modifier
                                    .background(Color.Blue)
                                    .fillMaxWidth()
                                    .height(100.dp)
                            ) {

                            }
                        }
                    }
                }
            )

        }
    }
}