package com.wanted.android.wanted.design.input.slider

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
internal fun WantedRangeSlider(
    value: ClosedFloatingPointRange<Float>,
    valueRange: ClosedFloatingPointRange<Float>,
    thumbSize: Dp,
    trackHeight: Dp,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    onValueChangeFinished: (ClosedFloatingPointRange<Float>) -> Unit,
    modifier: Modifier = Modifier,
    scope: CoroutineScope = rememberCoroutineScope(),
    enabled: Boolean = true,
    active: Boolean = true,
    colors: SliderColors = SliderDefaults.colors(),
    isRange: Boolean = false
) {
    val density = LocalDensity.current
    val totalStep =
        remember(valueRange) { mutableFloatStateOf(valueRange.endInclusive - valueRange.start) }
    val isDragging = remember { mutableStateOf(false) }

    val rawOffsetLeft = remember { mutableFloatStateOf(0f) }
    val rawOffsetRight = remember { mutableFloatStateOf(0f) }
    val rightThumbZIndex = remember { mutableFloatStateOf(1f) }
    val leftThumbZIndex = remember { mutableFloatStateOf(1f) }

    LaunchedEffect(key1 = value, isDragging) {
        if (!isDragging.value && rawOffsetLeft.floatValue != value.start) {
            rawOffsetLeft.floatValue = value.start
        }

        if (!isDragging.value && rawOffsetRight.floatValue != value.endInclusive) {
            rawOffsetRight.floatValue = value.endInclusive
        }
    }

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        val leftOffsetX = remember { mutableFloatStateOf(0f) }
        val rightOffsetX = remember { mutableFloatStateOf(0f) }

        LaunchedEffect(key1 = rawOffsetLeft.floatValue) {
            leftOffsetX.floatValue = getThumbPositionX(
                rawOffsetLeft.floatValue,
                totalStep.floatValue,
                maxWidth.value,
                thumbSize.value
            )
        }

        LaunchedEffect(key1 = rawOffsetRight.floatValue) {
            rightOffsetX.floatValue = getThumbPositionX(
                rawOffsetRight.floatValue,
                totalStep.floatValue,
                maxWidth.value,
                thumbSize.value
            )
        }

        val stepSize = remember(maxWidth.value, thumbSize, totalStep) {
            val sliderWidth = maxWidth.value - thumbSize.value
            val stepSize = if (sliderWidth == 0f) 0f else sliderWidth / totalStep.floatValue
            mutableFloatStateOf(stepSize)
        }

        Box(
            modifier = Modifier
                .padding(start = (thumbSize.value * 0.5).dp)
                .width(maxWidth - thumbSize)
                .height(trackHeight)
                .clip(RoundedCornerShape(trackHeight))
                .background(colors.tickColor(enabled = enabled, active = active))
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .width((abs(rightOffsetX.floatValue - leftOffsetX.floatValue)).dp)
                .offset(
                    x = if (leftOffsetX.floatValue < rightOffsetX.floatValue) {
                        (leftOffsetX.floatValue + thumbSize.value * 0.5).dp
                    } else {
                        (rightOffsetX.floatValue + thumbSize.value * 0.5).dp
                    }
                )
                .height(trackHeight)
                .clip(RoundedCornerShape(trackHeight))
                .background(colors.trackColor(enabled = enabled, active = active))
                .onGloballyPositioned { layoutCoordinates ->
                    scope.launch {
                        if (isDragging.value) {
                            val leftOffset = leftOffsetX.floatValue
                            val rightOffset = rightOffsetX.floatValue

                            val startX = if (leftOffset < rightOffset) {
                                leftOffset
                            } else {
                                rightOffset
                            }

                            val startStep = if (startX == 0f) 0f else (startX / stepSize.floatValue)

                            val endX =
                                startX + with(density) { layoutCoordinates.size.width.toDp() }.value
                            val endStep = if (endX == 0f) 0f else (endX / stepSize.floatValue)

                            if (startStep < endStep) {
                                onValueChange(startStep..endStep)
                            } else {
                                onValueChange(endStep..startStep)
                            }
                        }
                    }
                }
        )

        if (isRange) {
            WantedSliderThumb(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = leftOffsetX.floatValue.dp).
                    zIndex(leftThumbZIndex.floatValue),
                enabled = enabled,
                thumbSize = thumbSize.value.dp,
                contentColor = colors.thumbColor(enabled),
                onDragStart = {
                    leftThumbZIndex.floatValue = 10f
                    isDragging.value = true
                },
                onDrag = { dragAmount: Offset ->
                    scope.launch {
                        leftOffsetX.floatValue = getThumbPositionXWithPosition(
                            positionX = leftOffsetX.floatValue,
                            dragAmountX = with(density) { dragAmount.x.toDp() }.value,
                            maxSliderWidth = maxWidth.value,
                            thumbSize = thumbSize.value
                        )
                    }
                },
                onDragEnd = {
                    leftThumbZIndex.floatValue = 1f
                    val leftStep = getStep(leftOffsetX.floatValue, stepSize.floatValue)
                    val rightStep =
                        getStep(rightOffsetX.floatValue, stepSize.floatValue)

                    leftOffsetX.floatValue = getThumbPositionX(
                        step = leftStep,
                        totalStep = totalStep.floatValue,
                        maxSliderWidth = maxWidth.value,
                        thumbSize = thumbSize.value
                    )

                    isDragging.value = false
                    if (leftStep <= rightStep) {
                        onValueChangeFinished(leftStep..rightStep)
                    } else {
                        onValueChangeFinished(rightStep..leftStep)
                    }
                }
            )
        }

        WantedSliderThumb(
            modifier = Modifier
                .size(thumbSize)
                .align(Alignment.CenterStart)
                .offset(x = rightOffsetX.floatValue.dp)
                .zIndex(rightThumbZIndex.floatValue),
            enabled = enabled,
            thumbSize = thumbSize.value.dp,
            contentColor = colors.thumbColor(enabled),
            onDragStart = {
                isDragging.value = true
                rightThumbZIndex.floatValue = 10f
            },
            onDrag = { dragAmount: Offset ->
                scope.launch {
                    rightOffsetX.floatValue = getThumbPositionXWithPosition(
                        positionX = rightOffsetX.floatValue,
                        dragAmountX = with(density) { dragAmount.x.toDp() }.value,
                        maxSliderWidth = maxWidth.value,
                        thumbSize = thumbSize.value
                    )
                }
            },
            onDragEnd = {
                rightThumbZIndex.floatValue = 1f
                val leftStep = getStep(leftOffsetX.floatValue, stepSize.floatValue)
                val rightStep =
                    getStep(rightOffsetX.floatValue, stepSize.floatValue)

                rightOffsetX.floatValue = getThumbPositionX(
                    step = rightStep,
                    totalStep = totalStep.floatValue,
                    maxSliderWidth = maxWidth.value,
                    thumbSize = thumbSize.value
                )
                isDragging.value = false
                if (leftStep <= rightStep) {
                    onValueChangeFinished(leftStep..rightStep)
                } else {
                    onValueChangeFinished(rightStep..leftStep)
                }
            }
        )
    }
}

fun getStep(offset: Float, stepSize: Float): Float {
    return if (offset == 0f) {
        0
    } else {
        (offset / stepSize).roundToInt()
    }.toFloat()
}

private fun getThumbPositionXWithPosition(
    positionX: Float,
    dragAmountX: Float,
    maxSliderWidth: Float,
    thumbSize: Float,
): Float {
    var result = positionX + dragAmountX
    if (result < 0f) {
        result = 0f
    }
    if (result + thumbSize > maxSliderWidth) {
        result = maxSliderWidth - thumbSize
    }

    return result
}


private fun getThumbPositionX(
    step: Float,
    totalStep: Float,
    maxSliderWidth: Float,
    thumbSize: Float
): Float {

    val position = if ((maxSliderWidth - thumbSize) <= 0f) {
        0f
    } else if (step > totalStep) {
        maxSliderWidth - thumbSize
    } else {
        (maxSliderWidth - thumbSize) / totalStep * step
    }
    return position
}

@Stable
private fun SliderColors.tickColor(enabled: Boolean, active: Boolean): Color =
    if (enabled) {
        if (active) activeTickColor else inactiveTickColor
    } else {
        if (active) disabledActiveTickColor else disabledInactiveTickColor
    }

@Stable
private fun SliderColors.trackColor(enabled: Boolean, active: Boolean): Color =
    if (enabled) {
        if (active) activeTrackColor else inactiveTrackColor
    } else {
        if (active) disabledActiveTrackColor else disabledInactiveTrackColor
    }

@Stable
private fun SliderColors.thumbColor(enabled: Boolean): Color =
    if (enabled) thumbColor else disabledThumbColor

