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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.WantedTextStyle

/**
 * 중앙 정렬 타이틀을 가진 다이얼로그용 앱바입니다.
 *
 * 일반 앱바와 다르게 타이틀이 가운데 정렬되며 다양한 스타일을 지원합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedDialogCenterTopAppBar(
 *     title = "다이얼로그 제목",
 *     actions = { IconButton(...) }
 * )
 * ```
 *
 * @param modifier Modifier: 앱바 외형 및 배치를 조정하는 Modifier입니다.
 * @param windowInsets WindowInsets: 인셋 정보를 적용합니다.
 * @param background Color: 앱바 배경 색상입니다.
 * @param type TopAppBarType: 앱바 유형 (Normal, Floating, Extended)입니다.
 * @param scrollableState ScrollableState?: 스크롤 상태입니다.
 * @param title String: 타이틀 텍스트입니다.
 * @param navigationIcon @Composable (() -> Unit)?: 좌측 아이콘 컴포저블입니다.
 * @param actions @Composable (RowScope.() -> Unit)?: 우측 액션 컴포저블입니다.
 */
@Composable
fun WantedDialogCenterTopAppBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WantedTopAppBarDefaults.windowInsets,
    background: Color = colorResource(id = R.color.background_elevated_normal),
    type: WantedTopAppBarContract.TopAppBarType = WantedTopAppBarContract.TopAppBarType.Normal,
    scrollableState: ScrollableState? = null,
    title: String = "",
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null
) {
    WantedCenterTopAppBar(
        modifier = modifier,
        windowInsets = windowInsets,
        background = background,
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

/**
 * 중앙 정렬 타이틀과 닫기 버튼이 포함된 다이얼로그용 앱바입니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedDialogCenterCloseTopAppBar(
 *     title = "제목",
 *     onClickBack = { /* 닫기 처리 */ }
 * )
 * ```
 *
 * @param modifier Modifier: 앱바 외형 및 배치를 조정하는 Modifier입니다.
 * @param windowInsets WindowInsets: 인셋 정보를 적용합니다.
 * @param background Color: 앱바 배경 색상입니다.
 * @param type TopAppBarType: 앱바 유형 (Normal, Floating, Extended)입니다.
 * @param scrollableState ScrollableState?: 스크롤 상태입니다.
 * @param title String: 중앙 타이틀 텍스트입니다.
 * @param navigationIcon @Composable (() -> Unit)?: 좌측 아이콘 컴포저블입니다.
 * @param onClickClose () -> Unit: 닫기 아이콘 클릭 시 콜백입니다.
 */
@Composable
fun WantedDialogCenterCloseTopAppBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WantedTopAppBarDefaults.windowInsets,
    background: Color = colorResource(id = R.color.background_elevated_normal),
    type: WantedTopAppBarContract.TopAppBarType = WantedTopAppBarContract.TopAppBarType.Normal,
    scrollableState: ScrollableState? = null,
    title: String = "",
    navigationIcon: @Composable (() -> Unit)? = null,
    onClickClose: () -> Unit = {}
) {
    WantedCenterTopAppBar(
        modifier = modifier,
        windowInsets = windowInsets,
        background = background,
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
                    type = WantedTopAppBarContract.TopAppBarType.Floating,
                )
            }

            WantedDialogCenterTopAppBar(
                type = WantedTopAppBarContract.TopAppBarType.Extended,
                title = "title",
            )

            WantedDialogCenterCloseTopAppBar(
                title = "title",
                onClickClose = { }
            )

            Box(Modifier.background(Color.DarkGray)) {
                WantedDialogCenterCloseTopAppBar(
                    type = WantedTopAppBarContract.TopAppBarType.Floating,
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
                type = WantedTopAppBarContract.TopAppBarType.Extended,
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


