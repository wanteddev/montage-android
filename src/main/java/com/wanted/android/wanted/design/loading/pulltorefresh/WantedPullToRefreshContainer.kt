package com.wanted.android.wanted.design.loading.pulltorefresh

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.launch


/**
 * 피그마 : https://www.figma.com/design/MK6KmtXBxX7ZkoQXfD9MFH/%EA%B0%9C%EC%84%A0%3A-Components?node-id=4279-37961&t=rgBL8FqtWf5Wcg7D-0
 */
@Composable
fun WantedPullToRefreshBox(
    modifier: Modifier = Modifier,
    state: PullToRefreshState = rememberPullToRefreshState(),
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val scale = remember { Animatable(1f) }
    var isRefresh by remember(isRefreshing) { mutableStateOf(isRefreshing) }
    var isPullEnd by remember { mutableStateOf(true) }

    // Alpha 애니메이션
    val alpha by rememberInfiniteTransition(label = "").animateFloat(
        initialValue = 1f,
        targetValue = if (isRefresh) 0.61f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                easing = CubicBezierEasing(0.42f, 0.0f, 0.58f, 1.0f)
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    // Refresh 상태 관리
    LaunchedEffect(state.distanceFraction) {
        if (state.distanceFraction <= 0f) {
            isPullEnd = false
        }

        if (!isRefresh && state.distanceFraction > 1f) {
            isRefresh = true
            scope.launch {
                scale.animateTo(
                    1.025f,
                    animationSpec = tween(250, easing = easingCurve)
                )

                scale.animateTo(
                    1f,
                    animationSpec = tween(250, easing = easingCurve)
                )

                onRefresh()
                isPullEnd = true
            }
        }
    }

    // PullToRefreshBox 구현
    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = isRefresh,
        state = state,
        onRefresh = onRefresh,
        indicator = {
            RefreshIndicator(
                state = state,
                scale = scale.value,
                alpha = alpha,
                isPullEnd = isPullEnd
            )
        }
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .graphicsLayer {
                    translationY = when {
                        isRefresh -> state.distanceFraction * PositionalThreshold.toPx()
                        isPullEnd -> state.distanceFraction * (PositionalThreshold * 0.5f).toPx()
                        else -> state.distanceFraction * (PositionalThreshold * 1.5f).toPx()
                    }
                    clip = true
                }
        ) {
            content()
        }
    }
}

@Composable
private fun RefreshIndicator(
    state: PullToRefreshState,
    scale: Float,
    alpha: Float,
    isPullEnd: Boolean
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        ProgressIndicator(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(SIZE)
                .scale(scale)
                .graphicsLayer {
                    translationY = state.distanceFraction * PositionalThreshold.toPx() - size.height
                    clip = true
                }
                .alpha(alpha),
            progress = if (!isPullEnd) state.distanceFraction else 1f
        )
    }
}

@Composable
private fun ProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset(
            assetName = if (isSystemInDarkTheme()) {
                "pullToRefresh-pull-dark.json"
            } else {
                "pullToRefresh-pull.json"
            }
        )
    )

    LottieAnimation(
        modifier = modifier
            .padding(4.dp)
            .fillMaxSize(),
        composition = composition,
        progress = { progress }
    )
}

// Constants
private val SIZE = 50.dp
private val PositionalThreshold = 80.dp

// Common easing curve
private val easingCurve = CubicBezierEasing(0.42f, 0.0f, 0.58f, 1.0f)
