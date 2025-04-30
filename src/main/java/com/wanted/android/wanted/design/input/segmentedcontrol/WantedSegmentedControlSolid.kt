package com.wanted.android.wanted.design.input.segmentedcontrol

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.clickOnce
import com.wanted.android.wanted.design.base.WantedDropShadow
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_28
import kotlinx.coroutines.launch


/**
 * 문자열 리스트 기반의 Solid 스타일 Segmented Control 컴포넌트입니다.
 *
 * 선택된 항목을 강조 표시하며, 내부적으로 애니메이션 되는 Knob과 함께 사용됩니다.
 * 항목은 텍스트 기반으로 자동 생성되며, 클릭 시 인덱스가 콜백으로 반환됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * val items = listOf("전체", "읽음", "안읽음")
 * var selectedIndex by remember { mutableIntStateOf(0) }
 *
 * WantedSegmentedControlSolid(
 *     items = items,
 *     selectedIndex = selectedIndex,
 *     onClick = { selectedIndex = it }
 * )
 * ```
 *
 * @param items List<String>: 표시할 항목 텍스트 리스트입니다.
 * @param selectedIndex Int: 현재 선택된 항목의 인덱스입니다.
 * @param modifier Modifier: 외형을 설정하는 Modifier입니다.
 * @param size SegmentedSize: 항목의 사이즈 설정입니다 (Small, Medium, Large).
 * @param onClick (index: Int) -> Unit: 항목 클릭 시 선택 인덱스를 반환하는 콜백입니다.
 */
@Composable
fun WantedSegmentedControlSolid(
    items: List<String>,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    size: WantedSegmentedContract.SegmentedSize = WantedSegmentedContract.SegmentedSize.Medium,
    onClick: (index: Int) -> Unit = {}
) {
    CompositionLocalProvider(LocalWantedSegmentedSize.provides(size)) {
        WantedSegmentedControlSolid(
            modifier = modifier,
            itemCount = items.size,
            selectedIndex = selectedIndex,
            onClick = onClick,
            item = { index ->
                WantedSegmentedControlSolidItem(
                    modifier = Modifier.fillMaxWidth(),
                    title = items[index],
                    isSelected = index == selectedIndex
                )
            }
        )
    }
}


/**
 * 사용자 정의 항목(item slot)과 애니메이션 Knob이 포함된 Solid 스타일 Segmented Control 컴포넌트입니다.
 *
 * 각 항목을 커스텀 Composable로 구성할 수 있으며, 선택 애니메이션은 Knob 위치 이동으로 표현됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedSegmentedControlSolid(
 *     itemCount = 3,
 *     selectedIndex = selectedIndex,
 *     item = { index ->
 *         WantedSegmentedControlSolidItem(
 *             title = "옵션 $index",
 *             isSelected = index == selectedIndex
 *         )
 *     },
 *     onClick = { selectedIndex = it }
 * )
 * ```
 *
 * @param itemCount Int: 표시할 항목 개수입니다.
 * @param selectedIndex Int: 현재 선택된 항목 인덱스입니다.
 * @param item (index: Int) -> Unit: 항목 렌더링을 위한 Composable 슬롯입니다.
 * @param modifier Modifier: 외형을 설정하는 Modifier입니다.
 * @param size SegmentedSize: 항목 크기 설정입니다.
 * @param onClick (index: Int) -> Unit: 클릭 시 호출되는 콜백입니다.
 */
@Composable
fun WantedSegmentedControlSolid(
    itemCount: Int,
    selectedIndex: Int,
    item: @Composable (index: Int) -> Unit,
    modifier: Modifier = Modifier,
    size: WantedSegmentedContract.SegmentedSize = WantedSegmentedContract.SegmentedSize.Medium,
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

    CompositionLocalProvider(LocalWantedSegmentedSize.provides(size)) {
        WantedSegmentControlSolidLayout(
            modifier = modifier,
            knob = {
                WantedSegmentedControlSolidKnob(
                    modifier = Modifier
                        .width(width)
                        .fillMaxHeight()
                        .offset { IntOffset(animatedOffsetX.value.toInt(), 0) }
                )
            },
            contents = {
                repeat(itemCount) { index ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickOnce(
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
}

@Composable
private fun WantedSegmentedControlSolidKnob(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        WantedDropShadow(
            Modifier.fillMaxSize(),
            shape = RoundedCornerShape(12.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp))
                .background(colorResource(id = R.color.background_elevated_normal))
                .background(colorResource(id = R.color.static_white).copy(alpha = OPACITY_28))
        )
    }
}

@Composable
private fun WantedSegmentControlSolidLayout(
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

@DevicePreviews
@Composable
private fun WantedSegmentedControlSolidPreview() {
    DesignSystemTheme {
        val items = remember {
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
                WantedSegmentedControlSolid(
                    modifier = Modifier,
                    items = items,
                    selectedIndex = selectedIndex,
                    onClick = {
                        selectedIndex = it
                    }
                )

                WantedSegmentedControlSolid(
                    modifier = Modifier,
                    itemCount = items.size,
                    selectedIndex = selectedIndex,
                    onClick = {
                        selectedIndex = it
                    },
                    item = { index ->
                        WantedSegmentedControlSolidItem(
                            modifier = Modifier.fillMaxWidth(),
                            title = items[index],
                            isSelected = index == selectedIndex,
                            icon = {
                                Icon(
                                    modifier = Modifier.fillMaxSize(),
                                    painter = painterResource(id = R.drawable.ic_normal_circle_exclamation_fill_svg),
                                    contentDescription = ""
                                )
                            }
                        )
                    }
                )
            }
        }
    }
}