package com.wanted.android.wanted.design.dialog.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.button.WantedButton
import com.wanted.android.wanted.design.button.config.WantedButtonDefaults
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType


@Composable
internal fun WantedAlertDialogButton(
    type: WantedAlertDialogButtonType = WantedAlertDialogButtonType.Positive,
    text: String,
    onClick: () -> Unit
) {
    when (type) {
        WantedAlertDialogButtonType.Positive -> {
            WantedButton(
                text = text,
                buttonShape = ButtonShape.TEXT,
                size = ButtonSize.MEDIUM,
                onClick = { onClick() }
            )
        }

        WantedAlertDialogButtonType.Neutral -> {
            WantedButton(
                text = text,
                buttonShape = ButtonShape.TEXT,
                size = ButtonSize.MEDIUM,
                type = ButtonType.ASSISTIVE,
                onClick = { onClick() }
            )
        }

        else -> {
            WantedButton(
                text = text,
                buttonShape = ButtonShape.TEXT,
                buttonDefault = WantedButtonDefaults.getDefault(
                    shape = ButtonShape.TEXT,
                    type = ButtonType.ASSISTIVE,
                    size = ButtonSize.SMALL,
                ).copy(
                    contentColor = colorResource(id = R.color.status_negative)
                ),
                size = ButtonSize.MEDIUM,
                type = ButtonType.ASSISTIVE,
                onClick = { onClick() }
            )
        }
    }
}

internal enum class WantedAlertDialogButtonType {
    Positive,
    Negative,
    Neutral
}
