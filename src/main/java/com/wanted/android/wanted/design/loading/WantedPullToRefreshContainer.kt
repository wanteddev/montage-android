package com.wanted.android.wanted.design.loading

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme


@Composable
fun WantedPullToRefreshContainer(
    modifier: Modifier = Modifier,
    pullRefreshState: PullToRefreshState
) {
    val state = rememberPullToRefreshState()
    LaunchedEffect(Unit) {
        pullRefreshState.nestedScrollConnection = state.nestedScrollConnection
    }

    LaunchedEffect(state.isRefreshing) {
        if (state.isRefreshing) {
            pullRefreshState.startRefresh()
        }
    }
    LaunchedEffect(pullRefreshState.isRefreshing) {
        if (!pullRefreshState.isRefreshing) {
            state.endRefresh()
        }
    }

    PullToRefreshContainer(
        modifier = modifier,
        state = state,
        contentColor = colorResource(id = R.color.label_normal),
        containerColor = colorResource(id = R.color.background_normal_normal),
        indicator = {
            if (!it.isRefreshing) {
                ProgressIndicator(
                    modifier = Modifier.fillMaxSize(),
                    progress = it.progress
                )
            } else {
                ProgressIndicatorLoading(modifier = Modifier.fillMaxSize())
            }
        }
    )
}

@Composable
private fun ProgressIndicatorLoading(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset(
            assetName = if (isSystemInDarkTheme()) {
                "pullToRefresh-loading.json"
            } else {
                "pullToRefresh-loading.json"
            }
        )
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        modifier = modifier.defaultMinSize(32.dp),
        composition = composition,
        progress = { progress },
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
        modifier = modifier.defaultMinSize(32.dp),
        composition = composition,
        progress = { progress },
    )
}

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
                WantedPullToRefreshContainer(
                    modifier = Modifier,
                    pullRefreshState = rememberPullToRefreshState()
                )
            }
        }
    }
}