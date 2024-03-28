package com.wanted.android.wanted.design.beta.loading

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.FontFixTextStyle


@Composable
fun LogoLoading(
    modifier: Modifier = Modifier,
    message: String? = null,
    dimColor: Color = Color.Transparent
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = dimColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            message?.let {
                Text(
                    text = it,
                    modifier = Modifier,
                    style = FontFixTextStyle(
                        color = colorResource(id = R.color.label_normal),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.size(20.dp))
            }

            Box(
                modifier = Modifier.wrapContentSize(),
                contentAlignment = Alignment.Center
            ) {
                ProgressIndicatorWanted()
            }
        }
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
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            LogoLoading()
        }
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
private fun LogoLoadingMessagePreview() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            LogoLoading(message = "loading...")
        }
    }
}

