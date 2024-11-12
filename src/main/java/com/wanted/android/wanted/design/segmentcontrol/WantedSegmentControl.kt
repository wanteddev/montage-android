package com.wanted.android.wanted.design.segmentcontrol

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_28
import kotlinx.coroutines.launch


@Composable
fun WantedSegmentControl(
    modifier: Modifier,
    items: List<String>,
    selectedIndex: Int,
    onClick: (index: Int) -> Unit = {}
) {
    WantedSegmentControl(
        modifier = modifier,
        itemCount = items.size,
        selectedIndex = selectedIndex,
        onClick = onClick,
        item = { index ->
            WantedSegmentControlItem(
                modifier = Modifier.fillMaxWidth(),
                title = items[index],
                isSelected = index == selectedIndex
            )
        }
    )
}

@Composable
fun WantedSegmentControl(
    modifier: Modifier,
    itemCount: Int,
    selectedIndex: Int,
    item: @Composable (index: Int) -> Unit,
    onClick: (index: Int) -> Unit = {}
) {
    val localDensity = LocalDensity.current
    var width by remember(itemCount) { mutableStateOf(0.dp) }
    val animatedOffsetX = remember(width) {
        Animatable(selectedIndex * with(localDensity) { width.toPx() })
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(selectedIndex) {
        scope.launch {
            animatedOffsetX.animateTo(
                targetValue = with(localDensity) { width.toPx() } * selectedIndex,
                animationSpec = tween(
                    durationMillis = 500,
                    easing = CubicBezierEasing(0.25f, 1.25f, 0.4f, 0.99f)
                )
            )
        }
    }

    WantedSegmentControlLayout(
        modifier = modifier,
        knob = {
            Box(
                modifier = Modifier
                    .width(width)
                    .fillMaxHeight()
                    .offset { IntOffset(animatedOffsetX.value.toInt(), 0) }
                    .clip(RoundedCornerShape(10.dp))
                    .background(colorResource(id = R.color.background_elevated_normal))
                    .background(colorResource(id = R.color.static_white).copy(alpha = OPACITY_28))
            )
        },
        contents = {
            for (index in 0 until itemCount) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickOnceForDesignSystem(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            onClick(index)
                        }
                        .onGloballyPositioned { coordinates ->
                            width = with(localDensity) { coordinates.size.width.toDp() }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    item(index)
                }
            }
        }
    )
}

@Composable
fun WantedSegmentControlLayout(
    modifier: Modifier = Modifier,
    knob: @Composable () -> Unit,
    contents: @Composable RowScope.() -> Unit
) {
    Box(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(12.dp))
            .background(colorResource(id = R.color.fill_normal))
            .padding(3.dp)

    ) {
        knob()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            contents()
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
private fun WantedSegmentControlPreview() {
    DesignSystemTheme {
        val itemList = remember {
            val items = mutableListOf<String>()
            for (index in 0..<3) {
                items.add("텍스트${index + 1}")
            }
            items
        }

        var selectedIndex by remember { mutableIntStateOf(0) }

        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedSegmentControl(
                    modifier = Modifier,
                    items = itemList,
                    selectedIndex = selectedIndex,
                    onClick = {
                        selectedIndex = it
                    }
                )
            }
        }
    }
}