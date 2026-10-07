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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedDropShadowDefaults.WantedShadowStyle
import com.wanted.android.wanted.design.base.wantedDropShadow
import com.wanted.android.wanted.design.input.segmentedcontrol.WantedSegmentedDefaults.SegmentedSize
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.clickOnce
import kotlinx.coroutines.launch


/**
 * WantedSegmentedControl
 *
 * 문자열 리스트 기반의 Segmented Control 컴포넌트입니다.
 *
 * 선택된 항목을 강조 표시하며, 애니메이션되는 Knob으로 선택 상태를 표현합니다.
 *
 * 사용 예시:
 * ```kotlin
 * val items = listOf("전체", "읽음", "안읽음")
 * var selectedIndex by remember { mutableIntStateOf(0) }
 *
 * WantedSegmentedControl(
 *     items = items,
 *     selectedIndex = selectedIndex,
 *     onClick = { selectedIndex = it }
 * )
 * ```
 *
 * @param items List<String>: 표시할 항목 텍스트 리스트입니다.
 * @param selectedIndex Int: 현재 선택된 항목의 인덱스입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param size SegmentedSize: 컴포넌트의 크기입니다. Small, Medium, Large 중 선택할 수 있습니다.
 * @param onClick (Int) -> Unit: 항목 클릭 시 선택된 인덱스를 전달하는 콜백 함수입니다.
 */
@Composable
fun WantedSegmentedControl(
    items: List<String>,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    size: SegmentedSize = SegmentedSize.Medium,
    onClick: (index: Int) -> Unit = {}
) {
    WantedSegmentedControl(
        modifier = modifier,
        itemCount = items.size,
        selectedIndex = selectedIndex,
        size = size,
        onClick = onClick,
        item = { index ->
            WantedSegmentedControlItem(
                modifier = Modifier.fillMaxWidth(),
                title = items[index],
                isSelected = index == selectedIndex
            )
        }
    )
}


/**
 * WantedSegmentedControl
 *
 * 사용자 정의 항목으로 구성할 수 있는 Segmented Control 컴포넌트입니다.
 *
 * 각 항목을 커스텀 컴포넌트로 구성할 수 있으며, 선택 애니메이션은 Knob 위치 이동으로 표현됩니다.
 * `iconOnly = true` 로 설정하면 각 세그먼트가 사이즈별 고정 너비로 배치되고, [LocalWantedSegmentedIconOnly]
 * 를 통해 하위 [WantedSegmentedControlItem] 이 아이콘 전용으로 렌더링됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * var selectedIndex by remember { mutableIntStateOf(0) }
 *
 * WantedSegmentedControl(
 *     itemCount = 3,
 *     selectedIndex = selectedIndex,
 *     item = { index ->
 *         WantedSegmentedControlItem(
 *             title = "옵션 $index",
 *             isSelected = index == selectedIndex,
 *             icon = { Icon(...) }
 *         )
 *     },
 *     onClick = { selectedIndex = it }
 * )
 * ```
 *
 * @param itemCount Int: 표시할 항목 개수입니다.
 * @param selectedIndex Int: 현재 선택된 항목의 인덱스입니다.
 * @param item @Composable (Int) -> Unit: 각 항목을 렌더링하는 Composable 슬롯입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param size SegmentedSize: 컴포넌트의 크기입니다. Small, Medium, Large 중 선택할 수 있습니다.
 * @param iconOnly Boolean: 아이콘 전용 모드 여부입니다. true 이면 세그먼트가 사이즈별 고정 너비로 배치됩니다.
 * @param onClick (Int) -> Unit: 항목 클릭 시 선택된 인덱스를 전달하는 콜백 함수입니다.
 */
@Composable
fun WantedSegmentedControl(
    itemCount: Int,
    selectedIndex: Int,
    item: @Composable (index: Int) -> Unit,
    modifier: Modifier = Modifier,
    size: SegmentedSize = SegmentedSize.Medium,
    iconOnly: Boolean = false,
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

    CompositionLocalProvider(
        LocalWantedSegmentedSize.provides(size),
        LocalWantedSegmentedIconOnly provides iconOnly
    ) {
        WantedSegmentControlLayout(
            modifier = modifier,
            size = size,
            iconOnly = iconOnly,
            knob = {
                WantedSegmentedControlKnob(
                    knobRadius = size.knobRadius,
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
                            .then(
                                // iconOnly: 항목이 size.iconOnlyWidth × size.height 고정 크기라 세그먼트는 이를 감싸고,
                                // text/icon+text: 균등 분할한다.
                                if (iconOnly) {
                                    Modifier
                                } else {
                                    Modifier.weight(1f)
                                }
                            )
                            .fillMaxHeight()
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
private fun WantedSegmentedControlKnob(
    knobRadius: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.wantedDropShadow(
            style = WantedShadowStyle.XSmall(
                borderRadius = knobRadius,
                backgroundColor = DesignSystemTheme.colors.surfaceElevatedPrimary
            )
        )
    )
}

@Composable
private fun WantedSegmentControlLayout(
    size: SegmentedSize,
    iconOnly: Boolean,
    knob: @Composable () -> Unit,
    contents: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(size.containerRadius))
            .background(DesignSystemTheme.colors.surfaceNeutralSecondary)
            .padding(WantedSegmentedDefaults.ContainerPadding)
    ) {
        knob()

        Row(
            modifier = if (iconOnly) Modifier else Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            contents()
        }
    }
}

@DevicePreviews
@Composable
private fun WantedSegmentedControlPreview() {
    DesignSystemTheme {
        val items = remember {
            val items = mutableListOf<String>()
            for (index in 0..<3) {
                items.add("텍스트${index + 1}")
            }
            items
        }

        var selectedIndex by remember { mutableIntStateOf(0) }
        var iconSelectedIndex by remember { mutableIntStateOf(0) }

        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                SegmentedSize.entries.forEach { size ->
                    WantedSegmentedControl(
                        items = items,
                        selectedIndex = selectedIndex,
                        size = size,
                        onClick = { selectedIndex = it }
                    )
                }

                WantedSegmentedControl(
                    itemCount = items.size,
                    selectedIndex = selectedIndex,
                    size = SegmentedSize.Large,
                    onClick = { selectedIndex = it },
                    item = { index ->
                        WantedSegmentedControlItem(
                            modifier = Modifier.fillMaxWidth(),
                            title = items[index],
                            isSelected = index == selectedIndex,
                            icon = {
                                Icon(
                                    modifier = Modifier.fillMaxSize(),
                                    painter = painterResource(id = R.drawable.icon_normal_circle_exclamation_fill),
                                    contentDescription = ""
                                )
                            }
                        )
                    }
                )

                SegmentedSize.entries.forEach { size ->
                    WantedSegmentedControl(
                        itemCount = 3,
                        selectedIndex = iconSelectedIndex,
                        size = size,
                        iconOnly = true,
                        onClick = { iconSelectedIndex = it },
                        item = { index ->
                            WantedSegmentedControlItem(
                                isSelected = index == iconSelectedIndex,
                                icon = {
                                    Icon(
                                        modifier = Modifier.fillMaxSize(),
                                        painter = painterResource(id = R.drawable.icon_normal_circle_exclamation_fill),
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
}
