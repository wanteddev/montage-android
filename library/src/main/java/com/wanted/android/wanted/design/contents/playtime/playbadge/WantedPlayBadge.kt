package com.wanted.android.wanted.design.contents.playtime.playbadge

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
import com.wanted.android.wanted.design.contents.playtime.playbadge.WantedPlayBadgeDefaults.Size
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_28
import com.wanted.android.wanted.design.util.OPACITY_61

/**
 * WantedPlayBadge
 *
 * 재생 아이콘을 시각적으로 구분할 수 있도록 표시하는 컴포넌트입니다.
 *
 * 배경과 아이콘으로 구성되며, 크기 및 대체 색상 사용 여부를 설정할 수 있습니다.
 * Size에 따라 전체 크기와 아이콘 크기가 달라집니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedPlayBadge(
 *     size = Size.Medium,
 *     isAlternative = true,
 *     modifier = Modifier
 * )
 * ```
 *
 * @param size Size: 배지의 크기 옵션입니다. Small, Medium, Large 세 가지 크기를 제공합니다.
 * @param isAlternative Boolean: true일 경우 대체 색상(더 어두운 회색 계열 배경)을 사용합니다.
 * @param modifier Modifier: 배지의 외형, 정렬, 패딩 등을 제어하는 Modifier입니다.
 */
@Composable
fun WantedPlayBadge(
    size: Size = Size.Medium,
    isAlternative: Boolean = false,
    modifier: Modifier,
) {
    Box(
        modifier = modifier
            .size(size.container)
            .clip(CircleShape)
            .background(DesignSystemTheme.colors.transparent),
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
            tint = DesignSystemTheme.colors.staticWhite
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
                WantedPlayBadge(
                    modifier = Modifier,
                    size = Size.Small
                )

                WantedPlayBadge(
                    modifier = Modifier,
                    size = Size.Medium
                )

                WantedPlayBadge(
                    modifier = Modifier,
                    size = Size.Large
                )

                WantedPlayBadge(
                    modifier = Modifier,
                    isAlternative = true
                )
            }
        }
    }
}