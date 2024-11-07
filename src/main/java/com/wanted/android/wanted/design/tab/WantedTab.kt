package com.wanted.android.wanted.design.tab

import android.content.res.Configuration
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
fun WantedTab(
    modifier: Modifier,
    selectedTabIndex: Int,
    itemSize: Int,
    padding: Boolean,
    onClickItem: (index: Int) -> Unit
) {
    val density = LocalDensity.current
    val tabWidths = remember(itemSize) {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(itemSize) {
            tabWidthStateList.add(0.dp)
        }
        tabWidthStateList
    }

    ScrollableFlexTabRow(
        modifier = modifier,
        selectedTabProvider = { selectedTabIndex },
        dividerFitTab = false,
        edgePadding = 8.dp,
        minItemWidth = 32.dp,
        contentColor = colorResource(id = R.color.transparent),
        containerColor = colorResource(id = R.color.transparent),
        indicator = { tabPositions ->
            val tabPosition = tabPositions.getOrNull(selectedTabIndex)
            tabPosition?.let {
                SecondaryIndicator(
                    modifier = Modifier.customTabIndicatorOffset(
                        currentTabPosition = tabPosition,
                        tabWidth = tabWidths[selectedTabIndex],
                    ),
                    height = 2.dp,
                    color = colorResource(id = R.color.label_normal)
                )
            }
        },
        divider = {
            HorizontalDivider(color = colorResource(id = R.color.line_normal_alternative))
        }
    ) {
        for (index in 0 until itemSize) {
            WantedTabItem(
                modifier = Modifier
                    .padding(bottom = 2.dp)
                    .clickOnceForDesignSystem {}
                    .padding(10.dp)
                    .padding(horizontal = 2.dp)
                    .wrapContentSize(),
                title = tag.name,
                isSelect = selectedTabIndex == index,
                onTextLayout = { layoutCoordinates ->
                    tabWidths[index] = with(density) { layoutCoordinates.size.width.toDp() }
                }
            )
        }
    }
}


@Composable
private fun WantedTabItem(
    modifier: Modifier = Modifier,
    title: String,
    isSelect: Boolean,
    onTextLayout: ((TextLayoutResult) -> Unit)
) {
    Text(
        modifier = modifier,
        text = title,
        style = WantedTextStyle(
            colorRes = if (isSelect) {
                R.color.label_normal
            } else {
                R.color.interaction_inactive
            },
            style = DesignSystemTheme.typography.body1Bold
        ),
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        onTextLayout = { textLayoutResult ->
            onTextLayout(textLayoutResult)
        }
    )
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


@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Preview(
    "foldableLight",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "ko",
    device = Devices.FOLDABLE
)
@Composable
private fun WantedTabPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedTab(
                    modifier = Modifier
                )
            }
        }
    }
}