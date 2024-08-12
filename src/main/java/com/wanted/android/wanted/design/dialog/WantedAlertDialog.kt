package com.wanted.android.wanted.design.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.dialog.view.WantedAlertDialogButton
import com.wanted.android.wanted.design.dialog.view.WantedAlertDialogButtonType
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun WantedAlertDialog(
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
                .padding(start = 28.dp, end = 28.dp, bottom = 20.dp, top = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 24.dp, alignment = Alignment.End),
            verticalAlignment = Alignment.CenterVertically
        ) {
            negative?.invoke()

            positive()
        }
    }
}
//
//@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
//@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
//@Preview(
//    "foldableLight",
//    uiMode = Configuration.UI_MODE_NIGHT_NO,
//    locale = "ko",
//    device = Devices.FOLDABLE
//)
//@Composable
//private fun WantedDialogPreview() {
//    DesignSystemTheme {
//        Surface {
//            WantedAlertDialog(
//                title = "간략한 제목",
//                message = "목적을 명확하고 간단히 안내합니다.",
//                negative = "보조 행동",
//                positive = "권장 행동",
//                onClickPositive = {}
//            )
//        }
//    }
//}
