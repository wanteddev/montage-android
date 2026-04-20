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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.input.search.WantedSearchField
import com.wanted.android.wanted.design.input.search.WantedSearchFieldDefaults.Size
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarContract.Variant
import com.wanted.android.wanted.design.navigations.topbar.view.WantedDisplayTopAppBarLayout
import com.wanted.android.wanted.design.navigations.topbar.view.WantedTopAppBarLayout
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_88

/**
 * WantedTopAppBar
 *
 * 기본 TopAppBar 컴포넌트입니다.
 *
 * 다양한 Variant를 지원하며, 스크롤 상태에 따라 하단 Divider가 표시됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedTopAppBar(
 *     title = { Text(...) },
 *     navigationIcon = { Icon(...) },
 *     actions = { IconButton(...) }
 * )
 * ```
 *
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param windowInsets WindowInsets: 적용할 WindowInsets입니다.
 * @param variant Variant: 앱바 형태입니다.
 * @param backgroundColor Color: 앱바 배경 색상입니다.
 * @param background Boolean: 앱바 배경을 표시할지 여부입니다.
 * @param scrollableState ScrollableState?: 스크롤 상태를 관리하는 객체입니다.
 * @param navigationIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다.
 * @param title (@Composable () -> Unit)?: 타이틀 슬롯입니다.
 * @param actions (@Composable RowScope.() -> Unit)?: 우측 액션 슬롯입니다.
 */
@Composable
fun WantedTopAppBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WantedTopAppBarDefaults.windowInsets,
    variant: Variant = Variant.Normal,
    backgroundColor: Color = DesignSystemTheme.colors.backgroundNormalNormal,
    background: Boolean = true,
    scrollableState: ScrollableState? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null
) {
    val isScrollBackground = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = scrollableState?.canScrollBackward) {
        isScrollBackground.value = scrollableState?.canScrollBackward == true
    }

    Box(
        modifier = when {
            variant == Variant.Floating && isScrollBackground.value
                    || variant == Variant.Floating && background -> {
                modifier
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                backgroundColor.copy(alpha = OPACITY_88),
                                DesignSystemTheme.colors.transparent
                            )
                        )
                    )
                    .padding(bottom = 16.dp)
            }

            !background && !isScrollBackground.value -> {
                modifier.background(DesignSystemTheme.colors.transparent)
            }

            else -> {
                modifier.background(backgroundColor)
            }
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
    }
}

/**
 * WantedTopAppBar
 *
 * 문자열 타이틀을 받는 TopAppBar 컴포넌트입니다.
 *
 * 타이틀 정렬을 좌측 또는 중앙으로 설정할 수 있습니다.
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
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param windowInsets WindowInsets: 적용할 WindowInsets입니다.
 * @param variant Variant: 앱바 형태입니다.
 * @param backgroundColor Color: 앱바 배경 색상입니다.
 * @param background Boolean: 앱바 배경을 표시할지 여부입니다.
 * @param titleAlignCenter Boolean: 타이틀 중앙 정렬 여부입니다.
 * @param scrollableState ScrollableState?: 스크롤 상태를 관리하는 객체입니다.
 * @param title String: 타이틀 텍스트입니다.
 * @param navigationIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다.
 * @param actions (@Composable RowScope.() -> Unit)?: 우측 액션 슬롯입니다.
 */
@Composable
fun WantedTopAppBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WantedTopAppBarDefaults.windowInsets,
    variant: Variant = Variant.Normal,
    backgroundColor: Color = DesignSystemTheme.colors.backgroundNormalNormal,
    background: Boolean = true,
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
            backgroundColor = backgroundColor,
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
            backgroundColor = backgroundColor,
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
 * WantedBackTopAppBar
 *
 * 뒤로 가기 아이콘이 포함된 TopAppBar 컴포넌트입니다.
 *
 * 좌측에 뒤로 가기 아이콘이 고정으로 배치됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedBackTopAppBar(
 *     title = "타이틀",
 *     onClickBack = { /* 뒤로 가기 처리 */ }
 * )
 * ```
 *
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param windowInsets WindowInsets: 적용할 WindowInsets입니다.
 * @param variant Variant: 앱바 형태입니다.
 * @param backgroundColor Color: 앱바 배경 색상입니다.
 * @param background Boolean: 앱바 배경을 표시할지 여부입니다.
 * @param scrollableState ScrollableState?: 스크롤 상태를 관리하는 객체입니다.
 * @param titleAlignCenter Boolean: 타이틀 중앙 정렬 여부입니다.
 * @param title String: 타이틀 텍스트입니다.
 * @param actions (@Composable RowScope.() -> Unit)?: 우측 액션 슬롯입니다.
 * @param onClickBack () -> Unit: 뒤로 가기 버튼 클릭 시 호출되는 콜백입니다.
 */
@Composable
fun WantedBackTopAppBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WantedTopAppBarDefaults.windowInsets,
    variant: Variant = Variant.Normal,
    backgroundColor: Color = DesignSystemTheme.colors.backgroundNormalNormal,
    background: Boolean = true,
    scrollableState: ScrollableState? = null,
    titleAlignCenter: Boolean = false,
    title: String = "",
    actions: @Composable (RowScope.() -> Unit)? = null,
    onClickBack: () -> Unit = {}
) {
    WantedTopAppBar(
        modifier = modifier,
        windowInsets = windowInsets,
        backgroundColor = backgroundColor,
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
    backgroundColor: Color = DesignSystemTheme.colors.backgroundNormalNormal,
    background: Boolean = true,
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
    textStyle: TextStyle = DesignSystemTheme.typography.body1Regular.copy(
        color = if (enabled) {
            DesignSystemTheme.colors.labelNormal
        } else {
            DesignSystemTheme.colors.labelAlternative
        }
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
        backgroundColor = backgroundColor,
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

/**
 * WantedSearchTopAppBar
 *
 * 검색 기능을 제공하는 TopAppBar 컴포넌트입니다.
 *
 * 좌측에 뒤로가기 버튼과 검색 입력 필드를 포함한 상단 앱바로, 검색어 입력, 포커스 관리, 키보드 액션 등의 기능을 제공합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedSearchTopAppBar(
 *     text = searchText,
 *     placeholder = "검색어를 입력하세요",
 *     onValueChange = { newText -> searchText = newText },
 *     onClickBack = { navController.popBackStack() },
 *     actions = {
 *         IconButton(onClick = { /* 검색 */ }) {
 *             Icon(...)
 *         }
 *     }
 * )
 * ```
 *
 * @param text String: 검색 필드에 표시될 텍스트입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param windowInsets WindowInsets: 적용할 WindowInsets입니다.
 * @param backgroundColor Color: 앱바 배경 색상입니다.
 * @param background Boolean: 앱바 배경을 표시할지 여부입니다.
 * @param scrollableState ScrollableState?: 스크롤 상태를 관리하는 객체입니다.
 * @param placeholder String: 검색 필드의 플레이스홀더 텍스트입니다.
 * @param enabled Boolean: 검색 필드 활성화 여부입니다.
 * @param size Size: 검색 필드의 크기입니다.
 * @param maxWordCount Int: 최대 입력 가능한 글자 수입니다.
 * @param enabledOverflowText Boolean: 텍스트 오버플로우 허용 여부입니다.
 * @param interactionSource MutableInteractionSource?: 사용자 인터랙션 상태를 추적하는 객체입니다.
 * @param keyboardOptions KeyboardOptions: 키보드 옵션입니다.
 * @param keyboardActions KeyboardActions: 키보드 액션 핸들러입니다.
 * @param focused State<Boolean>?: 검색 필드의 포커스 상태입니다.
 * @param textStyle TextStyle: 검색 텍스트의 스타일입니다.
 * @param cursorBrush Brush: 커서의 브러시(색상)입니다.
 * @param focusRequester FocusRequester?: 포커스 요청을 위한 객체입니다.
 * @param actions (@Composable RowScope.() -> Unit)?: 우측 액션 슬롯입니다.
 * @param onClickBack () -> Unit: 뒤로가기 버튼 클릭 시 호출되는 콜백입니다.
 * @param onValueChange (String) -> Unit: 검색어 변경 시 호출되는 콜백입니다.
 */
@Composable
fun WantedSearchTopAppBar(
    text: String,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WantedTopAppBarDefaults.windowInsets,
    backgroundColor: Color = DesignSystemTheme.colors.backgroundNormalNormal,
    background: Boolean = true,
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
    textStyle: TextStyle = DesignSystemTheme.typography.body1Regular.copy(
        color = if (enabled) {
            DesignSystemTheme.colors.labelNormal
        } else {
            DesignSystemTheme.colors.labelAlternative
        }
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
        backgroundColor = backgroundColor,
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