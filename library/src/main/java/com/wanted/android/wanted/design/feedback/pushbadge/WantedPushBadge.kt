package com.wanted.android.wanted.design.feedback.pushbadge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.feedback.pushbadge.PushBadgeTypes.PushBadgePosition
import com.wanted.android.wanted.design.feedback.pushbadge.PushBadgeTypes.PushBadgeSize
import com.wanted.android.wanted.design.feedback.pushbadge.PushBadgeTypes.PushBadgeVariant
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import kotlin.math.min

/**
 * WantedPushBadge
 *
 * 아이콘이나 UI 요소에 붙여 표시되는 Push badge 컴포넌트입니다.
 *
 * Dot, Text, MaxCount 타입 중 하나를 선택할 수 있으며, 위치·사이즈·색상·타이포그래피를 설정할 수 있습니다.
 * 아바타 등 겹치는 배경에서 배지를 분리하는 outlineBorder(외곽 보더)와, 부착 위치를 미세 조정하는 inset 을 지원합니다.
 *
 * 다이나믹 타입(시스템 폰트 확대)은 핸드오프 기준에 맞춰 fontScale [MAX_FONT_SCALE] 까지만 반영합니다.
 * 배지 높이는 고정값이 아니라 `라인 높이 + 세로 패딩` 으로 결정되므로 확대 배율에 따라 함께 커집니다.
 *
 * 사용 예시:
 * ```kotlin
 * // 임의 문자열
 * WantedPushBadge(variant = PushBadgeVariant.Text, text = "N")
 *
 * // 개수(99 초과 시 "99+")
 * WantedPushBadge(variant = PushBadgeVariant.MaxCount, text = "128") // -> "99+"
 *
 * // 아바타 위에 외곽 보더와 함께
 * WantedPushBadge(size = PushBadgeSize.Medium, outlineBorder = true)
 * ```
 *
 * @param modifier Modifier: 배지의 배치·정렬 등에 사용되는 Modifier입니다.
 * @param variant PushBadgeVariant: 표시할 배지 타입입니다. Dot, Text, MaxCount 중 선택합니다.
 * @param size PushBadgeSize: 배지의 크기입니다. XSmall, Small, Medium 중 선택합니다.
 * @param position PushBadgePosition: 배지의 위치입니다. TopEnd 등 9가지 위치를 지원합니다.
 * @param text String: `Text`·`MaxCount` 타입일 때 표시할 문자열입니다. `MaxCount` 는 이 값을 숫자로 해석합니다.
 * @param maxCount Int: `MaxCount` 타입의 상한값입니다. `text` 를 숫자로 해석한 값이 이 값을 넘으면 "maxCount+"로 표기합니다. 기본값은 99입니다.
 * @param outlineBorder Boolean: 겹치는 배경에서 배지를 분리하는 외곽 보더 표시 여부입니다. 기본값은 false입니다.
 * @param outlineBorderColor Color: 외곽 보더 색상입니다. 기본값은 backgroundNeutralPrimary입니다.
 * @param background Color: 배지의 배경 색상입니다. 기본값은 surfaceBrandPrimary입니다.
 * @param contentColor Color: 텍스트 색상입니다. 기본값은 static_white입니다.
 * @param textStyle TextStyle?: 텍스트 타이포그래피 오버라이드입니다. null이면 사이즈별 기본값을 사용합니다.
 * @param inset DpOffset: 부착 위치 미세 조정값입니다. 값이 커질수록 대상 안쪽으로 이동하며, 기본값은 (0, 0)입니다.
 */
@Composable
fun WantedPushBadge(
    modifier: Modifier = Modifier,
    variant: PushBadgeVariant = PushBadgeVariant.Dot,
    size: PushBadgeSize = PushBadgeSize.XSmall,
    position: PushBadgePosition = PushBadgePosition.TopEnd,
    text: String = "",
    maxCount: Int = DEFAULT_MAX_COUNT,
    outlineBorder: Boolean = false,
    outlineBorderColor: Color = DesignSystemTheme.colors.backgroundNeutralPrimary,
    background: Color = DesignSystemTheme.colors.surfaceBrandPrimary,
    contentColor: Color = DesignSystemTheme.colors.staticWhite,
    textStyle: TextStyle? = null,
    inset: DpOffset = DpOffset(0.dp, 0.dp)
) {
    val density = LocalDensity.current
    var badgeSize by remember { mutableStateOf(DpSize(0.dp, 0.dp)) }

    Box(
        modifier = modifier
            .offset { getOffset(density, position, badgeSize.width, badgeSize.height, inset) }
            .onGloballyPositioned { coordinates ->
                badgeSize = with(density) {
                    DpSize(coordinates.size.width.toDp(), coordinates.size.height.toDp())
                }
            },
        contentAlignment = Alignment.Center
    ) {
        // 핸드오프: Android 는 fontScale 1.30(시스템 '가장 크게')까지만 확대한다. (iOS 의 dynamicTypeSize 상한과 동일)
        // dp 배율(density.density)은 그대로 두고 fontScale 만 제한해, sp 기반 텍스트만 상한이 걸린다.
        CompositionLocalProvider(LocalDensity provides density.clampFontScale()) {
            PushBadgeBody(
                variant = variant,
                size = size,
                text = text,
                maxCount = maxCount,
                outlineBorder = outlineBorder,
                outlineBorderColor = outlineBorderColor,
                background = background,
                contentColor = contentColor,
                textStyle = textStyle
            )
        }
    }
}

/** fontScale 만 [MAX_FONT_SCALE] 로 제한한 Density 를 반환합니다. dp 배율은 그대로 유지합니다. */
private fun Density.clampFontScale(): Density =
    if (fontScale <= MAX_FONT_SCALE) this else Density(density, min(fontScale, MAX_FONT_SCALE))

@Composable
private fun PushBadgeBody(
    variant: PushBadgeVariant,
    size: PushBadgeSize,
    text: String,
    maxCount: Int,
    outlineBorder: Boolean,
    outlineBorderColor: Color,
    background: Color,
    contentColor: Color,
    textStyle: TextStyle?
) {
    val metrics = size.metrics
    val fullShape = DesignSystemTheme.radius.radiusFull

    val outlineBorderGap = when (variant) {
        PushBadgeVariant.Dot -> (metrics.dotOutlineBorderSize - metrics.dotSize) / 2
        else -> metrics.textOutlineBorderGap
    }

    val outlineBorderModifier = if (outlineBorder) {
        Modifier
            .background(outlineBorderColor, fullShape)
            .padding(outlineBorderGap)
    } else {
        Modifier
    }

    Box(
        modifier = outlineBorderModifier,
        contentAlignment = Alignment.Center
    ) {
        when (variant) {
            PushBadgeVariant.Dot -> {
                Box(
                    modifier = Modifier
                        .size(metrics.dotSize)
                        .background(background, fullShape)
                )
            }

            PushBadgeVariant.Text -> {
                PushBadgeTextBody(
                    label = text,
                    size = size,
                    background = background,
                    contentColor = contentColor,
                    textStyle = textStyle,
                    shape = fullShape
                )
            }

            PushBadgeVariant.MaxCount -> {
                val count = text.toIntOrNull() ?: 0
                PushBadgeTextBody(
                    label = if (count > maxCount) "$maxCount+" else count.toString(),
                    size = size,
                    background = background,
                    contentColor = contentColor,
                    textStyle = textStyle,
                    shape = fullShape
                )
            }
        }
    }
}

@Composable
private fun PushBadgeTextBody(
    label: String,
    size: PushBadgeSize,
    background: Color,
    contentColor: Color,
    textStyle: TextStyle?,
    shape: Shape
) {
    val metrics = size.metrics

    Box(
        modifier = Modifier
            .background(background, shape)
            // 핸드오프: 1글자일 때는 고정 dp 가 아니라 1:1 비율로 정사각을 강제한다.
            .badgeSizing(square = label.length == SINGLE_CHARACTER_LENGTH)
            // 핸드오프: 높이는 고정하지 않고 라인 높이 + 세로 패딩으로 결정한다.
            .padding(
                horizontal = metrics.textPaddingHorizontal,
                vertical = metrics.textPaddingVertical
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = textStyle ?: size.defaultTextStyle(),
            color = contentColor
        )
    }
}

// 배지 본체의 크기를 결정합니다.
//
// 1. 부모 폭 제한을 무시하고 내용 그대로 측정합니다. 배지는 대상(아이콘 버튼 등) 밖으로 넘쳐 붙는
//    요소인데 부모 폭에 맞춰 줄면, 폰트 확대 시 "99+" 가 "9…" 로 잘린다.
// 2. [square] 가 true 면 가로 폭을 세로 높이 이상으로 늘려 1:1 비율을 보장합니다. 고정 dp 최소 너비로는
//    폰트 확대 시 높이만 커져 정사각이 깨지므로, 실제 측정 높이를 기준으로 맞춥니다.
private fun Modifier.badgeSizing(square: Boolean): Modifier = layout { measurable, constraints ->
    val placeable = measurable.measure(
        constraints.copy(minWidth = 0, maxWidth = Constraints.Infinity)
    )
    val width = if (square) maxOf(placeable.width, placeable.height) else placeable.width

    layout(width, placeable.height) {
        placeable.place(x = (width - placeable.width) / 2, y = 0)
    }
}

private fun getOffset(
    density: Density,
    position: PushBadgePosition,
    width: Dp,
    height: Dp,
    inset: DpOffset
): IntOffset {
    val (signX, signY) = when (position) {
        PushBadgePosition.TopStart -> -1f to -1f
        PushBadgePosition.TopCenter -> 0f to -1f
        PushBadgePosition.TopEnd -> 1f to -1f
        PushBadgePosition.MiddleStart -> -1f to 0f
        PushBadgePosition.MiddleCenter -> 0f to 0f
        PushBadgePosition.MiddleEnd -> 1f to 0f
        PushBadgePosition.BottomStart -> -1f to 1f
        PushBadgePosition.BottomCenter -> 0f to 1f
        PushBadgePosition.BottomEnd -> 1f to 1f
    }

    return with(density) {
        val halfWidth = width.toPx() / 2f
        val halfHeight = height.toPx() / 2f
        val insetX = inset.x.toPx()
        val insetY = inset.y.toPx()

        // signX/Y 방향이 바깥쪽(대상 모서리 밖)이므로, inset 만큼 다시 안쪽으로 당긴다.
        IntOffset(
            (signX * (halfWidth - insetX)).toInt(),
            (signY * (halfHeight - insetY)).toInt()
        )
    }
}

private const val DEFAULT_MAX_COUNT = 99

/** 핸드오프가 정의한 Android 다이나믹 타입 확대 상한입니다. (시스템 '가장 크게') */
private const val MAX_FONT_SCALE = 1.3f

/** 1:1 정사각 비율을 강제하는 글자 수입니다. */
private const val SINGLE_CHARACTER_LENGTH = 1

/**
 * data class PushBadgeMetrics
 *
 * Push badge 의 사이즈별 치수 스펙입니다. (Figma 4.0.0 핸드오프 기준)
 *
 * @property dotSize Dp: Dot 타입의 점 지름입니다.
 * @property dotOutlineBorderSize Dp: Dot 타입에 outlineBorder 적용 시 외곽 보더 지름입니다.
 * @property textPaddingVertical Dp: Text/MaxCount 배지의 상하 여백입니다. 라인 높이와 합쳐 배지 높이를 만듭니다.
 * @property textPaddingHorizontal Dp: Text/MaxCount 배지의 좌우 여백입니다.
 * @property textOutlineBorderGap Dp: Text/MaxCount 배지에 outlineBorder 적용 시 배지와 외곽 보더 사이 간격입니다.
 */
private data class PushBadgeMetrics(
    val dotSize: Dp,
    val dotOutlineBorderSize: Dp,
    val textPaddingVertical: Dp,
    val textPaddingHorizontal: Dp,
    val textOutlineBorderGap: Dp
)

private val PushBadgeSize.metrics: PushBadgeMetrics
    get() = when (this) {
        PushBadgeSize.XSmall -> PushBadgeMetrics(
            dotSize = 4.dp,
            dotOutlineBorderSize = 5.dp,
            textPaddingVertical = 1.dp,
            textPaddingHorizontal = 4.dp,
            textOutlineBorderGap = 1.dp
        )

        PushBadgeSize.Small -> PushBadgeMetrics(
            dotSize = 6.dp,
            dotOutlineBorderSize = 8.dp,
            textPaddingVertical = 3.dp,
            textPaddingHorizontal = 6.dp,
            textOutlineBorderGap = 1.5.dp
        )

        PushBadgeSize.Medium -> PushBadgeMetrics(
            dotSize = 8.dp,
            dotOutlineBorderSize = 10.dp,
            textPaddingVertical = 2.dp,
            textPaddingHorizontal = 7.dp,
            textOutlineBorderGap = 2.dp
        )
    }

@Composable
private fun PushBadgeSize.defaultTextStyle(): TextStyle = when (this) {
    PushBadgeSize.XSmall,
    PushBadgeSize.Small -> DesignSystemTheme.typography.caption2Bold

    PushBadgeSize.Medium -> DesignSystemTheme.typography.label1Bold
}


@DevicePreviews
@Composable
private fun WantedPushBadgePreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Row {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(20.dp)
                        .background(Color.White),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    PushBadgePosition.entries.forEach { position ->
                        Box(modifier = Modifier.background(Color.Gray)) {
                            WantedPushBadge(
                                modifier = Modifier,
                                position = position
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(20.dp)
                        .background(Color.White),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    PushBadgePosition.entries.forEach { position ->
                        Box(modifier = Modifier.background(Color.Gray)) {
                            WantedPushBadge(
                                modifier = Modifier,
                                variant = PushBadgeVariant.Text,
                                position = position,
                                text = "12"
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(20.dp)
                        .background(Color.White),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    PushBadgePosition.entries.forEach { position ->
                        Box(modifier = Modifier.background(Color.Gray)) {
                            WantedPushBadge(
                                modifier = Modifier,
                                variant = PushBadgeVariant.MaxCount,
                                position = position,
                                text = "128",
                                outlineBorder = true
                            )
                        }
                    }
                }
            }
        }
    }
}
