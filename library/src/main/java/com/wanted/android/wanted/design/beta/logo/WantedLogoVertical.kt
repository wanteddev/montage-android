package com.wanted.android.wanted.design.beta.logo

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.wanted.android.designsystem.R


//
// 파일에서 각각 normal과 monochrome으로 구분
// monochrome은 단색으로, 각 클라이언트에서 tint를 입히는 식으로 적용
// 파일 자체로 다크 모드 대응이 필요한 경우에는 white로 구분
//
@Composable
fun WantedLogoVertical(
    modifier: Modifier = Modifier,
    tintColor: Color? = null
) {
    Image(
        modifier = modifier,
        painter = when (tintColor) {
            null -> painterResource(id = R.drawable.wanted_logo_vertical_normal)
            else -> painterResource(id = R.drawable.wanted_logo_vertical_monochrome)
        },
        colorFilter = tintColor?.let { ColorFilter.tint(it) },
        contentDescription = "WantedLogoHorizontal"
    )

}