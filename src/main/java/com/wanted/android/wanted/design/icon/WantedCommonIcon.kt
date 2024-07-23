package com.wanted.android.wanted.design.icon

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
fun WantedCommonIcon(
    modifier: Modifier = Modifier,
    @DrawableRes resourceId: Int,
    tint: Color,
    size: Dp = 22.dp
) {
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(11.dp)
                .background(colorResource(id = R.color.static_white))
        )

        Icon(
            contentDescription = "icon",
            painter = painterResource(id = resourceId),
            modifier = Modifier.size(22.dp),
            tint = tint
        )
    }
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
private fun WantedCommonIconPreview() {
    DesignSystemTheme {
        Surface(
            Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                WantedCommonIcon(
                    modifier = Modifier.size(22.dp),
                    resourceId = R.drawable.ic_normal_circle_exclamation_fill_svg,
                    tint = colorResource(id = R.color.status_negative)
                )

                WantedCommonIcon(
                    modifier = Modifier.size(22.dp),
                    resourceId = R.drawable.ic_normal_circle_check_fill_svg,
                    tint = colorResource(id = R.color.primary_normal)
                )

                WantedCommonIcon(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .clickOnceForDesignSystem { },
                    resourceId = R.drawable.ic_normal_circle_close_svg,
                    tint = colorResource(id = R.color.label_alternative)
                )

                WantedCommonIcon(
                    modifier = Modifier.size(22.dp),
                    resourceId = R.drawable.ic_normal_circle_exclamation_fill_svg,
                    tint = colorResource(id = R.color.status_cautionary)
                )

                WantedCommonIcon(
                    modifier = Modifier.size(22.dp),
                    resourceId = R.drawable.ic_normal_circle_check_fill_svg,
                    tint = colorResource(id = R.color.status_positive)
                )
            }
        }
    }
}