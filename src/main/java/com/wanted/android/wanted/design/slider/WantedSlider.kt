package com.wanted.android.wanted.design.slider

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle
import kotlin.math.floor
import kotlin.math.roundToInt

/**
 * figma : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=23088-74063&m=dev
 * 설명 : https://www.figma.com/design/MK6KmtXBxX7ZkoQXfD9MFH/%EA%B0%9C%EC%84%A0%3A-Components?node-id=3249-11005&t=nhmJmOWJUXIp8HXo-4
 */

@Composable
fun WantedSlider(
    modifier: Modifier = Modifier,
    heading: Boolean,
) {
    SliderLayout(
        modifier = modifier,
        header = if (heading) {
            {

            }
        } else null,
        slider = {

        }
    )
}

@Composable
private fun SliderLayout(
    modifier: Modifier = Modifier,
    header: @Composable (() -> Unit)? = null,
    slider: @Composable (maxWidth: Float) -> Unit,
    minLabel: @Composable (BoxWithConstraintsScope.(maxWidth: Float) -> Unit)? = null,
    maxLabel: @Composable (BoxWithConstraintsScope.(maxWidth: Float) -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        header?.let {
            header.invoke()
            Spacer(modifier = Modifier.size(32.dp))
        }

        BoxWithConstraints(Modifier.fillMaxWidth()) {
            slider(maxWidth.value)
        }

        if (minLabel != null || maxLabel != null) {
            Spacer(modifier = Modifier.size(12.dp))

            BoxWithConstraints(Modifier.fillMaxWidth()) {
                minLabel?.invoke(this, maxWidth.value)
                maxLabel?.invoke(this, maxWidth.value)
            }
        }
    }
}


@Composable
fun WantedSlideImpl(
    modifier: Modifier,
    labelMin: String,
    labelMax: String,
    annalRange: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
) {
    val density = LocalDensity.current
    val minOffsetX = remember { mutableFloatStateOf(0f) }
    val minTextWidth = remember { mutableFloatStateOf(0f) }

    val maxOffsetX = remember { mutableFloatStateOf(0f) }
    val maxTextWidth = remember { mutableFloatStateOf(0f) }

    val sliderWidth = remember { mutableFloatStateOf(0f) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        WantedRangeSlider(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            value = annalRange,
            valueRange = 0f..10f,
            colors = SliderDefaults.colors(
                thumbColor = colorResource(id = R.color.primary_normal),
                activeTrackColor = colorResource(id = R.color.primary_normal),
                activeTickColor = colorResource(id = R.color.fill_strong)
            ),
            thumbSize = ThumbRadius * 2,
            trackHeight = 6.dp,
            onValueChange = { range ->
                minOffsetX.floatValue =
                    getTextOffset(range.start, sliderWidth.floatValue, minTextWidth.floatValue)
                maxOffsetX.floatValue = getTextOffset(
                    range.endInclusive,
                    sliderWidth.floatValue,
                    maxTextWidth.floatValue
                )
                onValueChange(range)

            },
            onValueChangeFinished = { range ->
                onValueChange(
                    range.start.roundToInt().toFloat()..range.endInclusive.roundToInt().toFloat()
                )
            }
        )

        Spacer(modifier = Modifier.size(12.dp))

        BoxWithConstraints(Modifier.fillMaxWidth()) {
            sliderWidth.floatValue = maxWidth.value

            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .wrapContentSize()
                    .offset(x = minOffsetX.floatValue.dp)
                    .onGloballyPositioned { layoutCoordinates ->
                        minTextWidth.floatValue =
                            with(density) { layoutCoordinates.size.width.toDp() }.value
                        minOffsetX.floatValue = getTextOffset(
                            annalRange.start,
                            sliderWidth.floatValue,
                            minTextWidth.floatValue
                        )
                    },
                text = labelMin,
                style = WantedTextStyle(
                    colorRes = R.color.label_normal,
                    style = DesignSystemTheme.typography.label1Medium
                )
            )

            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .wrapContentSize()
                    .offset(x = maxOffsetX.floatValue.dp)
                    .onGloballyPositioned { layoutCoordinates ->
                        maxTextWidth.floatValue =
                            with(density) { layoutCoordinates.size.width.toDp() }.value

                        maxOffsetX.floatValue = getTextOffset(
                            annalRange.endInclusive,
                            sliderWidth.floatValue,
                            maxTextWidth.floatValue
                        )
                    },
                text = labelMax,
                style = WantedTextStyle(
                    colorRes = R.color.label_normal,
                    style = DesignSystemTheme.typography.label1Medium
                )
            )
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


private val ThumbRadius = 14.dp

@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Preview(
    "foldableLight",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "ko",
    device = Devices.FOLDABLE
)
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