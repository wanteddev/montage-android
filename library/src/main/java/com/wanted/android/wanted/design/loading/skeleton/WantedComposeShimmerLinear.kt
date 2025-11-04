package com.wanted.android.wanted.design.loading.skeleton

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.colorResource
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.OPACITY_16

/**
 * shimmerLinear
 *
 * Linear 방식의 shimmer 애니메이션을 적용하는 Modifier 확장 함수입니다.
 *
 * 좌상단에서 우하단 방향으로 흐르는 그라디언트 효과를 주기적으로 반복합니다.
 * 주로 스켈레톤 UI에 사용됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * Box(
 *     modifier = Modifier
 *         .size(200.dp)
 *         .shimmerLinear(
 *             colorRes = R.color.fill_alternative,
 *             alpha = 0.16f
 *         )
 * )
 * ```
 *
 * @param colorRes Int: 그라디언트 색상 리소스 ID입니다.
 * @param alpha Float: 투명도 값입니다. 0.0 ~ 1.0 사이의 값을 사용합니다.
 * @return Modifier: shimmer 애니메이션이 적용된 Modifier입니다.
 */
fun Modifier.shimmerLinear(
    colorRes: Int = R.color.fill_alternative,
    alpha: Float = OPACITY_16,
): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "")
    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = ANIMATION_INTERVAL,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = ANIMATION_DURATION, easing = LinearEasing),
            RepeatMode.Restart
        ),
        label = "",
    )
    val shimmerColors = listOf(
        colorResource(id = colorRes),
        colorResource(id = colorRes).copy(alpha = alpha),
    )
    drawWithContent {
        drawContent()
        drawRect(
            Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(translateAnimation, translateAnimation),
                end = Offset(
                    translateAnimation + GRADATION_DISTANCE_X,
                    translateAnimation + GRADATION_DISTANCE_ANGLE
                ),
                tileMode = TileMode.Mirror,
            )
        )
    }
}

private const val ANIMATION_DURATION = 1500
private const val ANIMATION_INTERVAL = 600f
private const val GRADATION_DISTANCE_X = 300f
private const val GRADATION_DISTANCE_ANGLE = 0f