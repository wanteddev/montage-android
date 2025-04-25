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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.clickOnce

val LocalWantedTouchArea = WantedButtonContentCompositionLocal()

@Composable
fun WantedTouchArea(
    modifier: Modifier = Modifier,
    verticalPadding: Dp = 0.dp,
    horizontalPadding: Dp = 0.dp,
    shape: Shape = RoundedCornerShape(6.dp),
    enabled: Boolean = true,
    enabledInnerTouch: Boolean = LocalWantedTouchArea.current.getEnableInnerTouch(),
    rippleColor: Color = Color.Unspecified,
    isUseRipple: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable BoxScope.() -> Unit,
    onClick: (() -> Unit)? = null
) {

    val contentHeight = remember { mutableStateOf(0.dp) }
    val contentWidth = remember { mutableStateOf(0.dp) }

    val localDensity = LocalDensity.current
    ConstraintLayout(
        modifier = modifier
    ) {
        val (box, touch) = createRefs()
        val alphaModifier = if (enabledInnerTouch) {
            Modifier.alpha(0f)
        } else {
            Modifier
        }

        Box(
            modifier = Modifier
                .wrapContentSize()
                .then(alphaModifier)
                .constrainAs(box) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentAlignment = Alignment.Center
        ) {
            content()
        }

        Layout(
            modifier = Modifier
                .constrainAs(touch) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints  // Match width of text
                    height = Dimension.fillToConstraints // Match height of text
                }
                .onGloballyPositioned { coordinates ->
                    // Set column height using the LayoutCoordinates
                    contentHeight.value = with(localDensity) { coordinates.size.height.toDp() }
                    contentWidth.value = with(localDensity) { coordinates.size.width.toDp() }
                }
                .clip(shape)
                .clickOnce(
                    enabled = onClick != null && enabled,
                    indication = if (isUseRipple) {
                        ripple(
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
                    onClick?.invoke()
                },
            content = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (enabledInnerTouch) {
                        content()
                    }
                }
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



interface WantedTouchAreaLoader {
    fun getEnableInnerTouch(): Boolean
}

private class WantedTouchAreaLoaderImpl() : WantedTouchAreaLoader {
    override fun getEnableInnerTouch(): Boolean = false
}

@JvmInline
value class WantedButtonContentCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedTouchAreaLoader> = staticCompositionLocalOf { WantedTouchAreaLoaderImpl() }
) {
    val current: WantedTouchAreaLoader
        @Composable get() = delegate.current

    infix fun provides(value: WantedTouchAreaLoader) = delegate provides value
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
                    horizontalPadding = 10.dp,
                    verticalPadding = 10.dp,
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