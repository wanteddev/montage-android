package com.wanted.android.wanted.design.segmentedcontrol

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.OPACITY_5
import com.wanted.android.wanted.design.util.WantedTextStyle


/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=22610-72534&m=dev
 *
 * icon의 색상은 text color와 동일하게 적용된다.
 * tint를 적용하면 tint color가 적용된다.
 */
@Composable
fun WantedSegmentedControlOutlined(
    modifier: Modifier,
    items: List<String>,
    selectedIndex: Int,
    onClick: (index: Int) -> Unit = {}
) {
    WantedSegmentedControlOutlined(
        modifier = modifier,
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

@Composable
fun WantedSegmentedControlOutlined(
    modifier: Modifier,
    itemCount: Int,
    selectedIndex: Int,
    item: @Composable (index: Int) -> Unit,
    onClick: (index: Int) -> Unit = {}
) {
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
                selectedIndex -> Unit
                selectedIndex - 1 -> Unit
                else -> {
                    VerticalDivider(
                        color = colorResource(id = R.color.line_normal_normal)
                    )
                }
            }
        }
    }
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