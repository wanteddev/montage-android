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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.actions.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.theme.DesignSystemTheme


/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=22610-72534&m=dev
 *
 * icon의 색상은 text color와 동일하게 적용된다.
 * tint를 적용하면 tint color가 적용된다.
 */
@Composable
fun WantedSegmentedControlOutlined(
    modifier: Modifier,
    size: WantedSegmentedContract.SegmentedSize = WantedSegmentedContract.SegmentedSize.Medium,
    items: List<String>,
    selectedIndex: Int,
    onClick: (index: Int) -> Unit = {}
) {
    CompositionLocalProvider(LocalWantedSegmentedSize.provides(size)) {
        WantedSegmentedControlOutlined(
            modifier = modifier,
            size = size,
            itemCount = items.size,
            onClick = onClick,
            selectedIndex = selectedIndex,
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

@Composable
fun WantedSegmentedControlOutlined(
    modifier: Modifier,
    size: WantedSegmentedContract.SegmentedSize = WantedSegmentedContract.SegmentedSize.Medium,
    itemCount: Int,
    selectedIndex: Int,
    item: @Composable (index: Int) -> Unit,
    onClick: (index: Int) -> Unit = {}
) {
    CompositionLocalProvider(LocalWantedSegmentedSize.provides(size)) {
        Row(
            modifier = modifier
                .height(IntrinsicSize.Min)
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(10.dp),
                    color = colorResource(id = R.color.line_normal_normal)
                ),
            horizontalArrangement = Arrangement.spacedBy(-(1).dp)
        ) {
            repeat(itemCount) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickOnceForDesignSystem(
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
                            color = colorResource(id = R.color.line_normal_normal)
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
                    selectedIndex = selectedIndex,
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
                                    painter = painterResource(id = R.drawable.ic_normal_circle_exclamation_fill_svg),
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