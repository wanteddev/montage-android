package com.wanted.android.wanted.design.input.picker.numberpicker

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * WantedNumberPicker
 *
 * 숫자 범위를 기준으로 자동 생성된 리스트를 사용하여 숫자를 선택할 수 있는 Wheel 형태의 NumberPicker입니다.
 *
 * start부터 end까지 지정한 step 간격의 숫자를 리스트로 생성하여 표시하며, 선택한 값은 콜백으로 반환됩니다.
 * 내부적으로 WantedStringPicker를 사용하여 Wheel UI를 제공합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedNumberPicker(
 *     start = 1,
 *     end = 12,
 *     step = 1,
 *     selectedValue = 5,
 *     onSelect = { index, value, enabled ->
 *         // 선택된 숫자 처리
 *     }
 * )
 * ```
 *
 * @param start Int: 시작 숫자입니다
 * @param end Int: 종료 숫자입니다
 * @param step Int: 숫자 간격입니다
 * @param modifier Modifier: 외형을 설정하는 Modifier입니다
 * @param itemList List<Int>: 표시할 숫자 리스트입니다. 기본값은 start 부터 end 까지 step 간격으로 만든 리스트이며, 직접 지정하면 start/end/step 으로 만든 목록을 대체합니다(start 는 selectedValue 기본값으로만 쓰입니다)
 * @param selectedValue Int: 기본 선택값입니다
 * @param enableMinValue Int: 선택 가능 최소값입니다
 * @param enableMaxValue Int: 선택 가능 최대값입니다. itemList 의 최댓값보다 크게 주면 첫 항목만 선택할 수 있게 되므로 itemList 범위 안에서 지정합니다.
 * @param pagerState PagerState: 스크롤 상태를 관리하는 Pager 상태입니다
 * @param textStyle TextStyle: 항목 높이(itemSize) 기본값을 계산할 때만 쓰입니다. 글자 스타일은 이 값과 무관하게 heading1Medium 으로 고정됩니다
 * @param itemSize Dp: 각 항목의 높이입니다
 * @param visibleCount Int: 화면에 표시될 항목 수입니다
 * @param userScrollEnabled Boolean: 사용자의 스크롤 가능 여부입니다. false 이면 스크롤이 막히고 모든 항목이 비활성 색으로 표시됩니다
 * @param onSelect (Int, Int, Boolean) -> Unit: 스크롤이 멈출 때(최초 표시 시점 포함) 호출되며 인자는 (선택된 항목의 인덱스, 그 항목의 숫자 값, 선택 가능 범위 안인지 여부) 입니다
 */
@Composable
fun WantedNumberPicker(
    start: Int,
    end: Int,
    step: Int,
    modifier: Modifier = Modifier,
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
    ) { itemList.size },
    textStyle: TextStyle = DesignSystemTheme.typography.heading1Medium.copy(
        color = DesignSystemTheme.colors.foregroundNeutralPrimary
    ),
    itemSize: Dp = with(LocalDensity.current) { textStyle.lineHeight.toDp() },
    visibleCount: Int = 5,
    userScrollEnabled: Boolean = true,
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
        userScrollEnabled = userScrollEnabled,
        onSelect = { index, enabled ->
            onSelect(index, itemList[index], enabled)
        }
    )
}

/**
 * WantedNumberPicker
 *
 * 숫자 리스트를 직접 전달하여 사용할 수 있는 NumberPicker 컴포넌트입니다.
 *
 * 전달된 리스트에서 인덱스를 기준으로 선택하며, 선택 상태는 콜백으로 전달됩니다.
 * 사용자는 직접 리스트를 생성하여 원하는 숫자 구성을 조정할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedNumberPicker(
 *     itemList = listOf(0, 15, 30, 45),
 *     selectedIndex = 2,
 *     onSelect = { index, enabled ->
 *         // index 처리
 *     }
 * )
 * ```
 *
 * @param modifier Modifier: 외형을 설정하는 Modifier입니다
 * @param itemList List<Int>: 표시할 숫자 리스트입니다
 * @param selectedIndex Int: 선택된 인덱스입니다
 * @param enableStartIndex Int: 선택 가능한 시작 인덱스입니다
 * @param enableEndIndex Int: 선택 가능한 종료 인덱스입니다
 * @param pagerState PagerState: 스크롤 상태를 관리하는 Pager 상태입니다
 * @param textStyle TextStyle: 항목 높이(itemSize) 기본값을 계산할 때만 쓰입니다. 글자 스타일은 이 값과 무관하게 heading1Medium 으로 고정됩니다
 * @param itemSize Dp: 각 항목의 높이입니다
 * @param visibleCount Int: 화면에 표시할 항목 개수입니다
 * @param onSelect (Int, Boolean) -> Unit: 스크롤이 멈출 때(최초 표시 시점 포함) 호출되며 인자는 (선택된 항목의 인덱스, enableStartIndex..enableEndIndex 범위 안인지 여부) 입니다. 숫자 값은 전달되지 않습니다
 */
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
    textStyle: TextStyle = DesignSystemTheme.typography.heading1Medium.copy(
        color = DesignSystemTheme.colors.foregroundNeutralPrimary
    ),
    itemSize: Dp = with(LocalDensity.current) { textStyle.lineHeight.toDp() },
    visibleCount: Int = 5,
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

/**
 * fun WantedStringPicker(...)
 *
 * 문자열 목록을 세로 휠로 보여 주고 가운데 항목을 선택하는 피커입니다. WantedNumberPicker 가 내부적으로 이 컴포넌트를 씁니다.
 *
 * enableStartIndex..enableEndIndex 밖의 항목도 휠에는 표시되지만 비활성 색(foregroundDisablePrimary)으로 그려지며, 그 위치에서 멈추면 onSelect 의 enabled 가 false 로 전달됩니다.
 *
 * @param itemList List<String>: 휠에 표시할 문자열 목록입니다.
 * @param modifier Modifier: 외형을 설정하는 Modifier입니다.
 * @param selectedIndex Int: 처음 선택할 항목의 인덱스입니다. 값이 바뀌면 그 위치로 스크롤합니다.
 * @param enableStartIndex Int: 선택 가능한 첫 인덱스입니다. 기본값은 0입니다.
 * @param enableEndIndex Int: 선택 가능한 마지막 인덱스입니다. 기본값은 itemList 의 마지막 인덱스입니다.
 * @param pagerState PagerState: 스크롤 상태를 관리하는 Pager 상태입니다.
 * @param textStyle TextStyle: 항목 높이(itemSize) 기본값을 계산할 때만 쓰입니다. 글자 스타일은 이 값과 무관하게 heading1Medium 으로 고정됩니다.
 * @param itemSize Dp: 각 항목의 높이입니다. 기본값은 textStyle 의 줄 높이입니다.
 * @param visibleCount Int: 한 번에 보이는 항목 수입니다. 기본값은 5입니다.
 * @param userScrollEnabled Boolean: 사용자의 스크롤 가능 여부입니다. false 이면 스크롤이 막히고 모든 항목이 비활성 색으로 표시됩니다.
 * @param onSelect (Int, Boolean) -> Unit: 스크롤이 멈출 때(최초 표시 시점 포함) (선택된 인덱스, 선택 가능 범위 안인지 여부) 로 호출됩니다.
 */
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun WantedStringPicker(
    itemList: List<String>,
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    enableStartIndex: Int = 0,
    enableEndIndex: Int = itemList.lastIndex,
    pagerState: PagerState = rememberPagerState(
        initialPage = selectedIndex,
        initialPageOffsetFraction = 0f
    ) { itemList.size },
    textStyle: TextStyle = DesignSystemTheme.typography.heading1Medium.copy(
        color = DesignSystemTheme.colors.foregroundNeutralPrimary
    ),
    itemSize: Dp = with(LocalDensity.current) { textStyle.lineHeight.toDp() },
    visibleCount: Int = 5,
    userScrollEnabled: Boolean = true,
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

    val haptic = LocalHapticFeedback.current
    LaunchedEffect(haptic, pagerState.currentPage) {
        snapshotFlow { pagerState.currentPage }.collect {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        }
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(itemSize * (visibleCount + 2)),
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
                    visibleCount = visibleCount + 2,
                    title = itemList.get(index = page),
                    enabled = if (userScrollEnabled) page in enableStartIndex..enableEndIndex else false
                )
            },
            userScrollEnabled = userScrollEnabled
        )
    }
}

@Composable
private fun PickerContent(
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
                if (visibleCount == 7) {
                    Log.d(
                        "_SMY",
                        "graphicsLayer 1 $GAP $page $halfVisibleCount $pageOffset $offsetY $rotation"
                    )
                }
            },
        text = title,
        textAlign = TextAlign.Center,
        style = DesignSystemTheme.typography.heading1Medium,
        color = when {
            !enabled -> DesignSystemTheme.colors.foregroundDisablePrimary
            pagerState.currentPage == page -> DesignSystemTheme.colors.foregroundNeutralPrimary
            else -> DesignSystemTheme.colors.foregroundNeutralQuaternary
        }
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