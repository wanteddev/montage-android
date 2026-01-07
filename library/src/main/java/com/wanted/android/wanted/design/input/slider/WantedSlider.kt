package com.wanted.android.wanted.design.input.slider

import android.annotation.SuppressLint
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import kotlin.math.floor
import kotlin.math.roundToInt

/**
 * WantedSlider
 *
 * 단일 값을 선택할 수 있는 Slider 컴포넌트입니다.
 *
 * 사용자가 Thumb을 이동하여 하나의 값을 선택할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * var sliderValue by remember { mutableFloatStateOf(30f) }
 *
 * WantedSlider(
 *     value = sliderValue,
 *     valueRange = 0f..100f,
 *     header = "볼륨",
 *     label = "${sliderValue.toInt()}",
 *     onValueChange = { sliderValue = it }
 * )
 * ```
 *
 * @param value Float: 현재 선택된 값입니다.
 * @param valueRange ClosedFloatingPointRange<Float>: Slider가 선택할 수 있는 값의 범위입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param header String: 상단에 표시할 헤더 텍스트입니다.
 * @param label String: 하단에 표시할 라벨 텍스트입니다.
 * @param enabled Boolean: Slider 활성화 여부입니다.
 * @param onValueChange (Float) -> Unit: 값이 변경될 때 호출되는 콜백입니다.
 */
@Composable
fun WantedSlider(
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    modifier: Modifier = Modifier,
    header: String = "",
    label: String = "",
    enabled: Boolean = true,
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

/**
 * WantedSlider
 *
 * 범위를 선택할 수 있는 Slider 컴포넌트입니다.
 *
 * 사용자가 양 끝Thumb을 조절하여 최소값과 최대값을 선택할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * var rangeValue by remember { mutableStateOf(10f..90f) }
 *
 * WantedSlider(
 *     value = rangeValue,
 *     valueRange = 0f..100f,
 *     header = "가격 범위",
 *     labelMin = "${rangeValue.start.toInt()}만원",
 *     labelMax = "${rangeValue.endInclusive.toInt()}만원",
 *     onValueChange = { rangeValue = it }
 * )
 * ```
 *
 * @param value ClosedFloatingPointRange<Float>: 현재 선택된 값의 범위입니다.
 * @param valueRange ClosedFloatingPointRange<Float>: 선택 가능한 값의 최소/최대 범위입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param header String: 상단에 표시할 헤더 텍스트입니다.
 * @param labelMin String: 최소값 라벨 텍스트입니다.
 * @param labelMax String: 최대값 라벨 텍스트입니다.
 * @param enabled Boolean: Slider 활성화 여부입니다.
 * @param onValueChange (ClosedFloatingPointRange<Float>) -> Unit: 값이 변경될 때 호출되는 콜백입니다.
 */
@Composable
fun WantedSlider(
    value: ClosedFloatingPointRange<Float>,
    valueRange: ClosedFloatingPointRange<Float>,
    modifier: Modifier = Modifier,
    header: String = "",
    labelMin: String = "",
    labelMax: String = "",
    enabled: Boolean = true,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit
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
    value: ClosedFloatingPointRange<Float>,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    modifier: Modifier = Modifier,
    header: String = "",
    labelMin: String = "",
    labelMax: String = "",
    isRange: Boolean = false,
    enabled: Boolean = true
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
                    style = DesignSystemTheme.typography.headline2Bold,
                    color = if (enabled) {
                        DesignSystemTheme.colors.labelNormal
                    } else {
                        DesignSystemTheme.colors.labelDisable
                    },
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
                    thumbColor = DesignSystemTheme.colors.primaryNormal,
                    disabledThumbColor = DesignSystemTheme.colors.interactionDisable,
                    activeTrackColor = DesignSystemTheme.colors.primaryNormal,
                    disabledActiveTrackColor = DesignSystemTheme.colors.interactionDisable,
                    activeTickColor = DesignSystemTheme.colors.fillStrong,
                    disabledActiveTickColor = DesignSystemTheme.colors.interactionDisable
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

@SuppressLint("UnusedBoxWithConstraintsScope")
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
                value = DesignSystemTheme.typography.headline2Bold.copy(
                    color = if (isEnable) {
                        DesignSystemTheme.colors.labelNormal
                    } else {
                        DesignSystemTheme.colors.labelDisable
                    }
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
                    value = DesignSystemTheme.typography.label1Medium.copy(
                        color = if (isEnable) {
                            DesignSystemTheme.colors.labelNormal
                        } else {
                            DesignSystemTheme.colors.labelDisable
                        }
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