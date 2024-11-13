package com.wanted.android.wanted.design.segmentcontrol

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
fun WantedSegmentControlOutlined(
    modifier: Modifier,
    items: List<String>,
    selectedIndex: Int,
    onClick: (index: Int) -> Unit = {}
) {
    WantedSegmentControlOutlined(
        modifier = modifier,
        itemCount = items.size,
        onClick = onClick,
        selectedIndex = selectedIndex,
        item = { index ->
            WantedSegmentControlOutlinedItem(
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
fun WantedSegmentControlOutlined(
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

@Composable
fun WantedSegmentControlOutlinedItem(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean,
    isFirst: Boolean = false,
    isLast: Boolean = false,
    icon: @Composable (() -> Unit)? = null
) {

    val textColor = remember {
        mutableStateOf(
            if (isSelected) {
                R.color.primary_normal
            } else {
                R.color.label_alternative
            }
        )
    }
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) {
            colorResource(id = R.color.primary_normal).copy(alpha = OPACITY_5)
        } else {
            colorResource(id = R.color.transparent)
        },
        animationSpec = tween(durationMillis = 500),
        label = ""
    )

    val borderColor by animateColorAsState(
        targetValue = if (isSelected) {
            colorResource(id = R.color.primary_normal).copy(alpha = OPACITY_43)
        } else {
            colorResource(id = R.color.transparent)
        },
        animationSpec = tween(durationMillis = 500),
        label = ""
    )


    CompositionLocalProvider(
        value = LocalContentColor provides colorResource(id = textColor.value)
    ) {
        Row(
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = when {
                        isFirst -> RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)
                        isLast -> RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
                        else -> RoundedCornerShape(0.dp)
                    }
                )
                .background(
                    color = backgroundColor,
                    shape = when {
                        isFirst -> RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)
                        isLast -> RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
                        else -> RoundedCornerShape(0.dp)
                    }
                )
                .padding(vertical = 12.dp, horizontal = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                space = 4.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Box(modifier = Modifier.size(20.dp)) {
                    icon()
                }
            }

            Text(
                modifier = Modifier.wrapContentSize(),
                text = title,
                textAlign = TextAlign.Center,
                maxLines = 1,
                style = WantedTextStyle(
                    colorRes = if (isSelected) {
                        R.color.primary_normal
                    } else {
                        R.color.label_alternative
                    },
                    style = DesignSystemTheme.typography.headline2Medium
                )
            )
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
private fun WantedSegmentControlSolidPreview() {
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
                WantedSegmentControlOutlined(
                    modifier = Modifier,
                    items = items,
                    selectedIndex = selectedIndex,
                    onClick = {
                        selectedIndex = it
                    }
                )

                WantedSegmentControlOutlined(
                    modifier = Modifier,
                    itemCount = items.size,
                    selectedIndex = selectedIndex,
                    onClick = {
                        selectedIndex = it
                    },
                    item = { index ->
                        WantedSegmentControlOutlinedItem(
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