package com.wanted.android.wanted.design.toast

import android.content.res.Configuration
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

sealed class WantedToastVariant(
    @DrawableRes val resourceId: Int,
    @ColorRes val tinColor: Int
) {
    data object NORMAL : WantedToastVariant(-1, -1)
    data object SUCCESS : WantedToastVariant(
        R.drawable.ic_normal_circle_check_fill_svg,
        R.color.status_positive
    )

    data object ERROR : WantedToastVariant(
        R.drawable.ic_normal_circle_exclamation_fill_svg,
        R.color.status_cautionary
    )

    data object CUSTOM : WantedToastVariant(-1, -1)
}

@Composable
fun WantedToast(
    modifier: Modifier = Modifier,
    variant: WantedToastVariant = WantedToastVariant.NORMAL,
    text: String = "",
    customIconSlot: @Composable () -> Unit = {},
) {
    val iconSlot: @Composable () -> Unit = {
        when (variant) {
            WantedToastVariant.NORMAL -> Unit
            WantedToastVariant.CUSTOM -> customIconSlot()
            else -> {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(11.dp)
                            .background(colorResource(id = R.color.static_white))
                    )
                    Icon(
                        contentDescription = "icon",
                        painter = painterResource(id = variant.resourceId),
                        modifier = Modifier
                            .size(22.dp),
                        tint = colorResource(id = variant.tinColor)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
    }
    ToastBasicLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            iconSlot()
            Text(
                modifier = Modifier
                    .padding(horizontal = 2.dp, vertical = 5.dp),
                text = text,
                style = WantedTextStyle(
                    colorRes = R.color.inverse_label,
                    style = DesignSystemTheme.typography.body2Bold
                ).copy(
                    lineHeightStyle = LineHeightStyle(
                        LineHeightStyle.Alignment.Center,
                        LineHeightStyle.Trim.None
                    )
                ),
            )
        }
    }
}

/*
https://www.figma.com/design/MK6KmtXBxX7ZkoQXfD9MFH/%EA%B0%9C%EC%84%A0%3A-Components?node-id=1596-18812&m=dev
 */
@Composable
fun ToastBasicLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(colorResource(id = R.color.background_normal_normal))
            .background(colorResource(id = R.color.inverse_background).copy(0.61f))
            .background(colorResource(id = R.color.primary_normal).copy(0.08f))
            .padding(horizontal = 16.dp, vertical = 11.dp)
    ) {
        content()
    }
}

@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Composable
fun ToastNormalPreview() {
    DesignSystemTheme {
        WantedToast(
            variant = WantedToastVariant.NORMAL,
            text = "메시지에 마침표를 찍어요."
        )
    }
}

@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Composable
fun ToastSuccessPreview() {
    DesignSystemTheme {
        WantedToast(
            variant = WantedToastVariant.SUCCESS,
            text = "메시지에 마침표를 찍어요."
        )
    }
}

@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Composable
fun ToastErrorPreview() {
    DesignSystemTheme {
        WantedToast(
            variant = WantedToastVariant.ERROR,
            text = "메시지에 마침표를 찍어요."
        )
    }
}

@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Composable
fun ToastCustomPreview() {
    DesignSystemTheme {
        WantedToast(
            variant = WantedToastVariant.CUSTOM,
            customIconSlot = {
                Icon(
                    contentDescription = "icon",
                    painter = painterResource(id = R.drawable.ic_normal_eye_fill_svg),
                    modifier = Modifier
                        .size(22.dp),
                    tint = colorResource(id = R.color.design_default_color_error)
                )
            },
            text = "메시지에 마침표를 찍어요."
        )
    }
}