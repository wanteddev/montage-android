package com.wanted.android.wanted.design.snackbar

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DefaultSnackBar(
    content: @Composable () -> Unit,
) {
    Snackbar(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(vertical = 14.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(8.dp)),
        backgroundColor = colorResource(id = R.color.inverse_background),
        contentColor = colorResource(id = R.color.inverse_label),
        elevation = 6.dp,
    ) {
        content()
    }
}

@Composable
fun TextSnackBar(
    text: String
) {
    DefaultSnackBar {
        Text(
            text = text,
            style = WantedTextStyle(
                colorRes = R.color.inverse_label,
                style = DesignSystemTheme.typography.label1Medium
            ),
        )
    }
}

@Composable
fun IconTextSnackBar(
    icon: @Composable () -> Unit,
    text: String
) {
    DefaultSnackBar {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon()
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = text,
                style = WantedTextStyle(
                    colorRes = R.color.inverse_label,
                    style = DesignSystemTheme.typography.label1Medium
                ),
            )
        }
    }
}

@Preview("light", showBackground = true, backgroundColor = 0xFFFFFFFF, uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Composable
private fun TextSnackBarPreview() {
    TextSnackBar(
        text = "메시지에 마침표를 찍어요."
    )
}

@Preview("light", showBackground = true, backgroundColor = 0xFFFFFFFF, uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Composable
private fun IconSnackBarPreview() {
    IconTextSnackBar(
        icon = {
            Icon(
                modifier = Modifier
                    .size(16.5.dp),
                painter = painterResource(id = R.drawable.ic_normal_circle_check_svg),
                contentDescription = null,
                tint = colorResource(id = R.color.status_positive),
            )
        },
        text = "메시지에 마침표를 찍어요."
    )
}