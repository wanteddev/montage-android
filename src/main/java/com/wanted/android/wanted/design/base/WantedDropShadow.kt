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
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
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
fun Modifier.wantedDropShadow(style: WantedShadowStyle) = this
    .dropShadow(
        shadows = style.getShadow(),
        borderRadius = style.borderRadius,
        isBackgroundTransparent = style.backgroundColor == Color.Transparent
    )
    .background(
        color = style.backgroundColor,
        shape = RoundedCornerShape(style.borderRadius)
    )

// 성능 최적화된 다중 shadow 지원하는 modifier
private fun Modifier.dropShadow(
    shadows: List<WantedShadowToken>,
    borderRadius: Dp = 0.dp,
    isBackgroundTransparent: Boolean = false
) = this.then(
    Modifier.drawBehind {
        this.drawIntoCanvas { canvas ->
            // Paint 객체를 한 번만 생성하고 재사용
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()

            // shadow를 뒤에서부터 그려서 올바른 layering 구현
            // reversed() 대신 인덱스로 역순 접근하여 새로운 리스트 생성 방지
            for (i in shadows.size - 1 downTo 0) {
                val shadow = shadows[i]

                // Paint 설정을 각 shadow마다 업데이트
                frameworkPaint.color = shadow.color.toArgb()

                // BlurMaskFilter 설정
                if (shadow.blurRadius > 0.dp) {
                    frameworkPaint.maskFilter = BlurMaskFilter(
                        shadow.blurRadius.toPx(),
                        BlurMaskFilter.Blur.NORMAL
                    )
                } else {
                    // blur가 0이면 maskFilter를 null로 설정
                    frameworkPaint.maskFilter = null
                }

                val spreadPixel = shadow.spreadRadius.toPx()
                val left = -spreadPixel + shadow.offsetX.toPx()
                val top = -spreadPixel + shadow.offsetY.toPx()
                val right = size.width + spreadPixel + shadow.offsetX.toPx()
                val bottom = size.height + spreadPixel + shadow.offsetY.toPx()

                if (isBackgroundTransparent) {
                    // shadow 그리기 전에 원본 컴포넌트 영역을 clip out
                    canvas.save()

                    // 원본 컴포넌트의 모양을 clipPath로 제외
                    val componentPath = Path().apply {
                        addRoundRect(
                            androidx.compose.ui.geometry.RoundRect(
                                left = 0f,
                                top = 0f,
                                right = size.width,
                                bottom = size.height,
                                radiusX = borderRadius.toPx(),
                                radiusY = borderRadius.toPx()
                            )
                        )
                    }

                    // 원본 컴포넌트 영역을 제외하고 shadow 그리기
                    canvas.clipPath(componentPath, ClipOp.Difference)
                }

                canvas.drawRoundRect(
                    left = left,
                    top = top,
                    right = right,
                    bottom = bottom,
                    radiusX = borderRadius.toPx(),
                    radiusY = borderRadius.toPx(),
                    paint = paint
                )

                if (isBackgroundTransparent) {
                    canvas.restore()
                }
            }
        }
    }
)

sealed class WantedShadowStyle(
    open val borderRadius: Dp,
    open val backgroundColor: Color
) {
    abstract fun getShadow(): List<WantedShadowToken>

    data class XSmall(
        override val borderRadius: Dp = 12.dp,
        override val backgroundColor: Color = Color.White
    ) : WantedShadowStyle(borderRadius, backgroundColor) {
        override fun getShadow() = listOf(
            // 0 1px 2px -1px rgba(23, 23, 23, 0.10)
            WantedShadowToken(
                offsetX = 0.dp,
                offsetY = 1.dp,
                blurRadius = 2.dp,
                spreadRadius = (-1).dp,
                color = Color(0x1A171717) // rgba(23, 23, 23, 0.10)
            )
        )
    }

    data class Small(
        override val borderRadius: Dp = 12.dp,
        override val backgroundColor: Color = Color.White
    ) : WantedShadowStyle(borderRadius, backgroundColor) {
        override fun getShadow() = listOf(
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
    }

    data class Medium(
        override val borderRadius: Dp = 12.dp,
        override val backgroundColor: Color = Color.White
    ) : WantedShadowStyle(borderRadius, backgroundColor) {
        override fun getShadow() = listOf(
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
    }

    data class Large(
        override val borderRadius: Dp = 12.dp,
        override val backgroundColor: Color = Color.White
    ) : WantedShadowStyle(borderRadius, backgroundColor) {
        override fun getShadow(): List<WantedShadowToken> =
                listOf(
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
    }

    data class XLarge(
        override val borderRadius: Dp = 12.dp,
        override val backgroundColor: Color = Color.White
    ) : WantedShadowStyle(borderRadius, backgroundColor) {
        override fun getShadow() = listOf(
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

                // backgroud color가 transparent 일때
                Box(
                    Modifier
                        .size(100.dp)
                        .wantedDropShadow(
                            style = WantedShadowStyle
                                .XLarge()
                                .copy(backgroundColor = colorResource(R.color.transparent))
                        )
                )

                // XSMALL Shadow
                Box(
                    Modifier
                        .size(100.dp)
                        .wantedDropShadow(WantedShadowStyle.XSmall())
                )

                // SMALL Shadow
                Box(
                    Modifier
                        .size(100.dp)
                        .wantedDropShadow(WantedShadowStyle.Small())
                )

                // MEDIUM Shadow
                Box(
                    Modifier
                        .size(100.dp)
                        .wantedDropShadow(WantedShadowStyle.Medium())
                )

                // LARGE Shadow
                Box(
                    Modifier
                        .size(100.dp)
                        .wantedDropShadow(WantedShadowStyle.Large())
                )

                // XLARGE Shadow
                Box(
                    Modifier
                        .size(100.dp)
                        .wantedDropShadow(WantedShadowStyle.XLarge())
                )

            }
        }
    }
}