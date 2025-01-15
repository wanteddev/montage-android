package com.wanted.android.wanted.design.playtime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.playtime.WantedPlayTimeContract.Size
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_28
import com.wanted.android.wanted.design.util.OPACITY_61


@Composable
fun WantedPlayTime(
    modifier: Modifier,
    size: Size = Size.Medium,
    isAlternative: Boolean = false
) {
    Box(
        modifier = modifier
            .size(size.container)
            .clip(CircleShape)
            .background(colorResource(R.color.transparent)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .size(size.container)
                .clip(CircleShape)
                .alpha(if (isAlternative) OPACITY_61 else OPACITY_28)
                .background(
                    if (isAlternative) {
                        colorResource(R.color.cool_neutral_30)
                    } else {
                        colorResource(R.color.cool_neutral_40)
                    }
                )

        )

        Icon(
            painter = painterResource(R.drawable.icon_normal_play),
            modifier = Modifier.size(size.icon),
            contentDescription = "",
            tint = colorResource(R.color.static_white)
        )
    }

}

@DevicePreviews
@Composable
private fun WantedPlayTimePreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedPlayTime(
                    modifier = Modifier,
                    size = Size.Small
                )

                WantedPlayTime(
                    modifier = Modifier,
                    size = Size.Medium
                )

                WantedPlayTime(
                    modifier = Modifier,
                    size = Size.Large
                )

                WantedPlayTime(
                    modifier = Modifier,
                    isAlternative = true
                )
            }
        }
    }
}