package com.wanted.android.wanted.design.pagination

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.avatar.BoarderType
import com.wanted.android.wanted.design.avatar.getBoarderModifier
import com.wanted.android.wanted.design.pagination.WantedIndicatorContract.IndicatorDotSize
import com.wanted.android.wanted.design.pagination.WantedIndicatorContract.WantedDotIndicatorType
import com.wanted.android.wanted.design.pagination.WantedIndicatorContract.WantedIndicatorSize
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_16
import com.wanted.android.wanted.design.util.OPACITY_52
import kotlin.math.abs
import kotlin.math.floor


@Composable
fun WantedDotIndicator(
    modifier: Modifier = Modifier,
    indicatorSize: WantedIndicatorSize = WantedIndicatorSize.Normal,
    type: WantedDotIndicatorType = WantedDotIndicatorType.Normal,
    totalPageCount: Int,
    visibleDotCount: Int,
    currentIndex: Int
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
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 10.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalPageCount) { index ->
            if (type == WantedDotIndicatorType.Normal) {
                IndicatorDot(
                    indicatorSize = indicatorSize,
                    index = index,
                    visibleArea = visibleArea,
                    visibleDotCount = visibleDotCount,
                    totalPageCount = totalPageCount,
                    currentIndex = currentIndex
                )
            } else {
                IndicatorBoarder(
                    indicatorSize = indicatorSize,
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
    modifier: Modifier = Modifier,
    indicatorSize: WantedIndicatorSize,
    index: Int,
    visibleArea: Pair<Int, Int>,
    visibleDotCount: Int,
    totalPageCount: Int,
    currentIndex: Int
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

    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = if (visibleArea.first == index) {
            expandHorizontally(expandFrom = Alignment.End)
        } else {
            expandHorizontally(expandFrom = Alignment.Start)
        },
        exit = if (visibleArea.first == index) {
            shrinkHorizontally(shrinkTowards = Alignment.End)
        } else {
            shrinkHorizontally(shrinkTowards = Alignment.Start)
        }
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(indicatorDotSize)
                .background(
                    color = if (currentIndex == index) {
                        colorResource(id = R.color.label_normal)
                    } else {
                        colorResource(id = R.color.label_normal).copy(OPACITY_16)
                    },
                    shape = CircleShape
                )
        )
    }
}


@Composable
private fun IndicatorBoarder(
    modifier: Modifier = Modifier,
    indicatorSize: WantedIndicatorSize,
    index: Int,
    visibleArea: Pair<Int, Int>,
    visibleDotCount: Int,
    totalPageCount: Int,
    currentIndex: Int
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

    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = if (visibleArea.first == index) {
            expandHorizontally(expandFrom = Alignment.End)
        } else {
            expandHorizontally(expandFrom = Alignment.Start)
        },
        exit = if (visibleArea.first == index) {
            shrinkHorizontally(shrinkTowards = Alignment.End)
        } else {
            shrinkHorizontally(shrinkTowards = Alignment.Start)
        }
    ) {
        Box(
            modifier = Modifier
                .size(indicatorDotSize)
                .background(
                    colorResource(id = R.color.static_white),
                    shape = CircleShape
                )
                .getBoarderModifier(
                    size = indicatorDotSize,
                    isCircleShape = true,
                    boarderType = BoarderType.OutLine,
                    boarderWidth = 1.dp,
                    boarderColor = if (currentIndex == index) {
                        colorResource(id = R.color.transparent)
                    } else {
                        colorResource(id = R.color.static_white).copy(alpha = OPACITY_52)
                    }
                )
                .getBoarderModifier(
                    size = indicatorDotSize,
                    isCircleShape = true,
                    boarderType = BoarderType.OutLine,
                    boarderWidth = 1.dp,
                    boarderColor = if (currentIndex == index) {
                        colorResource(id = R.color.line_normal_neutral)
                    } else {
                        colorResource(id = R.color.line_normal_neutral)
                    }
                )


        )
    }
}


fun getPaginationDotVisibleArea(
    maxDotCount: Int = 5,
    totalPageCount: Int = 10,
    currentIndex: Int = 0
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
    size: WantedIndicatorSize,
    dotSize: IndicatorDotSize
): Dp {
    return if (size == WantedIndicatorSize.Normal) {
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
    totalPageCount: Int
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
                    indicatorSize = WantedIndicatorSize.Small,
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
                        indicatorSize = WantedIndicatorSize.Small,
                        type = WantedDotIndicatorType.White,
                        totalPageCount = 10,
                        currentIndex = 3
                    )
                }

            }
        }
    }
}