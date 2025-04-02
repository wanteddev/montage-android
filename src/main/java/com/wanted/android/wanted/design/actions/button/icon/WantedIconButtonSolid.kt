package com.wanted.android.wanted.design.actions.button.icon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
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
fun WantedIconButtonSolid(
    modifier: Modifier,
    @DrawableRes icon: Int,
    size: WantedIconButtonSize,
    enabled: Boolean = true,
    tint: Color = colorResource(id = R.color.static_white),
    background: Color = colorResource(id = R.color.primary_normal),
    onClick: () -> Unit = {}
) {
    WantedIconButtonSolid(
        modifier = modifier.size(size.size),
        icon = icon,
        padding = size.padding,
        enabled = enabled,
        tint = tint,
        background = background,
        onClick = onClick
    )
}

@Composable
fun WantedIconButtonSolid(
    modifier: Modifier,
    @DrawableRes icon: Int,
    padding: Dp = 10.dp,
    enabled: Boolean = true,
    tint: Color = colorResource(id = R.color.static_white),
    background: Color = colorResource(id = R.color.primary_normal),
    onClick: () -> Unit = {}
) {
    Icon(
        modifier = modifier
            .clip(CircleShape)
            .background(if (enabled) background else colorResource(id = R.color.fill_normal))
            .clickOnce(enabled) { onClick() }
            .padding(padding),
        painter = painterResource(id = icon),
        contentDescription = "",
        tint = if (enabled) tint else colorResource(id = R.color.label_disable)
    )
}

@DevicePreviews
@Composable
private fun WantedIconButtonSolidPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {

                WantedIconButtonSolid(
                    modifier = Modifier,
                    size = WantedIconButtonSize.Medium,
                    icon = R.drawable.graphic_company_12dp_svg,
                    onClick = {}
                )

                WantedIconButtonSolid(
                    modifier = Modifier,
                    size = WantedIconButtonSize.Small,
                    icon = R.drawable.graphic_company_12dp_svg,
                    onClick = {}
                )

                WantedIconButtonSolid(
                    modifier = Modifier.size(40.dp),
                    icon = R.drawable.graphic_company_12dp_svg,
                    onClick = {}
                )
            }
        }
    }
}