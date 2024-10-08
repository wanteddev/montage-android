package com.wanted.android.wanted.design.slider

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_12

@Composable
fun WantedSliderThumb(
    modifier: Modifier = Modifier,
    thumbSize: Dp,
    contentColor: Color = colorResource(id = R.color.primary_normal),
    boarderColor: Color = colorResource(id = R.color.background_normal_normal),
) {
    WantedSliderThumbArea(
        modifier = Modifier
            .indication(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    color = colorResource(id = R.color.primary_normal).copy(alpha = OPACITY_12)
                )
            )
            .then(modifier),
        boarderColor = boarderColor,
        content = {
            Box(
                modifier = Modifier
                    .size(thumbSize)
                    .clip(CircleShape)
                    .background(contentColor)
            )
        }
    )
}

@Composable
fun WantedSliderThumbArea(
    modifier: Modifier = Modifier,
    boarderColor: Color,
    content: @Composable () -> Unit,
) {
    val contentHeight = remember { mutableStateOf(0.dp) }
    val contentWidth = remember { mutableStateOf(0.dp) }

    val localDensity = LocalDensity.current
    ConstraintLayout(
        modifier = modifier
    ) {
        val (box, touch, boarder) = createRefs()
        Layout(
            modifier = Modifier
                .constrainAs(boarder) {
                    top.linkTo(box.top)
                    start.linkTo(box.start)
                    end.linkTo(box.end)
                    bottom.linkTo(box.bottom)
                    width = Dimension.fillToConstraints  // Match width of text
                    height = Dimension.fillToConstraints // Match height of text
                }
                .clip(CircleShape),
            content = {
                Box(modifier = Modifier.clip(CircleShape).background( Color.Black))
            }
        ) { measurables, constraints ->
            val textPlaceable = measurables[0].measure(constraints)

            // Calculate the expanded dimensions
            val expandedWidth = textPlaceable.width + (6.dp.toPx()).toInt()
            val expandedHeight = textPlaceable.height + (6.dp.toPx()).toInt()

            layout(expandedWidth, expandedHeight) {
                textPlaceable.placeRelative(
                    x = (expandedWidth - textPlaceable.width) / 2,
                    y = (expandedHeight - textPlaceable.height) / 2
                )
            }
        }
        Box(
            modifier = Modifier
                .wrapContentSize()
                .constrainAs(box) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .onGloballyPositioned { coordinates ->
                    // Set column height using the LayoutCoordinates
                    contentHeight.value = with(localDensity) { coordinates.size.height.toDp() }
                    contentWidth.value = with(localDensity) { coordinates.size.width.toDp() }
                },
            contentAlignment = Alignment.Center
        ) {
            content()
        }
        Layout(
            modifier = Modifier
                .constrainAs(touch) {
                    top.linkTo(box.top)
                    start.linkTo(box.start)
                    end.linkTo(box.end)
                    bottom.linkTo(box.bottom)
                    width = Dimension.fillToConstraints  // Match width of text
                    height = Dimension.fillToConstraints // Match height of text
                }
                .clip(CircleShape),
            content = {
                Box(modifier = Modifier)
            }
        ) { measurables, constraints ->
            val textPlaceable = measurables[0].measure(constraints)

            // Calculate the expanded dimensions
            val expandedWidth = textPlaceable.width + (2 * 6.dp.toPx()).toInt()
            val expandedHeight = textPlaceable.height + (2 * 6.dp.toPx()).toInt()

            layout(expandedWidth, expandedHeight) {
                textPlaceable.placeRelative(
                    x = (expandedWidth - textPlaceable.width) / 2,
                    y = (expandedHeight - textPlaceable.height) / 2
                )
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
private fun WantedSliderThumbPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedSliderThumb(
                    thumbSize = 20.dp
                )
            }
        }
    }
}