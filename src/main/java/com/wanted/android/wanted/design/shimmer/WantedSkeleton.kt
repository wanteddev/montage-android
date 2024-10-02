package com.wanted.android.wanted.design.shimmer

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_16
import com.wanted.android.wanted.design.util.OPACITY_5


@Composable
fun WantedSkeleton(
    modifier: Modifier = Modifier,
    widthRadio: Float,
    isCenter: Boolean = false,
    height: Dp,
    shape: RoundedCornerShape,
    colorRes: Int = R.color.fill_alternative,
    alpha: Float = OPACITY_16
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {

        if (widthRadio != 1f && isCenter) {
            Box(
                modifier = Modifier
                    .weight((1 - widthRadio)* 0.5f)
                    .height(height)
            )
        }

        Box(
            modifier = Modifier
                .weight(widthRadio)
                .height(height)
                .clip(shape)
                .shimmer(
                    colorRes = colorRes,
                    alpha = alpha
                )
        )

        if (widthRadio != 1f) {
            Box(
                modifier = Modifier
                    .weight((1 - widthRadio) * (if (isCenter) 0.5f else 1f))
                    .height(height)
            )
        }
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
fun WantedSkeletonPreview() {
    DesignSystemTheme {
        Surface(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                WantedSkeleton(
                    widthRadio = 0.75f,
                    height = 18.dp,
                    shape = RoundedCornerShape(3.dp),
                    colorRes = R.color.fill_alternative,
                    alpha = OPACITY_5
                )

                WantedSkeleton(
                    widthRadio = 0.75f,
                    height = 18.dp,
                    isCenter = true,
                    shape = RoundedCornerShape(3.dp),
                    colorRes = R.color.fill_alternative,
                    alpha = OPACITY_5
                )
            }
        }

    }
}
