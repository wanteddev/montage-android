package com.wanted.android.wanted.design.navigations.topbar.dialogtopbar

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.button.iconbutton.IconButtonInteractionEffect
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonBackground
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonBackgroundSize
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonNormal
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonNormalSize
import com.wanted.android.wanted.design.actions.button.textbutton.WantedTextButton
import com.wanted.android.wanted.design.actions.button.textbutton.WantedTextButtonDefaults
import com.wanted.android.wanted.design.actions.button.textbutton.WantedTextButtonSize
import com.wanted.android.wanted.design.input.search.WantedSearchField
import com.wanted.android.wanted.design.input.search.WantedSearchFieldDefaults
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarDefaults
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarIconButton
import com.wanted.android.wanted.design.navigations.topbar.dialogtopbar.WantedDialogTopAppBarContract.Variant
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_88

/**
 * WantedDialogTopAppBar
 *
 * 다이얼로그용 TopAppBar 컴포넌트입니다.
 *
 * 타이틀과 좌우 컴포넌트를 설정할 수 있습니다.
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
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param windowInsets WindowInsets: 적용할 WindowInsets입니다.
 * @param backgroundColor Color: 앱바 배경 색상입니다.
 * @param background Boolean: 앱바 배경을 표시할지 여부입니다.
 * @param variant Variant: 앱바 형태입니다.
 * @param navigationPadding Dp: Navigation 의 상하좌우 여백입니다. 기본값은 Popup · Bottom Sheet 값(24)이고 Full 은 [WantedDialogTopAppBarDefaults.FULL_NAVIGATION_PADDING] 을 넘깁니다.
 * @param scrollableState ScrollableState?: 스크롤 상태를 관리하는 객체입니다.
 * @param title String: 타이틀 텍스트입니다.
 * @param navigationIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다.
 * @param actions (@Composable RowScope.() -> Unit)?: 우측 액션 슬롯입니다.
 */
@Composable
fun WantedDialogTopAppBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WindowInsets(0),
    backgroundColor: Color = DesignSystemTheme.colors.surfaceElevatedPrimary,
    background: Boolean = true,
    variant: Variant = Variant.Normal,
    navigationPadding: Dp = WantedDialogTopAppBarDefaults.NAVIGATION_PADDING,
    scrollableState: ScrollableState? = null,
    title: String = "",
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null
) {
    WantedDialogTopAppBar(
        modifier = modifier,
        windowInsets = windowInsets,
        backgroundColor = backgroundColor,
        background = background,
        variant = variant,
        navigationPadding = navigationPadding,
        scrollableState = scrollableState,
        navigationIcon = navigationIcon,
        title = {
            Text(
                text = title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        actions = actions
    )
}

/**
 * WantedDialogCloseTopAppBar
 *
 * 닫기 버튼이 포함된 다이얼로그용 TopAppBar 컴포넌트입니다.
 *
 * 우측에 닫기 아이콘이 고정으로 배치됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedDialogCloseTopAppBar(
 *     title = "제목",
 *     onClickClose = { /* 닫기 처리 */ }
 * )
 * ```
 *
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param windowInsets WindowInsets: 적용할 WindowInsets입니다.
 * @param backgroundColor Color: 앱바 배경 색상입니다.
 * @param background Boolean: 앱바 배경을 표시할지 여부입니다.
 * @param variant Variant: 앱바 형태입니다.
 * @param navigationPadding Dp: Navigation 의 상하좌우 여백입니다. 기본값은 Popup · Bottom Sheet 값(24)이고 Full 은 [WantedDialogTopAppBarDefaults.FULL_NAVIGATION_PADDING] 을 넘깁니다.
 * @param scrollableState ScrollableState?: 스크롤 상태를 관리하는 객체입니다.
 * @param navigationIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다.
 * @param title String: 타이틀 텍스트입니다.
 * @param onClickClose () -> Unit: 닫기 버튼 클릭 시 호출되는 콜백입니다.
 */
@Composable
fun WantedDialogCloseTopAppBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WindowInsets(0),
    backgroundColor: Color = DesignSystemTheme.colors.surfaceElevatedPrimary,
    background: Boolean = true,
    variant: Variant = Variant.Normal,
    navigationPadding: Dp = WantedDialogTopAppBarDefaults.NAVIGATION_PADDING,
    scrollableState: ScrollableState? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    title: String = "",
    onClickClose: () -> Unit = {}
) {
    WantedDialogTopAppBar(
        modifier = modifier,
        windowInsets = windowInsets,
        backgroundColor = backgroundColor,
        background = background,
        variant = variant,
        navigationPadding = navigationPadding,
        scrollableState = scrollableState,
        navigationIcon = navigationIcon,
        title = {
            Text(
                text = title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        actions = {
            CloseButton(
                iconBackground = variant.useIconBackground,
                onClick = onClickClose
            )
        }
    )
}

// 모달 Navigation 의 닫기 버튼이다. Top Navigation 의 아이콘 버튼과 규격이 달라 여기서 직접 구성한다 —
// 레이아웃 영역은 24×24 이고 컨테이너 36 은 상하좌우로 6 씩 넘친다. 인터랙션은 Dim 이다.
@Composable
private fun CloseButton(
    iconBackground: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.size(CLOSE_BUTTON_LAYOUT_SIZE),
        contentAlignment = Alignment.Center
    ) {
        if (iconBackground) {
            WantedIconButtonBackground(
                modifier = Modifier.requiredSize(CLOSE_BUTTON_CONTAINER_SIZE),
                icon = R.drawable.icon_normal_close,
                size = WantedIconButtonBackgroundSize.Custom(CLOSE_BUTTON_CONTAINER_SIZE),
                onClick = onClick
            )
        } else {
            WantedIconButtonNormal(
                modifier = Modifier.requiredSize(CLOSE_BUTTON_CONTAINER_SIZE),
                icon = R.drawable.icon_normal_close,
                size = WantedIconButtonNormalSize.Xlarge,
                interactionEffect = IconButtonInteractionEffect.Dim,
                onClick = onClick
            )
        }
    }
}

// Figma(4.0.0) Modal Navigation preset: 레이아웃 영역 24×24, 컨테이너 36 (상하좌우 6 씩 넘침)
private val CLOSE_BUTTON_LAYOUT_SIZE = 24.dp
private val CLOSE_BUTTON_CONTAINER_SIZE = 36.dp

/**
 * WantedDialogSearchTopAppBar
 *
 * 검색 필드가 포함된 다이얼로그용 TopAppBar 컴포넌트입니다. (Variant.Search)
 *
 * 제목 대신 검색 필드(폭 가변)를 두고, 우측에 취소 텍스트 버튼을 배치합니다.
 * 화면용 WantedSearchTopAppBar 와 같은 WantedSearchField(Medium, 높이 40)를 사용합니다.
 *
 * 사용 예시:
 * ```kotlin
 * var keyword by remember { mutableStateOf("") }
 *
 * WantedDialogSearchTopAppBar(
 *     text = keyword,
 *     placeholder = "검색어를 입력해 주세요.",
 *     cancelText = "취소",
 *     onClickCancel = { /* 닫기 처리 */ },
 *     onValueChange = { keyword = it }
 * )
 * ```
 *
 * @param text String: 검색 필드에 입력된 텍스트입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param windowInsets WindowInsets: 적용할 WindowInsets입니다.
 * @param backgroundColor Color: 앱바 배경 색상입니다.
 * @param background Boolean: 앱바 배경을 표시할지 여부입니다.
 * @param navigationPadding Dp: Navigation 의 상하좌우 여백입니다. 기본값은 Popup · Bottom Sheet 값(24)이고 Full 은 [WantedDialogTopAppBarDefaults.FULL_NAVIGATION_PADDING] 을 넘깁니다.
 * @param scrollableState ScrollableState?: 스크롤 상태를 관리하는 객체입니다.
 * @param placeholder String: 검색 필드의 placeholder 입니다.
 * @param enabled Boolean: 검색 필드 입력 가능 여부입니다.
 * @param keyboardOptions KeyboardOptions: 키보드 옵션입니다.
 * @param keyboardActions KeyboardActions: 키보드 액션입니다.
 * @param focusRequester FocusRequester?: 검색 필드 포커스를 제어하는 객체입니다.
 * @param cancelText String: 우측 취소 텍스트 버튼 문구입니다. 빈 문자열이면 버튼을 표시하지 않습니다.
 * @param navigationIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다. 기본값은 없음이며, 넣으면 24 아이콘이 검색 필드 왼쪽에 붙습니다.
 * @param onClickCancel () -> Unit: 취소 버튼 클릭 시 호출되는 콜백입니다.
 * @param onValueChange (String) -> Unit: 검색어 변경 시 호출되는 콜백입니다.
 */
@Composable
fun WantedDialogSearchTopAppBar(
    text: String,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WindowInsets(0),
    backgroundColor: Color = DesignSystemTheme.colors.surfaceElevatedPrimary,
    background: Boolean = true,
    navigationPadding: Dp = WantedDialogTopAppBarDefaults.NAVIGATION_PADDING,
    scrollableState: ScrollableState? = null,
    placeholder: String = "",
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    focusRequester: FocusRequester? = null,
    cancelText: String = "",
    navigationIcon: @Composable (() -> Unit)? = null,
    onClickCancel: () -> Unit = {},
    onValueChange: (String) -> Unit = {}
) {
    val localFocusRequester = focusRequester ?: remember { FocusRequester() }

    WantedDialogTopAppBar(
        modifier = modifier,
        windowInsets = windowInsets,
        variant = Variant.Search,
        backgroundColor = backgroundColor,
        background = background,
        navigationPadding = navigationPadding,
        scrollableState = scrollableState,
        navigationIcon = navigationIcon,
        title = {
            WantedSearchField(
                modifier = Modifier.fillMaxWidth(),
                text = text,
                placeholder = placeholder,
                size = WantedSearchFieldDefaults.Size.Medium,
                enabled = enabled,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                focusRequester = localFocusRequester,
                onValueChange = onValueChange
            )
        },
        actions = if (cancelText.isNotEmpty()) {
            { CancelTextButton(text = cancelText, onClick = onClickCancel) }
        } else {
            null
        }
    )
}

// Modal Navigation Variant=Search 의 Trailing Text 프리셋이다. Headline 2/Regular · foregroundNeutralPrimary 이다.
@Composable
private fun CancelTextButton(
    text: String,
    onClick: () -> Unit
) {
    WantedTextButton(
        text = text,
        size = WantedTextButtonSize.MEDIUM,
        buttonDefault = WantedTextButtonDefaults.getDefault(
            size = WantedTextButtonSize.MEDIUM,
            contentColor = DesignSystemTheme.colors.foregroundNeutralPrimary,
            textStyle = DesignSystemTheme.typography.headline2Regular
        ),
        onClick = onClick
    )
}


/**
 * WantedDialogTopAppBar
 *
 * 닫기 버튼이 포함된 다이얼로그용 TopAppBar 컴포넌트입니다.
 *
 * 우측에 닫기 아이콘이 고정으로 배치됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedDialogTopAppBar(
 *     title = "제목",
 *     navigationIcon = { },
 *     actions = { }
 * )
 * ```
 *
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param windowInsets WindowInsets: 적용할 WindowInsets입니다.
 * @param backgroundColor Color: 앱바 배경 색상입니다.
 * @param background Boolean: 앱바 배경을 표시할지 여부입니다.
 * @param variant Variant: 앱바 형태입니다.
 * @param navigationPadding Dp: Navigation 의 상하좌우 여백입니다. 기본값은 Popup · Bottom Sheet 값(24)이고 Full 은 [WantedDialogTopAppBarDefaults.FULL_NAVIGATION_PADDING] 을 넘깁니다.
 * @param scrollableState ScrollableState?: 스크롤 상태를 관리하는 객체입니다.
 * @param navigationIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다.
 * @param title (@Composable () -> Unit)?: 타이틀 텍스트 슬롯입니다.
 * @param actions (@Composable RowScope.() -> Unit)?: 우측 액션 슬롯입니다.
 */
@Composable
fun WantedDialogTopAppBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WantedTopAppBarDefaults.windowInsets,
    variant: Variant = Variant.Normal,
    backgroundColor: Color = DesignSystemTheme.colors.backgroundNeutralPrimary,
    background: Boolean = true,
    navigationPadding: Dp = WantedDialogTopAppBarDefaults.NAVIGATION_PADDING,
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
            variant is Variant.Floating && isScrollBackground.value
                    || variant is Variant.Floating && background -> {
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
        when (variant) {
            Variant.Normal -> {
                WantedDialogCenterTopAppBarLayout(
                    modifier = Modifier
                        .windowInsetsPadding(windowInsets),
                    navigationIcon = navigationIcon,
                    title = title,
                    actions = actions
                )
            }

            Variant.Emphasized -> {
                WantedDialogTopAppBarLayout(
                    modifier = Modifier.windowInsetsPadding(windowInsets),
                    navigationPadding = navigationPadding,
                    navigationIcon = navigationIcon,
                    title = title,
                    actions = actions
                )
            }

            is Variant.Floating -> {
                WantedDialogCenterTopAppBarLayout(
                    modifier = Modifier
                        .windowInsetsPadding(windowInsets),
                    navigationIcon = navigationIcon,
                    title = title,
                    actions = actions
                )
            }

            Variant.Search -> {
                WantedDialogTopAppBarLayout(
                    modifier = Modifier.windowInsetsPadding(windowInsets),
                    navigationPadding = navigationPadding,
                    contentGap = SEARCH_CONTENT_GAP,
                    navigationIcon = navigationIcon,
                    title = title,
                    actions = actions
                )
            }
        }
    
    }
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

            WantedDialogCloseTopAppBar(
                title = "title",
                onClickClose = { }
            )

            Box(Modifier.background(Color.DarkGray)) {
                WantedDialogCloseTopAppBar(
                    variant = Variant.Floating(),
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
                variant = Variant.Floating(iconBackground = true),
                title = "title",
                onClickClose = {}
            )
        }
    }
}

@DevicePreviews
@Composable
private fun DialogSearchTopAppBarPreview() {
    DesignSystemTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            WantedDialogSearchTopAppBar(
                text = "",
                placeholder = "검색어를 입력해 주세요.",
                cancelText = "취소",
            )

            WantedDialogSearchTopAppBar(
                text = "검색어",
                placeholder = "검색어를 입력해 주세요.",
                cancelText = "취소",
            )

            WantedDialogSearchTopAppBar(
                text = "",
                placeholder = "검색어를 입력해 주세요.",
                cancelText = "취소",
                navigationIcon = {
                    WantedTopAppBarIconButton(
                        painter = painterResource(id = R.drawable.icon_normal_arrow_left),
                        onClick = { }
                    )
                },
            )

            WantedDialogSearchTopAppBar(
                text = "",
                placeholder = "검색어를 입력해 주세요.",
                navigationPadding = WantedDialogTopAppBarDefaults.FULL_NAVIGATION_PADDING,
            )
        }
    }
}


