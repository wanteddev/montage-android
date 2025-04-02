package com.wanted.android.wanted.design.actions.button.icon

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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.clickOnce
import com.wanted.android.wanted.design.theme.DesignSystemTheme


@Composable
fun WantedIconButtonOutlined(
    modifier: Modifier,
    @DrawableRes icon: Int,
    size: WantedIconButtonSize,
    enabled: Boolean = true,
    outlineColor: Color = colorResource(id = R.color.line_normal_neutral),
    disableOutlineColor: Color = colorResource(id = R.color.line_normal_neutral),
    tint: Color = colorResource(id = R.color.label_normal),
    disableTint: Color = colorResource(id = R.color.label_disable),
    background: Color = colorResource(id = R.color.transparent),
    disableBackground: Color = colorResource(id = R.color.transparent),
    onClick: () -> Unit = {}
) {
    WantedIconButtonOutlined(
        modifier = modifier.size(size.size),
        icon = icon,
        enabled = enabled,
        padding = size.padding,
        outlineColor = outlineColor,
        disableOutlineColor = disableOutlineColor,
        tint = tint,
        disableTint = disableTint,
        background = background,
        disableBackground = disableBackground,
        onClick = onClick
    )
}

@Composable
fun WantedIconButtonOutlined(
    modifier: Modifier,
    @DrawableRes icon: Int,
    padding: Dp = 10.dp,
    enabled: Boolean = true,
    outlineColor: Color = colorResource(id = R.color.line_normal_neutral),
    disableOutlineColor: Color = colorResource(id = R.color.line_normal_neutral),
    tint: Color = colorResource(id = R.color.label_normal),
    disableTint: Color = colorResource(id = R.color.label_disable),
    background: Color = colorResource(id = R.color.transparent),
    disableBackground: Color = colorResource(id = R.color.transparent),
    onClick: () -> Unit = {}
) {
    Icon(
        modifier = modifier
            .clip(CircleShape)
            .background(if (enabled) background else disableBackground)
            .border(
                width = 1.dp,
                color = if (enabled) outlineColor else disableOutlineColor,
                shape = CircleShape
            )
            .clickOnce(enabled) { onClick() }
            .padding(padding),
        painter = painterResource(id = icon),
        contentDescription = "",
        tint = if (enabled) tint else disableTint
    )
}


@DevicePreviews
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
                    size = WantedIconButtonSize.Medium,
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