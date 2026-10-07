package com.wanted.android.wanted.design.presentation.modal.view

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.actions.button.textbutton.WantedTextButton
import com.wanted.android.wanted.design.actions.button.textbutton.WantedTextButtonColor
import com.wanted.android.wanted.design.actions.button.textbutton.WantedTextButtonDefaults
import com.wanted.android.wanted.design.actions.button.textbutton.WantedTextButtonSize
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_12


@Composable
internal fun WantedAlertDialogButton(
    text: String,
    type: WantedAlertDialogButtonType = WantedAlertDialogButtonType.Positive,
    onClick: () -> Unit = {}
) {
    when (type) {
        WantedAlertDialogButtonType.Positive -> {
            WantedTextButton(
                modifier = Modifier.padding(vertical = 4.dp),
                text = text,
                size = WantedTextButtonSize.MEDIUM,
                onClick = { onClick() }
            )
        }

        WantedAlertDialogButtonType.Neutral -> {
            WantedTextButton(
                modifier = Modifier.padding(vertical = 4.dp),
                text = text,
                size = WantedTextButtonSize.MEDIUM,
                color = WantedTextButtonColor.ASSISTIVE,
                onClick = { onClick() }
            )
        }

        else -> {
            WantedTextButton(
                modifier = Modifier.padding(vertical = 4.dp),
                text = text,
                // Text Button 스펙에 negative 색 축이 없어 contentColor로 덮는다.
                // 크기는 같은 다이얼로그의 Positive/Neutral 과 맞춰 MEDIUM 을 쓴다.
                buttonDefault = WantedTextButtonDefaults.getDefault(
                    color = WantedTextButtonColor.ASSISTIVE,
                    size = WantedTextButtonSize.MEDIUM,
                    contentColor = DesignSystemTheme.colors.foregroundNegativePrimary,
                    // 리플은 색 축이 아니라 덮어쓴 contentColor를 따른다.
                    rippleColor = DesignSystemTheme.colors.foregroundNegativePrimary.copy(alpha = OPACITY_12)
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
