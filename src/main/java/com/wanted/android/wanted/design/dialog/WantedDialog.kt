package com.wanted.android.wanted.design.dialog

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.dialog.view.WantedAlertDialogButton
import com.wanted.android.wanted.design.dialog.view.WantedAlertDialogButtonType
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun WantedDialog(
    properties: DialogProperties = DialogProperties(),
    title: String? = null,
    message: String,
    confirm: String,
    isNegative: Boolean = false,
    cancel: String? = null,
    onClickConfirm: () -> Unit,
    onClickCancel: (() -> Unit)? = null,
    onDismissRequest: () -> Unit = {}
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
    negative: @Composable (() -> Unit)?
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(id = R.color.background_elevated_normal))
    ) {
        Column(
            modifier = Modifier.padding(28.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ProvideTextStyle(
                value = WantedTextStyle(
                    colorRes = R.color.label_normal,
                    style = DesignSystemTheme.typography.heading2Bold
                )
            ) {
                title?.invoke()
            }

            ProvideTextStyle(
                value = WantedTextStyle(
                    colorRes = R.color.label_alternative,
                    style = DesignSystemTheme.typography.body2Regular
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
                WantedDialog(
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