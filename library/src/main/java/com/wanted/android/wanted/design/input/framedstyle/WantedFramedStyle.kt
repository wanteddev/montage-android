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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
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
 * @param focused Boolean: 포커스 여부입니다. true일 경우 컴포넌트 외곽에 4dp Focus Ring을 그립니다.
 * @param shadow WantedShadowStyle: 적용할 섀도우 스타일입니다. 기본값은 XSmall입니다.
 * @return Modifier: 스타일이 적용된 Modifier를 반환합니다.
 */
fun Modifier.framedStyle(
    status: WantedFramedStyleStatus = WantedFramedStyleStatus.Normal,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    enabled: Boolean = true,
    focused: Boolean = false,
    shadow: WantedShadowStyle =  WantedShadowStyle.XSmall(),
) = composed {
    this
        .focusRing(visible = focused, shape = shape)
        .wantedDropShadow(shadow)
        .border(
            shape = shape,
            color = when {
                status == WantedFramedStyleStatus.Negative
                        || status == WantedFramedStyleStatus.Selected -> {
                    if (enabled) {
                        DesignSystemTheme.colors.backgroundNeutralPrimary
                            .copy(alpha = OPACITY_43)
                    } else {
                        DesignSystemTheme.colors.backgroundNeutralPrimary
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
                        DesignSystemTheme.colors.foregroundNegativePrimary.copy(OPACITY_43)
                    } else {
                        DesignSystemTheme.colors.foregroundNegativePrimary.copy(0.185f)
                    }
                }

                WantedFramedStyleStatus.Selected -> {
                    if (enabled) {
                        DesignSystemTheme.colors.lineBrandStrong.copy(OPACITY_43)
                    } else {
                        DesignSystemTheme.colors.lineBrandStrong.copy(0.185f)
                    }
                }

                else -> DesignSystemTheme.colors.lineNeutralSecondary
            },
            width = if (status == WantedFramedStyleStatus.Selected) 2.dp else 1.dp
        )
        .clip(shape)
}

/**
 * fun Modifier.focusRing(...)
 *
 * 컴포넌트 외곽에 Focus Ring(외부 강조 테두리)을 그리는 Modifier 확장 함수입니다.
 *
 * `framedStyle`의 inset border와 관심사를 분리하여, 포커스 시 컴포넌트 경계 바깥쪽에
 * stroke를 그립니다. `drawBehind`로 outset 영역에 그리므로 레이아웃에 영향을 주지 않고
 * (focus 토글 시 layout shift 없음), 이후 `.clip()`에 의해 잘리지 않습니다.
 *
 * stroke는 외곽 경계를 기준으로 두께의 절반만큼 바깥으로 밀어 `[edge, edge + width]` 구간에
 * 위치합니다. (CSS `box-shadow: 0 0 0 width` 와 동일) corner radius는 동심을 유지하기 위해
 * 컴포넌트 radius + 두께 절반으로 계산합니다.
 *
 * @param visible Boolean: ring 노출 여부입니다. false일 경우 아무것도 그리지 않습니다.
 * @param shape RoundedCornerShape: 컴포넌트의 모서리 형태입니다. ring radius 계산에 사용됩니다.
 * @param color Color: ring 색상입니다. 기본값은 lineBrandFocus 토큰입니다.
 * @param width Dp: ring 두께입니다. 기본값은 4dp입니다.
 * @return Modifier: Focus Ring이 적용된 Modifier를 반환합니다.
 */
fun Modifier.focusRing(
    visible: Boolean,
    shape: RoundedCornerShape,
    color: Color = Color.Unspecified,
    width: Dp = 4.dp,
): Modifier = composed {
    val ringColor = if (color == Color.Unspecified) {
        DesignSystemTheme.colors.lineBrandFocus
    } else {
        color
    }

    if (!visible) {
        this
    } else {
        this.drawBehind {
            val strokeWidth = width.toPx()
            val halfStroke = strokeWidth / 2f
            val cornerRadius = shape.topStart.toPx(size, this) + halfStroke

            drawRoundRect(
                color = ringColor,
                topLeft = Offset(-halfStroke, -halfStroke),
                size = Size(size.width + strokeWidth, size.height + strokeWidth),
                cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                style = Stroke(width = strokeWidth)
            )
        }
    }
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