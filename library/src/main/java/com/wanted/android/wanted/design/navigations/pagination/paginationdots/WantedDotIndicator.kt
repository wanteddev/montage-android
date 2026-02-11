package com.wanted.android.wanted.design.navigations.pagination.paginationdots

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.base.BorderType
import com.wanted.android.wanted.design.base.getBorderModifier
import com.wanted.android.wanted.design.navigations.pagination.paginationdots.WantedPaginationDotDefaults.IndicatorDotSize
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_16
import com.wanted.android.wanted.design.util.OPACITY_52
import com.wanted.android.wanted.design.util.OPACITY_8
import kotlin.math.abs
import kotlin.math.floor

/**
 * WantedDotIndicator
 *
 * 페이지네이션용 Dot Indicator 컴포넌트입니다.
 *
 * 현재 페이지 위치를 중심으로 일정 개수의 Dot을 표시하며,
 * 선택 상태에 따라 크기와 색상이 애니메이션으로 변경됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * var currentPage by remember { mutableIntStateOf(0) }
 *
 * WantedDotIndicator(
 *     totalPageCount = 10,
 *     visibleDotCount = 5,
 *     currentIndex = currentPage
 * )
 * ```
 *
 * @param totalPageCount Int: 전체 페이지 수입니다.
 * @param visibleDotCount Int: 화면에 표시할 최대 dot 개수입니다.
 * @param currentIndex Int: 현재 선택된 페이지 인덱스입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param size WantedDotIndicatorSize: dot의 크기입니다.
 * @param type WantedDotIndicatorType: dot의 스타일 타입입니다.
 */
@Composable
fun WantedDotIndicator(
    totalPageCount: Int,
    visibleDotCount: Int,
    currentIndex: Int,
    modifier: Modifier = Modifier,
    size: WantedPaginationDotDefaults.WantedDotIndicatorSize = WantedPaginationDotDefaults.WantedDotIndicatorSize.Medium,
    type: WantedPaginationDotDefaults.WantedDotIndicatorType = WantedPaginationDotDefaults.WantedDotIndicatorType.Normal
) {
    val visibleArea by remember(visibleDotCount, totalPageCount, currentIndex) {
        mutableStateOf(
            getPaginationDotVisibleArea(
                maxDotCount = visibleDotCount,
                totalPageCount = totalPageCount,
                currentIndex = currentIndex
            )
        )
    }

    // 각 점이 차지할 수 있는 최대 크기 (애니메이션 중 최대 크기 기준)
    val maxDotSizeDp = when (size) {
        WantedPaginationDotDefaults.WantedDotIndicatorSize.Medium -> 10.dp
        WantedPaginationDotDefaults.WantedDotIndicatorSize.Small -> 6.dp
    }
    // 점들 사이의 간격
    val spaceDp =
        if (type == WantedPaginationDotDefaults.WantedDotIndicatorType.Normal) 10.dp else 6.dp

    // visibleDotCount개의 점과 그 사이의 간격을 포함하는 전체 너비 계산
    val calculatedWidth: Dp
    if (visibleDotCount > 0) {
        val countOfDotsValue: Int = visibleDotCount
        val dotSizeValue: Dp = maxDotSizeDp
        val spaceSizeValue: Dp = spaceDp

        val totalDotsWidth: Dp = dotSizeValue * countOfDotsValue
        val numberOfSpaces: Int = (countOfDotsValue - 1).coerceAtLeast(0)
        val totalSpacesWidth: Dp = spaceSizeValue * numberOfSpaces

        calculatedWidth = totalDotsWidth + totalSpacesWidth
    } else {
        calculatedWidth = 0.dp
    }

    Row(
        modifier = modifier
            .heightIn(min = maxDotSizeDp) // 높이는 가장 큰 점
            .width(calculatedWidth) // 계산된 고정 너비 적용
        // .background(Color.Yellow.copy(alpha = 0.3f)) // 디버깅용: Row의 실제 영역 확인 시 사용
        ,
        horizontalArrangement = Arrangement.spacedBy(
            space = spaceDp,
            alignment = Alignment.CenterHorizontally // 고정된 너비 내에서 점들을 중앙에 배치
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalPageCount) { index ->
            if (type == WantedPaginationDotDefaults.WantedDotIndicatorType.Normal) {
                IndicatorDot(
                    indicatorSize = size,
                    index = index,
                    visibleArea = visibleArea,
                    visibleDotCount = visibleDotCount,
                    totalPageCount = totalPageCount,
                    currentIndex = currentIndex
                )
            } else {
                IndicatorBorder(
                    indicatorSize = size,
                    index = index,
                    visibleArea = visibleArea,
                    visibleDotCount = visibleDotCount,
                    totalPageCount = totalPageCount,
                    currentIndex = currentIndex
                )
            }
        }
    }
}

@Composable
private fun IndicatorDot(
    indicatorSize: WantedPaginationDotDefaults.WantedDotIndicatorSize,
    index: Int,
    visibleArea: Pair<Int, Int>,
    visibleDotCount: Int,
    totalPageCount: Int,
    currentIndex: Int,
    modifier: Modifier = Modifier
) {

    val indicatorDotSizeDp by remember(
        index,
        visibleArea,
        visibleDotCount,
        totalPageCount,
        currentIndex,
        indicatorSize // indicatorSize도 remember 키에 포함
    ) {
        mutableStateOf(
            getDotSize(
                size = indicatorSize,
                dotSize = getIndicatorDotSize(
                    index = index,
                    visibleStartIndex = visibleArea.first,
                    visibleEndIndex = visibleArea.second,
                    visibleDotCount = visibleDotCount,
                    totalPageCount = totalPageCount
                )
            )
        )
    }

    val isVisible by remember(indicatorDotSizeDp) { mutableStateOf(indicatorDotSizeDp > 0.dp) }


    val backgroundColor by animateColorAsState(
        targetValue = if (currentIndex == index) {
            DesignSystemTheme.colors.labelNormal
        } else {
            DesignSystemTheme.colors.labelNormal.copy(OPACITY_16)
        },
        animationSpec = tween(
            durationMillis = 200,
            easing = FastOutSlowInEasing
        ),
        label = "backgroundColorIndicatorDot"
    )

    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = if (visibleArea.first == index && visibleDotCount > 1 && index != visibleArea.second) { // 첫번째 점 + 여러개 + 마지막 점 아님
            expandHorizontally(
                animationSpec = tween(
                    durationMillis = 200,
                    easing = FastOutSlowInEasing
                ),
                expandFrom = Alignment.End
            )
        } else {
            expandHorizontally(
                animationSpec = tween(
                    durationMillis = 200,
                    easing = FastOutSlowInEasing
                ),
                expandFrom = Alignment.Start
            )
        },
        exit = if (visibleArea.first == index && visibleDotCount > 1 && index != visibleArea.second) { // 첫번째 점 + 여러개 + 마지막 점 아님
            shrinkHorizontally(
                animationSpec = tween(
                    durationMillis = 200,
                    easing = FastOutSlowInEasing
                ),
                shrinkTowards = Alignment.End
            )
        } else {
            shrinkHorizontally(
                animationSpec = tween(
                    durationMillis = 200,
                    easing = FastOutSlowInEasing
                ),
                shrinkTowards = Alignment.Start
            )
        }
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(
                    color = backgroundColor,
                    shape = CircleShape
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = FastOutSlowInEasing
                    )
                )
                .size(indicatorDotSizeDp)
        )
    }
}


@Composable
private fun IndicatorBorder(
    indicatorSize: WantedPaginationDotDefaults.WantedDotIndicatorSize,
    index: Int,
    visibleArea: Pair<Int, Int>,
    visibleDotCount: Int,
    totalPageCount: Int,
    currentIndex: Int,
    modifier: Modifier = Modifier
) {

    val indicatorDotSizeDp by remember(
        index,
        visibleArea,
        visibleDotCount,
        totalPageCount,
        currentIndex,
        indicatorSize // indicatorSize도 remember 키에 포함
    ) {
        mutableStateOf(
            getDotSize(
                size = indicatorSize,
                dotSize = getIndicatorDotSize(
                    index = index,
                    visibleStartIndex = visibleArea.first,
                    visibleEndIndex = visibleArea.second,
                    visibleDotCount = visibleDotCount,
                    totalPageCount = totalPageCount
                )
            )
        )
    }

    val isVisible by remember(indicatorDotSizeDp) { mutableStateOf(indicatorDotSizeDp > 0.dp) }

    val backgroundColor by animateColorAsState(
        targetValue = if (currentIndex == index) {
            DesignSystemTheme.colors.staticWhite
        } else {
            DesignSystemTheme.colors.staticWhite.copy(alpha = OPACITY_52)
        },
        animationSpec = tween(
            durationMillis = 200,
            easing = FastOutSlowInEasing
        ),
        label = "backgroundColorIndicatorBorder"
    )

    val borderColor by animateColorAsState(
        targetValue = if (currentIndex == index) {
            DesignSystemTheme.colors.lineNormalNeutral
        } else {
            DesignSystemTheme.colors.lineNormalNeutral.copy(OPACITY_8)
        },
        animationSpec = tween(
            durationMillis = 200,
            easing = FastOutSlowInEasing
        ),
        label = "borderColorIndicatorBorder"
    )

    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = if (visibleArea.first == index && visibleDotCount > 1 && index != visibleArea.second) {
            expandHorizontally(
                animationSpec = tween(
                    durationMillis = 200,
                    easing = FastOutSlowInEasing
                ),
                expandFrom = Alignment.End
            )
        } else {
            expandHorizontally(
                animationSpec = tween(
                    durationMillis = 200,
                    easing = FastOutSlowInEasing
                ),
                expandFrom = Alignment.Start
            )
        },
        exit = if (visibleArea.first == index && visibleDotCount > 1 && index != visibleArea.second) {
            shrinkHorizontally(
                animationSpec = tween(
                    durationMillis = 200,
                    easing = FastOutSlowInEasing
                ),
                shrinkTowards = Alignment.End
            )
        } else {
            shrinkHorizontally(
                animationSpec = tween(
                    durationMillis = 200,
                    easing = FastOutSlowInEasing
                ),
                shrinkTowards = Alignment.Start
            )
        }
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(
                    color = backgroundColor,
                    shape = CircleShape
                )
                .getBorderModifier(
                    size = indicatorDotSizeDp,
                    isCircleShape = true,
                    borderType = BorderType.OutLine,
                    borderWidth = 1.dp,
                    borderColor = borderColor,
                    backgroundColor = backgroundColor
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = FastOutSlowInEasing
                    )
                )
                .size(indicatorDotSizeDp)
        )
    }
}


private fun getPaginationDotVisibleArea(
    maxDotCount: Int = 5,
    totalPageCount: Int = 10,
    currentIndex: Int = 0,
): Pair<Int, Int> {
    if (totalPageCount <= 0 || maxDotCount <= 0) return Pair(-1, -1)
    val actualMaxDotCount = maxDotCount.coerceAtMost(totalPageCount)

    if (totalPageCount <= actualMaxDotCount) { // 모든 점이 항상 보이는 경우
        return Pair(0, totalPageCount - 1)
    }

    val centerShift = floor(actualMaxDotCount / 2.0).toInt() // 중심으로부터 얼마나 떨어져 있는지 (예: 5개면 2, 6개면 3)
    val startIndex: Int
    val endIndex: Int

    if (currentIndex < centerShift) { // 앞쪽에 가까울 때
        startIndex = 0
        endIndex = actualMaxDotCount - 1
    } else if (currentIndex >= totalPageCount - (actualMaxDotCount - centerShift)) { // 뒤쪽에 가까울 때 (짝/홀수 maxDotCount 모두 고려)
        startIndex = totalPageCount - actualMaxDotCount
        endIndex = totalPageCount - 1
    } else { // 중간에 있을 때
        startIndex =
            currentIndex - centerShift + (if (actualMaxDotCount % 2 == 0) 1 else 0) // 짝수일때 오른쪽으로 한칸 더
        endIndex = currentIndex + floor((actualMaxDotCount - 1) / 2.0).toInt()
    }
    return Pair(startIndex.coerceAtLeast(0), endIndex.coerceAtMost(totalPageCount - 1))
}

private fun getDotSize(
    size: WantedPaginationDotDefaults.WantedDotIndicatorSize,
    dotSize: IndicatorDotSize,
): Dp {
    return if (size == WantedPaginationDotDefaults.WantedDotIndicatorSize.Medium) {
        when (dotSize) {
            IndicatorDotSize.Max -> 10.dp
            IndicatorDotSize.Mid -> 8.dp
            IndicatorDotSize.Min -> 6.dp
            IndicatorDotSize.Zero -> 0.dp
        }

    } else { // Small
        when (dotSize) {
            IndicatorDotSize.Max -> 6.dp
            IndicatorDotSize.Mid -> 4.dp
            IndicatorDotSize.Min -> 2.dp
            IndicatorDotSize.Zero -> 0.dp
        }
    }
}

private fun getIndicatorDotSize(
    index: Int,
    visibleStartIndex: Int,
    visibleEndIndex: Int,
    visibleDotCount: Int,
    totalPageCount: Int,
): IndicatorDotSize {

    if (index < visibleStartIndex || index > visibleEndIndex) {
        return IndicatorDotSize.Zero
    }

    val centerIndex = floor(visibleDotCount * 0.5)

    // first
    if ((visibleStartIndex == 0 && centerIndex > index)
        || (visibleStartIndex == 1 && index == 2)
    ) {
        return IndicatorDotSize.Max
    }

    // last
    if (
        (visibleEndIndex == totalPageCount - 1 && index >= totalPageCount - centerIndex - 1)
        || (visibleEndIndex == totalPageCount - 2 && index == totalPageCount - 3)
    ) {
        return IndicatorDotSize.Max
    }

    val distance = abs(index - visibleStartIndex).coerceAtMost(abs(index - visibleEndIndex))
    if ((visibleStartIndex == 1 && index == 1)
        || (visibleEndIndex == totalPageCount - 2 && index == totalPageCount - 2)
    ) {
        return IndicatorDotSize.Mid
    }

    return when (distance) {
        1 -> {
            if (visibleDotCount <= 4) {
                IndicatorDotSize.Max
            } else {
                IndicatorDotSize.Mid
            }
        }

        0 -> {
            if (visibleDotCount <= 4) {
                IndicatorDotSize.Mid
            } else {
                IndicatorDotSize.Min
            }
        }

        else -> IndicatorDotSize.Max
    }
}


@DevicePreviews
@Composable
private fun WantedDotIndicatorPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WantedDotIndicator(
                    modifier = Modifier.background(Color.LightGray.copy(alpha = 0.3f)),
                    visibleDotCount = 5,
                    size = WantedPaginationDotDefaults.WantedDotIndicatorSize.Medium,
                    totalPageCount = 10,
                    currentIndex = 0
                )
                WantedDotIndicator(
                    modifier = Modifier.background(Color.LightGray.copy(alpha = 0.3f)),
                    visibleDotCount = 5,
                    size = WantedPaginationDotDefaults.WantedDotIndicatorSize.Medium,
                    totalPageCount = 10,
                    currentIndex = 2
                )
                WantedDotIndicator(
                    modifier = Modifier.background(Color.LightGray.copy(alpha = 0.3f)),
                    visibleDotCount = 5,
                    size = WantedPaginationDotDefaults.WantedDotIndicatorSize.Medium,
                    totalPageCount = 10,
                    currentIndex = 4 // 중간
                )
                WantedDotIndicator(
                    modifier = Modifier.background(Color.LightGray.copy(alpha = 0.3f)),
                    visibleDotCount = 5,
                    size = WantedPaginationDotDefaults.WantedDotIndicatorSize.Medium,
                    totalPageCount = 10,
                    currentIndex = 8 // 거의 끝
                )
                WantedDotIndicator(
                    modifier = Modifier.background(Color.LightGray.copy(alpha = 0.3f)),
                    visibleDotCount = 5,
                    size = WantedPaginationDotDefaults.WantedDotIndicatorSize.Medium,
                    totalPageCount = 10,
                    currentIndex = 9 // 끝
                )
                WantedDotIndicator(
                    modifier = Modifier.background(Color.DarkGray.copy(alpha = 0.3f)),
                    visibleDotCount = 3,
                    type = WantedPaginationDotDefaults.WantedDotIndicatorType.White,
                    size = WantedPaginationDotDefaults.WantedDotIndicatorSize.Small,
                    totalPageCount = 3, // total == visible
                    currentIndex = 1
                )
                WantedDotIndicator(
                    modifier = Modifier.background(Color.DarkGray.copy(alpha = 0.3f)),
                    visibleDotCount = 7, // visible > total
                    type = WantedPaginationDotDefaults.WantedDotIndicatorType.White,
                    size = WantedPaginationDotDefaults.WantedDotIndicatorSize.Small,
                    totalPageCount = 5,
                    currentIndex = 2
                )
                WantedDotIndicator(
                    modifier = Modifier.background(Color.LightGray.copy(alpha = 0.3f)),
                    visibleDotCount = 6, // 짝수 visibleDotCount
                    size = WantedPaginationDotDefaults.WantedDotIndicatorSize.Medium,
                    totalPageCount = 10,
                    currentIndex = 4
                )
            }
        }
    }
}

