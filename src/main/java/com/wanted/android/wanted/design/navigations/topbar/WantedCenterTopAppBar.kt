package com.wanted.android.wanted.design.navigations.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarContract.Variant
import com.wanted.android.wanted.design.navigations.topbar.view.WantedCenterTopAppBarLayout
import com.wanted.android.wanted.design.navigations.topbar.view.WantedDisplayTopAppBarLayout
import com.wanted.android.wanted.design.navigations.topbar.view.WantedOverLayoutDivider
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * 중앙 정렬된 타이틀을 포함하는 상단 앱바입니다.
 *
 * 앱바 유형(Normal, Extended)에 따라 레이아웃이 달라지며, 아이콘, 타이틀, 액션을 설정할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedCenterTopAppBar(
 *     title = { Text("타이틀") },
 *     navigationIcon = { Icon(...) },
 *     actions = { IconButton(...) }
 * )
 * ```
 *
 * @param modifier Modifier: 앱바 외형 및 배치를 조정하는 Modifier입니다.
 * @param windowInsets WindowInsets: 인셋을 적용하여 상태바 등 시스템 UI를 고려한 여백을 처리합니다.
 * @param background Color: 앱바의 배경 색상입니다.
 * @param variant Variant: 앱바의 유형으로 Normal 또는 Extended를 설정할 수 있습니다.
 * @param scrollableState ScrollableState?: 스크롤 상태를 반영하여 divider 표시 여부를 조절합니다.
 * @param navigationIcon @Composable (() -> Unit)?: 좌측 아이콘 영역에 표시할 컴포저블입니다.
 * @param title @Composable (() -> Unit)?: 중앙 타이틀 영역에 표시할 컴포저블입니다.
 * @param actions @Composable RowScope.() -> Unit: 우측 액션 버튼 영역입니다.
 */

@Composable
private fun WantedCenterTopAppBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WantedTopAppBarDefaults.windowInsets,
    background: Color = colorResource(id = R.color.background_normal_normal),
    variant: Variant = Variant.Normal,
    scrollableState: ScrollableState? = null,
    title: String = "",
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null
) {

    WantedCenterTopAppBar(
        modifier = modifier,
        windowInsets = windowInsets,
        background = background,
        variant = variant,
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

@Composable
private fun WantedCenterBackTopAppBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WantedTopAppBarDefaults.windowInsets,
    background: Color = colorResource(id = R.color.background_elevated_normal),
    variant: Variant = Variant.Normal,
    scrollableState: ScrollableState? = null,
    title: String = "",
    actions: @Composable (RowScope.() -> Unit)? = null,
    onClickBack: () -> Unit = {}
) {
    WantedCenterTopAppBar(
        modifier = modifier,
        windowInsets = windowInsets,
        background = background,
        variant = variant,
        scrollableState = scrollableState,
        navigationIcon = {
            WantedTopAppBarIconButton(
                painter = painterResource(id = R.drawable.icon_normal_arrow_left),
                onClick = { onClickBack() }
            )
        },
        title = title,
        actions = actions
    )
}

@Composable
fun WantedCenterTopAppBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WantedTopAppBarDefaults.windowInsets,
    background: Color = colorResource(R.color.background_normal_normal),
    variant: Variant = Variant.Normal,
    scrollableState: ScrollableState? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null
) {
    val isShowDivider = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = scrollableState?.canScrollBackward) {
        isShowDivider.value = scrollableState?.canScrollBackward == true
    }

    Box(
        modifier = modifier.background(background)
    ) {
        CompositionLocalProvider(LocalWantedTopBarIconVariant.provides(variant)) {
            when (variant) {
                Variant.Normal -> {
                    WantedCenterTopAppBarLayout(
                        modifier = Modifier.windowInsetsPadding(windowInsets),
                        navigationIcon = navigationIcon,
                        title = title,
                        actions = actions
                    )
                }

                Variant.Display -> {
                    WantedDisplayTopAppBarLayout(
                        modifier = Modifier.windowInsetsPadding(windowInsets),
                        navigationIcon = navigationIcon,
                        title = title,
                        actions = actions
                    )
                }

                else -> {
                    WantedCenterTopAppBarLayout(
                        modifier = Modifier.windowInsetsPadding(windowInsets),
                        navigationIcon = navigationIcon,
                        title = title,
                        actions = actions
                    )
                }
            }
        }

        if (isShowDivider.value) {
            WantedOverLayoutDivider(
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}


@DevicePreviews
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
                        painter = painterResource(id = R.drawable.icon_normal_arrow_left),
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