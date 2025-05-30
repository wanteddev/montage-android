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

/**
 * 시스템 또는 사용자 정의 가능한 BottomSheet 형태의 모달입니다.
 *
 * `isSystemBottomSheet` 여부에 따라 `ModalBottomSheet` 또는 `WantedDraggableModalBottomSheet`를 사용합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedModalBottomSheet(
 *     isShow = true,
 *     onDismissRequest = {},
 *     content = { Text("시트 내용") }
 * )
 * ```
 *
 * @param isShow Boolean: 모달 표시 여부입니다.
 * @param onDismissRequest () -> Unit: 닫힘 콜백입니다.
 * @param modifier Modifier: 외형 조정 Modifier입니다.
 * @param background Color: 배경 색상입니다.
 * @param type ModalType: 시트 유형 (Flexible, Fixed 등)입니다.
 * @param modalSize ModalSize: 콘텐츠 패딩 등을 조절하는 크기 설정입니다.
 * @param dismissOnClickOutside Boolean: 외부 클릭 시 닫힘 여부입니다.
 * @param topBar @Composable (() -> Unit)?: 상단 바입니다.
 * @param bottomBar @Composable (() -> Unit)?: 하단 바입니다.
 * @param content @Composable () -> Unit: 본문 콘텐츠입니다.
 */
@Composable
fun WantedModalBottomSheet(
    isShow: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    background: Color = colorResource(id = R.color.background_elevated_normal),
    type: ModalType = ModalType.Flexible,
    modalSize: ModalSize = ModalSize.Medium,
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


