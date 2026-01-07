package com.wanted.android.wanted.design.navigations.topbar.dialogtopbar

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
import com.wanted.android.wanted.design.navigations.topbar.WantedCenterTopAppBar
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarContract.Variant
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarDefaults
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarIconButton
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * WantedDialogCenterTopAppBar
 *
 * 중앙 정렬 타이틀을 가진 다이얼로그용 Top app bar 컴포넌트입니다.
 *
 * 타이틀이 중앙에 정렬되며, 다양한 Variant를 지원합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedDialogCenterTopAppBar(
 *     title = "다이얼로그 제목",
 *     actions = { IconButton(...) }
 * )
 * ```
 *
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param windowInsets WindowInsets: 적용할 WindowInsets입니다.
 * @param background Color: 앱바 배경 색상입니다.
 * @param variant Variant: 앱바 형태입니다.
 * @param scrollableState ScrollableState?: 스크롤 상태를 관리하는 객체입니다.
 * @param title String: 타이틀 텍스트입니다.
 * @param navigationIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다.
 * @param actions (@Composable RowScope.() -> Unit)?: 우측 액션 슬롯입니다.
 */
@Composable
fun WantedDialogCenterTopAppBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WantedTopAppBarDefaults.windowInsets,
    background: Color = DesignSystemTheme.colors.backgroundElevatedNormal,
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
            if (variant == Variant.Normal) {
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
                    style = DesignSystemTheme.typography.headline2Bold,
                    color = DesignSystemTheme.colors.labelStrong
                )
            }
        },
        actions = actions
    )
}

/**
 * WantedDialogCenterCloseTopAppBar
 *
 * 중앙 정렬 타이틀과 닫기 버튼이 포함된 다이얼로그용 Top app bar 컴포넌트입니다.
 *
 * 우측에 닫기 아이콘이 고정으로 배치됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedDialogCenterCloseTopAppBar(
 *     title = "제목",
 *     onClickClose = { /* 닫기 처리 */ }
 * )
 * ```
 *
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param windowInsets WindowInsets: 적용할 WindowInsets입니다.
 * @param background Color: 앱바 배경 색상입니다.
 * @param variant Variant: 앱바 형태입니다.
 * @param scrollableState ScrollableState?: 스크롤 상태를 관리하는 객체입니다.
 * @param title String: 중앙 타이틀 텍스트입니다.
 * @param navigationIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다.
 * @param onClickClose () -> Unit: 닫기 버튼 클릭 시 호출되는 콜백입니다.
 */
@Composable
fun WantedDialogCenterCloseTopAppBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WantedTopAppBarDefaults.windowInsets,
    background: Color = DesignSystemTheme.colors.backgroundElevatedNormal,
    variant: Variant = Variant.Normal,
    scrollableState: ScrollableState? = null,
    title: String = "",
    navigationIcon: @Composable (() -> Unit)? = null,
    onClickClose: () -> Unit = {}
) {
    WantedCenterTopAppBar(
        modifier = modifier,
        windowInsets = windowInsets,
        background = background,
        variant = variant,
        scrollableState = scrollableState,
        navigationIcon = navigationIcon,
        title = {
            if (variant == Variant.Normal) {
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
                    style = DesignSystemTheme.typography.headline2Bold,
                    color = DesignSystemTheme.colors.labelStrong
                )
            }
        },
        actions = {
            WantedTopAppBarIconButton(
                variant = variant,
                painter = painterResource(id = R.drawable.icon_normal_close),
                onClick = { onClickClose() }
            )
        }
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
                        painter = painterResource(id = R.drawable.icon_normal_share),
                        onClick = { }
                    )
                },
            )

            WantedDialogCenterTopAppBar(
                title = "title",
                actions = {
                    WantedTopAppBarIconButton(
                        painter = painterResource(id = R.drawable.icon_normal_share),
                        onClick = { }
                    )
                },
            )

            Box(Modifier.background(Color.DarkGray)) {
                WantedDialogCenterTopAppBar(
                    variant = Variant.Floating,
                )
            }

            WantedDialogCenterTopAppBar(
                variant = Variant.Display,
                title = "title",
            )

            WantedDialogCenterCloseTopAppBar(
                title = "title",
                onClickClose = { }
            )

            Box(Modifier.background(Color.DarkGray)) {
                WantedDialogCenterCloseTopAppBar(
                    variant = Variant.Floating,
                    navigationIcon = {
                        WantedTopAppBarIconButton(
                            painter = painterResource(id = R.drawable.icon_normal_share),
                            onClick = { }
                        )
                    },
                    onClickClose = { }
                )
            }

            WantedDialogCenterCloseTopAppBar(
                variant = Variant.Display,
                title = "title",
                navigationIcon = {
                    WantedTopAppBarIconButton(
                        painter = painterResource(id = R.drawable.icon_normal_share),
                        onClick = { }
                    )
                },
                onClickClose = {}
            )
        }
    }
}


