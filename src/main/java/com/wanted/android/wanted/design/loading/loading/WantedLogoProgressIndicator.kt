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
 * 다이얼로그를 통한 Wanted 로고 로딩 표시 컴포저블입니다.
 *
 * 외부 dismiss를 막는 속성을 포함한 Dialog 위에 로고 애니메이션이 표시됩니다.
 *
 * @param onDismissRequest () -> Unit: 다이얼로그 닫기 요청 콜백입니다.
 * @param properties DialogProperties: Dialog 구성 속성입니다.
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