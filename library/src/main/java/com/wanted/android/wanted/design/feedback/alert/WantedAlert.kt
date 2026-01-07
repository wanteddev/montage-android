package com.wanted.android.wanted.design.feedback.alert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.wanted.android.wanted.design.presentation.modal.view.WantedAlertDialogButton
import com.wanted.android.wanted.design.presentation.modal.view.WantedAlertDialogButtonType
import com.wanted.android.wanted.design.theme.DesignSystemTheme

/**
 * 확인 및 취소 버튼이 있는 기본 알림 dialog입니다.
 *
 * 제목, 메시지, 확인/취소 텍스트 및 클릭 이벤트를 전달하여 간단한 사용자 확인 dialog를 구성할 수 있습니다.
 * `isNegative` 값에 따라 확인 버튼의 스타일이 Positive 또는 Negative로 설정됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedAlert(
 *     title = "삭제하시겠습니까?",
 *     message = "삭제하면 되돌릴 수 없습니다.",
 *     confirm = "삭제",
 *     cancel = "취소",
 *     onClickConfirm = { /* 삭제 처리 */ },
 *     onClickCancel = { /* 취소 처리 */ },
 *     onDismissRequest = { /* 외부 클릭 시 */ }
 * )
 * ```
 *
 * @param message String: 다이얼로그 본문에 표시될 메시지입니다.
 * @param confirm String: 확인 버튼에 표시될 텍스트입니다.
 * @param onClickConfirm () -> Unit: 확인 버튼 클릭 시 호출되는 콜백입니다.
 * @param properties DialogProperties: 다이얼로그의 속성입니다. 기본값은 `DialogProperties()`입니다.
 * @param title String?: 다이얼로그 상단에 표시할 제목입니다. 선택적으로 설정 가능합니다.
 * @param isNegative Boolean: true일 경우 확인 버튼을 Negative 스타일로 표시합니다.
 * @param cancel String?: 취소 버튼에 표시할 텍스트입니다. null일 경우 표시되지 않습니다.
 * @param onClickCancel (() -> Unit)?: 취소 버튼 클릭 시 호출되는 콜백입니다.
 * @param onDismissRequest () -> Unit: 다이얼로그 외부 클릭 등으로 닫힐 때 호출되는 콜백입니다.
 */
@Composable
fun WantedAlert(
    message: String,
    confirm: String,
    onClickConfirm: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    title: String? = null,
    isNegative: Boolean = false,
    cancel: String? = null,
    onClickCancel: (() -> Unit)? = null,
    onDismissRequest: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = properties
    ) {
        WantedAlertDialogLayout(
            title = title?.let {
                {
                    Text(text = title)
                }
            },
            message = {
                Text(text = message)
            },
            negative = cancel?.let {
                {
                    WantedAlertDialogButton(
                        text = cancel,
                        type = WantedAlertDialogButtonType.Neutral,
                        onClick = { onClickCancel?.invoke() }

                    )
                }
            },
            positive = {
                WantedAlertDialogButton(
                    text = confirm,
                    type = if (isNegative) {
                        WantedAlertDialogButtonType.Negative
                    } else {
                        WantedAlertDialogButtonType.Positive
                    },

                    onClick = { onClickConfirm() }
                )
            }
        )
    }
}

@Composable
private fun WantedAlertDialogLayout(
    title: @Composable (() -> Unit)?,
    message: @Composable () -> Unit,
    positive: @Composable () -> Unit,
    negative: @Composable (() -> Unit)?,
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(DesignSystemTheme.colors.backgroundElevatedNormal)
    ) {
        Column(
            modifier = Modifier.padding(28.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ProvideTextStyle(
                value = DesignSystemTheme.typography.heading2Bold.copy(
                    color = DesignSystemTheme.colors.labelNormal
                )
            ) {
                title?.invoke()
            }

            ProvideTextStyle(
                value = DesignSystemTheme.typography.body2Regular.copy(
                    color = DesignSystemTheme.colors.labelAlternative
                )
            ) {
                message()
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 28.dp, end = 28.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 24.dp, alignment = Alignment.End),
            verticalAlignment = Alignment.CenterVertically
        ) {
            negative?.invoke()

            positive()
        }
    }
}

@Composable
@Preview
private fun WantedDialogPreview() {
    DesignSystemTheme {
        Surface {
            Column {
                WantedAlert(
                    title = "다이얼로그 타이틀이 두줄이 되는 경우에는 어떻게 될까요?",
                    message = "다이얼로그 메시지",
                    confirm = "확인",
                    cancel = "취소",
                    onClickConfirm = {},
                    onClickCancel = {}
                )
            }
        }
    }
}