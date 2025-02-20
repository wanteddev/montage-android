package com.wanted.android.wanted.design.input.slider

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle
import kotlin.math.floor
import kotlin.math.roundToInt

/**
 * figma : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=23088-74063&m=dev
 * 설명 : https://www.figma.com/design/MK6KmtXBxX7ZkoQXfD9MFH/%EA%B0%9C%EC%84%A0%3A-Components?node-id=3249-11005&t=NBcGxPA8xDj0pTCY-4
 */

@Composable
fun WantedSlider(
    modifier: Modifier = Modifier,
    header: String = "",
    label: String = "",
    enabled: Boolean = true,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit
) {
    WantedRangeSlider(
        modifier = modifier,
        header = header,
        labelMax = label,
        isRange = false,
        enabled = enabled,
        value = 0f..value,
        valueRange = valueRange,
        onValueChange = { range ->
            onValueChange(range.endInclusive)
        }
    )
}

@Composable
fun WantedSlider(
    modifier: Modifier = Modifier,
    header: String = "",
    labelMin: String = "",
    labelMax: String = "",
    enabled: Boolean = true,
    value: ClosedFloatingPointRange<Float>,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
) {
    WantedRangeSlider(
        modifier = modifier,
        header = header,
        labelMin = labelMin,
        labelMax = labelMax,
        isRange = true,
        enabled = enabled,
        value = value,
        valueRange = valueRange,
        onValueChange = onValueChange
    )
}

@Composable
private fun WantedRangeSlider(
    modifier: Modifier = Modifier,
    header: String = "",
    labelMin: String = "",
    labelMax: String = "",
    isRange: Boolean = false,
    value: ClosedFloatingPointRange<Float>,
    valueRange: ClosedFloatingPointRange<Float>,
    enabled: Boolean = true,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
) {
    val density = LocalDensity.current

    val minOffsetX = remember { mutableFloatStateOf(0f) }
    val minTextWidth = remember { mutableFloatStateOf(0f) }

    val maxOffsetX = remember { mutableFloatStateOf(0f) }
    val maxTextWidth = remember { mutableFloatStateOf(0f) }

    SliderLayout(
        modifier = modifier,
        isEnable = enabled,
        header = if (header.isNotEmpty()) {
            {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    textAlign = TextAlign.Center,
                    text = header,
                    style = WantedTextStyle(
                        colorRes = if (enabled) R.color.label_normal else R.color.label_disable,
                        style = DesignSystemTheme.typography.headline2Bold
                    )
                )
            }
        } else null,
        slider = { sliderWidth ->
            WantedRangeSlider(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                value = value,
                valueRange = valueRange,
                enabled = enabled,
                colors = SliderDefaults.colors(
                    thumbColor = colorResource(id = R.color.primary_normal),
                    disabledThumbColor = colorResource(id = R.color.interaction_disable),
                    activeTrackColor = colorResource(id = R.color.primary_normal),
                    disabledActiveTrackColor = colorResource(id = R.color.interaction_disable),
                    activeTickColor = colorResource(id = R.color.fill_strong),
                    disabledActiveTickColor = colorResource(id = R.color.interaction_disable)
                ),
                thumbSize = ThumbRadius * 2,
                isRange = isRange,
                trackHeight = TrackHeight,
                onValueChange = { range ->
                    minOffsetX.floatValue =
                        getTextOffset(range.start, sliderWidth, minTextWidth.floatValue)
                    maxOffsetX.floatValue = getTextOffset(
                        range.endInclusive,
                        sliderWidth,
                        maxTextWidth.floatValue
                    )
                    onValueChange(range)

                },
                onValueChangeFinished = { range ->
                    onValueChange(
                        range.start.roundToInt().toFloat()..range.endInclusive.roundToInt()
                            .toFloat()
                    )
                }
            )
        },
        minLabel = if (labelMin.isNotEmpty()) {
            { sliderWidth ->
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .wrapContentSize()
                        .offset(x = minOffsetX.floatValue.dp)
                        .onGloballyPositioned { layoutCoordinates ->
                            minTextWidth.floatValue =
                                with(density) { layoutCoordinates.size.width.toDp() }.value
                            minOffsetX.floatValue = getTextOffset(
                                value.start,
                                sliderWidth,
                                minTextWidth.floatValue
                            )
                        },
                    text = labelMin
                )
            }
        } else null,
        maxLabel = if (labelMax.isNotEmpty()) {
            { sliderWidth ->
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .wrapContentSize()
                        .offset(x = maxOffsetX.floatValue.dp)
                        .onGloballyPositioned { layoutCoordinates ->
                            maxTextWidth.floatValue =
                                with(density) { layoutCoordinates.size.width.toDp() }.value

                            maxOffsetX.floatValue = getTextOffset(
                                value.endInclusive,
                                sliderWidth,
                                maxTextWidth.floatValue
                            )
                        },
                    text = labelMax
                )
            }
        } else null
    )
}

@Composable
private fun SliderLayout(
    modifier: Modifier = Modifier,
    isEnable: Boolean,
    header: @Composable (() -> Unit)? = null,
    slider: @Composable (sliderWidth: Float) -> Unit,
    minLabel: @Composable (BoxWithConstraintsScope.(sliderWidth: Float) -> Unit)? = null,
    maxLabel: @Composable (BoxWithConstraintsScope.(sliderWidth: Float) -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        header?.let {
            ProvideTextStyle(
                value = WantedTextStyle(
                    colorRes = if (isEnable) R.color.label_normal else R.color.label_disable,
                    style = DesignSystemTheme.typography.headline2Bold
                )
            ) {
                header.invoke()
            }

            Spacer(modifier = Modifier.size(32.dp))
        }

        BoxWithConstraints(Modifier.fillMaxWidth()) {
            slider(maxWidth.value)
        }

        if (minLabel != null || maxLabel != null) {
            Spacer(modifier = Modifier.size(8.dp))

            BoxWithConstraints(Modifier.fillMaxWidth()) {
                ProvideTextStyle(
                    value = WantedTextStyle(
                        colorRes = if (isEnable) R.color.label_normal else R.color.label_disable,
                        style = DesignSystemTheme.typography.label1Medium
                    )
                ) {
                    minLabel?.invoke(this, maxWidth.value)
                    maxLabel?.invoke(this, maxWidth.value)
                }
            }
        }
    }
}

private fun getTextOffset(
    range: Float,
    width: Float,
    textWidth: Float,
    stepCount: Int = 10
): Float {
    val step = floor(range)
    val remain = range - step
    val margin = ThumbRadius.value * 2
    val sliderWidth = width - margin
    val stepSize = if (sliderWidth == 0f) 0f else sliderWidth / stepCount

    var offset =
        ((stepSize * step) + (stepSize * remain) + margin - (textWidth * 0.5) - ThumbRadius.value).toFloat()
    // 맨 왼쪽에 있을때 예외처리
    if (offset < 0f) {
        offset = 0f
    }

    // 맨 오른쪽에 있을때 예외처리
    if (offset + textWidth > sliderWidth + margin) {
        offset = sliderWidth + margin - textWidth
    }

    return offset
}


private val ThumbRadius = 10.dp
private val TrackHeight = 4.dp

@DevicePreviews
@Composable
private fun WantedSliderPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {

            }
        }
    }
}