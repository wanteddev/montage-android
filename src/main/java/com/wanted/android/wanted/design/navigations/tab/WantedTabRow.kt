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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * 고정형 탭 레이아웃을 제공하는 컴포저블입니다.
 *
 * 항목 수만큼 `WantedTabItem`을 구성하여 Material3의 TabRow와 함께 사용합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedTabRow(
 *     itemSize = 3,
 *     selectedTabIndex = 1,
 *     content = { index -> "탭$index" },
 *     onClickItem = { index -> ... }
 * )
 * ```
 *
 * @param itemSize Int: 탭 항목 수입니다.
 * @param selectedTabIndex Int: 현재 선택된 탭 인덱스입니다.
 * @param modifier Modifier: 레이아웃 설정용 Modifier입니다.
 * @param tabSize TabSize: 탭 텍스트 스타일 크기입니다.
 * @param onClickItem (index: Int) -> Unit: 탭 클릭 시 호출되는 콜백입니다.
 * @param content (index: Int) -> String: 탭에 표시할 텍스트를 반환하는 함수입니다.
 */
@Composable
fun WantedTabRow(
    itemSize: Int,
    selectedTabIndex: Int,
    modifier: Modifier,
    tabSize: WantedTabContract.TabSize = WantedTabContract.TabSize.Medium,
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
                        colorResource(id = R.color.fill_alternative)
                    } else {
                        colorResource(id = R.color.label_strong)
                    }

                )
            }
        },
        divider = {
            HorizontalDivider(color = colorResource(R.color.line_normal_alternative))
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
                    tabSize = WantedTabContract.TabSize.Small,
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