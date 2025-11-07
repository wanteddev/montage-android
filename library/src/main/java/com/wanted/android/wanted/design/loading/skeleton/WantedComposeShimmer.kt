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

/**
 * shimmer
 *
 * 깜박이는 shimmer 애니메이션을 적용하는 Modifier 확장 함수입니다.
 *
 * 투명도를 주기적으로 변경하여 깜박이는 효과를 제공합니다.
 * 주로 스켈레톤 UI에 사용됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * Box(
 *     modifier = Modifier
 *         .size(200.dp)
 *         .shimmer()
 * )
 * ```
 *
 * @param colorRes Int: 적용할 색상 리소스 ID입니다.
 * @return Modifier: shimmer 애니메이션이 적용된 Modifier입니다.
 */
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