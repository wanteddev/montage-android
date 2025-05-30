package com.wanted.android.wanted.design.presentation.modal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.wanted.android.wanted.design.navigations.topbar.WantedDialogTopAppBar
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalSize
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalType
import com.wanted.android.wanted.design.presentation.modal.WantedModalDefaults.heightModifier
import com.wanted.android.wanted.design.presentation.modal.view.WantedDialogLayout
import com.wanted.android.wanted.design.presentation.modal.view.WantedDialogTwoButtonImpl
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * 상단 앱바와 확인/취소 버튼을 포함한 기본 모달입니다.
 *
 * `WantedDialogTwoButtonImpl`을 기반으로 레이아웃을 구성합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedModal(
 *     topBar = { WantedDialogTopAppBar(title = "제목") },
 *     positive = "확인",
 *     onClickPositive = {},
 *     onDismissRequest = {},
 *     content = { Text("내용") }
 * )
 * ```
 *
 * @param onDismissRequest () -> Unit: 모달 외부 클릭 등으로 닫힐 때 호출되는 콜백입니다.
 * @param modifier Modifier: 외형 및 높이 조정 Modifier입니다.
 * @param type ModalType: 모달의 유형을 결정합니다 (Flexible, Fixed 등).
 * @param properties DialogProperties: Compose Dialog 속성입니다.
 * @param shape RoundedCornerShape: 모달의 모서리 둥근 정도입니다.
 * @param topBar @Composable (() -> Unit)?: 상단 앱바 컴포저블입니다.
 * @param positive String?: 확인 버튼 텍스트입니다.
 * @param negative String?: 취소 버튼 텍스트입니다.
 * @param onClickPositive (() -> Unit)?: 확인 버튼 클릭 시 콜백입니다.
 * @param onClickNegative (() -> Unit)?: 취소 버튼 클릭 시 콜백입니다.
 * @param content @Composable BoxScope.() -> Unit: 본문 영역입니다.
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
 * 확인/취소 버튼 없이 콘텐츠 중심으로 구성된 모달입니다.
 *
 * 커스텀 bottomBar를 삽입할 수 있으며, `WantedDialogLayout`을 기반으로 구성됩니다.
 *
 * @param onDismissRequest () -> Unit: 모달 외부 클릭 등으로 닫힐 때 호출되는 콜백입니다.
 * @param modifier Modifier: 외형 및 높이 조정 Modifier입니다.
 * @param type ModalType: 모달의 유형 (Flexible, Fixed 등)을 설정합니다.
 * @param properties DialogProperties: Compose Dialog 속성입니다.
 * @param shape RoundedCornerShape: 모달의 모서리 둥글기입니다.
 * @param topBar @Composable (() -> Unit)?: 상단 앱바 컴포저블입니다.
 * @param bottomBar (@Composable () -> Unit)?: 하단 바를 위한 Slot입니다.
 * @param content @Composable BoxScope.() -> Unit: 본문 콘텐츠를 구성하는 Composable Slot입니다.
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
 * LazyColumn 기반의 스크롤 가능한 콘텐츠를 포함한 모달입니다.
 *
 * 콘텐츠가 많은 경우 LazyColumn을 사용하여 스크롤 처리가 가능합니다.
 *
 * @param onDismissRequest () -> Unit: 모달 외부 클릭 등으로 닫힐 때 호출되는 콜백입니다.
 * @param modifier Modifier: 외형 및 높이 조정 Modifier입니다.
 * @param type ModalType: 모달의 유형 (Flexible, Fixed 등)을 설정합니다.
 * @param properties DialogProperties: Compose Dialog 속성입니다.
 * @param shape RoundedCornerShape: 모달의 모서리 둥글기입니다.
 * @param topBar @Composable (() -> Unit)?: 상단 앱바 컴포저블입니다.
 * @param bottomBar (@Composable () -> Unit)?: 하단 바를 위한 Slot입니다.
 * @param lazyContent LazyListScope.() -> Unit: LazyColumn 콘텐츠를 구성하는 Slot입니다.
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
