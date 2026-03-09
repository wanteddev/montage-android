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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.base.WantedDropShadowDefaults.WantedShadowSpreadStyle
import com.wanted.android.wanted.design.base.WantedDropShadowDefaults.WantedShadowStyle
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews


/**
 * WantedDropShadow
 *
 * 블러 효과를 사용한 Drop shadow를 생성하는 컴포넌트입니다.
 *
 * 간단한 블러 효과로 그림자를 구현하며, 커스텀 형태를 지원합니다.
 * 배경색, 그림자 색상, 블러 정도를 개별적으로 설정할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedDropShadow(
 *     modifier = Modifier.size(100.dp),
 *     background = Color.Transparent,
 *     dropShadowColor = Color.Black.copy(0.1f),
 *     blur = 2.dp,
 *     shape = RoundedCornerShape(12.dp)
 * )
 * ```
 *
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param background Color: 배경 색상입니다.
 * @param dropShadowColor Color: 드롭 Shadow의 색상입니다.
 * @param blur Dp: 블러의 정도입니다.
 * @param shape Shape: 컴포넌트의 형태입니다.
 */
@Composable
fun WantedDropShadow(
    modifier: Modifier = Modifier,
    background: Color = DesignSystemTheme.colors.transparent,
    dropShadowColor: Color = DesignSystemTheme.colors.staticBlack.copy(0.03f),
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

/**
 * fun Modifier.wantedDropShadow(...)
 *
 * 컴포넌트에 Drop shadow 효과를 적용하는 Modifier 확장 함수입니다.
 *
 * WantedShadowStyle을 사용하여 미리 정의된 그림자 스타일을 적용합니다.
 * 배경색과 테두리 반경을 함께 설정할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * Box(
 *     Modifier
 *         .size(100.dp)
 *         .wantedDropShadow(WantedShadowStyle.Medium())
 * )
 * ```
 *
 * @param style WantedShadowStyle: 적용할 Shadow 스타일입니다.
 * @return Modifier: Drop shadow 가 적용된 Modifier를 반환합니다.
 */
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

/**
 * fun Modifier.wantedDropShadowSpread(...)
 *
 * 컴포넌트에 확산형 Drop shadw 효과를 적용하는 Modifier 확장 함수입니다.
 *
 * WantedShadowSpreadStyle을 사용하여 확산형 그림자 스타일을 적용합니다.
 * 일반 그림자보다 더 넓게 퍼지는 효과를 제공합니다.
 *
 * 사용 예시:
 * ```kotlin
 * Box(
 *     Modifier
 *         .size(100.dp)
 *         .wantedDropShadowSpread(WantedShadowSpreadStyle.Medium())
 * )
 * ```
 *
 * @param style WantedShadowSpreadStyle: 적용할 확산형 Shadow 스타일입니다.
 * @return Modifier: 확산형 드롭 Shadow가 적용된 Modifier를 반환합니다.
 */
@Composable
fun Modifier.wantedDropShadowSpread(style: WantedShadowSpreadStyle): Modifier {
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
    shadows: List<WantedDropShadowDefaults.WantedShadowToken>,
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


/**
 * object WantedDropShadowDefaults
 *
 * 이 객체는 Drop shadow 스타일과 토큰을 정의하고 관리합니다.
 *
 * WantedShadowStyle과 WantedShadowSpreadStyle을 통해 다양한 그림자 효과를 제공하며,
 * WantedShadowToken을 통해 그림자의 세부 속성을 정의합니다.
 *
 */
object WantedDropShadowDefaults {
    /**
     * sealed class WantedShadowStyle
     *
     * Drop shadow의 기본 스타일을 정의하는 sealed 클래스입니다.
     * 다양한 크기의 그림자 프리셋을 제공합니다.
     *
     * @param borderRadius Dp: 테두리의 반경입니다.
     * @param backgroundColor Color: 배경 색상입니다.
     */
    sealed class WantedShadowStyle(
        open val borderRadius: Dp,
        open val backgroundColor: Color
    ) {
        /**
         * abstract fun getShadow()
         *
         * Shadow 토큰을 반환하는 함수입니다.
         *
         * @return List<WantedShadowToken>: Shadow 토큰의 리스트입니다.
         */
        abstract fun getShadow(): List<WantedShadowToken>

        /**
         * data class XSmall
         *
         * XSmall Shadow 스타일을 반환하는 데이터 클래스입니다.
         *
         * @param borderRadius Dp: 테두리의 반경입니다. 기본값은 12.dp입니다.
         * @param backgroundColor Color: 배경 색상입니다. 기본값은 Color.Transparent입니다.
         */
        data class XSmall(
            override val borderRadius: Dp = 12.dp,
            override val backgroundColor: Color = Color.Transparent
        ) : WantedShadowStyle(borderRadius, backgroundColor) {
            // 성능을 위해 lazy로 shadow 리스트 생성
            private val shadowList by lazy {
                listOf(
                    WantedShadowToken(
                        offsetX = 0.dp,
                        offsetY = 1.dp,
                        blurRadius = 0.87.dp,
                        spreadRadius = (-1).dp,
                        color = Color(0x1A171717) // rgba(23, 23, 23, 0.10)
                    )
                )
            }

            override fun getShadow() = shadowList
        }

        /**
         * data class Small
         *
         * Small Shadow 스타일을 반환하는 데이터 클래스입니다.
         *
         * @param borderRadius Dp: 테두리의 반경입니다. 기본값은 12.dp입니다.
         * @param backgroundColor Color: 배경 색상입니다. 기본값은 Color.Transparent입니다.
         */
        data class Small(
            override val borderRadius: Dp = 12.dp,
            override val backgroundColor: Color = Color.Transparent
        ) : WantedShadowStyle(borderRadius, backgroundColor) {
            private val shadowList by lazy {
                listOf(
                    WantedShadowToken(
                        offsetX = 0.dp,
                        offsetY = 2.dp,
                        blurRadius = 1.73.dp,
                        spreadRadius = (-2).dp,
                        color = Color(0x0F171717) // rgba(23, 23, 23, 0.06)
                    ),
                    WantedShadowToken(
                        offsetX = 0.dp,
                        offsetY = 4.dp,
                        blurRadius = 4.33.dp,
                        spreadRadius = (-1).dp,
                        color = Color(0x0F171717) // rgba(23, 23, 23, 0.06)
                    )
                )
            }

            override fun getShadow() = shadowList
        }

        /**
         * data class Medium
         *
         * Medium Shadow 스타일을 반환하는 데이터 클래스입니다.
         *
         * @param borderRadius Dp: 테두리의 반경입니다. 기본값은 12.dp입니다.
         * @param backgroundColor Color: 배경 색상입니다. 기본값은 Color.Transparent입니다.
         */
        data class Medium(
            override val borderRadius: Dp = 12.dp,
            override val backgroundColor: Color = Color.Transparent
        ) : WantedShadowStyle(borderRadius, backgroundColor) {
            private val shadowList by lazy {
                listOf(
                    WantedShadowToken(
                        offsetX = 0.dp,
                        offsetY = 4.dp,
                        blurRadius = 3.46.dp,
                        spreadRadius = (-2).dp,
                        color = Color(0x12000000) // rgba(0, 0, 0, 0.07)
                    ),
                    WantedShadowToken(
                        offsetX = 0.dp,
                        offsetY = 10.dp,
                        blurRadius = 10.39.dp,
                        spreadRadius = (-3).dp,
                        color = Color(0x12171717) // rgba(23, 23, 23, 0.07)
                    )
                )
            }

            override fun getShadow() = shadowList
        }

        /**
         * data class Large
         *
         * Large Shadow 스타일을 반환하는 데이터 클래스입니다.
         *
         * @param borderRadius Dp: 테두리의 반경입니다. 기본값은 12.dp입니다.
         * @param backgroundColor Color: 배경 색상입니다. 기본값은 Color.Transparent입니다.
         */
        data class Large(
            override val borderRadius: Dp = 12.dp,
            override val backgroundColor: Color = Color.Transparent
        ) : WantedShadowStyle(borderRadius, backgroundColor) {
            private val shadowList by lazy {
                listOf(
                    WantedShadowToken(
                        offsetX = 0.dp,
                        offsetY = 6.dp,
                        blurRadius = 5.20.dp,
                        spreadRadius = (-4).dp,
                        color = Color(0x14171717) // rgba(23, 23, 23, 0.08)
                    ),
                    WantedShadowToken(
                        offsetX = 0.dp,
                        offsetY = 16.dp,
                        blurRadius = 15.59.dp,
                        spreadRadius = (-6).dp,
                        color = Color(0x14171717) // rgba(23, 23, 23, 0.08)
                    )
                )
            }

            override fun getShadow() = shadowList
        }

        /**
         * data class XLarge
         *
         * XLarge Shadow 스타일을 반환하는 데이터 클래스입니다.
         *
         * @param borderRadius Dp: 테두리의 반경입니다. 기본값은 12.dp입니다.
         * @param backgroundColor Color: 배경 색상입니다. 기본값은 Color.Transparent입니다.
         */
        data class XLarge(
            override val borderRadius: Dp = 12.dp,
            override val backgroundColor: Color = Color.Transparent
        ) : WantedShadowStyle(borderRadius, backgroundColor) {
            private val shadowList by lazy {
                listOf(
                    WantedShadowToken(
                        offsetX = 0.dp,
                        offsetY = 10.dp,
                        blurRadius = 8.66.dp,
                        spreadRadius = (-5).dp,
                        color = Color(0x1A171717) // rgba(23, 23, 23, 0.10)
                    ),
                    WantedShadowToken(
                        offsetX = 0.dp,
                        offsetY = 24.dp,
                        blurRadius = 24.25.dp,
                        spreadRadius = (-10).dp,
                        color = Color(0x1F171717) // rgba(23, 23, 23, 0.12)
                    )
                )
            }

            override fun getShadow() = shadowList
        }
    }

    /**
     * sealed class WantedShadowSpreadStyle
     *
     * 확산형 Drop shadow 스타일을 정의하는 sealed 클래스입니다.
     * 일반 그림자보다 더 넓게 퍼지는 효과를 제공합니다.
     *
     * 제공되는 스타일:
     * - Small: 작은 확산형 그림자입니다.
     * - Medium: 중간 크기의 확산형 그림자입니다.
     *
     * @param borderRadius Dp: 테두리의 반경입니다.
     * @param backgroundColor Color: 배경 색상입니다.
     */
    sealed class WantedShadowSpreadStyle(
        open val borderRadius: Dp,
        open val backgroundColor: Color
    ) {
        /**
         * abstract fun getShadow()
         *
         * Shadow 토큰을 반환하는 함수입니다.
         *
         * @return List<WantedShadowToken>: Shadow 토큰의 리스트입니다.
         */
        abstract fun getShadow(): List<WantedShadowToken>

        /**
         * data class Small
         *
         * Small 확산형 Shadow 스타일을 반환하는 데이터 클래스입니다.
         *
         * @param borderRadius Dp: 테두리의 반경입니다. 기본값은 12.dp입니다.
         * @param backgroundColor Color: 배경 색상입니다. 기본값은 Color.Transparent입니다.
         */
        data class Small(
            override val borderRadius: Dp = 12.dp,
            override val backgroundColor: Color = Color.Transparent
        ) : WantedShadowSpreadStyle(borderRadius, backgroundColor) {
            private val shadowList by lazy {
                listOf(
                    WantedShadowToken(
                        offsetX = 0.dp,
                        offsetY = 0.dp,
                        blurRadius = 60.dp,
                        spreadRadius = (0).dp,
                        color = Color(0x1A171717) // rgba(23, 23, 23, 0.1)
                    )
                )
            }

            override fun getShadow() = shadowList
        }

        /**
         * data class Medium
         *
         * Medium 확산형 Shadow 스타일을 반환하는 데이터 클래스입니다.
         *
         * @param borderRadius Dp: 테두리의 반경입니다. 기본값은 12.dp입니다.
         * @param backgroundColor Color: 배경 색상입니다. 기본값은 Color.Transparent입니다.
         */
        data class Medium(
            override val borderRadius: Dp = 12.dp,
            override val backgroundColor: Color = Color.Transparent
        ) : WantedShadowSpreadStyle(borderRadius, backgroundColor) {
            private val shadowList by lazy {
                listOf(
                    WantedShadowToken(
                        offsetX = 0.dp,
                        offsetY = 15.dp,
                        blurRadius = 75.dp,
                        spreadRadius = (0).dp,
                        color = Color(0x1A171717) // rgba(23, 23, 23, 0.1)
                    ),
                )
            }

            override fun getShadow() = shadowList
        }
    }

    /**
     * data class WantedShadowToken
     *
     * Shadow의 개별 속성을 정의하는 데이터 클래스입니다.
     * Shadow의 위치, 크기, 색상 등을 설정할 수 있습니다.
     *
     * @param offsetX Dp: Shadow의 수평 오프셋입니다.
     * @param offsetY Dp: Shadow의 수직 오프셋입니다.
     * @param blurRadius Dp: Shadow의 블러 반경입니다.
     * @param spreadRadius Dp: Shadow의 확산 반경입니다.
     * @param color Color: Shadow의 색상입니다.
     */
    data class WantedShadowToken(
        val offsetX: Dp,
        val offsetY: Dp,
        val blurRadius: Dp,
        val spreadRadius: Dp,
        val color: Color
    )
}


@DevicePreviews
@Composable
private fun WantedDropShadowPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .background(DesignSystemTheme.colors.backgroundNormalAlternative)
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
                                .copy(backgroundColor = DesignSystemTheme.colors.transparent)
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