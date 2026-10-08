package com.wanted.android.wanted.design.presentation.modal.popup

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarDefaults
import com.wanted.android.wanted.design.navigations.topbar.dialogtopbar.WantedDialogCloseTopAppBar
import com.wanted.android.wanted.design.navigations.topbar.dialogtopbar.WantedDialogTopAppBarContract.Variant
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopupContract.Resize
import com.wanted.android.wanted.design.presentation.modal.view.WantedDialogLayout
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.pxToDp

/**
 * WantedPopup
 *
 * 상단 앱바와 Main Action 버튼을 포함한 기본 Popup 컴포넌트입니다.
 *
 * 사용 예시:
 * ```kotlin
 * var showModal by remember { mutableStateOf(true) }
 *
 * if (showModal) {
 *     WantedPopup(
 *         title = "제목",
 *         positive = "확인",
 *         onClickPositive = { showModal = false },
 *         onDismissRequest = { showModal = false },
 *         content = { Text("내용") }
 *     )
 * }
 * ```
 *
 * @param onDismissRequest () -> Unit: 모달 외부 클릭 등으로 닫힐 때 호출되는 콜백입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param resize Resize: Popup 의 높이 결정 방식입니다. 기본값은 Hug 입니다.
 * @param properties DialogProperties: Dialog 속성입니다.
 * @param popupDefault WantedPopupDefault: Popup 의 모양·여백 설정입니다.
 * @param title String?: Navigation 의 제목입니다. null 이면 제목 없이 Close Button 만 노출합니다. Close Button 은 [onDismissRequest] 로 Popup 을 닫습니다.
 * @param positive String?: Main Action 의 텍스트입니다.
 * @param onClickPositive (() -> Unit)?: Main Action 클릭 시 호출되는 콜백입니다.
 * @param content (@Composable BoxScope.() -> Unit): 본문 콘텐츠 슬롯입니다.
 */
@Composable
fun WantedPopup(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    resize: Resize = Resize.Hug,
    properties: DialogProperties = DialogProperties(),
    popupDefault: WantedPopupDefault = WantedPopupDefaults.getDefault(),
    title: String? = null,
    positive: String? = null,
    onClickPositive: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    WantedPopup(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        resize = resize,
        properties = properties,
        popupDefault = popupDefault,
        topBar = {
            // 제목이 없어도 Close Button 은 항상 노출한다. 제목은 4.0.0 기준 좌측 정렬(Emphasized) 고정이다.
            WantedDialogCloseTopAppBar(
                variant = Variant.Emphasized,
                title = title.orEmpty(),
                onClickClose = onDismissRequest
            )
        },
        bottomBar = onClickPositive?.let {
            {
                // 여백은 Popup 이 actionPadding 으로 넣으므로 Action Area 의 safeArea 여백은 끈다.
                WantedActionArea(
                    type = popupDefault.actionAreaType,
                    main = positive.orEmpty(),
                    onClickMain = onClickPositive,
                    safeArea = false,
                    divider = false
                )
            }
        },
        content = content
    )
}

/**
 * WantedPopup
 *
 * 커스텀 하단 바를 포함한 모달 컴포넌트입니다.
 *
 * Main Action 버튼 대신 커스텀 bottomBar를 사용할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * var showModal by remember { mutableStateOf(true) }
 *
 * if (showModal) {
 *     WantedPopup(
 *         topBar = { WantedDialogTopAppBar(title = "제목") },
 *         bottomBar = {
 *             Button(onClick = { showModal = false }) {
 *                 Text("닫기")
 *             }
 *         },
 *         onDismissRequest = { showModal = false },
 *         content = { Text("내용") }
 *     )
 * }
 * ```
 *
 * @param onDismissRequest () -> Unit: 모달 외부 클릭 등으로 닫힐 때 호출되는 콜백입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param resize Resize: Popup 의 높이 결정 방식입니다. 기본값은 Hug 입니다.
 * @param properties DialogProperties: Dialog 속성입니다.
 * @param popupDefault WantedPopupDefault: Popup 의 모양·여백 설정입니다.
 * @param topBar (@Composable () -> Unit)?: 상단 앱바 슬롯입니다.
 * @param bottomBar (@Composable () -> Unit)?: 하단 바 슬롯입니다.
 * @param content (@Composable BoxScope.() -> Unit): 본문 콘텐츠 슬롯입니다.
 */
@Composable
fun WantedPopup(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    resize: Resize = Resize.Hug,
    properties: DialogProperties = DialogProperties(),
    popupDefault: WantedPopupDefault = WantedPopupDefaults.getDefault(),
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = properties
    ) {
        WantedDialogLayout(
            modifier = modifier
                .widthIn(max = popupDefault.width)
                .then(heightModifier(resize, WantedModalContract.MAX_MODAL_SIZE.dp)),
            shape = popupDefault.shape,
            topBarPadding = PaddingValues(popupDefault.navigationPadding),
            bottomBarPadding = popupDefault.actionPadding,
            // Fixed 는 높이가 고정이므로 Content 가 남은 공간을 채워야 Action Area 가 하단에 붙는다.
            contentFillHeight = resize is Resize.Fixed,
            topBar = topBar,
            content = {
                Box(
                    modifier = Modifier
                        .then(if (resize is Resize.Fixed) Modifier.fillMaxSize() else Modifier)
                        .padding(
                            horizontal = popupDefault.contentHorizontalPadding,
                            vertical = popupDefault.contentVerticalPadding
                        )
                ) {
                    content()
                }
            },
            bottomBar = bottomBar
        )
    }
}

/**
 * WantedPopup
 *
 * LazyColumn 기반의 스크롤 가능한 모달 컴포넌트입니다.
 *
 * 많은 양의 콘텐츠를 스크롤하여 표시할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * var showModal by remember { mutableStateOf(true) }
 *
 * if (showModal) {
 *     WantedPopup(
 *         topBar = { WantedDialogTopAppBar(title = "제목") },
 *         onDismissRequest = { showModal = false },
 *         lazyContent = {
 *             items(20) { index ->
 *                 Text("아이템 $index")
 *             }
 *         }
 *     )
 * }
 * ```
 *
 * @param onDismissRequest () -> Unit: 모달 외부 클릭 등으로 닫힐 때 호출되는 콜백입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param resize Resize: Popup 의 높이 결정 방식입니다. 기본값은 Hug 입니다.
 * @param properties DialogProperties: Dialog 속성입니다.
 * @param popupDefault WantedPopupDefault: Popup 의 모양·여백 설정입니다.
 * @param topBar (@Composable () -> Unit)?: 상단 앱바 슬롯입니다.
 * @param bottomBar (@Composable () -> Unit)?: 하단 바 슬롯입니다.
 * @param lazyContent (LazyListScope.() -> Unit): LazyColumn 콘텐츠 슬롯입니다.
 */
@Composable
fun WantedPopup(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    resize: Resize = Resize.Hug,
    properties: DialogProperties = DialogProperties(),
    popupDefault: WantedPopupDefault = WantedPopupDefaults.getDefault(),
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    lazyContent: LazyListScope.() -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = properties
    ) {
        WantedDialogLayout(
            modifier = modifier
                .widthIn(max = popupDefault.width)
                .then(heightModifier(resize, WantedModalContract.MAX_MODAL_SIZE.dp)),
            shape = popupDefault.shape,
            topBarPadding = PaddingValues(popupDefault.navigationPadding),
            bottomBarPadding = popupDefault.actionPadding,
            // Fixed 는 높이가 고정이므로 Content 가 남은 공간을 채워야 Action Area 가 하단에 붙는다.
            contentFillHeight = resize is Resize.Fixed,
            topBar = topBar,
            content = {
                LazyColumn(
                    modifier = if (resize is Resize.Fixed) {
                        Modifier.fillMaxSize()
                    } else {
                        Modifier.fillMaxWidth()
                    },
                    contentPadding = PaddingValues(
                        horizontal = popupDefault.contentHorizontalPadding,
                        vertical = popupDefault.contentVerticalPadding
                    )
                ) {
                    lazyContent()
                }
            },
            bottomBar = bottomBar
        )
    }
}

@SuppressLint("ModifierFactoryExtensionFunction")
@Composable
private fun heightModifier(
    resize: Resize,
    maxHeight: Dp,
    configuration: Configuration = LocalConfiguration.current,
    windowInsets: WindowInsets = WantedTopAppBarDefaults.windowInsets
): Modifier {

    val windowInset = windowInsets.getTop(LocalDensity.current).pxToDp()
    val screenHeight = configuration.screenHeightDp.dp

    return when (resize) {
        is Resize.Fixed -> Modifier.height(min(resize.height, maxHeight))

        Resize.Hug -> {
            val available = screenHeight - windowInset - SCREEN_BOTTOM_MARGIN
            Modifier.heightIn(max = min(available, maxHeight))
        }
    }
}

private val SCREEN_BOTTOM_MARGIN = 10.dp


@DevicePreviews
@Composable
private fun WantedPopupPreview() {
    DesignSystemTheme {
        Scaffold {
            WantedPopup(
                modifier = Modifier.padding(it),
                title = "다이얼로그 타이틀",
                positive = "확인",
                onClickPositive = {},
                onDismissRequest = {},
                content = {
                    Text(text = "다이얼로그 내용")
                }
            )
        }
    }
}
