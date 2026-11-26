package com.wanted.android.wanted.design.input.framedstyle

import androidx.compose.foundation.background
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

/**
 * fun Modifier.framedStyle(...)
 *
 * 프레임 스타일을 적용하는 Modifier 확장 함수입니다.
 *
 * 여러 요소를 프레임으로 감싸 그룹화하고, 그룹 단위의 선택 상태를 강조할 때 사용합니다.
 * 상태에 맞는 1dp 테두리를 그리고, 선택 상태는 1dp 상태 색 border 아래에 1dp surface/neutral/primary Base border 를 깔고
 * 컴포넌트 바깥 4dp Ring 으로 강조합니다. 선택(selected)과 상태(status)는 서로 독립된 축입니다.
 * 배경은 그리지 않고, shadow 를 지정했을 때만 background/neutral/primary 를 깝니다.
 *
 * 상태별 외형:
 * - Normal: 테두리 line/neutral/secondary
 * - Normal + selected: 테두리 line/brand/strong + Base surface/neutral/primary + 바깥 Ring line/brand/focus
 * - Negative: 테두리 line/negative/primary
 * - Negative + selected: 테두리 line/negative/strong + Base surface/neutral/primary + 바깥 Ring line/negative/focus
 * - disabled: 상태와 관계없이 테두리·Base·Ring 의 컬러 토큰은 그대로 두고 현재 알파값에 43% 를 곱합니다(예: line/brand/strong 43% → 약 18.5%)
 *
 * 사용 예시:
 * ```
 * Box(
 *     modifier = Modifier
 *         .size(100.dp)
 *         .framedStyle(
 *             selected = true,
 *             status = WantedFramedStyleStatus.Normal,
 *             enabled = true
 *         )
 * )
 * ```
 *
 * @param selected Boolean: 선택 여부입니다. true일 경우 상태 색 테두리 아래 Base 를 깔고 바깥 4dp Ring 을 그립니다.
 * @param status WantedFramedStyleStatus: 프레임 상태입니다. Normal, Negative 중 하나를 선택합니다.
 * @param shape RoundedCornerShape: 모서리 둥글기 형태입니다. 기본값은 14dp입니다.
 * @param enabled Boolean: 활성화 여부입니다. false일 경우 상태와 관계없이 테두리·Base·Ring 의 컬러 토큰은 그대로 두고 현재 알파값에 43% 를 곱합니다.
 * @param shadow WantedShadowStyle?: 적용할 섀도우 스타일입니다. 기본값은 null 로 그림자와 배경을 그리지 않습니다. 지정하면 배경 background/neutral/primary 를 함께 깝니다.
 * @return Modifier: 스타일이 적용된 Modifier를 반환합니다.
 */
fun Modifier.framedStyle(
    selected: Boolean = false,
    status: WantedFramedStyleStatus = WantedFramedStyleStatus.Normal,
    shape: RoundedCornerShape = RoundedCornerShape(14.dp),
    enabled: Boolean = true,
    shadow: WantedShadowStyle? = null,
) = composed {
    val negative = status == WantedFramedStyleStatus.Negative

    this
        // 선택 상태는 disabled 에서도 Ring 을 그리고, Ring 색의 현재 opacity 에 43% 를 곱해 낮춘다.
        .focusRing(
            visible = selected,
            shape = shape,
            color = disabledAlpha(
                color = if (negative) {
                    DesignSystemTheme.colors.lineNegativeFocus
                } else {
                    DesignSystemTheme.colors.lineBrandFocus
                },
                enabled = enabled
            )
        )
        .then(
            if (shadow != null) {
                // 그림자는 배경이 있어야 보이므로 shadow 를 켰을 때만 배경을 깐다.
                // 배경은 border 보다 먼저 그린다. 나중에 그리면 배경이 border 위를 덮어 border 색이 지정 opacity 로 보이지 않는다.
                Modifier
                    .wantedDropShadow(shadow)
                    .background(color = DesignSystemTheme.colors.backgroundNeutralPrimary, shape = shape)
            } else {
                Modifier
            }
        )
        // Modifier.border 는 content 를 그린 뒤 자기 stroke 를 덧그리므로 먼저 선언한 border 가 위에 얹힌다.
        // 상태 색 border 를 먼저(위), underlay 를 나중(아래)에 선언해야 상태 색이 지정 opacity 그대로 보인다.
        .border(
            shape = shape,
            color = disabledAlpha(
                color = framedBorderColor(selected = selected, status = status),
                enabled = enabled
            ),
            width = 1.dp
        )
        .border(
            shape = shape,
            color = if (selected) {
                disabledAlpha(color = DesignSystemTheme.colors.surfaceNeutralPrimary, enabled = enabled)
            } else {
                DesignSystemTheme.colors.transparent
            },
            // 상태 색 border 와 같은 굵기(1dp)여야 한다. 굵기가 다르면 underlay 가 비쳐 두 겹으로 보인다.
            width = 1.dp
        )
        .clip(shape)
}

// 상태 색 border 색상입니다. line 토큰에 opacity 가 포함돼 있어 토큰을 그대로 씁니다.
@Composable
private fun framedBorderColor(
    selected: Boolean,
    status: WantedFramedStyleStatus
): Color = when (status) {
    WantedFramedStyleStatus.Negative -> if (selected) {
        DesignSystemTheme.colors.lineNegativeStrong
    } else {
        DesignSystemTheme.colors.lineNegativePrimary
    }

    WantedFramedStyleStatus.Normal -> if (selected) {
        DesignSystemTheme.colors.lineBrandStrong
    } else {
        DesignSystemTheme.colors.lineNeutralSecondary
    }
}

// disabled 는 상태와 관계없이 테두리·Base·Ring 색의 현재 opacity 에 43% 를 곱합니다(디자인 확인).
// 컬러 토큰 값(RGB)은 그대로 두고 알파값만 곱합니다. 43% 로 고정하는 것이 아니라 추가로 곱하는 값입니다.
// line 토큰은 opacity 를 이미 갖고 있어
// 예를 들어 line/brand/strong(43%) 은 disabled 에서 약 18.5%(0.43 × 0.43)가 됩니다. Figma 레이어 opacity 와 같은 결과입니다.
private fun disabledAlpha(color: Color, enabled: Boolean): Color =
    if (enabled) color else color.copy(alpha = color.alpha * DISABLED_OPACITY)

// 이전에는 disabled 알파를 0.185f 로 고정했습니다(copy(alpha = 0.185f)). 알파가 없는 원색 토큰을 쓰던 시절
// "토큰 43% × disabled 43%" 를 미리 계산해 둔 값입니다. 지금 line 토큰은 토큰마다 알파가 달라(43%·52% 등)
// 고정값으로 덮어쓰면 토큰 알파가 무시되고 모두 18.5% 가 됩니다. 그래서 고정값 대신 현재 알파에 곱합니다.
private const val DISABLED_OPACITY = 0.43f

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
 * 프레임 스타일의 상태를 정의하는 enum 클래스입니다. 선택 여부는 `framedStyle` 의 `selected` 로 따로 지정합니다.
 *
 * 각 상태에 따라 테두리 색상이 달라집니다(테두리 굵기는 모두 1dp):
 * - Normal: 일반 상태입니다. 기본 테두리 색상이 적용됩니다.
 * - Negative: 오류 또는 부정적인 상태입니다. 빨간색 계열 테두리가 적용됩니다.
 */
enum class WantedFramedStyleStatus {
    Normal,
    Negative
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
                        .framedStyle(status = WantedFramedStyleStatus.Normal)
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .framedStyle(selected = true, status = WantedFramedStyleStatus.Normal)
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .framedStyle(status = WantedFramedStyleStatus.Negative)
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .framedStyle(selected = true, status = WantedFramedStyleStatus.Negative)
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .framedStyle(
                            status = WantedFramedStyleStatus.Normal,
                            shadow = WantedShadowStyle.XSmall()
                        )
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .framedStyle(status = WantedFramedStyleStatus.Normal, enabled = false)
                )

                // disabled: 테두리·Base·Ring 색의 현재 opacity 에 43% 를 곱해 그린다.
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .framedStyle(selected = true, status = WantedFramedStyleStatus.Normal, enabled = false)
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .framedStyle(status = WantedFramedStyleStatus.Negative, enabled = false)
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .framedStyle(selected = true, status = WantedFramedStyleStatus.Negative, enabled = false)
                )
            }
        }
    }
}
