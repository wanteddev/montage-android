package com.wanted.android.wanted.design.input.segmentedcontrol

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.clickOnce


/**
 * WantedSegmentedControl
 *
 * 텍스트 리스트를 기반으로 하는 Segmented Control 컴포넌트입니다.
 *
 * 아이템을 구분하여 선택할 수 있으며, 선택된 항목의 인덱스를 콜백으로 전달합니다.
 * 내부적으로 WantedSegmentedControlOutlinedItem을 사용하며, 선택 상태에 따라 스타일이 변경됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * val items = listOf("One", "Two", "Three")
 * var selectedIndex by remember { mutableIntStateOf(0) }
 *
 * WantedSegmentedControlOutlined(
 *     items = items,
 *     selectedIndex = selectedIndex,
 *     onClick = { selectedIndex = it }
 * )
 * ```
 *
 * @param items List<String>: 항목에 표시할 텍스트 리스트입니다.
 * @param selectedIndex Int: 선택된 항목의 인덱스입니다.
 * @param modifier Modifier: 외형을 설정하는 Modifier입니다.
 * @param size WantedSegmentedDefaults.SegmentedSize: 세그먼트의 크기입니다 (Small, Medium, Large).
 * @param onClick (Int) -> Unit: 항목 클릭 시 선택된 인덱스를 반환하는 콜백입니다.
 */
@Composable
fun WantedSegmentedControlOutlined(
    items: List<String>,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    size: WantedSegmentedDefaults.SegmentedSize = WantedSegmentedDefaults.SegmentedSize.Medium,
    onClick: (Int) -> Unit = {},
) {
    CompositionLocalProvider(LocalWantedSegmentedSize.provides(size)) {
        WantedSegmentedControlOutlined(
            modifier = modifier,
            size = size,
            itemCount = items.size,
            onClick = onClick,
            item = { index ->
                WantedSegmentedControlOutlinedItem(
                    modifier = Modifier.fillMaxWidth(),
                    title = items[index],
                    isSelected = index == selectedIndex,
                    isFirst = index == 0,
                    isLast = index == items.lastIndex
                )
            }
        )
    }
}


/**
 * WantedSegmentedControl
 *
 * 사용자 정의 Slot 방식으로 구성할 수 있는 Segmented Control 컴포넌트입니다.
 *
 * 항목 수와 개별 항목을 입력받아 세그먼트를 구성하며,
 * 클릭 시 콜백을 통해 선택된 인덱스를 반환합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedSegmentedControlOutlined(
 *     itemCount = 3,
 *     item = { index ->
 *         WantedSegmentedControlOutlinedItem(
 *             title = "Item $index",
 *             isSelected = selectedIndex == index
 *         )
 *     },
 *     onClick = { index -> selectedIndex = index }
 * )
 * ```
 *
 * @param itemCount Int: 표시할 항목의 수입니다.
 * @param item (Int) -> Unit: 각 인덱스에 대응하는 항목 Composable 슬롯입니다.
 * @param modifier Modifier: 외형을 설정하는 Modifier입니다.
 * @param size WantedSegmentedDefaults.SegmentedSize: 세그먼트 크기 설정입니다.
 * @param onClick (Int) -> Unit: 항목 클릭 시 호출되는 콜백입니다.
 */
@Composable
fun WantedSegmentedControlOutlined(
    itemCount: Int,
    item: @Composable (index: Int) -> Unit,
    modifier: Modifier = Modifier,
    size: WantedSegmentedDefaults.SegmentedSize = WantedSegmentedDefaults.SegmentedSize.Medium,
    onClick: (index: Int) -> Unit = {},
) {
    CompositionLocalProvider(LocalWantedSegmentedSize.provides(size)) {
        Row(
            modifier = modifier
                .height(IntrinsicSize.Min)
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(10.dp),
                    color = DesignSystemTheme.colors.lineNormalNormal
                ),
            horizontalArrangement = Arrangement.spacedBy(-(1).dp)
        ) {
            repeat(itemCount) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickOnce(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            onClick(index)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    item(index)
                }

                when (index) {
                    itemCount - 1 -> Unit
                    else -> {
                        VerticalDivider(
                            color = DesignSystemTheme.colors.lineNormalNormal
                        )
                    }
                }
            }
        }
    }
}


@DevicePreviews
@Composable
private fun WantedSegmentedControlSolidPreview() {
    DesignSystemTheme {
        val items = remember {
            val items = mutableListOf<String>()
            for (index in 0..<3) {
                items.add("텍스트${index + 1}")
            }
            items
        }

        var selectedIndex by remember { mutableIntStateOf(0) }

        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedSegmentedControlOutlined(
                    modifier = Modifier,
                    items = items,
                    selectedIndex = selectedIndex,
                    onClick = {
                        selectedIndex = it
                    }
                )

                WantedSegmentedControlOutlined(
                    modifier = Modifier,
                    itemCount = items.size,
                    onClick = {
                        selectedIndex = it
                    },
                    item = { index ->
                        WantedSegmentedControlOutlinedItem(
                            modifier = Modifier.fillMaxWidth(),
                            title = items[index],
                            isSelected = index == selectedIndex,
                            isFirst = index == 0,
                            isLast = index == items.lastIndex,
                            icon = {
                                Icon(
                                    modifier = Modifier.fillMaxSize(),
                                    painter = painterResource(id = R.drawable.icon_normal_circle_exclamation_fill),
                                    contentDescription = ""
                                )
                            }
                        )
                    }
                )
            }
        }
    }
}