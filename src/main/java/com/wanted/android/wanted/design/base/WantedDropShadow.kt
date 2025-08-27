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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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


// 편의 함수들
fun Modifier.wantedDropShadow(
    size: WantedShadowSize
): Modifier {
    val shadowStyle = size.toShadowStyle()
    return this
        .dropShadow(
            shadows = shadowStyle.shadows,
            borderRadius = shadowStyle.borderRadius
        )
        .background(
            color = shadowStyle.backgroundColor,
            shape = RoundedCornerShape(shadowStyle.borderRadius)
        )
}


// 다중 shadow 지원하는 modifier
private fun Modifier.dropShadow(
    shadows: List<WantedShadowToken>,
    borderRadius: Dp = 0.dp
) = this.then(
    Modifier.drawBehind {
        this.drawIntoCanvas { canvas ->
            // shadow를 뒤에서부터 그려서 올바른 layering 구현
            shadows.reversed().forEach { shadow ->
                val paint = Paint()
                val frameworkPaint = paint.asFrameworkPaint()

                val spreadPixel = shadow.spreadRadius.toPx()
                val left = -spreadPixel + shadow.offsetX.toPx()
                val top = -spreadPixel + shadow.offsetY.toPx()
                val right = size.width + spreadPixel + shadow.offsetX.toPx()
                val bottom = size.height + spreadPixel + shadow.offsetY.toPx()

                if (shadow.blurRadius > 0.dp) {
                    frameworkPaint.maskFilter = BlurMaskFilter(
                        shadow.blurRadius.toPx(),
                        BlurMaskFilter.Blur.NORMAL
                    )
                }

                frameworkPaint.color = shadow.color.toArgb()

                canvas.drawRoundRect(
                    left = left,
                    top = top,
                    right = right,
                    bottom = bottom,
                    radiusX = borderRadius.toPx(),
                    radiusY = borderRadius.toPx(),
                    paint = paint
                )
            }
        }
    }
)

// Shadow 스타일 정의
data class ShadowStyle(
    val shadows: List<WantedShadowToken>,
    val borderRadius: Dp = 12.dp,
    val backgroundColor: Color = Color.White
)

enum class WantedShadowSize {
    XSmall,
    Small,
    Medium,
    Large,
    XLarge;

    fun toShadowStyle(): ShadowStyle {
        return when (this) {
            XSmall -> ShadowStyle(
                shadows = listOf(
                    // 0 1px 2px -1px rgba(23, 23, 23, 0.10)
                    WantedShadowToken(
                        offsetX = 0.dp,
                        offsetY = 1.dp,
                        blurRadius = 2.dp,
                        spreadRadius = (-1).dp,
                        color = Color(0x1A171717) // rgba(23, 23, 23, 0.10)
                    )
                )
            )

            Small -> ShadowStyle(
                shadows = listOf(
                    // 0 2px 4px -2px rgba(23, 23, 23, 0.06)
                    WantedShadowToken(
                        offsetX = 0.dp,
                        offsetY = 2.dp,
                        blurRadius = 4.dp,
                        spreadRadius = (-2).dp,
                        color = Color(0x0F171717) // rgba(23, 23, 23, 0.06)
                    ),
                    // 0 4px 6px -1px rgba(23, 23, 23, 0.06)
                    WantedShadowToken(
                        offsetX = 0.dp,
                        offsetY = 4.dp,
                        blurRadius = 6.dp,
                        spreadRadius = (-1).dp,
                        color = Color(0x0F171717) // rgba(23, 23, 23, 0.06)
                    )
                )
            )

            Medium -> ShadowStyle(
                shadows = listOf(
                    // 0 4px 6px -2px rgba(0, 0, 0, 0.07)
                    WantedShadowToken(
                        offsetX = 0.dp,
                        offsetY = 4.dp,
                        blurRadius = 6.dp,
                        spreadRadius = (-2).dp,
                        color = Color(0x12000000) // rgba(0, 0, 0, 0.07)
                    ),
                    // 0 10px 15px -3px rgba(23, 23, 23, 0.07)
                    WantedShadowToken(
                        offsetX = 0.dp,
                        offsetY = 10.dp,
                        blurRadius = 15.dp,
                        spreadRadius = (-3).dp,
                        color = Color(0x12171717) // rgba(23, 23, 23, 0.07)
                    )
                )
            )

            Large -> ShadowStyle(
                shadows = listOf(
                    // 0 6px 10px -4px rgba(23, 23, 23, 0.08)
                    WantedShadowToken(
                        offsetX = 0.dp,
                        offsetY = 6.dp,
                        blurRadius = 10.dp,
                        spreadRadius = (-4).dp,
                        color = Color(0x14171717) // rgba(23, 23, 23, 0.08)
                    ),
                    // 0 16px 24px -6px rgba(23, 23, 23, 0.08)
                    WantedShadowToken(
                        offsetX = 0.dp,
                        offsetY = 16.dp,
                        blurRadius = 24.dp,
                        spreadRadius = (-6).dp,
                        color = Color(0x14171717) // rgba(23, 23, 23, 0.08)
                    )
                )
            )

            XLarge -> ShadowStyle(
                shadows = listOf(
                    // 0 10px 15px -5px rgba(23, 23, 23, 0.10)
                    WantedShadowToken(
                        offsetX = 0.dp,
                        offsetY = 10.dp,
                        blurRadius = 15.dp,
                        spreadRadius = (-5).dp,
                        color = Color(0x1A171717) // rgba(23, 23, 23, 0.10)
                    ),
                    // 0 24px 38px -10px rgba(23, 23, 23, 0.12)
                    WantedShadowToken(
                        offsetX = 0.dp,
                        offsetY = 24.dp,
                        blurRadius = 38.dp,
                        spreadRadius = (-10).dp,
                        color = Color(0x1F171717) // rgba(23, 23, 23, 0.12)
                    )
                )
            )
        }
    }
}

data class WantedShadowToken(
    val offsetX: Dp,
    val offsetY: Dp,
    val blurRadius: Dp,
    val spreadRadius: Dp,
    val color: Color
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
                    .background(colorResource(R.color.background_normal_alternative))
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {

                // XSMALL Shadow
                Box(
                    Modifier
                        .size(100.dp)
                        .wantedDropShadow(WantedShadowSize.XSmall)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(12.dp)
                        )
                )

                // SMALL Shadow
                Box(
                    Modifier
                        .size(100.dp)
                        .wantedDropShadow(WantedShadowSize.Small)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(12.dp)
                        )
                )

                // MEDIUM Shadow
                Box(
                    Modifier
                        .size(100.dp)
                        .wantedDropShadow(WantedShadowSize.Medium)
                )

                // LARGE Shadow
                Box(
                    Modifier
                        .size(100.dp)
                        .wantedDropShadow(WantedShadowSize.Large)
                )

                // XLARGE Shadow
                Box(
                    Modifier
                        .size(100.dp)
                        .wantedDropShadow(WantedShadowSize.XLarge)
                )

            }
        }
    }
}