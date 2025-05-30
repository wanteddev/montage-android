package com.wanted.android.wanted.design.loading.pulltorefresh

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.launch

/**
 * 커스텀 애니메이션과 로티 인디케이터가 포함된 Pull-to-Refresh 컴포저블입니다.
 *
 * 시스템 다크 모드 여부에 따라 Lottie 애셋이 자동 전환되며, 유저가 스크롤을 아래로 당길 때
 * 부드러운 그래픽과 함께 새로고침 로직을 트리거할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * val isRefreshing by remember { mutableStateOf(false) }
 * WantedPullToRefreshBox(
 *     isRefreshing = isRefreshing,
 *     onRefresh = { /* 새로고침 처리 */ }
 * ) {
 *     LazyColumn { ... }
 * }
 * ```
 *
 * @param isRefreshing Boolean: 현재 새로고침 중인지 여부를 나타냅니다.
 * @param onRefresh () -> Unit: 유저가 당겨서 새로고침을 요청했을 때 호출되는 콜백입니다.
 * @param modifier Modifier: 외형 및 배치 설정용 modifier입니다.
 * @param state PullToRefreshState: Pull-to-Refresh 상태를 관리하는 객체입니다. 기본값은 `rememberPullToRefreshState()`입니다.
 * @param content @Composable () -> Unit: 새로고침 박스 내부에 배치할 UI 콘텐츠입니다.
 */
@Composable
fun WantedPullToRefreshBox(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    state: PullToRefreshState = rememberPullToRefreshState(),
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val scale = remember { Animatable(1f) }
    var isRefresh by remember(isRefreshing) { mutableStateOf(isRefreshing) }
    var isPullEnd by remember { mutableStateOf(true) }
    val density = LocalDensity.current
    val animateTransitionY by animateFloatAsState(
        when {
            isRefresh -> state.distanceFraction * with(density) { ((SIZE_HEIGHT + INDICATOR_PADDING) * 2).toPx() }
            isPullEnd -> 0f
            else -> state.distanceFraction * with(density) { ((SIZE_HEIGHT + INDICATOR_PADDING) * 3).toPx() }
        }, label = "animateTransitionY"
    )

    // Alpha 애니메이션
    val alpha by rememberInfiniteTransition(label = "").animateFloat(
        initialValue = 1f,
        targetValue = if (isPullEnd) 0.61f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
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

        if (!isRefresh && state.distanceFraction > 1.1f) {
            isRefresh = true
            scope.launch {
                scale.animateTo(
                    1.05f,
                    animationSpec = tween(250, easing = easingCurve)
                )

                scale.animateTo(
                    1f,
                    animationSpec = tween(250, easing = easingCurve)
                )
                isPullEnd = true
                onRefresh()
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
                isPullEnd = isPullEnd,
                isRefresh = isRefresh,
            )
        }
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .graphicsLayer {
                    translationY = when {
                        isRefresh -> animateTransitionY
                        // 들어갈때 Container 위치
                        isPullEnd -> state.distanceFraction  * (SIZE_HEIGHT * 0.5f).toPx()
                        // 땡겼을때 Container 위치
                        else -> state.distanceFraction * with(density) { ((SIZE_HEIGHT + INDICATOR_PADDING) * 3).toPx() }
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
    isPullEnd: Boolean,
    isRefresh: Boolean
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        ProgressIndicator(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(SIZE_WIDTH)
                .scale(scale)
                .graphicsLayer {
                    translationY = when {
                        // 로딩중일때 indicator 위치
                        isRefresh -> (SIZE_HEIGHT * 0.5f).toPx()
                        // 들어갈때 indicator 위치
                        isPullEnd -> state.distanceFraction * SIZE_HEIGHT.toPx() - SIZE_HEIGHT.toPx()
                        // 땡길때 indicator 위치
                        else -> state.distanceFraction * (SIZE_HEIGHT * 2).toPx() - SIZE_HEIGHT.toPx()
                    }
                    clip = true
                }
                .alpha(alpha),
            progress = if (!isPullEnd) state.distanceFraction else 1f
        )
    }
}

@Composable
private fun ProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
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
private val SIZE_WIDTH = 50.dp
private val SIZE_HEIGHT = 40.dp
private val INDICATOR_PADDING = 5.dp

// Common easing curve
private val easingCurve = CubicBezierEasing(0.42f, 0.0f, 0.58f, 1.0f)
