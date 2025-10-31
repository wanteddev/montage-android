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
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarContract.Variant
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.WantedTextStyle

/**
 * 다이얼로그 스타일의 일반 앱바입니다.
 *
 * 다이얼로그에서 사용하는 상단 앱바 형태로, 타이틀 및 좌우 컴포넌트를 설정할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedDialogTopAppBar(
 *     title = "다이얼로그 제목",
 *     navigationIcon = { Icon(...) },
 *     actions = { IconButton(...) }
 * )
 * ```
 *
 * @param modifier Modifier: 앱바 외형 및 배치를 조정하는 Modifier입니다.
 * @param windowInsets WindowInsets: 인셋 정보를 적용합니다.
 * @param background Color: 앱바의 배경 색상입니다.
 * @param variant Variant: 앱바 스타일 (Normal, Floating, Extended)입니다.
 * @param scrollableState ScrollableState?: 스크롤 상태를 기반으로 Divider를 제어합니다.
 * @param title String: 타이틀 텍스트입니다.
 * @param navigationIcon @Composable (() -> Unit)?: 좌측 아이콘 컴포저블입니다.
 * @param actions @Composable RowScope.() -> Unit?: 우측 액션 아이콘 영역입니다.
 */
@Composable
fun WantedDialogTopAppBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WindowInsets(0),
    background: Color = colorResource(R.color.background_elevated_normal),
    variant: Variant = Variant.Normal,
    scrollableState: ScrollableState? = null,
    title: String = "",
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null
) {
    WantedTopAppBar(
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
 * 닫기 버튼이 포함된 다이얼로그 앱바입니다.
 *
 * 우측에 닫기 아이콘을 고정 배치하며 클릭 콜백을 받을 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedDialogCloseTopAppBar(
 *     title = "제목",
 *     onClickBack = { /* 닫기 동작 */ }
 * )
 * ```
 *
 * @param modifier Modifier: 앱바 외형 및 배치를 조정하는 Modifier입니다.
 * @param windowInsets WindowInsets: 인셋 정보를 적용합니다.
 * @param background Color: 앱바의 배경 색상입니다.
 * @param variant Variant: 앱바 스타일 (Normal, Floating, Extended)입니다.
 * @param scrollableState ScrollableState?: 스크롤 상태를 기반으로 Divider를 제어합니다.
 * @param navigationIcon @Composable (() -> Unit)?: 좌측 아이콘 컴포저블입니다.
 * @param title String: 타이틀 텍스트입니다.
 * @param onClickClose () -> Unit: 닫기 아이콘 클릭 시 실행되는 콜백입니다.
 */
@Composable
fun WantedDialogCloseTopAppBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WindowInsets(0),
    background: Color = colorResource(R.color.background_elevated_normal),
    variant: Variant = Variant.Normal,
    scrollableState: ScrollableState? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    title: String = "",
    onClickClose: () -> Unit = {}
) {
    WantedTopAppBar(
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
                    style = WantedTextStyle(
                        colorRes = R.color.label_strong,
                        style = DesignSystemTheme.typography.headline2Bold
                    )
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
private fun CustomTopAppBarPreview() {
    DesignSystemTheme {

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            WantedDialogTopAppBar(
                title = "title",
            )

            WantedDialogTopAppBar(
                title = "title",
                navigationIcon = {
                    WantedTopAppBarIconButton(
                        painter = painterResource(id = R.drawable.icon_normal_share),
                        onClick = { }
                    )
                },
            )

            WantedDialogTopAppBar(
                title = "title",
                actions = {
                    WantedTopAppBarIconButton(
                        painter = painterResource(id = R.drawable.icon_normal_share),
                        onClick = { }
                    )
                },
            )

            Box(Modifier.background(Color.DarkGray)) {
                WantedDialogTopAppBar(
                    variant = Variant.Floating,
                )
            }

            WantedDialogTopAppBar(
                variant = Variant.Display,
                title = "title",
            )

            WantedDialogCloseTopAppBar(
                title = "title",
                onClickClose = { }
            )

            Box(Modifier.background(Color.DarkGray)) {
                WantedDialogCloseTopAppBar(
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

            WantedDialogCloseTopAppBar(
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


