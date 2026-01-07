package com.wanted.android.wanted.design.beta.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wanted.android.wanted.design.loading.loading.WantedLogoProgressIndicator
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.FontFixTextStyle


@Composable
fun LogoLoading(
    modifier: Modifier = Modifier,
    message: String? = null,
    size: Dp = 32.dp,
    dimColor: Color = Color.Transparent
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = dimColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            message?.let {
                Text(
                    text = it,
                    modifier = Modifier,
                    style = FontFixTextStyle(
                        color = DesignSystemTheme.colors.labelNormal,
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
                WantedLogoProgressIndicator(modifier = Modifier.size(size))
            }
        }
    }
}


@DevicePreviews
@Composable
private fun LogoLoadingPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            LogoLoading()
        }
    }
}

@DevicePreviews
@Composable
private fun LogoLoadingMessagePreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            LogoLoading(message = "loading...")
        }
    }
}

@DevicePreviews
@Composable
private fun LogoLoadingMessageSizePreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            LogoLoading(message = "loading...", size = 100.dp)
        }
    }
}


