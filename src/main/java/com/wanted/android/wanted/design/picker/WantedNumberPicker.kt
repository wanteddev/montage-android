package com.wanted.android.wanted.design.picker

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
fun WantedNumberPicker(
    modifier: Modifier = Modifier,
    start: Int,
    end: Int,
    step: Int,
    itemList: List<Int> = mutableListOf<Int>().apply {
        for (value in start until end + 1 step step) {
            this.add(value)
        }
    }.toList(),
    selectedValue: Int = start,
    enableMinValue: Int = 0,
    enableMaxValue: Int = itemList.lastOrNull() ?: 0,
    pagerState: PagerState = rememberPagerState(
        initialPage = itemList.indexOf(selectedValue).let { if (it == -1) 0 else it },
        initialPageOffsetFraction = 0f
    ) {
        itemList.size
    },
    textStyle: TextStyle = WantedTextStyle(
        colorRes = R.color.label_normal,
        style = DesignSystemTheme.typography.heading1Medium
    ),
    itemSize: Dp = with(LocalDensity.current) { textStyle.lineHeight.toDp() },
    visibleCount: Int = 7,
    onSelect: (index: Int, value: Int, enabled: Boolean) -> Unit = { _, _, _ -> }
) {

    WantedStringPicker(
        modifier = modifier,
        itemList = itemList.map { it.toString() },
        selectedIndex = itemList.indexOf(selectedValue).let { if (it == -1) 0 else it },
        enableStartIndex = itemList.indexOf(enableMinValue).let { index ->
            if (index == -1) {
                itemList.firstOrNull { it > enableMinValue }?.let {
                    itemList.indexOf(it)
                } ?: 0
            } else {
                index
            }
        },
        enableEndIndex = itemList.indexOf(enableMaxValue).let { index ->
            if (index == -1) {
                itemList.firstOrNull { it > enableMaxValue }?.let {
                    val result = itemList.indexOf(it) - 1
                    if (result > 0) result else 0
                } ?: 0
            } else {
                index
            }
        },
        pagerState = pagerState,
        textStyle = textStyle,
        itemSize = itemSize,
        visibleCount = visibleCount,
        onSelect = { index, enabled ->
            onSelect(index, itemList[index], enabled)
        }
    )
}

@Composable
fun WantedNumberPicker(
    modifier: Modifier = Modifier,
    itemList: List<Int>,
    selectedIndex: Int = 0,
    enableStartIndex: Int = 0,
    enableEndIndex: Int = itemList.lastIndex,
    pagerState: PagerState = rememberPagerState(
        initialPage = selectedIndex,
        initialPageOffsetFraction = 0f
    ) {
        itemList.size
    },
    textStyle: TextStyle = WantedTextStyle(
        colorRes = R.color.label_normal,
        style = DesignSystemTheme.typography.heading1Medium
    ),
    itemSize: Dp = with(LocalDensity.current) { textStyle.lineHeight.toDp() },
    visibleCount: Int = 7,
    onSelect: (index: Int, enabled: Boolean) -> Unit = { _, _ -> }
) {
    WantedStringPicker(
        modifier = modifier,
        itemList = itemList.map { it.toString() },
        selectedIndex = selectedIndex,
        enableStartIndex = enableStartIndex,
        enableEndIndex = enableEndIndex,
        pagerState = pagerState,
        textStyle = textStyle,
        itemSize = itemSize,
        visibleCount = visibleCount,
        onSelect = onSelect
    )
}

@Composable
fun WantedStringPicker(
    modifier: Modifier = Modifier,
    itemList: List<String>,
    selectedIndex: Int = 0,
    enableStartIndex: Int = 0,
    enableEndIndex: Int = itemList.lastIndex,
    pagerState: PagerState = rememberPagerState(
        initialPage = selectedIndex,
        initialPageOffsetFraction = 0f
    ) {
        itemList.size
    },
    textStyle: TextStyle = WantedTextStyle(
        colorRes = R.color.label_normal,
        style = DesignSystemTheme.typography.heading1Medium
    ),
    itemSize: Dp = with(LocalDensity.current) { textStyle.lineHeight.toDp() },
    visibleCount: Int = 7,
    onSelect: (index: Int, enabled: Boolean) -> Unit = { _, _ -> }
) {

    LaunchedEffect(selectedIndex) {
        if (pagerState.currentPage != selectedIndex) {
            pagerState.scrollToPage(selectedIndex)
        }
    }

    LaunchedEffect(key1 = pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            onSelect(
                pagerState.currentPage,
                pagerState.currentPage in enableStartIndex..enableEndIndex
            )
        }
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(itemSize * (visibleCount)),
        contentAlignment = Alignment.CenterStart
    ) {
        VerticalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            contentPadding = PaddingValues(
                top = (maxHeight - itemSize) * 0.5f,
                bottom = (maxHeight - itemSize) * 0.5f - itemSize + (GAP / 2).dp
            ),
            pageSize = PageSize.Fixed(itemSize + GAP.dp),
            flingBehavior = PagerDefaults.flingBehavior(
                state = pagerState,
                pagerSnapDistance = PagerSnapDistance.atMost(itemList.size),
                snapAnimationSpec = spring(stiffness = Spring.StiffnessHigh)
            ),
            pageContent = { page ->
                PickerContent(
                    modifier = Modifier.height(height = itemSize),
                    pagerState = pagerState,
                    page = page,
                    visibleCount = visibleCount,
                    title = itemList.get(index = page),
                    enabled = page in enableStartIndex..enableEndIndex
                )
            }
        )

        Box(
            Modifier
                .padding(horizontal = 8.dp)
                .height(itemSize)
                .fillMaxWidth()
                .background(
                    color = colorResource(R.color.fill_normal),
                    shape = RoundedCornerShape(8.dp)
                )
        )
    }
}

@Composable
fun PickerContent(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    page: Int,
    visibleCount: Int,
    title: String,
    enabled: Boolean
) {
    var offsetY by remember { mutableStateOf(0.dp) }

    val halfVisibleCount = (visibleCount / 2)
    Text(
        modifier = modifier
            .background(Color.Transparent)
            .wrapContentSize()
            .offset(0.dp, offsetY)
            .graphicsLayer {
                val pageOffset = pagerState.calculateCurrentOffsetForPage(page)
                val rotation = lerp(0f, 90f, pageOffset / halfVisibleCount)
                rotationX = if (rotation > 90 || rotation < -90) {
                    90f
                } else {
                    rotation
                }

                scaleX = lerp(
                    SELECT_SCALE,
                    DESELECT_SCALE,
                    if (pageOffset >= 0) pageOffset / halfVisibleCount else -pageOffset / halfVisibleCount
                )
                scaleY = lerp(
                    SELECT_SCALE,
                    DESELECT_SCALE,
                    if (pageOffset >= 0) pageOffset / halfVisibleCount else -pageOffset / halfVisibleCount
                )


                offsetY = if (pageOffset < 0) {
                    -(GAP / halfVisibleCount * (pageOffset * pageOffset)).dp
                } else {
                    (GAP / halfVisibleCount * (pageOffset * pageOffset)).dp
                }
                Log.d("_SMY", "graphicsLayer $page $title $offsetY $pageOffset")
            },
        text = title,
        textAlign = TextAlign.Center,
        style = WantedTextStyle(
            colorRes = if (enabled) {
                if (pagerState.currentPage == page) {
                    R.color.label_normal
                } else {
                    R.color.label_assistive
                }
            } else {
                R.color.label_disable
            },
            style = DesignSystemTheme.typography.heading1Medium
        )
    )
}

fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}

private const val SELECT_SCALE = 1f
private const val DESELECT_SCALE = 0.75f
private const val GAP = 20

@DevicePreviews
@Composable
private fun WantedNumberPickerPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                WantedNumberPicker(
                    modifier = Modifier.fillMaxWidth(),
                    itemList = listOf(1, 2, 3, 4, 5)
                )
            }
        }
    }
}