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
import androidx.compose.runtime.remember
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

@Composable
fun Modifier.wantedDropShadow(style: WantedShadowStyle): Modifier {
    // shadow 리스트를 remember로 캐싱하여 재계산 방지
    val cachedShadows = remember(style) { style.getShadow() }

    return this
        .dropShadow(
            shadows = cachedShadows,
            borderRadius = style.borderRadius,
            isBackgroundTransparent = style.backgroundColor == Color.Transparent
        )
        .background(
            color = style.backgroundColor,
            shape = RoundedCornerShape(style.borderRadius)
        )
}

private fun Modifier.dropShadow(
    shadows: List<WantedShadowToken>,
    borderRadius: Dp = 0.dp,
    isBackgroundTransparent: Boolean = false
) = this.then(
    Modifier.drawBehind {
        // Early return으로 불필요한 연산 방지
        if (shadows.isEmpty()) return@drawBehind

        // BlurMaskFilter 캐시 - dropShadow 함수 내부에서만 사용
        val blurMaskFilterCache = mutableMapOf<Float, BlurMaskFilter>()

        this.drawIntoCanvas { canvas ->
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()

            // transparent 배경인 경우를 위한 Path - 재사용 가능하도록 미리 생성
            var clipPath: Path? = null
            val borderRadiusPx = borderRadius.toPx()

            if (isBackgroundTransparent && borderRadiusPx > 0f) {
                clipPath = Path().apply {
                    addRoundRect(
                        androidx.compose.ui.geometry.RoundRect(
                            left = 0f,
                            top = 0f,
                            right = size.width,
                            bottom = size.height,
                            radiusX = borderRadiusPx,
                            radiusY = borderRadiusPx
                        )
                    )
                }
            }

            // shadow를 뒤에서부터 그려서 올바른 layering 구현
            for (i in shadows.size - 1 downTo 0) {
                val shadow = shadows[i]

                // 색상 설정
                frameworkPaint.color = shadow.color.toArgb()

                // BlurMaskFilter 캐싱 및 재사용 - 같은 drawBehind 호출 내에서만 캐싱
                val blurRadiusPx = shadow.blurRadius.toPx()
                if (blurRadiusPx > 0f) {
                    frameworkPaint.maskFilter = blurMaskFilterCache.getOrPut(blurRadiusPx) {
                        BlurMaskFilter(blurRadiusPx, BlurMaskFilter.Blur.NORMAL)
                    }
                } else {
                    frameworkPaint.maskFilter = null
                }

                // 그리기 영역 계산 - 변수 재사용으로 메모리 할당 최소화
                val spreadPixel = shadow.spreadRadius.toPx()
                val offsetXPx = shadow.offsetX.toPx()
                val offsetYPx = shadow.offsetY.toPx()

                val left = -spreadPixel + offsetXPx
                val top = -spreadPixel + offsetYPx
                val right = size.width + spreadPixel + offsetXPx
                val bottom = size.height + spreadPixel + offsetYPx

                // Clipping 처리 최적화
                var needsRestore = false
                if (isBackgroundTransparent) {
                    canvas.save()
                    needsRestore = true

                    // 미리 생성된 clipPath 재사용
                    clipPath?.let { path ->
                        canvas.clipPath(path, ClipOp.Difference)
                    }
                }

                // 그리기 - 조건부 radius 최적화
                if (borderRadiusPx > 0f) {
                    canvas.drawRoundRect(
                        left = left,
                        top = top,
                        right = right,
                        bottom = bottom,
                        radiusX = borderRadiusPx,
                        radiusY = borderRadiusPx,
                        paint = paint
                    )
                } else {
                    // radius가 0이면 더 빠른 drawRect 사용
                    canvas.drawRect(
                        left = left,
                        top = top,
                        right = right,
                        bottom = bottom,
                        paint = paint
                    )
                }

                if (needsRestore) {
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
        // 성능을 위해 lazy로 shadow 리스트 생성
        private val shadowList by lazy {
            listOf(
                WantedShadowToken(
                    offsetX = 0.dp,
                    offsetY = 1.dp,
                    blurRadius = 2.dp,
                    spreadRadius = (-1).dp,
                    color = Color(0x1A171717) // rgba(23, 23, 23, 0.10)
                )
            )
        }

        override fun getShadow() = shadowList
    }

    data class Small(
        override val borderRadius: Dp = 12.dp,
        override val backgroundColor: Color = Color.White
    ) : WantedShadowStyle(borderRadius, backgroundColor) {
        private val shadowList by lazy {
            listOf(
                WantedShadowToken(
                    offsetX = 0.dp,
                    offsetY = 2.dp,
                    blurRadius = 4.dp,
                    spreadRadius = (-2).dp,
                    color = Color(0x0F171717) // rgba(23, 23, 23, 0.06)
                ),
                WantedShadowToken(
                    offsetX = 0.dp,
                    offsetY = 4.dp,
                    blurRadius = 6.dp,
                    spreadRadius = (-1).dp,
                    color = Color(0x0F171717) // rgba(23, 23, 23, 0.06)
                )
            )
        }

        override fun getShadow() = shadowList
    }

    data class Medium(
        override val borderRadius: Dp = 12.dp,
        override val backgroundColor: Color = Color.White
    ) : WantedShadowStyle(borderRadius, backgroundColor) {
        private val shadowList by lazy {
            listOf(
                WantedShadowToken(
                    offsetX = 0.dp,
                    offsetY = 4.dp,
                    blurRadius = 6.dp,
                    spreadRadius = (-2).dp,
                    color = Color(0x12000000) // rgba(0, 0, 0, 0.07)
                ),
                WantedShadowToken(
                    offsetX = 0.dp,
                    offsetY = 10.dp,
                    blurRadius = 15.dp,
                    spreadRadius = (-3).dp,
                    color = Color(0x12171717) // rgba(23, 23, 23, 0.07)
                )
            )
        }

        override fun getShadow() = shadowList
    }

    data class Large(
        override val borderRadius: Dp = 12.dp,
        override val backgroundColor: Color = Color.White
    ) : WantedShadowStyle(borderRadius, backgroundColor) {
        private val shadowList by lazy {
            listOf(
                WantedShadowToken(
                    offsetX = 0.dp,
                    offsetY = 6.dp,
                    blurRadius = 10.dp,
                    spreadRadius = (-4).dp,
                    color = Color(0x14171717) // rgba(23, 23, 23, 0.08)
                ),
                WantedShadowToken(
                    offsetX = 0.dp,
                    offsetY = 16.dp,
                    blurRadius = 24.dp,
                    spreadRadius = (-6).dp,
                    color = Color(0x14171717) // rgba(23, 23, 23, 0.08)
                )
            )
        }

        override fun getShadow() = shadowList
    }

    data class XLarge(
        override val borderRadius: Dp = 12.dp,
        override val backgroundColor: Color = Color.White
    ) : WantedShadowStyle(borderRadius, backgroundColor) {
        private val shadowList by lazy {
            listOf(
                WantedShadowToken(
                    offsetX = 0.dp,
                    offsetY = 10.dp,
                    blurRadius = 15.dp,
                    spreadRadius = (-5).dp,
                    color = Color(0x1A171717) // rgba(23, 23, 23, 0.10)
                ),
                WantedShadowToken(
                    offsetX = 0.dp,
                    offsetY = 24.dp,
                    blurRadius = 38.dp,
                    spreadRadius = (-10).dp,
                    color = Color(0x1F171717) // rgba(23, 23, 23, 0.12)
                )
            )
        }

        override fun getShadow() = shadowList
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