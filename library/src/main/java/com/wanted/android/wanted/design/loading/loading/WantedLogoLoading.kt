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
 * WantedLogoLoading
 *
 * Wanted 심볼 로딩 애니메이션을 표시하는 컴포넌트입니다.
 *
 * isUseDim이 true인 경우 Dialog 기반으로 dim 처리된 로딩 화면이 표시됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedLogoLoading(isUseDim = true)
 * ```
 *
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
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

/**
 * WantedLogoLoadingDialog
 *
 * Dialog 기반의 Wanted 심볼 로딩 컴포넌트입니다.
 *
 * 외부 터치나 뒤로가기로 닫히지 않도록 설정된 Dialog에 로고 애니메이션이 표시됩니다.
 *
 * @param onDismissRequest () -> Unit: 다이얼로그 닫기 요청 시 호출되는 콜백입니다.
 * @param properties DialogProperties: Dialog 속성 설정입니다.
 */
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
