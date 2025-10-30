package com.wanted.android.wanted.design.navigations.topbar

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.input.search.WantedSearchField
import com.wanted.android.wanted.design.input.search.WantedSearchFieldConstant.Size
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarContract.Variant
import com.wanted.android.wanted.design.navigations.topbar.view.WantedDisplayTopAppBarLayout
import com.wanted.android.wanted.design.navigations.topbar.view.WantedOverLayoutDivider
import com.wanted.android.wanted.design.navigations.topbar.view.WantedTopAppBarLayout
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle


/**
 * 일반 TopAppBar 형식을 제공합니다. 정렬/타입에 따라 내부 레이아웃이 달라집니다.
 *
 * @param modifier Modifier: 외형 및 배치를 위한 Modifier입니다.
 * @param windowInsets WindowInsets: 인셋을 적용합니다.
 * @param variant Variant: 앱바 유형(Normal, Floating, Display, Search)입니다.
 * @param background Color: 앱바 배경 색상입니다.
 * @param scrollableState ScrollableState?: 스크롤 상태 정보입니다.
 * @param title @Composable (() -> Unit)?: 타이틀 컴포저블입니다.
 * @param navigationIcon @Composable (() -> Unit)?: 좌측 아이콘 컴포저블입니다.
 * @param actions @Composable RowScope.() -> Unit: 우측 액션 영역입니다.
 */
@Composable
fun WantedTopAppBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WantedTopAppBarDefaults.windowInsets,
    variant: Variant = Variant.Normal,
    background: Color = colorResource(R.color.background_normal_normal),
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
        modifier = if (variant == Variant.Floating) {
            modifier.background(
                Brush.verticalGradient(
                    colors = listOf(background, colorResource(R.color.transparent))
                )
            )
        } else {
            modifier.background(background)
        }
    ) {
        CompositionLocalProvider(LocalWantedTopBarIconVariant.provides(variant)) {
            when (variant) {
                Variant.Normal -> {
                    WantedTopAppBarLayout(
                        modifier = Modifier
                            .windowInsetsPadding(windowInsets),
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

                Variant.Floating -> {
                    WantedTopAppBarLayout(
                        modifier = Modifier.windowInsetsPadding(windowInsets),
                        navigationIcon = navigationIcon,
                        title = title,
                        actions = actions
                    )
                }

                Variant.Search -> {
                    WantedTopAppBarLayout(
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

/**
 * 통합 상단 앱바 컴포저블로, 일반형, Floating형, Extended형을 포함합니다.
 *
 * 타이틀 정렬, 배경, 스크롤 상태, 좌우 아이콘 등을 설정할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedTopAppBar(
 *     title = "타이틀",
 *     navigationIcon = { Icon(...) },
 *     actions = { IconButton(...) }
 * )
 * ```
 *
 * @param modifier Modifier: 외형 및 배치를 위한 Modifier입니다.
 * @param windowInsets WindowInsets: 인셋을 적용합니다.
 * @param variant Variant: 앱바 유형(Normal, Floating, Display, Search)입니다.
 * @param background Color: 앱바 배경 색상입니다.
 * @param titleAlignCenter Boolean: 타이틀을 중앙 정렬할지 여부입니다.
 * @param scrollableState ScrollableState?: 스크롤 상태 정보입니다.
 * @param title String: 타이틀로 표시할 텍스트입니다.
 * @param navigationIcon @Composable (() -> Unit)?: 좌측 아이콘 컴포저블입니다.
 * @param actions @Composable RowScope.() -> Unit: 우측 액션 영역입니다.
 */
@Composable
fun WantedTopAppBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WantedTopAppBarDefaults.windowInsets,
    variant: Variant = Variant.Normal,
    background: Color = colorResource(R.color.background_normal_normal),
    titleAlignCenter: Boolean = false,
    scrollableState: ScrollableState? = null,
    title: String = "",
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null
) {
    if (titleAlignCenter) {
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
    } else {
        WantedTopAppBar(
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
}

/**
 * 뒤로 가기 아이콘이 포함된 앱바를 제공합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedBackTopAppBar(
 *     title = "타이틀",
 *     onClickBack = { /* 뒤로가기 처리 */ }
 * )
 * ```
 *
 * @param modifier Modifier: 외형 및 배치를 위한 Modifier입니다.
 * @param windowInsets WindowInsets: 인셋을 적용합니다.
 * @param variant Variant: 앱바 유형(Normal, Floating, Display, Search)입니다.
 * @param background Color: 앱바 배경 색상입니다.
 * @param titleAlignCenter Boolean: 타이틀을 중앙 정렬할지 여부입니다.
 * @param scrollableState ScrollableState?: 스크롤 상태 정보입니다.
 * @param title String: 타이틀로 표시할 텍스트입니다.
 * @param actions @Composable RowScope.() -> Unit: 우측 액션 영역입니다.
 * @param onClickBack () -> Unit: 뒤로가기 아이콘 클릭 시 호출되는 콜백입니다.
 */
@Composable
fun WantedBackTopAppBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WantedTopAppBarDefaults.windowInsets,
    variant: Variant = Variant.Normal,
    background: Color = colorResource(R.color.background_normal_normal),
    scrollableState: ScrollableState? = null,
    titleAlignCenter: Boolean = false,
    title: String = "",
    actions: @Composable (RowScope.() -> Unit)? = null,
    onClickBack: () -> Unit = {}
) {
    WantedTopAppBar(
        modifier = modifier,
        windowInsets = windowInsets,
        background = background,
        variant = variant,
        scrollableState = scrollableState,
        titleAlignCenter = titleAlignCenter,
        navigationIcon = {
            WantedTopAppBarIconButton(
                variant = variant,
                painter = painterResource(id = R.drawable.icon_normal_arrow_left),
                onClick = { onClickBack() }
            )
        },
        title = title,
        actions = actions
    )
}

@Composable
fun WantedSearchTopAppBar(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WantedTopAppBarDefaults.windowInsets,
    background: Color = colorResource(R.color.background_normal_normal),
    scrollableState: ScrollableState? = null,
    placeholder: String = "",
    enabled: Boolean = true,
    size: Size = Size.Medium(),
    maxWordCount: Int = Int.MAX_VALUE,
    enabledOverflowText: Boolean = false,
    interactionSource: MutableInteractionSource? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    focused: State<Boolean>? = null,
    textStyle: TextStyle = WantedTextStyle(
        colorRes = if (enabled) R.color.label_normal else R.color.label_alternative,
        style = DesignSystemTheme.typography.body1Regular
    ),
    cursorBrush: Brush = SolidColor(textStyle.color),
    focusRequester: FocusRequester? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    onClickBack: () -> Unit = {},
    onValueChange: (TextFieldValue) -> Unit = {}
) {

    val localInteractionSource = interactionSource ?: remember { MutableInteractionSource() }
    val localFocused = focused ?: localInteractionSource.collectIsFocusedAsState()
    val localFocusRequester = focusRequester ?: remember { FocusRequester() }

    WantedTopAppBar(
        modifier = modifier,
        windowInsets = windowInsets,
        background = background,
        variant = Variant.Search,
        scrollableState = scrollableState,
        navigationIcon = {
            WantedTopAppBarIconButton(
                variant = Variant.Search,
                painter = painterResource(id = R.drawable.icon_normal_arrow_left),
                onClick = { onClickBack() }
            )
        },
        title = {
            WantedSearchField(
                value = value,
                placeholder = placeholder,
                size = size,
                enabled = enabled,
                maxWordCount = maxWordCount,
                enabledOverflowText = enabledOverflowText,
                interactionSource = localInteractionSource,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                focused = localFocused,
                cursorBrush = cursorBrush,
                textStyle = textStyle,
                focusRequester = localFocusRequester,
                onValueChange = onValueChange
            )
        },
        actions = actions
    )
}

@Composable
fun WantedSearchTopAppBar(
    text: String,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WantedTopAppBarDefaults.windowInsets,
    background: Color = colorResource(R.color.background_normal_normal),
    scrollableState: ScrollableState? = null,
    placeholder: String = "",
    enabled: Boolean = true,
    size: Size = Size.Small(),
    maxWordCount: Int = Int.MAX_VALUE,
    enabledOverflowText: Boolean = false,
    interactionSource: MutableInteractionSource? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    focused: State<Boolean>? = null,
    textStyle: TextStyle = WantedTextStyle(
        colorRes = if (enabled) R.color.label_normal else R.color.label_alternative,
        style = DesignSystemTheme.typography.body1Regular
    ),
    cursorBrush: Brush = SolidColor(textStyle.color),
    focusRequester: FocusRequester? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    onClickBack: () -> Unit = {},
    onValueChange: (String) -> Unit = {}
) {

    val localInteractionSource = interactionSource ?: remember { MutableInteractionSource() }
    val localFocused = focused ?: localInteractionSource.collectIsFocusedAsState()
    val localFocusRequester = focusRequester ?: remember { FocusRequester() }

    WantedTopAppBar(
        modifier = modifier,
        windowInsets = windowInsets,
        background = background,
        variant = Variant.Search,
        scrollableState = scrollableState,
        navigationIcon = {
            WantedTopAppBarIconButton(
                variant = Variant.Search,
                painter = painterResource(id = R.drawable.icon_normal_arrow_left),
                onClick = { onClickBack() }
            )
        },
        title = {
            WantedSearchField(
                text = text,
                placeholder = placeholder,
                size = size,
                enabled = enabled,
                maxWordCount = maxWordCount,
                enabledOverflowText = enabledOverflowText,
                interactionSource = localInteractionSource,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                focused = localFocused,
                cursorBrush = cursorBrush,
                textStyle = textStyle,
                focusRequester = localFocusRequester,
                onValueChange = onValueChange
            )
        },
        actions = actions
    )
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
                        painter = painterResource(id = R.drawable.icon_normal_share),
                        onClick = { }
                    )
                }
            )

            Box(Modifier.background(Color.DarkGray)) {
                WantedTopAppBar(
                    variant = Variant.Floating,
                    actions = {}
                )
            }

            WantedTopAppBar(
                variant = Variant.Display,
                title = "title",
                actions = {}
            )

            WantedBackTopAppBar(
                title = "title",
                onClickBack = {}
            )

            Box(Modifier.background(Color.DarkGray)) {
                WantedBackTopAppBar(
                    variant = Variant.Floating,
                    actions = {
                        WantedTopAppBarIconButton(
                            painter = painterResource(id = R.drawable.icon_normal_share),
                            onClick = { }
                        )
                        WantedTopAppBarIconButton(
                            painter = painterResource(id = R.drawable.icon_normal_share),
                            onClick = { }
                        )
                    },
                    onClickBack = { }
                )
            }
            Box(Modifier.background(Color.White)) {
                WantedBackTopAppBar(
                    variant = Variant.Floating,
                    actions = {
                        WantedTopAppBarIconButton(
                            painter = painterResource(id = R.drawable.icon_normal_share),
                            onClick = { }
                        )

                        WantedTopAppBarIconButton(
                            painter = painterResource(id = R.drawable.icon_normal_share),
                            onClick = { }
                        )
                        WantedTopAppBarIconButton(
                            painter = painterResource(id = R.drawable.icon_normal_share),
                            onClick = { }
                        )
                    },
                    onClickBack = { }
                )
            }


            WantedBackTopAppBar(
                variant = Variant.Display,
                title = "title",
                actions = {
                    WantedTopAppBarIconButton(
                        painter = painterResource(id = R.drawable.icon_normal_share),
                        onClick = { }
                    )
                },
                onClickBack = {}
            )
        }
    }
}