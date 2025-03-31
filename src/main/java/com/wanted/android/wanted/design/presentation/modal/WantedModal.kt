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
import com.wanted.android.wanted.design.presentation.modal.view.WantedDialogLayout
import com.wanted.android.wanted.design.presentation.modal.view.WantedDialogTwoButtonImpl
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews


@Composable
fun WantedModal(
    modifier: Modifier = Modifier,
    modalSize: ModalSize,
    properties: DialogProperties = DialogProperties(),
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    topBar: @Composable (() -> Unit)? = null,
    positive: String? = null,
    negative: String? = null,
    onClickPositive: (() -> Unit)? = null,
    onClickNegative: (() -> Unit)? = null,
    onDismissRequest: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = properties
    ) {
        WantedDialogTwoButtonImpl(
            modifier = modifier,
            modalSize = modalSize,
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


@Composable
fun WantedModal(
    modifier: Modifier = Modifier,
    modalSize: ModalSize,
    properties: DialogProperties = DialogProperties(),
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    onDismissRequest: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = properties
    ) {
        WantedDialogLayout(
            modifier = modifier,
            modalSize = modalSize,
            shape = shape,
            topBar = topBar,
            content = {
                Box(modifier = Modifier.padding(horizontal = modalSize.contentPadding)) {
                    content()
                }
            },
            bottomBar = bottomBar
        )
    }
}


@Composable
fun WantedModal(
    modifier: Modifier = Modifier,
    modalSize: ModalSize,
    properties: DialogProperties = DialogProperties(),
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    onDismissRequest: () -> Unit,
    lazyContent: LazyListScope.() -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = properties
    ) {
        WantedDialogLayout(
            modifier = modifier,
            modalSize = modalSize,
            shape = shape,
            topBar = topBar,
            content = {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(modalSize.contentPadding)
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
                modalSize = ModalSize.Medium,
                positive = "확인",
                topBar = {
                    WantedDialogTopAppBar(
                        title = "다이얼로그 타이틀",
                    )
                },
                onClickPositive = {},
                onDismissRequest = {}
            ) {
                Text(text = "다이얼로그 내용")
            }
        }
    }
}
