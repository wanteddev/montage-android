package com.wanted.android.wanted.design.navigations.tab

import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.wanted.android.wanted.design.input.input.control.CheckBoxSize
import com.wanted.android.wanted.design.input.input.control.WantedCheckBox
import com.wanted.android.wanted.design.navigations.tab.WantedTabDefaults.TabSize
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * WantedScrollableTabRow
 *
 * 스크롤할 수 있는 Tab 레이아웃 컴포넌트입니다
 *
 * 좌우 그라디언트 효과, 우측 아이콘을 지원하며, 선택된 Tab은 하단 Indicator로 강조합니다.
 *
 * 사용 예시:
 * ```kotlin
 * var selectedIndex by remember { mutableIntStateOf(0) }
 *
 * WantedScrollableTabRow(
 *     itemCount = 5,
 *     selectedTabIndex = selectedIndex,
 *     content = { index -> "탭$index" },
 *     onClickItem = { selectedIndex = it }
 * )
 * ```
 *
 * @param itemCount Int: 탭 항목 수입니다.
 * @param selectedTabIndex Int: 현재 선택된 탭 인덱스입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param disableIndexList List<Int>: 비활성화할 탭 인덱스 리스트입니다.
 * @param tabSize TabSize: 탭 크기 설정입니다.
 * @param horizontalPadding Boolean: 양쪽 여백 적용 여부입니다.
 * @param isLeftGradient Boolean: 왼쪽 gradient 표시 여부입니다.
 * @param isRightGradient Boolean: 오른쪽 gradient 표시 여부입니다.
 * @param gradientColor Color: gradient 색상입니다.
 * @param scrollState ScrollState: 스크롤 상태를 관리하는 객체입니다.
 * @param onClickItem (Int) -> Unit: 탭 클릭 시 호출되는 콜백입니다.
 * @param rightIcon (@Composable (Dp) -> Unit)?: 탭 우측에 추가할 아이콘 슬롯입니다.
 * @param content (Int) -> String: 각 탭의 텍스트를 반환하는 함수입니다.
 */
@Composable
fun WantedScrollableTabRow(
    itemCount: Int,
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    disableIndexList: List<Int> = emptyList(),
    tabSize: TabSize = TabSize.Medium,
    horizontalPadding: Boolean = false,
    isLeftGradient: Boolean = false,
    isRightGradient: Boolean = false,
    gradientColor: Color = DesignSystemTheme.colors.backgroundNormalNormal,
    scrollState: ScrollState = rememberScrollState(),
    onClickItem: (index: Int) -> Unit = {},
    rightIcon: @Composable ((Dp) -> Unit)? = null,
    content: (index: Int) -> String
) {
    CompositionLocalProvider(LocalTabGradationColor provides gradientColor) {
        WantedTabLayout(
            modifier = modifier,
            tabSize = tabSize,
            isLeftGradient = isLeftGradient && scrollState.canScrollBackward,
            isRightGradient = isRightGradient && scrollState.canScrollForward,
            tab = {
                WantedScrollableFlexTabRow(
                    modifier = Modifier,
                    tabSize = tabSize,
                    scrollState = scrollState,
                    itemCount = itemCount,
                    disableIndexList = disableIndexList,
                    selectedTabIndex = selectedTabIndex,
                    horizontalPadding = horizontalPadding,
                    content = content,
                    onClickItem = onClickItem
                )
            },
            rightIcon = rightIcon
        )
    }
}

@Composable
private fun WantedScrollableFlexTabRow(
    tabSize: TabSize,
    itemCount: Int,
    selectedTabIndex: Int,
    disableIndexList: List<Int>,
    horizontalPadding: Boolean,
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
    onClickItem: (index: Int) -> Unit = {},
    content: (index: Int) -> String
) {
    val density = LocalDensity.current
    val tabWidths = remember(itemCount) {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(itemCount) {
            tabWidthStateList.add(0.dp)
        }
        tabWidthStateList
    }


    ScrollableFlexTabRow(
        modifier = modifier,
        scrollState = scrollState,
        selectedTabProvider = { selectedTabIndex },
        dividerFitTab = false,
        edgePadding = if (horizontalPadding) 20.dp else 0.dp,
        minItemWidth = 32.dp,
        contentColor = DesignSystemTheme.colors.transparent,
        containerColor = DesignSystemTheme.colors.transparent,
        horizontalArrange = 24.dp,
        indicator = { tabPositions ->
            val tabPosition = tabPositions.getOrNull(selectedTabIndex)
            tabPosition?.let {
                SecondaryIndicator(
                    modifier = Modifier.customTabIndicatorOffset(
                        currentTabPosition = tabPosition,
                        tabWidth = tabWidths[selectedTabIndex],
                    ),
                    height = 2.dp,
                    color = DesignSystemTheme.colors.labelStrong
                )
            }
        },
        divider = {}
    ) {
        for (index in 0 until itemCount) {
            WantedTabItem(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .wrapContentSize(),
                tabSize = tabSize,
                title = content(index),
                active = selectedTabIndex == index,
                enable = disableIndexList.contains(index).not(),
                onTextLayout = { layoutCoordinates ->
                    tabWidths[index] = with(density) { layoutCoordinates.size.width.toDp() }
                },
                onClick = {
                    onClickItem(index)
                }
            )
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun WantedTabLayout(
    tabSize: TabSize,
    isLeftGradient: Boolean,
    isRightGradient: Boolean,
    modifier: Modifier = Modifier,
    rightIcon: @Composable ((Dp) -> Unit)? = null,
    tab: @Composable () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        HorizontalDivider(
            modifier = Modifier.align(Alignment.BottomStart),
            color = DesignSystemTheme.colors.lineNormalAlternative
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ConstraintLayout(
                modifier = Modifier.weight(1f)
            ) {
                val (tabRef, leftGradientRef, rightGradientRef) = createRefs()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .constrainAs(tabRef) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    tab()
                }

                if (isLeftGradient) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 1.dp)
                            .constrainAs(leftGradientRef) {
                                top.linkTo(tabRef.top)
                                bottom.linkTo(tabRef.bottom)
                                start.linkTo(tabRef.start)
                                height = Dimension.fillToConstraints
                            }
                            .width(48.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        LocalTabGradationColor.current,
                                        DesignSystemTheme.colors.transparent
                                    )
                                )
                            )
                    )
                }

                if (isRightGradient) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 1.dp)
                            .constrainAs(rightGradientRef) {
                                top.linkTo(tabRef.top)
                                bottom.linkTo(tabRef.bottom)
                                end.linkTo(tabRef.end)
                                height = Dimension.fillToConstraints
                            }
                            .width(48.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        DesignSystemTheme.colors.transparent,
                                        LocalTabGradationColor.current
                                    )
                                )
                            )
                    )
                }
            }

            rightIcon?.let {
                BoxWithConstraints(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 8.dp)
                        .size(
                            when (tabSize) {
                                TabSize.Small -> 20.dp
                                TabSize.Medium -> 22.dp
                                else -> 24.dp
                            }
                        )
                ) {
                    rightIcon(maxHeight)
                }
            }
        }
    }
}


private fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition,
    tabWidth: Dp
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "customTabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val currentTabWidth by animateDpAsState(
        targetValue = tabWidth,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow), label = ""
    )
    val indicatorOffset by animateDpAsState(
        targetValue = ((currentTabPosition.left + currentTabPosition.right - tabWidth) / 2),
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow), label = ""
    )

    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart) // indicator 표시 위치
        .offset(x = indicatorOffset)
        .width(currentTabWidth)
}


@DevicePreviews
@Composable
private fun WantedScrollableTabRowPreview() {
    DesignSystemTheme {
        val itemList = remember {
            val items = mutableListOf<String>()
            for (index in 0..<10) {
                items.add("텍스트${index + 1}")
            }
            items
        }
        Scaffold {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedScrollableTabRow(
                    modifier = Modifier,
                    selectedTabIndex = 1,
                    itemCount = itemList.size,
                    content = { index ->
                        itemList[index]
                    },
                    horizontalPadding = false,
                    onClickItem = {}
                )

                WantedScrollableTabRow(
                    modifier = Modifier,
                    tabSize = TabSize.Small,
                    selectedTabIndex = 1,
                    itemCount = itemList.size,
                    content = { index ->
                        itemList[index]
                    },
                    horizontalPadding = false,
                    onClickItem = {}
                )


                WantedScrollableTabRow(
                    modifier = Modifier,
                    selectedTabIndex = 1,
                    itemCount = itemList.size,
                    content = { index ->
                        itemList[index]
                    },
                    horizontalPadding = true,
                    onClickItem = {}
                )

                WantedScrollableTabRow(
                    modifier = Modifier,
                    selectedTabIndex = 1,
                    itemCount = itemList.size,
                    content = { index ->
                        itemList[index]
                    },
                    horizontalPadding = false,
                    isRightGradient = true,
                    isLeftGradient = true,
                    onClickItem = {},
                    rightIcon = {
                        WantedCheckBox(
                            modifier = Modifier,
                            size = CheckBoxSize.Normal,
                            onCheckedChange = {}
                        )
                    }
                )
            }
        }
    }
}