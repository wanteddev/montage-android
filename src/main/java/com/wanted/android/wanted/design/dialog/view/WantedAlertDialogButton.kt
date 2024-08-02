package com.wanted.android.wanted.design.dialog.view

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_12
import com.wanted.android.wanted.design.util.WantedTextStyle
import com.wanted.android.wanted.design.util.wantedRippleEffect


@Composable
internal fun WantedAlertDialogButton(
    modifier: Modifier = Modifier,
    type: WantedAlertDialogButtonType = WantedAlertDialogButtonType.Positive,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickOnceForDesignSystem(
                interactionSource = remember { MutableInteractionSource() },
                indication = getWantedRippleEffect(type),
                onClick = {
                    onClick()
                }
            )
            .padding(vertical = 4.dp, horizontal = 7.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = WantedTextStyle(
                colorRes = when (type) {
                    WantedAlertDialogButtonType.Positive -> R.color.primary_normal
                    WantedAlertDialogButtonType.Negative -> R.color.status_negative
                    else -> R.color.label_normal
                },
                style = DesignSystemTheme.typography.body1Bold
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun getWantedRippleEffect(
    type: WantedAlertDialogButtonType
) = wantedRippleEffect(
    when (type) {
        WantedAlertDialogButtonType.Positive -> colorResource(id = R.color.primary_normal_opacity12)
        WantedAlertDialogButtonType.Negative -> colorResource(id = R.color.status_negative).copy(
            alpha = OPACITY_12
        )

        else -> colorResource(id = R.color.label_normal_opacity12)
    }
)

internal enum class WantedAlertDialogButtonType {
    Positive,
    Negative,
    Neutral
}
