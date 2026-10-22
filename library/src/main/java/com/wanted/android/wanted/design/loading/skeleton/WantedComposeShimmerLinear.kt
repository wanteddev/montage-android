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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.colorResource
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.OPACITY_16

/**
 * fun Modifier.shimmerLinear(...)
 *
 * 콘텐츠 위에 color 와 반투명 color 를 잇는 가로 방향 그라데이션을 덮고, 1.5초 주기로 흐르게 하는 shimmer 효과를 적용합니다.
 *
 * 원래 콘텐츠를 먼저 그린 뒤 그 위에 사각형을 칠하므로, 모양(둥근 모서리 등)이 필요하면 이 Modifier 앞에 clip 을 적용합니다. 색을 리소스 ID 로 넘기려면 colorRes 오버로드를 사용합니다.
 *
 * @param color Color: 그라데이션의 기준 색입니다.
 * @param alpha Float: 그라데이션 반대쪽 끝에 쓸 color 의 투명도입니다. 기본값은 OPACITY_16(0.16) 입니다.
 */
fun Modifier.shimmerLinear(
    color: Color,
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
        color,
        color.copy(alpha = alpha),
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

/**
 * fun Modifier.shimmerLinear(...)
 *
 * shimmerLinear 의 색 리소스 오버로드입니다. colorRes 를 색으로 바꿔 color 오버로드와 같은 효과를 적용합니다.
 *
 * @param colorRes Int: 그라데이션 기준 색의 리소스 ID(`@ColorRes`)입니다. 기본값은 R.color.surface_neutral_tertiary 입니다.
 * @param alpha Float: 그라데이션 반대쪽 끝에 쓸 색의 투명도입니다. 기본값은 OPACITY_16(0.16) 입니다.
 */
fun Modifier.shimmerLinear(
    colorRes: Int = R.color.surface_neutral_tertiary,
    alpha: Float = OPACITY_16,
): Modifier = composed {
    shimmerLinear(
        color = colorResource(id = colorRes),
        alpha = alpha
    )
}

private const val ANIMATION_DURATION = 1500
private const val ANIMATION_INTERVAL = 600f
private const val GRADATION_DISTANCE_X = 300f
private const val GRADATION_DISTANCE_ANGLE = 0f