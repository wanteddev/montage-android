package com.wanted.android.wanted.design.presentation.modal

import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalSize
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalType
import com.wanted.android.wanted.design.presentation.modal.WantedModalDefaults.heightModifier
import com.wanted.android.wanted.design.presentation.modal.draggable.WantedDraggableModalBottomSheet
import com.wanted.android.wanted.design.presentation.modal.view.WantedDialogLayout


@Composable
fun WantedModalBottomSheet(
    modifier: Modifier = Modifier,
    isShow: Boolean,
    background: Color = colorResource(id = R.color.background_elevated_normal),
    type: ModalType = ModalType.Flexible,
    modalSize: ModalSize = ModalSize.Medium,
    dismissOnClickOutside: Boolean = true,
    onDismissRequest: () -> Unit,
    topBar: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit,
    bottomBar: (@Composable () -> Unit)? = null
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
                { WantedModalDefaults.DragHandle() }
            } else {
                null
            },
            content = {
                WantedDialogLayout(
                    modifier = Modifier
                        .then(heightModifier(type))
                        .fillMaxWidth(),
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
                    modifier = Modifier
                        .then(heightModifier(type))
                        .fillMaxWidth(),
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


