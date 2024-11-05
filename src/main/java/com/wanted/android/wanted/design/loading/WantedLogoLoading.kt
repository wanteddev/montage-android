package com.wanted.android.wanted.design.loading

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.wanted.android.wanted.design.theme.DesignSystemTheme

/**
 * 피그마 : https://www.figma.com/design/MK6KmtXBxX7ZkoQXfD9MFH/%EA%B0%9C%EC%84%A0%3A-Components?node-id=3297-9864&t=GbyYpYYnAOI0hwwu-4
 */
@Composable
fun WantedLogoLoading(
    modifier: Modifier = Modifier,
    isUseDim: Boolean = false
) {
    if (isUseDim) {
        WantedLogoLoading()
    } else {
        WantedLogoLoadingDialog()
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


@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Preview(
    "foldableLight",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "ko",
    device = Devices.FOLDABLE
)
@Composable
private fun LogoLoadingPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            WantedLogoLoading()
        }
    }
}
