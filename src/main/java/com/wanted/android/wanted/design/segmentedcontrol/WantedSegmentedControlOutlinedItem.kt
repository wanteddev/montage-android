package com.wanted.android.wanted.design.segmentedcontrol

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.segmentedcontrol.WantedSegmentedContract.SegmentedSize
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.OPACITY_5
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
fun WantedSegmentedControlOutlinedItem(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean,
    isFirst: Boolean = false,
    isLast: Boolean = false,
    icon: @Composable (() -> Unit)? = null
) {
    val textColor = remember(isSelected) {
        mutableIntStateOf(
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
        value = LocalContentColor provides colorResource(id = textColor.intValue)
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
                .padding(
                    vertical = when (LocalWantedSegmentedSize.current) {
                        SegmentedSize.Small -> 7.dp
                        SegmentedSize.Medium -> 9.dp
                        SegmentedSize.Large -> 12.dp
                    },
                    horizontal = when (LocalWantedSegmentedSize.current) {
                        SegmentedSize.Small -> 6.dp
                        SegmentedSize.Medium -> 8.dp
                        SegmentedSize.Large -> 8.dp
                    }
                )
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
                    style = when (LocalWantedSegmentedSize.current) {
                        SegmentedSize.Small -> DesignSystemTheme.typography.label2Medium
                        SegmentedSize.Medium -> DesignSystemTheme.typography.body2Medium
                        SegmentedSize.Large -> DesignSystemTheme.typography.headline2Medium
                    }
                )
            )
        }

    }
}

@DevicePreviews
@Composable
private fun WantedSegmentedControlOutlinedItemPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedSegmentedControlOutlinedItem(
                    modifier = Modifier,
                    title = "title",
                    isSelected = true
                )

                WantedSegmentedControlOutlinedItem(
                    modifier = Modifier,
                    title = "title",
                    isSelected = false
                )
            }
        }
    }
}