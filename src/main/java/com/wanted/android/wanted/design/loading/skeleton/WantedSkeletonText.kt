package com.wanted.android.wanted.design.loading.skeleton

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.loading.shimmer
import com.wanted.android.wanted.design.theme.DesignSystemTheme


/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=23703-75675&m=dev
 */

@Composable
fun WantedSkeletonText(
    modifier: Modifier = Modifier,
    length: WantedSkeletonLength,
    align: WantedSkeAlign = WantedSkeAlign.Left,
    shape: RoundedCornerShape = RoundedCornerShape(3.dp)
) {
    WantedSkeletonText(
        modifier = modifier.defaultMinSize(minHeight = 22.dp),
        align = align,
        widthRadio = length.radio,
        shape = shape
    )
}

@Composable
fun WantedSkeletonText(
    modifier: Modifier = Modifier,
    align: WantedSkeAlign = WantedSkeAlign.Left,
    widthRadio: Float,
    shape: RoundedCornerShape = RoundedCornerShape(3.dp),
    color: Color = colorResource(id = R.color.fill_normal)
) {
    ConstraintLayout(
        modifier = modifier
            .defaultMinSize(minHeight = 22.dp)
            .fillMaxWidth()
    ) {
        val (row) = createRefs()
        Row(
            modifier = Modifier
                .constrainAs(row) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints  // Match width of text
                    height = Dimension.fillToConstraints // Match height of text
                }
                .fillMaxWidth()
                .padding(vertical = 2.dp)
                .wrapContentHeight(),
            horizontalArrangement = when (align) {
                WantedSkeAlign.Left -> Arrangement.Start
                WantedSkeAlign.Center -> Arrangement.Center
                WantedSkeAlign.Right -> Arrangement.End
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(widthRadio)
                    .fillMaxHeight()
                    .clip(shape)
                    .background(color = color)
                    .shimmer()

            )
        }

    }

}

enum class WantedSkeletonLength(
    val radio: Float
) {
    Ratio100(radio = 1f),
    Ratio75(radio = 0.75f),
    Ratio50(radio = 0.5f),
    Ratio25(radio = 0.25f)
}

enum class WantedSkeAlign {
    Left,
    Center,
    Right,
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
                WantedSkeletonText(
                    length = WantedSkeletonLength.Ratio100,
                    align = WantedSkeAlign.Left,
                )

                WantedSkeletonText(
                    length = WantedSkeletonLength.Ratio75,
                    align = WantedSkeAlign.Left,
                )

                WantedSkeletonText(
                    length = WantedSkeletonLength.Ratio50,
                    align = WantedSkeAlign.Right,
                )

                WantedSkeletonText(
                    length = WantedSkeletonLength.Ratio25,
                    align = WantedSkeAlign.Center,
                )
            }
        }

    }
}
