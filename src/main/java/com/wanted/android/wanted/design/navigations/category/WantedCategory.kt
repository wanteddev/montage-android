package com.wanted.android.wanted.design.navigations.category

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.chip.actionchip.WantedActionChip
import com.wanted.android.wanted.design.actions.chip.actionchip.WantedActionChipContract.ActionChipSize
import com.wanted.android.wanted.design.actions.chip.actionchip.WantedActionChipContract.ActionChipVariant
import com.wanted.android.wanted.design.navigations.category.WantedCategoryContract.Size
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews


/**
 * 선택형 액션칩 목록을 표시하는 카테고리 컴포넌트입니다.
 *
 * 전달된 문자열 리스트를 기반으로 원하는 크기, 스타일, 정렬 및 선택 상태로 액션칩을 구성합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedCategory(
 *     itemList = listOf("태그1", "태그2"),
 *     selectedList = listOf("태그1"),
 *     onClick = { tag, selected -> ... }
 * )
 * ```
 *
 * @param itemList List<String>: 표시할 문자열 목록입니다.
 * @param selectedList List<String>: 선택된 항목 리스트입니다.
 * @param modifier Modifier: 레이아웃 설정용 Modifier입니다.
 * @param state LazyListState: 내부 LazyRow 상태를 제어하는 객체입니다.
 * @param size Size: 카테고리 항목 크기입니다.
 * @param horizontalPadding Boolean: 좌우 여백 여부입니다.
 * @param isVerticalPadding Boolean: 상하 여백 여부입니다.
 * @param isAlternative Boolean: 선택 시 Outlined 스타일 적용 여부입니다.
 * @param gradientColor Color: 좌우 그라디언트 배경 색상입니다.
 * @param rightIcon (@Composable (Dp) -> Unit)?: 우측에 표시할 아이콘 슬롯입니다.
 * @param onClick (item: String, isSelected: Boolean) -> Unit: 항목 클릭 시 선택 상태를 포함하여 호출됩니다.
 */
@Composable
fun WantedCategory(
    itemList: List<String>,
    selectedList: List<String>,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    size: Size = Size.Medium,
    horizontalPadding: Boolean = false,
    isVerticalPadding: Boolean = false,
    isAlternative: Boolean = false,
    gradientColor: Color = colorResource(R.color.background_normal_normal),
    rightIcon: @Composable ((Dp) -> Unit)? = null,
    onClick: (item: String, isSelected: Boolean) -> Unit
) {
    WantedCategory(
        modifier = modifier,
        state = state,
        size = size,
        horizontalPadding = horizontalPadding,
        isVerticalPadding = isVerticalPadding,
        gradientColor = gradientColor,
        rightIcon = rightIcon,
        content = {
            itemsIndexed(itemList) { index, item ->
                WantedActionChip(
                    text = item,
                    variant = if (selectedList.contains(item)) {
                        if (isAlternative) {
                            ActionChipVariant.Outlined
                        } else {
                            ActionChipVariant.Solid
                        }
                    } else {
                        ActionChipVariant.Outlined
                    },
                    size = when (size) {
                        Size.Small -> ActionChipSize.XSmall
                        Size.Medium -> ActionChipSize.Small
                        Size.Large -> ActionChipSize.Medium
                        Size.XLarge -> ActionChipSize.Large
                    },
                    isActive = selectedList.contains(item),
                    onClick = {
                        onClick(item, !selectedList.contains(item))
                    }
                )
            }
        }
    )
}

/**
 * 사용자 정의 콘텐츠로 구성할 수 있는 카테고리 컴포넌트입니다.
 *
 * LazyListScope 기반으로 직접 항목을 구성할 수 있으며, 좌우 그라디언트 및 아이콘 슬롯도 제공합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedCategory {
 *     items(10) {
 *         WantedActionChip(...)
 *     }
 * }
 * ```
 *
 * @param modifier Modifier: 레이아웃 설정용 Modifier입니다.
 * @param state LazyListState: LazyRow의 스크롤 상태입니다.
 * @param size Size: 액션칩 크기 및 여백 설정입니다.
 * @param horizontalPadding Boolean: 좌우 패딩 여부입니다.
 * @param isVerticalPadding Boolean: 상하 패딩 여부입니다.
 * @param gradientColor Color: 좌우 그라디언트 색상입니다.
 * @param rightIcon (@Composable (Dp) -> Unit)?: 우측 아이콘 슬롯입니다.
 * @param content LazyListScope.() -> Unit: 내부 아이템 UI 구성 블록입니다.
 */
@Composable
fun WantedCategory(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    size: Size = Size.Medium,
    horizontalPadding: Boolean = false,
    isVerticalPadding: Boolean = false,
    gradientColor: Color = colorResource(R.color.background_normal_normal),
    rightIcon: @Composable ((Dp) -> Unit)? = null,
    content: LazyListScope.() -> Unit
) {
    CompositionLocalProvider(LocalCategoryGradationColor provides gradientColor) {
        WantedCategoryLayout(
            modifier = modifier
                .fillMaxWidth(),
            size = size,
            isLeftGradient = if (horizontalPadding) false else state.canScrollBackward,
            isRightGradient = when {
                horizontalPadding && rightIcon != null -> state.canScrollForward
                horizontalPadding -> false
                else -> state.canScrollForward
            },
            content = {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = if (isVerticalPadding) size.verticalPadding else 0.dp),
                    state = state,
                    contentPadding = PaddingValues(horizontal = if (horizontalPadding) 20.dp else 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(size.horizontalSpacing)
                ) {
                    content()
                }
            },
            rightIcon = rightIcon
        )
    }
}


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun WantedCategoryLayout(
    isLeftGradient: Boolean,
    isRightGradient: Boolean,
    rightIcon: @Composable ((Dp) -> Unit)?,
    modifier: Modifier = Modifier,
    size: Size = Size.Medium,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ConstraintLayout(
                modifier = Modifier.weight(1f)
            ) {
                val (tabRef, leftGradientRef, rightGradientRef) = createRefs()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .constrainAs(tabRef) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    content()
                }

                if (isLeftGradient) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 1.dp)
                            .constrainAs(leftGradientRef) {
                                top.linkTo(tabRef.top)
                                bottom.linkTo(tabRef.bottom)
                                start.linkTo(tabRef.start)
                                height = Dimension.fillToConstraints
                            }
                            .width(48.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        LocalCategoryGradationColor.current,
                                        colorResource(id = android.R.color.transparent)
                                    )
                                )
                            )
                    )
                }

                if (isRightGradient) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 1.dp)
                            .constrainAs(rightGradientRef) {
                                top.linkTo(tabRef.top)
                                bottom.linkTo(tabRef.bottom)
                                end.linkTo(tabRef.end)
                                height = Dimension.fillToConstraints
                            }
                            .width(48.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        colorResource(id = android.R.color.transparent),
                                        LocalCategoryGradationColor.current
                                    )
                                )
                            )
                    )
                }
            }

            rightIcon?.let {
                BoxWithConstraints(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 8.dp)
                        .size(
                            when (size) {
                                Size.Small -> 20.dp
                                Size.Medium -> 22.dp
                                else -> 24.dp
                            }
                        )
                ) {
                    rightIcon(maxHeight)
                }
            }
        }
    }
}


@DevicePreviews
@Composable
private fun WantedCategoryPreview() {
    DesignSystemTheme {
        val itemList = mutableListOf<String>()
        repeat(20) {
            itemList.add("텍스트$it")
        }

        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedCategory(
                    modifier = Modifier,
                    size = Size.Medium,
                    content = {
                        itemsIndexed(itemList) { index, item ->
                            WantedActionChip(
                                text = item,
                                variant = if (index == 0) {
                                    ActionChipVariant.Solid
                                } else {
                                    ActionChipVariant.Outlined
                                },
                                size = ActionChipSize.Small,
                                isActive = index == 0
                            )
                        }
                    }
                )

                WantedCategory(
                    modifier = Modifier,
                    size = Size.Medium,
                    itemList = itemList,
                    selectedList = listOf(itemList.first()),
                    onClick = { _, _ -> }
                )

                WantedCategory(
                    modifier = Modifier,
                    size = Size.Medium,
                    itemList = itemList,
                    isAlternative = true,
                    selectedList = listOf(itemList.first()),
                    onClick = { _, _ -> }
                )
            }
        }
    }
}