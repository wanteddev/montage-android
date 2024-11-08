package com.wanted.android.wanted.design.base

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.theme.DesignSystemTheme

@Composable
fun WantedTouchArea(
    modifier: Modifier = Modifier,
    verticalPadding: Dp = 0.dp,
    horizontalPadding: Dp = 0.dp,
    shape: Shape = RoundedCornerShape(6.dp),
    enabled: Boolean = true,
    rippleColor: Color = Color.Unspecified,
    isUseRipple: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable BoxScope.() -> Unit,
    onClick: () -> Unit
) {
    val contentHeight = remember { mutableStateOf(0.dp) }
    val contentWidth = remember { mutableStateOf(0.dp) }

    val localDensity = LocalDensity.current
    ConstraintLayout(
        modifier = modifier
    ) {
        val (box, touch) = createRefs()
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
                .clip(shape)
                .clickOnceForDesignSystem(
                    enabled = enabled,
                    indication = if (isUseRipple) {
                        rememberRipple(
                            bounded = true, // 확장된 영역에 리플 효과를 적용
                            radius = if (contentWidth.value > contentHeight.value) {
                                contentWidth.value
                            } else {
                                contentHeight.value
                            }, // 리플의 크기를 확장된 터치 영역에 맞춤
                            color = rippleColor
                        )
                    } else null,
                    interactionSource = interactionSource
                ) {
                    onClick()
                },
            content = {
                Box(modifier = Modifier.fillMaxSize())
            }
        ) { measurables, constraints ->
            val textPlaceable = measurables[0].measure(constraints)

            // Calculate the expanded dimensions
            val expandedWidth = textPlaceable.width + (2 * horizontalPadding.toPx()).toInt()
            val expandedHeight = textPlaceable.height + (2 * verticalPadding.toPx()).toInt()

            layout(expandedWidth, expandedHeight) {
                textPlaceable.placeRelative(
                    x = (expandedWidth - textPlaceable.width) / 2,
                    y = (expandedHeight - textPlaceable.height) / 2
                )
            }
        }
    }
}

@Preview
@Composable
private fun WantedTouchAreaPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedTouchArea(
                    content = {
                        Text(text = "텍스트")
                    },
                    onClick = {
                    }
                )
            }
        }
    }
}