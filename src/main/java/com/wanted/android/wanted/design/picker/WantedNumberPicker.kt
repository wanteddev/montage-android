package com.wanted.android.wanted.design.picker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.theme.DesignSystemTheme


@Composable
fun WantedNumberPicker(
    modifier: Modifier
) {

}

@Composable
fun TiltedText(
    text: String,
    modifier: Modifier = Modifier,
    angle: Float = 45f, // 기울이는 각도
    textStyle: TextStyle = TextStyle(fontSize = 24.sp, color = Color.Black)
) {
    Text(
        text = text,
        style = textStyle,
        modifier = modifier
            .graphicsLayer(
                rotationX = angle // X축 기준으로 기울이기
            )
    )
}


@Composable
fun RolledBackwardsExample() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        TiltedText(
            text = "Compose",
            textStyle = TextStyle(
                fontSize = 24.sp,
                color = Color.Black
            ),
        )
    }
}

@DevicePreviews
@Composable
private fun WantedNumberPickerPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TiltedText(
                    text = "Compose",
                    angle = 89f,
                    textStyle = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Black
                    ),
                )

                Spacer(Modifier.size(0.dp))

                TiltedText(
                    text = "Compose",
                    angle = 60f,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                )

                Spacer(Modifier.size(8.dp))

                TiltedText(
                    text = "Compose",
                    angle = 30f,
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black
                    ),
                )

                Spacer(Modifier.size(16.dp))

                TiltedText(
                    text = "Compose",
                    angle = 0f,
                    textStyle = TextStyle(
                        fontSize = 24.sp,
                        color = Color.Black
                    ),
                )

                TiltedText(
                    text = "Compose",
                    angle = -30f,
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black
                    ),
                )

                TiltedText(
                    text = "Compose",
                    angle = -60f,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                )

                TiltedText(
                    text = "Compose",
                    angle = -90f,
                    textStyle = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Black
                    ),
                )

            }
        }
    }
}