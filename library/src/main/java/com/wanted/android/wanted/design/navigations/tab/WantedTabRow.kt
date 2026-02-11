package com.wanted.android.wanted.design.navigations.tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * WantedTabRow
 *
 * 고정형 Tab 레이아웃 컴포넌트입니다.
 *
 * Tab 항목들이 전체 너비에 균등하게 배치되며, 선택된 Tab은 하단 Indicator로 강조합니다.
 *
 * 사용 예시:
 * ```kotlin
 * var selectedIndex by remember { mutableIntStateOf(0) }
 *
 * WantedTabRow(
 *     itemSize = 3,
 *     selectedTabIndex = selectedIndex,
 *     content = { index -> "탭$index" },
 *     onClickItem = { selectedIndex = it }
 * )
 * ```
 *
 * @param itemSize Int: 탭 항목 수입니다.
 * @param selectedTabIndex Int: 현재 선택된 탭 인덱스입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param tabSize TabSize: 탭 텍스트 크기입니다.
 * @param disableIndexList List<Int>: 비활성화할 탭 인덱스 리스트입니다.
 * @param onClickItem (Int) -> Unit: 탭 클릭 시 호출되는 콜백입니다.
 * @param content (Int) -> String: 각 탭의 텍스트를 반환하는 함수입니다.
 */
@Composable
fun WantedTabRow(
    itemSize: Int,
    selectedTabIndex: Int,
    modifier: Modifier,
    tabSize: WantedTabDefaults.TabSize = WantedTabDefaults.TabSize.Medium,
    disableIndexList: List<Int> = emptyList(),
    onClickItem: (index: Int) -> Unit = {},
    content: (index: Int) -> String,
) {

    TabRow(
        modifier = modifier,
        selectedTabIndex = selectedTabIndex,
        indicator = { tabPositions ->
            if (selectedTabIndex < tabPositions.size) {
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    height = 1.dp,
                    color = if (disableIndexList.contains(selectedTabIndex)) {
                        DesignSystemTheme.colors.fillAlternative
                    } else {
                        DesignSystemTheme.colors.labelStrong
                    }

                )
            }
        },
        divider = {
            HorizontalDivider(color = DesignSystemTheme.colors.lineNormalAlternative)
        },
        tabs = {
            for (index in 0 until itemSize) {
                WantedTabItem(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .wrapContentSize(),
                    tabSize = tabSize,
                    title = content(index),
                    enable = !disableIndexList.contains(index),
                    active = selectedTabIndex == index,
                    onClick = {
                        onClickItem(index)
                    }
                )
            }
        }
    )
}


@DevicePreviews
@Composable
private fun WantedTabPreview() {
    DesignSystemTheme {
        val itemList = remember {
            val items = mutableListOf<String>()
            for (index in 0..<3) {
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
                WantedTabRow(
                    modifier = Modifier,
                    selectedTabIndex = 1,
                    itemSize = itemList.size,
                    disableIndexList = listOf(1),
                    content = { index ->
                        itemList[index]
                    },
                    onClickItem = {}
                )

                WantedTabRow(
                    modifier = Modifier,
                    tabSize = WantedTabDefaults.TabSize.Small,
                    selectedTabIndex = 1,
                    disableIndexList = listOf(0),
                    itemSize = itemList.size,
                    content = { index ->
                        itemList[index]
                    },
                    onClickItem = {}
                )


                WantedTabRow(
                    modifier = Modifier,
                    selectedTabIndex = 1,
                    itemSize = itemList.size,
                    content = { index ->
                        itemList[index]
                    },
                    onClickItem = {}
                )

                WantedTabRow(
                    modifier = Modifier,
                    selectedTabIndex = 1,
                    itemSize = itemList.size,
                    content = { index ->
                        itemList[index]
                    },
                    onClickItem = {}
                )
            }
        }
    }
}