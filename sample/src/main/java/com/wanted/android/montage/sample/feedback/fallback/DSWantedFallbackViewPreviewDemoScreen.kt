package com.wanted.android.montage.sample.feedback.fallback

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.wanted.design.feedback.fallback.WantedFallbackButtonVariant
import com.wanted.android.wanted.design.feedback.fallback.WantedFallbackPadding
import com.wanted.android.wanted.design.feedback.fallback.WantedFallbackView
import com.wanted.android.wanted.design.navigations.topbar.WantedBackTopAppBar
import com.wanted.android.wanted.design.theme.DesignSystemTheme

/**
 * [DSWantedFallbackViewDemoScreen] 에서 선택한 옵션 그대로 실제 화면처럼 보여주는 미리보기 화면입니다.
 *
 * 앱바와 [WantedFallbackView] 만 배치해 옵션(특히 padding)이 실제 화면에서 어떻게 보이는지 확인합니다.
 */
@Composable
fun DSWantedFallbackViewPreviewDemoScreen(
    heading: Boolean,
    description: Boolean,
    buttonVariant: WantedFallbackButtonVariant,
    padding: WantedFallbackPadding,
    main: Boolean,
    alternative: Boolean,
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedFallbackView") {
                onClickBack()
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            WantedFallbackView(
                padding = padding,
                heading = if (heading) {
                    "헤더입니다."
                } else {
                    null
                },
                description = if (description) {
                    "마침표를 찍어주세요."
                } else {
                    null
                },
                buttonVariant = buttonVariant,
                main = if (main) {
                    "행동"
                } else {
                    null
                },
                alternative = if (alternative) {
                    "보조행동"
                } else {
                    null
                }
            )
        }
    }
}

@DevicePreviews
@Composable
private fun DSWantedFallbackViewPreviewDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedFallbackViewPreviewDemoScreen(
            heading = true,
            description = true,
            buttonVariant = WantedFallbackButtonVariant.Single,
            padding = WantedFallbackPadding.Normal,
            main = true,
            alternative = true,
            onClickBack = { }
        )
    }
}
