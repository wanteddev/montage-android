package com.wanted.android.wanted.design.loading.skeleton

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.res.colorResource
import com.wanted.android.designsystem.R

fun Modifier.shimmer(
    colorRes: Int = R.color.background_normal_normal, // 기본 색상 리소스
): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "")
    val alphaAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 0.66f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = ANIMATION_DURATION,
                easing = CubicBezierEasing(0.42f, 0.0f, 0.58f, 1.0f)
            ),
            repeatMode = RepeatMode.Reverse // Reverse로 깜박이는 효과 추가
        ), label = ""
    )

    // Shimmer 컬러
    val shimmerColor = colorResource(id = colorRes)

    // 실제 Modifier에 적용

    drawWithContent {
        drawContent() // 원본 콘텐츠를 먼저 그린 후, 애니메이션을 덮어씌움

        // 깜박이는 Shimmer 애니메이션을 적용
        drawRect(color = shimmerColor.copy(alpha = alphaAnimation))
    }
}

private const val ANIMATION_DURATION = 1000