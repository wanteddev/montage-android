package com.wanted.android.wanted.design.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.dialog.WantedModalContract.BottomSheetDialogType
import com.wanted.android.wanted.design.dialog.WantedModalContract.ModalSize
import com.wanted.android.wanted.design.dialog.view.WantedDialogLayout
import com.wanted.android.wanted.design.topbar.WantedTopAppBarDefaults
import com.wanted.android.wanted.design.util.pxToDp


@Composable
fun WantedModalBottomSheet(
    modifier: Modifier,
    background: Color = colorResource(id = R.color.background_elevated_normal),
    type: BottomSheetDialogType = BottomSheetDialogType.Flexible,
    modalSize: ModalSize = ModalSize.Normal,
    onDismissRequest: () -> Unit,
    topBar: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit,
    bottomBar: (@Composable () -> Unit)? = null
) {
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = type is BottomSheetDialogType.Fixed
    )

    val configuration = LocalConfiguration.current
    val width = remember(configuration) { configuration.screenWidthDp.dp }
    val windowInset = WantedTopAppBarDefaults.windowInsets.getTop(LocalDensity.current).pxToDp()
    val maxHeight = remember(configuration) {
        if (type is BottomSheetDialogType.Fixed) {
            if (type.isFullScreen) {
                configuration.screenHeightDp.dp - windowInset - 10.dp - WantedModalDefaults.DRAG_HANDLE_SIZE_DP.dp
            } else {
                type.height
            }
        } else {
            configuration.screenHeightDp.dp - windowInset - 10.dp - WantedModalDefaults.DRAG_HANDLE_SIZE_DP.dp
        }
    }

    ModalBottomSheet(
        modifier = modifier.fillMaxWidth(),
        containerColor = background,
        tonalElevation = 0.dp,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetMaxWidth = width,
        sheetState = bottomSheetState,
        dragHandle = { WantedModalDefaults.DragHandle() },
        content = {
            WantedDialogLayout(
                modifier = modifier.heightIn(max = maxHeight),
                modalSize = modalSize,
                topBar = topBar,
                content = content,
                bottomBar = bottomBar
            )
        },
        onDismissRequest = { onDismissRequest() }
    )
}


