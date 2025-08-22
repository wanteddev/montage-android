package com.wanted.android.wanted.design.base

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews


fun Modifier.dropShadow(
    color: Color = Color(0f, 0f, 0f, 0.12f),
    borderRadius: Dp = 10.dp,
    blurRadius: Dp = 2.dp,
    offsetY: Dp = 1.dp,
    offsetX: Dp = 1.dp,
    spread: Float = 0f,
    modifier: Modifier = Modifier,
) = this.then(
    modifier.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            val spreadPixel = spread.dp.toPx()
            val leftPixel = (0f - spreadPixel) + offsetX.toPx()
            val topPixel = (0f - spreadPixel) + offsetY.toPx()
            val rightPixel = (this.size.width + spreadPixel)
            val bottomPixel = (this.size.height + spreadPixel)

            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter =
                    (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }

            frameworkPaint.color = color.toArgb()
            it.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = borderRadius.toPx(),
                radiusY = borderRadius.toPx(),
                paint
            )
        }
    }
)

@Composable
fun WantedDropShadow(
    modifier: Modifier = Modifier,
    background: Color = colorResource(id = R.color.background_normal_normal),
    dropShadowColor: Color = colorResource(id = R.color.static_black).copy(0.03f),
    blur: Dp = 1.dp,
    shape: Shape
) {
    Box(modifier = modifier) {
        Box(
            Modifier
                .fillMaxSize()
                .blur(blur, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .border(
                    shape = shape,
                    color = dropShadowColor,
                    width = 1.dp
                )

        )

        Box(
            Modifier
                .fillMaxSize()
                .background(
                    shape = shape,
                    color = background
                )
        )
    }

}

@DevicePreviews
@Composable
private fun WantedDropShadowPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                Box(
                    Modifier
                        .size(100.dp)
                        .dropShadow()
                        .background(
                            shape = RoundedCornerShape(12.dp),
                            color = colorResource(id = R.color.background_normal_normal)
                        )

                )
            }
        }
    }
}