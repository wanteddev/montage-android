package com.wanted.android.wanted.design.button.icon

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.theme.DesignSystemTheme




@Composable
fun WantedIconButtonOutlined(
    modifier: Modifier,
    @DrawableRes icon: Int,
    size: WantedIconButtonSize,
    outlineColor: Color = colorResource(id = R.color.line_normal_neutral),
    disableOutlineColor: Color = colorResource(id = R.color.line_normal_neutral),
    enabled: Boolean = true,
    tint: Color = colorResource(id = R.color.label_normal),
    background: Color = colorResource(id = R.color.transparent),
    onClick: () -> Unit = {}
) {
    WantedIconButtonOutlined(
        modifier = modifier.size(size.size),
        icon = icon,
        outlineColor = outlineColor,
        disableOutlineColor = disableOutlineColor,
        padding = size.padding,
        enabled = enabled,
        tint = tint,
        background = background,
        onClick = onClick
    )
}

@Composable
fun WantedIconButtonOutlined(
    modifier: Modifier,
    @DrawableRes icon: Int,
    outlineColor: Color = colorResource(id = R.color.line_normal_neutral),
    disableOutlineColor: Color = colorResource(id = R.color.line_normal_neutral),
    padding: Dp = 10.dp,
    enabled: Boolean = true,
    tint: Color = colorResource(id = R.color.label_normal),
    background: Color = colorResource(id = R.color.transparent),
    onClick: () -> Unit = {}
) {
    Icon(
        modifier = modifier
            .clip(CircleShape)
            .background(background)
            .border(
                width = 1.dp,
                color = if (enabled) outlineColor else disableOutlineColor,
                shape = CircleShape
            )
            .clickOnceForDesignSystem(enabled) { onClick() }
            .padding(padding),
        painter = painterResource(id = icon),
        contentDescription = "",
        tint = if (enabled) tint else colorResource(id = R.color.label_disable)
    )
}


@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Preview(
    "foldableLight",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "ko",
    device = Devices.FOLDABLE
)
@Composable
private fun WantedIconButtonOutlinedPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedIconButtonOutlined(
                    modifier = Modifier,
                    size = WantedIconButtonSize.Normal,
                    icon = R.drawable.graphic_company_12dp_svg,
                    onClick = {}
                )

                WantedIconButtonOutlined(
                    modifier = Modifier,
                    size = WantedIconButtonSize.Small,
                    icon = R.drawable.graphic_company_12dp_svg,
                    onClick = {}
                )

                WantedIconButtonOutlined(
                    modifier = Modifier.size(40.dp),
                    icon = R.drawable.graphic_company_12dp_svg,
                    onClick = {}
                )
            }
        }
    }
}