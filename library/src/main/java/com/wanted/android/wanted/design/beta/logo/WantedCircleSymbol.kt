package com.wanted.android.wanted.design.beta.logo

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.wanted.android.designsystem.R


//
// - 파일에서 각각 normal과 monochrome으로 구분
// - monochrome은 단색으로, 각 클라이언트에서 tint를 입히는 식으로 적용
// - 파일 자체로 다크 모드 대응이 필요한 경우에는 white로 구분
//
@Composable
fun WantedCircleSymbol(
    modifier: Modifier = Modifier,
    tintColor: Color? = null
) {
    Image(
        modifier = modifier,
        painter = when (tintColor) {
            null -> painterResource(id = R.drawable.wanted_symbol_normal)
            else -> painterResource(id = R.drawable.wanted_symbol_monochrome)
        },
        contentDescription = "WantedCircleSymbol",
        colorFilter = tintColor?.let { ColorFilter.tint(it) },
    )

}

@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Composable
fun PreviewWantedCircleSymbol() {
    WantedCircleSymbol()
}

@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Composable
fun PreviewWantedCircleTintSymbol() {
    WantedCircleSymbol(tintColor = colorResource(id = R.color.blue_50))
}