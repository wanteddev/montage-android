package com.wanted.android.wanted.design

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko", device = "spec:width=673dp,height=841dp")
internal annotation class DevicePreviews