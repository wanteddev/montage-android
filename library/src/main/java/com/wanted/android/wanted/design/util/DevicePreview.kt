package com.wanted.android.wanted.design.util

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko", device = Devices.PIXEL_FOLD)
internal annotation class DevicePreviews