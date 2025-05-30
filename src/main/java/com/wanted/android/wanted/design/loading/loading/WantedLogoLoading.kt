package com.wanted.android.wanted.design.loading.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.theme.DesignSystemTheme

/**
 * Wanted 로고 기반의 로딩 애니메이션을 표시하는 컴포저블입니다.
 *
 * `isUseDim`이 true일 경우, Dialog 기반 dim 처리된 로딩 레이어가 출력됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedLogoLoading(isUseDim = true)
 * ```
 *
 * @param modifier Modifier: 레이아웃 설정입니다.
 * @param isUseDim Boolean: dim 배경 사용 여부입니다.
 */
@Composable
fun WantedLogoLoading(
    modifier: Modifier = Modifier,
    isUseDim: Boolean = false
) {
    if (isUseDim) {
        WantedLogoLoadingDialog()
    } else {
        WantedLogoLoading(modifier)
    }
}

@Composable
private fun WantedLogoLoading(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            WantedLogoProgressIndicator()
        }
    }
}

@Composable
fun WantedLogoLoadingDialog(
    onDismissRequest: () -> Unit = {},
    properties: DialogProperties = DialogProperties(
        dismissOnBackPress = false,
        dismissOnClickOutside = false,
        usePlatformDefaultWidth = false,
        decorFitsSystemWindows = false
    )
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        WantedLogoProgressIndicator()
    }
}


@DevicePreviews
@Composable
private fun LogoLoadingPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            WantedLogoLoading(isUseDim = true)
        }
    }
}
