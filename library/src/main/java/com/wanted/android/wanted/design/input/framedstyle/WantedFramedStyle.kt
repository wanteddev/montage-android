package com.wanted.android.wanted.design.input.framedstyle

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.base.WantedDropShadowDefaults.WantedShadowStyle
import com.wanted.android.wanted.design.base.wantedDropShadow
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_43

/**
 * fun Modifier.framedStyle(...)
 *
 * 프레임 스타일을 적용하는 Modifier 확장 함수입니다.
 *
 * 테두리, 그림자, 모서리 둥글기 등을 적용하여 카드나 입력 필드 등에 프레임 스타일을 부여합니다.
 * 상태(Normal, Negative, Selected)에 따라 다른 색상과 테두리 굵기가 적용됩니다.
 *
 * 사용 예시:
 * ```
 * Box(
 *     modifier = Modifier
 *         .size(100.dp)
 *         .framedStyle(
 *             status = WantedFramedStyleStatus.Selected,
 *             enabled = true
 *         )
 * )
 * ```
 *
 * @param status WantedFramedStyleStatus: 프레임 상태입니다. Normal, Negative, Selected 중 하나를 선택합니다.
 * @param shape RoundedCornerShape: 모서리 둥글기 형태입니다. 기본값은 12dp입니다.
 * @param enabled Boolean: 활성화 여부입니다. false일 경우 불투명도가 낮아집니다.
 * @param shadow WantedShadowStyle: 적용할 섀도우 스타일입니다. 기본값은 XSmall입니다.
 * @return Modifier: 스타일이 적용된 Modifier를 반환합니다.
 */
fun Modifier.framedStyle(
    status: WantedFramedStyleStatus = WantedFramedStyleStatus.Normal,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    enabled: Boolean = true,
    shadow: WantedShadowStyle =  WantedShadowStyle.XSmall(),
) = composed {
    this
        .wantedDropShadow(shadow)
        .border(
            shape = shape,
            color = when {
                status == WantedFramedStyleStatus.Negative
                        || status == WantedFramedStyleStatus.Selected -> {
                    if (enabled) {
                        DesignSystemTheme.colors.backgroundNormalNormal
                            .copy(alpha = OPACITY_43)
                    } else {
                        DesignSystemTheme.colors.backgroundNormalNormal
                            .copy(alpha = 0.185f)
                    }
                }

                else -> DesignSystemTheme.colors.transparent
            },
            width = if (status == WantedFramedStyleStatus.Selected) 2.dp else 1.dp
        )
        .border(
            shape = shape,
            color = when (status) {
                WantedFramedStyleStatus.Negative -> {
                    if (enabled) {
                        DesignSystemTheme.colors.statusNegative.copy(OPACITY_43)
                    } else {
                        DesignSystemTheme.colors.statusNegative.copy(0.185f)
                    }
                }

                WantedFramedStyleStatus.Selected -> {
                    if (enabled) {
                        DesignSystemTheme.colors.primaryNormal.copy(OPACITY_43)
                    } else {
                        DesignSystemTheme.colors.primaryNormal.copy(0.185f)
                    }
                }

                else -> DesignSystemTheme.colors.lineNormalNeutral
            },
            width = if (status == WantedFramedStyleStatus.Selected) 2.dp else 1.dp
        )
        .clip(shape)
}

/**
 * enum class WantedFramedStyleStatus
 *
 * 프레임 스타일의 상태를 정의하는 enum 클래스입니다.
 *
 * 각 상태에 따라 테두리 색상과 두께가 달라집니다:
 * - Normal: 일반 상태입니다. 기본 테두리 색상이 적용됩니다.
 * - Negative: 오류 또는 부정적인 상태입니다. 빨간색 계열 테두리가 적용됩니다.
 * - Selected: 선택된 상태입니다. 파란색 계열의 2dp 테두리가 적용됩니다.
 */
enum class WantedFramedStyleStatus {
    Normal,
    Negative,
    Selected
}

@DevicePreviews
@Composable
private fun WantedFramedStylePreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .framedStyle(
                            status = WantedFramedStyleStatus.Negative,
                            enabled = true
                        )
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .framedStyle(
                            status = WantedFramedStyleStatus.Selected,
                            enabled = true
                        )
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .framedStyle(
                            status = WantedFramedStyleStatus.Normal,
                            enabled = true
                        )
                )


                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .framedStyle(
                            status = WantedFramedStyleStatus.Negative,
                            enabled = false
                        )
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .framedStyle(
                            status = WantedFramedStyleStatus.Selected,
                            enabled = false
                        )
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .framedStyle(
                            status = WantedFramedStyleStatus.Normal,
                            enabled = false
                        )
                )
            }
        }
    }
}