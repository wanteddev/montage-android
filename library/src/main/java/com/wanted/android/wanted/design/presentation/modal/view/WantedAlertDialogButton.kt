package com.wanted.android.wanted.design.presentation.modal.view

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.actions.button.config.WantedButtonDefaults
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.ButtonVariant


@Composable
internal fun WantedAlertDialogButton(
    text: String,
    type: WantedAlertDialogButtonType = WantedAlertDialogButtonType.Positive,
    onClick: () -> Unit = {}
) {
    when (type) {
        WantedAlertDialogButtonType.Positive -> {
            WantedButton(
                modifier = Modifier.padding(vertical = 4.dp),
                text = text,
                variant = ButtonVariant.TEXT,
                size = ButtonSize.MEDIUM,
                onClick = { onClick() }
            )
        }

        WantedAlertDialogButtonType.Neutral -> {
            WantedButton(
                modifier = Modifier.padding(vertical = 4.dp),
                text = text,
                variant = ButtonVariant.TEXT,
                size = ButtonSize.MEDIUM,
                type = ButtonType.ASSISTIVE,
                onClick = { onClick() }
            )
        }

        else -> {
            WantedButton(
                modifier = Modifier.padding(vertical = 4.dp),
                text = text,
                buttonDefault = WantedButtonDefaults.getDefault(
                    variant = ButtonVariant.TEXT,
                    type = ButtonType.ASSISTIVE,
                    size = ButtonSize.SMALL,
                ).copy(
                    contentColor = DesignSystemTheme.colors.statusNegative
                ),
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
