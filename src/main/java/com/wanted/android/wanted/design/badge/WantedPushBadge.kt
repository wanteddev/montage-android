package com.wanted.android.wanted.design.badge

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
fun WantedPushBadge(
    modifier: Modifier = Modifier,
    variant: PushBadgeVariant = PushBadgeVariant.Dot,
    count: Int = 0,
) {
    when (variant) {
        PushBadgeVariant.Dot -> PushBadgeDot(modifier)
        PushBadgeVariant.Number -> PushBadgeImpl(modifier, "$count")
        PushBadgeVariant.New -> PushBadgeImpl(modifier, "N")
    }
}

@Composable
private fun PushBadgeDot(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(20.dp)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.primary_normal), shape = CircleShape)
        )
    }
}

@Composable
private fun PushBadgeImpl(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(
        modifier = modifier
            .size(20.dp)
            .background(colorResource(id = R.color.primary_normal), shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = WantedTextStyle(
                colorRes = R.color.static_white,
                style = DesignSystemTheme.typography.caption2Bold
            )
        )
    }
}

enum class PushBadgeVariant {
    Dot,
    Number,
    New
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
private fun WantedPushBadgePreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedPushBadge(
                    modifier = Modifier
                )

                WantedPushBadge(
                    modifier = Modifier,
                    variant = PushBadgeVariant.Number,
                    count = 1
                )

                WantedPushBadge(
                    modifier = Modifier,
                    variant = PushBadgeVariant.New
                )
            }
        }
    }
}