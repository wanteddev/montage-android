package com.wanted.android.wanted.design.loading.loading

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition


class WantedLogoProgressIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    @Composable
    override fun Content() {
        WantedLogoProgressIndicator()
    }
}

/**
 * WantedLogoProgressIndicator
 *
 * Wanted 심볼 Lottie 애니메이션을 표시하는 컴포넌트입니다.
 *
 * 시스템 다크 모드 설정에 따라 자동으로 라이트/다크 테마의 로딩 애니메이션이 표시됩니다.
 *
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 */
@Composable
fun WantedLogoProgressIndicator(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset(
            assetName = if (isSystemInDarkTheme()) {
                "loading-wanted-dark.json"
            } else {
                "loading-wanted-light.json"
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