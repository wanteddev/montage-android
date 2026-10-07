package com.wanted.android.wanted.design.presentation.modal.bottomsheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalType
import com.wanted.android.wanted.design.presentation.modal.bottomsheet.WantedBottomSheetDefaults.heightModifier
import com.wanted.android.wanted.design.presentation.modal.bottomsheet.draggable.WantedDraggableModalBottomSheet
import com.wanted.android.wanted.design.presentation.modal.view.WantedDialogLayout
import com.wanted.android.wanted.design.theme.DesignSystemTheme

/**
 * WantedModalBottomSheet
 *
 * Bottom sheet 형태의 모달 컴포넌트입니다.
 *
 * 시스템 Bottom sheet 또는 커스텀 드래그가 가능한 Bottom sheet를 사용할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * var showSheet by remember { mutableStateOf(false) }
 * val listState = rememberLazyListState()
 * // Foundation 1.8.x부터 anchoredDraggable이 nestedScroll 체인에 참여하면서, LazyColumn 스크롤 잔여값이 시트로 전달되어 튕김 버그가 발생할 수 있습니다.
 * val scrollPolicy = rememberBottomSheetScrollPolicy(listState)
 *
 * WantedModalBottomSheet(
 *     isShow = showSheet,
 *     onDismissRequest = { showSheet = false },
 *     content = {
 *         // 목록 스크롤은 LazyColumn이 담당하고, 위로 남는 잔여 스크롤만 정책으로 차단합니다.
 *         // LazyColumn 에서만 Modifier.nestedScroll(scrollPolicy.connection) 추가 합니다.
 *         Column(modifier = Modifier.nestedScroll(scrollPolicy.connection)) {
 *             LazyColumn(state = listState) {
 *                 // items(...)
 *             }
 *         }
 *     }
 * )
 * ```
 *
 * @param isShow Boolean: 모달 표시 여부입니다.
 * @param onDismissRequest () -> Unit: 모달이 닫힐 때 호출되는 콜백입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param background Color: 배경 색상입니다.
 * @param type ModalType: 모달의 형태입니다.
 * @param sheetDefault WantedBottomSheetDefault: Bottom Sheet 의 모양·여백 설정입니다. 기본값은 [type] 에 맞춰 골라집니다 (Full 만 별도 값).
 * @param dismissOnClickOutside Boolean: 외부 클릭 시 닫힘 여부입니다.
 * @param topBar (@Composable () -> Unit)?: 상단 바 슬롯입니다.
 * @param bottomBar (@Composable () -> Unit)?: 하단 바 슬롯입니다.
 * @param content (@Composable () -> Unit): 본문 콘텐츠 슬롯입니다.
 */
@Composable
fun WantedModalBottomSheet(
    isShow: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    background: Color = DesignSystemTheme.colors.surfaceElevatedPrimary,
    type: ModalType = ModalType.Flexible,
    sheetDefault: WantedBottomSheetDefault = WantedBottomSheetDefaults.forType(type),
    dismissOnClickOutside: Boolean = true,
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = type !is ModalType.Flexible,
        confirmValueChange = { sheetValue: SheetValue ->
            if (!type.isCloseable) {
                sheetValue == SheetValue.Expanded
            } else {
                true
            }
        }
    )

    val configuration = LocalConfiguration.current
    val width = remember(configuration) { configuration.screenWidthDp.dp }

    if (!type.isSystemBottomSheet) {
        WantedDraggableModalBottomSheet(
            isShow = isShow,
            contentColor = background,
            properties = remember {
                DialogProperties(
                    usePlatformDefaultWidth = true,
                    decorFitsSystemWindows = false,
                    dismissOnClickOutside = dismissOnClickOutside
                )
            },
            dragHandle = if (type.isCloseable) {
                { WantedBottomSheetDefaults.DragHandle() }
            } else {
                null
            },
            shape = sheetDefault.shape,
            content = {
                WantedDialogLayout(
                    modifier = Modifier
                        .then(heightModifier(type))
                        .fillMaxWidth(),
                    shape = sheetDefault.shape,
                    topBarPadding = PaddingValues(sheetDefault.navigationPadding),
                    bottomBarPadding = sheetDefault.actionPadding,
                    // 높이가 고정인 타입은 Content 가 남은 공간을 채워야 Action Area 가 하단에 붙는다.
                    contentFillHeight = type.isFixedHeight,
                    topBar = topBar,
                    content = {
                        SheetContent(
                            sheetDefault = sheetDefault,
                            fillHeight = type.isFixedHeight,
                            content = content
                        )
                    },
                    bottomBar = bottomBar
                )
            },
            onDismissRequest = onDismissRequest
        )
    } else if (isShow) {
        ModalBottomSheet(
            modifier = modifier.fillMaxWidth(),
            properties = ModalBottomSheetProperties(
                SecureFlagPolicy.Inherit,
                dismissOnClickOutside
            ),
            containerColor = background,
            contentColor = background,
            tonalElevation = 0.dp,
            shape = sheetDefault.shape,
            sheetMaxWidth = width,
            sheetState = bottomSheetState,
            dragHandle = if (type.isCloseable) {
                { WantedBottomSheetDefaults.DragHandle() }
            } else {
                null
            },
            content = {
                WantedDialogLayout(
                    modifier = Modifier
                        .then(heightModifier(type))
                        .fillMaxWidth(),
                    shape = sheetDefault.shape,
                    topBarPadding = PaddingValues(sheetDefault.navigationPadding),
                    bottomBarPadding = sheetDefault.actionPadding,
                    // 높이가 고정인 타입은 Content 가 남은 공간을 채워야 Action Area 가 하단에 붙는다.
                    contentFillHeight = type.isFixedHeight,
                    topBar = topBar,
                    content = {
                        SheetContent(
                            sheetDefault = sheetDefault,
                            fillHeight = type.isFixedHeight,
                            content = content
                        )
                    },
                    bottomBar = bottomBar
                )
            },
            onDismissRequest = {
                onDismissRequest()
            }
        )
    }
}

// Content 여백은 Bottom Sheet 가 소유한다. 여백 없이 쓰려면 sheetDefault 의 content padding 을 0 으로 넘긴다.
@Composable
private fun SheetContent(
    sheetDefault: WantedBottomSheetDefault,
    fillHeight: Boolean,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .then(if (fillHeight) Modifier.fillMaxSize() else Modifier)
            .padding(sheetDefault.contentPadding)
    ) {
        content()
    }
}

// 높이가 고정되는 타입인지 여부다. Flexible·FixedWrapContent 는 콘텐츠 높이를 따라가므로 Content 를 늘리지 않고,
// 나머지는 높이가 먼저 정해지므로 Content 가 남은 공간을 채워야 Action Area 가 하단에 붙는다.
private val ModalType.isFixedHeight: Boolean
    get() = when (this) {
        is ModalType.Fixed,
        is ModalType.FixedFullScreen,
        is ModalType.FixedRatio -> true

        ModalType.Flexible,
        is ModalType.FixedWrapContent -> false
    }
