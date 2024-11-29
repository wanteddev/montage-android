package com.wanted.android.wanted.design.loading.pulltorefresh

import android.content.Context
import android.content.res.Configuration
import android.provider.Settings
import android.util.Log
import androidx.compose.animation.core.animate
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationState
import com.airbnb.lottie.compose.LottieCancellationBehavior
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.resetToBeginning
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import kotlin.math.abs
import kotlin.math.pow


@Composable
fun WantedPullToRefreshContainer(
    modifier: Modifier = Modifier,
    state: WantedPullToRefreshState,
    offset: ((Dp) -> Unit)? = null
) {
    val density = LocalDensity.current

    LaunchedEffect(state.verticalOffset) {
        offset?.invoke(with(density) { state.verticalOffset.toDp() })
    }

    var isRefresh by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }
    var atEndAnimation by remember { mutableStateOf(false) }

    val pullToRefreshState = (state as? WantedPullToRefreshStateImpl)
    pullToRefreshState?.setDelegate(object : WantedPullToRefreshStateDelegate {
        override fun onEndRefresh() {
            isRefresh = false
            isPlaying = false
            Log.d("_SMY", "onEndRefresh")
        }

        override fun onStartRefresh() {
            isRefresh = true
            isPlaying = true
            Log.d("_SMY", "onStartRefresh")
        }
    })

    LaunchedEffect(isRefresh, atEndAnimation, isPlaying) {

        if (atEndAnimation && !isRefresh) {
            pullToRefreshState?.animatedToEndRefresh()
        } else {
            Log.d(
                "_SMY",
                "replay"
            )
            isPlaying = isRefresh
        }
    }

    WantedPullToRefreshContainer(
        modifier = modifier,
        state = state,
        indicator = {
            if (!state.isRefreshing) {
                ProgressIndicator(
                    modifier = Modifier.fillMaxSize(),
                    progress = state.progress
                )
            } else {
                ProgressIndicatorLoading(
                    modifier = Modifier.fillMaxSize(),
                    isPlaying = isPlaying,
                    onAtEndAnimation = {
                        atEndAnimation = it
                        isPlaying = !it
                    }
                )
            }
        }
    )
}

@Composable
private fun WantedPullToRefreshContainer(
    modifier: Modifier = Modifier,
    state: WantedPullToRefreshState,
    indicator: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier
            .size(32.dp)
            .graphicsLayer {
                translationY = state.verticalOffset - size.height
            }
            .background(color = colorResource(R.color.transparent))
    ) {
        indicator()
    }
}

@Composable
private fun ProgressIndicatorLoading(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    onAtEndAnimation: (Boolean) -> Unit = {}
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset(
            assetName = if (isSystemInDarkTheme()) {
                "pullToRefresh-loading-0.5sEnd.json"
            } else {
                "pullToRefresh-loading-0.5sEnd.json"
            }
        )
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1,
        onAtEndAnimation = onAtEndAnimation,
        cancellationBehavior = LottieCancellationBehavior.OnIterationFinish,
        isPlaying = isPlaying,
        ignoreSystemAnimatorScale = true
    )

    LottieAnimation(
        modifier = modifier
            .padding(4.dp)
            .fillMaxSize(),
        composition = composition,
        progress = { progress },
        safeMode = true
    )
}

@Composable
private fun animateLottieCompositionAsState(
    composition: LottieComposition?,
    isPlaying: Boolean = true,
    restartOnPlay: Boolean = true,
    reverseOnRepeat: Boolean = false,
    clipSpec: LottieClipSpec? = null,
    speed: Float = 1f,
    iterations: Int = 1,
    cancellationBehavior: LottieCancellationBehavior = LottieCancellationBehavior.Immediately,
    ignoreSystemAnimatorScale: Boolean = false,
    useCompositionFrameRate: Boolean = false,
    onAtEndAnimation: (Boolean) -> Unit
): LottieAnimationState {
    require(iterations > 0) { "Iterations must be a positive number ($iterations)." }
    require(speed.isFinite()) { "Speed must be a finite number. It is $speed." }

    val animatable = rememberLottieAnimatable()
    var wasPlaying by remember { mutableStateOf(isPlaying) }

    // Dividing by 0 correctly yields Float.POSITIVE_INFINITY here.
    val actualSpeed = if (ignoreSystemAnimatorScale) speed else (speed / getAnimationScale(
        LocalContext.current
    ))

    LaunchedEffect(animatable.isAtEnd) {
        onAtEndAnimation(animatable.isAtEnd)
    }
    LaunchedEffect(
        composition,
        isPlaying,
        clipSpec,
        actualSpeed,
        iterations,
    ) {
        if (isPlaying && !wasPlaying && restartOnPlay) {
            Log.d(
                "_SMY",
                "isPlaying: $isPlaying  wasPlaying: $wasPlaying restartOnPlay: $restartOnPlay"
            )
            animatable.resetToBeginning()
        }
        wasPlaying = isPlaying
        if (!isPlaying) return@LaunchedEffect

        animatable.animate(
            composition,
            iterations = iterations,
            reverseOnRepeat = reverseOnRepeat,
            speed = actualSpeed,
            clipSpec = clipSpec,
            initialProgress = animatable.progress,
            continueFromPreviousAnimate = false,
            cancellationBehavior = cancellationBehavior,
            useCompositionFrameRate = useCompositionFrameRate,
        )
    }

    return animatable
}

private fun getAnimationScale(context: Context): Float {
    return Settings.Global.getFloat(
        context.contentResolver,
        Settings.Global.ANIMATOR_DURATION_SCALE, 1.0f
    )
}

@Composable
private fun ProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset(
            assetName = if (isSystemInDarkTheme()) {
                "pullToRefresh-pull.json"
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
        progress = { progress },
    )
}

/**
 * Creates a [PullToRefreshState].
 *
 * Note that in most cases, you are advised to use [rememberPullToRefreshState] when in composition.
 *
 * @param positionalThresholdPx The positional threshold, in pixels, in which a refresh is triggered
 * @param initialRefreshing The initial refreshing value of [PullToRefreshState]
 * @param enabled a callback used to determine whether scroll events are to be handled by this
 * [PullToRefreshState]
 */
fun WantedPullToRefreshState(
    positionalThresholdPx: Float,
    initialRefreshing: Boolean = false,
    enabled: () -> Boolean = { true },
): WantedPullToRefreshState = WantedPullToRefreshStateImpl(
    initialRefreshing = initialRefreshing,
    positionalThreshold = positionalThresholdPx,
    enabled = enabled,
)

@Stable
interface WantedPullToRefreshState : PullToRefreshState {

}

@Stable
private interface WantedPullToRefreshAnimatedState : WantedPullToRefreshState {
    fun setDelegate(delegate: WantedPullToRefreshStateDelegate)

    suspend fun animatedToEndRefresh()
}


@Composable
fun rememberWantedPullToRefreshState(
    positionalThreshold: Dp = PullToRefreshDefaults.PositionalThreshold,
    enabled: () -> Boolean = { true },
): WantedPullToRefreshState {
    val density = LocalDensity.current
    val positionalThresholdPx = with(density) { positionalThreshold.toPx() }
    return rememberSaveable(
        positionalThresholdPx, enabled,
        saver = WantedPullToRefreshStateImpl.Saver(
            positionalThreshold = positionalThresholdPx,
            enabled = enabled,
        )
    ) {
        WantedPullToRefreshStateImpl(
            initialRefreshing = false,
            positionalThreshold = positionalThresholdPx,
            enabled = enabled,
        )
    }
}


internal interface WantedPullToRefreshStateDelegate {
    fun onEndRefresh()
    fun onStartRefresh()
}

internal class WantedPullToRefreshStateImpl(
    initialRefreshing: Boolean,
    override val positionalThreshold: Float,
    enabled: () -> Boolean,
) : WantedPullToRefreshAnimatedState {

    private var delegate: WantedPullToRefreshStateDelegate? = null

    override val progress get() = adjustedDistancePulled / positionalThreshold
    override val verticalOffset get() = _verticalOffset

    override val isRefreshing get() = _refreshing

    override fun startRefresh() {
        delegate?.onStartRefresh()
        _refreshing = true
        _verticalOffset = positionalThreshold
    }

    override fun setDelegate(delegate: WantedPullToRefreshStateDelegate) {
        this.delegate = delegate
    }


    override fun endRefresh() {
        delegate?.onEndRefresh() ?: run {
            _verticalOffset = 0f
            _refreshing = false
        }
    }

    override suspend fun animatedToEndRefresh() {
        animateTo(0f)
        _refreshing = false
    }

    override var nestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(
            available: Offset,
            source: NestedScrollSource,
        ): Offset = when {
            !enabled() -> Offset.Zero
            // Swiping up
            source == NestedScrollSource.Drag && available.y < 0 -> {
                consumeAvailableOffset(available)
            }

            else -> Offset.Zero
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset = when {
            !enabled() -> Offset.Zero
            // Swiping down
            source == NestedScrollSource.Drag && available.y > 0 -> {
                consumeAvailableOffset(available)
            }

            else -> Offset.Zero
        }

        override suspend fun onPreFling(available: Velocity): Velocity {
            return Velocity(0f, onRelease(available.y))
        }
    }

    /** Helper method for nested scroll connection */
    fun consumeAvailableOffset(available: Offset): Offset {
        val y = if (isRefreshing) 0f else {
            val newOffset = (distancePulled + available.y).coerceAtLeast(0f)
            val dragConsumed = newOffset - distancePulled
            distancePulled = newOffset
            _verticalOffset = calculateVerticalOffset()
            dragConsumed
        }
        return Offset(0f, y)
    }

    /** Helper method for nested scroll connection. Calls onRefresh callback when triggered */
    suspend fun onRelease(velocity: Float): Float {
        if (isRefreshing) return 0f // Already refreshing, do nothing
        // Trigger refresh
        if (adjustedDistancePulled > positionalThreshold) {
            startRefresh()
        } else {
            animateTo(0f)
        }

        val consumed = when {
            // We are flinging without having dragged the pull refresh (for example a fling inside
            // a list) - don't consume
            distancePulled == 0f -> 0f
            // If the velocity is negative, the fling is upwards, and we don't want to prevent the
            // the list from scrolling
            velocity < 0f -> 0f
            // We are showing the indicator, and the fling is downwards - consume everything
            else -> velocity
        }
        distancePulled = 0f
        return consumed
    }

    private suspend fun animateTo(offset: Float) {
        animate(initialValue = _verticalOffset, targetValue = offset) { value, _ ->
            _verticalOffset = value
        }
    }

    /** Provides custom vertical offset behavior for [PullToRefreshContainer] */
    private fun calculateVerticalOffset(): Float = when {
        // If drag hasn't gone past the threshold, the position is the adjustedDistancePulled.
        adjustedDistancePulled <= positionalThreshold -> adjustedDistancePulled
        else -> {
            // How far beyond the threshold pull has gone, as a percentage of the threshold.
            val overshootPercent = abs(progress) - 1.0f
            // Limit the overshoot to 200%. Linear between 0 and 200.
            val linearTension = overshootPercent.coerceIn(0f, 2f)
            // Non-linear tension. Increases with linearTension, but at a decreasing rate.
            val tensionPercent = linearTension - linearTension.pow(2) / 4
            // The additional offset beyond the threshold.
            val extraOffset = positionalThreshold * tensionPercent
            positionalThreshold + extraOffset
        }
    }

    companion object {
        /** The default [Saver] for [PullToRefreshStateImpl]. */
        fun Saver(
            positionalThreshold: Float,
            enabled: () -> Boolean,
        ) = Saver<WantedPullToRefreshState, Boolean>(
            save = { it.isRefreshing },
            restore = { isRefreshing ->
                WantedPullToRefreshStateImpl(isRefreshing, positionalThreshold, enabled)
            }
        )
    }

    private var distancePulled by mutableFloatStateOf(0f)
    private val adjustedDistancePulled: Float get() = distancePulled * DragMultiplier
    private var _verticalOffset by mutableFloatStateOf(0f)
    private var _refreshing by mutableStateOf(initialRefreshing)
}

private const val DragMultiplier = 0.5f

@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Preview(
    "foldableLight",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "ko",
    device = Devices.FOLDABLE
)
@Composable
private fun WantedPullToRefreshContainerPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
//                WantedPullToRefreshContainer(
//                    modifier = Modifier,
//                    state = rememberWantedPullToRefreshState()
//                )
            }
        }
    }
}