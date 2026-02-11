package com.wanted.android.wanted.design.presentation.modal.popup

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarDefaults
import com.wanted.android.wanted.design.navigations.topbar.dialogtopbar.WantedDialogTopAppBar
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalSize
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalType
import com.wanted.android.wanted.design.presentation.modal.bottomsheet.WantedBottomSheetDefaults.heightModifier
import com.wanted.android.wanted.design.presentation.modal.view.WantedDialogLayout
import com.wanted.android.wanted.design.presentation.modal.view.WantedDialogTwoButtonImpl
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.pxToDp

/**
 * WantedModal
 *
 * 상단 앱바와 확인/취소 버튼을 포함한 기본 모달 컴포넌트입니다.
 *
 * 사용 예시:
 * ```kotlin
 * var showModal by remember { mutableStateOf(true) }
 *
 * if (showModal) {
 *     WantedModal(
 *         topBar = { WantedDialogTopAppBar(title = "제목") },
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
 * @param type ModalType: 모달의 형태입니다.
 * @param properties DialogProperties: Dialog 속성입니다.
 * @param shape RoundedCornerShape: 모달의 모서리 둥글기입니다.
 * @param topBar (@Composable () -> Unit)?: 상단 앱바 슬롯입니다.
 * @param positive String?: 확인 버튼 텍스트입니다.
 * @param negative String?: 취소 버튼 텍스트입니다.
 * @param onClickPositive (() -> Unit)?: 확인 버튼 클릭 시 호출되는 콜백입니다.
 * @param onClickNegative (() -> Unit)?: 취소 버튼 클릭 시 호출되는 콜백입니다.
 * @param content (@Composable BoxScope.() -> Unit): 본문 콘텐츠 슬롯입니다.
 */
@Composable
fun WantedModal(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    type: ModalType = ModalType.Flexible,
    properties: DialogProperties = DialogProperties(),
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    topBar: @Composable (() -> Unit)? = null,
    positive: String? = null,
    negative: String? = null,
    onClickPositive: (() -> Unit)? = null,
    onClickNegative: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = properties
    ) {
        WantedDialogTwoButtonImpl(
            modifier = modifier.then(heightModifier(type, WantedModalContract.MAX_MODAL_SIZE.dp)),
            shape = shape,
            topBar = topBar,
            positive = positive,
            negative = negative,
            onClickPositive = onClickPositive,
            onClickNegative = onClickNegative,
            content = content
        )
    }
}

/**
 * WantedModal
 *
 * 커스텀 하단 바를 포함한 모달 컴포넌트입니다.
 *
 * 확인/취소 버튼 대신 커스텀 bottomBar를 사용할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * var showModal by remember { mutableStateOf(true) }
 *
 * if (showModal) {
 *     WantedModal(
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
 * @param type ModalType: 모달의 형태입니다.
 * @param properties DialogProperties: Dialog 속성입니다.
 * @param shape RoundedCornerShape: 모달의 모서리 둥글기입니다.
 * @param topBar (@Composable () -> Unit)?: 상단 앱바 슬롯입니다.
 * @param bottomBar (@Composable () -> Unit)?: 하단 바 슬롯입니다.
 * @param content (@Composable BoxScope.() -> Unit): 본문 콘텐츠 슬롯입니다.
 */
@Composable
fun WantedModal(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    type: ModalType = ModalType.Flexible,
    properties: DialogProperties = DialogProperties(),
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = properties
    ) {
        WantedDialogLayout(
            modifier = modifier.then(heightModifier(type, WantedModalContract.MAX_MODAL_SIZE.dp)),
            modalSize = ModalSize.Medium,
            shape = shape,
            topBar = topBar,
            content = {
                Box(modifier = Modifier.padding(horizontal = ModalSize.Medium.contentPadding)) {
                    content()
                }
            },
            bottomBar = bottomBar
        )
    }
}

/**
 * WantedModal
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
 *     WantedModal(
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
 * @param type ModalType: 모달의 형태입니다.
 * @param properties DialogProperties: Dialog 속성입니다.
 * @param shape RoundedCornerShape: 모달의 모서리 둥글기입니다.
 * @param topBar (@Composable () -> Unit)?: 상단 앱바 슬롯입니다.
 * @param bottomBar (@Composable () -> Unit)?: 하단 바 슬롯입니다.
 * @param lazyContent (LazyListScope.() -> Unit): LazyColumn 콘텐츠 슬롯입니다.
 */
@Composable
fun WantedModal(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    type: ModalType = ModalType.Flexible,
    properties: DialogProperties = DialogProperties(),
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    lazyContent: LazyListScope.() -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = properties
    ) {
        WantedDialogLayout(
            modifier = modifier.then(heightModifier(type, WantedModalContract.MAX_MODAL_SIZE.dp)),
            modalSize = ModalSize.Medium,
            shape = shape,
            topBar = topBar,
            content = {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(ModalSize.Medium.contentPadding)
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
    type: ModalType,
    maxHeight: Dp? = null,
    configuration: Configuration = LocalConfiguration.current,
    windowInsets: WindowInsets = WantedTopAppBarDefaults.windowInsets
): Modifier {

    val windowInset = windowInsets.getTop(LocalDensity.current).pxToDp()
    val screenHeight = configuration.screenHeightDp.dp

    return when (type) {
        is ModalType.Fixed -> {
            Modifier.Companion.height(min(type.height, maxHeight ?: type.height))
        }

        is ModalType.FixedFullScreen -> {
            val height = screenHeight - windowInset
            Modifier.Companion.height(min(height, maxHeight ?: height))
        }

        is ModalType.FixedRatio -> {
            val height =
                screenHeight - windowInset - 10.dp
            val ratioHeight = screenHeight * type.ratio
            val result = min(ratioHeight, height)

            Modifier.Companion.height(min(result, maxHeight ?: result))
        }

        else -> {
            val result =
                screenHeight - windowInset - 10.dp
            Modifier.Companion.heightIn(max = min(result, maxHeight ?: result))
        }
    }
}


@DevicePreviews
@Composable
private fun WantedDialogPreview() {
    DesignSystemTheme {
        Scaffold {
            WantedModal(
                modifier = Modifier.padding(it),
                topBar = {
                    WantedDialogTopAppBar(
                        title = "다이얼로그 타이틀",
                    )
                },
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
