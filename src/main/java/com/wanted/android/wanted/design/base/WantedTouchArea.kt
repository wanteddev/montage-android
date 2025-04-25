package com.wanted.android.wanted.design.base

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
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
    /**
     * preview가 아니고 innerTouch가 되어야 할경우만 계산한다.
     * view를 계산하게되면 간혹 IllegalStateException 발생
     * java.lang.IllegalStateException: Asking for intrinsic measurements of SubcomposeLayout layouts is not supported.
     */
    val calculateContentSize = !LocalInspectionMode.current && enabledInnerTouch
    val contentHeight = remember { mutableStateOf(0.dp) }
    val contentWidth = remember { mutableStateOf(0.dp) }

    val localDensity = LocalDensity.current

    val sizeModifier = if (calculateContentSize) {
        Modifier
            .width(contentWidth.value)
            .height(contentHeight.value)
    } else {
        Modifier
    }

    val clickModifier = Modifier.clickOnce(
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
    }


    ConstraintLayout(
        modifier = modifier
    ) {
        val (box, touch) = createRefs()

        if (calculateContentSize) {
            MeasureOnly(
                content = {
                    Box { content() }
                },
                onSizeCalculated = { size ->
                    contentHeight.value = with(localDensity) { size.height.toDp() }
                    contentWidth.value = with(localDensity) { size.width.toDp() }
                }
            )
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
                .then(sizeModifier),
            contentAlignment = Alignment.Center
        ) {
            if (!calculateContentSize) {
                content()
            }
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
                    if (!calculateContentSize) {
                        contentHeight.value = with(localDensity) { coordinates.size.height.toDp() }
                        contentWidth.value = with(localDensity) { coordinates.size.width.toDp() }
                    }
                }
                .clip(shape)
                .then(clickModifier),
            content = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (calculateContentSize) {
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

@Composable
private fun MeasureOnly(
    content: @Composable () -> Unit,
    onSizeCalculated: (IntSize) -> Unit
) {
    SubcomposeLayout { constraints ->
        // 1) "measure" 키로 content만 subcompose
        val measurables = subcompose("measure", content)
        // 2) constraints의 최소 크기를 0으로 열어 두고 실제 크기만 측정
        val placeable = measurables.first()
            .measure(constraints.copy(minWidth = 0, minHeight = 0))
        // 3) 측정된 size 콜백으로 전달

        onSizeCalculated(IntSize(placeable.width, placeable.height))
        // 4) layout 단계에서는 보이지 않게 0×0 크기로 설정
        layout(width = 0, height = 0) { /* no placement → draw pass에서 skip */ }
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
                verticalArrangement = Arrangement.spacedBy(0.dp),
            ) {

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Green),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(Color.Red)
                            .clickOnce {
                            }
                    )
                }

                WantedTouchArea(
                    horizontalPadding = 20.dp,
                    verticalPadding = 20.dp,
                    content = {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.Red),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .background(Color.Green)
                                    .clickOnce {
                                    }
                            )
                        }
                    },
                    onClick = {

                    }
                )

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Green),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(Color.Red)
                            .clickOnce {
                            }
                    )
                }
            }
        }
    }
}