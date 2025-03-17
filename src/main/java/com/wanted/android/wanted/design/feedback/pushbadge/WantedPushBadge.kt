package com.wanted.android.wanted.design.feedback.pushbadge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.feedback.pushbadge.PushBadgeContract.PushBadgePosition
import com.wanted.android.wanted.design.feedback.pushbadge.PushBadgeContract.PushBadgeSize
import com.wanted.android.wanted.design.feedback.pushbadge.PushBadgeContract.PushBadgeVariant
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews


@Composable
fun WantedPushBadge(
    modifier: Modifier = Modifier,
    variant: PushBadgeVariant = PushBadgeVariant.Dot,
    size: PushBadgeSize = PushBadgeSize.XSmall,
    position: PushBadgePosition = PushBadgePosition.TopEnd,
    count: String = "",
    background: Color = colorResource(id = R.color.primary_normal),
    contentColor: Color = colorResource(id = R.color.static_white)
) {
    when (variant) {
        PushBadgeVariant.Dot -> {
            PushBadgeDot(
                modifier = modifier,
                size = size,
                position = position,
                background = background
            )
        }

        PushBadgeVariant.Number -> {
            PushBadgeImpl(
                modifier = modifier,
                text = count,
                size = size,
                position = position,
                background = background,
                contentColor = contentColor
            )
        }

        PushBadgeVariant.New -> {
            PushBadgeImpl(
                modifier = modifier,
                text = "N",
                size = size,
                position = position,
                background = background,
                contentColor = contentColor
            )
        }
    }
}

@Composable
private fun PushBadgeDot(
    modifier: Modifier = Modifier,
    size: PushBadgeSize,
    position: PushBadgePosition,
    background: Color
) {
    val density = LocalDensity.current
    Box(
        modifier = modifier
            .size(20.dp)
            .padding(
                when (size) {
                    PushBadgeSize.XSmall -> 8.dp
                    PushBadgeSize.Small -> 7.dp
                    PushBadgeSize.Medium -> 6.dp
                }
            )
            .offset {
                getOffset(density, position, 20.dp)
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(background, shape = CircleShape)
        )
    }
}

private fun getOffset(
    density: Density,
    position: PushBadgePosition,
    width: Dp,
    height: Dp = width
): IntOffset {
    val offsetX = with(density) {
        width.toPx().toInt() / 2
    }
    val offsetY = with(density) {
        height.toPx().toInt() / 2
    }

    return when (position) {
        PushBadgePosition.TopStart -> IntOffset(-offsetX, -offsetY)
        PushBadgePosition.TopCenter -> IntOffset(0, -offsetY)
        PushBadgePosition.TopEnd -> IntOffset(offsetX, -offsetY)
        PushBadgePosition.MiddleStart -> IntOffset(-offsetX, 0)
        PushBadgePosition.MiddleCenter -> IntOffset(0, 0)
        PushBadgePosition.MiddleEnd -> IntOffset(offsetX, 0)
        PushBadgePosition.BottomStart -> IntOffset(-offsetX, offsetY)
        PushBadgePosition.BottomCenter -> IntOffset(0, offsetY)
        PushBadgePosition.BottomEnd -> IntOffset(offsetX, offsetY)
    }
}

@Composable
private fun PushBadgeImpl(
    modifier: Modifier = Modifier,
    text: String,
    size: PushBadgeSize,
    position: PushBadgePosition,
    background: Color,
    contentColor: Color,
) {
    val density = LocalDensity.current
    val badgeSize = remember(size) { mutableStateOf(Pair(20.dp, 20.dp)) }

    Box(
        modifier = modifier
            .defaultMinSize(
                when (size) {
                    PushBadgeSize.XSmall -> 16.dp
                    PushBadgeSize.Small -> 20.dp
                    PushBadgeSize.Medium -> 24.dp
                }
            )
            .offset {
                getOffset(
                    density,
                    position,
                    badgeSize.value.first,
                    badgeSize.value.second
                )
            }
            .background(background, shape = CircleShape)
            .padding(
                when (size) {
                    PushBadgeSize.XSmall -> 1.dp
                    PushBadgeSize.Small -> 3.dp
                    PushBadgeSize.Medium -> 2.dp
                }
            )
            .onGloballyPositioned { coordinates ->
                with(density) {
                    badgeSize.value =
                        Pair(coordinates.size.width.toDp(), coordinates.size.height.toDp())
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = when (size) {
                PushBadgeSize.XSmall -> DesignSystemTheme.typography.caption2Bold
                PushBadgeSize.Small -> DesignSystemTheme.typography.caption2Bold
                PushBadgeSize.Medium -> DesignSystemTheme.typography.label1Bold
            },
            color = contentColor
        )
    }
}


@DevicePreviews
@Composable
private fun WantedPushBadgePreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Row {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    PushBadgePosition.entries.forEach {
                        Box(modifier = Modifier.background(Color.Gray)) {
                            WantedPushBadge(
                                modifier = Modifier,
                                position = it
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    PushBadgePosition.entries.forEach {
                        Box(modifier = Modifier.background(Color.Gray)) {
                            WantedPushBadge(
                                modifier = Modifier,
                                variant = PushBadgeVariant.Number,
                                position = it,
                                count = "1"
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {

                    PushBadgePosition.entries.forEach {
                        Box(modifier = Modifier.background(Color.Gray)) {
                            WantedPushBadge(
                                modifier = Modifier,
                                position = it,
                                variant = PushBadgeVariant.New
                            )
                        }
                    }
                }
            }
        }
    }
}