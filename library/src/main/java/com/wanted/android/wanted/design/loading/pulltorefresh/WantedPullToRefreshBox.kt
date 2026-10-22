package com.wanted.android.wanted.design.loading.pulltorefresh

import android.util.Log
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
 * WantedPullToRefreshBox
 *
 * Pull-to-Refresh 기능을 제공하는 컴포넌트입니다.
 *
 * 시스템 다크 모드에 따라 Lottie 애니메이션이 자동으로 전환되며,
 * 사용자가 화면을 아래로 당겨 새로고침을 실행할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * // ViewModel 이 새로고침을 시작하면 isRefreshing 을 true 로, 끝나면 false 로 바꾼다.
 * WantedPullToRefreshBox(
 *     isRefreshing = uiState.isRefreshing,
 *     onRefresh = { viewModel.refresh() }
 * ) {
 *     LazyColumn(modifier = Modifier.fillMaxSize()) {
 *         items(list) { item ->
 *             Text(text = item)
 *         }
 *     }
 * }
 * ```
 *
 * @param isRefreshing Boolean: 새로고침 중인지 여부입니다. 당김이 끝나면 컴포넌트가 내부적으로 새로고침 상태가 되고, 이 값이 바뀔 때만 해제되므로 onRefresh 에서 true 로 올렸다가 작업 완료 후 false 로 내려야 합니다. 같은 프레임에서 true·false 를 연달아 쓰면 인디케이터가 계속 남습니다.
 * @param onRefresh () -> Unit: 당긴 거리가 PullToRefreshState 임계값의 110% 를 넘은 상태에서 손을 떼면 호출되는 콜백입니다. 확대 애니메이션(약 0.5초)이 끝난 뒤에 호출됩니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param state PullToRefreshState: Pull-to-Refresh 상태를 관리하는 객체입니다.
 * @param content @Composable BoxScope.() -> Unit: 새로고침 박스 내부에 배치할 콘텐츠입니다. 상단 가운데(TopCenter)에 놓인 내용 크기의 Box 안에 그려지므로 BoxScope 정렬도 그 Box 기준입니다. 화면을 채우거나 가운데 정렬하려면 콘텐츠에 fillMaxSize 를 줍니다.
 */
@Composable
fun WantedPullToRefreshBox(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    state: PullToRefreshState = rememberPullToRefreshState(),
    content: @Composable BoxScope.() -> Unit,
) {
    val scope = rememberCoroutineScope()
    val scale = remember { Animatable(1f) }
    var isRefresh by remember(isRefreshing) { mutableStateOf(isRefreshing) }
    var isPullEnd by remember { mutableStateOf(true) }
    val density = LocalDensity.current

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

    LaunchedEffect(state.distanceFraction, state.isAnimating) {
        if (state.distanceFraction <= 0f) {
            isPullEnd = false
        }

        if (state.isAnimating && !isRefresh && state.distanceFraction > 1.1f) {
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

    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = isRefresh,
        state = state,
        onRefresh = {},
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
                        isRefresh -> {
                            val result = state.distanceFraction * with(density) { ((SIZE_HEIGHT + INDICATOR_PADDING) * 2).toPx() }
                            Log.d("WantedPullToRefreshBox", "animateTransitionY isRefresh ${state.distanceFraction}  $result")
                            state.distanceFraction * with(density) { ((SIZE_HEIGHT + INDICATOR_PADDING) * 2).toPx() }
                        }
                        // 들어갈때 Container 위치
                        isPullEnd -> {
                            val result = state.distanceFraction * (SIZE_HEIGHT * 0.5f).toPx()
                            Log.d("WantedPullToRefreshBox", "animateTransitionY isPullEnd ${state.distanceFraction}  $result")
                            state.distanceFraction * (SIZE_HEIGHT * 0.5f).toPx()
                        }
                        // 땡겼을때 Container 위치
                        else -> {
                            val result = state.distanceFraction * with(density) { ((SIZE_HEIGHT + INDICATOR_PADDING) * 3).toPx() }
                            Log.d("WantedPullToRefreshBox", "animateTransitionY else  ${state.distanceFraction}  $result")
                            state.distanceFraction * with(density) { ((SIZE_HEIGHT + INDICATOR_PADDING) * 3).toPx() }
                        }
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

private val SIZE_WIDTH = 50.dp
private val SIZE_HEIGHT = 40.dp
private val INDICATOR_PADDING = 5.dp

private val easingCurve = CubicBezierEasing(0.42f, 0.0f, 0.58f, 1.0f)
