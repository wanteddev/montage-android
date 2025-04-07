package com.wanted.android.wanted.design.presentation.modal

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarDefaults
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.BottomSheetDialogType
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalSize
import com.wanted.android.wanted.design.presentation.modal.draggable.WantedDraggableModalBottomSheet
import com.wanted.android.wanted.design.presentation.modal.view.WantedDialogLayout
import com.wanted.android.wanted.design.util.pxToDp


@Composable
fun WantedModalBottomSheet(
    modifier: Modifier = Modifier,
    isShow: Boolean,
    background: Color = colorResource(id = R.color.background_elevated_normal),
    type: BottomSheetDialogType = BottomSheetDialogType.Flexible,
    modalSize: ModalSize = ModalSize.Medium,
    dismissOnClickOutside: Boolean = true,
    onDismissRequest: () -> Unit,
    topBar: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit,
    bottomBar: (@Composable () -> Unit)? = null
) {
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = type !is BottomSheetDialogType.Flexible,
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
    val windowInset = WantedTopAppBarDefaults.windowInsets.getTop(LocalDensity.current).pxToDp()
    val screenHeight = configuration.screenHeightDp.dp

    val heightModifier = remember(configuration) {
        when (type) {
            is BottomSheetDialogType.Fixed -> {
                Modifier.height(type.height)
            }

            is BottomSheetDialogType.FixedFullScreen -> {
                Modifier.height(
                    screenHeight - windowInset - 10.dp - WantedModalDefaults.DRAG_HANDLE_SIZE_DP.dp
                )
            }

            is BottomSheetDialogType.FixedRatio -> {
                val height =
                    screenHeight - windowInset - 10.dp - WantedModalDefaults.DRAG_HANDLE_SIZE_DP.dp
                val ratioHeight = screenHeight * type.ratio
                val result = if (ratioHeight > height) {
                    height
                } else {
                    ratioHeight
                }

                Modifier.height(result)
            }

            else -> {
                val result =
                    screenHeight - windowInset - 10.dp - WantedModalDefaults.DRAG_HANDLE_SIZE_DP.dp
                Modifier.heightIn(max = result)
            }
        }
    }

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
                { WantedModalDefaults.DragHandle() }
            } else {
                null
            },
            content = {
                WantedDialogLayout(
                    modifier = heightModifier.fillMaxWidth(),
                    modalSize = modalSize,
                    topBar = topBar,
                    content = content,
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
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            sheetMaxWidth = width,
            sheetState = bottomSheetState,
            dragHandle = if (type.isCloseable) {
                { WantedModalDefaults.DragHandle() }
            } else {
                null
            },
            content = {
                WantedDialogLayout(
                    modifier = heightModifier.fillMaxWidth(),
                    modalSize = modalSize,
                    topBar = topBar,
                    content = content,
                    bottomBar = bottomBar
                )
            },
            onDismissRequest = {
                onDismissRequest()
            }
        )
    }
}


