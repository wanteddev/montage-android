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
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.theme.DesignSystemTheme

@Composable
fun WantedTabRow(
    modifier: Modifier,
    tabSize: WantedTabContract.TabSize = WantedTabContract.TabSize.Medium,
    itemSize: Int,
    selectedTabIndex: Int,
    content: (index: Int) -> String,
    onClickItem: (index: Int) -> Unit = {}
) {

    TabRow(
        modifier = modifier,
        selectedTabIndex = selectedTabIndex,
        indicator = { tabPositions ->
            if (selectedTabIndex < tabPositions.size) {
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    height = 1.dp,
                    color = colorResource(id = R.color.label_strong)
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
                    isSelect = selectedTabIndex == index,
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
        Scaffold() {
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
                    content = { index ->
                        itemList[index]
                    },
                    onClickItem = {}
                )

                WantedTabRow(
                    modifier = Modifier,
                    tabSize = WantedTabContract.TabSize.Small,
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