package com.wanted.android.wanted.design.navigations.pagination

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.BorderType
import com.wanted.android.wanted.design.base.getBorderModifier
import com.wanted.android.wanted.design.navigations.pagination.WantedPaginationContract.IndicatorDotSize
import com.wanted.android.wanted.design.navigations.pagination.WantedPaginationContract.WantedDotIndicatorSize
import com.wanted.android.wanted.design.navigations.pagination.WantedPaginationContract.WantedDotIndicatorType
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_16
import com.wanted.android.wanted.design.util.OPACITY_52
import com.wanted.android.wanted.design.util.OPACITY_8
import kotlin.math.abs
import kotlin.math.floor


/**
 * 페이지네이션용 dot indicator를 표시하는 컴포저블입니다.
 *
 * 현재 페이지 위치를 중심으로 일정 개수의 dot을 보여주며, 선택 상태에 따라 색상, 크기, 애니메이션이 다르게 표시됩니다.
 * `type`이 `Normal`일 경우 배경이 채워진 형태, `White`일 경우 테두리만 있는 흰색 스타일로 구성됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedDotIndicator(
 *     totalPageCount = 10,
 *     visibleDotCount = 5,
 *     currentIndex = 2
 * )
 * ```
 *
 * @param totalPageCount Int: 전체 페이지 수입니다.
 * @param visibleDotCount Int: 화면에 표시할 최대 dot 수입니다.
 * @param currentIndex Int: 현재 선택된 페이지 index입니다.
 * @param modifier Modifier: 배치와 외형을 위한 Modifier입니다.
 * @param size WantedDotIndicatorSize: dot의 크기를 결정합니다. 기본값은 Medium입니다.
 * @param type WantedDotIndicatorType: dot 스타일 타입입니다. 기본값은 Normal입니다.
 */
@Composable
fun WantedDotIndicator(
    totalPageCount: Int,
    visibleDotCount: Int,
    currentIndex: Int,
    modifier: Modifier = Modifier,
    size: WantedDotIndicatorSize = WantedDotIndicatorSize.Medium,
    type: WantedDotIndicatorType = WantedDotIndicatorType.Normal
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

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            space = if (type == WantedDotIndicatorType.Normal) 10.dp else 6.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalPageCount) { index ->
            if (type == WantedDotIndicatorType.Normal) {
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
    indicatorSize: WantedDotIndicatorSize,
    index: Int,
    visibleArea: Pair<Int, Int>,
    visibleDotCount: Int,
    totalPageCount: Int,
    currentIndex: Int,
    modifier: Modifier = Modifier
) {

    val indicatorDotSize by remember(
        index,
        visibleArea,
        visibleDotCount,
        totalPageCount,
        currentIndex
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

    val isVisible by remember(indicatorDotSize) { mutableStateOf(indicatorDotSize > 0.dp) }


    val backgroundColor by animateColorAsState(
        targetValue = if (currentIndex == index) {
            colorResource(id = R.color.label_normal)
        } else {
            colorResource(id = R.color.label_normal).copy(OPACITY_16)
        },
        animationSpec = tween(
            durationMillis = 200,
            easing = FastOutSlowInEasing
        ),
        label = ""
    )

    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = if (visibleArea.first == index) {
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
        exit = if (visibleArea.first == index) {
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
                .size(indicatorDotSize)
        )
    }
}


@Composable
private fun IndicatorBorder(
    indicatorSize: WantedDotIndicatorSize,
    index: Int,
    visibleArea: Pair<Int, Int>,
    visibleDotCount: Int,
    totalPageCount: Int,
    currentIndex: Int,
    modifier: Modifier = Modifier
) {

    val indicatorDotSize by remember(
        index,
        visibleArea,
        visibleDotCount,
        totalPageCount,
        currentIndex
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

    val isVisible by remember(indicatorDotSize) { mutableStateOf(indicatorDotSize > 0.dp) }

    val backgroundColor by animateColorAsState(
        targetValue = if (currentIndex == index) {
            colorResource(id = R.color.static_white)
        } else {
            colorResource(id = R.color.static_white).copy(alpha = OPACITY_52)
        },
        animationSpec = tween(
            durationMillis = 200,
            easing = FastOutSlowInEasing
        ),
        label = ""
    )

    val borderColor by animateColorAsState(
        targetValue = if (currentIndex == index) {
            colorResource(id = R.color.line_normal_neutral)
        } else {
            colorResource(id = R.color.line_normal_neutral).copy(OPACITY_8)
        },
        animationSpec = tween(
            durationMillis = 200,
            easing = FastOutSlowInEasing
        ),
        label = ""
    )

    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = if (visibleArea.first == index) {
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
        exit = if (visibleArea.first == index) {
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
                    size = indicatorDotSize,
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
                .size(indicatorDotSize)
        )
    }
}


private fun getPaginationDotVisibleArea(
    maxDotCount: Int = 5,
    totalPageCount: Int = 10,
    currentIndex: Int = 0,
): Pair<Int, Int> {
    val centerIndex = floor(maxDotCount * 0.5).toInt()
    val isEven = maxDotCount % 2 == 0

    /**
     * dot 이 짝수일때 Center
     */
    if (isEven
        && currentIndex >= centerIndex
        && totalPageCount - centerIndex > currentIndex
    ) {
        return Pair(currentIndex - centerIndex + 1, currentIndex + centerIndex)
    }


    /**
     * 처음
     */
    if (currentIndex <= centerIndex) {
        return Pair(0, maxDotCount - 1)
    }

    /**
     * 마지막
     */
    if (totalPageCount - centerIndex <= currentIndex) {
        return Pair(totalPageCount - maxDotCount, totalPageCount - 1)
    }

    /**
     * 중간
     */
    return Pair(currentIndex - centerIndex, currentIndex + centerIndex)
}

private fun getDotSize(
    size: WantedDotIndicatorSize,
    dotSize: IndicatorDotSize,
): Dp {
    return if (size == WantedDotIndicatorSize.Medium) {
        when (dotSize) {
            IndicatorDotSize.Max -> 10
            IndicatorDotSize.Mid -> 8
            IndicatorDotSize.Min -> 6
            IndicatorDotSize.Zero -> 0
        }

    } else {
        when (dotSize) {
            IndicatorDotSize.Max -> 6
            IndicatorDotSize.Mid -> 4
            IndicatorDotSize.Min -> 2
            IndicatorDotSize.Zero -> 0
        }
    }.dp
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
            ) {
                WantedDotIndicator(
                    visibleDotCount = 5,
                    size = WantedDotIndicatorSize.Small,
                    totalPageCount = 10,
                    currentIndex = 3
                )

                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .background(Color.Cyan)
                        .padding(5.dp)
                ) {
                    WantedDotIndicator(
                        visibleDotCount = 5,
                        size = WantedDotIndicatorSize.Small,
                        type = WantedDotIndicatorType.White,
                        totalPageCount = 10,
                        currentIndex = 3
                    )
                }

            }
        }
    }
}