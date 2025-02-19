package com.wanted.android.wanted.design.actions.button.icon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.feedback.pushbadge.WantedPushBadge
import com.wanted.android.wanted.design.theme.DesignSystemTheme

@Composable
fun WantedIconButtonNormal(
    modifier: Modifier,
    @DrawableRes icon: Int,
    enabled: Boolean = true,
    tint: Color = colorResource(id = R.color.label_normal),
    pushBadge: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    WantedTouchArea(
        content = {
            Icon(
                modifier = modifier,
                painter = painterResource(id = icon),
                contentDescription = "",
                tint = if (enabled) tint else colorResource(id = R.color.label_disable),
            )

            pushBadge?.let {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(10.dp, (-10).dp)
                ) {
                    pushBadge()
                }
            }
        },
        enabled = enabled,
        shape = CircleShape,
        horizontalPadding = 8.dp,
        verticalPadding = 8.dp,
        onClick = onClick
    )

}


@DevicePreviews
@Composable
private fun WantedIconButtonNormalPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {

                WantedIconButtonNormal(
                    modifier = Modifier.size(24.dp),
                    icon = R.drawable.graphic_company_12dp_svg,
                    onClick = {}
                )

                WantedIconButtonNormal(
                    modifier = Modifier.size(24.dp),
                    icon = R.drawable.graphic_company_12dp_svg,
                    pushBadge = {
                        WantedPushBadge()
                    },
                    onClick = {}
                )
            }
        }
    }
}