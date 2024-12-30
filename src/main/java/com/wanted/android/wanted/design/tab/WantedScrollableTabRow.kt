package com.wanted.android.wanted.design.tab

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.element.CheckBoxSize
import com.wanted.android.wanted.design.element.WantedCheckBox
import com.wanted.android.wanted.design.theme.DesignSystemTheme

@Composable
fun WantedScrollableTabRow(
    modifier: Modifier,
    tabSize: WantedTabContract.TabSize = WantedTabContract.TabSize.Medium,
    itemCount: Int,
    selectedTabIndex: Int,
    padding: Boolean = false,
    isLeftGradation: Boolean = false,
    isRightGradation: Boolean = false,
    gradientColor: Color = colorResource(R.color.background_normal_normal),
    scrollState: ScrollState = rememberScrollState(),
    content: (index: Int) -> String,
    onClickItem: (index: Int) -> Unit = {},
    rightIcon: @Composable (() -> Unit)? = null
) {
    CompositionLocalProvider(LocalTabGradationColor provides gradientColor) {
        WantedTabLayout(
            modifier = modifier,
            isLeftGradation = isLeftGradation && scrollState.canScrollBackward,
            isRightGradation = isRightGradation && scrollState.canScrollForward,
            tab = {
                WantedScrollableFlexTabRow(
                    modifier = Modifier,
                    tabSize = tabSize,
                    scrollState = scrollState,
                    itemCount = itemCount,
                    selectedTabIndex = selectedTabIndex,
                    padding = padding,
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
    modifier: Modifier = Modifier,
    tabSize: WantedTabContract.TabSize,
    itemCount: Int,
    selectedTabIndex: Int,
    padding: Boolean,
    scrollState: ScrollState,
    content: (index: Int) -> String,
    onClickItem: (index: Int) -> Unit = {},
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
        edgePadding = if (padding) 20.dp else 0.dp,
        minItemWidth = 32.dp,
        contentColor = colorResource(id = R.color.transparent),
        containerColor = colorResource(id = R.color.transparent),
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
                    color = colorResource(id = R.color.label_strong)
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
                isSelect = selectedTabIndex == index,
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

@Composable
private fun WantedTabLayout(
    modifier: Modifier = Modifier,
    isLeftGradation: Boolean,
    isRightGradation: Boolean,
    tab: @Composable () -> Unit,
    rightIcon: @Composable (() -> Unit)?
) {
    Box(
        modifier = modifier
    ) {
        HorizontalDivider(
            modifier = Modifier.align(Alignment.BottomStart),
            color = colorResource(R.color.line_normal_alternative)
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

                if (isLeftGradation) {
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
                                        colorResource(id = android.R.color.transparent)
                                    )
                                )
                            )
                    )
                }

                if (isRightGradation) {
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
                                        colorResource(id = android.R.color.transparent),
                                        LocalTabGradationColor.current
                                    )
                                )
                            )
                    )
                }
            }

            rightIcon?.let {
                Box(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 8.dp)
                ) {
                    rightIcon()
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
                    padding = false,
                    onClickItem = {}
                )

                WantedScrollableTabRow(
                    modifier = Modifier,
                    tabSize = WantedTabContract.TabSize.Small,
                    selectedTabIndex = 1,
                    itemCount = itemList.size,
                    content = { index ->
                        itemList[index]
                    },
                    padding = false,
                    onClickItem = {}
                )


                WantedScrollableTabRow(
                    modifier = Modifier,
                    selectedTabIndex = 1,
                    itemCount = itemList.size,
                    content = { index ->
                        itemList[index]
                    },
                    padding = true,
                    onClickItem = {}
                )

                WantedScrollableTabRow(
                    modifier = Modifier,
                    selectedTabIndex = 1,
                    itemCount = itemList.size,
                    content = { index ->
                        itemList[index]
                    },
                    padding = false,
                    isRightGradation = true,
                    isLeftGradation = true,
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